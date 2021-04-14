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

import {INPUT_TYPES} from '../../utils/inputTypes';
import {getElementOutput, isDefined, isEmpty} from '../../utils/utils';
import CodeMirrorEditor from '../CodeMirrorEditor';
import PreviewModal from '../PreviewModal';
import ThemeContext from '../ThemeContext';
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

function Element({
	collapseAll,
	elementTemplateJSON,
	entityJSON,
	error = {},
	id,
	index,
	indexFields = [],
	onBlur = () => {},
	onChange = () => {},
	onDeleteElement,
	prefixedId,
	setFieldTouched = () => {},
	setFieldValue = () => {},
	touched = {},
	uiConfigurationJSON,
	uiConfigurationValues,
}) {
	const {locale} = useContext(ThemeContext);
	const [collapse, setCollapse] = useState(false);
	const [active, setActive] = useState(false);

	useEffect(() => {
		setCollapse(collapseAll);
	}, [collapseAll]);

	const _getInputId = (elementId, configKey) => {
		return `${elementId}_${configKey}`;
	};

	const _getInputName = (configKey) => {
		return `selectedQueryElements[${index}].uiConfigurationValues.${configKey}`;
	};

	const _handleDelete = () => {
		onDeleteElement(id);
	};

	const _handleToggle = () => {
		setFieldValue(
			`selectedQueryElements[${index}].elementTemplateJSON.enabled`,
			!elementTemplateJSON.enabled
		);
	};

	const _hasDescription =
		!isEmpty(elementTemplateJSON.description) ||
		!isEmpty(elementTemplateJSON.description[locale]);

	const _hasConfigurationValues =
		!!uiConfigurationJSON &&
		uiConfigurationJSON.fieldSets &&
		uiConfigurationJSON.fieldSets.some(
			(item) => item.fields && item.fields.length > 0
		);

	const _hasError = (config) =>
		touched.uiConfigurationValues &&
		touched.uiConfigurationValues[config.name] &&
		error.uiConfigurationValues &&
		!!error.uiConfigurationValues[config.name];

	const _renderInput = (config) => {
		const disabled = !elementTemplateJSON.enabled;
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
						setFieldTouched={setFieldTouched}
						setFieldValue={setFieldValue}
						value={uiConfigurationValues[config.name]}
					/>
				);
			case INPUT_TYPES.MULTISELECT:
				return (
					<MultiSelectInput
						disabled={disabled}
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
						step={typeOptions.step}
						unit={typeOptions.unit}
						value={uiConfigurationValues[config.name]}
					/>
				);
			case INPUT_TYPES.SELECT:
				return (
					<SelectInput
						configKey={config.name}
						disabled={disabled}
						label={config.label}
						name={inputName}
						options={typeOptions.options}
						setFieldTouched={setFieldTouched}
						setFieldValue={setFieldValue}
						value={uiConfigurationValues[config.name]}
					/>
				);
			case INPUT_TYPES.SLIDER:
				return (
					<SliderInput
						disabled={disabled}
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
			className={getCN('element', 'sheet', {
				disabled: !elementTemplateJSON.enabled,
			})}
			id={prefixedId}
		>
			<ClayList className="configuration-header-list">
				<ClayList.Item flex>
					<ClayList.ItemField>
						<ClaySticker size="md">
							<ClayIcon symbol={elementTemplateJSON.icon} />
						</ClaySticker>
					</ClayList.ItemField>

					<ClayList.ItemField expand>
						{elementTemplateJSON.title && (
							<ClayList.ItemTitle>
								{elementTemplateJSON.title[locale] ||
									(typeof elementTemplateJSON.title ==
										'string' &&
										elementTemplateJSON.title)}
							</ClayList.ItemTitle>
						)}

						{_hasDescription && (
							<ClayList.ItemText subtext={true}>
								{elementTemplateJSON.description[locale] ||
									(typeof elementTemplateJSON.description ==
										'string' &&
										elementTemplateJSON.description)}
							</ClayList.ItemText>
						)}
					</ClayList.ItemField>

					<ClayToggle
						onToggle={_handleToggle}
						toggled={elementTemplateJSON.enabled}
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
							<PreviewModal
								body={
									<div className="json-modal">
										<CodeMirrorEditor
											readOnly
											value={JSON.stringify(
												getElementOutput({
													elementTemplateJSON,
													uiConfigurationJSON,
													uiConfigurationValues,
												}),
												null,
												'\t'
											)}
										/>
									</div>
								}
								size="lg"
								title={Liferay.Language.get('element-json')}
							>
								<ClayDropDown.Item>
									{Liferay.Language.get('view-element-json')}
								</ClayDropDown.Item>
							</PreviewModal>

							{onDeleteElement && (
								<ClayDropDown.Item onClick={_handleDelete}>
									{Liferay.Language.get('remove')}
								</ClayDropDown.Item>
							)}
						</ClayDropDown.ItemList>
					</ClayDropDown>

					{_hasConfigurationValues && (
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

			{!collapse && _hasConfigurationValues && (
				<ClayList className="configuration-form-list">
					{uiConfigurationJSON.fieldSets.map((fieldSet) => {
						if (fieldSet.fields) {
							return fieldSet.fields.map((config) => (
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

												{config.helpText && (
													<ClayTooltipProvider>
														<ClaySticker
															displayType="unstyled"
															size="sm"
														>
															<ClayIcon
																data-tooltip-align="top"
																symbol="info-circle"
																title={
																	config.helpText
																}
															/>
														</ClaySticker>
													</ClayTooltipProvider>
												)}

												{config.typeOptions &&
													isDefined(
														config.typeOptions
															.required
													) &&
													!config.typeOptions
														.required && (
														<span className="optional-text">
															{Liferay.Language.get(
																'optional'
															)}
														</span>
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
														error
															.uiConfigurationValues[
															config.name
														]
													}
												</ClayForm.FeedbackItem>
											</ClayForm.FeedbackGroup>
										)}
									</ClayList.ItemField>
								</ClayList.Item>
							));
						}
					})}
				</ClayList>
			)}
		</div>
	);
}

Element.propTypes = {
	collapseAll: PropTypes.bool,
	elementTemplateJSON: PropTypes.object,
	entityJSON: PropTypes.object,
	error: PropTypes.object,
	id: PropTypes.number,
	index: PropTypes.number,
	indexFields: PropTypes.arrayOf(PropTypes.object),
	onBlur: PropTypes.func,
	onChange: PropTypes.func,
	onDeleteElement: PropTypes.func,
	prefixedId: PropTypes.string,
	setFieldTouched: PropTypes.func,
	setFieldValue: PropTypes.func,
	touched: PropTypes.object,
	uiConfigurationJSON: PropTypes.object,
	uiConfigurationValues: PropTypes.object,
};

export default React.memo(Element);
