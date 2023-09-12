import React from 'react';
import {Column} from 'shared/components/chart-tooltip/types';
import {Modal, Step} from 'experiments/components/summary-base-card/types';

export type Data = {
	key: string;
	value: string;
	id?: string;
};

export type DataPoint = {
	color: string;
	dataKey: string;
	name: string;
	payload: Data;
};

export type DxpSessionVariant = {
	control: boolean;
	dxpVariantName: string;
	sessionHistogram: Array<Histogram>;
};

export type FormattedHistogram = {
	key: Array<Date>;
	value: Array<number>;
};

export type FormatHistogramKeyValue = (
	variants: Array<VariantFormatted>,
	metricUnit: string
) => Function | Object;

export type FormatYAxisFn = (metricUnit: string) => (value: number) => string;

export type GetDateFn = (date: Date) => string;

export type GetFormattedDataTooltip = (
	dataPoint: Array<DataPoint>
) => {
	header: {columns: Column[]}[];
	rows: {columns: Column[]}[];
};

export type GetFormattedHistogramFn = (
	histogram: Array<Histogram>
) => FormattedHistogram;

export type GetFormattedMedianFn = (
	median: number,
	metric: MetricName
) => string;

export type MetricHistogram = {
	processedDate: string;
	variantMetrics: Array<VariantMetric>;
};

export type GetLinkFn = ({
	action,
	id,
	pageURL
}: {
	action?: string;
	id: string;
	pageURL: string;
}) => string;

export type GetFormattedVariantHistogramFn = (
	histogram: Array<VariantHistogram>
) => FormattedHistogram;

export type GetMetricNameFn = (metric: MetricName) => string;

export type GetMetricUnitFn = (metric: MetricName) => string;

export type GetStatusColorFn = (status: Status) => string;

export type GetStatusNameFn = (status: Status) => string;

export type GetStepFn = (step: StepInputs) => Step;

export type GetShortIntervals = (intervals: Array<Date>) => Array<Date>;

export type GetTicksFn = (maxValue: number) => Array<number>;

export type GetVariantLabels = (experiment: {
	bestVariant?: Variant;
	dxpVariantId: string;
	publishedDXPVariantId?: string;
	status: Status;
	winnerDXPVariantId?: string;
}) => Array<{status: string; value: string}>;

export type Histogram = {
	key: string;
	value: string;
};

export type HistogramToBeNormalized = {
	dxpVariants: Array<Variant>;
	goal: {
		metric: MetricName;
	};
	metricsHistogram: Array<MetricHistogram>;
};

export type MetricName =
	| 'BOUNCE_RATE'
	| 'CLICK_RATE'
	| 'MAX_SCROLL_DEPTH'
	| 'TIME_ON_PAGE';

export type MergedVariant = {
	changes: number;
	confidenceInterval: Array<number>;
	control: boolean;
	dxpVariantId: string;
	dxpVariantName: string;
	improvement: number;
	median: number;
	probabilityToWin: number;
	trafficSplit: number;
	uniqueVisitors: number;
};

export type MergedVariantsFn = (
	variants: Array<Variant>,
	variantMetrics: Array<VariantMetric>
) => Array<MergedVariant>;

export type ModalCompleteFn = (
	experimentId: string,
	publishedDXPVariantId: string
) => {
	Component: React.ReactNode;
	props: {
		experimentId: string;
		modalBody: React.ReactNode;
		nextStatus: Status;
		submitMessage: string;
		title: string;
	};
	title: string;
};

export type ModalPublishOtherVariantFn = (
	dxpVariants: Array<Variant>,
	experimentId: string,
	pageURL: string,
	title?: string
) => {
	Component: React.ReactNode;
	props: {
		dxpVariants: Array<Variant>;
		experimentId: string;
		pageURL: string;
		title: string;
	};
	title: string;
};

export type ModalPublishVariantFn = (
	dxpVariantId: string,
	dxpVariantName: string,
	experimentId: string,
	pageURL: string
) => {
	Component: React.ReactNode;
	props: {
		dxpVariantId: string;
		dxpVariantName: string;
		experimentId: string;
		pageURL: string;
		title: string;
	};
	title: string;
};

export type NormalizeHistogramFn = (
	histogram: HistogramToBeNormalized
) => Array<VariantFormatted>;

export type MakeAllRefetchFn = (queries: Array<Function>) => void;

export type SortOrderExperiment = (
	experimentA: DxpSessionVariant | VariantFormatted,
	experimentB: DxpSessionVariant | VariantFormatted
) => number;

export type Status =
	| 'COMPLETED'
	| 'DRAFT'
	| 'FINISHED_NO_WINNER'
	| 'FINISHED_WINNER'
	| 'PAUSED'
	| 'RUNNING'
	| 'SCHEDULED'
	| 'TERMINATED';

export type StepInputs = {
	disabled?: boolean;
	Description: React.FC<React.HTMLAttributes<HTMLElement>>;
	modal?: Modal;
	showIcon?: boolean;
	title: string;
	tooltip?: string;
};

export type TooltipMetric = {
	accessor?: string;
	dataRenderer: (props) => any;
	title: string;
	name: string;
};

export type VariantFormatted = {
	control: boolean;
	dxpVariantName: string;
	dxpVariantId: string;
	variantsHistogram: Array<VariantHistogram>;
};

export type VariantHistogram = {
	processedDate: string;
	median: number;
};

export type Variant = {
	changes: number;
	control: boolean;
	dxpVariantId: string;
	dxpVariantName: string;
	trafficSplit: number;
	uniqueVisitors: number;
};

export type VariantMetric = {
	confidenceInterval: Array<number>;
	dxpVariantId: string;
	improvement: number;
	median: number;
	probabilityToWin: number;
};
