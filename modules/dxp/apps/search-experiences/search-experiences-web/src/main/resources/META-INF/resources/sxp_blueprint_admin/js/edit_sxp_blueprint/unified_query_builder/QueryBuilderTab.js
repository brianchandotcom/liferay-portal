/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import {PropTypes} from 'prop-types';
import React, {useEffect} from 'react';

import {
	SIDEBAR_STATE,
	getStorageAddSXPElementSidebar,
} from '../../utils/sessionStorage';
import {SIDEBAR_TYPES} from '../../utils/types/sidebarTypes';
import QuerySettings from '../query_builder_tab/QuerySettings';
import QuerySXPElements from './QuerySXPElements';
import ScopeSelector from './ScopeSelector';

function QueryBuilderTab({
	applyIndexerClauses,
	clauseContributorsList = [],
	elementInstances,
	entityJSON,
	errors = [],
	frameworkConfig = {},
	isSubmitting,
	indexFields,
	isIndexCompany,
	onApplyIndexerClausesChange,
	onBlur,
	onChange,
	onDeleteSXPElement,
	onFetchSearchableTypes,
	onFrameworkConfigChange,
	onAssetSubtypesMapChange,
	scope,
	searchableTypes = [],
	setFieldTouched,
	setFieldValue,
	openSidebar,
	setOpenSidebar,
	setScope,
	assetSubtypesMap,
	touched = [],
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
		(type) =>
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

					<div className="sheet">
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
					</div>
				</div>
			</div>
		</ClayLayout.ContainerFluid>
	);
}

QueryBuilderTab.propTypes = {
	applyIndexerClauses: PropTypes.bool,
	assetSubtypesMap: PropTypes.object,
	clauseContributorsList: PropTypes.arrayOf(PropTypes.string),
	elementInstances: PropTypes.arrayOf(PropTypes.object),
	entityJSON: PropTypes.object,
	errors: PropTypes.arrayOf(PropTypes.object),
	frameworkConfig: PropTypes.object,
	indexFields: PropTypes.arrayOf(PropTypes.object),
	isIndexCompany: PropTypes.bool,
	isSubmitting: PropTypes.bool,
	onApplyIndexerClausesChange: PropTypes.func,
	onAssetSubtypesMapChange: PropTypes.func,
	onBlur: PropTypes.func,
	onChange: PropTypes.func,
	onDeleteSXPElement: PropTypes.func,
	onFetchSearchableTypes: PropTypes.func,
	onFrameworkConfigChange: PropTypes.func,
	openSidebar: PropTypes.string,
	scope: PropTypes.object,
	searchableTypes: PropTypes.arrayOf(PropTypes.object),
	setFieldTouched: PropTypes.func,
	setFieldValue: PropTypes.func,
	setOpenSidebar: PropTypes.func,
	setScope: PropTypes.func,
	touched: PropTypes.arrayOf(PropTypes.object),
};

export default React.memo(QueryBuilderTab);
