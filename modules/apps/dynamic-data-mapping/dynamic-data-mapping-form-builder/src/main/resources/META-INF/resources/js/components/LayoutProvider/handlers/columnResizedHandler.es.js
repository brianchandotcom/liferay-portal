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

import {updateFocusedField} from '../util/focusedField.es';

const getColumn = (pages, nestedIndexes = []) => {
	let column;
	let context = pages;

	nestedIndexes.forEach(indexes => {
		const {columnIndex, pageIndex, rowIndex} = indexes;

		column = FormSupport.getColumn(
			context,
			column ? 0 : pageIndex,
			rowIndex,
			columnIndex
		);

		context = column && column.fields;
	});

	return column;
};

const getContext = (pages, nestedIndexes = []) => {
	let context = pages;

	if (nestedIndexes.length) {
		nestedIndexes.forEach((indexes, i) => {
			const {columnIndex, pageIndex, rowIndex} = indexes;

			context =
				context[i > 0 ? 0 : pageIndex].rows[rowIndex].columns[
					columnIndex
				].fields;
		});
	}

	return context;
};

const getColumnPosition = (context, indexes) => {
	const {columnIndex, pageIndex, rowIndex} = indexes;

	return FormSupport.getColumnPosition(
		context,
		pageIndex,
		rowIndex,
		columnIndex
	);
};

export const handleResizeRight = (
	props,
	state,
	source,
	sourceIndexes,
	targetColumn
) => {
	const {pages} = state;

	let newPages = [...pages];

	const parentIndexes = sourceIndexes.slice(0, -1);

	const indexes = sourceIndexes[sourceIndexes.length - 1];

	let parentContext = getContext(pages, parentIndexes);

	const currentColumn = getColumn(parentContext, [indexes]);

	const nextColumn = getColumn(parentContext, [
		{
			columnIndex: indexes.columnIndex + 1,
			pageIndex: indexes.pageIndex,
			rowIndex: indexes.rowIndex
		}
	]);

	const currentColumnPosition = getColumnPosition(parentContext, {
		columnIndex: indexes.columnIndex,
		pageIndex: parentIndexes.length ? 0 : indexes.pageIndex,
		rowIndex: indexes.rowIndex
	});

	let newCurrentColumn;
	let newNextColumn;

	if (nextColumn) {
		const newSize = Math.abs(currentColumnPosition - targetColumn);

		if (
			targetColumn < currentColumnPosition &&
			currentColumn.size > newSize
		) {
			newCurrentColumn = {
				...currentColumn,
				size: Math.max(currentColumn.size - newSize, 1),
			};

			newNextColumn = {
				...nextColumn,
				size: Math.min(nextColumn.size + newSize, 12),
			};
		}
		else if (targetColumn > currentColumnPosition) {
			if (nextColumn.size === 1 && nextColumn.fields.length === 0) {
				newCurrentColumn = {
					...currentColumn,
					size: currentColumn.size + newSize,
				};

				parentContext = FormSupport.removeColumn(
					parentContext,
					parentIndexes.length ? 0 : indexes.pageIndex,
					indexes.rowIndex,
					indexes.columnIndex + 1
				);
			}
			else if (nextColumn.size > newSize) {
				newCurrentColumn = {
					...currentColumn,
					size: currentColumn.size + newSize,
				};

				newNextColumn = {
					...nextColumn,
					size: nextColumn.size - newSize,
				};
			}
		}
	}
	else if (
		currentColumn.size > currentColumnPosition - targetColumn &&
		targetColumn < currentColumnPosition
	) {
		const newSize = currentColumnPosition - targetColumn;

		newCurrentColumn = {
			...currentColumn,
			size: currentColumn.size - newSize,
		};

		parentContext = FormSupport.addColumn(
			parentContext,
			indexes.columnIndex + 1,
			parentIndexes.length ? 0 : indexes.pageIndex,
			indexes.rowIndex,
			{
				fields: [],
				size: newSize,
			}
		);
	}

	if (newCurrentColumn) {
		parentContext = FormSupport.updateColumn(
			parentContext,
			parentIndexes.length ? 0 : indexes.pageIndex,
			indexes.rowIndex,
			indexes.columnIndex,
			newCurrentColumn
		);
	}

	if (newNextColumn) {
		parentContext = FormSupport.updateColumn(
			parentContext,
			parentIndexes.length ? 0 : indexes.pageIndex,
			indexes.rowIndex,
			indexes.columnIndex + 1,
			newNextColumn
		);
	}

	if (parentIndexes.length) {
		parentContext[0] = updateFocusedField(
			props,
			{focusedField: parentContext[0]},
			'rows',
			parentContext[0].rows
		);

		parentIndexes.reduce((result, next, index, vector) => {
			const {columnIndex, pageIndex, rowIndex} = vector[index];

			return result[pageIndex].rows[rowIndex].columns[columnIndex].fields;
		}, newPages)[0] = parentContext[0];
	}
	else {
		newPages = parentContext;
	}

	return newPages;
};

