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
import createSection from './sectionAddedHandler.es';
import {updateFocusedField} from '../util/focusedField.es';

const addRow = (props, targetContext, indexes, fields) => {
	const {pageIndex, rowIndex} = indexes;

	let newTargetContext = targetContext[pageIndex];

	if (newTargetContext.nestedFields) {
		const newNestedFields = newTargetContext.nestedFields;

		fields.forEach(field => {
			if (!newNestedFields.includes(field)) {
				newNestedFields.push(field);
			}
		})

		newTargetContext = updateFocusedField(
			props,
			{focusedField: newTargetContext},
			'nestedFields',
			newNestedFields
		);

		fields = fields.map(field => field.fieldName);
	}

	newTargetContext = FormSupport.addRow(
		[newTargetContext],
		rowIndex,
		pageIndex,
		FormSupport.implAddRow(12, fields)
	);

	if (newTargetContext[0].nestedFields) {
		newTargetContext = [updateFocusedField(
			props,
			{focusedField: newTargetContext[0]},
			'rows',
			newTargetContext[0].rows
		)];
	}

	return newTargetContext;
};

const getContext = (context, nestedIndexes = []) => {
	if (nestedIndexes.length) {
		nestedIndexes.forEach((indexes, i) => {
			const {columnIndex, pageIndex, rowIndex} = indexes;

			let fields =
				context[i > 0 ? 0 : pageIndex].rows[rowIndex].columns[
					columnIndex
				].fields;

			if (context[0].nestedFields) {
				fields = fields.map(field =>
					context[0].nestedFields.find(
						nestedField => nestedField.fieldName === field
					)
				);
			}

			context = fields;
		});
	}

	return context;
};

export default (props, state, {addedToPlaceholder, source, target}) => {
	let {pages} = state;

	const sourceNestedIndexes = FormSupport.getNestedIndexes(source);

	const sourceIndexes = sourceNestedIndexes[sourceNestedIndexes.length -1];

	const {columnIndex, pageIndex, rowIndex} = sourceIndexes;

	const targetNestedIndexes = FormSupport.getNestedIndexes(target);

	const targetIndexes = targetNestedIndexes[targetNestedIndexes.length-1];
	
	let currentContext = getContext(pages, sourceNestedIndexes);

	let currentContextParentContext = getContext(pages, sourceNestedIndexes.slice(0, -1));

	currentContextParentContext[sourceNestedIndexes.length > 1 ? 0 : pageIndex].rows = FormSupport.removeFields(currentContextParentContext, pageIndex, rowIndex, columnIndex)[sourceNestedIndexes.length > 1 ? 0 : pageIndex].rows;
	
	let targetContext = getContext(pages, targetNestedIndexes.slice(0, -1));
	
	let newTargetContext;

	if (target.dataset.ddmFieldColumn) {
		pages = createSection(props, {...state, pages}, {
			data: {
				target: target.children[0]
			},
			newField: currentContext[0]
		}).pages;
	} else {
		if (targetIndexes.rowIndex > currentContextParentContext[sourceNestedIndexes.length > 1 ? 0 : pageIndex].rows.length - 1 ||
			addedToPlaceholder
		) {
			newTargetContext = addRow(
				props,
				targetContext,
				targetIndexes,
				currentContext
			)
		} else {
			//WHEN?
			newTargetContext = FormSupport.addFieldToColumn(
				targetContext,
				targetIndexes.pageIndex,
				targetIndexes.rowIndex,
				targetIndexes.columnIndex,
				targetContext[0]
			);
		}

		targetContext[targetNestedIndexes.length > 1 ? 0 : targetIndexes.pageIndex].rows = newTargetContext[targetNestedIndexes.length > 1 ? 0 : targetIndexes.pageIndex].rows
		targetContext[targetNestedIndexes.length > 1 ? 0 : targetIndexes.pageIndex].settingsContext = newTargetContext[targetNestedIndexes.length > 1 ? 0 : targetIndexes.pageIndex].settingsContext
	}

	currentContextParentContext[sourceNestedIndexes.length > 1 ? 0 : pageIndex].rows = FormSupport.removeEmptyRows(currentContextParentContext, sourceNestedIndexes.length > 1 ? 0 : pageIndex);

	if (currentContextParentContext[sourceNestedIndexes.length > 1 ? 0 : pageIndex].nestedFields) {
		const newSectionContext = updateFocusedField(
			props,
			{focusedField: currentContextParentContext[0]},
			'rows',
			currentContextParentContext[sourceNestedIndexes.length > 1 ? 0 : pageIndex].rows
		);

		currentContextParentContext[sourceNestedIndexes.length > 1 ? 0 : pageIndex].settingsContext = newSectionContext.settingsContext;
	}

	return {
		pages
	};
/*
	const {columnIndex, pageIndex, rowIndex} = source;

	const currentColumn = FormSupport.getColumn(
		pages,
		pageIndex,
		rowIndex,
		columnIndex
	);

	pages = FormSupport.removeFields(pages, pageIndex, rowIndex, columnIndex);

	if (target.dataset.ddmFieldColumn) {
		pages = createSection(props, {...state, pages}, {
			data: {
				target: target.children[0]
			},
			newField: currentColumn.fields[0]
		}).pages;
	} else if (
		target.rowIndex > pages[pageIndex].rows.length - 1 ||
		addedToPlaceholder
	) {
		pages = FormSupport.addRow(
			pages,
			targetIndexes.rowIndex,
			targetIndexes.pageIndex,
			FormSupport.implAddRow(12, currentColumn.fields)
		);
	}
	else {
		pages = FormSupport.addFieldToColumn(
			pages,
			targetIndexes.pageIndex,
			targetIndexes.rowIndex,
			targetIndexes.columnIndex,
			currentColumn.fields[0]
		);
	}

	pages[pageIndex].rows = FormSupport.removeEmptyRows(pages, pageIndex);

	return {
		pages,
	};
	*/
};
