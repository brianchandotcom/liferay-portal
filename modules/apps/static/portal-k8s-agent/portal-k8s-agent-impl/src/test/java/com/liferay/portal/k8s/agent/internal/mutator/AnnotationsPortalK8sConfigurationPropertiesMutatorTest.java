/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
				"ext.lxc.liferay.com.domains", StringUtil.merge(domains, "\n")
			).build(),
			HashMapBuilder.<String, String>create(
				0
			).build(),
			properties);

		Assert.assertArrayEquals(
			domains, (String[])properties.get("ext.lxc.liferay.com.domains"));
	}

	@Rule
	public LiferayUnitTestRule liferayUnitTestRule = new LiferayUnitTestRule();

}