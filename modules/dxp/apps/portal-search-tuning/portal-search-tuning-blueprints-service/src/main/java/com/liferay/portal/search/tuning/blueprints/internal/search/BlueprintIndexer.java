/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.blueprints.internal.search;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.search.localization.SearchLocalizationHelper;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;
import com.liferay.portal.search.tuning.blueprints.service.BlueprintLocalService;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = Indexer.class)
public class BlueprintIndexer extends BaseIndexer<Blueprint> {

	public static final String CLASS_NAME = Blueprint.class.getName();

	public BlueprintIndexer() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.GROUP_ID, Field.MODIFIED_DATE, Field.SCOPE_GROUP_ID,
			Field.UID);
		setDefaultSelectedLocalizedFieldNames(Field.DESCRIPTION, Field.TITLE);
		setFilterSearch(true);
		setPermissionAware(true);
	}

	@Override
	public Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		String languageId = LocaleUtil.toLanguageId(locale);

		return _createSummary(
			document,
			LocalizationUtil.getLocalizedName(Field.DESCRIPTION, languageId),
			LocalizationUtil.getLocalizedName(Field.TITLE, languageId));
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, String entryClassName,
			long entryClassPK, String actionId)
		throws Exception {

		return _blueprintModelResourcePermission.contains(
			permissionChecker, entryClassPK, ActionKeys.VIEW);
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		addSearchLocalizedTerm(
			searchQuery, searchContext, Field.DESCRIPTION, false);
		addSearchLocalizedTerm(searchQuery, searchContext, Field.TITLE, false);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		String[] localizedFieldNames =
			_searchLocalizationHelper.getLocalizedFieldNames(
				new String[] {Field.CONTENT, Field.DESCRIPTION, Field.TITLE},
				searchContext);

		queryConfig.addHighlightFieldNames(localizedFieldNames);
	}

	@Override
	protected void doDelete(Blueprint blueprint) throws Exception {
		deleteDocument(blueprint.getCompanyId(), blueprint.getBlueprintId());
	}

	@Override
	protected Document doGetDocument(Blueprint blueprint) throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Indexing Blueprint " + blueprint);
		}

		Document document = getBaseModelDocument(CLASS_NAME, blueprint);

		document.addDate(Field.MODIFIED_DATE, blueprint.getModifiedDate());

		document.addNumber(Field.TYPE, blueprint.getType());

		for (Locale locale :
				LanguageUtil.getCompanyAvailableLocales(
					blueprint.getCompanyId())) {

			String languageId = LocaleUtil.toLanguageId(locale);

			document.addText(
				LocalizationUtil.getLocalizedName(
					Field.DESCRIPTION, languageId),
				blueprint.getDescription(locale));
			document.addText(
				LocalizationUtil.getLocalizedName(
					"localized_" + Field.TITLE, languageId),
				blueprint.getTitle(locale));
			document.addTextSortable(
				LocalizationUtil.getLocalizedName(
					"localized_" + Field.TITLE, languageId),
				blueprint.getTitle(locale));
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Document " + blueprint + " indexed successfully");
		}

		return document;
	}

	@Override
	protected void doReindex(Blueprint blueprint) throws Exception {
		_indexWriterHelper.updateDocument(
			getSearchEngineId(), blueprint.getCompanyId(),
			getDocument(blueprint), isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		doReindex(_blueprintLocalService.getBlueprint(classPK));
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexBlueprints(companyId);
	}

	protected void reindexBlueprints(long companyId) throws PortalException {
		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_blueprintLocalService.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			(Blueprint blueprint) -> {
				try {
					indexableActionableDynamicQuery.addDocuments(
						getDocument(blueprint));
				}
				catch (PortalException portalException) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to index Blueprint " +
								blueprint.getBlueprintId(),
							portalException);
					}
				}
			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private Summary _createSummary(
		Document document, String descriptionField, String titleField) {

		String prefix = Field.SNIPPET + StringPool.UNDERLINE;

		return new Summary(
			document.get(prefix + titleField, titleField),
			document.get(prefix + descriptionField, descriptionField));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BlueprintIndexer.class);

	@Reference
	private BlueprintLocalService _blueprintLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.portal.search.tuning.blueprints.model.Blueprint)"
	)
	private ModelResourcePermission<Blueprint>
		_blueprintModelResourcePermission;

	@Reference
	private IndexWriterHelper _indexWriterHelper;

	@Reference
	private SearchLocalizationHelper _searchLocalizationHelper;

}