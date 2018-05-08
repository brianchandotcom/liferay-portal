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

package com.liferay.portal.search.internal.contributor.document;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeIndexer;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentContributor;
import com.liferay.portal.search.spi.model.index.contributor.ExpandoBridgeRetriever;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = DocumentContributor.class)
public class ExpandoBridgeDocumentContributor implements DocumentContributor {

	@Override
	public void contribute(Document document, BaseModel baseModel) {
		ExpandoBridgeRetriever expandoBridgeRetriever =
			_expandoBridgeIndexers.getService(baseModel.getModelClassName());

		ExpandoBridge expandoBridge = null;

		if (expandoBridgeRetriever != null) {
			expandoBridge = expandoBridgeRetriever.getExpandoBridge(baseModel);
		}
		else {
			expandoBridge = baseModel.getExpandoBridge();
		}

		expandoBridgeIndexer.addAttributes(document, expandoBridge);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_expandoBridgeIndexers = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ExpandoBridgeRetriever.class, "indexer.class.name");
	}

	@Deactivate
	protected void deactivate() {
		_expandoBridgeIndexers.close();
	}

	@Reference
	protected ExpandoBridgeIndexer expandoBridgeIndexer;

	private ServiceTrackerMap<String, ExpandoBridgeRetriever>
		_expandoBridgeIndexers;

}