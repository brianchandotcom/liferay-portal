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

import {EVENT_TYPES} from '../actions/eventTypes.es';
import {PagesVisitor} from '../util/visitors.es';

export default (state, action) => {
	switch (action.type) {
		case EVENT_TYPES.ALL: {
			const {defaultSiteLanguageId} = state;
			const {editingLanguageId, pages} = action.payload;

			if (
				editingLanguageId &&
				state.editingLanguageId !== editingLanguageId
			) {
				const visitor = new PagesVisitor(pages ?? state.pages);

				return {
					...action.payload,
					pages: visitor.mapFields(
						({localizedValue, localizedValueEdited}) => {
							let value;

							const defaultValue =
								localizedValue[defaultSiteLanguageId];

							if (localizedValue) {
								if (localizedValue[editingLanguageId] != null) {
									if (
										!localizedValue[editingLanguageId]
											.length &&
										!localizedValueEdited?.[
											editingLanguageId
										]
									) {
										value = defaultValue;
									}
									else {
										value =
											localizedValue[editingLanguageId];
									}
								}
								else if (defaultValue) {
									value = defaultValue;
								}
							}

							return {
								value,
							};
						},
						true,
						true,
						true
					),
				};
			}

			return action.payload;
		}
		case EVENT_TYPES.UPDATE_DATA_RECORD_VALUES:
			return {
				dataRecordValues: action.payload,
			};
		default:
			return state;
	}
};
