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

package com.liferay.amazon.rankings.action;

import com.liferay.amazon.rankings.model.AmazonRankings;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Arrays;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true, property = {
			"javax.portlet.name=com_liferay_amazon_rankings_portlet_AmazonRankingsPortlet"
	},
	service = ConfigurationAction.class
)
public class ConfigurationActionImpl extends DefaultConfigurationAction {

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String[] isbns = StringUtil.split(
			StringUtil.toUpperCase(getParameter(actionRequest, "isbns")),
			CharPool.SPACE);

		String accessKeyID = getParameter(actionRequest, AmazonRankings.AMAZON_ACCESS_KEY_ID);
		String associateTag = getParameter(actionRequest, AmazonRankings.AMAZON_ASSOCIATE_TAG);
		String secretAccessKey = getParameter(actionRequest, AmazonRankings.AMAZON_SECRET_ACCESS_KEY);

		Arrays.sort(isbns);

		setPreference(actionRequest, "isbns", isbns);
		setPreference(actionRequest, AmazonRankings.AMAZON_ACCESS_KEY_ID, accessKeyID);
		setPreference(actionRequest, AmazonRankings.AMAZON_ASSOCIATE_TAG, associateTag);
		setPreference(actionRequest, AmazonRankings.AMAZON_SECRET_ACCESS_KEY, secretAccessKey);

		super.processAction(portletConfig, actionRequest, actionResponse);
	}

}