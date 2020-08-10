import React from 'react';

import Icon from './Icon';
import {getLocalizedText} from "../utils/utils.es";

function NoMembers(props) {
    return(
        <div className={'no-members'}>
            <p>
                <Icon symbol={'close'} spritemap={props.spritemap} />
            </p>
            <p>{ getLocalizedText('no-members-found') }</p>
        </div>
    );
}

export default NoMembers;
