/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.persistence.internal.upgrade.v3_0_2;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.saml.persistence.model.impl.SamlPeerBindingImpl;

/**
 * @author Stian Sigvartsen
 */
public class SamlPeerBindingUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_dropIndex(SamlPeerBindingImpl.TABLE_NAME, "IX_E642E1AE");

		_dropIndex(SamlPeerBindingImpl.TABLE_NAME, "IX_81ACF542");

		_dropIndex(SamlPeerBindingImpl.TABLE_NAME, "IX_BC82BDFC");
	}

	private void _dropIndex(String tableName, String indexName)
		throws Exception {

		if (_log.isInfoEnabled()) {
			_log.info(
				String.format(
					"Dropping index %s from table %s", indexName, tableName));
		}

		if (hasIndex(tableName, indexName)) {
			runSQL(
				StringBundler.concat(
					"drop index ", indexName, " on ", tableName));
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SamlPeerBindingUpgradeProcess.class);

}