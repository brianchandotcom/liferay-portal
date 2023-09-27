/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import React from 'react';

function formatPrice(price) {
	return price.replaceAll(' ', '\u00A0');
}

function Price({className, ...priceDetails}) {
	const finalPrice =
		priceDetails.finalPrice ||
		priceDetails.promoPriceFormatted ||
		priceDetails.priceFormatted;
	const price = priceDetails.priceFormatted;

	return finalPrice !== price ? (
		<div className={classNames('text-right', className)}>
			<small className="d-block font-weight-bold mb-n1">
				<del>{formatPrice(price)}</del>
			</small>

			<strong className="text-danger">{formatPrice(finalPrice)}</strong>
		</div>
	) : (
		<strong>{formatPrice(finalPrice)}</strong>
	);
}

export default Price;
