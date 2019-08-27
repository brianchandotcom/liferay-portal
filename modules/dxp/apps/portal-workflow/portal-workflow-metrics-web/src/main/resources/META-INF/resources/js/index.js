import 'string.prototype.startswith';
import AppComponent from './components/App';
import React from 'react';
import ReactDOM from 'react-dom';

export default function(defaultDelta, deltas, isAmPm, maxPages, namespace) {
	const container = document.getElementById(`${namespace}root`);

	const buildContainer = () => {
		ReactDOM.render(
			<AppComponent
				companyId={Liferay.ThemeDisplay.getCompanyId()}
				defaultDelta={defaultDelta}
				deltas={deltas}
				isAmPm={isAmPm}
				maxPages={maxPages}
				namespace={namespace}
			/>,
			container
		);
		container.setAttribute('data-rendered', true);
	};

	if (!container.getAttribute('data-rendered')) {
		buildContainer();
	}

	Liferay.once('destroyPortlet', () => {
		ReactDOM.unmountComponentAtNode(container);
	});
}
