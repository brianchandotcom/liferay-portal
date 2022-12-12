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

package com.liferay.portal.search.web.internal.upgrade.v3_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.search.web.internal.category.facet.portlet.CategoryFacetPortlet;
import com.liferay.portal.search.web.internal.custom.facet.display.context.CustomFacetDisplayContext;
import com.liferay.portal.search.web.internal.custom.facet.portlet.CustomFacetPortlet;
import com.liferay.portal.search.web.internal.facet.display.context.AssetCategoriesSearchFacetDisplayContext;
import com.liferay.portal.search.web.internal.facet.display.context.AssetEntriesSearchFacetDisplayContext;
import com.liferay.portal.search.web.internal.facet.display.context.AssetTagsSearchFacetDisplayContext;
import com.liferay.portal.search.web.internal.facet.display.context.FolderSearchFacetDisplayContext;
import com.liferay.portal.search.web.internal.facet.display.context.ScopeSearchFacetDisplayContext;
import com.liferay.portal.search.web.internal.facet.display.context.UserSearchFacetTermDisplayContext;
import com.liferay.portal.search.web.internal.folder.facet.portlet.FolderFacetPortlet;
import com.liferay.portal.search.web.internal.modified.facet.display.context.ModifiedFacetTermDisplayContext;
import com.liferay.portal.search.web.internal.modified.facet.portlet.ModifiedFacetPortlet;
import com.liferay.portal.search.web.internal.site.facet.portlet.SiteFacetPortlet;
import com.liferay.portal.search.web.internal.tag.facet.portlet.TagFacetPortlet;
import com.liferay.portal.search.web.internal.type.facet.portlet.TypeFacetPortlet;
import com.liferay.portal.search.web.internal.user.facet.portlet.UserFacetPortlet;
import com.liferay.portlet.display.template.PortletDisplayTemplate;

import java.util.Map;

/**
 * @author Bryan Engler
 */
public class FacetTemplateUpgradeProcess extends UpgradeProcess {

	public FacetTemplateUpgradeProcess(
		ClassNameLocalService classNameLocalService) {

		_classNameLocalService = classNameLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_deleteOrphanedDefaultFacetTemplates();

		_updateFacetTemplates();
	}

	private void _deleteOrphanedDefaultFacetTemplates() throws Exception {
		long resourceClassNameId = _classNameLocalService.getClassNameId(
			PortletDisplayTemplate.class);

		String[] defaultTemplateKeys = {
			"'CATEGORY-FACET-CLOUD-FTL'", "'CATEGORY-FACET-COMPACT-FTL'",
			"'CATEGORY-FACET-LABEL-FTL'", "'CATEGORY-FACET-VOCABULARY-FTL'",
			"'CUSTOM-FACET-COMPACT-FTL'", "'CUSTOM-FACET-LABEL-FTL'",
			"'FOLDER-FACET-COMPACT-FTL'", "'FOLDER-FACET-LABEL-FTL'",
			"'SITE-FACET-COMPACT-FTL'", "'SITE-FACET-LABEL-FTL'",
			"'TAG-FACET-CLOUD-FTL'", "'TAG-FACET-COMPACT-FTL'",
			"'TAG-FACET-LABEL-FTL'", "'TYPE-FACET-COMPACT-FTL'",
			"'TYPE-FACET-LABEL-FTL'"
		};

		String[] classNames = {
			"com.liferay.portal.search.web.internal.facet.display.context." +
				"AssetCategoriesSearchFacetTermDisplayContext",
			"com.liferay.portal.search.web.internal.custom.facet.display." +
				"context.CustomFacetTermDisplayContext",
			"com.liferay.portal.search.web.internal.facet.display.context." +
				"FolderSearchFacetTermDisplayContext",
			"com.liferay.portal.search.web.internal.facet.display.context." +
				"ScopeSearchFacetTermDisplayContext",
			"com.liferay.portal.search.web.internal.facet.display.context." +
				"AssetTagsSearchFacetTermDisplayContext",
			"com.liferay.portal.search.web.internal.facet.display.context." +
				"AssetEntriesSearchFacetTermDisplayContext"
		};

		for (String className : classNames) {
			runSQL(
				StringBundler.concat(
					"delete from DDMTemplate where resourceClassNameId = ",
					resourceClassNameId, " and classNameId = ",
					_classNameLocalService.getClassNameId(className),
					" and templateKey in (",
					StringUtil.merge(defaultTemplateKeys, StringPool.COMMA),
					")"));
		}
	}

	private void _updateFacetTemplates() throws Exception {
		long resourceClassNameId = _classNameLocalService.getClassNameId(
			PortletDisplayTemplate.class);

		for (Map.Entry<String, String> entry :
				_newFacetTemplateClassNames.entrySet()) {

			long newClassNameId = _classNameLocalService.getClassNameId(
				entry.getValue());
			long oldClassNameId = _classNameLocalService.getClassNameId(
				entry.getKey());

			runSQL(
				StringBundler.concat(
					"update DDMTemplate set classNameId = ", newClassNameId,
					" where classNameId = ", oldClassNameId,
					" and resourceClassNameId = ", resourceClassNameId));
		}
	}

	private final ClassNameLocalService _classNameLocalService;
	private final Map<String, String> _newFacetTemplateClassNames =
		HashMapBuilder.put(
			"com.liferay.portal.search.web.internal.custom.facet.display." +
				"context.CustomFacetTermDisplayContext",
			CustomFacetPortlet.class.getName()
		).put(
			"com.liferay.portal.search.web.internal.facet.display.context." +
				"AssetCategoriesSearchFacetTermDisplayContext",
			CategoryFacetPortlet.class.getName()
		).put(
			"com.liferay.portal.search.web.internal.facet.display.context." +
				"AssetEntriesSearchFacetTermDisplayContext",
			TypeFacetPortlet.class.getName()
		).put(
			"com.liferay.portal.search.web.internal.facet.display.context." +
				"AssetTagsSearchFacetTermDisplayContext",
			TagFacetPortlet.class.getName()
		).put(
			"com.liferay.portal.search.web.internal.facet.display.context." +
				"FolderSearchFacetTermDisplayContext",
			FolderFacetPortlet.class.getName()
		).put(
			"com.liferay.portal.search.web.internal.facet.display.context." +
				"ScopeSearchFacetTermDisplayContext",
			SiteFacetPortlet.class.getName()
		).put(
			AssetCategoriesSearchFacetDisplayContext.class.getName(),
			CategoryFacetPortlet.class.getName()
		).put(
			CustomFacetDisplayContext.class.getName(),
			CustomFacetPortlet.class.getName()
		).put(
			FolderSearchFacetDisplayContext.class.getName(),
			FolderFacetPortlet.class.getName()
		).put(
			ModifiedFacetTermDisplayContext.class.getName(),
			ModifiedFacetPortlet.class.getName()
		).put(
			ScopeSearchFacetDisplayContext.class.getName(),
			SiteFacetPortlet.class.getName()
		).put(
			AssetTagsSearchFacetDisplayContext.class.getName(),
			TagFacetPortlet.class.getName()
		).put(
			AssetEntriesSearchFacetDisplayContext.class.getName(),
			TypeFacetPortlet.class.getName()
		).put(
			UserSearchFacetTermDisplayContext.class.getName(),
			UserFacetPortlet.class.getName()
		).build();

}