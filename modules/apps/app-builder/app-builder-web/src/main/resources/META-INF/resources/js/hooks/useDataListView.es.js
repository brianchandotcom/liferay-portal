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

import {DataDefinitionUtils} from 'data-engine-taglib';
import {useEffect, useState} from 'react';

import {getItem} from '../utils/client.es';
import {errorToast} from '../utils/toast.es';

export default function useDataListView(dataListViewId, dataDefinitionId) {
	const [state, setState] = useState({
		columns: [],
		dataDefinition: null,
		dataListView: {
			fieldNames: [],
		},
		isLoading: true,
	});

	useEffect(() => {
		Promise.all([
			getItem(`/o/data-engine/v2.0/data-definitions/${dataDefinitionId}`),
			getItem(`/o/data-engine/v2.0/data-list-views/${dataListViewId}`),
		])
			.then(([dataDefinition, dataListView]) => {
				setState((prevState) => ({
					...prevState,
					columns: dataListView.fieldNames.map((column) => {
						const {
							label: value,
						} = DataDefinitionUtils.getDataDefinitionField(
							dataDefinition,
							column
						);

						return {
							key: 'dataRecordValues/' + column,
							sortable: true,
							value,
						};
					}),
					dataDefinition: {
						...prevState.dataDefinition,
						...dataDefinition,
					},
					dataListView: {
						...prevState.dataListView,
						...dataListView,
					},
					isLoading: false,
				}));
			})
			.catch(() => {
				setState((prevState) => ({
					...prevState,
					isLoading: false,
				}));

				errorToast();
			});
	}, [dataDefinitionId, dataListViewId]);

	return state;
}
