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

package com.liferay.forms.apio.client.test.internal.activator;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalService;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestHelper;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.apio.test.util.AuthConfigurationTestUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author Paulo Cruz
 */
public abstract class BaseFormApioTestBundleActivator
	implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) {
		_ddmFormInstanceLocalServiceServiceReference =
			bundleContext.getServiceReference(
				DDMFormInstanceLocalService.class);

		_ddmFormInstanceLocalService = bundleContext.getService(
			_ddmFormInstanceLocalServiceServiceReference);

		_groupLocalServiceServiceReference = bundleContext.getServiceReference(
			GroupLocalService.class);

		_groupLocalService = bundleContext.getService(
			_groupLocalServiceServiceReference);

		_portalServiceReference = bundleContext.getServiceReference(
			Portal.class);

		_portal = bundleContext.getService(_portalServiceReference);

		try {
			AuthConfigurationTestUtil.deployOAuthConfiguration(bundleContext);

			_prepareTest();
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public void stop(BundleContext bundleContext) {
		_cleanUp();

		bundleContext.ungetService(
			_ddmFormInstanceLocalServiceServiceReference);
		bundleContext.ungetService(_groupLocalServiceServiceReference);
		bundleContext.ungetService(_portalServiceReference);
	}

	protected abstract Class<?> getFormDefinitionClass();

	protected abstract String getSiteName();

	private void _addDDMFormInstance(
			User user, Group group, DDMStructure ddmStructure)
		throws PortalException {

		LocalizedValue name = new LocalizedValue();

		name.addString(LocaleUtil.getDefault(), "My Form");

		LocalizedValue description = new LocalizedValue();

		description.addString(LocaleUtil.getDefault(), "This is my Form");

		_ddmFormInstanceLocalService.addFormInstance(
			user.getUserId(), group.getGroupId(), ddmStructure.getStructureId(),
			name.getValues(), description.getValues(),
			DDMFormValuesTestUtil.createDDMFormValues(
				ddmStructure.getDDMForm()),
			ServiceContextTestUtil.getServiceContext(group, user.getUserId()));
	}

	private DDMStructure _addDDMStructure(Group group, DDMForm ddmForm)
		throws Exception {

		long classNameId = _portal.getClassNameId(
			"com.liferay.dynamic.data.mapping.model.DDMFormInstance");

		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(classNameId, group);

		return ddmStructureTestHelper.addStructure(
			classNameId, null, getSiteName(), ddmForm,
			StorageType.JSON.getValue(), DDMStructureConstants.TYPE_DEFAULT);
	}

	private void _cleanUp() {
		try {
			_groupLocalService.deleteGroup(_group);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private void _prepareTest() throws Exception {
		String siteName = getSiteName();

		User user = UserTestUtil.getAdminUser(TestPropsValues.getCompanyId());
		Map<Locale, String> nameMap = Collections.singletonMap(
			LocaleUtil.getDefault(), siteName);

		_group = _groupLocalService.addGroup(
			user.getUserId(), GroupConstants.DEFAULT_PARENT_GROUP_ID, null, 0,
			GroupConstants.DEFAULT_LIVE_GROUP_ID, nameMap, nameMap,
			GroupConstants.TYPE_SITE_OPEN, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION,
			StringPool.SLASH +
				FriendlyURLNormalizerUtil.normalize(siteName),
			true, true, ServiceContextTestUtil.getServiceContext());

		DDMForm ddmForm = DDMFormFactory.create(getFormDefinitionClass());

		DDMStructure ddmStructure = _addDDMStructure(_group, ddmForm);

		_addDDMFormInstance(user, _group, ddmStructure);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseFormApioTestBundleActivator.class);

	private DDMFormInstanceLocalService _ddmFormInstanceLocalService;
	private ServiceReference<DDMFormInstanceLocalService>
		_ddmFormInstanceLocalServiceServiceReference;
	private Group _group;
	private GroupLocalService _groupLocalService;
	private ServiceReference<GroupLocalService>
		_groupLocalServiceServiceReference;
	private Portal _portal;
	private ServiceReference<Portal> _portalServiceReference;

}