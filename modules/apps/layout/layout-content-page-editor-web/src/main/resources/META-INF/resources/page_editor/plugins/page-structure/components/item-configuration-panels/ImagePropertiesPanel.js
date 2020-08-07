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
import React, {useEffect, useState} from 'react';

import {BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../app/config/constants/backgroundImageFragmentEntryProcessor';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../app/config/constants/editableFragmentEntryProcessor';
import {EDITABLE_TYPES} from '../../../../app/config/constants/editableTypes';
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
		const url = selectEditableValueContent(
			state,
			fragmentEntryLinkId,
			editableId,
			processorKey
		);

		return url === editableValue.defaultValue ? '' : url;
	});

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

	const onImageChange = (imageTitle, imageUrl) => {
		const {editableValues} = state.fragmentEntryLinks[fragmentEntryLinkId];

		const editableProcessorValues = editableValues[processorKey];

		const editableValue = editableProcessorValues[editableId];

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

		nextEditableValue = {
			...editableValue,
			config: nextEditableValueConfig,
			[state.languageId]: imageUrl,
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
				imageTitle={editableConfig.imageTitle || imageUrl}
				label={Liferay.Language.get('image')}
				onClearButtonPressed={() => onImageChange('', '')}
				onImageSelected={(image) =>
					onImageChange(image.title, image.url)
				}
			/>

			{type === EDITABLE_TYPES.image && (
				<>
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
				</>
			)}
		</>
	);
}

ImagePropertiesPanel.propTypes = {
	item: getEditableItemPropTypes().isRequired,
};
