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
import React, {useEffect, useState} from 'react';

import {EDITABLE_TYPES} from '../../config/constants/editableTypes';
import {ImageSelector} from './../../../common/components/ImageSelector';
import MappingSelector from './MappingSelector';

const IMAGE_SOURCE = {
	contentMapping: 'content_mapping',
	manualSelection: 'manual_selection'
};

export const ContainerBackgroundImageConfiguration = ({
	backgroundImage,
	onValueChange
}) => {
	const [imageSource, setImageSource] = useState(
		IMAGE_SOURCE.manualSelection
	);

	useEffect(() => {
		setImageSource(
			backgroundImage.fieldId || backgroundImage.mappedField
				? IMAGE_SOURCE.contentMapping
				: IMAGE_SOURCE.manualSelection
		);
	}, [backgroundImage]);

	return (
		<>
			<ClayForm.Group small>
				<label htmlFor="containerBackgroundImage">
					{Liferay.Language.get('image-source')}
				</label>
				<ClaySelectWithOption
					aria-label={Liferay.Language.get('image-source')}
					id="containerBackgroundImage"
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
					value={imageSource}
				/>
			</ClayForm.Group>
			{imageSource === IMAGE_SOURCE.manualSelection ? (
				<ImageSelector
					imageTitle={backgroundImage.title}
					label={Liferay.Language.get('background-image')}
					onClearButtonPressed={() =>
						onValueChange({
							backgroundImage: {
								title: '',
								url: ''
							}
						})
					}
					onImageSelected={image =>
						onValueChange({
							backgroundImage: {
								title: image.title,
								url: image.url
							}
						})
					}
				/>
			) : (
				<MappingSelector
					fieldType={EDITABLE_TYPES.image}
					mappedItem={backgroundImage}
					onMappingSelect={mappedItem => {
						onValueChange({
							backgroundImage: mappedItem
						});
					}}
				/>
			)}
		</>
	);
};
