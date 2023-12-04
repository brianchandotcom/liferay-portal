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

export const SummaryWinnerCard = ({experiment, timeZoneId}) => {
	const {
		description,
		dxpVariants,
		goal,
		metrics: {completion, elapsedDays, variantMetrics},
		sessions,
		startedDate,
		status,
		type,
		winnerDXPVariantId
	} = experiment;

	const variants = mergedVariants(dxpVariants, variantMetrics);

	const winnerVariant = variants.find(
		({dxpVariantId}) => dxpVariantId === winnerDXPVariantId
	);

	const secondPlaceVariant = variants.find(
		({dxpVariantId}) => dxpVariantId !== winnerDXPVariantId
	);

	return (
		<SummaryBaseCard status={status.toLowerCase()}>
			<SummaryBaseCard.Header
				Description={() =>
					sub(Liferay.Language.get('started-x'), [
						formatDateToTimeZone(startedDate, 'll', timeZoneId)
					]) as any
				}
				title={Liferay.Language.get('winner-declared')}
			/>

			{winnerVariant && winnerVariant?.control ? (
				<SummaryAlert symbol='check-circle'>
					<SummaryTitle
						className='font-weight-bold mb-1'
						label={
							sub(
								Liferay.Language.get(
									'control-has-outperformed-x-by-at-least-x'
								),
								[
									secondPlaceVariant.dxpVariantName,
									`${toRounded(
										Math.abs(
											secondPlaceVariant.improvement
										),
										2
									)}%`
								]
							) as string
						}
					/>

					<strong>
						{Liferay.Language.get(
							'we-recommend-that-you-keep-control-published-and-complete-this-test'
						)}
					</strong>
				</SummaryAlert>
			) : (
				<SummaryAlert symbol='exclamation-circle'>
					<SummaryTitle
						className='font-weight-bold mb-1'
						label={
							sub(
								Liferay.Language.get(
									'x-has-outperformed-control-by-at-least-x'
								),
								[
									winnerVariant?.dxpVariantName,
									`${toRounded(
										winnerVariant?.improvement,
										2
									)}%`
								]
							) as string
						}
					/>

					<strong>
						{Liferay.Language.get(
							'we-recommend-that-you-publish-the-winning-variant'
						)}
					</strong>
				</SummaryAlert>
			)}

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
								{winnerVariant &&
									winnerVariant?.improvement > 0 && (
										<SummarySection.Variant
											lift={`${toRounded(
												winnerVariant?.improvement,
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
