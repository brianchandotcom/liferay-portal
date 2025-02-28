/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayBreadcrumb from '@clayui/breadcrumb';
import ClayForm, {ClayToggle} from '@clayui/form';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import ClayTabs from '@clayui/tabs';
import {InputLocalized} from 'frontend-js-components-web';
import React, {useState} from 'react';

import {useSelector, useStateDispatch} from '../contexts/StateContext';
import selectPublishedFields from '../selectors/selectPublishedFields';
import selectStructureField from '../selectors/selectStructureField';
import selectStructureLabel from '../selectors/selectStructureLabel';
import selectStructureStatus from '../selectors/selectStructureStatus';
import {Field} from '../utils/field';
import ERCInput from './ERCInput';
import TextInput from './TextInput';

export default function StructureFieldSettings({
	fieldName,
}: {
	fieldName: Field['name'];
}) {
	const dispatch = useStateDispatch();
	const structureLabel = useSelector(selectStructureLabel);
	const field = useSelector(selectStructureField(fieldName));

	return (
		<ClayLayout.ContainerFluid size="md" view>
			<ClayBreadcrumb
				className="mb-3"
				items={[
					{
						label: structureLabel,
						onClick: () => {
							dispatch({
								item: {type: 'structure'},
								type: 'select-item',
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
						<SearchTab />
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

	return (
		<>
			<div className="pb-2">
				<p className="font-weight-semi-bold mb-0 text-3">
					{Liferay.Language.get('field-type')}
				</p>

				<ClayLabel displayType="info">{field.type}</ClayLabel>
			</div>

			<div className="mt-4 pb-2">
				<TextInput
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

				<ClayForm.Group className="mb-0">
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

function SearchTab() {
	return (
		<>
			<ClayForm.Group>
				<ClayToggle
					label={Liferay.Language.get('searchable')}
					onToggle={() => {}}
					toggled={false}
				/>
			</ClayForm.Group>
		</>
	);
}
