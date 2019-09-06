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

import Form from 'dynamic-data-mapping-form-renderer/js/containers/Form/Form.es';

const spritemap = `${Liferay.ThemeDisplay.getPathThemeImages()}/lexicon/icons.svg`;

export default ({dataLayoutBuilder, settingsContext}, container) => {
	const handleFieldBlurred = ({fieldInstance, value}) => {
		const {fieldName} = fieldInstance;

		dataLayoutBuilder.dispatch('fieldBlurred', {
			editingLanguageId: 'en_US',
			propertyName: fieldName,
			propertyValue: value
		});
	};

	const handleFieldEdited = ({fieldInstance, value}) => {
		if (fieldInstance && !fieldInstance.isDisposed()) {
			const {fieldName} = fieldInstance;

			dataLayoutBuilder.dispatch('fieldEdited', {
				editingLanguageId: 'en_US',
				propertyName: fieldName,
				propertyValue: value
			});
		}
	};

	const handleFormAttached = () => {
		const firstInput = container.querySelector('input');

		if (firstInput && !container.contains(document.activeElement)) {
			firstInput.focus();

			if (firstInput.select) {
				firstInput.select();
			}
		}
	};

	return new Form(
		{
			...settingsContext,
			editable: true,
			events: {
				attached: handleFormAttached,
				fieldBlurred: handleFieldBlurred,
				fieldEdited: handleFieldEdited
			},
			spritemap
		},
		container
	);
};
