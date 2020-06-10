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

import MappingInputs from './components/MappingInputs';

function SeoMapping({
	description,
	fields,
	portletNamespace,
	selectedSource,
	title,
}) {
	return (
		<MappingInputs
			fields={fields}
			inputs={[
				{
					label: Liferay.Language.get('html-title'),
					name: `${portletNamespace}title`,
					selectedFieldKey: title,
				},
				{
					label: Liferay.Language.get('description'),
					name: `${portletNamespace}description`,
					selectedFieldKey: description,
				},
			]}
			selectedSource={selectedSource}
		/>
	);
}

SeoMapping.propTypes = {
	description: PropTypes.string.isRequired,
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
	title: PropTypes.string.isRequired,
};

export default function (props) {
	return (
		<SeoMapping
			{...props}
			portletNamespace={`_${props.portletNamespace}_`}
		/>
	);
}
