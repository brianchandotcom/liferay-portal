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

package com.liferay.analytics.reports.test.info.display.contributor;

import com.liferay.analytics.reports.test.MockObject;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayField;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.service.ClassNameLocalService;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Cristina González
 */
public class MockInfoDisplayContributor
	implements InfoDisplayContributor<MockObject> {

	public MockInfoDisplayContributor(
		ClassNameLocalService classNameLocalService) {

		_classNameLocalService = classNameLocalService;
	}

	@Override
	public String getClassName() {
		return MockObject.class.getName();
	}

	@Override
	public Set<InfoDisplayField> getInfoDisplayFields(
			long classTypeId, Locale locale)
		throws PortalException {

		return null;
	}

	@Override
	public Map<String, Object> getInfoDisplayFieldsValues(
			MockObject mockObject, Locale locale)
		throws PortalException {

		return new HashMap<>();
	}

	@Override
	public InfoDisplayObjectProvider<MockObject> getInfoDisplayObjectProvider(
			long classPK)
		throws PortalException {

		return new MockInfoDisplayObjectProvider(_classNameLocalService);
	}

	@Override
	public InfoDisplayObjectProvider<MockObject> getInfoDisplayObjectProvider(
			long groupId, String urlTitle)
		throws PortalException {

		return null;
	}

	@Override
	public String getInfoURLSeparator() {
		return "/mockSeparator/";
	}

	public static class MockInfoDisplayObjectProvider
		implements InfoDisplayObjectProvider<MockObject> {

		public MockInfoDisplayObjectProvider(
			ClassNameLocalService classNameLocalService) {

			ClassName className = classNameLocalService.getClassName(
				MockObject.class.getName());

			_classNameId = className.getClassNameId();
		}

		@Override
		public long getClassNameId() {
			return _classNameId;
		}

		@Override
		public long getClassPK() {
			return 0;
		}

		@Override
		public long getClassTypeId() {
			return 0;
		}

		@Override
		public String getDescription(Locale locale) {
			return null;
		}

		@Override
		public MockObject getDisplayObject() {
			return new MockObject();
		}

		@Override
		public long getGroupId() {
			return 0;
		}

		@Override
		public String getKeywords(Locale locale) {
			return null;
		}

		@Override
		public String getTitle(Locale locale) {
			return null;
		}

		@Override
		public String getURLTitle(Locale locale) {
			return null;
		}

		private final long _classNameId;

	}

	private final ClassNameLocalService _classNameLocalService;

}