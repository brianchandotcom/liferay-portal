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

import {BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../../app/config/constants/backgroundImageFragmentEntryProcessor';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../../app/config/constants/editableFragmentEntryProcessor';
import {EDITABLE_TYPES} from '../../../../../../app/config/constants/editableTypes';
import {useCollectionConfig} from '../../../../../../app/contexts/CollectionItemContext';
import {
	useDispatch,
	useSelector,
} from '../../../../../../app/contexts/StoreContext';
import selectEditableValue from '../../../../../../app/selectors/selectEditableValue';
import updateEditableValues from '../../../../../../app/thunks/updateEditableValues';
import isMapped from '../../../../../../app/utils/editable-value/isMapped';
import MappingSelector from '../../../../../../common/components/MappingSelector';
import {getEditableItemPropTypes} from '../../../../../../prop-types/index';
import DateEditableFormatInput from './DateEditableFormatInput';

export function MappingPanel({item}) {
	const collectionConfig = useCollectionConfig();

	const {editableId, fragmentEntryLinkId, type} = item;

	const dispatch = useDispatch();
	const state = useSelector((state) => state);

	const fragmentEntryLink = state.fragmentEntryLinks[fragmentEntryLinkId];

	const processoryKey =
		type === EDITABLE_TYPES.backgroundImage
			? BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR
			: EDITABLE_FRAGMENT_ENTRY_PROCESSOR;

	const editableValue = selectEditableValue(
		state,
		fragmentEntryLinkId,
		editableId,
		processoryKey
	);

	const updateEditableValue = (nextEditableValue) => {
		let nextEditableConfig = {...editableValue.config};

		if (
			type === EDITABLE_TYPES['date-time'] &&
			!isMapped(nextEditableValue)
		) {
			nextEditableConfig = {};
		}
		else if (
			type === EDITABLE_TYPES.image &&
			isMapped(nextEditableValue)
		) {
			nextEditableConfig = {
				...editableValue.config,
				alt: '',
				imageTitle: '',
			};
		}

		const nextEditableValues = {
			...fragmentEntryLink.editableValues,
			[processoryKey]: {
				...fragmentEntryLink.editableValues[processoryKey],
				[editableId]: {
					config: nextEditableConfig,
					defaultValue: editableValue.defaultValue,
					...nextEditableValue,
				},
			},
		};

		dispatch(
			updateEditableValues({
				editableValues: nextEditableValues,
				fragmentEntryLinkId,
			})
		);
	};

	return (
		<>
			{collectionConfig && (
				<p className="page-editor__mapping-panel__helper text-secondary">
					{Liferay.Language.get('collection-mapping-help')}
				</p>
			)}

			<MappingSelector
				fieldType={type}
				mappedItem={editableValue}
				onMappingSelect={updateEditableValue}
			/>

			{type === EDITABLE_TYPES['date-time'] &&
				isMapped(editableValue) && (
					<DateEditableFormatInput
						editableId={editableId}
						editableValueNamespace={item.editableValueNamespace}
						editableValues={fragmentEntryLink.editableValues}
						fragmentEntryLinkId={item.fragmentEntryLinkId}
					/>
				)}
		</>
	);
}

MappingPanel.propTypes = {
	item: getEditableItemPropTypes(),
};
