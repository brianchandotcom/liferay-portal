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

import {EVENT_TYPES} from '../actions/eventTypes.es';
import {evaluate} from '../util/evaluation.es';

export default function previousPage({
	activePage,
	defaultLanguageId,
	editingLanguageId,
	formId,
	groupId,
	pages,
	portletNamespace,
	rules,
}) {
	return (dispatch) => {
		evaluate(null, {
			defaultLanguageId,
			editingLanguageId,
			groupId,
			pages,
			portletNamespace,
			rules,
		}).then((evaluatedPages) => {
			let previousActivePageIndex = activePage;

			for (let i = activePage - 1; i > -1; i--) {
				if (evaluatedPages[i].enabled) {
					previousActivePageIndex = i;

					break;
				}
			}

			const activePageUpdated = Math.max(previousActivePageIndex, 0);

			dispatch({
				payload: activePageUpdated,
				type: EVENT_TYPES.CHANGE_ACTIVE_PAGE,
			});

			Liferay.fire('ddmFormPageShow', {
				formId,
				page: activePageUpdated,
				title: pages[activePageUpdated].title,
			});
		});
	};
}
