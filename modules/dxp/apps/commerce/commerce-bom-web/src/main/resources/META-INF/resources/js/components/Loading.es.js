import React from "react";

export default function Loading() {
    return (
        <div 
            className="panel panel-secondary"
        >
            <div className="panel-body">
                <h3 className="text-center">
                    {Liferay.Language.get('loading')}
                </h3>
            </div>
        </div>
    )
}

