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

import {ClayInput} from '@clayui/form';
import React, {useCallback, useState} from 'react';

import {ImageSelector} from '../../../common/components/ImageSelector';
import {useDebounceCallback} from '../../../core/hooks/useDebounceCallback';
import {getEditableItemPropTypes} from '../../../prop-types/index';
import {BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR} from '../../config/constants/backgroundImageFragmentEntryProcessor';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../config/constants/editableFragmentEntryProcessor';
import {EDITABLE_TYPES} from '../../config/constants/editableTypes';
import selectEditableValueContent from '../../selectors/selectEditableValueContent';
import selectPrefixedSegmentsExperienceId from '../../selectors/selectPrefixedSegmentsExperienceId';
import {useDispatch, useSelector} from '../../store/index';
import updateEditableValuesThunk from '../../thunks/updateEditableValues';

export function ImagePropertiesPanel({item}) {
	const {editableId, editableType, fragmentEntryLinkId} = item;

	const dispatch = useDispatch();
	const state = useSelector(state => state);

	const processoryKey =
		editableType === EDITABLE_TYPES.backgroundImage
			? BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR
			: EDITABLE_FRAGMENT_ENTRY_PROCESSOR;

	const editableValue =
		state.fragmentEntryLinks[fragmentEntryLinkId].editableValues[
			processoryKey
		][editableId];

	const editableConfig = editableValue.config || {};

	const [imageDescription, setImageDescription] = useState(
		editableConfig.alt
	);

	const imageUrl = useSelector(state => {
		const url = selectEditableValueContent(
			state,
			fragmentEntryLinkId,
			editableId,
			processoryKey
		);

		return url === editableValue.defaultValue ? '' : url;
	});

	const updateEditableValues = useCallback(
		newConfig => {
			const editableValues =
				state.fragmentEntryLinks[fragmentEntryLinkId].editableValues;
			const editableProcessorValues = editableValues[processoryKey];

			const nextEditableValues = {
				...editableValues,
				[processoryKey]: {
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
					segmentsExperienceId: state.segmentsExperienceId,
				})
			);
		},
		[
			dispatch,
			editableConfig,
			editableId,
			fragmentEntryLinkId,
			processoryKey,
			state.fragmentEntryLinks,
			state.segmentsExperienceId,
		]
	);

	const [debounceUpdateEditableValues] = useDebounceCallback(
		updateEditableValues,
		500
	);

	const onImageChange = (imageTitle, imageUrl) => {
		const {editableValues} = state.fragmentEntryLinks[fragmentEntryLinkId];

		const editableProcessorValues = editableValues[processoryKey];

		const editableValue = editableProcessorValues[editableId];

		const prefixedSegmentsExperienceId = selectPrefixedSegmentsExperienceId(
			state
		);

		let nextEditableValue = {};

		setImageDescription('');

		const nextEditableValueConfig = {
			...editableValue.config,
			alt: '',
			imageTitle: '',
		};

		if (imageTitle) {
			nextEditableValueConfig.imageTitle = imageTitle;
		}

		if (prefixedSegmentsExperienceId) {
			nextEditableValue = {
				...editableValue,
				config: nextEditableValueConfig,
				[prefixedSegmentsExperienceId]: {
					...editableValue[prefixedSegmentsExperienceId],
					[state.languageId]: imageUrl,
				},
			};
		}
		else {
			nextEditableValue = {
				...editableValue,
				config: nextEditableValueConfig,
				[state.languageId]: imageUrl,
			};
		}

		const nextEditableValues = {
			...editableValues,

			[processoryKey]: {
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
				imageTitle={editableConfig.imageTitle || imageUrl}
				label={Liferay.Language.get('image')}
				onClearButtonPressed={() => onImageChange('', '')}
				onImageSelected={image => onImageChange(image.title, image.url)}
			/>

			{editableType === EDITABLE_TYPES.image && (
				<>
					<label htmlFor="imageDescription">
						{Liferay.Language.get('image-description')}
					</label>
					<ClayInput
						id="imageDescription"
						onChange={event => {
							setImageDescription(event.target.value);

							debounceUpdateEditableValues({
								alt: event.target.value,
							});
						}}
						sizing="sm"
						type="text"
						value={imageDescription || ''}
					/>
				</>
			)}
		</>
	);
}

ImagePropertiesPanel.propTypes = {
	item: getEditableItemPropTypes().isRequired,
};
