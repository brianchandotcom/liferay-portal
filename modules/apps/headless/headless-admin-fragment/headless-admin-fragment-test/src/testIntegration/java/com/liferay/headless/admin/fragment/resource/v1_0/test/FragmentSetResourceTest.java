/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.fragment.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.headless.admin.fragment.client.dto.v1_0.FragmentSet;
import com.liferay.headless.admin.fragment.client.problem.Problem;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@FeatureFlag("LPD-39244")
@RunWith(Arquillian.class)
public class FragmentSetResourceTest extends BaseFragmentSetResourceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Override
	@Test
	public void testDeleteSiteFragmentSet() throws Exception {
		super.testDeleteSiteFragmentSet();

		FragmentSet fragmentSet = testGetSiteFragmentSetsPage_addFragmentSet(
			testGroup.getExternalReferenceCode(), randomFragmentSet());

		fragmentSetResource.deleteSiteFragmentSet(
			testGroup.getExternalReferenceCode(),
			fragmentSet.getExternalReferenceCode());

		Assert.assertNull(
			_fragmentCollectionLocalService.
				fetchFragmentCollectionByExternalReferenceCode(
					fragmentSet.getExternalReferenceCode(),
					testGroup.getGroupId()));
	}

	@Override
	@Test
	public void testPostSiteFragmentSet() throws Exception {
		FragmentSet randomFragmentSet = randomFragmentSet();

		FragmentSet postFragmentSet = testPostSiteFragmentSet_addFragmentSet(
			randomFragmentSet);

		assertEquals(randomFragmentSet, postFragmentSet);
		assertValid(postFragmentSet);

		FragmentSet duplicateERCFragmentSet = randomFragmentSet();

		duplicateERCFragmentSet.setExternalReferenceCode(
			postFragmentSet.getExternalReferenceCode());

		_assertProblemException(
			"BAD_REQUEST", "This external reference code is already in use.",
			() -> fragmentSetResource.postSiteFragmentSet(
				testGroup.getExternalReferenceCode(), duplicateERCFragmentSet));

		FragmentSet duplicateKeyFragmentSet = randomFragmentSet();

		duplicateKeyFragmentSet.setKey(postFragmentSet.getKey());

		_assertProblemException(
			"CONFLICT", "A fragment set with the same key already exists",
			() -> fragmentSetResource.postSiteFragmentSet(
				testGroup.getExternalReferenceCode(), duplicateKeyFragmentSet));
	}

	@Override
	@Test
	public void testPutSiteFragmentSet() throws Exception {
		FragmentSet fragmentSet = randomFragmentSet();

		FragmentSet putFragmentSet = fragmentSetResource.putSiteFragmentSet(
			testGroup.getExternalReferenceCode(),
			fragmentSet.getExternalReferenceCode(), fragmentSet);

		assertEquals(fragmentSet, putFragmentSet);
		assertValid(putFragmentSet);

		FragmentSet getFragmentSet = fragmentSetResource.getSiteFragmentSet(
			testGroup.getExternalReferenceCode(),
			putFragmentSet.getExternalReferenceCode());

		assertEquals(fragmentSet, getFragmentSet);
		assertValid(getFragmentSet);

		fragmentSet.setDescription(RandomTestUtil.randomString());
		fragmentSet.setName(RandomTestUtil.randomString());

		putFragmentSet = fragmentSetResource.putSiteFragmentSet(
			testGroup.getExternalReferenceCode(),
			fragmentSet.getExternalReferenceCode(), fragmentSet);

		assertEquals(fragmentSet, putFragmentSet);
		assertValid(putFragmentSet);

		getFragmentSet = fragmentSetResource.getSiteFragmentSet(
			testGroup.getExternalReferenceCode(),
			putFragmentSet.getExternalReferenceCode());

		assertEquals(fragmentSet, getFragmentSet);
		assertValid(getFragmentSet);

		String originalExternalReferenceCode =
			fragmentSet.getExternalReferenceCode();
		String originalKey = fragmentSet.getKey();

		Boolean originalMarketplace = fragmentSet.getMarketplace();

		fragmentSet.setExternalReferenceCode(RandomTestUtil.randomString());
		fragmentSet.setKey(RandomTestUtil.randomString());
		fragmentSet.setMarketplace(!originalMarketplace);

		putFragmentSet = fragmentSetResource.putSiteFragmentSet(
			testGroup.getExternalReferenceCode(), originalExternalReferenceCode,
			fragmentSet);

		Assert.assertEquals(
			originalExternalReferenceCode,
			putFragmentSet.getExternalReferenceCode());
		Assert.assertEquals(originalKey, putFragmentSet.getKey());
		Assert.assertEquals(
			originalMarketplace, putFragmentSet.getMarketplace());

		FragmentSet duplicateKeyFragmentSet = randomFragmentSet();

		duplicateKeyFragmentSet.setKey(originalKey);

		_assertProblemException(
			"CONFLICT", "A fragment set with the same key already exists",
			() -> fragmentSetResource.putSiteFragmentSet(
				testGroup.getExternalReferenceCode(),
				duplicateKeyFragmentSet.getExternalReferenceCode(),
				duplicateKeyFragmentSet));
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"description", "externalReferenceCode", "key", "marketplace", "name"
		};
	}

	@Override
	protected FragmentSet randomFragmentSet() throws Exception {
		FragmentSet fragmentSet = super.randomFragmentSet();

		fragmentSet.setDateCreated(new Date(System.currentTimeMillis()));
		fragmentSet.setDateModified(new Date(System.currentTimeMillis()));

		return fragmentSet;
	}

	@Override
	protected Map<String, Map<String, String>>
			testGetSiteFragmentSetsPage_getExpectedActions(
				String siteExternalReferenceCode)
		throws Exception {

		return new HashMap<>();
	}

	@Override
	protected FragmentSet testPostSiteFragmentSet_addFragmentSet(
			FragmentSet fragmentSet)
		throws Exception {

		return fragmentSetResource.putSiteFragmentSet(
			testGroup.getExternalReferenceCode(),
			fragmentSet.getExternalReferenceCode(), fragmentSet);
	}

	private void _assertProblemException(
			String status, String title,
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		try {
			unsafeRunnable.run();

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals(status, problem.getStatus());
			Assert.assertEquals(title, problem.getTitle());
		}
	}

	@Inject
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

}