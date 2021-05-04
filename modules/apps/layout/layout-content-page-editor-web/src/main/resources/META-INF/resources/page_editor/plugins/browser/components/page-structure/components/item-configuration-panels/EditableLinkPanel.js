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

import LinkField from '../../../../../../app/components/fragment-configuration-fields/LinkField';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../../app/config/constants/editableFragmentEntryProcessor';
import {EDITABLE_TYPES} from '../../../../../../app/config/constants/editableTypes';
import {config} from '../../../../../../app/config/index';
import {
	useDispatch,
	useSelector,
	useSelectorCallback,
} from '../../../../../../app/contexts/StoreContext';
import selectEditableValue from '../../../../../../app/selectors/selectEditableValue';
import selectEditableValues from '../../../../../../app/selectors/selectEditableValues';
import selectSegmentsExperienceId from '../../../../../../app/selectors/selectSegmentsExperienceId';
import updateEditableValues from '../../../../../../app/thunks/updateEditableValues';
import {deepEqual} from '../../../../../../app/utils/checkDeepEqual';
import isMapped from '../../../../../../app/utils/editable-value/isMapped';
import {getEditableItemPropTypes} from '../../../../../../prop-types/index';

export default function EditableLinkPanel({item}) {
	const dispatch = useDispatch();
	const languageId = useSelector((state) => state.languageId);
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);

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
			) || {},
		[item.fragmentEntryLinkId, item.editableId],
		deepEqual
	);

	const [linkConfig, setLinkConfig] = useState({});
	const [linkValue, setLinkValue] = useState({href: '', target: ''});
	const [imageConfig, setImageConfig] = useState({});

	useEffect(() => {
		const linkConfig = {
			...(editableValue.config || {}),
		};

		if (Object.keys(linkConfig).length > 0) {
			setImageConfig({
				alt: linkConfig.alt || '',
				imageConfiguration: linkConfig.imageConfiguration || {},
			});

			delete linkConfig.alt;
			delete linkConfig.imageConfiguration;

			setLinkConfig(linkConfig);

			setLinkValue({
				...linkConfig,
				href:
					linkConfig.href ||
					linkConfig[languageId]?.href ||
					linkConfig[config.defaultLanguageId]?.href ||
					'',
				target:
					linkConfig.target ||
					linkConfig[languageId]?.target ||
					linkConfig[config.defaultLanguageId]?.target ||
					'',
			});
		}
		else {
			setImageConfig({});
			setLinkConfig({});
			setLinkValue({href: '', target: ''});
		}
	}, [editableValue.config, languageId]);

	const handleValueSelect = (_, nextLinkConfig) => {
		let nextConfig;

		if (isMapped(nextLinkConfig) || isMapped(linkConfig)) {
			nextConfig = {...imageConfig, ...nextLinkConfig};
		}
		else {
			nextConfig = {
				...imageConfig,
				...(linkConfig || {}),
			};

			if (Object.keys(nextLinkConfig).length) {
				nextConfig = {
					...nextConfig,
					[languageId]: {
						href: nextLinkConfig.href,
						target: nextLinkConfig.target || '',
					},
				};
			}
		}

		if (item.type !== EDITABLE_TYPES.link) {
			nextConfig.mapperType = 'link';
		}

		dispatch(
			updateEditableValues({
				editableValues: {
					...editableValues,
					[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {
						...editableValues[EDITABLE_FRAGMENT_ENTRY_PROCESSOR],
						[item.editableId]: {
							...editableValue,
							config: nextConfig,
						},
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
