/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.patcher.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.osb.patcher.constants.PatcherBuildConstants;
import com.liferay.osb.patcher.constants.WorkflowConstants;
import com.liferay.osb.patcher.model.PatcherBuild;
import com.liferay.osb.patcher.model.PatcherFix;
import com.liferay.osb.patcher.service.PatcherBuildLocalServiceUtil;
import com.liferay.osb.patcher.service.PatcherFixLocalServiceUtil;
import com.liferay.osb.patcher.util.PatcherBuildUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.util.Collections;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Michael Prigge
 */
@RunWith(Arquillian.class)
public class PatcherBuildUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.osb.patcher.service"));

	@Before
	public void setUp() throws Exception {
		_user = TestPropsValues.getUser();
	}

	@Test
	public void testUpdatePatcherBuildFixesReusingExistingHotfixSkipsCompile()
		throws Exception {

		PatcherBuild patcherBuild = _addPatcherBuild();

		PatcherFix patcherFix = _addPatcherFix();

		PatcherBuildUtil.updatePatcherBuildFixes(
			_user, patcherBuild,
			Collections.singletonList(patcherFix.getPatcherFixId()), true);

		PatcherBuild reloadedPatcherBuild =
			PatcherBuildLocalServiceUtil.getPatcherBuild(
				patcherBuild.getPatcherBuildId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_BUILD_MERGING,
			reloadedPatcherBuild.getStatus());
	}

	@Test
	public void testUpdatePatcherBuildFixesWithoutExistingHotfixCompiles()
		throws Exception {

		PatcherBuild patcherBuild = _addPatcherBuild();

		PatcherFix patcherFix = _addPatcherFix();

		PatcherBuildUtil.updatePatcherBuildFixes(
			_user, patcherBuild,
			Collections.singletonList(patcherFix.getPatcherFixId()), false);

		PatcherBuild reloadedPatcherBuild =
			PatcherBuildLocalServiceUtil.getPatcherBuild(
				patcherBuild.getPatcherBuildId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_BUILD_COMPILING,
			reloadedPatcherBuild.getStatus());
	}

	private PatcherBuild _addPatcherBuild() throws Exception {
		PatcherBuild patcherBuild =
			PatcherBuildLocalServiceUtil.createPatcherBuild(
				CounterLocalServiceUtil.increment());

		patcherBuild.setCompanyId(_user.getCompanyId());
		patcherBuild.setUserId(_user.getUserId());
		patcherBuild.setUserName(_user.getFullName());
		patcherBuild.setCreateDate(new Date());
		patcherBuild.setModifiedDate(new Date());
		patcherBuild.setName(RandomTestUtil.randomString());
		patcherBuild.setChildBuild(false);
		patcherBuild.setType(PatcherBuildConstants.TYPE_HOTFIX);
		patcherBuild.setStatus(WorkflowConstants.STATUS_BUILD_MERGING);
		patcherBuild.setStatusByUserId(_user.getUserId());
		patcherBuild.setStatusByUserName(_user.getFullName());
		patcherBuild.setStatusDate(new Date());

		return PatcherBuildLocalServiceUtil.addPatcherBuild(patcherBuild);
	}

	private PatcherFix _addPatcherFix() throws Exception {
		PatcherFix patcherFix = PatcherFixLocalServiceUtil.createPatcherFix(
			CounterLocalServiceUtil.increment());

		patcherFix.setCompanyId(_user.getCompanyId());
		patcherFix.setUserId(_user.getUserId());
		patcherFix.setUserName(_user.getFullName());
		patcherFix.setCreateDate(new Date());
		patcherFix.setModifiedDate(new Date());
		patcherFix.setName(RandomTestUtil.randomString());

		return PatcherFixLocalServiceUtil.addPatcherFix(patcherFix);
	}

	private User _user;

}