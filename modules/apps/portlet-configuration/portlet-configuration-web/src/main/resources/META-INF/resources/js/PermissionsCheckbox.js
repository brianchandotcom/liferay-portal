/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCheckbox} from '@clayui/form';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

export default function PermissionsCheckbox({
	checked: initialChecked,
	componentId: _componentId,
	indeterminate: initialIndeterminate,
	locale: _locale,
	portletId: _portletId,
	portletNamespace: _portletNamespace,
	...otherProps
}) {
	const [checked, setChecked] = useState(
		Boolean(initialChecked || initialIndeterminate)
	);
	const [indeterminate, setIndeterminate] = useState(
		Boolean(initialIndeterminate)
	);
	const [value, setValue] = useState(
		initialIndeterminate ? 'indeterminate' : ''
	);

	return (
		<ClayCheckbox
			checked={checked}
			indeterminate={indeterminate}
			inline
			onChange={() => {
				setChecked((prevCheckedState) => !prevCheckedState);

				if (indeterminate) {
					setIndeterminate(false);
					setValue('');
				}
			}}
			value={value}
			{...otherProps}
		/>
	);
}

PermissionsCheckbox.propTypes = {
	checked: PropTypes.bool,
	indeterminate: PropTypes.bool,
};
