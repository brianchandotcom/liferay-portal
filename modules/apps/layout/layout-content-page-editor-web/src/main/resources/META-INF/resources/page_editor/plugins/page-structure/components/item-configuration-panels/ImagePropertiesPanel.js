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
import {setIn} from '../../../../app/utils/setIn';
import {updateIn} from '../../../../app/utils/updateIn';
import {useId} from '../../../../app/utils/useId';
import {ImageSelector} from '../../../../common/components/ImageSelector';
import {ImageSelectorDescription} from '../../../../common/components/ImageSelectorDescription';
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
	const [imageSize, setImageSize] = useState(DEFAULT_IMAGE_SIZE);
	const [imageSizes, setImageSizes] = useState([]);
	const imageSizeSelectId = useId();
	const languageId = useSelector(selectLanguageId);
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

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

	const imageDescription =
		typeof editableConfig.alt === 'object'
			? editableConfig.alt[languageId] ||
			  editableConfig.alt[config.defaultLanguageId] ||
			  ''
			: editableConfig.alt || '';

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

	const handleImageChanged = (nextImage) => {
		const nextEditableValue = {
			...editableValue,

			config: {
				alt: {},
				imageConfiguration: {},
				imageTitle: nextImage.title || '',
			},
			[languageId]: config.adaptiveMediaEnabled
				? nextImage
				: nextImage.url,
		};

		delete nextEditableValue.classNameId;
		delete nextEditableValue.classPK;
		delete nextEditableValue.collectionFieldId;
		delete nextEditableValue.fieldId;
		delete nextEditableValue.mappedField;

		dispatch(
			updateEditableValuesThunk({
				editableValues: setIn(
					editableValues,
					[processorKey, editableId],
					nextEditableValue
				),
				fragmentEntryLinkId,
				segmentsExperienceId,
			})
		);
	};

	const handleImageDescriptionChanged = (nextImageDescription) => {
		dispatch(
			updateEditableValuesThunk({
				editableValues: updateIn(
					editableValues,
					[processorKey, editableId, 'config', 'alt'],
					(alt) => {

						// If alt is a string (old style), we need to
						// migrate it to an object to allow translations.

						if (typeof alt === 'string') {
							return {
								[config.defaultLanguageId]: alt,
								[languageId]: nextImageDescription,
							};
						}

						return {
							...alt,
							[languageId]: nextImageDescription,
						};
					},
					{}
				),
				fragmentEntryLinkId,
				segmentsExperienceId,
			})
		);
	};

	const handleImageSizeChanged = (imageSizeId) => {
		dispatch(
			updateEditableValuesThunk({
				editableValues: setIn(
					editableValues,
					[
						processorKey,
						editableId,
						'config',
						'imageConfiguration',
						selectedViewportSize,
					],
					imageSizeId
				),
				fragmentEntryLinkId,
				segmentsExperienceId,
			})
		);
	};

	return (
		<>
			{selectedViewportSize === VIEWPORT_SIZES.desktop && (
				<ImageSelector
					imageTitle={imageTitle}
					label={Liferay.Language.get('image')}
					onClearButtonPressed={() => {
						handleImageChanged({
							fileEntryId: '',
							title: '',
							url: '',
						});
					}}
					onImageSelected={handleImageChanged}
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
							handleImageSizeChanged(event.target.value)
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

			{selectedViewportSize === VIEWPORT_SIZES.desktop &&
				type === EDITABLE_TYPES.image && (
					<ImageSelectorDescription
						imageDescription={imageDescription}
						onImageDescriptionChanged={
							handleImageDescriptionChanged
						}
					/>
				)}
		</>
	);
}

ImagePropertiesPanel.propTypes = {
	item: getEditableItemPropTypes().isRequired,
};
