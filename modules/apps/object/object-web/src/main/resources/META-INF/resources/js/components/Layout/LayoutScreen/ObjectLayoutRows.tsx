/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {TObjectLayoutRow} from '../types';
import {ObjectLayoutColumns} from './ObjectLayoutColumns';

interface ObjectLayoutRowsProps extends React.HTMLAttributes<HTMLElement> {
	boxIndex: number;
	objectLayoutRows: TObjectLayoutRow[];
	tabIndex: number;
}

export function ObjectLayoutRows({
	boxIndex,
	objectLayoutRows,
	tabIndex,
}: ObjectLayoutRowsProps) {
	return (
		<>
			{objectLayoutRows?.map(({objectLayoutColumns}, rowIndex) => {
				return (
					<div
						className="layout-tab__rows row"
						key={`row_${rowIndex}`}
					>
						<ObjectLayoutColumns
							boxIndex={boxIndex}
							objectLayoutColumns={objectLayoutColumns}
							rowIndex={rowIndex}
							tabIndex={tabIndex}
						/>
					</div>
				);
			})}
		</>
	);
}
