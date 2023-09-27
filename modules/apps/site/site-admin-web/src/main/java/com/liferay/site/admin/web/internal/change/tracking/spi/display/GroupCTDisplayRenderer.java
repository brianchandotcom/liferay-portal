/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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