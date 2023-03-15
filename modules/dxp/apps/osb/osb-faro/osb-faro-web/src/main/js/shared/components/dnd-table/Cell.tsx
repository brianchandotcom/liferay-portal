import ClayTable from '@clayui/table';
import React from 'react';
import {get, isNil} from 'lodash';
import {IDataColumn} from 'shared/types';

interface ICellProps extends IDataColumn {
	data: any;
}

const Cell: React.FC<ICellProps> = ({
	accessor,
	cellRenderer: CellRenderer,
	cellRendererProps,
	className,
	data,
	dataFormatter = val => val
}) => {
	if (CellRenderer) {
		return (
			<CellRenderer
				{...cellRendererProps}
				className={className}
				data={data}
			/>
		);
	}

	const dataValue = get(data, accessor);

	return (
		<ClayTable.Cell>
			{!isNil(dataValue) ? dataFormatter(dataValue, data) : '-'}
		</ClayTable.Cell>
	);
};

export default Cell;
