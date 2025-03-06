/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayBreadcrumb from '@clayui/breadcrumb';
import {Option, Picker} from '@clayui/core';
import ClayForm, {ClayRadio, ClayRadioGroup, ClayToggle} from '@clayui/form';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import ClayTabs from '@clayui/tabs';
import {InputLocalized, useId} from 'frontend-js-components-web';
import React, {useMemo, useState} from 'react';

import {useSelector, useStateDispatch} from '../contexts/StateContext';
import selectPublishedFields from '../selectors/selectPublishedFields';
import selectStructureError from '../selectors/selectStructureError';
import selectStructureField from '../selectors/selectStructureField';
import selectStructureLocalizedLabel from '../selectors/selectStructureLocalizedLabel';
import selectStructureStatus from '../selectors/selectStructureStatus';
import {Field} from '../utils/field';
import getFieldComponents from '../utils/getFieldComponents';
import {isFieldTextSearchable} from '../utils/isFieldTextSearchable';
import ERCInput from './ERCInput';
import Input from './Input';

export default function StructureFieldSettings({
	fieldName,
}: {
	fieldName: Field['name'];
}) {
	const dispatch = useStateDispatch();
	const error = useSelector(selectStructureError);
	const field = useSelector(selectStructureField(fieldName));
	const structureLabel = useSelector(selectStructureLocalizedLabel);

	return (
		<ClayLayout.ContainerFluid size="md" view>
			{error ? (
				<ClayAlert
					displayType="danger"
					role={null}
					title={Liferay.Language.get('error')}
				>
					{error}
				</ClayAlert>
			) : null}

			<ClayBreadcrumb
				className="mb-3"
				items={[
					{
						label: structureLabel,
						onClick: () => {
							dispatch({
								selection: [],
								type: 'set-selection',
							});
						},
					},
					{
						label: field!.label[
							Liferay.ThemeDisplay.getDefaultLanguageId()
						]!,
					},
				]}
			/>

			<ClayTabs>
				<ClayTabs.List>
					<ClayTabs.Item>
						{Liferay.Language.get('general')}
					</ClayTabs.Item>

					<ClayTabs.Item>
						{Liferay.Language.get('search')}
					</ClayTabs.Item>
				</ClayTabs.List>

				<ClayTabs.Panels fade>
					<ClayTabs.TabPane>
						<GeneralTab field={field!} />
					</ClayTabs.TabPane>

					<ClayTabs.TabPane>
						<SearchTab field={field!} />
					</ClayTabs.TabPane>
				</ClayTabs.Panels>
			</ClayTabs>
		</ClayLayout.ContainerFluid>
	);
}

function GeneralTab({field}: {field: Field}) {
	const dispatch = useStateDispatch();

	const status = useSelector(selectStructureStatus);
	const publishedFields = useSelector(selectPublishedFields);

	const isPublished =
		status === 'published' && publishedFields.has(field.name);

	const [label, setLabel] = useState<Liferay.Language.LocalizedValue<string>>(
		field.label
	);

	const {FirstSectionComponent, SecondSectionComponent} = getFieldComponents(
		field.type
	);

	const labelInputId = useId();

	return (
		<>
			<div className="pb-2">
				<p className="font-weight-semi-bold mb-0 text-3">
					{Liferay.Language.get('field-type')}
				</p>

				<ClayLabel displayType="info">{field.type}</ClayLabel>
			</div>

			<div className="mt-4 pb-2">
				<Input
					disabled={isPublished}
					label={Liferay.Language.get('field-name')}
					onValueChange={(value) => {
						dispatch({
							name: field.name,
							newName: value,
							type: 'update-field',
						});
					}}
					required
					value={field.name}
				/>

				<InputLocalized
					id={labelInputId}
					label={Liferay.Language.get('label')}
					onBlur={() => {
						dispatch({
							label,
							name: field.name,
							type: 'update-field',
						});
					}}
					onChange={(label) => setLabel(label)}
					required
					translations={
						label as Liferay.Language.LocalizedValue<string>
					}
				/>

				<FirstSectionComponent field={field} />
			</div>

			<div className="mt-4 pb-2">
				<ClayForm.Group className="mb-3">
					<ClayToggle
						disabled={isPublished}
						label={Liferay.Language.get('mandatory')}
						onToggle={(value) => {
							dispatch({
								name: field.name,
								required: value,
								type: 'update-field',
							});
						}}
						toggled={field.required}
					/>
				</ClayForm.Group>

				<ClayForm.Group className="mb-3">
					<ClayToggle
						disabled={isPublished}
						label={Liferay.Language.get('localizable')}
						onToggle={(value) => {
							dispatch({
								localized: value,
								name: field.name,
								type: 'update-field',
							});
						}}
						toggled={field.localized}
					/>
				</ClayForm.Group>

				<SecondSectionComponent field={field} />
			</div>

			<div className="mt-4">
				<ERCInput
					onValueChange={(value) => {
						dispatch({
							erc: value,
							name: field.name,
							type: 'update-field',
						});
					}}
					value={field.erc}
				/>
			</div>
		</>
	);
}

