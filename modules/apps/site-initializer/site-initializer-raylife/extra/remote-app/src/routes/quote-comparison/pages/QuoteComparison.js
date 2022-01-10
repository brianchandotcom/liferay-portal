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
