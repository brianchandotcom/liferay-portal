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

import ClayLayout from '@clayui/layout';
import {SearchInput} from 'data-engine-taglib';
import React, {useContext, useState} from 'react';

import {useRequest} from '../../../hooks/index.es';
import {getLocalizedValue} from '../../../utils/lang.es';
import EditAppContext, {
	UPDATE_DATA_LAYOUT_ID,
	UPDATE_DATA_LIST_VIEW_ID,
	UPDATE_WORKFLOW_PROCESS_ID,
} from './EditAppContext.es';
import ListItems from './ListItems.es';

const EditAppBody = ({
	defaultLanguageId,
	endpoint,
	itemType,
	title,
	...restProps
}) => {
	const [searchText, setSearchText] = useState('');

	const {
		dispatch,
		state: {
			app: {dataLayoutId, dataListViewId, workflowDefinitionName},
		},
	} = useContext(EditAppContext);

	const {
		response: {items = []},
		isLoading,
	} = useRequest(endpoint);

	const filteredItems = items.filter((item) =>
		new RegExp(searchText, 'ig').test(
			getLocalizedValue(defaultLanguageId, item.name)
		)
	);

	const stepItems = {
		DATA_LAYOUT: {
			id: dataLayoutId,
			items: filteredItems,
			type: UPDATE_DATA_LAYOUT_ID,
		},
		DATA_LIST_VIEW: {
			id: dataListViewId,
			items: filteredItems,
			type: UPDATE_DATA_LIST_VIEW_ID,
		},
		WORKFLOW_PROCESS: {
			id: workflowDefinitionName ?? '',
			items: [
				{
					dateCreated: null,
					dateModified: null,
					id: '',
					name: {
						[defaultLanguageId]: Liferay.Language.get(
							'no-workflow'
						),
					},
				},
				...filteredItems.map(({name, title, ...restProps}) => ({
					...restProps,
					id: name,
					name: {[defaultLanguageId]: title},
				})),
			],
			type: UPDATE_WORKFLOW_PROCESS_ID,
		},
	};

	return (
		<>
			<ClayLayout.ContentRow className="mb-4 pl-4 pr-4">
				<ClayLayout.ContentCol expand>{title}</ClayLayout.ContentCol>
			</ClayLayout.ContentRow>

			<ClayLayout.ContentRow className="mb-4 pl-4 pr-4">
				<ClayLayout.ContentCol expand>
					<SearchInput
						onChange={(searchText) => setSearchText(searchText)}
					/>
				</ClayLayout.ContentCol>
			</ClayLayout.ContentRow>

			<ClayLayout.ContentRow className="pl-4 pr-4 scrollable-container">
				<ClayLayout.ContentCol expand>
					<ListItems
						defaultLanguageId={defaultLanguageId}
						isEmpty={stepItems[itemType].items.length === 0}
						isLoading={isLoading}
						itemId={stepItems[itemType].id}
						items={stepItems[itemType].items}
						keywords={searchText}
						onChange={(item) => {
							dispatch({
								...item,
								type: stepItems[itemType].type,
							});
						}}
						{...restProps}
					/>
				</ClayLayout.ContentCol>
			</ClayLayout.ContentRow>
		</>
	);
};

export default EditAppBody;
