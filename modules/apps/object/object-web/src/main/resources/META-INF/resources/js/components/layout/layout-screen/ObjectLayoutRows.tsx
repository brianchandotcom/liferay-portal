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

import {TObjectLayoutRow} from '../types';
import ObjectLayoutColumns from './ObjectLayoutColumns';

interface IObjectLayoutRowsProps extends React.HTMLAttributes<HTMLElement> {
	boxIndex: number;
	objectLayoutRows?: TObjectLayoutRow[];
	tabIndex: number;
}

const ObjectLayoutRows: React.FC<IObjectLayoutRowsProps> = ({
	boxIndex,
	objectLayoutRows,
	tabIndex,
}) => {
	return (
		<>
			{objectLayoutRows?.map(({objectLayoutColumns}, rowIndex) => {
				return (
					<div className="layout-tab__rows" key={`row_${rowIndex}`}>
						{!!objectLayoutColumns?.length && (
							<ObjectLayoutColumns
								boxIndex={boxIndex}
								objectLayoutColumns={objectLayoutColumns}
								rowIndex={rowIndex}
								tabIndex={tabIndex}
							/>
						)}
					</div>
				);
			})}
		</>
	);
};

export default ObjectLayoutRows;
