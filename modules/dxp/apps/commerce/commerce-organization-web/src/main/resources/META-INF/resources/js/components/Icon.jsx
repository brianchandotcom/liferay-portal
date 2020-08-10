import React from "react";

export default function Icon(props) {
	return (
		<svg aria-hidden="true" className="lexicon-icon"
			role="presentation"
			viewBox="0 0 24 24">
			<title>{props.symbol}</title>
			<use xlinkHref={`${props.spritemap}#${props.symbol}`} />
		</svg>
	)
}
