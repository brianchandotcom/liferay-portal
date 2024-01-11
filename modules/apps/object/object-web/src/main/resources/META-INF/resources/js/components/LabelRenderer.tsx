/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import React from 'react';

import './LabelRenderer.scss';

interface IProps {
	onClick: () => void;
	value: LocalizedValue<string>;
}

export default function LabelRenderer({onClick, value}: IProps) {
	return (
		<div className="table-list-title">
			<span
				className="lfr__object-label-renderer"
				onClick={onClick}
				role="link"
			>
				{Object.keys(value).length !== 0 ? (
					value
				) : (
					<ClayIcon symbol="view" />
				)}
			</span>
		</div>
	);
}
