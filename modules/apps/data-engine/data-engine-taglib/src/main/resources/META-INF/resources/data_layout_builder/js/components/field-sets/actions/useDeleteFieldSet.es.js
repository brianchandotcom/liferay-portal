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

import {PagesVisitor} from 'dynamic-data-mapping-form-renderer';
import {useContext} from 'react';

import AppContext from '../../../AppContext.es';
import {
	DELETE_DATA_DEFINITION_FIELD,
	UPDATE_FIELDSETS,
} from '../../../actions.es';
import {confirmDelete, deleteItem} from '../../../utils/client.es';
import {errorToast, successToast} from '../../../utils/toast.es';

export default ({dataLayoutBuilder}) => {
	const [{dataDefinition, fieldSets}, dispatch] = useContext(AppContext);

	return (fieldSet, confirmDeletion) => {
		const endpoint = '/o/data-engine/v2.0/data-definitions/';

		const onError = () =>
			errorToast(Liferay.Language.get('the-item-could-not-be-deleted'));

		const onSuccess = () => {
			dispatch({
				payload: {
					fieldSets: fieldSets.filter(({id}) => id !== fieldSet.id),
				},
				type: UPDATE_FIELDSETS,
			});

			successToast(
				Liferay.Language.get('the-item-was-deleted-successfully')
			);

			return Promise.resolve();
		};

		const deleteField = () => {
			const dataDefinitionField = dataDefinition.dataDefinitionFields.find(
				({customProperties: {ddmStructureId}}) =>
					ddmStructureId == fieldSet.id
			);

			if (dataDefinitionField) {
				const {pages} = dataLayoutBuilder.getStore();
				const visitor = new PagesVisitor(pages);
				const fieldName = dataDefinitionField.name;
				const event = {
					activePage: 0,
					fieldName,
				};
				if (visitor.containsField(fieldName, true)) {
					dataLayoutBuilder.dispatch('fieldDeleted', event);
				}
				else {
					dispatch({
						payload: {fieldName},
						type: DELETE_DATA_DEFINITION_FIELD,
					});
				}
			}

			return Promise.resolve();
		};

		if (confirmDeletion) {
			confirmDelete(endpoint)(fieldSet)
				.then((confirmed) => {
					if (confirmed) {
						return onSuccess();
					}

					return Promise.resolve();
				})
				.then(deleteField)
				.catch(onError);
		}
		else {
			deleteItem(`${endpoint}${fieldSet.id}`)
				.then(deleteField)
				.then(onSuccess)
				.catch(onError);
		}
	};
};
