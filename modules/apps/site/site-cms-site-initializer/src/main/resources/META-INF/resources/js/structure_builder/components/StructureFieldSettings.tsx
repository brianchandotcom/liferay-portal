/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayBreadcrumb from '@clayui/breadcrumb';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import ClayTabs from '@clayui/tabs';
import React from 'react';

import {useSelector, useStateDispatch} from '../contexts/StateContext';
import selectStructureField from '../selectors/selectStructureField';
import selectStructureLabel from '../selectors/selectStructureLabel';
import {Field} from '../utils/field';

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
	return <div></div>;
}

function SearchTab() {
	return <div>Search</div>;
}
