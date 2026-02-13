/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.document.library.configuration.DLConfiguration;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.object.constants.ObjectFolderConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.service.ObjectDefinitionService;
import com.liferay.object.service.ObjectDefinitionSettingLocalService;
import com.liferay.object.service.ObjectEntryFolderLocalServiceUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.editor.configuration.EditorConfiguration;
import com.liferay.portal.kernel.editor.configuration.EditorConfigurationFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.cms.site.initializer.internal.util.ActionUtil;
import com.liferay.site.cms.site.initializer.internal.util.PermissionUtil;
import com.liferay.translation.exporter.TranslationInfoItemFieldValuesExporter;
import com.liferay.translation.exporter.TranslationInfoItemFieldValuesExporterRegistry;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marco Galluzzi
 */
public abstract class BaseSectionDisplayContext {

	public BaseSectionDisplayContext(
		DepotEntryLocalService depotEntryLocalService,
		DLConfiguration dlConfiguration, GroupLocalService groupLocalService,
		HttpServletRequest httpServletRequest, Language language,
		ObjectDefinitionService objectDefinitionService,
		ObjectDefinitionSettingLocalService objectDefinitionSettingLocalService,
		ModelResourcePermission<ObjectEntryFolder>
			objectEntryFolderModelResourcePermission,
		Portal portal,
		TranslationInfoItemFieldValuesExporterRegistry
			translationInfoItemFieldValuesExporterRegistry) {

		this.depotEntryLocalService = depotEntryLocalService;

		_dlConfiguration = dlConfiguration;

		this.groupLocalService = groupLocalService;
		this.httpServletRequest = httpServletRequest;
		this.language = language;

		_objectDefinitionService = objectDefinitionService;

		this.portal = portal;
		_translationInfoItemFieldValuesExporterRegistry =
			translationInfoItemFieldValuesExporterRegistry;

		themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		objectEntryFolder = _getObjectEntryFolder(
			themeDisplay.getCompanyId(),
			httpServletRequest.getAttribute(InfoDisplayWebKeys.INFO_ITEM));

		_sectionDisplayContextHelper = new SectionDisplayContextHelper(
			depotEntryLocalService, groupLocalService, language,
			objectDefinitionSettingLocalService,
			objectEntryFolderModelResourcePermission, portal);
	}

	public String getAdditionalAPIURLParameters() {
		return _sectionDisplayContextHelper.getAdditionalAPIURLParameters(
			getCMSSectionFilterString(), httpServletRequest,
			getRootObjectEntryFolderExternalReferenceCode());
	}

