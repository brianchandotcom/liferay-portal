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

import {useContext} from 'react';

import AppContext from '../../../AppContext.es';
import {UPDATE_FIELDSETS} from '../../../actions.es';
import {addItem} from '../../../utils/client.es';
import {containsField} from '../../../utils/dataLayoutVisitor.es';
import {errorToast, successToast} from '../../../utils/toast.es';

export default ({childrenContext}) => {
	const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();
	const [{fieldSets}, dispatch] = useContext(AppContext);
	const {state: childrenState} = childrenContext;

	return (fieldSetName) => {
		const {
			dataDefinition: {dataDefinitionFields: fields},
			dataLayout: {dataLayoutPages},
		} = childrenState;

		const dataDefinitionFields = fields.filter(
			({fieldType, name}) =>
				fieldType !== 'fieldset' && containsField(dataLayoutPages, name)
		);

		const name = {
			[defaultLanguageId]: fieldSetName,
		};

		const fieldSetDefinition = {
			availableLanguageIds: [defaultLanguageId],
			dataDefinitionFields,
			defaultDataLayout: {
				dataLayoutPages,
				name,
			},
			name,
		};

		return addItem(
			`/o/data-engine/v2.0/data-definitions/by-content-type/app-builder-fieldset`,
			fieldSetDefinition
		)
			.then((dataDefinitionFieldSet) => {
				dispatch({
					payload: {
						fieldSets: [...fieldSets, dataDefinitionFieldSet],
					},
					type: UPDATE_FIELDSETS,
				});

				successToast(Liferay.Language.get('fieldset-saved'));

				return Promise.resolve();
			})
			.catch(({message}) => errorToast(message));
	};
};
