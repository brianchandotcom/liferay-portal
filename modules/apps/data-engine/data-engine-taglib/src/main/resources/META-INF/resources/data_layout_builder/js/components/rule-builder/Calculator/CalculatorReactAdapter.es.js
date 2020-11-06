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

import {ClayIconSpriteContext} from '@clayui/icon';
import {getConnectedReactComponentAdapter} from 'dynamic-data-mapping-form-renderer';
import Soy from 'metal-soy';
import React, {forwardRef} from 'react';

import Calculator from './Calculator.es';
import templates from './CalculatorReactAdapter.soy';

/**
 * This creates a compatibility layer for the Field component on React, allowing
 * it to be called by Metal+Soy file.
 */
export const CalculatorReactAdapter = forwardRef(
	(
		{
			expression,
			fields,
			functions,
			index,
			instance,
			resultSelected,
			...field
		},
		ref
	) => {
		const emit = (name, event) => instance.emit(name, event);

		return (
			<ClayIconSpriteContext.Provider value={field.spritemap}>
				<Calculator
					expression={expression}
					fields={fields}
					functions={functions}
					index={index}
					onEditExpression={(event) => emit('editExpression', event)}
					ref={ref}
					resultSelected={resultSelected}
					{...field}
				/>
			</ClayIconSpriteContext.Provider>
		);
	}
);

const ReactComponentAdapter = getConnectedReactComponentAdapter(
	CalculatorReactAdapter
);

Soy.register(ReactComponentAdapter, templates);

export default ReactComponentAdapter;
