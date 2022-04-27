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

import {openSimpleInputModal} from 'frontend-js-web';

import openDeleteStyleBookModal from './openDeleteStyleBookModal';

export default function propsTransformer({
	additionalProps: {copyStyleBookEntryURL, exportStyleBookEntriesURL},
	portletNamespace,
	...otherProps
}) {
	const copySelectedStyleBookEntries = () => {
		const form = document.getElementById(`${portletNamespace}fm`);

		const styleBookEntryIds = document.getElementById(
			`${portletNamespace}styleBookEntryIds`
		);

		if (!form || !styleBookEntryIds) {
			return;
		}

		styleBookEntryIds.setAttribute(
			'value',
			Liferay.Util.listCheckedExcept(form, `${portletNamespace}allRowIds`)
		);

		const styleBookEntryFm = document.getElementById(
			`${portletNamespace}styleBookEntryFm`
		);

		if (styleBookEntryFm) {
			submitForm(styleBookEntryFm, copyStyleBookEntryURL);
		}
	};

	const deleteSelectedStyleBookEntries = () => {
		openDeleteStyleBookModal({
			multiple: true,
			onDelete: () => {
				const form = document.getElementById(`${portletNamespace}fm`);

				if (form) {
					submitForm(form);
				}
			},
		});
	};

	const exportSelectedStyleBookEntries = () => {
		const form = document.getElementById(`${portletNamespace}fm`);

		if (form) {
			submitForm(form, exportStyleBookEntriesURL);
		}
	};

	return {
		...otherProps,
		onActionButtonClick: (event, {item}) => {
			const action = item?.data?.action;

			if (action === 'copySelectedStyleBookEntries') {
				copySelectedStyleBookEntries();
			}
			else if (action === 'deleteSelectedStyleBookEntries') {
				deleteSelectedStyleBookEntries();
			}
			else if (action === 'exportSelectedStyleBookEntries') {
				exportSelectedStyleBookEntries();
			}
		},
		onCreateButtonClick(event, {item}) {
			const data = item?.data;

			openSimpleInputModal({
				dialogTitle: data?.title,
				formSubmitURL: data?.addStyleBookEntryURL,
				mainFieldLabel: Liferay.Language.get('name'),
				mainFieldName: 'name',
				namespace: `${portletNamespace}`,
				placeholder: Liferay.Language.get('name'),
			});
		},
	};
}
