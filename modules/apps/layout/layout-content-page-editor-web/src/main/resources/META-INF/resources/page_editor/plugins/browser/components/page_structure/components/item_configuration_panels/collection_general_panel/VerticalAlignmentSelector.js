/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClaySelectWithOption} from '@clayui/form';
import PropTypes from 'prop-types';
import React from 'react';

const VERTICAL_ALIGNMENT_OPTIONS = [
	{label: Liferay.Language.get('top'), value: 'start'},
	{label: Liferay.Language.get('middle'), value: 'center'},
	{label: Liferay.Language.get('bottom'), value: 'end'},
];

export function VerticalAlignmentSelector({
	collectionVerticalAlignmentId,
	handleConfigurationChanged,
	value,
}) {
	return (
		<ClayForm.Group small>
			<label htmlFor={collectionVerticalAlignmentId}>
				{Liferay.Language.get('vertical-alignment')}
			</label>

			<ClaySelectWithOption
				id={collectionVerticalAlignmentId}
				onChange={(event) => {
					const nextValue = event.target.value;

					handleConfigurationChanged({
						verticalAlignment: nextValue,
					});
				}}
				options={VERTICAL_ALIGNMENT_OPTIONS}
				value={value || ''}
			/>
		</ClayForm.Group>
	);
}

VerticalAlignmentSelector.propTypes = {
	collectionVerticalAlignmentId: PropTypes.string.isRequired,
	handleConfigurationChanged: PropTypes.func.isRequired,
	value: PropTypes.string.isRequired,
};
