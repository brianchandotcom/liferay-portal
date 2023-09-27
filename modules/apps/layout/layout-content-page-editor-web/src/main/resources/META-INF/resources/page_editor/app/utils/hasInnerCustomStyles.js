/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
const STYLES_PROCESSOR_KEY =
	'com.liferay.fragment.entry.processor.styles.StylesFragmentEntryProcessor';

export default function hasInnerCommonStyles(fragmentEntryLink) {
	const processor = fragmentEntryLink?.editableValues[STYLES_PROCESSOR_KEY];

	return Boolean(processor?.hasCommonStyles);
}
