import ClayTable from '@clayui/table';
import {memo} from 'react';
import Skeleton from '../Skeleton';

const TableSkeleton = ({itemsPerPage = 5, totalColumns}) => {
	return (
		<ClayTable.Body>
			{[...new Array(itemsPerPage)].map((_, dataIndex) => (
				<ClayTable.Row key={dataIndex}>
					{[...new Array(totalColumns)].map((_, columnIndex) => (
						<ClayTable.Cell
							align="center"
							expanded
							key={`table-${dataIndex}-${columnIndex}`}
						>
							<Skeleton className="w-100" height={24} />
						</ClayTable.Cell>
					))}
				</ClayTable.Row>
			))}
		</ClayTable.Body>
	);
};

export default memo(TableSkeleton);
