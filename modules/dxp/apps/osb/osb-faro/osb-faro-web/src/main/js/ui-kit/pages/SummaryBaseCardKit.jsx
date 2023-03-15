import Item from '../components/Item';
import React from 'react';
import Row from '../components/Row';
import SummarBaseCard from 'experiments/components/summary-base-card';

export default () => (
	<Row>
		<Item>
			<SummarBaseCard>
				<SummarBaseCard.Header>
					{
						'Lorem ipsum dolor sit, amet consectetur adipisicing elit.  Corporis beatae labore minus temporibus molestiae dicta incidunt fugit nulla, omnis rerum odio qui eos dolorum aut asperiores. Voluptatibus, quisquam! Cum, qui.'
					}{' '}
				</SummarBaseCard.Header>
				<SummarBaseCard.Body>
					{
						'Lorem ipsum, dolor sit amet consectetur adipisicing elit.  Voluptatibus velit commodi, unde officiis atque aliquam ex suscipit veniam eveniet quaerat provident placeat minus saepe error cum numquam dolorum rem illo.'
					}{' '}
				</SummarBaseCard.Body>
				<SummarBaseCard.Footer>
					{
						'Lorem ipsum dolor sit amet consectetur adipisicing elit.  Libero reprehenderit nostrum iste veritatis ratione porro esse eligendi quidem laudantium impedit nesciunt necessitatibus aliquid, facere consequuntur ex earum dicta officia quasi!  '
					}
				</SummarBaseCard.Footer>
			</SummarBaseCard>
		</Item>
	</Row>
);
