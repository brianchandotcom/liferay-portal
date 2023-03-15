import CommerceAverageRevenuePerAccountQuery, {
	CommerceAverageRevenuePerAccountData
} from 'commerce/queries/AverageRevenuePerAccountQuery';
import CommerceMetricCard from './CommerceMetricCard';
import React from 'react';

const AverageRevenuePerAccountCard = () => (
	<CommerceMetricCard<CommerceAverageRevenuePerAccountData>
		description={Liferay.Language.get(
			'total-order-value-divided-by-accounts'
		)}
		emptyTitle={Liferay.Language.get(
			'there-is-no-revenue-on-the-selected-period'
		)}
		label={Liferay.Language.get('avg-revenue-per-account')}
		mapper={(result: CommerceAverageRevenuePerAccountData) =>
			result?.orderAccountAverageCurrencyValues
		}
		Query={CommerceAverageRevenuePerAccountQuery}
	/>
);

export default AverageRevenuePerAccountCard;
