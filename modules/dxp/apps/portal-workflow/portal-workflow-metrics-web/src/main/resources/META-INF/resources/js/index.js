import AppComponent from './components/App';
import React from 'react';
import ReactDOM from 'react-dom';

export default function(elementId) {
	ReactDOM.render(
		<AppComponent companyId={Liferay.ThemeDisplay.getCompanyId()} />,
		document.getElementById(elementId)
	);
}