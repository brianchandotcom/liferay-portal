/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import {ClayVerticalNav} from '@clayui/nav';
import {PropTypes} from 'prop-types';
import React, {useEffect, useState} from 'react';

import {
	SIDEBAR_STATE,
	getStorageAddSXPElementSidebar,
} from '../../utils/sessionStorage';
import {SIDEBAR_TYPES} from '../../utils/types/sidebarTypes';
import QuerySXPElements from './QuerySXPElements';
import QuerySettings from './QuerySettings';

const VERTICAL_NAV_KEYS = {
	QUERY_SETTINGS: 'querySettings',
	QUERY_SXP_ELEMENTS: 'querySXPElements',
};

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
	searchableTypes = [],
	setFieldTouched,
	setFieldValue,
	openSidebar,
	setOpenSidebar,
	touched = [],
}) {
	const [activeVerticalNavKey, setActiveVerticalNavKey] = useState(
		VERTICAL_NAV_KEYS.QUERY_SXP_ELEMENTS
	);

	/**
	 * Opens the add sxp element sidebar if it was previously open.
	 */
	useEffect(() => {
		if (
			activeVerticalNavKey === VERTICAL_NAV_KEYS.QUERY_SXP_ELEMENTS &&
			!openSidebar &&
			getStorageAddSXPElementSidebar() === SIDEBAR_STATE.OPEN
		) {
			setOpenSidebar(SIDEBAR_TYPES.ADD_SXP_ELEMENT);
		}
	}, [activeVerticalNavKey, openSidebar, setOpenSidebar]);

	/**
	 * Handles sidebar visibility. If 'visible' is not provided, sidebar
	 * will toggle between open or closed.
	 * @param {string} type A `SIDEBAR_TYPES` value.
	 * @param {visible} boolean Defaults to false if sidebar is open.
	 */
	const _handleChangeSidebarVisibility = (type) => (
		visible = openSidebar !== type
	) => {
		if (visible) {
			setOpenSidebar(type);
		}
		else if (openSidebar === type) {
			setOpenSidebar('');
		}
	};

	/**
	 * Handles navigating to a different vertical nav tab. Certain sidebars
	 * will close depending on which tab it navigates to.
	 * @param {string} verticalNavKey A `VERTICAL_NAV_KEYS` value.
	 */
	const _handleClickVerticalNav = (verticalNavKey) => () => {
		setActiveVerticalNavKey(verticalNavKey);

		if (
			(verticalNavKey === VERTICAL_NAV_KEYS.QUERY_SXP_ELEMENTS &&
				openSidebar === SIDEBAR_TYPES.CLAUSE_CONTRIBUTORS) ||
			(verticalNavKey === VERTICAL_NAV_KEYS.QUERY_SXP_ELEMENTS &&
				openSidebar === SIDEBAR_TYPES.INDEXER_CLAUSES) ||
			(verticalNavKey === VERTICAL_NAV_KEYS.QUERY_SETTINGS &&
				openSidebar === SIDEBAR_TYPES.ADD_SXP_ELEMENT)
		) {
			setOpenSidebar('');
		}
	};

	return (
		<ClayLayout.ContainerFluid
			className="layout-section-main query-builder-tab"
			size="xl"
		>
			<div className="layout-section-main-shift">
				<ClayLayout.Row>
					<ClayLayout.Col md={3} sm={12}>
						<ClayVerticalNav
							items={[
								{
									active:
										activeVerticalNavKey ===
										VERTICAL_NAV_KEYS.QUERY_SXP_ELEMENTS,
									label: Liferay.Language.get(
										'query-elements'
									),
									onClick: _handleClickVerticalNav(
										VERTICAL_NAV_KEYS.QUERY_SXP_ELEMENTS
									),
								},
								{
									active:
										activeVerticalNavKey ===
										VERTICAL_NAV_KEYS.QUERY_SETTINGS,
									label: Liferay.Language.get(
										'query-settings'
									),
									onClick: _handleClickVerticalNav(
										VERTICAL_NAV_KEYS.QUERY_SETTINGS
									),
								},
							]}
						/>
					</ClayLayout.Col>

					<ClayLayout.Col md={9} sm={12}>
						<div className="vertical-nav-content-wrapper">
							{activeVerticalNavKey ===
								VERTICAL_NAV_KEYS.QUERY_SXP_ELEMENTS && (
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
							)}

							{activeVerticalNavKey ===
								VERTICAL_NAV_KEYS.QUERY_SETTINGS && (
								<QuerySettings
									applyIndexerClauses={applyIndexerClauses}
									clauseContributorsList={
										clauseContributorsList
									}
									frameworkConfig={frameworkConfig}
									onApplyIndexerClausesChange={
										onApplyIndexerClausesChange
									}
									onChangeClauseContributorsVisibility={_handleChangeSidebarVisibility(
										SIDEBAR_TYPES.CLAUSE_CONTRIBUTORS
									)}
									onChangeIndexerClausesVisibility={_handleChangeSidebarVisibility(
										SIDEBAR_TYPES.INDEXER_CLAUSES
									)}
									onFetchSearchableTypes={
										onFetchSearchableTypes
									}
									onFrameworkConfigChange={
										onFrameworkConfigChange
									}
									searchableTypes={searchableTypes}
								/>
							)}
						</div>
					</ClayLayout.Col>
				</ClayLayout.Row>
			</div>
		</ClayLayout.ContainerFluid>
	);
}

QueryBuilderTab.propTypes = {
	applyIndexerClauses: PropTypes.bool,
	clauseContributorsList: PropTypes.arrayOf(PropTypes.string),
	elementInstances: PropTypes.arrayOf(PropTypes.object),
	entityJSON: PropTypes.object,
	errors: PropTypes.arrayOf(PropTypes.object),
	frameworkConfig: PropTypes.object,
	indexFields: PropTypes.arrayOf(PropTypes.object),
	isIndexCompany: PropTypes.bool,
	isSubmitting: PropTypes.bool,
	onApplyIndexerClausesChange: PropTypes.func,
	onBlur: PropTypes.func,
	onChange: PropTypes.func,
	onDeleteSXPElement: PropTypes.func,
	onFetchSearchableTypes: PropTypes.func,
	onFrameworkConfigChange: PropTypes.func,
	openSidebar: PropTypes.string,
	searchableTypes: PropTypes.arrayOf(PropTypes.object),
	setFieldTouched: PropTypes.func,
	setFieldValue: PropTypes.func,
	setOpenSidebar: PropTypes.func,
	touched: PropTypes.arrayOf(PropTypes.object),
};

export default React.memo(QueryBuilderTab);
