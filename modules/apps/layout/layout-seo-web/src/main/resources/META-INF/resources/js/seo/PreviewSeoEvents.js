/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const previewSeoFireChange = (portletNamespace, data) =>
	Liferay.fire(`${portletNamespace}PreviewSeo:changed`, {data});

const previewSeoOnChange = (portletNamespace, callback) =>
	Liferay.on(`${portletNamespace}PreviewSeo:changed`, (event) => {
		callback(event.data);
	});

export {previewSeoFireChange, previewSeoOnChange};
