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

package com.liferay.headless.delivery.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.service.WikiNodeLocalServiceUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class WikiNodeResourceTest extends BaseWikiNodeResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		List<WikiNode> wikiNodes = WikiNodeLocalServiceUtil.getNodes(
			testGroup.getGroupId());

		for (WikiNode wikiNode : wikiNodes) {
			WikiNodeLocalServiceUtil.deleteNode(wikiNode);
		}
	}

	@Override
	@Test
	public void testPutSiteWikiNodeByExternalReferenceCode() throws Exception {

		// Update

		super.testPutSiteWikiNodeByExternalReferenceCode();

		// Add

		com.liferay.headless.delivery.client.dto.v1_0.WikiNode randomWikiNode =
			randomWikiNode();

		com.liferay.headless.delivery.client.dto.v1_0.WikiNode putWikiNode =
			wikiNodeResource.putSiteWikiNodeByExternalReferenceCode(
				randomWikiNode.getExternalReferenceCode(),
				testGroup.getGroupId(), randomWikiNode);

		assertEquals(randomWikiNode, putWikiNode);
		assertValid(putWikiNode);

		com.liferay.headless.delivery.client.dto.v1_0.WikiNode getWikiPage =
			wikiNodeResource.getSiteWikiNodeByExternalReferenceCode(
				putWikiNode.getExternalReferenceCode(), testGroup.getGroupId());

		assertEquals(randomWikiNode, getWikiPage);
		assertValid(getWikiPage);

		Assert.assertEquals(
			randomWikiNode.getExternalReferenceCode(),
			putWikiNode.getExternalReferenceCode());
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"description", "name"};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {"creatorId"};
	}

	@Override
	protected com.liferay.headless.delivery.client.dto.v1_0.WikiNode
			testGraphQLWikiNode_addWikiNode()
		throws Exception {

		return testGetWikiNode_addWikiNode();
	}

}