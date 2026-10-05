/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect.persistence.internal.upgrade.v2_5_3;

import com.liferay.portal.kernel.dao.db.IndexMetadata;
import com.liferay.portal.kernel.dao.db.IndexMetadataFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.util.Collections;

/**
 * @author Jorge García Jiménez
 */
public class OpenIdConnectSessionUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		dropIndexes(
			Collections.singletonList("IX_DBE1CBFD"), "OpenIdConnectSession");

		IndexMetadata indexMetadata =
			IndexMetadataFactoryUtil.createIndexMetadata(
				false, "OpenIdConnectSession", "companyId", "issuer",
				"sessionId");

		if (!hasIndex("OpenIdConnectSession", indexMetadata.getIndexName())) {
			addIndexes(connection, Collections.singletonList(indexMetadata));
		}
	}

}