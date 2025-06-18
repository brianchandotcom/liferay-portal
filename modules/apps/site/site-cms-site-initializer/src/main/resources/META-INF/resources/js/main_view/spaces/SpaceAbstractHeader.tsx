/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import {openModal} from 'frontend-js-components-web';
import React from 'react';

interface SpaceAbstractHeaderProps {
	label: string;
	title: string;
	url: string;
}

export default function SpaceAbstractHeader({
	label,
	title,
	url,
}: SpaceAbstractHeaderProps) {
	const openAbstractModal = () => {
		openModal({
			bodyHTML: `<p>${label}</p>`,
			center: true,
			containerProps: {},
			size: 'lg',
			title,
		});
	};

	return (
		<div className="align-items-center d-flex justify-content-between">
			<h2 className="font-weight-semi-bold m-0 text-4">{title}</h2>

			{url ? (
				<ClayLink href={url}>{label}</ClayLink>
			) : (
				<ClayLink href="#" onClick={openAbstractModal}>
					{label}
				</ClayLink>
			)}
		</div>
	);
}
