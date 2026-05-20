import * as API from 'shared/api';
import Card from 'shared/components/Card';
import classNames from 'classnames';
import ClayIcon from '@clayui/icon';
import ClaySticker from '@clayui/sticker';
import Loading from 'shared/components/Loading';
import MultiStepNav from '@clayui/multi-step-nav';
import React from 'react';
import {CUSTOM_DATE_FORMAT, formatUTCDate} from 'shared/util/date';
import {
	IAccountLifecycleStageStatus,
	IAccountLifecycleStatus
} from 'shared/api/lifecycle';
import {
	LifecycleStages,
	lifecycleStagesLabelMap
} from 'contacts/pages/account/utils/constants';
import {sub} from 'shared/util/lang';
import {Text} from '@clayui/core';
import {useParams} from 'react-router-dom';
import {useRequest} from 'shared/hooks/useRequest';

interface LifecycleStatusProps {
	className?: string;
}

const getStageLabel = (stage: IAccountLifecycleStageStatus) => {
	const entry = lifecycleStagesLabelMap[stage.stageType as LifecycleStages];

	return entry?.label ?? stage.description ?? stage.stageType;
};

const LifecycleStatus: React.FC<LifecycleStatusProps> = ({className}) => {
	const {groupId, id: accountId} = useParams<{
		groupId: string;
		id: string;
	}>();

	const {data: lifecyclesData, loading: lifecyclesLoading} = useRequest({
		dataSourceFn: API.lifecycle.fetchAccountLifecycles,
		variables: {groupId}
	});

	const accountLifecycle = lifecyclesData?.[0];

	const {data: statusData, loading: statusLoading} = useRequest({
		dataSourceFn: API.accounts.fetchLifecycleStatus,
		skipRequest: !accountLifecycle,
		variables: {
			accountId,
			accountLifecycleId: accountLifecycle?.id ?? '',
			groupId
		}
	});

	const isLoading =
		lifecyclesLoading || (Boolean(accountLifecycle?.id) && statusLoading);

	if (isLoading) {
		return (
			<Card className={classNames(className, 'p-3')} minHeight={284}>
				<Card.Body>
					<Loading />
				</Card.Body>
			</Card>
		);
	}

	const lifecycle: IAccountLifecycleStatus | undefined = statusData;
	const stages = (lifecycle?.accountLifecycleStageStatuses ?? [])
		.slice()
		.sort((a, b) => a.displayOrder - b.displayOrder);

	const progressionStages = stages.filter(
		stage => stage.stageType !== LifecycleStages.AT_RISK
	);
	const atRiskStage = stages.find(
		stage => stage.stageType === LifecycleStages.AT_RISK
	);

	const activeIndex = progressionStages.reduce(
		(lastIndex, stage, i) => (stage.startDate ? i : lastIndex),
		-1
	);
	const activeStage =
		activeIndex >= 0 ? progressionStages[activeIndex] : undefined;
	const isAtRisk = Boolean(atRiskStage?.startDate && !atRiskStage.endDate);

	return (
		<Card className={classNames(className, 'p-3')} minHeight={284}>
			<Card.Title>
				<Text weight='semi-bold'>
					{Liferay.Language.get('lifecycle-status').toUpperCase()}
				</Text>
				<p>
					<Text color='secondary' size={3} weight='normal'>
						{Liferay.Language.get(
							'shows-the-current-stage-progression-for-this-account'
						)}
					</Text>
				</p>
			</Card.Title>
			<Card.Body className='justify-content-around p-0'>
				<div className='align-items-end d-none d-sm-flex flex-row lifecycle-status-multistep'>
					<MultiStepNav center className='flex-fill pb-0'>
						{progressionStages.map((stage, i) => (
							<MultiStepNav.Item
								active={i === activeIndex}
								expand={i + 1 !== progressionStages.length}
								key={stage.id}
								state={i < activeIndex ? 'complete' : undefined}
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
													CUSTOM_DATE_FORMAT
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
									className={classNames('rounded-circle', {
										'bg-red': isAtRisk
									})}
									displayType='secondary'
								>
									<ClayIcon
										className={
											isAtRisk
												? 'text-white'
												: 'text-secondary'
										}
										symbol='exclamation-circle'
									/>
								</ClaySticker>
							</MultiStepNav.Item>
						</MultiStepNav>
					)}
				</div>
				{(activeStage || atRiskStage) && (
					<div className='d-sm-none lifecycle-status-summary'>
						{activeStage && (
							<>
								<div className='align-items-baseline d-flex justify-content-between'>
									<Text
										color='primary'
										size={3}
										weight='semi-bold'
									>
										{getStageLabel(activeStage)}
									</Text>
									<Text color='secondary' size={3}>
										{sub(
											Liferay.Language.get('step-x-of-x'),
											[
												String(activeIndex + 1),
												String(progressionStages.length)
											]
										)}
									</Text>
								</div>
								{activeStage.startDate && (
									<Text color='secondary' size={3}>
										{formatUTCDate(
											activeStage.startDate,
											CUSTOM_DATE_FORMAT
										)}
									</Text>
								)}
							</>
						)}
						{atRiskStage && (
							<div
								className={classNames(
									'align-items-baseline d-flex justify-content-between',
									{'mt-3': activeStage}
								)}
							>
								<Text
									color='secondary'
									size={3}
									weight='semi-bold'
								>
									{Liferay.Language.get('at-risk')}
								</Text>
								<Text color='secondary' size={3}>
									{isAtRisk
										? Liferay.Language.get('yes')
										: Liferay.Language.get('no')}
								</Text>
							</div>
						)}
					</div>
				)}
			</Card.Body>
		</Card>
	);
};

export default LifecycleStatus;
