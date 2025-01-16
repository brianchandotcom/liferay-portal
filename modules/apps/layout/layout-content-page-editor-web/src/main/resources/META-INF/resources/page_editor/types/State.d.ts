/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FragmentEntryLinkMap} from '../app/actions/addFragmentEntryLinks';
import layoutDataReducer from '../app/reducers/layoutDataReducer';

export interface State {
	fragmentEntryLinks: FragmentEntryLinkMap;
	languageId: Liferay.Language.Locale;
	layoutData: ReturnType<typeof layoutDataReducer>;
	segmentsExperienceId: string;
}
