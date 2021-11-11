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

function QueryBuilderTab({
	entityJSON,
	errors = [],
	frameworkConfig = {},
	isSubmitting,
	onBlur,
	onChange,
	onDeleteSXPElement,
	onFrameworkConfigChange,
	onToggleSidebar,
	selectedSXPElements,
	setFieldTouched,
	setFieldValue,
	touched = [],
}) {
	const {locale} = useContext(ThemeContext);

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

	const _renderSelectedElements = () => (
		<>
			{selectedSXPElements.map((sxpElement, index) => {
				return sxpElement.uiConfigurationJSON ? (
					<SXPElement
						collapseAll={collapseAll}
						entityJSON={entityJSON}
						error={errors[index]}
						id={sxpElement.id}
						index={index}
						indexFields={indexFields}
						isSubmitting={isSubmitting}
						key={sxpElement.id}
						onBlur={onBlur}
						onChange={onChange}
						onDeleteSXPElement={onDeleteSXPElement}
						prefixedId={`${SXP_ELEMENT_PREFIX.QUERY}-${index}`}
						searchableTypes={searchableTypes}
						setFieldTouched={setFieldTouched}
						setFieldValue={setFieldValue}
						sxpElementTemplateJSON={
							sxpElement.sxpElementTemplateJSON
						}
						touched={touched[index]}
						uiConfigurationJSON={sxpElement.uiConfigurationJSON}
						uiConfigurationValues={sxpElement.uiConfigurationValues}
					/>
				) : (
					<JSONSXPElement
						collapseAll={collapseAll}
						error={errors[index]}
						id={sxpElement.id}
						index={index}
						isSubmitting={isSubmitting}
						key={sxpElement.id}
						onDeleteSXPElement={onDeleteSXPElement}
						prefixedId={`${SXP_ELEMENT_PREFIX.QUERY}-${index}`}
						setFieldTouched={setFieldTouched}
						setFieldValue={setFieldValue}
						sxpElementTemplateJSON={
							sxpElement.sxpElementTemplateJSON
						}
						touched={touched[index]}
						uiConfigurationValues={sxpElement.uiConfigurationValues}
					/>
				);
			})}
		</>
	);

	return (
		<ClayLayout.ContainerFluid
			className="builder query-builder-tab"
			size="xl"
		>
			<div className="builder-content-shift">
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

				{selectedSXPElements.length === 0 ? (
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

				<ClayLayout.Row
					className="configuration-header configuration-header-settings"
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
										frameworkConfig.searchable_asset_types
									}
								/>
							</ClayPanel.Body>
						</ClayPanel>
					</ClayPanel.Group>
				</div>
			</div>
		</ClayLayout.ContainerFluid>
	);
}

QueryBuilderTab.propTypes = {
	entityJSON: PropTypes.object,
	errors: PropTypes.arrayOf(PropTypes.object),
	frameworkConfig: PropTypes.object,
	isSubmitting: PropTypes.bool,
	onBlur: PropTypes.func,
	onChange: PropTypes.func,
	onDeleteSXPElement: PropTypes.func,
	onFrameworkConfigChange: PropTypes.func,
	onToggleSidebar: PropTypes.func,
	selectedSXPElements: PropTypes.arrayOf(PropTypes.object),
	setFieldTouched: PropTypes.func,
	setFieldValue: PropTypes.func,
	touched: PropTypes.arrayOf(PropTypes.object),
};

export default React.memo(QueryBuilderTab);
