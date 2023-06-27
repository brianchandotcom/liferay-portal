import BaseCard from 'shared/components/base-card';
import Card from 'shared/components/Card';
import ErrorDisplay from 'shared/components/ErrorDisplay';
import React, {useState} from 'react';
import StatesRenderer from 'shared/components/states-renderer/StatesRenderer';
import TrendComponent from 'shared/components/Trend';
import withCurrentUser from 'shared/hoc/WithCurrentUser';
import {ApolloError} from 'apollo-client';
import {DocumentNode} from 'graphql';
import {getIcon, getStatsColor} from 'shared/util/metrics';
import {
	getRangeSelectorsFromQuery,
	getSafeRangeSelectors
} from 'shared/util/util';
import {RangeSelectors, RawRangeSelectors} from 'shared/types';
import {sub} from 'shared/util/lang';
import {toRounded} from 'shared/util/numbers';
import {Trend} from 'commerce/utils/types';
import {useParams} from 'react-router-dom';
import {useQuery} from '@apollo/react-hooks';
import {User} from 'shared/util/records';

type Currency = {
	currencyCode: string;
	trend: Trend;
	value: string;
};

interface ICommerceMetricCardProps<TGraphQlData>
	extends React.HTMLAttributes<HTMLElement> {
	description: string;
	emptyTitle: string;
	label: string;
	mapper: (result: TGraphQlData) => Currency[];
	Query: DocumentNode;
	currentUser: User;
}

interface ICommerceMetricCardWithStatesRendererProps
	extends React.HTMLAttributes<HTMLElement> {
	empty?: boolean;
	emptyTitle: string;
	error?: ApolloError;
	loading?: boolean;
}

interface TGraphQlVariables extends RawRangeSelectors {
	channelId: string;
}

const CommerceCardWithStatesRenderer: React.FC<ICommerceMetricCardWithStatesRendererProps> = ({
	children,
	empty = false,
	emptyTitle,
	error,
	loading = false
}) => (
	<StatesRenderer empty={empty} error={!!error} loading={loading}>
		<StatesRenderer.Loading />
		<StatesRenderer.Empty
			description={Liferay.Language.get(
				'check-back-later-to-verify-if-data-has-been-received-from-your-data-sources'
			)}
			showIcon={false}
			title={emptyTitle}
		/>
		<StatesRenderer.Error apolloError={error}>
			<ErrorDisplay />
		</StatesRenderer.Error>
		<StatesRenderer.Success>{children}</StatesRenderer.Success>
	</StatesRenderer>
);

export function CommerceMetricCard<TGraphQlData>({
	currentUser,
	description,
	emptyTitle,
	label,
	mapper,
	Query
}: ICommerceMetricCardProps<TGraphQlData>): React.ReactElement {
	const {channelId, query} = useParams();
	const [rangeSelectors, setRangeSelectors] = useState<RangeSelectors>(
		getRangeSelectorsFromQuery(query)
	);
	const {data, error, loading} = useQuery<TGraphQlData, TGraphQlVariables>(
		Query,
		{
			fetchPolicy: 'network-only',
			variables: {
				channelId,
				...getSafeRangeSelectors(rangeSelectors)
			}
		}
	);

	const result = mapper(data);

	const {currencyCode, trend, value} = getCurrency(result);

	return (
		<BaseCard
			className='commerce-card-root'
			label={label}
			legacyDropdownRangeKey={false}
			minHeight={298}
		>
			{({rangeSelectors}) => {
				setRangeSelectors(rangeSelectors);

				return (
					<Card.Body className='align-items-center justify-content-center'>
						<CommerceCardWithStatesRenderer
							empty={!result?.length}
							emptyTitle={emptyTitle}
							error={error}
							loading={loading}
						>
							<h1 className='commerce-card-currency font-size-lg-3x font-weight-semibold mb-2'>
								{formatCurrency(
									currencyCode,
									currentUser.languageId,
									value
								)}
							</h1>

							<div className='d-flex align-items-center mb-2'>
								<span className='font-size-sm-1x text-secondary'>
									{sub(
										Liferay.Language.get('x-vs-previous'),
										[
											<TrendComponent
												className='d-inline'
												color={getStatsColor(
													trend.trendClassification
												)}
												icon={getIcon(trend.percentage)}
												key='TREND'
												label={`${toRounded(
													Math.abs(trend.percentage)
												)}%`}
											/>
										],
										false
									)}
								</span>
							</div>

							<p className='font-size-sm-1x text-center'>
								{description}
							</p>
						</CommerceCardWithStatesRenderer>
					</Card.Body>
				);
			}}
		</BaseCard>
	);
}

function getCurrency(currencies: Currency[]): Currency {
	const defaultCurrencyCode = 'USD';

	if (!currencies || !currencies.length) {
		return {
			currencyCode: defaultCurrencyCode,
			trend: {
				percentage: 0,
				trendClassification: 'NEUTRAL'
			},
			value: '0'
		};
	}

	return (
		currencies?.find(
			({currencyCode}) => currencyCode === defaultCurrencyCode
		) ?? currencies[0]
	);
}

function formatCurrency(
	currencyCode: string,
	locale: string,
	value: string
): string {
	return new Intl.NumberFormat(locale.replace('_', '-'), {
		currency: currencyCode,
		style: 'currency'
	}).format(parseFloat(value));
}

export default withCurrentUser(CommerceMetricCard);
