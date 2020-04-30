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

package com.liferay.layout.internal.search.spi.model.index.contributor;

import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.layout.internal.search.util.LayoutPageTemplateStructureRenderUtil;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.servlet.DynamicServletRequest;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.staging.StagingGroupHelper;

import java.util.HashMap;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Vagner B.C
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.portal.kernel.model.Layout",
	service = ModelDocumentContributor.class
)
public class LayoutModelDocumentContributor
	implements ModelDocumentContributor<Layout> {

	public static final String CLASS_NAME = Layout.class.getName();

	@Override
	public void contribute(Document document, Layout layout) {
		if (layout.isSystem() ||
			(layout.getStatus() != WorkflowConstants.STATUS_APPROVED)) {

			return;
		}

		document.addText(
			Field.DEFAULT_LANGUAGE_ID, layout.getDefaultLanguageId());
		document.addLocalizedText(Field.NAME, layout.getNameMap());
		document.addText(
			"privateLayout", String.valueOf(layout.isPrivateLayout()));
		document.addText(Field.TYPE, layout.getType());

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layout.getGroupId(), _portal.getClassNameId(Layout.class),
					layout.getPlid());

		for (String languageId : layout.getAvailableLanguageIds()) {
			Locale locale = LocaleUtil.fromLanguageId(languageId);

			document.addText(
				Field.getLocalizedName(locale, Field.TITLE),
				layout.getName(locale));
		}

		if (layoutPageTemplateStructure == null) {
			return;
		}

		HttpServletRequest httpServletRequest = null;
		HttpServletResponse httpServletResponse = null;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if ((serviceContext != null) && (serviceContext.getRequest() != null)) {
			httpServletRequest = DynamicServletRequest.addQueryString(
				serviceContext.getRequest(), "p_l_id=" + layout.getPlid(),
				false);
			httpServletResponse = serviceContext.getResponse();
		}

		long[] segmentsExperienceIds = {SegmentsExperienceConstants.ID_DEFAULT};

		Set<Locale> locales = LanguageUtil.getAvailableLocales(
			layout.getGroupId());

		for (Locale locale : locales) {
			try {
				String content = StringPool.BLANK;

				if ((httpServletRequest == null) ||
					(httpServletResponse == null)) {

					content = _getStagedContent(layout, locale);
				}
				else {
					content =
						LayoutPageTemplateStructureRenderUtil.
							renderLayoutContent(
								_fragmentRendererController, httpServletRequest,
								httpServletResponse,
								layoutPageTemplateStructure,
								FragmentEntryLinkConstants.VIEW,
								new HashMap<>(), locale, segmentsExperienceIds);
				}

				if (Validator.isNull(content)) {
					continue;
				}

				document.addText(
					Field.getLocalizedName(locale, Field.CONTENT), content);
			}
			catch (PortalException portalException) {
				throw new SystemException(portalException);
			}
		}
	}

	private String _getStagedContent(Layout layout, Locale locale)
		throws PortalException {

		Group group = _groupLocalService.getGroup(layout.getGroupId());

		Group stagingGroup = null;

		if (ExportImportThreadLocal.isInitialLayoutStagingInProcess()) {
			stagingGroup = _stagingGroupHelper.fetchLiveGroup(group);
		}
		else if (!group.isStaged() || group.isStagingGroup()) {
			stagingGroup = group;
		}
		else {
			stagingGroup = group.getStagingGroup();
		}

		Layout stagingLayout = _layoutLocalService.fetchLayoutByUuidAndGroupId(
			layout.getUuid(), stagingGroup.getGroupId(),
			layout.isPrivateLayout());

		SearchContext searchContext = new SearchContext();

		BooleanClause booleanClause = BooleanClauseFactoryUtil.create(
			Field.ENTRY_CLASS_PK, String.valueOf(stagingLayout.getPlid()),
			BooleanClauseOccur.MUST.getName());

		searchContext.setBooleanClauses(new BooleanClause[] {booleanClause});

		searchContext.setCompanyId(stagingGroup.getCompanyId());
		searchContext.setGroupIds(new long[] {stagingGroup.getGroupId()});
		searchContext.setEntryClassNames(new String[] {Layout.class.getName()});

		Indexer indexer = IndexerRegistryUtil.getIndexer(
			Layout.class.getName());

		Hits hits = indexer.search(searchContext);

		Document[] documents = hits.getDocs();

		if (documents.length != 1) {
			return StringPool.BLANK;
		}

		Document document = documents[0];

		return document.get(Field.getLocalizedName(locale, Field.CONTENT));
	}

	@Reference
	private FragmentRendererController _fragmentRendererController;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private StagingGroupHelper _stagingGroupHelper;

}