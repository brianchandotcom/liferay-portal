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
import React, {useContext} from 'react';

import {DispatchContext} from '../../app/reducers/index';
import {ConfigContext} from '../config/index';
import selectPrefixedSegmentsExperienceId from '../selectors/selectPrefixedSegmentsExperienceId';
import {StoreContext} from '../store/index';
import updateItemConfig from '../thunks/updateItemConfig';

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

const SELECTORS = {
	paddingHorizontal: 'paddingHorizontal',
	paddingVertical: 'paddingVertical',
	type: 'type'
};

export const SpacingConfigurationPanel = ({item}) => {
	const config = useContext(ConfigContext);
	const dispatch = useContext(DispatchContext);
	const segmentsExperienceId = selectPrefixedSegmentsExperienceId(
		useContext(StoreContext)
	);

	const handleSelectValueChanged = (identifier, value) => {
		dispatch(
			updateItemConfig({
				config,
				itemConfig: {
					[identifier]: value
				},
				itemId: item.itemId,
				segmentsExperienceId
			})
		);
	};

	return (
		<div className="floating-toolbar-spacing-panel">
			<ClayForm.Group>
				<label htmlFor="floatingToolbarSpacingPanelContainerTypeOption">
					{Liferay.Language.get('container')}
				</label>
				<ClaySelectWithOption
					aria-label={Liferay.Language.get('container')}
					id="floatingToolbarSpacingPanelContainerTypeOption"
					onChange={({target: {value}}) =>
						handleSelectValueChanged(SELECTORS.type, value)
					}
					options={[
						{
							label: Liferay.Language.get('fixed-width'),
							value: 'fixed'
						},
						{
							label: Liferay.Language.get('fluid'),
							value: 'fluid'
						}
					]}
					value={item.config.type}
				/>
			</ClayForm.Group>
			<ClayForm.Group className="form-group-autofit">
				<div className="form-group-item">
					<label htmlFor="floatingToolbarSpacingPanelPaddingVerticalOption">
						{Liferay.Language.get('padding-v')}
					</label>
					<ClaySelectWithOption
						aria-label={Liferay.Language.get('padding-v')}
						id="floatingToolbarSpacingPanelPaddingVerticalOption"
						onChange={({target: {value}}) =>
							handleSelectValueChanged(
								SELECTORS.paddingVertical,
								Number(value)
							)
						}
						options={PADDING_OPTIONS}
						value={String(item.config.paddingVertical)}
					/>
				</div>

				<div className="form-group-item">
					<label htmlFor="floatingToolbarSpacingPanelPaddingHorizontalOption">
						{Liferay.Language.get('padding-h')}
					</label>
					<ClaySelectWithOption
						aria-label={Liferay.Language.get('padding-h')}
						id="floatingToolbarSpacingPanelPaddingHorizontalOption"
						onChange={({target: {value}}) =>
							handleSelectValueChanged(
								SELECTORS.paddingHorizontal,
								Number(value)
							)
						}
						options={PADDING_OPTIONS}
						value={String(item.config.paddingHorizontal)}
					/>
				</div>
			</ClayForm.Group>
		</div>
	);
};
