import Card from 'shared/components/Card';
import Label from '@clayui/label';
import React from 'react';
import {ButtonWithIcon, Icon, Text} from '@clayui/core';
import {sub} from 'shared/util/lang';

const stage1 = {
	description: Liferay.Language.get(
		'identifies-cold-accounts-showing-early-intent-so-marketing-can-run-targeted-ads.'
	),
	metric: {
		average: 9.8,
		percentage: 43.6,
		value: 13
	},
	stageName: Liferay.Language.get('aware')
};

const stage2 = {
	description: Liferay.Language.get(
		'the-buying-committee-is-researching-us.-triggers-warm-call-alerts-to-sales.'
	),
	metric: {
		average: 4.6,
		percentage: 28.4,
		value: 64
	},
	stageName: Liferay.Language.get('engaged')
};
const stage3 = {
	description: Liferay.Language.get(
		'active-deal.-automatically-halts-generic-marketing-spend-so-sales-can-work-the-account.'
	),
	metric: {
		average: 4.5,
		percentage: 18.2,
		value: 41
	},
	stageName: Liferay.Language.get('pipeline')
};

const stage4 = {
	description: Liferay.Language.get(
		'identifies-cold-accounts-showing-early-intent-so-marketing-can-run-targeted-ads.'
	),
	metric: {
		average: 12,
		percentage: 9.8,
		value: 22
	},
	stageName: Liferay.Language.get('onboarding')
};

const stage5 = {
	description: Liferay.Language.get(
		'account-is-healthy-and-realizing-roi.-safe-to-pitch-expansion/add-ons.'
	),
	metric: {
		average: 0,
		percentage: 0,
		value: 0
	},
	stageName: Liferay.Language.get('established')
};

const REFERENCE_BAR_HEIGHT = 136;
const MIN_BAR_HEIGHT = 4;

const getBarHeight = (percentage: number, referencePercentage: number) => {
	if (referencePercentage <= 0) return MIN_BAR_HEIGHT;
	return (
		(percentage / referencePercentage) * REFERENCE_BAR_HEIGHT ||
		MIN_BAR_HEIGHT
	);
};

interface IChartSectionProps {
	description: string;
	metric: {
		average: number;
		percentage: number;
		value: number;
	};
	onFilterClick: (lifecycleStage: string) => void;
	referencePercentage: number;
	stageName: string;
}

const ChartSection = ({
	description,
	metric,
	onFilterClick,
	referencePercentage,
	stageName
}: IChartSectionProps) => {
	const barHeight = getBarHeight(metric.percentage, referencePercentage);

	const barClassName = `${
		metric.percentage > 0
			? 'lifecycle-chart-bar'
			: 'empty-lifecycle-chart-bar'
	}`;
	return (
		<div className='col d-flex flex-column p-3'>
			<div className='align-items-center d-flex mb-2'>
				<Text size={4} weight='semi-bold'>
					{stageName}
				</Text>
				<ButtonWithIcon
					borderless
					className='ml-auto'
					displayType='secondary'
					onClick={() => onFilterClick(stageName)}
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
							{metric.value}
						</Text>
					</p>
					<Text color='secondary' size={4}>
						{(
							sub(
								Liferay.Language.get(
									'x-percent-of-all-accounts'
								),
								[metric.percentage]
							) as string
						).toLowerCase()}
					</Text>
				</div>
				<div className='mt-3 text-secondary'>
					{metric.average != 0 ? (
						<>
							<span className='mr-4'>{`${metric.average}`}</span>
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
				className='align-items-end d-flex mt-3 mx-n2'
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

interface IProgressionSectionProps {
	nextBarHeight: number;
	percentage: number;
	previousBarHeight: number;
}

const ProgressionSection = ({
	nextBarHeight,
	percentage,
	previousBarHeight
}: IProgressionSectionProps) => {
	const topLeft = REFERENCE_BAR_HEIGHT - previousBarHeight;
	const topRight = REFERENCE_BAR_HEIGHT - nextBarHeight;

	return (
		<div className='border-light d-flex flex-column progression-section px-2 py-3'>
			<Label className='mt-auto p-0' displayType='info'>
				<span className='inline-item ml-1'>
					{`${percentage}%`}
					<Icon symbol='angle-right-small' />
				</span>
			</Label>
			<div
				className='bg-light mt-auto mx-n2 trapezium-path'
				style={{
					clipPath: `polygon(0 ${topLeft}px, 100% ${topRight}px, 100% 100%, 0 100%)`,
					height: REFERENCE_BAR_HEIGHT
				}}
			/>
		</div>
	);
};

interface IChartProps {
	lifecycleStages: IChartSectionProps[];
}

const LifecycleChart = () => {
	const onFilterClick = (stageName: string) => console.log(stageName);

	const refPct = stage1.metric.percentage;
	const h1 = getBarHeight(stage1.metric.percentage, refPct);
	const h2 = getBarHeight(stage2.metric.percentage, refPct);
	const h3 = getBarHeight(stage3.metric.percentage, refPct);
	const h4 = getBarHeight(stage4.metric.percentage, refPct);
	const h5 = getBarHeight(stage5.metric.percentage, refPct);

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
					<div className='h-100 mt-4 no-gutters row'>
						<ChartSection
							description={stage1.description}
							metric={stage1.metric}
							onFilterClick={onFilterClick}
							referencePercentage={refPct}
							stageName={stage1.stageName}
						/>
						<ProgressionSection
							nextBarHeight={h2}
							percentage={64}
							previousBarHeight={h1}
						/>
						<ChartSection
							description={stage2.description}
							metric={stage2.metric}
							onFilterClick={onFilterClick}
							referencePercentage={refPct}
							stageName={stage2.stageName}
						/>
						<ProgressionSection
							nextBarHeight={h3}
							percentage={64}
							previousBarHeight={h2}
						/>
						<ChartSection
							description={stage3.description}
							metric={stage3.metric}
							onFilterClick={onFilterClick}
							referencePercentage={refPct}
							stageName={stage3.stageName}
						/>
						<ProgressionSection
							nextBarHeight={h4}
							percentage={64}
							previousBarHeight={h3}
						/>
						<ChartSection
							description={stage4.description}
							metric={stage4.metric}
							onFilterClick={onFilterClick}
							referencePercentage={refPct}
							stageName={stage4.stageName}
						/>
						<ProgressionSection
							nextBarHeight={h5}
							percentage={64}
							previousBarHeight={h4}
						/>
						<ChartSection
							description={stage5.description}
							metric={stage5.metric}
							onFilterClick={onFilterClick}
							referencePercentage={refPct}
							stageName={stage5.stageName}
						/>
					</div>
				</div>
			</Card.Body>
		</Card>
	);
};

export default LifecycleChart;
