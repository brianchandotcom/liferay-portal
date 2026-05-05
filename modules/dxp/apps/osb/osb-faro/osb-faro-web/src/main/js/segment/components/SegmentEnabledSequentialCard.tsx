import Card from 'shared/components/Card';
import Form from 'shared/components/form';
import React from 'react';
import {Text} from '@clayui/core';

const SegmentEnabledSequentialCard = () => (
	<Card>
		<Card.Header>
			<Card.Title>{Liferay.Language.get('order')}</Card.Title>
		</Card.Header>

		<Card.Body>
			<p>
				<Form.ToggleSwitch
					className='sequential'
					label={Liferay.Language.get('enable-sequential')}
					name='sequential'
				/>
			</p>

			<Text color='secondary' size={3}>
				{Liferay.Language.get(
					'when-sequential-mode-is-enabled,-event-1-must-be-completed-before-event-2,-although-other-events-may-occur-in-between'
				)}
			</Text>
		</Card.Body>
	</Card>
);

export {SegmentEnabledSequentialCard};
