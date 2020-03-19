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

import {getIndexes} from 'dynamic-data-mapping-form-renderer/js/components/FormRenderer/FormSupport.es';
import dom from 'metal-dom';
import React, {useEffect, useState} from 'react';

import DataLayoutBuilderColumnDropZone from './DataLayoutBuilderColumnDropZone.es';
import DragLayer from './DragLayer.es';

const getColumns = () => [
	...document.querySelectorAll(
		'.col-empty .ddm-target:not([data-drop-disabled="true"])'
	),
];

const getFields = () => [
	...document.querySelectorAll(
		'.ddm-field-container.ddm-target:not([data-drop-disabled="true"])'
	),
];

const getColumnKey = node => {
	const {columnIndex, pageIndex, rowIndex} = getIndexes(node.parentElement);
	const placeholder = !!dom.closest(node, '.placeholder');

	return `column_${pageIndex}_${rowIndex}_${columnIndex}_${placeholder}`;
};

const getFieldKey = node => {
	return node.dataset.fieldName;
};

export default ({dataLayoutBuilder}) => {
	const [columns, setColumns] = useState(getColumns());
	const [fields, setFields] = useState(getFields());

	useEffect(() => {
		const provider = dataLayoutBuilder.getLayoutProvider();

		const eventHandler = provider.on('rendered', () => {
			setColumns(getColumns());
			setFields(getFields());
		});

		return () => eventHandler.removeListener();
	}, [dataLayoutBuilder]);

	return (
		<>
			<DragLayer />

			{columns.map(
				(node, index) =>
					node.parentElement && (
						<DataLayoutBuilderColumnDropZone
							dataLayoutBuilder={dataLayoutBuilder}
							key={getColumnKey(node, index)}
							node={node}
						/>
					)
			)}

			{fields.map(
				(node, index) =>
					node.parentElement && (
						<DataLayoutBuilderColumnDropZone
							dataLayoutBuilder={dataLayoutBuilder}
							key={getFieldKey(node, index)}
							node={node}
						/>
					)
			)}
		</>
	);
};
