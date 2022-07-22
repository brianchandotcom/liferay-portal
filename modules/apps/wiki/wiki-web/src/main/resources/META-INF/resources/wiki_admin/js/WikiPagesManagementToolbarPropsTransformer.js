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

import openConfirm from '../../wiki/js/openConfirm';

export default function propsTransformer({
	additionalProps: {deletePagesCmd, deletePagesURL, trashEnabled},
	portletNamespace,
	...otherProps
}) {
	return {
		...otherProps,
		onActionButtonClick: (event, {item}) => {
			if (item?.data?.action === 'deletePages') {
				openConfirm({
					message: Liferay.Language.get(
						'are-you-sure-you-want-to-delete-the-selected-entries'
					),
					onConfirm: (isConfirmed) => {
						if (isConfirmed || trashEnabled) {
							const form = document.getElementById(
								`${portletNamespace}fm`
							);

							if (form) {
								postForm(form, {
									data: {
										cmd: deletePagesCmd,
									},
									url: deletePagesURL,
								});
							}
						}
					},
				});
			}
		},
	};
}
