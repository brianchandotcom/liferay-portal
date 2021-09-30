import ProductComparison from '~/shared/components/product-comparison';
import {STORAGE_KEYS, Storage} from '~/shared/services/liferay/storage';

const data = {};

const QuoteInfo = () => {
	const applicationId = Storage.getItem(STORAGE_KEYS.APPLICATION_ID);

	const onClickPolicyDetails = () => {};

	return (
		<div className="quote-info">
			<h1>Awesome Choice!</h1>
			<h3>You&apos;re almost finished.</h3>

			<ProductComparison
				highlightMostPopularText="Great Coverage"
				onClickPolicyDetails={onClickPolicyDetails}
				product={data}
				purchasable={false}
			/>

			<div className="application-id">
				Application {`#${applicationId}`}
			</div>
		</div>
	);
};

export default QuoteInfo;
