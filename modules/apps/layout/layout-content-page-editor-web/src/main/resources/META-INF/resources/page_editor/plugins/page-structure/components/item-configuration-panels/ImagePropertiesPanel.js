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

import React from 'react';

import {BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../app/config/constants/backgroundImageFragmentEntryProcessor';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../app/config/constants/editableFragmentEntryProcessor';
import {EDITABLE_TYPES} from '../../../../app/config/constants/editableTypes';
import {VIEWPORT_SIZES} from '../../../../app/config/constants/viewportSizes';
import {config} from '../../../../app/config/index';
import selectEditableValueContent from '../../../../app/selectors/selectEditableValueContent';
import selectLanguageId from '../../../../app/selectors/selectLanguageId';
import selectSegmentsExperienceId from '../../../../app/selectors/selectSegmentsExperienceId';
import {useDispatch, useSelector} from '../../../../app/store/index';
import updateEditableValuesThunk from '../../../../app/thunks/updateEditableValues';
import {setIn} from '../../../../app/utils/setIn';
import {updateIn} from '../../../../app/utils/updateIn';
import {ImageSelector} from '../../../../common/components/ImageSelector';
import {ImageSelectorDescription} from '../../../../common/components/ImageSelectorDescription';
import {ImageSelectorSize} from '../../../../common/components/ImageSelectorSize';
import {getEditableItemPropTypes} from '../../../../prop-types/index';

export function ImagePropertiesPanel({item}) {
	const {editableId, fragmentEntryLinkId, type} = item;

	const dispatch = useDispatch();
	const editables = useSelector((state) => state.editables);
	const fragmentEntryLinks = useSelector((state) => state.fragmentEntryLinks);
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
	const editableElement = editables?.[item.parentId]?.[item.itemId]?.element;

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

	const imageSizeId = editableConfig.imageProperties?.[selectedViewportSize];

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

			{config.adaptiveMediaEnabled && editableContent?.fileEntryId && (
				<ImageSelectorSize
					editableElement={editableElement}
					fileEntryId={editableContent.fileEntryId}
					imageSizeId={imageSizeId}
					onImageSizeIdChanged={handleImageSizeChanged}
				/>
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
