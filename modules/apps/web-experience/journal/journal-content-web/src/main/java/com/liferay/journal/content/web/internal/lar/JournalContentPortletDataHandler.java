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

package com.liferay.journal.content.web.internal.lar;

import com.liferay.exportimport.kernel.lar.BasePortletDataHandler;
import com.liferay.exportimport.kernel.lar.DataLevel;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.journal.content.web.configuration.JournalContentWebConfigurationValues;
import com.liferay.journal.content.web.constants.JournalContentPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.util.StringPool;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * <p>
 * Provides the Journal Content portlet export and import functionality, which
 * is to clone the article, structure, and template referenced in the Journal
 * Content portlet if the article is associated with the layout's group. Upon
 * import, a new instance of the corresponding article, structure, and template
 * will be created or updated. The author of the newly created objects are
 * determined by the JournalCreationStrategy class defined in
 * <i>portal.properties</i>.
 * </p>
 *
 * <p>
 * This <code>PortletDataHandler</code> differs from from
 * <code>JournalPortletDataHandlerImpl</code> in that it only exports articles
 * referenced in Journal Content portlets. Articles not displayed in Journal
 * Content portlets will not be exported unless
 * <code>JournalPortletDataHandlerImpl</code> is activated.
 * </p>
 *
 * @author Joel Kozikowski
 * @author Raymond Augé
 * @author Bruno Farache
 * @author Daniel Kocsis
 * @author Mate Thurzo
 * @see    com.liferay.journal.lar.JournalCreationStrategy
 * @see    PortletDataHandler
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + JournalContentPortletKeys.JOURNAL_CONTENT
	},
	service = PortletDataHandler.class
)
public class JournalContentPortletDataHandler extends BasePortletDataHandler {

	public static final String SCHEMA_VERSION = "1.0.0";

	@Override
	public String getSchemaVersion() {
		return SCHEMA_VERSION;
	}

	@Activate
	protected void activate() {
		setDataLevel(DataLevel.PORTLET_INSTANCE);
		setDataPortletPreferences("articleId", "ddmTemplateKey", "groupId");
		setExportControls(
			new PortletDataHandlerBoolean(
				null, "selected-web-content", true, true, null,
				JournalArticle.class.getName()));
		setPublishToLiveByDefault(
			JournalContentWebConfigurationValues.PUBLISH_TO_LIVE_BY_DEFAULT);
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletPreferences == null) {
			return portletPreferences;
		}

		portletPreferences.setValue("articleId", StringPool.BLANK);
		portletPreferences.setValue("ddmTemplateKey", StringPool.BLANK);
		portletPreferences.setValue("groupId", StringPool.BLANK);

		return portletPreferences;
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

}