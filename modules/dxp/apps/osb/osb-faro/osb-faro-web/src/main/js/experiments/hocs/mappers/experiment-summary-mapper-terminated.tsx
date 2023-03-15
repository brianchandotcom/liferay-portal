import React from 'react';
import SummarySection from 'experiments/components/summary-section';
import {formatDateToTimeZone} from 'shared/util/date';
import {getMetricName, modalDelete} from 'experiments/util/experiments';
import {sub} from 'shared/util/lang';
import {toRounded} from 'shared/util/numbers';
import {toThousandsABTesting} from 'experiments/util/experiments';

export default ({
	bestVariant,
	experimentId,
	finishedDate,
	goal,
	metrics: {completion, elapsedDays, estimatedDaysLeft},
	sessions,
	startedDate,
	timeZoneId
}) => ({
	header: {
		cardModals: [modalDelete(experimentId)],
		Description: () => (
			<div className='date'>
				<div>
					{sub(Liferay.Language.get('started-x'), [
						formatDateToTimeZone(startedDate, 'll', timeZoneId)
					])}
				</div>

				{finishedDate && (
					<div>
						{sub(Liferay.Language.get('stopped-x'), [
							formatDateToTimeZone(finishedDate, 'll', timeZoneId)
						])}
					</div>
				)}
			</div>
		),
		title: Liferay.Language.get('test-was-terminated')
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
								sub(Liferay.Language.get('about-x-days-left'), [
									estimatedDaysLeft
								])
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
