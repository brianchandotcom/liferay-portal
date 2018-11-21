import React from 'react';
import ReactDOM from 'react-dom';
import ClayODataQueryBuilder from './ClayODataQueryBuilder.es';
import ThemeContext from './ThemeContext.es';

export default function(id, props, context) {
	ReactDOM.render(
		<ThemeContext.Provider value={context}>
			<ClayODataQueryBuilder {...props} />
		</ThemeContext.Provider>,
		document.getElementById(id)
	);
}