/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const LOCALIZED_INPUT_MASK_FORMAT_STRING = Liferay.Language.get(
	'input-mask-format'
);

const fieldPopoverMap = {
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
