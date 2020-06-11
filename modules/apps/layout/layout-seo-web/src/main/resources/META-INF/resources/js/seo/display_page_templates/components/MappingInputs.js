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

import MappingInput from './MappingInput';

function MappingInputs({fields, inputs, selectedSource}) {
	return (
		<>
			{inputs.map((props) => (
				<MappingInput
					initialFields={fields}
					key={props.name}
					selectedSource={selectedSource}
					{...props}
				/>
			))}
		</>
	);
}

MappingInputs.propTypes = {
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
		})
	).isRequired,
};

export default MappingInputs;
