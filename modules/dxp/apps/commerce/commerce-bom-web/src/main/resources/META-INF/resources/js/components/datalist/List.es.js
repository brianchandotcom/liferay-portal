import React, {
    Fragment
} from 'react';

import HighlightedText from '../utilities/HighlightedText.es';

export function Empty() {
    return (
        <div className="commerce-datalist__option commerce-datalist__option--empty">
            {Liferay.Language.get('no-results-found')}
        </div>
    )
}

export function CheckableListElement(props) {
    return (
		<label
			className="commerce-datalist__option commerce-datalist__option--label"
		>
			<input
				type="checkbox"
				value={props.value}
                autoComplete="off"
                checked={props.selected}
				onChange={
                    (e) => {
                        props.updateElementState(
                        e,
                        {
                            label: props.label,
                            value: props.value
                        },
                        !props.selected
                    )}
                }
			/>
            <HighlightedText 
                text={props.label}
                query={props.query}
            />
		</label>
    )
}

export function BaseListElement(props) {
    const detaElementClasses = `commerce-datalist__option commerce-datalist__option--select${(props.selected) ? ' commerce-datalist__option--active' : ''}`;

    return (
        <div 
            className={ detaElementClasses } 
            value={ props.formattedValue || null } 
            onClick={
                (e) => props.value
                ? props.updateElementState(
                    e,
                    {
                        label: props.label,
                        value: props.value
                    },
                    true
                )
                : props.resetState()
            }
        >
            <HighlightedText 
                text={props.label || 'none'}
                query={props.query}
            />
        </div>
    )    
}

export function ListElement(props) {
    const { checkable, ...data } = props;

    return checkable 
        ? <CheckableListElement {...data} />
        : <BaseListElement {...data} />
}

export function isElementSelected(currentElValue, selectedData) {
    return selectedData.reduce(
        (acc, addedElement) => acc || currentElValue === addedElement.value,
        false
    )
}

export function List(props) {
    if(
        props.query && 
        props.data && 
        props.data.length && 
        props.filteredValues
    ) {
        return (
            props.filteredValues.length 
            ? <Fragment>
                {
                    !props.multiselect && 
                    <BaseListElement 
                        resetState={props.resetState}
                    />
                }
                {
                    props.filteredValues.map(
                        filteredValue => {
                            return props.data
                                .filter( dataElement => dataElement.value === filteredValue )
                                .map(
                                    (dataElement, i) => (
                                        <ListElement
                                            key={ i }
                                            checkable={ !!props.multiselect }
                                            value={ dataElement.value }
                                            label={ dataElement.label }
                                            query={ props.query }
                                            name={ props.name }
                                            selected={ isElementSelected(dataElement.value, props.selectedData) }
                                            updateElementState={ props.updateElementState }
                                        />
                                    )
                                )
                        }
                    )
                }
            </Fragment>
            : <Empty />
        )
    }

    return (
        props.data && 
        props.data.length
    ) ? (
        <Fragment>
            {
                !props.multiselect && 
                <BaseListElement 
                    resetState={props.resetState}
                />
            }
            {props.data.map( 
                (dataElement, i) => (
                    <ListElement
                        key={i}
                        checkable={ !!props.multiselect }
                        value={ dataElement.value }
                        label={ dataElement.label }
                        query={ props.query }
                        name={ props.name }
                        selected={ isElementSelected(dataElement.value, props.selectedData) }
                        updateElementState={ props.updateElementState }
                    />
                )
            )}
        </Fragment>
    ) : <Empty />
}

export default List;