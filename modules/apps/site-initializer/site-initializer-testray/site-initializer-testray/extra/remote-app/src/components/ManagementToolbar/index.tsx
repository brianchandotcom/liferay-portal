/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayManagementToolbar from '@clayui/management-toolbar';
import {ReactNode, useContext} from 'react';

import {ListViewContext} from '../../context/ListViewContext';
import {
	FilterSchemaOption,
	filterSchema as filterSchemas,
} from '../../schema/filter';
import {TableProps} from '../Table';
import ManagementToolbarLeft from './ManagementToolbarLeft';
import ManagementToolbarResultsBar from './ManagementToolbarResultsBar';
import ManagementToolbarRight from './ManagementToolbarRight';

export type ManagementToolbarProps = {
	actions: any;
	addButton?: () => void;
	buttons?: ReactNode | ((actions: any) => ReactNode);
	display?: {
		columns?: boolean;
	};

	/**
	 * Check out the file {src/schema/filter.ts}
	 */
	filterSchema?: FilterSchemaOption;
	tableProps: Pick<TableProps, 'columns'>;
	title?: string;
	totalItems: number;
};

const ManagementToolbar: React.FC<ManagementToolbarProps> = ({
	actions,
	addButton,
	buttons,
	display,
	filterSchema,
	tableProps,
	title,
	totalItems,
}) => {
	const [{filters}] = useContext(ListViewContext);

	const disabled = totalItems === 0;

	return (
		<>
			<ClayManagementToolbar>
				<ManagementToolbarLeft title={title} />

				<ManagementToolbarRight
					actions={actions}
					addButton={addButton}
					buttons={
						typeof buttons === 'function'
							? buttons(actions)
							: buttons
					}
					columns={tableProps.columns}
					disabled={disabled}
					display={display}
					filterSchema={(filterSchemas as any)[filterSchema ?? '']}
				/>
			</ClayManagementToolbar>

			{!!filters.entries?.filter(({value}) => value).length && (
				<ManagementToolbarResultsBar totalItems={totalItems} />
			)}
		</>
	);
};

export default ManagementToolbar;
