/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.internal.upgrade.v1_3_3;

import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.search.experiences.internal.model.listener.CompanyModelListener;
import com.liferay.search.experiences.rest.dto.v1_0.SXPElement;
import com.liferay.search.experiences.rest.dto.v1_0.util.SXPElementUtil;

import java.io.IOException;

import java.net.URL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Felipe Lorenz
 */
public class SXPBlueprintUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select externalReferenceCode, readOnly from SXPElement");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update SXPElement set elementDefinitionJSON = ? where " +
						"externalReferenceCode = ?")) {

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					if (!resultSet.getBoolean("readOnly")) {
						continue;
					}

					Map<String, SXPElement> sxpElements = _getSXPElements();

					SXPElement sxpElement = sxpElements.get(
						resultSet.getString("externalReferenceCode"));

					if (sxpElement == null) {
						continue;
					}

					preparedStatement2.setString(
						1, String.valueOf(sxpElement.getElementDefinition()));
					preparedStatement2.setString(
						2, resultSet.getString("externalReferenceCode"));

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

	private Map<String, SXPElement> _getSXPElements() {
		if (_sxpElements != null) {
			return _sxpElements;
		}

		Bundle bundle = FrameworkUtil.getBundle(CompanyModelListener.class);

		Package pkg = CompanyModelListener.class.getPackage();

		String path = StringUtil.replace(
			pkg.getName(), CharPool.PERIOD, CharPool.SLASH);

		_sxpElements = new HashMap<>();

		Enumeration<URL> enumeration = bundle.findEntries(
			path.concat("/dependencies"), "*.json", false);

		try {
			while (enumeration.hasMoreElements()) {
				URL url = enumeration.nextElement();

				SXPElement sxpElement = SXPElementUtil.toSXPElement(
					StreamUtil.toString(url.openStream()));

				_sxpElements.put(
					sxpElement.getExternalReferenceCode(), sxpElement);
			}
		}
		catch (IOException ioException) {
			_log.error(ioException);
		}

		return _sxpElements;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SXPBlueprintUpgradeProcess.class);

	private Map<String, SXPElement> _sxpElements;

}