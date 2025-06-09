/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayBreadcrumb from '@clayui/breadcrumb';
import {Option, Picker} from '@clayui/core';
import ClayForm, {ClayCheckbox, ClayRadio, ClayRadioGroup} from '@clayui/form';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import ClayTabs from '@clayui/tabs';
import {useId} from 'frontend-js-components-web';
import React, {useEffect, useMemo} from 'react';

import {Uuid, useSelector, useStateDispatch} from '../../contexts/StateContext';
import selectPublishedFields from '../../selectors/selectPublishedFields';
import selectStructureField from '../../selectors/selectStructureField';
import selectStructureLocalizedLabel from '../../selectors/selectStructureLocalizedLabel';
import selectStructureUuid from '../../selectors/selectStructureUuid';
import {FIELD_TYPE_LABEL, Field} from '../../utils/field';
import focusInvalidElement from '../../utils/focusInvalidElement';
import getFieldComponents from '../../utils/getFieldComponents';
import {isFieldTextSearchable} from '../../utils/isFieldTextSearchable';
import ERCInput from '../ERCInput';
import Input from '../Input';
import {LocalizedInput} from '../LocalizedInput';

export default function StructureFieldSettings({uuid}: {uuid: Uuid}) {
	const dispatch = useStateDispatch();
	const field = useSelector(selectStructureField(uuid));
	const structureLabel = useSelector(selectStructureLocalizedLabel);
	const structureUuid = useSelector(selectStructureUuid);

	useEffect(() => {
		focusInvalidElement();
	}, []);

	return (
		<ClayLayout.ContainerFluid className="px-4" size="md" view>
			<ClayBreadcrumb
				className="mb-3"
				items={[
					{
						label: structureLabel,
						onClick: () => {
							dispatch({
								selection: [structureUuid],
								type: 'set-selection',
							});
						},
					},
					{
						active: true,
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
					<ClayTabs.TabPane className="px-0">
						<GeneralTab field={field!} />
					</ClayTabs.TabPane>

					<ClayTabs.TabPane className="px-0">
						<SearchTab field={field!} />
					</ClayTabs.TabPane>
				</ClayTabs.Panels>
			</ClayTabs>
		</ClayLayout.ContainerFluid>
	);
}

function GeneralTab({field}: {field: Field}) {
	const dispatch = useStateDispatch();

	const publishedFields = useSelector(selectPublishedFields);

	const isPublished = publishedFields.has(field.uuid);

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

				<ClayLabel displayType="info">
					{FIELD_TYPE_LABEL[field.type]}
				</ClayLabel>
			</div>

			<div className="mt-4 pb-2">
				<LocalizedInput
					id={labelInputId}
					label={Liferay.Language.get('label')}
					onSave={(translations) => {
						dispatch({
							label: translations,
							type: 'update-field',
							uuid: field.uuid,
						});
					}}
					required
					translations={field.label}
				/>

				<Input
					disabled={isPublished}
					label={Liferay.Language.get('field-name')}
					onValueChange={(value) => {
						dispatch({
							name: value,
							type: 'update-field',
							uuid: field.uuid,
						});
					}}
					required
					value={field.name}
				/>

				<FirstSectionComponent field={field} />
			</div>

			<div className="pb-2">
				<ClayForm.Group className="mb-3">
					<ClayCheckbox
						checked={field.required}
						disabled={isPublished}
						label={Liferay.Language.get('mandatory')}
						onChange={(event) => {
							dispatch({
								required: event.target.checked,
								type: 'update-field',
								uuid: field.uuid,
							});
						}}
					/>
				</ClayForm.Group>

				<ClayForm.Group className="mb-3">
					<ClayCheckbox
						checked={field.localized}
						disabled={isPublished}
						label={Liferay.Language.get('localizable')}
						onChange={(event) => {
							dispatch({
								localized: event.target.checked,
								type: 'update-field',
								uuid: field.uuid,
							});
						}}
					/>
				</ClayForm.Group>

				<SecondSectionComponent field={field} />
			</div>

			<div>
				<ERCInput
					disabled={isPublished}
					onValueChange={(value) => {
						dispatch({
							erc: value,
							type: 'update-field',
							uuid: field.uuid,
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
				<ClayCheckbox
					checked={field.indexableConfig.indexed}
					label={Liferay.Language.get('searchable')}
					onChange={(event) => {
						dispatch({
							indexableConfig: {
								indexed: event.target.checked,
								indexedAsKeyword: false,
								indexedLanguageId:
									Liferay.ThemeDisplay.getDefaultLanguageId(),
							},
							type: 'update-field',
							uuid: field.uuid,
						});
					}}
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
									type: 'update-field',
									uuid: field.uuid,
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
									type: 'update-field',
									uuid: field.uuid,
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
