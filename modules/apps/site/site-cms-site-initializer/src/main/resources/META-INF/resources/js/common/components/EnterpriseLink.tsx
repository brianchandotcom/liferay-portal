/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import {sub} from 'frontend-js-web';
import React from 'react';

import {ENTERPRISE_URL} from '../utils/constants';

export default function EnterpriseLink({className}: {className?: string}) {
	return (
		<ClayLink
			aria-label={sub(
				Liferay.Language.get('x-opens-new-window'),
				Liferay.Language.get('get-enterprise-details')
			)}
			className={className}
			href={ENTERPRISE_URL}
			rel="noopener noreferrer"
			target="_blank"
		>
			<span className="d-inline">
				{Liferay.Language.get('get-enterprise-details')}

				<ClayIcon className="ml-2" symbol="shortcut" />
			</span>
		</ClayLink>
	);
}
