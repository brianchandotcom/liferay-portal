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

package com.liferay.wiki.internal.change.tracking.spi.resolver;

import com.liferay.change.tracking.spi.resolver.ConstraintResolver;
import com.liferay.change.tracking.spi.resolver.context.ConstraintResolverContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;
import com.liferay.portal.kernel.util.MathUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.language.LanguageResources;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiPageLocalService;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(service = ConstraintResolver.class)
public class WikiPageVersionConstraintResolver
	implements ConstraintResolver<WikiPage> {

	@Override
	public String getConflictDescriptionKey() {
		return "duplicate-wiki-page-version";
	}

	@Override
	public Class<WikiPage> getModelClass() {
		return WikiPage.class;
	}

	@Override
	public String getResolutionDescriptionKey() {
		return "the-wiki-page-version-was-updated-to-latest";
	}

	@Override
	public ResourceBundle getResourceBundle(Locale locale) {
		return LanguageResources.getResourceBundle(locale);
	}

	@Override
	public String[] getUniqueIndexColumnNames() {
		return new String[] {"resourcePrimKey", "nodeId", "version"};
	}

	@Override
	public void resolveConflict(
			ConstraintResolverContext<WikiPage> constraintResolverContext)
		throws PortalException {

		WikiPage ctPage = constraintResolverContext.getSourceCTModel();

		WikiPage prodPage = constraintResolverContext.getInTarget(
			() -> wikiPageLocalService.getLatestPage(
				ctPage.getResourcePrimKey(), WorkflowConstants.STATUS_ANY,
				false));

		ctPage.setVersion(MathUtil.format(prodPage.getVersion() + 0.1, 1, 1));

		wikiPageLocalService.updateWikiPage(ctPage);

		CTPersistence<WikiPage> ctPersistence =
			wikiPageLocalService.getCTPersistence();

		ctPersistence.flush();

		if (ctPage.isHead()) {
			prodPage.setHead(false);

			wikiPageLocalService.updateWikiPage(prodPage);

			ctPersistence.flush();
		}
	}

	@Reference
	protected WikiPageLocalService wikiPageLocalService;

}