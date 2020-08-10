import React,
{
    Fragment
} from 'react'

import {
    convertString
} from '../../utilities/localization.es';

function LocalizedText(props) {
    return(
        <Fragment>{convertString(props.children)}</Fragment>
    )
}

export default LocalizedText