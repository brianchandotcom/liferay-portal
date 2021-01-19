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
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.file.criterion.FileItemSelectorCriterion;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pedro Queiroz
 */
@Component(
	immediate = true,
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

		Map<String, Object> parameters = new HashMap<>();

		parameters.put(
			"allowGuestUsers",
			GetterUtil.getBoolean(ddmFormField.getProperty("allowGuestUsers")));

		HttpServletRequest httpServletRequest =
			ddmFormFieldRenderingContext.getHttpServletRequest();

		if (Validator.isNotNull(ddmFormFieldRenderingContext.getValue())) {
			JSONObject valueJSONObject = getValueJSONObject(
				ddmFormFieldRenderingContext.getValue());

			if ((valueJSONObject != null) && (valueJSONObject.length() > 0)) {
				FileEntry fileEntry = getFileEntry(valueJSONObject);

				parameters.put("fileEntryTitle", getFileEntryTitle(fileEntry));
				parameters.put(
					"fileEntryURL",
					getFileEntryURL(httpServletRequest, fileEntry));
			}
		}

		long folderId = 0;

		if (!ddmFormFieldRenderingContext.isReadOnly()) {
			folderId = _getFolderId(
				GetterUtil.getLong(
					ddmFormFieldRenderingContext.getProperty("groupId")),
				httpServletRequest);
		}

		parameters.put("folderId", folderId);

		parameters.put(
			"groupId", ddmFormFieldRenderingContext.getProperty("groupId"));

		ThemeDisplay themeDisplay = getThemeDisplay(httpServletRequest);

		if ((themeDisplay == null) || themeDisplay.isSignedIn()) {
			parameters.put(
				"itemSelectorURL",
				getItemSelectorURL(
					ddmFormFieldRenderingContext, folderId,
					httpServletRequest));
		}
		else {
			String guestUploadURL = GetterUtil.getString(
				ddmFormField.getProperty("guestUploadURL"));

			if (Validator.isNull(guestUploadURL)) {
				guestUploadURL = getGuestUploadURL(
					ddmFormFieldRenderingContext, folderId, httpServletRequest);
			}

			parameters.put("guestUploadURL", guestUploadURL);
		}

		parameters.put(
			"maximumRepetitions",
			GetterUtil.getInteger(
				ddmFormField.getProperty("maximumRepetitions")));
		parameters.put(
			"maximumSubmissionLimitReached",
			GetterUtil.getBoolean(
				ddmFormField.getProperty("maximumSubmissionLimitReached")));

		String value = ddmFormFieldRenderingContext.getValue();

		if (Validator.isNull(value)) {
			value = "{}";
		}

		parameters.put("value", value);

		return parameters;
	}

	protected FileEntry getFileEntry(JSONObject valueJSONObject) {
		try {
			return dlAppService.getFileEntryByUuidAndGroupId(
				valueJSONObject.getString("uuid"),
				valueJSONObject.getLong("groupId"));
		}
		catch (PortalException portalException) {
			_log.error("Unable to retrieve file entry ", portalException);

			return null;
		}
	}

	protected String getFileEntryTitle(FileEntry fileEntry) {
		if (fileEntry == null) {
			return StringPool.BLANK;
		}

		return html.escape(fileEntry.getTitle());
	}

	protected String getFileEntryURL(
		HttpServletRequest httpServletRequest, FileEntry fileEntry) {

		if (fileEntry == null) {
			return StringPool.BLANK;
		}

		ThemeDisplay themeDisplay = getThemeDisplay(httpServletRequest);

		if (themeDisplay == null) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(9);

		sb.append(themeDisplay.getPathContext());

		sb.append("/documents/");
		sb.append(fileEntry.getRepositoryId());
		sb.append(StringPool.SLASH);
		sb.append(fileEntry.getFolderId());
		sb.append(StringPool.SLASH);
		sb.append(
			URLCodec.encodeURL(html.unescape(fileEntry.getTitle()), true));
		sb.append(StringPool.SLASH);
		sb.append(fileEntry.getUuid());

		return html.escape(sb.toString());
	}

	protected String getGuestUploadURL(
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext,
		long folderId, HttpServletRequest httpServletRequest) {

		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest);

		PortletURL portletURL = requestBackedPortletURLFactory.createActionURL(
			DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM);

		portletURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/dynamic_data_mapping_form/upload_file_entry");
		portletURL.setParameter(
			"formInstanceId",
			ParamUtil.getString(httpServletRequest, "formInstanceId"));
		portletURL.setParameter(
			"groupId",
			String.valueOf(
				ddmFormFieldRenderingContext.getProperty("groupId")));
		portletURL.setParameter("folderId", String.valueOf(folderId));

		return portletURL.toString();
	}

	protected String getItemSelectorURL(
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext,
		long folderId, HttpServletRequest httpServletRequest) {

		if (_itemSelector == null) {
			return StringPool.BLANK;
		}

		long groupId = GetterUtil.getLong(
			ddmFormFieldRenderingContext.getProperty("groupId"));

		Group group = _groupLocalService.fetchGroup(groupId);

		if (group == null) {
			ThemeDisplay themeDisplay = getThemeDisplay(httpServletRequest);

			if (themeDisplay != null) {
				group = themeDisplay.getScopeGroup();
			}
		}

		DDMUserPersonalFolderItemSelectorCriterion
			ddmUserPersonalFolderItemSelectorCriterion =
				new DDMUserPersonalFolderItemSelectorCriterion(
					folderId, groupId);

		ddmUserPersonalFolderItemSelectorCriterion.
			setDesiredItemSelectorReturnTypes(
				new FileEntryItemSelectorReturnType());

		FileItemSelectorCriterion fileItemSelectorCriterion =
			new FileItemSelectorCriterion();

		fileItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new FileEntryItemSelectorReturnType());

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest),
			group, groupId,
			ddmFormFieldRenderingContext.getPortletNamespace() +
				"selectDocumentLibrary",
			ddmUserPersonalFolderItemSelectorCriterion,
			fileItemSelectorCriterion);

		return itemSelectorURL.toString();
	}

	protected ResourceBundle getResourceBundle(Locale locale) {
		ResourceBundle portalResourceBundle = portal.getResourceBundle(locale);

		ResourceBundle moduleResourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return new AggregateResourceBundle(
			moduleResourceBundle, portalResourceBundle);
	}

	protected ThemeDisplay getThemeDisplay(
		HttpServletRequest httpServletRequest) {

		return (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	protected JSONObject getValueJSONObject(String value) {
		try {
			return jsonFactory.createJSONObject(value);
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException, jsonException);
			}

			return null;
		}
	}

	@Reference
	protected DLAppService dlAppService;

	@Reference
	protected Html html;

	@Reference
	protected JSONFactory jsonFactory;

	@Reference
	protected Portal portal;

	private User _createDDMFormDefaultUser(long companyId) {
		try {
			long creatorUserId = 0;
			boolean autoPassword = true;
			String password1 = StringPool.BLANK;
			String password2 = StringPool.BLANK;
			boolean autoScreenName = false;
			String screenName =
				DDMFormConstants.DDM_FORM_DEFAULT_USER_SCREEN_NAME;

			Company company = _companyLocalService.getCompany(companyId);

			String emailAddress = StringBundler.concat(
				screenName, StringPool.AT, company.getMx());

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
				_log.debug(portalException, portalException);
			}

			return null;
		}
	}

	private Folder _createDDMFormUploadedFilesFolder(
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
				_log.debug(portalException, portalException);
			}

			return null;
		}
	}

	private Folder _createPrivateUserFolder(
		long repositoryId, long parentFolderId,
		HttpServletRequest httpServletRequest, User user) {

		try {
			return dlAppService.addFolder(
				repositoryId, parentFolderId, user.getScreenName(),
				LanguageUtil.get(
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
			return _userLocalService.getUserByScreenName(
				companyId, DDMFormConstants.DDM_FORM_DEFAULT_USER_SCREEN_NAME);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}

			return _createDDMFormDefaultUser(companyId);
		}
	}

	private Folder _getDDMFormUploadedFilesFolder(
		long userId, long repositoryId, HttpServletRequest httpServletRequest) {

		try {
			return _portletFileRepository.getPortletFolder(
				repositoryId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				DDMFormConstants.DDM_FORM_UPLOADED_FILES_FOLDER_NAME);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}

			return _createDDMFormUploadedFilesFolder(
				userId, repositoryId, httpServletRequest);
		}
	}

	private long _getFolderId(
		long groupId, HttpServletRequest httpServletRequest) {

		try {
			ThemeDisplay themeDisplay = getThemeDisplay(httpServletRequest);

			if (themeDisplay == null) {
				return DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
			}

			long repositoryId = _getRepositoryId(groupId, httpServletRequest);

			User user = _getDDMFormDefaultUser(themeDisplay.getCompanyId());

			Folder folder = _getDDMFormUploadedFilesFolder(
				user.getUserId(), repositoryId, httpServletRequest);

			if (themeDisplay.isSignedIn()) {
				folder = _getPrivateUserFolder(
					repositoryId, folder.getFolderId(), httpServletRequest,
					themeDisplay.getUser());
			}

			return folder.getFolderId();
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}
		}

		return DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	}

	private Folder _getPrivateUserFolder(
		long repositoryId, long parentFolderId,
		HttpServletRequest httpServletRequest, User user) {

		try {
			return dlAppService.getFolder(
				repositoryId, parentFolderId, user.getScreenName());
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"The user " + user.getUserId() +
						" does not have a private uploads folder",
					portalException);
			}

			return _createPrivateUserFolder(
				repositoryId, parentFolderId, httpServletRequest, user);
		}
	}

	private long _getRepositoryId(
			long groupId, HttpServletRequest httpServletRequest)
		throws PortalException {

		Repository repository = _portletFileRepository.fetchPortletRepository(
			groupId, DDMFormConstants.SERVICE_NAME);

		if (repository == null) {
			repository = _portletFileRepository.addPortletRepository(
				groupId, DDMFormConstants.SERVICE_NAME,
				_getServiceContext(httpServletRequest));
		}

		return repository.getRepositoryId();
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

	private static final Log _log = LogFactoryUtil.getLog(
		DocumentLibraryDDMFormFieldTemplateContextContributor.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private PortletFileRepository _portletFileRepository;

	@Reference
	private UserLocalService _userLocalService;

}