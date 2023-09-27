/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import React from 'react';
import {WarningBadge} from '../../Badges/Warning';

export function InputAreaWithError({children, className, error}) {
	return (
		<div
			className={classNames('input-area d-block', {
				[className]: className,
				'has-error': error,
			})}
		>
			{children}

			{error?.message && <WarningBadge>{error.message}</WarningBadge>}
		</div>
	);
}
