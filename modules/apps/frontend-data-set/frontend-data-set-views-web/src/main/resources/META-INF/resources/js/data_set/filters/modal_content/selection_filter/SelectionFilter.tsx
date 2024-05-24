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
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayModal from '@clayui/modal';
import classNames from 'classnames';
import fuzzy from 'fuzzy';
import React, {useEffect, useState} from 'react';

import CheckboxMultiSelect from '../../../../components/CheckboxMultiSelect';
import FilterModalConfiguration from '../../../../components/FilterModalConfiguration';
import FilterModalFooter from '../../../../components/FilterModalFooter';
import RequiredMark from '../../../../components/RequiredMark';
import getAllPicklists from '../../../../utils/getAllPicklists';
import {
	EFilterType,
	ESelectionFilterSourceType,
	IField,
	IFilter,
	IPickList,
	ISelectionFilter,
} from '../../../../utils/types';
import ObjectPicklist from './source_type/ObjectPicklist';

function Header() {
	return <>{Liferay.Language.get('new-selection-filter')}</>;
}

interface IBodyProps {
	closeModal: Function;
	fieldNames?: string[];
	fields: IField[];
	filter?: IFilter;
	handleSave: Function;
	namespace: string;
}

function Body({
	closeModal,
	fieldNames,
	fields,
	filter,
	handleSave,
	namespace,
}: IBodyProps) {
	const [fieldInUseValidationError, setFieldInUseValidationError] = useState<
		boolean
	>(false);
	const [preselectedValueInput, setPreselectedValueInput] = useState('');
	const [saveButtonDisabled, setSaveButtonDisabled] = useState<boolean>(
		filter ? false : true
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
	const [selectedPicklist, setSelectedPicklist] = useState<IPickList>();
	const [sourceType, setSourceType] = useState<
		ESelectionFilterSourceType | undefined
	>();
	const fdsFilterLabelTranslations = filter?.label_i18n ?? {};
	const [i18nFilterLabels, setI18nFilterLabels] = useState(
		fdsFilterLabelTranslations
	);

	const includeModeFormElementId = `${namespace}IncludeMode`;
	const multipleFormElementId = `${namespace}Multiple`;
	const sourceOptionFormElementId = `${namespace}SourceOption`;
	const preselectedValuesFormElementId = `${namespace}PreselectedValues`;

	const isValidSingleMode =
		multiple || (!multiple && !(preselectedValues.length > 1));

	const filteredSourceItems = !selectedPicklist
		? []
		: selectedPicklist.listTypeEntries
				.filter((item) => fuzzy.match(preselectedValueInput, item.name))
				.map((item) => ({
					label: item.name,
					value: String(item.externalReferenceCode),
				}));

	const handleFilterSave = () => {
		let body: any = {
			fieldName: selectedField?.name,
			label_i18n: i18nFilterLabels,
		};

		if (Liferay.FeatureFlags['LPD-10754']) {
			body = {
				...body,
				source: selectedPicklist?.externalReferenceCode,
				sourceType,
			};
		}
		else {
			body = {
				...body,
				listTypeDefinitionERC: selectedPicklist?.externalReferenceCode,
			};
		}

		body = {
			...body,
			include: includeMode === 'include',
			multiple,
			preselectedValues: JSON.stringify(
				preselectedValues.map((item: any) => item.externalReferenceCode)
			),
		};

		handleSave(body);
	};

	const isFormInvalid = ({
		i18nFilterLabels,
		selectedField,
		selectedPicklist,
		sourceType,
	}: {
		i18nFilterLabels: Partial<Liferay.Language.FullyLocalizedValue<string>>;
		selectedField: IField | undefined;
		selectedPicklist?: IPickList;
		sourceType?: string;
	}) => {
		if (!selectedField) {
			return true;
		}

		if (selectedField && !filter) {
			if (inUseFields.includes(selectedField.name)) {
				return true;
			}
		}

		if (!i18nFilterLabels || !Object.values(i18nFilterLabels).length) {
			return true;
		}
		else {
			let isI18nFilterLabelInvalid = false;

			Object.values(i18nFilterLabels).forEach((value) => {
				if (!value) {
					isI18nFilterLabelInvalid = true;
				}
			});

			if (isI18nFilterLabelInvalid) {
				return true;
			}
		}

		if (
			Liferay.FeatureFlags['LPD-10754'] &&
			(!selectedPicklist || !sourceType)
		) {
			return true;
		}

		return false;
	};

	useEffect(() => {
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
				setSelectedPicklist(picklist);
			}
		});

		if (filter?.filterType === EFilterType.SELECTION) {
			const selectionFilter = filter as ISelectionFilter;
			setSourceType(selectionFilter.sourceType);
		}
	}, [filter]);

	useEffect(() => {
		if (selectedPicklist && filter) {
			const validSavedPreselectedValues = selectedPicklist.listTypeEntries.filter(
				(item) =>
					JSON.parse(
						(filter as ISelectionFilter).preselectedValues || '[]'
					).includes(item.externalReferenceCode)
			);

			setPreselectedValues(validSavedPreselectedValues);

			setIncludeMode(
				validSavedPreselectedValues?.length
					? filter && (filter as ISelectionFilter).include
						? 'include'
						: 'exclude'
					: 'include'
			);
		}
	}, [filter, selectedPicklist]);

	if (!Liferay.FeatureFlags['LPD-10754'] && !picklists.length) {
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
					fieldNames={fieldNames}
					fields={fields}
					filter={filter}
					namespace={namespace}
					onChange={({i18nFilterLabels, selectedField}) => {
						setI18nFilterLabels(i18nFilterLabels);
						setSelectedField(selectedField);
						setFieldInUseValidationError(fieldInUseValidationError);

						setSaveButtonDisabled(
							isFormInvalid({
								i18nFilterLabels,
								selectedField,
								selectedPicklist,
								sourceType,
							})
						);
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

											setSaveButtonDisabled(
												isFormInvalid({
													i18nFilterLabels,
													selectedField,
													selectedPicklist,
													sourceType: newSourceType,
												})
											);
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
								{sourceType ===
									ESelectionFilterSourceType.PICKLIST && (
									<ObjectPicklist
										filter={filter}
										namespace={namespace}
										onChange={(item: IPickList) => {
											setSelectedPicklist(item);

											setSaveButtonDisabled(
												isFormInvalid({
													i18nFilterLabels,
													selectedField,
													selectedPicklist: item,
													sourceType,
												})
											);
										}}
									/>
								)}

								{selectedPicklist && (
									<>
										<ClayLayout.SheetSection className="mb-4">
											<h3 className="sheet-subtitle">
												{Liferay.Language.get(
													' filter-options'
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
															return selectedPicklist.listTypeEntries.find(
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

											setSelectedPicklist(picklist);

											setSaveButtonDisabled(
												isFormInvalid({
													i18nFilterLabels,
													selectedField,
													selectedPicklist: picklist,
													sourceType,
												})
											);

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
											selectedPicklist?.externalReferenceCode ||
											''
										}
									/>
								</ClayForm.Group>

								{selectedPicklist && (
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
															return selectedPicklist.listTypeEntries.find(
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
				handleSave={handleFilterSave}
				saveButtonDisabled={saveButtonDisabled}
			/>
		</>
	);
}

export default {
	Body,
	Header,
};
