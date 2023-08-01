/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			parsedValue = dateToInternationalHuman(
				value.replaceAll
					? value.replaceAll('-', '/')
					: value.replace(/-/g, '/')
			);
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
					<b className="c-mr-1 font-weight-bold text-dark">
						{propertyLabel}
					</b>
				)}

				{operatorLabel && (
					<span className="c-mr-1 font-weight-bold operator">
						{operatorLabel}
					</span>
				)}

				<b>{unescapeSingleQuotes(parsedValue)}</b>
			</span>
		);
	};

	const value = criterion ? criterion.value : '';
	const operatorLabel = selectedOperator ? selectedOperator.label : '';
	const propertyLabel = selectedProperty ? selectedProperty.label : '';

	return (
		<span className="c-ml-2 criterion-string">
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
