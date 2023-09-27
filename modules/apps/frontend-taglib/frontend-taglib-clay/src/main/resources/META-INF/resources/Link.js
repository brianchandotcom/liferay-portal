/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import classNames from 'classnames';
import React from 'react';

export default function Link({
	additionalProps: _additionalProps,
	componentId: _componentId,
	cssClass,
	icon,
	label,
	locale: _locale,
	portletId: _portletId,
	portletNamespace: _portletNamespace,
	...otherProps
}) {
	return (
		<ClayLink className={cssClass} {...otherProps}>
			{icon && (
				<span
					className={classNames('inline-item', {
						'inline-item-before': label,
					})}
				>
					<ClayIcon symbol={icon} />
				</span>
			)}

			{label}
		</ClayLink>
	);
}
