/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import swapFragmentAction from '../../actions/swapFragment';
import swapFragment from '../../thunks/swapFragment';

function undoAction({action}: {action: ReturnType<typeof swapFragmentAction>}) {
	const {editableValues, fragmentEntryLinkId} = action.fragmentEntryLink;

	return swapFragment({
		editableValues: JSON.stringify(editableValues),
		fragmentEntryKey: action.previousFragmentEntryKey,
		fragmentEntryLinkId,
		groupId: undefined,
	});
}

function getDerivedStateForUndo({
	action,
}: {
	action: ReturnType<typeof swapFragmentAction>;
}) {
	return action;
}

export {getDerivedStateForUndo, undoAction};
