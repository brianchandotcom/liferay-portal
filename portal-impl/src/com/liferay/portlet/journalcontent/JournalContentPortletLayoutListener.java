/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journalcontent;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletLayoutListener;
import com.liferay.portal.kernel.portlet.PortletLayoutListenerException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UniqueList;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.NoSuchTemplateException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalContentSearchLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalTemplateLocalServiceUtil;
import com.liferay.portlet.layoutconfiguration.util.xml.PortletLogic;

import java.util.List;

import javax.portlet.PortletPreferences;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class JournalContentPortletLayoutListener
	implements PortletLayoutListener {

	public void onAddToLayout(String portletId, long plid)
		throws PortletLayoutListenerException {

		if (_log.isDebugEnabled()) {
			_log.debug("Add " + portletId + " to layout " + plid);
		}

		try {
			Layout layout = LayoutLocalServiceUtil.getLayout(plid);

			PortletPreferences preferences =
				PortletPreferencesFactoryUtil.getPortletSetup(
					layout, portletId, StringPool.BLANK);

			String articleId = preferences.getValue("articleId", null);

			if (Validator.isNull(articleId)) {
				return;
			}

			JournalContentSearchLocalServiceUtil.updateContentSearch(
				layout.getGroupId(), layout.isPrivateLayout(),
				layout.getLayoutId(), portletId, articleId, true);
		}
		catch (Exception e) {
			throw new PortletLayoutListenerException(e);
		}
	}

	public void onMoveInLayout(String portletId, long plid)
		throws PortletLayoutListenerException {

		if (_log.isDebugEnabled()) {
			_log.debug("Move " + portletId + " from in " + plid);
		}
	}

	public void onRemoveFromLayout(String portletId, long plid)
		throws PortletLayoutListenerException {

		if (_log.isDebugEnabled()) {
			_log.debug("Remove " + portletId + " from layout " + plid);
		}

		try {
			Layout layout = LayoutLocalServiceUtil.getLayout(plid);

			PortletPreferences preferences =
				PortletPreferencesFactoryUtil.getPortletSetup(
					layout, portletId, StringPool.BLANK);

			String articleId = preferences.getValue("articleId", null);

			if (Validator.isNull(articleId)) {
				return;
			}

			JournalContentSearchLocalServiceUtil.deleteArticleContentSearch(
				layout.getGroupId(), layout.isPrivateLayout(),
				layout.getLayoutId(), portletId, articleId);

			deleteRuntimePortletResources(layout, articleId);
		}
		catch (Exception e) {
			throw new PortletLayoutListenerException(e);
		}
	}

	protected void deleteRuntimePortletResources(
			Layout layout, String articleId)
		throws Exception{

		long companyId = layout.getCompanyId();

		List<String> runtimePortletIds = getRuntimePortletIds(
			companyId, layout.getGroupId(), articleId);

		if ((runtimePortletIds != null) && !runtimePortletIds.isEmpty()) {
			PortletLocalServiceUtil.deletePortlets(
				companyId,
				runtimePortletIds.toArray(new String[runtimePortletIds.size()]),
				layout.getPlid());
		}
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

	protected List<String> getRuntimePortletIds(String content)
		throws Exception {

		List<String> portletIds = new UniqueList<String>();

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

	protected List<String> getRuntimePortletIds(
			long companyId, long scopeGroupId , String articleId)
		throws Exception {

		JournalArticle journalArticle = null;

		Group companyGroup = GroupLocalServiceUtil.getCompanyGroup(
			companyId);

		try {
			journalArticle = JournalArticleLocalServiceUtil.getDisplayArticle(
				scopeGroupId, articleId);
		}
		catch (NoSuchArticleException nsae) {
		}

		if (journalArticle == null) {
			try {
				journalArticle =
					JournalArticleLocalServiceUtil.getDisplayArticle(
						companyGroup.getGroupId(), articleId);
			}
			catch (NoSuchArticleException nsae) {
				return null;
			}
		}

		List<String> portletIds = getRuntimePortletIds(
			journalArticle.getContent());

		if (Validator.isNotNull(journalArticle.getTemplateId())) {
			JournalTemplate journalTemplate = null;

			try {
				journalTemplate = JournalTemplateLocalServiceUtil.getTemplate(
					scopeGroupId, journalArticle.getTemplateId());
			}
			catch (NoSuchTemplateException nste) {
				journalTemplate = JournalTemplateLocalServiceUtil.getTemplate(
					companyGroup.getGroupId(), journalArticle.getTemplateId());
			}

			portletIds.addAll(getRuntimePortletIds(journalTemplate.getXsl()));
		}

		return portletIds;
	}

	private static Log _log = LogFactoryUtil.getLog(
		JournalContentPortletLayoutListener.class);

}