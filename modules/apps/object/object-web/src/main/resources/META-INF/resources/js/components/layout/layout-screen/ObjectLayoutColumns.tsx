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

import React from 'react';

import {TObjectLayoutColumn} from '../types';
import ObjectLayoutField from './ObjectLayoutField';

interface IObjectLayoutColumnsProps extends React.HTMLAttributes<HTMLElement> {
	boxIndex: number;
	objectLayoutColumns?: TObjectLayoutColumn[];
	rowIndex: number;
	tabIndex: number;
}

const ObjectLayoutColumns: React.FC<IObjectLayoutColumnsProps> = ({
	boxIndex,
	objectLayoutColumns,
	rowIndex,
	tabIndex,
}) => {
	return (
		<>
			{objectLayoutColumns?.map(({objectFieldId}, columnIndex) => {
				return (
					<div
						className="layout-tab__columns"
						key={`column_${columnIndex}`}
						style={{width: `${100 / objectLayoutColumns.length}%`}}
					>
						<ObjectLayoutField
							boxIndex={boxIndex}
							columnIndex={columnIndex}
							objectFieldId={objectFieldId}
							rowIndex={rowIndex}
							tabIndex={tabIndex}
						/>
					</div>
				);
			})}
		</>
	);
};

export default ObjectLayoutColumns;
