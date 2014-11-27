create table Account_ (
	mvccVersion bigint default 0,
	accountId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	parentAccountId bigint,
	name varchar(75) null,
	legalName varchar(75) null,
	legalId varchar(75) null,
	legalType varchar(75) null,
	sicCode varchar(75) null,
	tickerSymbol varchar(75) null,
	industry varchar(75) null,
	type_ varchar(75) null,
	size_ varchar(75) null
);

create table Address (
	mvccVersion bigint default 0,
	uuid_ varchar(75) null,
	addressId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	classNameId bigint,
	classPK bigint,
	street1 varchar(75) null,
	street2 varchar(75) null,
	street3 varchar(75) null,
	city varchar(75) null,
	zip varchar(75) null,
	regionId bigint,
	countryId bigint,
	typeId int,
	mailing bit,
	primary_ bit
);

create table AnnouncementsDelivery (
	deliveryId bigint not null primary key,
	companyId bigint,
	userId bigint,
	type_ varchar(75) null,
	email bit,
	sms bit,
	website bit
);

create table AnnouncementsEntry (
	uuid_ varchar(75) null,
	entryId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	classNameId bigint,
	classPK bigint,
	title varchar(75) null,
	content longvarchar null,
	url longvarchar null,
	type_ varchar(75) null,
	displayDate timestamp null,
	expirationDate timestamp null,
	priority int,
	alert bit
);

create table AnnouncementsFlag (
	flagId bigint not null primary key,
	userId bigint,
	createDate timestamp null,
	entryId bigint,
	value int
);

create table AssetCategory (
	uuid_ varchar(75) null,
	categoryId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	parentCategoryId bigint,
	leftCategoryId bigint,
	rightCategoryId bigint,
	name varchar(75) null,
	title longvarchar null,
	description longvarchar null,
	vocabularyId bigint
);

create table AssetCategoryProperty (
	categoryPropertyId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	categoryId bigint,
	key_ varchar(75) null,
	value varchar(75) null
);

create table AssetEntries_AssetCategories (
	categoryId bigint not null,
	entryId bigint not null,
	primary key (categoryId, entryId)
);

create table AssetEntries_AssetTags (
	entryId bigint not null,
	tagId bigint not null,
	primary key (entryId, tagId)
);

create table AssetEntry (
	entryId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	classNameId bigint,
	classPK bigint,
	classUuid varchar(75) null,
	classTypeId bigint,
	visible bit,
	startDate timestamp null,
	endDate timestamp null,
	publishDate timestamp null,
	expirationDate timestamp null,
	mimeType varchar(75) null,
	title longvarchar null,
	description longvarchar null,
	summary longvarchar null,
	url longvarchar null,
	layoutUuid varchar(75) null,
	height int,
	width int,
	priority double,
	viewCount int
);

create table AssetLink (
	linkId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	entryId1 bigint,
	entryId2 bigint,
	type_ int,
	weight int
);

create table AssetTag (
	tagId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	name varchar(75) null,
	assetCount int
);

create table AssetTagProperty (
	tagPropertyId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	tagId bigint,
	key_ varchar(75) null,
	value varchar(255) null
);

create table AssetTagStats (
	tagStatsId bigint not null primary key,
	tagId bigint,
	classNameId bigint,
	assetCount int
);

create table AssetVocabulary (
	uuid_ varchar(75) null,
	vocabularyId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	name varchar(75) null,
	title longvarchar null,
	description longvarchar null,
	settings_ longvarchar null
);

create table BackgroundTask (
	mvccVersion bigint default 0,
	backgroundTaskId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	name varchar(255) null,
	servletContextNames varchar(255) null,
	taskExecutorClassName varchar(200) null,
	taskContextMap longvarchar null,
	completed bit,
	completionDate timestamp null,
	status int,
	statusMessage longvarchar null
);

create table BlogsEntry (
	uuid_ varchar(75) null,
	entryId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	title varchar(150) null,
	subtitle longvarchar null,
	urlTitle varchar(150) null,
	description longvarchar null,
	content longvarchar null,
	displayDate timestamp null,
	allowPingbacks bit,
	allowTrackbacks bit,
	trackbacks longvarchar null,
	coverImageFileEntryId bigint,
	coverImageURL varchar(75) null,
	smallImage bit,
	smallImageFileEntryId bigint,
	smallImageId bigint,
	smallImageURL longvarchar null,
	status int,
	statusByUserId bigint,
	statusByUserName varchar(75) null,
	statusDate timestamp null
);

create table BlogsStatsUser (
	statsUserId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	entryCount int,
	lastPostDate timestamp null,
	ratingsTotalEntries int,
	ratingsTotalScore double,
	ratingsAverageScore double
);

create table BrowserTracker (
	mvccVersion bigint default 0,
	browserTrackerId bigint not null primary key,
	userId bigint,
	browserKey bigint
);

create table CalEvent (
	uuid_ varchar(75) null,
	eventId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	title varchar(75) null,
	description longvarchar null,
	location longvarchar null,
	startDate timestamp null,
	endDate timestamp null,
	durationHour int,
	durationMinute int,
	allDay bit,
	timeZoneSensitive bit,
	type_ varchar(75) null,
	repeating bit,
	recurrence longvarchar null,
	remindBy int,
	firstReminder int,
	secondReminder int
);

create table ClassName_ (
	mvccVersion bigint default 0,
	classNameId bigint not null primary key,
	value varchar(200) null
);

create table ClusterGroup (
	mvccVersion bigint default 0,
	clusterGroupId bigint not null primary key,
	name varchar(75) null,
	clusterNodeIds varchar(75) null,
	wholeCluster bit
);

create table Company (
	mvccVersion bigint default 0,
	companyId bigint not null primary key,
	accountId bigint,
	webId varchar(75) null,
	key_ longvarchar null,
	mx varchar(75) null,
	homeURL longvarchar null,
	logoId bigint,
	system bit,
	maxUsers int,
	active_ bit
);

create table Contact_ (
	mvccVersion bigint default 0,
	contactId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	classNameId bigint,
	classPK bigint,
	accountId bigint,
	parentContactId bigint,
	emailAddress varchar(75) null,
	firstName varchar(75) null,
	middleName varchar(75) null,
	lastName varchar(75) null,
	prefixId int,
	suffixId int,
	male bit,
	birthday timestamp null,
	smsSn varchar(75) null,
	aimSn varchar(75) null,
	facebookSn varchar(75) null,
	icqSn varchar(75) null,
	jabberSn varchar(75) null,
	msnSn varchar(75) null,
	mySpaceSn varchar(75) null,
	skypeSn varchar(75) null,
	twitterSn varchar(75) null,
	ymSn varchar(75) null,
	employeeStatusId varchar(75) null,
	employeeNumber varchar(75) null,
	jobTitle varchar(100) null,
	jobClass varchar(75) null,
	hoursOfOperation varchar(75) null
);

create table Counter (
	name varchar(75) not null primary key,
	currentId bigint
);

create table Country (
	mvccVersion bigint default 0,
	countryId bigint not null primary key,
	name varchar(75) null,
	a2 varchar(75) null,
	a3 varchar(75) null,
	number_ varchar(75) null,
	idd_ varchar(75) null,
	zipRequired bit,
	active_ bit
);

create table CyrusUser (
	userId varchar(75) not null primary key,
	password_ varchar(75) not null
);

create table CyrusVirtual (
	emailAddress varchar(75) not null primary key,
	userId varchar(75) not null
);

create table DDLRecord (
	uuid_ varchar(75) null,
	recordId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	versionUserId bigint,
	versionUserName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	DDMStorageId bigint,
	recordSetId bigint,
	version varchar(75) null,
	displayIndex int
);

create table DDLRecordSet (
	uuid_ varchar(75) null,
	recordSetId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	DDMStructureId bigint,
	recordSetKey varchar(75) null,
	name longvarchar null,
	description longvarchar null,
	minDisplayRows int,
	scope int
);

create table DDLRecordVersion (
	recordVersionId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	DDMStorageId bigint,
	recordSetId bigint,
	recordId bigint,
	version varchar(75) null,
	displayIndex int,
	status int,
	statusByUserId bigint,
	statusByUserName varchar(75) null,
	statusDate timestamp null
);

create table DDMContent (
	uuid_ varchar(75) null,
	contentId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	name longvarchar null,
	description longvarchar null,
	data_ longvarchar null
);

create table DDMStorageLink (
	uuid_ varchar(75) null,
	storageLinkId bigint not null primary key,
	classNameId bigint,
	classPK bigint,
	structureId bigint
);

create table DDMStructure (
	uuid_ varchar(75) null,
	structureId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	parentStructureId bigint,
	classNameId bigint,
	structureKey varchar(75) null,
	version varchar(75) null,
	name longvarchar null,
	description longvarchar null,
	definition longvarchar null,
	storageType varchar(75) null,
	type_ int
);

create table DDMStructureLink (
	structureLinkId bigint not null primary key,
	classNameId bigint,
	classPK bigint,
	structureId bigint
);

create table DDMStructureVersion (
	structureVersionId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	structureId bigint,
	version varchar(75) null,
	name longvarchar null,
	description longvarchar null,
	definition longvarchar null,
	storageType varchar(75) null,
	type_ int
);

create table DDMTemplate (
	uuid_ varchar(75) null,
	templateId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	classNameId bigint,
	classPK bigint,
	templateKey varchar(75) null,
	name longvarchar null,
	description longvarchar null,
	type_ varchar(75) null,
	mode_ varchar(75) null,
	language varchar(75) null,
	script longvarchar null,
	cacheable bit,
	smallImage bit,
	smallImageId bigint,
	smallImageURL varchar(75) null
);

create table DLContent (
	contentId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	repositoryId bigint,
	path_ varchar(255) null,
	version varchar(75) null,
	data_ blob,
	size_ bigint
);

create table DLFileEntry (
	uuid_ varchar(75) null,
	fileEntryId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	classNameId bigint,
	classPK bigint,
	repositoryId bigint,
	folderId bigint,
	treePath longvarchar null,
	name varchar(255) null,
	fileName varchar(255) null,
	extension varchar(75) null,
	mimeType varchar(75) null,
	title varchar(255) null,
	description longvarchar null,
	extraSettings longvarchar null,
	fileEntryTypeId bigint,
	version varchar(75) null,
	size_ bigint,
	readCount int,
	smallImageId bigint,
	largeImageId bigint,
	custom1ImageId bigint,
	custom2ImageId bigint,
	manualCheckInRequired bit
);

create table DLFileEntryMetadata (
	uuid_ varchar(75) null,
	fileEntryMetadataId bigint not null primary key,
	DDMStorageId bigint,
	DDMStructureId bigint,
	fileEntryTypeId bigint,
	fileEntryId bigint,
	fileVersionId bigint
);

create table DLFileEntryType (
	uuid_ varchar(75) null,
	fileEntryTypeId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	fileEntryTypeKey varchar(75) null,
	name longvarchar null,
	description longvarchar null
);

create table DLFileEntryTypes_DDMStructures (
	structureId bigint not null,
	fileEntryTypeId bigint not null,
	primary key (structureId, fileEntryTypeId)
);

create table DLFileEntryTypes_DLFolders (
	fileEntryTypeId bigint not null,
	folderId bigint not null,
	primary key (fileEntryTypeId, folderId)
);

create table DLFileRank (
	fileRankId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	createDate timestamp null,
	fileEntryId bigint,
	active_ bit
);

create table DLFileShortcut (
	uuid_ varchar(75) null,
	fileShortcutId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	repositoryId bigint,
	folderId bigint,
	toFileEntryId bigint,
	treePath longvarchar null,
	active_ bit,
	status int,
	statusByUserId bigint,
	statusByUserName varchar(75) null,
	statusDate timestamp null
);

create table DLFileVersion (
	uuid_ varchar(75) null,
	fileVersionId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	repositoryId bigint,
	folderId bigint,
	fileEntryId bigint,
	treePath longvarchar null,
	fileName varchar(255) null,
	extension varchar(75) null,
	mimeType varchar(75) null,
	title varchar(255) null,
	description longvarchar null,
	changeLog varchar(75) null,
	extraSettings longvarchar null,
	fileEntryTypeId bigint,
	version varchar(75) null,
	size_ bigint,
	checksum varchar(75) null,
	status int,
	statusByUserId bigint,
	statusByUserName varchar(75) null,
	statusDate timestamp null
);

