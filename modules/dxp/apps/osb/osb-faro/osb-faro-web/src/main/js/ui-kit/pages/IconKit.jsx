import Icon from 'shared/components/Icon';
import Item from '../components/Item';
import React from 'react';
import Row from '../components/Row';

const SIZES = ['sm', 'md', 'lg', 'xl', 'xxl', 'xxxl'];

const IconKit = () => (
	<div>
		<Row>
			{SIZES.map((size, index) => (
				<Item key={index}>
					<Icon size={size} symbol='ac-star' />
				</Item>
			))}
		</Row>

		<Row>
			{SIZES.map((size, index) => (
				<Item key={index}>
					<Icon monospaced size={size} symbol='ac-star' />
				</Item>
			))}
		</Row>
	</div>
);

export default IconKit;
