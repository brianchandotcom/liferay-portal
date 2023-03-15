import Button from 'shared/components/Button';
import Chip from 'shared/components/Chip';
import EventDropdown from './EventDropdown';
import React from 'react';
import {Event} from 'event-analysis/utils/types';

interface IEventChipProps {
	event: Event;
	onEventChange: (event?: Event) => void;
}

const EventChip: React.FC<IEventChipProps> = React.forwardRef<
	HTMLDivElement,
	IEventChipProps & {onClick?: () => void}
>(({event: {displayName, name}, onClick, onEventChange}, ref) => (
	<Chip
		className='event-chip-root'
		onCloseClick={() => onEventChange()}
		ref={ref}
	>
		<Button className='event-name' display='unstyled' onClick={onClick}>
			{displayName || name}
		</Button>
	</Chip>
));

const EventChipWrapper: React.FC<IEventChipProps> = ({
	event,
	onEventChange
}) => (
	<EventDropdown
		eventId={event.id}
		onEventChange={onEventChange}
		trigger={<EventChip event={event} onEventChange={onEventChange} />}
	/>
);

export default EventChipWrapper;
