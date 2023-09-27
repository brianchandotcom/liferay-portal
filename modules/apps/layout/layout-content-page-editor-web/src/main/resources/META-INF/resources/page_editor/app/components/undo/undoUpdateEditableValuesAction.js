/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import updateEditableValues from '../../thunks/updateEditableValues';

function undoAction({action}) {
	return updateEditableValues({
		...action,
	});
}

function getDerivedStateForUndo({action, state}) {
	const {fragmentEntryLinkId} = action;

	const fragmentEntryLink = state.fragmentEntryLinks[fragmentEntryLinkId];

	return {
		editableValues: fragmentEntryLink.editableValues,
		fragmentEntryLinkId,
	};
}

export {undoAction, getDerivedStateForUndo};
