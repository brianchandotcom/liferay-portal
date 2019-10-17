/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.asset.categories.navigation.web.internal.exportimport.portlet.preferences.processor;

import com.liferay.asset.categories.navigation.constants.AssetCategoriesNavigationPortletKeys;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.portlet.preferences.processor.Capability;
import com.liferay.exportimport.portlet.preferences.processor.ExportImportPortletPreferencesProcessor;
import com.liferay.exportimport.portlet.preferences.processor.base.BaseExportImportPortletPreferencesProcessor;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + AssetCategoriesNavigationPortletKeys.ASSET_CATEGORIES_NAVIGATION,
	service = ExportImportPortletPreferencesProcessor.class
)
public class AssetCategoriesNavigationPortletPreferencesProcessor
	extends BaseExportImportPortletPreferencesProcessor {

	@Override
	public List<Capability> getExportCapabilities() {
		return ListUtil.fromArray(_exportCapability);
	}

	@Override
	public List<Capability> getImportCapabilities() {
		return ListUtil.fromArray(_importCapability);
	}

	@Override
	public PortletPreferences processExportPortletPreferences(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		try {
			return updateExportPortletPreferences(
				portletDataContext, portletPreferences,
				portletDataContext.getPortletId());
		}
		catch (Exception e) {
			throw new PortletDataException(
				"Unable to update asset categories navigation portlet " +
					"preferences during export",
				e);
		}
	}

	@Override
	public PortletPreferences processImportPortletPreferences(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		try {
			return updateImportPortletPreferences(
				portletDataContext, portletPreferences);
		}
		catch (Exception e) {
			throw new PortletDataException(
				"Unable to update asset categories navigation portlet " +
					"preferences during import",
				e);
		}
	}

	@Override
	protected String getExportPortletPreferencesValue(
			PortletDataContext portletDataContext, Portlet portlet,
			String className, long primaryKeyLong)
		throws Exception {

		String uuid = null;
		long groupId = 0;

		if (className.equals(AssetVocabulary.class.getName())) {
			AssetVocabulary assetVocabulary =
				_assetVocabularyLocalService.fetchAssetVocabulary(
					primaryKeyLong);

			if (assetVocabulary != null) {
				uuid = assetVocabulary.getUuid();
				groupId = assetVocabulary.getGroupId();

				portletDataContext.addReferenceElement(
					portlet, portletDataContext.getExportDataRootElement(),
					assetVocabulary,
					PortletDataContext.REFERENCE_TYPE_DEPENDENCY, true);
			}
		}

		if (Validator.isNull(uuid)) {
			return null;
		}

		return StringUtil.merge(new Object[] {uuid, groupId}, StringPool.POUND);
	}

	@Override
	protected Long getImportPortletPreferencesNewValue(
			PortletDataContext portletDataContext, Class<?> clazz,
			long companyGroupId, Map<Long, Long> primaryKeys,
			String portletPreferencesOldValue)
		throws Exception {

		if (Validator.isNumber(portletPreferencesOldValue)) {
			long oldPrimaryKey = GetterUtil.getLong(portletPreferencesOldValue);

			return MapUtil.getLong(primaryKeys, oldPrimaryKey, oldPrimaryKey);
		}

		String className = clazz.getName();

		String[] oldValues = StringUtil.split(
			portletPreferencesOldValue, StringPool.POUND);

		String uuid = oldValues[0];

		long groupId = portletDataContext.getScopeGroupId();

		if (oldValues.length > 1) {
			Map<Long, Long> groupIds =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					Group.class);

			groupId = MapUtil.getLong(
				groupIds, GetterUtil.getLong(oldValues[1]));
		}

		if (className.equals(AssetVocabulary.class.getName())) {
			AssetVocabulary assetVocabulary =
				_assetVocabularyLocalService.
					fetchAssetVocabularyByUuidAndGroupId(uuid, groupId);

			if (assetVocabulary != null) {
				return assetVocabulary.getVocabularyId();
			}
		}

		return null;
	}

	protected PortletPreferences updateExportPortletPreferences(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences, String portletId)
		throws Exception {

		Portlet portlet = _portletLocalService.getPortletById(
			portletDataContext.getCompanyId(), portletId);

		Enumeration<String> enu = portletPreferences.getNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			if (name.equals("assetVocabularyIds")) {
				updateExportPortletPreferencesClassPKs(
					portletDataContext, portlet, portletPreferences, name,
					AssetVocabulary.class.getName());
			}
		}

		return portletPreferences;
	}

	protected PortletPreferences updateImportPortletPreferences(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws Exception {

		Company company = _companyLocalService.getCompanyById(
			portletDataContext.getCompanyId());

		Group companyGroup = company.getGroup();

		Enumeration<String> enu = portletPreferences.getNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			if (name.equals("assetVocabularyIds")) {
				updateImportPortletPreferencesClassPKs(
					portletDataContext, portletPreferences, name,
					AssetVocabulary.class, companyGroup.getGroupId());
			}
		}

		return portletPreferences;
	}

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference(target = "(name=PortletDisplayTemplateExporter)")
	private Capability _exportCapability;

	@Reference(target = "(name=PortletDisplayTemplateImporter)")
	private Capability _importCapability;

	@Reference
	private PortletLocalService _portletLocalService;

}