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

import {ClayCardWithHorizontal} from '@clayui/card';
import React, {useCallback, useEffect, useState} from 'react';

import {EVENT_MANAGEMENT_TOOLBAR_TOGGLE_ALL_ITEMS} from '../constants';
import normalizeDropdownItems from '../normalize_dropdown_items';

export default function HorizontalCard({
	actions,
	additionalProps: _additionalProps,
	componentId: _componentId,
	cssClass,
	disabled,
	href,
	inputName,
	inputValue,
	locale: _locale,
	portletId: _portletId,
	portletNamespace: _portletNamespace,
	selectable,
	selected: initialSelected,
	symbol,
	title,
	...otherProps
}) {
	const [selected, setSelected] = useState(initialSelected);

	const handleToggleAllItems = useCallback(
		({checked}) => {
			setSelected(checked);
		},
		[setSelected]
	);

	useEffect(() => {
		Liferay.on(
			EVENT_MANAGEMENT_TOOLBAR_TOGGLE_ALL_ITEMS,
			handleToggleAllItems
		);

		return () => {
			Liferay.detach(
				EVENT_MANAGEMENT_TOOLBAR_TOGGLE_ALL_ITEMS,
				handleToggleAllItems
			);
		};
	}, [handleToggleAllItems]);

	return (
		<ClayCardWithHorizontal
			actions={normalizeDropdownItems(actions)}
			checkboxProps={{
				name: inputName ?? '',
				value: inputValue ?? '',
			}}
			className={cssClass}
			disabled={disabled}
			href={href}
			interactive={false}
			onSelectChange={
				selectable
					? (selected) => {
							setSelected(selected);
					  }
					: null
			}
			selectable={selectable}
			selected={selected}
			symbol={symbol}
			title={title}
			{...otherProps}
		/>
	);
}
