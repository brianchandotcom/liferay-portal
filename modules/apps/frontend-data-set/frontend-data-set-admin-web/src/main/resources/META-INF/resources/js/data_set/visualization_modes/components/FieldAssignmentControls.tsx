/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import {sub} from 'frontend-js-web';
import React from 'react';

import {IField} from '../../../utils/types';

interface IFieldAssignmentControlsProps {
	field?: IField;
	label: string;
	onClearSelection: () => void;
	openSelectFieldModal: () => void;
}

function FieldAssignmentControls({
	field,
	label,
	onClearSelection,
	openSelectFieldModal,
}: IFieldAssignmentControlsProps) {
	return field ? (
		<ClayDropDownWithItems
			items={[
				{
					label: Liferay.Language.get('change-assignment'),
					onClick: openSelectFieldModal,
					symbolLeft: 'change',
				},
				{
					label: Liferay.Language.get('clear-assignment'),
					onClick: onClearSelection,
					symbolLeft: 'times-circle',
				},
			]}
			trigger={
				<ClayButtonWithIcon
					aria-label={sub(
						Liferay.Language.get('view-x-options'),
						label
					)}
					displayType="secondary"
					size="sm"
					symbol="ellipsis-v"
					title={sub(Liferay.Language.get('view-x-options'), label)}
				/>
			}
		/>
	) : (
		<ClayButtonWithIcon
			aria-label={Liferay.Language.get('assign-field')}
			displayType="secondary"
			onClick={openSelectFieldModal}
			symbol="plus"
			title={Liferay.Language.get('assign-field')}
		/>
	);
}

export default FieldAssignmentControls;
