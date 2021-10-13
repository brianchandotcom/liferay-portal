import React, {useEffect, useState} from 'react';

import ProductComparison from '~/common/components/product-comparison';
import {LiferayService} from '~/common/services/liferay';
import {STORAGE_KEYS, Storage} from '~/common/services/liferay/storage';

const QuoteComparison = () => {
	const [quotes, setQuotes] = useState([]);

	useEffect(() => {
		LiferayService.getQuoteComparison()
			.then((data) => setQuotes(data.items))
			.catch((error) => console.error(error.message));
	}, []);

	const onClickPurchase = ({id}) => {
		Storage.setItem(STORAGE_KEYS.PRODUCT_ID, id);

		const siteName = LiferayService.getLiferaySiteName();

		window.location.href = `${siteName}/selected-quote`;
	};

	const onClickPolicyDetails = () => {};

	return (
		<div className="quote-comparison">
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
