/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {delegate, openSelectionModal} from 'frontend-js-web';

export default function ({defaultParentGroupId, namespace, portletURL}) {
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
				const {groupdescriptivename, groupid, grouptype} = event;

				parentSiteInput.value = `${groupdescriptivename} (${grouptype})`;

				primaryKeysInput.value = groupid;

				membershipContainer.classList.remove('hide');
			},
			selectEventName: `${namespace}selectGroup`,
			title: Liferay.Language.get('select-site'),
			url: portletURL,
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
