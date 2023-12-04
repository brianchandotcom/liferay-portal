import React from 'react';
import {formatDateToTimeZone} from 'shared/util/date';
import {
	getBestVariant,
	getMetricName,
	toThousandsABTesting
} from 'experiments/util/experiments';
import {sub} from 'shared/util/lang';
import {SummaryBaseCard} from './SummaryBaseCard';
import {SummaryParagraph} from './SummaryParagraph';
import {SummarySection} from './SummarySection';
import {toRounded} from 'shared/util/numbers';

export const SummaryRunningCard = ({experiment, timeZoneId}) => {
	const {
		description,
		goal,
		metrics: {completion, elapsedDays, estimatedDaysLeft},
		sessions,
		startedDate,
		status,
		type
	} = experiment;

	const bestVariant = getBestVariant(experiment);

	return (
		<SummaryBaseCard status={status.toLowerCase()}>
			<SummaryBaseCard.Header
				Description={() =>
					sub(Liferay.Language.get('started-x'), [
						formatDateToTimeZone(startedDate, 'll', timeZoneId)
					]) as any
				}
				title={Liferay.Language.get('test-is-running')}
			/>

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
								title={Liferay.Language.get('days-running')}
							>
								<SummarySection.Heading
									value={String(elapsedDays)}
								/>

								{!!estimatedDaysLeft && (
									<SummarySection.Description
										value={
											sub(
												estimatedDaysLeft > 1
													? Liferay.Language.get(
															'about-x-days-left'
													  )
													: Liferay.Language.get(
															'about-x-day-left'
													  ),
												[estimatedDaysLeft]
											) as string
										}
									/>
								)}
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
					</div>
				</div>
			</SummaryBaseCard.Body>
		</SummaryBaseCard>
	);
};
