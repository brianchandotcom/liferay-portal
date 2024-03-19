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
import com.liferay.headless.delivery.dto.v1_0.PageDefinition;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReader;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactory;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.site.exception.SerializationException;
import com.liferay.site.initializer.SiteInitializerSerializer;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalService;
import com.liferay.style.book.util.comparator.StyleBookEntryNameComparator;

import java.io.File;
import java.io.InputStream;

import java.util.List;
import java.util.Objects;

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
			_serializeLayouts(groupId, zipWriter);
			_serializeStyleBookEntries(groupId, zipWriter);

			return zipWriter.getFile();
		}
		catch (Exception exception) {
			throw new SerializationException(exception);
		}
	}

	private void _addZipEntry(
			String fileName, InputStream inputStream, ZipWriter zipWriter)
		throws Exception {

		zipWriter.addEntry("site-initializer/" + fileName, inputStream);
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

	private String _getLayoutDirName(Layout layout) throws Exception {
		String dirName = _normalize(layout.getName(LocaleUtil.US));

		if (layout.getParentLayoutId() == 0) {
			return dirName;
		}

		Layout parentLayout = _layoutLocalService.getLayout(
			layout.getGroupId(), layout.isPrivateLayout(),
			layout.getParentLayoutId());

		return _getLayoutDirName(parentLayout) + "/" + dirName;
	}

	private LayoutStructure _getLayoutStructure(Layout layout) {
		if (layout.getType(
			).equalsIgnoreCase(
				LayoutConstants.TYPE_CONTENT
			)) {

			LayoutPageTemplateStructure layoutPageTemplateStructure =
				_layoutPageTemplateStructureLocalService.
					fetchLayoutPageTemplateStructure(
						layout.getGroupId(), layout.getPlid());

			return LayoutStructure.of(
				layoutPageTemplateStructure.getDefaultSegmentsExperienceData());
		}

		return null;
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
			long groupId, Long parentFolderId, String zipDirName,
			ZipWriter zipWriter)
		throws Exception {

		List<FileEntry> fileEntries = _dlAppService.getFileEntries(
			groupId, parentFolderId);

		for (FileEntry fileEntry : fileEntries) {
			_addZipEntry(
				_normalize(zipDirName + "/" + fileEntry.getFileName()),
				fileEntry.getContentStream(), zipWriter);
		}

		List<Folder> folders = _dlAppService.getFolders(
			groupId, parentFolderId);

		for (Folder folder : folders) {
			_serializeDocuments(
				groupId, folder.getFolderId(),
				zipDirName + "/" + folder.getName(), zipWriter);
		}
	}

	private void _serializeLayout(Layout layout, ZipWriter zipWriter)
		throws Exception {

		String dirName = "layouts/" + _getLayoutDirName(layout);

		_addZipEntry(
			dirName + "/page.json",
			JSONUtil.put(
				"friendlyURL", layout.getFriendlyURL()
			).put(
				"friendlyURL", layout.getFriendlyURL()
			).put(
				"hidden", layout.isHidden()
			).put(
				"name_i18n",
				JSONUtil.put("en_US", layout.getName(LocaleUtil.US))
			).put(
				"priority", layout.getPriority()
			).put(
				"private", layout.isPrivateLayout()
			).put(
				"system", layout.isSystem()
			).put(
				"type", layout.getType()
			).put(
				"typeSettings",
				() -> {
					if (Validator.isNull(layout.getTypeSettings())) {
						return null;
					}

					String[] parts = StringUtil.split(
						layout.getTypeSettings(), CharPool.EQUAL);

					JSONObject typeSettingsjsonObject = JSONUtil.put(
						"key", parts[0]);

					if (Objects.equals(
							layout.getType(),
							LayoutConstants.TYPE_LINK_TO_LAYOUT)) {

						Layout linkToLayout = _layoutLocalService.getLayout(
							layout.getGroupId(), layout.isPrivateLayout(),
							GetterUtil.getLong(parts[1].replace("\n", "")));

						typeSettingsjsonObject.put(
							"value",
							"[$LAYOUT_ID:" +
								linkToLayout.getName(LocaleUtil.US) + "$]");
					}
					else if (Objects.equals(
								layout.getType(), LayoutConstants.TYPE_URL)) {

						typeSettingsjsonObject.put(
							"value", parts[1].replace("\n", ""));
					}

					return JSONUtil.put(typeSettingsjsonObject);
				}
			),
			zipWriter);

		LayoutStructure layoutStructure = _getLayoutStructure(layout);

		if (layoutStructure == null) {
			return;
		}

		PageDefinition pageDefinition = _pageDefinitionDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				true, null, _dtoConverterRegistry, null, layout.getPlid(), null,
				null, null) {

				{
					setAttribute("embeddedPageDefinition", Boolean.TRUE);
					setAttribute("groupId", layout.getGroupId());
					setAttribute("layout", layout);
				}
			},
			layoutStructure);

		_addZipEntry(
			dirName + "/page-definition.json",
			JSONUtil.put(
				"pageElement", pageDefinition.getPageElement()
			).put(
				"settings", pageDefinition.getSettings()
			),
			zipWriter);
	}

	private void _serializeLayouts(long groupId, ZipWriter zipWriter)
		throws Exception {

		List<Layout> layouts = _layoutLocalService.getLayouts(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		for (Layout layout : layouts) {
			_serializeLayout(layout, zipWriter);
		}
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
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference(
		target = "(component.name=com.liferay.headless.delivery.internal.dto.v1_0.converter.PageDefinitionDTOConverter)"
	)
	private DTOConverter<LayoutStructure, PageDefinition>
		_pageDefinitionDTOConverter;

	@Reference
	private SAXReader _saxReader;

	@Reference
	private StyleBookEntryLocalService _styleBookEntryLocalService;

	@Reference
	private ZipWriterFactory _zipWriterFactory;

}