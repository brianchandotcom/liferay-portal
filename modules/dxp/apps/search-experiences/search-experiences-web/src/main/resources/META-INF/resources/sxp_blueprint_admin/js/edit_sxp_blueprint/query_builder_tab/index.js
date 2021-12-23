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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import {ClayVerticalNav} from '@clayui/nav';
import ClayPanel from '@clayui/panel';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {PropTypes} from 'prop-types';
import React, {useContext, useEffect, useState} from 'react';

import JSONSXPElement from '../../shared/JSONSXPElement';
import ThemeContext from '../../shared/ThemeContext';
import SXPElement from '../../shared/sxp_element/index';
import {SXP_ELEMENT_PREFIX} from '../../utils/constants';
import {fetchData} from '../../utils/fetch';
import SelectTypes from './SelectTypes';

const VERTICAL_NAV_KEYS = {
	QUERY_ELEMENTS: 'queryElements',
	QUERY_SETTINGS: 'querySettings',
};

function QueryBuilderTab({
	elementInstances,
	entityJSON,
	errors = [],
	frameworkConfig = {},
	isSubmitting,
	onBlur,
	onChange,
	onDeleteSXPElement,
	onFrameworkConfigChange,
	onToggleSidebar,
	setFieldTouched,
	setFieldValue,
	touched = [],
}) {
	const {locale} = useContext(ThemeContext);

	const [activeVerticalNavKey, setActiveVerticalNavKey] = useState(
		VERTICAL_NAV_KEYS.QUERY_ELEMENTS
	);
	const [collapseAll, setCollapseAll] = useState(false);
	const [searchableTypes, setSearchableTypes] = useState(null);
	const [indexFields, setIndexFields] = useState(null);

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
	}, []); //eslint-disable-line

	if (!searchableTypes || !indexFields) {
		return null;
	}

	/**
	 * Handles navigating to a different vertical nav tab.
	 * @param {string} key A `VERTICAL_NAV_KEYS` value.
	 */
	const _handleClickVerticalNav = (verticalNavKey) => () => {
		setActiveVerticalNavKey(verticalNavKey);
	};

	const _renderContentQueryElements = () => {
		return (
			<>
				<ClayLayout.Row
					className="configuration-header"
					justify="between"
				>
					<ClayLayout.Col size={6}>
						{Liferay.Language.get('query-builder')}
					</ClayLayout.Col>

					<ClayLayout.Col size={6}>
						<div className="builder-actions">
							<ClayButton
								aria-label={Liferay.Language.get(
									'collapse-all'
								)}
								className="collapse-button"
								displayType="unstyled"
								onClick={() => setCollapseAll(!collapseAll)}
							>
								{collapseAll
									? Liferay.Language.get('expand-all')
									: Liferay.Language.get('collapse-all')}
							</ClayButton>

							<ClayTooltipProvider>
								<ClayButton
									aria-label={Liferay.Language.get(
										'add-query-element'
									)}
									displayType="primary"
									monospaced
									onClick={onToggleSidebar}
									small
									title={Liferay.Language.get(
										'add-query-element'
									)}
								>
									<ClayIcon symbol="plus" />
								</ClayButton>
							</ClayTooltipProvider>
						</div>
					</ClayLayout.Col>
				</ClayLayout.Row>

				{elementInstances.length === 0 ? (
					<div className="sheet">
						<div className="selected-sxp-elements-empty-text">
							{Liferay.Language.get(
								'add-elements-to-optimize-search-results-for-your-use-cases'
							)}
						</div>
					</div>
				) : (
					_renderSelectedElements()
				)}
			</>
		);
	};

	const _renderContentQuerySettings = () => {
		return (
			<>
				<ClayLayout.Row
					className="configuration-header"
					justify="between"
				>
					<ClayLayout.Col size={12}>
						{Liferay.Language.get('query-settings')}
					</ClayLayout.Col>
				</ClayLayout.Row>

				<div className="settings-content-container sheet">
					<ClayPanel.Group flush>
						<ClayPanel
							className="searchable-types"
							collapsable
							displayTitle={Liferay.Language.get(
								'searchable-types'
							)}
							displayType="unstyled"
							showCollapseIcon
						>
							<ClayPanel.Body>
								<div className="sheet-text">
									{Liferay.Language.get(
										'select-the-searchable-types-description'
									)}
								</div>

								<SelectTypes
									onFrameworkConfigChange={
										onFrameworkConfigChange
									}
									searchableTypes={searchableTypes}
									selectedTypes={
										frameworkConfig.searchableAssetTypes
									}
								/>
							</ClayPanel.Body>
						</ClayPanel>
					</ClayPanel.Group>
				</div>
			</>
		);
	};

	const _renderSelectedElements = () => (
		<>
			{elementInstances.map(
				({id, sxpElement, uiConfigurationValues}, index) => {
					return sxpElement.elementDefinition?.uiConfiguration ? (
						<SXPElement
							collapseAll={collapseAll}
							entityJSON={entityJSON}
							error={errors[index]}
							id={id}
							index={index}
							indexFields={indexFields}
							isSubmitting={isSubmitting}
							key={id}
							onBlur={onBlur}
							onChange={onChange}
							onDeleteSXPElement={onDeleteSXPElement}
							prefixedId={`${SXP_ELEMENT_PREFIX.QUERY}-${index}`}
							searchableTypes={searchableTypes}
							setFieldTouched={setFieldTouched}
							setFieldValue={setFieldValue}
							sxpElement={sxpElement}
							touched={touched[index]}
							uiConfigurationValues={uiConfigurationValues}
						/>
					) : (
						<JSONSXPElement
							collapseAll={collapseAll}
							error={errors[index]}
							id={id}
							index={index}
							isSubmitting={isSubmitting}
							key={id}
							onDeleteSXPElement={onDeleteSXPElement}
							prefixedId={`${SXP_ELEMENT_PREFIX.QUERY}-${index}`}
							setFieldTouched={setFieldTouched}
							setFieldValue={setFieldValue}
							sxpElement={sxpElement}
							touched={touched[index]}
							uiConfigurationValues={uiConfigurationValues}
						/>
					);
				}
			)}
		</>
	);

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
										VERTICAL_NAV_KEYS.QUERY_ELEMENTS,
									label: Liferay.Language.get(
										'query-elements'
									),
									onClick: _handleClickVerticalNav(
										VERTICAL_NAV_KEYS.QUERY_ELEMENTS
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
								VERTICAL_NAV_KEYS.QUERY_ELEMENTS &&
								_renderContentQueryElements()}

							{activeVerticalNavKey ===
								VERTICAL_NAV_KEYS.QUERY_SETTINGS &&
								_renderContentQuerySettings()}
						</div>
					</ClayLayout.Col>
				</ClayLayout.Row>
			</div>
		</ClayLayout.ContainerFluid>
	);
}

QueryBuilderTab.propTypes = {
	elementInstances: PropTypes.arrayOf(PropTypes.object),
	entityJSON: PropTypes.object,
	errors: PropTypes.arrayOf(PropTypes.object),
	frameworkConfig: PropTypes.object,
	isSubmitting: PropTypes.bool,
	onBlur: PropTypes.func,
	onChange: PropTypes.func,
	onDeleteSXPElement: PropTypes.func,
	onFrameworkConfigChange: PropTypes.func,
	onToggleSidebar: PropTypes.func,
	setFieldTouched: PropTypes.func,
	setFieldValue: PropTypes.func,
	touched: PropTypes.arrayOf(PropTypes.object),
};

export default React.memo(QueryBuilderTab);
