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

import {PagesVisitor} from '../../util/visitors.es';
import {EVENT_TYPES} from '../actions/eventTypes.es';

export default (state, action, config) => {
	switch (action.type) {
		case EVENT_TYPES.UPDATE_EDITING_LANGUAGE: {
			const {defaultLanguageId} = config;
			const {editingLanguageId, pages} = action.payload;

			const visitor = new PagesVisitor(pages ?? state.pages);

			return {
				editingLanguageId,
				pages: visitor.mapFields(
					({
						localizable,
						localizedValue,
						localizedValueEdited,
						value,
					}) => {
						if (!localizable) {
							return {value};
						}

						let _value;

						const defaultValue = localizedValue[defaultLanguageId];

						if (localizedValue) {
							if (localizedValue[editingLanguageId] != null) {
								if (
									Array.isArray(
										localizedValue[editingLanguageId]
									) &&
									!localizedValue[editingLanguageId]
										?.length &&
									!localizedValueEdited?.[editingLanguageId]
								) {
									_value = defaultValue;
								}
								else {
									_value = localizedValue[editingLanguageId];
								}
							}
							else if (defaultValue) {
								_value = defaultValue;
							}
						}

						try {
							_value = JSON.parse(_value);
						}
						catch (e) {}

						return {
							value: _value,
						};
					},
					true,
					true,
					true
				),
			};
		}
		case EVENT_TYPES.PAGE.UPDATE:
			return {
				pages: action.payload,
			};
		default:
			return state;
	}
};
