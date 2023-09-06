/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.model.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.search.experiences.rest.dto.v1_0.SXPElement;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author André de Oliveira
 */
@RunWith(Arquillian.class)
public class CompanyModelListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testSXPElements() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(_modelListener.getClass());

		Class<?> clazz = bundle.loadClass(
			"com.liferay.search.experiences.internal.util.SXPElementUtil");

		List<SXPElement> sxpElements = ReflectionTestUtil.getFieldValue(
			clazz, "_sxpElements");

		Assert.assertNotEquals(0, sxpElements.size());

		for (SXPElement sxpElement : sxpElements) {
			Assert.assertNotNull(sxpElement);
		}
	}

	@Inject(
		filter = "component.name=com.liferay.search.experiences.internal.model.listener.CompanyModelListener"
	)
	private ModelListener<?> _modelListener;

}