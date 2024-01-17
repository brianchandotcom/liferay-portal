/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import updateFragmentEntryLinkConfiguration from '../../actions/updateFragmentEntryLinkConfiguration';
import FragmentService from '../../services/FragmentService';
import {clearPageContents} from '../../utils/usePageContents';

function undoAction({action}) {
	const {editableValues, fragmentEntryLinkId} = action;

	return (dispatch, getState) => {
		const {languageId, segmentsExperienceId} = getState();

		return FragmentService.updateConfigurationValues({
			editableValues,
			fragmentEntryLinkId,
			languageId,
			onNetworkStatus: dispatch,
			segmentsExperienceId,
		}).then(({fragmentEntryLink, layoutData}) => {
			dispatch(
				updateFragmentEntryLinkConfiguration({
					fragmentEntryLink,
					fragmentEntryLinkId,
					layoutData,
				})
			);

			clearPageContents();
		});
	};
}

function getDerivedStateForUndo({action, state}) {
	const {fragmentEntryLink} = action;

	const previousFragmentEntryLink =
		state.fragmentEntryLinks[fragmentEntryLink.fragmentEntryLinkId];

	return {
		editableValues: previousFragmentEntryLink.editableValues,
		fragmentEntryLinkId: fragmentEntryLink.fragmentEntryLinkId,
	};
}

export {undoAction, getDerivedStateForUndo};
