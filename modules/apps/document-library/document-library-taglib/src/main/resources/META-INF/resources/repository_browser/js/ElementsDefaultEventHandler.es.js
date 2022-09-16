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
	DefaultEventHandler,
	fetch,
	openConfirmModal,
	openToast,
} from 'frontend-js-web';

class ElementsDefaultEventHandler extends DefaultEventHandler {
	delete({deleteURL}) {
		openConfirmModal({
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-delete-this'
			),
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					fetch(deleteURL, {
						method: 'DELETE',
					}).then((response) => {
						if (response.ok) {
							window.location.reload();
						}
						else {
							openToast({
								message: Liferay.Language.get(
									'an-unexpected-error-occurred'
								),
								type: 'danger',
							});
						}
					});
				}
			},
		});
	}
}

export default ElementsDefaultEventHandler;
