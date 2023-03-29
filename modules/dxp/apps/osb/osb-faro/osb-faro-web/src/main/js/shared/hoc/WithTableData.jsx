import React from 'react';
import Table from 'shared/components/table';
import {compose, withError} from 'shared/hoc';
import {withEmpty} from 'cerebro-shared/hocs/utils';

const withTableData = (
	withData,
	{emptyDescription, emptyIcon, emptyTitle, getColumns, rowIdentifier}
) => {
	const TableWithData = compose(
		withData(),
		WrappedComponent => props => (
			<WrappedComponent {...props} columns={getColumns(props)} />
		),
		withError({page: false}),
		withEmpty({emptyDescription, emptyIcon, emptyTitle, spacer: true})
	)(Table);

	class TableData extends React.Component {
		render() {
			return (
				<TableWithData {...this.props} rowIdentifier={rowIdentifier} />
			);
		}
	}

	return TableData;
};

export default withTableData;
