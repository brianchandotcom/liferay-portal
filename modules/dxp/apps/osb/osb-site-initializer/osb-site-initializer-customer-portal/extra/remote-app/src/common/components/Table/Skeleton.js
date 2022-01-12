/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

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
