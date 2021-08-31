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

package com.liferay.template.web.internal.display.context;

import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemDetailsProvider;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Eudaldo Alonso
 * @author Lourdes Fernández Besada
 */
public class InformationTemplatesTemplateDisplayContext
	extends BaseTemplateDisplayContext {

	public InformationTemplatesTemplateDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		super(liferayPortletRequest, liferayPortletResponse);

		_infoItemServiceTracker =
			(InfoItemServiceTracker)liferayPortletRequest.getAttribute(
				InfoItemServiceTracker.class.getName());
	}

	@Override
	public long[] getClassNameIds() {
		if (_classNameIds != null) {
			return _classNameIds;
		}

		List<String> infoItemClassNames =
			_infoItemServiceTracker.getInfoItemClassNames(
				InfoItemFormProvider.class);

		Stream<String> infoItemClassNamesStream = infoItemClassNames.stream();

		_classNameIds = infoItemClassNamesStream.mapToLong(
			PortalUtil::getClassNameId
		).toArray();

		return _classNameIds;
	}

	@Override
	public long getResourceClassNameId() {
		if (_resourceClassNameId != null) {
			return _resourceClassNameId;
		}

		_resourceClassNameId = PortalUtil.getClassNameId(
			InfoItemFormProvider.class);

		return _resourceClassNameId;
	}

	public String getTemplateSubtypeLabel(long classNameId, long classPK) {
		return Optional.ofNullable(
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFormVariationsProvider.class,
				PortalUtil.getClassName(classNameId))
		).map(
			infoItemFormVariationsProvider ->
				infoItemFormVariationsProvider.getInfoItemFormVariation(
					themeDisplay.getScopeGroupId(), String.valueOf(classPK))
		).map(
			infoItemFormVariation -> infoItemFormVariation.getLabel(
				themeDisplay.getLocale())
		).orElse(
			StringPool.BLANK
		);
	}

	@Override
	public String getTemplateTypeLabel(long classNameId) {
		return Optional.ofNullable(
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemDetailsProvider.class,
				PortalUtil.getClassName(classNameId))
		).map(
			InfoItemDetailsProvider::getInfoItemClassDetails
		).map(
			infoItemDetails -> infoItemDetails.getLabel(
				themeDisplay.getLocale())
		).orElse(
			ResourceActionsUtil.getModelResource(
				themeDisplay.getLocale(), PortalUtil.getClassName(classNameId))
		);
	}

	private long[] _classNameIds;
	private final InfoItemServiceTracker _infoItemServiceTracker;
	private Long _resourceClassNameId;

}