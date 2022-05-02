/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.search.experiences.rest.client.dto.v1_0.SXPElement;
import com.liferay.search.experiences.rest.client.http.HttpInvoker;
import com.liferay.search.experiences.rest.client.pagination.Page;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class SXPElementResourceTest extends BaseSXPElementResourceTestCase {

	@Override
	@Test
	public void testGetSXPElementExport() throws Exception {
		SXPElement sxpElement = randomSXPElement();

		SXPElement postSXPElement = testPostSXPElement_addSXPElement(
			sxpElement);

		HttpInvoker.HttpResponse httpResponse =
			sxpElementResource.getSXPElementExportHttpResponse(
				postSXPElement.getId());

		Assert.assertTrue(
			JSONUtil.equals(
				JSONFactoryUtil.createJSONObject(httpResponse.getContent()),
				JSONUtil.put(
					"description_i18n",
					JSONUtil.put("en_US", sxpElement.getDescription())
				).put(
					"elementDefinition", JSONFactoryUtil.createJSONObject()
				).put(
					"schemaVersion", postSXPElement.getSchemaVersion()
				).put(
					"title_i18n", JSONUtil.put("en_US", sxpElement.getTitle())
				).put(
					"type", postSXPElement.getType()
				)));
	}

	@Override
	@Test
	public void testGetSXPElementsPageWithFilterDateTimeEquals()
		throws Exception {

		_deleteSXPElements();

		super.testGetSXPElementsPageWithFilterDateTimeEquals();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetSXPElement() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetSXPElementNotFound() throws Exception {
	}

	@Override
	@Test
	public void testPostSXPElement() throws Exception {
		super.testPostSXPElement();

		String description = RandomTestUtil.randomString();
		String title = RandomTestUtil.randomString();

		SXPElement sxpElement = SXPElement.toDTO(
			JSONUtil.put(
				"description", description
			).put(
				"title", title
			).toString());

		SXPElement postSXPElement = testPostSXPElement_addSXPElement(
			sxpElement);

		sxpElement.setCreateDate(postSXPElement.getCreateDate());

		sxpElement.setDescription_i18n(
			Collections.singletonMap(
				LocaleUtil.toBCP47LanguageId(LocaleUtil.US), description));
		sxpElement.setExternalReferenceCode(
			postSXPElement.getExternalReferenceCode());
		sxpElement.setId(postSXPElement.getId());
		sxpElement.setModifiedDate(postSXPElement.getModifiedDate());
		sxpElement.setReadOnly(false);
		sxpElement.setSchemaVersion(postSXPElement.getSchemaVersion());
		sxpElement.setTitle_i18n(
			Collections.singletonMap(
				LocaleUtil.toBCP47LanguageId(LocaleUtil.US), title));
		sxpElement.setType(0);
		sxpElement.setUserName(postSXPElement.getUserName());
		sxpElement.setVersion(postSXPElement.getVersion());

		Assert.assertEquals(sxpElement.toString(), postSXPElement.toString());

		assertValid(postSXPElement);
	}

	@Override
	@Test
	public void testPostSXPElementValidate() throws Exception {
		sxpElementResource.postSXPElementValidate("{}");
	}

	@Override
	protected SXPElement randomSXPElement() throws Exception {
		SXPElement sxpElement = super.randomSXPElement();

		sxpElement.setDescription_i18n(
			Collections.singletonMap("en_US", sxpElement.getDescription()));
		sxpElement.setTitle(_TITLE_PREFIX + sxpElement.getTitle());
		sxpElement.setTitle_i18n(
			Collections.singletonMap("en_US", sxpElement.getTitle()));

		return sxpElement;
	}

	@Override
	protected SXPElement testDeleteSXPElement_addSXPElement() throws Exception {
		return _addSXPElement(randomSXPElement());
	}

	@Override
	protected SXPElement testGetSXPElement_addSXPElement() throws Exception {
		return _addSXPElement(randomSXPElement());
	}

	@Override
	protected SXPElement testGetSXPElementsPage_addSXPElement(
			SXPElement sxpElement)
		throws Exception {

		return _addSXPElement(sxpElement);
	}

	@Override
	protected SXPElement testGraphQLSXPElement_addSXPElement()
		throws Exception {

		return _addSXPElement(randomSXPElement());
	}

	@Override
	protected SXPElement testPatchSXPElement_addSXPElement() throws Exception {
		return _addSXPElement(randomSXPElement());
	}

	@Override
	protected SXPElement testPostSXPElement_addSXPElement(SXPElement sxpElement)
		throws Exception {

		return _addSXPElement(sxpElement);
	}

	@Override
	protected SXPElement testPostSXPElementCopy_addSXPElement(
			SXPElement sxpElement)
		throws Exception {

		return _addSXPElement(sxpElement);
	}

	private SXPElement _addSXPElement(SXPElement sxpElement) throws Exception {
		return sxpElementResource.postSXPElement(sxpElement);
	}

	private void _deleteSXPElements() throws Exception {
		Page<SXPElement> page = sxpElementResource.getSXPElementsPage(
			null, null, null, null);

		for (SXPElement sxpElement : page.getItems()) {
			String title = sxpElement.getTitle();

			if (title.startsWith(_TITLE_PREFIX)) {
				sxpElementResource.deleteSXPElement(sxpElement.getId());
			}
		}
	}

	private static final String _TITLE_PREFIX = "SXPERT";

}