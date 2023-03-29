import * as d3 from 'd3';
import autobind from 'autobind-decorator';
import ChartTooltip from 'shared/components/chart-tooltip';
import React from 'react';
import ReactDOMServer from 'react-dom/server';
import {geomap as ClayGeoMap} from 'clay-charts-shared';
import {Colors} from 'shared/util/charts';
import {toThousands} from 'shared/util/numbers';

/**
 * GeoMap
 * @class
 */
class GeoMap extends ClayGeoMap.Geomap {
	constructor(config) {
		super(config);

		this.metriclabel = config.metriclabel
			? config.metriclabel
			: Liferay.Language.get('views');
		this.isEmpty = config.isEmpty ? config.isEmpty : false;
		this.selected = config.selected ? config.selected : null;

		this._height = config.height;
		this._width = config.width;
	}

	attached() {
		super.attached();

		this.tooltip = d3
			.select('body')
			.selectAll('div.analytics-geomap')
			.data([0])
			.selectAll('div')
			.data([0])
			.append('div')
			.attr('class', 'bb-tooltip-container')
			.style('display', 'none');
	}

	/**
	 * Click handler
	 * @param {Object} d
	 * @protected
	 */
	// eslint-disable-next-line require-jsdoc, no-underscore-dangle
	_handleClick() {}

	/**
	 * Mouse over handler
	 * @param {Object} feature
	 * @param {Number} idx
	 * @param {Array} selection
	 */
	// eslint-disable-next-line require-jsdoc, no-underscore-dangle
	_handleMouseOut(feature, idx, selection) {
		const node = selection[idx];
		d3.select(node).style('fill', this.handleNodeColor.bind(this));
		this.tooltip.style('display', 'none');
	}

	/**
	 * Handle Node Color
	 * @param {object} feature
	 */
	handleNodeColor(feature) {
		if (this.isEmpty) {
			return Colors.mapEmpty;
		} else if (this._selected) {
			return feature.id === this._selected.id
				? Colors.mapSelected
				: Colors.mapEmpty;
		} else if (feature.properties.total > 0) {
			return this._fillFn.bind(this)(feature);
		}

		return Colors.mapEmpty;
	}

	/**
	 * Mouse over handler
	 * @param {Object} feature
	 * @param {Number} idx
	 * @param {Array} selection
	 * @protected
	 */
	// eslint-disable-next-line require-jsdoc, no-underscore-dangle
	_handleMouseOver(feature, idx, selection) {
		const node = selection[idx];
		d3.select(node).style(
			'fill',
			this.isEmpty
				? Colors.mapEmpty
				: this._selected
				? this.handleNodeColor(feature)
				: Colors.mapSelected
		);

		this.handleShowPopover(feature);
	}

	/**
	 * Handle Mouse Move
	 */
	handleMouseMove() {
		const {height, width} = this.tooltip.node().getBoundingClientRect();

		const mousePositions = this.alignTooltip(d3.event, width, height);
		this.tooltip
			.style('left', `${mousePositions.left}px`)
			.style('top', `${mousePositions.top}px`);
	}

	/**
	 * Handle Show Popover
	 * @param {object} feature
	 */
	handleShowPopover(feature) {
		if (this._selected) {
			if (feature.id === this._selected.id) {
				this.tooltip
					.html(this.formatTooltipData(feature))
					.style('display', null);
			} else {
				this.tooltip.style('display', 'none');
			}
		} else {
			this.tooltip
				.html(this.formatTooltipData(feature))
				.style('display', null);
		}
	}

	/**
	 * Data load handler
	 * @param {Error} err
	 * @param {Object} mapData
	 * @protected
	 */
	// eslint-disable-next-line require-jsdoc, no-underscore-dangle
	_onDataLoad(err, mapData) {
		if (err) {
			throw err;
		}

		if (!mapData || !mapData.features.length) {
			return;
		}

		const features = mapData.features;

		// Calculate domain based on values received
		const values = features.map(f => f.properties[this._color.value]);

		this._domainMin = Math.min.apply(null, values);
		this._domainMax = Math.max.apply(null, values);

		this.colorScale = d3
			.scaleQuantize()
			.domain([this._domainMin, this._domainMax])
			.range(Colors.mapBluePallete);

		const bounds = this.svg.node().getBoundingClientRect();

		this.projection = d3
			.geoMercator()
			.scale(100)
			.translate([bounds.width / 2, bounds.height / 2])
			.fitWidth(bounds.width, mapData);

		this.path = d3.geoPath().projection(this.projection);

		this.mapLayerHandler(features);
	}

