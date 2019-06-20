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

/**
 * A Property is a reference to define behaviour of each
 * criterion in the Criteria Editor.
 *
 */
const propertyShape = PropTypes.shape({
	entityUrl: PropTypes.string,
	label: PropTypes.string,
	name: PropTypes.string.isRequired,
	options: PropTypes.array,
	type: PropTypes.string.isRequired
});

const conjunctionShape = PropTypes.shape({
	label: PropTypes.string.isRequired,
	name: PropTypes.string.isRequired
});

/**
 * A Criterion is the unit that forms a rule.
 *
 * {propertyName} works as an identifier to related to a property
 */
const criterionShape = PropTypes.shape({
	displayValue: PropTypes.string,
	operatorName: PropTypes.string,
	propertyName: PropTypes.string,
	value: PropTypes.oneOfType([
		PropTypes.array,
		PropTypes.number,
		PropTypes.string
	])
});

/**
 * A Criteria is contains and defines the relationship of a set of Criterion.
 * Those Criterion can be contained as well in a Criteria.
 */
const criteriaShape = PropTypes.shape({
	conjunctionName: PropTypes.string,
	groupId: PropTypes.string
});

criteriaShape.items = PropTypes.arrayOf(
	PropTypes.oneOfType([criteriaShape, criterionShape])
);

/**
 * The Initial Contributor is a way to define the Contributor
 * that the server provides at the app initialization
 * to the app. The front has to parse it.
 *
 * - conjunctionId: the value of the congunction
 * - conjunctionInputId: an identifier for the back-fornt relationship
 * - initialQuery: the Criteria in a oData query format
 * - inputId: an identifier for the back-fornt relationship
 * - propertyKey: an indentifier to help get information about the properties in its Property Group
 */
const initialContributorShape = PropTypes.shape({
	conjunctionId: PropTypes.string.isRequired,
	conjunctionInputId: PropTypes.string.isRequired,
	initialQuery: PropTypes.oneOfType([
		PropTypes.string,
		PropTypes.shape(null)
	]),
	inputId: PropTypes.string.isRequired,
	propertyKey: PropTypes.string.isRequired
});

/**
 * A Contributor contains a set Criterias, Properties and identifiers.
 *
 * - conjuntionId: the value of the congunction
 * - conjunctionInputId: an identifier for the back-fornt relationship
 * - criteriaMap: a Criteria frontend consumable
 * - entityName: an identifier for the back-front relationship
 * - inputId: an identifier for the back-fornt relationship
 * - modelLabel: a human friendly name of the type of Contributor
 * - properties: a list of properties that can conform the Criteria of this Contributor
 * - propertyKey: an indentifier to help get information about the properties in its Property Group
 * - query: the Criteria in oData query format
 */
const contributorShape = PropTypes.shape({
	conjunctionId: PropTypes.string,
	conjunctionInputId: PropTypes.string,
	criteriaMap: PropTypes.oneOfType([criteriaShape, PropTypes.shape(null)]),
	entityName: PropTypes.string,
	inputId: PropTypes.string,
	modelLabel: PropTypes.string,
	properties: PropTypes.arrayOf(propertyShape),
	propertyKey: PropTypes.string,
	query: PropTypes.string
});

/**
 * An Operator defines the relationship between a property and a value in a Criterion
 * - label: human friendly value
 * - name: computer frinedly value
 */
const operatorShape = PropTypes.shape({
	label: PropTypes.string.isRequired,
	name: PropTypes.string.isRequired
});

/**
 * A Property Group contains a set of Properties that can be used
 * to create a Criteria in a Contributor
 */
const propertyGroupShape = PropTypes.shape({
	entityName: PropTypes.string.isRequired,
	name: PropTypes.string.isRequired,
	properties: PropTypes.arrayOf(propertyShape).isRequired,
	propertyKey: PropTypes.string.isRequired
});

/**
 * The Property Types contains a relationship of the types of properties
 * and their compatible operators
 */
const propertyTypesShape = PropTypes.shape({
	boolean: PropTypes.arrayOf(PropTypes.string).isRequired,
	date: PropTypes.arrayOf(PropTypes.string).isRequired,
	double: PropTypes.arrayOf(PropTypes.string).isRequired,
	id: PropTypes.arrayOf(PropTypes.string).isRequired,
	integer: PropTypes.arrayOf(PropTypes.string).isRequired,
	string: PropTypes.arrayOf(PropTypes.string).isRequired
});

export {
	propertyTypesShape,
	conjunctionShape,
	criteriaShape,
	initialContributorShape,
	criterionShape,
	contributorShape,
	operatorShape,
	propertyGroupShape,
	propertyShape
};
