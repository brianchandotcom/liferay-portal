/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
