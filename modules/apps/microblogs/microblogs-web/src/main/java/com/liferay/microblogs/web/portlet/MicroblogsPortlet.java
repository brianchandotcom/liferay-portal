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

package com.liferay.microblogs.web.portlet;

import com.liferay.microblogs.constants.MicroblogsPortletKeys;
import com.liferay.microblogs.model.MicroblogsEntry;
import com.liferay.microblogs.service.MicroblogsEntryLocalService;
import com.liferay.microblogs.service.MicroblogsEntryService;
import com.liferay.microblogs.util.MicroblogsUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.service.AssetEntryLocalService;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=microblogs-portlet",
		"com.liferay.portlet.display-category=category.collaboration",
		"com.liferay.portlet.footer-portlet-javascript=/microblogs/js/main.js",
		"com.liferay.portlet.header-portlet-css=/microblogs/css/main.css",
		"com.liferay.portlet.icon=/microblogs/icons/microblogs.png",
		"javax.portlet.display-name=Microblogs",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.view-template=/microblogs/view.jsp",
		"javax.portlet.name=" + MicroblogsPortletKeys.MICROBLOGS,
		"javax.portlet.portlet-info.keyworkds=Microblogs",
		"javax.portlet.portlet-info.short-title=Microblogs",
		"javax.portlet.portlet-info.title=Microblogs",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator,guest,power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class MicroblogsPortlet extends MVCPortlet {

	public void deleteMicroblogsEntry(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long microblogsEntryId = ParamUtil.getLong(
			actionRequest, "microblogsEntryId");

		_microblogsEntryService.deleteMicroblogsEntry(microblogsEntryId);
	}

	public void updateMicroblogsEntry(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long microblogsEntryId = ParamUtil.getLong(
			actionRequest, "microblogsEntryId");

		String content = ParamUtil.getString(actionRequest, "content");
		int type = ParamUtil.getInteger(actionRequest, "type");
		long parentMicroblogsEntryId = ParamUtil.getLong(
			actionRequest, "parentMicroblogsEntryId");
		int socialRelationType = ParamUtil.getInteger(
			actionRequest, "socialRelationType");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			MicroblogsEntry.class.getName(), actionRequest);

		String[] assetTagNames = getAssetTagNames(content);

		serviceContext.setAssetTagNames(assetTagNames);

		if (microblogsEntryId > 0) {
			_microblogsEntryService.updateMicroblogsEntry(
				microblogsEntryId, content, socialRelationType, serviceContext);
		}
		else {
			_microblogsEntryService.addMicroblogsEntry(
				themeDisplay.getUserId(), content, type,
				parentMicroblogsEntryId, socialRelationType, serviceContext);
		}
	}

	public void updateMicroblogsEntryViewCount(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long microblogsEntryId = ParamUtil.getLong(
			actionRequest, "microblogsEntryId");

		MicroblogsEntry microblogsEntry =
			_microblogsEntryLocalService.fetchMicroblogsEntry(
				microblogsEntryId);

		if (microblogsEntry == null) {
			return;
		}

		_assetEntryLocalService.incrementViewCounter(
			0, MicroblogsEntry.class.getName(), microblogsEntryId, 1);
	}

	protected String[] getAssetTagNames(String content) {
		List<String> assetTagNames = new ArrayList<>();

		assetTagNames.addAll(MicroblogsUtil.getHashtags(content));

		assetTagNames.addAll(MicroblogsUtil.getScreenNames(content));

		return assetTagNames.toArray(new String[assetTagNames.size()]);
	}

	@Reference(unbind = "-")
	protected void setAssetEntryLocalService(
		AssetEntryLocalService assetEntryLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setMicroblogsEntryLocalService(
		MicroblogsEntryLocalService microblogsEntryLocalService) {

		_microblogsEntryLocalService = microblogsEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setMicroblogsEntryService(
		MicroblogsEntryService microblogsEntryService) {

		_microblogsEntryService = microblogsEntryService;
	}

	private volatile AssetEntryLocalService _assetEntryLocalService;
	private volatile MicroblogsEntryLocalService _microblogsEntryLocalService;
	private volatile MicroblogsEntryService _microblogsEntryService;

}