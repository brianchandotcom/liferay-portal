import React, 
{
    useState,
    useContext
} from 'react';

import Icon from './utilities/Icon.es';
import Collapse from './collapse/Collapse.es';
import { StoreContext } from './StoreContext.es';

function ModelsList(props) {
    return (
        <ul className="list-group">
            {props.models.map(
                (
                    {
                        name,
                        power,
                        productionYears
                    },
                    i
                ) => (
                    <li 
                        className="list-group-item" 
                        key={i}
                    >
                        <div className="row">
                            <div className="col">{name}</div>
                            <div className="col-auto text-right">{power}</div>
                            <div className="col-4 text-right">{productionYears}</div>
                        </div>
                    </li>
                )
            )}
        </ul>
    )
}

function Brands(props) {
    const [selectedBrand, setSelectedBrand] = useState(0);

    const {state} = useContext(StoreContext);

    return (
        <div className="suitable-veichles panel panel-secondary sticky-panel">
            <div className="panel-heading">
                <h2 className="panel-title">
                    {Liferay.Language.get('suitable-veichles')}
                </h2>
            </div>
            <div className="panel-body">
                {
                    props.data.map(
                        (el, i) => <Collapse 
                            key={i}
                            title={el.name}
                            additionalWrapperClasses={i !== 0 && 'mt-3'}
                            content={<ModelsList models={el.models}  />}
                            open={i === selectedBrand}
                            handleOpen={() => setSelectedBrand(i)}
                            handleClose={() => setSelectedBrand(null)}
                            openIcon={<Icon spritemap={state.app.spritemap} symbol={'minus'}/>}
                            closedIcon={<Icon spritemap={state.app.spritemap} symbol={'plus'}/>}
                        />
                    )
                }
            </div>
        </div>
    )
}

function Card(props) {

    const { state, actions } = useContext(StoreContext);

    const itemsCard = (
        <div className="area-card card image-card">
            <div className="aspect-ratio aspect-ratio-4-to-3 card-item-first bg-checkered">
                <img 
                    alt={props.title}
                    src={props.thumbnail}
                    className="aspect-ratio-item-center-middle"
                />
            </div>
            <div className="card-body">
                <div className="card-row">
                    <div className="autofit-col autofit-col-expand">
                        <div 
                            className="card-title text-truncate" 
                            data-title={props.title}
                            title={props.title}
                        >
                            {props.title}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )

    function handleCardLink(e) {
        e.preventDefault();
        const url = `?${props.type === 'folder' ? 'folderId' : 'areaId'}=${props.id}`;
        state.app.history.push(url);
    }

    return (
        <a 
            className="card-link"
            href="#"
            onClick={handleCardLink}
            data-senna-off
        >
            {itemsCard}
        </a>
    )
}

function CardContainer(props) {
    return (
        <div className="area-list row">
            {props.items.map((el, i) => {
                return (
                    <div 
                        key={i}
                        className={props.cardsWrapperAdditionalClasses}
                    >   
                        <Card 
                            title={el.name}
                            thumbnail={el.thumbnail}
                            id={el.id}
                            type={el.type}
                        />
                    </div>
                )
            })}
        </div>
    )
}

export default function FolderViewer() {

    const { state } = useContext(StoreContext);

    return state.folder.items
        ? (
            <div className="row">
                <div className="col">
                    <CardContainer 
                        items={state.folder.items}
                        cardsWrapperAdditionalClasses={
                            state.folder.brands
                                ? 'col-3' 
                                : 'col-2'
                        }
                    />
                </div>
                {state.folder.brands && (
                    <div className="col-sm-4 position-relative">
                        <Brands
                            data={state.folder.brands}
                        />
                    </div>
                )}
            </div>
        )
        : null
}