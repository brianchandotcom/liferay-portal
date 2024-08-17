/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayEmptyState from '@clayui/empty-state';
import React from 'react';

interface IEmptyStateProps extends React.HTMLAttributes<HTMLElement> {
	description: string;
	imgSrc?: string;
	title: string;
}

const EmptyState: React.FC<IEmptyStateProps> = ({
	children,
	description,
	imgSrc,
	title,
}) => (
	<div className="d-flex justify-content-center pt-6">
		<div
			className="align-items-center d-flex flex-column justify-content-center text-center"
			style={{maxWidth: 268}}
		>
			{!imgSrc && (
				<div style={{width: 88}}>
					<img
						src="/o/analytics-reports-js-components-web/assets/ac-icon.svg"
						style={{width: '100%'}}
					/>
				</div>
			)}

			<ClayEmptyState
				description={description}
				imgSrc={imgSrc}
				small
				title={title}
			>
				{children}
			</ClayEmptyState>
		</div>
	</div>
);

export default EmptyState;
