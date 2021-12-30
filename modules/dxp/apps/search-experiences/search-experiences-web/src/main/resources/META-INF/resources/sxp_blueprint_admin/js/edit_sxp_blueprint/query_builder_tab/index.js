/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayLayout from '@clayui/layout';
import {ClayVerticalNav} from '@clayui/nav';
import {PropTypes} from 'prop-types';
import React, {useContext, useEffect, useState} from 'react';

import ThemeContext from '../../shared/ThemeContext';
import {SIDEBARS} from '../../utils/constants';
import {fetchData} from '../../utils/fetch';
import QuerySXPElements from './QuerySXPElements';
import QuerySettings from './QuerySettings';

const VERTICAL_NAV_KEYS = {
	QUERY_SETTINGS: 'querySettings',
	QUERY_SXP_ELEMENTS: 'querySXPElements',
};

function QueryBuilderTab({
	applyIndexerClauses,
	elementInstances,
	entityJSON,
	errors = [],
	frameworkConfig = {},
	isSubmitting,
	onApplyIndexerClausesChange,
	onBlur,
	onChange,
	onDeleteSXPElement,
	onFrameworkConfigChange,
	setFieldTouched,
	setFieldValue,
	openSidebar,
	setOpenSidebar,
	touched = [],
}) {
	const {locale} = useContext(ThemeContext);

	const [activeVerticalNavKey, setActiveVerticalNavKey] = useState(
		VERTICAL_NAV_KEYS.QUERY_SXP_ELEMENTS
	);
	const [searchableTypes, setSearchableTypes] = useState(null);
	const [indexFields, setIndexFields] = useState(null);
	const [keywordQueryContributors, setKeywordQueryContributors] = useState(
		null
	);
	const [
		modelPrefilterContributors,
		setModelPrefilterContributors,
	] = useState(null);
	const [
		queryPrefilterContributors,
		setQueryPrefilterContributors,
	] = useState(null);

	useEffect(() => {
		fetchData(
			`/o/search-experiences-rest/v1.0/searchable-asset-names/${locale}`,
			{method: 'GET'},
			(responseContent) => setSearchableTypes(responseContent.items),
			() => setSearchableTypes([])
		);

		fetchData(
			`/o/search-experiences-rest/v1.0/field-mapping-infos`,
			{method: 'GET'},
			(responseContent) => setIndexFields(responseContent.items),
			() => setIndexFields([])
		);

		[
			{
				setProperty: setKeywordQueryContributors,
				url:
					'/o/search-experiences-rest/v1.0/keyword-query-contributors',
			},
			{
				setProperty: setModelPrefilterContributors,
				url:
					'/o/search-experiences-rest/v1.0/model-prefilter-contributors',
			},
			{
				setProperty: setQueryPrefilterContributors,
				url:
					'/o/search-experiences-rest/v1.0/query-prefilter-contributors',
			},
		].forEach(({setProperty, url}) =>
			fetchData(
				url,
				{method: 'GET'},
				(responseContent) =>
					setProperty(
						responseContent.items
							.map(({className}) => className)
							.filter((item) => item)
							.sort()
					),
				() => setProperty([])
			)
		);
	}, []); //eslint-disable-line

	if (
		!searchableTypes ||
		!indexFields ||
		!keywordQueryContributors ||
		!modelPrefilterContributors ||
		!queryPrefilterContributors
	) {
		return null;
	}

	/**
	 * Handles sidebar visibility. If 'visible' is not provided, sidebar
	 * will toggle between open or closed.
	 * @param {string} type A `SIDEBARS` value.
	 * @param {visible} boolean Defaults to false if sidebar is open.
	 */
	const _handleChangeSidebarVisibility = (type) => (
		visible = openSidebar !== type
	) => {
		setOpenSidebar(visible ? type : '');
	};

	/**
	 * Handles navigating to a different vertical nav tab. Certain sidebars
	 * will close depending on which tab it navigates to.
	 * @param {string} key A `VERTICAL_NAV_KEYS` value.
	 */
	const _handleClickVerticalNav = (verticalNavKey) => () => {
		setActiveVerticalNavKey(verticalNavKey);

		if (
			(verticalNavKey === VERTICAL_NAV_KEYS.QUERY_SXP_ELEMENTS &&
				openSidebar === SIDEBARS.CLAUSE_CONTRIBUTORS) ||
			(verticalNavKey === VERTICAL_NAV_KEYS.QUERY_SETTINGS &&
				openSidebar === SIDEBARS.ADD_SXP_ELEMENT)
		) {
			setOpenSidebar('');
		}
	};

	return (
		<ClayLayout.ContainerFluid
			className="builder query-builder-tab"
			size="xl"
		>
			<div className="builder-content-shift">
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
									isSubmitting={isSubmitting}
									onBlur={onBlur}
									onChange={onChange}
									onChangeAddSXPElementVisibility={_handleChangeSidebarVisibility(
										SIDEBARS.ADD_SXP_ELEMENT
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
									clauseContributorsList={[
										...keywordQueryContributors,
										...modelPrefilterContributors,
										...queryPrefilterContributors,
									]}
									frameworkConfig={frameworkConfig}
									onApplyIndexerClausesChange={
										onApplyIndexerClausesChange
									}
									onChangeClauseContributorsVisibility={_handleChangeSidebarVisibility(
										SIDEBARS.CLAUSE_CONTRIBUTORS
									)}
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
	elementInstances: PropTypes.arrayOf(PropTypes.object),
	entityJSON: PropTypes.object,
	errors: PropTypes.arrayOf(PropTypes.object),
	frameworkConfig: PropTypes.object,
	isSubmitting: PropTypes.bool,
	onApplyIndexerClausesChange: PropTypes.func,
	onBlur: PropTypes.func,
	onChange: PropTypes.func,
	onDeleteSXPElement: PropTypes.func,
	onFrameworkConfigChange: PropTypes.func,
	openSidebar: PropTypes.string,
	setFieldTouched: PropTypes.func,
	setFieldValue: PropTypes.func,
	setOpenSidebar: PropTypes.func,
	touched: PropTypes.arrayOf(PropTypes.object),
};

export default React.memo(QueryBuilderTab);
