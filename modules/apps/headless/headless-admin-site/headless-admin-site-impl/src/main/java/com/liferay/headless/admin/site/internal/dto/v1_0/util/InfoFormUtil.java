/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.util;

import com.liferay.info.exception.NoSuchFormVariationException;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.InfoItemServiceRegistryUtil;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.layout.util.structure.CollectionStyledLayoutStructureItem;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
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

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(CompanyThreadLocal.getCompanyId());
		serviceContext.setScopeGroupId(scopeGroupId);

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			return infoItemFormProvider.getInfoForm(
				collectionJSONObject.getString("itemSubtype"), scopeGroupId);
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