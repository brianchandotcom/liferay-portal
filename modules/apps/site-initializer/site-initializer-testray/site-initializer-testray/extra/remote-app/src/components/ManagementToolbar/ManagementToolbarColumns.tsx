/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {useContext, useState} from 'react';

import {ListViewContext, ListViewTypes} from '../../context/ListViewContext';
import i18n from '../../i18n';
import Form from '../Form';
import {Column} from '../Table';

type ManagementToolbarColumnsProps = {
	columns: Column[];
	onClose: () => void;
};

type ColumnsState = {
	[key: string]: boolean;
};

const ManagementToolbarColumns: React.FC<ManagementToolbarColumnsProps> = ({
	columns,
	onClose,
}) => {
	const [{columns: contextColumns, columnsFixed}, dispatch] = useContext(
		ListViewContext
	);

	const columnsNotFixed = columns.filter(
		({key}) => !columnsFixed.includes(key)
	);

	const [selectedColumns, setSelectedColumns] = useState<ColumnsState>(() => {
		const newColumns: ColumnsState = {};

		columnsNotFixed.forEach(({key}) => {
			newColumns[key] = contextColumns[key] ?? true;
		});

		return newColumns;
	});

	const disabled = Object.values(selectedColumns).every(
		(visible) => !visible
	);

	return (
		<div className="align-content-between d-flex flex-column">
			<div className="dropdown-header">
				<p className="font-weight-bold my-2">
					{i18n.translate('columns')}
				</p>
			</div>

			<div className="management-toolbar-body">
				<div className="popover-columns-content">
					{columnsNotFixed.map((column, index) => (
						<Form.Checkbox
							checked={selectedColumns[column.key]}
							key={index}
							label={column.value}
							onChange={(event) =>
								setSelectedColumns({
									...selectedColumns,
									[column.key]: event.target.checked,
								})
							}
							value={
								(selectedColumns[
									column.key
								] as unknown) as string
							}
						/>
					))}
				</div>
			</div>

			<div className="mt-5 popover-footer">
				<Form.Divider />

				<ClayButton
					disabled={disabled}
					onClick={() => {
						dispatch({
							payload: {
								columns: {
									...selectedColumns,
								},
							},
							type: ListViewTypes.SET_COLUMNS,
						});

						onClose();
					}}
				>
					{i18n.translate('apply')}
				</ClayButton>
			</div>
		</div>
	);
};

export default ManagementToolbarColumns;
