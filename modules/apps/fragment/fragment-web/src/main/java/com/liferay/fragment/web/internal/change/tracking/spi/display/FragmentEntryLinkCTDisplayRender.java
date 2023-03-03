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

package com.liferay.fragment.web.internal.change.tracking.spi.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.change.tracking.spi.display.context.DisplayContext;
import com.liferay.fragment.helper.FragmentEntryLinkHelper;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.DefaultFragmentRendererContext;
import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(service = CTDisplayRenderer.class)
public class FragmentEntryLinkCTDisplayRender
	extends BaseCTDisplayRenderer<FragmentEntryLink> {

	@Override
	public Class<FragmentEntryLink> getModelClass() {
		return FragmentEntryLink.class;
	}

	@Override
	public String getTitle(Locale locale, FragmentEntryLink fragmentEntryLink)
		throws PortalException {

		Layout layout = _layoutLocalService.fetchLayout(
			fragmentEntryLink.getPlid());
		String name = _fragmentEntryLinkHelper.getFragmentEntryName(
			fragmentEntryLink, locale);

		if ((layout != null) && !name.equals(StringPool.BLANK)) {
			return _language.format(
				locale, "x-for-x", new String[] {name, layout.getName(locale)});
		}

		return null;
	}

	@Override
	public boolean isHideable(FragmentEntryLink fragmentEntryLink) {
		if (fragmentEntryLink.getOriginalFragmentEntryLinkId() == 0) {
			return false;
		}

		return true;
	}

	@Override
	public String renderPreview(
			DisplayContext<FragmentEntryLink> displayContext)
		throws Exception {

		FragmentEntryLink fragmentEntryLink = displayContext.getModel();

		DefaultFragmentRendererContext defaultFragmentRendererContext =
			new DefaultFragmentRendererContext(fragmentEntryLink);

		defaultFragmentRendererContext.setLocale(displayContext.getLocale());

		return _fragmentRendererController.render(
			defaultFragmentRendererContext,
			displayContext.getHttpServletRequest(),
			displayContext.getHttpServletResponse());
	}

	@Override
	protected void buildDisplay(
		DisplayBuilder<FragmentEntryLink> displayBuilder) {

		FragmentEntryLink fragmentEntryLink = displayBuilder.getModel();

		displayBuilder.display(
			"name",
			_fragmentEntryLinkHelper.getFragmentEntryName(
				fragmentEntryLink, displayBuilder.getLocale())
		).display(
			"create-date", fragmentEntryLink.getCreateDate()
		).display(
			"modified-date", fragmentEntryLink.getModifiedDate()
		).display(
			"css", fragmentEntryLink.getCss(), true, true
		).display(
			"editable-values",
			() -> {
				JSONObject jsonObject = _jsonFactory.createJSONObject(
					fragmentEntryLink.getEditableValues());

				return jsonObject.toString(4);
			},
			true, true
		);
	}

	@Reference
	private FragmentEntryLinkHelper _fragmentEntryLinkHelper;

	@Reference
	private FragmentRendererController _fragmentRendererController;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private LayoutLocalService _layoutLocalService;

}