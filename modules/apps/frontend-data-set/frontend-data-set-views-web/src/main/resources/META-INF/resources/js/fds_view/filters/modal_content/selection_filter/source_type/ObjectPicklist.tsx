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

import CheckboxMultiSelect from '../../../../../components/CheckboxMultiSelect';
import RequiredMark from '../../../../../components/RequiredMark';
import {IPickList} from '../../../../../utils/types';

interface IObjectPicklistProps {
	includeMode: string;
	multiple: boolean;
	namespace: string;
	onIncludeModeChange: (val: string) => void;
	onMultipleChange: (val: boolean) => void;
	onPreselectedValuesChange: (val: any[]) => void;
	onSelectedPicklistChange: (val?: IPickList) => void;
	picklists: IPickList[];
	preselectedValues: any[];
	selectedPicklist?: IPickList;
}

function ObjectPicklist({
	includeMode,
	multiple,
	namespace,
	onIncludeModeChange,
	onMultipleChange,
	onPreselectedValuesChange,
	onSelectedPicklistChange,
	picklists,
	preselectedValues,
	selectedPicklist,
}: IObjectPicklistProps) {
	const [preselectedValueInput, setPreselectedValueInput] = useState('');

	const includeModeFormElementId = `${namespace}IncludeMode`;
	const multipleFormElementId = `${namespace}Multiple`;
	const objectPicklistFormElementId = `${namespace}ObjectPicklist`;
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

	return (
		<>
			{!picklists.length ? (
				<ClayAlert displayType="info" title="Info">
					{Liferay.Language.get(
						'no-filter-sources-are-available.-create-a-picklist-or-a-vocabulary-for-this-type-of-filter'
					)}
				</ClayAlert>
			) : (
				<ClayForm.Group>
					<label htmlFor={objectPicklistFormElementId}>
						{Liferay.Language.get('picklist')}

						<RequiredMark />
					</label>

					<ClaySelectWithOption
						aria-label={Liferay.Language.get('picklist')}
						name={objectPicklistFormElementId}
						onChange={(event) => {
							onSelectedPicklistChange(
								picklists.find(
									(item) =>
										String(item.externalReferenceCode) ===
										event.target.value
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
						required
						title={Liferay.Language.get('source-options')}
						value={selectedPicklist?.externalReferenceCode || ''}
					/>
				</ClayForm.Group>
			)}

			{selectedPicklist && (
				<>
					<ClayLayout.SheetSection className="mb-4">
						<h3 className="sheet-subtitle">
							{Liferay.Language.get(' filter-options')}
						</h3>
					</ClayLayout.SheetSection>
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
								value: String(item.externalReferenceCode),
							}))}
							loadingState={4}
							onChange={setPreselectedValueInput}
							onItemsChange={(selectedItems: any) =>
								onPreselectedValuesChange(
									selectedItems.map(({value}: any) => {
										return selectedPicklist.listTypeEntries.find(
											(item) =>
												String(
													item.externalReferenceCode
												) === String(value)
										);
									})
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

					<ClayLayout.Row justify="start">
						<ClayLayout.Col size={6}>
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
						</ClayLayout.Col>

						{preselectedValues?.length > 0 && (
							<ClayLayout.Col size={6}>
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
							</ClayLayout.Col>
						)}
					</ClayLayout.Row>
				</>
			)}
		</>
	);
}

export default ObjectPicklist;
