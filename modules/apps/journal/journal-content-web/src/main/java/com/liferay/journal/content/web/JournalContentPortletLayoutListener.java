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

package com.liferay.journal.content.web;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.journal.content.web.constants.JournalContentPortletKeys;
import com.liferay.journal.exception.NoSuchArticleException;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalContentSearchLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletLayoutListener;
import com.liferay.portal.kernel.portlet.PortletLayoutListenerException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.layoutconfiguration.util.xml.PortletLogic;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.PortletInstance;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.LayoutLocalService;
import com.liferay.portal.service.PortletLocalService;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + JournalContentPortletKeys.JOURNAL_CONTENT
	},
	service = PortletLayoutListener.class
)
public class JournalContentPortletLayoutListener
	implements PortletLayoutListener {

	@Override
	public void onAddToLayout(String portletId, long plid)
		throws PortletLayoutListenerException {

		if (_log.isDebugEnabled()) {
			_log.debug("Add " + portletId + " to layout " + plid);
		}

		try {
			Layout layout = _layoutLocalService.getLayout(plid);

			PortletPreferences portletPreferences =
				PortletPreferencesFactoryUtil.getPortletSetup(
					layout, portletId, StringPool.BLANK);

			String articleId = portletPreferences.getValue("articleId", null);

			if (Validator.isNull(articleId)) {
				return;
			}

			_journalContentSearchLocalService.updateContentSearch(
				layout.getGroupId(), layout.isPrivateLayout(),
				layout.getLayoutId(), portletId, articleId, true);
		}
		catch (Exception e) {
			throw new PortletLayoutListenerException(e);
		}
	}

	@Override
	public void onMoveInLayout(String portletId, long plid)
		throws PortletLayoutListenerException {

		if (_log.isDebugEnabled()) {
			_log.debug("Move " + portletId + " from in " + plid);
		}
	}

	@Override
	public void onRemoveFromLayout(String portletId, long plid)
		throws PortletLayoutListenerException {

		if (_log.isDebugEnabled()) {
			_log.debug("Remove " + portletId + " from layout " + plid);
		}

		try {
			Layout layout = _layoutLocalService.getLayout(plid);

			PortletPreferences portletPreferences =
				PortletPreferencesFactoryUtil.getPortletSetup(
					layout, portletId, StringPool.BLANK);

			String articleId = portletPreferences.getValue("articleId", null);

			if (Validator.isNull(articleId)) {
				return;
			}

			_journalContentSearchLocalService.deleteArticleContentSearch(
				layout.getGroupId(), layout.isPrivateLayout(),
				layout.getLayoutId(), portletId, articleId);

			String[] runtimePortletIds = getRuntimePortletIds(
				layout.getCompanyId(), layout.getGroupId(), articleId);

			if (runtimePortletIds.length > 0) {
				_portletLocalService.deletePortlets(
					layout.getCompanyId(), runtimePortletIds, layout.getPlid());
			}
		}
		catch (Exception e) {
			throw new PortletLayoutListenerException(e);
		}
	}

	@Override
	public void onSetup(String portletId, long plid)
		throws PortletLayoutListenerException {

		if (_log.isDebugEnabled()) {
			_log.debug("Setup " + portletId + " from layout " + plid);
		}

		try {
			Layout layout = _layoutLocalService.getLayout(plid);

			PortletPreferences portletPreferences =
				PortletPreferencesFactoryUtil.getPortletSetup(
					layout, portletId, StringPool.BLANK);

			String articleId = portletPreferences.getValue("articleId", null);

			if (Validator.isNull(articleId)) {
				_journalContentSearchLocalService.deleteArticleContentSearch(
					layout.getGroupId(), layout.isPrivateLayout(),
					layout.getLayoutId(), portletId);

				return;
			}

			_journalContentSearchLocalService.updateContentSearch(
				layout.getGroupId(), layout.isPrivateLayout(),
				layout.getLayoutId(), portletId, articleId, true);
		}
		catch (Exception e) {
			throw new PortletLayoutListenerException(e);
		}
	}

	@Override
	public void updatePropertiesOnRemoveFromLayout(
			String portletId, UnicodeProperties typeSettingsProperties)
		throws PortletLayoutListenerException {
	}

	protected String getRuntimePortletId(String xml) throws Exception {
		Document document = SAXReaderUtil.read(xml);

		Element rootElement = document.getRootElement();

		String portletName = rootElement.attributeValue("name");
		String instanceId = rootElement.attributeValue("instance");

		PortletInstance portletInstance = new PortletInstance(
			portletName, instanceId);

		return portletInstance.getPortletInstanceKey();
	}

	protected String[] getRuntimePortletIds(
			long companyId, long scopeGroupId, String articleId)
		throws Exception {

		Group group = _groupLocalService.getCompanyGroup(companyId);

		JournalArticle article = null;

		try {
			article = _journalArticleLocalService.getDisplayArticle(
				scopeGroupId, articleId);
		}
		catch (NoSuchArticleException nsae) {
		}

		if (article == null) {
			try {
				article = _journalArticleLocalService.getDisplayArticle(
					group.getGroupId(), articleId);
			}
			catch (NoSuchArticleException nsae) {
				return new String[0];
			}
		}

		Set<String> portletIds = getRuntimePortletIds(article.getContent());

		if (Validator.isNotNull(article.getDDMTemplateKey())) {
			DDMTemplate ddmTemplate = _ddmTemplateLocalService.getTemplate(
				scopeGroupId, PortalUtil.getClassNameId(DDMStructure.class),
				article.getDDMTemplateKey(), true);

			portletIds.addAll(getRuntimePortletIds(ddmTemplate.getScript()));
		}

		return portletIds.toArray(new String[portletIds.size()]);
	}

	protected Set<String> getRuntimePortletIds(String content)
		throws Exception {

		Set<String> portletIds = new LinkedHashSet<>();

		for (int index = 0;;) {
			index = content.indexOf(PortletLogic.OPEN_TAG, index);

			if (index == -1) {
				break;
			}

			int close1 = content.indexOf(PortletLogic.CLOSE_1_TAG, index);
			int close2 = content.indexOf(PortletLogic.CLOSE_2_TAG, index);

			int closeIndex = -1;

			if ((close2 == -1) || ((close1 != -1) && (close1 < close2))) {
				closeIndex = close1 + PortletLogic.CLOSE_1_TAG.length();
			}
			else {
				closeIndex = close2 + PortletLogic.CLOSE_2_TAG.length();
			}

			if (closeIndex == -1) {
				break;
			}

			portletIds.add(
				getRuntimePortletId(content.substring(index, closeIndex)));

			index = closeIndex;
		}

		return portletIds;
	}

	@Reference(unbind = "-")
	protected void setDDMTemplateLocalService(
		DDMTemplateLocalService ddmTemplateLocalService) {

		_ddmTemplateLocalService = ddmTemplateLocalService;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setJournalArticleLocalService(
		JournalArticleLocalService journalArticleLocalService) {

		_journalArticleLocalService = journalArticleLocalService;
	}

	@Reference(unbind = "-")
	protected void setJournalContentSearchLocalService(
		JournalContentSearchLocalService journalContentSearchLocalService) {

		_journalContentSearchLocalService = journalContentSearchLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	@Reference(unbind = "-")
	protected void setPortletLocalService(
		PortletLocalService portletLocalService) {

		_portletLocalService = portletLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalContentPortletLayoutListener.class);

	private volatile DDMTemplateLocalService _ddmTemplateLocalService;
	private volatile GroupLocalService _groupLocalService;
	private volatile JournalArticleLocalService _journalArticleLocalService;
	private volatile JournalContentSearchLocalService _journalContentSearchLocalService;
	private volatile LayoutLocalService _layoutLocalService;
	private volatile PortletLocalService _portletLocalService;

}