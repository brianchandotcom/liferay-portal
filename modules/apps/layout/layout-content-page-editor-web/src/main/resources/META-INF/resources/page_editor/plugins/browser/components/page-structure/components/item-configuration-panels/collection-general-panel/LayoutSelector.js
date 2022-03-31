/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayForm, {ClaySelectWithOption} from '@clayui/form';
import PropTypes from 'prop-types';
import React from 'react';

import {config} from '../../../../../../../app/config/index';

const LAYOUT_OPTIONS = config.featureFlagLps119551
	? [
			{label: Liferay.Language.get('full-width'), value: '1'},
			{
				label: Liferay.Util.sub(Liferay.Language.get('x-columns'), 2),
				value: '2',
			},
			{
				label: Liferay.Util.sub(Liferay.Language.get('x-columns'), 3),
				value: '3',
			},
			{
				label: Liferay.Util.sub(Liferay.Language.get('x-columns'), 4),
				value: '4',
			},
			{
				label: Liferay.Util.sub(Liferay.Language.get('x-columns'), 5),
				value: '5',
			},
			{
				label: Liferay.Util.sub(Liferay.Language.get('x-columns'), 6),
				value: '6',
			},
			{
				label: Liferay.Util.sub(Liferay.Language.get('x-columns'), 12),
				value: '12',
			},
	  ]
	: [
			{label: Liferay.Language.get('full-width'), value: '1'},
			{
				label: Liferay.Util.sub(Liferay.Language.get('x-columns'), 2),
				value: '2',
			},
			{
				label: Liferay.Util.sub(Liferay.Language.get('x-columns'), 3),
				value: '3',
			},
			{
				label: Liferay.Util.sub(Liferay.Language.get('x-columns'), 4),
				value: '4',
			},
			{
				label: Liferay.Util.sub(Liferay.Language.get('x-columns'), 5),
				value: '5',
			},
			{
				label: Liferay.Util.sub(Liferay.Language.get('x-columns'), 6),
				value: '6',
			},
	  ];

export function LayoutSelector({
	collectionConfig,
	collectionLayoutId,
	handleConfigurationChanged,
	numberOfColumns,
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
				value={
					config.featureFlagLps119551
						? collectionConfig.numberOfColumns
						: numberOfColumns
				}
			/>
		</ClayForm.Group>
	);
}

LayoutSelector.propTypes = {
	collectionConfig: PropTypes.object.isRequired,
	collectionLayoutId: PropTypes.string.isRequired,
	handleConfigurationChanged: PropTypes.func.isRequired,
	numberOfColumns: PropTypes.number.isRequired,
};
