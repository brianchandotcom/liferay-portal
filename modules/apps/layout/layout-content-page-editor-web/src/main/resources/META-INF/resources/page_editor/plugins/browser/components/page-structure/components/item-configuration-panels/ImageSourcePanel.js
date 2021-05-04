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

import {BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../../app/config/constants/backgroundImageFragmentEntryProcessor';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../../app/config/constants/editableFragmentEntryProcessor';
import {EDITABLE_TYPES} from '../../../../../../app/config/constants/editableTypes';
import {VIEWPORT_SIZES} from '../../../../../../app/config/constants/viewportSizes';
import {config} from '../../../../../../app/config/index';
import {
	useDispatch,
	useSelector,
} from '../../../../../../app/contexts/StoreContext';
import selectEditableValueContent from '../../../../../../app/selectors/selectEditableValueContent';
import selectLanguageId from '../../../../../../app/selectors/selectLanguageId';
import selectSegmentsExperienceId from '../../../../../../app/selectors/selectSegmentsExperienceId';
import updateEditableValuesThunk from '../../../../../../app/thunks/updateEditableValues';
import isMapped from '../../../../../../app/utils/editable-value/isMapped';
import isMappedToCollection from '../../../../../../app/utils/editable-value/isMappedToCollection';
import isMappedToInfoItem from '../../../../../../app/utils/editable-value/isMappedToInfoItem';
import {setIn} from '../../../../../../app/utils/setIn';
import {updateIn} from '../../../../../../app/utils/updateIn';
import {useId} from '../../../../../../app/utils/useId';
import {ImageSelector} from '../../../../../../common/components/ImageSelector';
import {ImageSelectorDescription} from '../../../../../../common/components/ImageSelectorDescription';
import {ImageSelectorSize} from '../../../../../../common/components/ImageSelectorSize';
import {getEditableItemPropTypes} from '../../../../../../prop-types/index';
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
	const dispatch = useDispatch();
	const fragmentEntryLinks = useSelector((state) => state.fragmentEntryLinks);
	const languageId = useSelector(selectLanguageId);
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);
	const sourceSelectionInputId = useId();

	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const editableValues =
		fragmentEntryLinks[item.fragmentEntryLinkId].editableValues;

	const editableValue =
		editableValues[item.editableValueNamespace][item.editableId];

	const [source, setSource] = useState(() =>
		isMapped(editableValue)
			? SOURCE_OPTIONS.mapping.value
			: SOURCE_OPTIONS.direct.value
	);

	const handleSourceChanged = (event) => {
		setSource(event.target.value);

		if (Object.keys(editableValue).length) {
			dispatch(
				updateEditableValuesThunk({
					editableValues: setIn(
						editableValues,
						[item.editableValueNamespace, item.editableId],
						{
							config: {
								...(editableValue.config || {}),
								alt: {},
								imageConfiguration: {},
							},
						}
					),
					fragmentEntryLinkId: item.fragmentEntryLinkId,
					languageId,
					segmentsExperienceId,
				})
			);
		}
	};

	let ConfigurationPanel = DirectImagePanel;

	if (source === SOURCE_OPTIONS.mapping.value) {
		ConfigurationPanel = MappingImagePanel;
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
							onChange={handleSourceChanged}
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
		let nextEditableValue;

		if (isMapped(editableValue) || isMapped(nextImage)) {
			nextEditableValue = {
				config: setIn(editableValue.config, ['alt'], {}),
				...nextImage,
			};
		}
		else {
			nextEditableValue = setIn(
				editableValue,
				['config', 'alt', languageId],
				''
			);

			if (nextImage) {
				nextEditableValue[languageId] = nextImage;
			}
			else {
				delete nextEditableValue[languageId];
			}
		}

		if (!nextEditableValue.config?.imageConfiguration) {
			nextEditableValue = setIn(
				nextEditableValue,
				['config', 'imageConfiguration'],
				{}
			);
		}

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
					handleImageChanged(null);
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
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	return (
		<>
			{selectedViewportSize === VIEWPORT_SIZES.desktop ? (
				<MappingPanel item={item} />
			) : null}
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

	return editableContent?.fileEntryId ||
		isMappedToInfoItem(editableContent) ||
		isMappedToCollection(editableContent) ? (
		<ImageSelectorSize
			editableElement={editableElement}
			fieldValue={editableContent}
			imageSizeId={imageSizeId}
			onImageSizeIdChanged={
				item.type === EDITABLE_TYPES.image
					? handleImageSizeChanged
					: null
			}
		/>
	) : null;
}

ImagePanelSizeSelector.propTypes = {
	item: getEditableItemPropTypes().isRequired,
};
