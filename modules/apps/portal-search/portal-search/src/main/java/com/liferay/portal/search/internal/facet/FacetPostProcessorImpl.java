/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.facet;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.FacetPostProcessor;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;

import java.util.Collection;

import org.osgi.service.component.annotations.Component;

/**
 * @author Bryan Engler
 * @author André de Oliveira
 */
@Component(service = FacetPostProcessor.class)
public class FacetPostProcessorImpl implements FacetPostProcessor {

	@Override
	public void exclude(Collection<Document> documents, Facet facet) {
		FacetCollector facetCollector = facet.getFacetCollector();

		if (facetCollector == null) {
			return;
		}

		FacetDiscounter facetDiscounter = new FacetDiscounter(facet);

		facetDiscounter.discount(documents);
	}

}