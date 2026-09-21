/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.template.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateManager;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collection;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * @author Dante Wang
 * @author Debora Buriti
 * @author Tina Tian
 */
@RunWith(Arquillian.class)
public class TemplateRestrictedVariablesTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetRestrictedVariables() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			TemplateRestrictedVariablesTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		Collection<ServiceReference<TemplateManager>> serviceReferences =
			bundleContext.getServiceReferences(TemplateManager.class, null);

		Assert.assertFalse(serviceReferences.isEmpty());

		for (ServiceReference<TemplateManager> serviceReference :
				serviceReferences) {

			TemplateManager templateManager = bundleContext.getService(
				serviceReference);

			try {
				String name = templateManager.getName();
				Template template = templateManager.getTemplate(
					new StringTemplateResource(
						RandomTestUtil.randomString(),
						RandomTestUtil.randomString()),
					true);

				for (String restrictedVariable :
						templateManager.getRestrictedVariables()) {

					Assert.assertFalse(
						restrictedVariable + " accessible in " + name,
						template.containsKey(restrictedVariable));

					template.put(
						restrictedVariable, RandomTestUtil.randomString());

					Assert.assertFalse(
						restrictedVariable + " accessible in " + name,
						template.containsKey(restrictedVariable));
				}
			}
			finally {
				bundleContext.ungetService(serviceReference);
			}
		}
	}

	@Test
	public void testRestrictedPortletModel() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			TemplateRestrictedVariablesTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		Collection<ServiceReference<TemplateManager>> serviceReferences =
			bundleContext.getServiceReferences(
				TemplateManager.class,
				"(language.type=" + TemplateConstants.LANG_TYPE_FTL + ")");

		Assert.assertFalse(serviceReferences.isEmpty());

		Portlet portlet = new PortletImpl(
			TestPropsValues.getCompanyId(), RandomTestUtil.randomString());

		for (ServiceReference<TemplateManager> serviceReference :
				serviceReferences) {

			TemplateManager templateManager = bundleContext.getService(
				serviceReference);

			try {
				Template template = templateManager.getTemplate(
					new StringTemplateResource(
						RandomTestUtil.randomString(), "${portlet}"),
					true);

				template.put("portlet", portlet);

				UnsyncStringWriter unsyncStringWriter =
					new UnsyncStringWriter();

				template.processTemplate(unsyncStringWriter);

				Assert.assertEquals(
					"Denied access to the toString method in class " +
						portlet.getClass(),
					unsyncStringWriter.toString());

				template = templateManager.getTemplate(
					new StringTemplateResource(
						RandomTestUtil.randomString(), "${portlet.portletId}"),
					true);

				template.put("portlet", portlet);

				try {
					template.processTemplate(new UnsyncStringWriter());

					Assert.fail();
				}
				catch (TemplateException templateException) {
					Throwable throwable = templateException.getCause();

					String message = throwable.getMessage();

					Assert.assertTrue(
						message,
						message.contains(
							"Denied access to method or field portletId of " +
								portlet.getClass()));
				}

				template = templateManager.getTemplate(
					new StringTemplateResource(
						RandomTestUtil.randomString(), "${portlet.portletId}"),
					false);

				template.put("portlet", portlet);

				unsyncStringWriter = new UnsyncStringWriter();

				template.processTemplate(unsyncStringWriter);

				Assert.assertEquals(
					portlet.getPortletId(), unsyncStringWriter.toString());
			}
			finally {
				bundleContext.ungetService(serviceReference);
			}
		}
	}

}