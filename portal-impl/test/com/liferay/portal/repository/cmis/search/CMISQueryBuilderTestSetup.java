/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.repository.cmis.search;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.search.generic.BooleanQueryFactoryImpl;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.util.InitUtil;
import com.liferay.portal.util.PropsUtil;

import junit.extensions.TestSetup;

import junit.framework.Test;

/**
 * @author Mika Koivisto
 */
public class CMISQueryBuilderTestSetup extends TestSetup {

	CMISQueryBuilderTestSetup(Test test) {
		super(test);
	}

	@Override
	public void setUp() {
		PropsUtil.set("spring.configs",
			"META-INF/management-spring.xml,META-INF/util-spring.xml," +
			"META-INF/messaging-core-spring.xml," +
			"META-INF/messaging-misc-spring.xml,META-INF/search-spring.xml," +
			"META-INF/service-builder-spring.xml");
		PropsUtil.set(
			PropsKeys.RESOURCE_ACTIONS_READ_PORTLET_RESOURCES, "false");
		PropsUtil.set(
			PropsKeys.EHCACHE_PORTAL_CACHE_MANAGER_JMX_ENABLED, "false");
		PropsUtil.set(PropsKeys.RESOURCE_ACTIONS_CONFIGS, "");

		InitUtil.initWithSpring();

		BooleanQueryFactoryUtil util = new BooleanQueryFactoryUtil();

		for (java.lang.reflect.Field field :
			util.getClass().getDeclaredFields()) {

			BeanReference ref = field.getAnnotation(BeanReference.class);

			if (ref != null) {
				field.setAccessible(true);

				try {
					field.set(util, util);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		util.setGenericQueryFactory(new BooleanQueryFactoryImpl());
	}

	@Override
	public void tearDown() {
		ServiceTestUtil.destroyServices();
	}

}