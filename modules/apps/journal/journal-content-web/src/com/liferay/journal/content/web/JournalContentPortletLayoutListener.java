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

import com.liferay.journal.content.web.constants.JournalContentPortletKeys;
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
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalContentSearchLocalServiceUtil;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;

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
			Layout layout = LayoutLocalServiceUtil.getLayout(plid);

			PortletPreferences portletPreferences =
				PortletPreferencesFactoryUtil.getPortletSetup(
					layout, portletId, StringPool.BLANK);

			String articleId = portletPreferences.getValue("articleId", null);

			if (Validator.isNull(articleId)) {
				return;
			}

			JournalContentSearchLocalServiceUtil.updateContentSearch(
				layout.getGroupId(), layout.getLayoutId(), portletId, articleId,
				true);
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
			Layout layout = LayoutLocalServiceUtil.getLayout(plid);

			PortletPreferences portletPreferences =
				PortletPreferencesFactoryUtil.getPortletSetup(
					layout, portletId, StringPool.BLANK);

			String articleId = portletPreferences.getValue("articleId", null);

			if (Validator.isNull(articleId)) {
				return;
			}

			JournalContentSearchLocalServiceUtil.deleteArticleContentSearch(
				layout.getGroupId(), layout.getLayoutId(), portletId,
				articleId);

			String[] runtimePortletIds = getRuntimePortletIds(
				layout.getCompanyId(), layout.getGroupId(), articleId);

			if (runtimePortletIds.length > 0) {
				PortletLocalServiceUtil.deletePortlets(
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
			Layout layout = LayoutLocalServiceUtil.getLayout(plid);

			PortletPreferences portletPreferences =
				PortletPreferencesFactoryUtil.getPortletSetup(
					layout, portletId, StringPool.BLANK);

			String articleId = portletPreferences.getValue("articleId", null);

			if (Validator.isNull(articleId)) {
				JournalContentSearchLocalServiceUtil.deleteArticleContentSearch(
					layout.getGroupId(), layout.getLayoutId(), portletId);

				return;
			}

			JournalContentSearchLocalServiceUtil.updateContentSearch(
				layout.getGroupId(), layout.getLayoutId(), portletId, articleId,
				true);
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

		String instanceId = rootElement.attributeValue("instance");
		String portletId = rootElement.attributeValue("name");

		if (Validator.isNotNull(instanceId)) {
			portletId += PortletConstants.INSTANCE_SEPARATOR + instanceId;
		}

		return portletId;
	}

	protected String[] getRuntimePortletIds(
			long companyId, long scopeGroupId, String articleId)
		throws Exception {

		Group group = GroupLocalServiceUtil.getCompanyGroup(companyId);

		JournalArticle article = null;

		try {
			article = JournalArticleLocalServiceUtil.getDisplayArticle(
				scopeGroupId, articleId);
		}
		catch (NoSuchArticleException nsae) {
		}

		if (article == null) {
			try {
				article = JournalArticleLocalServiceUtil.getDisplayArticle(
					group.getGroupId(), articleId);
			}
			catch (NoSuchArticleException nsae) {
				return new String[0];
			}
		}

		Set<String> portletIds = getRuntimePortletIds(article.getContent());

		if (Validator.isNotNull(article.getDDMTemplateKey())) {
			DDMTemplate ddmTemplate = DDMTemplateLocalServiceUtil.getTemplate(
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

	private static final Log _log = LogFactoryUtil.getLog(
		JournalContentPortletLayoutListener.class);

}