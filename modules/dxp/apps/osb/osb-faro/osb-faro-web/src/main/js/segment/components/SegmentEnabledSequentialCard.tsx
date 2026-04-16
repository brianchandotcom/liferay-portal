import Card from 'shared/components/Card';
import Form from '../../shared/components/form';
import React from 'react';
import {Text} from '@clayui/core';

interface ISegmentEnabledSequentialCardProps {
	sequential: boolean;
}

const SegmentEnabledSequentialCard: React.FC<ISegmentEnabledSequentialCardProps> = () => (
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
					'when-this-is-enabled,-event-2-must-occur-after-event-1,-with-any-number-of-events-in-between'
				)}
			</Text>
		</Card.Body>
	</Card>
);

export {SegmentEnabledSequentialCard};