	public Map<String, Object> getAdditionalProps() {
		return HashMapBuilder.<String, Object>put(
			"assetLibraries",
			_sectionDisplayContextHelper.getDepotEntriesJSONArray(
				httpServletRequest,
				getRootObjectEntryFolderExternalReferenceCode())
		).put(
			"autocompleteURL",
			() -> StringBundler.concat(
				"/o/search/v1.0/search?emptySearch=",
				"true&entryClassNames=com.liferay.portal.kernel.model.User,",
				"com.liferay.portal.kernel.model.UserGroup&nestedFields=",
				"embedded")
		).put(
			"availableExportFileFormats",
			() -> TransformUtil.transform(
				_translationInfoItemFieldValuesExporterRegistry.
					getTranslationInfoItemFieldValuesExporters(),
				this::_getExportFileFormatJSONObject)
		).put(
			"availableTargetLocales",
			_getLocalesJSONArray(
				themeDisplay.getLocale(),
				LanguageUtil.getAvailableLocales(themeDisplay.getSiteGroupId()))
		).put(
			"baseAssetLibraryViewURL", ActionUtil.getBaseSpaceURL(themeDisplay)
		).put(
			"baseFolderViewURL", ActionUtil.getBaseViewFolderURL(themeDisplay)
		).put(
			"brokenLinksCheckerEnabled",
			GetterUtil.getBoolean(
				PropsUtil.get(PropsKeys.CMS_BROKEN_LINKS_CHECKER_ENABLED))
		).put(
			"cmsGroupId",
			() -> {
				try {
					Group group = groupLocalService.getGroup(
						themeDisplay.getCompanyId(), GroupConstants.CMS);

					return GetterUtil.getLong(group.getGroupId());
				}
				catch (PortalException portalException) {
					if (_log.isDebugEnabled()) {
						_log.debug(portalException);
					}
				}

				return null;
			}
		).put(
			"collaboratorURLs",
			() -> {
				Map<String, String> collaboratorURLs = new HashMap<>();

				for (ObjectDefinition objectDefinition :
						_objectDefinitionService.getCMSObjectDefinitions(
							themeDisplay.getCompanyId(),
							getObjectFolderExternalReferenceCodes())) {

					collaboratorURLs.put(
						objectDefinition.getClassName(),
						StringBundler.concat(
							"/o", objectDefinition.getRESTContextPath(),
							"/{objectEntryId}/collaborators"));
				}

				collaboratorURLs.put(
					ObjectEntryFolder.class.getName(),
					"/o/headless-object/v1.0/object-entry-folders" +
						"/{objectEntryFolderId}/collaborators");

				return collaboratorURLs;
			}
		).put(
			"commentsProps",
			HashMapBuilder.<String, Object>put(
				"addCommentURL",
				StringBundler.concat(
					themeDisplay.getPortalURL(), themeDisplay.getPathMain(),
					GroupConstants.CMS_FRIENDLY_URL,
					"/add_content_item_comment")
			).put(
				"deleteCommentURL",
				StringBundler.concat(
					themeDisplay.getPortalURL(), themeDisplay.getPathMain(),
					GroupConstants.CMS_FRIENDLY_URL,
					"/delete_content_item_comment")
			).put(
				"editCommentURL",
				StringBundler.concat(
					themeDisplay.getPortalURL(), themeDisplay.getPathMain(),
					GroupConstants.CMS_FRIENDLY_URL,
					"/edit_content_item_comment")
			).put(
				"editorConfig",
				() -> {
					EditorConfiguration contentItemCommentEditorConfiguration =
						EditorConfigurationFactoryUtil.getEditorConfiguration(
							StringPool.BLANK, "contentItemCommentEditor",
							StringPool.BLANK, Collections.emptyMap(),
							themeDisplay,
							RequestBackedPortletURLFactoryUtil.create(
								httpServletRequest));

					Map<String, Object> data =
						contentItemCommentEditorConfiguration.getData();

					return data.get("editorConfig");
				}
			).put(
				"getCommentsURL",
				StringBundler.concat(
					themeDisplay.getPortalURL(), themeDisplay.getPathMain(),
					GroupConstants.CMS_FRIENDLY_URL, "/get_asset_comments")
			).build()
		).put(
			"contentViewURL",
			StringBundler.concat(
				themeDisplay.getPortalURL(), themeDisplay.getPathMain(),
				GroupConstants.CMS_FRIENDLY_URL,
				"/edit_content_item?&p_l_mode=read&p_p_state=",
				LiferayWindowState.POP_UP, "&redirect=",
				themeDisplay.getURLCurrent(), "&objectEntryId={embedded.id}")
		).put(
			"defaultPermissionAdditionalProps",
			PermissionUtil.getDefaultPermissionAdditionalProps(
				httpServletRequest, themeDisplay)
		).put(
			"fileMimeTypeCssClasses",
			() -> {
				if (_dlConfiguration == null) {
					return null;
				}

				return _getFileMimeTypeCssClasses();
			}
		).put(
			"fileMimeTypeIcons",
			() -> {
				if (_dlConfiguration == null) {
					return null;
				}

				return _getFileMimeTypeIcons();
			}
		).put(
			"objectDefinitionCssClasses",
			HashMapBuilder.put(
				"default", "content-icon-custom-structure"
			).put(
				"L_CMS_BASIC_WEB_CONTENT", "content-icon-basic-content"
			).put(
				"L_CMS_BLOG", "content-icon-blog"
			).put(
				"L_CMS_EXTERNAL_VIDEO", "file-icon-color-3"
			).build()
		).put(
			"objectDefinitionIcons",
			HashMapBuilder.put(
				"default", "web-content"
			).put(
				"L_CMS_BASIC_WEB_CONTENT", "forms"
			).put(
				"L_CMS_BLOG", "blogs"
			).put(
				"L_CMS_EXTERNAL_VIDEO", "document-multimedia"
			).build()
		).put(
			"objectEntryFolderExternalReferenceCode",
			() -> {
				if (objectEntryFolder == null) {
					return null;
				}

				return objectEntryFolder.getExternalReferenceCode();
			}
		).put(
			"parentObjectEntryFolderExternalReferenceCode",
			_getParentObjectEntryFolderExternalReferenceCode()
		).put(
			"redirect", themeDisplay.getURLCurrent()
		).build();
	}

