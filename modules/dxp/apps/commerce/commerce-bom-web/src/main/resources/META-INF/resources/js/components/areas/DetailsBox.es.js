import React from 'react';

import { StoreContext } from '../StoreContext.es';

export function DetailsListElement(props) {
    const { state, actions } = React.useContext(StoreContext);

    const highlightedModifierClass = 
        (
            state.area.highlightedDetail && 
            state.area.highlightedDetail.number === props.number 
        )
        ? ' detail-row--highlighted' 
        : '';

    return (
        <a
            className={`detail-row d-table-row${highlightedModifierClass}`} 
            href={props.url && (state.app.basePathUrl + props.url)}
            onFocus={() => actions.highlightDetail(props.number, true)}
            onMouseOver={() => actions.highlightDetail(props.number, true)}
            onMouseOut={() => actions.highlightDetail(null)}
        >
            <div className="d-table-cell">
                <span className="autocomplete-item">{props.number}</span>
            </div>
            <div className="d-table-cell">
                {props.name}
            </div>
            <div className="d-table-cell u-tar">{props.sku}</div>
        </a>
    )
}

function DetailsBox() {
    const { state } = React.useContext(StoreContext);

    const spotsFilteredByNumbers = state.area.spots.reduce((filtered, spot, i) => {
        if(i && filtered[filtered.length - 1].number === spot.number) {
            return filtered;
        }
        return filtered.concat(spot);
    },[])

    const list = spotsFilteredByNumbers.map(spot => {
        const relatedProduct = state.area.products.reduce(
            (acc, prod) => acc || (prod.id === spot.productId ? prod : false),
            false
        );
        return {
            number: spot.number,
            name: relatedProduct.name,
            sku: relatedProduct.sku,
            url: relatedProduct.url
        }
    })

    return (
        <div className="panel panel-secondary grid-panel">
            <div className="panel-heading">
                <h2 className="panel-title">{state.area.name}</h2>
            </div>
            <div className="panel-body">
                <div className="products-table commerce-small-table d-table">
                    <div className="d-table-head-group">
                        <div className="d-table-row">
                            <div className="d-table-cell">
                                {Liferay.Language.get('n')}
                            </div>
                            <div className="d-table-cell">
                                {Liferay.Language.get('name')}
                            </div>
                            <div className="d-table-cell u-tar">
                                {Liferay.Language.get('sku')}
                            </div>
                        </div>
                    </div>
                    <div className="d-table-row-group">
                        { list.map((detail, i) => <DetailsListElement key={i} {...detail} />) }
                    </div>
                </div>
            </div>
        </div>
    );
}

export default DetailsBox;
