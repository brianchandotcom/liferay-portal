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

import classNames from 'classnames';
import React, {ReactNode} from 'react';

export enum Orientation {
	HORIZONTAL = 'HORIZONTAL',
	VERTICAL = 'VERTICAL',
}

type QAItem = {
	divider?: boolean;
	title: string;
	value: string | ReactNode;
};

type QATableProps = {
	items: QAItem[];
	orientation?: keyof typeof Orientation;
};

const QATable: React.FC<QATableProps> = ({
	items,
	orientation = Orientation.HORIZONTAL,
}) => (
	<table className="qa w-100">
		<tbody>
			{items.map((item, index) => (
				<React.Fragment key={index}>
					<tr
						className={classNames({
							'd-flex flex-column':
								orientation === Orientation.VERTICAL,
						})}
						key={index}
					>
						<th className="small-heading">{item.title}</th>

						<td>{item.value}</td>
					</tr>

					{item.divider && (
						<tr>
							<td>
								<hr />
							</td>
						</tr>
					)}
				</React.Fragment>
			))}
		</tbody>
	</table>
);

export default QATable;
