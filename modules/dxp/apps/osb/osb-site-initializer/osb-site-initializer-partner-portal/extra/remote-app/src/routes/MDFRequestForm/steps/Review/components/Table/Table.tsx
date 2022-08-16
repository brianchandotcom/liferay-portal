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

interface ITable {
	title?: string;
	value?: string;
}

interface IProps {
	items: ITable[];
	title?: string;
}

const Table = ({items, title}: IProps) => (
	<ClayTable className="border-0">
		<ClayTable.Head>
			<ClayTable.Row>
				<ClayTable.Cell
					className="border-neutral-2 border-top"
					expanded
					headingCell
				>
					{title}
				</ClayTable.Cell>

				<ClayTable.Cell className="border-neutral-2 border-top"></ClayTable.Cell>
			</ClayTable.Row>
		</ClayTable.Head>

		<ClayTable.Body>
			{items?.map((value: ITable, index: number) => (
				<ClayTable.Row key={index}>
					<ClayTable.Cell className="border-0 w-50">
						{value.title}
					</ClayTable.Cell>

					<ClayTable.Cell className="border-0 w-50">
						{value.value}
					</ClayTable.Cell>
				</ClayTable.Row>
			))}
		</ClayTable.Body>
	</ClayTable>
);

export default Table;
