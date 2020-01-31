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

import {PagesVisitor} from 'dynamic-data-mapping-form-builder';

import FormRenderer from './FormRenderer.es';

class FormRendererWithProvider extends FormRenderer {
	_handleFieldEdited(properties) {
		const pageVisitor = new PagesVisitor(this.pages);

		const pages = pageVisitor.mapFields(field => {
			if (field.fieldName === properties.fieldInstance.fieldName) {
				return {
					...field,
					value: properties.value
				};
			}
		});

		this.pages = pages;
	}

	created() {
		this.on('fieldEdited', this._handleFieldEdited);
	}
}

export default FormRendererWithProvider;
