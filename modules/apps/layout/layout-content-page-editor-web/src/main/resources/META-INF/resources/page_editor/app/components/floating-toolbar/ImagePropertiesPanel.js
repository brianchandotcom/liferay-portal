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
import React, {useContext, useCallback, useState} from 'react';

import {ImageSelector} from '../../../common/components/ImageSelector';
import {useDebounceCallback} from '../../../core/hooks/useDebounceCallback';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../config/constants/editableFragmentEntryProcessor';
import {ConfigContext} from '../../config/index';
import selectPrefixedSegmentsExperienceId from '../../selectors/selectPrefixedSegmentsExperienceId';
import {useSelector, useDispatch} from '../../store/index';
import updateEditableValues from '../../thunks/updateEditableValues';

export function ImagePropertiesPanel({item}) {
	const {editableId, fragmentEntryLinkId} = item;

	const config = useContext(ConfigContext);
	const dispatch = useDispatch();
	const state = useSelector(state => state);

	const editableValue =
		state.fragmentEntryLinks[fragmentEntryLinkId].editableValues[
			EDITABLE_FRAGMENT_ENTRY_PROCESSOR
		][editableId];

	const editableConfig = editableValue.config || {};

	const [imageDescription, setImageDescription] = useState(
		editableConfig.alt
	);

	const updateRowConfig = useCallback(
		newConfig => {
			const editableValues =
				state.fragmentEntryLinks[fragmentEntryLinkId].editableValues;
			const editableProcessorValues =
				editableValues[EDITABLE_FRAGMENT_ENTRY_PROCESSOR];

			const nextEditableValues = {
				...editableValues,
				[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {
					...editableProcessorValues,
					[editableId]: {
						...editableProcessorValues[editableId],
						config: {
							...editableConfig,
							...newConfig
						}
					}
				}
			};

			dispatch(
				updateEditableValues({
					config,
					editableValues: nextEditableValues,
					fragmentEntryLinkId,
					segmentsExperienceId: state.segmentsExperienceId
				})
			);
		},
		[
			config,
			dispatch,
			editableConfig,
			editableId,
			fragmentEntryLinkId,
			state.fragmentEntryLinks,
			state.segmentsExperienceId
		]
	);

	const [debounceUpdateRowConfig] = useDebounceCallback(updateRowConfig, 500);

	const onImageChange = (imageTitle, imageUrl) => {
		const {editableValues} = state.fragmentEntryLinks[fragmentEntryLinkId];

		const editableProcessorValues =
			editableValues[EDITABLE_FRAGMENT_ENTRY_PROCESSOR];

		const editableValue = editableProcessorValues[editableId];

		const prefixedSegmentsExperienceId = selectPrefixedSegmentsExperienceId(
			state
		);

		let nextEditableValue = {};

		if (state.segmentsExperienceId) {
			nextEditableValue = {
				...editableValue,
				config: {...editableValue.config, imageTitle},
				[prefixedSegmentsExperienceId]: {
					...editableValue[prefixedSegmentsExperienceId],
					[state.languageId]: imageUrl
				}
			};
		}
		else {
			nextEditableValue = {
				...editableValue,
				config: {...editableValue.config, imageTitle},
				[state.languageId]: imageUrl
			};
		}

		const nextEditableValues = {
			...editableValues,

			[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {
				...editableProcessorValues,
				[editableId]: {
					...nextEditableValue
				}
			}
		};

		dispatch(
			updateEditableValues({
				config,
				editableValues: nextEditableValues,
				fragmentEntryLinkId,
				segmentsExperienceId: state.segmentsExperienceId
			})
		);
	};

	return (
		<>
			<ImageSelector
				imageTitle={editableConfig.imageTitle}
				label={Liferay.Language.get('image')}
				onClearButtonPressed={() => onImageChange('', '')}
				onImageSelected={image => onImageChange(image.title, image.url)}
			/>
			<label htmlFor="imageDescription">
				{Liferay.Language.get('image-description')}
			</label>
			<ClayInput
				id="imageDescription"
				onChange={event => {
					setImageDescription(event.target.value);

					debounceUpdateRowConfig({
						alt: event.target.value
					});
				}}
				sizing="sm"
				type="text"
				value={imageDescription || ''}
			/>
		</>
	);
}
