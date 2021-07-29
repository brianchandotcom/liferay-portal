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

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Lourdes Fernández Besada
 */
public interface TemplateDisplayContext {

	public String getAddPermissionActionId();

	public long[] getClassNameIds();

	public List<DropdownItem> getDDMTemplateActionDropdownItems(
			DDMTemplate ddmTemplate)
		throws Exception;

	public String getDDMTemplateEditURL(DDMTemplate ddmTemplate)
		throws PortalException;

	public String getDDMTemplateScope(DDMTemplate ddmTemplate)
		throws PortalException;

	public List<NavigationItem> getNavigationItems();

	public long getResourceClassNameId();

	public String getResourceName(long classNameId);

	public SearchContainer<DDMTemplate> getTemplateSearchContainer();

	public String getTemplateTypeLabel(long classNameId);

	public boolean isAddDDMTemplateEnabled();

}