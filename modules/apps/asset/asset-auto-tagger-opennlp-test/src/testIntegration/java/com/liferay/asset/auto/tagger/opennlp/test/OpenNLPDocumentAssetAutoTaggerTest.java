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

package com.liferay.asset.auto.tagger.opennlp.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.auto.tagger.text.extractor.TextExtractor;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRenderer;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Cristina González
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class OpenNLPDocumentAssetAutoTaggerTest
	extends BaseOpenNLPDocumentAssetAutoTaggerTestCase {

	@After
	public void tearDown() {
		if (_assetRendererFactoryServiceRegistration != null) {
			_assetRendererFactoryServiceRegistration.unregister();

			_assetRendererFactoryServiceRegistration = null;
		}

		if (_textExtractorServiceRegistration != null) {
			_textExtractorServiceRegistration.unregister();

			_textExtractorServiceRegistration = null;
		}
	}

	@Test
	public void testDoesNotAutoTagAnAssetWithNoTextExtractor()
		throws Exception {

		String className = RandomTestUtil.randomString();

		_registerAssetRendererFactory(
			new TestAssetRendererFactory(group.getGroupId(), className, null));

		testWithOpenNLPDocumentAssetAutoTagProviderEnabled(
			className,
			() -> {
				AssetEntry assetEntry = assetEntryLocalService.updateEntry(
					TestPropsValues.getUserId(), group.getGroupId(), className,
					RandomTestUtil.randomLong(), new long[0], new String[0]);

				Collection<String> assetTagNames = Arrays.asList(
					assetEntry.getTagNames());

				Assert.assertEquals(
					assetTagNames.toString(), 0, assetTagNames.size());
			});
	}

	@Override
	protected AssetEntry getAssetEntry(String text) throws Exception {
		_registerAssetRendererFactory(
			new TestAssetRendererFactory(
				group.getGroupId(), getClassName(), text));

		_registerTextExtractor(
			new TextExtractor<String>() {

				@Override
				public String extract(String text, Locale locale) {
					return text;
				}

				@Override
				public String getClassName() {
					return OpenNLPDocumentAssetAutoTaggerTest.this.
						getClassName();
				}

			});

		long classPK = RandomTestUtil.randomLong();
		long[] assetCategoryIds = new long[0];
		String[] assetTagNames = new String[0];

		assetEntryLocalService.updateEntry(
			TestPropsValues.getUserId(), group.getGroupId(), getClassName(),
			classPK, assetCategoryIds, assetTagNames);

		return assetEntryLocalService.updateEntry(
			TestPropsValues.getUserId(), group.getGroupId(), null, null,
			getClassName(), classPK, null, 0, assetCategoryIds, assetTagNames,
			true, true, null, null, new Date(), null, ContentTypes.TEXT_PLAIN,
			null, null, null, null, null, 0, 0, (Double)null);
	}

	@Override
	protected String getClassName() {
		return _className;
	}

	private void _registerAssetRendererFactory(
		AssetRendererFactory assetRendererFactory) {

		Registry registry = RegistryUtil.getRegistry();

		_assetRendererFactoryServiceRegistration = registry.registerService(
			AssetRendererFactory.class, assetRendererFactory);
	}

	private void _registerTextExtractor(TextExtractor textExtractor) {
		Registry registry = RegistryUtil.getRegistry();

		_textExtractorServiceRegistration = registry.registerService(
			TextExtractor.class, textExtractor);
	}

	private ServiceRegistration<AssetRendererFactory>
		_assetRendererFactoryServiceRegistration;
	private String _className = RandomTestUtil.randomString();
	private ServiceRegistration<TextExtractor>
		_textExtractorServiceRegistration;

	private class TestAssetRendererFactory extends BaseAssetRendererFactory {

		public TestAssetRendererFactory(
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

}