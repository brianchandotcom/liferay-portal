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

import {sub} from 'frontend-js-web';

export default function openDeleteCategoryModal({
	message,
	multiple = false,
	onDelete,
}) {
	Liferay.Util.openModal({
		bodyHTML:
			message ||
			Liferay.Language.get('are-you-sure-you-want-to-delete-this'),
		buttons: [
			{
				autoFocus: true,
				displayType: 'secondary',
				label: Liferay.Language.get('cancel'),
				type: 'cancel',
			},
			{
				displayType: 'danger',
				label: Liferay.Language.get('delete'),
				onClick: ({processClose}) => {
					processClose();

					onDelete();
				},
			},
		],
		status: 'danger',
		title: sub(
			Liferay.Language.get('delete-x'),
			multiple
				? Liferay.Language.get('categories')
				: Liferay.Language.get('category')
		),
	});
}
