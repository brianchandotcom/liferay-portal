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

import {PropTypes} from 'prop-types';
import React from 'react';

import MappingFields from './components/MappingFields';
import lang from './utils/lang';

export default function OpenGraphMapping({
	fields,
	openGraphDescription,
	openGraphImage,
	openGraphImageAlt,
	openGraphTitle,
	portletNamespace,
	selectedSource,
}) {
	return (
		<MappingFields
			fields={fields}
			inputs={[
				{
					fieldType: 'text',
					helpMessage: lang.sub(
						Liferay.Language.get(
							'map-a-x-field-it-will-be-used-as-x'
						),
						Liferay.Language.get('text'),
						Liferay.Language.get('title')
					),
					label: Liferay.Language.get('title'),
					name: `${portletNamespace}TypeSettingsProperties--mapped-openGraphTitle--`,
					value: openGraphTitle,
				},
				{
					component: 'textarea',
					fieldType: 'text',
					helpMessage: lang.sub(
						Liferay.Language.get(
							'map-a-x-field-it-will-be-used-as-x'
						),
						Liferay.Language.get('text'),
						Liferay.Language.get('description')
					),
					label: Liferay.Language.get('description'),
					name: `${portletNamespace}TypeSettingsProperties--mapped-openGraphDescription--`,
					value: openGraphDescription,
				},
				{
					fieldType: 'image',
					helpMessage: lang.sub(
						Liferay.Language.get(
							'map-a-x-field-it-will-be-used-as-x'
						),
						Liferay.Language.get('image'),
						Liferay.Language.get('image')
					),
					label: Liferay.Language.get('image'),
					name: `${portletNamespace}TypeSettingsProperties--mapped-openGraphImage--`,
					selectedFieldKey: openGraphImage,
				},
				{
					component: 'textarea',
					fieldType: 'text',
					helpMessage: lang.sub(
						Liferay.Language.get(
							'map-a-x-field-it-will-be-used-as-x'
						),
						Liferay.Language.get('text'),
						Liferay.Language.get('open-graph-image-alt-description')
					),
					label: Liferay.Language.get(
						'open-graph-image-alt-description'
					),
					name: `${portletNamespace}TypeSettingsProperties--mapped-openGraphImageAlt--`,
					value: openGraphImageAlt,
				},
			]}
			selectedSource={selectedSource}
		/>
	);
}

OpenGraphMapping.propTypes = {
	fields: PropTypes.arrayOf(
		PropTypes.shape({
			key: PropTypes.string,
			label: PropTypes.string,
		})
	).isRequired,
	openGraphDescription: PropTypes.string,
	openGraphImage: PropTypes.string,
	openGraphImageAlt: PropTypes.string,
	openGraphTitle: PropTypes.string,
	selectedSource: PropTypes.shape({
		classNameLabel: PropTypes.string,
		classTypeLabel: PropTypes.string,
	}).isRequired,
};
