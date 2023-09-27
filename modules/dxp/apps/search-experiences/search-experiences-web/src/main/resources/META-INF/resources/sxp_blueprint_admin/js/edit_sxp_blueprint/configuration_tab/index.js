/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayRadio, ClayRadioGroup, ClaySelect} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import getCN from 'classnames';
import React, {useContext} from 'react';

import advancedConfigurationSchema from '../../../schemas/advanced-configuration.schema.json';
import aggregationConfigurationSchema from '../../../schemas/aggregation-configuration.schema.json';
import highlightConfigurationSchema from '../../../schemas/highlight-configuration.schema.json';
import parameterConfigurationSchema from '../../../schemas/parameter-configuration.schema.json';
import sortConfigurationSchema from '../../../schemas/sort-configuration.schema.json';
import CodeMirrorEditor from '../../shared/CodeMirrorEditor';
import LearnMessage from '../../shared/LearnMessage';
import ThemeContext from '../../shared/ThemeContext';
import {DEFAULT_INDEX_CONFIGURATION} from '../../utils/constants';

const CONFIGURATION_SCHEMAS = {
	advancedConfig: advancedConfigurationSchema,
	aggregationConfig: aggregationConfigurationSchema,
	highlightConfig: highlightConfigurationSchema,
	parameterConfig: parameterConfigurationSchema,
	sortConfig: sortConfigurationSchema,
};

