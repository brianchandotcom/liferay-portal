/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClaySelectWithOption} from '@clayui/form';
import {sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React from 'react';

const LAYOUT_OPTIONS = [
	{label: Liferay.Language.get('full-width'), value: '1'},
	{
		label: sub(Liferay.Language.get('x-columns'), 2),
		value: '2',
	},
	{
		label: sub(Liferay.Language.get('x-columns'), 3),
		value: '3',
	},
	{
		label: sub(Liferay.Language.get('x-columns'), 4),
		value: '4',
	},
	{
		label: sub(Liferay.Language.get('x-columns'), 5),
		value: '5',
	},
	{
		label: sub(Liferay.Language.get('x-columns'), 6),
		value: '6',
	},
	{
		label: sub(Liferay.Language.get('x-columns'), 12),
		value: '12',
	},
];

export function LayoutSelector({
	collectionConfig,
	collectionLayoutId,
	handleConfigurationChanged,
}) {
	return (
		<ClayForm.Group small>
			<label htmlFor={collectionLayoutId}>
				{Liferay.Language.get('layout')}
			</label>

			<ClaySelectWithOption
				aria-label={Liferay.Language.get('layout')}
				id={collectionLayoutId}
				onChange={(event) =>
					handleConfigurationChanged({
						numberOfColumns: event.target.value,
					})
				}
				options={LAYOUT_OPTIONS}
				value={collectionConfig.numberOfColumns}
			/>
		</ClayForm.Group>
	);
}

LayoutSelector.propTypes = {
	collectionConfig: PropTypes.object.isRequired,
	collectionLayoutId: PropTypes.string.isRequired,
	handleConfigurationChanged: PropTypes.func.isRequired,
};
