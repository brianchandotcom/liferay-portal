import React from 'react';
import {formatDateToTimeZone} from 'shared/util/date';
import {
	getBestVariant,
	getMetricName,
	toThousandsABTesting
} from 'experiments/util/experiments';
import {sub} from 'shared/util/lang';
import {SummaryAlert} from './SummaryAlert';
import {SummaryBaseCard} from './SummaryBaseCard';
import {SummaryParagraph} from './SummaryParagraph';
import {SummarySection} from './SummarySection';
import {SummaryTitle} from './SummaryTitle';
import {toRounded} from 'shared/util/numbers';

export const SummaryNoWinnerCard = ({experiment, timeZoneId}) => {
	const {
		description,
		goal,
		metrics: {completion, elapsedDays},
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
				title={Liferay.Language.get('no-clear-winner')}
			/>

			<SummaryAlert symbol='exclamation-circle'>
				<SummaryTitle
					className='font-weight-bold mb-1'
					label={Liferay.Language.get('there-is-no-clear-winner')}
				/>

				<strong>
					{Liferay.Language.get(
						'we-recommend-that-you-use-any-of-the-test-candidates-as-they-will-perform-similarly'
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
								title={Liferay.Language.get('days-running')}
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

						{goal && (
							<SummarySection
								title={Liferay.Language.get('test-metric')}
							>
								<SummarySection.MetricType
									value={getMetricName(goal.metric)}
								/>

								{bestVariant?.improvement > 0 && (
									<SummarySection.Variant
										lift={`${toRounded(
											bestVariant.improvement,
											2
										)}%`}
										status='up'
									/>
								)}
							</SummarySection>
						)}
					</div>
				</div>
			</SummaryBaseCard.Body>
		</SummaryBaseCard>
	);
};
