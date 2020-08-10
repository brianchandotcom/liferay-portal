import React from 'react';

function ErrorMessage() {
    return (
        <div className="panel panel-secondary">
            <div className="panel-body">
                <h2 className="text-center">
                    {Liferay.Language.get('unexpected-error')}
                </h2>
            </div>
        </div>
    )
}

export default ErrorMessage;
