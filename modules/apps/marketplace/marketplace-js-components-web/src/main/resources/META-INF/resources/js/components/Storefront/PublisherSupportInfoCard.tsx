/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import React, {ReactNode} from 'react';

type PublisherSupportInfoCardProps = {
	symbol?: string;
	title?: string;
	urlImage?: string;
	value: ReactNode | string;
};

export default function PublisherSupportInfoCard({
	symbol = 'cog',
	title,
	urlImage,
	value,
}: PublisherSupportInfoCardProps) {
	const HeadingComponent = typeof value === 'string' ? 'h3' : React.Fragment;

	return (
		<div className="align-items-center d-flex flex-row mb-4">
			<span className="align-items-center d-flex justify-content-center mr-3 rounded-circle">
				{urlImage ? (
					<img
						alt="App Logo"
						className="object-fit-contain rounded-circle"
						draggable={false}
						height={40}
						src={urlImage}
						width={40}
					/>
				) : (
					<ClayIcon symbol={symbol} />
				)}
			</span>

			<div className="d-flex flex-column">
				{title && <span className="text-secondary">{title}</span>}

				<HeadingComponent>{value}</HeadingComponent>
			</div>
		</div>
	);
}
