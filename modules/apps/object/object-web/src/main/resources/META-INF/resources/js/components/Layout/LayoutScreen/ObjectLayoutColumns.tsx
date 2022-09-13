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
