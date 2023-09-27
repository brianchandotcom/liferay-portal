/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import toggleWidgetHighlighted from '../../thunks/toggleWidgetHighlighted';

function undoAction({action}) {
	const {highlighted, initiallyHighlighted, portletId} = action;

	return toggleWidgetHighlighted({
		highlighted,
		initiallyHighlighted,
		portletId,
	});
}

function getDerivedStateForUndo({action}) {
	const {highlighted, initiallyHighlighted, portletId} = action;

	return {
		highlighted: !highlighted,
		initiallyHighlighted: initiallyHighlighted || highlighted,
		portletId,
	};
}

export {undoAction, getDerivedStateForUndo};
