/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayBreadcrumb from '@clayui/breadcrumb';
import ClayLayout from '@clayui/layout';
import React from 'react';

import {Field, useSelector, useStateDispatch} from '../contexts/StateContext';
import selectStructureField from '../selectors/selectStructureField';
import selectStructureLabel from '../selectors/selectStructureLabel';

export default function StructureFieldSettings({
	fieldName,
}: {
	fieldName: Field['name'];
}) {
	const dispatch = useStateDispatch();
	const structureLabel = useSelector(selectStructureLabel);
	const field = useSelector(selectStructureField(fieldName));

	return (
		<ClayLayout.ContainerFluid view>
			<ClayBreadcrumb
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
		</ClayLayout.ContainerFluid>
	);
}
