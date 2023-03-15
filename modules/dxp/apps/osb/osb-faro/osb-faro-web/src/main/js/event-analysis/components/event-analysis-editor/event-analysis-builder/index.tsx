import AttributeBreakdownSection from './AttributeBreakdownSection';
import AttributeFilterSection from './AttributeFilterSection';
import EventSection from './EventSection';
import React from 'react';
import {Event} from 'event-analysis/utils/types';

interface IEventAnalysisBuilderProps {
	event?: Event;
	onEventChange: (event: Event) => void;
}

const EventAnalysisBuilder: React.FC<IEventAnalysisBuilderProps> = ({
	event,
	onEventChange
}) => (
	<div className='event-analysis-builder-root d-flex flex-column'>
		<EventSection event={event} onEventChange={onEventChange} />

		<AttributeBreakdownSection eventId={event?.id} />

		<AttributeFilterSection eventId={event?.id} />
	</div>
);

export default EventAnalysisBuilder;
