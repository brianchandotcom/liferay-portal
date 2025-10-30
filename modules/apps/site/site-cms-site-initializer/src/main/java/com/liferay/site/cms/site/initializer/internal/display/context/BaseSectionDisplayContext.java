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
import com.liferay.object.constants.ObjectFolderConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.service.ObjectDefinitionService;
import com.liferay.object.service.ObjectDefinitionSettingLocalService;
import com.liferay.object.service.ObjectEntryFolderLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.editor.configuration.EditorConfiguration;
import com.liferay.portal.kernel.editor.configuration.EditorConfigurationFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
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
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.cms.site.initializer.internal.util.ActionUtil;
import com.liferay.site.cms.site.initializer.internal.util.PermissionUtil;
import com.liferay.translation.constants.TranslationPortletKeys;

import jakarta.portlet.ActionRequest;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
		Portal portal) {

		this.depotEntryLocalService = depotEntryLocalService;

		_dlConfiguration = dlConfiguration;

		this.groupLocalService = groupLocalService;
		this.httpServletRequest = httpServletRequest;
		this.language = language;

		_objectDefinitionService = objectDefinitionService;

		this.portal = portal;

		themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		objectEntryFolder = _getObjectEntryFolder(
			themeDisplay.getCompanyId(),
			httpServletRequest.getAttribute(InfoDisplayWebKeys.INFO_ITEM));

		_baseSectionDisplayContextHelper = new BaseSectionDisplayContextHelper(
			depotEntryLocalService, groupLocalService,
			objectDefinitionSettingLocalService,
			objectEntryFolderModelResourcePermission);
	}

	public String getAdditionalAPIURLParameters() {
		return _baseSectionDisplayContextHelper.getAdditionalAPIURLParameters(
			getCMSSectionFilterString(), httpServletRequest,
			getRootObjectEntryFolderExternalReferenceCode());
	}

	public Map<String, Object> getAdditionalProps() {
		return HashMapBuilder.<String, Object>put(
			"assetLibraries",
			_baseSectionDisplayContextHelper.getDepotEntriesJSONArray(
				httpServletRequest)
		).put(
			"autocompleteURL",
			() -> StringBundler.concat(
				"/o/search/v1.0/search?emptySearch=",
				"true&entryClassNames=com.liferay.portal.kernel.model.User,",
				"com.liferay.portal.kernel.model.UserGroup&nestedFields=",
				"embedded")
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
			).build()
		).put(
			"objectDefinitionIcons",
			HashMapBuilder.put(
				"default", "web-content"
			).put(
				"L_CMS_BASIC_WEB_CONTENT", "forms"
			).put(
				"L_CMS_BLOG", "blogs"
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
		return _baseSectionDisplayContextHelper.getCreationMenu(
			getCreationMenuDropdownItems(), httpServletRequest);
	}

	public List<DropdownItem> getCreationMenuDropdownItems() {
		return Collections.emptyList();
	}

	public abstract Map<String, Object> getEmptyState();

	public List<FDSActionDropdownItem> getFDSActionDropdownItems() {
		return ListUtil.fromArray(
			new FDSActionDropdownItem(
				ActionUtil.getBaseViewFolderURL(themeDisplay) + "{embedded.id}",
				"view", "actionLinkFolder",
				LanguageUtil.get(httpServletRequest, "view-folder"), "get",
				"update", null,
				HashMapBuilder.<String, Object>put(
					"entryClassName", ObjectEntryFolder.class.getName()
				).build()),
			new FDSActionDropdownItem(
				StringBundler.concat(
					themeDisplay.getPathFriendlyURLPublic(),
					GroupConstants.CMS_FRIENDLY_URL, "/e/edit-folder/",
					portal.getClassNameId(ObjectEntryFolder.class),
					"/{embedded.id}?redirect=", themeDisplay.getURLCurrent()),
				"pencil", "editFolder",
				LanguageUtil.get(httpServletRequest, "edit"), "get", "update",
				"update",
				HashMapBuilder.<String, Object>put(
					"entryClassName", ObjectEntryFolder.class.getName()
				).build()),
			new FDSActionDropdownItem(
				StringBundler.concat(
					themeDisplay.getPortalURL(), themeDisplay.getPathMain(),
					GroupConstants.CMS_FRIENDLY_URL,
					"/edit_content_item?objectEntryId={embedded.id}&",
					"redirect=", themeDisplay.getURLCurrent()),
				"pencil", "actionLink",
				LanguageUtil.get(httpServletRequest, "edit"), "get", "update",
				null),
			new FDSActionDropdownItem(
				null, "share", "share",
				LanguageUtil.get(httpServletRequest, "share"), "get", "share",
				"link"),
			new FDSActionDropdownItem(
				StringBundler.concat(
					themeDisplay.getPortalURL(), themeDisplay.getPathMain(),
					GroupConstants.CMS_FRIENDLY_URL,
					"/translate_content_item?objectEntryId={embedded.id}&",
					"redirect=", themeDisplay.getURLCurrent()),
				"automatic-translate", "translate",
				LanguageUtil.get(httpServletRequest, "translate"), "get",
				"update", null),
			new FDSActionDropdownItem(
				"{actions.expire.href}", "time", "expire",
				LanguageUtil.get(httpServletRequest, "expire"), "post",
				"expire", "headless"),
			new FDSActionDropdownItem(
				StringBundler.concat(
					themeDisplay.getPortalURL(), themeDisplay.getPathMain(),
					GroupConstants.CMS_FRIENDLY_URL,
					"/edit_content_item?&p_l_mode=read&p_p_state=",
					LiferayWindowState.POP_UP, "&redirect=",
					themeDisplay.getURLCurrent(),
					"&objectEntryId={embedded.id}"),
				"view", "view-content",
				LanguageUtil.get(httpServletRequest, "view"), null, "get",
				null),
			new FDSActionDropdownItem(
				StringPool.BLANK, "view", "view-file",
				LanguageUtil.get(httpServletRequest, "view"), null, "get",
				null),
			new FDSActionDropdownItem(
				StringBundler.concat(
					themeDisplay.getPathFriendlyURLPublic(),
					GroupConstants.CMS_FRIENDLY_URL,
					"/version-history?objectEntryId={embedded.id}&backURL=",
					themeDisplay.getURLCurrent()),
				"date-time", "version-history",
				LanguageUtil.get(httpServletRequest, "view-history"), "get",
				"versions", null),
			new FDSActionDropdownItem(
				PortletURLBuilder.create(
					portal.getControlPanelPortletURL(
						httpServletRequest, TranslationPortletKeys.TRANSLATION,
						ActionRequest.RENDER_PHASE)
				).setMVCRenderCommandName(
					"/translation/export_translation"
				).setParameter(
					"className", "{entryClassName}"
				).setParameter(
					"classPK", "{embedded.id}"
				).setParameter(
					"groupId", "{embedded.scopeId}"
				).setWindowState(
					LiferayWindowState.POP_UP
				).buildString(),
				"upload", "export-for-translation",
				LanguageUtil.get(httpServletRequest, "export-for-translation"),
				null, "get", null),
			new FDSActionDropdownItem(
				PortletURLBuilder.create(
					portal.getControlPanelPortletURL(
						httpServletRequest, TranslationPortletKeys.TRANSLATION,
						ActionRequest.RENDER_PHASE)
				).setMVCRenderCommandName(
					"/translation/import_translation"
				).setParameter(
					"className", "{entryClassName}"
				).setParameter(
					"classPK", "{embedded.id}"
				).setParameter(
					"groupId", "{embedded.scopeId}"
				).setWindowState(
					LiferayWindowState.POP_UP
				).buildString(),
				"download", "import-translation",
				LanguageUtil.get(httpServletRequest, "import-translation"),
				null, "update", null),
			new FDSActionDropdownItem(
				null, "copy", "copy",
				language.get(httpServletRequest, "copy-to"), null, null, null),
			new FDSActionDropdownItem(
				null, "move", "move", language.get(httpServletRequest, "move"),
				null, null, null),
			new FDSActionDropdownItem(
				PortletURLBuilder.create(
					portal.getControlPanelPortletURL(
						httpServletRequest,
						"com_liferay_portlet_configuration_web_portlet_" +
							"PortletConfigurationPortlet",
						ActionRequest.RENDER_PHASE)
				).setMVCPath(
					"/edit_permissions.jsp"
				).setRedirect(
					themeDisplay.getURLCurrent()
				).setParameter(
					"modelResource", "{entryClassName}"
				).setParameter(
					"modelResourceDescription", "{embedded.name}"
				).setParameter(
					"resourceGroupId", "{embedded.scopeId}"
				).setParameter(
					"resourcePrimKey", "{embedded.id}"
				).setWindowState(
					LiferayWindowState.POP_UP
				).buildString(),
				"password-policies", "permissions",
				language.get(httpServletRequest, "permissions"), "get",
				"permissions", "modal-permissions"),
			new FDSActionDropdownItem(
				StringPool.BLANK, "password-policies", "default-permissions",
				LanguageUtil.get(httpServletRequest, "default-permissions"),
				null, "permissions", null),
			new FDSActionDropdownItem(
				StringPool.BLANK, "password-policies",
				"edit-and-propagate-default-permissions",
				LanguageUtil.get(
					httpServletRequest,
					"edit-and-propagate-default-permissions"),
				null, "permissions", null),
			new FDSActionDropdownItem(
				StringPool.BLANK, "password-policies",
				"reset-to-default-permissions",
				LanguageUtil.get(
					httpServletRequest, "reset-to-default-permissions"),
				null, "permissions", null),
			new FDSActionDropdownItem(
				null, "trash", "delete",
				language.get(httpServletRequest, "delete"), null, "delete",
				null));
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

	protected String appendStatus(String filterString) {
		return _baseSectionDisplayContextHelper.appendStatus(filterString);
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

	private final BaseSectionDisplayContextHelper
		_baseSectionDisplayContextHelper;
	private final DLConfiguration _dlConfiguration;
	private final ObjectDefinitionService _objectDefinitionService;

}