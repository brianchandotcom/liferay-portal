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
import com.liferay.change.tracking.rest.client.dto.v1_0.Publication;

import org.junit.runner.RunWith;

/**
 * @author David Truong
 */
@RunWith(Arquillian.class)
public class PublicationResourceTest extends BasePublicationResourceTestCase {

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"description", "name"};
	}

	@Override
	protected Publication testDeletePublication_addPublication()
		throws Exception {

		return publicationResource.postPublication(randomPublication());
	}

	@Override
	protected Publication testGetPublication_addPublication() throws Exception {
		return publicationResource.postPublication(randomPublication());
	}

	@Override
	protected Publication testGetPublicationsPage_addPublication(
			Publication publication)
		throws Exception {

		return publicationResource.postPublication(publication);
	}

	@Override
	protected Publication testGraphQLPublication_addPublication()
		throws Exception {

		return publicationResource.postPublication(randomPublication());
	}

	@Override
	protected Publication testPatchPublication_addPublication()
		throws Exception {

		return publicationResource.postPublication(randomPublication());
	}

	@Override
	protected Publication testPostPublication_addPublication(
			Publication publication)
		throws Exception {

		return publicationResource.postPublication(publication);
	}

	@Override
	protected Publication testPostPublicationCheckout_addPublication()
		throws Exception {

		return publicationResource.postPublication(randomPublication());
	}

	@Override
	protected Publication testPostPublicationPublish_addPublication()
		throws Exception {

		return publicationResource.postPublication(randomPublication());
	}

	@Override
	protected Publication testPostPublicationSchedulePublish_addPublication()
		throws Exception {

		return publicationResource.postPublication(randomPublication());
	}

	@Override
	protected Publication testPutPublication_addPublication() throws Exception {
		return publicationResource.postPublication(randomPublication());
	}

}