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

package com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0.converter;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.headless.delivery.dto.v1_0.DisplayPageTemplate;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

/**
 * @author Rubén Pulido
 */
public class DisplayPageTemplateDTOConverter {

	public static DisplayPageTemplate toDTO(
		LayoutPageTemplateEntry layoutPageTemplateEntry) {

		return new DisplayPageTemplate() {
			{
				contentSubtypeName = _getClassTypeName(layoutPageTemplateEntry);
				contentTypeClassName = layoutPageTemplateEntry.getClassName();
				name = layoutPageTemplateEntry.getName();
			}
		};
	}

	private static String _getClassTypeName(
		LayoutPageTemplateEntry layoutPageTemplateEntry) {

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				layoutPageTemplateEntry.getClassName());

		if ((assetRendererFactory == null) ||
			!assetRendererFactory.isSupportsClassTypes()) {

			return null;
		}

		ClassTypeReader classTypeReader =
			assetRendererFactory.getClassTypeReader();

		ClassType classType = null;

		try {
			classType = classTypeReader.getClassType(
				layoutPageTemplateEntry.getClassTypeId(),
				LocaleUtil.getSiteDefault());
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get class type", portalException);
			}
		}

		if (classType == null) {
			return null;
		}

		return classType.getName();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DisplayPageTemplateDTOConverter.class);

}