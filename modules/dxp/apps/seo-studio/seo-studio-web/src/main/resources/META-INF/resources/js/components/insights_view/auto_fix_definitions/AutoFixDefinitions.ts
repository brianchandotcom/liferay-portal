/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {AutoFixDefinition} from './AutoFixDefinition';
import {TITLE_AUTO_FIX_DEFINITION} from './TitleAutoFixDefinition';

export const AUTO_FIX_DEFINITIONS: Record<string, AutoFixDefinition> = {
	missingOrEmptyTitleTag: TITLE_AUTO_FIX_DEFINITION,
};
