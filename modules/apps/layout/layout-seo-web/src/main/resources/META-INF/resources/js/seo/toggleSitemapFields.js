/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {debounce, toggleDisabled} from 'frontend-js-web';

export default function ({namespace}) {
	let disposed = false;
	let robotsInputChangeEventHandler;
	let robotsInputLocalized;

	const canonicalURLEnabledCheck = document.getElementById(
		`${namespace}canonicalURLEnabled`
	);

	const sitemapFields = [
		document.getElementById(`${namespace}sitemap-include`),
		document.getElementById(`${namespace}sitemap-priority`),
		document.getElementById(`${namespace}sitemap-changefreq`),
	].filter((field) => !field.disabled);

	const toggleSitemapFields = () => {
		if (canonicalURLEnabledCheck?.checked) {
			toggleDisabled(sitemapFields, true);

			return;
		}

		if (!robotsInputLocalized) {
			return;
		}

		const robotsInputValue = robotsInputLocalized
			.getAttrs()
			.translatedLanguages.values()
			.map((languageId) => robotsInputLocalized.getValue(languageId))
			.join('\n');

		toggleDisabled(
			sitemapFields,
			/(noindex)|(nofollow)/i.test(robotsInputValue)
		);
	};

	canonicalURLEnabledCheck?.addEventListener('change', toggleSitemapFields);

	Liferay.componentReady(`${namespace}robots`).then((component) => {
		if (disposed) {
			return;
		}

		robotsInputLocalized = component;

		robotsInputChangeEventHandler = robotsInputLocalized
			.get('inputPlaceholder')
			.on('input', debounce(toggleSitemapFields, 300));

		toggleSitemapFields();
	});

	return {
		dispose() {
			disposed = true;

			canonicalURLEnabledCheck?.removeEventListener(
				'change',
				toggleSitemapFields
			);

			robotsInputChangeEventHandler?.detach();
		},
	};
}
