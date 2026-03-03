/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.document.library.internal.upgrade.v1_0_18;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.TextFormatter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Balázs Sáfrány-Kovalik
 */
public class AssetVocabularyUpgradeProcess extends UpgradeProcess {

	public AssetVocabularyUpgradeProcess(
		AssetVocabularyLocalService assetVocabularyLocalService,
		CompanyLocalService companyLocalService,
		GroupLocalService groupLocalService) {

		_assetVocabularyLocalService = assetVocabularyLocalService;
		_companyLocalService = companyLocalService;
		_groupLocalService = groupLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_companyLocalService.forEachCompany(
			company -> {
				Map<String, String> externalReferenceCodes = new HashMap<>();

				Group companyGroup = _groupLocalService.getCompanyGroup(
					company.getCompanyId());

				Group companyStagingGroup = companyGroup.getStagingGroup();

				for (String name : _reservedNames) {
					String reservedExternalReferenceCode =
						_toExternalReferenceCode(name);

					_updateAssetVocabulary(
						externalReferenceCodes, companyGroup, name,
						reservedExternalReferenceCode);

					if (companyStagingGroup == null) {
						continue;
					}

					_updateAssetVocabulary(
						externalReferenceCodes, companyStagingGroup, name,
						reservedExternalReferenceCode);
				}

				if (externalReferenceCodes.isEmpty()) {
					return;
				}

				StringBundler sb1 = new StringBundler(5);

				sb1.append("select portletPreferenceValueId, largeValue, ");
				sb1.append("smallValue from PortletPreferenceValue where ");
				sb1.append("PortletPreferenceValue.companyId = ");
				sb1.append(company.getCompanyId());
				sb1.append(" and PortletPreferenceValue.name = ?");

				StringBundler sb2 = new StringBundler(3);

				sb2.append("update PortletPreferenceValue set largeValue = ");
				sb2.append("?, smallValue = ? where portletPreferenceValueId ");
				sb2.append("= ?");

				try (PreparedStatement preparedStatement1 =
						connection.prepareStatement(sb1.toString());
					PreparedStatement preparedStatement2 =
						AutoBatchPreparedStatementUtil.concurrentAutoBatch(
							connection, sb2.toString())) {

					preparedStatement1.setString(
						1, "assetVocabularyExternalReferenceCodes_L_GLOBAL");

					ResultSet resultSet = preparedStatement1.executeQuery();

					while (resultSet.next()) {
						String largeValue = resultSet.getString(2);

						largeValue = MapUtil.getString(
							externalReferenceCodes, largeValue, largeValue);

						String smallValue = resultSet.getString(3);

						smallValue = MapUtil.getString(
							externalReferenceCodes, smallValue, smallValue);

						preparedStatement2.setString(1, largeValue);
						preparedStatement2.setString(2, smallValue);
						preparedStatement2.setLong(3, resultSet.getLong(1));

						preparedStatement2.addBatch();
					}

					preparedStatement1.setString(
						1, "groupVocabularyExternalReferenceCodes");

					resultSet = preparedStatement1.executeQuery();

					while (resultSet.next()) {
						String largeValue = resultSet.getString(2);
						String smallValue = resultSet.getString(3);

						for (Map.Entry<String, String> entry :
								externalReferenceCodes.entrySet()) {

							largeValue = StringUtil.replace(
								largeValue, "L_GLOBAL&&" + entry.getKey(),
								"L_GLOBAL&&" + entry.getValue());
							smallValue = StringUtil.replace(
								smallValue, "L_GLOBAL&&" + entry.getKey(),
								"L_GLOBAL&&" + entry.getValue());
						}

						preparedStatement2.setString(1, largeValue);
						preparedStatement2.setString(2, smallValue);
						preparedStatement2.setLong(3, resultSet.getLong(1));

						preparedStatement2.addBatch();
					}

					preparedStatement2.executeBatch();
				}
			});
	}

	private String _toExternalReferenceCode(String name) {
		return "L_" + TextFormatter.format(name, TextFormatter.A);
	}

	private void _updateAssetVocabulary(
		Map<String, String> externalReferenceCodes, Group companyGroup,
		String name, String reservedExternalReferenceCode) {

		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.fetchGroupVocabulary(
				companyGroup.getGroupId(), name);

		if ((assetVocabulary != null) &&
			!reservedExternalReferenceCode.equals(
				assetVocabulary.getExternalReferenceCode())) {

			externalReferenceCodes.put(
				assetVocabulary.getExternalReferenceCode(),
				reservedExternalReferenceCode);

			assetVocabulary.setExternalReferenceCode(
				reservedExternalReferenceCode);

			_assetVocabularyLocalService.updateAssetVocabulary(assetVocabulary);
		}
	}

	private static final Set<String> _reservedNames = SetUtil.fromArray(
		"audience", PropsValues.ASSET_VOCABULARY_DEFAULT, "stage", "topic");

	private final AssetVocabularyLocalService _assetVocabularyLocalService;
	private final CompanyLocalService _companyLocalService;
	private final GroupLocalService _groupLocalService;

}