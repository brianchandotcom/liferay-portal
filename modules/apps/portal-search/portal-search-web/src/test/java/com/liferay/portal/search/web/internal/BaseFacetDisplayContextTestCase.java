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

package com.liferay.portal.search.web.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.internal.custom.facet.configuration.CustomFacetPortletInstanceConfiguration;
import com.liferay.portal.search.web.internal.facet.display.context.BucketDisplayContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

import org.mockito.Mockito;

/**
 * @author Amanda Costa
 */
public abstract class BaseFacetDisplayContextTestCase {

	protected static String buildNameFrequencyString(
		List<BucketDisplayContext> bucketDisplayContexts) {

		StringBundler sb = new StringBundler(bucketDisplayContexts.size() * 4);

		for (BucketDisplayContext bucketDisplayContext :
				bucketDisplayContexts) {

			sb.append(bucketDisplayContext.getBucketText());
			sb.append(StringPool.COLON);
			sb.append(bucketDisplayContext.getFrequency());
			sb.append(StringPool.PIPE);
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	protected static TermCollector createTermCollector(
		String term, int frequency) {

		TermCollector termCollector = Mockito.mock(TermCollector.class);

		Mockito.doReturn(
			frequency
		).when(
			termCollector
		).getFrequency();

		Mockito.doReturn(
			term
		).when(
			termCollector
		).getTerm();

		return termCollector;
	}

	protected static HttpServletRequest getHttpServletRequest()
		throws ConfigurationException {

		HttpServletRequest httpServletRequest = Mockito.mock(
			HttpServletRequest.class);

		Mockito.doReturn(
			getThemeDisplay(CustomFacetPortletInstanceConfiguration.class)
		).when(
			httpServletRequest
		).getAttribute(
			WebKeys.THEME_DISPLAY
		);

		return httpServletRequest;
	}

	protected static PortletDisplay getPortletDisplay(
			Class<?> facetPortletConfiguration)
		throws ConfigurationException {

		PortletDisplay portletDisplay = Mockito.mock(PortletDisplay.class);

		Mockito.doReturn(
			Mockito.mock(facetPortletConfiguration)
		).when(
			portletDisplay
		).getPortletInstanceConfiguration(
			Mockito.any()
		);

		return portletDisplay;
	}

	protected static RenderRequest getRenderRequest(
			Class<?> facetPortletConfiguration)
		throws ConfigurationException {

		RenderRequest renderRequest = Mockito.mock(RenderRequest.class);

		Mockito.doReturn(
			getThemeDisplay(facetPortletConfiguration)
		).when(
			renderRequest
		).getAttribute(
			WebKeys.THEME_DISPLAY
		);

		return renderRequest;
	}

	protected static List<TermCollector> getTermCollectors(
		String[] terms, int[] frequencies) {

		List<TermCollector> termCollectors = new ArrayList<>();

		for (int i = 0; i < terms.length; i++) {
			termCollectors.add(createTermCollector(terms[i], frequencies[i]));
		}

		return termCollectors;
	}

	protected static ThemeDisplay getThemeDisplay(
			Class<?> facetPortletConfiguration)
		throws ConfigurationException {

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.doReturn(
			getPortletDisplay(facetPortletConfiguration)
		).when(
			themeDisplay
		).getPortletDisplay();

		return themeDisplay;
	}

	protected static void setUpTermCollector(
		FacetCollector facetCollector, String term, int frequency) {

		Mockito.doReturn(
			Collections.singletonList(createTermCollector(term, frequency))
		).when(
			facetCollector
		).getTermCollectors();
	}

	protected static void setUpTermCollectors(
		FacetCollector facetCollector, List<TermCollector> termCollectors) {

		Mockito.doReturn(
			termCollectors
		).when(
			facetCollector
		).getTermCollectors();
	}

}