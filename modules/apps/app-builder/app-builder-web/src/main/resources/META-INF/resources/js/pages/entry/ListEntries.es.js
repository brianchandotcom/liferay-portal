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

import React, {useContext, useEffect, useState} from 'react';
import {Link, withRouter} from 'react-router-dom';

import {AppContext} from '../../AppContext.es';
import Button from '../../components/button/Button.es';
import ListView from '../../components/list-view/ListView.es';
import {Loading} from '../../components/loading/Loading.es';
import {toQuery, toQueryString} from '../../hooks/useQuery.es';
import {confirmDelete, getItem} from '../../utils/client.es';
import {FieldValuePreview} from './FieldPreview.es';

const ListEntries = withRouter(({history, location}) => {
	const [dataDefinition, setDataDefinition] = useState();
	const [state, setState] = useState({
		dataDefinitionId: null,
		dataLayoutId: null,
		dataListView: {
			fieldNames: []
		},
		isLoading: true
	});

	const {appId, basePortletURL} = useContext(AppContext);

	useEffect(() => {
		getItem(`/o/app-builder/v1.0/apps/${appId}`).then(
			({dataDefinitionId, dataLayoutId, dataListViewId}) => {
				return Promise.all([
					getItem(
						`/o/data-engine/v1.0/data-definitions/${dataDefinitionId}`
					).then(dataDefinition => setDataDefinition(dataDefinition)),
					getItem(
						`/o/data-engine/v1.0/data-list-views/${dataListViewId}`
					).then(dataListView => {
						setState(prevState => ({
							...prevState,
							dataDefinitionId,
							dataLayoutId,
							dataListView: {
								...prevState.dataListView,
								...dataListView
							},
							isLoading: false
						}));
					})
				]);
			}
		);
	}, [appId]);

	const {dataDefinitionId, dataLayoutId, dataListView, isLoading} = state;
	const {fieldNames: columns} = dataListView;

	const defaultDataRecordValues = {};

	columns.forEach(column => {
		defaultDataRecordValues[column] = ' - ';
	});

	const getEditURL = (dataRecordId = 0) =>
		Liferay.Util.PortletURL.createRenderURL(basePortletURL, {
			dataDefinitionId,
			dataLayoutId,
			dataRecordId,
			mvcPath: '/edit_entry.jsp'
		});

	const handleEditItem = dataRecordId => {
		Liferay.Util.navigate(getEditURL(dataRecordId));
	};

	return (
		<Loading isLoading={isLoading}>
			<ListView
				actions={[
					{
						action: ({viewURL}) =>
							Promise.resolve(history.push(viewURL)),
						name: Liferay.Language.get('view')
					},
					{
						action: ({id}) => Promise.resolve(handleEditItem(id)),
						name: Liferay.Language.get('edit')
					},
					{
						action: confirmDelete(
							'/o/data-engine/v1.0/data-records/'
						),
						name: Liferay.Language.get('delete')
					}
				]}
				addButton={() => (
					<Button
						className="nav-btn nav-btn-monospaced navbar-breakpoint-down-d-none"
						onClick={() => handleEditItem(0)}
						symbol="plus"
						tooltip={Liferay.Language.get('new-entry')}
					/>
				)}
				columns={columns.map(column => ({
					key: column,
					value: column
				}))}
				emptyState={{
					button: () => (
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

					columns.forEach(fieldName => {
						displayedDataRecordValues[
							fieldName
						] = dataDefinition && (
							<Link to={viewURL}>
								<FieldValuePreview
									dataDefinition={dataDefinition}
									dataRecordValues={dataRecordValues}
									displayType="list"
									fieldName={fieldName}
								/>
							</Link>
						);
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