function SearchTab({field}: {field: Field}) {
	const dispatch = useStateDispatch();

	const languageLabels = useMemo(
		() =>
			Object.entries(Liferay.Language.available).map(([key, value]) => {
				return {label: value, value: key};
			}),
		[]
	);

	return (
		<>
			<ClayForm.Group>
				<ClayToggle
					label={Liferay.Language.get('searchable')}
					onToggle={(value) => {
						dispatch({
							indexableConfig: {
								indexed: value,
								indexedAsKeyword: false,
								indexedLanguageId:
									Liferay.ThemeDisplay.getDefaultLanguageId(),
							},
							name: field.name,
							type: 'update-field',
						});
					}}
					toggled={field.indexableConfig.indexed}
				/>
			</ClayForm.Group>

			{field.indexableConfig.indexed && isFieldTextSearchable(field) ? (
				<>
					<p className="text-secondary">
						{Liferay.Language.get(
							'specify-whether-to-index-the-field-for-search'
						)}
					</p>
					<ClayForm.Group>
						<ClayRadioGroup
							defaultValue={
								field.indexableConfig.indexedAsKeyword
									? 'keyword'
									: 'text'
							}
							inline
							onChange={(value: React.ReactText) => {
								dispatch({
									indexableConfig: {
										indexed: true,
										indexedAsKeyword: value === 'keyword',
										indexedLanguageId:
											value === 'keyword'
												? undefined
												: Liferay.ThemeDisplay.getDefaultLanguageId(),
									},
									name: field.name,
									type: 'update-field',
								});
							}}
						>
							<ClayRadio
								label={Liferay.Language.get('keyword')}
								value="keyword"
							/>

							<ClayRadio
								label={Liferay.Language.get('text')}
								value="text"
							/>
						</ClayRadioGroup>
					</ClayForm.Group>

					{!field.indexableConfig.indexedAsKeyword ? (
						<Picker
							aria-label={Liferay.Language.get('language')}
							defaultSelectedKey={Liferay.ThemeDisplay.getDefaultLanguageId()}
							items={languageLabels}
							onSelectionChange={(
								indexedLanguageId: React.Key
							) => {
								dispatch({
									indexableConfig: {
										indexed: true,
										indexedAsKeyword: false,
										indexedLanguageId:
											indexedLanguageId as Liferay.Language.Locale,
									},
									name: field.name,
									type: 'update-field',
								});
							}}
							selectedKey={
								field.indexableConfig.indexedLanguageId
							}
						>
							{(item) => (
								<Option key={item.value}>{item.label}</Option>
							)}
						</Picker>
					) : null}
				</>
			) : null}
		</>
	);
}
