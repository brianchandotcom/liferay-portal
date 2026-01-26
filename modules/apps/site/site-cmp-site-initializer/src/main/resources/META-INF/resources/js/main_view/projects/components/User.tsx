/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import './User.scss';

export interface UserProps {
	image?: string;
	name: string;
}

export default function User({image, name}: UserProps) {
	return (
		<div className="lfr-cmp__user-container">
			{image && (
				<img alt={name} className="lfr-cmp__user-avatar" src={image} />
			)}

			<span>{name}</span>
		</div>
	);
}
