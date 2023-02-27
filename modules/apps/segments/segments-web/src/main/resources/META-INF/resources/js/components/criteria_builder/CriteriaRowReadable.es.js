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
import React, {Component} from 'react';

import {
	DATE_OPERATORS,
	HAS_OPERATORS,
	PROPERTY_TYPES,
	SINCE_VALUES,
	SUPPORTED_EVENT_DATE_OPERATORS,
	SUPPORTED_EVENT_OPERATORS,
	SUPPORTED_PROPERTY_TYPES,
} from '../../utils/constants';
import {unescapeSingleQuotes} from '../../utils/odata.es';
import {
	dateToInternationalHuman,
	getSupportedOperatorsFromEvent,
} from '../../utils/utils.es';

class CriteriaRowReadable extends Component {
	static propTypes = {
		criterion: PropTypes.object.isRequired,
		selectedOperator: PropTypes.object,
		selectedProperty: PropTypes.object.isRequired,
	};

	static defaultProps = {
		criterion: {},
	};

	_renderEventDateString = (criterion) => {
		const dateOperators = getSupportedOperatorsFromEvent(
			SUPPORTED_EVENT_DATE_OPERATORS,
			SUPPORTED_PROPERTY_TYPES,
			'DATE'
		);

		const isEverOperator = !criterion.day;

		if (isEverOperator) {
			return dateOperators.find(
				(operator) => operator.name === DATE_OPERATORS.EVER
			)?.label;
		}

		const isBetweenOperator =
			criterion?.day?.operatorName === DATE_OPERATORS.BETWEEN;
		const isSinceOperator = SINCE_VALUES[criterion?.day?.value]
			? true
			: false;

		const dateOperatorLabel = dateOperators.find(
			(operator) => operator.name === criterion?.day?.operatorName
		)?.label;

		if (isBetweenOperator) {
			const startParsedValue = dateToInternationalHuman(
				criterion?.day?.value?.start
			);
			const endParsedValue = dateToInternationalHuman(
				criterion?.day?.value?.end
			);

			return (
				<span>
					<span className="mr-1 operator">{dateOperatorLabel}</span>

					<b className="text-lowercase">{`${unescapeSingleQuotes(
						startParsedValue
					)} ${Liferay.Language.get('to')} ${unescapeSingleQuotes(
						endParsedValue
					)}`}</b>
				</span>
			);
		}

		const operatorLabel = isSinceOperator
			? Liferay.Language.get('since')
			: dateOperatorLabel;
		const value = isSinceOperator
			? SINCE_VALUES[criterion?.day?.value].toLowerCase()
			: criterion?.day?.value;

		return this._renderCriteriaString({
			operatorLabel,
			type: isSinceOperator ? PROPERTY_TYPES.STRING : PROPERTY_TYPES.DATE,
			value,
		});
	};

	_renderEventCriteriaString = ({criterion, propertyLabel}) => {
		const notOperators = getSupportedOperatorsFromEvent(
			SUPPORTED_EVENT_OPERATORS,
			SUPPORTED_PROPERTY_TYPES,
			'NOT'
		);

		const notOperatorKey = criterion.operatorNot
			? HAS_OPERATORS.NOT_HAS
			: HAS_OPERATORS.HAS;

		const notOperatorLabel = notOperators.find(
			(operator) => operator.name === notOperatorKey
		)?.label;

		const integerOperators = getSupportedOperatorsFromEvent(
			SUPPORTED_EVENT_OPERATORS,
			SUPPORTED_PROPERTY_TYPES,
			'INTEGER'
		);

		const integerOperatorLabel = integerOperators.find(
			(operator) => operator.name === criterion.operatorName
		)?.label;

		const eventCriteriaPart = this._renderCriteriaString({
			propertyLabel,
			value: criterion.assetId,
		});

		const dateCriteriaPart = this._renderEventDateString(criterion);

		return (
			<span>
				<b className="mr-1 text-dark">{Liferay.Language.get('user')}</b>

				<span className="mr-1 operator">{notOperatorLabel}</span>

				{eventCriteriaPart}

				<span className="ml-1 mr-1 operator">
					{integerOperatorLabel}
				</span>

				<b className="mr-1 text-lowercase">{criterion.value}</b>

				<span className="mr-1 operator">
					{Liferay.Language.get('times')}
				</span>

				{dateCriteriaPart}
			</span>
		);
	};

	_renderCriteriaString = ({operatorLabel, propertyLabel, type, value}) => {
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

	render() {
		const {criterion, selectedOperator, selectedProperty} = this.props;
		const value = criterion ? criterion.value : '';
		const operatorLabel = selectedOperator ? selectedOperator.label : '';
		const propertyLabel = selectedProperty ? selectedProperty.label : '';

		return (
			<span className="criterion-string">
				{selectedProperty.type === PROPERTY_TYPES.EVENT
					? this._renderEventCriteriaString({
							criterion,
							propertyLabel,
					  })
					: this._renderCriteriaString({
							operatorLabel,
							propertyLabel,
							type: selectedProperty.type,
							value: criterion.displayValue || value,
					  })}
			</span>
		);
	}
}

export default CriteriaRowReadable;
