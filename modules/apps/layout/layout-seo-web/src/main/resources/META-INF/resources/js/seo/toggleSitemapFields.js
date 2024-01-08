/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {debounce, toggleDisabled} from 'frontend-js-web';

const INCLUDE_SITEMAP_FIELD_DISABLED_VALUE = '0';

export default function ({namespace}) {
	let disposed = false;
	let robotsInputChangeEventHandler;
	let robotsInputLocalized;

	const canonicalURLEnabledCheck = document.getElementById(
		`${namespace}canonicalURLEnabled`
	);

	const includeSitemapField = document.getElementById(
		`${namespace}sitemap-include`
	);

	const sitemapFields = [
		includeSitemapField,
		document.getElementById(`${namespace}sitemap-priority`),
		document.getElementById(`${namespace}sitemap-changefreq`),
	].filter((field) => !field.disabled);

	const disableSitemapFields = () => {
		includeSitemapField.value = INCLUDE_SITEMAP_FIELD_DISABLED_VALUE;

		toggleDisabled(sitemapFields, true);
	};

	const toggleSitemapFields = () => {
		if (canonicalURLEnabledCheck?.checked) {
			disableSitemapFields();

			return;
		}

		if (!robotsInputLocalized) {
			toggleDisabled(sitemapFields, false);

			return;
		}

		const robotsInputValue = robotsInputLocalized
			.getAttrs()
			.translatedLanguages.values()
			.map((languageId) => robotsInputLocalized.getValue(languageId))
			.join('\n');

		if (/(noindex)|(nofollow)/i.test(robotsInputValue)) {
			disableSitemapFields();
		}
		else {
			toggleDisabled(sitemapFields, false);
		}
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
