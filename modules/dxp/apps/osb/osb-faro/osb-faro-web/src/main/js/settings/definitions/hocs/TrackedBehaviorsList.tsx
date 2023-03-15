import Card from 'shared/components/Card';
import React from 'react';
import Table from 'shared/components/table';
import {definitionsListColumns} from 'shared/util/table-columns';
import {NameCell} from 'shared/components/table/cell-components';

interface ITrackedBehaviorsListProps {
	authorized?: boolean;
}

interface ITableWithDataProps extends ITrackedBehaviorsListProps {}

// TODO: LRAC-4284 Remove mock data once backend is implemented
const mockBehaviors = [
	{name: 'Page Views', restricted: true, type: 'Page Event'},
	{name: 'Blog Views', restricted: false, type: 'Blog Event'},
	{name: 'Blog Clicks', restricted: false, type: 'Blog Event'},
	{
		name: 'Document & Media Downloads',
		restricted: false,
		type: 'Document & Media Event'
	}
];

const TableWithData: React.FC<ITableWithDataProps> = ({authorized}) => (
	<Table
		columns={[
			{
				accessor: 'name',
				cellRenderer: NameCell,
				label: Liferay.Language.get('attribute-name'),
				sortable: false
			},
			{
				accessor: 'type',
				className: 'table-cell-expand',
				label: Liferay.Language.get('event-type'),
				sortable: false
			},
			definitionsListColumns.restrictAccess(authorized)
		]}
		items={mockBehaviors}
		rowIdentifier='name'
	/>
);

export const TrackedBehaviorsList: React.FC<ITrackedBehaviorsListProps> = ({
	authorized
}) => (
	<Card className='tracked-behaviors-list-root'>
		<TableWithData authorized={authorized} />
	</Card>
);

TrackedBehaviorsList.defaultProps = {
	authorized: false
};

export default TrackedBehaviorsList;
