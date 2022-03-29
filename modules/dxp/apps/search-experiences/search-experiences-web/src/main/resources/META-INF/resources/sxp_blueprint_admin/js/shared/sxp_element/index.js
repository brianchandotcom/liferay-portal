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
import ClayDropDown from '@clayui/drop-down';
import ClayForm, {ClayToggle} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayList from '@clayui/list';
import ClaySticker from '@clayui/sticker';
import {ClayTooltipProvider} from '@clayui/tooltip';
import getCN from 'classnames';
import {PropTypes} from 'prop-types';
import React, {useContext, useEffect, useState} from 'react';

import {DEFAULT_SXP_ELEMENT_ICON} from '../../utils/data';
import {INPUT_TYPES} from '../../utils/inputTypes';
import {
	cleanUIConfiguration,
	getSXPElementJSON,
	isDefined,
} from '../../utils/utils';
import {PreviewModalWithCopyDownload} from '../PreviewModal';
import ThemeContext from '../ThemeContext';
import {getLocalizedText} from './../../utils/language';
import DateInput from './DateInput';
import FieldInput from './FieldInput';
import FieldListInput from './FieldListInput';
import ItemSelectorInput from './ItemSelectorInput';
import JSONInput from './JSONInput';
import MultiSelectInput from './MultiSelectInput';
import NumberInput from './NumberInput';
import SelectInput from './SelectInput';
import SliderInput from './SliderInput';
import TextInput from './TextInput';

/**
 * Converts the searchable types to be compatible with ClaySelect options prop.
 * @param {Array} searchableTypes Searchable types array from the EditSXPBlueprintDisplayBuilder
 * @returns {Array}
 */
const convertSearchableTypesToSelectOptions = (searchableTypes) => {
	return searchableTypes.map(({className, displayName}) => ({
		label: displayName,
		value: className,
	}));
};

