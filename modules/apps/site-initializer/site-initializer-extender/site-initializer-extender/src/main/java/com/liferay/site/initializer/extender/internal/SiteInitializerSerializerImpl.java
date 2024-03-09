/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.initializer.extender.internal;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReader;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactory;
import com.liferay.site.exception.SerializationException;
import com.liferay.site.initializer.SiteInitializerSerializer;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalService;
import com.liferay.style.book.util.comparator.StyleBookEntryNameComparator;

import java.io.File;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(service = SiteInitializerSerializer.class)
public class SiteInitializerSerializerImpl
	implements SiteInitializerSerializer {

	@Override
	public File serialize(long groupId) throws SerializationException {
		if (!FeatureFlagManagerUtil.isEnabled("LPD-19870")) {
			throw new UnsupportedOperationException();
		}

		try {
			ZipWriter zipWriter = _zipWriterFactory.getZipWriter();

			_serializeDocuments(
				groupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				"documents/group", zipWriter);
			_serializeDDMStructures(groupId, zipWriter);
			_serializeDDMTemplates(groupId, zipWriter);
			_serializeStyleBookEntries(groupId, zipWriter);

			return zipWriter.getFile();
		}
		catch (Exception exception) {
			throw new SerializationException(exception);
		}
	}

	private void _addZipEntry(
			String fileName, JSONObject jsonObject, ZipWriter zipWriter)
		throws Exception {

		_addZipEntry(fileName, JSONUtil.toString(jsonObject), zipWriter);
	}

	private void _addZipEntry(
			String fileName, String string, ZipWriter zipWriter)
		throws Exception {

		zipWriter.addEntry("site-initializer/" + fileName, string);
	}

	private String _normalize(String string) {
		string = StringUtil.toLowerCase(string);

		return StringUtil.replace(string, CharPool.SPACE, CharPool.DASH);
	}

	private void _serializeDDMStructure(
			DDMStructure ddmStructure, ZipWriter zipWriter)
		throws Exception {

		Document document = _saxReader.createDocument();

		Element rootElement = document.addElement("root");

		Element structureElement = rootElement.addElement("structure");

		Element definitionElement = structureElement.addElement("definition");

		String definition = ddmStructure.getDefinition();

		if (JSONUtil.isJSONObject(definition)) {
			definition = JSONUtil.toString(
				_jsonFactory.createJSONObject(definition));
		}

		definitionElement.addCDATA(definition);

		Element descriptionElement = structureElement.addElement("description");

		descriptionElement.addText(
			ddmStructure.getDescription(LocaleUtil.getDefault()));

		Element nameElement = structureElement.addElement("name");

		nameElement.addText(ddmStructure.getName(LocaleUtil.getDefault()));

		_addZipEntry(
			"ddm-structures/" + _normalize(ddmStructure.getStructureKey()) +
				".xml",
			document.formattedString(), zipWriter);
	}

	private void _serializeDDMStructures(long groupId, ZipWriter zipWriter)
		throws Exception {

		for (DDMStructure ddmStructure :
				_ddmStructureLocalService.getStructures(groupId)) {

			_serializeDDMStructure(ddmStructure, zipWriter);
		}
	}

	private void _serializeDDMTemplate(
			DDMTemplate ddmTemplate, ZipWriter zipWriter)
		throws Exception {

		_addZipEntry(
			"ddm-templates/" + _normalize(ddmTemplate.getTemplateKey()) +
				"/ddm-template.ftl",
			ddmTemplate.getScript(), zipWriter);
		_addZipEntry(
			"ddm-templates/" + _normalize(ddmTemplate.getTemplateKey()) +
				"/ddm-template.json",
			JSONUtil.put(
				"className", ddmTemplate.getClassName()
			).put(
				"ddmTemplateKey", ddmTemplate.getTemplateKey()
			).put(
				"name", ddmTemplate.getName(LocaleUtil.getDefault())
			).put(
				"resourceClassName", ddmTemplate.getResourceClassName()
			),
			zipWriter);
	}

	private void _serializeDDMTemplates(long groupId, ZipWriter zipWriter)
		throws Exception {

		for (DDMTemplate ddmTemplate :
				_ddmTemplateLocalService.getTemplatesByGroupId(groupId)) {

			_serializeDDMTemplate(ddmTemplate, zipWriter);
		}
	}

	private void _serializeDocuments(
			long groupId, Long parentFolderId, String parentFolderName,
			ZipWriter zipWriter)
		throws Exception {

		List<FileEntry> fileEntries = _dlAppService.getFileEntries(
			groupId, parentFolderId);

		for (FileEntry fileEntry : fileEntries) {
			_serializeFileEntry(fileEntry, parentFolderName, zipWriter);
		}

		List<Folder> subfolders = _dlAppService.getFolders(
			groupId, parentFolderId);

		for (Folder subfolder : subfolders) {
			_serializeDocuments(
				groupId, subfolder.getFolderId(),
				parentFolderName + "/" + subfolder.getName(), zipWriter);
		}
	}

	private void _serializeFileEntry(
			FileEntry fileEntry, String parentFolderName, ZipWriter zipWriter)
		throws Exception {

		byte[] fileContentBytesArray = _file.getBytes(
			fileEntry.getContentStream());

		String content = new String(fileContentBytesArray);

		_addZipEntry(
			_normalize(parentFolderName + "/" + fileEntry.getFileName()),
			content, zipWriter);
	}

	private void _serializeStyleBookEntries(long groupId, ZipWriter zipWriter)
		throws Exception {

		List<StyleBookEntry> styleBookEntries =
			_styleBookEntryLocalService.getStyleBookEntries(
				groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StyleBookEntryNameComparator(true));

		for (StyleBookEntry styleBookEntry : styleBookEntries) {
			styleBookEntry.populateZipWriter(
				zipWriter, "site-initializer/style-books");
		}
	}

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private com.liferay.portal.kernel.util.File _file;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private SAXReader _saxReader;

	@Reference
	private StyleBookEntryLocalService _styleBookEntryLocalService;

	@Reference
	private ZipWriterFactory _zipWriterFactory;

}