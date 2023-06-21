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

package com.liferay.headless.builder.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.batch.engine.unit.BatchEngineUnitProcessor;
import com.liferay.batch.engine.unit.BatchEngineUnitReader;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public abstract class BaseTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {

		// TODO Delete the bundle deployment when the FF LPS-184413 is removed

		Bundle testBundle = FrameworkUtil.getBundle(BaseTestCase.class);

		BundleContext bundleContext = testBundle.getBundleContext();

		for (Bundle bundle : bundleContext.getBundles()) {
			if (Objects.equals(
					bundle.getSymbolicName(),
					"com.liferay.headless.builder.impl")) {

				CompletableFuture<Void> completableFuture =
					_batchEngineUnitProcessor.processBatchEngineUnits(
						_batchEngineUnitReader.getBatchEngineUnits(bundle));

				completableFuture.join();
			}
		}

		_beforeTestApplicationERCs = _getApplicationERCs();
	}

	@After
	public void tearDown() throws Exception {
		Set<String> applicationERCs = _getApplicationERCs();

		applicationERCs.removeAll(_beforeTestApplicationERCs);

		for (String externalReferenceCode : applicationERCs) {
			HTTPTestUtil.invoke(
				null,
				"headless-builder/applications/by-external-reference-code/" +
					externalReferenceCode,
				Http.Method.DELETE);
		}
	}

	private Set<String> _getApplicationERCs() throws Exception {
		JSONObject jsonObject = HTTPTestUtil.invoke(
			null, "headless-builder/applications", Http.Method.GET);

		return JSONUtil.toStringSet(
			jsonObject.getJSONArray("items"), "externalReferenceCode");
	}

	@Inject
	private BatchEngineUnitProcessor _batchEngineUnitProcessor;

	@Inject
	private BatchEngineUnitReader _batchEngineUnitReader;

	private Set<String> _beforeTestApplicationERCs;

}