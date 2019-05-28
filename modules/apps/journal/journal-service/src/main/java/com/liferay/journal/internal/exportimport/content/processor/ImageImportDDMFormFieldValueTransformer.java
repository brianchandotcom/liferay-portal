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

package com.liferay.journal.internal.exportimport.content.processor;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.util.DDMFormFieldValueTransformer;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Pavel Savinov
 */
public class ImageImportDDMFormFieldValueTransformer
	implements DDMFormFieldValueTransformer {

	public ImageImportDDMFormFieldValueTransformer(
		String content, DLAppService dlAppService,
		PortletDataContext portletDataContext, StagedModel stagedModel) {

		_dlAppService = dlAppService;
		_portletDataContext = portletDataContext;
		_stagedModel = stagedModel;

		_setContent(content);
	}

	public String getContent() {
		return _document.asXML();
	}

	@Override
	public String getFieldType() {
		return DDMFormFieldType.IMAGE;
	}

	@Override
	public void transform(DDMFormFieldValue ddmFormFieldValue)
		throws PortalException {

		Value value = ddmFormFieldValue.getValue();

		for (Locale locale : value.getAvailableLocales()) {
			String valueString = value.getString(locale);

			JSONObject jsonObject = null;

			try {
				jsonObject = JSONFactoryUtil.createJSONObject(valueString);
			}
			catch (JSONException jsone) {
				if (_log.isDebugEnabled()) {
					_log.debug("Unable to parse JSON", jsone);
				}

				continue;
			}

			FileEntry importedFileEntry = fetchImportedFileEntry(
				_portletDataContext, jsonObject.getLong("fileEntryId"),
				jsonObject.getString("uuid"));

			if (importedFileEntry == null) {
				continue;
			}

			String fileEntryJSON = toJSON(
				importedFileEntry, jsonObject.getString("type"),
				jsonObject.getString("alt"));

			value.addString(locale, fileEntryJSON);

			StringBundler sb = new StringBundler(4);

			sb.append("//dynamic-element[@type='image']");
			sb.append("/dynamic-content[contains(text(),");
			sb.append(HtmlUtil.escapeXPathAttribute(valueString));
			sb.append(")]");

			XPath xPath = SAXReaderUtil.createXPath(sb.toString());

			List<Node> imageNodes = xPath.selectNodes(_document);

			for (Node imageNode : imageNodes) {
				Element imageElement = (Element)imageNode;

				imageElement.clearContent();

				imageElement.addCDATA(fileEntryJSON);
			}
		}
	}

	protected FileEntry fetchImportedFileEntry(
			PortletDataContext portletDataContext, long oldClassPK, String uuid)
		throws PortalException {

		try {
			Map<Long, Long> fileEntryPKs =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					DLFileEntry.class);

			Long classPK = fileEntryPKs.get(oldClassPK);

			if (classPK == null) {
				if (Validator.isNotNull(uuid)) {
					return _dlAppService.getFileEntryByUuidAndGroupId(
						uuid, portletDataContext.getScopeGroupId());
				}

				return null;
			}

			return _dlAppService.getFileEntry(classPK);
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to find file entry", pe);
			}
		}

		return null;
	}

	protected String toJSON(FileEntry fileEntry, String type, String alt) {
		JournalArticle article = (JournalArticle)_stagedModel;

		JSONObject jsonObject = JSONUtil.put(
			"alt", alt
		).put(
			"fileEntryId", fileEntry.getFileEntryId()
		).put(
			"groupId", fileEntry.getGroupId()
		).put(
			"name", fileEntry.getFileName()
		).put(
			"resourcePrimKey", article.getResourcePrimKey()
		).put(
			"title", fileEntry.getTitle()
		).put(
			"type", type
		).put(
			"uuid", fileEntry.getUuid()
		);

		return jsonObject.toString();
	}

	private void _setContent(String content) {
		try {
			_document = SAXReaderUtil.read(content);
		}
		catch (DocumentException de) {
			if (_log.isDebugEnabled()) {
				_log.debug("Invalid content:\n" + content);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ImageImportDDMFormFieldValueTransformer.class);

	private final DLAppService _dlAppService;
	private Document _document;
	private final PortletDataContext _portletDataContext;
	private final StagedModel _stagedModel;

}