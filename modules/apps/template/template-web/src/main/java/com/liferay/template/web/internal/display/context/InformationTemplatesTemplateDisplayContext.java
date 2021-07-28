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

import com.liferay.dynamic.data.mapping.configuration.DDMWebConfiguration;
import com.liferay.dynamic.data.mapping.constants.DDMActionKeys;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.template.constants.TemplatePortletKeys;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author Eudaldo Alonso
 * @author Lourdes Fernández Besada
 */
public class InformationTemplatesTemplateDisplayContext
	extends BaseTemplateDisplayContext {

	public InformationTemplatesTemplateDisplayContext(
		DDMWebConfiguration ddmWebConfiguration,
		InfoItemServiceTracker infoItemServiceTracker,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		super(
			ddmWebConfiguration, liferayPortletRequest, liferayPortletResponse);

		_infoItemServiceTracker = infoItemServiceTracker;
	}

	@Override
	public String getAddPermissionActionId() {
		return DDMActionKeys.ADD_TEMPLATE;
	}

	@Override
	public String getResourceName(long classNameId) {
		return TemplatePortletKeys.TEMPLATE;
	}

	@Override
	public String getTemplateType(long classNameId) {
		return ResourceActionsUtil.getModelResource(
			themeDisplay.getLocale(), PortalUtil.getClassName(classNameId));
	}

	@Override
	protected long[] getClassNameIds() {
		if (_classNameIds != null) {
			return _classNameIds;
		}

		List<String> infoItemClassNamesList =
			_infoItemServiceTracker.getInfoItemClassNames(
				InfoItemFormProvider.class);

		Stream<String> infoItemClassNamesStream =
			infoItemClassNamesList.stream();

		_classNameIds = infoItemClassNamesStream.mapToLong(
			className -> PortalUtil.getClassNameId(className)
		).toArray();

		return _classNameIds;
	}

	@Override
	protected long getResourceClassNameId() {
		if (_resourceClassNameId != null) {
			return _resourceClassNameId;
		}

		_resourceClassNameId = PortalUtil.getClassNameId(
			InfoItemFormProvider.class);

		return _resourceClassNameId;
	}

	private long[] _classNameIds;
	private final InfoItemServiceTracker _infoItemServiceTracker;
	private Long _resourceClassNameId;

}