/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCardWithUser} from '@clayui/card';
import React, {useCallback, useEffect, useMemo, useState} from 'react';

import {EVENT_MANAGEMENT_TOOLBAR_TOGGLE_ALL_ITEMS} from '../constants';
import normalizeDropdownItems from '../normalize_dropdown_items';

export default function UserCard({
	actions,
	additionalProps: _additionalProps,
	componentId: _componentId,
	cssClass,
	inputName = '',
	inputValue = '',
	locale: _locale,
	portletId: _portletId,
	portletNamespace: _portletNamespace,
	selectable,
	selected: initialSelected,
	symbol,
	...otherProps
}) {
	const normalizedActions = useMemo(() => normalizeDropdownItems(actions), [
		actions,
	]);

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
		<ClayCardWithUser
			actions={normalizedActions}
			checkboxProps={{
				name: inputName ?? '',
				value: inputValue ?? '',
			}}
			className={cssClass}
			onSelectChange={
				selectable
					? () => {
							setSelected(!selected);
					  }
					: null
			}
			selected={selected}
			userSymbol={symbol}
			{...otherProps}
		/>
	);
}
