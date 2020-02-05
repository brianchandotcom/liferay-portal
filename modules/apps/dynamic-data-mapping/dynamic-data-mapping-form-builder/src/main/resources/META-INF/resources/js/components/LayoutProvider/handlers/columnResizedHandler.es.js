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

import {FormSupport} from 'dynamic-data-mapping-form-renderer';

export const handleResizeRight = (
	state,
	{columnIndex, pageIndex, rowIndex},
	targetColumn
) => {
	const {pages} = state;

	let newPages = [...pages];

	const currentColumn = FormSupport.getColumn(
		pages,
		pageIndex,
		rowIndex,
		columnIndex
	);
	const currentPosition = FormSupport.getColumnPosition(
		pages,
		pageIndex,
		rowIndex,
		columnIndex
	);
	const nextColumn = FormSupport.getColumn(
		newPages,
		pageIndex,
		rowIndex,
		columnIndex + 1
	);

	let newCurrentColumn;

	if (nextColumn) {
		const newSize = Math.abs(currentPosition - targetColumn);
		let newNextColumn;

		if (targetColumn < currentPosition && currentColumn.size > newSize) {
			newCurrentColumn = {
				...currentColumn,
				size: Math.max(currentColumn.size - newSize, 1)
			};

			newNextColumn = {
				...nextColumn,
				size: Math.min(nextColumn.size + newSize, 12)
			};
		}
		else if (targetColumn > currentPosition) {
			if (nextColumn.size === 1 && nextColumn.fields.length === 0) {
				newCurrentColumn = {
					...currentColumn,
					size: currentColumn.size + newSize
				};

				newPages = FormSupport.removeColumn(
					newPages,
					pageIndex,
					rowIndex,
					columnIndex + 1
				);
			}
			else if (nextColumn.size > newSize) {
				newCurrentColumn = {
					...currentColumn,
					size: currentColumn.size + newSize
				};

				newNextColumn = {
					...nextColumn,
					size: nextColumn.size - newSize
				};
			}
		}

		if (newNextColumn) {
			newPages = FormSupport.updateColumn(
				newPages,
				pageIndex,
				rowIndex,
				columnIndex + 1,
				newNextColumn
			);
		}
	}
	else if (
		currentColumn.size > currentPosition - targetColumn &&
		targetColumn < currentPosition
	) {
		const newSize = currentPosition - targetColumn;

		newCurrentColumn = {
			...currentColumn,
			size: currentColumn.size - newSize
		};

		newPages = FormSupport.addColumn(
			newPages,
			columnIndex + 1,
			pageIndex,
			rowIndex,
			{
				fields: [],
				size: newSize
			}
		);
	}

	if (newCurrentColumn) {
		newPages = FormSupport.updateColumn(
			newPages,
			pageIndex,
			rowIndex,
			columnIndex,
			newCurrentColumn
		);
	}

	return newPages;
};

export const handleResizeLeft = (state, source, targetColumn) => {
	const {columnIndex, pageIndex, rowIndex} = FormSupport.getIndexes(source);
	const {pages} = state;

	let newPages = [...pages];

	const currentColumn = FormSupport.getColumn(
		pages,
		pageIndex,
		rowIndex,
		columnIndex
	);
	const previousColumn = FormSupport.getColumn(
		pages,
		pageIndex,
		rowIndex,
		columnIndex - 1
	);
	const previousColumnPosition = FormSupport.getColumnPosition(
		pages,
		pageIndex,
		rowIndex,
		columnIndex - 1
	);

	if (
		previousColumn &&
		previousColumn.fields.length == 0 &&
		previousColumn.size === 1 &&
		targetColumn <= previousColumnPosition
	) {
		newPages = FormSupport.removeColumn(
			newPages,
			pageIndex,
			rowIndex,
			columnIndex - 1
		);

		source.dataset.ddmFieldColumn = columnIndex - 1;

		newPages = FormSupport.updateColumn(
			newPages,
			pageIndex,
			rowIndex,
			columnIndex - 1,
			{
				...currentColumn,
				size: currentColumn.size + 1
			}
		);
	}
	else if (previousColumn) {
		const newSize = Math.abs(previousColumnPosition - targetColumn);

		if (
			previousColumnPosition >= targetColumn &&
			previousColumn.size > newSize
		) {
			currentColumn.size += newSize;
			previousColumn.size -= newSize;
		}
		else if (
			previousColumnPosition < targetColumn &&
			currentColumn.size > newSize
		) {
			currentColumn.size -= newSize;
			previousColumn.size += newSize;
		}

		newPages = FormSupport.updateColumn(
			newPages,
			pageIndex,
			rowIndex,
			columnIndex,
			currentColumn
		);

		newPages = FormSupport.updateColumn(
			newPages,
			pageIndex,
			rowIndex,
			columnIndex - 1,
			previousColumn
		);
	}
	else if (columnIndex === 0 && targetColumn > 0) {
		newPages = FormSupport.addColumn(
			newPages,
			columnIndex,
			pageIndex,
			rowIndex,
			{
				fields: [],
				size: targetColumn
			}
		);

		newPages = FormSupport.updateColumn(
			newPages,
			pageIndex,
			rowIndex,
			columnIndex + 1,
			{
				...currentColumn,
				size: currentColumn.size - targetColumn
			}
		);

		source.dataset.ddmFieldColumn = columnIndex + 1;
	}

	return newPages;
};

export const handleColumnResized = (state, source, column, direction) => {
	const sourceIndexes = FormSupport.getIndexes(source);
	const {columnIndex, pageIndex, rowIndex} = sourceIndexes;

	let newPages = [...state.pages];

	const currentColumn = FormSupport.getColumn(
		newPages,
		pageIndex,
		rowIndex,
		columnIndex
	);

	if (currentColumn) {
		if (direction === 'left') {
			newPages = handleResizeLeft(state, source, column);
		}
		else {
			newPages = handleResizeRight(state, sourceIndexes, column + 1);
		}
	}

	newPages[pageIndex].rows = FormSupport.removeEmptyRows(newPages, pageIndex);

	return {
		pages: newPages
	};
};

export default handleColumnResized;
