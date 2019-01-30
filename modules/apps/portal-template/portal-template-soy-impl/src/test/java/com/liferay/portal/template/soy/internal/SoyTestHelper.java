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

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheListener;
import com.liferay.portal.kernel.cache.PortalCacheListenerScope;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.cache.SingleVMPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.URLTemplateResource;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Reader;
import java.io.Serializable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

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

		SoyFileSet.Builder builder = SoyFileSet.builder();

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
		Map<HashSet<TemplateResource>, SoyTofuCacheBag> cache = new HashMap<>();

		return new PortalCache() {

			@Override
			public Object get(Serializable key) {
				return cache.get(key);
			}

			@Override
			public List getKeys() {
				return new ArrayList<>(cache.keySet());
			}

			@Override
			public String getName() {
				return null;
			}

			@Override
			public PortalCacheManager getPortalCacheManager() {
				return null;
			}

			@Override
			public String getPortalCacheName() {
				return null;
			}

			@Override
			public void put(Serializable key, Object value) {
				cache.put(
					(HashSet<TemplateResource>)key, (SoyTofuCacheBag)value);
			}

			@Override
			public void put(Serializable key, Object value, int timeToLive) {
			}

			@Override
			public void registerPortalCacheListener(
				PortalCacheListener portalCacheListener) {
			}

			@Override
			public void registerPortalCacheListener(
				PortalCacheListener portalCacheListener,
				PortalCacheListenerScope portalCacheListenerScope) {
			}

			@Override
			public void remove(Serializable key) {
				cache.remove(key);
			}

			@Override
			public void removeAll() {
			}

			@Override
			public void unregisterPortalCacheListener(
				PortalCacheListener portalCacheListener) {
			}

			@Override
			public void unregisterPortalCacheListeners() {
			}

		};
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