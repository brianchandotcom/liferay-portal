/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

function onLocaleChange(layoutRendererInstance, event) {
	const selectedLanguageId = event.item.getAttribute('data-value');

	layoutRendererInstance.setState({editingLanguageId: selectedLanguageId});
}

export default function dataEngineLayoutRendererLanguageProxy(props) {
	let localeChangedHandler = null;

	Liferay.componentReady(props.namespace + 'dataEngineLayoutRenderer').then(
		event => {
			localeChangedHandler = Liferay.after(
				'inputLocalized:localeChanged',
				onLocaleChange.bind(this, event)
			);
		}
	);

	function destroyInstance(_event) {
		if (localeChangedHandler) {
			localeChangedHandler.detach();
		}

		Liferay.detach('destroyPortlet', destroyInstance);
	}

	Liferay.on('destroyPortlet', destroyInstance);
}
