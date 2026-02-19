import * as API from 'shared/api';
import BasePage from 'shared/components/base-page';
import Card from 'shared/components/Card';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import IndividualMetricsQuery from 'shared/queries/IndividualMetricsQuery';
import IndividualsList from './IndividualsList';
import Loading from 'shared/components/Loading';
import NoResultsDisplay from 'shared/components/NoResultsDisplay';
import React from 'react';
import URLConstants from 'shared/util/url-constants';
import {Text as ClayText} from '@clayui/core';
import {CSVType} from 'shared/components/download-report/utils';
import {DownloadStaticCSVReport} from 'shared/components/download-report/DownloadStaticCSVReport';
import {getIcon, getStatsColor} from 'shared/util/metrics';
import {INTERVAL_KEY_MAP} from 'shared/util/time';
import {isNil} from 'lodash';
import {RangeKeyTimeRanges, Sizes} from 'shared/util/constants';
import {Routes, toRoute} from 'shared/util/router';
import {sub} from 'shared/util/lang';
import {toRounded, toThousands} from 'shared/util/numbers';
import {TrendClassification} from 'segment/types';
import {useCurrentUser} from 'shared/hooks/useCurrentUser';
import {useDataSource} from 'shared/hooks/useDataSource';
import {useParams} from 'react-router-dom';
import {useQuery} from '@apollo/react-hooks';
import {useRequest} from 'shared/hooks/useRequest';

type IndividualsMetricCard = {
	trend: {
		percentage: number | null;
		trendClassification: TrendClassification;
	};
	value: number;
};

interface IIndividualsMetricsCardProps {
	data: IndividualsMetricCard;
	description: string;
	title: string;
}

interface IIndividualsOverviewEmptyStateProps {
	authorized: boolean;
	dataSourceData: {total?: number} | null;
	dataSourceLoading: boolean;
	groupId: string;
}

const IndividualsMetricsCard: React.FC<IIndividualsMetricsCardProps> = ({
	data,
	description,
	title
}) => {
	const rawValue = data?.value || 0;

	const individuals = toThousands(rawValue);

	return (
		<>
			<Card.Header>
				<ClayText weight='semi-bold'>{title.toUpperCase()}</ClayText>
			</Card.Header>

			<Card.Body className='d-flex flex-column'>
				<div className='flex-grow-1'>
					<span className='text-secondary'>{description}</span>
				</div>

				<h2 className='mt-2'>
					{
						sub(
							rawValue === 1
								? Liferay.Language.get('x-individual')
								: Liferay.Language.get('x-individuals'),
							[individuals]
						) as string
					}
				</h2>

				<div>
					{!!data?.trend.trendClassification &&
						data?.trend.trendClassification !==
							TrendClassification.Neutral && (
							<span
								style={{
									color: getStatsColor(
										data?.trend?.trendClassification
									)
								}}
							>
								<ClayIcon
									symbol={getIcon(
										data?.trend?.percentage ?? 0
									)}
								/>
							</span>
						)}

					<span className='text-secondary'>
						{sub(
							Liferay.Language.get('x-vs-previous-30-days'),
							[
								<span
									className='mr-1'
									key='percentage'
									style={{
										color:
											getStatsColor(
												data?.trend?.trendClassification
											) || TrendClassification.Neutral
									}}
								>
									{`${toRounded(
										data?.trend?.percentage ?? 0,
										2
									)}%`}
								</span>
							],
							false
						)}
					</span>
				</div>
			</Card.Body>
		</>
	);
};

