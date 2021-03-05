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
import React, {useEffect, useState} from 'react';

import LinkField, {
	TARGET_OPTIONS,
} from '../../../../app/components/fragment-configuration-fields/LinkField';
import {config} from '../../../../app/config/index';
import selectSegmentsExperienceId from '../../../../app/selectors/selectSegmentsExperienceId';
import {useDispatch, useSelector} from '../../../../app/store/index';
import updateItemConfig from '../../../../app/thunks/updateItemConfig';
import {getLayoutDataItemPropTypes} from '../../../../prop-types/index';

export default function ContainerGeneralPanel({item}) {
	const dispatch = useDispatch();
	const languageId = useSelector((state) => state.languageId);
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);

	const [linkValue, setLinkValue] = useState({});

	useEffect(
		() =>
			setLinkValue(
				item.config?.link[languageId] ||
					item.config?.link[config.defaultLanguageId] ||
					item.config?.link ||
					{}
			),
		[item.config?.link, languageId]
	);

	const handleValueSelect = (_, linkConfig) => {
		const nextLinkConfig = {
			...item.config?.link,
			[languageId]: linkConfig,
		};

		dispatch(
			updateItemConfig({
				itemConfig: {
					...item.config,
					link: nextLinkConfig,
				},
				itemId: item.itemId,
				segmentsExperienceId,
			})
		);
	};

	return (
		<LinkField
			field={{name: 'link'}}
			onValueSelect={handleValueSelect}
			value={linkValue || {}}
		/>
	);
}

ContainerGeneralPanel.propTypes = {
	item: getLayoutDataItemPropTypes({
		config: PropTypes.shape({
			link: PropTypes.oneOfType([
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
	}),
};
