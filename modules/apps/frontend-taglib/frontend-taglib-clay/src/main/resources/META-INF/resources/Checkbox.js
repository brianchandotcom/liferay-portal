/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCheckbox} from '@clayui/form';
import React, {useState} from 'react';

export default function Checkbox({
	additionalProps: _additionalProps,
	checked,
	componentId: _componentId,
	cssClass,
	locale: _locale,
	portletId: _portletId,
	portletNamespace: _portletNamespace,
	...otherProps
}) {
	const [isChecked, setIsChecked] = useState(checked);

	return (
		<ClayCheckbox
			checked={isChecked}
			className={cssClass}
			onChange={() => setIsChecked((isChecked) => !isChecked)}
			{...otherProps}
		/>
	);
}