function ConfigurationTab({
	advancedConfig,
	aggregationConfig,
	errors,
	highlightConfig,
	indexConfig,
	parameterConfig,
	searchIndexes,
	setFieldTouched,
	setFieldValue,
	sortConfig,
	touched,
}) {
	const {featureFlagLps153813, isCompanyAdmin} = useContext(ThemeContext);

	/**
	 * Gets the `external` value using the `searchIndexes` array.
	 * @param {string} name The index name.
	 * @returns {boolean}
	 */
	const _getExternalValue = (name) => {
		return searchIndexes.find((searchIndex) => searchIndex.name === name)
			.external;
	};

	/**
	 * Called when the Index Configuration radio selection is changed.
	 * @param {boolean} value
	 * 	true = 'Default Company Index',
	 * 	false = 'Configure a Different Index'
	 */
	const _handleIndexConfigurationRadioChange = (value) => {
		setFieldValue(
			'indexConfig',
			value
				? DEFAULT_INDEX_CONFIGURATION
				: {
						external: searchIndexes[0].external,
						indexName: searchIndexes[0].name,
				  }
		);
	};

	/**
	 * Called when the Index Configuration "Configure a Different Index"
	 * selector is changed.
	 * @param {string} event.target.value
	 */
	const _handleIndexConfigurationSelectChange = (event) => {
		setFieldValue('indexConfig', {
			external: _getExternalValue(event.target.value),
			indexName: event.target.value,
		});
	};

	/**
	 * Checks if company index is selected.
	 * @returns {boolean}
	 */
	const _isCompanyIndex = () => {
		return indexConfig.indexName === '';
	};

	const _renderEditor = (configName, configValue) => (
		<div
			className={getCN({
				'has-error': touched[configName] && errors[configName],
			})}
			onBlur={() => setFieldTouched(configName)}
		>
			<CodeMirrorEditor
				autocompleteSchema={CONFIGURATION_SCHEMAS[configName]}
				onChange={(value) => setFieldValue(configName, value)}
				value={configValue}
			/>

			{touched[configName] && errors[configName] && (
				<ClayForm.FeedbackGroup>
					<ClayForm.FeedbackItem>
						<ClayForm.FeedbackIndicator symbol="exclamation-full" />

						{errors[configName]}
					</ClayForm.FeedbackItem>
				</ClayForm.FeedbackGroup>
			)}
		</div>
	);

	return (
		<ClayLayout.ContainerFluid className="layout-section-main" size="xl">
			<div className="layout-section-main-shift">
				<div className="sheet sheet-lg">
					<h2 className="sheet-title">
						{Liferay.Language.get('configuration')}
					</h2>

					<div className="sheet-text">
						<span className="help-text">
							{Liferay.Language.get(
								'enter-additional-blueprints-configuration-settings-below-refer-to-the-documentation-for-help'
							)}
						</span>

						<LearnMessage resourceKey="search-blueprint-configuration" />
					</div>

					<ClayForm.Group>
						<label>
							{Liferay.Language.get('aggregation-configuration')}
						</label>

						<div className="sheet-text">
							<span className="help-text">
								{Liferay.Language.get(
									'aggregation-configuration-description'
								)}
							</span>

							<LearnMessage resourceKey="aggregation-configuration" />
						</div>

						{_renderEditor('aggregationConfig', aggregationConfig)}
					</ClayForm.Group>

					<ClayForm.Group>
						<label>
							{Liferay.Language.get('highlight-configuration')}
						</label>

						<div className="sheet-text">
							<span className="help-text">
								{Liferay.Language.get(
									'highlight-configuration-description'
								)}
							</span>

							<LearnMessage resourceKey="highlight-configuration" />
						</div>

						{_renderEditor('highlightConfig', highlightConfig)}
					</ClayForm.Group>

					<ClayForm.Group>
						<label>
							{Liferay.Language.get('sort-configuration')}
						</label>

						<div className="sheet-text">
							<span className="help-text">
								{Liferay.Language.get(
									'sort-configuration-description'
								)}
							</span>

							<LearnMessage resourceKey="sort-configuration" />
						</div>

						{_renderEditor('sortConfig', sortConfig)}
					</ClayForm.Group>

					<ClayForm.Group>
						<label>
							{Liferay.Language.get('parameter-configuration')}
						</label>

						<div className="sheet-text">
							<span className="help-text">
								{Liferay.Language.get(
									'parameter-configuration-description'
								)}
							</span>

							<LearnMessage resourceKey="parameter-configuration" />
						</div>

						{_renderEditor('parameterConfig', parameterConfig)}
					</ClayForm.Group>

					<ClayForm.Group>
						<label>
							{Liferay.Language.get('advanced-configuration')}
						</label>

						<div className="sheet-text">
							<span className="help-text">
								{Liferay.Language.get(
									'advanced-configuration-description'
								)}
							</span>

							<LearnMessage resourceKey="advanced-configuration" />
						</div>

						{_renderEditor('advancedConfig', advancedConfig)}
					</ClayForm.Group>

					{featureFlagLps153813 && isCompanyAdmin && (
						<ClayForm.Group>
							<label>
								{Liferay.Language.get('index-configuration')}
							</label>

							<div className="mb-4 sheet-text">
								<span className="help-text">
									{Liferay.Language.get(
										'index-configuration-description'
									)}
								</span>

								<LearnMessage resourceKey="index-configuration" />
							</div>

							<ClayRadioGroup
								onChange={_handleIndexConfigurationRadioChange}
								value={_isCompanyIndex()}
							>
								<ClayRadio
									label={Liferay.Language.get(
										'company-index'
									)}
									value={true}
								/>

								<ClayRadio
									disabled={!searchIndexes.length}
									label={Liferay.Language.get(
										'configure-a-different-index'
									)}
									value={false}
								/>
							</ClayRadioGroup>

							{!_isCompanyIndex() && (
								<ClaySelect
									aria-label={Liferay.Language.get(
										'index-configuration'
									)}
									onChange={
										_handleIndexConfigurationSelectChange
									}
									value={indexConfig.indexName}
								>
									{searchIndexes.map((searchIndex) => (
										<ClaySelect.Option
											key={searchIndex.name}
											label={searchIndex.name}
											value={searchIndex.name}
										/>
									))}
								</ClaySelect>
							)}
						</ClayForm.Group>
					)}
				</div>
			</div>
		</ClayLayout.ContainerFluid>
	);
}

export default React.memo(ConfigurationTab);
