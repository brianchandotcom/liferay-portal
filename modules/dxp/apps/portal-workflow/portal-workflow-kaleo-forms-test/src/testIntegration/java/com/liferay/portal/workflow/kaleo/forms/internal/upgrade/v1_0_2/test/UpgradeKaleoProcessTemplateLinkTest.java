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

package com.liferay.portal.workflow.kaleo.forms.internal.upgrade.v1_0_2.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMTemplateLink;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLinkLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Inácio Nery
 */
@RunWith(Arquillian.class)
public class UpgradeKaleoProcessTemplateLinkTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_timestamp = new Timestamp(System.currentTimeMillis());

		setUpClassNameIds();
		setUpPrimaryKeys();
		setUpUpgradeKaleoProcessTemplateLink();
	}

	@After
	public void tearDown() throws Exception {
		deleteKaleoProcess(_kaleoProcessId);
		deleteKaleoProcessLink(_kaleoProcessLinkId);
	}

	@Test
	public void testCreateKaleoProcess() throws Exception {
		addKaleoProcess(_kaleoProcessId);

		_upgradeKaleoProcessTemplateLink.upgrade();

		DDMTemplateLink ddmTemplateLink =
			DDMTemplateLinkLocalServiceUtil.getTemplateLink(
				_kaleoProcessClassNameId, _kaleoProcessId);

		Assert.assertNotNull(ddmTemplateLink);

		_ddmTemplateLinks.add(ddmTemplateLink);
	}

	@Test
	public void testCreateKaleoProcessLink() throws Exception {
		addKaleoProcessLink(_kaleoProcessLinkId);

		_upgradeKaleoProcessTemplateLink.upgrade();

		DDMTemplateLink ddmTemplateLink =
			DDMTemplateLinkLocalServiceUtil.getTemplateLink(
				_kaleoProcessLinkClassNameId, _kaleoProcessLinkId);

		Assert.assertNotNull(ddmTemplateLink);

		_ddmTemplateLinks.add(ddmTemplateLink);
	}

	protected void addKaleoProcess(long kaleoProcessId) throws Exception {
		StringBundler sb = new StringBundler(5);

		sb.append("insert into KaleoProcess (uuid_, kaleoProcessId, groupId, ");
		sb.append("companyId, userId, userName, createDate, modifiedDate, ");
		sb.append("DDLRecordSetId, DDMTemplateId, workflowDefinitionName, ");
		sb.append("workflowDefinitionVersion) values (?, ?, ?, ?, ?, ?, ?, ");
		sb.append("?, ?, ?, ?, ?)");

		String sql = sb.toString();

		try (Connection con = DataAccess.getConnection();
			PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, PortalUUIDUtil.generate());
			ps.setLong(2, kaleoProcessId);
			ps.setLong(3, _group.getGroupId());
			ps.setLong(4, _group.getCompanyId());
			ps.setLong(5, TestPropsValues.getUserId());
			ps.setString(6, null);
			ps.setTimestamp(7, _timestamp);
			ps.setTimestamp(8, _timestamp);
			ps.setLong(9, RandomTestUtil.randomLong());
			ps.setLong(10, RandomTestUtil.randomLong());
			ps.setString(11, StringUtil.randomString());
			ps.setInt(12, RandomTestUtil.randomInt());

			ps.executeUpdate();
		}
	}

	protected void addKaleoProcessLink(long kaleoProcessLinkId)
		throws Exception {

		StringBundler sb = new StringBundler(3);

		sb.append("insert into KaleoProcessLink (kaleoProcessLinkId, ");
		sb.append("kaleoProcessId, workflowTaskName, DDMTemplateId) values ");
		sb.append("(?, ?, ?, ?)");

		String sql = sb.toString();

		try (Connection con = DataAccess.getConnection();
			PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setLong(1, kaleoProcessLinkId);
			ps.setLong(2, _kaleoProcessId);
			ps.setString(3, StringUtil.randomString());
			ps.setLong(4, RandomTestUtil.randomLong());

			ps.executeUpdate();
		}
	}

	protected void deleteKaleoProcess(long kaleoProcessId) throws Exception {
		DB db = DBManagerUtil.getDB();

		db.runSQL(
			"delete from KaleoProcess where kaleoProcessId = " +
				kaleoProcessId);
	}

	protected void deleteKaleoProcessLink(long kaleoProcessLinkId)
		throws Exception {

		DB db = DBManagerUtil.getDB();

		db.runSQL(
			"delete from KaleoProcessLink where kaleoProcessLinkId = " +
				kaleoProcessLinkId);
	}

	protected void setUpClassNameIds() {
		_kaleoProcessLinkClassNameId = PortalUtil.getClassNameId(
			"com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessLink");
		_kaleoProcessClassNameId = PortalUtil.getClassNameId(
			"com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess");
	}

	protected void setUpPrimaryKeys() {
		_kaleoProcessId = RandomTestUtil.randomLong();
		_kaleoProcessLinkId = RandomTestUtil.randomLong();
	}

	protected void setUpUpgradeKaleoProcessTemplateLink() {
		Registry registry = RegistryUtil.getRegistry();

		UpgradeStepRegistrator upgradeStepRegistror = registry.getService(
			registry.getServiceReference(
				"com.liferay.portal.workflow.kaleo.forms.internal.upgrade." +
					"KaleoFormsServiceUpgrade"));

		upgradeStepRegistror.register(
			new UpgradeStepRegistrator.Registry() {

				@Override
				public void register(
					String fromSchemaVersionString,
					String toSchemaVersionString, UpgradeStep... upgradeSteps) {

					for (UpgradeStep upgradeStep : upgradeSteps) {
						Class<?> clazz = upgradeStep.getClass();

						String className = clazz.getName();

						if (className.contains(
								"UpgradeKaleoProcessTemplateLink")) {

							_upgradeKaleoProcessTemplateLink =
								(UpgradeProcess)upgradeStep;
						}
					}
				}

			});
	}

	@DeleteAfterTestRun
	private final List<DDMTemplateLink> _ddmTemplateLinks = new ArrayList<>();

	@DeleteAfterTestRun
	private Group _group;

	private long _kaleoProcessClassNameId;
	private long _kaleoProcessId;
	private long _kaleoProcessLinkClassNameId;
	private long _kaleoProcessLinkId;
	private Timestamp _timestamp;
	private UpgradeProcess _upgradeKaleoProcessTemplateLink;

}