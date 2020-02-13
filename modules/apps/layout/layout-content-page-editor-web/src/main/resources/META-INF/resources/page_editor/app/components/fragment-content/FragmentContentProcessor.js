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

import {useEffect, useMemo} from 'react';

import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../config/constants/editableFragmentEntryProcessor';
import {config} from '../../config/index';
import Processors from '../../processors/index';
import selectPrefixedSegmentsExperienceId from '../../selectors/selectPrefixedSegmentsExperienceId';
import {useDispatch, useSelector} from '../../store/index';
import updateEditableValues from '../../thunks/updateEditableValues';
import {useEditableProcessorUniqueId} from './EditableProcessorContext';
import getEditableUniqueId from './getEditableUniqueId';

export default function FragmentContentProcessor({
	element,
	fragmentEntryLinkId
}) {
	const dispatch = useDispatch();
	const editableProcessorUniqueId = useEditableProcessorUniqueId();
	const languageId = useSelector(
		state => state.languageId || config.defaultLanguageId
	);
	const segmentsExperienceId = useSelector(
		selectPrefixedSegmentsExperienceId
	);

	const editableElement = useMemo(
		() =>
			element
				? Array.from(element.querySelectorAll('lfr-editable')).find(
						editableElement =>
							getEditableUniqueId(
								fragmentEntryLinkId,
								editableElement.id
							) === editableProcessorUniqueId
				  )
				: null,
		[editableProcessorUniqueId, element, fragmentEntryLinkId]
	);

	const editableValues = useSelector(
		state =>
			state.fragmentEntryLinks[fragmentEntryLinkId] &&
			state.fragmentEntryLinks[fragmentEntryLinkId].editableValues
	);

	useEffect(() => {
		if (!editableElement || !editableValues) {
			return;
		}

		const editableId = editableElement.id;
		const editableType = editableElement.getAttribute('type');
		const editableValue =
			editableValues[EDITABLE_FRAGMENT_ENTRY_PROCESSOR][editableId];
		const processor = Processors[editableType] || Processors.fallback;

		processor.createEditor(
			editableElement,
			value => {
				processor.render(editableElement, value, editableValue.config);

				let nextEditableValue = {
					...editableValue
				};

				if (segmentsExperienceId) {
					nextEditableValue = {
						...nextEditableValue,

						[segmentsExperienceId]: {
							...(nextEditableValue[segmentsExperienceId] || {}),
							[languageId]: value
						}
					};
				}
				else {
					nextEditableValue = {
						...nextEditableValue,
						[languageId]: value
					};
				}

				dispatch(
					updateEditableValues({
						config,
						editableValues: {
							...editableValues,
							[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {
								...editableValues[
									EDITABLE_FRAGMENT_ENTRY_PROCESSOR
								],
								[editableId]: nextEditableValue
							}
						},
						fragmentEntryLinkId,
						segmentsExperienceId
					})
				);
			},
			() => {
				processor.destroyEditor(editableElement, editableValue.config);
			},
			config
		);

		return () => {
			processor.destroyEditor(editableElement, editableValue.config);
		};
	}, [
		dispatch,
		editableElement,
		editableValues,
		fragmentEntryLinkId,
		languageId,
		segmentsExperienceId
	]);

	return null;
}
