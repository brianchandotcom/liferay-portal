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

import ClayForm, {ClayInput} from '@clayui/form';
import ClayTable from '@clayui/table';
import React, {useState} from 'react';

const CellPreview = ({cell, cellIndex, handleEditCell, rowIndex}) => {
	const [isCellEditing, setIsCellEditing] = useState(false);
	const [cellValue, setCellValue] = useState(cell);

	return (
		<ClayTable.Cell key={cellIndex}>
			{!isCellEditing && <>{cellValue}</>}

			{isCellEditing && (
				<ClayForm.Group>
					<ClayInput
						id={cell.replace(/ /, '-')}
						onBlur={(event) => {
							if (
								!event.currentTarget.contains(
									event.relatedTarget
								)
							) {
								setIsCellEditing(false);
								handleEditCell(cellValue, cellIndex, rowIndex);
							}
						}}
						onChange={(event) => {
							setCellValue(event.target.value);
						}}
						onFocus={(event) => {
							if (
								!event.currentTarget.contains(
									event.relatedTarget
								)
							) {
								setIsCellEditing(true);
							}
						}}
						type="text"
						value={cellValue}
					/>
				</ClayForm.Group>
			)}
		</ClayTable.Cell>
	);
};
export default CellPreview;
