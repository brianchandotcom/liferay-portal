/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayForm, {
	ClayRadio,
	ClayRadioGroup,
	ClaySelectWithOption,
} from '@clayui/form';
import {TItem} from '@clayui/form/lib/SelectBox';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayModal from '@clayui/modal';
import classNames from 'classnames';
import {fetch} from 'frontend-js-web';
import fuzzy from 'fuzzy';
import React, {useEffect, useState} from 'react';

import {FDSViewType} from '../../../../FDSViews';
import CheckboxMultiSelect from '../../../../components/CheckboxMultiSelect';
import FilterModalConfiguration from '../../../../components/FilterModalConfiguration';
import FilterModalFooter from '../../../../components/FilterModalFooter';
import RequiredMark from '../../../../components/RequiredMark';
import getAllPicklists from '../../../../utils/getAllPicklists';
import openDefaultFailureToast from '../../../../utils/openDefaultFailureToast';
import {
	EFilterType,
	ESelectionFilterSourceType,
	IField,
	IFilter,
	IPickList,
	ISelectionFilter,
} from '../../../../utils/types';
import ApiRestApplication from './source_type/ApiRestApplication';
import ObjectPicklist from './source_type/ObjectPicklist';

function Header() {
	return <>{Liferay.Language.get('new-selection-filter')}</>;
}

interface IBodyProps {
	closeModal: Function;
	fdsView: FDSViewType;
	fieldNames?: string[];
	fields: IField[];
	filter?: IFilter;
	namespace: string;
	onSave: Function;
	restApplications: string[];
}

