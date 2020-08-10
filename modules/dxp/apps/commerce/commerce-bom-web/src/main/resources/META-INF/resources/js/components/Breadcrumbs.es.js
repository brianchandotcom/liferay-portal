import React, {useContext} from 'react';

import {StoreContext} from './StoreContext.es';

function Breadcrumbs(props) {

	const { state } = useContext(StoreContext);

    return props.data ? (
        <ol className="breadcrumb mb-3">
            {props.data.map(
                (el, i) => {
                    const content = <span className="breadcrumb-text-truncate">{el.label}</span>

                    function handleBreadcrumbLink(e) {
                        e.preventDefault();
		                const filteredId = /^.*\/(.*)$/.exec(el.url)[1]
                        const formattedUrl = `?folderId=${filteredId}`;
                        state.app.history.push(formattedUrl);
                    }

                    return (
                        <li className="breadcrumb-item" key={i}>
                            {
                                el.url 
                                    ? <a href="#" onClick={handleBreadcrumbLink} key={i} data-senna-off>{content}</a>
                                    : content
                            }
                        </li>
                    )
                })
            }
        </ol>
    ) : null;
}

export default Breadcrumbs;
