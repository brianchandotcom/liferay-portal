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

import {delegate} from 'frontend-js-web';

import openSaveTemplateModalImplementation from './OpenSaveTemplateModal.es';

export default function ({
	buttonContainerId,
	formSaveAsTemplateDataQuerySelector,
	formSaveAsTemplateURL,
	portletNamespace,
}) {
	const buttonContainer = document.getElementById(buttonContainerId);

	const onClick = (event) => {
		event.preventDefault();

		openSaveTemplateModalImplementation({
			dialogTitle: Liferay.Language.get('save-as-template'),
			formDataQuerySelector: formSaveAsTemplateDataQuerySelector,
			formSubmitURL: formSaveAsTemplateURL,
			modalFieldLabel: Liferay.Language.get('name'),
			modalFieldName: 'name',
			modalFieldPlaceholder: Liferay.Language.get('template-name'),
			namespace: portletNamespace,
		});
	};

	const clickDelegate = delegate(
		buttonContainer,
		'click',
		'.btn-secondary',
		onClick
	);

	return {
		dispose() {
			clickDelegate.dispose();
		},
	};
}
