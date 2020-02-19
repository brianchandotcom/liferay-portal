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

import React, {useContext} from 'react';

import {getEditableItemPropTypes} from '../../../prop-types/index';
import {BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR} from '../../config/constants/backgroundImageFragmentEntryProcessor';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../config/constants/editableFragmentEntryProcessor';
import {EDITABLE_TYPES} from '../../config/constants/editableTypes';
import {ConfigContext} from '../../config/index';
import selectEditableValue from '../../selectors/selectEditableValue';
import {useDispatch, useSelector} from '../../store/index';
import updateEditableValues from '../../thunks/updateEditableValues';
import MappingSelector from './MappingSelector';

export function MappingPanel({item}) {
	const {editableId, editableType, fragmentEntryLinkId} = item;

	const config = useContext(ConfigContext);
	const dispatch = useDispatch();
	const state = useSelector(state => state);

	const fragmentEntryLink = state.fragmentEntryLinks[fragmentEntryLinkId];

	const processoryKey =
		editableType === EDITABLE_TYPES.backgroundImage
			? BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR
			: EDITABLE_FRAGMENT_ENTRY_PROCESSOR;

	const editableValue = selectEditableValue(
		state,
		fragmentEntryLinkId,
		editableId,
		processoryKey
	);

	const updateEditableValue = newEditableValue => {
		const nextEditableValues = {
			...fragmentEntryLink.editableValues,
			[processoryKey]: {
				...fragmentEntryLink.editableValues[processoryKey],
				[editableId]: {
					config: editableValue.config,
					defaultValue: editableValue.defaultValue,
					...newEditableValue
				}
			}
		};

		dispatch(
			updateEditableValues({
				editableValues: nextEditableValues,
				fragmentEntryLinkId,
				segmentsExperienceId: state.segmentsExperienceId
			})
		);
	};

	return (
		<MappingSelector
			fieldType={editableType}
			mappedItem={editableValue}
			onMappingSelect={updateEditableValue}
		/>
	);
}

MappingPanel.propTypes = {
	item: getEditableItemPropTypes()
};
