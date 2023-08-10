import React from 'react';
import RunExperimentModal from 'experiments/components/modals/RunExperimentModal';
import {
	getExperimentLink,
	getMetricName,
	getStep,
	modalDelete
} from 'experiments/util/experiments';
import {sub} from 'shared/util/lang';

export default ({
	dxpExperienceName,
	dxpSegmentName,
	dxpVariants,
	experimentId,
	goal,
	id,
	pageURL
}) => {
	const currentStep = dxpVariants ? 3 : goal ? 2 : 1;
	const showTooltip = goal && goal.metric === 'CLICK_RATE' && !goal.target;

	return {
		header: {
			cardModals: [modalDelete(experimentId)],
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
					label: dxpExperienceName
						? Liferay.Language.get('change-target')
						: Liferay.Language.get('set-target'),
					link: getExperimentLink({id, pageURL}),
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
					label: goal
						? Liferay.Language.get('change-metric')
						: Liferay.Language.get('set-metric'),
					link: getExperimentLink({id, pageURL}),
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
					label: dxpVariants
						? Liferay.Language.get('edit-variants')
						: Liferay.Language.get('create-variants'),
					link: getExperimentLink({id, pageURL}),
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
					label: Liferay.Language.get('review'),
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
