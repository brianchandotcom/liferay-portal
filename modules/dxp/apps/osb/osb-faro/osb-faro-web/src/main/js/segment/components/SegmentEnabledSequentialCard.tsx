import Card from 'shared/components/Card';
import React from 'react';
import {ClayToggle} from '@clayui/form';
import {Text} from '@clayui/core';

interface ISegmentEnabledSequentialCardProps {
	onToggle: (enabled: boolean) => void;
	toggled: boolean;
}

const SegmentEnabledSequentialCard: React.FC<ISegmentEnabledSequentialCardProps> = ({
	onToggle,
	toggled
}) => (
	<Card>
		<Card.Header>
			<Card.Title>{Liferay.Language.get('order')}</Card.Title>
		</Card.Header>

		<Card.Body>
			<p>
				<ClayToggle
					data-testid='segment-enabled-sequential-toggle'
					label={Liferay.Language.get('enabled-sequential')}
					onToggle={onToggle}
					toggled={toggled}
				/>
			</p>

			<Text color='secondary' size={3}>
				{Liferay.Language.get(
					'when-this-is-enabled,-event-2-must-occur-after-event-1,-with-any-number-of-events-in-between.-When-this-is-disabled,-events-can-be-completed-in-any-order.-nested-criteria-are-not-supported'
				)}
			</Text>
		</Card.Body>
	</Card>
);

export {SegmentEnabledSequentialCard};
