/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClaySelectWithOption} from '@clayui/form';
import React from 'react';

import {getSelectOptions} from '../../../../../../../common/getSelectOptions';

const VERTICAL_ALIGNMENT_OPTIONS = [
	{label: Liferay.Language.get('top'), value: 'start'},
	{label: Liferay.Language.get('middle'), value: 'center'},
	{label: Liferay.Language.get('bottom'), value: 'end'},
] as const;

export type VerticalAlignmentOption =
	(typeof VERTICAL_ALIGNMENT_OPTIONS)[number]['value'];

interface Props {
	collectionVerticalAlignmentId: string;
	handleConfigurationChanged: (change: {
		verticalAlignment: VerticalAlignmentOption;
	}) => void;
	value?: VerticalAlignmentOption;
}

export function VerticalAlignmentSelector({
	collectionVerticalAlignmentId,
	handleConfigurationChanged,
	value,
}: Props) {
	return (
		<ClayForm.Group small>
			<label htmlFor={collectionVerticalAlignmentId}>
				{Liferay.Language.get('vertical-alignment')}
			</label>

			<ClaySelectWithOption
				id={collectionVerticalAlignmentId}
				onChange={(event) => {
					const nextValue = event.target
						.value as VerticalAlignmentOption;

					handleConfigurationChanged({
						verticalAlignment: nextValue,
					});
				}}
				options={getSelectOptions(VERTICAL_ALIGNMENT_OPTIONS)}
				value={value || ''}
			/>
		</ClayForm.Group>
	);
}
