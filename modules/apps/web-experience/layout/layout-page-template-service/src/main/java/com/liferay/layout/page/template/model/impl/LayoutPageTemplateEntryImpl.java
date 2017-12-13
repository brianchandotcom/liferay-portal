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

package com.liferay.layout.page.template.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.layout.page.template.model.LayoutPageTemplateFragment;
import com.liferay.layout.page.template.service.LayoutPageTemplateFragmentLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.StringBundler;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public class LayoutPageTemplateEntryImpl
	extends LayoutPageTemplateEntryBaseImpl {

	public LayoutPageTemplateEntryImpl() {
	}

	@Override
	public String getContent() throws PortalException {
		List<LayoutPageTemplateFragment> layoutPageTemplateFragments =
			LayoutPageTemplateFragmentLocalServiceUtil.
				getLayoutPageTemplateFragmentsByPageTemplate(
					getGroupId(), getLayoutPageTemplateEntryId());

		StringBundler css = new StringBundler(
			layoutPageTemplateFragments.size());
		StringBundler html = new StringBundler(
			layoutPageTemplateFragments.size());
		StringBundler js = new StringBundler(
			layoutPageTemplateFragments.size());

		for (LayoutPageTemplateFragment layoutPageTemplateFragment :
				layoutPageTemplateFragments) {

			css.append(layoutPageTemplateFragment.getCss());
			html.append(layoutPageTemplateFragment.getHtml());
			js.append(layoutPageTemplateFragment.getJs());
		}

		StringBundler sb = new StringBundler(7);

		sb.append("<html><head><style>");
		sb.append(css.toString());
		sb.append("</style><script>");
		sb.append(js.toString());
		sb.append("</script></head><body>");
		sb.append(html.toString());
		sb.append("</body></html>");

		return sb.toString();
	}

}