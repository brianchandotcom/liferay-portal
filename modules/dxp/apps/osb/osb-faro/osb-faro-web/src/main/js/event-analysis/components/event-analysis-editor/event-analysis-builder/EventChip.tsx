import Chip from 'shared/components/Chip';
import ClayButton from '@clayui/button';
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
		<ClayButton
			className='button-root event-name'
			displayType='unstyled'
			onClick={onClick}
		>
			{displayName || name}
		</ClayButton>
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
