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

package com.liferay.organizations.item.selector.web.internal;

import com.liferay.frontend.taglib.clay.servlet.taglib.VerticalCard;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.organizations.item.selector.web.internal.frontend.taglib.clay.servlet.taglib.OrganizationVerticalCard;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.util.HtmlUtil;

import java.util.Locale;

import javax.portlet.RenderRequest;

/**
 * @author Eudaldo Aloso
 */
public class OrganizationItemDescriptor
	implements ItemSelectorViewDescriptor.ItemDescriptor {

	public OrganizationItemDescriptor(Organization organization) {
		_organization = organization;
	}

	@Override
	public String getIcon() {
		return "organizations";
	}

	@Override
	public String getImageURL() {
		return _organization.getLogoURL();
	}

	@Override
	public String getPayload() {
		return JSONUtil.put(
			"name", _organization.getName()
		).put(
			"organizationId", String.valueOf(_organization.getOrganizationId())
		).put(
			"type", _organization.getType()
		).toString();
	}

	@Override
	public String getSubtitle(Locale locale) {
		return StringPool.BLANK;
	}

	@Override
	public String getTitle(Locale locale) {
		return HtmlUtil.escape(_organization.getName());
	}

	@Override
	public VerticalCard getVerticalCard(
		RenderRequest renderRequest, RowChecker rowChecker) {

		return new OrganizationVerticalCard(
			_organization, renderRequest, rowChecker);
	}

	private final Organization _organization;

}