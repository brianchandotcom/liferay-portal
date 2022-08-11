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

import classNames from 'classnames';
import React, {ReactNode} from 'react';

export enum Orientation {
	HORIZONTAL = 'HORIZONTAL',
	VERTICAL = 'VERTICAL',
}

type QAItem = {
	divider?: boolean;
	flexHeading?: boolean;
	title?: string;
	value: string | ReactNode;
};

type QATableProps = {
	items: QAItem[];
	orientation?: keyof typeof Orientation;
	title?: string;
};

const QATable: React.FC<QATableProps> = ({
	items,
	orientation = Orientation.HORIZONTAL,
	title,
}) => (
	<table className="qa w-100">
		{title && <div className="font-weight-bold mb-4 mt-3">{title}</div>}

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
						<td
							className={classNames('large-heading pb-3 w-50', {
								'd-flex': item.flexHeading,
							})}
						>
							{item.title}
						</td>

						<td className="pb-3 w-50">{item.value}</td>
					</tr>

					{item.divider && (
						<tr>
							<td>
								<hr />
							</td>

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
