/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.internal.search;

import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriterHelperUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalService;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Joao Victor Alves
 */
@Component(service = Indexer.class)
public class SiteNavigationMenuIndexer extends BaseIndexer<SiteNavigationMenu> {

	public SiteNavigationMenuIndexer() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.GROUP_ID, Field.TITLE, Field.UID);
		setFilterSearch(true);
		setPermissionAware(true);
	}

	@Override
	public String getClassName() {
		return _CLASS_NAME;
	}

	@Override
	protected void doDelete(SiteNavigationMenu siteNavigationMenu)
		throws Exception {

		deleteDocument(
			siteNavigationMenu.getCompanyId(),
			siteNavigationMenu.getPrimaryKey());
	}

	@Override
	protected Document doGetDocument(SiteNavigationMenu siteNavigationMenu) {
		Document document = getBaseModelDocument(
			_CLASS_NAME, siteNavigationMenu);

		document.addText(Field.TITLE, siteNavigationMenu.getName());
		document.addDate(
			Field.MODIFIED_DATE, siteNavigationMenu.getModifiedDate());

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		String title = document.get(Field.TITLE);

		return new Summary(title, snippet);
	}

	@Override
	protected void doReindex(SiteNavigationMenu siteNavigationMenu)
		throws Exception {

		Document document = getDocument(siteNavigationMenu);

		if (document != null) {
			IndexWriterHelperUtil.updateDocument(
				siteNavigationMenu.getCompanyId(), document);
		}
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		doReindex(
			_siteNavigationMenuLocalService.getSiteNavigationMenu(classPK));
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		_reindexSiteNavigationMenus(companyId);
	}

	private void _reindexSiteNavigationMenus(long companyId) throws Exception {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_siteNavigationMenuLocalService.
				getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			(SiteNavigationMenu siteNavigationMenu) -> {
				try {
					indexableActionableDynamicQuery.addDocuments(
						getDocument(siteNavigationMenu));
				}
				catch (PortalException portalException) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to index site navigation menu " +
								siteNavigationMenu.getSiteNavigationMenuId(),
							portalException);
					}
				}
			});

		indexableActionableDynamicQuery.performActions();
	}

	private static final String _CLASS_NAME =
		SiteNavigationMenu.class.getName();

	private static final Log _log = LogFactoryUtil.getLog(
		SiteNavigationMenuIndexer.class);

	@Reference
	private SiteNavigationMenuLocalService _siteNavigationMenuLocalService;

}