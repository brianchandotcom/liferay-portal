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

import {ClaySelectWithOption} from '@clayui/form';
import classNames from 'classnames';
import React from 'react';

export default function Select({
	additionalProps: _additionalProps,
	componentId: _componentId,
	containerCssClass,
	cssClass,
	id,
	label,
	locale: _locale,
	name,
	options,
	portletId: _portletId,
	portletNamespace: _portletNamespace,
	...otherProps
}) {
	const defaultValue = options.find((option) => option.selected)?.value;

	return (
		<div className={classNames('form-group', containerCssClass)}>
			{label && <label htmlFor={id || name}>{label}</label>}

			<ClaySelectWithOption
				className={cssClass}
				defaultValue={defaultValue}
				id={id || name}
				name={name}
				options={options.map((option) => {
					return {label: option.label, value: option.value};
				})}
				{...otherProps}
			/>
		</div>
	);
}
