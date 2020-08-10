import React from 'react';
import {getLocalizedText} from "../utils/utils.es";

export default function PaneOrgInfo(props) {
    const {showMenu, orgName, childrenNo, colorIdentifier} = props;

    return (
        <div className='pane-org-info'>
            <div className={'org-color-identifier'}
                 style={{backgroundColor: colorIdentifier}}></div>
            <div className='org-data'>
                <p>{orgName}</p>
                <p>{!!childrenNo ? `${childrenNo} ${getLocalizedText('suborganizations')}` : `${getLocalizedText('suborganization')}`}</p>
            </div>
            <div role='button' tabIndex='1' className='org-actions'>
                <p style={{display: 'none'}}>&sdot;&sdot;&sdot;</p>
            </div>
        </div>
    );
}
