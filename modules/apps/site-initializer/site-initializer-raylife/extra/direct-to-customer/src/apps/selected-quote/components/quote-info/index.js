import ProductComparison from '~/shared/components/product-comparison';
import {STORAGE_KEYS, Storage} from '~/shared/services/liferay/storage';

const data = {
	aggregateLimit: 2000000.0,
	businessPersonalProperty: 50000.0,
	category: 'standard',
	dateCreated: '2021-09-28T17:19:34Z',
	dateModified: '2021-09-28T17:19:34Z',
	externalReferenceCode: '',
	id: 48792,
	moneyAndSecurities: 0.0,
	mostPopular: true,
	perOccuranceLimit: 1000000,
	price: 719.0,
	productRecallOrReplacement: true,
	promo: 359.0,
};

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
