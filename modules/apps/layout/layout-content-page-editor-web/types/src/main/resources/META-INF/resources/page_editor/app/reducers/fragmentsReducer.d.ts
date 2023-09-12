/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import addFragmentComposition from '../actions/addFragmentComposition';
import toggleFragmentHighlighted from '../actions/toggleFragmentHighlighted';
import updateFragments, {FragmentSet} from '../actions/updateFragments';
export default function fragmentsReducer(
	fragments: FragmentSet[] | undefined,
	action: ReturnType<
		| typeof addFragmentComposition
		| typeof toggleFragmentHighlighted
		| typeof updateFragments
	>
): FragmentSet[];
