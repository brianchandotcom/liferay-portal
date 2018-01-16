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

package com.liferay.fragment.web.internal.portlet.util;

import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.service.FragmentCollectionService;
import com.liferay.fragment.service.FragmentEntryService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.zip.ZipReader;

import javax.portlet.ActionRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = ImportUtil.class)
public class ImportUtil {

	public void importFragmentCollections(
			ActionRequest actionRequest, ZipReader zipReader)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		String definition = zipReader.getEntryAsString(
			"/fragment_collections/definition.xml");

		Document document = SAXReaderUtil.read(definition);

		Element rootElement = document.getRootElement();

		for (Element fragmentCollectionElement :
				rootElement.elements("fragment-collection")) {

			String path =
				"/fragment_collections/" + fragmentCollectionElement.getText();

			String fragmentCollectionDefinition = zipReader.getEntryAsString(
				path + "/definition.xml");

			Document fragmentCollectionDocument = SAXReaderUtil.read(
				fragmentCollectionDefinition);

			Element fragmentCollectionRootElement =
				fragmentCollectionDocument.getRootElement();

			FragmentCollection fragmentCollection =
				_fragmentCollectionService.addFragmentCollection(
					themeDisplay.getScopeGroupId(),
					fragmentCollectionRootElement.elementText("name"),
					fragmentCollectionRootElement.elementText("description"),
					serviceContext);

			importFragmentEntries(
				actionRequest, zipReader,
				fragmentCollection.getFragmentCollectionId(), path);
		}
	}

	public void importFragmentEntries(
			ActionRequest actionRequest, ZipReader zipReader,
			long fragmentCollectionId, String path)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		String definition = zipReader.getEntryAsString(
			path + "/fragment_entries/definition.xml");

		Document document = SAXReaderUtil.read(definition);

		Element rootElement = document.getRootElement();

		for (Element fragmentEntry : rootElement.elements("fragment-entry")) {
			StringBundler sb = new StringBundler(4);

			sb.append(path);
			sb.append("/fragment_entries/");
			sb.append(fragmentEntry.getText());
			sb.append("/definition.xml");

			String fragmentEntryDefinition = zipReader.getEntryAsString(
				sb.toString());

			Document fragmentEntryDocument = SAXReaderUtil.read(
				fragmentEntryDefinition);

			Element fragmentEntryRootElement =
				fragmentEntryDocument.getRootElement();

			String css = zipReader.getEntryAsString(
				fragmentEntryRootElement.elementText("css-path"));
			String html = zipReader.getEntryAsString(
				fragmentEntryRootElement.elementText("html-path"));
			String js = zipReader.getEntryAsString(
				fragmentEntryRootElement.elementText("js-path"));

			_fragmentEntryService.addFragmentEntry(
				themeDisplay.getScopeGroupId(), fragmentCollectionId,
				fragmentEntryRootElement.elementText("name"), css, html, js,
				WorkflowConstants.STATUS_APPROVED, serviceContext);
		}
	}

	@Reference
	private FragmentCollectionService _fragmentCollectionService;

	@Reference
	private FragmentEntryService _fragmentEntryService;

}