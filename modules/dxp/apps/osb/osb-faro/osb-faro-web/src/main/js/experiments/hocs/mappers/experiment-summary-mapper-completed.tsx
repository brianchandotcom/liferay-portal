import React from 'react';
import SummarySection from 'experiments/components/summary-section';
import {formatDateToTimeZone} from 'shared/util/date';
import {getMetricName, mergedVariants} from 'experiments/util/experiments';
import {sub} from 'shared/util/lang';
import {toRounded} from 'shared/util/numbers';
import {toThousandsABTesting} from 'experiments/util/experiments';

export default ({
	dxpVariants,
	finishedDate,
	goal,
	metrics: {completion, elapsedDays, variantMetrics},
	publishedDXPVariantId,
	sessions,
	startedDate,
	timeZoneId
}) => {
	const publishedVariant = mergedVariants(dxpVariants, variantMetrics).find(
		({dxpVariantId}) => dxpVariantId === publishedDXPVariantId
	);

	return {
		alert:
			publishedVariant && !publishedVariant.control
				? {
						description: Liferay.Language.get(
							'your-new-experience-was-successfully-published-and-no-more-data-will-be-collected-for-this-test'
						),
						symbol: 'check-circle',
						title: sub(
							Liferay.Language.get('x-has-been-published'),
							[publishedVariant.dxpVariantName]
						)
				  }
				: {
						description: Liferay.Language.get(
							'no-more-data-will-be-collected-for-this-test'
						),
						symbol: 'check-circle',
						title: Liferay.Language.get(
							'control-has-been-kept-as-the-experience'
						)
				  },
		header: {
			Description: () => (
				<div className='date'>
					<div>
						{sub(Liferay.Language.get('started-x'), [
							formatDateToTimeZone(startedDate, 'll', timeZoneId)
						])}
					</div>
					{finishedDate && (
						<div>
							{sub(Liferay.Language.get('ended-x'), [
								formatDateToTimeZone(
									finishedDate,
									'll',
									timeZoneId
								)
							])}
						</div>
					)}
				</div>
			),
			title: Liferay.Language.get('test-complete')
		},
		sections: [
			{
				Body: () => (
					<SummarySection
						title={Liferay.Language.get('test-completion')}
					>
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
					<SummarySection title={Liferay.Language.get('days-ran')}>
						<SummarySection.Heading value={String(elapsedDays)} />
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
						<SummarySection
							title={Liferay.Language.get('test-metric')}
						>
							<SummarySection.MetricType
								value={getMetricName(goal.metric)}
							/>
							{publishedVariant &&
								publishedVariant.improvement > 0 && (
									<SummarySection.Variant
										lift={`${toRounded(
											publishedVariant.improvement,
											2
										)}%`}
										status='up'
									/>
								)}
						</SummarySection>
					)
			}
		]
	};
};
