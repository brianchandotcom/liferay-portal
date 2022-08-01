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

import {FREEMARKER_FRAGMENT_ENTRY_PROCESSOR} from '../config/constants/freemarkerFragmentEntryProcessor';
import {isRequiredFormField} from './isRequiredFormField';

export function isRequiredFormInput(item, fragmentEntryLinks, formFields) {
	const {inputFieldId, inputRequired} =
		fragmentEntryLinks[item.config.fragmentEntryLinkId].editableValues[
			FREEMARKER_FRAGMENT_ENTRY_PROCESSOR
		] || {};

	return inputRequired || isRequiredFormField(inputFieldId, formFields);
}
