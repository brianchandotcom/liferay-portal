import {useContext} from 'react';
import ProductComparison from '../../../../common/components/product-comparison';
import {
	STORAGE_KEYS,
	Storage,
} from '../../../../common/services/liferay/storage';
import {SelectedQuoteContext} from '../../context/SelectedQuoteContextProvider';

const applicationId = Storage.getItem(STORAGE_KEYS.APPLICATION_ID);

const QuoteInfo = () => {
	const [{product}] = useContext(SelectedQuoteContext);

	return (
		<div className="pt-0 px-3 quote-info">
			{product.id && (
				<ProductComparison
					highlightMostPopularText="Great Coverage"
					onClickPolicyDetails={() => {}}
					product={product}
					purchasable={false}
				/>
			)}

			<div className="font-weight-bolder mt-5 text-uppercase">
				Policy {`#${applicationId}`}
			</div>
		</div>
	);
};

export default QuoteInfo;
