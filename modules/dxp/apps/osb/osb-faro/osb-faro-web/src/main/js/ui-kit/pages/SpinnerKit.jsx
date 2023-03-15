import Item from '../components/Item';
import React from 'react';
import Row from '../components/Row';
import Spinner from 'shared/components/Spinner';

export default () => (
	<div>
		{Spinner.DISPLAYS.map((display, i) => (
			<Row key={i}>
				{Spinner.SIZES.map((size, j) => (
					<Item key={j}>
						<Spinner display={display} size={size} />
					</Item>
				))}
			</Row>
		))}
	</div>
);
