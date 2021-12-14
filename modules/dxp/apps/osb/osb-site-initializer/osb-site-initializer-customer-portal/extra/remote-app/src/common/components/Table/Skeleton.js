import ClayTable from '@clayui/table';
import Skeleton from '../Skeleton';

const TableSkeleton = ({itemsPerPage = 5, totalColumns}) => {
	return (
		<ClayTable.Body>
			{[...new Array(itemsPerPage)].map((_, rowIndex) => (
				<ClayTable.Row key={rowIndex}>
					{[...new Array(totalColumns)].map((_, cellIndex) => (
						<ClayTable.Cell
							align="center"
							expanded
							key={`table-${rowIndex}-${cellIndex}`}
						>
							<Skeleton className="w-100" height={24} />
						</ClayTable.Cell>
					))}
				</ClayTable.Row>
			))}
		</ClayTable.Body>
	);
};

export default TableSkeleton;
