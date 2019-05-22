import React from 'react';
import ReactDOM from 'react-dom';
import SynonymSetsForm from './components/SynonymSetsForm.es';
import ThemeContext from './ThemeContext.es';

export default function(id, props, context) {
	ReactDOM.render(
		<ThemeContext.Provider value={context}>
			<div className="synonym-sets-root">
				<SynonymSetsForm {...props} />
			</div>
		</ThemeContext.Provider>,
		document.getElementById(id)
	);
}