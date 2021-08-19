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

export default function SeoMapping({
	description,
	fields,
	portletNamespace,
	selectedSource,
	title,
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
						Liferay.Language.get('html-title')
					),
					label: Liferay.Language.get('html-title'),
					name: `${portletNamespace}TypeSettingsProperties--mapped-title--`,
					value: title,
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
					name: `${portletNamespace}TypeSettingsProperties--mapped-description--`,
					value: description,
				},
			]}
			selectedSource={selectedSource}
		/>
	);
}

SeoMapping.propTypes = {
	description: PropTypes.string,
	fields: PropTypes.arrayOf(
		PropTypes.shape({
			key: PropTypes.string,
			label: PropTypes.string,
		})
	).isRequired,
	selectedSource: PropTypes.shape({
		classNameLabel: PropTypes.string,
		classTypeLabel: PropTypes.string,
	}).isRequired,
	title: PropTypes.string,
};
