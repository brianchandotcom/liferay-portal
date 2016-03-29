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

package com.liferay.portal.search.internal.facet;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.FacetBuilder;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.facet.FacetFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author André de Oliveira
 */
public class FacetManagerImplTest {

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		Mockito.doReturn(
			_facet
		).when(
			_facetFactory
		).newInstance(Mockito.<SearchContext>any());
	}

	@Test
	public void testCreate() throws Exception {
		String className = RandomTestUtil.randomString();

		setUpFacetManagerImpl(className);

		FacetConfiguration facetConfiguration = new FacetConfiguration();

		facetConfiguration.setClassName(className);

		Assert.assertSame(_facet, buildFacet(facetConfiguration));
	}

	@Test(expected = NullPointerException.class)
	public void testEmptyFacetConfiguration() throws Exception {
		buildFacet(new FacetConfiguration());
	}

	@Test
	public void testMissingFacetFactory() throws Exception {
		FacetConfiguration facetConfiguration = new FacetConfiguration();

		facetConfiguration.setClassName(RandomTestUtil.randomString());

		Assert.assertNull(buildFacet(facetConfiguration));
	}

	@Test
	public void testNoFacetConfiguration() throws Exception {
		String className = RandomTestUtil.randomString();

		setUpFacetManagerImpl(className);

		FacetBuilder facetBuilder = _facetManagerImpl.createFacetBuilder(
			className, null);

		Facet facet = facetBuilder.build();

		Assert.assertSame(_facet, facet);

		Mockito.verify(
			_facet, Mockito.never()
		).setFacetConfiguration(
			Mockito.<FacetConfiguration>any()
		);
	}

	protected Facet buildFacet(FacetConfiguration facetConfiguration) {
		FacetBuilder facetBuilder = _facetManagerImpl.createFacetBuilder(
			facetConfiguration.getClassName(), null);

		facetBuilder.setFacetConfiguration(facetConfiguration);

		return facetBuilder.build();
	}

	protected void setUpFacetManagerImpl(String className) {
		Mockito.doReturn(
			className
		).when(
			_facetFactory
		).getFacetClassName();

		_facetManagerImpl.addFacetFactory(_facetFactory);
	}

	@Mock
	private Facet _facet;

	@Mock
	private FacetFactory _facetFactory;

	private final FacetManagerImpl _facetManagerImpl = new FacetManagerImpl();

}