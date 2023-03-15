import React from 'react';
import SummarySection from 'experiments/components/summary-section';
import UpdateExperimentStatusModal from 'experiments/components/modals/UpdateExperimentStatusModal';
import {formatDateToTimeZone} from 'shared/util/date';
import {getMetricName} from 'experiments/util/experiments';
import {sub} from 'shared/util/lang';
import {toRounded} from 'shared/util/numbers';
import {toThousandsABTesting} from 'experiments/util/experiments';

export default ({
	bestVariant,
	experimentId,
	goal,
	metrics: {completion, elapsedDays, estimatedDaysLeft},
	sessions,
	startedDate,
	timeZoneId
}) => ({
	header: {
		Description: () =>
			sub(Liferay.Language.get('started-x'), [
				formatDateToTimeZone(startedDate, 'll', timeZoneId)
			]),
		modals: [
			{
				Component: UpdateExperimentStatusModal,
				props: {
					experimentId,
					modalBody: (
						<>
							<div className='mb-2 text-secondary'>
								{Liferay.Language.get(
									'are-you-sure-you-want-to-terminate-this-test'
								)}
							</div>
							<strong>
								{Liferay.Language.get(
									'no-more-traffic-will-be-directed-to-the-test-variants-and-we-will-stop-collecting-test-data'
								)}{' '}
								{Liferay.Language.get(
									'you-will-still-have-access-to-the-data-that-has-already-been-collected'
								)}
							</strong>
						</>
					),
					modalStatus: 'warning',
					nextStatus: 'TERMINATED',
					submitMessage: Liferay.Language.get('stop-test'),
					title: Liferay.Language.get('terminate-test')
				},
				title: Liferay.Language.get('terminate-test')
			}
		],
		title: Liferay.Language.get('test-is-running')
	},
	sections: [
		{
			Body: () => (
				<SummarySection title={Liferay.Language.get('test-completion')}>
					<SummarySection.Heading
						value={`${toRounded(completion)}%`}
					/>
					<SummarySection.ProgressBar
						value={parseInt(toRounded(completion))}
					/>
				</SummarySection>
			)
		},
		{
			Body: () => (
				<SummarySection title={Liferay.Language.get('days-running')}>
					<SummarySection.Heading value={String(elapsedDays)} />
					{estimatedDaysLeft && (
						<SummarySection.Description
							value={String(
								sub(
									estimatedDaysLeft > 1
										? Liferay.Language.get(
												'about-x-days-left'
										  )
										: Liferay.Language.get(
												'about-x-day-left'
										  ),
									[estimatedDaysLeft]
								)
							)}
						/>
					)}
				</SummarySection>
			)
		},
		{
			Body: () => (
				<SummarySection
					title={Liferay.Language.get('total-test-sessions')}
				>
					<SummarySection.Heading
						value={toThousandsABTesting(sessions)}
					/>
				</SummarySection>
			)
		},
		{
			Body: () =>
				goal && (
					<SummarySection title={Liferay.Language.get('test-metric')}>
						<SummarySection.MetricType
							value={getMetricName(goal.metric)}
						/>
						{bestVariant && bestVariant.improvement > 0 && (
							<SummarySection.Variant
								lift={`${toRounded(
									bestVariant.improvement,
									2
								)}%`}
								status='up'
							/>
						)}
					</SummarySection>
				)
		}
	]
});
