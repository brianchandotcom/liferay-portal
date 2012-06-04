Liferay.Service.register("Liferay.Service.Portal", "com.liferay.portal.service");

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Account",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Address",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addAddress: true,
		deleteAddress: true,
		getAddress: true,
		getAddresses: true,
		updateAddress: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "ClassName",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		getClassName: true,
		getClassNameId: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Company",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addCompany: true,
		deleteLogo: true,
		getCompanyById: true,
		getCompanyByLogoId: true,
		getCompanyByMx: true,
		getCompanyByVirtualHost: true,
		getCompanyByWebId: true,
		removePreferences: true,
		updateCompany: true,
		updateDisplay: true,
		updatePreferences: true,
		updateSecurity: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Contact",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Country",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addCountry: true,
		fetchCountry: true,
		fetchCountryByA2: true,
		fetchCountryByA3: true,
		getCountries: true,
		getCountry: true,
		getCountryByA2: true,
		getCountryByA3: true,
		getCountryByName: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "EmailAddress",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addEmailAddress: true,
		deleteEmailAddress: true,
		getEmailAddress: true,
		getEmailAddresses: true,
		updateEmailAddress: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Group",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addGroup: true,
		addRoleGroups: true,
		deleteGroup: true,
		getGroup: true,
		getManageableSites: true,
		getOrganizationsGroups: true,
		getUserGroup: true,
		getUserGroupsGroups: true,
		getUserOrganizationsGroups: true,
		getUserPlaces: true,
		getUserSites: true,
		hasUserGroup: true,
		search: true,
		searchCount: true,
		setRoleGroups: true,
		unsetRoleGroups: true,
		updateFriendlyURL: true,
		updateGroup: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Image",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		getImage: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Layout",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addLayout: true,
		deleteLayout: true,
		getDefaultPlid: true,
		getLayoutName: true,
		getLayoutReferences: true,
		getLayouts: true,
		setLayouts: true,
		unschedulePublishToLive: true,
		unschedulePublishToRemote: true,
		updateLayout: true,
		updateLookAndFeel: true,
		updateName: true,
		updateParentLayoutId: true,
		updatePriority: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "LayoutBranch",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addLayoutBranch: true,
		deleteLayoutBranch: true,
		updateLayoutBranch: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "LayoutPrototype",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addLayoutPrototype: true,
		deleteLayoutPrototype: true,
		getLayoutPrototype: true,
		search: true,
		updateLayoutPrototype: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "LayoutRevision",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addLayoutRevision: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "LayoutSet",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		updateLayoutSetPrototypeLinkEnabled: true,
		updateLookAndFeel: true,
		updateSettings: true,
		updateVirtualHost: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "LayoutSetBranch",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addLayoutSetBranch: true,
		deleteLayoutSetBranch: true,
		getLayoutSetBranches: true,
		mergeLayoutSetBranch: true,
		updateLayoutSetBranch: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "LayoutSetPrototype",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addLayoutSetPrototype: true,
		deleteLayoutSetPrototype: true,
		getLayoutSetPrototype: true,
		search: true,
		updateLayoutSetPrototype: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "ListType",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		getListType: true,
		getListTypes: true,
		validate: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "MembershipRequest",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addMembershipRequest: true,
		deleteMembershipRequests: true,
		getMembershipRequest: true,
		updateStatus: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Organization",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addGroupOrganizations: true,
		addOrganization: true,
		addPasswordPolicyOrganizations: true,
		deleteLogo: true,
		deleteOrganization: true,
		getManageableOrganizations: true,
		getOrganization: true,
		getOrganizationId: true,
		getOrganizations: true,
		getOrganizationsCount: true,
		getUserOrganizations: true,
		setGroupOrganizations: true,
		unsetGroupOrganizations: true,
		unsetPasswordPolicyOrganizations: true,
		updateOrganization: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "OrgLabor",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addOrgLabor: true,
		deleteOrgLabor: true,
		getOrgLabor: true,
		getOrgLabors: true,
		updateOrgLabor: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "PasswordPolicy",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addPasswordPolicy: true,
		deletePasswordPolicy: true,
		updatePasswordPolicy: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Permission",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		checkPermission: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Phone",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addPhone: true,
		deletePhone: true,
		getPhone: true,
		getPhones: true,
		updatePhone: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "PluginSetting",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		updatePluginSetting: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Portal",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		getAutoDeployDirectory: true,
		getBuildNumber: true,
		testAddClassName_Rollback: true,
		testAddClassName_Success: true,
		testAddClassNameAndTestTransactionPortletBar_PortalRollback: true,
		testAddClassNameAndTestTransactionPortletBar_PortletRollback: true,
		testAddClassNameAndTestTransactionPortletBar_Success: true,
		testCounterIncrement_Rollback: true,
		testDeleteClassName: true,
		testGetBuildNumber: true,
		testGetUserId: true,
		testHasClassName: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Portlet",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		getWARPortlets: true,
		updatePortlet: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "PortletPreferences",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		deleteArchivedPreferences: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Region",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addRegion: true,
		fetchRegion: true,
		getRegion: true,
		getRegions: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Repository",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addRepository: true,
		checkRepository: true,
		deleteRepository: true,
		getLocalRepositoryImpl: true,
		getRepository: true,
		getRepositoryImpl: true,
		getSupportedConfigurations: true,
		getSupportedParameters: true,
		getTypeSettingsProperties: true,
		updateRepository: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "ResourceBlock",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addCompanyScopePermission: true,
		addGroupScopePermission: true,
		addIndividualScopePermission: true,
		removeAllGroupScopePermissions: true,
		removeCompanyScopePermission: true,
		removeGroupScopePermission: true,
		removeIndividualScopePermission: true,
		setCompanyScopePermissions: true,
		setGroupScopePermissions: true,
		setIndividualScopePermissions: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "ResourcePermission",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addResourcePermission: true,
		removeResourcePermission: true,
		removeResourcePermissions: true,
		setIndividualResourcePermissions: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Role",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addRole: true,
		addUserRoles: true,
		deleteRole: true,
		getGroupRoles: true,
		getRole: true,
		getUserGroupGroupRoles: true,
		getUserGroupRoles: true,
		getUserRelatedRoles: true,
		getUserRoles: true,
		hasUserRole: true,
		hasUserRoles: true,
		unsetUserRoles: true,
		updateRole: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Team",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addTeam: true,
		deleteTeam: true,
		getGroupTeams: true,
		getTeam: true,
		getUserTeams: true,
		hasUserTeam: true,
		updateTeam: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Theme",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		getThemes: true,
		getWARThemes: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "User",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addGroupUsers: true,
		addOrganizationUsers: true,
		addPasswordPolicyUsers: true,
		addRoleUsers: true,
		addTeamUsers: true,
		addUser: true,
		addUserGroupUsers: true,
		addUserWithWorkflow: true,
		deletePortrait: true,
		deleteRoleUser: true,
		deleteUser: true,
		getGroupUserIds: true,
		getOrganizationUserIds: true,
		getRoleUserIds: true,
		getUserByEmailAddress: true,
		getUserById: true,
		getUserByScreenName: true,
		getUserIdByEmailAddress: true,
		getUserIdByScreenName: true,
		hasGroupUser: true,
		hasRoleUser: true,
		setRoleUsers: true,
		setUserGroupUsers: true,
		unsetGroupUsers: true,
		unsetOrganizationUsers: true,
		unsetPasswordPolicyUsers: true,
		unsetRoleUsers: true,
		unsetTeamUsers: true,
		unsetUserGroupUsers: true,
		updateAgreedToTermsOfUse: true,
		updateEmailAddress: true,
		updateIncompleteUser: true,
		updateLockoutById: true,
		updateOpenId: true,
		updateOrganizations: true,
		updatePassword: true,
		updateReminderQuery: true,
		updateScreenName: true,
		updateStatus: true,
		updateUser: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "UserGroup",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addGroupUserGroups: true,
		addTeamUserGroups: true,
		addUserGroup: true,
		deleteUserGroup: true,
		getUserGroup: true,
		getUserUserGroups: true,
		unsetGroupUserGroups: true,
		unsetTeamUserGroups: true,
		updateUserGroup: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "UserGroupGroupRole",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addUserGroupGroupRoles: true,
		deleteUserGroupGroupRoles: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "UserGroupRole",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addUserGroupRoles: true,
		deleteUserGroupRoles: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Website",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addWebsite: true,
		deleteWebsite: true,
		getWebsite: true,
		getWebsites: true,
		updateWebsite: true
	}
);

