import AttributeChip, {DragTypes} from './AttributeChip';
import AttributeFilterDropdown from './attribute-filter-dropdown';
import React from 'react';
import {Attribute, Filter} from 'event-analysis/utils/types';
import {DeleteFilter} from '../context/attributes';
import {getFilterDisplay} from 'event-analysis/utils/utils';

const AttributeFilterChip: React.FC<{
	attribute: Attribute;
	eventId: string;
	filter: Filter;
	index: number;
	onCloseClick: DeleteFilter;
	onMove: (params: {from: number; to: number}) => void;
	uneditableIds: string[];
}> = ({
	attribute,
	eventId,
	filter,
	index,
	onCloseClick,
	onMove,
	uneditableIds
}) => {
	const [label, value] = getFilterDisplay(attribute, filter);

	const {dataType, description, displayName} = filter;

	const modifiedAttribute = {
		...attribute,
		dataType,
		description,
		displayName
	};

	return (
		<AttributeFilterDropdown
			attribute={modifiedAttribute}
			eventId={eventId}
			filter={filter}
			trigger={
				<AttributeChip
					dataType={dataType}
					description={description}
					displayName={displayName}
					draggable={false}
					dragType={DragTypes.AttributeFilterChip}
					id={filter.id}
					index={index}
					label={label}
					onCloseClick={onCloseClick}
					onMove={onMove}
					value={value}
				/>
			}
			uneditableIds={uneditableIds}
		/>
	);
};

export default AttributeFilterChip;
