import React from 'react';
import ReactDOM from 'react-dom';
import App from './App.es';

import { StoreProvider } from './components/StoreContext.es';

export default function(componentId, id, props) {
	const portletFrame = window.document.getElementById(id);
	let instance = null;
	ReactDOM.render(
		<StoreProvider>
			<App
				ref={component => {
					instance = component;
				}}
				showFilters={false}
				{...props}
			/>
		</StoreProvider>,
		portletFrame
	);
	if (window.Liferay) {
		window.Liferay.component(componentId, instance);
	}
	return instance;
}
