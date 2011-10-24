Liferay.Service.register("Liferay.Service.Portal", "com.liferay.portal.service");

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Address",
	{
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
		getClassName: true,
		getClassNameId: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Company",
	{
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
	Liferay.Service.Portal, "Country",
	{
		addCountry: true,
		fetchCountry: true,
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
		addGroup: true,
		addRoleGroups: true,
		deleteGroup: true,
		getGroup: true,
		getManageableSites: true,
		getOrganizationsGroups: true,
		getUserGroup: true,
		getUserGroupsGroups: true,
		getUserOrganizationsGroups: true,
		getUserSites: true,
		getUserPlaces: true,
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
		getImage: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Layout",
	{
		addLayout: true,
		deleteLayout: true,
		getDefaultPlid: true,
		getLayoutName: true,
		getLayoutReferences: true,
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
		addLayoutBranch: true,
		deleteLayoutBranch: true,
		updateLayoutBranch: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "LayoutPrototype",
	{
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
		addLayoutRevision: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "LayoutSet",
	{
		updateLookAndFeel: true,
		updateSettings: true,
		updateVirtualHost: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "LayoutSetBranch",
	{
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
		getListType: true,
		getListTypes: true,
		validate: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "MembershipRequest",
	{
		addMembershipRequest: true,
		deleteMembershipRequests: true,
		getMembershipRequest: true,
		updateStatus: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Organization",
	{
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
		addPasswordPolicy: true,
		deletePasswordPolicy: true,
		updatePasswordPolicy: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Permission",
	{
		checkPermission: true,
		hasGroupPermission: true,
		hasUserPermission: true,
		hasUserPermissions: true,
		setGroupPermissions: true,
		setIndividualPermissions: true,
		setOrgGroupPermissions: true,
		setRolePermission: true,
		setRolePermissions: true,
		setUserPermissions: true,
		unsetRolePermission: true,
		unsetRolePermissions: true,
		unsetUserPermissions: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Phone",
	{
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
		updatePluginSetting: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Portal",
	{
		getAutoDeployDirectory: true,
		getBuildNumber: true,
		testAddClassName_Rollback: true,
		testAddClassName_Success: true,
		testAddClassNameAndTestTransactionPortletBar_PortalRollback: true,
		testAddClassNameAndTestTransactionPortletBar_PortletRollback: true,
		testAddClassNameAndTestTransactionPortletBar_Success: true,
		testCounterIncrement_Rollback: true,
		testDeleteClassName: true,
		testGetUserId: true,
		testHasClassName: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Portlet",
	{
		getWARPortlets: true,
		updatePortlet: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "PortletPreferences",
	{
		deleteArchivedPreferences: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Region",
	{
		addRegion: true,
		getRegions: true,
		getRegion: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Repository",
	{
		addRepository: true,
		checkRepository: true,
		getLocalRepositoryImpl: true,
		getRepository: true,
		getRepositoryImpl: true,
		getSupportedConfigurations: true,
		getSupportedParameters: true,
		getTypeSettingsProperties: true,
		unmountRepositories: true,
		unmountRepository: true,
		updateRepository: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Resource",
	{
		getResource: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "ResourceBlock",
	{
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
		addResourcePermission: true,
		removeResourcePermission: true,
		removeResourcePermissions: true,
		setIndividualResourcePermissions: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Role",
	{
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
		getThemes: true,
		getWARThemes: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "User",
	{
		addGroupUsers: true,
		addOrganizationUsers: true,
		addPasswordPolicyUsers: true,
		addRoleUsers: true,
		addTeamUsers: true,
		addUser: true,
		addUserWithWorkflow: true,
		addUserGroupUsers: true,
		deletePortrait: true,
		deleteRoleUser: true,
		deleteUser: true,
		getDefaultUserId: true,
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
		updateIncompleteUser: true,
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
		addUserGroupGroupRoles: true,
		deleteUserGroupGroupRoles: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "UserGroupRole",
	{
		addUserGroupRoles: true,
		deleteUserGroupRoles: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Portal, "Website",
	{
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
		updateDelivery: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Announcements, "AnnouncementsEntry",
	{
		addEntry: true,
		deleteEntry: true,
		updateEntry: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Announcements, "AnnouncementsFlag",
	{
		addFlag: true,
		deleteFlag: true,
		getFlag: true
	}
);

Liferay.Service.register("Liferay.Service.Asset", "com.liferay.portlet.asset.service");

Liferay.Service.registerClass(
	Liferay.Service.Asset, "AssetCategory",
	{
		addCategory: true,
		deleteCategories: true,
		deleteCategory: true,
		getCategories: true,
		getCategory: true,
		getChildCategories: true,
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
		deleteEntry: true,
		getCompanyEntries: true,
		getEntry: true,
		getGroupEntries: true,
		getGroupEntriesCount: true,
		getGroupsEntries: true,
		getOrganizationEntries: true,
		subscribe: true,
		unsubscribe: true
	}
);

Liferay.Service.register("Liferay.Service.Bookmarks", "com.liferay.portlet.bookmarks.service");

Liferay.Service.registerClass(
	Liferay.Service.Bookmarks, "BookmarksEntry",
	{
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
		addFileShortcut: true,
		addFolder: true,
		cancelCheckOut: true,
		checkInFileEntry: true,
		checkOutFileEntry: true,
		copyFolder: true,
		deleteFileEntry: true,
		deleteFileEntryByTitle: true,
		deleteFileShortcut: true,
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
		getSubfolderIds: true,
		getTempFileEntryNames: true,
		lockFolder: true,
		moveFileEntry: true,
		moveFolder: true,
		refreshFileEntryLock: true,
		refreshFolderLock: true,
		revertFileEntry: true,
		search: true,
		unlockFolder: true,
		updateFileShortcut: true,
		updateFolder: true,
		verifyFileEntryCheckOut: true,
		verifyInheritableLock: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.DL, "DLFileEntry",
	{
		cancelCheckOut: true,
		checkInFileEntry: true,
		checkOutFileEntry: true,
		copyFileEntry: true,
		deleteFileEntry: true,
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
		moveFileEntry: true,
		refreshFileEntryLock: true,
		revertFileEntry: true,
		verifyFileEntryCheckOut: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.DL, "DLFileEntryType",
	{
		addFileEntryType: true,
		deleteFileEntryType: true,
		getFileEntryType: true,
		getFileEntryTypes: true,
		getFileEntryTypesCount: true,
		updateFileEntryType: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.DL, "DLFileShortcut",
	{
		addFileShortcut: true,
		deleteFileShortcut: true,
		getFileShortcut: true,
		updateFileShortcut: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.DL, "DLFileVersion",
	{
		getFileVersion: true,
		getLatestFileVersion: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.DL, "DLFolder",
	{
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
		getDLSyncUpdate: true
	}
);

Liferay.Service.register("Liferay.Service.DDL", "com.liferay.portlet.dynamicdatalists.service");

Liferay.Service.registerClass(
	Liferay.Service.DDL, "DDLRecord",
	{
		addRecord: true,
		updateRecord: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.DDL, "DDLRecordSet",
	{
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
		addStructure: true,
		copyStructure: true,
		deleteStructure: true,
		fetchStructure: true,
		getStructure: true,
		updateStructure: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.DDM, "DDMStructureLink",
	{
		addStructureLink: true,
		deleteStructureLink: true,
		getStructureLink: true,
		updateStructureLink: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.DDM, "DDMTemplate",
	{
		addTemplate: true,
		deleteTemplate: true,
		getTemplate: true,
		getTemplates: true,
		updateTemplate: true
	}
);

Liferay.Service.register("Liferay.Service.Expando", "com.liferay.portlet.expando.service");

Liferay.Service.registerClass(
	Liferay.Service.Expando, "ExpandoColumn",
	{
		addColumn: true,
		deleteColumn: true,
		updateColumn: true,
		updateTypeSettings: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Expando, "ExpandoValue",
	{
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
		getArticlesByLayoutUuid: true,
		getLatestArticle: true,
		removeArticleLocale: true,
		search: true,
		searchCount: true,
		subscribe: true,
		unsubscribe: true,
		updateArticle: true,
		updateArticleTranslation: true,
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
		addBan: true,
		deleteBan: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.MB, "MBCategory",
	{
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
		addRuleGroupInstance: true,
		deleteRuleGroupInstance: true,
		getRuleGroupInstances: true,
		getRuleGroupInstancesCount: true,
		updateRuleGroupInstance: true
	}
);

Liferay.Service.register("Liferay.Service.Polls", "com.liferay.portlet.polls.service");

Liferay.Service.registerClass(
	Liferay.Service.Polls, "PollsQuestion",
	{
		addQuestion: true,
		deleteQuestion: true,
		getQuestion: true,
		updateQuestion: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Polls, "PollsVote",
	{
		addVote: true
	}
);

Liferay.Service.register("Liferay.Service.Ratings", "com.liferay.portlet.ratings.service");

Liferay.Service.registerClass(
	Liferay.Service.Ratings, "RatingsEntry",
	{
		deleteEntry: true,
		updateEntry: true
	}
);

Liferay.Service.register("Liferay.Service.Shopping", "com.liferay.portlet.shopping.service");

Liferay.Service.registerClass(
	Liferay.Service.Shopping, "ShoppingCategory",
	{
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
		addLicense: true,
		deleteLicense: true,
		getLicense: true,
		updateLicense: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.SC, "SCProductEntry",
	{
		addProductEntry: true,
		deleteProductEntry: true,
		getProductEntry: true,
		updateProductEntry: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.SC, "SCProductVersion",
	{
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