Liferay.Service.register("Liferay.Service.Announcements", "com.liferay.portlet.announcements.service");

Liferay.Service.registerClass(
	Liferay.Service.Announcements, "AnnouncementsDelivery",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		updateDelivery: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Announcements, "AnnouncementsEntry",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addEntry: true,
		deleteEntry: true,
		updateEntry: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Announcements, "AnnouncementsFlag",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addFlag: true,
		deleteFlag: true,
		getFlag: true
	}
);

Liferay.Service.register("Liferay.Service.Asset", "com.liferay.portlet.asset.service");

Liferay.Service.registerClass(
	Liferay.Service.Asset, "AssetCategory",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addCategory: true,
		deleteCategories: true,
		deleteCategory: true,
		getCategories: true,
		getCategory: true,
		getChildCategories: true,
		getJSONSearch: true,
		getJSONVocabularyCategories: true,
		getVocabularyCategories: true,
		getVocabularyCategoriesCount: true,
		getVocabularyRootCategories: true,
		moveCategory: true,
		search: true,
		updateCategory: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Asset, "AssetCategoryProperty",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addCategoryProperty: true,
		deleteCategoryProperty: true,
		getCategoryProperties: true,
		getCategoryPropertyValues: true,
		updateCategoryProperty: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Asset, "AssetEntry",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		getCompanyEntries: true,
		getCompanyEntriesCount: true,
		getCompanyEntryDisplays: true,
		getEntries: true,
		getEntriesCount: true,
		getEntry: true,
		incrementViewCounter: true,
		searchEntryDisplays: true,
		searchEntryDisplaysCount: true,
		updateEntry: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Asset, "AssetTag",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addTag: true,
		deleteTag: true,
		deleteTags: true,
		getGroupsTags: true,
		getGroupTags: true,
		getGroupTagsCount: true,
		getJSONGroupTags: true,
		getTag: true,
		getTags: true,
		getTagsCount: true,
		mergeTags: true,
		search: true,
		updateTag: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Asset, "AssetTagProperty",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addTagProperty: true,
		deleteTagProperty: true,
		getTagProperties: true,
		getTagPropertyValues: true,
		updateTagProperty: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Asset, "AssetVocabulary",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addVocabulary: true,
		deleteVocabularies: true,
		deleteVocabulary: true,
		getCompanyVocabularies: true,
		getGroupsVocabularies: true,
		getGroupVocabularies: true,
		getGroupVocabulariesCount: true,
		getJSONGroupVocabularies: true,
		getVocabularies: true,
		getVocabulary: true,
		updateVocabulary: true
	}
);

