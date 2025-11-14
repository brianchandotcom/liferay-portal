/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import changeMasterLayout from '../../../app/thunks/changeMasterLayout';
import {config} from '../../config/index';

function undoAction({action}) {
	return changeMasterLayout({
		masterLayoutPageTemplateEntryERC:
			action.masterLayoutPageTemplateEntryERC,
	});
}

function getDerivedStateForUndo({action, state}) {
	const masterLayoutExists = config.masterLayouts.some(
		(masterLayout) =>
			masterLayout.masterLayoutPageTemplateEntryERC ===
			state.masterLayout.masterLayoutPageTemplateEntryERC
	);

	if (!masterLayoutExists) {
		return null;
	}

	return {
		masterLayoutPageTemplateEntryERC:
			state.masterLayout.masterLayoutPageTemplateEntryERC,
		nextMasterLayoutPageTemplateEntryERC:
			action.masterLayoutPageTemplateEntryERC,
	};
}

export {undoAction, getDerivedStateForUndo};
