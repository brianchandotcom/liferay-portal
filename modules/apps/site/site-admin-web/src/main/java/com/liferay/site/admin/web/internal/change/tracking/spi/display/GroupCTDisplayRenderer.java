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

package com.liferay.site.admin.web.internal.change.tracking.spi.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.portal.kernel.model.Group;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author David Truong
 */
@Component(service = CTDisplayRenderer.class)
public class GroupCTDisplayRenderer extends BaseCTDisplayRenderer<Group> {

	@Override
	public String[] getAvailableLanguageIds(Group group) {
		return group.getAvailableLanguageIds();
	}

	@Override
	public String getDefaultLanguageId(Group group) {
		return group.getDefaultLanguageId();
	}

	@Override
	public Class<Group> getModelClass() {
		return Group.class;
	}

	@Override
	public String getTitle(Locale locale, Group group) {
		return group.getName(locale);
	}

	@Override
	public boolean isHideable(Group group) {
		return group.isControlPanel();
	}

	@Override
	protected void buildDisplay(DisplayBuilder<Group> displayBuilder) {
		Group group = displayBuilder.getModel();

		displayBuilder.display(
			"name", group.getName(displayBuilder.getLocale())
		).display(
			"description", group.getDescription(displayBuilder.getLocale())
		).display(
			"friendly-url", group.getFriendlyURL()
		);
	}

}