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

import openToast from 'frontend-js-web/liferay/toast/commands/OpenToast.es';
import React, {useContext, useEffect, useState} from 'react';
import {Link, withRouter} from 'react-router-dom';

import {AppContext} from '../../AppContext.es';
import Button from '../../components/button/Button.es';
import ListView from '../../components/list-view/ListView.es';
import {Loading} from '../../components/loading/Loading.es';
import {toQuery, toQueryString} from '../../hooks/useQuery.es';
import {confirmDelete, getItem} from '../../utils/client.es';
import {getFieldLabel} from '../../utils/dataDefinition.es';
import {FieldValuePreview} from './FieldPreview.es';

const ListEntries = withRouter(({history, location}) => {
	const [state, setState] = useState({
		dataDefinition: null,
		dataListView: {
			fieldNames: []
		},
		isLoading: true
	});

	const {
		basePortletURL,
		dataDefinitionId,
		dataListViewId,
		showFormView
	} = useContext(AppContext);

	useEffect(() => {
		Promise.all([
			getItem(`/o/data-engine/v1.0/data-definitions/${dataDefinitionId}`),
			getItem(`/o/data-engine/v1.0/data-list-views/${dataListViewId}`)
		]).then(([dataDefinition, dataListView]) => {
			setState(prevState => ({
				...prevState,
				dataDefinition: {
					...prevState.dataDefinition,
					...dataDefinition
				},
				dataListView: {
					...prevState.dataListView,
					...dataListView
				},
				isLoading: false
			}));
		});
	}, [dataDefinitionId, dataListViewId]);

	const {dataDefinition, dataListView, isLoading} = state;
	const {fieldNames: columns} = dataListView;

	const getEditURL = (dataRecordId = 0) =>
		Liferay.Util.PortletURL.createRenderURL(basePortletURL, {
			dataRecordId,
			mvcPath: '/edit_entry.jsp'
		});

	const handleEditItem = dataRecordId => {
		Liferay.Util.navigate(getEditURL(dataRecordId));
	};

	let actions = [];

	if (showFormView) {
		actions = [
			{
				action: ({viewURL}) => Promise.resolve(history.push(viewURL)),
				name: Liferay.Language.get('view')
			},
			{
				action: ({id}) => Promise.resolve(handleEditItem(id)),
				name: Liferay.Language.get('edit')
			},
			{
				action: item =>
					confirmDelete('/o/data-engine/v1.0/data-records/')(
						item
					).then(confirmed => {
						if (confirmed) {
							openToast({
								message: Liferay.Language.get(
									'an-entry-was-deleted'
								),
								title: Liferay.Language.get('success'),
								type: 'success'
							});
						}

						return Promise.resolve(confirmed);
					}),
				name: Liferay.Language.get('delete')
			}
		];
	}

	return (
		<Loading isLoading={isLoading}>
			<ListView
				actions={actions}
				addButton={() =>
					showFormView && (
						<Button
							className="nav-btn nav-btn-monospaced navbar-breakpoint-down-d-none"
							onClick={() => handleEditItem(0)}
							symbol="plus"
							tooltip={Liferay.Language.get('new-entry')}
						/>
					)
				}
				columns={columns.map(column => ({
					key: column,
					value: getFieldLabel(dataDefinition, column)
				}))}
				emptyState={{
					button: () =>
						showFormView && (
							<Button
								displayType="secondary"
								onClick={() => handleEditItem(0)}
							>
								{Liferay.Language.get('new-entry')}
							</Button>
						),
					title: Liferay.Language.get('there-are-no-entries-yet')
				}}
				endpoint={`/o/data-engine/v1.0/data-definitions/${dataDefinitionId}/data-records`}
			>
				{(item, index) => {
					const {dataRecordValues = {}, id} = item;
					const query = toQuery(location.search, {
						keywords: '',
						page: 1,
						pageSize: 20,
						sort: ''
					});

					const entryIndex =
						query.pageSize * (query.page - 1) + index + 1;

					const viewURL = `/entries/${entryIndex}?${toQueryString(
						query
					)}`;

					const displayedDataRecordValues = {};

					columns.forEach((fieldName, columnIndex) => {
						let fieldValuePreview = (
							<FieldValuePreview
								dataDefinition={dataDefinition}
								dataRecordValues={dataRecordValues}
								displayType="list"
								fieldName={fieldName}
							/>
						);

						if (columnIndex === 0) {
							fieldValuePreview = (
								<Link to={viewURL}>{fieldValuePreview}</Link>
							);
						}

						displayedDataRecordValues[
							fieldName
						] = fieldValuePreview;
					});

					return {
						...displayedDataRecordValues,
						id,
						viewURL
					};
				}}
			</ListView>
		</Loading>
	);
});

export default ListEntries;
