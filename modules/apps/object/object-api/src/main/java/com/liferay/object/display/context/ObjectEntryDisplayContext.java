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

package com.liferay.object.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectLayout;
import com.liferay.object.model.ObjectLayoutBox;
import com.liferay.object.model.ObjectLayoutTab;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Map;

import javax.servlet.jsp.PageContext;

/**
 * @author Gabriel Albuquerque
 */
public interface ObjectEntryDisplayContext {

	public ObjectDefinition getObjectDefinition1();

	public ObjectDefinition getObjectDefinition2() throws PortalException;

	public ObjectEntry getObjectEntry() throws PortalException;

	public ObjectLayout getObjectLayout() throws PortalException;

	public ObjectLayoutBox getObjectLayoutBox(String type)
		throws PortalException;

	public ObjectLayoutTab getObjectLayoutTab() throws PortalException;

	public ObjectRelationship getObjectRelationship() throws PortalException;

	public String getObjectRelationshipERCObjectFieldName();

	public String getParentObjectEntryId();

	public CreationMenu getRelatedModelCreationMenu(
			ObjectRelationship objectRelationship)
		throws PortalException;

	public String getRelatedObjectEntryItemSelectorURL(
			ObjectRelationship objectRelationship)
		throws PortalException;

	public Map<String, String> getRelationshipContextParams()
		throws PortalException;

	public boolean isDefaultUser();

	public boolean isReadOnly();

	public boolean isShowObjectEntryForm() throws PortalException;

	public String renderDDMForm(PageContext pageContext) throws PortalException;

}