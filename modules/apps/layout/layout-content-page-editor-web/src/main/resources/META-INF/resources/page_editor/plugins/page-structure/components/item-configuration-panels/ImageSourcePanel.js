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
import React, {useState} from 'react';

import {BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../app/config/constants/backgroundImageFragmentEntryProcessor';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../app/config/constants/editableFragmentEntryProcessor';
import {EDITABLE_TYPES} from '../../../../app/config/constants/editableTypes';
import {FILE_ENTRY_CLASS_NAME} from '../../../../app/config/constants/fileEntryClassName';
import {VIEWPORT_SIZES} from '../../../../app/config/constants/viewportSizes';
import {config} from '../../../../app/config/index';
import selectEditableValueContent from '../../../../app/selectors/selectEditableValueContent';
import selectLanguageId from '../../../../app/selectors/selectLanguageId';
import selectSegmentsExperienceId from '../../../../app/selectors/selectSegmentsExperienceId';
import {useDispatch, useSelector} from '../../../../app/store/index';
import updateEditableValuesThunk from '../../../../app/thunks/updateEditableValues';
import isMapped from '../../../../app/utils/isMapped';
import {setIn} from '../../../../app/utils/setIn';
import {updateIn} from '../../../../app/utils/updateIn';
import {useId} from '../../../../app/utils/useId';
import {ImageSelector} from '../../../../common/components/ImageSelector';
import {ImageSelectorDescription} from '../../../../common/components/ImageSelectorDescription';
import {ImageSelectorSize} from '../../../../common/components/ImageSelectorSize';
import {getEditableItemPropTypes} from '../../../../prop-types/index';
import {MappingPanel} from './MappingPanel';

const SOURCE_OPTIONS = {
	direct: {
		label: Liferay.Language.get('direct'),
		value: 'direct',
	},
	mapping: {
		label: Liferay.Language.get('mapping'),
		value: 'mapping',
	},
};

export default function ImageSourcePanel({item}) {
	const fragmentEntryLinks = useSelector((state) => state.fragmentEntryLinks);
	const sourceSelectionInputId = useId();

	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const editableValue =
		fragmentEntryLinks[item.fragmentEntryLinkId].editableValues[
			item.editableValueNamespace
		][item.editableId];

	const [source, setSource] = useState(() =>
		isMapped(editableValue)
			? SOURCE_OPTIONS.mapping.value
			: SOURCE_OPTIONS.direct.value
	);

	let ConfigurationPanel = DirectImagePanel;

	if (source === SOURCE_OPTIONS.mapping.value) {
		if (selectedViewportSize === VIEWPORT_SIZES.desktop) {
			ConfigurationPanel = MappingImagePanel;
		}
		else {
			ConfigurationPanel = null;
		}
	}

	return (
		<>
			{selectedViewportSize === VIEWPORT_SIZES.desktop && (
				<ClayForm>
					<ClayForm.Group>
						<label htmlFor={sourceSelectionInputId}>
							{Liferay.Language.get('source-selection')}
						</label>

						<ClaySelectWithOption
							className="form-control form-control-sm mb-3"
							id={sourceSelectionInputId}
							onChange={(event) => setSource(event.target.value)}
							options={Object.values(SOURCE_OPTIONS)}
							value={source}
						/>
					</ClayForm.Group>
				</ClayForm>
			)}

			{ConfigurationPanel && <ConfigurationPanel item={item} />}
		</>
	);
}

ImageSourcePanel.propTypes = {
	item: getEditableItemPropTypes().isRequired,
};

function DirectImagePanel({item}) {
	const {editableId, fragmentEntryLinkId, type} = item;

	const dispatch = useDispatch();
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
		editableContent?.title ||
		editableConfig.imageTitle ||
		(imageUrl === editableValue.defaultValue ? '' : imageUrl);

	const imageDescription =
		typeof editableConfig.alt === 'object' && editableConfig.alt
			? editableConfig.alt[languageId] ||
			  editableConfig.alt[config.defaultLanguageId] ||
			  ''
			: editableConfig.alt || '';

	const handleImageChanged = (nextImage) => {
		const nextEditableValue = {
			...editableValue,

			config: {
				alt: {[languageId]: ''},
				imageConfiguration: {},
				imageTitle: nextImage.title,
			},
			[languageId]: nextImage.url,
		};

		if (config.adaptiveMediaEnabled) {
			delete nextEditableValue.config.imageTitle;

			nextEditableValue[languageId] = nextImage;
		}

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
				languageId,
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

	return (
		<>
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

			<ImagePanelSizeSelector item={item} />

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

DirectImagePanel.propTypes = {
	item: getEditableItemPropTypes().isRequired,
};

function MappingImagePanel({item}) {
	return (
		<>
			<MappingPanel item={item} />
			<ImagePanelSizeSelector item={item} />
		</>
	);
}

MappingImagePanel.propTypes = {
	item: getEditableItemPropTypes().isRequired,
};

function ImagePanelSizeSelector({item}) {
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

	const imageSizeId =
		editableConfig.imageConfiguration?.[selectedViewportSize];

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
		config.adaptiveMediaEnabled &&
		(editableContent?.fileEntryId ||
			(editableContent?.className === FILE_ENTRY_CLASS_NAME &&
				editableContent?.classPK)) && (
			<ImageSelectorSize
				editableElement={editableElement}
				fileEntryId={
					editableContent.fileEntryId || editableContent.classPK
				}
				imageSizeId={imageSizeId}
				onImageSizeIdChanged={
					item.type === EDITABLE_TYPES.image
						? handleImageSizeChanged
						: null
				}
			/>
		)
	);
}

ImagePanelSizeSelector.propTypes = {
	item: getEditableItemPropTypes().isRequired,
};
