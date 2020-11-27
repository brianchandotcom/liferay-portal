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

import ClayForm from '@clayui/form';
import {RulesSupport} from 'dynamic-data-mapping-form-builder';
import {
	Field,
	PageProvider,
	useFieldTypesResource,
} from 'dynamic-data-mapping-form-renderer';
import React, {useMemo} from 'react';

function CalculatorDisplay({expression, fields}) {
	const {resource: fieldTypes} = useFieldTypesResource();

	const value = useMemo(() => {
		const newMaskedExpression = expression.replace(/[[\]]/g, '');

		return (
			RulesSupport.replaceFieldNameByFieldLabel(
				newMaskedExpression,
				fields
			) ?? newMaskedExpression
		);
	}, [expression, fields]);

	return (
		<ClayForm.Group>
			<PageProvider value={{fieldTypes}}>
				<Field
					field={{
						displayStyle: 'multiline',
						name: 'calculator-expression',
						placeholder: Liferay.Language.get(
							'the-expression-will-be-displayed-here'
						),
						readOnly: true,
						showLabel: false,
						type: 'text',
						value,
					}}
				/>
			</PageProvider>
		</ClayForm.Group>
	);
}

export default CalculatorDisplay;
