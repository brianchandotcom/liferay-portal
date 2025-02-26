/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayBreadcrumb from '@clayui/breadcrumb';
import ClayForm, {ClayToggle} from '@clayui/form';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import ClayTabs from '@clayui/tabs';
import React from 'react';

import {useSelector, useStateDispatch} from '../contexts/StateContext';
import selectStructureField from '../selectors/selectStructureField';
import selectStructureLabel from '../selectors/selectStructureLabel';
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
						label: field!.label,
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
					label={Liferay.Language.get('field-name')}
					onValueChange={() => {}}
					value={field.name}
				/>

				<TextInput
					className="mb-0"
					label={Liferay.Language.get('label')}
					onValueChange={() => {}}
					value={field.name}
				/>
			</div>

			<div className="mt-4 pb-2">
				<ClayForm.Group className="mb-3">
					<ClayToggle
						label={Liferay.Language.get('mandatory')}
						onToggle={() => {}}
						toggled={false}
					/>
				</ClayForm.Group>

				<ClayForm.Group className="mb-0">
					<ClayToggle
						label={Liferay.Language.get('localizable')}
						onToggle={() => {}}
						toggled={false}
					/>
				</ClayForm.Group>
			</div>

			<div className="mt-4">
				<ERCInput onValueChange={() => {}} value="" />
			</div>
		</>
	);
}

function SearchTab() {
	return <div>Search</div>;
}
