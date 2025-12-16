/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.spi.model.index.contributor;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentContributor;
import com.liferay.portal.search.expando.ExpandoBridgeIndexer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = DocumentContributor.class)
public class ExpandoBridgeDocumentContributor
	implements DocumentContributor<ExpandoBridge> {

	@Override
	public void contribute(
		Document document, BaseModel<ExpandoBridge> baseModel) {

		expandoBridgeIndexer.addAttributes(
			document, baseModel.getExpandoBridge());
	}

	@Reference
	protected ExpandoBridgeIndexer expandoBridgeIndexer;

}