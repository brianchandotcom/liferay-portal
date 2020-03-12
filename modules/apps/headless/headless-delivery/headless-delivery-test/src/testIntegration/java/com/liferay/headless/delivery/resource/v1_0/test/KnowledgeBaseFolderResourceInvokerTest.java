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
import com.liferay.headless.delivery.dto.v1_0.KnowledgeBaseFolder;
import com.liferay.headless.delivery.resource.v1_0.KnowledgeBaseFolderResourceInvoker;
import com.liferay.knowledge.base.exception.NoSuchFolderException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Matthew Tambara
 */
@RunWith(Arquillian.class)
public class KnowledgeBaseFolderResourceInvokerTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testFactory() throws Throwable {
		String name = PrincipalThreadLocal.getName();

		long testUserId = TestPropsValues.getUserId();

		PrincipalThreadLocal.setName(testUserId);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.COMPANY_ID, _group.getCompanyId());

		mockHttpServletRequest.setAttribute(WebKeys.USER_ID, testUserId);

		KnowledgeBaseFolder expectedKnowledgeBaseFolder =
			_randomKnowledgeBaseFolder();

		Exception exception1 = new Exception();

		// TransactionInvokerUtil is used to simulate the service call
		// environment, this doesn't need to be done in production

		try {
			TransactionInvokerUtil.invoke(
				_REQUIRES_NEW_TRANSACTION_CONFIG,
				() -> {
					_knowledgeBaseFolderResourceInvoker.invoke(
						knowledgeBaseFolderResource -> {
							KnowledgeBaseFolder postedKnowledgeBaseFolder =
								knowledgeBaseFolderResource.
									postSiteKnowledgeBaseFolder(
										_group.getGroupId(),
										expectedKnowledgeBaseFolder);

							Assert.assertEquals(
								expectedKnowledgeBaseFolder.getName(),
								postedKnowledgeBaseFolder.getName());

							KnowledgeBaseFolder getKnowledgeBaseFolder =
								knowledgeBaseFolderResource.
									getKnowledgeBaseFolder(
										postedKnowledgeBaseFolder.getId());

							Assert.assertEquals(
								postedKnowledgeBaseFolder,
								getKnowledgeBaseFolder);

							throw exception1;
						},
						mockHttpServletRequest);

					return null;
				});
		}
		catch (Exception exception2) {
			Assert.assertSame(exception1, exception2);
		}
		finally {
			PrincipalThreadLocal.setName(name);
		}

		PrincipalThreadLocal.setName(testUserId);

		try {
			TransactionInvokerUtil.invoke(
				_SUPPORTS_TRANSACTION_CONFIG,
				() -> {
					_knowledgeBaseFolderResourceInvoker.invoke(
						knowledgeBaseFolderResource -> {
							try {
								knowledgeBaseFolderResource.
									getKnowledgeBaseFolder(
										expectedKnowledgeBaseFolder.getId());

								Assert.fail();
							}
							catch (Exception exception2) {
								Assert.assertSame(
									NoSuchFolderException.class,
									exception2.getClass());
							}
						},
						mockHttpServletRequest);

					return null;
				});
		}
		finally {
			PrincipalThreadLocal.setName(name);
		}
	}

	private KnowledgeBaseFolder _randomKnowledgeBaseFolder() throws Exception {
		return new KnowledgeBaseFolder() {
			{
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				name = RandomTestUtil.randomString();
				numberOfKnowledgeBaseArticles = RandomTestUtil.randomInt();
				numberOfKnowledgeBaseFolders = RandomTestUtil.randomInt();
				parentKnowledgeBaseFolderId = RandomTestUtil.randomLong();
				siteId = _group.getGroupId();
			}
		};
	}

	private static final TransactionConfig _REQUIRES_NEW_TRANSACTION_CONFIG =
		TransactionConfig.Factory.create(
			Propagation.REQUIRES_NEW, new Class<?>[] {Exception.class});

	private static final TransactionConfig _SUPPORTS_TRANSACTION_CONFIG =
		TransactionConfig.Factory.create(
			Propagation.SUPPORTS, new Class<?>[] {Exception.class});

	private Group _group;

	@Inject
	private KnowledgeBaseFolderResourceInvoker
		_knowledgeBaseFolderResourceInvoker;

}