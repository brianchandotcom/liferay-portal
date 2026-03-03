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
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
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
public class AssetVocabularyERCUpgradeProcess extends UpgradeProcess {

	public AssetVocabularyERCUpgradeProcess(
		AssetVocabularyLocalService assetVocabularyLocalService,
		CompanyLocalService companyLocalService,
		GroupLocalService groupLocalService,
		PortletPreferencesLocalService portletPreferencesLocalService) {

		_assetVocabularyLocalService = assetVocabularyLocalService;
		_companyLocalService = companyLocalService;
		_groupLocalService = groupLocalService;
		_portletPreferencesLocalService = portletPreferencesLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_companyLocalService.forEachCompany(
			company -> {
				Map<String, String> idToERCMap = new HashMap<>();

				Group companyGroup = _groupLocalService.getCompanyGroup(
					company.getCompanyId());

				Group companyStagingGroup = companyGroup.getStagingGroup();

				for (String name : _reservedNames) {
					String reservedERC = _toExternalReferenceCode(name);

					AssetVocabulary assetVocabulary =
						_assetVocabularyLocalService.fetchGroupVocabulary(
							companyGroup.getGroupId(), name);

					if ((assetVocabulary != null) &&
						!reservedERC.equals(
							assetVocabulary.getExternalReferenceCode())) {

						idToERCMap.put(
							assetVocabulary.getExternalReferenceCode(),
							reservedERC);

						assetVocabulary.setExternalReferenceCode(reservedERC);

						_assetVocabularyLocalService.updateAssetVocabulary(
							assetVocabulary);
					}

					if (companyStagingGroup != null) {
						assetVocabulary =
							_assetVocabularyLocalService.fetchGroupVocabulary(
								companyStagingGroup.getGroupId(), name);

						if ((assetVocabulary != null) &&
							!reservedERC.equals(
								assetVocabulary.getExternalReferenceCode())) {

							idToERCMap.put(
								assetVocabulary.getExternalReferenceCode(),
								reservedERC);

							assetVocabulary.setExternalReferenceCode(
								reservedERC);

							_assetVocabularyLocalService.updateAssetVocabulary(
								assetVocabulary);
						}
					}
				}

				if (idToERCMap.isEmpty()) {
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
							idToERCMap, largeValue, largeValue);

						String smallValue = resultSet.getString(3);

						smallValue = MapUtil.getString(
							idToERCMap, smallValue, smallValue);

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
								idToERCMap.entrySet()) {

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

	private static final Set<String> _reservedNames = SetUtil.fromArray(
		"topic", "stage", "audience", PropsValues.ASSET_VOCABULARY_DEFAULT);

	private final AssetVocabularyLocalService _assetVocabularyLocalService;
	private final CompanyLocalService _companyLocalService;
	private final GroupLocalService _groupLocalService;
	private final PortletPreferencesLocalService
		_portletPreferencesLocalService;

}