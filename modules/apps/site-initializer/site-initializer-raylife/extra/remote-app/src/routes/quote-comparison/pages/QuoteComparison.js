/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import React, {useEffect, useState} from 'react';

import ProductComparison from '../../../common/components/product-comparison';
import {STORAGE_KEYS, Storage} from '../../../common/services/liferay/storage';
import {getLiferaySiteName} from '../../../common/utils/liferay';
import {getQuoteComparisons} from '../service/QuoteComparison';

const QuoteComparison = () => {
	const [quotes, setQuotes] = useState([]);

	useEffect(() => {
		getQuoteComparisons()
			.then((data) => setQuotes(data.items))
			.catch((error) => console.error(error.message));
	}, []);

	const onClickPurchase = ({id}) => {
		Storage.setItem(STORAGE_KEYS.PRODUCT_ID, id);

		window.location.href = `${getLiferaySiteName()}/selected-quote`;
	};

	const onClickPolicyDetails = () => {};

	return (
		<div className="d-flex flex-wrap mb-7 quote-comparison">
			{quotes.map((quote, index) => (
				<ProductComparison
					key={index}
					onClickPolicyDetails={onClickPolicyDetails}
					onClickPurchase={onClickPurchase}
					product={quote}
				/>
			))}
		</div>
	);
};

export default QuoteComparison;
