import {useEffect, useState} from 'react';
import ProductComparison from '~/common/components/product-comparison';
import {LiferayService} from '~/common/services/liferay';
import {STORAGE_KEYS, Storage} from '~/common/services/liferay/storage';

const applicationId = Storage.getItem(STORAGE_KEYS.APPLICATION_ID);
const productId = Storage.getItem(STORAGE_KEYS.PRODUCT_ID);

const QuoteInfo = () => {
	const [product, setProduct] = useState({});

	useEffect(() => {
		LiferayService.getQuoteComparisonById(productId)
			.then((product) => {
				setProduct({...product, mostPopular: true});
			})
			.catch((error) => console.error(error.message));
	}, []);

	const onClickPolicyDetails = () => {};

	return (
		<div className="quote-info">
			{product.id && (
				<ProductComparison
					highlightMostPopularText="Great Coverage"
					onClickPolicyDetails={onClickPolicyDetails}
					product={product}
					purchasable={false}
				/>
			)}

			<div className="policy-id">POLICY {`#${applicationId}`}</div>
		</div>
	);
};

export default QuoteInfo;
