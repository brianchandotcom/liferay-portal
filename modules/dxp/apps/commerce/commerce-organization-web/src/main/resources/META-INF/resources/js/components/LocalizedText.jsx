import React, { Fragment } from 'react'
import { getLocalizedText } from "../utils/utils.es";

export default function LocalizedText(props) {
	return(
		<Fragment>
			{ getLocalizedText(props.children) }
		</Fragment>
	)
}
