import 'string.prototype.startswith';
import AppComponent from './components/App';
import React from 'react';
import ReactDOM from 'react-dom';

export default function(defaultDelta, deltas, maxPages, namespace, portletId) {
	const container = document.getElementById(`${namespace}root`);

	const buildContainer = () => {
		ReactDOM.render(
			<AppComponent
				companyId={Liferay.ThemeDisplay.getCompanyId()}
				defaultDelta={defaultDelta}
				deltas={deltas}
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

	Liferay.once('beforeNavigate', ({path = ''}) => {
		if (path.indexOf(portletId) > -1) {
			ReactDOM.unmountComponentAtNode(container);
			buildContainer();
		}
		else {
			ReactDOM.unmountComponentAtNode(container);
		}
	});
}