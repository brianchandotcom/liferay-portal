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

package com.liferay.headless.delivery.internal.dto.v1_0.util;

import com.liferay.headless.delivery.dto.v1_0.RenderedPage;
import com.liferay.headless.delivery.internal.resource.v1_0.BaseContentPageResourceImpl;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.util.JaxRsLinkUtil;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.core.UriInfo;

/**
 * @author Jürgen Kappler
 */
public class RenderedPageUtil {

	public static RenderedPage getRenderedPage(
			DTOConverterContext dtoConverterContext, Layout layout,
			LayoutPageTemplateEntryLocalService
				layoutPageTemplateEntryLocalService,
			Portal portal)
		throws PortalException {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_getLayoutPageTemplateEntry(
				layout, layoutPageTemplateEntryLocalService, portal);

		LayoutPageTemplateEntry masterLayout = _getMasterLayout(
			layout, layoutPageTemplateEntryLocalService);

		return new RenderedPage() {
			{
				renderedPageURL = _getJaxRsLink(dtoConverterContext, layout);

				setMasterPageId(
					() -> {
						if (masterLayout != null) {
							return masterLayout.getLayoutPageTemplateEntryKey();
						}

						return null;
					});

				setMasterPageName(
					() -> {
						if (masterLayout != null) {
							return masterLayout.getName();
						}

						return null;
					});

				setPageTemplateId(
					() -> {
						if (layoutPageTemplateEntry != null) {
							return layoutPageTemplateEntry.
								getLayoutPageTemplateEntryKey();
						}

						return null;
					});

				setPageTemplateName(
					() -> {
						if (layoutPageTemplateEntry != null) {
							return layoutPageTemplateEntry.getName();
						}

						return null;
					});
			}
		};
	}

	private static String _getJaxRsLink(
			DTOConverterContext dtoConverterContext, Layout layout)
		throws PortalException {

		Optional<UriInfo> uriInfoOptional =
			dtoConverterContext.getUriInfoOptional();

		UriInfo uriInfo = uriInfoOptional.get();

		if (uriInfo == null) {
			return null;
		}

		List<Object> arguments = new ArrayList<>();

		String methodName = "getSiteContentPageRenderedPage";

		String friendlyURL = layout.getFriendlyURL(
			dtoConverterContext.getLocale());

		arguments.add(layout.getGroupId());
		arguments.add(friendlyURL.substring(1));

		long segmentsExperienceId = GetterUtil.getLong(
			dtoConverterContext.getAttribute("segmentsExperienceId"));

		if (segmentsExperienceId != SegmentsEntryConstants.ID_DEFAULT) {
			methodName =
				"getSiteContentPageExperienceExperienceKeyRenderedPage";

			SegmentsExperience segmentsExperience =
				SegmentsExperienceLocalServiceUtil.getSegmentsExperience(
					segmentsExperienceId);

			arguments.add(segmentsExperience.getSegmentsExperienceKey());
		}

		return JaxRsLinkUtil.getJaxRsLink(
			"headless-delivery", BaseContentPageResourceImpl.class, methodName,
			uriInfo, arguments.toArray(new Object[0]));
	}

	private static LayoutPageTemplateEntry _getLayoutPageTemplateEntry(
		Layout layout,
		LayoutPageTemplateEntryLocalService layoutPageTemplateEntryLocalService,
		Portal portal) {

		if (layout.getClassNameId() != portal.getClassNameId(
				LayoutPageTemplateEntry.class)) {

			return null;
		}

		return layoutPageTemplateEntryLocalService.fetchLayoutPageTemplateEntry(
			layout.getClassPK());
	}

	private static LayoutPageTemplateEntry _getMasterLayout(
		Layout layout,
		LayoutPageTemplateEntryLocalService
			layoutPageTemplateEntryLocalService) {

		Layout masterLayout = LayoutLocalServiceUtil.fetchLayout(
			layout.getMasterLayoutPlid());

		if (masterLayout == null) {
			return null;
		}

		return layoutPageTemplateEntryLocalService.fetchLayoutPageTemplateEntry(
			masterLayout.getClassPK());
	}

}