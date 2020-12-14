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

import React, {useEffect, useState} from 'react';

import LinkField from '../../../../app/components/fragment-configuration-fields/LinkField';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../app/config/constants/editableFragmentEntryProcessor';
import {EDITABLE_TYPES} from '../../../../app/config/constants/editableTypes';
import {config} from '../../../../app/config/index';
import selectEditableValue from '../../../../app/selectors/selectEditableValue';
import selectEditableValues from '../../../../app/selectors/selectEditableValues';
import selectSegmentsExperienceId from '../../../../app/selectors/selectSegmentsExperienceId';
import {
	useDispatch,
	useSelector,
	useSelectorCallback,
} from '../../../../app/store/index';
import updateEditableValues from '../../../../app/thunks/updateEditableValues';
import {deepEqual} from '../../../../app/utils/checkDeepEqual';
import {getEditableItemPropTypes} from '../../../../prop-types/index';

export default function EditableLinkPanel({item}) {
	const dispatch = useDispatch();
	const languageId = useSelector((state) => state.languageId);
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);

	const editableValues = useSelectorCallback(
		(state) => selectEditableValues(state, item.fragmentEntryLinkId),
		[item.fragmentEntryLinkId]
	);

	const editableValue = useSelectorCallback(
		(state) => {
			const editableValue =
				selectEditableValue(
					state,
					item.fragmentEntryLinkId,
					item.editableId,
					EDITABLE_FRAGMENT_ENTRY_PROCESSOR
				) || {};

			if (!editableValue.config) {
				editableValue.config = {};
			}

			return editableValue;
		},
		[item.fragmentEntryLinkId, item.editableId],
		deepEqual
	);

	const [linkValue, setLinkValue] = useState({});

	useEffect(
		() =>
			setLinkValue(
				editableValue.config[languageId] ||
					editableValue.config[config.defaultLanguageId] ||
					editableValue.config
			),
		[editableValue.config, languageId]
	);

	const handleValueSelect = (_, nextConfig) => {
		const config = {
			...editableValue.config,
			[languageId]: nextConfig,
		};

		if (
			Object.keys(nextConfig).length > 0 &&
			item.type !== EDITABLE_TYPES.link
		) {
			config.mapperType = 'link';
		}

		dispatch(
			updateEditableValues({
				editableValues: {
					...editableValues,
					[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {
						...editableValues[EDITABLE_FRAGMENT_ENTRY_PROCESSOR],
						[item.editableId]: {...editableValue, config},
					},
				},
				fragmentEntryLinkId: item.fragmentEntryLinkId,
				languageId,
				segmentsExperienceId,
			})
		);
	};

	return (
		<LinkField
			field={{name: 'link'}}
			onValueSelect={handleValueSelect}
			value={linkValue}
		/>
	);
}

EditableLinkPanel.propTypes = {
	item: getEditableItemPropTypes(),
};
