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

import {EVENT_TYPES as CORE_EVENT_TYPES} from '../../../core/actions/eventTypes.es';
import {evaluate} from '../../../utils/evaluation.es';
import {PagesVisitor} from '../../../utils/visitors.es';

export default function nextPage({
	activePage,
	defaultLanguageId,
	editingLanguageId,
	formId,
	groupId,
	pages,
	portletNamespace,
	rules,
	selectedPage,
	viewMode,
}) {
	const isValidPage = (currentPage, pages) => {
		const visitor = new PagesVisitor(pages);
		let validPage = true;

		visitor.mapFields(
			({valid}, fieldIndex, columnIndex, rowIndex, pageIndex) => {
				if (currentPage === pageIndex && !valid) {
					validPage = false;
				}
			},
			true,
			true
		);

		return validPage;
	};

	return (dispatch) => {
		evaluate(null, {
			activePage,
			defaultLanguageId,
			editingLanguageId,
			formId,
			groupId,
			nextPage: activePage + 1,
			pages,
			portletNamespace,
			previousPage: activePage,
			rules,
			viewMode,
		}).then((evaluatedPages) => {
			let validPage = true;

			let currentPage = activePage;

			if (selectedPage) {
				while (validPage) {
					validPage = isValidPage(currentPage, evaluatedPages);

					if (currentPage === selectedPage) {
						break;
					}

					if (validPage) {
						currentPage++;
					}
				}
			}
			else {
				validPage = isValidPage(activePage, evaluatedPages);
			}

			if (validPage || selectedPage) {
				const nextActivePageIndex = selectedPage
					? evaluatedPages.findIndex(
							({enabled}, index) =>
								enabled && index === currentPage
					  )
					: evaluatedPages.findIndex(
							({enabled}, index) => enabled && index > activePage
					  );

				const activePageUpdated = Math.min(
					nextActivePageIndex,
					pages.length - 1
				);

				dispatch({
					payload: {activePage: activePageUpdated},
					type: CORE_EVENT_TYPES.PAGE.CHANGE,
				});

				dispatch({
					payload: evaluatedPages,
					type: CORE_EVENT_TYPES.PAGE.UPDATE,
				});

				Liferay.fire('ddmFormPageShow', {
					formId,
					page: activePageUpdated,
					title: pages[activePageUpdated].title,
				});
			}
			else {
				const pageIndex = selectedPage ? currentPage : activePage;
				dispatch({
					payload: {newPages: evaluatedPages, pageIndex},
					type: CORE_EVENT_TYPES.PAGE.VALIDATION_FAILED,
				});
			}
		});
	};
}
