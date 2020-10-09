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

import ClayForm, {ClayInput} from '@clayui/form';
import React, {useEffect, useState} from 'react';

import {BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../app/config/constants/backgroundImageFragmentEntryProcessor';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../app/config/constants/editableFragmentEntryProcessor';
import {EDITABLE_TYPES} from '../../../../app/config/constants/editableTypes';
import {config} from '../../../../app/config/index';
import selectEditableValueContent from '../../../../app/selectors/selectEditableValueContent';
import {useDispatch, useSelector} from '../../../../app/store/index';
import updateEditableValuesThunk from '../../../../app/thunks/updateEditableValues';
import {useId} from '../../../../app/utils/useId';
import {ImageSelector} from '../../../../common/components/ImageSelector';
import {getEditableItemPropTypes} from '../../../../prop-types/index';

export function ImagePropertiesPanel({item}) {
	const {editableId, fragmentEntryLinkId, type} = item;
	const dispatch = useDispatch();
	const imageDescriptionId = useId();
	const state = useSelector((state) => state);

	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const processorKey =
		type === EDITABLE_TYPES.backgroundImage
			? BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR
			: EDITABLE_FRAGMENT_ENTRY_PROCESSOR;

	const editableValues =
		state.fragmentEntryLinks[fragmentEntryLinkId].editableValues;

	const editableValue = editableValues[processorKey][editableId];

	const editableConfig = editableValue.config || {};

	const [imageDescription, setImageDescription] = useState(
		editableConfig.alt || ''
	);

	const editables = useSelector((state) => state.editables);

	const editableElement = editables
		? editables[item.parentId]?.[item.itemId]?.element
		: undefined;

	const [imageSize, setImageSize] = useState(null);

	useEffect(() => {
		if (editableElement != null) {
			const setSize = () => {
				if (
					editableElement.naturalHeight &&
					editableElement.naturalWidth
				) {
					setImageSize({
						height: editableElement.naturalHeight,
						width: editableElement.naturalWidth,
					});
				}
			};

			if (editableElement.complete) {
				setSize();
			}
			else {
				editableElement.addEventListener('load', setSize);

				return () =>
					editableElement.removeEventListener('load', setSize);
			}
		}
	}, [editableConfig.naturalHeight, editableElement, selectedViewportSize]);

	useEffect(() => {
		const editableConfig = editableValue ? editableValue.config || {} : {};

		setImageDescription((imageDescription) => {
			if (imageDescription !== editableConfig.alt) {
				return editableConfig.alt || '';
			}

			return imageDescription;
		});
	}, [editableValue]);

	const imageUrl = useSelector((state) => {
		const content = selectEditableValueContent(
			state,
			fragmentEntryLinkId,
			editableId,
			processorKey
		);

		return content?.url ?? content;
	});

	const imageTitle =
		editableConfig.imageTitle ||
		(imageUrl === editableValue.defaultValue ? '' : imageUrl);

	const updateEditableValues = (
		alt,
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
						alt,
					},
				},
			},
		};

		dispatch(
			updateEditableValuesThunk({
				editableValues: nextEditableValues,
				fragmentEntryLinkId,
				segmentsExperienceId: state.segmentsExperienceId,
			})
		);
	};

	const onImageChange = (imageTitle, imageUrl, fileEntryId) => {
		const {editableValues} = state.fragmentEntryLinks[fragmentEntryLinkId];

		const editableProcessorValues = editableValues[processorKey];

		const editableValue = editableProcessorValues[editableId];

		let nextEditableValue = {};

		setImageDescription('');

		const nextEditableValueConfig = {
			...editableValue.config,
			alt: '',
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
			[state.languageId]: nextEditableValueContent,
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
				segmentsExperienceId: state.segmentsExperienceId,
			})
		);
	};

	return (
		<>
			<ImageSelector
				imageTitle={imageTitle}
				label={Liferay.Language.get('image')}
				onClearButtonPressed={() => onImageChange('', '')}
				onImageSelected={(image) =>
					onImageChange(image.title, image.url, image.fileEntryId)
				}
			/>

			{imageTitle && imageSize && (
				<div className="mb-2 small">
					<b>{Liferay.Language.get('resolution')}:</b>
					<span className="ml-2">
						{imageSize.width}x{imageSize.height}px
					</span>
				</div>
			)}

			{type === EDITABLE_TYPES.image && (
				<ClayForm.Group>
					<label htmlFor={imageDescriptionId}>
						{Liferay.Language.get('image-description')}
					</label>
					<ClayInput
						id={imageDescriptionId}
						onBlur={() => {
							const previousValue = editableConfig.alt || '';

							if (previousValue !== imageDescription) {
								updateEditableValues(
									imageDescription,
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
