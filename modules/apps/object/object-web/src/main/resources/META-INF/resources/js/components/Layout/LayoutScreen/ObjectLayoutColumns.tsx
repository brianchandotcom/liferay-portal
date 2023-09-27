/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import React from 'react';

import {TObjectLayoutColumn} from '../types';
import {ObjectLayoutField} from './ObjectLayoutField';

interface ObjectLayoutColumnsProps extends React.HTMLAttributes<HTMLElement> {
	boxIndex: number;
	objectLayoutColumns?: TObjectLayoutColumn[];
	rowIndex: number;
	tabIndex: number;
}

export function ObjectLayoutColumns({
	boxIndex,
	objectLayoutColumns,
	rowIndex,
	tabIndex,
}: ObjectLayoutColumnsProps) {
	return (
		<>
			{objectLayoutColumns?.map(
				({objectFieldName, size}, columnIndex) => {
					return (
						<div
							className={classNames('layout-tab__columns', {
								[`col-md-${size}`]: size,
							})}
							key={`column_${columnIndex}`}
						>
							<ObjectLayoutField
								boxIndex={boxIndex}
								columnIndex={columnIndex}
								objectFieldName={objectFieldName}
								rowIndex={rowIndex}
								tabIndex={tabIndex}
							/>
						</div>
					);
				}
			)}
		</>
	);
}
