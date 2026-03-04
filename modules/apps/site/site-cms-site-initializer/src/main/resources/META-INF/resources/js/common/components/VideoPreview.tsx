/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	DLVideoExternalShortcutPreview,
	useDLVideoExternalShortcutFields,
} from 'document-library-video';
import {createResourceURL, getPortletNamespace} from 'frontend-js-web';
import React from 'react';

const PORTLET_ID =
	'com_liferay_document_library_video_internal_portlet_DLVideoPortlet';

export default function VideoPreview({previewURL}: {previewURL: string}) {
	const baseURL = new URL(
		Liferay.ThemeDisplay.getLayoutRelativeControlPanelURL(),
		Liferay.ThemeDisplay.getPortalURL()
	).toString();

	const portletNamespace = getPortletNamespace(PORTLET_ID);

	const getVideoFieldsURL = createResourceURL(baseURL, {
		p_p_id: PORTLET_ID,
		p_p_resource_id:
			'/document_library_video/get_dl_video_external_shortcut_fields',
		portletNamespace,
	}).href;

	const {error, fields, loading} = useDLVideoExternalShortcutFields({
		getDLVideoExternalShortcutFieldsURL: getVideoFieldsURL,
		namespace: portletNamespace,
		url: previewURL,
	});

	return (
		<DLVideoExternalShortcutPreview
			error={error}
			framed
			loading={loading}
			videoHTML={fields?.HTML}
		/>
	);
}
