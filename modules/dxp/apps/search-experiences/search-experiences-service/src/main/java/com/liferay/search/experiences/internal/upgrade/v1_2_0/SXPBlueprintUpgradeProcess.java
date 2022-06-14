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

package com.liferay.search.experiences.internal.upgrade.v1_2_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.BaseExternalReferenceCodeUpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.search.experiences.model.SXPElement;
import com.liferay.search.experiences.model.impl.SXPElementImpl;
import com.liferay.search.experiences.rest.dto.v1_0.ElementInstance;
import com.liferay.search.experiences.rest.dto.v1_0.util.ElementInstanceUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Wade Cao
 */
public class SXPBlueprintUpgradeProcess
	extends BaseExternalReferenceCodeUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (hasColumn("SXPBlueprint", "key_")) {
			alterTableDropColumn("SXPBlueprint", "key_");
		}

		super.doUpgrade();

		List<SXPElement> sxpElements = new ArrayList<>();

		StringBundler sb = new StringBundler(2);

		sb.append("select externalReferenceCode, sxpElementId, version from ");
		sb.append("SXPElement");

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sb.toString());
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				SXPElementImpl sxpElementImpl = new SXPElementImpl();

				sxpElementImpl.setExternalReferenceCode(
					resultSet.getString("externalReferenceCode"));
				sxpElementImpl.setSXPElementId(
					resultSet.getLong("sxpElementId"));
				sxpElementImpl.setVersion(resultSet.getString("version"));

				sxpElements.add(sxpElementImpl);
			}
		}

		sb = new StringBundler(2);

		sb.append("select elementInstancesJSON, sxpBlueprintId, version from ");
		sb.append("SXPBlueprint");

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				sb.toString());
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update SXPBlueprint set elementInstancesJSON = ?, " +
						"version = ? where sxpBlueprintId = ?")) {

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					String version = resultSet.getString("version");

					if (Validator.isNull(version)) {
						version = "1.0";
					}

					preparedStatement2.setString(
						1,
						_getElementInstancesJSON(
							resultSet.getString("elementInstancesJSON"),
							sxpElements));
					preparedStatement2.setString(2, version);

					preparedStatement2.setLong(
						3, resultSet.getLong("sxpBlueprintId"));

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

	@Override
	protected String[][] getTableAndPrimaryKeyColumnNames() {
		return new String[][] {{"SXPBlueprint", "sxpBlueprintId"}};
	}

	private String _getElementInstancesJSON(
		String elementInstancesJSON, List<SXPElement> sxpElements) {

		ElementInstance[] elementInstances =
			ElementInstanceUtil.toElementInstances(elementInstancesJSON);

		if (ArrayUtil.isEmpty(elementInstances)) {
			return StringPool.BLANK;
		}

		for (ElementInstance elementInstance : elementInstances) {
			SXPElement sxpElement = null;

			com.liferay.search.experiences.rest.dto.v1_0.SXPElement
				sxpElementDTO = elementInstance.getSxpElement();

			for (SXPElement currSXPElement : sxpElements) {
				if (Objects.equals(
						currSXPElement.getSXPElementId(),
						sxpElementDTO.getId())) {

					sxpElement = currSXPElement;

					break;
				}
			}

			if (sxpElement == null) {
				continue;
			}

			sxpElementDTO.setExternalReferenceCode(
				sxpElement.getExternalReferenceCode());
			sxpElementDTO.setVersion(sxpElement.getVersion());
		}

		return Arrays.toString(elementInstances);
	}

}