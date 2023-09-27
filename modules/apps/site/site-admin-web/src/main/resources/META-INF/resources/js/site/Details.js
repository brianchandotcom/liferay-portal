/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {createPortletURL, delegate, openSelectionModal} from 'frontend-js-web';

export default function ({
	defaultParentGroupId,
	eventName,
	groupId,
	namespace,
	portletURL,
	windowState,
}) {
	const eventDelegates = [];
	const form = document.getElementById(`${namespace}fm`);
	const membershipContainer = document.getElementById(
		`${namespace}membershipRestrictionContainer`
	);
	const parentSiteInput = document.getElementById(
		`${namespace}parentSiteTitle`
	);
	const primaryKeysInput = document.getElementById(
		`${namespace}parentGroupSearchContainerPrimaryKeys`
	);

	const onChangeParentSite = () => {
		openSelectionModal({
			onSelect: (event) => {
				const {entityid, entityname, grouptype} = event;

				parentSiteInput.value = `${entityname} (${grouptype})`;

				primaryKeysInput.value = entityid;

				membershipContainer.classList.remove('hide');
			},
			selectEventName: `${namespace}selectGroup`,
			title: Liferay.Language.get('select-site'),
			url: createPortletURL(portletURL, {
				eventName,
				groupId,
				includeCurrentGroup: false,
				p_p_state: windowState,
			}).toString(),
		});
	};

	const onClearParentSite = () => {
		parentSiteInput.value = '';

		primaryKeysInput.value = defaultParentGroupId;

		membershipContainer.classList.add('hide');
	};

	const changeParentSite = delegate(
		form,
		'click',
		`#${namespace}changeParentSiteLink`,
		onChangeParentSite
	);

	eventDelegates.push(changeParentSite);

	const clearParentSite = delegate(
		form,
		'click',
		`#${namespace}clearParentSiteLink`,
		onClearParentSite
	);

	eventDelegates.push(clearParentSite);

	return {
		dispose() {
			eventDelegates.forEach((eventDelegate) => eventDelegate.dispose());
		},
	};
}
