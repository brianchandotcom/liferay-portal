import Card from 'shared/components/Card';
import classNames from 'classnames';
import ClayIcon from '@clayui/icon';
import ClaySticker from '@clayui/sticker';
import MultiStepNav from '@clayui/multi-step-nav';
import React from 'react';
import {formatUTCDate} from 'shared/util/date';
import {
	LifecycleStages,
	lifecycleStagesLabelMap
} from 'contacts/pages/account/utils/constants';
import {Text} from '@clayui/core';

interface LifecycleStatusProps {
	className?: string;
}

interface IAccountLifecycleStageStatus {
	description?: string;
	displayOrder: number;
	endDate?: string;
	id: string;
	maxDuration?: number;
	stageType: string;
	startDate?: string;
}

interface IAccountLifecycleStatus {
	id: string;
	name: string;
	stages?: IAccountLifecycleStageStatus[];
}

const STAGE_DATE_FORMAT = 'MMM D, YYYY';

const getStageLabel = (stage: IAccountLifecycleStageStatus) => {
	const entry = lifecycleStagesLabelMap[stage.stageType as LifecycleStages];

	return entry?.label ?? stage.description ?? stage.stageType;
};

// TODO: replace with real data once the AccountLifecycleStatus endpoint is wired.
const MOCK_LIFECYCLE_STATUS: IAccountLifecycleStatus = {
	id: 'mock-status',
	name: 'Default Lifecycle',
	stages: [
		{
			displayOrder: 0,
			endDate: '2026-01-16T00:00:00.000Z',
			id: 'mock-stage-aware',
			stageType: LifecycleStages.AWARE,
			startDate: '2026-01-04T00:00:00.000Z'
		},
		{
			displayOrder: 1,
			id: 'mock-stage-engaged',
			stageType: LifecycleStages.ENGAGED,
			startDate: '2026-01-16T00:00:00.000Z'
		},
		{
			displayOrder: 2,
			id: 'mock-stage-pipeline',
			stageType: LifecycleStages.PIPELINE
		},
		{
			displayOrder: 3,
			id: 'mock-stage-onboarding',
			stageType: LifecycleStages.ONBOARDING
		},
		{
			displayOrder: 4,
			id: 'mock-stage-established',
			stageType: LifecycleStages.ESTABLISHED
		},
		{
			displayOrder: 5,
			id: 'mock-stage-at-risk',
			stageType: LifecycleStages.AT_RISK
		}
	]
};

const LifecycleStatus: React.FC<LifecycleStatusProps> = ({className}) => {
	const lifecycle = MOCK_LIFECYCLE_STATUS;
	const stages = (lifecycle.stages ?? [])
		.slice()
		.sort((a, b) => a.displayOrder - b.displayOrder);

	const progressionStages = stages.filter(
		stage => stage.stageType !== LifecycleStages.AT_RISK
	);
	const atRiskStage = stages.find(
		stage => stage.stageType === LifecycleStages.AT_RISK
	);

	const activeIndex = progressionStages.findIndex(
		stage => stage.startDate && !stage.endDate
	);

	return (
		<Card className={classNames(className, 'p-3')}>
			<Card.Title>
				<Text size={4} weight='semi-bold'>
					{Liferay.Language.get('lifecycle-status').toUpperCase()}
				</Text>
				<p>
					<Text color='secondary' weight='normal'>
						{Liferay.Language.get(
							'current-stage-progression-for-this-account'
						)}
					</Text>
				</p>
			</Card.Title>
			<Card.Body className='justify-content-around p-0'>
				<div className='align-items-end d-flex flex-row lifecycle-status-multistep'>
					<MultiStepNav center className='flex-fill pb-0'>
						{progressionStages.map((stage, i) => (
							<MultiStepNav.Item
								active={i === activeIndex}
								expand={i + 1 !== progressionStages.length}
								key={stage.id}
								state={stage.endDate ? 'complete' : undefined}
							>
								<MultiStepNav.Title>
									{getStageLabel(stage)}
								</MultiStepNav.Title>
								<MultiStepNav.Divider />
								<MultiStepNav.Indicator
									label={1 + i}
									subTitle={
										stage.startDate
											? formatUTCDate(
													stage.startDate,
													STAGE_DATE_FORMAT
											  )
											: undefined
									}
								/>
							</MultiStepNav.Item>
						))}
					</MultiStepNav>
					{atRiskStage && (
						<MultiStepNav center className='ml-3 pb-0'>
							<MultiStepNav.Item>
								<MultiStepNav.Title>
									{getStageLabel(atRiskStage)}
								</MultiStepNav.Title>
								<MultiStepNav.Divider />
								<ClaySticker
									className='rounded-circle'
									displayType='secondary'
								>
									<ClayIcon
										className='text-secondary'
										symbol='exclamation-circle'
									/>
								</ClaySticker>
							</MultiStepNav.Item>
						</MultiStepNav>
					)}
				</div>
			</Card.Body>
		</Card>
	);
};

export default LifecycleStatus;
