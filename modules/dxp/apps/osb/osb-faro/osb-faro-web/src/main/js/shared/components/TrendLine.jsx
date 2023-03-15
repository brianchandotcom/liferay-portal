import autobind from 'autobind-decorator';
import ClayCharts from 'clay-charts-react';
import getCN from 'classnames';
import React from 'react';
import {hasChanges} from 'shared/util/react';
import {isNumber} from 'lodash';
import {PropTypes} from 'prop-types';

export default class TrendLine extends React.Component {
	static defaultProps = {
		point: {r: 1},
		tooltip: {show: false}
	};

	static propTypes = {
		data: PropTypes.array.isRequired,
		guideLine: PropTypes.number,
		onPointSelect: PropTypes.func,
		point: PropTypes.object,
		positionLine: PropTypes.number,
		tooltip: PropTypes.object
	};

	constructor(props) {
		super(props);

		this._chartRef = React.createRef();
	}

	componentDidMount() {
		window.addEventListener('toggleSidebar', this.flushChart);
	}

	shouldComponentUpdate(nextProps) {
		return hasChanges(this.props, nextProps, 'data');
	}

	componentWillUnmount() {
		window.removeEventListener('toggleSidebar', this.flushChart);
	}

	@autobind
	flushChart() {
		this._chartRef.current.chart.flush(true);
	}

	render() {
		const {
			className,
			data,
			guideLine,
			onPointSelect,
			point,
			positionLine,
			tooltip,
			...otherProps
		} = this.props;

		return (
			<div className={getCN('trendline-root', className)}>
				<ClayCharts
					{...otherProps}
					axis={{x: {show: false}, y: {show: false}}}
					axisX={{show: false}}
					axisY={{show: false}}
					data={{
						columns: [data],
						onover: onPointSelect,
						type: 'spline'
					}}
					grid={{
						x: {
							lines: isNumber(positionLine)
								? [
										{
											class: 'position-line',
											value: positionLine
										}
								  ]
								: undefined,
							show: false
						},
						y: {
							lines: isNumber(guideLine)
								? [
										{
											class: 'guide-line',
											value: String(data[guideLine])
										}
								  ]
								: undefined,
							show: false
						}
					}}
					legend={{show: false}}
					line={{classes: null}}
					point={point}
					ref={this._chartRef}
					splineInterpolationType='monotone-x'
					tooltip={tooltip}
				/>
			</div>
		);
	}
}
