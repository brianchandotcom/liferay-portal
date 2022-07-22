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

import {
	getCheckedCheckboxes,
	openConfirmModal,
	postForm,
} from 'frontend-js-web';

function openConfirm({message, onConfirm}) {
	if (Liferay.FeatureFlags['LPS-148659']) {
		openConfirmModal({message, onConfirm});
	}
	else if (confirm(message)) {
		onConfirm(true);
	}
}

export default function propsTransformer({portletNamespace, ...otherProps}) {
	return {
		...otherProps,
		onActionButtonClick: (event, {item}) => {
			const data = item?.data;

			const action = data?.action;

			if (action === 'deleteDispatchLogs') {
				openConfirm({
					message: Liferay.Language.get(
						'are-you-sure-you-want-to-delete-this'
					),
					onConfirm: (isConfirmed) => {
						if (isConfirmed) {
							const form = document.getElementById(
								`${portletNamespace}fm`
							);

							if (form) {
								postForm(form, {
									data: {
										deleteDispatchLogIds: getCheckedCheckboxes(
											form,
											`${portletNamespace}allRowIds`
										),
									},
									url: data?.deleteDispatchLogsURL,
								});
							}
						}
					},
				});
			}
		},
	};
}
