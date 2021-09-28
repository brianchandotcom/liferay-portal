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
