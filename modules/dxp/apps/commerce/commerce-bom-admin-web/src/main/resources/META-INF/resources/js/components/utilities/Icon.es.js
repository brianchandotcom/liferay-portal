import React from "react";

export default function Icon(props) {
    const { 
        symbol,
        spritemap,
        className,
        ...otherProps
    } = props;

    return (
        <svg 
            {...otherProps}
            className={`lexicon-icon lexicon-icon-${symbol}${className ? ` ${className}` : ``}`}
            role="presentation"
        >
            <use xlinkHref={`${spritemap}#${symbol}`} />
        </svg>
    )
}

