import * as API from 'shared/api';
import Card from 'shared/components/Card';
import classNames from 'classnames';
import Label from '@clayui/label';
import Loading from 'shared/components/Loading';
import React from 'react';
import {ButtonWithIcon, Icon, Text} from '@clayui/core';
import {
	LifecycleStages,
	lifecycleStagesLabelMap
} from 'contacts/pages/account/utils/constants';
import {SectionHeader} from 'shared/components/SectionHeader';
import {sub} from 'shared/util/lang';
import {useLifecycle} from 'lifecycle/context/LifecycleContext';
import {useParams} from 'react-router-dom';
import {useRequest} from 'shared/hooks/useRequest';

interface ILifecycleStage {
	accountCount: number;
	averageDaysInStage: number;
	description: string;
	percentage: number;
	stageType: LifecycleStages;
}

const EMPTY_STAGES: ILifecycleStage[] = [
	LifecycleStages.AWARE,
	LifecycleStages.ENGAGED,
	LifecycleStages.PIPELINE,
	LifecycleStages.ONBOARDING,
	LifecycleStages.ESTABLISHED
].map(stageType => ({
	accountCount: 0,
	averageDaysInStage: 0,
	description: '',
	percentage: 0,
	stageType
}));

const REFERENCE_BAR_HEIGHT = 136;
const MIN_BAR_HEIGHT = 4;

const getBarHeight = (percentage: number, referencePercentage: number) => {
	if (referencePercentage <= 0) return MIN_BAR_HEIGHT;
	return (
		(percentage / referencePercentage) * REFERENCE_BAR_HEIGHT ||
		MIN_BAR_HEIGHT
	);
};

const getProgressionPercentage = (current: number, next: number) =>
	current === 0 ? 0 : Math.round((next / current) * 100);

interface IStageMetricsProps {
	accountCount: number;
	averageDaysInStage: number;
	description: string;
	empty?: boolean;
	onFilterClick: (stageType: LifecycleStages) => void;
	percentage: number;
	referencePercentage: number;
	stageType: LifecycleStages;
}

const StageMetrics = ({
	accountCount,
	averageDaysInStage,
	description,
	empty,
	onFilterClick,
	percentage,
	referencePercentage,
	stageType
}: IStageMetricsProps) => {
	const barHeight = getBarHeight(percentage, referencePercentage);

	const barClassName =
		percentage > 0 ? 'stage-metrics__bar' : 'stage-metrics__bar--empty';

	return (
		<div className='col-12 col-lg d-flex flex-column p-3 stage-metrics'>
			<div className='align-items-center d-flex mb-2'>
				<Text size={4} weight='semi-bold'>
					{lifecycleStagesLabelMap[stageType].label}
				</Text>
				<ButtonWithIcon
					aria-labelledby='title'
					borderless
					className='ml-auto'
					displayType='secondary'
					onClick={() => onFilterClick(stageType)}
					size='sm'
					symbol='filter'
				/>
			</div>
			<Text as='p' color='secondary'>
				{description}
			</Text>
			<div className='justify-self-center mt-auto'>
				<div>
					<p className='mb-0'>
						<Text size={7} weight='bold'>
							{accountCount}
						</Text>
					</p>
					<Text color='secondary' size={4}>
						{(
							sub(
								Liferay.Language.get(
									'x-percent-of-all-accounts'
								),
								[percentage]
							) as string
						).toLowerCase()}
					</Text>
				</div>
				<div className='mt-3 text-secondary'>
					{averageDaysInStage != 0 ? (
						<>
							<span className='mr-4'>{`${averageDaysInStage}`}</span>
							<span>
								{Liferay.Language.get('avg.-day').toLowerCase()}
							</span>
						</>
					) : (
						<>
							<span className='mr-4'>{'—'}</span>
							<span>
								{Liferay.Language.get(
									'no-activity'
								).toLowerCase()}
							</span>
						</>
					)}
				</div>
			</div>
			<div
				className='align-items-end d-none d-lg-flex mt-3 mx-n2'
				style={{height: REFERENCE_BAR_HEIGHT}}
			>
				{!empty && (
					<div
						className={`${barClassName} rounded-lg w-100`}
						style={{height: barHeight}}
					/>
				)}
			</div>
		</div>
	);
};

