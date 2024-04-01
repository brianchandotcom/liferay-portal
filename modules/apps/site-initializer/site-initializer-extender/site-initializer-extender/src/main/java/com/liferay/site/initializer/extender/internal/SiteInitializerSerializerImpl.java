/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.initializer.extender.internal;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.headless.delivery.dto.v1_0.PageDefinition;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalFolderService;
import com.liferay.layout.exporter.LayoutsExporter;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReader;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactory;
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
import java.util.Set;
import java.util.TreeSet;

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
			_serializeLayoutPageTemplates(groupId, zipWriter);
			_serializeLayouts(groupId, "layouts", zipWriter);
			_serializeJournalArticles(
				groupId, JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				"journal-articles", zipWriter);
			_serializeStyleBookEntries(groupId, zipWriter);
			_serializeUserAccounts(groupId, zipWriter);

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
			String fileName, JSONArray jsonArray, ZipWriter zipWriter)
		throws Exception {

		_addZipEntry(fileName, JSONUtil.toString(jsonArray), zipWriter);
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

		List<DDMStructure> ddmStructures =
			_ddmStructureLocalService.getStructures(groupId);

		for (DDMStructure ddmStructure : ddmStructures) {
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

		List<DDMTemplate> ddmTemplates =
			_ddmTemplateLocalService.getTemplatesByGroupId(groupId);

		for (DDMTemplate ddmTemplate : ddmTemplates) {
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

	private void _serializeJournalArticles(
			long groupId, long parentFolderId, String zipDirName,
			ZipWriter zipWriter)
		throws Exception {

		List<JournalArticle> journalArticles =
			_journalArticleLocalService.getArticles(groupId, parentFolderId);

		for (JournalArticle journalArticle : journalArticles) {
			_addZipEntry(
				_normalize(
					StringBundler.concat(
						zipDirName, "/", journalArticle.getArticleId(),
						".json")),
				JSONUtil.put(
					"ddmStructureKey", journalArticle.getDDMStructureKey()
				).put(
					"name", journalArticle.getArticleId()
				),
				zipWriter);
			_addZipEntry(
				_normalize(
					StringBundler.concat(
						zipDirName, "/", journalArticle.getArticleId(),
						".xml")),
				journalArticle.getContent(), zipWriter);
		}

		List<JournalFolder> journalFolders = _journalFolderService.getFolders(
			groupId, parentFolderId);

		for (JournalFolder journalFolder : journalFolders) {
			_addZipEntry(
				_normalize(
					StringBundler.concat(
						zipDirName, "/", journalFolder.getName(),
						"metadata.json")),
				JSONUtil.put(
					"description", journalFolder.getDescription()
				).put(
					"externalReferenceCode",
					journalFolder.getExternalReferenceCode()
				).put(
					"name", journalFolder.getName()
				).put(
					"viewableBy", "Anyone"
				),
				zipWriter);

			_serializeJournalArticles(
				groupId, journalFolder.getFolderId(),
				StringBundler.concat(zipDirName, "/", journalFolder.getName()),
				zipWriter);
		}
	}

	private void _serializeLayout(
			Layout layout, String zipDirName, ZipWriter zipWriter)
		throws Exception {

		_addZipEntry(
			zipDirName + "/page.json",
			JSONUtil.put(
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

					JSONObject jsonObject = JSONUtil.put("key", parts[0]);

					if (Objects.equals(
							layout.getType(),
							LayoutConstants.TYPE_LINK_TO_LAYOUT)) {

						Layout linkToLayout = _layoutLocalService.getLayout(
							layout.getGroupId(), layout.isPrivateLayout(),
							GetterUtil.getLong(parts[1].replace("\n", "")));

						jsonObject.put(
							"value",
							"[$LAYOUT_ID:" +
								linkToLayout.getName(LocaleUtil.US) + "$]");
					}
					else if (Objects.equals(
								layout.getType(), LayoutConstants.TYPE_URL)) {

						jsonObject.put("value", parts[1].replace("\n", ""));
					}

					return JSONUtil.put(jsonObject);
				}
			),
			zipWriter);

		if (!Objects.equals(layout.getType(), LayoutConstants.TYPE_CONTENT)) {
			return;
		}

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layout.getGroupId(), layout.getPlid());

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
			LayoutStructure.of(
				layoutPageTemplateStructure.
					getDefaultSegmentsExperienceData()));

		_addZipEntry(
			zipDirName + "/page-definition.json",
			JSONUtil.put(
				"pageElement", pageDefinition.getPageElement()
			).put(
				"settings", pageDefinition.getSettings()
			),
			zipWriter);
	}

	private void _serializeLayoutPageTemplates(
			long groupId, ZipWriter zipWriter)
		throws Exception {

		File file = _layoutsExporter.exportLayoutPageTemplateEntries(groupId);
		ZipReader zipReader = null;

		try {
			zipReader = _zipReaderFactory.getZipReader(file);

			for (String name : zipReader.getEntries()) {
				InputStream inputStream = zipReader.getEntryAsInputStream(name);

				_addZipEntry(
					"layout-page-templates/" + name, inputStream, zipWriter);
			}
		}
		finally {
			if (zipReader != null) {
				zipReader.close();
			}

			file.delete();
		}
	}

	private void _serializeLayouts(
			long groupId, boolean privateLayout, long layoutId,
			String zipDirName, ZipWriter zipWriter)
		throws Exception {

		List<Layout> layouts = _layoutLocalService.getLayouts(
			groupId, privateLayout, layoutId);

		for (Layout layout : layouts) {
			zipDirName +=
				CharPool.SLASH + _normalize(layout.getName(LocaleUtil.US));

			_serializeLayout(layout, zipDirName, zipWriter);
			_serializeLayouts(
				groupId, layout.isPrivateLayout(), layout.getLayoutId(),
				zipDirName, zipWriter);
		}
	}

	private void _serializeLayouts(
			long groupId, String zipDirName, ZipWriter zipWriter)
		throws Exception {

		_serializeLayouts(
			groupId, false, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			zipDirName, zipWriter);
		_serializeLayouts(
			groupId, true, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, zipDirName,
			zipWriter);
	}

	private void _serializeOrganization(
		Organization organization, JSONArray jsonArray) {

		JSONObject organizationJSONObject = JSONUtil.put(
			"childOrganizations", _jsonFactory.createJSONArray()
		).put(
			"externalReferenceCode", organization.getExternalReferenceCode()
		).put(
			"name", organization.getName()
		);

		for (Organization childOrganization :
				_organizationLocalService.getOrganizations(
					organization.getCompanyId(),
					organization.getOrganizationId())) {

			_serializeOrganization(
				childOrganization,
				(JSONArray)organizationJSONObject.get("childOrganizations"));
		}

		jsonArray.put(organizationJSONObject);
	}

	private void _serializeOrganizations(
			Set<Organization> organizations, ZipWriter zipWriter)
		throws Exception {

		JSONArray organizationsJSONArray = _jsonFactory.createJSONArray();

		for (Organization organization : organizations) {
			_serializeOrganization(organization, organizationsJSONArray);
		}

		_addZipEntry("organizations.json", organizationsJSONArray, zipWriter);
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

	private void _serializeUserAccounts(long groupId, ZipWriter zipWriter)
		throws Exception {

		Set<AccountEntry> accountEntries = new TreeSet<>();
		Set<Organization> organizations = new TreeSet<>();
		Set<Role> roles = new TreeSet<>();

		_addZipEntry(
			"user-accounts.json",
			JSONUtil.toJSONArray(
				_userLocalService.getGroupUsers(groupId),
				user -> {
					for (Role role : user.getRoles()) {
						if (StringUtil.equals(
								role.getName(), RoleConstants.ADMINISTRATOR) ||
							StringUtil.equals(
								role.getName(), RoleConstants.POWER_USER)) {

							return null;
						}
					}

					roles.addAll(user.getRoles());

					List<AccountEntry> userAccountEntries =
						_accountEntryLocalService.getUserAccountEntries(
							user.getUserId(), null, null, null,
							QueryUtil.ALL_POS, QueryUtil.ALL_POS);

					accountEntries.addAll(userAccountEntries);

					List<Organization> userOrganizations =
						user.getOrganizations();

					organizations.addAll(userOrganizations);

					return JSONUtil.put(
						"accountBriefs",
						JSONUtil.toJSONArray(
							userAccountEntries,
							accountEntry -> JSONUtil.put(
								"externalReferenceCode",
								accountEntry.getExternalReferenceCode()))
					).put(
						"alternateName", user.getScreenName()
					).put(
						"emailAddress", user.getEmailAddresses()
					).put(
						"externalReferenceCode", user.getExternalReferenceCode()
					).put(
						"familyName", user.getLastName()
					).put(
						"givenName", user.getFirstName()
					).put(
						"name", user.getFullName()
					).put(
						"organizationBriefs",
						JSONUtil.toJSONArray(
							userOrganizations,
							organization -> JSONUtil.put(
								"name", organization.getName()))
					);
				}),
			zipWriter);

		_addZipEntry(
			"roles.json",
			JSONUtil.toJSONArray(
				roles,
				role -> {
					if (StringUtil.equals(role.getName(), RoleConstants.USER)) {
						return null;
					}

					return JSONUtil.put(
						"name", role.getName()
					).put(
						"name_i18n", JSONUtil.put("en-US", role.getName())
					).put(
						"type", role.getType()
					);
				}),
			zipWriter);

		_addZipEntry(
			"accounts.json",
			JSONUtil.toJSONArray(
				accountEntries,
				accountEntry -> JSONUtil.put(
					"externalReferenceCode",
					accountEntry.getExternalReferenceCode()
				).put(
					"name", accountEntry.getName()
				).put(
					"type", accountEntry.getType()
				)),
			zipWriter);

		_serializeOrganizations(organizations, zipWriter);
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private JournalFolderService _journalFolderService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private LayoutsExporter _layoutsExporter;

	@Reference
	private OrganizationLocalService _organizationLocalService;

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
	private UserLocalService _userLocalService;

	@Reference
	private ZipReaderFactory _zipReaderFactory;

	@Reference
	private ZipWriterFactory _zipWriterFactory;

}