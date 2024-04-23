/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.test.util.layout.display.page;

import com.liferay.info.test.util.model.MockObject;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.test.util.LayoutFriendlyURLRandomizerBumper;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.Locale;

/**
 * @author Lourdes Fernández Besada
 */
public class MockObjectLayoutDisplayPageObjectProvider
	implements LayoutDisplayPageObjectProvider {

	public MockObjectLayoutDisplayPageObjectProvider(
		MockObject mockObject, Portal portal) {

		_mockObject = mockObject;
		_portal = portal;
	}

	@Override
	public long getClassNameId() {
		return _portal.getClassNameId(MockObject.class.getName());
	}

	@Override
	public long getClassPK() {
		return _mockObject.getClassPK();
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
	public Object getDisplayObject() {
		return _mockObject;
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
		return RandomTestUtil.randomString();
	}

	@Override
	public String getURLTitle(Locale locale) {
		return FriendlyURLNormalizerUtil.normalize(
			RandomTestUtil.randomString(
				LayoutFriendlyURLRandomizerBumper.INSTANCE));
	}

	private final MockObject _mockObject;
	private final Portal _portal;

}