/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.cleanup.internal.upgrade;

/**
 * @author Eudaldo Alonso
 */
public class HTMLPreviewServiceUpgradeProcess extends BaseUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		removeServiceData(
			"Preview", new String[] {"com.liferay.html.preview.service"},
			new String[] {"com.liferay.html.preview.model.HtmlPreviewEntry"},
			new String[] {"HtmlPreviewEntry"});
	}

}