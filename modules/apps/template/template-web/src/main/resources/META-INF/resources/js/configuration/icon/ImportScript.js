/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openToast} from 'frontend-js-web';

export default function ({namespace}) {
	const fileInput = document.getElementById(`${namespace}importScriptInput`);

	Liferay.Util.setPortletConfigurationIconAction(
		`${namespace}templateImportScript`,
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
