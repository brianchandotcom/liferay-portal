/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import {MiniCartUtils} from 'commerce-frontend-js';
import React from 'react';

const ProductURLDataRenderer = ({
	additionalProps: {productURLSeparator, siteDefaultURL},
	itemData: {productURLs},
	value,
}) => {
	const productURL = MiniCartUtils.generateProductPageURL(
		siteDefaultURL,
		productURLs,
		productURLSeparator
	);

	return (
		<div className="table-list-title">
			<ClayLink data-senna-off href={productURL}>
				{value}
			</ClayLink>
		</div>
	);
};

export default ProductURLDataRenderer;