Liferay.Service.register("Liferay.Service.Blogs", "com.liferay.portlet.blogs.service");

Liferay.Service.registerClass(
	Liferay.Service.Blogs, "BlogsEntry",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		deleteEntry: true,
		getCompanyEntries: true,
		getEntry: true,
		getGroupEntries: true,
		getGroupEntriesCount: true,
		getGroupsEntries: true,
		getOrganizationEntries: true,
		moveEntryToTrash: true,
		restoreEntryFromTrash: true,
		subscribe: true,
		unsubscribe: true
	}
);

Liferay.Service.register("Liferay.Service.Bookmarks", "com.liferay.portlet.bookmarks.service");

Liferay.Service.registerClass(
	Liferay.Service.Bookmarks, "BookmarksEntry",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addEntry: true,
		deleteEntry: true,
		getEntries: true,
		getEntriesCount: true,
		getEntry: true,
		getFoldersEntriesCount: true,
		getGroupEntries: true,
		getGroupEntriesCount: true,
		openEntry: true,
		updateEntry: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Bookmarks, "BookmarksFolder",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addFolder: true,
		deleteFolder: true,
		getFolder: true,
		getFolders: true,
		getFoldersCount: true,
		getSubfolderIds: true,
		updateFolder: true
	}
);

Liferay.Service.register("Liferay.Service.Cal", "com.liferay.portlet.calendar.service");

