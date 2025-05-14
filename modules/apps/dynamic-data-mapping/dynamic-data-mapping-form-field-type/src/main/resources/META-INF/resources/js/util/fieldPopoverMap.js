/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {sub} from 'frontend-js-web';

const HTML_AUTOCOMPLETE_ATTRIBUTE_TITLE = Liferay.Language.get(
	'html-autocomplete-attribute'
);

const LOCALIZED_INPUT_MASK_FORMAT_STRING =
	Liferay.Language.get('input-mask-format');

const fieldPopoverMap = {
	htmlAutocompleteAttribute: {
		alignPosition: 'left-top',
		content: sub(
			Liferay.Language.get(
				'set-the-x-for-this-field-this-informs-the-browser-of-the-type-of-data-it-stores'
			),
			`<a href="https://html.spec.whatwg.org/multipage/form-control-infrastructure.html#autofill" target="_blank">${HTML_AUTOCOMPLETE_ATTRIBUTE_TITLE.toLowerCase().replace(
				'html',
				'HTML'
			)}</a>`
		),
		header: HTML_AUTOCOMPLETE_ATTRIBUTE_TITLE,
	},
	inputMaskFormat: {
		alignPosition: 'right-bottom',
		content: Liferay.Language.get(
			'an-input-mask-helps-to-ensure-a-predefined-format'
		),
		header: LOCALIZED_INPUT_MASK_FORMAT_STRING,
		hideOnTriggerOut: true,
		image: {
			alt: LOCALIZED_INPUT_MASK_FORMAT_STRING,
			height: 170,
			src: `${themeDisplay.getPathThemeImages()}/forms/input_mask_format.png`,
			width: 232,
		},
	},
};

export default fieldPopoverMap;