const handleResizeLeft = (
	props,
	state,
	source,
	sourceIndexes,
	targetColumn
) => {
	const {pages} = state;

	let newPages = [...pages];

	const indexes = sourceIndexes[sourceIndexes.length - 1];

	const parentIndexes = sourceIndexes.slice(0, -1);

	let parentContext = getContext(pages, parentIndexes);

	const currentColumn = getColumn(parentContext, [indexes]);

	const previousColumn = getColumn(parentContext, [
		{
			columnIndex: indexes.columnIndex - 1,
			pageIndex: indexes.pageIndex,
			rowIndex: indexes.rowIndex
		}
	]);

	const previousColumnPosition = getColumnPosition(parentContext, {
		columnIndex: indexes.columnIndex - 1,
		pageIndex: parentIndexes.length ? 0 : indexes.pageIndex,
		rowIndex: indexes.rowIndex
	});

	if (
		previousColumn &&
		previousColumn.fields.length == 0 &&
		previousColumn.size === 1 &&
		targetColumn <= previousColumnPosition
	) {
		parentContext = FormSupport.removeColumn(
			parentContext,
			parentIndexes.length ? 0 : indexes.pageIndex,
			indexes.rowIndex,
			indexes.columnIndex - 1
		);

		source.dataset.ddmFieldColumn = indexes.columnIndex - 1;

		parentContext = FormSupport.updateColumn(
			parentContext,
			parentIndexes.length ? 0 : indexes.pageIndex,
			indexes.rowIndex,
			indexes.columnIndex - 1,
			{
				...currentColumn,
				size: currentColumn.size + 1,
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

		parentContext = FormSupport.updateColumn(
			parentContext,
			parentIndexes.length ? 0 : indexes.pageIndex,
			indexes.rowIndex,
			indexes.columnIndex,
			currentColumn
		);

		parentContext = FormSupport.updateColumn(
			parentContext,
			parentIndexes.length ? 0 : indexes.pageIndex,
			indexes.rowIndex,
			indexes.columnIndex - 1,
			previousColumn
		);
	}
	else if (indexes.columnIndex === 0 && targetColumn > 0) {
		parentContext = FormSupport.addColumn(
			parentContext,
			indexes.columnIndex,
			parentIndexes.length ? 0 : indexes.pageIndex,
			indexes.rowIndex,
			{
				fields: [],
				size: targetColumn,
			}
		);

		parentContext = FormSupport.updateColumn(
			parentContext,
			parentIndexes.length ? 0 : indexes.pageIndex,
			indexes.rowIndex,
			indexes.columnIndex + 1,
			{
				...currentColumn,
				size: currentColumn.size - targetColumn,
			}
		);

		source.dataset.ddmFieldColumn = indexes.columnIndex + 1;
	}

	if (parentIndexes.length) {
		parentContext[0] = updateFocusedField(
			props,
			{focusedField: parentContext[0]},
			'rows',
			parentContext[0].rows
		);

		parentIndexes.reduce((result, next, index, vector) => {
			const {columnIndex, pageIndex, rowIndex} = vector[index];

			return result[pageIndex].rows[rowIndex].columns[columnIndex].fields;
		}, newPages)[0] = parentContext[0];
	}
	else {
		newPages = parentContext;
	}

	return newPages;
};

export default (props, state, source, container, column, direction) => {
	const {pages} = state;

	let newPages = [...pages];

	const sourceIndexes = FormSupport.getNestedIndexes(container);

	const currentColumn = getColumn(pages, sourceIndexes);

	if (currentColumn) {
		if (direction === 'left') {
			newPages = handleResizeLeft(
				props,
				state,
				source,
				sourceIndexes,
				column
			);
		}
		else {
			newPages = handleResizeRight(
				props,
				state,
				source,
				sourceIndexes,
				column + 1
			);
		}
	}

	return {
		pages: newPages,
	};
};
