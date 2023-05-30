import AttributeFilterChip from './AttributeFilterChip';
import AttributeFilterDropdown from './attribute-filter-dropdown';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import React from 'react';
import {Align} from '@clayui/drop-down';
import {Attributes, Breakdowns, Filters} from 'event-analysis/utils/types';
import {
	DeleteFilter,
	MoveFilter,
	withAttributesConsumer
} from '../context/attributes';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

interface IAttributeFilterSectionProps {
	attributes: Attributes;
	breakdownOrder: string[];
	breakdowns: Breakdowns;
	deleteFilter: DeleteFilter;
	eventId: string;
	filterOrder: string[];
	filters: Filters;
	moveFilter: MoveFilter;
}

export const AttributeFilterSection: React.FC<IAttributeFilterSectionProps> = ({
	attributes,
	deleteFilter,
	eventId,
	filterOrder,
	filters,
	moveFilter
}) => {
	const uneditableIds = Object.keys(attributes);

	return (
		<div className='attribute-filter-section-root d-flex align-items-center'>
			<div className='section-header'>
				{Liferay.Language.get('filter')}
			</div>

			{!!eventId && (
				<div className='attribute-container d-flex align-items-center justify-content-between'>
					<DndProvider backend={HTML5Backend}>
						<div className='attribute-list d-flex align-items-center'>
							{filterOrder.map((id, i) => (
								<AttributeFilterChip
									attribute={
										attributes[filters[id].attributeId]
									}
									eventId={eventId}
									filter={filters[id]}
									index={i}
									key={id}
									onCloseClick={deleteFilter}
									onMove={moveFilter}
									uneditableIds={uneditableIds}
								/>
							))}
						</div>
					</DndProvider>

					<AttributeFilterDropdown
						alignmentPosition={Align.LeftTop}
						eventId={eventId}
						trigger={
							<ClayButton
								aria-label={Liferay.Language.get('add')}
								borderless
								className='button-root add-attribute'
								displayType='secondary'
								size='sm'
							>
								<ClayIcon className='icon-root' symbol='plus' />
							</ClayButton>
						}
						uneditableIds={uneditableIds}
					/>
				</div>
			)}
		</div>
	);
};

export default withAttributesConsumer(AttributeFilterSection);
