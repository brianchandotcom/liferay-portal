/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import selectLayoutDataItemLabel from '../../selectors/selectLayoutDataItemLabel';
import getFragmentItem from '../../utils/getFragmentItem';

/**
 * Obtain the name associated to the undo action,
 * for those cases where there is not name associated, returns null
 *
 * @param {object} options
 * @param {object} options.action
 * @param {object} options.state
 * @return {string|null}
 */
export function getItemNameFromAction({action, state}) {
	if (action.ruleId) {
		const rule =
			action.layoutData.pageRules.find(
				(rule) => rule.id === action.ruleId
			) ||
			state.layoutData.pageRules.find(
				(rule) => rule.id === action.ruleId
			);

		return rule.name;
	}

	const fragmentEntryLinks = action.fragmentEntryLinks
		? Object.values(action.fragmentEntryLinks).reduce(
				(acc, fragmentEntryLink) => {
					acc[fragmentEntryLink.fragmentEntryLinkId] =
						fragmentEntryLink;

					return acc;
				},
				{}
			)
		: state.fragmentEntryLinks;

	const itemId = action.triggerItemId || action.itemId || action.itemIds?.[0];

	if (action.itemIds?.length > 1) {
		return Liferay.Language.get('elements');
	}

	const item =
		state.layoutData?.items[itemId] ||
		action.layoutData?.items[itemId] ||
		getFragmentItem(state.layoutData, action.fragmentEntryLinkId) ||
		getFragmentItem(action.layoutData, action.fragmentEntryLinkId);

	if (!item) {
		return null;
	}

	return selectLayoutDataItemLabel(
		{fragmentEntryLinks, layoutData: state.layoutData},
		item
	);
}
