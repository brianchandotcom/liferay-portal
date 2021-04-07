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

import {evaluate} from '../../utils/evaluation.es';
import {PagesVisitor} from '../../utils/visitors.es';
import {EVENT_TYPES} from '../actions/eventTypes.es';

export default function formValidate({
	activePage,
	defaultLanguageId,
	editingLanguageId,
	groupId,
	pages,
	portletNamespace,
	rules,
	viewMode,
}) {
	return (dispatch) => {
		return evaluate(null, {
			defaultLanguageId,
			editingLanguageId,
			groupId,
			pages,
			portletNamespace,
			rules,
			viewMode,
		}).then((evaluatedPages) => {
			let validForm = true;
			const visitor = new PagesVisitor(evaluatedPages);

			visitor.mapFields(
				({valid}) => {
					if (!valid) {
						validForm = false;
					}
				},
				true,
				true
			);

			if (!validForm) {
				dispatch({
					payload: {
						newPages: evaluatedPages,
						pageIndex: activePage,
					},
					type: EVENT_TYPES.PAGE.VALIDATION_FAILED,
				});
			}

			return Promise.resolve(validForm);
		});
	};
}
