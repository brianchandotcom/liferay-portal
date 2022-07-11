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

import {ClayCheckbox} from '@clayui/form';
import React, {useState} from 'react';

const CHECKBOX_DEFAULT_VALUE = 'on';

const CHECKBOX_STATUS = {
	checked: 'checked',
	indeterminate: 'indeterminate',
	unchecked: 'unchecked',
};

export default function PermissionsCheckbox({
	additionalProps: _additionalProps,
	checked,
	componentId: _componentId,
	cssClass,
	indeterminate,
	indeterminateValue = 'indeterminate',
	locale: _locale,
	portletId: _portletId,
	portletNamespace: _portletNamespace,
	value,
	...otherProps
}) {
	const [checkboxStatus, setCheckboxStatus] = useState(
		indeterminate
			? CHECKBOX_STATUS.indeterminate
			: checked
			? CHECKBOX_STATUS.checked
			: CHECKBOX_STATUS.unchecked
	);

	return (
		<ClayCheckbox
			checked={checkboxStatus !== CHECKBOX_STATUS.unchecked}
			className={cssClass}
			indeterminate={checkboxStatus === CHECKBOX_STATUS.indeterminate}
			inline
			onChange={() => {
				setCheckboxStatus((checkboxStatus) =>
					checkboxStatus === CHECKBOX_STATUS.unchecked
						? CHECKBOX_STATUS.checked
						: CHECKBOX_STATUS.unchecked
				);
			}}
			value={
				checkboxStatus === CHECKBOX_STATUS.indeterminate
					? indeterminateValue
					: value
					? value
					: CHECKBOX_DEFAULT_VALUE
			}
			{...otherProps}
		/>
	);
}
