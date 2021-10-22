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

import {postForm} from 'frontend-js-web';

export default function propsTransformer({portletNamespace, ...otherProps}) {
	const deleteBatchPlannerPlans = (itemData) => {
		if (
			confirm(
				Liferay.Language.get(
					'are-you-sure-you-want-to-delete-the-selected-templates'
				)
			)
		) {
			const form = document.getElementById(`${portletNamespace}fm`);

			const searchContainer = document.getElementById(
				`${portletNamespace}batchPlannerPlanSearchContainer`
			);

			if (form && searchContainer) {
				postForm(form, {
					data: {
						batchPlannerPlanIds: Liferay.Util.listCheckedExcept(
							searchContainer,
							`${portletNamespace}allRowIds`
						),
					},
					url: itemData?.deleteBatchPlannerPlansURL,
				});
			}
		}
	};

	return {
		...otherProps,
		onActionButtonClick: (event, {item}) => {
			const data = item?.data;

			const action = data?.action;

			if (action === 'deleteBatchPlannerPlans') {
				deleteBatchPlannerPlans(data);
			}
		},
	};
}
