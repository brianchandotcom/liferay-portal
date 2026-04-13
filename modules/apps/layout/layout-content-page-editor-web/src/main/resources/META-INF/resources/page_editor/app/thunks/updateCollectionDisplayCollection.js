/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import updateCollectionDisplayCollectionAction from '../actions/updateCollectionDisplayCollection';
import {FREEMARKER_FRAGMENT_ENTRY_PROCESSOR} from '../config/constants/freemarkerFragmentEntryProcessor';
import LayoutService from '../services/LayoutService';
import getTargetCollectionDisplayField from '../utils/getTargetCollectionDisplayField';
import {clearPageContents} from '../utils/usePageContents';

export default function updateCollectionDisplayCollection({
	collection,
	itemId,
	listStyle,
}) {
	return (dispatch, getState) => {
		const editableValuesChanges = computeEditableValuesChanges(
			getState(),
			itemId
		);

		return LayoutService.updateCollectionDisplayConfig({
			editableValuesChanges,
			itemConfig: {
				collection,
				listItemStyle: null,
				listStyle,
				paginationType: 'numeric',
				showAllItems: false,
				templateKey: null,
			},
			itemId,
			languageId: getState().languageId,
			onNetworkStatus: dispatch,
			segmentsExperienceId: getState().segmentsExperienceId,
		}).then(({fragmentEntryLinks, layoutData}) => {
			dispatch(
				updateCollectionDisplayCollectionAction({
					fragmentEntryLinks,
					itemId,
					layoutData,
				})
			);

			clearPageContents();
		});
	};
}

function computeEditableValuesChanges(state, itemId) {
	const editableValuesChanges = {};

	for (const fragmentEntryLink of Object.values(state.fragmentEntryLinks)) {
		const field = getTargetCollectionDisplayField(fragmentEntryLink);

		if (!field || !field.targetCollections.includes(itemId)) {
			continue;
		}

		const configValues =
			fragmentEntryLink.editableValues?.[
				FREEMARKER_FRAGMENT_ENTRY_PROCESSOR
			] || {};

		const newTargetCollections = field.targetCollections.filter(
			(id) => id !== itemId
		);

		const nextConfigValues = {
			...configValues,
			[field.fieldName]: newTargetCollections,
		};

		if (!newTargetCollections.length) {
			nextConfigValues.filterKey = '';
		}

		editableValuesChanges[fragmentEntryLink.fragmentEntryLinkId] = {
			...fragmentEntryLink.editableValues,
			[FREEMARKER_FRAGMENT_ENTRY_PROCESSOR]: nextConfigValues,
		};
	}

	return editableValuesChanges;
}
