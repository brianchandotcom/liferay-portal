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

import dom from 'metal-dom';

import {getField, isFieldSet, isFieldSetChild} from './fieldSupport.es';

export const disableFieldSetDragSources = (element, pages) => {
	const dragSources = element.querySelectorAll('.ddm-drag');

	for (let i = 0; i < dragSources.length; i++) {
		const source = dragSources[i];

		const {fieldName} = source.parentElement.dataset;

		if (isFieldSetChild(pages, fieldName)) {
			source.setAttribute('data-drag-disabled', true);
		}
	}
};

export const disableFieldSetDropTargets = (element, pages) => {
	const dropTargets = element.querySelectorAll('.ddm-target');

	for (let i = 0; i < dropTargets.length; i++) {
		const target = dropTargets[i];

		const parentFieldNode = dom.closest(target, '.ddm-field-container');

		if (parentFieldNode) {
			const {fieldName} = parentFieldNode.dataset;

			const parentField = getField(pages, fieldName);

			if (
				(parentField && isFieldSet(parentField)) ||
				isFieldSetChild(pages, fieldName)
			) {
				target.setAttribute('data-drop-disabled', true);
			}
		}
	}
};
