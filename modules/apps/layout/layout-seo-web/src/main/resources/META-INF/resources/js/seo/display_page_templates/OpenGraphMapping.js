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

import MappingField from './components/MappingField';

function OpenGraphMapping({
	fields,
	openGraphDescription,
	openGraphTitle,
	portletNamespace,
	selectedSource,
}) {
	return (
		<>
			<MappingField
				fields={fields}
				label={Liferay.Language.get('title')}
				name={`${portletNamespace}openGraphTitle`}
				selectedField={
					fields.find(({key}) => key === openGraphTitle) || fields[0]
				}
				selectedSource={selectedSource}
			/>

			<MappingField
				fields={fields}
				label={Liferay.Language.get('description')}
				name={`${portletNamespace}openGraphDescription`}
				selectedField={
					fields.find(({key}) => key === openGraphDescription) ||
					fields[0]
				}
				selectedSource={selectedSource}
			/>
		</>
	);
}

OpenGraphMapping.propTypes = {
	fields: PropTypes.arrayOf(
		PropTypes.shape({
			key: PropTypes.string,
			label: PropTypes.string,
		})
	).isRequired,
	openGraphDescription: PropTypes.string.isRequired,
	openGraphTitle: PropTypes.string.isRequired,
};

export default function (props) {
	return (
		<OpenGraphMapping
			{...props}
			portletNamespace={`_${props.portletNamespace}_`}
		/>
	);
}
