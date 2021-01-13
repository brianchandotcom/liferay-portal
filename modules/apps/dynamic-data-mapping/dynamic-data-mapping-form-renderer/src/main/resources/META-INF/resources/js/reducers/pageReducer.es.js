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

import {sub} from 'dynamic-data-mapping-form-field-type/util/strings.es';

import {EVENT_TYPES} from '../actions/eventTypes.es';
import {PagesVisitor} from '../util/visitors.es';

export default (state, action) => {
	switch (action.type) {
		case EVENT_TYPES.UPDATE_PAGES:
			return {
				pages: action.payload,
			};
		case EVENT_TYPES.CHANGE_ACTIVE_PAGE:
			return {
				activePage: action.payload,
			};
		case EVENT_TYPES.PAGE_VALIDATION_FAILED: {
			const {newPages, pageIndex} = action.payload;
			const visitor = new PagesVisitor(newPages ?? state.pages);

			let firstInvalidFieldLabel = null;
			let firstInvalidFieldInput = null;

			const pages = visitor.mapFields(
				(
					field,
					fieldIndex,
					columnIndex,
					rowIndex,
					currentPageIndex
				) => {
					const displayErrors = currentPageIndex === pageIndex;

					if (
						displayErrors &&
						field.errorMessage !== undefined &&
						field.errorMessage !== '' &&
						!field.valid &&
						firstInvalidFieldLabel == null
					) {
						firstInvalidFieldLabel = field.label;
						firstInvalidFieldInput = document.querySelector(
							`[name='${field.name}']`
						);
					}

					return {
						...field,
						displayErrors,
					};
				},
				true,
				true
			);

			if (firstInvalidFieldInput) {
				firstInvalidFieldInput.focus();
			}

			return {
				forceAriaUpdate: Date.now(),
				invalidFormMessage: sub(
					Liferay.Language.get('this-form-is-invalid-check-field-x'),
					[firstInvalidFieldLabel]
				),
				pages,
			};
		}
		default:
			return state;
	}
};
