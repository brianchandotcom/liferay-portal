import Card from 'shared/components/Card';
import Label from '@clayui/label';
import React from 'react';
import {ButtonWithIcon, Icon, Text} from '@clayui/core';
import {LifecycleStages} from 'contacts/pages/account/utils/constants';
import {sub} from 'shared/util/lang';

interface ILifecycleStage {
	accountCount: number;
	averageDaysInStage: number;
	description: string;
	percentage: number;
	stageType: LifecycleStages;
}

const STAGES: ILifecycleStage[] = [
	{
		accountCount: 13,
		averageDaysInStage: 9.8,
		description:
			'Identifies cold accounts showing early intent so Marketing can run targeted ads.',
		percentage: 43.6,
		stageType: LifecycleStages.AWARE
	},
	{
		accountCount: 504,
		averageDaysInStage: 4.6,
		description:
			'The buying committee is researching us. Triggers "warm call" alerts to Sales.',
		percentage: 28.4,
		stageType: LifecycleStages.ENGAGED
	},
	{
		accountCount: 41,
		averageDaysInStage: 4.5,
		description:
			'Active deal. Automatically halts generic marketing spend so Sales can work the account.',
		percentage: 18.2,
		stageType: LifecycleStages.PIPELINE
	},
	{
		accountCount: 22,
		averageDaysInStage: 12,
		description:
			'Identifies cold accounts showing early intent so Marketing can run targeted ads.',
		percentage: 9.8,
		stageType: LifecycleStages.ONBOARDING
	},
	{
		accountCount: 0,
		averageDaysInStage: 0,
		description:
			'Account is healthy and realizing ROI. Safe to pitch expansion/add-ons.',
		percentage: 0,
		stageType: LifecycleStages.ESTABLISHED
	}
];

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
	onFilterClick: (stageType: LifecycleStages) => void;
	percentage: number;
	referencePercentage: number;
	stageType: LifecycleStages;
}

const StageMetrics = ({
	accountCount,
	averageDaysInStage,
	description,
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
					{Liferay.Language.get(stageType)}
				</Text>
				<ButtonWithIcon
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
				<div
					className={`${barClassName} rounded-lg w-100`}
					style={{height: barHeight}}
				/>
			</div>
		</div>
	);
};

interface IStageProgressionProps {
	nextBarHeight: number;
	percentage: number;
	previousBarHeight: number;
}

const StageProgression = ({
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
				className='bg-light d-none d-lg-block mt-auto mx-n2 stage-progression__fill'
				style={{
					clipPath: `polygon(0 ${topLeft}px, 100% ${topRight}px, 100% 100%, 0 100%)`,
					height: REFERENCE_BAR_HEIGHT
				}}
			/>
		</div>
	);
};

const LifecycleChart = () => {
	const onFilterClick = (stageType: LifecycleStages) =>
		console.log(stageType);

	const refPct = STAGES[0]?.percentage ?? 0;

	return (
		<Card className='p-3'>
			<Card.Title>{Liferay.Language.get('accounts-by-stage')}</Card.Title>
			<Card.Body noPadding>
				<div className='mt-1'>
					<Text color='secondary' size={3}>
						{Liferay.Language.get(
							'the-distribution-of-accounts-across-the-lifecycle-stages-within-the-timeframe.'
						)}
					</Text>
					<div className='flex-lg-nowrap h-100 mt-4 no-gutters row'>
						{STAGES.map((stage, index) => {
							const nextStage = STAGES[index + 1];
							return (
								<React.Fragment key={stage.stageType}>
									<StageMetrics
										accountCount={stage.accountCount}
										averageDaysInStage={
											stage.averageDaysInStage
										}
										description={stage.description}
										onFilterClick={onFilterClick}
										percentage={stage.percentage}
										referencePercentage={refPct}
										stageType={stage.stageType}
									/>
									{nextStage && (
										<StageProgression
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
				</div>
			</Card.Body>
		</Card>
	);
};

export default LifecycleChart;
