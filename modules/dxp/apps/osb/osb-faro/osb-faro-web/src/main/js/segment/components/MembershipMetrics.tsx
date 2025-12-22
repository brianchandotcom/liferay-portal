import Card from 'shared/components/Card';
import ClayIcon from '@clayui/icon';
import ClayPopover from '@clayui/popover';
import Loading from 'shared/components/Loading';
import React from 'react';
import {addAlert} from 'shared/actions/alerts';
import {Alert} from 'shared/types';
import {ClayButtonWithIcon} from '@clayui/button';
import {connect, ConnectedProps} from 'react-redux';
import {convertMillisecondsToDays} from 'shared/util/date';
import {fetchMembershipMetrics} from 'shared/api/individual-segment';
import {getIcon, getStatsColor, getTrendSign} from 'shared/util/metrics';
import {getPercentage} from 'shared/util/util';
import {
	Metric,
	metricsDescription,
	metricsTitles,
	TrendClassification
} from 'segment/types';
import {ReportContainer} from 'shared/components/download-report/DownloadPDFReport';
import {sub} from 'shared/util/lang';
import {toThousands} from 'shared/util/numbers';
import {useParams} from 'react-router-dom';
import {useRequest} from 'shared/hooks/useRequest';

const connector = connect(null, {
	addAlert
});

type PropsFromRedux = ConnectedProps<typeof connector>;

interface ICardSectionProps {
	data: Metric;
	description: string;
	title: string;
	totalIndividuals?: number;
	trendComparison?: number;
}

