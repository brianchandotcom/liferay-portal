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
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalServiceUtil;
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
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * @author Paulo Cruz
 */
public abstract class BaseFormApioTestBundleActivator
	implements BundleActivator {

	public static final String FORM_DEFAULT_LANGUAGE = "en-US";

	public static final String SITE_NAME =
		BaseFormApioTestBundleActivator.class.getSimpleName() + "Site";

	@Override
	public void start(BundleContext bundleContext) {
		try {
			AuthConfigurationTestUtil.deployOAuthConfiguration(bundleContext);

			_prepareTest();
		}
		catch (Exception e) {
			_cleanUp();

			throw new RuntimeException(e);
		}
	}

	@Override
	public void stop(BundleContext bundleContext) {
		_cleanUp();
	}

	protected abstract Class<?> getFormDefinitionClass();

	private DDMFormInstance _addDDMFormInstance(
			User user, Group group, DDMStructure ddmStructure)
		throws PortalException {

		LocalizedValue name = new LocalizedValue();

		name.addString(LocaleUtil.getDefault(), "My Form");

		LocalizedValue description = new LocalizedValue();

		description.addString(LocaleUtil.getDefault(), "This is my Form");

		return DDMFormInstanceLocalServiceUtil.addFormInstance(
			user.getUserId(), group.getGroupId(), ddmStructure.getStructureId(),
			name.getValues(), description.getValues(),
			DDMFormValuesTestUtil.createDDMFormValues(
				ddmStructure.getDDMForm()),
			ServiceContextTestUtil.getServiceContext(group, user.getUserId()));
	}

	private DDMStructure _addDDMStructure(Group group, DDMForm ddmForm)
		throws Exception {

		long classNameId = PortalUtil.getClassNameId(
			"com.liferay.dynamic.data.mapping.model.DDMFormInstance");

		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(classNameId, group);

		return ddmStructureTestHelper.addStructure(
			classNameId, null, SITE_NAME, ddmForm, StorageType.JSON.getValue(),
			DDMStructureConstants.TYPE_DEFAULT);
	}

	private void _cleanUp() {
		try {
			GroupLocalServiceUtil.deleteGroup(_group);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private void _prepareTest() throws Exception {
		User user = UserTestUtil.getAdminUser(TestPropsValues.getCompanyId());
		Map<Locale, String> nameMap = Collections.singletonMap(
			LocaleUtil.getDefault(), SITE_NAME);

		_group = GroupLocalServiceUtil.addGroup(
			user.getUserId(), GroupConstants.DEFAULT_PARENT_GROUP_ID, null, 0,
			GroupConstants.DEFAULT_LIVE_GROUP_ID, nameMap, nameMap,
			GroupConstants.TYPE_SITE_OPEN, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION,
			StringPool.SLASH +
				FriendlyURLNormalizerUtil.normalize(SITE_NAME),
			true, true, ServiceContextTestUtil.getServiceContext());

		DDMForm ddmForm = DDMFormFactory.create(getFormDefinitionClass());

		DDMStructure ddmStructure = _addDDMStructure(_group, ddmForm);

		_addDDMFormInstance(user, _group, ddmStructure);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseFormApioTestBundleActivator.class);

	private Group _group;

}