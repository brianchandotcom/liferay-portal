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

package com.liferay.journal.web.internal.change.tracking.spi.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.journal.model.JournalArticleResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Gislayne Vitorino
 */
@Component(service = CTDisplayRenderer.class)
public class JournalArticleResourceCTDisplayRenderer
	extends BaseCTDisplayRenderer<JournalArticleResource> {

	@Override
	public Class<JournalArticleResource> getModelClass() {
		return JournalArticleResource.class;
	}

	@Override
	public String getTitle(
			Locale locale, JournalArticleResource journalArticleResource)
		throws PortalException {

		return StringBundler.concat(
			journalArticleResource.getModelClassName(), " ",
			journalArticleResource.getResourcePrimKey());
	}

	@Override
	public boolean isHideable(JournalArticleResource journalArticleResource) {
		return true;
	}

	@Override
	protected void buildDisplay(
		DisplayBuilder<JournalArticleResource> displayBuilder) {

		JournalArticleResource journalArticleResource =
			displayBuilder.getModel();

		displayBuilder.display(
			"mvcc-version", journalArticleResource.getMvccVersion()
		).display(
			"ct-collection-id", journalArticleResource.getCtCollectionId()
		).display(
			"uuid", journalArticleResource.getUuid()
		).display(
			"resource-prim-key", journalArticleResource.getResourcePrimKey()
		).display(
			"group-id", journalArticleResource.getGroupId()
		).display(
			"company-id", journalArticleResource.getCompanyId()
		).display(
			"article-id", journalArticleResource.getArticleId()
		);
	}

}