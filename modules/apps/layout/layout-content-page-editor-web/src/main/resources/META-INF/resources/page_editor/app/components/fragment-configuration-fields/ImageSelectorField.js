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

import {ImageSelector} from '../../../common/components/ImageSelector';
import MappingSelector from '../../../common/components/MappingSelector';
import {ConfigurationFieldPropTypes} from '../../../prop-types/index';
import {EDITABLE_TYPES} from '../../config/constants/editableTypes';
import {VIEWPORT_SIZES} from '../../config/constants/viewportSizes';
import {config} from '../../config/index';
import ImageService from '../../services/ImageService';
import {useDispatch, useSelector} from '../../store/index';
import {useId} from '../../utils/useId';

const IMAGE_SOURCES = {
	direct: {
		label: Liferay.Language.get('direct'),
		value: 'direct',
	},

	mapping: {
		label: Liferay.Language.get('mapping'),
		value: 'mapping',
	},
};

export const ImageSelectorField = ({field, onValueSelect, value = {}}) => {
	const dispatch = useDispatch();
	const imageSourceInputId = useId();

	const [imageConfigurations, setImageConfigurations] = useState([]);

	const [imageFileSize, setImageFileSize] = useState('');

	const [imageSource, setImageSource] = useState(() =>
		value.fieldId || value.mappedField
			? IMAGE_SOURCES.mapping.value
			: IMAGE_SOURCES.direct.value
	);

	const [imageWidth, setImageWidth] = useState('');

	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const {maxWidth, minWidth} = config.availableViewportSizes[
		selectedViewportSize
	];

	const handleImageChanged = (image) => {
		onValueSelect(field.name, image);
	};

	const handleSourceChanged = (event) => {
		setImageSource(event.target.value);

		if (Object.keys(value).length) {
			handleImageChanged({});
		}
	};

	useEffect(() => {
		const fileEntryId = value?.fileEntryId;

		if (config.adaptiveMediaEnabled && fileEntryId > 0) {
			ImageService.getAvailableImageConfigurations({
				fileEntryId,
				onNetworkStatus: dispatch,
			}).then((availableImageConfigurations) =>
				setImageConfigurations(availableImageConfigurations)
			);
		}
	}, [dispatch, value.fileEntryId]);

	useEffect(() => {
		if (config.adaptiveMediaEnabled) {
			imageConfigurations.forEach((imageConfiguration) => {
				if (
					(selectedViewportSize === VIEWPORT_SIZES.desktop &&
						imageConfiguration.value === 'auto') ||
					(imageConfiguration.width > minWidth &&
						imageConfiguration.width <= maxWidth)
				) {
					setImageFileSize(imageConfiguration.size);
					setImageWidth(imageConfiguration.width);
				}
			});
		}
	}, [imageConfigurations, maxWidth, minWidth, selectedViewportSize]);

	return (
		<>
			<ClayForm.Group small>
				<label htmlFor={imageSourceInputId}>
					{Liferay.Language.get('image-source')}
				</label>

				<ClaySelectWithOption
					id={imageSourceInputId}
					onChange={handleSourceChanged}
					options={Object.values(IMAGE_SOURCES)}
					value={imageSource}
				/>
			</ClayForm.Group>

			{imageSource === IMAGE_SOURCES.direct.value ? (
				<ImageSelector
					imageTitle={value.title}
					label={field.label}
					onClearButtonPressed={() => handleImageChanged({})}
					onImageSelected={handleImageChanged}
				/>
			) : (
				<MappingSelector
					fieldType={EDITABLE_TYPES.backgroundImage}
					mappedItem={value}
					onMappingSelect={handleImageChanged}
				/>
			)}

			{config.adaptiveMediaEnabled && value?.fileEntryId && imageWidth && (
				<div className="page-editor__image-properties-panel__resolution-label">
					<b>{Liferay.Language.get('width')}:</b>
					<span className="ml-1">{imageWidth}px</span>
				</div>
			)}

			{config.adaptiveMediaEnabled &&
				value?.fileEntryId &&
				imageFileSize && (
					<div className="mb-3 page-editor__image-properties-panel__resolution-label">
						<b>{Liferay.Language.get('file-size')}:</b>
						<span className="ml-1">
							{Number(imageFileSize).toFixed(2)}kB
						</span>
					</div>
				)}
		</>
	);
};

ImageSelectorField.propTypes = {
	field: PropTypes.shape(ConfigurationFieldPropTypes),
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.oneOfType([PropTypes.string, PropTypes.object]),
};
