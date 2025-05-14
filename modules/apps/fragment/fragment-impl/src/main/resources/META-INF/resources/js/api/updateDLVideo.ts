/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Fields, updateDLVideoFields, validateUrl} from 'document-library-video';
import {
	createResourceURL,
	debounce,
	getPortletNamespace,
} from 'frontend-js-web';

const PORTLET_ID =
	'com_liferay_document_library_video_internal_portlet_DLVideoPortlet';

const RESOURCE_ID =
	'/document_library_video/get_dl_video_external_shortcut_fields';

export async function updateDLVideo({
	onUpdate,
	url,
}: {
	onUpdate: (html: string | null, title?: string) => void;
	url: string;
}) {
	const baseURL =
		Liferay.ThemeDisplay.getPortalURL() +
		Liferay.ThemeDisplay.getLayoutRelativeControlPanelURL();
	const portletNamespace = getPortletNamespace(PORTLET_ID);

	const getVideoFieldsURL = createResourceURL(baseURL, {
		p_p_id: PORTLET_ID,
		p_p_resource_id: RESOURCE_ID,
		portletNamespace,
	}).href;

	const getFields = debounce((videoURL: string) => {
		updateDLVideoFields({
			getVideoFieldsURL,
			namespace: portletNamespace,
			onError: () => {
				console.error(
					Liferay.Language.get(
						'sorry,-this-platform-is-not-supported'
					)
				);
			},
			onUpdate: ({HTML: html, TITLE: title}: Fields) => {
				onUpdate(html, title);
			},
			videoURL,
		});
	}, 500);

	if (url && validateUrl(url)) {
		getFields(url);
	}
	else {
		onUpdate(null);
	}
}
