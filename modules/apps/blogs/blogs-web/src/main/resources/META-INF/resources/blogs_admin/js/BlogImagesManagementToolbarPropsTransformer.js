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

export default function propsTransformer({portletNamespace, ...otherProps}) {
	const deleteImages = () => {
		if (
			confirm(
				Liferay.Language.get(
					'are-you-sure-you-want-to-delete-the-selected-images'
				)
			)
		) {
			const form = document.getElementById(`${portletNamespace}fm`);

			if (form) {
				var cmd = form.querySelector(`#${portletNamespace}cmd`);

				cmd?.setAttribute('value', 'delete');

				var deleteFileEntryIds = form.querySelector(
					`#${portletNamespace}deleteFileEntryIds`
				);

				deleteFileEntryIds?.setAttribute(
					'value',
					Liferay.Util.listCheckedExcept(
						form,
						`${portletNamespace}allRowIds`
					)
				);

				submitForm(form);
			}
		}
	};

	return {
		...otherProps,
		onActionButtonClick: (event, {item}) => {
			if (item?.data?.action === 'deleteImages') {
				deleteImages();
			}
		},
	};
}
