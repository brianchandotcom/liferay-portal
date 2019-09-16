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

package com.liferay.wiki.uad.test;

import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.service.WikiNodeLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author William Newbury
 */
@Component(immediate = true, service = WikiNodeUADTestHelper.class)
public class WikiNodeUADTestHelper {

	public WikiNode addWikiNode(long userId) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId());

		return _wikiNodeLocalService.addNode(
			userId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), serviceContext);
	}

	public WikiNode addWikiNodeWithStatusByUserId(
			long userId, long statusByUserId)
		throws Exception {

		WikiNode wikiNode = addWikiNode(userId);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId());

		return _wikiNodeLocalService.updateStatus(
			statusByUserId, wikiNode, WorkflowConstants.STATUS_APPROVED,
			serviceContext);
	}

	public void cleanUpDependencies(List<WikiNode> wikiNodes) throws Exception {
	}

	@Reference
	private WikiNodeLocalService _wikiNodeLocalService;

}