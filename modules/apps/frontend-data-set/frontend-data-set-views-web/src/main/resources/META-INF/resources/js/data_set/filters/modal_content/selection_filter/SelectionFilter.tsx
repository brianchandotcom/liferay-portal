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
import classNames from 'classnames';
import fuzzy from 'fuzzy';
import React, {useState} from 'react';

import CheckboxMultiSelect from '../../../../components/CheckboxMultiSelect';
import RequiredMark from '../../../../components/RequiredMark';
import {ESelectionFilterSourceType, IPickList} from '../../../../utils/types';
import ObjectPicklist from './source_type/ObjectPicklist';

function Header() {
	return <>{Liferay.Language.get('new-selection-filter')}</>;
}

interface IBodyProps {
	includeMode: string;
	multiple: boolean;
	namespace: string;
	onIncludeModeChange: (val: string) => void;
	onMultipleChange: (val: boolean) => void;
	onPreselectedValuesChange: (val: any[]) => void;
	onSelectedPicklistChange: (val?: IPickList) => void;
	onSourceChange: (val: ESelectionFilterSourceType | undefined) => void;
	picklists: IPickList[];
	preselectedValues?: any[];
	selectedPicklist?: IPickList;
	sourceType: ESelectionFilterSourceType | undefined;
}

function Body({
	includeMode,
	multiple,
	namespace,
	onIncludeModeChange,
	onMultipleChange,
	onPreselectedValuesChange,
	onSelectedPicklistChange,
	onSourceChange,
	picklists,
	preselectedValues = [],
	selectedPicklist,
	sourceType,
}: IBodyProps) {
	const [preselectedValueInput, setPreselectedValueInput] = useState('');

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
								onSourceChange(
									event.target
										.value as ESelectionFilterSourceType
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
									value: ESelectionFilterSourceType.PICKLIST,
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
					{sourceType === ESelectionFilterSourceType.PICKLIST && (
						<ObjectPicklist
							includeMode={includeMode}
							multiple={multiple}
							namespace={namespace}
							onIncludeModeChange={onIncludeModeChange}
							onMultipleChange={onMultipleChange}
							onPreselectedValuesChange={
								onPreselectedValuesChange
							}
							onSelectedPicklistChange={onSelectedPicklistChange}
							picklists={picklists}
							preselectedValues={preselectedValues}
							selectedPicklist={selectedPicklist}
						/>
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
							aria-label={Liferay.Language.get('source-options')}
							name={sourceOptionFormElementId}
							onChange={(event) => {
								onSelectedPicklistChange(
									picklists.find(
										(item) =>
											String(
												item.externalReferenceCode
											) === event.target.value
									)
								);

								onPreselectedValuesChange([]);
							}}
							options={[
								{
									disabled: true,
									label: Liferay.Language.get('select'),
									value: '',
								},
								...picklists.map((item) => ({
									label: item.name,
									value: item.externalReferenceCode,
								})),
							]}
							title={Liferay.Language.get('source-options')}
							value={
								selectedPicklist?.externalReferenceCode || ''
							}
						/>
					</ClayForm.Group>

					{selectedPicklist && (
						<>
							<ClayForm.Group>
								<label htmlFor={multipleFormElementId}>
									{Liferay.Language.get('selection')}

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
										onMultipleChange(newVal === 'true');
									}}
									value={multiple ? 'true' : 'false'}
								>
									<ClayRadio
										label={Liferay.Language.get('multiple')}
										value="true"
									/>

									<ClayRadio
										label={Liferay.Language.get('single')}
										value="false"
									/>
								</ClayRadioGroup>
							</ClayForm.Group>
							<ClayForm.Group
								className={classNames({
									'has-error': !isValidSingleMode,
								})}
							>
								<label htmlFor={preselectedValuesFormElementId}>
									{Liferay.Language.get('preselected-values')}

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
									inputName={preselectedValuesFormElementId}
									items={preselectedValues.map((item) => ({
										label: item.name,
										value: String(
											item.externalReferenceCode
										),
									}))}
									loadingState={4}
									onChange={setPreselectedValueInput}
									onItemsChange={(selectedItems: any) =>
										onPreselectedValuesChange(
											selectedItems.map(
												({value}: any) => {
													return selectedPicklist.listTypeEntries.find(
														(item) =>
															String(
																item.externalReferenceCode
															) === String(value)
													);
												}
											)
										)
									}
									placeholder={Liferay.Language.get(
										'select-a-default-value-for-your-filter'
									)}
									sourceItems={filteredSourceItems}
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
									<label htmlFor={includeModeFormElementId}>
										{Liferay.Language.get('filter-mode')}

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
										name={includeModeFormElementId}
										onChange={(val: any) =>
											onIncludeModeChange(val)
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
	);
}

export default {
	Body,
	Header,
};