create table DLFolder (
	uuid_ varchar(75) null,
	folderId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	repositoryId bigint,
	mountPoint bit,
	parentFolderId bigint,
	treePath longvarchar null,
	name varchar(100) null,
	description longvarchar null,
	lastPostDate timestamp null,
	defaultFileEntryTypeId bigint,
	hidden_ bit,
	restrictionType int,
	status int,
	statusByUserId bigint,
	statusByUserName varchar(75) null,
	statusDate timestamp null
);

create table DLSyncEvent (
	syncEventId bigint not null primary key,
	modifiedTime bigint,
	event varchar(75) null,
	type_ varchar(75) null,
	typePK bigint
);

create table EmailAddress (
	mvccVersion bigint default 0,
	uuid_ varchar(75) null,
	emailAddressId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	classNameId bigint,
	classPK bigint,
	address varchar(75) null,
	typeId int,
	primary_ bit
);

create table ExpandoColumn (
	columnId bigint not null primary key,
	companyId bigint,
	tableId bigint,
	name varchar(75) null,
	type_ int,
	defaultData longvarchar null,
	typeSettings longvarchar null
);

create table ExpandoRow (
	rowId_ bigint not null primary key,
	companyId bigint,
	modifiedDate timestamp null,
	tableId bigint,
	classPK bigint
);

create table ExpandoTable (
	tableId bigint not null primary key,
	companyId bigint,
	classNameId bigint,
	name varchar(75) null
);

create table ExpandoValue (
	valueId bigint not null primary key,
	companyId bigint,
	tableId bigint,
	columnId bigint,
	rowId_ bigint,
	classNameId bigint,
	classPK bigint,
	data_ longvarchar null
);

create table ExportImportConfiguration (
	mvccVersion bigint default 0,
	exportImportConfigurationId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	name varchar(75) null,
	description longvarchar null,
	type_ int,
	settings_ longvarchar null,
	status int,
	statusByUserId bigint,
	statusByUserName varchar(75) null,
	statusDate timestamp null
);

create table Group_ (
	mvccVersion bigint default 0,
	uuid_ varchar(75) null,
	groupId bigint not null primary key,
	companyId bigint,
	creatorUserId bigint,
	classNameId bigint,
	classPK bigint,
	parentGroupId bigint,
	liveGroupId bigint,
	treePath longvarchar null,
	name varchar(150) null,
	description longvarchar null,
	type_ int,
	typeSettings longvarchar null,
	manualMembership bit,
	membershipRestriction int,
	friendlyURL varchar(255) null,
	site bit,
	remoteStagingGroupCount int,
	active_ bit
);

create table Groups_Orgs (
	groupId bigint not null,
	organizationId bigint not null,
	primary key (groupId, organizationId)
);

create table Groups_Roles (
	groupId bigint not null,
	roleId bigint not null,
	primary key (groupId, roleId)
);

create table Groups_UserGroups (
	groupId bigint not null,
	userGroupId bigint not null,
	primary key (groupId, userGroupId)
);

create table Image (
	mvccVersion bigint default 0,
	imageId bigint not null primary key,
	modifiedDate timestamp null,
	type_ varchar(75) null,
	height int,
	width int,
	size_ int
);

create table JournalArticle (
	uuid_ varchar(75) null,
	id_ bigint not null primary key,
	resourcePrimKey bigint,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	folderId bigint,
	classNameId bigint,
	classPK bigint,
	treePath longvarchar null,
	articleId varchar(75) null,
	version double,
	title longvarchar null,
	urlTitle varchar(150) null,
	description longvarchar null,
	content longvarchar null,
	DDMStructureKey varchar(75) null,
	DDMTemplateKey varchar(75) null,
	layoutUuid varchar(75) null,
	displayDate timestamp null,
	expirationDate timestamp null,
	reviewDate timestamp null,
	indexable bit,
	smallImage bit,
	smallImageId bigint,
	smallImageURL longvarchar null,
	status int,
	statusByUserId bigint,
	statusByUserName varchar(75) null,
	statusDate timestamp null
);

create table JournalArticleImage (
	articleImageId bigint not null primary key,
	groupId bigint,
	articleId varchar(75) null,
	version double,
	elInstanceId varchar(75) null,
	elName varchar(75) null,
	languageId varchar(75) null,
	tempImage bit
);

create table JournalArticleResource (
	uuid_ varchar(75) null,
	resourcePrimKey bigint not null primary key,
	groupId bigint,
	articleId varchar(75) null
);

create table JournalContentSearch (
	contentSearchId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	privateLayout bit,
	layoutId bigint,
	portletId varchar(200) null,
	articleId varchar(75) null
);

create table JournalFeed (
	uuid_ varchar(75) null,
	id_ bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	feedId varchar(75) null,
	name varchar(75) null,
	description longvarchar null,
	DDMStructureKey varchar(75) null,
	DDMTemplateKey varchar(75) null,
	DDMRendererTemplateKey varchar(75) null,
	delta int,
	orderByCol varchar(75) null,
	orderByType varchar(75) null,
	targetLayoutFriendlyUrl varchar(255) null,
	targetPortletId varchar(75) null,
	contentField varchar(75) null,
	feedFormat varchar(75) null,
	feedVersion double
);

create table JournalFolder (
	uuid_ varchar(75) null,
	folderId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	parentFolderId bigint,
	treePath longvarchar null,
	name varchar(100) null,
	description longvarchar null,
	restrictionType int,
	status int,
	statusByUserId bigint,
	statusByUserName varchar(75) null,
	statusDate timestamp null
);

create table JournalFolders_DDMStructures (
	structureId bigint not null,
	folderId bigint not null,
	primary key (structureId, folderId)
);

create table Layout (
	mvccVersion bigint default 0,
	uuid_ varchar(75) null,
	plid bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	privateLayout bit,
	layoutId bigint,
	parentLayoutId bigint,
	name longvarchar null,
	title longvarchar null,
	description longvarchar null,
	keywords longvarchar null,
	robots longvarchar null,
	type_ varchar(75) null,
	typeSettings longvarchar null,
	hidden_ bit,
	friendlyURL varchar(255) null,
	iconImageId bigint,
	themeId varchar(75) null,
	colorSchemeId varchar(75) null,
	wapThemeId varchar(75) null,
	wapColorSchemeId varchar(75) null,
	css longvarchar null,
	priority int,
	layoutPrototypeUuid varchar(75) null,
	layoutPrototypeLinkEnabled bit,
	sourcePrototypeLayoutUuid varchar(75) null
);

create table LayoutBranch (
	mvccVersion bigint default 0,
	LayoutBranchId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	layoutSetBranchId bigint,
	plid bigint,
	name varchar(75) null,
	description longvarchar null,
	master bit
);

create table LayoutFriendlyURL (
	mvccVersion bigint default 0,
	uuid_ varchar(75) null,
	layoutFriendlyURLId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	plid bigint,
	privateLayout bit,
	friendlyURL varchar(255) null,
	languageId varchar(75) null
);

create table LayoutPrototype (
	mvccVersion bigint default 0,
	uuid_ varchar(75) null,
	layoutPrototypeId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	name longvarchar null,
	description longvarchar null,
	settings_ longvarchar null,
	active_ bit
);

create table LayoutRevision (
	mvccVersion bigint default 0,
	layoutRevisionId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	layoutSetBranchId bigint,
	layoutBranchId bigint,
	parentLayoutRevisionId bigint,
	head bit,
	major bit,
	plid bigint,
	privateLayout bit,
	name longvarchar null,
	title longvarchar null,
	description longvarchar null,
	keywords longvarchar null,
	robots longvarchar null,
	typeSettings longvarchar null,
	iconImageId bigint,
	themeId varchar(75) null,
	colorSchemeId varchar(75) null,
	wapThemeId varchar(75) null,
	wapColorSchemeId varchar(75) null,
	css longvarchar null,
	status int,
	statusByUserId bigint,
	statusByUserName varchar(75) null,
	statusDate timestamp null
);

create table LayoutSet (
	mvccVersion bigint default 0,
	layoutSetId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	createDate timestamp null,
	modifiedDate timestamp null,
	privateLayout bit,
	logoId bigint,
	themeId varchar(75) null,
	colorSchemeId varchar(75) null,
	wapThemeId varchar(75) null,
	wapColorSchemeId varchar(75) null,
	css longvarchar null,
	pageCount int,
	settings_ longvarchar null,
	layoutSetPrototypeUuid varchar(75) null,
	layoutSetPrototypeLinkEnabled bit
);

create table LayoutSetBranch (
	mvccVersion bigint default 0,
	layoutSetBranchId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	privateLayout bit,
	name varchar(75) null,
	description longvarchar null,
	master bit,
	logoId bigint,
	themeId varchar(75) null,
	colorSchemeId varchar(75) null,
	wapThemeId varchar(75) null,
	wapColorSchemeId varchar(75) null,
	css longvarchar null,
	settings_ longvarchar null,
	layoutSetPrototypeUuid varchar(75) null,
	layoutSetPrototypeLinkEnabled bit
);

create table LayoutSetPrototype (
	mvccVersion bigint default 0,
	uuid_ varchar(75) null,
	layoutSetPrototypeId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	name longvarchar null,
	description longvarchar null,
	settings_ longvarchar null,
	active_ bit
);

create table ListType (
	mvccVersion bigint default 0,
	listTypeId int not null primary key,
	name varchar(75) null,
	type_ varchar(75) null
);

create table Lock_ (
	mvccVersion bigint default 0,
	uuid_ varchar(75) null,
	lockId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	className varchar(75) null,
	key_ varchar(200) null,
	owner varchar(1024) null,
	inheritable bit,
	expirationDate timestamp null
);

create table MBBan (
	uuid_ varchar(75) null,
	banId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	banUserId bigint
);

create table MBCategory (
	uuid_ varchar(75) null,
	categoryId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	parentCategoryId bigint,
	name varchar(75) null,
	description longvarchar null,
	displayStyle varchar(75) null,
	threadCount int,
	messageCount int,
	lastPostDate timestamp null,
	status int,
	statusByUserId bigint,
	statusByUserName varchar(75) null,
	statusDate timestamp null
);

create table MBDiscussion (
	uuid_ varchar(75) null,
	discussionId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	classNameId bigint,
	classPK bigint,
	threadId bigint
);

create table MBMailingList (
	uuid_ varchar(75) null,
	mailingListId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	categoryId bigint,
	emailAddress varchar(75) null,
	inProtocol varchar(75) null,
	inServerName varchar(75) null,
	inServerPort int,
	inUseSSL bit,
	inUserName varchar(75) null,
	inPassword varchar(75) null,
	inReadInterval int,
	outEmailAddress varchar(75) null,
	outCustom bit,
	outServerName varchar(75) null,
	outServerPort int,
	outUseSSL bit,
	outUserName varchar(75) null,
	outPassword varchar(75) null,
	allowAnonymous bit,
	active_ bit
);

create table MBMessage (
	uuid_ varchar(75) null,
	messageId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	classNameId bigint,
	classPK bigint,
	categoryId bigint,
	threadId bigint,
	rootMessageId bigint,
	parentMessageId bigint,
	subject varchar(75) null,
	body longvarchar null,
	format varchar(75) null,
	anonymous bit,
	priority double,
	allowPingbacks bit,
	answer bit,
	status int,
	statusByUserId bigint,
	statusByUserName varchar(75) null,
	statusDate timestamp null
);

create table MBStatsUser (
	statsUserId bigint not null primary key,
	groupId bigint,
	userId bigint,
	messageCount int,
	lastPostDate timestamp null
);

create table MBThread (
	uuid_ varchar(75) null,
	threadId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	categoryId bigint,
	rootMessageId bigint,
	rootMessageUserId bigint,
	messageCount int,
	viewCount int,
	lastPostByUserId bigint,
	lastPostDate timestamp null,
	priority double,
	question bit,
	status int,
	statusByUserId bigint,
	statusByUserName varchar(75) null,
	statusDate timestamp null
);

create table MBThreadFlag (
	uuid_ varchar(75) null,
	threadFlagId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	threadId bigint
);

create table MDRAction (
	uuid_ varchar(75) null,
	actionId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	classNameId bigint,
	classPK bigint,
	ruleGroupInstanceId bigint,
	name longvarchar null,
	description longvarchar null,
	type_ varchar(255) null,
	typeSettings longvarchar null
);

