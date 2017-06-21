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

package com.liferay.portal.template.soy.internal;

import com.google.common.io.CharStreams;
import com.google.template.soy.SoyFileSet;
import com.google.template.soy.SoyFileSet.Builder;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.URLTemplateResource;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Reader;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * @author Marcellus Tavares
 * @author Bruno Basto
 */
public class SoyTestHelper {

	public SoyManager getSoyManager() {
		return _soyManager;
	}

	public SoyTemplate getSoyTemplate(
		List<TemplateResource> templateResources) {

		return (SoyTemplate)_soyManager.getTemplate(templateResources, false);
	}

	public SoyTemplate getSoyTemplate(String fileName) {
		TemplateResource templateResource = getTemplateResource(fileName);

		return (SoyTemplate)_soyManager.getTemplate(templateResource, false);
	}

	public SoyTemplate getSoyTemplate(String... fileNames) {
		List<TemplateResource> templateResources = getTemplateResources(
			Arrays.asList(fileNames));

		return getSoyTemplate(templateResources);
	}

	public void setUp() throws Exception {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());

		setUpSoyManager();
	}

	public void tearDown() {
		_soyManager.destroy();
	}

	protected SoyFileSet getSoyFileSet(List<TemplateResource> templateResources)
		throws Exception {

		Builder builder = SoyFileSet.builder();

		for (TemplateResource templateResource : templateResources) {
			Reader reader = templateResource.getReader();

			builder.add(
				CharStreams.toString(reader), templateResource.getTemplateId());
		}

		return builder.build();
	}

	protected TemplateResource getTemplateResource(String name) {
		TemplateResource templateResource = null;

		String resource = _TPL_PATH.concat(name);

		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		URL url = classLoader.getResource(resource);

		if (url != null) {
			templateResource = new URLTemplateResource(resource, url);
		}

		return templateResource;
	}

	protected List<TemplateResource> getTemplateResources(
		List<String> fileNames) {

		List<TemplateResource> templateResources = new ArrayList<>();

		for (String fileName : fileNames) {
			templateResources.add(getTemplateResource(fileName));
		}

		return templateResources;
	}

	protected PortalCache mockPortalCache() {
		PortalCache portalCache = Mockito.mock(PortalCache.class);

		Map<HashSet<TemplateResource>, SoyTofuCacheBag> cache = new HashMap<>();

		Mockito.when(
			portalCache.get(Matchers.any())
		).then(
			new Answer<SoyTofuCacheBag>() {

				@Override
				public SoyTofuCacheBag answer(InvocationOnMock invocationOnMock)
					throws Throwable {

					Object[] args = invocationOnMock.getArguments();

					HashSet<TemplateResource> key =
						(HashSet<TemplateResource>)args[0];

					return cache.get(key);
				}

			}
		);

		Mockito.when(
			portalCache.getKeys()
		).then(
			new Answer<List<HashSet<TemplateResource>>>() {

				@Override
				public List<HashSet<TemplateResource>> answer(
						InvocationOnMock invocationOnMock)
					throws Throwable {

					List<HashSet<TemplateResource>> list = new ArrayList<>(
						cache.keySet());

					return list;
				}

			}
		);

		Mockito.doAnswer(
			new Answer<Void>() {

				@Override
				public Void answer(InvocationOnMock invocationOnMock)
					throws Throwable {

					Object[] args = invocationOnMock.getArguments();

					HashSet<TemplateResource> key =
						(HashSet<TemplateResource>)args[0];

					SoyTofuCacheBag value = (SoyTofuCacheBag)args[1];

					cache.put(key, value);

					return null;
				}

			}
		).when(
			portalCache
		).put(
			Mockito.any(), Mockito.any()
		);

		Mockito.doAnswer(
			new Answer<Void>() {

				@Override
				public Void answer(InvocationOnMock invocationOnMock)
					throws Throwable {

					Object[] args = invocationOnMock.getArguments();

					HashSet<TemplateResource> key =
						(HashSet<TemplateResource>)args[0];

					cache.remove(key);

					return null;
				}

			}
		).when(
			portalCache
		).remove(
			Mockito.any()
		);

		return portalCache;
	}

	protected void setUpSoyManager() throws Exception {
		_soyManager = new SoyManager();

		_soyManager.setTemplateContextHelper(new SoyTemplateContextHelper());

		_soyManager.setSingleVMPool(
			(SingleVMPool)ProxyUtil.newProxyInstance(
				SingleVMPool.class.getClassLoader(),
				new Class<?>[] {SingleVMPool.class},
				new InvocationHandler() {

					@Override
					public Object invoke(
							Object proxy, Method method, Object[] args)
						throws Throwable {

						if ("getPortalCache".equals(method.getName())) {
							return mockPortalCache();
						}

						throw new UnsupportedOperationException(
							method.toString());
					}

				}));
	}

	private static final String _TPL_PATH =
		"com/liferay/portal/template/soy/dependencies/";

	private SoyManager _soyManager;

}