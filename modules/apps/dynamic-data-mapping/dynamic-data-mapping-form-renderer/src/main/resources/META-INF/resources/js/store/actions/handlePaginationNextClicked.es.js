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

import {evaluate} from '../../util/evaluation.es';
import {PagesVisitor} from '../../util/visitors.es';

export default (evaluatorContext, dispatch) => {
	const {activePage, pages} = evaluatorContext;

	return evaluate(null, evaluatorContext).then(evaluatedPages => {
		let validPage = true;
		const visitor = new PagesVisitor(evaluatedPages);

		visitor.mapFields(
			({valid}, fieldIndex, columnIndex, rowIndex, pageIndex) => {
				if (activePage === pageIndex && !valid) {
					validPage = false;
				}
			}
		);

		if (validPage) {
			const nextActivePageIndex = evaluatedPages.findIndex(
				({enabled}, index) => {
					let found = false;

					if (enabled && index > activePage) {
						found = true;
					}

					return found;
				}
			);

			dispatch(
				'activePageUpdated',
				Math.min(nextActivePageIndex, pages.length - 1)
			);
		} else {
			dispatch('pageValidationFailed', activePage);
		}
	});
};
