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

import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../../app/config/constants/editableFragmentEntryProcessor';
import {EDITABLE_TYPES} from '../../../../../../app/config/constants/editableTypes';
import {
	useDispatch,
	useSelectorCallback,
} from '../../../../../../app/contexts/StoreContext';
import selectEditableValue from '../../../../../../app/selectors/selectEditableValue';
import selectEditableValues from '../../../../../../app/selectors/selectEditableValues';
import updateEditableValues from '../../../../../../app/thunks/updateEditableValues';
import {updateIn} from '../../../../../../app/utils/updateIn';
import MappingSelector from '../../../../../../common/components/MappingSelector';
import {getEditableItemPropTypes} from '../../../../../../prop_types/index';

export default function EditableActionPanel({item}) {
	const dispatch = useDispatch();

	const editableValues = useSelectorCallback(
		(state) => selectEditableValues(state, item.fragmentEntryLinkId),
		[item.fragmentEntryLinkId]
	);

	const editableValue = useSelectorCallback(
		(state) =>
			selectEditableValue(
				state,
				item.fragmentEntryLinkId,
				item.editableId,
				EDITABLE_FRAGMENT_ENTRY_PROCESSOR
			),
		[item.fragmentEntryLinkId]
	);

	const onValueSelect = (name, value) => {
		dispatch(
			updateEditableValues({
				editableValues: updateIn(
					editableValues,
					[
						EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
						[item.editableId],
						'config',
						name,
					],
					() => value
				),
				fragmentEntryLinkId: item.fragmentEntryLinkId,
			})
		);
	};

	return (
		<>
			<MappingSelector
				fieldSelectorLabel={Liferay.Language.get('action')}
				fieldType={EDITABLE_TYPES.action}
				mappedItem={editableValue.config.mappedAction || {}}
				onMappingSelect={(action) => {
					onValueSelect('mappedAction', action);
				}}
			/>
		</>
	);
}

EditableActionPanel.propTypes = {
	item: getEditableItemPropTypes(),
};
