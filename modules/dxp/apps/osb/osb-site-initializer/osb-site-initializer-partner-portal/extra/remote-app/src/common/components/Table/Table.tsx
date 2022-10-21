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

import TableItem from '../../../routes/MDFRequestForm/steps/Review/interfaces/tableItem';

interface IProps {
	items: TableItem[];
	title: string;
}

const Table = ({items, title}: IProps) => (
	<ClayTable className="bg-brand-primary-lighten-6 border-0 table-striped">
		<ClayTable.Head>
			<ClayTable.Row>
				<ClayTable.Cell
					className="border-neutral-2 border-top rounded-0 w-50"
					expanded
					headingCell
				>
					<p className="mt-4 text-neutral-10">{title}</p>
				</ClayTable.Cell>

				<ClayTable.Cell className="border-neutral-2 border-top rounded-0 w-50"></ClayTable.Cell>
			</ClayTable.Row>
		</ClayTable.Head>

		<ClayTable.Body>
			{items?.map((item: TableItem, index: number) => (
				<ClayTable.Row key={index}>
					<ClayTable.Cell className="border-0 w-50">
						<p className="text-neutral-10">{item.title}</p>
					</ClayTable.Cell>

					<ClayTable.Cell className="border-0 w-50">
						<p className="text-neutral-10">{item.value}</p>
					</ClayTable.Cell>
				</ClayTable.Row>
			))}
		</ClayTable.Body>
	</ClayTable>
);

export default Table;
