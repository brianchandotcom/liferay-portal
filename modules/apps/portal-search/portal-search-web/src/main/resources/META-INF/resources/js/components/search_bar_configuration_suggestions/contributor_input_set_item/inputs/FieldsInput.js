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

import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayMultiSelect from '@clayui/multi-select';
import {ClayTooltipProvider} from '@clayui/tooltip';
import getCN from 'classnames';
import React, {useEffect, useState} from 'react';

function FieldsInput({
	onChange,
	isRequired = false,
	fields = [],
	touched,
	onBlur,
}) {
	const [value, setValue] = useState('');
	const [items, setItems] = useState(
		fields.map((field) => ({
			label: field,
			value: field,
		}))
	);

	const _handleBlur = () => {
		if (value) {
			setItems([
				...items,
				{
					label: value,
					value,
				},
			]);

			setValue('');
		}

		onBlur();
	};

	/**
	 * Apply useEffect to perform `onChange` because `attributes` might not be
	 * the most up-to-date inside `onChange` when it is passed into a function
	 * for `onItemsChange`.
	 */
	useEffect(() => {
		onChange(items.map((item) => item.value));
	}, [items]); //eslint-disable-line

	return (
		<ClayInput.GroupItem
			className={getCN({
				'has-error': isRequired && !items.length && touched,
			})}
		>
			<label>
				{Liferay.Language.get('fields')}

				<ClayTooltipProvider>
					<span
						className="c-ml-2"
						data-tooltip-align="top"
						title={Liferay.Language.get('fields-suggestion-help')}
					>
						<ClayIcon symbol="question-circle-full" />
					</span>
				</ClayTooltipProvider>
			</label>

			<ClayMultiSelect
				items={items}
				onBlur={_handleBlur}
				onChange={setValue}
				onItemsChange={setItems}
				value={value}
			/>
		</ClayInput.GroupItem>
	);
}

export default FieldsInput;
