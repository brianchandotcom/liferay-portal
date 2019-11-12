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

package com.liferay.journal.demo.data.creator.internal;

import com.liferay.journal.demo.data.creator.JournalArticleDemoDataCreator;
import com.liferay.journal.exception.NoSuchArticleException;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = JournalArticleDemoDataCreator.class)
public class JournalArticleDemoDataCreatorImpl
	implements JournalArticleDemoDataCreator {

	@Override
	public JournalArticle create(long userId, long groupId)
		throws IOException, PortalException {

		int index = _getNextIndex();

		Map<Locale, String> titleMap = _getTitleMap(index);
		Map<Locale, String> descriptionMap = _getDescriptionMap(index);
		String content = _getContent(index);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(groupId);

		JournalArticle journalArticle = _journalArticleLocalService.addArticle(
			userId, groupId, 0, titleMap, descriptionMap, content,
			"BASIC-WEB-CONTENT", "BASIC-WEB-CONTENT", serviceContext);

		_entryIds.add(journalArticle.getId());

		return journalArticle;
	}

	@Override
	public void delete() throws PortalException {
		for (long entryId : _entryIds) {
			try {
				_journalArticleLocalService.deleteJournalArticle(entryId);
			}
			catch (NoSuchArticleException nsae) {
				if (_log.isWarnEnabled()) {
					_log.warn(nsae, nsae);
				}
			}

			_entryIds.remove(entryId);
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		Collections.addAll(_availableIndexes, new Integer[] {1, 2, 3, 4, 5});

		Collections.shuffle(_availableIndexes);
	}

	@Reference(unbind = "-")
	protected void setJournalArticleLocalService(
		JournalArticleLocalService journalArticleLocalService) {

		_journalArticleLocalService = journalArticleLocalService;
	}

	@Reference(unbind = "-")
	protected void setJournalFolderLocalService(
		JournalFolderLocalService journalFolderLocalService) {
	}

	private Document _createDocumentContent(String locale) {
		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		rootElement.addAttribute("available-locales", locale);
		rootElement.addAttribute("default-locale", locale);

		return document;
	}

	private String _getContent(int index) throws IOException {
		Class<?> clazz = getClass();

		String contentPath = StringBundler.concat(
			"com/liferay/journal/demo/data/creator/internal/dependencies",
			"/article", index, "/content.txt");

		String content = StringUtil.read(
			clazz.getClassLoader(), contentPath, false);

		return _getStructuredContent(content);
	}

	private Map<Locale, String> _getDescriptionMap(int index)
		throws IOException {

		return HashMapBuilder.put(
			LocaleUtil.getSiteDefault(),
			() -> {
				Class<?> clazz = getClass();

				String descriptionPath = StringBundler.concat(
					"com/liferay/journal/demo/data/creator/internal",
					"/dependencies/article", index, "/description.txt");

				return StringUtil.read(
					clazz.getClassLoader(), descriptionPath, false);
			}
		).build();
	}

	private int _getNextIndex() {
		int index = _atomicInteger.getAndIncrement();

		if (index == (_availableIndexes.size() - 1)) {
			_atomicInteger.set(0);
		}

		return _availableIndexes.get(index);
	}

	private String _getStructuredContent(String content) {
		Locale locale = LocaleUtil.getSiteDefault();

		Document document = _createDocumentContent(locale.toString());

		Element rootElement = document.getRootElement();

		Element dynamicElementElement = rootElement.addElement(
			"dynamic-element");

		dynamicElementElement.addAttribute("index-type", "text");
		dynamicElementElement.addAttribute("name", "content");
		dynamicElementElement.addAttribute("type", "text_area");

		Element element = dynamicElementElement.addElement("dynamic-content");

		element.addAttribute("language-id", LocaleUtil.toLanguageId(locale));
		element.addCDATA(content);

		return document.asXML();
	}

	private Map<Locale, String> _getTitleMap(int index) throws IOException {
		return HashMapBuilder.put(
			LocaleUtil.getSiteDefault(),
			() -> {
				Class<?> clazz = getClass();

				String titlePath = StringBundler.concat(
					"com/liferay/journal/demo/data/creator/internal",
					"/dependencies/article", index, "/title.txt");

				return StringUtil.read(
					clazz.getClassLoader(), titlePath, false);
			}
		).build();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleDemoDataCreatorImpl.class);

	private final AtomicInteger _atomicInteger = new AtomicInteger(0);
	private final List<Integer> _availableIndexes =
		new CopyOnWriteArrayList<>();
	private final List<Long> _entryIds = new CopyOnWriteArrayList<>();
	private JournalArticleLocalService _journalArticleLocalService;

}