create table MDRRule (
	uuid_ varchar(75) null,
	ruleId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	ruleGroupId bigint,
	name longvarchar null,
	description longvarchar null,
	type_ varchar(255) null,
	typeSettings longvarchar null
);

create table MDRRuleGroup (
	uuid_ varchar(75) null,
	ruleGroupId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	name longvarchar null,
	description longvarchar null
);

create table MDRRuleGroupInstance (
	uuid_ varchar(75) null,
	ruleGroupInstanceId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	classNameId bigint,
	classPK bigint,
	ruleGroupId bigint,
	priority int
);

create table MembershipRequest (
	mvccVersion bigint default 0,
	membershipRequestId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	createDate timestamp null,
	comments longvarchar null,
	replyComments longvarchar null,
	replyDate timestamp null,
	replierUserId bigint,
	statusId int
);

create table Organization_ (
	mvccVersion bigint default 0,
	uuid_ varchar(75) null,
	organizationId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	parentOrganizationId bigint,
	treePath longvarchar null,
	name varchar(100) null,
	type_ varchar(75) null,
	recursable bit,
	regionId bigint,
	countryId bigint,
	statusId int,
	comments longvarchar null,
	logoId bigint
);

create table OrgGroupRole (
	mvccVersion bigint default 0,
	organizationId bigint not null,
	groupId bigint not null,
	roleId bigint not null,
	primary key (organizationId, groupId, roleId)
);

create table OrgLabor (
	mvccVersion bigint default 0,
	orgLaborId bigint not null primary key,
	organizationId bigint,
	typeId int,
	sunOpen int,
	sunClose int,
	monOpen int,
	monClose int,
	tueOpen int,
	tueClose int,
	wedOpen int,
	wedClose int,
	thuOpen int,
	thuClose int,
	friOpen int,
	friClose int,
	satOpen int,
	satClose int
);

create table PasswordPolicy (
	mvccVersion bigint default 0,
	uuid_ varchar(75) null,
	passwordPolicyId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	defaultPolicy bit,
	name varchar(75) null,
	description longvarchar null,
	changeable bit,
	changeRequired bit,
	minAge bigint,
	checkSyntax bit,
	allowDictionaryWords bit,
	minAlphanumeric int,
	minLength int,
	minLowerCase int,
	minNumbers int,
	minSymbols int,
	minUpperCase int,
	regex varchar(75) null,
	history bit,
	historyCount int,
	expireable bit,
	maxAge bigint,
	warningTime bigint,
	graceLimit int,
	lockout bit,
	maxFailure int,
	lockoutDuration bigint,
	requireUnlock bit,
	resetFailureCount bigint,
	resetTicketMaxAge bigint
);

create table PasswordPolicyRel (
	mvccVersion bigint default 0,
	passwordPolicyRelId bigint not null primary key,
	passwordPolicyId bigint,
	classNameId bigint,
	classPK bigint
);

create table PasswordTracker (
	mvccVersion bigint default 0,
	passwordTrackerId bigint not null primary key,
	userId bigint,
	createDate timestamp null,
	password_ varchar(75) null
);

create table Phone (
	mvccVersion bigint default 0,
	uuid_ varchar(75) null,
	phoneId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	classNameId bigint,
	classPK bigint,
	number_ varchar(75) null,
	extension varchar(75) null,
	typeId int,
	primary_ bit
);

create table PluginSetting (
	mvccVersion bigint default 0,
	pluginSettingId bigint not null primary key,
	companyId bigint,
	pluginId varchar(75) null,
	pluginType varchar(75) null,
	roles longvarchar null,
	active_ bit
);

create table PortalPreferences (
	mvccVersion bigint default 0,
	portalPreferencesId bigint not null primary key,
	ownerId bigint,
	ownerType int,
	preferences longvarchar null
);

create table Portlet (
	mvccVersion bigint default 0,
	id_ bigint not null primary key,
	companyId bigint,
	portletId varchar(200) null,
	roles longvarchar null,
	active_ bit
);

create table PortletItem (
	mvccVersion bigint default 0,
	portletItemId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	name varchar(75) null,
	portletId varchar(200) null,
	classNameId bigint
);

create table PortletPreferences (
	mvccVersion bigint default 0,
	portletPreferencesId bigint not null primary key,
	ownerId bigint,
	ownerType int,
	plid bigint,
	portletId varchar(200) null,
	preferences longvarchar null
);

create table RatingsEntry (
	uuid_ varchar(75) null,
	entryId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	classNameId bigint,
	classPK bigint,
	score double
);

create table RatingsStats (
	statsId bigint not null primary key,
	classNameId bigint,
	classPK bigint,
	totalEntries int,
	totalScore double,
	averageScore double
);

create table Region (
	mvccVersion bigint default 0,
	regionId bigint not null primary key,
	countryId bigint,
	regionCode varchar(75) null,
	name varchar(75) null,
	active_ bit
);

create table Release_ (
	mvccVersion bigint default 0,
	releaseId bigint not null primary key,
	createDate timestamp null,
	modifiedDate timestamp null,
	servletContextName varchar(75) null,
	buildNumber int,
	buildDate timestamp null,
	verified bit,
	state_ int,
	testString varchar(1024) null
);

create table Repository (
	mvccVersion bigint default 0,
	uuid_ varchar(75) null,
	repositoryId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	classNameId bigint,
	name varchar(75) null,
	description longvarchar null,
	portletId varchar(200) null,
	typeSettings longvarchar null,
	dlFolderId bigint
);

create table RepositoryEntry (
	mvccVersion bigint default 0,
	uuid_ varchar(75) null,
	repositoryEntryId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	repositoryId bigint,
	mappedId varchar(255) null,
	manualCheckInRequired bit
);

create table ResourceAction (
	mvccVersion bigint default 0,
	resourceActionId bigint not null primary key,
	name varchar(255) null,
	actionId varchar(75) null,
	bitwiseValue bigint
);

create table ResourceBlock (
	mvccVersion bigint default 0,
	resourceBlockId bigint not null primary key,
	companyId bigint,
	groupId bigint,
	name varchar(75) null,
	permissionsHash varchar(75) null,
	referenceCount bigint
);

create table ResourceBlockPermission (
	mvccVersion bigint default 0,
	resourceBlockPermissionId bigint not null primary key,
	resourceBlockId bigint,
	roleId bigint,
	actionIds bigint
);

create table ResourcePermission (
	mvccVersion bigint default 0,
	resourcePermissionId bigint not null primary key,
	companyId bigint,
	name varchar(255) null,
	scope int,
	primKey varchar(255) null,
	roleId bigint,
	ownerId bigint,
	actionIds bigint
);

create table ResourceTypePermission (
	mvccVersion bigint default 0,
	resourceTypePermissionId bigint not null primary key,
	companyId bigint,
	groupId bigint,
	name varchar(75) null,
	roleId bigint,
	actionIds bigint
);

create table Role_ (
	mvccVersion bigint default 0,
	uuid_ varchar(75) null,
	roleId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	classNameId bigint,
	classPK bigint,
	name varchar(75) null,
	title longvarchar null,
	description longvarchar null,
	type_ int,
	subtype varchar(75) null
);

create table SCFrameworkVersi_SCProductVers (
	frameworkVersionId bigint not null,
	productVersionId bigint not null,
	primary key (frameworkVersionId, productVersionId)
);

create table SCFrameworkVersion (
	frameworkVersionId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	name varchar(75) null,
	url longvarchar null,
	active_ bit,
	priority int
);

create table SCLicense (
	licenseId bigint not null primary key,
	name varchar(75) null,
	url longvarchar null,
	openSource bit,
	active_ bit,
	recommended bit
);

create table SCLicenses_SCProductEntries (
	licenseId bigint not null,
	productEntryId bigint not null,
	primary key (licenseId, productEntryId)
);

create table SCProductEntry (
	productEntryId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	name varchar(75) null,
	type_ varchar(75) null,
	tags varchar(255) null,
	shortDescription longvarchar null,
	longDescription longvarchar null,
	pageURL longvarchar null,
	author varchar(75) null,
	repoGroupId varchar(75) null,
	repoArtifactId varchar(75) null
);

create table SCProductScreenshot (
	productScreenshotId bigint not null primary key,
	companyId bigint,
	groupId bigint,
	productEntryId bigint,
	thumbnailId bigint,
	fullImageId bigint,
	priority int
);

create table SCProductVersion (
	productVersionId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	productEntryId bigint,
	version varchar(75) null,
	changeLog longvarchar null,
	downloadPageURL longvarchar null,
	directDownloadURL varchar(2000) null,
	repoStoreArtifact bit
);

create table ServiceComponent (
	mvccVersion bigint default 0,
	serviceComponentId bigint not null primary key,
	buildNamespace varchar(75) null,
	buildNumber bigint,
	buildDate bigint,
	data_ longvarchar null
);

create table Shard (
	mvccVersion bigint default 0,
	shardId bigint not null primary key,
	classNameId bigint,
	classPK bigint,
	name varchar(75) null
);

create table ShoppingCart (
	cartId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	itemIds longvarchar null,
	couponCodes varchar(75) null,
	altShipping int,
	insure bit
);

create table ShoppingCategory (
	categoryId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	parentCategoryId bigint,
	name varchar(75) null,
	description longvarchar null
);

create table ShoppingCoupon (
	couponId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	code_ varchar(75) null,
	name varchar(75) null,
	description longvarchar null,
	startDate timestamp null,
	endDate timestamp null,
	active_ bit,
	limitCategories longvarchar null,
	limitSkus longvarchar null,
	minOrder double,
	discount double,
	discountType varchar(75) null
);

create table ShoppingItem (
	itemId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	categoryId bigint,
	sku varchar(75) null,
	name varchar(200) null,
	description longvarchar null,
	properties longvarchar null,
	fields_ bit,
	fieldsQuantities longvarchar null,
	minQuantity int,
	maxQuantity int,
	price double,
	discount double,
	taxable bit,
	shipping double,
	useShippingFormula bit,
	requiresShipping bit,
	stockQuantity int,
	featured_ bit,
	sale_ bit,
	smallImage bit,
	smallImageId bigint,
	smallImageURL longvarchar null,
	mediumImage bit,
	mediumImageId bigint,
	mediumImageURL longvarchar null,
	largeImage bit,
	largeImageId bigint,
	largeImageURL longvarchar null
);

create table ShoppingItemField (
	itemFieldId bigint not null primary key,
	itemId bigint,
	name varchar(75) null,
	values_ longvarchar null,
	description longvarchar null
);

create table ShoppingItemPrice (
	itemPriceId bigint not null primary key,
	itemId bigint,
	minQuantity int,
	maxQuantity int,
	price double,
	discount double,
	taxable bit,
	shipping double,
	useShippingFormula bit,
	status int
);

create table ShoppingOrder (
	orderId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	number_ varchar(75) null,
	tax double,
	shipping double,
	altShipping varchar(75) null,
	requiresShipping bit,
	insure bit,
	insurance double,
	couponCodes varchar(75) null,
	couponDiscount double,
	billingFirstName varchar(75) null,
	billingLastName varchar(75) null,
	billingEmailAddress varchar(75) null,
	billingCompany varchar(75) null,
	billingStreet varchar(75) null,
	billingCity varchar(75) null,
	billingState varchar(75) null,
	billingZip varchar(75) null,
	billingCountry varchar(75) null,
	billingPhone varchar(75) null,
	shipToBilling bit,
	shippingFirstName varchar(75) null,
	shippingLastName varchar(75) null,
	shippingEmailAddress varchar(75) null,
	shippingCompany varchar(75) null,
	shippingStreet varchar(75) null,
	shippingCity varchar(75) null,
	shippingState varchar(75) null,
	shippingZip varchar(75) null,
	shippingCountry varchar(75) null,
	shippingPhone varchar(75) null,
	ccName varchar(75) null,
	ccType varchar(75) null,
	ccNumber varchar(75) null,
	ccExpMonth int,
	ccExpYear int,
	ccVerNumber varchar(75) null,
	comments longvarchar null,
	ppTxnId varchar(75) null,
	ppPaymentStatus varchar(75) null,
	ppPaymentGross double,
	ppReceiverEmail varchar(75) null,
	ppPayerEmail varchar(75) null,
	sendOrderEmail bit,
	sendShippingEmail bit
);

