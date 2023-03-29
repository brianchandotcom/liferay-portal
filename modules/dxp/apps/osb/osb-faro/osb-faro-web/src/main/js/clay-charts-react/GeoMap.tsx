import React from 'react';
import {geomap} from 'clay-charts-shared';

export interface IGeomapReactProps {
	color: string;
	data: any;
	grid: boolean;
	line: boolean;
	point: boolean;
	element: boolean;
	pollingInterval: number;
}

/**
 * GeoMap Chart component.
 * @extends React.Component
 * @param {Object} props
 * @return {ReactElement}
 */
export default class GeomapReact extends React.Component<IGeomapReactProps> {
	_geoMapInstance: any;
	_containerRef: React.RefObject<HTMLDivElement>;

	/** @inheritdoc */
	constructor(props) {
		super(props);

		this._containerRef = React.createRef();
	}

	/** @inheritdoc */
	componentDidMount() {
		const {data, ...otherProps} = this.props;

		this._geoMapInstance = new geomap.Geomap({
			...otherProps,
			data: data.data,
			element: this._containerRef.current
		});

		this._geoMapInstance.attached();
	}

	/** @inheritdoc */
	componentWillUnmount() {
		this._geoMapInstance.disposed();
	}

	/** @inheritdoc */
	render() {
		/* eslint-disable */
		const {
			color,
			data,
			element,
			grid,
			line,
			point,
			pollingInterval,
			...otherProps
		} = this.props;
		/* eslint-enable */

		const {height = '100%', width = '100%'} = this._geoMapInstance
			? this._geoMapInstance.getSize()
			: {};

		return (
			<div
				style={{height, width}}
				{...otherProps}
				ref={this._containerRef}
			/>
		);
	}
}
