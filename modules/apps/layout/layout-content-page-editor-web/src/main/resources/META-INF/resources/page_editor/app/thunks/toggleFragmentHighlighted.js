/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import toggleFragmentHighlightedAction from '../actions/toggleFragmentHighlighted';
import FragmentService from '../services/FragmentService';

export default function toggleFragmentHighlighted({
	fragmentEntryKey,
	groupId = '0',
	highlighted,
	initiallyHighlighted,
}) {
	return (dispatch) => {
		return FragmentService.toggleFragmentHighlighted({
			fragmentEntryKey,
			groupId,
			highlighted,
			onNetworkStatus: dispatch,
		}).then(({highlightedFragments}) => {
			dispatch(
				toggleFragmentHighlightedAction({
					fragmentEntryKey,
					groupId,
					highlighted,
					highlightedFragments,
					initiallyHighlighted,
				})
			);
		});
	};
}
