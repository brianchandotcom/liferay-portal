import React from 'react';

function BaseContainer() {
    return (
        <div className="panel panel-secondary">
            <div className="panel-body">
                <h2>
                    {Liferay.Language.get('select-car-and-parts')}
                </h2>
                <h4>
                    {Liferay.Language.get('please-fill-the-form-select-above-to-start-your-research')}
                </h4>
            </div>
        </div>
    )
}

export default BaseContainer;
