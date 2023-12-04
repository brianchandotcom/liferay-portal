import React from 'react';
import {formatDateToTimeZone} from 'shared/util/date';
import {
	getMetricName,
	mergedVariants,
	toThousandsABTesting
} from 'experiments/util/experiments';
import {sub} from 'shared/util/lang';
import {SummaryAlert} from './SummaryAlert';
import {SummaryBaseCard} from './SummaryBaseCard';
import {SummaryParagraph} from './SummaryParagraph';
import {SummarySection} from './SummarySection';
import {SummaryTitle} from './SummaryTitle';
import {toRounded} from 'shared/util/numbers';

export const SummaryCompletedCard = ({experiment, timeZoneId}) => {
	const {
		description,
		dxpVariants,
		finishedDate,
		goal,
		metrics: {completion, elapsedDays, variantMetrics},
		publishedDXPVariantId,
		sessions,
		startedDate,
		status,
		type
	} = experiment;

	const publishedVariant = mergedVariants(dxpVariants, variantMetrics).find(
		({dxpVariantId}) => dxpVariantId === publishedDXPVariantId
	);

	return (
		<SummaryBaseCard status={status.toLowerCase()}>
			<SummaryBaseCard.Header
				Description={() => (
					<div className='date'>
						<div>
							{sub(Liferay.Language.get('started-x'), [
								formatDateToTimeZone(
									startedDate,
									'll',
									timeZoneId
								)
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
				)}
				title={Liferay.Language.get('test-complete')}
			/>

			<SummaryAlert symbol='check-circle'>
				<SummaryTitle
					className='font-weight-bold mb-1'
					label={
						sub(Liferay.Language.get('x-has-been-published'), [
							publishedVariant?.dxpVariantName
						]) as string
					}
				/>

				<strong>
					{Liferay.Language.get(
						'no-more-data-will-be-collected-for-this-test'
					)}
				</strong>
			</SummaryAlert>

			<SummaryBaseCard.Body>
				<div className='w-100 mt-4'>
					<SummaryParagraph
						description={description}
						title={Liferay.Language.get('summary')}
					/>

					<div className='analytics-summary-card-sections'>
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

						{type === 'AB' && (
							<SummarySection
								title={Liferay.Language.get('days-ran')}
							>
								<SummarySection.Heading
									value={String(elapsedDays)}
								/>
							</SummarySection>
						)}

						<SummarySection
							title={Liferay.Language.get('total-test-sessions')}
						>
							<SummarySection.Heading
								value={toThousandsABTesting(sessions)}
							/>
						</SummarySection>

						<SummarySection
							title={Liferay.Language.get('test-metric')}
						>
							<SummarySection.MetricType
								value={getMetricName(goal.metric)}
							/>

							{publishedVariant?.improvement > 0 && (
								<SummarySection.Variant
									lift={`${toRounded(
										publishedVariant.improvement,
										2
									)}%`}
									status='up'
								/>
							)}
						</SummarySection>
					</div>
				</div>
			</SummaryBaseCard.Body>
		</SummaryBaseCard>
	);
};