function SXPElement({
	collapseAll,
	entityJSON,
	error = {},
	id,
	index,
	indexFields = [],
	isSubmitting,
	onBlur = () => {},
	onChange = () => {},
	onDeleteSXPElement,
	prefixedId,
	searchableTypes = [],
	setFieldTouched = () => {},
	setFieldValue = () => {},
	sxpElement,
	touched = {},
	uiConfigurationValues,
}) {
	const {locale} = useContext(ThemeContext);

	const [collapse, setCollapse] = useState(false);
	const [active, setActive] = useState(false);

	const description = getLocalizedText(sxpElement.description_i18n, locale);
	const title = getLocalizedText(sxpElement.title_i18n, locale);

	const fieldSets = cleanUIConfiguration(
		sxpElement.elementDefinition?.uiConfiguration
	).fieldSets;

	useEffect(() => {
		setCollapse(collapseAll);
	}, [collapseAll]);

	const _getInputId = (sxpElementId, configKey) => {
		return `${sxpElementId}_${configKey}`;
	};

	const _getInputName = (configKey) => {
		return `elementInstances[${index}].uiConfigurationValues.${configKey}`;
	};

	const _isEnabled = () => {
		const enabled =
			sxpElement.elementDefinition?.configuration?.queryConfiguration
				?.queryEntries?.[0]?.enabled;

		return isDefined(enabled) ? enabled : true;
	};

	const _handleDelete = () => {
		onDeleteSXPElement(id);
	};

	const _handleToggle = () => {
		setFieldValue(
			`elementInstances[${index}].sxpElement.elementDefinition.` +
				`configuration.queryConfiguration.queryEntries[0].enabled`,
			!_isEnabled()
		);
	};

	const _hasError = (config) =>
		touched.uiConfigurationValues?.[config.name] &&
		!!error.uiConfigurationValues?.[config.name];

	const _renderInput = (config) => {
		const disabled = !_isEnabled() || isSubmitting;
		const inputId = _getInputId(id, config.name);
		const inputName = _getInputName(config.name);
		const typeOptions = config.typeOptions || {};

		switch (config.type) {
			case INPUT_TYPES.DATE:
				return (
					<DateInput
						configKey={config.name}
						disabled={disabled}
						name={inputName}
						setFieldTouched={setFieldTouched}
						setFieldValue={setFieldValue}
						value={uiConfigurationValues[config.name]}
					/>
				);
			case INPUT_TYPES.FIELD_MAPPING:
				return (
					<FieldInput
						disabled={disabled}
						id={inputId}
						indexFields={indexFields}
						name={inputName}
						setFieldTouched={setFieldTouched}
						setFieldValue={setFieldValue}
						showBoost={typeOptions.boost}
						value={uiConfigurationValues[config.name]}
					/>
				);
			case INPUT_TYPES.FIELD_MAPPING_LIST:
				return (
					<FieldListInput
						disabled={disabled}
						id={inputId}
						indexFields={indexFields}
						name={inputName}
						onBlur={onBlur}
						setFieldTouched={setFieldTouched}
						setFieldValue={setFieldValue}
						showBoost={typeOptions.boost}
						value={uiConfigurationValues[config.name]}
					/>
				);
			case INPUT_TYPES.ITEM_SELECTOR:
				return (
					<ItemSelectorInput
						disabled={disabled}
						entityJSON={entityJSON}
						id={inputId}
						itemType={typeOptions.itemType}
						label={config.label}
						name={inputName}
						setFieldTouched={setFieldTouched}
						setFieldValue={setFieldValue}
						value={uiConfigurationValues[config.name]}
					/>
				);
			case INPUT_TYPES.JSON:
				return (
					<JSONInput
						disabled={disabled}
						label={config.label}
						name={inputName}
						nullable={typeOptions.nullable}
						required={typeOptions.required}
						setFieldTouched={setFieldTouched}
						setFieldValue={setFieldValue}
						value={uiConfigurationValues[config.name]}
					/>
				);
			case INPUT_TYPES.MULTISELECT:
				return (
					<MultiSelectInput
						disabled={disabled}
						id={inputId}
						label={config.label}
						name={inputName}
						setFieldTouched={setFieldTouched}
						setFieldValue={setFieldValue}
						value={uiConfigurationValues[config.name]}
					/>
				);
			case INPUT_TYPES.NUMBER:
				return (
					<NumberInput
						configKey={config.name}
						disabled={disabled}
						id={inputId}
						label={config.label}
						max={typeOptions.max}
						min={typeOptions.min}
						name={inputName}
						onBlur={onBlur}
						onChange={onChange}
						setFieldValue={setFieldValue}
						step={typeOptions.step}
						unit={typeOptions.unit}
						value={uiConfigurationValues[config.name]}
					/>
				);
			case INPUT_TYPES.SEARCHABLE_TYPE:
				return (
					<SelectInput
						configKey={config.name}
						disabled={disabled}
						id={inputId}
						label={config.label}
						name={inputName}
						nullable={typeOptions.nullable}
						onBlur={onBlur}
						onChange={onChange}
						options={convertSearchableTypesToSelectOptions(
							searchableTypes
						)}
						setFieldValue={setFieldValue}
						value={uiConfigurationValues[config.name]}
					/>
				);
			case INPUT_TYPES.SELECT:
				return (
					<SelectInput
						configKey={config.name}
						disabled={disabled}
						id={inputId}
						label={config.label}
						name={inputName}
						nullable={typeOptions.nullable}
						onBlur={onBlur}
						onChange={onChange}
						options={typeOptions.options}
						setFieldValue={setFieldValue}
						value={uiConfigurationValues[config.name]}
					/>
				);
			case INPUT_TYPES.SLIDER:
				return (
					<SliderInput
						disabled={disabled}
						id={inputId}
						label={config.label}
						max={typeOptions.max}
						min={typeOptions.min}
						name={inputName}
						onBlur={onBlur}
						onChange={onChange}
						setFieldTouched={setFieldTouched}
						setFieldValue={setFieldValue}
						step={typeOptions.step}
						value={uiConfigurationValues[config.name]}
					/>
				);
			default:
				return (
					<TextInput
						disabled={disabled}
						id={inputId}
						label={config.label}
						name={inputName}
						onBlur={onBlur}
						onChange={onChange}
						value={uiConfigurationValues[config.name]}
					/>
				);
		}
	};

	return (
		<div
			className={getCN('sxp-element', 'sheet', {
				disabled: !_isEnabled(),
			})}
			id={prefixedId}
		>
			<ClayList className="configuration-header-list">
				<ClayList.Item flex>
					<ClayList.ItemField>
						<ClaySticker size="md">
							<ClayIcon
								symbol={
									sxpElement.elementDefinition?.icon ||
									DEFAULT_SXP_ELEMENT_ICON
								}
							/>
						</ClaySticker>
					</ClayList.ItemField>

					<ClayList.ItemField expand>
						{title && (
							<ClayList.ItemTitle>{title}</ClayList.ItemTitle>
						)}

						{description && (
							<ClayList.ItemText subtext={true}>
								{description}
							</ClayList.ItemText>
						)}
					</ClayList.ItemField>

					<ClayToggle
						aria-label={
							_isEnabled()
								? Liferay.Language.get('enabled')
								: Liferay.Language.get('disabled')
						}
						onToggle={_handleToggle}
						toggled={_isEnabled()}
					/>

					<ClayDropDown
						active={active}
						alignmentPosition={3}
						onActiveChange={setActive}
						trigger={
							<ClayList.ItemField>
								<ClayButton
									aria-label={Liferay.Language.get(
										'dropdown'
									)}
									borderless
									displayType="secondary"
									monospaced
									small
								>
									<ClayIcon symbol="ellipsis-v" />
								</ClayButton>
							</ClayList.ItemField>
						}
					>
						<ClayDropDown.ItemList>
							<PreviewModalWithCopyDownload
								fileName="sxpElement.json"
								size="lg"
								text={JSON.stringify(
									getSXPElementJSON(
										sxpElement,
										uiConfigurationValues
									),
									null,
									'\t'
								)}
								title={Liferay.Language.get('element-json')}
							>
								<ClayDropDown.Item>
									{Liferay.Language.get('view-element-json')}
								</ClayDropDown.Item>
							</PreviewModalWithCopyDownload>

							{onDeleteSXPElement && (
								<ClayDropDown.Item onClick={_handleDelete}>
									{Liferay.Language.get('remove')}
								</ClayDropDown.Item>
							)}
						</ClayDropDown.ItemList>
					</ClayDropDown>

					{fieldSets.length > 0 && (
						<ClayList.ItemField>
							<ClayButton
								aria-label={
									!collapse
										? Liferay.Language.get('collapse')
										: Liferay.Language.get('expand')
								}
								borderless
								displayType="secondary"
								monospaced
								onClick={() => {
									setCollapse(!collapse);
								}}
								small
							>
								<ClayIcon
									symbol={
										!collapse ? 'angle-down' : 'angle-right'
									}
								/>
							</ClayButton>
						</ClayList.ItemField>
					)}
				</ClayList.Item>
			</ClayList>

			{!collapse && fieldSets.length > 0 && (
				<ClayList className="configuration-form-list">
					{fieldSets.map(({fields}) => {
						return fields.map((config) => (
							<ClayList.Item
								className={config.type}
								flex
								key={config.name}
							>
								{config.type !== INPUT_TYPES.JSON && (
									<ClayList.ItemField className="list-item-label">
										<label
											htmlFor={_getInputId(
												id,
												config.name
											)}
										>
											{config.label}

											{((isDefined(
												config.typeOptions?.required
											) &&
												!config.typeOptions.required) ||
												config.typeOptions
													?.nullable) && (
												<span className="optional-text">
													{Liferay.Language.get(
														'optional'
													)}
												</span>
											)}

											{config.helpText && (
												<ClayTooltipProvider>
													<ClaySticker
														displayType="unstyled"
														size="sm"
														title={config.helpText}
													>
														<ClayIcon
															data-tooltip-align="top"
															symbol="info-circle"
														/>
													</ClaySticker>
												</ClayTooltipProvider>
											)}
										</label>
									</ClayList.ItemField>
								)}

								<ClayList.ItemField
									className={getCN({
										'has-error': _hasError(config),
									})}
									expand
								>
									{_renderInput(config)}

									{_hasError(config) && (
										<ClayForm.FeedbackGroup>
											<ClayForm.FeedbackItem>
												<ClayForm.FeedbackIndicator symbol="exclamation-full" />

												{
													error.uiConfigurationValues[
														config.name
													]
												}
											</ClayForm.FeedbackItem>
										</ClayForm.FeedbackGroup>
									)}
								</ClayList.ItemField>
							</ClayList.Item>
						));
					})}
				</ClayList>
			)}
		</div>
	);
}

SXPElement.propTypes = {
	collapseAll: PropTypes.bool,
	entityJSON: PropTypes.object,
	error: PropTypes.object,
	id: PropTypes.number,
	index: PropTypes.number,
	indexFields: PropTypes.arrayOf(PropTypes.object),
	isSubmitting: PropTypes.bool,
	onBlur: PropTypes.func,
	onChange: PropTypes.func,
	onDeleteSXPElement: PropTypes.func,
	prefixedId: PropTypes.string,
	searchableTypes: PropTypes.arrayOf(PropTypes.object),
	setFieldTouched: PropTypes.func,
	setFieldValue: PropTypes.func,
	touched: PropTypes.object,
	uiConfigurationValues: PropTypes.object,
};

export default React.memo(SXPElement);