const IndividualsOverviewEmptyState: React.FC<IIndividualsOverviewEmptyStateProps> = ({
	authorized,
	dataSourceData,
	dataSourceLoading,
	groupId
}) => {
	if (dataSourceLoading) {
		return (
			<NoResultsDisplay>
				<Loading key='LOADING' />
			</NoResultsDisplay>
		);
	}

	if (isNil(dataSourceData?.total) || dataSourceData?.total === 0) {
		return (
			<Card pageDisplay>
				<NoResultsDisplay
					description={
						<>
							{authorized
								? Liferay.Language.get(
										'connect-a-data-source-to-get-started'
								  )
								: Liferay.Language.get(
										'contact-an-administrator-to-connect-a-data-source-to-get-started'
								  )}

							<ClayLink
								className='d-block'
								decoration='underline'
								href={URLConstants.HelpConnectDxp}
								target='_blank'
							>
								<ClayText size={4}>
									{Liferay.Language.get(
										'learn-more-about-data-sources'
									)}
								</ClayText>

								<ClayIcon
									aria-label={Liferay.Language.get(
										'learn-more-about-data-sources'
									)}
									className='ml-1'
									fontSize={12}
									symbol='shortcut'
								/>
							</ClayLink>
						</>
					}
					icon={{
						border: false,
						size: Sizes.XXXLarge,
						symbol: 'ac_satellite'
					}}
					primary
					title={Liferay.Language.get('no-data-source-synced')}
				>
					{authorized && (
						<ClayLink
							button
							className='button-root'
							displayType='primary'
							href={toRoute(Routes.SETTINGS_DATA_SOURCE_LIST, {
								groupId
							})}
						>
							{Liferay.Language.get('connect-data-source')}
						</ClayLink>
					)}
				</NoResultsDisplay>
			</Card>
		);
	}

	return null;
};

const IndividualsOverviewCDP = () => {
	const {channelId, groupId} = useParams();

	const currentUser = useCurrentUser();

	const dataSourceStates = useDataSource();

	const authorized = currentUser.isAdmin();

	const {data: dataSourceData, loading: dataSourceLoading} = useRequest({
		dataSourceFn: API.dataSource.search,
		variables: {
			delta: 1,
			groupId
		}
	});
	const {data, loading} = useQuery(IndividualMetricsQuery, {
		fetchPolicy: 'network-only',
		variables: {
			channelId,
			interval: INTERVAL_KEY_MAP.week,
			rangeKey: Number(RangeKeyTimeRanges.Last30Days)
		}
	});

	return (
		<>
			<BasePage.SubHeader>
				<div className='d-flex justify-content-end w-100'>
					<DownloadStaticCSVReport
						disabled={dataSourceStates.empty}
						type={CSVType.Individual}
						typeLang={Liferay.Language.get('individuals')}
					/>
				</div>
			</BasePage.SubHeader>

			<BasePage.Body pageContainer>
				<IndividualsOverviewEmptyState
					authorized={authorized}
					dataSourceData={dataSourceData}
					dataSourceLoading={dataSourceLoading}
					groupId={groupId}
				/>

				{dataSourceData?.total > 0 && (
					<>
						<div className='d-flex flex-row justify-content-between'>
							{loading && <Loading key='LOADING' />}

							<Card className='w-100'>
								<IndividualsMetricsCard
									data={
										data?.individualMetric
											?.totalIndividualsMetric
									}
									description={Liferay.Language.get(
										'this-is-the-total-number-of-individuals,-including-both-known-individuals-and-anonymous-individuals'
									)}
									title={Liferay.Language.get(
										'total-individuals'
									)}
								/>
							</Card>

							<Card className='mx-3 w-100'>
								<IndividualsMetricsCard
									data={
										data?.individualMetric
											?.knownIndividualsMetric
									}
									description={Liferay.Language.get(
										'this-is-the-total-number-of-known-individuals.-an-individual-is-considered-known-if-we-have-any-identifiable-information-about-the-individual'
									)}
									title={Liferay.Language.get(
										'known-individuals'
									)}
								/>
							</Card>

							<Card className='w-100'>
								<IndividualsMetricsCard
									data={
										data?.individualMetric
											?.anonymousIndividualsMetric
									}
									description={Liferay.Language.get(
										'this-is-the-total-number-of-anonymous-individuals.-anonymous-individuals-are-removed-after-30-days-of-inactivity'
									)}
									title={Liferay.Language.get(
										'anonymous-individuals'
									)}
								/>
							</Card>
						</div>

						<IndividualsList />
					</>
				)}
			</BasePage.Body>
		</>
	);
};

export default IndividualsOverviewCDP;
