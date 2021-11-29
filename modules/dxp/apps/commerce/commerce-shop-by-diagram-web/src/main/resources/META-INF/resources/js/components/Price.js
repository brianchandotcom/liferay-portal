/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
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
