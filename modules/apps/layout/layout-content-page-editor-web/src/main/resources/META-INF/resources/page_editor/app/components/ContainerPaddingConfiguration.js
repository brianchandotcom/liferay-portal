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
import React from 'react';

const PADDING_OPTIONS = [
	{
		label: '0',
		value: '0'
	},
	{
		label: '1',
		value: '3'
	},
	{
		label: '2',
		value: '4'
	},
	{
		label: '4',
		value: '5'
	},
	{
		label: '6',
		value: '6'
	},
	{
		label: '8',
		value: '7'
	},
	{
		label: '10',
		value: '8'
	}
];

const CONTAINER_PADDING_IDENTIFIERS = {
	paddingHorizontal: 'paddingHorizontal',
	paddingVertical: 'paddingVertical'
};

export const ContainerPaddingConfiguration = ({
	onValueChange,
	paddingHorizontal,
	paddingVertical
}) => (
	<ClayForm.Group className="form-group-autofit">
		<div className="form-group-item">
			<label htmlFor="containerPaddingVertical">
				{Liferay.Language.get('padding-v')}
			</label>
			<ClaySelectWithOption
				aria-label={Liferay.Language.get('padding-v')}
				id="containerPaddingVertical"
				onChange={({target: {value}}) =>
					onValueChange({
						[CONTAINER_PADDING_IDENTIFIERS.paddingVertical]: Number(
							value
						)
					})
				}
				options={PADDING_OPTIONS}
				value={String(paddingVertical)}
			/>
		</div>

		<div className="form-group-item">
			<label htmlFor="containerPaddingHorizontal">
				{Liferay.Language.get('padding-h')}
			</label>
			<ClaySelectWithOption
				aria-label={Liferay.Language.get('padding-h')}
				id="containerPaddingHorizontal"
				onChange={({target: {value}}) =>
					onValueChange({
						[CONTAINER_PADDING_IDENTIFIERS.paddingHorizontal]: Number(
							value
						)
					})
				}
				options={PADDING_OPTIONS}
				value={String(paddingHorizontal)}
			/>
		</div>
	</ClayForm.Group>
);
