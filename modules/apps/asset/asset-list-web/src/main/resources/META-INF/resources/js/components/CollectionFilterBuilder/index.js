/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {generateConditionId, ConditionBuilder} from './condition_builder/ConditionBuilder';
import {DefaultValueInput} from './condition_builder/DefaultValueInput';
import React, {useState} from 'react';

import {getCollectionOperators} from './operators';

/**
 * Collections-specific wrapper around the generic ConditionBuilder.
 *
 * Restricts operators to the OData set required by LPD-74731 and serializes
 * the current value into a hidden input so the typeSettings handler picks it
 * up on form submit.
 *
 * @param {Object} props
 * @param {'all'|'any'} [props.initialConditionType='all']
 * @param {Array<{id:string, propertyName?:string, operatorName?:string, value?:string}>} [props.initialConditions]
 * @param {string} props.namespace - portlet namespace, used for the hidden input name
 * @param {(state: {conditionType:'all'|'any', conditions:Array}) => void} [props.onChange]
 * @param {Array<{name:string, label:string, type:string, options?:Array}>} props.properties
 */
export default function CollectionFilterBuilder({
	initialConditionType = 'all',
	initialConditions,
	namespace,
	onChange,
	properties,
}) {
	const [conditions, setConditions] = useState(
		initialConditions?.length
			? initialConditions
			: [{id: generateConditionId()}]
	);

	const [conditionType, setConditionType] = useState(initialConditionType);

	const handleChange = (newConditions, newType) => {
		setConditions(newConditions);
		setConditionType(newType);

		onChange?.({conditionType: newType, conditions: newConditions});
	};

	return (
		<>
			<ConditionBuilder
				conditions={conditions}
				conditionType={conditionType}
				getOperators={getCollectionOperators}
				onChange={handleChange}
				properties={properties}
				renderValueInput={DefaultValueInput}
				showConjunctionPicker
			/>

			<input
				name={`${namespace}fieldCriteria`}
				type="hidden"
				value={JSON.stringify({conditionType, conditions})}
			/>
		</>
	);
}
