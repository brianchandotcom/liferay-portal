/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.uad.test;

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
@Component(service = WikiNodeUADTestHelper.class)
public class WikiNodeUADTestHelper {

	public WikiNode addWikiNode(long userId) throws Exception {
		return _wikiNodeLocalService.addNode(
			userId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId()));
	}

	public WikiNode addWikiNodeWithStatusByUserId(
			long userId, long statusByUserId)
		throws Exception {

		WikiNode wikiNode = addWikiNode(userId);

		return _wikiNodeLocalService.updateStatus(
			statusByUserId, wikiNode, WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId()));
	}

	public void cleanUpDependencies(List<WikiNode> wikiNodes) throws Exception {
	}

	@Reference
	private WikiNodeLocalService _wikiNodeLocalService;

}