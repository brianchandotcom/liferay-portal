import Item from '../components/Item';
import MetricBar, {Displays, Sizes} from 'shared/components/MetricBar';
import React from 'react';
import Row from '../components/Row';

export default class MetricBarKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				{Object.values(Sizes).map(size => (
					<Row key={size}>
						{Object.values(Displays).map(display => (
							<Item key={display}>
								<span
									style={{
										display: 'inline-block',
										width: '100px'
									}}
								>
									<MetricBar
										display={display}
										percent={0.3}
										size={size}
									/>
								</span>
							</Item>
						))}
					</Row>
				))}
			</div>
		);
	}
}
