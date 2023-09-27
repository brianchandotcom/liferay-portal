/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.exception.CTCollectionStatusException;
import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.rest.client.dto.v1_0.CTCollection;
import com.liferay.change.tracking.rest.client.dto.v1_0.Status;
import com.liferay.change.tracking.rest.client.http.HttpInvoker;
import com.liferay.change.tracking.service.CTCollectionLocalService;
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
public class CTCollectionResourceTest extends BaseCTCollectionResourceTestCase {

	@Override
	@Test
	public void testPostCTCollectionCheckout() throws Exception {
		CTCollection ctCollection =
			testPostCTCollectionCheckout_addCTCollection();

		assertHttpResponseStatusCode(
			Response.Status.NO_CONTENT.getStatusCode(),
			ctCollectionResource.postCTCollectionCheckoutHttpResponse(
				ctCollection.getId()));

		CTPreferences ctPreferences =
			_ctPreferencesLocalService.getCTPreferences(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId());

		Assert.assertEquals(
			ctPreferences.getCtCollectionId(), (long)ctCollection.getId());
	}

	@Override
	@Test
	public void testPostCTCollectionPublish() throws Exception {
		CTCollection ctCollection =
			testPostCTCollectionPublish_addCTCollection();

		assertHttpResponseStatusCode(
			Response.Status.NO_CONTENT.getStatusCode(),
			ctCollectionResource.postCTCollectionPublishHttpResponse(
				ctCollection.getId()));

		ctCollection = ctCollectionResource.getCTCollection(
			ctCollection.getId());

		Status status = ctCollection.getStatus();

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, (int)status.getCode());

		_assertHttpResponseProblem(
			CTCollectionStatusException.class,
			ctCollectionResource.postCTCollectionPublishHttpResponse(
				ctCollection.getId()));
	}

	@Override
	@Test
	public void testPostCTCollectionSchedulePublish() throws Exception {
		CTCollection ctCollection =
			testPostCTCollectionSchedulePublish_addCTCollection();

		_assertHttpResponseProblem(
			IllegalArgumentException.class,
			ctCollectionResource.postCTCollectionSchedulePublishHttpResponse(
				ctCollection.getId(),
				new Date(
					System.currentTimeMillis() - RandomTestUtil.randomInt())));

		assertHttpResponseStatusCode(
			Response.Status.NO_CONTENT.getStatusCode(),
			ctCollectionResource.postCTCollectionSchedulePublishHttpResponse(
				ctCollection.getId(),
				new Date(
					System.currentTimeMillis() + RandomTestUtil.randomInt())));

		ctCollection = ctCollectionResource.getCTCollection(
			ctCollection.getId());

		Status status = ctCollection.getStatus();

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
	protected CTCollection testDeleteCTCollection_addCTCollection()
		throws Exception {

		return ctCollectionResource.postCTCollection(randomCTCollection());
	}

	@Override
	protected CTCollection
			testDeleteCTCollectionByExternalReferenceCode_addCTCollection()
		throws Exception {

		return ctCollectionResource.postCTCollection(randomCTCollection());
	}

	@Override
	protected CTCollection testGetCTCollection_addCTCollection()
		throws Exception {

		return ctCollectionResource.postCTCollection(randomCTCollection());
	}

	@Override
	protected CTCollection
			testGetCTCollectionByExternalReferenceCode_addCTCollection()
		throws Exception {

		return ctCollectionResource.postCTCollection(randomCTCollection());
	}

	@Override
	protected CTCollection testGetCTCollectionsPage_addCTCollection(
			CTCollection ctCollection)
		throws Exception {

		CTCollection postCTCollection = ctCollectionResource.postCTCollection(
			ctCollection);

		com.liferay.change.tracking.model.CTCollection
			serviceBuilderCTCollection =
				_ctCollectionLocalService.getCTCollection(
					postCTCollection.getId());

		serviceBuilderCTCollection.setCreateDate(ctCollection.getDateCreated());
		serviceBuilderCTCollection.setModifiedDate(
			ctCollection.getDateModified());

		serviceBuilderCTCollection =
			_ctCollectionLocalService.updateCTCollection(
				serviceBuilderCTCollection);

		return ctCollectionResource.getCTCollection(
			serviceBuilderCTCollection.getCtCollectionId());
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
	protected CTCollection
			testPatchCTCollectionByExternalReferenceCode_addCTCollection()
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
	private CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private CTPreferencesLocalService _ctPreferencesLocalService;

	@Inject
	private JSONFactory _jsonFactory;

}