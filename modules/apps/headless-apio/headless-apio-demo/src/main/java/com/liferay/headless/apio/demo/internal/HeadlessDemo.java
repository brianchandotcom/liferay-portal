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

package com.liferay.headless.apio.demo.internal;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerTracker;
import com.liferay.dynamic.data.mapping.kernel.DDMTemplate;
import com.liferay.dynamic.data.mapping.kernel.DDMTemplateManager;
import com.liferay.dynamic.data.mapping.kernel.DDMTemplateManagerUtil;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.util.DDMUtil;
import com.liferay.journal.constants.JournalContentPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.InputStream;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class HeadlessDemo extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) {
		try {
			Role role = _roleLocalService.getRole(
				company.getCompanyId(), RoleConstants.ADMINISTRATOR);

			List<User> users = _userLocalService.getRoleUsers(
				role.getRoleId(), 0, 1);

			User user = users.get(0);

			_group = _createDemoGroup(company, user);

			List<JournalArticle> journalArticles = _createJournalArticles(
				company, _group, user);

			Folder folder = _createFolder(
				user, _group, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				"Main Folder");

			_createFolder(user, _group, folder.getFolderId(), "SubFolder");

			FileEntry fileEntry = _createFileEntry(user, _group, folder);

			_createMainPage(
				company, _group, user, journalArticles, folder, fileEntry);
		}
		catch (Exception e) {
			_log.error("Error initializing data ", e);
		}
	}

	@Deactivate
	protected void deactivate() throws PortalException {
		if (_group != null) {
			_groupLocalService.deleteGroup(_group);
		}
	}

	private JournalArticle _addJournalArticle(
			Company company, Group group, User user, DDMStructure ddmStructure,
			DDMTemplate ddmTemplate, Map<Locale, String> titleMap,
			String fileName, String[] tags)
		throws Exception {

		JournalArticle journalArticle = _journalArticleLocalService.addArticle(
			user.getUserId(), _group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, titleMap, null,
			_read("journal-article/" + fileName),
			ddmStructure.getStructureKey(), ddmTemplate.getTemplateKey(),
			_getServiceContext(company, group, user));

		_journalArticleLocalService.updateAsset(
			user.getUserId(), journalArticle, null, tags, null, null);

		return journalArticle;
	}

	private FileEntry _addPostmanVarFile(
			Group group, User user, DDMStructure ddmStructure,
			JournalArticle journalArticle, Folder folder, FileEntry fileEntry)
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(group.getGroupId());

		String postmanVars = _read(
			"file-entry/demo-variables.json",
			Arrays.asList(
				String.valueOf(group.getGroupId()),
				String.valueOf(fileEntry.getFileEntryId()),
				String.valueOf(folder.getFolderId()),
				String.valueOf(ddmStructure.getStructureId()),
				String.valueOf(journalArticle.getResourcePrimKey()),
				String.valueOf(user.getUserId())));

		return _dlAppLocalService.addFileEntry(
			user.getUserId(), group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Demo.postman_environment.json", ContentTypes.APPLICATION_JSON,
			postmanVars.getBytes(), serviceContext);
	}

	private Group _createDemoGroup(Company company, User user)
		throws Exception {

		return _groupLocalService.addGroup(
			user.getUserId(), GroupConstants.DEFAULT_PARENT_GROUP_ID, null, 0,
			GroupConstants.DEFAULT_LIVE_GROUP_ID,
			Collections.singletonMap(LocaleUtil.US, _SITE_NAME),
			Collections.singletonMap(LocaleUtil.US, _SITE_NAME),
			GroupConstants.TYPE_SITE_OPEN, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION,
			StringPool.SLASH + FriendlyURLNormalizerUtil.normalize(_SITE_NAME),
			true, true,
			_getServiceContext(
				company,
				_groupLocalService.getGroup(
					company.getCompanyId(), GroupConstants.GUEST),
				user));
	}

	private FileEntry _createFileEntry(User user, Group group, Folder folder)
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		return _dlAppLocalService.addFileEntry(
			user.getUserId(), group.getGroupId(), folder.getFolderId(), "Logo",
			ContentTypes.IMAGE_PNG,
			FileUtil.getBytes(
				HeadlessDemo.class, _PATH + "layout-set/logo.png"),
			serviceContext);
	}

	private Folder _createFolder(
			User user, Group group, long parentFolderId, String name)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		return _dlAppLocalService.addFolder(
			user.getUserId(), group.getGroupId(), parentFolderId, name, "",
			serviceContext);
	}

	private List<JournalArticle> _createJournalArticles(
			Company company, Group group, User user)
		throws Exception {

		DDMStructure ddmStructure = _getDDMStructure(
			company, group, user,
			"ddm-structure/demo-football-match-result-local-vs-visitor.json");

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		DDMTemplate ddmTemplate = DDMTemplateManagerUtil.addTemplate(
			user.getUserId(), group.getGroupId(),
			_portal.getClassNameId(DDMStructure.class),
			ddmStructure.getStructureId(),
			_portal.getClassNameId(JournalArticle.class), null,
			Collections.singletonMap(LocaleUtil.US, "Template Demo Example"),
			null, DDMTemplateManager.TEMPLATE_TYPE_DISPLAY, null,
			TemplateConstants.LANG_TYPE_VM, "$name.getData()", false, false,
			null, null, serviceContext);

		JournalArticle journalArticle1 = _addJournalArticle(
			company, group, user, ddmStructure, ddmTemplate,
			new HashMap<Locale, String>() {
				{
					put(LocaleUtil.US, "Southampton vs Man. City");
					put(LocaleUtil.SPAIN, "Southampton contra Man. City");
				}
			},
			"demo-football-match-result-southampton-vs-manchester.xml",
			new String[] {"premier"});

		JournalArticle journalArticle2 = _addJournalArticle(
			company, group, user, ddmStructure, ddmTemplate,
			new HashMap<Locale, String>() {
				{
					put(LocaleUtil.US, "Man. City vs Crystal Palace");
					put(LocaleUtil.SPAIN, "Man. City contra Crystal Palace");
				}
			},
			"demo-football-match-result-manchester-vs-crystal.xml",
			new String[] {"premier"});

		JournalArticle journalArticle3 = _addJournalArticle(
			company, group, user, ddmStructure, ddmTemplate,
			new HashMap<Locale, String>() {
				{
					put(LocaleUtil.US, "Leicester City vs Man. City");
					put(LocaleUtil.SPAIN, "Leicester City contra Man. City");
				}
			},
			"demo-football-match-result-leicester-vs-manchester.xml",
			new String[] {"premier"});

		JournalArticle journalArticle4 = _addJournalArticle(
			company, group, user, ddmStructure, ddmTemplate,
			new HashMap<Locale, String>() {
				{
					put(LocaleUtil.US, "Man. City vs Hoffenhein");
					put(LocaleUtil.SPAIN, "Man. City contra Hoffenhein");
				}
			},
			"demo-football-match-result-manchester-vs-hoffenhein.xml",
			new String[] {"champions"});

		return Arrays.asList(
			journalArticle1, journalArticle2, journalArticle3, journalArticle4);
	}

	private void _createMainPage(
			Company company, Group group, User user,
			List<JournalArticle> journalArticles, Folder folder,
			FileEntry fileEntry)
		throws Exception {

		_layoutSetLocalService.updateLogo(
			group.getGroupId(), false, true,
			FileUtil.getBytes(
				HeadlessDemo.class, _PATH + "layout-set/logo.png"));

		DDMStructure mainPageDDMStructure = _getDDMStructure(
			company, group, user, "ddm-structure/demo-variables.json");

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		DDMTemplate mainPageDDMTemplate = DDMTemplateManagerUtil.addTemplate(
			user.getUserId(), group.getGroupId(),
			_portal.getClassNameId(DDMStructure.class),
			mainPageDDMStructure.getStructureId(),
			_portal.getClassNameId(JournalArticle.class), null,
			Collections.singletonMap(
				LocaleUtil.US, "Template Main Page Example"),
			null, DDMTemplateManager.TEMPLATE_TYPE_DISPLAY, null,
			TemplateConstants.LANG_TYPE_VM,
			_read("ddm-template/demo-variables.ftl"), false, false, null, null,
			serviceContext);

		JournalArticle journalArticle = journalArticles.get(0);

		String friendlyURL =
			StringPool.SLASH + FriendlyURLNormalizerUtil.normalize("Main Page");

		Layout layout = _layoutLocalService.addLayout(
			user.getUserId(), group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, "Main Page", null,
			"Main Page", LayoutConstants.TYPE_PORTLET, false, friendlyURL,
			_getServiceContext(
				company,
				_groupLocalService.getGroup(
					company.getCompanyId(), GroupConstants.GUEST),
				user));

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		layoutTypePortlet.setLayoutTemplateId(user.getUserId(), "1_column");

		String portletIdAdded = layoutTypePortlet.addPortletId(
			user.getUserId(), JournalContentPortletKeys.JOURNAL_CONTENT, false);

		long ownerId = PortletKeys.PREFS_OWNER_ID_DEFAULT;
		int ownerType = PortletKeys.PREFS_OWNER_TYPE_LAYOUT;

		PortletPreferences prefs =
			_portletPreferencesLocalService.getPreferences(
				company.getCompanyId(), ownerId, ownerType, layout.getPlid(),
				portletIdAdded);

		DDMStructure ddmStructure = journalArticle.getDDMStructure();

		FileEntry postmanFileEntry = _addPostmanVarFile(
			group, user, ddmStructure, journalArticle, folder, fileEntry);

		JournalArticle mainPageJournalArticle =
			_journalArticleLocalService.addArticle(
				user.getUserId(), _group.getGroupId(),
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				Collections.singletonMap(LocaleUtil.US, "Main Page"), null,
				_read(
					"journal-article/demo-variables.xml",
					Arrays.asList(
						String.valueOf(group.getGroupId()),
						String.valueOf(fileEntry.getFileEntryId()),
						String.valueOf(folder.getFolderId()),
						String.valueOf(ddmStructure.getStructureId()),
						String.valueOf(journalArticle.getResourcePrimKey()),
						String.valueOf(user.getUserId()),
						String.valueOf(postmanFileEntry.getGroupId()),
						String.valueOf(postmanFileEntry.getTitle()),
						String.valueOf(postmanFileEntry.getUuid()))),
				mainPageDDMStructure.getStructureKey(),
				mainPageDDMTemplate.getTemplateKey(),
				_getServiceContext(company, group, user));

		prefs.setValue("articleId", mainPageJournalArticle.getArticleId());

		_portletPreferencesLocalService.updatePreferences(
			ownerId, ownerType, layout.getPlid(), portletIdAdded, prefs);

		_layoutLocalService.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());
	}

	private DDMForm _deserialize(String content) {
		DDMFormDeserializer ddmFormDeserializer =
			_ddmFormDeserializerTracker.getDDMFormDeserializer("json");

		DDMFormDeserializerDeserializeRequest.Builder builder =
			DDMFormDeserializerDeserializeRequest.Builder.newBuilder(content);

		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				ddmFormDeserializer.deserialize(builder.build());

		return ddmFormDeserializerDeserializeResponse.getDDMForm();
	}

	private DDMStructure _getDDMStructure(
			Company company, Group group, User user, String fileName)
		throws Exception {

		DDMForm ddmForm = _deserialize(_read(fileName));

		DDMFormLayout ddmFormLayout = DDMUtil.getDefaultDDMFormLayout(ddmForm);

		return _ddmStructureLocalService.addStructure(
			user.getUserId(), group.getGroupId(),
			DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			_portal.getClassNameId(JournalArticle.class), null,
			Collections.singletonMap(LocaleUtil.US, fileName),
			Collections.singletonMap(LocaleUtil.US, fileName), ddmForm,
			ddmFormLayout, StorageType.JSON.getValue(),
			DDMStructureConstants.TYPE_DEFAULT,
			_getServiceContext(company, group, user));
	}

	private ServiceContext _getServiceContext(
		Company company, Group group, User user) {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCompanyId(company.getCompanyId());
		serviceContext.setScopeGroupId(group.getGroupId());
		serviceContext.setUserId(user.getUserId());

		return serviceContext;
	}

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(_PATH + fileName);

		return StringUtil.read(inputStream);
	}

	private String _read(String fileName, List<String> vars) throws Exception {
		return String.format(_read(fileName), vars.toArray());
	}

	private static final String _PATH =
		"/com/liferay/headless/apio/demo/internal/dependencies/";

	private static final String _SITE_NAME = "Sports Magazine";

	private static final Log _log = LogFactoryUtil.getLog(HeadlessDemo.class);

	@Reference
	private DDMFormDeserializerTracker _ddmFormDeserializerTracker;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	private Group _group;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}