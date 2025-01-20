/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {ReactNode} from 'react';

type ProductPurchaseHeaderProps = {
	children?: ReactNode;
	image?: string;
	rightNode?: ReactNode;
	subsectionTitleLeft: string;
	subsectionTitleRight: string;
	subtitle?: string | ReactNode;
	title: string;
};

const ProductPurchaseHeader = ({
	children,
	image,
	rightNode,
	subsectionTitleLeft,
	subsectionTitleRight,
	subtitle,
	title,
}: ProductPurchaseHeaderProps) => {
	const HeadingComponent = title.length > 30 ? 'h3' : 'h1';

	return (
		<div className="p-4">
			<div className="d-flex flex-row justify-content-between">
				<div className="d-flex flex-row">
					<img
						alt="App Icon"
						className="object-fit-cover rounded"
						draggable={false}
						height="64px"
						src={image}
						width="64px"
					/>

					<div className="align-items-center ml-4">
						<HeadingComponent className="text-weight-bold">
							{title}
						</HeadingComponent>

						<span className="sub-text">{subtitle}</span>
					</div>
				</div>

				{rightNode}
			</div>

			{children}

			<hr />

			<div className="d-flex flex-row justify-content-between">
				<strong className="align-self-center">
					{subsectionTitleLeft}
				</strong>

				<div className="align-items-center d-flex">
					<div className="align-items-end d-flex flex-column m-2">
						<strong>{subsectionTitleRight}</strong>
					</div>
				</div>
			</div>
		</div>
	);
};
export default ProductPurchaseHeader;