interface IStageProgressionProps {
	empty?: boolean;
	nextBarHeight: number;
	percentage: number;
	previousBarHeight: number;
}

const StageProgression = ({
	empty,
	nextBarHeight,
	percentage,
	previousBarHeight
}: IStageProgressionProps) => {
	const topLeft = REFERENCE_BAR_HEIGHT - previousBarHeight;
	const topRight = REFERENCE_BAR_HEIGHT - nextBarHeight;

	return (
		<div className='align-items-center align-items-lg-stretch border-light col-12 col-lg-auto d-flex flex-lg-column flex-row justify-content-center justify-content-lg-start px-2 py-3 stage-progression'>
			<Label className='mt-lg-auto p-0' displayType='info'>
				<span className='inline-item ml-1'>
					{`${percentage}%`}
					<span className='d-lg-none ml-1'>
						{Liferay.Language.get(
							'conversion-to-next-stage'
						).toLowerCase()}
					</span>
					<Icon symbol='angle-right-small' />
				</span>
			</Label>
			<div
				className={classNames([
					'd-none',
					'd-lg-block',
					'mt-auto',
					'mx-n2',
					empty ? '' : 'bg-light stage-progression__fill'
				])}
				style={{
					clipPath: empty
						? undefined
						: `polygon(0 ${topLeft}px, 100% ${topRight}px, 100% 100%, 0 100%)`,
					height: REFERENCE_BAR_HEIGHT
				}}
			/>
		</div>
	);
};

const LifecycleChart = () => {
	const {updateFilters} = useLifecycle();

	const {groupId} = useParams();

	const {
		data: stagesData,
		error,
		loading
	} = useRequest({
		dataSourceFn: API.lifecycle.fetchLifecycleStages as (params: {
			[key: string]: any;
		}) => Promise<any>,
		variables: {
			groupId,
			lifecycleId: '1'
		}
	});

	const isEmpty = error || !stagesData?.length;

	const stages: ILifecycleStage[] = isEmpty ? EMPTY_STAGES : stagesData;

	const onFilterClick = (stageType: LifecycleStages) =>
		updateFilters({lifecycleStageFilter: stageType});

	const refPct = stages[0]?.percentage ?? 0;

	return (
		<>
			<SectionHeader
				icon='polls'
				title={Liferay.Language.get('lifecycle-stages')}
			/>
			<Card className='p-3'>
				<Card.Title>
					{Liferay.Language.get('accounts-by-stage')}
				</Card.Title>
				<Card.Body noPadding>
					<div className='mt-1'>
						<Text color='secondary' size={3}>
							{Liferay.Language.get(
								'the-distribution-of-accounts-across-the-lifecycle-stages-within-the-timeframe.'
							)}
						</Text>
						{loading ? (
							<Loading className='mt-4' />
						) : (
							<div className='flex-lg-nowrap h-100 mt-4 no-gutters row'>
								{stages.map((stage, index) => {
									const nextStage = stages[index + 1];
									return (
										<React.Fragment key={stage.stageType}>
											<StageMetrics
												accountCount={
													stage.accountCount
												}
												averageDaysInStage={
													stage.averageDaysInStage
												}
												description={stage.description}
												empty={isEmpty}
												onFilterClick={onFilterClick}
												percentage={stage.percentage}
												referencePercentage={refPct}
												stageType={stage.stageType}
											/>
											{nextStage && (
												<StageProgression
													empty={isEmpty}
													nextBarHeight={getBarHeight(
														nextStage.percentage,
														refPct
													)}
													percentage={getProgressionPercentage(
														stage.accountCount,
														nextStage.accountCount
													)}
													previousBarHeight={getBarHeight(
														stage.percentage,
														refPct
													)}
												/>
											)}
										</React.Fragment>
									);
								})}
							</div>
						)}
					</div>
				</Card.Body>
			</Card>
		</>
	);
};

export default LifecycleChart;
