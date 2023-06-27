import BaseCard from 'shared/components/base-card';
import Card from 'shared/components/Card';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import getMetricsMapper from 'cerebro-shared/hocs/mappers/metrics';
import React, {useCallback, useState} from 'react';
import {ASSET_METRICS} from 'shared/util/constants';
import {compose} from 'redux';
import {DocumentNode} from 'graphql';
import {graphql} from '@apollo/react-hoc';
import {MetricChart} from 'shared/components/metric-card/MetricChart';
import {RangeSelectors, Router} from 'shared/types';
import {withEmpty, withError} from 'cerebro-shared/hocs/utils';
import {withLoading} from 'shared/hoc';

const CHARTS = {
	line: {
		component: MetricChart,
		mapper: getMetricsMapper
	}
};

interface IAssetComponent extends IChartProps {
	assetId: string;
	router: Router;
	showTabs: boolean;
}

interface IChartProps {
	chartHeight: number;
	handleShowPreviousChanged: (newVal: any) => void;
	id: number;
	items?: Object[];
	onRemoveAsset: (id: number) => void;
	panel: {chartType: string};
	rangeSelectors: RangeSelectors;
	showPrevious: boolean;
}

const Chart: React.FC<IChartProps> = ({
	chartHeight,
	handleShowPreviousChanged,
	id,
	items,
	onRemoveAsset,
	panel: {chartType},
	rangeSelectors,
	showPrevious
}) => {
	const Chart = CHARTS[chartType].component;

	return (
		<>
			<Chart
				chartHeight={chartHeight}
				compareToPrevious={showPrevious}
				data={items[0]}
				onCompareToPreviousChange={handleShowPreviousChanged}
				rangeSelectors={rangeSelectors}
			/>

			<div className='d-flex justify-content-end'>
				<ClayButton
					aria-label={Liferay.Language.get('delete')}
					borderless
					className='button-root'
					displayType='secondary'
					onClick={() => onRemoveAsset(id)}
					size='sm'
				>
					<ClayIcon className='icon-root' symbol='trash' />
				</ClayButton>
			</div>
		</>
	);
};

const getMetric = name => {
	const {title, type} = ASSET_METRICS.find(({key}) => key == name);
	return [{name, title, type}];
};

const getMapper = ({chartType, metric}) => {
	const mapper = CHARTS[chartType].mapper;

	if (chartType === 'line') {
		return mapper(({custom}) => custom, getMetric(metric));
	}

	return mapper(({custom}) => custom);
};

interface IAssetCardProps extends React.HTMLAttributes<HTMLElement> {
	assetId: string;
	itemQuery: DocumentNode;
	label: string;
	legacyDropdownRangeKey: boolean;
	onRemoveAsset: () => void;
	panel: {
		chartType: string;
		metric: string;
	};
	rangeSelector?: RangeSelectors;
	router: Router;
}

const AssetCard: React.FC<IAssetCardProps> = ({
	assetId,
	className = 'analytics-custom-metrics-card',
	id,
	itemQuery,
	label,
	legacyDropdownRangeKey,
	onRemoveAsset,
	panel
}) => {
	const AssetComponent = (compose(
		graphql(itemQuery, getMapper(panel)),
		withLoading(),
		withError(),
		withEmpty()
	)(Chart) as unknown) as React.FC<IAssetComponent>;

	const [showPrevious, setShowPrevious] = useState(false);

	const handleShowPreviousChanged = useCallback(
		newVal => setShowPrevious(newVal),
		[]
	);

	return (
		<BaseCard
			className={className}
			label={label}
			legacyDropdownRangeKey={legacyDropdownRangeKey}
			minHeight={536}
		>
			{({rangeSelectors, router}) => (
				<Card.Body>
					<AssetComponent
						assetId={assetId}
						chartHeight={416}
						handleShowPreviousChanged={handleShowPreviousChanged}
						id={Number(id)}
						onRemoveAsset={onRemoveAsset}
						panel={panel}
						rangeSelectors={rangeSelectors}
						router={router}
						showPrevious={showPrevious}
						showTabs={false}
					/>
				</Card.Body>
			)}
		</BaseCard>
	);
};

export default AssetCard;
