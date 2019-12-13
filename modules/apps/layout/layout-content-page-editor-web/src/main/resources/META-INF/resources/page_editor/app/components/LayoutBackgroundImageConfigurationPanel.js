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
import React, {useContext, useState} from 'react';

import addMappedInfoItem from '../actions/addMappedInfoItem';
import updateItemConfig from '../actions/updateItemConfig';
import {DispatchContext} from '../reducers/index';
import {AssetSelectionPanel} from './AssetSelectionPanel';
import {ManualSelectionPanel} from './ManualSelectionPanel';

const IMAGE_SOURCE = {
	contentMapping: 'content_mapping',
	manualSelection: 'manual_selection'
};

/**
 * Renders Layout Background Image Configuration Panel.
 */
export const LayoutBackgroundImageConfigurationPanel = ({item}) => {
	const dispatch = useContext(DispatchContext);
	const {
		config: {backgroundImageTitle},
		itemId
	} = item;

	const [imageSource, setImageSource] = useState(
		IMAGE_SOURCE.manualSelection
	);

	const handleBackgroundImageItemChanged = image =>
		dispatch(addMappedInfoItem(image));

	const handleBackgroundImageConfig = ({imageTitle, imageURL}) =>
		dispatch(
			updateItemConfig({
				config: {
					backgroundImage: imageURL,
					backgroundImageTitle: imageTitle
				},
				itemId
			})
		);

	return (
		<div className="floating-toolbar-layout-background-image-panel">
			<ClayForm.Group>
				<label htmlFor="floatingToolbarLayoutBackgroundImagePanelImageSourceTypeSelect">
					{Liferay.Language.get('image-source')}
				</label>
				<ClaySelectWithOption
					aria-label={Liferay.Language.get('image-source')}
					defaultValue={IMAGE_SOURCE.manualSelection}
					id="floatingToolbarLayoutBackgroundImagePanelImageSourceTypeSelect"
					onChange={({target: {value}}) => setImageSource(value)}
					options={[
						{
							label: Liferay.Language.get('manual-selection'),
							value: IMAGE_SOURCE.manualSelection
						},
						{
							label: Liferay.Language.get('content-mapping'),
							value: IMAGE_SOURCE.contentMapping
						}
					]}
				/>
			</ClayForm.Group>
			{imageSource === IMAGE_SOURCE.manualSelection ? (
				<ManualSelectionPanel
					backgroundImageTitle={backgroundImageTitle}
					onClearButtonPressed={() =>
						handleBackgroundImageConfig({
							imageTitle: undefined,
							imageURL: undefined
						})
					}
					onImageSelected={image =>
						handleBackgroundImageConfig({
							imageTitle: image.title,
							imageURL: image.url
						})
					}
				/>
			) : (
				<AssetSelectionPanel
					backgroundImageTitle={backgroundImageTitle}
					onItemSelectorChanged={handleBackgroundImageItemChanged}
				/>
			)}
		</div>
	);
};
