/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import openPortletModal from './openPortletModal';

export interface OpenPortletWindowProps {
	bodyCssClass: string;
	portlet: string;
	uri: string;
}

/**
 * A utility with API that matches Liferay.Portlet.openWindow. The purpose of
 * this utility is backwards compatibility.
 * @deprecated As of Athanasius (7.3.x), replaced by Liferay.Portlet.openModal
 */
export default function openPortletWindow({
	bodyCssClass,
	portlet,
	uri,
	...otherProps
}: OpenPortletWindowProps) {
	openPortletModal({
		iframeBodyCssClass: bodyCssClass,
		portletSelector: portlet,
		url: uri,
		...otherProps,
	});
}
