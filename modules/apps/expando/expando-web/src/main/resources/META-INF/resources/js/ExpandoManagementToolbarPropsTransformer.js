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

export default function propsTransformer({
	additionalProps: {deleteExpandosURL},
	portletNamespace,
	...otherProps
}) {
	return {
		...otherProps,
		onActionButtonClick: (event, {item}) => {
			const action = item.data?.action;

			if (action === 'deleteCustomFields') {
				const form = document.getElementById(`${portletNamespace}fm`);

				if (form) {
					var columnIds = form.querySelector(
						`#${portletNamespace}columnIds`
					);

					if (columnIds) {
						var checkedIds = Liferay.Util.listCheckedExcept(
							form,
							`${portletNamespace}allRowIds`
						);

						columnIds.setAttribute('value', checkedIds);

						submitForm(form, deleteExpandosURL);
					}
				}
			}
		},
	};
}
