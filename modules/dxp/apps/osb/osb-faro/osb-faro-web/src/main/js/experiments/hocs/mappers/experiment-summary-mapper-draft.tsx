import React from 'react';
import RunExperimentModal from 'experiments/components/modals/RunExperimentModal';
import {getMetricName, getStep} from 'experiments/util/experiments';
import {sub} from 'shared/util/lang';

export default ({
	dxpExperienceName,
	dxpSegmentName,
	dxpVariants,
	experimentId,
	goal
}) => {
	const currentStep = dxpVariants ? 3 : goal ? 2 : 1;
	const showTooltip = goal && goal.metric === 'CLICK_RATE' && !goal.target;

	return {
		header: {
			Description: () =>
				Liferay.Language.get('finish-the-setup-to-run-the-test'),
			title: Liferay.Language.get('test-is-in-draft-mode')
		},
		setup: {
			current: currentStep,
			steps: [
				getStep({
					Description: props => (
						<span {...props}>
							{dxpExperienceName ? (
								<>
									<div>
										<span className='text-secondary mr-1'>
											{`${Liferay.Language.get(
												'experience'
											)}:`}
										</span>
										{dxpExperienceName}
									</div>
									<div>
										<span className='text-secondary mr-1'>
											{`${Liferay.Language.get(
												'segment'
											)}:`}
										</span>
										{dxpSegmentName}
									</div>
								</>
							) : (
								Liferay.Language.get(
									'select-a-control-experience-and-target-segment-for-your-test'
								)
							)}
						</span>
					),
					title: Liferay.Language.get('test-target')
				}),
				getStep({
					Description: props => (
						<span {...props}>
							{goal ? (
								<strong>{getMetricName(goal.metric)}</strong>
							) : (
								Liferay.Language.get(
									'choose-a-metric-that-determines-your-campaigns-success'
								)
							)}
						</span>
					),
					title: Liferay.Language.get('test-metric')
				}),
				getStep({
					Description: props => {
						const totalVariants =
							dxpVariants &&
							dxpVariants.filter(({control}) => !control).length;

						const labelVariants =
							dxpVariants && totalVariants > 1
								? Liferay.Language.get('x-variants')
								: Liferay.Language.get('x-variant');

						return (
							<span {...props}>
								{dxpVariants
									? sub(labelVariants, [totalVariants])
									: Liferay.Language.get(
											'no-variants-created'
									  )}
							</span>
						);
					},
					title: Liferay.Language.get('variants')
				}),
				getStep({
					Description: props => (
						<span {...props}>
							{Liferay.Language.get(
								'review-traffic-split-and-run-your-test'
							)}
						</span>
					),
					disabled: showTooltip,
					modal: {
						Component: RunExperimentModal,
						props: {
							dxpVariants,
							experimentId
						}
					},
					showIcon: false,
					title: Liferay.Language.get('review-&-run'),
					tooltip:
						showTooltip &&
						dxpVariants &&
						Liferay.Language.get(
							'select-the-target-element-on-liferay-dxp-to-run-the-test'
						)
				})
			],
			title: Liferay.Language.get('setup')
		}
	};
};
