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

import openTemplateModal from './modal/openTemplateModal';

export default function propsTransformer({
	additionalProps: {addTemplateEntryURL, itemTypes} = {},
	portletNamespace,
	...otherProps
}) {
	const deleteSelectedTemplateEntries = () => {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			const form = document.getElementById(`${portletNamespace}fm`);

			if (form) {
				submitForm(form);
			}
		}
	};

	return {
		...otherProps,
		onActionButtonClick(event, {item}) {
			const data = item?.data;

			const action = data?.action;

			if (action === 'deleteSelectedTemplateEntries') {
				deleteSelectedTemplateEntries();
			}
		},
		onCreateButtonClick(event, {item}) {
			const action = item?.data?.action;

			if (action === 'addInformationTemplateEntry') {
				openTemplateModal({
					addTemplateEntryURL,
					itemTypes,
					namespace: portletNamespace,
				});
			}
		},
	};
}