	/**
	 * Handle mapLayer
	 * @param  {} features
	 */
	mapLayerHandler(features) {
		this.mapLayer
			.selectAll('path')
			.data(features)
			.enter()
			.append('path')
			.attr('d', this.path)
			.attr('vector-effect', 'non-scaling-stroke')
			.attr('fill', this.handleNodeColor.bind(this))
			.on('click', this._handleClickHandler)
			.on('mouseout', this._handleMouseOut.bind(this))
			.on('mousemove', this.handleMouseMove.bind(this))
			.on('mouseover', this._handleMouseOver.bind(this));
	}

	@autobind
	alignTooltip(event, width, height) {
		const arrowPopoverSize = 6;
		const tooltipDistance = 8;

		const {layerX, layerY} = event;

		return {
			left: layerX - width / 2,
			top: layerY - (height + arrowPopoverSize + tooltipDistance)
		};
	}

	/**
	 * Format Tooltip Data
	 * @param {object} d
	 */
	formatTooltipData(d) {
		const metriclabel = this.metriclabel;
		const header = [
			{
				columns: [
					{
						align: 'left',
						colspan: 2,
						label: d.properties.countryName
					}
				]
			}
		];

		const rows = [
			{
				columns: [
					{
						align: 'left',
						label: `${toThousands(
							d.properties.total
						)} ${metriclabel}`,
						width: 102
					},
					{
						align: 'right',
						label: `${d.properties.value}%`,
						width: 42
					}
				]
			}
		];

		return ReactDOMServer.renderToString(
			<ChartTooltip header={header} rows={rows} />
		);
	}

	/**
	 * Handle Selected
	 * @param {object} feature
	 */
	handleSelected(feature) {
		this._selected = feature;

		this.mapLayer &&
			this.mapLayer
				.selectAll('path')
				.style('fill', this.handleNodeColor.bind(this));
	}

	/**
	 * Sync Is Empty
	 * @param {boolean} isEmpty
	 */
	handleIsEmpty(isEmpty) {
		this.isEmpty = isEmpty;

		this.mapLayer &&
			this.mapLayer
				.selectAll('path')
				.style('fill', this.handleNodeColor.bind(this));
	}
}

/**
 * GeoMap Chart component.
 * @extends React.Component
 * @param {Object} props
 * @return {ReactElement}
 */
export default class GeomapReact extends React.Component {
	/** @inheritdoc */
	constructor(props) {
		super(props);

		this._containerRef = React.createRef();
	}

	/** @inheritdoc */
	componentDidMount() {
		const {data, ...otherProps} = this.props;

		this._geoMapInstance = new GeoMap({
			...otherProps,
			data,
			element: this._containerRef.current
		});

		this._geoMapInstance.attached();
	}

	/** @inheritdoc */
	componentDidUpdate(prevProps) {
		const {data, isEmpty, selected} = this.props;

		if (data !== prevProps.data) {
			this._geoMapInstance._updateData(data); // eslint-disable-line no-underscore-dangle
		}

		if (isEmpty !== prevProps.isEmpty) {
			this._geoMapInstance.handleIsEmpty(isEmpty);
		}

		if (selected !== prevProps.selected) {
			this._geoMapInstance.handleSelected(selected);
		}
	}

	/** @inheritdoc */
	componentWillUnmount() {
		this._geoMapInstance.disposed();

		if (this._geoMapInstance.tooltip) {
			this._geoMapInstance.tooltip.remove();
		}
	}

	/** @inheritdoc */
	render() {
		const {height = '232px', width = '350px'} = this._geoMapInstance
			? this._geoMapInstance.getSize()
			: {};

		return (
			<div
				className='geomap-container'
				ref={this._containerRef}
				style={{height, width}}
			/>
		);
	}
}
