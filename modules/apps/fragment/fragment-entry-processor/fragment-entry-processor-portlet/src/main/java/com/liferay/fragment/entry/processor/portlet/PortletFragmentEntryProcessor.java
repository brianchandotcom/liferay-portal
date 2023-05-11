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

package com.liferay.fragment.entry.processor.portlet;

import com.liferay.fragment.constants.FragmentWebKeys;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.FragmentEntryProcessor;
import com.liferay.fragment.processor.FragmentEntryProcessorContext;
import com.liferay.fragment.renderer.FragmentPortletRenderer;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.impl.DefaultLayoutTypeAccessPolicyImpl;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	property = "fragment.entry.processor.priority:Integer=3",
	service = FragmentEntryProcessor.class
)
public class PortletFragmentEntryProcessor implements FragmentEntryProcessor {

	@Override
	public String processFragmentEntryLinkHTML(
			FragmentEntryLink fragmentEntryLink, String html,
			FragmentEntryProcessorContext fragmentEntryProcessorContext)
		throws PortalException {

		HttpServletRequest httpServletRequest =
			fragmentEntryProcessorContext.getHttpServletRequest();

		if (httpServletRequest != null) {
			httpServletRequest.setAttribute(
				FragmentWebKeys.FRAGMENT_ENTRY_LINK, fragmentEntryLink);
		}

		if (fragmentEntryLink.isTypePortlet()) {
			return _renderWidgetHTML(
				fragmentEntryLink.getEditableValues(),
				fragmentEntryProcessorContext);
		}

		return html;
	}

	private String _renderWidgetHTML(
			String editableValues,
			FragmentEntryProcessorContext fragmentEntryProcessorContext)
		throws PortalException {

		JSONObject jsonObject = _jsonFactory.createJSONObject(editableValues);

		String portletId = jsonObject.getString("portletId");

		if (Validator.isNull(portletId)) {
			return StringPool.BLANK;
		}

		String instanceId = jsonObject.getString("instanceId");

		String encodedPortletId = PortletIdCodec.encode(portletId, instanceId);

		String html = _fragmentPortletRenderer.renderPortlet(
			fragmentEntryProcessorContext.getHttpServletRequest(),
			fragmentEntryProcessorContext.getHttpServletResponse(), portletId,
			instanceId,
			PortletPreferencesFactoryUtil.toXML(
				PortletPreferencesFactoryUtil.getPortletPreferences(
					fragmentEntryProcessorContext.getHttpServletRequest(),
					encodedPortletId)));

		HttpServletRequest httpServletRequest =
			fragmentEntryProcessorContext.getHttpServletRequest();

		FragmentEntryLink fragmentEntryLink =
			(FragmentEntryLink)httpServletRequest.getAttribute(
				FragmentWebKeys.FRAGMENT_ENTRY_LINK);

		if (fragmentEntryLink != null) {
			String checkAccessAllowedToPortletCacheKey = StringBundler.concat(
				"LIFERAY_SHARED_",
				DefaultLayoutTypeAccessPolicyImpl.class.getName(), "#",
				ParamUtil.getLong(
					fragmentEntryProcessorContext.getHttpServletRequest(),
					"p_l_id"),
				"#", encodedPortletId);

			httpServletRequest.setAttribute(
				FragmentWebKeys.ACCESS_ALLOWED_TO_FRAGMENT_ENTRY_LINK_ID +
					fragmentEntryLink.getFragmentEntryLinkId(),
				GetterUtil.getBoolean(
					httpServletRequest.getAttribute(
						checkAccessAllowedToPortletCacheKey),
					true));
		}

		return html;
	}

	@Reference
	private FragmentPortletRenderer _fragmentPortletRenderer;

	@Reference
	private JSONFactory _jsonFactory;

}