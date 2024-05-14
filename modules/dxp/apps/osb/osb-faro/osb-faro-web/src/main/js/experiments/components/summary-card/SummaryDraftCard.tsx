import Card from 'shared/components/Card';
import ClayMultiStep from '../clay-multi-step';
import getCN from 'classnames';
import React from 'react';
import {getMetricName} from 'experiments/util/experiments';
import {sub} from 'shared/util/lang';
import {SummaryBaseCard} from './SummaryBaseCard';
import {SummaryTitle} from './SummaryTitle';

export const SummaryDraftCard = ({
	experiment: {dxpExperienceName, dxpSegmentName, dxpVariants, goal, status}
}) => {
	const currentStep = dxpVariants ? 3 : goal ? 2 : 1;

	const totalVariants = dxpVariants?.filter(({control}) => !control).length;

	const steps = [
		{
			Description: ({className}) => (
				<span className={className}>
					{dxpExperienceName ? (
						<>
							<div>
								<span className='text-secondary mr-1'>
									{`${Liferay.Language.get('experience')}:`}
								</span>

								{dxpExperienceName}
							</div>

							<div>
								<span className='text-secondary mr-1'>
									{`${Liferay.Language.get('segment')}:`}
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
		},
		{
			Description: ({className}) => (
				<span className={className}>
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
		},
		{
			Description: ({className}) => (
				<span className={className}>
					{dxpVariants
						? sub(
								dxpVariants && totalVariants > 1
									? Liferay.Language.get('x-variants')
									: Liferay.Language.get('x-variant'),
								[totalVariants]
						  )
						: Liferay.Language.get('no-variants-created')}
				</span>
			),
			title: Liferay.Language.get('variants')
		},
		{
			Description: ({className}) => (
				<span className={className}>
					{Liferay.Language.get(
						'review-traffic-split-and-run-your-test'
					)}
				</span>
			),
			title: Liferay.Language.get('review-&-run')
		}
	];

	return (
		<SummaryBaseCard status={status.toLowerCase()}>
			<SummaryBaseCard.Header
				Description={() =>
					Liferay.Language.get('finish-the-setup-to-run-the-test')
				}
				title={Liferay.Language.get('test-is-in-draft-mode')}
			/>

			<SummaryBaseCard.Body>
				<div className='w-100 mt-4'>
					<SummaryTitle
						className='mb-4'
						label={Liferay.Language.get('test-target')}
					/>

					<ClayMultiStep
						current={currentStep}
						showIndicatorLabel={false}
					>
						{steps.map(({Description, title}, index) => (
							<ClayMultiStep.Item
								Body={() => (
									<Card
										className={getCN(
											'analytics-summary-card-step-content',
											{
												[`analytics-summary-card-step-content-${status}`]: status
											}
										)}
									>
										<Card.Body>
											<div className='h4'>{title}</div>

											<Description className='analytics-summary-card-step-content-description' />
										</Card.Body>
									</Card>
								)}
								key={index}
							/>
						))}
					</ClayMultiStep>
				</div>
			</SummaryBaseCard.Body>
		</SummaryBaseCard>
	);
};
