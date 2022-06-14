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

package com.liferay.portal.k8s.agent.internal.mutator;

import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Dictionary;
import java.util.Hashtable;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Raymond Augé
 */
public class AnnotationsPortalK8sConfigurationPropertiesMutatorTest {

	@Test
	public void testParseDomains() {
		AnnotationsPortalK8sConfigurationPropertiesMutator mutator =
			new AnnotationsPortalK8sConfigurationPropertiesMutator();

		String[] domains = {"ext.domain.example", "other.domain.example"};

		Dictionary<String, Object> properties = new Hashtable<>();

		mutator.mutateConfigurationProperties(
			HashMapBuilder.put(
				"com.liferay.lxc.ext.domains", StringUtil.merge(domains, "\n")
			).build(),
			HashMapBuilder.<String, String>create(
				0
			).build(),
			properties);

		Assert.assertArrayEquals(
			domains, (String[])properties.get("com.liferay.lxc.ext.domains"));
	}

	@Rule
	private LiferayUnitTestRule _liferayUnitTestRule =
		new LiferayUnitTestRule();

}