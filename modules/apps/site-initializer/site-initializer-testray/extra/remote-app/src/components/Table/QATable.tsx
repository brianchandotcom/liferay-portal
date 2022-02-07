/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {ReactNode} from 'react';

type QAItem = {
	title: string;
	value: string | ReactNode;
};

type QATableProps = {
	items: QAItem[];
};

const QATable: React.FC<QATableProps> = ({items}) => (
	<table className="qa">
		<tbody>
			{items.map((item, index) => (
				<tr key={index}>
					<th>{item.title}</th>

					<td>{item.value}</td>
				</tr>
			))}
		</tbody>
	</table>
);

export default QATable;
