/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import isMapped from '../../../../../../app/utils/editable_value/isMapped';
import MappingSelector from '../../../../../../common/components/MappingSelector';
import {getEditableItemPropTypes} from '../../../../../../prop_types/index';
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
				fieldType={
					type === EDITABLE_TYPES.action ? EDITABLE_TYPES.text : type
				}
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
