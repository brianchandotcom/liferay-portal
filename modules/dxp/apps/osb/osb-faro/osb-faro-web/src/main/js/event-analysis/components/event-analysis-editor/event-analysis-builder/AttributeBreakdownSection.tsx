import AttributeBreakdownChip from './AttributeBreakdownChip';
import AttributeBreakdownDropdown from './attribute-breakdown-dropdown';
import Button from 'shared/components/Button';
import HTML5Backend from 'react-dnd-html5-backend';
import React from 'react';
import {
	AddBreakdown,
	AddBreakdownParams,
	DeleteBreakdown,
	EditBreakdown,
	MoveBreakdown,
	withAttributesConsumer
} from '../context/attributes';
import {Align} from '@clayui/drop-down';
import {Attributes, Breakdowns, Filters} from 'event-analysis/utils/types';
import {DndProvider} from 'react-dnd';

const MAX_ATTRIBUTES = 3;

interface IAttributeBreakdownSectionProps {
	addBreakdown: AddBreakdown;
	attributes: Attributes;
	breakdownOrder: string[];
	breakdowns: Breakdowns;
	deleteBreakdown: DeleteBreakdown;
	editBreakdown: EditBreakdown;
	eventId: string;
	filters: Filters;
	moveBreakdown: MoveBreakdown;
}

export const AttributeBreakdownSection: React.FC<
	IAttributeBreakdownSectionProps
> = ({
	addBreakdown,
	attributes,
	breakdownOrder,
	breakdowns,
	deleteBreakdown,
	editBreakdown,
	eventId,
	moveBreakdown
}) => {
	const disabledIds = breakdownOrder.map(
		breakdownId => breakdowns[breakdownId].attributeId
	);

	const uneditableIds = Object.keys(attributes);

	const onAttributeSelect: AddBreakdown | EditBreakdown = (
		params: AddBreakdownParams
	) => {
		addBreakdown(params);

		const {
			attribute: {displayName, name, type},
			breakdown: {attributeType}
		} = params;

		analytics.track('Event Analysis Editor - Selected a Breakdown', {
			attributeName: displayName || name,
			attributeType,
			type
		});
	};

	return (
		<div className='attribute-breakdown-section-root d-flex align-items-center'>
			<div className='section-header'>
				{Liferay.Language.get('breakdown')}
			</div>

			{!!eventId && (
				<div className='attribute-container d-flex align-items-center justify-content-between'>
					<DndProvider backend={HTML5Backend}>
						<div className='attribute-list d-flex align-items-center'>
							{breakdownOrder.map((id, i) => (
								<AttributeBreakdownChip
									attribute={
										attributes[breakdowns[id].attributeId]
									}
									breakdown={breakdowns[id]}
									disabledIds={disabledIds}
									eventId={eventId}
									index={i}
									key={id}
									onCloseClick={deleteBreakdown}
									onEditSubmit={editBreakdown}
									onMove={moveBreakdown}
									uneditableIds={uneditableIds}
								/>
							))}
						</div>
					</DndProvider>

					{breakdownOrder.length < MAX_ATTRIBUTES && (
						<AttributeBreakdownDropdown
							alignmentPosition={Align.LeftTop}
							disabledIds={disabledIds}
							eventId={eventId}
							onAttributeSelect={onAttributeSelect}
							trigger={
								<Button
									borderless
									className='add-attribute'
									display='light'
									icon='plus'
									iconAlignment='left'
									size='sm'
								/>
							}
							uneditableIds={uneditableIds}
						/>
					)}
				</div>
			)}
		</div>
	);
};

export default withAttributesConsumer(AttributeBreakdownSection);
