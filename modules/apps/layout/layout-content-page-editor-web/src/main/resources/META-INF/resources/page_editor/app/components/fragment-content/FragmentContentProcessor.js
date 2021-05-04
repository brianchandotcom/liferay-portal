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

import PropTypes from 'prop-types';
import {useEffect} from 'react';

import {useToControlsId} from '../../contexts/CollectionItemContext';
import {
	useEditableProcessorClickPosition,
	useEditableProcessorUniqueId,
	useSetEditableProcessorUniqueId,
} from '../../contexts/EditableProcessorContext';
import {
	useDispatch,
	useSelector,
	useSelectorCallback,
} from '../../contexts/StoreContext';
import selectLanguageId from '../../selectors/selectLanguageId';
import selectSegmentsExperienceId from '../../selectors/selectSegmentsExperienceId';
import updateEditableValues from '../../thunks/updateEditableValues';

export default function FragmentContentProcessor({
	fragmentEntryLinkId,
	itemId,
}) {
	const dispatch = useDispatch();
	const editableProcessorClickPosition = useEditableProcessorClickPosition();
	const editableProcessorUniqueId = useEditableProcessorUniqueId();
	const languageId = useSelector(selectLanguageId);
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);
	const setEditableProcessorUniqueId = useSetEditableProcessorUniqueId();
	const toControlsId = useToControlsId();

	const editable = useSelectorCallback(
		(state) =>
			Object.values(state.editables?.[toControlsId(itemId)] || {}).find(
				(editable) =>
					editableProcessorUniqueId === toControlsId(editable.itemId)
			),
		[editableProcessorUniqueId, itemId, toControlsId]
	);

	const editableCollectionItemId = toControlsId(
		editable ? editable.itemId : ''
	);

	const editableValues = useSelectorCallback(
		(state) =>
			state.fragmentEntryLinks[fragmentEntryLinkId] &&
			state.fragmentEntryLinks[fragmentEntryLinkId].editableValues,
		[fragmentEntryLinkId]
	);

	useEffect(() => {
		if (
			!editable ||
			!editableValues ||
			editableCollectionItemId !== editableProcessorUniqueId
		) {
			return;
		}

		const editableValue =
			editableValues[editable.editableValueNamespace][
				editable.editableId
			];

		editable.processor.createEditor(
			editable.element,
			(value, config = {}) => {
				const defaultValue = editableValue.defaultValue?.trim() ?? '';
				const previousValue = editableValue[languageId];

				if (
					previousValue === value ||
					(!previousValue && value === defaultValue)
				) {
					return Promise.resolve();
				}

				const editableConfig = {
					...(editableValue.config || {}),
					...config,
				};

				return dispatch(
					updateEditableValues({
						editableValues: {
							...editableValues,
							[editable.editableValueNamespace]: {
								...editableValues[
									editable.editableValueNamespace
								],
								[editable.editableId]: {
									...editableValue,
									config: editableConfig,
									[languageId]: value,
								},
							},
						},
						fragmentEntryLinkId,
						languageId,
						segmentsExperienceId,
					})
				);
			},
			() => {
				if (editableCollectionItemId === editableProcessorUniqueId) {
					setEditableProcessorUniqueId(null);
				}

				editable.processor.destroyEditor(
					editable.element,
					editableValue.config
				);
			},
			editableProcessorClickPosition
		);
	}, [
		dispatch,
		editable,
		editableCollectionItemId,
		editableProcessorClickPosition,
		editableProcessorUniqueId,
		editableValues,
		fragmentEntryLinkId,
		languageId,
		segmentsExperienceId,
		setEditableProcessorUniqueId,
	]);

	return null;
}

FragmentContentProcessor.propTypes = {
	fragmentEntryLinkId: PropTypes.string.isRequired,
};
