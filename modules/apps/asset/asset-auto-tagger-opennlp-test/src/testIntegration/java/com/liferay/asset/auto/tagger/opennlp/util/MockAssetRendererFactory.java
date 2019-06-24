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

package com.liferay.asset.auto.tagger.opennlp.util;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.BaseAssetRenderer;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Cristina González
 * @author Alejandro Tardín
 */
public class MockAssetRendererFactory extends BaseAssetRendererFactory {

	public MockAssetRendererFactory(
		long groupId, String className, Object assetObject) {

		_groupId = groupId;
		_className = className;
		_assetObject = assetObject;
	}

	@Override
	public AssetRenderer getAssetRenderer(long classPK, int type) {
		return new BaseAssetRenderer() {

			@Override
			public Object getAssetObject() {
				return _assetObject;
			}

			@Override
			public String getClassName() {
				return _className;
			}

			@Override
			public long getClassPK() {
				return RandomTestUtil.randomLong();
			}

			@Override
			public long getGroupId() {
				return _groupId;
			}

			@Override
			public String getSummary(
				PortletRequest portletRequest,
				PortletResponse portletResponse) {

				return null;
			}

			@Override
			public String getTitle(Locale locale) {
				return null;
			}

			@Override
			public long getUserId() {
				return RandomTestUtil.randomLong();
			}

			@Override
			public String getUserName() {
				return null;
			}

			@Override
			public String getUuid() {
				return null;
			}

			@Override
			public boolean include(
				HttpServletRequest httpServletRequest,
				HttpServletResponse httpServletResponse, String template) {

				return false;
			}

		};
	}

	@Override
	public String getClassName() {
		return _className;
	}

	@Override
	public String getType() {
		return "test";
	}

	private final Object _assetObject;
	private final String _className;
	private final long _groupId;

}