Liferay.Service.registerClass(
	Liferay.Service.Cal, "CalEvent",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addEvent: true,
		deleteEvent: true,
		getEvent: true,
		getEvents: true,
		getEventsCount: true,
		hasEvents: true,
		updateEvent: true
	}
);

Liferay.Service.register("Liferay.Service.DL", "com.liferay.portlet.documentlibrary.service");

Liferay.Service.registerClass(
	Liferay.Service.DL, "DLApp",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addFileShortcut: true,
		addFolder: true,
		cancelCheckOut: true,
		checkInFileEntry: true,
		checkOutFileEntry: true,
		copyFolder: true,
		deleteFileEntry: true,
		deleteFileEntryByTitle: true,
		deleteFileShortcut: true,
		deleteFileVersion: true,
		deleteFolder: true,
		deleteTempFileEntry: true,
		getFileEntries: true,
		getFileEntriesAndFileShortcutsCount: true,
		getFileEntriesCount: true,
		getFileEntry: true,
		getFileEntryByUuidAndGroupId: true,
		getFileShortcut: true,
		getFolder: true,
		getFolders: true,
		getFoldersAndFileEntriesAndFileShortcutsCount: true,
		getFoldersCount: true,
		getFoldersFileEntriesCount: true,
		getGroupFileEntries: true,
		getGroupFileEntriesCount: true,
		getMountFolders: true,
		getMountFoldersCount: true,
		getSubfolderIds: true,
		getTempFileEntryNames: true,
		lockFileEntry: true,
		lockFolder: true,
		moveFileEntry: true,
		moveFileEntryToTrash: true,
		moveFileShortcutToTrash: true,
		moveFolder: true,
		moveFolderToTrash: true,
		refreshFileEntryLock: true,
		refreshFolderLock: true,
		restoreFileEntryFromTrash: true,
		restoreFileShortcutFromTrash: true,
		restoreFolderFromTrash: true,
		revertFileEntry: true,
		search: true,
		unlockFileEntry: true,
		unlockFolder: true,
		updateFileShortcut: true,
		updateFolder: true,
		verifyFileEntryCheckOut: true,
		verifyFileEntryLock: true,
		verifyInheritableLock: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.DL, "DLFileEntry",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		cancelCheckOut: true,
		checkInFileEntry: true,
		checkOutFileEntry: true,
		copyFileEntry: true,
		deleteFileEntry: true,
		deleteFileVersion: true,
		fetchFileEntryByImageId: true,
		getFileEntries: true,
		getFileEntriesCount: true,
		getFileEntry: true,
		getFileEntryByUuidAndGroupId: true,
		getFileEntryLock: true,
		getFoldersFileEntriesCount: true,
		getGroupFileEntries: true,
		getGroupFileEntriesCount: true,
		hasFileEntryLock: true,
		isFileEntryCheckedOut: true,
		lockFileEntry: true,
		moveFileEntry: true,
		refreshFileEntryLock: true,
		revertFileEntry: true,
		unlockFileEntry: true,
		verifyFileEntryCheckOut: true,
		verifyFileEntryLock: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.DL, "DLFileEntryType",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addFileEntryType: true,
		deleteFileEntryType: true,
		getFileEntryType: true,
		getFileEntryTypes: true,
		getFileEntryTypesCount: true,
		search: true,
		searchCount: true,
		updateFileEntryType: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.DL, "DLFileShortcut",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addFileShortcut: true,
		deleteFileShortcut: true,
		getFileShortcut: true,
		updateFileShortcut: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.DL, "DLFileVersion",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		getFileVersion: true,
		getLatestFileVersion: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.DL, "DLFolder",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addFolder: true,
		deleteFolder: true,
		getFileEntriesAndFileShortcutsCount: true,
		getFolder: true,
		getFolderIds: true,
		getFolders: true,
		getFoldersAndFileEntriesAndFileShortcuts: true,
		getFoldersAndFileEntriesAndFileShortcutsCount: true,
		getFoldersCount: true,
		getMountFolders: true,
		getMountFoldersCount: true,
		getSubfolderIds: true,
		hasFolderLock: true,
		hasInheritableLock: true,
		isFolderLocked: true,
		lockFolder: true,
		moveFolder: true,
		refreshFolderLock: true,
		unlockFolder: true,
		updateFolder: true,
		verifyInheritableLock: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.DL, "DLSync",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		getDLSyncUpdate: true
	}
);

