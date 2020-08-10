import React from 'react';

function BaseContainer() {
    return (
        <div className="panel panel-secondary">
            <div className="panel-body">
                <h4>
                    {Liferay.Language.get('loading')}
                </h4>
            </div>
        </div>
    )
}

export default BaseContainer;
