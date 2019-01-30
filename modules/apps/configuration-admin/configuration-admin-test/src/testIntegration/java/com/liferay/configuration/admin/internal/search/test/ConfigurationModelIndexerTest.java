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

package com.liferay.configuration.admin.internal.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.osgi.util.service.OSGiServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition.Scope;
import com.liferay.portal.configuration.metatype.definitions.ExtendedAttributeDefinition;
import com.liferay.portal.configuration.metatype.definitions.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.IOException;
import java.io.InputStream;

import java.lang.reflect.Constructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Pei-Jung Lan
 */
@RunWith(Arquillian.class)
public class ConfigurationModelIndexerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_bundle = FrameworkUtil.getBundle(ConfigurationModelIndexerTest.class);

		_bundleContext = _bundle.getBundleContext();

		Bundle configAdminWebBundle = _getBundle(
			"com.liferay.configuration.admin.web");

		Class<?> configurationModelClass = configAdminWebBundle.loadClass(
			"com.liferay.configuration.admin.web.internal.model." +
				"ConfigurationModel");

		_configurationModelConstructor = configurationModelClass.getConstructor(
			ExtendedObjectClassDefinition.class, Configuration.class,
			String.class, String.class, boolean.class);
	}

	@After
	public void tearDown() throws SearchException {
		_indexWriterHelper.deleteDocument(
			_indexer.getSearchEngineId(), CompanyConstants.SYSTEM,
			_document.getUID(), true);
	}

	@Test
	public void testSearchAfterReindex() throws Exception {
		Configuration configuration = _addCompanyFactoryConfiguration(
			"test.pid");

		SearchContext searchContext = new SearchContext();

		searchContext.setAndSearch(false);
		searchContext.setCompanyId(CompanyConstants.SYSTEM);
		searchContext.setKeywords("test.pid");
		searchContext.setLocale(LocaleUtil.US);

		Hits hits = _indexer.search(searchContext);

		Assert.assertEquals(hits.toString(), 1, hits.getLength());

		configuration.delete();

		_indexer.reindex(new String[0]);

		hits = _indexer.search(searchContext);

		Assert.assertEquals(hits.toString(), 0, hits.getLength());
	}

	private Configuration _addCompanyFactoryConfiguration(String factoryPid)
		throws Exception {

		Configuration configuration = OSGiServiceUtil.callService(
			_bundleContext, ConfigurationAdmin.class,
			configurationAdmin -> configurationAdmin.createFactoryConfiguration(
				factoryPid, StringPool.QUESTION));

		Map<String, String> extensionAttributes = new HashMap<>();

		extensionAttributes.put("factoryInstanceLabelAttribute", "companyId");
		extensionAttributes.put("scope", Scope.COMPANY.toString());

		ExtendedObjectClassDefinition extendedObjectClassDefinition =
			new SimpleExtendedObjectClassDefinition(
				configuration, extensionAttributes);

		Object configurationModel = _configurationModelConstructor.newInstance(
			extendedObjectClassDefinition, configuration,
			_bundle.getSymbolicName(), StringPool.QUESTION, true);

		_document = _indexer.getDocument(configurationModel);

		_indexWriterHelper.addDocument(
			_indexer.getSearchEngineId(), CompanyConstants.SYSTEM, _document,
			true);

		return configuration;
	}

	private Bundle _getBundle(String bundleSymbolicName) {
		Bundle[] bundles = _bundleContext.getBundles();

		for (Bundle bundle : bundles) {
			if (bundleSymbolicName.equals(bundle.getSymbolicName())) {
				return bundle;
			}
		}

		return null;
	}

	private Bundle _bundle;
	private BundleContext _bundleContext;
	private Constructor _configurationModelConstructor;
	private Document _document;

	@Inject(filter = "component.name=*.ConfigurationModelIndexer")
	private Indexer _indexer;

	@Inject
	private IndexWriterHelper _indexWriterHelper;

	private class SimpleExtendedObjectClassDefinition
		implements ExtendedObjectClassDefinition {

		public SimpleExtendedObjectClassDefinition(
			Configuration configuration,
			Map<String, String> extensionAttributes) {

			_configuration = configuration;
			_extensionAttributes = extensionAttributes;
		}

		@Override
		public ExtendedAttributeDefinition[] getAttributeDefinitions(
			int filter) {

			return new ExtendedAttributeDefinition[0];
		}

		@Override
		public String getDescription() {
			return null;
		}

		@Override
		public Map<String, String> getExtensionAttributes(String uri) {
			return _extensionAttributes;
		}

		@Override
		public Set<String> getExtensionUris() {
			return null;
		}

		@Override
		public InputStream getIcon(int size) throws IOException {
			return null;
		}

		@Override
		public String getID() {
			return _configuration.getFactoryPid();
		}

		@Override
		public String getName() {
			return null;
		}

		private final Configuration _configuration;
		private final Map<String, String> _extensionAttributes;

	}

}