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

package com.liferay.template.test.util;

import com.liferay.dynamic.data.mapping.constants.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateServiceUtil;
import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemFormVariation;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
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
import java.util.stream.Stream;

/**
 * @author Lourdes Fernández Besada
 */
public class TemplateTestUtil {

	public static TemplateEntry addAnyTemplateEntry(
			InfoItemServiceTracker infoItemServiceTracker,
			ServiceContext serviceContext)
		throws PortalException {

		InfoItemClassDetails infoItemClassDetails =
			getFirstTemplateInfoItemClassDetails(
				infoItemServiceTracker, serviceContext.getScopeGroupId());

		InfoItemFormVariation infoItemFormVariation =
			getFirstInfoItemFormVariation(
				infoItemClassDetails, infoItemServiceTracker,
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
		InfoItemServiceTracker infoItemServiceTracker, long groupId) {

		InfoItemFormVariationsProvider<?> infoItemFormVariationsProvider =
			infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFormVariationsProvider.class,
				infoItemClassDetails.getClassName());

		if (infoItemFormVariationsProvider != null) {
			Collection<InfoItemFormVariation> infoItemFormVariations =
				infoItemFormVariationsProvider.getInfoItemFormVariations(
					groupId);

			Stream<InfoItemFormVariation> infoItemFormVariationsStream =
				infoItemFormVariations.stream();

			return infoItemFormVariationsStream.findFirst(
			).orElse(
				null
			);
		}

		return null;
	}

	public static InfoItemClassDetails getFirstTemplateInfoItemClassDetails(
		InfoItemServiceTracker infoItemServiceTracker, long groupId) {

		for (InfoItemClassDetails infoItemClassDetails :
				infoItemServiceTracker.getInfoItemClassDetails(
					TemplateInfoItemCapability.KEY)) {

			InfoItemFormVariationsProvider<?> infoItemFormVariationsProvider =
				infoItemServiceTracker.getFirstInfoItemService(
					InfoItemFormVariationsProvider.class,
					infoItemClassDetails.getClassName());

			if (infoItemFormVariationsProvider != null) {
				Collection<InfoItemFormVariation> infoItemFormVariations =
					infoItemFormVariationsProvider.getInfoItemFormVariations(
						groupId);

				Stream<InfoItemFormVariation> infoItemFormVariationsStream =
					infoItemFormVariations.stream();

				InfoItemFormVariation infoItemFormVariation =
					infoItemFormVariationsStream.findFirst(
					).orElse(
						null
					);

				if (infoItemFormVariation == null) {
					continue;
				}

				return infoItemClassDetails;
			}

			return infoItemClassDetails;
		}

		return null;
	}

}