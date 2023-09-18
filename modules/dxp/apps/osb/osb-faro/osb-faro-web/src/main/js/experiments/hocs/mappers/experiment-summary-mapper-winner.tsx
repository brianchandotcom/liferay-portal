import React from 'react';
import SummarySection from 'experiments/components/summary-section';
import {formatDateToTimeZone} from 'shared/util/date';
import {getMetricName, mergedVariants} from 'experiments/util/experiments';
import {sub} from 'shared/util/lang';
import {toRounded} from 'shared/util/numbers';
import {toThousandsABTesting} from 'experiments/util/experiments';

type Alert = {
	description?: string;
	symbol?: string;
	title?: Array<string> | string;
};

export default ({
	dxpVariants,
	goal,
	metrics: {completion, elapsedDays, variantMetrics},
	sessions,
	startedDate,
	timeZoneId,
	winnerDXPVariantId
}) => {
	const variants = mergedVariants(dxpVariants, variantMetrics);

	const winnerVariant = variants.find(
		({dxpVariantId}) => dxpVariantId === winnerDXPVariantId
	);

	let alert: Alert = {
		symbol: 'check-circle'
	};

	if (winnerVariant) {
		if (winnerVariant.control) {
			const secondPlaceVariant = variants.find(
				({dxpVariantId}) => dxpVariantId !== winnerDXPVariantId
			);

			alert = {
				...alert,
				description: Liferay.Language.get(
					'we-recommend-that-you-keep-control-published-and-complete-this-test'
				),
				title: sub(
					Liferay.Language.get(
						'control-has-outperformed-x-by-at-least-x'
					),
					[
						secondPlaceVariant.dxpVariantName,
						`${toRounded(
							Math.abs(secondPlaceVariant.improvement),
							2
						)}%`
					]
				)
			};
		} else {
			alert = {
				...alert,
				description: Liferay.Language.get(
					'we-recommend-that-you-publish-the-winning-variant'
				),
				title: sub(
					Liferay.Language.get(
						'x-has-outperformed-control-by-at-least-x'
					),
					[
						winnerVariant.dxpVariantName,
						`${toRounded(winnerVariant.improvement, 2)}%`
					]
				)
			};
		}
	}

	return {
		alert,
		header: {
			Description: () =>
				sub(Liferay.Language.get('started-x'), [
					formatDateToTimeZone(startedDate, 'll', timeZoneId)
				]),

			title: Liferay.Language.get('winner-declared')
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
					<SummarySection
						title={Liferay.Language.get('days-running')}
					>
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
							{winnerVariant && winnerVariant.improvement > 0 && (
								<SummarySection.Variant
									lift={`${toRounded(
										winnerVariant.improvement,
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
