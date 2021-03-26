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

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import {ClayRadio} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayList from '@clayui/list';
import ClayPanel from '@clayui/panel';
import ClaySticker from '@clayui/sticker';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {PropTypes} from 'prop-types';
import React, {useContext, useState} from 'react';

import JSONElement from '../../shared/JSONElement';
import ThemeContext from '../../shared/ThemeContext';
import Element from '../../shared/element/index';
import {ELEMENT_PREFIX} from '../../utils/constants';
import {getElementOutput} from '../../utils/utils';
import SelectAssetTypes from './SelectAssetTypes';

const FrameworkListItem = ({
	checked,
	description,
	imagePath,
	onChange,
	title,
}) => {
	return (
		<ClayList.Item flex>
			<ClayList.ItemField>
				<ClayRadio checked={checked} onChange={onChange} />
			</ClayList.ItemField>

			<ClayList.ItemField>
				<ClaySticker className="framework-icon" size="xl">
					<ClaySticker.Image alt="placeholder" src={imagePath} />
				</ClaySticker>
			</ClayList.ItemField>

			<ClayList.ItemField className="framework-text" expand>
				<ClayList.ItemTitle>{title}</ClayList.ItemTitle>

				<ClayList.ItemText>{description}</ClayList.ItemText>
			</ClayList.ItemField>
		</ClayList.Item>
	);
};

function QueryBuilder({
	entityJSON,
	errors = [],
	frameworkConfig,
	indexFields,
	onBlur,
	onChange,
	onDeleteElement,
	onFrameworkConfigChange,
	onToggleSidebar,
	searchableAssetTypes,
	selectedElements,
	setFieldTouched,
	setFieldValue,
	touched = [],
}) {
	const {contextPath} = useContext(ThemeContext);
	const [collapseAll, setCollapseAll] = useState(false);

	const _hasMustClause =
		!!frameworkConfig.apply_indexer_clauses ||
		selectedElements.some((element) => {
			const elementOutput = getElementOutput(element);

			return (
				elementOutput.clauses &&
				elementOutput.clauses[0] &&
				elementOutput.clauses[0].occur &&
				elementOutput.clauses[0].occur === 'must' &&
				elementOutput.enabled
			);
		});

	const _renderSelectedElements = () => {
		return (
			<>
				{!_hasMustClause && (
					<ClayAlert
						displayType="warning"
						title={Liferay.Language.get('warning')}
					>
						{Liferay.Language.get(
							'there-are-no-match-clauses-in-your-configuration'
						)}
					</ClayAlert>
				)}

				{selectedElements.map((element, index) => {
					return element.uiConfigurationJSON ? (
						<Element
							collapseAll={collapseAll}
							elementTemplateJSON={element.elementTemplateJSON}
							entityJSON={entityJSON}
							error={errors[index]}
							id={element.id}
							index={index}
							indexFields={indexFields}
							key={element.id}
							onBlur={onBlur}
							onChange={onChange}
							onDeleteElement={onDeleteElement}
							prefixedId={`${ELEMENT_PREFIX.QUERY}-${element.id}`}
							setFieldTouched={setFieldTouched}
							setFieldValue={setFieldValue}
							touched={touched[index]}
							uiConfigurationJSON={element.uiConfigurationJSON}
							uiConfigurationValues={
								element.uiConfigurationValues
							}
						/>
					) : (
						<JSONElement
							collapseAll={collapseAll}
							elementTemplateJSON={element.elementTemplateJSON}
							error={errors[index]}
							id={element.id}
							index={index}
							key={element.id}
							onDeleteElement={onDeleteElement}
							prefixedId={`${ELEMENT_PREFIX.QUERY}-${element.id}`}
							setFieldTouched={setFieldTouched}
							setFieldValue={setFieldValue}
							touched={touched[index]}
							uiConfigurationValues={
								element.uiConfigurationValues
							}
						/>
					);
				})}
			</>
		);
	};

	return (
		<ClayLayout.ContainerFluid className="builder" size="xl">
			<div className="builder-shift">
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

				{selectedElements.length === 0 ? (
					<div className="sheet">
						<div className="selected-elements-empty-text">
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
						{Liferay.Language.get('settings')}
					</ClayLayout.Col>
				</ClayLayout.Row>

				<div className="settings-content-container sheet">
					<ClayPanel.Group flush>
						<ClayPanel
							className="searchable-asset-types"
							collapsable
							displayTitle={Liferay.Language.get(
								'searchable-asset-types'
							)}
							displayType="unstyled"
							showCollapseIcon
						>
							<ClayPanel.Body>
								<div className="sheet-text">
									{Liferay.Language.get(
										'select-the-searchable-asset-types'
									)}
								</div>

								<SelectAssetTypes
									onFrameworkConfigChange={
										onFrameworkConfigChange
									}
									searchableAssetTypes={searchableAssetTypes}
									selectedAssetTypes={
										frameworkConfig.searchable_asset_types
									}
								/>
							</ClayPanel.Body>
						</ClayPanel>
					</ClayPanel.Group>

					<ClayPanel.Group flush>
						<ClayPanel
							collapsable
							displayTitle={Liferay.Language.get('framework')}
							displayType="unstyled"
							showCollapseIcon
						>
							<ClayPanel.Body>
								<div className="sheet-text">
									{Liferay.Language.get(
										'please-note-that-blueprints-selected-framework-determines-whether-the-asset-types-default-clause-is-used'
									)}
								</div>

								<ClayList>
									<FrameworkListItem
										checked={
											frameworkConfig.apply_indexer_clauses
										}
										description={Liferay.Language.get(
											'compose-elements-on-top-of-liferay-default-search-clauses'
										)}
										imagePath={`${contextPath}/images/liferay-default-clauses.svg`}
										onChange={() =>
											onFrameworkConfigChange({
												apply_indexer_clauses: true,
											})
										}
										title={Liferay.Language.get(
											'liferay-default-clauses'
										)}
									/>

									<FrameworkListItem
										checked={
											!frameworkConfig.apply_indexer_clauses
										}
										description={Liferay.Language.get(
											'compose-elements-from-the-ground-up'
										)}
										imagePath={`${contextPath}/images/custom-clauses.svg`}
										onChange={() =>
											onFrameworkConfigChange({
												apply_indexer_clauses: false,
											})
										}
										title={Liferay.Language.get(
											'custom-clauses'
										)}
									/>
								</ClayList>
							</ClayPanel.Body>
						</ClayPanel>
					</ClayPanel.Group>
				</div>
			</div>
		</ClayLayout.ContainerFluid>
	);
}

QueryBuilder.propTypes = {
	entityJSON: PropTypes.object,
	errors: PropTypes.arrayOf(PropTypes.object),
	frameworkConfig: PropTypes.object,
	indexFields: PropTypes.arrayOf(PropTypes.object),
	onBlur: PropTypes.func,
	onChange: PropTypes.func,
	onDeleteElement: PropTypes.func,
	onFrameworkConfigChange: PropTypes.func,
	onToggleSidebar: PropTypes.func,
	searchableAssetTypes: PropTypes.arrayOf(PropTypes.string),
	selectedElements: PropTypes.arrayOf(PropTypes.object),
	setFieldTouched: PropTypes.func,
	setFieldValue: PropTypes.func,
	touched: PropTypes.arrayOf(PropTypes.object),
};

export default React.memo(QueryBuilder);
