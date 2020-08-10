import React,
{
    Fragment
} from 'react'

export function convertString(string) {
    try {
        return window.Liferay.Language.get(string)
    } catch (error) {
        return string
    }
}

function LocalizedText(props) {
    return(
        <Fragment>{convertString(props.children)}</Fragment>
    )
}

export default LocalizedText