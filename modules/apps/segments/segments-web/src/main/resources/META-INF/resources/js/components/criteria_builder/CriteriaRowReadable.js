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

import {PROPERTY_TYPES} from '../../utils/constants';
import {unescapeSingleQuotes} from '../../utils/odata';
import {dateToInternationalHuman} from '../../utils/utils';

export default function CriteriaRowReadable({
	criterion = {},
	selectedOperator,
	selectedProperty,
}) {
	const _renderCriteriaString = ({
		operatorLabel,
		propertyLabel,
		type,
		value,
	}) => {
		let parsedValue = null;

		if (type === PROPERTY_TYPES.DATE) {
			parsedValue = dateToInternationalHuman(value.replaceAll('-', '/'));
		}
		else if (type === PROPERTY_TYPES.DATE_TIME) {
			parsedValue = dateToInternationalHuman(value);
		}
		else {
			parsedValue = value;
		}

		return (
			<span>
				{propertyLabel && (
					<b className="mr-1 text-dark">{propertyLabel}</b>
				)}

				{operatorLabel && (
					<span className="mr-1 operator">{operatorLabel}</span>
				)}

				<b>{unescapeSingleQuotes(parsedValue)}</b>
			</span>
		);
	};

	const value = criterion ? criterion.value : '';
	const operatorLabel = selectedOperator ? selectedOperator.label : '';
	const propertyLabel = selectedProperty ? selectedProperty.label : '';

	return (
		<span className="criterion-string">
			{_renderCriteriaString({
				operatorLabel,
				propertyLabel,
				type: selectedProperty.type,
				value: criterion.displayValue || value,
			})}
		</span>
	);
}

CriteriaRowReadable.propTypes = {
	criterion: PropTypes.object.isRequired,
	selectedOperator: PropTypes.object,
	selectedProperty: PropTypes.object.isRequired,
};
