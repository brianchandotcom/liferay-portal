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

package com.liferay.journal.internal.info.provider;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.kernel.DDMStructureLink;
import com.liferay.dynamic.data.mapping.kernel.DDMStructureLinkManager;
import com.liferay.dynamic.data.mapping.kernel.DDMStructureManager;
import com.liferay.dynamic.data.mapping.kernel.DDMTemplate;
import com.liferay.info.provider.InfoObjectDDMTemplateProvider;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	property = "model.class.name=com.liferay.journal.model.JournalArticle",
	service = InfoObjectDDMTemplateProvider.class
)
public class JournalArticleInfoObjectDDMTemplateProviderImpl
	implements InfoObjectDDMTemplateProvider {

	@Override
	public List<DDMTemplate> getDDMTemplates(long classPK)
		throws PortalException {

		AssetRendererFactory<JournalArticle> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClass(
				JournalArticle.class);

		AssetRenderer<JournalArticle> assetRenderer =
			assetRendererFactory.getAssetRenderer(classPK);

		JournalArticle article = assetRenderer.getAssetObject();

		List<DDMStructureLink> ddmStructureLinks =
			_ddmStructureLinkManager.getStructureLinks(
				_portal.getClassNameId(JournalArticle.class.getName()),
				article.getId());

		Stream<DDMStructureLink> stream = ddmStructureLinks.stream();

		return stream.flatMap(
			ddmStructureLink -> {
				DDMStructure ddmStructure = _ddmStructureManager.fetchStructure(
					ddmStructureLink.getStructureId());

				try {
					List<DDMTemplate> ddmTemplates =
						ddmStructure.getTemplates();

					return ddmTemplates.stream();
				}
				catch (PortalException pe) {
					_log.error("Unable to get DDM templates", pe);
				}

				return Stream.empty();
			}
		).collect(
			Collectors.toList()
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleInfoObjectDDMTemplateProviderImpl.class);

	@Reference
	private DDMStructureLinkManager _ddmStructureLinkManager;

	@Reference
	private DDMStructureManager _ddmStructureManager;

	@Reference
	private Portal _portal;

}