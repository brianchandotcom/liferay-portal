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

import {FIELD_TYPES} from '../constants';
import MappingInput from './MappingInput';
import MappingSelector from './MappingSelector';

function MappingFields({fields, inputs, selectedSource}) {
	return inputs.map((props) => {
		const filteredFields = fields.filter(
			({type}) => type === props.fieldType
		);

		return props.fieldType === FIELD_TYPES.TEXT ? (
			<MappingInput
				fields={filteredFields}
				key={props.name}
				selectedSource={selectedSource}
				{...props}
			/>
		) : (
			<MappingSelector
				fields={filteredFields}
				key={props.name}
				selectedSource={selectedSource}
				{...props}
			/>
		);
	});
}

MappingFields.propTypes = {
	fields: PropTypes.arrayOf(
		PropTypes.shape({
			key: PropTypes.string,
			label: PropTypes.string,
		})
	).isRequired,
	inputs: PropTypes.arrayOf(
		PropTypes.shape({
			label: PropTypes.string,
			name: PropTypes.string,
			selectedFieldKey: PropTypes.string,
			value: PropTypes.string,
		})
	).isRequired,
};

export default MappingFields;
