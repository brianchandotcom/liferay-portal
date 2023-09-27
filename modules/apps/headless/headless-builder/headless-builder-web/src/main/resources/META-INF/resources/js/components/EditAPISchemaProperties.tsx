/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayManagementToolbar, {
	ClayResultsBar,
} from '@clayui/management-toolbar';
import {sub} from 'frontend-js-web';
import React, {Dispatch, SetStateAction, useEffect, useState} from 'react';

import PropertiesTreeView from './PropertiesTreeView';
import {AddObjectFieldsDataToProperties} from './utils/dataUtils';
import {getAllItems} from './utils/fetchUtil';

import '../../css/main.scss';

interface EditAPISchemaPropertiesProps {
	fetchedSchemaData: FetchedSchemaData;
	schemaId: number;
	schemaUIData: APISchemaUIData;
	setFetchedSchemaData: Dispatch<SetStateAction<FetchedSchemaData>>;
	setSchemaUIData: Dispatch<SetStateAction<APISchemaUIData>>;
}

interface SearchState {
	filteredSchemaProperties: TreeViewItemData[];
	searchKeyword: string;
}

export default function EditAPISchemaProperties({
	fetchedSchemaData,
	schemaId,
	schemaUIData,
	setFetchedSchemaData,
	setSchemaUIData,
}: EditAPISchemaPropertiesProps) {
	const [searchState, setSearchState] = useState<SearchState>({
		filteredSchemaProperties: [],
		searchKeyword: '',
	});

	const clearSearch = () =>
		setSearchState({
			filteredSchemaProperties: [],
			searchKeyword: '',
		});

	const fetchAPISchemaProperties = () => {
		getAllItems<APISchemaPropertyItem>({
			url: `/o/headless-builder/schemas/${schemaId}/apiSchemaToAPIProperties`,
		}).then((response) => {
			setFetchedSchemaData((previous) => ({
				...previous,
				schemaProperties: response.length ? response : [],
			}));

			if (response.length) {
				getAllItems<ObjectDefinition>({
					url: '/o/object-admin/v1.0/object-definitions',
				}).then((objectDefinitionsResponse) => {
					if (response.length && fetchedSchemaData.apiSchema) {
						setSchemaUIData((previous) => ({
							...previous,
							schemaProperties: AddObjectFieldsDataToProperties({
								apiSchema: fetchedSchemaData.apiSchema!,
								objectDefinitions: objectDefinitionsResponse,
								schemaProperties: response,
							}),
						}));
					}
				});
			}
		});
	};

	const getFilteredSchemaProperties = (
		searchKeyword: string
	): TreeViewItemData[] | undefined => {
		return schemaUIData.schemaProperties?.filter((property) =>
			property.name.toLowerCase().includes(searchKeyword.toLowerCase())
		);
	};

	const handleSearch = (searchKeyword: string) => {
		if (searchKeyword && schemaUIData.schemaProperties) {
			setSearchState({
				filteredSchemaProperties: getFilteredSchemaProperties(
					searchKeyword
				)!,
				searchKeyword,
			});
		}
		else {
			clearSearch();
		}
	};

	useEffect(() => {
		fetchAPISchemaProperties();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<>
			<div className="search-container">
				<ClayManagementToolbar>
					<ClayManagementToolbar.Search>
						<ClayInput.Group>
							<ClayInput.GroupItem>
								<ClayInput
									aria-label={Liferay.Language.get('search')}
									className="form-control input-group-inset input-group-inset-after"
									onChange={({target: {value}}) =>
										handleSearch(value)
									}
									placeholder={Liferay.Language.get('search')}
									type="text"
									value={searchState.searchKeyword}
								/>

								<ClayInput.GroupInsetItem
									after
									className="pr-3"
									tag="span"
								>
									<ClayIcon symbol="search" />
								</ClayInput.GroupInsetItem>
							</ClayInput.GroupItem>
						</ClayInput.Group>
					</ClayManagementToolbar.Search>
				</ClayManagementToolbar>

				{searchState.searchKeyword && (
					<ClayResultsBar>
						<ClayResultsBar.Item expand>
							<span className="component-text text-truncate-inline">
								<span className="text-truncate">
									{sub(
										Liferay.Language.get('x-result-for-x'),
										searchState.filteredSchemaProperties
											.length,
										searchState.searchKeyword
									)}
								</span>
							</span>
						</ClayResultsBar.Item>

						<ClayResultsBar.Item>
							<ClayButton
								className="component-link tbar-link"
								displayType="unstyled"
								onClick={clearSearch}
							>
								{Liferay.Language.get('clear-all')}
							</ClayButton>
						</ClayResultsBar.Item>
					</ClayResultsBar>
				)}
			</div>

			<PropertiesTreeView
				schemaUIData={schemaUIData}
				searchState={searchState}
				setSchemaUIData={setSchemaUIData}
			/>
		</>
	);
}
