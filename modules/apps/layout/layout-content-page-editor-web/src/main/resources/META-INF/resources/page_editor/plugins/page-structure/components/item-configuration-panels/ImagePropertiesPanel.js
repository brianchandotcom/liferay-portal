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

import ClayForm, {ClayInput, ClaySelectWithOption} from '@clayui/form';
import React, {useEffect, useState} from 'react';

import {BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../app/config/constants/backgroundImageFragmentEntryProcessor';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../app/config/constants/editableFragmentEntryProcessor';
import {EDITABLE_TYPES} from '../../../../app/config/constants/editableTypes';
import {VIEWPORT_SIZES} from '../../../../app/config/constants/viewportSizes';
import {config} from '../../../../app/config/index';
import selectEditableValueContent from '../../../../app/selectors/selectEditableValueContent';
import selectLanguageId from '../../../../app/selectors/selectLanguageId';
import selectSegmentsExperienceId from '../../../../app/selectors/selectSegmentsExperienceId';
import ImageService from '../../../../app/services/ImageService';
import {useDispatch, useSelector} from '../../../../app/store/index';
import updateEditableValuesThunk from '../../../../app/thunks/updateEditableValues';
import {useId} from '../../../../app/utils/useId';
import {ImageSelector} from '../../../../common/components/ImageSelector';
import {getEditableItemPropTypes} from '../../../../prop-types/index';

const DEFAULT_IMAGE_SIZE_ID = 'auto';

const DEFAULT_IMAGE_SIZE = {
	size: null,
	value: DEFAULT_IMAGE_SIZE_ID,
	width: null,
};

export function ImagePropertiesPanel({item}) {
	const {editableId, fragmentEntryLinkId, type} = item;

	const dispatch = useDispatch();
	const editables = useSelector((state) => state.editables);
	const fragmentEntryLinks = useSelector((state) => state.fragmentEntryLinks);
	const imageDescriptionInputId = useId();
	const [imageSize, setImageSize] = useState(DEFAULT_IMAGE_SIZE);
	const [imageSizes, setImageSizes] = useState([]);
	const imageSizeSelectId = useId();
	const languageId = useSelector(selectLanguageId);
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const canUpdateImage = selectedViewportSize === VIEWPORT_SIZES.desktop;

	const processorKey =
		type === EDITABLE_TYPES.backgroundImage
			? BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR
			: EDITABLE_FRAGMENT_ENTRY_PROCESSOR;

	const editableValues =
		fragmentEntryLinks[fragmentEntryLinkId].editableValues;

	const editableValue = editableValues[processorKey][editableId];

	const editableConfig = editableValue.config || {};

	const editableElement = editables
		? editables[item.parentId]?.[item.itemId]?.element
		: undefined;

	const editableContent = selectEditableValueContent(
		{fragmentEntryLinks, languageId},
		fragmentEntryLinkId,
		editableId,
		processorKey
	);

	const imageUrl =
		typeof editableContent === 'string'
			? editableContent
			: editableContent?.url;

	const imageTitle =
		editableConfig.imageTitle ||
		(imageUrl === editableValue.defaultValue ? '' : imageUrl);

	const [imageDescription, setImageDescription] = useState(
		editableConfig.alt || ''
	);

	useEffect(() => {
		const {maxWidth, minWidth} = config.availableViewportSizes[
			selectedViewportSize
		];

		const configSize =
			editableConfig.imageProperties?.[selectedViewportSize] ||
			DEFAULT_IMAGE_SIZE;

		setImageSize(configSize);

		if (editableElement && configSize.value === DEFAULT_IMAGE_SIZE_ID) {
			const setAutoSize = () => {
				const autoSize =
					imageSizes.find(
						({width}) =>
							(width <= maxWidth && width > minWidth) ||
							width === editableElement.naturalWidth
					) ||
					imageSizes.find(
						({value}) => value === DEFAULT_IMAGE_SIZE_ID
					) ||
					DEFAULT_IMAGE_SIZE;

				setImageSize({
					...autoSize,
					width: autoSize.width || editableElement.naturalWidth,
				});
			};

			if (editableElement.complete) {
				setAutoSize();
			}
			else {
				editableElement.addEventListener('load', setAutoSize);

				return () => {
					editableElement.removeEventListener('load', setAutoSize);
				};
			}
		}
	}, [
		editableConfig.imageProperties,
		editableElement,
		imageSizes,
		selectedViewportSize,
	]);

	useEffect(() => {
		const fileEntryId = editableContent?.fileEntryId;

		if (config.adaptiveMediaEnabled && fileEntryId > 0) {
			ImageService.getAvailableImageConfigurations({
				fileEntryId,
				onNetworkStatus: dispatch,
			}).then((availableImageSizes) => {
				setImageSizes(availableImageSizes);
			});
		}
	}, [dispatch, editableContent.fileEntryId]);

	useEffect(() => {
		setImageDescription((imageDescription) => {
			if (imageDescription !== editableConfig.alt) {
				return editableConfig.alt || '';
			}

			return imageDescription;
		});
	}, [editableConfig.alt]);

	const updateEditableConfig = (
		newConfig = {},
		editableValues,
		editableId,
		processorKey
	) => {
		const editableProcessorValues = editableValues[processorKey];

		const editableValue = editableValues[processorKey][editableId];

		const editableConfig = editableValue.config || {};

		const nextEditableValues = {
			...editableValues,
			[processorKey]: {
				...editableProcessorValues,
				[editableId]: {
					...editableProcessorValues[editableId],
					config: {
						...editableConfig,
						...newConfig,
					},
				},
			},
		};

		dispatch(
			updateEditableValuesThunk({
				editableValues: nextEditableValues,
				fragmentEntryLinkId,
				segmentsExperienceId,
			})
		);
	};

	const onImageChange = (imageTitle, imageUrl, fileEntryId) => {
		const {editableValues} = fragmentEntryLinks[fragmentEntryLinkId];

		const editableProcessorValues = editableValues[processorKey];

		const editableValue = editableProcessorValues[editableId];

		let nextEditableValue = {};

		setImageDescription('');
		setImageSize(DEFAULT_IMAGE_SIZE);
		setImageSizes([]);

		const nextEditableValueConfig = {
			...editableValue.config,
			alt: '',
			imageConfiguration: {},
			imageTitle: imageTitle || '',
		};

		const nextEditableValueContent = config.adaptiveMediaEnabled
			? {
					fileEntryId,
					url: imageUrl,
			  }
			: imageUrl;

		nextEditableValue = {
			...editableValue,
			config: nextEditableValueConfig,
			[languageId]: nextEditableValueContent,
		};

		const nextEditableValues = {
			...editableValues,

			[processorKey]: {
				...editableProcessorValues,
				[editableId]: {
					...nextEditableValue,
				},
			},
		};

		dispatch(
			updateEditableValuesThunk({
				editableValues: nextEditableValues,
				fragmentEntryLinkId,
				segmentsExperienceId,
			})
		);
	};

	return (
		<>
			{canUpdateImage && (
				<ImageSelector
					imageTitle={imageTitle}
					label={Liferay.Language.get('image')}
					onClearButtonPressed={() => onImageChange('', '')}
					onImageSelected={(image) =>
						onImageChange(image.title, image.url, image.fileEntryId)
					}
				/>
			)}

			{config.adaptiveMediaEnabled && imageSizes?.length > 0 && (
				<ClayForm.Group className="mb-2">
					<label htmlFor={imageSizeSelectId}>
						{Liferay.Language.get('resolution')}
					</label>
					<ClaySelectWithOption
						className={'form-control form-control-sm'}
						id={imageSizeSelectId}
						name={imageSizeSelectId}
						onChange={(event) =>
							updateEditableConfig(
								{
									imageConfiguration: {
										...(editableConfig.imageConfiguration ||
											{}),
										[selectedViewportSize]:
											event.target.value,
									},
								},
								editableValues,
								editableId,
								processorKey
							)
						}
						options={imageSizes}
						value={
							editableConfig.imageConfiguration?.[
								selectedViewportSize
							] || DEFAULT_IMAGE_SIZE_ID
						}
					/>
				</ClayForm.Group>
			)}

			{config.adaptiveMediaEnabled && imageTitle && imageSize.width && (
				<div className="page-editor__image-properties-panel__resolution-label">
					<b>{Liferay.Language.get('width')}:</b>
					<span className="ml-1">{imageSize.width}px</span>
				</div>
			)}

			{config.adaptiveMediaEnabled && imageTitle && imageSize.size && (
				<div className="mb-3 page-editor__image-properties-panel__resolution-label">
					<b>{Liferay.Language.get('file-size')}:</b>
					<span className="ml-1">
						{Number(imageSize.size).toFixed(2)}kB
					</span>
				</div>
			)}

			{canUpdateImage && type === EDITABLE_TYPES.image && (
				<ClayForm.Group>
					<label htmlFor={imageDescriptionInputId}>
						{Liferay.Language.get('image-description')}
					</label>
					<ClayInput
						id={imageDescriptionInputId}
						onBlur={() => {
							const previousValue = editableConfig.alt || '';

							if (previousValue !== imageDescription) {
								updateEditableConfig(
									{alt: imageDescription},
									editableValues,
									editableId,
									processorKey
								);
							}
						}}
						onChange={(event) => {
							setImageDescription(event.target.value);
						}}
						sizing="sm"
						type="text"
						value={imageDescription || ''}
					/>
				</ClayForm.Group>
			)}
		</>
	);
}

ImagePropertiesPanel.propTypes = {
	item: getEditableItemPropTypes().isRequired,
};
