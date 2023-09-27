import React from 'react';
import SummarySection from 'experiments/components/summary-section';
import {formatDateToTimeZone} from 'shared/util/date';
import {getMetricName} from 'experiments/util/experiments';
import {sub} from 'shared/util/lang';
import {toRounded} from 'shared/util/numbers';
import {toThousandsABTesting} from 'experiments/util/experiments';

const getAlertValues = ({dxpVariants, metrics, winnerDXPVariantId}) => {
	const getWinnerAndSecondPlaceVariants = () => {
		let secondPlaceVariant;
		let winnerVariant;

		dxpVariants.forEach(({dxpVariantId, dxpVariantName}) => {
			if (dxpVariantId === winnerDXPVariantId) {
				winnerVariant = {dxpVariantId, dxpVariantName};
			} else {
				secondPlaceVariant = {dxpVariantId, dxpVariantName};
			}
		});

		return {secondPlaceVariant, winnerVariant};
	};

	const {
		secondPlaceVariant,
		winnerVariant
	} = getWinnerAndSecondPlaceVariants();

	const getWinnerVariantMetrics = () => {
		let winnerVariantMetrics;
		let secondPlaceVariantMetrics;

		metrics.variantMetrics.forEach(({dxpVariantId, improvement}) => {
			if (dxpVariantId === winnerDXPVariantId) {
				winnerVariantMetrics = {dxpVariantId, improvement};
			} else {
				secondPlaceVariantMetrics = {dxpVariantId, improvement};
			}
		});

		return {secondPlaceVariantMetrics, winnerVariantMetrics};
	};

	const {
		secondPlaceVariantMetrics,
		winnerVariantMetrics
	} = getWinnerVariantMetrics();

	return winnerDXPVariantId
		? winnerVariant.dxpVariantId !== 'DEFAULT'
			? {
					description: Liferay.Language.get(
						'while-some-improvement-was-observed-the-current-test-has-not-gathered-sufficient-data-to-confidently-determine-a-winner'
					),
					symbol: 'exclamation-circle',
					title: sub(
						Liferay.Language.get(
							'x-has-outperformed-x-by-at-least-x-percent'
						),
						[
							winnerVariant.dxpVariantName,
							secondPlaceVariant.dxpVariantName,
							Math.abs(winnerVariantMetrics.improvement).toFixed(
								2
							)
						]
					)
			  }
			: {
					description: Liferay.Language.get(
						'while-some-improvement-was-observed-the-current-test-has-not-gathered-sufficient-data-to-confidently-determine-a-winner'
					),
					symbol: 'exclamation-circle',
					title: sub(
						Liferay.Language.get(
							'x-has-outperformed-x-by-at-least-x-percent'
						),
						[
							winnerVariant.dxpVariantName,
							secondPlaceVariant.dxpVariantName,
							Math.abs(
								secondPlaceVariantMetrics.improvement
							).toFixed(2)
						]
					)
			  }
		: {
				description: Liferay.Language.get(
					'while-some-improvement-was-observed-the-current-test-has-not-gathered-sufficient-data-to-confidently-determine-a-winner'
				),
				symbol: 'exclamation-circle',
				title: Liferay.Language.get('there-is-no-clear-winner')
		  };
};

export default ({
	bestVariant,
	dxpVariants,
	finishedDate,
	goal,
	metrics: {completion, elapsedDays, estimatedDaysLeft, variantMetrics},
	sessions,
	startedDate,
	timeZoneId,
	winnerDXPVariantId
}) => ({
	alert: getAlertValues({
		dxpVariants,
		metrics: {
			completion,
			elapsedDays,
			estimatedDaysLeft,
			variantMetrics
		},
		winnerDXPVariantId
	}),
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