	public String getAPIURL() {
		return "/o/search/v1.0/search?" + getAdditionalAPIURLParameters();
	}

	public Map<String, Object> getBreadcrumbProps() throws PortalException {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		addBreadcrumbItem(jsonArray, false, null, _getLayoutName());

		return HashMapBuilder.<String, Object>put(
			"breadcrumbItems", jsonArray
		).put(
			"hideSpace", true
		).build();
	}

	public List<DropdownItem> getBulkActionDropdownItems() {
		return ListUtil.fromArray(
			new FDSActionDropdownItem(
				"#", "trash", "delete",
				LanguageUtil.get(httpServletRequest, "delete"), null, null,
				null));
	}

	public CreationMenu getCreationMenu() {
		return _sectionDisplayContextHelper.getCreationMenu(
			getCreationMenuDropdownItems(), httpServletRequest,
			getRootObjectEntryFolderExternalReferenceCode());
	}

	public List<DropdownItem> getCreationMenuDropdownItems() {
		return Collections.emptyList();
	}

	public abstract Map<String, Object> getEmptyState();

	public List<FDSActionDropdownItem> getFDSActionDropdownItems() {
		return _sectionDisplayContextHelper.getFDSActionDropdownItems(
			httpServletRequest);
	}

	protected void addBreadcrumbItem(
		JSONArray jsonArray, boolean active, String friendlyURL, String label) {

		jsonArray.put(
			JSONUtil.put(
				"active", active
			).put(
				"href", friendlyURL
			).put(
				"label", label
			));
	}

	protected String appendGroupIds(String filterString) {
		return _sectionDisplayContextHelper.appendGroupIds(
			filterString, httpServletRequest);
	}

	protected String appendStatus(String filterString) {
		return _sectionDisplayContextHelper.appendStatus(filterString);
	}

	protected abstract String getCMSSectionFilterString();

	protected String[] getObjectFolderExternalReferenceCodes() {
		return new String[] {
			ObjectFolderConstants.EXTERNAL_REFERENCE_CODE_CONTENT_STRUCTURES,
			ObjectFolderConstants.EXTERNAL_REFERENCE_CODE_FILE_TYPES
		};
	}

	protected String getRootObjectEntryFolderExternalReferenceCode() {
		return null;
	}

	protected final DepotEntryLocalService depotEntryLocalService;
	protected final GroupLocalService groupLocalService;
	protected final HttpServletRequest httpServletRequest;
	protected final Language language;
	protected final ObjectEntryFolder objectEntryFolder;
	protected final Portal portal;
	protected final ThemeDisplay themeDisplay;

	private JSONObject _getExportFileFormatJSONObject(
		TranslationInfoItemFieldValuesExporter
			translationInfoItemFieldValuesExporter) {

		return JSONUtil.put(
			"displayName",
			() -> {
				InfoLocalizedValue<String> labelInfoLocalizedValue =
					translationInfoItemFieldValuesExporter.
						getLabelInfoLocalizedValue();

				return labelInfoLocalizedValue.getValue(
					themeDisplay.getLocale());
			}
		).put(
			"mimeType", translationInfoItemFieldValuesExporter.getMimeType()
		);
	}

	private Map<String, String> _getFileMimeTypeCssClasses() {
		return HashMapBuilder.put(
			"default", "file-icon-color-0"
		).putAll(
			_getFileMimeTypeValues(
				_dlConfiguration.codeFileMimeTypes(), "file-icon-color-7")
		).putAll(
			_getFileMimeTypeValues(
				_dlConfiguration.compressedFileMimeTypes(), "file-icon-color-1")
		).putAll(
			_getFileMimeTypeValues(
				ArrayUtil.append(
					_dlConfiguration.multimediaFileMimeTypes(),
					ContentTypes.
						APPLICATION_VND_LIFERAY_VIDEO_EXTERNAL_SHORTCUT_HTML),
				"file-icon-color-3")
		).putAll(
			_getFileMimeTypeValues(
				_dlConfiguration.presentationFileMimeTypes(),
				"file-icon-color-4")
		).putAll(
			_getFileMimeTypeValues(
				_dlConfiguration.spreadSheetFileMimeTypes(),
				"file-icon-color-2")
		).putAll(
			_getFileMimeTypeValues(
				_dlConfiguration.textFileMimeTypes(), "file-icon-color-6")
		).putAll(
			_getFileMimeTypeValues(
				_dlConfiguration.vectorialFileMimeTypes(), "file-icon-color-5")
		).build();
	}

