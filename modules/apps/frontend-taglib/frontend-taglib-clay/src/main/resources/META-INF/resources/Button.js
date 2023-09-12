/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React from 'react';

export default function Button({
	additionalProps: _additionalProps,
	componentId: _componentId,
	cssClass,
	icon,
	label,
	locale: _locale,
	portletId: _portletId,
	portletNamespace: _portletNamespace,
	swapIconSide,
	...otherProps
}) {
	return (
		<ClayButton className={cssClass} {...otherProps}>
			{icon && !swapIconSide && (
				<span
					className={classNames('inline-item', {
						'inline-item-before': label,
					})}
				>
					<ClayIcon symbol={icon} />
				</span>
			)}

			{label}

			{icon && swapIconSide && (
				<span
					className={classNames('inline-item', {
						'inline-item-after': label,
					})}
				>
					<ClayIcon symbol={icon} />
				</span>
			)}
		</ClayButton>
	);
}
