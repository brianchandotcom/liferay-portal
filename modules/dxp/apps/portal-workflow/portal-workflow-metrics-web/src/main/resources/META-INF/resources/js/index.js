import AppComponent from './components/App';
import React from 'react';
import ReactDOM from 'react-dom';

export default function(elementId, defaultDelta, deltas, maxPages) {
	ReactDOM.render(
		<AppComponent
			companyId={Liferay.ThemeDisplay.getCompanyId()}
			defaultDelta={defaultDelta}
			deltas={deltas}
			maxPages={maxPages}
		/>,
		document.getElementById(elementId)
	);
}