export const CardSection: React.FC<ICardSectionProps> = ({
	data,
	description,
	title,
	totalIndividuals,
	trendComparison
}) => {
	const isAverageSegmentMetric =
		title === metricsTitles.averageSegmentMembershipDurationMetric;

	const rawValue = data?.value || 0;

	let displayValue = '';

	if (isAverageSegmentMetric) {
		const days = convertMillisecondsToDays(rawValue);

		const languageKey = sub(
			days === 1
				? Liferay.Language.get('x-day').toLowerCase()
				: Liferay.Language.get('x-days').toLowerCase(),
			[days]
		);

		displayValue = languageKey as string;
	} else {
		displayValue = sub(
			rawValue === 1
				? Liferay.Language.get('x-member').toLowerCase()
				: Liferay.Language.get('x-members').toLowerCase(),
			[toThousands(rawValue)]
		) as string;
	}

	const previousDurationDays = convertMillisecondsToDays(
		data?.previousValue ?? 0
	);

	const previousValueComparison =
		(data?.previousValue * trendComparison) / 100;

	return (
		<div className='card-section d-flex flex-column justify-content-between my-3 px-3 type-trend-root w-100'>
			<div className='d-flex align-items-center justify-content-between'>
				<div className='d-flex align-items-center'>
					<Card.Title>{title}</Card.Title>

					<ClayPopover
						alignPosition='top'
						closeOnClickOutside
						header={title}
						trigger={
							<ClayButtonWithIcon
								displayType='unstyled'
								size='xs'
								symbol='question-circle-full'
							/>
						}
					>
						{description}
					</ClayPopover>
				</div>

				<span className='text-secondary text-uppercase font-weight-semibold'>
					{title ===
						metricsTitles.averageSegmentMembershipDurationMetric ||
					title === metricsTitles.totalMembersMetric
						? Liferay.Language.get('last-30-days')
						: Liferay.Language.get('last-24-hours')}
				</span>
			</div>

			<h2 className='text-secondary my-3'>{displayValue}</h2>

			<div className='d-flex flex-row'>
				{!isAverageSegmentMetric && (
					<>
						<span className='mr-1 text-secondary'>
							<span>
								{sub(
									title === metricsTitles.totalMembersMetric
										? Liferay.Language.get(
												'x-percent-of-all-individuals'
										  )
										: Liferay.Language.get(
												'x-percent-of-all-members'
										  ),
									[
										getPercentage(
											data?.value,
											totalIndividuals
										)
									]
								)}
							</span>
						</span>

						<span className='mx-3 text-secondary'>{'|'}</span>
					</>
				)}

				<>
					{isAverageSegmentMetric && (
						<span className='mr-1 text-3 text-secondary'>
							<span
								style={{
									color: getStatsColor(
										data?.trend?.trendClassification
									)
								}}
							>
								{getTrendSign(previousValueComparison)}
							</span>

							{sub(
								Liferay.Language.get('x-vs-last-x-days'),
								[
									<span
										className='mr-1'
										key='previousDays'
										style={{
											color: getStatsColor(
												data?.trend?.trendClassification
											)
										}}
									>
										{sub(
											previousDurationDays === 1
												? Liferay.Language.get(
														'x-day'
												  ).toLowerCase()
												: Liferay.Language.get(
														'x-days'
												  ).toLowerCase(),
											[previousDurationDays]
										)}
									</span>,
									30
								],
								false
							)}
						</span>
					)}

					<span className='text-3'>
						{!isAverageSegmentMetric && (
							<>
								{!!data?.trend?.trendClassification &&
									data?.trend?.trendClassification !==
										TrendClassification.Neutral && (
										<span
											className='ml-1'
											style={{
												color: getStatsColor(
													data?.trend
														?.trendClassification
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
										title ===
											metricsTitles.totalMembersMetric
											? Liferay.Language.get(
													'x-vs-last-x-days'
											  )
											: Liferay.Language.get(
													'x-vs-last-x-day-avg'
											  ),
										[
											<span
												className='mr-1'
												key='percentage'
												style={{
													color:
														getStatsColor(
															data?.trend
																?.trendClassification
														) ||
														TrendClassification.Neutral
												}}
											>
												{`${
													data?.trend?.percentage ?? 0
												}%`}
											</span>,
											30
										],
										false
									)}
								</span>
							</>
						)}
					</span>
				</>
			</div>
		</div>
	);
};

const MembershipMetrics: React.FC<PropsFromRedux> = ({addAlert}) => {
	const {groupId, id} = useParams();

	const {data, error, loading} = useRequest({
		dataSourceFn: fetchMembershipMetrics,
		variables: {groupId, individualSegmentId: id}
	});

	if (error) {
		addAlert({
			alertType: Alert.Types.Error,
			message: Liferay.Language.get(
				'there-was-an-error-processing-your-request.-try-again.-if-the-problem-persists,-please-contact-support'
			)
		});
	}

	if (loading) {
		return <Loading />;
	}

	return (
		<div className='membership-metrics-root'>
			<Card id={ReportContainer.AverageSegmentMembershipDurationCard}>
				<CardSection
					data={data?.averageSegmentMembershipDurationMetric}
					description={
						metricsDescription.averageSegmentMembershipDurationMetric
					}
					title={metricsTitles.averageSegmentMembershipDurationMetric}
					trendComparison={
						data?.averageSegmentMembershipDurationMetric?.trend
							?.percentage ?? 0
					}
				/>
			</Card>

			<Card
				className='d-flex flex-row justify-content-between'
				id={ReportContainer.MembershipMetricsCard}
			>
				<CardSection
					data={data?.totalMembersMetric}
					description={metricsDescription.totalMembersMetric}
					title={metricsTitles.totalMembersMetric}
					totalIndividuals={
						data?.totalMembersMetric?.totalIndividuals
					}
				/>

				<CardSection
					data={data?.entryRateMetric}
					description={metricsDescription.entryRateMetric}
					title={metricsTitles.entryRateMetric}
					totalIndividuals={
						data?.totalMembersMetric?.totalIndividuals
					}
				/>

				<CardSection
					data={data?.exitRateMetric}
					description={metricsDescription.exitRate}
					title={metricsTitles.exitRate}
					totalIndividuals={
						data?.totalMembersMetric?.totalIndividuals
					}
				/>
			</Card>
		</div>
	);
};

export default connector(MembershipMetrics);
