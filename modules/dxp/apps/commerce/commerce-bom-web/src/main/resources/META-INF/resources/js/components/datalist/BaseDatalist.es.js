import React, {
    useRef,
    useState,
    useEffect
} from 'react';

import { pipe } from '../../utilities/index.es';

import List from './List.es';
import Icon from '../utilities/Icon.es';

export function SelectedData(props) {
    return (
        <div className="commerce-datalist__values">
            {
                props.data && props.data.map(
                    (selectedElement, i) => (
                        <span 
                            key={i}
                            className="commerce-datalist__value"
                        >
                            {selectedElement.label}
                            <button 
                                type="button" 
                                className="commerce-datalist__delete-value"
                                onClick={
                                    (e) => props.updateElementState(
                                        e, 
                                        {
                                            label: selectedElement.label,
                                            value: selectedElement.value
                                        },
                                        false
                                    )
                                }
                            >
                                <Icon 
                                    spritemap={props.spritemap}
                                    symbol='close'
                                />
                            </button>
                        </span>
                    )
                )
            }       
        </div>
    )
}

export function BaseDatalist(props) {

    const [dropdownState, setDropdownState] = useState('collapsed')
    const [selectedData, updateSelectedData] = useState([])
    const [selectedValues, updateSelectedValues] = useState([])
    const [query, setQuery] = useState(props.value || null)

    const containerClasses = `commerce-datalist${props.additionalClasses ? ` ${props.additionalClasses}`: ''}${props.disabled ? ` is-disabled`: ''}`
    const inputWrapperClasses = `commerce-datalist__input-wrapper${dropdownState ? ` is-${dropdownState}` : ''}`
    const dropdownClasses = `commerce-datalist__dropdown${dropdownState ? ` is-${dropdownState}` : ''}`;

    const datalistRef = useRef();
    const dropdownRef = useRef();
    const addedDataContainer = useRef();

    const handleChangeAndUpdateData = pipe(handleValueChange, updateSelectedData);

    function handleValueChange(newValues) {
        props.emit('selectedValuesChanged', newValues);
        return newValues
    }

    function handleOutsideClick(e) {
        e.preventDefault();
        if(
            !datalistRef.current.contains(e.target)
        ) {
            collapseDropdown();
        }
    }

    function updateElementState(e, listElement, toBeAdded = true) {
        if(props.multiselect) {
            handleChangeAndUpdateData(
                toBeAdded 
                ? [
                    ...selectedData,
                    listElement
                ]
                : selectedData.filter(
                    el => el.label !== listElement.label
                )
            )
        } else {
            handleChangeAndUpdateData(listElement.value ? [listElement] : [])
            setQuery(listElement.label);
        }
        collapseDropdown();
    }

    function handleInputChange(e) {
        updateQuery(e.target.value)
    }

    function updateQuery(value = null) {
        setQuery(value);
        props.emit('queryUpdated', value);
    }

    function resetState() {
        updateQuery();
        handleChangeAndUpdateData([]);
        collapseDropdown();
    }
    
    function resetQueryAndCollapse() {
        updateQuery();
        collapseDropdown();
    }


    function expandDropdown() {
        setDropdownState('expanding');
        dropdownRef.current.addEventListener(
            'animationend',
            handleOpeningAnimationEnd,
            {
                once: true
            }
        )
    }

    function handleOpeningAnimationEnd(e) {
        setDropdownState('expanded');
        window.addEventListener('click', handleOutsideClick, {once: true});
    }

    function collapseDropdown() {
        setDropdownState('collapsing');
        dropdownRef.current.addEventListener(
            'animationend',
            handleClosingnimationEnd,
            {
                once: true
            }
        )
    }

    function handleClosingnimationEnd() {
        setDropdownState('collapsed');
    }

    useEffect(() => {
        const newSelectedValues = selectedData ? selectedData.map(el => el.value) : [];
        if(JSON.stringify(selectedValues) !== JSON.stringify(newSelectedValues)){
            updateSelectedValues(newSelectedValues);
            scrollAddedDataContainer()
        }
    }, [
        selectedData,
        selectedValues
    ])

    function scrollAddedDataContainer() {
        if(addedDataContainer.current) {
            setTimeout(() => {
                addedDataContainer.current.scrollTo({
                    top: 0,
                    left: Number.MAX_SAFE_INTEGER,
                    behaviour: 'smooth'
                });
            }, 50);
        }
    }

    return (
        <div 
            className={containerClasses} 
            ref={datalistRef}
        >
            { 
                props.label && 
                <label
                    htmlFor={props.Id}
                    className="commerce-modal__label"
                >
                    {props.label}
                </label>
            }
            <div 
                className={inputWrapperClasses}
                onClick={(e) => dropdownState === 'collapsed' && expandDropdown(e)}
            >
                <div className="commerce-datalist__mask">
                    <div 
                        ref={addedDataContainer}
                        className="commerce-datalist__query-wrapper"
                    >
                        { 
                            props.multiselect && 
                            <SelectedData 
                                data={selectedData}
                                updateElementState={updateElementState}
                                spritemap={props.spritemap}
                            />
                        }
                        <input 
                            className="commerce-datalist__query-input"
                            onChange={handleInputChange}
                            id={props.inputId}
                            onFocus={(e) => dropdownState === 'collapsed' && expandDropdown(e)}
                            autoComplete="off" 
                            placeholder={props.placeholder}
                            type="text"
                            value={query || ''}
                        />
                    </div>
                </div>
                <span className="commerce-datalist__icon-wrapper">
                    <button 
                        className="commerce-datalist__icon commerce-datalist__icon--reset"
                        onClick={resetQueryAndCollapse}
                    >
                        <Icon 
                            spritemap={props.spritemap}
                            symbol='reset'
                        />
                    </button>
                    <span className="commerce-datalist__icon commerce-datalist__icon--default">
                        <Icon 
                            spritemap={props.spritemap}
                            symbol='chevron'
                        />
                    </span>
                </span>
            </div>

            <div 
                ref={dropdownRef}
                className={dropdownClasses}
            >
                <div className="commerce-datalist__data">
                    <List 
                        query={ query }
                        updateElementState={ updateElementState }
                        selectedData={ selectedData }
                        resetState={ resetState }
                        disabled
                        { ...props } 
                    />
                </div>
            </div>
        </div>
    );
}

export default BaseDatalist;
