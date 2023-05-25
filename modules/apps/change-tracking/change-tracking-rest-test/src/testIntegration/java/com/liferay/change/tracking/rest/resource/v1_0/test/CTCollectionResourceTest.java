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

package com.liferay.change.tracking.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.rest.client.dto.v1_0.CTCollection;

import org.junit.runner.RunWith;

/**
 * @author David Truong
 */
@RunWith(Arquillian.class)
public class CTCollectionResourceTest extends BaseCTCollectionResourceTestCase {

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"description", "name"};
	}

	@Override
	protected CTCollection testDeleteCTCollection_addCTCollection()
		throws Exception {

		return ctCollectionResource.postCTCollection(randomCTCollection());
	}

	@Override
	protected CTCollection testGetCTCollection_addCTCollection()
		throws Exception {

		return ctCollectionResource.postCTCollection(randomCTCollection());
	}

	@Override
	protected CTCollection testGetCTCollectionsPage_addCTCollection(
			CTCollection ctCollection)
		throws Exception {

		return ctCollectionResource.postCTCollection(ctCollection);
	}

	@Override
	protected CTCollection testGraphQLCTCollection_addCTCollection()
		throws Exception {

		return ctCollectionResource.postCTCollection(randomCTCollection());
	}

	@Override
	protected CTCollection testPatchCTCollection_addCTCollection()
		throws Exception {

		return ctCollectionResource.postCTCollection(randomCTCollection());
	}

	@Override
	protected CTCollection testPostCTCollection_addCTCollection(
			CTCollection ctCollection)
		throws Exception {

		return ctCollectionResource.postCTCollection(ctCollection);
	}

	@Override
	protected CTCollection testPostCTCollectionCheckout_addCTCollection()
		throws Exception {

		return ctCollectionResource.postCTCollection(randomCTCollection());
	}

	@Override
	protected CTCollection testPostCTCollectionPublish_addCTCollection()
		throws Exception {

		return ctCollectionResource.postCTCollection(randomCTCollection());
	}

	@Override
	protected CTCollection testPostCTCollectionSchedulePublish_addCTCollection()
		throws Exception {

		return ctCollectionResource.postCTCollection(randomCTCollection());
	}

	@Override
	protected CTCollection testPutCTCollection_addCTCollection()
		throws Exception {

		return ctCollectionResource.postCTCollection(randomCTCollection());
	}

}