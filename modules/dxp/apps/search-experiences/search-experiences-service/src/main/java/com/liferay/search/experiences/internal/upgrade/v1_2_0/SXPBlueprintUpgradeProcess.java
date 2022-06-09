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
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
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
public class SXPBlueprintUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (hasColumn("SXPBlueprint", "key_")) {
			alterTableDropColumn("SXPBlueprint", "key_");
		}

		if (!hasColumn("SXPBlueprint", "externalReferenceCode")) {
			alterTableAddColumn(
				"SXPBlueprint", "externalReferenceCode", "VARCHAR(75)");
		}

		List<SXPElement> sxpElements = new ArrayList<>();

		StringBundler sb = new StringBundler(3);

		sb.append("select SXPElement.externalReferenceCode, ");
		sb.append("SXPElement.sxpElementId, SXPElement.version from ");
		sb.append("SXPElement");

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sb.toString());
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				SXPElementImpl sxpElementImpl = new SXPElementImpl();

				sxpElementImpl.setExternalReferenceCode(resultSet.getString(1));
				sxpElementImpl.setSXPElementId(resultSet.getLong(2));
				sxpElementImpl.setVersion(resultSet.getString(3));

				sxpElements.add(sxpElementImpl);
			}
		}

		sb = new StringBundler(4);

		sb.append("select SXPBlueprint.elementInstancesJSON, ");
		sb.append("SXPBlueprint.externalReferenceCode, ");
		sb.append("SXPBlueprint.sxpBlueprintId, SXPBlueprint.version from ");
		sb.append("SXPBlueprint");

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				sb.toString());
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update SXPBlueprint set elementInstancesJSON = ?, " +
						"externalReferenceCode = ?, version = ? where " +
							"sxpBlueprintId = ?")) {

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					String externalReferenceCode = resultSet.getString(2);

					if (Validator.isNull(externalReferenceCode)) {
						continue;
					}

					long sxpBlueprintId = resultSet.getLong(3);

					String version = resultSet.getString(4);

					if (Validator.isNull(version)) {
						version = "1.0";
					}

					preparedStatement2.setString(
						1,
						_getElementInstancesJSON(
							resultSet.getString(1), sxpElements));
					preparedStatement2.setString(2, externalReferenceCode);
					preparedStatement2.setString(3, version);

					preparedStatement2.setLong(4, sxpBlueprintId);

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
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