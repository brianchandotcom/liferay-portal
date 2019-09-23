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

import {UPDATE_FIELD_TYPES, UPDATE_PAGES} from './actions.es';
import {useEffect} from 'react';

export default (dataLayoutBuilder, dispatch) => {
	useEffect(() => {
		const provider = dataLayoutBuilder.getProvider();

		provider.props.fieldActions = [
			{
				action: indexes =>
					dataLayoutBuilder.dispatch('fieldDuplicated', {indexes}),
				label: Liferay.Language.get('duplicate')
			},
			{
				action: indexes =>
					dataLayoutBuilder.dispatch('fieldDeleted', {indexes}),
				label: Liferay.Language.get('remove'),
				separator: true
			},
			{
				action: indexes =>
					dataLayoutBuilder.dispatch('fieldDeleted', {indexes}),
				label: Liferay.Language.get('delete-from-object'),
				style: 'danger'
			}
		];
	}, [dataLayoutBuilder]);

	useEffect(() => {
		const provider = dataLayoutBuilder.getProvider();

		const eventHandler = provider.on('pagesChanged', ({newVal}) => {
			provider.once('rendered', () => {
				dispatch({payload: {pages: newVal}, type: UPDATE_PAGES});
			});
		});

		return () => eventHandler.removeListener();
	}, [dataLayoutBuilder, dispatch]);

	useEffect(() => {
		const fieldTypes = dataLayoutBuilder.getFieldTypes();

		dispatch({payload: {fieldTypes}, type: UPDATE_FIELD_TYPES});
	}, [dataLayoutBuilder, dispatch]);
};
