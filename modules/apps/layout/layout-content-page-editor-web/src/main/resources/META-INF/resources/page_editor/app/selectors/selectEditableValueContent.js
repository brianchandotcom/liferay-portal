/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getEditableLocalizedValue} from '../utils/getEditableLocalizedValue';
import selectEditableValue from './selectEditableValue';

export default function selectEditableValueContent(
	{fragmentEntryLinks, languageId},
	fragmentEntryLinkId,
	editableId,
	processorType
) {
	return getEditableLocalizedValue(
		selectEditableValue(
			{fragmentEntryLinks},
			fragmentEntryLinkId,
			editableId,
			processorType
		),
		languageId
	);
}
