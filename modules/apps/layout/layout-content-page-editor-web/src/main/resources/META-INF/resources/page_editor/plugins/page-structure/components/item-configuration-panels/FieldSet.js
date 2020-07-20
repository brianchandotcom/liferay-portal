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

import {FRAGMENT_CONFIGURATION_FIELDS} from '../../../../app/components/fragment-configuration-fields/index';
import {ConfigurationFieldPropTypes} from '../../../../prop-types/index';

export const FieldSet = ({
	configurationValues,
	fields,
	label,
	onValueSelect,
}) => {
	return (
		<>
			{label && <p className="mb-3 sheet-subtitle">{label}</p>}

			{fields.map((field, index) => {
				const FieldComponent =
					field.type && FRAGMENT_CONFIGURATION_FIELDS[field.type];

				const fieldValue = configurationValues[field.name];

				const visible =
					!field.dependencies ||
					field.dependencies.every(
						(dependency) =>
							configurationValues[dependency.styleName] ===
							dependency.value
					);

				return (
					visible && (
						<FieldComponent
							field={field}
							key={index}
							onValueSelect={onValueSelect}
							value={fieldValue}
						/>
					)
				);
			})}
		</>
	);
};

FieldSet.propTypes = {
	configurationValues: PropTypes.object,
	fields: PropTypes.arrayOf(PropTypes.shape(ConfigurationFieldPropTypes)),
	label: PropTypes.string,
	onValueSelect: PropTypes.func.isRequired,
};
