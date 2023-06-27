import autobind from 'autobind-decorator';
import ClayChart from 'clay-charts-react';
import getCN from 'classnames';
import Loading from 'shared/components/Loading';
import NoResultsDisplay from 'shared/components/NoResultsDisplay';
import omitDefinedProps from 'shared/util/omitDefinedProps';
import React from 'react';
import {defer, get, merge} from 'lodash';
import {hasChanges} from 'shared/util/react';
import {PropTypes} from 'prop-types';

const DEFAULT_HEIGHT = 328;

export const AREA = 'area';
export const BAR_CHART = 'bar';
export const COMBINED_CHART = 'combined';
export const DONUT_CHART = 'donut';
export const GAUGE_CHART = 'gauge';
export const LINE_CHART = 'line';
export const SPLINE_CHART = 'spline';

export const CHART_COLORS = [
	'#4B9BFF',
	'#FFB46E',
	'#FF5F5F',
	'#50D2A0',
	'#FF73C3',
	'#9CE269',
	'#B077FF',
	'#FFD76E',
	'#5FC8FF'
];

export const CHART_COLOR_NAMES = {
	greyjoy: '#000000',
	lannister: '#FF5F5F',
	martell: '#50D2A0',
	martellD2: '#31BE88',
	martellD4: '#26966B',
	martellL1: '#64D7AB',
	martellL2: '#79DCB6',
	martellL4: '#A1E7CC',
	mormont: '#FFB46E',
	mormontD2: '#FF9A3B',
	mormontL2: '#FFCEA1',
	mormontL4: '#FFE9D4',
	stark: '#4B9BFF',
	starkD2: '#187FFF',
	starkL2: '#7EB7FF',
	starkL4: '#B1D4FF'
};

const CHART_TYPES = [
	AREA,
	BAR_CHART,
	COMBINED_CHART,
	DONUT_CHART,
	GAUGE_CHART,
	LINE_CHART,
	SPLINE_CHART
];

/**
 * Convert previous api to current api.
 * @param {Array} columnData
 */
function convertData(columnData, chartType = COMBINED_CHART) {
	return columnData.reduce(
		(acc, {axis, color, data, id, type}) => {
			acc.columns = acc.columns.concat([[id, ...data]]);

			if (chartType === COMBINED_CHART) {
				if (!acc.types) {
					acc.types = {};
				}

				acc.types = {...acc.types, [id]: type};
			} else {
				acc.type = chartType;
			}

			if (axis) {
				acc.axes = {
					...acc.axes,
					[id]: axis
				};
			}

			if (color) {
				if (!acc.colors) {
					acc.colors = {};
				}

				acc.colors = {...acc.colors, [id]: color};
			}

			return acc;
		},
		{axes: {}, columns: []}
	);
}

export default class Chart extends React.Component {
	static defaultProps = {
		alwaysShowSelectedTooltip: false,
		axisRotated: false,
		height: DEFAULT_HEIGHT,
		legend: {},
		line: {},
		otherData: {},
		point: {},
		unloadBeforeLoad: true
	};

	static propTypes = {
		alwaysShowSelectedTooltip: PropTypes.bool,
		axisRotated: PropTypes.bool,
		axisX: PropTypes.object,
		axisY: PropTypes.object,
		axisY2: PropTypes.object,
		chartType: PropTypes.oneOf(CHART_TYPES),
		className: PropTypes.string,
		data: PropTypes.array.isRequired,
		dataId: PropTypes.string.isRequired,
		generateChartOnLoad: PropTypes.bool,
		grid: PropTypes.object,
		height: PropTypes.number,
		id: PropTypes.string.isRequired,
		initialSelectedIndexes: PropTypes.array,
		legend: PropTypes.object,
		line: PropTypes.object,
		loading: PropTypes.bool,
		noResultsProps: PropTypes.object,
		onPointSelect: PropTypes.func,
		otherData: PropTypes.object,
		point: PropTypes.object,
		unloadBeforeLoad: PropTypes.bool,
		x: PropTypes.string,
		y2Label: PropTypes.string,
		yLabel: PropTypes.string
	};

	constructor(props) {
		super(props);

		this._chartRef = React.createRef();
	}

	componentDidMount() {
		window.addEventListener('toggleSidebar', this.flushChart);
	}

	shouldComponentUpdate(nextProps) {
		return hasChanges(this.props, nextProps, 'data', 'loading');
	}

	componentDidUpdate(prevProps) {
		const {generateChartOnLoad, initialSelectedIndexes} = this.props;

		if (hasChanges(prevProps, this.props, 'data')) {
			this.updateSelectedIndexes(initialSelectedIndexes);

			if (generateChartOnLoad) {
				this.generateChart();
			}
		}
	}

