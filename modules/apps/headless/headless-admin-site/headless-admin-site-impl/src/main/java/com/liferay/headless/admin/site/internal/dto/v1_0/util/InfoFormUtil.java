/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.util;

import com.liferay.info.exception.NoSuchFormVariationException;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.InfoItemFormVariation;
import com.liferay.info.item.InfoItemServiceRegistryUtil;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.layout.util.structure.CollectionStyledLayoutStructureItem;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;

/**
 * @author Lourdes Fernández Besada
 */
public class InfoFormUtil {

	public static InfoForm getCollectionInfoForm(
		CollectionStyledLayoutStructureItem collectionStyledLayoutStructureItem,
		long scopeGroupId) {

		if (collectionStyledLayoutStructureItem == null) {
			return null;
		}

		JSONObject collectionJSONObject =
			collectionStyledLayoutStructureItem.getCollectionJSONObject();

		if (collectionJSONObject == null) {
			return null;
		}

		String itemType = collectionJSONObject.getString("itemType");

		if (itemType == null) {
			return null;
		}

		InfoItemFormProvider<Object> infoItemFormProvider =
			InfoItemServiceRegistryUtil.getFirstInfoItemService(
				InfoItemFormProvider.class, itemType);

		if (infoItemFormProvider == null) {
			return null;
		}

		return _getInfoForm(
			collectionJSONObject.getString("itemSubtype"), infoItemFormProvider,
			scopeGroupId);
	}

	public static InfoForm getDisplayPageTemplateInfoForm(long plid) {
		Layout layout = LayoutLocalServiceUtil.fetchLayout(plid);

		if (layout == null) {
			return null;
		}

		if (layout.isDraftLayout()) {
			layout = LayoutLocalServiceUtil.fetchLayout(layout.getClassPK());
		}

		if (layout == null) {
			return null;
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			LayoutPageTemplateEntryLocalServiceUtil.
				fetchLayoutPageTemplateEntryByPlid(layout.getPlid());

		if ((layoutPageTemplateEntry == null) ||
			(layoutPageTemplateEntry.getType() !=
				LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE)) {

			return null;
		}

		InfoItemFormProvider<Object> infoItemFormProvider =
			InfoItemServiceRegistryUtil.getFirstInfoItemService(
				InfoItemFormProvider.class,
				layoutPageTemplateEntry.getClassName());

		if (infoItemFormProvider == null) {
			return null;
		}

		InfoItemFormVariationsProvider<?> infoItemFormVariationsProvider =
			InfoItemServiceRegistryUtil.getFirstInfoItemService(
				InfoItemFormVariationsProvider.class,
				layoutPageTemplateEntry.getClassName());

		if (infoItemFormVariationsProvider == null) {
			return infoItemFormProvider.getInfoForm();
		}

		InfoItemFormVariation infoItemFormVariation =
			infoItemFormVariationsProvider.getInfoItemFormVariation(
				layoutPageTemplateEntry.getGroupId(),
				layoutPageTemplateEntry.getLayoutPageTemplateEntryKey(),
				String.valueOf(layoutPageTemplateEntry.getClassTypeId()));

		if (infoItemFormVariation == null) {
			return null;
		}

		return _getInfoForm(
			infoItemFormVariation.getKey(), infoItemFormProvider,
			layoutPageTemplateEntry.getGroupId());
	}

	private static InfoForm _getInfoForm(
		String formVariationKey,
		InfoItemFormProvider<Object> infoItemFormProvider, long scopeGroupId) {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(CompanyThreadLocal.getCompanyId());
		serviceContext.setScopeGroupId(scopeGroupId);

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			return infoItemFormProvider.getInfoForm(
				formVariationKey, scopeGroupId);
		}
		catch (NoSuchFormVariationException noSuchFormVariationException) {
			if (_log.isDebugEnabled()) {
				_log.debug(noSuchFormVariationException);
			}
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(InfoFormUtil.class);

}