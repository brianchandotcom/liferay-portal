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
import com.liferay.change.tracking.exception.CTCollectionStatusException;
import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.rest.client.dto.v1_0.Publication;
import com.liferay.change.tracking.rest.client.dto.v1_0.Status;
import com.liferay.change.tracking.rest.client.http.HttpInvoker;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;

import java.util.Date;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author David Truong
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class PublicationResourceTest extends BasePublicationResourceTestCase {

	@Override
	@Test
	public void testPostPublicationCheckout() throws Exception {
		Publication publication = testPostPublicationCheckout_addPublication();

		assertHttpResponseStatusCode(
			Response.Status.NO_CONTENT.getStatusCode(),
			publicationResource.postPublicationCheckoutHttpResponse(
				publication.getId()));

		CTPreferences ctPreferences =
			_ctPreferencesLocalService.getCTPreferences(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId());

		Assert.assertEquals(
			ctPreferences.getCtCollectionId(), (long)publication.getId());
	}

	@Override
	@Test
	public void testPostPublicationPublish() throws Exception {
		Publication publication = testPostPublicationPublish_addPublication();

		assertHttpResponseStatusCode(
			Response.Status.NO_CONTENT.getStatusCode(),
			publicationResource.postPublicationPublishHttpResponse(
				publication.getId()));

		publication = publicationResource.getPublication(publication.getId());

		Status status = publication.getStatus();

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, (int)status.getCode());

		_assertHttpResponseProblem(
			CTCollectionStatusException.class,
			publicationResource.postPublicationPublishHttpResponse(
				publication.getId()));
	}

	@Override
	@Test
	public void testPostPublicationSchedulePublish() throws Exception {
		Publication publication =
			testPostPublicationSchedulePublish_addPublication();

		_assertHttpResponseProblem(
			IllegalArgumentException.class,
			publicationResource.postPublicationSchedulePublishHttpResponse(
				publication.getId(),
				new Date(
					System.currentTimeMillis() - RandomTestUtil.randomInt())));

		assertHttpResponseStatusCode(
			204,
			publicationResource.postPublicationSchedulePublishHttpResponse(
				publication.getId(),
				new Date(
					System.currentTimeMillis() + RandomTestUtil.randomInt())));

		publication = publicationResource.getPublication(publication.getId());

		Status status = publication.getStatus();

		Assert.assertEquals(
			WorkflowConstants.STATUS_SCHEDULED, (int)status.getCode());
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"description", "name"};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {
			"dateScheduled", "description", "ownerName", "status"
		};
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

	private void _assertHttpResponseProblem(
			Class<?> exceptionClass, HttpInvoker.HttpResponse httpResponse)
		throws Exception {

		Assert.assertEquals(
			Response.Status.BAD_REQUEST.getStatusCode(),
			httpResponse.getStatusCode());

		if (exceptionClass != null) {
			JSONObject jsonObject = _jsonFactory.createJSONObject(
				httpResponse.getContent());

			Assert.assertEquals(
				exceptionClass.getSimpleName(), jsonObject.get("type"));
		}
	}

	@Inject
	private CTPreferencesLocalService _ctPreferencesLocalService;

	@Inject
	private JSONFactory _jsonFactory;

}