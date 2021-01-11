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

import {ClayCardWithUser} from '@clayui/card';
import React, {useState} from 'react';

import getDataAttributes from '../get_data_attributes';

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
	const [selected, setSelected] = useState(initialSelected);

	return (
		<ClayCardWithUser
			actions={actions?.map(({data, ...rest}) => {
				const dataAttributes = getDataAttributes(data);

				return {
					...dataAttributes,
					...rest,
				};
			})}
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
