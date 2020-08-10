import React from "react";

function processQuery( query, text = '' ) {
    const regex = new RegExp(`(.*?)(${query || ''})(.*)`, 'gmi');
    const results = regex.exec(text);

    return results 
        ? Array(3).fill('').map((_, i) => results[i + 1].toString())
        : [text, '', ''];
}

export default function HighlightedText(props) {

    const [
        firstPart,
        highlightedPart,
        thirdPart
    ] = processQuery(props.query, props.text);

    return (
        <span className="autocomplete-item">
            {props.inverted 
                ? (
                    <React.Fragment>
                        {firstPart}
                        {highlightedPart && <strong>{highlightedPart}</strong>}
                        {thirdPart}
                    </React.Fragment>
                )
                : (
                    <React.Fragment>
                        <strong>{firstPart}</strong>
                        {highlightedPart}
                        <strong>{thirdPart}</strong>
                    </React.Fragment>
                )
            }
        </span>
    );
}