	private Map<String, String> _getFileMimeTypeIcons() {
		return HashMapBuilder.put(
			"default", "document-default"
		).putAll(
			_getFileMimeTypeValues(
				_dlConfiguration.codeFileMimeTypes(), "document-code")
		).putAll(
			_getFileMimeTypeValues(
				_dlConfiguration.compressedFileMimeTypes(),
				"document-compressed")
		).putAll(
			_getFileMimeTypeValues(
				_dlConfiguration.presentationFileMimeTypes(),
				"document-presentation")
		).putAll(
			_getFileMimeTypeValues(
				_dlConfiguration.spreadSheetFileMimeTypes(), "document-table")
		).putAll(
			_getFileMimeTypeValues(
				_dlConfiguration.textFileMimeTypes(), "document-text")
		).putAll(
			_getFileMimeTypeValues(
				_dlConfiguration.vectorialFileMimeTypes(), "document-vector")
		).putAll(
			_getFileMimeTypeMultimediaCssClasses(
				ArrayUtil.append(
					_dlConfiguration.multimediaFileMimeTypes(),
					ContentTypes.
						APPLICATION_VND_LIFERAY_VIDEO_EXTERNAL_SHORTCUT_HTML))
		).build();
	}

	private Map<String, String> _getFileMimeTypeMultimediaCssClasses(
		String[] mimeTypes) {

		Map<String, String> fileMimeTypeMultimediaCssClasses = new HashMap<>();

		for (String mimeType : mimeTypes) {
			if (mimeType.startsWith("image")) {
				fileMimeTypeMultimediaCssClasses.put(
					mimeType, "document-image");
			}
			else {
				fileMimeTypeMultimediaCssClasses.put(
					mimeType, "document-multimedia");
			}
		}

		return fileMimeTypeMultimediaCssClasses;
	}

	private Map<String, String> _getFileMimeTypeValues(
		String[] mimeTypes, String value) {

		Map<String, String> fileMimeTypeValues = new HashMap<>();

		for (String mimeType : mimeTypes) {
			fileMimeTypeValues.put(mimeType, value);
		}

		return fileMimeTypeValues;
	}

	private String _getLayoutName() {
		Layout layout = themeDisplay.getLayout();

		if (layout == null) {
			return null;
		}

		return layout.getName(themeDisplay.getLocale(), true);
	}

	private JSONArray _getLocalesJSONArray(
		Locale locale, Collection<Locale> locales) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		locales.forEach(
			currentLocale -> jsonArray.put(
				JSONUtil.put(
					"displayName",
					LocaleUtil.getLocaleDisplayName(currentLocale, locale)
				).put(
					"languageId", LocaleUtil.toLanguageId(currentLocale)
				)));

		return jsonArray;
	}

	private ObjectEntryFolder _getObjectEntryFolder(
		long companyId, Object object) {

		if (object instanceof DepotEntry) {
			DepotEntry depotEntry = (DepotEntry)object;

			return ObjectEntryFolderLocalServiceUtil.
				fetchObjectEntryFolderByExternalReferenceCode(
					getRootObjectEntryFolderExternalReferenceCode(),
					depotEntry.getGroupId(), companyId);
		}
		else if (object instanceof ObjectEntryFolder) {
			return (ObjectEntryFolder)object;
		}

		return null;
	}

	private String _getParentObjectEntryFolderExternalReferenceCode() {
		if (objectEntryFolder == null) {
			return getRootObjectEntryFolderExternalReferenceCode();
		}

		return objectEntryFolder.getExternalReferenceCode();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseSectionDisplayContext.class);

	private final DLConfiguration _dlConfiguration;
	private final ObjectDefinitionService _objectDefinitionService;
	private final SectionDisplayContextHelper _sectionDisplayContextHelper;
	private final TranslationInfoItemFieldValuesExporterRegistry
		_translationInfoItemFieldValuesExporterRegistry;

}