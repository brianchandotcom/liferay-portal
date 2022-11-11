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

import {openToast} from 'frontend-js-web';

export default function ({namespace}) {
	const fileInput = document.getElementById(`${namespace}importScriptInput`);

	Liferay.Util.setPortletConfigurationIconAction(
		`${namespace}journalDDMTemplateImportScript`,
		() => {
			fileInput.click();
		}
	);

	const onChange = (event) => {
		const target = event.target;
		const [file] = target.files || [];

		if (file) {
			file.text()
				.then((text) => {
					if (text) {
						Liferay.fire(`${namespace}scriptImported`, {
							fileName: file.name,
							script: text,
						});
					}
					else {
						showInvalidFileError();
					}
				})
				.catch(() => {
					showInvalidFileError();
				})
				.finally(() => {
					target.value = '';
				});
		}
		else {
			target.value = '';

			showInvalidFileError();
		}
	};

	fileInput.addEventListener('change', onChange);

	return {
		dispose() {
			fileInput.removeEventListener('change', onChange);
		},
	};
}

function showInvalidFileError() {
	openToast({
		message: Liferay.Language.get(
			'an-unexpected-error-occurred-while-importing-the-script'
		),
		title: Liferay.Language.get('error'),
		type: 'danger',
	});
}
