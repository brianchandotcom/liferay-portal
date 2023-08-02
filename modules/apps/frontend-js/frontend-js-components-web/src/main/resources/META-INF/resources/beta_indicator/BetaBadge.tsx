/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import React from 'react';

import './BetaBadge.scss';

export const betaClassNames = 'btn-xs btn-beta beta-rounded-circle ml-2';

export default function BetaBadge({standalone = true}) {
	return (
		<span
			className={classNames('inline-item', {
				[`${betaClassNames}`]: standalone,
			})}
		>
			<b>Beta</b>
		</span>
	);
}
