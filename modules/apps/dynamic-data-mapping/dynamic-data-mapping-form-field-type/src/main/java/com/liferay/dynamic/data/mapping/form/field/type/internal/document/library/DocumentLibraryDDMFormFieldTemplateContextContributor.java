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

package com.liferay.dynamic.data.mapping.form.field.type.internal.document.library;

import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.dynamic.data.mapping.constants.DDMFormConstants;
import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.form.item.selector.criterion.DDMUserPersonalFolderItemSelectorCriterion;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.file.criterion.FileItemSelectorCriterion;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pedro Queiroz
 */
@Component(
	property = "ddm.form.field.type.name=" + DDMFormFieldTypeConstants.DOCUMENT_LIBRARY,
	service = {
		DDMFormFieldTemplateContextContributor.class,
		DocumentLibraryDDMFormFieldTemplateContextContributor.class
	}
)
public class DocumentLibraryDDMFormFieldTemplateContextContributor
	implements DDMFormFieldTemplateContextContributor {

	@Override
	public Map<String, Object> getParameters(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		return HashMapBuilder.<String, Object>put(
			"allowGuestUsers",
			GetterUtil.getBoolean(ddmFormField.getProperty("allowGuestUsers"))
		).put(
			"groupId", ddmFormFieldRenderingContext.getProperty("groupId")
		).put(
			"maximumRepetitions",
			GetterUtil.getInteger(
				ddmFormField.getProperty("maximumRepetitions"))
		).put(
			"maximumSubmissionLimitReached",
			GetterUtil.getBoolean(
				ddmFormField.getProperty("maximumSubmissionLimitReached"))
		).put(
			"message",
			_getMessage(
				ddmFormFieldRenderingContext.getLocale(),
				ddmFormFieldRenderingContext.getValue())
		).put(
			"objectFieldAcceptedFileExtensions",
			GetterUtil.getString(
				ddmFormField.getProperty("objectFieldAcceptedFileExtensions"))
		).put(
			"value",
			() -> {
				String value = ddmFormFieldRenderingContext.getValue();

				if (Validator.isNull(value)) {
					return "{}";
				}

				return value;
			}
		).putAll(
			_getFileEntryParameters(
				ddmFormFieldRenderingContext.getHttpServletRequest(),
				ddmFormFieldRenderingContext.getValue())
		).putAll(
			_getUploadParameters(ddmFormField, ddmFormFieldRenderingContext)
		).build();
	}

	protected ResourceBundle getResourceBundle(Locale locale) {
		return new AggregateResourceBundle(
			ResourceBundleUtil.getBundle(
				"content.Language", locale, getClass()),
			_portal.getResourceBundle(locale));
	}

	protected ThemeDisplay getThemeDisplay(
		HttpServletRequest httpServletRequest) {

		return (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	private boolean _containsAddFolderPermission(
		PermissionChecker permissionChecker, long groupId, long folderId) {

		try {
			return ModelResourcePermissionUtil.contains(
				_dlFolderModelResourcePermission, permissionChecker, groupId,
				folderId, ActionKeys.ADD_FOLDER);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return false;
		}
	}

	private User _createDDMFormDefaultUser(long companyId) {
		try {
			long creatorUserId = 0;
			boolean autoPassword = true;
			String password1 = StringPool.BLANK;
			String password2 = StringPool.BLANK;
			boolean autoScreenName = false;
			String screenName =
				DDMFormConstants.DDM_FORM_DEFAULT_USER_SCREEN_NAME;
			String emailAddress = _getEmailAddress(companyId);
			Locale locale = LocaleUtil.getDefault();
			String firstName =
				DDMFormConstants.DDM_FORM_DEFAULT_USER_FIRST_NAME;
			String middleName = StringPool.BLANK;
			String lastName = DDMFormConstants.DDM_FORM_DEFAULT_USER_LAST_NAME;
			long prefixId = 0;
			long suffixId = 0;
			boolean male = true;
			int birthdayMonth = Calendar.JANUARY;
			int birthdayDay = 1;
			int birthdayYear = 1970;
			String jobTitle = StringPool.BLANK;
			long[] groupIds = null;
			long[] organizationIds = null;
			long[] roleIds = null;
			long[] userGroupIds = null;
			boolean sendEmail = false;
			ServiceContext serviceContext = null;

			User user = _userLocalService.addUser(
				creatorUserId, companyId, autoPassword, password1, password2,
				autoScreenName, screenName, emailAddress, locale, firstName,
				middleName, lastName, prefixId, suffixId, male, birthdayMonth,
				birthdayDay, birthdayYear, jobTitle, groupIds, organizationIds,
				roleIds, userGroupIds, sendEmail, serviceContext);

			_userLocalService.updateStatus(
				user.getUserId(), WorkflowConstants.STATUS_INACTIVE,
				new ServiceContext());

			return user;
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return null;
		}
	}

	private Folder _createDDMFormFolder(
		long userId, long repositoryId, HttpServletRequest httpServletRequest) {

		try {
			return _portletFileRepository.addPortletFolder(
				userId, repositoryId,
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				DDMFormConstants.DDM_FORM_UPLOADED_FILES_FOLDER_NAME,
				_getServiceContext(httpServletRequest));
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return null;
		}
	}

	private Folder _createPrivateUserFolder(
		long repositoryId, long parentFolderId,
		HttpServletRequest httpServletRequest, User user) {

		try {
			return _dlAppService.addFolder(
				repositoryId, parentFolderId, user.getScreenName(),
				_language.get(
					getResourceBundle(user.getLocale()),
					"this-folder-was-automatically-created-by-forms-to-store-" +
						"all-your-uploaded-files"),
				ServiceContextFactory.getInstance(httpServletRequest));
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to retrieve private uploads folder of user " +
						user.getUserId(),
					portalException);
			}

			return null;
		}
	}

	private User _getDDMFormDefaultUser(long companyId) {
		try {
			return _userLocalService.getUserByEmailAddress(
				companyId, _getEmailAddress(companyId));
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return _createDDMFormDefaultUser(companyId);
		}
	}

	private long _getDDMFormFolderId(
		long companyId, long repositoryId,
		HttpServletRequest httpServletRequest) {

		Folder folder = null;

		try {
			folder = _portletFileRepository.getPortletFolder(
				repositoryId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				DDMFormConstants.DDM_FORM_UPLOADED_FILES_FOLDER_NAME);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			User user = _getDDMFormDefaultUser(companyId);

			folder = _createDDMFormFolder(
				user.getUserId(), repositoryId, httpServletRequest);
		}

		if (folder == null) {
			return DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		}

		return folder.getFolderId();
	}

	private String _getEmailAddress(long companyId) {
		try {
			Company company = _companyLocalService.getCompany(companyId);

			return StringBundler.concat(
				DDMFormConstants.DDM_FORM_DEFAULT_USER_SCREEN_NAME,
				StringPool.AT, company.getMx());
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return null;
		}
	}

	private FileEntry _getFileEntry(JSONObject valueJSONObject) {
		try {
			return _dlAppService.getFileEntryByUuidAndGroupId(
				valueJSONObject.getString("uuid"),
				valueJSONObject.getLong("groupId"));
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to retrieve file entry ", portalException);
			}

			return null;
		}
	}

	private Map<String, Object> _getFileEntryParameters(
		HttpServletRequest httpServletRequest, String value) {

		if (Validator.isNull(value)) {
			return new HashMap<>();
		}

		JSONObject valueJSONObject = _getValueJSONObject(value);

		if ((valueJSONObject == null) || (valueJSONObject.length() <= 0)) {
			return new HashMap<>();
		}

		FileEntry fileEntry = _getFileEntry(valueJSONObject);

		return HashMapBuilder.<String, Object>put(
			"fileEntryTitle", _getFileEntryTitle(fileEntry)
		).put(
			"fileEntryURL", _getFileEntryURL(httpServletRequest, fileEntry)
		).build();
	}

	private String _getFileEntryTitle(FileEntry fileEntry) {
		if (fileEntry == null) {
			return StringPool.BLANK;
		}

		return _html.escape(fileEntry.getTitle());
	}

	private String _getFileEntryURL(
		HttpServletRequest httpServletRequest, FileEntry fileEntry) {

		if (fileEntry == null) {
			return StringPool.BLANK;
		}

		ThemeDisplay themeDisplay = getThemeDisplay(httpServletRequest);

		if (themeDisplay == null) {
			return StringPool.BLANK;
		}

		return _html.escape(
			StringBundler.concat(
				themeDisplay.getPathContext(), "/documents/",
				fileEntry.getRepositoryId(), StringPool.SLASH,
				fileEntry.getFolderId(), StringPool.SLASH,
				URLCodec.encodeURL(_html.unescape(fileEntry.getTitle()), true),
				StringPool.SLASH, fileEntry.getUuid()));
	}

	private String _getGuestUploadURL(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext,
		long folderId, HttpServletRequest httpServletRequest) {

		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest);

		return PortletURLBuilder.create(
			requestBackedPortletURLFactory.createActionURL(
				GetterUtil.getString(
					_portal.getPortletId(httpServletRequest),
					DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM))
		).setActionName(
			"/dynamic_data_mapping_form/upload_file_entry"
		).setParameter(
			"folderId", folderId
		).setParameter(
			"formInstanceId",
			ParamUtil.getString(
				httpServletRequest, "formInstanceId",
				String.valueOf(
					ddmFormFieldRenderingContext.getDDMFormInstanceId()))
		).setParameter(
			"groupId", ddmFormFieldRenderingContext.getProperty("groupId")
		).setParameter(
			"objectFieldId",
			GetterUtil.getLong(ddmFormField.getProperty("objectFieldId"))
		).buildString();
	}

	private String _getItemSelectorURL(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext,
		long folderId, long repositoryId, ThemeDisplay themeDisplay) {

		if (_itemSelector == null) {
			return StringPool.BLANK;
		}

		long groupId = GetterUtil.getLong(
			ddmFormFieldRenderingContext.getProperty("groupId"));

		Group group = _groupLocalService.fetchGroup(groupId);

		if (group == null) {
			group = themeDisplay.getScopeGroup();
		}

		List<ItemSelectorCriterion> itemSelectorCriteria = new ArrayList<>();

		DDMUserPersonalFolderItemSelectorCriterion
			ddmUserPersonalFolderItemSelectorCriterion =
				new DDMUserPersonalFolderItemSelectorCriterion(
					folderId, groupId);

		ddmUserPersonalFolderItemSelectorCriterion.
			setDesiredItemSelectorReturnTypes(
				new FileEntryItemSelectorReturnType());
		ddmUserPersonalFolderItemSelectorCriterion.setObjectFieldId(
			GetterUtil.getLong(ddmFormField.getProperty("objectFieldId")));
		ddmUserPersonalFolderItemSelectorCriterion.setRepositoryId(
			repositoryId);

		itemSelectorCriteria.add(ddmUserPersonalFolderItemSelectorCriterion);

		String portletNamespace =
			ddmFormFieldRenderingContext.getPortletNamespace();

		if (!StringUtil.startsWith(
				portletNamespace,
				_portal.getPortletNamespace(
					DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM))) {

			FileItemSelectorCriterion fileItemSelectorCriterion =
				new FileItemSelectorCriterion();

			fileItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
				new FileEntryItemSelectorReturnType());

			itemSelectorCriteria.add(fileItemSelectorCriterion);
		}

		return String.valueOf(
			_itemSelector.getItemSelectorURL(
				RequestBackedPortletURLFactoryUtil.create(
					ddmFormFieldRenderingContext.getHttpServletRequest()),
				group, groupId, portletNamespace + "selectDocumentLibrary",
				itemSelectorCriteria.toArray(new ItemSelectorCriterion[0])));
	}

	private String _getMessage(Locale defaultLocale, String value) {
		if (Validator.isNull(value)) {
			return StringPool.BLANK;
		}

		JSONObject valueJSONObject = _getValueJSONObject(value);

		if ((valueJSONObject == null) || (valueJSONObject.length() <= 0)) {
			return StringPool.BLANK;
		}

		FileEntry fileEntry = _getFileEntry(valueJSONObject);

		if (fileEntry == null) {
			return _language.get(
				getResourceBundle(defaultLocale),
				"the-selected-document-was-deleted");
		}

		if (fileEntry.isInTrash()) {
			return _language.get(
				getResourceBundle(defaultLocale),
				"the-selected-document-was-moved-to-the-recycle-bin");
		}

		return StringPool.BLANK;
	}

	private long _getPrivateUserFolderId(
		long repositoryId, long parentFolderId,
		HttpServletRequest httpServletRequest, User user) {

		Folder folder = null;

		try {
			folder = _dlAppService.getFolder(
				repositoryId, parentFolderId, user.getScreenName());
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"The user " + user.getUserId() +
						" does not have a private uploads folder",
					portalException);
			}

			folder = _createPrivateUserFolder(
				repositoryId, parentFolderId, httpServletRequest, user);
		}

		if (folder == null) {
			return DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		}

		return folder.getFolderId();
	}

	private Repository _getRepository(
		long groupId, HttpServletRequest httpServletRequest) {

		try {
			Repository repository =
				_portletFileRepository.fetchPortletRepository(
					groupId, DDMFormConstants.SERVICE_NAME);

			if (repository != null) {
				return repository;
			}

			return _portletFileRepository.addPortletRepository(
				groupId, DDMFormConstants.SERVICE_NAME,
				_getServiceContext(httpServletRequest));
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return null;
		}
	}

	private ServiceContext _getServiceContext(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			httpServletRequest);

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		return serviceContext;
	}

	private Map<String, Object> _getUploadParameters(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		if (ddmFormFieldRenderingContext.isReadOnly()) {
			return new HashMap<>();
		}

		HttpServletRequest httpServletRequest =
			ddmFormFieldRenderingContext.getHttpServletRequest();

		ThemeDisplay themeDisplay = getThemeDisplay(httpServletRequest);

		if ((themeDisplay == null) ||
			(!themeDisplay.isSignedIn() &&
			 !GetterUtil.getBoolean(
				 ddmFormField.getProperty("allowGuestUsers")))) {

			return new HashMap<>();
		}

		long groupId = GetterUtil.getLong(
			ddmFormFieldRenderingContext.getProperty("groupId"));

		Repository repository = _getRepository(groupId, httpServletRequest);

		if (repository == null) {
			return new HashMap<>();
		}

		long ddmFormFolderId = _getDDMFormFolderId(
			themeDisplay.getCompanyId(), repository.getRepositoryId(),
			httpServletRequest);

		if (!themeDisplay.isSignedIn()) {
			return HashMapBuilder.<String, Object>put(
				"folderId", ddmFormFolderId
			).put(
				"guestUploadURL",
				() -> {
					String guestUploadURL = GetterUtil.getString(
						ddmFormField.getProperty("guestUploadURL"));

					if (Validator.isNotNull(guestUploadURL)) {
						return guestUploadURL;
					}

					return _getGuestUploadURL(
						ddmFormField, ddmFormFieldRenderingContext,
						ddmFormFolderId, httpServletRequest);
				}
			).build();
		}

		if (!_containsAddFolderPermission(
				themeDisplay.getPermissionChecker(), groupId,
				ddmFormFolderId)) {

			return HashMapBuilder.<String, Object>put(
				"showUploadPermissionMessage", true
			).build();
		}

		long privateUserFolderId = _getPrivateUserFolderId(
			repository.getRepositoryId(), ddmFormFolderId, httpServletRequest,
			themeDisplay.getUser());

		return HashMapBuilder.<String, Object>put(
			"folderId", privateUserFolderId
		).put(
			"itemSelectorURL",
			() -> {
				String itemSelectorURL = GetterUtil.getString(
					ddmFormField.getProperty("itemSelectorURL"));

				if (Validator.isNotNull(itemSelectorURL)) {
					return itemSelectorURL;
				}

				return _getItemSelectorURL(
					ddmFormField, ddmFormFieldRenderingContext,
					privateUserFolderId, repository.getRepositoryId(),
					themeDisplay);
			}
		).build();
	}

	private JSONObject _getValueJSONObject(String value) {
		try {
			return _jsonFactory.createJSONObject(value);
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DocumentLibraryDDMFormFieldTemplateContextContributor.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private DLAppService _dlAppService;

	@Reference(
		target = "(model.class.name=com.liferay.document.library.kernel.model.DLFolder)"
	)
	private ModelResourcePermission<DLFolder> _dlFolderModelResourcePermission;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Html _html;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private PortletFileRepository _portletFileRepository;

	@Reference
	private UserLocalService _userLocalService;

}