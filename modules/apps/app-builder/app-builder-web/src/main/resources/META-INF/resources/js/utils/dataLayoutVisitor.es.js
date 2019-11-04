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

export function findField(dataLayoutPages, fieldName) {
	return (dataLayoutPages || []).find(({dataLayoutRows}) => {
		return (dataLayoutRows || []).find(({dataLayoutColumns}) => {
			return (dataLayoutColumns || []).find(({fieldNames}) => {
				return (fieldNames || []).find(name => name === fieldName);
			});
		});
	});
}

export function containsField(dataLayoutPages, fieldName) {
	return !!findField(dataLayoutPages, fieldName);
}

export function mapDataLayoutColumns(dataLayoutPages, fn = () => {}) {
	return (dataLayoutPages || []).map(
		({dataLayoutRows, ...dataLayoutPage}, pageIndex) => {
			return {
				...dataLayoutPage,
				dataLayoutRows: (dataLayoutRows || []).map(
					({dataLayoutColumns, ...dataLayoutRow}, rowIndex) => {
						return {
							...dataLayoutRow,
							dataLayoutColumns: dataLayoutColumns.map(
								(dataLayoutColumn, columnIndex) =>
									fn(
										dataLayoutColumn,
										columnIndex,
										rowIndex,
										pageIndex
									)
							)
						};
					}
				)
			};
		}
	);
}

export function deleteField(dataLayoutPages, fieldName) {
	return mapDataLayoutColumns(
		dataLayoutPages,
		({fieldNames, ...dataLayoutColumn}) => {
			return {
				...dataLayoutColumn,
				fieldNames: (fieldNames || []).filter(
					name => name !== fieldName
				)
			};
		}
	);
}

export function getFieldNameFromIndexes(
	{dataLayoutPages},
	{columnIndex, fieldIndex = 0, pageIndex, rowIndex}
) {
	return dataLayoutPages[pageIndex].dataLayoutRows[rowIndex]
		.dataLayoutColumns[columnIndex].fieldNames[fieldIndex];
}

export function getIndexesFromFieldName({dataLayoutPages}, fieldName) {
	let indexes = {};

	dataLayoutPages.some(({dataLayoutRows}, pageIndex) => {
		return dataLayoutRows.some(({dataLayoutColumns}, rowIndex) => {
			return dataLayoutColumns.some(({fieldNames}, columnIndex) => {
				return fieldNames.some(name => {
					if (name === fieldName) {
						indexes = {
							columnIndex,
							pageIndex,
							rowIndex
						};

						return true;
					}

					return false;
				});
			});
		});
	});

	return indexes;
}
