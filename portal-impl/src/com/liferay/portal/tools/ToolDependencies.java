/**
 * Copyright (c) 2000-2014 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools;

import com.liferay.portal.cache.SingleVMPoolImpl;
import com.liferay.portal.cache.memory.MemoryPortalCacheManager;
import com.liferay.portal.kernel.cache.SingleVMPoolUtil;
import com.liferay.portal.kernel.microsofttranslator.MicrosoftTranslatorFactoryUtil;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.microsofttranslator.MicrosoftTranslatorFactoryImpl;
import com.liferay.portal.model.ModelHintsImpl;
import com.liferay.portal.model.ModelHintsUtil;
import com.liferay.portal.security.auth.FullNameGeneratorFactory;
import com.liferay.portal.service.permission.PortletPermissionImpl;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.util.DigesterImpl;
import com.liferay.portal.util.FastDateFormatFactoryImpl;
import com.liferay.portal.util.FileImpl;
import com.liferay.portal.util.FriendlyURLNormalizerImpl;
import com.liferay.portal.util.HtmlImpl;
import com.liferay.portal.util.InitUtil;
import com.liferay.portal.xml.SAXReaderImpl;

/**
 * @author Raymond Augé
 */
public class ToolDependencies {

	public static void wire() {
		InitUtil.init();

		DigesterUtil digesterUtil = new DigesterUtil();

		digesterUtil.setDigester(new DigesterImpl());

		FastDateFormatFactoryUtil fastDateFormatFactoryUtil =
			new FastDateFormatFactoryUtil();

		fastDateFormatFactoryUtil.setFastDateFormatFactory(
			new FastDateFormatFactoryImpl());

		FileUtil fileUtil = new FileUtil();

		fileUtil.setFile(new FileImpl());

		FriendlyURLNormalizerUtil friendlyURLNormalizerUtil =
			new FriendlyURLNormalizerUtil();

		friendlyURLNormalizerUtil.setFriendlyURLNormalizer(
			new FriendlyURLNormalizerImpl());

		FullNameGeneratorFactory fullNameGeneratorFactory =
			new FullNameGeneratorFactory();

		try {
			fullNameGeneratorFactory.afterPropertiesSet();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		HtmlUtil htmlUtil = new HtmlUtil();

		htmlUtil.setHtml(new HtmlImpl());

		MicrosoftTranslatorFactoryUtil microsoftTranslatorFactoryUtil =
			new MicrosoftTranslatorFactoryUtil();

		microsoftTranslatorFactoryUtil.setMicrosoftTranslatorFactory(
			new MicrosoftTranslatorFactoryImpl());

		ModelHintsUtil modelHintsUtil = new ModelHintsUtil();

		ModelHintsImpl modelHintsImpl = new ModelHintsImpl();

		SAXReaderImpl saxReaderImpl = new SAXReaderImpl();

		modelHintsImpl.setSAXReader(saxReaderImpl);
		modelHintsImpl.afterPropertiesSet();

		modelHintsUtil.setModelHints(modelHintsImpl);

		SingleVMPoolUtil singleVMPoolUtil = new SingleVMPoolUtil();

		MemoryPortalCacheManager<String, String> memoryPortalCacheManager =
			new MemoryPortalCacheManager<String, String>();

		memoryPortalCacheManager.afterPropertiesSet();

		PortletPermissionUtil portletPermissionUtil =
			new PortletPermissionUtil();

		portletPermissionUtil.setPortletPermission(new PortletPermissionImpl());

		SAXReaderUtil saxReaderUtil = new SAXReaderUtil();

		saxReaderUtil.setSAXReader(saxReaderImpl);

		SingleVMPoolImpl singleVMPoolImpl = new SingleVMPoolImpl();

		singleVMPoolImpl.setPortalCacheManager(memoryPortalCacheManager);

		singleVMPoolUtil.setSingleVMPool(singleVMPoolImpl);
	}

}