function Body({
	closeModal,
	fieldNames,
	fields,
	filter,
	namespace,
	onSave,
	restApplications,
}: IBodyProps) {
	const [fieldInUseValidationError, setFieldInUseValidationError] = useState<
		boolean
	>(false);
	const [labelValidationError, setLabelValidationError] = useState(false);

	const [filteredSourceItems, setFilteredSourceItems] = useState<TItem[]>([]);
	const [preselectedValueInput, setPreselectedValueInput] = useState('');
	const [saveButtonDisabled, setSaveButtonDisabled] = useState<boolean>(
		false
	);
	const [includeMode, setIncludeMode] = useState<string>('include');
	const inUseFields: (string | undefined)[] = fields.map((item) =>
		fieldNames?.includes(item.name) ? item.name : undefined
	);
	const [multiple, setMultiple] = useState<boolean>(
		(filter as ISelectionFilter)?.multiple ?? true
	);
	const [picklists, setPicklists] = useState<IPickList[]>([]);
	const [preselectedValues, setPreselectedValues] = useState<any[]>([]);
	const [selectedField, setSelectedField] = useState<IField | undefined>(
		fields.find((item) => item.name === filter?.fieldName)
	);
	const [source, setSource] = useState<IPickList | string | undefined>();
	const [sourceType, setSourceType] = useState<
		ESelectionFilterSourceType | undefined
	>();
	const fdsFilterLabelTranslations = filter?.label_i18n ?? {};
	const [i18nFilterLabels, setI18nFilterLabels] = useState(
		fdsFilterLabelTranslations
	);
	const [selectedRESTApplication, setSelectedRESTApplication] = useState<
		string
	>('');
	const [selectedRESTSchema, setSelectedRESTSchema] = useState<string>('');
	const [selectedRESTEndpoint, setSelectedRESTEndpoint] = useState<string>(
		''
	);
	const [selectedItemKey, setSelectedItemKey] = useState<string>('');
	const [selectedItemLabel, setSelectedItemLabel] = useState<string>('');

	const includeModeFormElementId = `${namespace}IncludeMode`;
	const multipleFormElementId = `${namespace}Multiple`;
	const sourceOptionFormElementId = `${namespace}SourceOption`;
	const preselectedValuesFormElementId = `${namespace}PreselectedValues`;

	const isValidSingleMode =
		multiple || (!multiple && !(preselectedValues.length > 1));

	async function getAPIValues(source: string) {
		const response = await fetch(`/o${source}`);

		if (!response.ok) {
			openDefaultFailureToast();

			return [];
		}

		const responseJSON = await response.json();

		return responseJSON;
	}

	const validate = ({i18nFilterLabels}: any) => {
		if (Liferay.FeatureFlags['LPD-10754']) {
			if (!i18nFilterLabels || !Object.values(i18nFilterLabels).length) {
				setLabelValidationError(true);

				return false;
			}
			else {
				let isI18nFilterLabelValid = true;

				Object.values(i18nFilterLabels).forEach((value) => {
					if (!value) {
						isI18nFilterLabelValid = false;
					}
				});

				if (!isI18nFilterLabelValid) {
					setLabelValidationError(true);

					return false;
				}
			}

			setLabelValidationError(false);
		}

		if (!selectedField) {
			return false;
		}

		if (selectedField && !filter) {
			if (inUseFields.includes(selectedField.name)) {
				setFieldInUseValidationError(true);

				return true;
			}
			else {
				setFieldInUseValidationError(false);
			}
		}

		if (Liferay.FeatureFlags['LPD-10754'] && (!source || !sourceType)) {
			return false;
		}

		return true;
	};

	const saveSelectionFilter = () => {
		setSaveButtonDisabled(true);

		const success = validate({i18nFilterLabels});

		if (success) {
			let formData: any = {
				fieldName: selectedField?.name,
				label_i18n: i18nFilterLabels,
			};

			if (Liferay.FeatureFlags['LPD-10754']) {
				if (sourceType === ESelectionFilterSourceType.API_HEADLESS) {
					formData = {
						...formData,
						restApplication: selectedRESTApplication,
						restEndpoint: selectedRESTEndpoint,
						restSchema: selectedRESTSchema,
						source: source as string,
						sourceType,
					};
				}

				if (sourceType === ESelectionFilterSourceType.PICKLIST) {
					formData = {
						...formData,
						source: (source as IPickList)?.externalReferenceCode,
						sourceType,
					};
				}
			}
			else {
				formData = {
					...formData,
					listTypeDefinitionERC: (source as IPickList)
						?.externalReferenceCode,
				};
			}

			formData = {
				...formData,
				include: includeMode === 'include',
				multiple,
				preselectedValues: JSON.stringify(
					preselectedValues.map(
						(item: any) => item.externalReferenceCode
					)
				),
			};

			onSave(formData);
		}
		else {
			setSaveButtonDisabled(false);
		}
	};

	useEffect(() => {
		if ((filter as ISelectionFilter)?.sourceType === ESelectionFilterSourceType.PICKLIST) {
			getAllPicklists().then((items) => {
				setPicklists(items);

				const picklist = items.find((item) =>
					Liferay.FeatureFlags['LPD-10754']
						? String(item.externalReferenceCode) ===
						(filter as any)?.source
						: String(item.externalReferenceCode) ===
						(filter as any)?.listTypeDefinitionERC
				);

				if (picklist) {
					setSource(picklist);
				}
			});
		}

		if (filter) {
			const selectionFilter = filter as ISelectionFilter;
			setSourceType(selectionFilter.sourceType);
		}
	}, [filter]);

	useEffect(() => {
		if (source && filter) {
			let validSavedPreselectedValues: any[] = [];
		
			if (sourceType === ESelectionFilterSourceType.API_HEADLESS) {
				console.log((filter as ISelectionFilter).preselectedValues)
				//validSavedPreselectedValues = (filter as ISelectionFilter).preselectedValues;
			}

			if (sourceType === ESelectionFilterSourceType.PICKLIST) {
				validSavedPreselectedValues = (source as IPickList).listTypeEntries.filter(
					(item) =>
						JSON.parse(
							(filter as ISelectionFilter).preselectedValues || '[]'
						).includes(item.externalReferenceCode)
				);
			}

			setPreselectedValues(validSavedPreselectedValues);

			setIncludeMode(
				validSavedPreselectedValues?.length
					? filter && (filter as ISelectionFilter).include
						? 'include'
						: 'exclude'
					: 'include'
			);
		}
	}, [filter, source]);

	useEffect(() => {
		if (sourceType === ESelectionFilterSourceType.PICKLIST) {
			setFilteredSourceItems(
				!source
					? []
					: (source as IPickList).listTypeEntries
							.filter((item) =>
								fuzzy.match(preselectedValueInput, item.name)
							)
							.map((item) => ({
								label: item.name,
								value: String(item.externalReferenceCode),
							}))
			);
		}
	}, [preselectedValueInput, source, sourceType]);

	useEffect(() => {
		if (source && sourceType === ESelectionFilterSourceType.API_HEADLESS) {
			getAPIValues(source as string).then((apiValues) => {
				setFilteredSourceItems(
					!apiValues.items.length
						? []
						: apiValues.items
								.filter((item: any) =>
									fuzzy.match(
										preselectedValueInput,
										item[selectedItemLabel]
									)
								)
								.map((item: any) => {
									return {
										label: item[selectedItemLabel],
										value: item[selectedItemKey],
									};
								})
				);
			});
		}
	}, [
		preselectedValueInput,
		selectedItemKey,
		selectedItemLabel,
		source,
		sourceType,
	]);

	if (!Liferay.FeatureFlags['LPD-10754'] && sourceType === ESelectionFilterSourceType.API_HEADLESS && !picklists.length) {
		return (
			<ClayAlert displayType="info" title="Info">
				{Liferay.Language.get(
					'no-filter-sources-are-available.-create-a-picklist-or-a-vocabulary-for-this-type-of-filter'
				)}
			</ClayAlert>
		);
	}

	return (
		<>
			<ClayModal.Body>
				<FilterModalConfiguration
					fieldInUseValidationError={fieldInUseValidationError}
					fieldNames={fieldNames}
					fields={fields}
					filter={filter}
					labelValidationError={labelValidationError}
					namespace={namespace}
					onChange={({i18nFilterLabels, selectedField}) => {
						setI18nFilterLabels(i18nFilterLabels);
						setSelectedField(selectedField);

						validate({i18nFilterLabels, selectedField});
					}}
				/>

				{!fieldInUseValidationError && (
					<>
						{Liferay.FeatureFlags['LPD-10754'] && (
							<>
								<ClayLayout.SheetSection className="mb-4">
									<h3 className="sheet-subtitle">
										{Liferay.Language.get('filter-source')}
									</h3>

									<ClayForm.Text>
										{Liferay.Language.get(
											'the-filter-source-determines-the-values-to-be-offered-in-this-filter-to-the-user'
										)}
									</ClayForm.Text>
								</ClayLayout.SheetSection>

								<ClayForm.Group>
									<label htmlFor={sourceOptionFormElementId}>
										{Liferay.Language.get('source')}

										<RequiredMark />
									</label>

									<ClaySelectWithOption
										aria-label={Liferay.Language.get(
											'choose-an-option'
										)}
										name={sourceOptionFormElementId}
										onChange={(event) => {
											const newSourceType = event.target
												.value as ESelectionFilterSourceType;
												
											setSourceType(newSourceType);

											setSource(undefined);
											setPreselectedValueInput('');
											setPreselectedValues([]);
										}}
										options={[
											{
												disabled: true,
												label: Liferay.Language.get(
													'choose-an-option'
												),
												value: '',
											},
											{
												disabled: false,
												label: Liferay.Language.get(
													'api-rest-application'
												),
												value:
													ESelectionFilterSourceType.API_HEADLESS,
											},
											{
												disabled: false,
												label: Liferay.Language.get(
													'object-picklist'
												),
												value:
													ESelectionFilterSourceType.PICKLIST,
											},
										]}
										required
										title={Liferay.Language.get('source')}
										value={sourceType || ''}
									/>
								</ClayForm.Group>
							</>
						)}

						{Liferay.FeatureFlags['LPD-10754'] ? (
							<>
								{sourceType &&
									sourceType ===
										ESelectionFilterSourceType.PICKLIST && (
										<ObjectPicklist
											filter={filter}
											namespace={namespace}
											onChange={(item: IPickList) => {
												setSource(item);
											}}
										/>
									)}

								{sourceType &&
									sourceType ===
										ESelectionFilterSourceType.API_HEADLESS && (
										<ApiRestApplication
											onChange={({
												selectedItemKey,
												selectedItemLabel,
												selectedRESTApplication,
												selectedRESTEndpoint,
												selectedRESTSchema,
											}) => {
												if (
													selectedRESTApplication &&
													selectedRESTEndpoint
												) {
													setSource(
														`${selectedRESTApplication}${selectedRESTEndpoint}`
													);
												}

												setSelectedRESTApplication(
													selectedRESTApplication
												);
												setSelectedRESTEndpoint(
													selectedRESTEndpoint
												);
												setSelectedRESTSchema(
													selectedRESTSchema
												);
												setSelectedItemKey(
													selectedItemKey
												);
												setSelectedItemLabel(
													selectedItemLabel
												);
											}}
											restApplications={restApplications}
										/>
									)}

								{source && (
									<>
										<ClayLayout.SheetSection className="mb-4">
											<h3 className="sheet-subtitle">
												{Liferay.Language.get(
													'filter-options'
												)}
											</h3>
										</ClayLayout.SheetSection>
										<ClayForm.Group
											className={classNames({
												'has-error': !isValidSingleMode,
											})}
										>
											<label
												htmlFor={
													preselectedValuesFormElementId
												}
											>
												{Liferay.Language.get(
													'preselected-values'
												)}

												<span
													className="label-icon lfr-portal-tooltip ml-2"
													title={Liferay.Language.get(
														'choose-values-to-preselect-for-your-filters-source-option'
													)}
												>
													<ClayIcon symbol="question-circle-full" />
												</span>
											</label>

											<CheckboxMultiSelect
												allowsCustomLabel={false}
												aria-label={Liferay.Language.get(
													'preselected-values'
												)}
												inputName={
													preselectedValuesFormElementId
												}
												items={preselectedValues.map(
													(item) => {
														let valueItem;

														if (
															sourceType ===
															ESelectionFilterSourceType.PICKLIST
														) {
															valueItem = {
																label:
																	item.name,
																value: String(
																	item.externalReferenceCode
																),
															};
														}

														if (
															sourceType ===
															ESelectionFilterSourceType.API_HEADLESS
														) {
															valueItem = {
																label:
																	item[
																		selectedItemLabel
																	],
																value:
																	item[
																		selectedItemKey
																	],
															};
														}

														return valueItem as TItem;
													}
												)}
												loadingState={4}
												onChange={
													setPreselectedValueInput
												}
												onItemsChange={(
													selectedItems: any
												) => {
													let preselectedValues;

													if (
														sourceType ===
														ESelectionFilterSourceType.API_HEADLESS
													) {
														preselectedValues = selectedItems.map(
															({value}: any) => {
																return filteredSourceItems.find(
																	(item) =>
																		String(
																			item.value
																		) ===
																		String(
																			value
																		)
																);
															}
														);
													}

													if (
														sourceType ===
														ESelectionFilterSourceType.PICKLIST
													) {
														preselectedValues = selectedItems.map(
															({value}: any) => {
																return (source as IPickList).listTypeEntries.find(
																	(item) =>
																		String(
																			item.externalReferenceCode
																		) ===
																		String(
																			value
																		)
																);
															}
														);
													}

													setPreselectedValues(
														preselectedValues
													);

													setIncludeMode(
														preselectedValues.length
															? filter &&
															  (filter as ISelectionFilter)
																	.include
																? 'include'
																: 'exclude'
															: 'include'
													);
												}}
												placeholder={Liferay.Language.get(
													'select-a-default-value-for-your-filter'
												)}
												sourceItems={
													filteredSourceItems
												}
												value={preselectedValueInput}
											/>

											{!isValidSingleMode && (
												<ClayForm.FeedbackGroup>
													<ClayForm.FeedbackItem>
														<ClayForm.FeedbackIndicator symbol="exclamation-full" />

														{Liferay.Language.get(
															'only-one-value-is-allowed-in-single-selection-mode'
														)}
													</ClayForm.FeedbackItem>
												</ClayForm.FeedbackGroup>
											)}
										</ClayForm.Group>

										<ClayLayout.Row justify="start">
											<ClayLayout.Col size={6}>
												<ClayForm.Group>
													<label
														htmlFor={
															multipleFormElementId
														}
													>
														{Liferay.Language.get(
															'selection'
														)}

														<span
															className="label-icon lfr-portal-tooltip ml-2"
															title={Liferay.Language.get(
																'determines-how-many-preselected-values-for-the-filter-can-be-added'
															)}
														>
															<ClayIcon symbol="question-circle-full" />
														</span>
													</label>

													<ClayRadioGroup
														name={
															multipleFormElementId
														}
														onChange={(
															newVal: any
														) => {
															const newMultiple =
																newVal ===
																'true';
															setMultiple(
																newMultiple
															);
														}}
														value={
															multiple
																? 'true'
																: 'false'
														}
													>
														<ClayRadio
															label={Liferay.Language.get(
																'multiple'
															)}
															value="true"
														/>

														<ClayRadio
															label={Liferay.Language.get(
																'single'
															)}
															value="false"
														/>
													</ClayRadioGroup>
												</ClayForm.Group>
											</ClayLayout.Col>

											{preselectedValues?.length > 0 && (
												<ClayLayout.Col size={6}>
													<ClayForm.Group>
														<label
															htmlFor={
																includeModeFormElementId
															}
														>
															{Liferay.Language.get(
																'filter-mode'
															)}

															<span
																className="label-icon lfr-portal-tooltip ml-2"
																title={Liferay.Language.get(
																	'include-returns-only-the-selected-values.-exclude-returns-all-except-the-selected-ones'
																)}
															>
																<ClayIcon symbol="question-circle-full" />
															</span>
														</label>

														<ClayRadioGroup
															name={
																includeModeFormElementId
															}
															onChange={(
																val: any
															) => {
																setIncludeMode(
																	val
																);
															}}
															value={includeMode}
														>
															<ClayRadio
																label={Liferay.Language.get(
																	'include'
																)}
																value="include"
															/>

															<ClayRadio
																label={Liferay.Language.get(
																	'exclude'
																)}
																value="exclude"
															/>
														</ClayRadioGroup>
													</ClayForm.Group>
												</ClayLayout.Col>
											)}
										</ClayLayout.Row>
									</>
								)}
							</>
						) : (
							<>
								<ClayForm.Group>
									<label htmlFor={sourceOptionFormElementId}>
										{Liferay.Language.get('source-options')}

										<span
											className="label-icon lfr-portal-tooltip ml-2"
											title={Liferay.Language.get(
												'choose-a-picklist-to-associate-with-this-filter'
											)}
										>
											<ClayIcon symbol="question-circle-full" />
										</span>
									</label>

									<ClaySelectWithOption
										aria-label={Liferay.Language.get(
											'source-options'
										)}
										name={sourceOptionFormElementId}
										onChange={(event) => {
											const picklist = picklists.find(
												(item) =>
													String(
														item.externalReferenceCode
													) === event.target.value
											);

											setSource(picklist);

											setPreselectedValues([]);
										}}
										options={[
											{
												disabled: true,
												label: Liferay.Language.get(
													'select'
												),
												value: '',
											},
											...picklists.map((item) => ({
												label: item.name,
												value:
													item.externalReferenceCode,
											})),
										]}
										title={Liferay.Language.get(
											'source-options'
										)}
										value={
											(source as IPickList)
												?.externalReferenceCode || ''
										}
									/>
								</ClayForm.Group>

								{source && (
									<>
										<ClayForm.Group>
											<label
												htmlFor={multipleFormElementId}
											>
												{Liferay.Language.get(
													'selection'
												)}

												<span
													className="label-icon lfr-portal-tooltip ml-2"
													title={Liferay.Language.get(
														'determines-how-many-preselected-values-for-the-filter-can-be-added'
													)}
												>
													<ClayIcon symbol="question-circle-full" />
												</span>
											</label>

											<ClayRadioGroup
												name={multipleFormElementId}
												onChange={(newVal: any) => {
													setMultiple(
														newVal === 'true'
													);
												}}
												value={
													multiple ? 'true' : 'false'
												}
											>
												<ClayRadio
													label={Liferay.Language.get(
														'multiple'
													)}
													value="true"
												/>

												<ClayRadio
													label={Liferay.Language.get(
														'single'
													)}
													value="false"
												/>
											</ClayRadioGroup>
										</ClayForm.Group>
										<ClayForm.Group
											className={classNames({
												'has-error': !isValidSingleMode,
											})}
										>
											<label
												htmlFor={
													preselectedValuesFormElementId
												}
											>
												{Liferay.Language.get(
													'preselected-values'
												)}

												<span
													className="label-icon lfr-portal-tooltip ml-2"
													title={Liferay.Language.get(
														'choose-values-to-preselect-for-your-filters-source-option'
													)}
												>
													<ClayIcon symbol="question-circle-full" />
												</span>
											</label>

											<CheckboxMultiSelect
												allowsCustomLabel={false}
												aria-label={Liferay.Language.get(
													'preselected-values'
												)}
												inputName={
													preselectedValuesFormElementId
												}
												items={preselectedValues.map(
													(item) => ({
														label: item.name,
														value: String(
															item.externalReferenceCode
														),
													})
												)}
												loadingState={4}
												onChange={
													setPreselectedValueInput
												}
												onItemsChange={(
													selectedItems: any
												) => {
													const preselectedValues = selectedItems.map(
														({value}: any) => {
															return (source as IPickList).listTypeEntries.find(
																(item) =>
																	String(
																		item.externalReferenceCode
																	) ===
																	String(
																		value
																	)
															);
														}
													);

													setPreselectedValues(
														preselectedValues
													);

													setIncludeMode(
														preselectedValues.length
															? filter &&
															  (filter as ISelectionFilter)
																	.include
																? 'include'
																: 'exclude'
															: 'include'
													);
												}}
												placeholder={Liferay.Language.get(
													'select-a-default-value-for-your-filter'
												)}
												sourceItems={
													filteredSourceItems
												}
												value={preselectedValueInput}
											/>

											{!isValidSingleMode && (
												<ClayForm.FeedbackGroup>
													<ClayForm.FeedbackItem>
														<ClayForm.FeedbackIndicator symbol="exclamation-full" />

														{Liferay.Language.get(
															'only-one-value-is-allowed-in-single-selection-mode'
														)}
													</ClayForm.FeedbackItem>
												</ClayForm.FeedbackGroup>
											)}
										</ClayForm.Group>

										{preselectedValues?.length > 0 && (
											<ClayForm.Group>
												<label
													htmlFor={
														includeModeFormElementId
													}
												>
													{Liferay.Language.get(
														'filter-mode'
													)}

													<span
														className="label-icon lfr-portal-tooltip ml-2"
														title={Liferay.Language.get(
															'include-returns-only-the-selected-values.-exclude-returns-all-except-the-selected-ones'
														)}
													>
														<ClayIcon symbol="question-circle-full" />
													</span>
												</label>

												<ClayRadioGroup
													name={
														includeModeFormElementId
													}
													onChange={(val: any) =>
														setIncludeMode(val)
													}
													value={includeMode}
												>
													<ClayRadio
														label={Liferay.Language.get(
															'include'
														)}
														value="include"
													/>

													<ClayRadio
														label={Liferay.Language.get(
															'exclude'
														)}
														value="exclude"
													/>
												</ClayRadioGroup>
											</ClayForm.Group>
										)}
									</>
								)}
							</>
						)}
					</>
				)}
			</ClayModal.Body>

			<FilterModalFooter
				closeModal={closeModal}
				onSave={saveSelectionFilter}
				saveButtonDisabled={saveButtonDisabled}
			/>
		</>
	);
}

export default {
	Body,
	Header,
};
