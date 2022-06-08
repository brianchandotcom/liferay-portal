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
import ClayMultiSelect from '@clayui/multi-select';
import React, {useMemo, useState} from 'react';

import useControlledState from '../../../core/hooks/useControlledState';
import {useId} from '../../utils/useId';

export default function CSSClassSelectorField({
	field,
	onValueSelect,
	value: initialValue,
}) {
	const cssClass = useMemo(() => {
		return (
			initialValue?.map((cssClass) => ({
				label: cssClass,
				value: cssClass,
			})) ?? []
		);
	}, [initialValue]);

	const [items, setItems] = useControlledState(cssClass);

	const [value, setValue] = useState('');

	const cssClassesInputId = useId();
	const helpTextId = useId();

	return (
		<ClayForm.Group small>
			<label htmlFor={cssClassesInputId}>
				{Liferay.Language.get('css-classes')}
			</label>

			<ClayMultiSelect
				id={cssClassesInputId}
				items={items}
				onChange={setValue}
				onItemsChange={(items) => {
					setItems(items);

					onValueSelect(
						field.name,
						items.map((item) => item.value)
					);
				}}
				placeholder={
					items.length > 0
						? null
						: Liferay.Language.get('type-to-add-a-class')
				}
				value={value}
			/>

			<div className="mt-1 small text-secondary" id={helpTextId}>
				{Liferay.Language.get(
					'use-a-space-enter-or-comma-to-add-multiple-classes'
				)}
			</div>
		</ClayForm.Group>
	);
}
