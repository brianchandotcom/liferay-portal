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

package com.liferay.document.library.internal.search;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchResult;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.SummaryFactory;
import com.liferay.portal.kernel.search.result.SearchResultContributor;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 * @author André de Oliveira
 */
@Component(service = SearchResultContributor.class)
public class DLFileEntrySearchResultContributor
	implements SearchResultContributor {

	@Override
	public void addRelatedModel(
			SearchResult searchResult, Document document, Locale locale,
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws PortalException {

		long entryClassPK = GetterUtil.getLong(
			document.get(Field.ENTRY_CLASS_PK));

		FileEntry fileEntry = _dlAppLocalService.getFileEntry(entryClassPK);

		if (fileEntry != null) {
			Summary summary = _summaryFactory.getSummary(
				document, DLFileEntry.class.getName(),
				fileEntry.getFileEntryId(), locale, portletRequest,
				portletResponse);

			if (Validator.isNull(summary.getContent())) {
				summary.setContent(fileEntry.getTitle());
			}

			searchResult.addFileEntry(fileEntry, summary);
		}
		else {
			long classNameId = GetterUtil.getLong(
				document.get(Field.CLASS_NAME_ID));

			ClassName className = _classNameLocalService.getClassName(
				classNameId);

			long classPK = GetterUtil.getLong(document.get(Field.CLASS_PK));

			Summary summary = _summaryFactory.getSummary(
				document, className.getClassName(), classPK, locale,
				portletRequest, portletResponse);

			searchResult.setSummary(summary);
		}
	}

	@Override
	public String getEntryClassName() {
		return DLFileEntryConstants.getClassName();
	}

	@Reference(unbind = "-")
	public void setClassNameLocalService(
		ClassNameLocalService classNameLocalService) {

		_classNameLocalService = classNameLocalService;
	}

	@Reference(unbind = "-")
	public void setDLAppLocalService(DLAppLocalService dlAppLocalService) {
		_dlAppLocalService = dlAppLocalService;
	}

	@Reference(unbind = "-")
	public void setSummaryFactory(SummaryFactory summaryFactory) {
		_summaryFactory = summaryFactory;
	}

	private ClassNameLocalService _classNameLocalService;
	private DLAppLocalService _dlAppLocalService;
	private SummaryFactory _summaryFactory;

}