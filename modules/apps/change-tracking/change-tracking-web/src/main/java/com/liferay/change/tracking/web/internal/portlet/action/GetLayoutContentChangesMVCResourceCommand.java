/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR
 * LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.portlet.action;

import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;

import jakarta.portlet.PortletRequest;
import jakarta.portlet.ResourceRequest;
import jakarta.portlet.ResourceResponse;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	property = {
		"jakarta.portlet.name=" + CTPortletKeys.PUBLICATIONS,
		"mvc.command.name=/change_tracking/get_layout_content_changes"
	},
	service = MVCResourceCommand.class
)
public class GetLayoutContentChangesMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		try {
			long ctEntryId = ParamUtil.getLong(resourceRequest, "ctEntryId");

			CTEntry ctEntry = _ctEntryLocalService.getCTEntry(ctEntryId);

			if ((ctEntry == null) ||
				!Objects.equals(
					_portal.getClassNameId(Layout.class),
					ctEntry.getModelClassNameId())) {

				return;
			}

			ThemeDisplay themeDisplay =
				(ThemeDisplay)resourceRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			SearchResponse searchResponse =
				_getLayoutContentChangesSearchResponse(
					themeDisplay.getCompanyId(), ctEntry.getCtCollectionId(),
					ParamUtil.getInteger(resourceRequest, "cur"),
					ctEntry.getModelClassPK());

			List<Document> documents = searchResponse.getDocuments();

			if (documents.isEmpty()) {
				return;
			}

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"layoutContentChanges",
					_getLayoutContentChangesJSONArray(
						documents,
						_portal.getHttpServletRequest(resourceRequest))
				).put(
					"total", searchResponse.getTotalHits()
				));
		}
		catch (PortalException portalException) {
			_log.error(portalException);

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"errorMessage",
					_language.get(
						_portal.getHttpServletRequest(resourceRequest),
						"an-unexpected-error-occurred")));
		}
	}

	private JSONArray _getLayoutContentChangesJSONArray(
		List<Document> documents, HttpServletRequest httpServletRequest) {

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		for (Document document : documents) {
			jsonArray = jsonArray.put(
				JSONUtil.put(
					Field.TITLE, document.getString(Field.TITLE)
				).put(
					"ctEntryId", document.getLong(Field.ENTRY_CLASS_PK)
				).put(
					"viewChangeURL",
					PortletURLBuilder.create(
						PortletURLFactoryUtil.create(
							httpServletRequest, CTPortletKeys.PUBLICATIONS,
							PortletRequest.RENDER_PHASE)
					).setMVCRenderCommandName(
						"/change_tracking/view_change"
					).setParameter(
						"ctCollectionId", document.getLong("ctCollectionId")
					).setParameter(
						"ctEntryId", document.getLong(Field.ENTRY_CLASS_PK)
					).buildString()
				));
		}

		return jsonArray;
	}

	private SearchResponse _getLayoutContentChangesSearchResponse(
		long companyId, long ctCollectionId, int cur, long plid) {

		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder(
			).companyId(
				companyId
			).emptySearchEnabled(
				true
			).fields(
				Field.ENTRY_CLASS_PK, Field.TITLE, "ctCollectionId"
			).from(
				cur
			).modelIndexerClasses(
				CTEntry.class
			).size(
				20
			).sorts(
				_sorts.field(
					Field.getSortableFieldName(Field.MODIFIED_DATE),
					SortOrder.DESC)
			).withSearchContext(
				searchContext -> {
					searchContext.setAttribute(
						"ctCollectionId", ctCollectionId);
					searchContext.setAttribute(
						"modelClassNameId",
						new long[] {
							_portal.getClassNameId(FragmentEntryLink.class)
						});
					searchContext.setAttribute("showHideable", Boolean.TRUE);

					BooleanQueryImpl booleanQueryImpl = new BooleanQueryImpl();

					booleanQueryImpl.addRequiredTerm("plid", plid);

					searchContext.setBooleanClauses(
						new BooleanClause[] {
							BooleanClauseFactoryUtil.create(
								booleanQueryImpl,
								BooleanClauseOccur.MUST.getName())
						});
				}
			);

		return _searcher.search(searchRequestBuilder.build());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetLayoutContentChangesMVCResourceCommand.class);

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private Searcher _searcher;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@Reference
	private Sorts _sorts;

}