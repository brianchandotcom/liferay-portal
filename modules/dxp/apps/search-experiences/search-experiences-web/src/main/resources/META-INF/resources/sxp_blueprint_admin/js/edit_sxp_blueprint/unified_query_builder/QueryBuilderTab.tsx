/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import React, {useEffect} from 'react';

import {
	SIDEBAR_STATE,
	getStorageAddSXPElementSidebar,
} from '../../utils/sessionStorage';
import {
	IElementInstances,
	IFrameworkConfiguration,
	IIndexFields,
	IScope,
	ISearchableTypes,
	ISelectedSubtype,
} from '../../utils/types';
import {SIDEBAR_TYPES} from '../../utils/types/sidebarTypes';

// @ts-ignore

import QuerySettings from '../query_builder_tab/QuerySettings';
import QuerySXPElements from './QuerySXPElements';
import ScopeSelector from './ScopeSelector';
import CustomPanel from './shared/CustomPanel';

function QueryBuilderTab({
	applyIndexerClauses,
	assetSubtypesMap,
	clauseContributorsList = [],
	elementInstances,
	entityJSON,
	errors = [],
	frameworkConfig = {},
	isSubmitting,
	indexFields,
	isIndexCompany,
	onApplyIndexerClausesChange,
	onAssetSubtypesMapChange,
	onBlur,
	onChange,
	onDeleteSXPElement,
	onFetchSearchableTypes,
	onFrameworkConfigChange,
	openSidebar,
	scope,
	searchableTypes = [],
	setFieldTouched,
	setFieldValue,
	setOpenSidebar,
	setScope,
	touched = [],
}: {
	applyIndexerClauses: boolean;
	assetSubtypesMap: {[key: string]: string};
	clauseContributorsList: string[];
	elementInstances: IElementInstances[];
	entityJSON: {[key: string]: any};
	errors: (
		| {[uiConfigurationValues: string]: {[field: string]: string}}
		| undefined
	)[];
	frameworkConfig: IFrameworkConfiguration;
	indexFields: IIndexFields[];
	isIndexCompany: boolean;
	isSubmitting: boolean;
	onApplyIndexerClausesChange: (value: boolean) => void;
	onAssetSubtypesMapChange: (subtypes: ISelectedSubtype[]) => void;
	onBlur: (event: React.FocusEvent<any>) => void;
	onChange: (event: React.ChangeEvent<any>) => void;
	onDeleteSXPElement: (id: number) => void;
	onFetchSearchableTypes: () => void;
	onFrameworkConfigChange: (value: IFrameworkConfiguration) => void;
	openSidebar: (typeof SIDEBAR_TYPES)[keyof typeof SIDEBAR_TYPES] | '';
	scope: IScope[];
	searchableTypes: ISearchableTypes[];
	setFieldTouched: (field: string, touched?: boolean) => void;
	setFieldValue: (field: string, value: any) => void;
	setOpenSidebar: (
		sidebarType: (typeof SIDEBAR_TYPES)[keyof typeof SIDEBAR_TYPES] | ''
	) => void;
	setScope: (scope: IScope[]) => void;
	touched: (
		| {[uiConfigurationValues: string]: {[field: string]: boolean}}
		| undefined
	)[];
}) {

	/**
	 * Opens the add sxp element sidebar if it was previously open.
	 */
	useEffect(() => {
		if (
			!openSidebar &&
			getStorageAddSXPElementSidebar() === SIDEBAR_STATE.OPEN
		) {
			setOpenSidebar(SIDEBAR_TYPES.ADD_SXP_ELEMENT);
		}
	}, [openSidebar, setOpenSidebar]);

	/**
	 * Handles sidebar visibility. If 'visible' is not provided, sidebar
	 * will toggle between open or closed.
	 * @param {string} type A `SIDEBAR_TYPES` value.
	 * @param {visible} boolean Defaults to false if sidebar is open.
	 */
	const _handleChangeSidebarVisibility =
		(type: (typeof SIDEBAR_TYPES)[keyof typeof SIDEBAR_TYPES] | '') =>
		(visible = openSidebar !== type) => {
			if (visible) {
				setOpenSidebar(type);
			}
			else if (openSidebar === type) {
				setOpenSidebar('');
			}
		};

	return (
		<ClayLayout.ContainerFluid
			className="layout-section-main query-builder-tab unified-query-builder-tab"
			size="xxl"
		>
			<div className="layout-section-main-shift">
				<div className="vertical-nav-content-wrapper">
					<ScopeSelector scope={scope} setScope={setScope} />

					<QuerySXPElements
						elementInstances={elementInstances}
						entityJSON={entityJSON}
						errors={errors}
						indexFields={indexFields}
						isIndexCompany={isIndexCompany}
						isSubmitting={isSubmitting}
						onBlur={onBlur}
						onChange={onChange}
						onChangeAddSXPElementVisibility={_handleChangeSidebarVisibility(
							SIDEBAR_TYPES.ADD_SXP_ELEMENT
						)}
						onDeleteSXPElement={onDeleteSXPElement}
						searchableTypes={searchableTypes}
						setFieldTouched={setFieldTouched}
						setFieldValue={setFieldValue}
						touched={touched}
					/>

					<CustomPanel
						initialCollapse
						title={Liferay.Language.get('query-settings')}
					>
						<QuerySettings
							applyIndexerClauses={applyIndexerClauses}
							assetSubtypesMap={assetSubtypesMap}
							clauseContributorsList={clauseContributorsList}
							frameworkConfig={frameworkConfig}
							onApplyIndexerClausesChange={
								onApplyIndexerClausesChange
							}
							onAssetSubtypesMapChange={onAssetSubtypesMapChange}
							onChangeClauseContributorsVisibility={_handleChangeSidebarVisibility(
								SIDEBAR_TYPES.CLAUSE_CONTRIBUTORS
							)}
							onChangeIndexerClausesHelpVisibility={_handleChangeSidebarVisibility(
								SIDEBAR_TYPES.INDEXER_CLAUSES_HELP
							)}
							onChangeQueryContributorsHelpVisibility={_handleChangeSidebarVisibility(
								SIDEBAR_TYPES.QUERY_CONTRIBUTORS_HELP
							)}
							onFetchSearchableTypes={onFetchSearchableTypes}
							onFrameworkConfigChange={onFrameworkConfigChange}
							searchableTypes={searchableTypes}
						/>
					</CustomPanel>
				</div>
			</div>
		</ClayLayout.ContainerFluid>
	);
}

export default React.memo(QueryBuilderTab);
