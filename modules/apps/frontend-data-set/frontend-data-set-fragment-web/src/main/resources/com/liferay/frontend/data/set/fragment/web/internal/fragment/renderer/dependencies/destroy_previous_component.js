/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function () {
	const componentIds = (Liferay._fdsFragmentComponentIds =
		Liferay._fdsFragmentComponentIds || {});

	const previousComponentId = componentIds['[$FRAGMENT_ENTRY_LINK_ID$]'];

	if (previousComponentId) {

		// The page editor re-renders the fragment by replacing its HTML, which
		// detaches the previously mounted React container before this script
		// runs. Unmounting a root whose container is already detached makes
		// React operate on stale nodes and throw, corrupting the markup the
		// page editor is still injecting. Drop the registry reference instead,
		// which releases the component and lets the detached tree be garbage
		// collected without touching the DOM.

		Liferay.component(previousComponentId, null);
	}

	componentIds['[$FRAGMENT_ENTRY_LINK_ID$]'] = '[$COMPONENT_ID$]';
})();
