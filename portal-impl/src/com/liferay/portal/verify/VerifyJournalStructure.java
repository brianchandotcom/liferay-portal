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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portlet.dynamicdatamapping.NoSuchStructureException;
import com.liferay.portlet.dynamicdatamapping.util.DDMFieldsCounter;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleImage;
import com.liferay.portlet.journal.service.JournalArticleImageLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;

import java.util.List;

/**
 * @author Michael C. Han
 */
public class VerifyJournalStructure extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		verifyJournalArticleStructures();
	}

	protected void updateDynamicElements(List<Element> dynamicElements)
		throws PortalException {

		DDMFieldsCounter ddmFieldsCounter = new DDMFieldsCounter();

		for (Element dynamicElement : dynamicElements) {
			updateDynamicElements(dynamicElement.elements("dynamic-element"));

			String name = dynamicElement.attributeValue("name");

			int index = ddmFieldsCounter.get(name);

			dynamicElement.addAttribute("index", String.valueOf(index));

			String type = dynamicElement.attributeValue("type");

			if (type.equals("image")) {
				updateImageElement(dynamicElement, name, index);
			}

			ddmFieldsCounter.incrementKey(name);
		}
	}

	protected void updateImageElement(Element element, String name, int index)
		throws PortalException {

		Element dynamicContentElement = element.element("dynamic-content");

		long articleImageId = GetterUtil.getLong(
			dynamicContentElement.attributeValue("id"));

		JournalArticleImage articleImage =
			JournalArticleImageLocalServiceUtil.getArticleImage(articleImageId);

		articleImage.setElName(name + StringPool.UNDERLINE + index);

		JournalArticleImageLocalServiceUtil.updateJournalArticleImage(
			articleImage);
	}

	protected void verifyDynamicElements(JournalArticle article)
		throws Exception {

		Document document = SAXReaderUtil.read(article.getContent());

		Element rootElement = document.getRootElement();

		updateDynamicElements(rootElement.elements("dynamic-element"));

		article.setContent(document.asXML());

		JournalArticleLocalServiceUtil.updateJournalArticle(article);
	}

	protected void verifyJournalArticleStructures() throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			JournalArticleLocalServiceUtil.getActionableDynamicQuery();

		long count = actionableDynamicQuery.performCount();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Processing " + count +
					" journal articles for structure and dynamic elements.");
		}

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object)
					throws PortalException {

					JournalArticle article = (JournalArticle)object;

					try {
						JournalArticleLocalServiceUtil.checkStructure(
							article.getGroupId(), article.getArticleId(),
							article.getVersion());
					}
					catch (NoSuchStructureException nsse) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Removing reference to missing structure for " +
									"article " + article.getId());
						}

						article.setDDMStructureKey(StringPool.BLANK);
						article.setDDMTemplateKey(StringPool.BLANK);

						JournalArticleLocalServiceUtil.updateJournalArticle(
							article);
					}
					catch (Exception e) {
						_log.error(
							"Unable to check the structure for article " +
								article.getId(),
							e);
					}

					try {
						verifyDynamicElements(article);
					}
					catch (Exception e) {
						_log.error(
							"Unable to update content for article " +
								article.getId(),
							e);
					}
				}
			});

		actionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		VerifyJournalStructure.class);

}