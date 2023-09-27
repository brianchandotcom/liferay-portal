/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FREEMARKER_FRAGMENT_ENTRY_PROCESSOR} from '../config/constants/freemarkerFragmentEntryProcessor';
import {isRequiredFormField} from './isRequiredFormField';

export function isRequiredFormInput(item, fragmentEntryLinks, formFields) {
	const {inputFieldId, inputRequired} =
		fragmentEntryLinks[item.config.fragmentEntryLinkId].editableValues[
			FREEMARKER_FRAGMENT_ENTRY_PROCESSOR
		] || {};

	return inputRequired || isRequiredFormField(inputFieldId, formFields);
}