Liferay.Service.register("Liferay.Service.DDL", "com.liferay.portlet.dynamicdatalists.service");

Liferay.Service.registerClass(
	Liferay.Service.DDL, "DDLRecord",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addRecord: true,
		getRecord: true,
		updateRecord: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.DDL, "DDLRecordSet",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addRecordSet: true,
		deleteRecordSet: true,
		getRecordSet: true,
		updateMinDisplayRows: true,
		updateRecordSet: true
	}
);

Liferay.Service.register("Liferay.Service.DDM", "com.liferay.portlet.dynamicdatamapping.service");

Liferay.Service.registerClass(
	Liferay.Service.DDM, "DDMStructure",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addStructure: true,
		copyStructure: true,
		deleteStructure: true,
		fetchStructure: true,
		getStructure: true,
		search: true,
		searchCount: true,
		updateStructure: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.DDM, "DDMTemplate",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addTemplate: true,
		copyTemplates: true,
		deleteTemplate: true,
		fetchTemplate: true,
		getTemplate: true,
		getTemplates: true,
		search: true,
		searchCount: true,
		updateTemplate: true
	}
);

Liferay.Service.register("Liferay.Service.Expando", "com.liferay.portlet.expando.service");

Liferay.Service.registerClass(
	Liferay.Service.Expando, "ExpandoColumn",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addColumn: true,
		deleteColumn: true,
		updateColumn: true,
		updateTypeSettings: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Expando, "ExpandoValue",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addValue: true,
		addValues: true,
		getData: true,
		getJSONData: true
	}
);

Liferay.Service.register("Liferay.Service.Flags", "com.liferay.portlet.flags.service");

Liferay.Service.registerClass(
	Liferay.Service.Flags, "FlagsEntry",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addEntry: true
	}
);

Liferay.Service.register("Liferay.Service.IG", "com.liferay.portlet.imagegallery.service");

Liferay.Service.register("Liferay.Service.Journal", "com.liferay.portlet.journal.service");

