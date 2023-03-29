import Button from 'shared/components/Button';
import Item from '../components/Item';
import React from 'react';
import Row from '../components/Row';

const Displays = [
	'danger',
	'dark',
	'default',
	'info',
	'light',
	'link',
	'primary',
	'profile',
	'secondary',
	'success',
	'unstyled',
	'warning'
];

const Sizes = ['sm', 'lg'];

class ButtonKit extends React.Component {
	render() {
		return (
			<div>
				<Row>
					<Button icon='times' iconAlignment='right'>
						{'Button with Icon'}
					</Button>
				</Row>

				<Row>
					{Displays.map((display, index) => (
						<Item key={index}>
							<Button display={display}>{display}</Button>
						</Item>
					))}
				</Row>
				<Row>
					{Displays.map((display, index) => (
						<Item key={index}>
							<Button display={display} outline>
								{display} {'outline'}
							</Button>
						</Item>
					))}
				</Row>
				<Row>
					{Sizes.map((size, index) => (
						<Item key={index}>
							<Button size={size}>{size}</Button>
						</Item>
					))}
				</Row>

				{Sizes.map((size, index) => (
					<Item key={index}>
						<Button block size={size}>
							{size} {'block'}
						</Button>
					</Item>
				))}

				<Row>
					<Button loading>{'loading'}</Button>
				</Row>
			</div>
		);
	}
}

export default ButtonKit;