	componentWillUnmount() {
		window.removeEventListener('toggleSidebar', this.flushChart);

		if (this._deferredSelect) {
			clearTimeout(this._deferredSelect);
		}
	}

	@autobind
	flushChart() {
		const chart = this.getChartRef();

		if (chart) {
			chart.flush(true);
		}

		this.handleShowTooltip();
	}

	generateChart() {
		const billboardReact = get(this._chartRef, ['current']);

		if (billboardReact) {
			billboardReact.generateChart();
		}
	}

	@autobind
	handleChartSelect(point) {
		const {onPointSelect} = this.props;

		onPointSelect && onPointSelect(point);
	}

	getChartRef() {
		return get(this._chartRef, ['current', 'chart']);
	}

	/**
	 * Only show tooltip when a single item is selected
	 */
	@autobind
	handleShowTooltip() {
		if (this.props.alwaysShowSelectedTooltip) {
			const chart = this.getChartRef();

			const selectedPoints = chart.selected();

			const multiSelectEnabled =
				chart.internal.config.data_selection_multiple;

			if (selectedPoints.length && !multiSelectEnabled) {
				chart.tooltip.show({
					index: selectedPoints[0].index
				});
			}
		}
	}

	@autobind
	updateSelectedIndexes(indexes = []) {
		const {dataId, id} = this.props;

		// Using `defer` so `chart` will be defined.
		this._deferredSelect = defer(() => {
			const chart = this.getChartRef();

			if (chart) {
				chart.select(`${id}${dataId}`, indexes, true);
			}
		});
	}

	/**
	 * Public method for manually selecting a point. This is often used for the
	 * first render, which is why the `defer` is necessary, otherwise the
	 * `chart` is undefined.
	 * @param {Array} indexes - The indexes you want to select
	 */
	select(indexes) {
		const {dataId, id} = this.props;

		this._deferredSelect = defer(() => {
			const chart = this.getChartRef();

			if (chart) {
				chart.select(`${id}${dataId}`, indexes);
			}

			this.handleShowTooltip();
		});
	}

	/**
	 * Public method for manually unselecting a point.
	 * @param {Array} indexes - The indexes you want to unselect
	 */
	unselect(indexes) {
		const {alwaysShowSelectedTooltip, dataId, id} = this.props;

		const chart = this.getChartRef();

		if (chart) {
			chart.unselect(`${id}${dataId}`, indexes);

			if (alwaysShowSelectedTooltip) {
				chart.tooltip.hide();
			}
		}
	}

	renderChart() {
		const {
			axisRotated,
			axisX,
			axisY,
			axisY2,
			chartType,
			data,
			grid,
			height,
			legend,
			line,
			loading,
			noResultsProps,
			onPointSelect,
			otherData,
			point,
			unloadBeforeLoad,
			x,
			...otherProps
		} = this.props;

		if (data.length) {
			return (
				<>
					{/* eslint-disable */}
					<ClayChart
						{...omitDefinedProps(otherProps, Chart.propTypes)}
						axis={{
							rotated: axisRotated,
							x: merge(
								{tick: {outer: false, show: false}},
								axisX
							),
							y: merge(
								{tick: {outer: false, show: false}},
								axisY
							),
							y2: merge(
								{tick: {outer: false, show: false}},
								axisY2
							)
						}}
						data={{
							...convertData(data, chartType),
							onclick: this.handleChartSelect,
							selection: onPointSelect
								? {
										enabled: true,
										grouped: true,
										multiple: false
								  }
								: {},
							x,
							...otherData
						}}
						grid={merge({x: {show: false}, y: {show: true}}, grid)}
						legend={merge({show: false}, legend)}
						line={merge({classes: null}, line)}
						onout={this.handleShowTooltip}
						point={merge(
							{
								focus: {expand: {enabled: true, r: 5}},
								pattern: ['circle'],
								r: 0,
								select: {r: 5}
							},
							point
						)}
						ref={this._chartRef}
						selection={
							onPointSelect
								? {
										enabled: true,
										grouped: true,
										multiple: false
								  }
								: {}
						}
						unloadBeforeLoad={unloadBeforeLoad}
						size={{height}}
					/>
					{/* eslint-enable */}
				</>
			);
		} else if (!loading) {
			return <NoResultsDisplay {...noResultsProps} />;
		}
	}

	render() {
		const {className, loading, onPointSelect, y2Label, yLabel} = this.props;

		const classes = getCN('chart-root', className, {
			selectable: onPointSelect
		});

		return (
			<div className={classes}>
				{(yLabel || y2Label) && (
					<div className='chart-labels'>
						<span>{yLabel}</span>

						<span>{y2Label}</span>
					</div>
				)}

				{this.renderChart()}

				{loading && <Loading />}
			</div>
		);
	}
}
