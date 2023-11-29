/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {toggleDisabled} from 'frontend-js-web';

import {previewSeoFireChange} from './PreviewSeoEvents';

export default function ({namespace}) {
	const canonicalURLEnabledCheck = document.getElementById(
		`${namespace}canonicalURLEnabled`
	);
	const canonicalURLField = document.getElementById(
		`${namespace}canonicalURL`
	);
	const canonicalURLFieldDefaultLocale = document.getElementById(
		`${namespace}canonicalURL_${Liferay.ThemeDisplay.getLanguageId()}`
	);
	const canonicalURLAlert = document.getElementById(
		`${namespace}canonicalURLAlert`
	);
	const canonicalURLSettings = document.getElementById(
		`${namespace}customCanonicalURLSettings`
	);

	canonicalURLEnabledCheck.addEventListener('change', (event) => {
		canonicalURLAlert.classList.toggle('hide');

		const label = canonicalURLSettings.querySelector('label');

		toggleDisabled(
			[canonicalURLField, canonicalURLFieldDefaultLocale, label],
			!event.target.checked
		);

		if (!canonicalURLField.value && canonicalURLField.placeholder) {
			canonicalURLField.value = canonicalURLField.placeholder;
		}

		previewSeoFireChange(namespace, {
			disabled: !event.target.checked,
			type: 'url',
			value: canonicalURLField.value,
		});
	});
}
