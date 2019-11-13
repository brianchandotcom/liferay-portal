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

import com.liferay.headless.delivery.client.dto.v1_0.ContentSetElement;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class ContentSetElementResourceTest
	extends BaseContentSetElementResourceTestCase {

	@Override
	protected String testGetSiteContentSetByUuidContentSetElementsPage_getUuid()
		throws Exception {
		return "uuid";
	}

	@Override
	protected String testGetSiteContentSetByKeyContentSetElementsPage_getKey()
		throws Exception {
		return "key";
	}

	@Override
	protected Long testGetContentSetContentSetElementsPage_getContentSetId()
		throws Exception {
		return 0L;
	}

	@Override
	protected ContentSetElement testGetContentSetContentSetElementsPage_addContentSetElement(
		Long contentSetId, ContentSetElement contentSetElement)
		throws Exception {

		return new ContentSetElement();
	}

	@Override
	protected ContentSetElement testGetSiteContentSetByKeyContentSetElementsPage_addContentSetElement(
		Long siteId, String key, ContentSetElement contentSetElement)
		throws Exception {

		return new ContentSetElement();
	}

	@Override
	protected ContentSetElement testGetSiteContentSetByUuidContentSetElementsPage_addContentSetElement(
		Long siteId, String uuid, ContentSetElement contentSetElement)
		throws Exception {

		return new ContentSetElement();
	}
}