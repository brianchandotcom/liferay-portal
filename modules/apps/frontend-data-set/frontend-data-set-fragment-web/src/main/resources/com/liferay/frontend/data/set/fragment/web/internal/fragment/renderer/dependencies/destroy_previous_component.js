/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function () {
	const componentIds = (Liferay._fdsFragmentComponentIds =
		Liferay._fdsFragmentComponentIds || {});

	const previousComponentId = componentIds['[$FRAGMENT_ENTRY_LINK_ID$]'];

	if (previousComponentId) {
		const previousComponent = Liferay.component(previousComponentId);

		if (previousComponent) {

			// Remove the registry entry synchronously so re-registering the
			// same ID does not warn about a duplicate, then defer the unmount
			// out of the current React render to avoid the warning "Attempted
			// to synchronously unmount a root while React was already
			// rendering".

			Liferay.component(previousComponentId, null);

			if (previousComponent.destroy) {
				requestAnimationFrame(() => previousComponent.destroy());
			}
		}
	}

	componentIds['[$FRAGMENT_ENTRY_LINK_ID$]'] = '[$COMPONENT_ID$]';
})();
