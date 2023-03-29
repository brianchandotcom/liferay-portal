import Item from '../components/Item';
import React from 'react';
import Row from '../components/Row';

const COLORS = [
	'main',
	'mainLighten4',
	'mainLighten8',
	'mainLighten28',
	'mainLighten52',
	'mainLighten65',
	'mainLighten74',
	'primaryLighten23',
	'primaryLighten33',
	'primaryLighten45',
	'primary',
	'primaryDarken5',
	'primaryDarken10',
	'white',
	'lightBackground',
	'error',
	'errorLighten28',
	'errorLighten50',
	'warning',
	'warningLighten25',
	'warningLighten60',
	'info',
	'infoLighten28',
	'infoLighten53',
	'limegreen',
	'seagreen'
];

class ColorKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<Row>
					{COLORS.map((color, index) => (
						<Item key={index}>
							<div className={`color-swatch ${color}`}>
								<div className='color-display' />
								<p className='color-label'>{color}</p>
							</div>
						</Item>
					))}
				</Row>
			</div>
		);
	}
}

export default ColorKit;
