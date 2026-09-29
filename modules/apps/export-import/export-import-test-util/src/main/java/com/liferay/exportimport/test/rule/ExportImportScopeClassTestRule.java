/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.test.rule;

import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalServiceUtil;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate.Scope;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.ClassTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.staging.StagingGroupHelper;

import java.util.Collections;

import org.junit.runner.Description;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Alberto Javier Moreno Lage
 */
public class ExportImportScopeClassTestRule extends ClassTestRule<Void> {

	public ExportImportScopeClassTestRule(Scope scope) {
		_scope = scope;
	}

	public Group getGroup() {
		return _group;
	}

	public Layout getLayout() {
		return _layout;
	}

	public Scope getScope() {
		return _scope;
	}

	public Group getTargetGroup() {
		return _targetGroup;
	}

	public Layout getTargetLayout() {
		return _targetLayout;
	}

	public User getTargetUser() {
		return _targetUser;
	}

	@Override
	protected void afterClass(Description description, Void v)
		throws Exception {

		if (_scope == Scope.COMPANY) {
			CompanyLocalServiceUtil.deleteCompany(_targetCompany);
		}
		else if (_scope == Scope.DEPOT) {
			DepotEntryLocalServiceUtil.deleteDepotEntry(_depotEntry);
			DepotEntryLocalServiceUtil.deleteDepotEntry(_targetDepotEntry);
		}
		else {
			GroupLocalServiceUtil.deleteGroup(_group);
			GroupLocalServiceUtil.deleteGroup(_targetGroup);
		}
	}

	@Override
	protected Void beforeClass(Description description) throws Exception {
		if (_scope == Scope.COMPANY) {
			StagingGroupHelper stagingGroupHelper = _getStagingGroupHelper();

			_group = stagingGroupHelper.fetchCompanyGroup(
				TestPropsValues.getCompanyId());

			_targetCompany = CompanyTestUtil.addCompany();

			_targetGroup = stagingGroupHelper.fetchCompanyGroup(
				_targetCompany.getCompanyId());
			_targetUser = UserTestUtil.addCompanyAdminUser(_targetCompany);

			return null;
		}

		if (_scope == Scope.DEPOT) {
			_depotEntry = _addDepotEntry();
			_targetDepotEntry = _addDepotEntry();

			_group = _depotEntry.getGroup();
			_targetGroup = _targetDepotEntry.getGroup();
		}
		else {
			_group = GroupTestUtil.addGroup();
			_targetGroup = GroupTestUtil.addGroup();
		}

		_layout = LayoutTestUtil.addTypePortletLayout(_group.getGroupId());
		_targetLayout = LayoutTestUtil.addTypePortletLayout(
			_targetGroup.getGroupId());
		_targetUser = TestPropsValues.getUser();

		return null;
	}

	private DepotEntry _addDepotEntry() throws Exception {
		return DepotEntryLocalServiceUtil.addDepotEntry(
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			DepotConstants.TYPE_ASSET_LIBRARY,
			ServiceContextTestUtil.getServiceContext());
	}

	private StagingGroupHelper _getStagingGroupHelper() {
		Bundle bundle = FrameworkUtil.getBundle(
			ExportImportScopeClassTestRule.class);

		BundleContext bundleContext = bundle.getBundleContext();

		return bundleContext.getService(
			bundleContext.getServiceReference(StagingGroupHelper.class));
	}

	private DepotEntry _depotEntry;
	private Group _group;
	private Layout _layout;
	private final Scope _scope;
	private Company _targetCompany;
	private DepotEntry _targetDepotEntry;
	private Group _targetGroup;
	private Layout _targetLayout;
	private User _targetUser;

}