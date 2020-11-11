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

const DEFAULT_IMAGE_CONFIGURATION = 'auto';

export function ImagePropertiesPanel({item}) {
	const {editableId, fragmentEntryLinkId, type} = item;

	const dispatch = useDispatch();
	const imageConfigurationId = useId();
	const imageDescriptionId = useId();
	const fragmentEntryLinks = useSelector((state) => state.fragmentEntryLinks);
	const languageId = useSelector(selectLanguageId);
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);
	const editables = useSelector((state) => state.editables);
	const [imageConfiguration, setImageConfiguration] = useState(
		DEFAULT_IMAGE_CONFIGURATION
	);
	const [imageConfigurations, setImageConfigurations] = useState([]);
	const [imageSize, setImageSize] = useState({fileSize: null, width: null});

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
		if (editableElement !== null) {
			const {maxWidth, minWidth} = config.availableViewportSizes[
				selectedViewportSize
			];

			const setSize = () => {
				if (
					(!imageConfigurations.length ||
						selectedViewportSize === VIEWPORT_SIZES.desktop) &&
					editableElement.naturalWidth
				) {
					const autoImageConfiguration = {
						...(imageConfigurations.find(
							(imageConfiguration) =>
								imageConfiguration.value === 'auto'
						) || {}),

						width: editableElement.naturalWidth,
					};

					setImageSize({
						fileSize: autoImageConfiguration.size || null,
						width: autoImageConfiguration.width,
					});
				}
				else {
					const viewportImageConfiguration = imageConfigurations.find(
						(imageConfiguration) =>
							imageConfiguration.width &&
							((imageConfiguration.width <= maxWidth &&
								imageConfiguration.width > minWidth) ||
								imageConfiguration.width ===
									editableElement.naturalWidth)
					) || {width: editableElement.naturalWidth};

					setImageSize({
						fileSize: viewportImageConfiguration.size || null,
						width: viewportImageConfiguration.width,
					});
				}
			};

			if (editableElement && editableElement.complete) {
				setSize();
			}
			else if (editableElement && !editableElement.complete) {
				editableElement.addEventListener('load', setSize);

				return () =>
					editableElement.removeEventListener('load', setSize);
			}
		}
	}, [
		editableConfig.naturalHeight,
		editableElement,
		imageConfigurations,
		selectedViewportSize,
	]);

	useEffect(() => {
		const fileEntryId = editableContent?.fileEntryId;

		if (config.adaptiveMediaEnabled && fileEntryId > 0) {
			ImageService.getAvailableImageConfigurations({
				fileEntryId,
				onNetworkStatus: dispatch,
			}).then((availableImageConfigurations) =>
				setImageConfigurations(availableImageConfigurations)
			);
		}
	}, [dispatch, editableContent.fileEntryId]);

	useEffect(() => {
		const selectedImageConfigurationValue =
			editableConfig.imageConfiguration?.[selectedViewportSize] ??
			DEFAULT_IMAGE_CONFIGURATION;

		const selectedImageConfiguration = imageConfigurations.find(
			(imageConfiguration) =>
				imageConfiguration.value === selectedImageConfigurationValue
		);

		if (selectedImageConfiguration) {
			setImageConfiguration(selectedImageConfiguration.value);
		}

		setImageDescription((imageDescription) => {
			if (imageDescription !== editableConfig.alt) {
				return editableConfig.alt || '';
			}

			return imageDescription;
		});
	}, [
		editableConfig.alt,
		editableConfig.imageConfiguration,
		editableValue,
		imageConfigurations,
		selectedViewportSize,
		languageId,
	]);

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

		setImageConfiguration(DEFAULT_IMAGE_CONFIGURATION);
		setImageConfigurations([]);
		setImageDescription('');
		setImageSize({fileSize: null, width: null});

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

			{config.adaptiveMediaEnabled && imageConfigurations?.length > 0 && (
				<ClayForm.Group className="mb-2">
					<label htmlFor={imageConfigurationId}>
						{Liferay.Language.get('resolution')}
					</label>
					<ClaySelectWithOption
						className={'form-control form-control-sm'}
						id={imageConfigurationId}
						name={imageConfigurationId}
						onChange={(event) => {
							const imageConfiguration =
								editableConfig.imageConfiguration || {};

							const nextImageConfiguration = {
								...imageConfiguration,
								[selectedViewportSize]: event.target.value,
							};

							updateEditableConfig(
								{imageConfiguration: nextImageConfiguration},
								editableValues,
								editableId,
								processorKey
							);
						}}
						options={imageConfigurations}
						value={imageConfiguration}
					/>
				</ClayForm.Group>
			)}

			{config.adaptiveMediaEnabled && imageTitle && imageSize.width && (
				<div className="page-editor__image-properties-panel__resolution-label">
					<b>{Liferay.Language.get('width')}:</b>
					<span className="ml-1">{imageSize.width}px</span>
				</div>
			)}

			{config.adaptiveMediaEnabled && imageTitle && imageSize.fileSize && (
				<div className="mb-3 page-editor__image-properties-panel__resolution-label">
					<b>{Liferay.Language.get('file-size')}:</b>
					<span className="ml-1">
						{Number(imageSize.fileSize).toFixed(2)}kB
					</span>
				</div>
			)}

			{canUpdateImage && type === EDITABLE_TYPES.image && (
				<ClayForm.Group>
					<label htmlFor={imageDescriptionId}>
						{Liferay.Language.get('image-description')}
					</label>
					<ClayInput
						id={imageDescriptionId}
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
