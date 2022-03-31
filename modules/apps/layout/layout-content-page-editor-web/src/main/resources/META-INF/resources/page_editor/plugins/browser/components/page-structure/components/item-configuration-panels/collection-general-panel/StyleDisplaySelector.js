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
import React, {useEffect, useState} from 'react';

import {config} from '../../../../../../../app/config/index';
import InfoItemService from '../../../../../../../app/services/InfoItemService';
import {useId} from '../../../../../../../app/utils/useId';

const LIST_STYLE_GRID = '';

const DEFAULT_LIST_STYLE = {
	label: Liferay.Language.get('grid'),
	value: LIST_STYLE_GRID,
};

export function StyleDisplaySelector({
	collectionItemType,
	handleConfigurationChanged,
	listStyle,
}) {
	const [availableListStyles, setAvailableListStyles] = useState([
		DEFAULT_LIST_STYLE,
	]);

	const listStyleId = useId();

	useEffect(() => {
		if (collectionItemType) {
			InfoItemService.getAvailableListRenderers({
				className: collectionItemType,
			})
				.then((response) => {
					setAvailableListStyles([
						DEFAULT_LIST_STYLE,
						{
							label: Liferay.Language.get('templates'),
							options: response,
							type: 'group',
						},
					]);
				})
				.catch(() => {
					setAvailableListStyles([DEFAULT_LIST_STYLE]);
				});
		}
	}, [collectionItemType]);

	return (
		<ClayForm.Group small>
			<label htmlFor={listStyleId}>
				{config.featureFlagLps119551
					? Liferay.Language.get('style-display')
					: Liferay.Language.get('list-style')}
			</label>

			<ClaySelectWithOption
				aria-label={
					config.featureFlagLps119551
						? Liferay.Language.get('style-display')
						: Liferay.Language.get('list-style')
				}
				id={listStyleId}
				onChange={(event) =>
					handleConfigurationChanged({
						listStyle: event.target.value,
					})
				}
				options={availableListStyles}
				value={listStyle}
			/>
		</ClayForm.Group>
	);
}

StyleDisplaySelector.propTypes = {
	collectionItemType: PropTypes.string,
	handleConfigurationChanged: PropTypes.func.isRequired,
	listStyle: PropTypes.string,
};
