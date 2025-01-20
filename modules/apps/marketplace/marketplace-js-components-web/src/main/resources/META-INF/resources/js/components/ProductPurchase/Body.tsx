/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import React, {ComponentProps, ReactNode} from 'react';

type ProductPurchaseBodyProps = {
	children: ReactNode;
	primaryButtonProps?: ComponentProps<typeof ClayButton>;
	secondaryButtonProps?: ComponentProps<typeof ClayButton>;
	title: string;
};

const ProductPurchaseBody: React.FC<ProductPurchaseBodyProps> = ({
	children,
	primaryButtonProps,
	secondaryButtonProps,
	title,
}) => (
	<div className="m-4 marketplace-product-purchase p-5 rounded-lg">
		<div className="align-items-center d-flex flex-column justify-content-center">
			<h1 className="mb-5">{title}</h1>

			<p className="text-center text-secondary w-75">{children}</p>
		</div>

		<div className="d-flex justify-content-between mt-6 w-100">
			{secondaryButtonProps && (
				<ClayButton
					borderless
					displayType="unstyled"
					{...secondaryButtonProps}
				/>
			)}

			{primaryButtonProps && <ClayButton {...primaryButtonProps} />}
		</div>
	</div>
);

export {ProductPurchaseBody};