Liferay.Service.registerClass(
	Liferay.Service.Journal, "JournalArticle",
	{
		addArticle: true,
		copyArticle: true,
		deleteArticle: true,
		expireArticle: true,
		getArticle: true,
		getArticleByUrlTitle: true,
		getArticles: true,
		getArticlesByArticleId: true,
		getArticlesByLayoutUuid: true,
		getArticlesByStructureId: true,
		getArticlesByUserId: true,
		getArticlesCount: true,
		getArticlesCountByArticleId: true,
		getArticlesCountByStructureId: true,
		getArticlesCountByUserId: true,
		getDisplayArticleByUrlTitle: true,
		getFoldersAndArticlesCount: true,
		getLatestArticle: true,
		removeArticleLocale: true,
		search: true,
		searchCount: true,
		subscribe: true,
		unsubscribe: true,
		updateArticle: true,
		updateContent: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Journal, "JournalFeed",
	{
		addFeed: true,
		deleteFeed: true,
		getFeed: true,
		updateFeed: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Journal, "JournalFolder",
	{
		addFolder: true,
		deleteFolder: true,
		getFolder: true,
		getFolders: true,
		getFoldersAndArticlesCount: true,
		getFoldersCount: true,
		getSubfolderIds: true,
		moveFolder: true,
		updateFolder: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Journal, "JournalStructure",
	{
		addStructure: true,
		copyStructure: true,
		deleteStructure: true,
		getStructure: true,
		getStructures: true,
		search: true,
		searchCount: true,
		updateStructure: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Journal, "JournalTemplate",
	{
		addTemplate: true,
		copyTemplate: true,
		deleteTemplate: true,
		getStructureTemplates: true,
		getTemplate: true,
		search: true,
		searchCount: true,
		updateTemplate: true
	}
);

Liferay.Service.register("Liferay.Service.MB", "com.liferay.portlet.messageboards.service");

Liferay.Service.registerClass(
	Liferay.Service.MB, "MBBan",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addBan: true,
		deleteBan: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.MB, "MBCategory",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addCategory: true,
		deleteCategory: true,
		getCategories: true,
		getCategoriesCount: true,
		getCategory: true,
		getCategoryIds: true,
		getSubcategoryIds: true,
		getSubscribedCategories: true,
		getSubscribedCategoriesCount: true,
		subscribeCategory: true,
		unsubscribeCategory: true,
		updateCategory: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.MB, "MBMessage",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addDiscussionMessage: true,
		addMessage: true,
		deleteDiscussionMessage: true,
		deleteMessage: true,
		getCategoryMessages: true,
		getCategoryMessagesCount: true,
		getGroupMessagesCount: true,
		getMessage: true,
		getMessageDisplay: true,
		getThreadAnswersCount: true,
		getThreadMessages: true,
		getThreadMessagesCount: true,
		subscribeMessage: true,
		unsubscribeMessage: true,
		updateAnswer: true,
		updateDiscussionMessage: true,
		updateMessage: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.MB, "MBThread",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		deleteThread: true,
		getGroupThreads: true,
		getGroupThreadsCount: true,
		getThreads: true,
		getThreadsCount: true,
		lockThread: true,
		moveThread: true,
		splitThread: true,
		unlockThread: true
	}
);

Liferay.Service.register("Liferay.Service.MDR", "com.liferay.portlet.mobiledevicerules.service");

Liferay.Service.registerClass(
	Liferay.Service.MDR, "MDRAction",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addAction: true,
		deleteAction: true,
		fetchAction: true,
		getAction: true,
		updateAction: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.MDR, "MDRRule",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addRule: true,
		deleteRule: true,
		fetchRule: true,
		getRule: true,
		updateRule: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.MDR, "MDRRuleGroup",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addRuleGroup: true,
		copyRuleGroup: true,
		deleteRuleGroup: true,
		fetchRuleGroup: true,
		getRuleGroup: true,
		updateRuleGroup: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.MDR, "MDRRuleGroupInstance",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addRuleGroupInstance: true,
		deleteRuleGroupInstance: true,
		getRuleGroupInstances: true,
		getRuleGroupInstancesCount: true,
		updateRuleGroupInstance: true
	}
);

Liferay.Service.register("Liferay.Service.Polls", "com.liferay.portlet.polls.service");

Liferay.Service.registerClass(
	Liferay.Service.Polls, "PollsChoice",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Polls, "PollsQuestion",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addQuestion: true,
		deleteQuestion: true,
		getQuestion: true,
		updateQuestion: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Polls, "PollsVote",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addVote: true
	}
);

Liferay.Service.register("Liferay.Service.Ratings", "com.liferay.portlet.ratings.service");

Liferay.Service.registerClass(
	Liferay.Service.Ratings, "RatingsEntry",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		deleteEntry: true,
		updateEntry: true
	}
);

