/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.fragment.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.headless.admin.fragment.client.dto.v1_0.Fragment;
import com.liferay.headless.admin.fragment.client.dto.v1_0.FragmentVersion;
import com.liferay.headless.admin.fragment.client.problem.Problem;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@FeatureFlag("LPD-39244")
@RunWith(Arquillian.class)
public class FragmentResourceTest extends BaseFragmentResourceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_fragmentCollection = _addFragmentCollection();
	}

	@Override
	@Test
	public void testPostSiteFragmentSetFragment() throws Exception {
		super.testPostSiteFragmentSetFragment();

		_assertPostSiteFragmentSetFragmentDuplicateKeyProblemException();
		_testPostSiteFragmentSetFragmentApproved();
		_testPostSiteFragmentSetFragmentApprovedAndDraft();
		_testPostSiteFragmentSetFragmentDraft();
		_testPostSiteFragmentSetFragmentEmpty();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"cacheable", "externalReferenceCode",
			"fragmentSetExternalReferenceCode", "fragmentVersions", "key",
			"marketplace", "name", "readOnly"
		};
	}

	@Override
	protected Fragment randomFragment() throws Exception {
		Fragment fragment = super.randomFragment();

		fragment.setFragmentSetExternalReferenceCode(
			_fragmentCollection.getExternalReferenceCode());
		fragment.setFragmentVersions(
			new FragmentVersion[] {
				new FragmentVersion() {
					{
						configuration = RandomTestUtil.randomString();
						css = RandomTestUtil.randomString();
						html = RandomTestUtil.randomString();
						js = RandomTestUtil.randomString();
						status = FragmentVersion.Status.APPROVED;
					}
				},
				new FragmentVersion() {
					{
						configuration = RandomTestUtil.randomString();
						css = RandomTestUtil.randomString();
						html = RandomTestUtil.randomString();
						js = RandomTestUtil.randomString();
						status = Status.DRAFT;
					}
				}
			});

		return fragment;
	}

	@Override
	protected Fragment testPostSiteFragmentSetFragment_addFragment(
			Fragment fragment)
		throws Exception {

		return _postFragment(fragment);
	}

	private FragmentCollection _addFragmentCollection() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(testGroup.getGroupId());

		return _fragmentCollectionLocalService.addFragmentCollection(
			null, serviceContext.getUserId(), testGroup.getGroupId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			serviceContext);
	}

	private void _assertPostSiteFragmentSetFragmentDuplicateKeyProblemException()
		throws Exception {

		Fragment postFragment = _postFragment(randomFragment());

		Fragment duplicateFragment = randomFragment();

		duplicateFragment.setKey(postFragment.getKey());

		_assertProblemException(
			"CONFLICT", "a-fragment-entry-with-the-key-x-already-exists",
			() -> _postFragment(duplicateFragment), postFragment.getKey());
	}

	private void _assertProblemException(
			String status, String titleKey,
			UnsafeRunnable<Exception> unsafeRunnable, Object... titleArguments)
		throws Exception {

		try {
			unsafeRunnable.run();
			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals(status, problem.getStatus());
			Assert.assertEquals(
				_language.format(
					LocaleUtil.getDefault(), titleKey, titleArguments),
				problem.getTitle());
		}
	}

	private void _assertProblemException(
			String titleKey, UnsafeRunnable<Exception> unsafeRunnable,
			Object... titleArguments)
		throws Exception {

		_assertProblemException(
			"BAD_REQUEST", titleKey, unsafeRunnable, titleArguments);
	}

	private Fragment _postFragment(Fragment fragment) throws Exception {
		return fragmentResource.postSiteFragmentSetFragment(
			testGroup.getExternalReferenceCode(),
			_fragmentCollection.getExternalReferenceCode(), fragment);
	}

	private Fragment _randomFragment(boolean approved, boolean draft)
		throws Exception {

		return _randomFragment(approved, draft, null, null);
	}

	private Fragment _randomFragment(
			boolean approved, boolean draft, String externalReferenceCode,
			String key)
		throws Exception {

		Fragment fragment = super.randomFragment();

		List<FragmentVersion> fragmentVersions = new ArrayList<>();

		if (approved) {
			fragmentVersions.add(
				new FragmentVersion() {
					{
						configuration = RandomTestUtil.randomString();
						css = RandomTestUtil.randomString();
						html = RandomTestUtil.randomString();
						js = RandomTestUtil.randomString();
						status = FragmentVersion.Status.APPROVED;
					}
				});
		}

		if (draft) {
			fragmentVersions.add(
				new FragmentVersion() {
					{
						configuration = RandomTestUtil.randomString();
						css = RandomTestUtil.randomString();
						html = RandomTestUtil.randomString();
						js = RandomTestUtil.randomString();
						status = FragmentVersion.Status.DRAFT;
					}
				});
		}

		fragment.setExternalReferenceCode(externalReferenceCode);
		fragment.setFragmentSetExternalReferenceCode(
			_fragmentCollection.getExternalReferenceCode());
		fragment.setFragmentVersions(
			fragmentVersions.toArray(new FragmentVersion[0]));

		if (key != null) {
			fragment.setKey(key);
		}

		fragment.setMarketplace(false);

		return fragment;
	}

	private void _testPostSiteFragmentSetFragmentApproved() throws Exception {
		_testPostSiteFragmentSetFragmentApproved(true, false);
	}

	private void _testPostSiteFragmentSetFragmentApproved(
			boolean approved, boolean draft)
		throws Exception {

		Fragment postFragment = _postFragment(_randomFragment(approved, draft));

		Fragment getFragment = fragmentResource.getSiteFragment(
			testGroup.getExternalReferenceCode(),
			postFragment.getExternalReferenceCode());

		assertEquals(postFragment, getFragment);
		assertValid(getFragment);
	}

	private void _testPostSiteFragmentSetFragmentApprovedAndDraft()
		throws Exception {

		_testPostSiteFragmentSetFragmentApproved(true, true);
	}

	private void _testPostSiteFragmentSetFragmentDraft() throws Exception {
		_testPostSiteFragmentSetFragmentApproved(false, true);
	}

	private void _testPostSiteFragmentSetFragmentEmpty() throws Exception {
		_testPostSiteFragmentSetFragmentApproved(false, false);
	}

	private FragmentCollection _fragmentCollection;

	@Inject
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Inject
	private Language _language;

}