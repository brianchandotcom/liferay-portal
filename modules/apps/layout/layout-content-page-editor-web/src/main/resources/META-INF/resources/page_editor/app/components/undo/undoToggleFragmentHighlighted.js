/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import toggleFragmentHighlighted from '../../thunks/toggleFragmentHighlighted';

function undoAction({action}) {
	const {fragmentEntryKey, highlighted, initiallyHighlighted} = action;

	return toggleFragmentHighlighted({
		fragmentEntryKey,
		highlighted,
		initiallyHighlighted,
	});
}

function getDerivedStateForUndo({action}) {
	const {fragmentEntryKey, highlighted, initiallyHighlighted} = action;

	return {
		fragmentEntryKey,
		highlighted: !highlighted,
		initiallyHighlighted: initiallyHighlighted || highlighted,
	};
}

export {undoAction, getDerivedStateForUndo};
