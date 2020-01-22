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

import {CONTAINER_PADDING_IDENTIFIERS} from '../config/constants/containerPaddingIdentifiers';
import {PADDING_OPTIONS} from '../config/constants/paddingOptions';

export const ContainerPaddingVerticalConfiguration = ({
	onValueChange,
	paddingBottom,
	paddingTop
}) => (
	<ClayForm.Group className="form-group-autofit" small>
		<div className="form-group-item">
			<label htmlFor="containerPaddingTop">
				{Liferay.Language.get('padding-top')}
			</label>
			<ClaySelectWithOption
				aria-label={Liferay.Language.get('padding-top')}
				id="containerPaddingTop"
				onChange={({target: {value}}) =>
					onValueChange({
						[CONTAINER_PADDING_IDENTIFIERS.paddingTop]: Number(
							value
						)
					})
				}
				options={PADDING_OPTIONS}
				value={String(paddingTop)}
			/>
		</div>

		<div className="form-group-item">
			<label htmlFor="containerPaddingBottom">
				{Liferay.Language.get('padding-bottom')}
			</label>
			<ClaySelectWithOption
				aria-label={Liferay.Language.get('padding-bottom')}
				id="containerPaddingBottom"
				onChange={({target: {value}}) =>
					onValueChange({
						[CONTAINER_PADDING_IDENTIFIERS.paddingBottom]: Number(
							value
						)
					})
				}
				options={PADDING_OPTIONS}
				value={String(paddingBottom)}
			/>
		</div>
	</ClayForm.Group>
);

export const ContainerPaddingHorizontalConfiguration = ({
	onValueChange,
	paddingHorizontal
}) => (
	<ClayForm.Group className="form-group-autofit" small>
		<div className="form-group-item">
			<label htmlFor="containerPaddingHorizontal">
				{Liferay.Language.get('padding-horizontal')}
			</label>
			<ClaySelectWithOption
				aria-label={Liferay.Language.get('padding-horizontal')}
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
