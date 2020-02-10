/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {UPDATE_ROW_CONFIG} from './actions.es';
import {
	disableSavingChangesStatusAction,
	enableSavingChangesStatusAction,
	updateLastSaveDateAction
} from './saveChanges.es';
import {updatePageContentsAction} from './updatePageContents.es';
import {updatePageEditorLayoutDataAction} from './updatePageEditorLayoutData.es';

function updateRowConfigAction(rowId, configContent) {
	return function(dispatch) {
		dispatch(enableSavingChangesStatusAction());

		dispatch(updateRowConfig(rowId, configContent));

		dispatch(updatePageEditorLayoutDataAction());

		dispatch(updateLastSaveDateAction());
		dispatch(disableSavingChangesStatusAction());

		const {backgroundImage} = configContent;

		if (
			backgroundImage &&
			(backgroundImage.fieldId !== undefined ||
				backgroundImage.mappedField !== undefined)
		) {
			dispatch(updatePageContentsAction());
		}
	};
}

function updateRowConfig(rowId, configContent) {
	return {
		config: {...configContent},
		rowId,
		type: UPDATE_ROW_CONFIG
	};
}

export {updateRowConfigAction};
