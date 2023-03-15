import BaseDropdown from './base-dropdown';
import Constants from 'shared/util/constants';
import EVENT_DEFINITION_QUERY, {
	UPDATE_EVENT_DEFINITION
} from 'event-analysis/queries/EventDefinitionQuery';
import EVENT_DEFINITIONS_QUERY, {
	EventDefinitionsData,
	EventDefinitionsVariables
} from 'event-analysis/queries/EventDefinitionsQuery';
import React, {useState} from 'react';
import {Align} from '@clayui/drop-down';
import {close, modalTypes, open} from 'shared/actions/modals';
import {connect} from 'react-redux';
import {DISPLAY_NAME} from 'shared/util/pagination';
import {Event, EventTypes} from 'event-analysis/utils/types';
import {Modal} from 'shared/types';
import {SafeResults} from 'shared/hoc/util';
import {useQuery} from '@apollo/react-hooks';

const {
	pagination: {orderDefault}
} = Constants;

interface IAnalysisDropdownProps {
	alignmentPosition?: typeof Align[keyof typeof Align];
	close: Modal.close;
	eventId?: string;
	onEventChange: (event: Event) => void;
	open: Modal.open;
	trigger: React.ReactElement;
}

const AnalysisDropdown: React.FC<IAnalysisDropdownProps> = ({
	alignmentPosition = Align.RightTop,
	close,
	eventId,
	onEventChange,
	open,
	trigger
}) => {
	const [query, setQuery] = useState('');
	const [eventType, setEventType] = useState<EventTypes>(EventTypes.All);

	const result = useQuery<EventDefinitionsData | EventDefinitionsVariables>(
		EVENT_DEFINITIONS_QUERY,
		{
			fetchPolicy: 'network-only',
			variables: {
				eventType,
				hidden: false,
				keyword: '',
				page: 0,
				size: 200,
				sort: {
					column: DISPLAY_NAME,
					type: orderDefault.toUpperCase()
				}
			}
		}
	);

	return (
		<BaseDropdown alignmentPosition={alignmentPosition} trigger={trigger}>
			{({setActive}) => (
				<>
					<BaseDropdown.Header
						activeTabId={eventType}
						tabs={[
							{
								onClick: () => setEventType(EventTypes.All),
								tabId: EventTypes.All,
								title: Liferay.Language.get('all')
							},
							{
								onClick: () => setEventType(EventTypes.Default),
								tabId: EventTypes.Default,
								title: Liferay.Language.get('default')
							},
							{
								onClick: () => setEventType(EventTypes.Custom),
								tabId: EventTypes.Custom,
								title: Liferay.Language.get('custom')
							}
						]}
						title={Liferay.Language.get('events')}
					/>

					<SafeResults {...result} page={false} pageDisplay={false}>
						{({
							eventDefinitions: {eventDefinitions}
						}: {
							eventDefinitions: {eventDefinitions: Event[]};
						}) => (
							<BaseDropdown.SearchableList
								activeId={eventId}
								items={eventDefinitions}
								onEditClick={(event: Event) => {
									open(
										modalTypes.EDIT_ATTRIBUTE_EVENT_MODAL,
										{
											id: event.id,
											mutation: UPDATE_EVENT_DEFINITION,
											onClose: (save: boolean) => {
												if (save) {
													result.refetch();
												}

												close();
											},
											query: EVENT_DEFINITION_QUERY
										}
									);

									setActive(false);
								}}
								onItemClick={(event: Event) => {
									if (event.id !== eventId) {
										onEventChange(event);

										setActive(false);
										setEventType(EventTypes.All);
										setQuery('');
									}
								}}
								onQueryChange={setQuery}
								query={query}
							/>
						)}
					</SafeResults>
				</>
			)}
		</BaseDropdown>
	);
};

export default connect(null, {close, open})(AnalysisDropdown);
