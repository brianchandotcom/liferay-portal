/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0.util;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryServiceUtil;
import com.liferay.headless.admin.site.dto.v1_0.ItemExternalReference;
import com.liferay.headless.admin.site.dto.v1_0.PageSettings;
import com.liferay.headless.admin.site.dto.v1_0.Scope;
import com.liferay.headless.admin.site.dto.v1_0.WidgetPageSettings;
import com.liferay.headless.common.spi.service.context.ServiceContextBuilder;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupServiceUtil;
import com.liferay.portal.kernel.service.LayoutPrototypeLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Date;

/**
 * @author Lourdes Fernández Besada
 */
public class ServiceContextUtil {

	public static ServiceContext createServiceContext(
			ItemExternalReference[] assetCategoriesItemExternalReferences,
			long companyId, Date createDate, long groupId,
			HttpServletRequest httpServletRequest, String[] keywords,
			Date modifiedDate, long userId, String uuid,
			PageSettings pageSettings)
		throws Exception {

		ServiceContext serviceContext = ServiceContextBuilder.create(
			groupId, httpServletRequest, null
		).build();

		serviceContext.setAssetCategoryIds(
			_getAssetCategoryIds(
				groupId, assetCategoriesItemExternalReferences));
		serviceContext.setAssetTagNames(keywords);
		serviceContext.setCompanyId(companyId);
		serviceContext.setCreateDate(createDate);
		serviceContext.setModifiedDate(modifiedDate);
		serviceContext.setUserId(userId);
		serviceContext.setUuid(uuid);

		if (pageSettings instanceof WidgetPageSettings) {
			WidgetPageSettings widgetPageSettings =
				(WidgetPageSettings)pageSettings;

			serviceContext.setAttribute(
				"layoutPrototypeLinkEnabled",
				widgetPageSettings.getInheritChanges());

			ItemExternalReference itemExternalReference =
				widgetPageSettings.getWidgetPageTemplateReference();

			if (itemExternalReference != null) {
				Scope scope = itemExternalReference.getScope();

				long scopeGroupId = groupId;

				if (scope != null) {
					Group group =
						GroupLocalServiceUtil.getGroupByExternalReferenceCode(
							scope.getExternalReferenceCode(), companyId);

					scopeGroupId = group.getGroupId();
				}

				LayoutPageTemplateEntry layoutPageTemplateEntry =
					LayoutPageTemplateEntryLocalServiceUtil.
						fetchLayoutPageTemplateEntryByExternalReferenceCode(
							itemExternalReference.getExternalReferenceCode(),
							scopeGroupId);

				if (layoutPageTemplateEntry == null) {
					throw new UnsupportedOperationException();
				}

				LayoutPrototype layoutPrototype =
					LayoutPrototypeLocalServiceUtil.fetchLayoutPrototype(
						layoutPageTemplateEntry.getLayoutPrototypeId());

				if (layoutPrototype == null) {
					throw new UnsupportedOperationException();
				}

				serviceContext.setAttribute(
					"layoutPrototypeUuid", layoutPrototype.getUuid());
			}
		}

		return serviceContext;
	}

	public static ServiceContext createServiceContext(
		long groupId, HttpServletRequest httpServletRequest, long userId) {

		ServiceContext serviceContext = ServiceContextBuilder.create(
			groupId, httpServletRequest, null
		).build();

		serviceContext.setUserId(userId);

		return serviceContext;
	}

	private static long[] _getAssetCategoryIds(
			long groupId, ItemExternalReference[] itemExternalReferences)
		throws Exception {

		if (ArrayUtil.isEmpty(itemExternalReferences)) {
			return new long[0];
		}

		Group group = GroupServiceUtil.getGroup(groupId);

		return TransformUtil.unsafeTransformToLongArray(
			ListUtil.fromArray(itemExternalReferences),
			itemExternalReference -> {
				long scopeGroupId = groupId;

				Scope scope = itemExternalReference.getScope();

				if (scope != null) {
					scopeGroupId = GroupUtil.getGroupId(
						true, true, group.getCompanyId(),
						scope.getExternalReferenceCode());
				}

				AssetCategory assetCategory =
					AssetCategoryServiceUtil.
						fetchCategoryByExternalReferenceCode(
							itemExternalReference.getExternalReferenceCode(),
							scopeGroupId);

				if (assetCategory == null) {
					throw new UnsupportedOperationException();
				}

				return assetCategory.getCategoryId();
			});
	}

}