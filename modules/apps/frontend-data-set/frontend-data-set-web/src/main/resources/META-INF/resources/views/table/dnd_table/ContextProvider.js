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

import PropTypes from 'prop-types';
import React, {useCallback, useMemo, useState} from 'react';

import Context from './TableContext';

function ContextProvider({children, columnNames}) {
	const [tableWidth, updateTableWidth] = useState(null);
	const [columnDefinitions, updateColumnDefinitions] = useState(new Map());
	const [draggingColumnName, updateDraggingColumnName] = useState(null);
	const [draggingAllowed, updateDraggingAllowed] = useState(true);

	const registerColumn = (name, width, resizable) => {
		updateColumnDefinitions((definitions) => {
			const updatedDefinitions = new Map(definitions);

			updatedDefinitions.set(name, {
				resizable,
				width,
			});

			return updatedDefinitions;
		});
	};

	const isFixed = useMemo(() => {
		const allRegistered = columnNames.every((name) =>
			columnDefinitions.has(name)
		);

		return allRegistered;
	}, [columnDefinitions, columnNames]);

	const resizeColumn = useCallback(
		(name, width) => {
			if (isFixed) {
				updateColumnDefinitions((definitions) => {
					const resizedColumn = definitions.get(name);

					const isColumnReducing = resizedColumn.width > width;

					let totalWidth = 0;

					definitions.forEach((definition) => {
						totalWidth += definition.width;
					});

					const nextColumnName =
						columnNames[columnNames.indexOf(name) + 1];
					const nextColumn = definitions.get(nextColumnName);
					const columnsAreShorterThanContainer =
						totalWidth < tableWidth;

					if (
						(isColumnReducing &&
							columnsAreShorterThanContainer &&
							!nextColumn?.resizable) ||
						width < 40
					) {
						updateDraggingAllowed(false);

						return definitions;
					}

					updateDraggingAllowed(true);

					const newDefinitions = new Map(definitions);

					if (isColumnReducing && columnsAreShorterThanContainer) {
						newDefinitions.set(nextColumnName, {
							...nextColumn,
							width:
								nextColumn.width + resizedColumn.width - width,
						});
					}

					newDefinitions.set(name, {
						...resizedColumn,
						width,
					});

					return newDefinitions;
				});
			}
		},
		[columnNames, tableWidth, isFixed]
	);

	return (
		<Context.Provider
			value={{
				columnDefinitions,
				columnNames,
				draggingAllowed,
				draggingColumnName,
				isFixed,
				registerColumn,
				resizeColumn,
				tableWidth,
				updateDraggingAllowed,
				updateDraggingColumnName,
				updateTableWidth,
			}}
		>
			{children}
		</Context.Provider>
	);
}

ContextProvider.defaultProps = {
	columnNames: [],
};

ContextProvider.propTypes = {
	columnNames: PropTypes.arrayOf(PropTypes.string),
};

export default ContextProvider;
