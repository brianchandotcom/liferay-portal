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
import React from 'react';

import {getEditableItemPropTypes} from '../../../prop-types/index';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../config/constants/editableFragmentEntryProcessor';
import {EDITABLE_TYPES} from '../../config/constants/editableTypes';
import selectEditableValue from '../../selectors/selectEditableValue';
import selectEditableValues from '../../selectors/selectEditableValues';
import selectSegmentsExperienceId from '../../selectors/selectSegmentsExperienceId';
import {useDispatch, useSelector} from '../../store/index';
import updateEditableValues from '../../thunks/updateEditableValues';
import LinkField, {
	TARGET_OPTIONS,
} from '../fragment-configuration-fields/LinkField';

export default function EditableLinkPanel({item}) {
	const dispatch = useDispatch();
	const state = useSelector((state) => state);
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);

	const editableValues = selectEditableValues(
		state,
		item.fragmentEntryLinkId
	);

	const editableValue = selectEditableValue(
		state,
		item.fragmentEntryLinkId,
		item.editableId,
		EDITABLE_FRAGMENT_ENTRY_PROCESSOR
	);

	const handleValueSelect = (_, nextConfig) => {
		const config = {...nextConfig};

		if (
			Object.keys(nextConfig).length > 0 &&
			item.editableType !== EDITABLE_TYPES.link
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
				segmentsExperienceId,
			})
		);
	};

	return (
		<LinkField
			field={{name: 'link'}}
			onValueSelect={handleValueSelect}
			value={editableValue.config}
		/>
	);
}

EditableLinkPanel.propTypes = {
	item: getEditableItemPropTypes({
		config: PropTypes.oneOfType([
			PropTypes.shape({
				href: PropTypes.string,
				target: PropTypes.oneOf(Object.values(TARGET_OPTIONS)),
			}),
			PropTypes.shape({
				classNameId: PropTypes.string,
				classPK: PropTypes.string,
				fieldId: PropTypes.string,
				target: PropTypes.oneOf(Object.values(TARGET_OPTIONS)),
			}),
			PropTypes.shape({
				mappedField: PropTypes.string,
				target: PropTypes.oneOf(Object.values(TARGET_OPTIONS)),
			}),
		]),
	}),
};