Liferay.Service.register("Liferay.Service.Shopping", "com.liferay.portlet.shopping.service");

Liferay.Service.registerClass(
	Liferay.Service.Shopping, "ShoppingCategory",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addCategory: true,
		deleteCategory: true,
		getCategories: true,
		getCategoriesCount: true,
		getCategory: true,
		getSubcategoryIds: true,
		updateCategory: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Shopping, "ShoppingCoupon",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addCoupon: true,
		deleteCoupon: true,
		getCoupon: true,
		search: true,
		updateCoupon: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Shopping, "ShoppingItem",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addBookItems: true,
		deleteItem: true,
		getCategoriesItemsCount: true,
		getItem: true,
		getItems: true,
		getItemsCount: true,
		getItemsPrevAndNext: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Shopping, "ShoppingOrder",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		completeOrder: true,
		deleteOrder: true,
		getOrder: true,
		sendEmail: true,
		updateOrder: true
	}
);

Liferay.Service.register("Liferay.Service.Social", "com.liferay.portlet.social.service");

Liferay.Service.registerClass(
	Liferay.Service.Social, "SocialActivitySetting",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		getActivityDefinition: true,
		getActivityDefinitions: true,
		getJSONActivityDefinitions: true,
		updateActivitySetting: true,
		updateActivitySettings: true
	}
);

Liferay.Service.register("Liferay.Service.SC", "com.liferay.portlet.softwarecatalog.service");

Liferay.Service.registerClass(
	Liferay.Service.SC, "SCFrameworkVersion",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addFrameworkVersion: true,
		deleteFrameworkVersion: true,
		getFrameworkVersion: true,
		getFrameworkVersions: true,
		updateFrameworkVersion: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.SC, "SCLicense",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addLicense: true,
		deleteLicense: true,
		getLicense: true,
		updateLicense: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.SC, "SCProductEntry",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addProductEntry: true,
		deleteProductEntry: true,
		getProductEntry: true,
		updateProductEntry: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.SC, "SCProductVersion",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addProductVersion: true,
		deleteProductVersion: true,
		getProductVersion: true,
		getProductVersions: true,
		getProductVersionsCount: true,
		updateProductVersion: true
	}
);

Liferay.Service.register("Liferay.Service.Wiki", "com.liferay.portlet.wiki.service");

Liferay.Service.registerClass(
	Liferay.Service.Wiki, "WikiNode",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addNode: true,
		deleteNode: true,
		getNode: true,
		importPages: true,
		subscribeNode: true,
		unsubscribeNode: true,
		updateNode: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Wiki, "WikiPage",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		addPage: true,
		addPageAttachments: true,
		changeParent: true,
		deletePage: true,
		deletePageAttachment: true,
		deleteTempPageAttachment: true,
		getDraftPage: true,
		getNodePages: true,
		getNodePagesRSS: true,
		getPage: true,
		getPagesRSS: true,
		getTempPageAttachmentNames: true,
		movePage: true,
		revertPage: true,
		subscribePage: true,
		unsubscribePage: true,
		updatePage: true
	}
);

Liferay.Service.register("Liferay.Service.Trash", "com.liferay.portlet.trash.service");

Liferay.Service.registerClass(
	Liferay.Service.Trash, "TrashEntry",
	{
		getBeanIdentifier: true,
		setBeanIdentifier: true,
		deleteEntries: true,
		getEntries: true
	}
);