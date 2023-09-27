/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.template.test.util;

import com.liferay.dynamic.data.mapping.constants.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateServiceUtil;
import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemFormVariation;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.template.info.item.capability.TemplateInfoItemCapability;
import com.liferay.template.model.TemplateEntry;
import com.liferay.template.service.TemplateEntryLocalServiceUtil;

import java.util.Collection;

/**
 * @author Lourdes Fernández Besada
 */
public class TemplateTestUtil {

	public static TemplateEntry addAnyTemplateEntry(
			InfoItemServiceRegistry infoItemServiceRegistry,
			ServiceContext serviceContext)
		throws PortalException {

		InfoItemClassDetails infoItemClassDetails =
			getFirstTemplateInfoItemClassDetails(
				infoItemServiceRegistry, serviceContext.getScopeGroupId());

		InfoItemFormVariation infoItemFormVariation =
			getFirstInfoItemFormVariation(
				infoItemClassDetails, infoItemServiceRegistry,
				serviceContext.getScopeGroupId());

		return addTemplateEntry(
			infoItemClassDetails.getClassName(), infoItemFormVariation.getKey(),
			serviceContext);
	}

	public static DDMTemplate addDDMTemplate(
			long classNameId, long classPK, long resourceClassNameId,
			ServiceContext serviceContext)
		throws PortalException {

		return addDDMTemplate(
			classNameId, classPK, resourceClassNameId,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), serviceContext);
	}

	public static DDMTemplate addDDMTemplate(
			long classNameId, long classPK, long resourceClassNameId,
			String name, String description, String script,
			ServiceContext serviceContext)
		throws PortalException {

		return DDMTemplateServiceUtil.addTemplate(
			serviceContext.getScopeGroupId(), classNameId, classPK,
			resourceClassNameId,
			HashMapBuilder.put(
				PortalUtil.getSiteDefaultLocale(
					serviceContext.getScopeGroupId()),
				name
			).build(),
			HashMapBuilder.put(
				PortalUtil.getSiteDefaultLocale(
					serviceContext.getScopeGroupId()),
				description
			).build(),
			DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY, StringPool.BLANK,
			TemplateConstants.LANG_TYPE_FTL, script, serviceContext);
	}

	public static TemplateEntry addTemplateEntry(
			String infoItemClassName, String infoItemFormVariationKey,
			ServiceContext serviceContext)
		throws PortalException {

		DDMTemplate ddmTemplate = addDDMTemplate(
			PortalUtil.getClassNameId(TemplateEntry.class), 0,
			PortalUtil.getClassNameId(TemplateEntry.class), serviceContext);

		return TemplateEntryLocalServiceUtil.addTemplateEntry(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			ddmTemplate.getTemplateId(), infoItemClassName,
			infoItemFormVariationKey, serviceContext);
	}

	public static TemplateEntry addTemplateEntry(
			String infoItemClassName, String infoItemFormVariationKey,
			String name, String description, String script,
			ServiceContext serviceContext)
		throws PortalException {

		DDMTemplate ddmTemplate = addDDMTemplate(
			PortalUtil.getClassNameId(TemplateEntry.class), 0,
			PortalUtil.getClassNameId(TemplateEntry.class), name, description,
			script, serviceContext);

		return TemplateEntryLocalServiceUtil.addTemplateEntry(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			ddmTemplate.getTemplateId(), infoItemClassName,
			infoItemFormVariationKey, serviceContext);
	}

	public static InfoItemFormVariation getFirstInfoItemFormVariation(
		InfoItemClassDetails infoItemClassDetails,
		InfoItemServiceRegistry infoItemServiceRegistry, long groupId) {

		InfoItemFormVariationsProvider<?> infoItemFormVariationsProvider =
			infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemFormVariationsProvider.class,
				infoItemClassDetails.getClassName());

		if (infoItemFormVariationsProvider != null) {
			Collection<InfoItemFormVariation> infoItemFormVariations =
				infoItemFormVariationsProvider.getInfoItemFormVariations(
					groupId);

			for (InfoItemFormVariation infoItemFormVariation :
					infoItemFormVariations) {

				if (infoItemFormVariation == null) {
					continue;
				}

				return infoItemFormVariation;
			}
		}

		return null;
	}

	public static InfoItemClassDetails getFirstTemplateInfoItemClassDetails(
		InfoItemServiceRegistry infoItemServiceRegistry, long groupId) {

		for (InfoItemClassDetails infoItemClassDetails :
				infoItemServiceRegistry.getInfoItemClassDetails(
					TemplateInfoItemCapability.KEY)) {

			InfoItemFormVariationsProvider<?> infoItemFormVariationsProvider =
				infoItemServiceRegistry.getFirstInfoItemService(
					InfoItemFormVariationsProvider.class,
					infoItemClassDetails.getClassName());

			if (infoItemFormVariationsProvider == null) {
				continue;
			}

			Collection<InfoItemFormVariation> infoItemFormVariations =
				infoItemFormVariationsProvider.getInfoItemFormVariations(
					groupId);

			for (InfoItemFormVariation curInfoItemFormVariation :
					infoItemFormVariations) {

				if (curInfoItemFormVariation == null) {
					continue;
				}

				return infoItemClassDetails;
			}
		}

		return null;
	}

	public static String getRepeatableFieldSampleScriptFTL(String fieldName) {
		return StringBundler.concat(
			"<#if ", fieldName, ".getSiblings()?has_content><#list ", fieldName,
			".getSiblings() as cur_item><#if (cur_item.getData())??>",
			"${cur_item.getData()},</#if></#list></#if>");
	}

	public static String getSampleScriptFTL(String fieldName) {
		return StringBundler.concat(
			"<#if (", fieldName, ".getData())??>${", fieldName,
			".getData()}</#if>");
	}

}