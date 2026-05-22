/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@liferay/frontend-js-react-web';

import DetachedTreeItemSelectorModal, {
	TDetachedTreeItemSelectorModal,
} from './DetachedTreeItemSelectorModal';

export default function openTreeItemSelectorModal(
	props: TDetachedTreeItemSelectorModal
) {

	// Mount in detached node; Clay portals into document.body.
	// See: https://github.com/liferay/clay/blob/master/packages/clay-shared/src/Portal.tsx

	return render(
		DetachedTreeItemSelectorModal,
		props,
		document.createElement('div')
	);
}
