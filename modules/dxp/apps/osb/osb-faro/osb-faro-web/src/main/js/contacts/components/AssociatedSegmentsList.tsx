import Card from 'shared/components/Card';
import getCN from 'classnames';
import React from 'react';
import SearchableEntityTable from 'shared/components/SearchableEntityTable';
import {DATE_CREATED, NAME} from 'shared/util/pagination';
import {getPluralMessage} from 'shared/util/lang';
import {OrderedMap} from 'immutable';
import {OrderParams} from 'shared/util/records';
import {segmentsListColumns} from 'shared/util/table-columns';

interface IAssociatedSegmentsListProps {
	channelId: string;
	className?: string;
	dataSourceFn: (params: {[key: string]: any}) => void;
	delta: number;
	groupId: string;
	id: string;
	noResultsRenderer: () => React.ReactElement;
	orderIOMap: OrderedMap<string, OrderParams>;
	page: number;
	query: string;
	timeZoneId: string;
	total: number;
}

const AssociatedSegmentsList: React.FC<IAssociatedSegmentsListProps> = ({
	channelId,
	className,
	dataSourceFn,
	delta,
	groupId,
	id,
	noResultsRenderer,
	orderIOMap,
	page,
	query,
	timeZoneId,
	total
}) => (
	<Card
		className={getCN('associated-segments-list-root', className)}
		pageDisplay
	>
		<Card.Header>
			<Card.Title>
				{Liferay.Language.get('associated-segments')}
			</Card.Title>

			<div className='secondary-info'>
				{getPluralMessage(
					Liferay.Language.get('x-segment'),
					Liferay.Language.get('x-segments'),
					total,
					false,
					[<b key='SEGMENT_TOTAL'>{total.toLocaleString()}</b>]
				)}
			</div>
		</Card.Header>

		<SearchableEntityTable
			columns={[
				segmentsListColumns.getName({channelId, groupId}),
				segmentsListColumns.individualAddedDate,
				segmentsListColumns.getDateCreated(timeZoneId)
			]}
			dataSourceFn={dataSourceFn}
			dataSourceParams={{channelId, groupId, id}}
			delta={delta}
			entityLabel={Liferay.Language.get('associated-segments')}
			noResultsRenderer={noResultsRenderer}
			orderByOptions={[
				{
					label: Liferay.Language.get('name'),
					value: NAME
				},
				{
					label: Liferay.Language.get('date-created'),
					value: DATE_CREATED
				}
			]}
			orderIOMap={orderIOMap}
			page={page}
			query={query}
			rowIdentifier='id'
		/>
	</Card>
);

export default AssociatedSegmentsList;