create table ShoppingOrderItem (
	orderItemId bigint not null primary key,
	orderId bigint,
	itemId longvarchar null,
	sku varchar(75) null,
	name varchar(200) null,
	description longvarchar null,
	properties longvarchar null,
	price double,
	quantity int,
	shippedDate timestamp null
);

create table SocialActivity (
	activityId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	createDate bigint,
	activitySetId bigint,
	mirrorActivityId bigint,
	classNameId bigint,
	classPK bigint,
	parentClassNameId bigint,
	parentClassPK bigint,
	type_ int,
	extraData longvarchar null,
	receiverUserId bigint
);

create table SocialActivityAchievement (
	activityAchievementId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	createDate bigint,
	name varchar(75) null,
	firstInGroup bit
);

create table SocialActivityCounter (
	activityCounterId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	classNameId bigint,
	classPK bigint,
	name varchar(75) null,
	ownerType int,
	currentValue int,
	totalValue int,
	graceValue int,
	startPeriod int,
	endPeriod int,
	active_ bit
);

create table SocialActivityLimit (
	activityLimitId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	classNameId bigint,
	classPK bigint,
	activityType int,
	activityCounterName varchar(75) null,
	value varchar(75) null
);

create table SocialActivitySet (
	activitySetId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	createDate bigint,
	modifiedDate bigint,
	classNameId bigint,
	classPK bigint,
	type_ int,
	extraData longvarchar null,
	activityCount int
);

create table SocialActivitySetting (
	activitySettingId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	classNameId bigint,
	activityType int,
	name varchar(75) null,
	value varchar(1024) null
);

create table SocialRelation (
	uuid_ varchar(75) null,
	relationId bigint not null primary key,
	companyId bigint,
	createDate bigint,
	userId1 bigint,
	userId2 bigint,
	type_ int
);

create table SocialRequest (
	uuid_ varchar(75) null,
	requestId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	createDate bigint,
	modifiedDate bigint,
	classNameId bigint,
	classPK bigint,
	type_ int,
	extraData longvarchar null,
	receiverUserId bigint,
	status int
);

create table Subscription (
	mvccVersion bigint default 0,
	subscriptionId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	classNameId bigint,
	classPK bigint,
	frequency varchar(75) null
);

create table SystemEvent (
	mvccVersion bigint default 0,
	systemEventId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	classNameId bigint,
	classPK bigint,
	classUuid varchar(75) null,
	referrerClassNameId bigint,
	parentSystemEventId bigint,
	systemEventSetKey bigint,
	type_ int,
	extraData longvarchar null
);

create table Team (
	mvccVersion bigint default 0,
	teamId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	groupId bigint,
	name varchar(75) null,
	description longvarchar null
);

create table Ticket (
	mvccVersion bigint default 0,
	ticketId bigint not null primary key,
	companyId bigint,
	createDate timestamp null,
	classNameId bigint,
	classPK bigint,
	key_ varchar(75) null,
	type_ int,
	extraInfo longvarchar null,
	expirationDate timestamp null
);

create table TrashEntry (
	entryId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	classNameId bigint,
	classPK bigint,
	systemEventSetKey bigint,
	typeSettings longvarchar null,
	status int
);

create table TrashVersion (
	versionId bigint not null primary key,
	entryId bigint,
	classNameId bigint,
	classPK bigint,
	typeSettings longvarchar null,
	status int
);

create table UserNotificationDelivery (
	mvccVersion bigint default 0,
	userNotificationDeliveryId bigint not null primary key,
	companyId bigint,
	userId bigint,
	portletId varchar(200) null,
	classNameId bigint,
	notificationType int,
	deliveryType int,
	deliver bit
);

create table User_ (
	mvccVersion bigint default 0,
	uuid_ varchar(75) null,
	userId bigint not null primary key,
	companyId bigint,
	createDate timestamp null,
	modifiedDate timestamp null,
	defaultUser bit,
	contactId bigint,
	password_ varchar(75) null,
	passwordEncrypted bit,
	passwordReset bit,
	passwordModifiedDate timestamp null,
	digest varchar(255) null,
	reminderQueryQuestion varchar(75) null,
	reminderQueryAnswer varchar(75) null,
	graceLoginCount int,
	screenName varchar(75) null,
	emailAddress varchar(75) null,
	facebookId bigint,
	ldapServerId bigint,
	openId varchar(1024) null,
	portraitId bigint,
	languageId varchar(75) null,
	timeZoneId varchar(75) null,
	greeting varchar(255) null,
	comments longvarchar null,
	firstName varchar(75) null,
	middleName varchar(75) null,
	lastName varchar(75) null,
	jobTitle varchar(100) null,
	loginDate timestamp null,
	loginIP varchar(75) null,
	lastLoginDate timestamp null,
	lastLoginIP varchar(75) null,
	lastFailedLoginDate timestamp null,
	failedLoginAttempts int,
	lockout bit,
	lockoutDate timestamp null,
	agreedToTermsOfUse bit,
	emailAddressVerified bit,
	status int
);

create table UserGroup (
	mvccVersion bigint default 0,
	uuid_ varchar(75) null,
	userGroupId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	parentUserGroupId bigint,
	name varchar(75) null,
	description longvarchar null,
	addedByLDAPImport bit
);

create table UserGroupGroupRole (
	mvccVersion bigint default 0,
	userGroupId bigint not null,
	groupId bigint not null,
	roleId bigint not null,
	primary key (userGroupId, groupId, roleId)
);

create table UserGroupRole (
	mvccVersion bigint default 0,
	userId bigint not null,
	groupId bigint not null,
	roleId bigint not null,
	primary key (userId, groupId, roleId)
);

create table UserGroups_Teams (
	teamId bigint not null,
	userGroupId bigint not null,
	primary key (teamId, userGroupId)
);

create table UserIdMapper (
	mvccVersion bigint default 0,
	userIdMapperId bigint not null primary key,
	userId bigint,
	type_ varchar(75) null,
	description varchar(75) null,
	externalUserId varchar(75) null
);

create table UserNotificationEvent (
	mvccVersion bigint default 0,
	uuid_ varchar(75) null,
	userNotificationEventId bigint not null primary key,
	companyId bigint,
	userId bigint,
	type_ varchar(75) null,
	timestamp bigint,
	deliveryType int,
	deliverBy bigint,
	delivered bit,
	payload longvarchar null,
	actionRequired bit,
	archived bit
);

create table Users_Groups (
	groupId bigint not null,
	userId bigint not null,
	primary key (groupId, userId)
);

create table Users_Orgs (
	organizationId bigint not null,
	userId bigint not null,
	primary key (organizationId, userId)
);

create table Users_Roles (
	roleId bigint not null,
	userId bigint not null,
	primary key (roleId, userId)
);

create table Users_Teams (
	teamId bigint not null,
	userId bigint not null,
	primary key (teamId, userId)
);

create table Users_UserGroups (
	userId bigint not null,
	userGroupId bigint not null,
	primary key (userId, userGroupId)
);

create table UserTracker (
	mvccVersion bigint default 0,
	userTrackerId bigint not null primary key,
	companyId bigint,
	userId bigint,
	modifiedDate timestamp null,
	sessionId varchar(200) null,
	remoteAddr varchar(75) null,
	remoteHost varchar(75) null,
	userAgent varchar(200) null
);

create table UserTrackerPath (
	mvccVersion bigint default 0,
	userTrackerPathId bigint not null primary key,
	userTrackerId bigint,
	path_ longvarchar null,
	pathDate timestamp null
);

create table VirtualHost (
	mvccVersion bigint default 0,
	virtualHostId bigint not null primary key,
	companyId bigint,
	layoutSetId bigint,
	hostname varchar(75) null
);

create table WebDAVProps (
	mvccVersion bigint default 0,
	webDavPropsId bigint not null primary key,
	companyId bigint,
	createDate timestamp null,
	modifiedDate timestamp null,
	classNameId bigint,
	classPK bigint,
	props longvarchar null
);

create table Website (
	mvccVersion bigint default 0,
	uuid_ varchar(75) null,
	websiteId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	classNameId bigint,
	classPK bigint,
	url longvarchar null,
	typeId int,
	primary_ bit
);

create table WikiNode (
	uuid_ varchar(75) null,
	nodeId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	name varchar(75) null,
	description longvarchar null,
	lastPostDate timestamp null,
	status int,
	statusByUserId bigint,
	statusByUserName varchar(75) null,
	statusDate timestamp null
);

create table WikiPage (
	uuid_ varchar(75) null,
	pageId bigint not null primary key,
	resourcePrimKey bigint,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	nodeId bigint,
	title varchar(255) null,
	version double,
	minorEdit bit,
	content longvarchar null,
	summary longvarchar null,
	format varchar(75) null,
	head bit,
	parentTitle varchar(255) null,
	redirectTitle varchar(255) null,
	status int,
	statusByUserId bigint,
	statusByUserName varchar(75) null,
	statusDate timestamp null
);

create table WikiPageResource (
	uuid_ varchar(75) null,
	resourcePrimKey bigint not null primary key,
	nodeId bigint,
	title varchar(255) null
);

create table WorkflowDefinitionLink (
	mvccVersion bigint default 0,
	workflowDefinitionLinkId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	classNameId bigint,
	classPK bigint,
	typePK bigint,
	workflowDefinitionName varchar(75) null,
	workflowDefinitionVersion int
);

create table WorkflowInstanceLink (
	mvccVersion bigint default 0,
	workflowInstanceLinkId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null,
	classNameId bigint,
	classPK bigint,
	workflowInstanceId bigint
);


insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (1, 'canada', 'CA', 'CAN', '124', '001', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (2, 'china', 'CN', 'CHN', '156', '086', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (3, 'france', 'FR', 'FRA', '250', '033', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (4, 'germany', 'DE', 'DEU', '276', '049', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (5, 'hong-kong', 'HK', 'HKG', '344', '852', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (6, 'hungary', 'HU', 'HUN', '348', '036', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (7, 'israel', 'IL', 'ISR', '376', '972', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (8, 'italy', 'IT', 'ITA', '380', '039', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (9, 'japan', 'JP', 'JPN', '392', '081', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (10, 'south-korea', 'KR', 'KOR', '410', '082', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (11, 'netherlands', 'NL', 'NLD', '528', '031', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (12, 'portugal', 'PT', 'PRT', '620', '351', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (13, 'russia', 'RU', 'RUS', '643', '007', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (14, 'singapore', 'SG', 'SGP', '702', '065', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (15, 'spain', 'ES', 'ESP', '724', '034', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (16, 'turkey', 'TR', 'TUR', '792', '090', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (17, 'vietnam', 'VN', 'VNM', '704', '084', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (18, 'united-kingdom', 'GB', 'GBR', '826', '044', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (19, 'united-states', 'US', 'USA', '840', '001', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (20, 'afghanistan', 'AF', 'AFG', '4', '093', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (21, 'albania', 'AL', 'ALB', '8', '355', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (22, 'algeria', 'DZ', 'DZA', '12', '213', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (23, 'american-samoa', 'AS', 'ASM', '16', '684', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (24, 'andorra', 'AD', 'AND', '20', '376', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (25, 'angola', 'AO', 'AGO', '24', '244', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (26, 'anguilla', 'AI', 'AIA', '660', '264', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (27, 'antarctica', 'AQ', 'ATA', '10', '672', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (28, 'antigua-barbuda', 'AG', 'ATG', '28', '268', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (29, 'argentina', 'AR', 'ARG', '32', '054', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (30, 'armenia', 'AM', 'ARM', '51', '374', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (31, 'aruba', 'AW', 'ABW', '533', '297', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (32, 'australia', 'AU', 'AUS', '36', '061', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (33, 'austria', 'AT', 'AUT', '40', '043', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (34, 'azerbaijan', 'AZ', 'AZE', '31', '994', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (35, 'bahamas', 'BS', 'BHS', '44', '242', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (36, 'bahrain', 'BH', 'BHR', '48', '973', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (37, 'bangladesh', 'BD', 'BGD', '50', '880', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (38, 'barbados', 'BB', 'BRB', '52', '246', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (39, 'belarus', 'BY', 'BLR', '112', '375', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (40, 'belgium', 'BE', 'BEL', '56', '032', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (41, 'belize', 'BZ', 'BLZ', '84', '501', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (42, 'benin', 'BJ', 'BEN', '204', '229', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (43, 'bermuda', 'BM', 'BMU', '60', '441', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (44, 'bhutan', 'BT', 'BTN', '64', '975', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (45, 'bolivia', 'BO', 'BOL', '68', '591', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (46, 'bosnia-herzegovina', 'BA', 'BIH', '70', '387', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (47, 'botswana', 'BW', 'BWA', '72', '267', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (48, 'brazil', 'BR', 'BRA', '76', '055', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (49, 'british-virgin-islands', 'VG', 'VGB', '92', '284', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (50, 'brunei', 'BN', 'BRN', '96', '673', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (51, 'bulgaria', 'BG', 'BGR', '100', '359', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (52, 'burkina-faso', 'BF', 'BFA', '854', '226', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (53, 'burma-myanmar', 'MM', 'MMR', '104', '095', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (54, 'burundi', 'BI', 'BDI', '108', '257', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (55, 'cambodia', 'KH', 'KHM', '116', '855', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (56, 'cameroon', 'CM', 'CMR', '120', '237', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (57, 'cape-verde-island', 'CV', 'CPV', '132', '238', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (58, 'cayman-islands', 'KY', 'CYM', '136', '345', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (59, 'central-african-republic', 'CF', 'CAF', '140', '236', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (60, 'chad', 'TD', 'TCD', '148', '235', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (61, 'chile', 'CL', 'CHL', '152', '056', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (62, 'christmas-island', 'CX', 'CXR', '162', '061', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (63, 'cocos-islands', 'CC', 'CCK', '166', '061', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (64, 'colombia', 'CO', 'COL', '170', '057', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (65, 'comoros', 'KM', 'COM', '174', '269', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (66, 'republic-of-congo', 'CD', 'COD', '180', '242', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (67, 'democratic-republic-of-congo', 'CG', 'COG', '178', '243', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (68, 'cook-islands', 'CK', 'COK', '184', '682', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (69, 'costa-rica', 'CR', 'CRI', '188', '506', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (70, 'croatia', 'HR', 'HRV', '191', '385', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (71, 'cuba', 'CU', 'CUB', '192', '053', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (72, 'cyprus', 'CY', 'CYP', '196', '357', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (73, 'czech-republic', 'CZ', 'CZE', '203', '420', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (74, 'denmark', 'DK', 'DNK', '208', '045', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (75, 'djibouti', 'DJ', 'DJI', '262', '253', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (76, 'dominica', 'DM', 'DMA', '212', '767', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (77, 'dominican-republic', 'DO', 'DOM', '214', '809', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (78, 'ecuador', 'EC', 'ECU', '218', '593', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (79, 'egypt', 'EG', 'EGY', '818', '020', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (80, 'el-salvador', 'SV', 'SLV', '222', '503', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (81, 'equatorial-guinea', 'GQ', 'GNQ', '226', '240', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (82, 'eritrea', 'ER', 'ERI', '232', '291', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (83, 'estonia', 'EE', 'EST', '233', '372', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (84, 'ethiopia', 'ET', 'ETH', '231', '251', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (85, 'faeroe-islands', 'FO', 'FRO', '234', '298', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (86, 'falkland-islands', 'FK', 'FLK', '238', '500', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (87, 'fiji-islands', 'FJ', 'FJI', '242', '679', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (88, 'finland', 'FI', 'FIN', '246', '358', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (89, 'french-guiana', 'GF', 'GUF', '254', '594', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (90, 'french-polynesia', 'PF', 'PYF', '258', '689', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (91, 'gabon', 'GA', 'GAB', '266', '241', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (92, 'gambia', 'GM', 'GMB', '270', '220', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (93, 'georgia', 'GE', 'GEO', '268', '995', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (94, 'ghana', 'GH', 'GHA', '288', '233', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (95, 'gibraltar', 'GI', 'GIB', '292', '350', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (96, 'greece', 'GR', 'GRC', '300', '030', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (97, 'greenland', 'GL', 'GRL', '304', '299', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (98, 'grenada', 'GD', 'GRD', '308', '473', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (99, 'guadeloupe', 'GP', 'GLP', '312', '590', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (100, 'guam', 'GU', 'GUM', '316', '671', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (101, 'guatemala', 'GT', 'GTM', '320', '502', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (102, 'guinea', 'GN', 'GIN', '324', '224', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (103, 'guinea-bissau', 'GW', 'GNB', '624', '245', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (104, 'guyana', 'GY', 'GUY', '328', '592', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (105, 'haiti', 'HT', 'HTI', '332', '509', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (106, 'honduras', 'HN', 'HND', '340', '504', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (107, 'iceland', 'IS', 'ISL', '352', '354', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (108, 'india', 'IN', 'IND', '356', '091', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (109, 'indonesia', 'ID', 'IDN', '360', '062', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (110, 'iran', 'IR', 'IRN', '364', '098', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (111, 'iraq', 'IQ', 'IRQ', '368', '964', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (112, 'ireland', 'IE', 'IRL', '372', '353', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (113, 'ivory-coast', 'CI', 'CIV', '384', '225', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (114, 'jamaica', 'JM', 'JAM', '388', '876', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (115, 'jordan', 'JO', 'JOR', '400', '962', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (116, 'kazakhstan', 'KZ', 'KAZ', '398', '007', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (117, 'kenya', 'KE', 'KEN', '404', '254', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (118, 'kiribati', 'KI', 'KIR', '408', '686', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (119, 'kuwait', 'KW', 'KWT', '414', '965', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (120, 'north-korea', 'KP', 'PRK', '408', '850', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (121, 'kyrgyzstan', 'KG', 'KGZ', '471', '996', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (122, 'laos', 'LA', 'LAO', '418', '856', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (123, 'latvia', 'LV', 'LVA', '428', '371', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (124, 'lebanon', 'LB', 'LBN', '422', '961', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (125, 'lesotho', 'LS', 'LSO', '426', '266', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (126, 'liberia', 'LR', 'LBR', '430', '231', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (127, 'libya', 'LY', 'LBY', '434', '218', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (128, 'liechtenstein', 'LI', 'LIE', '438', '423', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (129, 'lithuania', 'LT', 'LTU', '440', '370', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (130, 'luxembourg', 'LU', 'LUX', '442', '352', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (131, 'macau', 'MO', 'MAC', '446', '853', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (132, 'macedonia', 'MK', 'MKD', '807', '389', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (133, 'madagascar', 'MG', 'MDG', '450', '261', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (134, 'malawi', 'MW', 'MWI', '454', '265', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (135, 'malaysia', 'MY', 'MYS', '458', '060', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (136, 'maldives', 'MV', 'MDV', '462', '960', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (137, 'mali', 'ML', 'MLI', '466', '223', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (138, 'malta', 'MT', 'MLT', '470', '356', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (139, 'marshall-islands', 'MH', 'MHL', '584', '692', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (140, 'martinique', 'MQ', 'MTQ', '474', '596', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (141, 'mauritania', 'MR', 'MRT', '478', '222', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (142, 'mauritius', 'MU', 'MUS', '480', '230', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (143, 'mayotte-island', 'YT', 'MYT', '175', '269', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (144, 'mexico', 'MX', 'MEX', '484', '052', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (145, 'micronesia', 'FM', 'FSM', '583', '691', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (146, 'moldova', 'MD', 'MDA', '498', '373', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (147, 'monaco', 'MC', 'MCO', '492', '377', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (148, 'mongolia', 'MN', 'MNG', '496', '976', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (149, 'montenegro', 'ME', 'MNE', '499', '382', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (150, 'montserrat', 'MS', 'MSR', '500', '664', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (151, 'morocco', 'MA', 'MAR', '504', '212', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (152, 'mozambique', 'MZ', 'MOZ', '508', '258', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (153, 'namibia', 'NA', 'NAM', '516', '264', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (154, 'nauru', 'NR', 'NRU', '520', '674', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (155, 'nepal', 'NP', 'NPL', '524', '977', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (156, 'netherlands-antilles', 'AN', 'ANT', '530', '599', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (157, 'new-caledonia', 'NC', 'NCL', '540', '687', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (158, 'new-zealand', 'NZ', 'NZL', '554', '064', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (159, 'nicaragua', 'NI', 'NIC', '558', '505', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (160, 'niger', 'NE', 'NER', '562', '227', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (161, 'nigeria', 'NG', 'NGA', '566', '234', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (162, 'niue', 'NU', 'NIU', '570', '683', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (163, 'norfolk-island', 'NF', 'NFK', '574', '672', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (164, 'norway', 'NO', 'NOR', '578', '047', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (165, 'oman', 'OM', 'OMN', '512', '968', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (166, 'pakistan', 'PK', 'PAK', '586', '092', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (167, 'palau', 'PW', 'PLW', '585', '680', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (168, 'palestine', 'PS', 'PSE', '275', '970', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (169, 'panama', 'PA', 'PAN', '591', '507', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (170, 'papua-new-guinea', 'PG', 'PNG', '598', '675', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (171, 'paraguay', 'PY', 'PRY', '600', '595', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (172, 'peru', 'PE', 'PER', '604', '051', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (173, 'philippines', 'PH', 'PHL', '608', '063', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (174, 'poland', 'PL', 'POL', '616', '048', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (175, 'puerto-rico', 'PR', 'PRI', '630', '787', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (176, 'qatar', 'QA', 'QAT', '634', '974', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (177, 'reunion-island', 'RE', 'REU', '638', '262', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (178, 'romania', 'RO', 'ROU', '642', '040', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (179, 'rwanda', 'RW', 'RWA', '646', '250', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (180, 'st-helena', 'SH', 'SHN', '654', '290', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (181, 'st-kitts', 'KN', 'KNA', '659', '869', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (182, 'st-lucia', 'LC', 'LCA', '662', '758', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (183, 'st-pierre-miquelon', 'PM', 'SPM', '666', '508', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (184, 'st-vincent', 'VC', 'VCT', '670', '784', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (185, 'san-marino', 'SM', 'SMR', '674', '378', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (186, 'sao-tome-principe', 'ST', 'STP', '678', '239', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (187, 'saudi-arabia', 'SA', 'SAU', '682', '966', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (188, 'senegal', 'SN', 'SEN', '686', '221', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (189, 'serbia', 'RS', 'SRB', '688', '381', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (190, 'seychelles', 'SC', 'SYC', '690', '248', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (191, 'sierra-leone', 'SL', 'SLE', '694', '249', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (192, 'slovakia', 'SK', 'SVK', '703', '421', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (193, 'slovenia', 'SI', 'SVN', '705', '386', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (194, 'solomon-islands', 'SB', 'SLB', '90', '677', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (195, 'somalia', 'SO', 'SOM', '706', '252', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (196, 'south-africa', 'ZA', 'ZAF', '710', '027', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (197, 'sri-lanka', 'LK', 'LKA', '144', '094', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (198, 'sudan', 'SD', 'SDN', '736', '095', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (199, 'suriname', 'SR', 'SUR', '740', '597', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (200, 'swaziland', 'SZ', 'SWZ', '748', '268', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (201, 'sweden', 'SE', 'SWE', '752', '046', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (202, 'switzerland', 'CH', 'CHE', '756', '041', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (203, 'syria', 'SY', 'SYR', '760', '963', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (204, 'taiwan', 'TW', 'TWN', '158', '886', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (205, 'tajikistan', 'TJ', 'TJK', '762', '992', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (206, 'tanzania', 'TZ', 'TZA', '834', '255', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (207, 'thailand', 'TH', 'THA', '764', '066', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (208, 'togo', 'TG', 'TGO', '768', '228', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (209, 'tonga', 'TO', 'TON', '776', '676', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (210, 'trinidad-tobago', 'TT', 'TTO', '780', '868', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (211, 'tunisia', 'TN', 'TUN', '788', '216', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (212, 'turkmenistan', 'TM', 'TKM', '795', '993', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (213, 'turks-caicos', 'TC', 'TCA', '796', '649', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (214, 'tuvalu', 'TV', 'TUV', '798', '688', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (215, 'uganda', 'UG', 'UGA', '800', '256', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (216, 'ukraine', 'UA', 'UKR', '804', '380', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (217, 'united-arab-emirates', 'AE', 'ARE', '784', '971', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (218, 'uruguay', 'UY', 'URY', '858', '598', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (219, 'uzbekistan', 'UZ', 'UZB', '860', '998', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (220, 'vanuatu', 'VU', 'VUT', '548', '678', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (221, 'vatican-city', 'VA', 'VAT', '336', '039', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (222, 'venezuela', 'VE', 'VEN', '862', '058', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (223, 'wallis-futuna', 'WF', 'WLF', '876', '681', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (224, 'western-samoa', 'WS', 'WSM', '882', '685', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (225, 'yemen', 'YE', 'YEM', '887', '967', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (226, 'zambia', 'ZM', 'ZMB', '894', '260', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (227, 'zimbabwe', 'ZW', 'ZWE', '716', '263', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (228, 'aland-islands', 'AX', 'ALA', '248', '359', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (229, 'bonaire-st-eustatius-saba', 'BQ', 'BES', '535', '599', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (230, 'bouvet-island', 'BV', 'BVT', '74', '047', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (231, 'british-indian-ocean-territory', 'IO', 'IOT', '86', '246', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (232, 'curacao', 'CW', 'CUW', '531', '599', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (233, 'french-southern-territories', 'TF', 'ATF', '260', '033', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (234, 'guernsey', 'GG', 'GGY', '831', '044', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (235, 'heard-island-mcdonald-islands', 'HM', 'HMD', '334', '061', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (236, 'isle-of-man', 'IM', 'IMN', '833', '044', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (237, 'jersey', 'JE', 'JEY', '832', '044', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (238, 'northern-mariana-islands', 'MP', 'MNP', '580', '670', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (239, 'pitcairn', 'PN', 'PCN', '612', '649', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (240, 'south-georgia-south-sandwich-islands', 'GS', 'SGS', '239', '044', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (241, 'south-sudan', 'SS', 'SSD', '728', '211', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (242, 'sint-maarten', 'SX', 'SXM', '534', '721', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (243, 'st-barthelemy', 'BL', 'BLM', '652', '590', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (244, 'st-martin', 'MF', 'MAF', '663', '590', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (245, 'tokelau', 'TK', 'TKL', '772', '690', false, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (246, 'timor-leste', 'TL', 'TLS', '626', '670', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (247, 'united-states-minor-outlying-islands', 'UM', 'UMI', '581', '699', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (248, 'united-states-virgin-islands', 'VI', 'VIR', '850', '340', true, true);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (249, 'western-sahara', 'EH', 'ESH', '732', '212', true, true);

insert into Region (regionId, countryId, regionCode, name, active_) values (1001, 1, 'AB', 'Alberta', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (1002, 1, 'BC', 'British Columbia', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (1003, 1, 'MB', 'Manitoba', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (1004, 1, 'NB', 'New Brunswick', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (1005, 1, 'NL', 'Newfoundland and Labrador', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (1006, 1, 'NT', 'Northwest Territories', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (1007, 1, 'NS', 'Nova Scotia', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (1008, 1, 'NU', 'Nunavut', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (1009, 1, 'ON', 'Ontario', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (1010, 1, 'PE', 'Prince Edward Island', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (1011, 1, 'QC', 'Quebec', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (1012, 1, 'SK', 'Saskatchewan', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (1013, 1, 'YT', 'Yukon', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (2001, 2, 'CN-34', 'Anhui', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (2002, 2, 'CN-92', 'Aomen', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (2003, 2, 'CN-11', 'Beijing', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (2004, 2, 'CN-50', 'Chongqing', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (2005, 2, 'CN-35', 'Fujian', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (2006, 2, 'CN-62', 'Gansu', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (2007, 2, 'CN-44', 'Guangdong', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (2008, 2, 'CN-45', 'Guangxi', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (2009, 2, 'CN-52', 'Guizhou', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (2010, 2, 'CN-46', 'Hainan', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (2011, 2, 'CN-13', 'Hebei', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (2012, 2, 'CN-23', 'Heilongjiang', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (2013, 2, 'CN-41', 'Henan', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (2014, 2, 'CN-42', 'Hubei', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (2015, 2, 'CN-43', 'Hunan', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (2016, 2, 'CN-32', 'Jiangsu', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (2017, 2, 'CN-36', 'Jiangxi', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (2018, 2, 'CN-22', 'Jilin', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (2019, 2, 'CN-21', 'Liaoning', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (2020, 2, 'CN-15', 'Nei Mongol', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (2021, 2, 'CN-64', 'Ningxia', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (2022, 2, 'CN-63', 'Qinghai', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (2023, 2, 'CN-61', 'Shaanxi', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (2024, 2, 'CN-37', 'Shandong', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (2025, 2, 'CN-31', 'Shanghai', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (2026, 2, 'CN-14', 'Shanxi', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (2027, 2, 'CN-51', 'Sichuan', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (2028, 2, 'CN-71', 'Taiwan', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (2029, 2, 'CN-12', 'Tianjin', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (2030, 2, 'CN-91', 'Xianggang', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (2031, 2, 'CN-65', 'Xinjiang', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (2032, 2, 'CN-54', 'Xizang', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (2033, 2, 'CN-53', 'Yunnan', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (2034, 2, 'CN-33', 'Zhejiang', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (3001, 3, 'A', 'Alsace', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (3002, 3, 'B', 'Aquitaine', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (3003, 3, 'C', 'Auvergne', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (3004, 3, 'P', 'Basse-Normandie', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (3005, 3, 'D', 'Bourgogne', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (3006, 3, 'E', 'Bretagne', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (3007, 3, 'F', 'Centre', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (3008, 3, 'G', 'Champagne-Ardenne', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (3009, 3, 'H', 'Corse', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (3010, 3, 'GF', 'Guyane', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (3011, 3, 'I', 'Franche Comté', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (3012, 3, 'GP', 'Guadeloupe', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (3013, 3, 'Q', 'Haute-Normandie', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (3014, 3, 'J', 'Île-de-France', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (3015, 3, 'K', 'Languedoc-Roussillon', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (3016, 3, 'L', 'Limousin', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (3017, 3, 'M', 'Lorraine', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (3018, 3, 'MQ', 'Martinique', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (3019, 3, 'N', 'Midi-Pyrénées', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (3020, 3, 'O', 'Nord Pas de Calais', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (3021, 3, 'R', 'Pays de la Loire', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (3022, 3, 'S', 'Picardie', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (3023, 3, 'T', 'Poitou-Charentes', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (3024, 3, 'U', 'Provence-Alpes-Côte-d''Azur', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (3025, 3, 'RE', 'Réunion', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (3026, 3, 'V', 'Rhône-Alpes', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (4001, 4, 'BW', 'Baden-Württemberg', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (4002, 4, 'BY', 'Bayern', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (4003, 4, 'BE', 'Berlin', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (4004, 4, 'BB', 'Brandenburg', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (4005, 4, 'HB', 'Bremen', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (4006, 4, 'HH', 'Hamburg', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (4007, 4, 'HE', 'Hessen', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (4008, 4, 'MV', 'Mecklenburg-Vorpommern', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (4009, 4, 'NI', 'Niedersachsen', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (4010, 4, 'NW', 'Nordrhein-Westfalen', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (4011, 4, 'RP', 'Rheinland-Pfalz', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (4012, 4, 'SL', 'Saarland', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (4013, 4, 'SN', 'Sachsen', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (4014, 4, 'ST', 'Sachsen-Anhalt', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (4015, 4, 'SH', 'Schleswig-Holstein', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (4016, 4, 'TH', 'Thüringen', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8001, 8, 'AG', 'Agrigento', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8002, 8, 'AL', 'Alessandria', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8003, 8, 'AN', 'Ancona', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8004, 8, 'AO', 'Aosta', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8005, 8, 'AR', 'Arezzo', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8006, 8, 'AP', 'Ascoli Piceno', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8007, 8, 'AT', 'Asti', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8008, 8, 'AV', 'Avellino', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8009, 8, 'BA', 'Bari', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8010, 8, 'BT', 'Barletta-Andria-Trani', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8011, 8, 'BL', 'Belluno', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8012, 8, 'BN', 'Benevento', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8013, 8, 'BG', 'Bergamo', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8014, 8, 'BI', 'Biella', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8015, 8, 'BO', 'Bologna', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8016, 8, 'BZ', 'Bolzano', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8017, 8, 'BS', 'Brescia', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8018, 8, 'BR', 'Brindisi', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8019, 8, 'CA', 'Cagliari', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8020, 8, 'CL', 'Caltanissetta', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8021, 8, 'CB', 'Campobasso', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8022, 8, 'CI', 'Carbonia-Iglesias', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8023, 8, 'CE', 'Caserta', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8024, 8, 'CT', 'Catania', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8025, 8, 'CZ', 'Catanzaro', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8026, 8, 'CH', 'Chieti', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8027, 8, 'CO', 'Como', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8028, 8, 'CS', 'Cosenza', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8029, 8, 'CR', 'Cremona', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8030, 8, 'KR', 'Crotone', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8031, 8, 'CN', 'Cuneo', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8032, 8, 'EN', 'Enna', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8033, 8, 'FM', 'Fermo', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8034, 8, 'FE', 'Ferrara', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8035, 8, 'FI', 'Firenze', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8036, 8, 'FG', 'Foggia', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8037, 8, 'FC', 'Forli-Cesena', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8038, 8, 'FR', 'Frosinone', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8039, 8, 'GE', 'Genova', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8040, 8, 'GO', 'Gorizia', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8041, 8, 'GR', 'Grosseto', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8042, 8, 'IM', 'Imperia', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8043, 8, 'IS', 'Isernia', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8044, 8, 'AQ', 'L''Aquila', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8045, 8, 'SP', 'La Spezia', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8046, 8, 'LT', 'Latina', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8047, 8, 'LE', 'Lecce', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8048, 8, 'LC', 'Lecco', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8049, 8, 'LI', 'Livorno', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8050, 8, 'LO', 'Lodi', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8051, 8, 'LU', 'Lucca', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8052, 8, 'MC', 'Macerata', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8053, 8, 'MN', 'Mantova', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8054, 8, 'MS', 'Massa-Carrara', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8055, 8, 'MT', 'Matera', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8056, 8, 'MA', 'Medio Campidano', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8057, 8, 'ME', 'Messina', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8058, 8, 'MI', 'Milano', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8059, 8, 'MO', 'Modena', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8060, 8, 'MB', 'Monza e Brianza', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8061, 8, 'NA', 'Napoli', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8062, 8, 'NO', 'Novara', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8063, 8, 'NU', 'Nuoro', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8064, 8, 'OG', 'Ogliastra', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8065, 8, 'OT', 'Olbia-Tempio', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8066, 8, 'OR', 'Oristano', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8067, 8, 'PD', 'Padova', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8068, 8, 'PA', 'Palermo', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8069, 8, 'PR', 'Parma', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8070, 8, 'PV', 'Pavia', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8071, 8, 'PG', 'Perugia', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8072, 8, 'PU', 'Pesaro e Urbino', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8073, 8, 'PE', 'Pescara', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8074, 8, 'PC', 'Piacenza', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8075, 8, 'PI', 'Pisa', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8076, 8, 'PT', 'Pistoia', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8077, 8, 'PN', 'Pordenone', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8078, 8, 'PZ', 'Potenza', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8079, 8, 'PO', 'Prato', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8080, 8, 'RG', 'Ragusa', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8081, 8, 'RA', 'Ravenna', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8082, 8, 'RC', 'Reggio Calabria', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8083, 8, 'RE', 'Reggio Emilia', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8084, 8, 'RI', 'Rieti', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8085, 8, 'RN', 'Rimini', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8086, 8, 'RM', 'Roma', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8087, 8, 'RO', 'Rovigo', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8088, 8, 'SA', 'Salerno', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8089, 8, 'SS', 'Sassari', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8090, 8, 'SV', 'Savona', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8091, 8, 'SI', 'Siena', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8092, 8, 'SR', 'Siracusa', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8093, 8, 'SO', 'Sondrio', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8094, 8, 'TA', 'Taranto', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8095, 8, 'TE', 'Teramo', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8096, 8, 'TR', 'Terni', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8097, 8, 'TO', 'Torino', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8098, 8, 'TP', 'Trapani', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8099, 8, 'TN', 'Trento', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8100, 8, 'TV', 'Treviso', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8101, 8, 'TS', 'Trieste', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8102, 8, 'UD', 'Udine', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8103, 8, 'VA', 'Varese', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8104, 8, 'VE', 'Venezia', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8105, 8, 'VB', 'Verbano-Cusio-Ossola', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8106, 8, 'VC', 'Vercelli', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8107, 8, 'VR', 'Verona', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8108, 8, 'VV', 'Vibo Valentia', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8109, 8, 'VI', 'Vicenza', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (8110, 8, 'VT', 'Viterbo', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (11001, 11, 'DR', 'Drenthe', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (11002, 11, 'FL', 'Flevoland', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (11003, 11, 'FR', 'Friesland', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (11004, 11, 'GE', 'Gelderland', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (11005, 11, 'GR', 'Groningen', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (11006, 11, 'LI', 'Limburg', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (11007, 11, 'NB', 'Noord-Brabant', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (11008, 11, 'NH', 'Noord-Holland', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (11009, 11, 'OV', 'Overijssel', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (11010, 11, 'UT', 'Utrecht', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (11011, 11, 'ZE', 'Zeeland', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (11012, 11, 'ZH', 'Zuid-Holland', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (15001, 15, 'AN', 'Andalusia', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (15002, 15, 'AR', 'Aragon', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (15003, 15, 'AS', 'Asturias', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (15004, 15, 'IB', 'Balearic Islands', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (15005, 15, 'PV', 'Basque Country', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (15006, 15, 'CN', 'Canary Islands', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (15007, 15, 'CB', 'Cantabria', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (15008, 15, 'CL', 'Castile and Leon', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (15009, 15, 'CM', 'Castile-La Mancha', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (15010, 15, 'CT', 'Catalonia', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (15011, 15, 'CE', 'Ceuta', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (15012, 15, 'EX', 'Extremadura', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (15013, 15, 'GA', 'Galicia', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (15014, 15, 'LO', 'La Rioja', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (15015, 15, 'M', 'Madrid', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (15016, 15, 'ML', 'Melilla', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (15017, 15, 'MU', 'Murcia', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (15018, 15, 'NA', 'Navarra', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (15019, 15, 'VC', 'Valencia', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19001, 19, 'AL', 'Alabama', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19002, 19, 'AK', 'Alaska', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19003, 19, 'AZ', 'Arizona', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19004, 19, 'AR', 'Arkansas', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19005, 19, 'CA', 'California', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19006, 19, 'CO', 'Colorado', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19007, 19, 'CT', 'Connecticut', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19008, 19, 'DC', 'District of Columbia', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19009, 19, 'DE', 'Delaware', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19010, 19, 'FL', 'Florida', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19011, 19, 'GA', 'Georgia', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19012, 19, 'HI', 'Hawaii', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19013, 19, 'ID', 'Idaho', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19014, 19, 'IL', 'Illinois', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19015, 19, 'IN', 'Indiana', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19016, 19, 'IA', 'Iowa', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19017, 19, 'KS', 'Kansas', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19018, 19, 'KY', 'Kentucky ', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19019, 19, 'LA', 'Louisiana ', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19020, 19, 'ME', 'Maine', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19021, 19, 'MD', 'Maryland', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19022, 19, 'MA', 'Massachusetts', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19023, 19, 'MI', 'Michigan', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19024, 19, 'MN', 'Minnesota', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19025, 19, 'MS', 'Mississippi', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19026, 19, 'MO', 'Missouri', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19027, 19, 'MT', 'Montana', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19028, 19, 'NE', 'Nebraska', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19029, 19, 'NV', 'Nevada', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19030, 19, 'NH', 'New Hampshire', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19031, 19, 'NJ', 'New Jersey', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19032, 19, 'NM', 'New Mexico', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19033, 19, 'NY', 'New York', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19034, 19, 'NC', 'North Carolina', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19035, 19, 'ND', 'North Dakota', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19036, 19, 'OH', 'Ohio', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19037, 19, 'OK', 'Oklahoma ', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19038, 19, 'OR', 'Oregon', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19039, 19, 'PA', 'Pennsylvania', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19040, 19, 'PR', 'Puerto Rico', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19041, 19, 'RI', 'Rhode Island', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19042, 19, 'SC', 'South Carolina', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19043, 19, 'SD', 'South Dakota', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19044, 19, 'TN', 'Tennessee', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19045, 19, 'TX', 'Texas', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19046, 19, 'UT', 'Utah', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19047, 19, 'VT', 'Vermont', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19048, 19, 'VA', 'Virginia', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19049, 19, 'WA', 'Washington', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19050, 19, 'WV', 'West Virginia', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19051, 19, 'WI', 'Wisconsin', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (19052, 19, 'WY', 'Wyoming', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (32001, 32, 'ACT', 'Australian Capital Territory', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (32002, 32, 'NSW', 'New South Wales', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (32003, 32, 'NT', 'Northern Territory', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (32004, 32, 'QLD', 'Queensland', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (32005, 32, 'SA', 'South Australia', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (32006, 32, 'TAS', 'Tasmania', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (32007, 32, 'VIC', 'Victoria', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (32008, 32, 'WA', 'Western Australia', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (33001, 33, 'AT-1', 'Burgenland', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (33002, 33, 'AT-2', 'Kärnten', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (33003, 33, 'AT-3', 'Niederösterreich', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (33004, 33, 'AT-4', 'Oberösterreich', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (33005, 33, 'AT-5', 'Salzburg', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (33006, 33, 'AT-6', 'Steiermark', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (33007, 33, 'AT-7', 'Tirol', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (33008, 33, 'AT-8', 'Vorarlberg', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (33009, 33, 'AT-9', 'Vienna', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (144001, 144, 'MX-AGS', 'Aguascalientes', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (144002, 144, 'MX-BCN', 'Baja California', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (144003, 144, 'MX-BCS', 'Baja California Sur', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (144004, 144, 'MX-CAM', 'Campeche', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (144005, 144, 'MX-CHP', 'Chiapas', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (144006, 144, 'MX-CHI', 'Chihuahua', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (144007, 144, 'MX-COA', 'Coahuila', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (144008, 144, 'MX-COL', 'Colima', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (144009, 144, 'MX-DUR', 'Durango', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (144010, 144, 'MX-GTO', 'Guanajuato', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (144011, 144, 'MX-GRO', 'Guerrero', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (144012, 144, 'MX-HGO', 'Hidalgo', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (144013, 144, 'MX-JAL', 'Jalisco', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (144014, 144, 'MX-MEX', 'Mexico', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (144015, 144, 'MX-MIC', 'Michoacan', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (144016, 144, 'MX-MOR', 'Morelos', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (144017, 144, 'MX-NAY', 'Nayarit', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (144018, 144, 'MX-NLE', 'Nuevo Leon', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (144019, 144, 'MX-OAX', 'Oaxaca', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (144020, 144, 'MX-PUE', 'Puebla', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (144021, 144, 'MX-QRO', 'Queretaro', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (144023, 144, 'MX-ROO', 'Quintana Roo', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (144024, 144, 'MX-SLP', 'San Luis Potosí', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (144025, 144, 'MX-SIN', 'Sinaloa', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (144026, 144, 'MX-SON', 'Sonora', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (144027, 144, 'MX-TAB', 'Tabasco', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (144028, 144, 'MX-TAM', 'Tamaulipas', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (144029, 144, 'MX-TLX', 'Tlaxcala', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (144030, 144, 'MX-VER', 'Veracruz', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (144031, 144, 'MX-YUC', 'Yucatan', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (144032, 144, 'MX-ZAC', 'Zacatecas', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (164001, 164, '01', 'Østfold', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (164002, 164, '02', 'Akershus', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (164003, 164, '03', 'Oslo', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (164004, 164, '04', 'Hedmark', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (164005, 164, '05', 'Oppland', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (164006, 164, '06', 'Buskerud', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (164007, 164, '07', 'Vestfold', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (164008, 164, '08', 'Telemark', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (164009, 164, '09', 'Aust-Agder', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (164010, 164, '10', 'Vest-Agder', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (164011, 164, '11', 'Rogaland', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (164012, 164, '12', 'Hordaland', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (164013, 164, '14', 'Sogn og Fjordane', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (164014, 164, '15', 'Møre of Romsdal', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (164015, 164, '16', 'Sør-Trøndelag', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (164016, 164, '17', 'Nord-Trøndelag', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (164017, 164, '18', 'Nordland', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (164018, 164, '19', 'Troms', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (164019, 164, '20', 'Finnmark', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (202001, 202, 'AG', 'Aargau', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (202002, 202, 'AR', 'Appenzell Ausserrhoden', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (202003, 202, 'AI', 'Appenzell Innerrhoden', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (202004, 202, 'BL', 'Basel-Landschaft', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (202005, 202, 'BS', 'Basel-Stadt', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (202006, 202, 'BE', 'Bern', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (202007, 202, 'FR', 'Fribourg', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (202008, 202, 'GE', 'Geneva', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (202009, 202, 'GL', 'Glarus', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (202010, 202, 'GR', 'Graubünden', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (202011, 202, 'JU', 'Jura', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (202012, 202, 'LU', 'Lucerne', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (202013, 202, 'NE', 'Neuchâtel', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (202014, 202, 'NW', 'Nidwalden', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (202015, 202, 'OW', 'Obwalden', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (202016, 202, 'SH', 'Schaffhausen', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (202017, 202, 'SZ', 'Schwyz', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (202018, 202, 'SO', 'Solothurn', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (202019, 202, 'SG', 'St. Gallen', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (202020, 202, 'TG', 'Thurgau', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (202021, 202, 'TI', 'Ticino', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (202022, 202, 'UR', 'Uri', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (202023, 202, 'VS', 'Valais', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (202024, 202, 'VD', 'Vaud', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (202025, 202, 'ZG', 'Zug', true);
insert into Region (regionId, countryId, regionCode, name, active_) values (202026, 202, 'ZH', 'Zürich', true);

//
// List types for accounts
//

insert into ListType (listTypeId, name, type_) values (10000, 'billing', 'com.liferay.portal.model.Account.address');
insert into ListType (listTypeId, name, type_) values (10001, 'other', 'com.liferay.portal.model.Account.address');
insert into ListType (listTypeId, name, type_) values (10002, 'p-o-box', 'com.liferay.portal.model.Account.address');
insert into ListType (listTypeId, name, type_) values (10003, 'shipping', 'com.liferay.portal.model.Account.address');

insert into ListType (listTypeId, name, type_) values (10004, 'email-address', 'com.liferay.portal.model.Account.emailAddress');
insert into ListType (listTypeId, name, type_) values (10005, 'email-address-2', 'com.liferay.portal.model.Account.emailAddress');
insert into ListType (listTypeId, name, type_) values (10006, 'email-address-3', 'com.liferay.portal.model.Account.emailAddress');

insert into ListType (listTypeId, name, type_) values (10007, 'fax', 'com.liferay.portal.model.Account.phone');
insert into ListType (listTypeId, name, type_) values (10008, 'local', 'com.liferay.portal.model.Account.phone');
insert into ListType (listTypeId, name, type_) values (10009, 'other', 'com.liferay.portal.model.Account.phone');
insert into ListType (listTypeId, name, type_) values (10010, 'toll-free', 'com.liferay.portal.model.Account.phone');
insert into ListType (listTypeId, name, type_) values (10011, 'tty', 'com.liferay.portal.model.Account.phone');

insert into ListType (listTypeId, name, type_) values (10012, 'intranet', 'com.liferay.portal.model.Account.website');
insert into ListType (listTypeId, name, type_) values (10013, 'public', 'com.liferay.portal.model.Account.website');

//
// List types for contacts
//

insert into ListType (listTypeId, name, type_) values (11000, 'business', 'com.liferay.portal.model.Contact.address');
insert into ListType (listTypeId, name, type_) values (11001, 'other', 'com.liferay.portal.model.Contact.address');
insert into ListType (listTypeId, name, type_) values (11002, 'personal', 'com.liferay.portal.model.Contact.address');

insert into ListType (listTypeId, name, type_) values (11003, 'email-address', 'com.liferay.portal.model.Contact.emailAddress');
insert into ListType (listTypeId, name, type_) values (11004, 'email-address-2', 'com.liferay.portal.model.Contact.emailAddress');
insert into ListType (listTypeId, name, type_) values (11005, 'email-address-3', 'com.liferay.portal.model.Contact.emailAddress');

insert into ListType (listTypeId, name, type_) values (11006, 'business', 'com.liferay.portal.model.Contact.phone');
insert into ListType (listTypeId, name, type_) values (11007, 'business-fax', 'com.liferay.portal.model.Contact.phone');
insert into ListType (listTypeId, name, type_) values (11008, 'mobile-phone', 'com.liferay.portal.model.Contact.phone');
insert into ListType (listTypeId, name, type_) values (11009, 'other', 'com.liferay.portal.model.Contact.phone');
insert into ListType (listTypeId, name, type_) values (11010, 'pager', 'com.liferay.portal.model.Contact.phone');
insert into ListType (listTypeId, name, type_) values (11011, 'personal', 'com.liferay.portal.model.Contact.phone');
insert into ListType (listTypeId, name, type_) values (11012, 'personal-fax', 'com.liferay.portal.model.Contact.phone');
insert into ListType (listTypeId, name, type_) values (11013, 'tty', 'com.liferay.portal.model.Contact.phone');

insert into ListType (listTypeId, name, type_) values (11014, 'dr', 'com.liferay.portal.model.Contact.prefix');
insert into ListType (listTypeId, name, type_) values (11015, 'mr', 'com.liferay.portal.model.Contact.prefix');
insert into ListType (listTypeId, name, type_) values (11016, 'mrs', 'com.liferay.portal.model.Contact.prefix');
insert into ListType (listTypeId, name, type_) values (11017, 'ms', 'com.liferay.portal.model.Contact.prefix');

insert into ListType (listTypeId, name, type_) values (11020, 'ii', 'com.liferay.portal.model.Contact.suffix');
insert into ListType (listTypeId, name, type_) values (11021, 'iii', 'com.liferay.portal.model.Contact.suffix');
insert into ListType (listTypeId, name, type_) values (11022, 'iv', 'com.liferay.portal.model.Contact.suffix');
insert into ListType (listTypeId, name, type_) values (11023, 'jr', 'com.liferay.portal.model.Contact.suffix');
insert into ListType (listTypeId, name, type_) values (11024, 'phd', 'com.liferay.portal.model.Contact.suffix');
insert into ListType (listTypeId, name, type_) values (11025, 'sr', 'com.liferay.portal.model.Contact.suffix');

insert into ListType (listTypeId, name, type_) values (11026, 'blog', 'com.liferay.portal.model.Contact.website');
insert into ListType (listTypeId, name, type_) values (11027, 'business', 'com.liferay.portal.model.Contact.website');
insert into ListType (listTypeId, name, type_) values (11028, 'other', 'com.liferay.portal.model.Contact.website');
insert into ListType (listTypeId, name, type_) values (11029, 'personal', 'com.liferay.portal.model.Contact.website');

//
// List types for organizations
//

insert into ListType (listTypeId, name, type_) values (12000, 'billing', 'com.liferay.portal.model.Organization.address');
insert into ListType (listTypeId, name, type_) values (12001, 'other', 'com.liferay.portal.model.Organization.address');
insert into ListType (listTypeId, name, type_) values (12002, 'p-o-box', 'com.liferay.portal.model.Organization.address');
insert into ListType (listTypeId, name, type_) values (12003, 'shipping', 'com.liferay.portal.model.Organization.address');

insert into ListType (listTypeId, name, type_) values (12004, 'email-address', 'com.liferay.portal.model.Organization.emailAddress');
insert into ListType (listTypeId, name, type_) values (12005, 'email-address-2', 'com.liferay.portal.model.Organization.emailAddress');
insert into ListType (listTypeId, name, type_) values (12006, 'email-address-3', 'com.liferay.portal.model.Organization.emailAddress');

insert into ListType (listTypeId, name, type_) values (12007, 'fax', 'com.liferay.portal.model.Organization.phone');
insert into ListType (listTypeId, name, type_) values (12008, 'local', 'com.liferay.portal.model.Organization.phone');
insert into ListType (listTypeId, name, type_) values (12009, 'other', 'com.liferay.portal.model.Organization.phone');
insert into ListType (listTypeId, name, type_) values (12010, 'toll-free', 'com.liferay.portal.model.Organization.phone');
insert into ListType (listTypeId, name, type_) values (12011, 'tty', 'com.liferay.portal.model.Organization.phone');

insert into ListType (listTypeId, name, type_) values (12012, 'administrative', 'com.liferay.portal.model.Organization.service');
insert into ListType (listTypeId, name, type_) values (12013, 'contracts', 'com.liferay.portal.model.Organization.service');
insert into ListType (listTypeId, name, type_) values (12014, 'donation', 'com.liferay.portal.model.Organization.service');
insert into ListType (listTypeId, name, type_) values (12015, 'retail', 'com.liferay.portal.model.Organization.service');
insert into ListType (listTypeId, name, type_) values (12016, 'training', 'com.liferay.portal.model.Organization.service');

insert into ListType (listTypeId, name, type_) values (12017, 'full-member', 'com.liferay.portal.model.Organization.status');
insert into ListType (listTypeId, name, type_) values (12018, 'provisional-member', 'com.liferay.portal.model.Organization.status');

insert into ListType (listTypeId, name, type_) values (12019, 'intranet', 'com.liferay.portal.model.Organization.website');
insert into ListType (listTypeId, name, type_) values (12020, 'public', 'com.liferay.portal.model.Organization.website');


insert into Counter (name, currentId) values ('com.liferay.counter.model.Counter', 10000);


insert into Release_ (releaseId, createDate, modifiedDate, servletContextName, buildNumber, verified) values (1, now(), now(), 'portal', 7000, false);


create table QUARTZ_BLOB_TRIGGERS (
	SCHED_NAME varchar(120) not null,
	TRIGGER_NAME varchar(200) not null,
	TRIGGER_GROUP varchar(200) not null,
	BLOB_DATA blob null,
	primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

create table QUARTZ_CALENDARS (
	SCHED_NAME varchar(120) not null,
	CALENDAR_NAME varchar(200) not null,
	CALENDAR blob not null,
	primary key (SCHED_NAME,CALENDAR_NAME)
);

create table QUARTZ_CRON_TRIGGERS (
	SCHED_NAME varchar(120) not null,
	TRIGGER_NAME varchar(200) not null,
	TRIGGER_GROUP varchar(200) not null,
	CRON_EXPRESSION varchar(200) not null,
	TIME_ZONE_ID varchar(80),
	primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

create table QUARTZ_FIRED_TRIGGERS (
	SCHED_NAME varchar(120) not null,
	ENTRY_ID varchar(95) not null,
	TRIGGER_NAME varchar(200) not null,
	TRIGGER_GROUP varchar(200) not null,
	INSTANCE_NAME varchar(200) not null,
	FIRED_TIME bigint not null,
	PRIORITY int not null,
	STATE varchar(16) not null,
	JOB_NAME varchar(200) null,
	JOB_GROUP varchar(200) null,
	IS_NONCONCURRENT bit null,
	REQUESTS_RECOVERY bit null,
	primary key (SCHED_NAME, ENTRY_ID)
);

create table QUARTZ_JOB_DETAILS (
	SCHED_NAME varchar(120) not null,
	JOB_NAME varchar(200) not null,
	JOB_GROUP varchar(200) not null,
	DESCRIPTION varchar(250) null,
	JOB_CLASS_NAME varchar(250) not null,
	IS_DURABLE bit not null,
	IS_NONCONCURRENT bit not null,
	IS_UPDATE_DATA bit not null,
	REQUESTS_RECOVERY bit not null,
	JOB_DATA blob null,
	primary key (SCHED_NAME, JOB_NAME, JOB_GROUP)
);

create table QUARTZ_LOCKS (
	SCHED_NAME varchar(120) not null,
	LOCK_NAME varchar(40) not null ,
	primary key (SCHED_NAME, LOCK_NAME)
);

create table QUARTZ_PAUSED_TRIGGER_GRPS (
	SCHED_NAME varchar(120) not null,
	TRIGGER_GROUP varchar(200) not null,
	primary key (SCHED_NAME, TRIGGER_GROUP)
);

create table QUARTZ_SCHEDULER_STATE (
	SCHED_NAME varchar(120) not null,
	INSTANCE_NAME varchar(200) not null,
	LAST_CHECKIN_TIME bigint not null,
	CHECKIN_INTERVAL bigint not null,
	primary key (SCHED_NAME, INSTANCE_NAME)
);

create table QUARTZ_SIMPLE_TRIGGERS (
	SCHED_NAME varchar(120) not null,
	TRIGGER_NAME varchar(200) not null,
	TRIGGER_GROUP varchar(200) not null,
	REPEAT_COUNT bigint not null,
	REPEAT_INTERVAL bigint not null,
	TIMES_TRIGGERED bigint not null,
	primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

create table QUARTZ_SIMPROP_TRIGGERS (
	SCHED_NAME varchar(120) not null,
	TRIGGER_NAME varchar(200) not null,
	TRIGGER_GROUP varchar(200) not null,
	STR_PROP_1 varchar(512) null,
	STR_PROP_2 varchar(512) null,
	STR_PROP_3 varchar(512) null,
	INT_PROP_1 int null,
	INT_PROP_2 int null,
	LONG_PROP_1 bigint null,
	LONG_PROP_2 bigint null,
	DEC_PROP_1 NUMERIC(13,4) null,
	DEC_PROP_2 NUMERIC(13,4) null,
	BOOL_PROP_1 bit null,
	BOOL_PROP_2 bit null,
	primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

create table QUARTZ_TRIGGERS (
	SCHED_NAME varchar(120) not null,
	TRIGGER_NAME varchar(200) not null,
	TRIGGER_GROUP varchar(200) not null,
	JOB_NAME varchar(200) not null,
	JOB_GROUP varchar(200) not null,
	DESCRIPTION varchar(250) null,
	NEXT_FIRE_TIME bigint null,
	PREV_FIRE_TIME bigint null,
	PRIORITY int null,
	TRIGGER_STATE varchar(16) not null,
	TRIGGER_TYPE varchar(8) not null,
	START_TIME bigint not null,
	END_TIME bigint null,
	CALENDAR_NAME varchar(200) null,
	MISFIRE_INSTR int null,
	JOB_DATA blob null,
	primary key  (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

commit;

create index IX_88328984 on QUARTZ_JOB_DETAILS (SCHED_NAME, JOB_GROUP);
create index IX_779BCA37 on QUARTZ_JOB_DETAILS (SCHED_NAME, REQUESTS_RECOVERY);

create index IX_BE3835E5 on QUARTZ_FIRED_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP);
create index IX_4BD722BM on QUARTZ_FIRED_TRIGGERS (SCHED_NAME, TRIGGER_GROUP);
create index IX_204D31E8 on QUARTZ_FIRED_TRIGGERS (SCHED_NAME, INSTANCE_NAME);
create index IX_339E078M on QUARTZ_FIRED_TRIGGERS (SCHED_NAME, INSTANCE_NAME, REQUESTS_RECOVERY);
create index IX_5005E3AF on QUARTZ_FIRED_TRIGGERS (SCHED_NAME, JOB_NAME, JOB_GROUP);
create index IX_BC2F03B0 on QUARTZ_FIRED_TRIGGERS (SCHED_NAME, JOB_GROUP);

create index IX_186442A4 on QUARTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP, TRIGGER_STATE);
create index IX_1BA1F9DC on QUARTZ_TRIGGERS (SCHED_NAME, TRIGGER_GROUP);
create index IX_91CA7CCE on QUARTZ_TRIGGERS (SCHED_NAME, TRIGGER_GROUP, NEXT_FIRE_TIME, TRIGGER_STATE, MISFIRE_INSTR);
create index IX_D219AFDE on QUARTZ_TRIGGERS (SCHED_NAME, TRIGGER_GROUP, TRIGGER_STATE);
create index IX_A85822A0 on QUARTZ_TRIGGERS (SCHED_NAME, JOB_NAME, JOB_GROUP);
create index IX_8AA50BE1 on QUARTZ_TRIGGERS (SCHED_NAME, JOB_GROUP);
create index IX_EEFE382A on QUARTZ_TRIGGERS (SCHED_NAME, NEXT_FIRE_TIME);
create index IX_F026CF4C on QUARTZ_TRIGGERS (SCHED_NAME, NEXT_FIRE_TIME, TRIGGER_STATE);
create index IX_F2DD7C7E on QUARTZ_TRIGGERS (SCHED_NAME, NEXT_FIRE_TIME, TRIGGER_STATE, MISFIRE_INSTR);
create index IX_1F92813C on QUARTZ_TRIGGERS (SCHED_NAME, NEXT_FIRE_TIME, MISFIRE_INSTR);
create index IX_99108B6E on QUARTZ_TRIGGERS (SCHED_NAME, TRIGGER_STATE);
create index IX_CD7132D0 on QUARTZ_TRIGGERS (SCHED_NAME, CALENDAR_NAME);


commit;
