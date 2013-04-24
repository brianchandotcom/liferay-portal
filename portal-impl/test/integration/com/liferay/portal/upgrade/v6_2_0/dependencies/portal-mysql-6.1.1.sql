drop database if exists lportal;
create database lportal character set utf8;
use lportal;

create table Account_ (
	accountId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
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
) engine InnoDB;

create table Address (
	addressId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	classNameId bigint,
	classPK bigint,
	street1 varchar(75) null,
	street2 varchar(75) null,
	street3 varchar(75) null,
	city varchar(75) null,
	zip varchar(75) null,
	regionId bigint,
	countryId bigint,
	typeId integer,
	mailing tinyint,
	primary_ tinyint
) engine InnoDB;

create table AnnouncementsDelivery (
	deliveryId bigint not null primary key,
	companyId bigint,
	userId bigint,
	type_ varchar(75) null,
	email tinyint,
	sms tinyint,
	website tinyint
) engine InnoDB;

create table AnnouncementsEntry (
	uuid_ varchar(75) null,
	entryId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	classNameId bigint,
	classPK bigint,
	title varchar(75) null,
	content longtext null,
	url longtext null,
	type_ varchar(75) null,
	displayDate datetime null,
	expirationDate datetime null,
	priority integer,
	alert tinyint
) engine InnoDB;

create table AnnouncementsFlag (
	flagId bigint not null primary key,
	userId bigint,
	createDate datetime null,
	entryId bigint,
	value integer
) engine InnoDB;

create table AssetCategory (
	uuid_ varchar(75) null,
	categoryId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	parentCategoryId bigint,
	leftCategoryId bigint,
	rightCategoryId bigint,
	name varchar(75) null,
	title longtext null,
	description longtext null,
	vocabularyId bigint
) engine InnoDB;

create table AssetCategoryProperty (
	categoryPropertyId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	categoryId bigint,
	key_ varchar(75) null,
	value varchar(75) null
) engine InnoDB;

create table AssetEntries_AssetCategories (
	entryId bigint not null,
	categoryId bigint not null,
	primary key (entryId, categoryId)
) engine InnoDB;

create table AssetEntries_AssetTags (
	entryId bigint not null,
	tagId bigint not null,
	primary key (entryId, tagId)
) engine InnoDB;

create table AssetEntry (
	entryId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	classNameId bigint,
	classPK bigint,
	classUuid varchar(75) null,
	classTypeId bigint,
	visible tinyint,
	startDate datetime null,
	endDate datetime null,
	publishDate datetime null,
	expirationDate datetime null,
	mimeType varchar(75) null,
	title longtext null,
	description longtext null,
	summary longtext null,
	url longtext null,
	layoutUuid varchar(75) null,
	height integer,
	width integer,
	priority double,
	viewCount integer
) engine InnoDB;

create table AssetLink (
	linkId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	entryId1 bigint,
	entryId2 bigint,
	type_ integer,
	weight integer
) engine InnoDB;

create table AssetTag (
	tagId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	name varchar(75) null,
	assetCount integer
) engine InnoDB;

create table AssetTagProperty (
	tagPropertyId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	tagId bigint,
	key_ varchar(75) null,
	value varchar(255) null
) engine InnoDB;

create table AssetTagStats (
	tagStatsId bigint not null primary key,
	tagId bigint,
	classNameId bigint,
	assetCount integer
) engine InnoDB;

create table AssetVocabulary (
	uuid_ varchar(75) null,
	vocabularyId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	name varchar(75) null,
	title longtext null,
	description longtext null,
	settings_ longtext null
) engine InnoDB;

create table BlogsEntry (
	uuid_ varchar(75) null,
	entryId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	title varchar(150) null,
	urlTitle varchar(150) null,
	description longtext null,
	content longtext null,
	displayDate datetime null,
	allowPingbacks tinyint,
	allowTrackbacks tinyint,
	trackbacks longtext null,
	smallImage tinyint,
	smallImageId bigint,
	smallImageURL longtext null,
	status integer,
	statusByUserId bigint,
	statusByUserName varchar(75) null,
	statusDate datetime null
) engine InnoDB;

create table BlogsStatsUser (
	statsUserId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	entryCount integer,
	lastPostDate datetime null,
	ratingsTotalEntries integer,
	ratingsTotalScore double,
	ratingsAverageScore double
) engine InnoDB;

create table BookmarksEntry (
	uuid_ varchar(75) null,
	entryId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	resourceBlockId bigint,
	folderId bigint,
	name varchar(255) null,
	url longtext null,
	description longtext null,
	visits integer,
	priority integer,
	status integer,
	statusByUserId bigint,
	statusByUserName varchar(75) null,
	statusDate datetime null
) engine InnoDB;

create table BookmarksFolder (
	uuid_ varchar(75) null,
	folderId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	resourceBlockId bigint,
	parentFolderId bigint,
	name varchar(75) null,
	description longtext null,
	status integer,
	statusByUserId bigint,
	statusByUserName varchar(75) null,
	statusDate datetime null
) engine InnoDB;

create table BrowserTracker (
	browserTrackerId bigint not null primary key,
	userId bigint,
	browserKey bigint
) engine InnoDB;

create table CalEvent (
	uuid_ varchar(75) null,
	eventId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	title varchar(75) null,
	description longtext null,
	location longtext null,
	startDate datetime null,
	endDate datetime null,
	durationHour integer,
	durationMinute integer,
	allDay tinyint,
	timeZoneSensitive tinyint,
	type_ varchar(75) null,
	repeating tinyint,
	recurrence longtext null,
	remindBy integer,
	firstReminder integer,
	secondReminder integer
) engine InnoDB;

create table ClassName_ (
	classNameId bigint not null primary key,
	value varchar(200) null
) engine InnoDB;

create table ClusterGroup (
	clusterGroupId bigint not null primary key,
	name varchar(75) null,
	clusterNodeIds varchar(75) null,
	wholeCluster tinyint
) engine InnoDB;

create table Company (
	companyId bigint not null primary key,
	accountId bigint,
	webId varchar(75) null,
	key_ longtext null,
	mx varchar(75) null,
	homeURL longtext null,
	logoId bigint,
	system tinyint,
	maxUsers integer,
	active_ tinyint
) engine InnoDB;

create table Contact_ (
	contactId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	classNameId bigint,
	classPK bigint,
	accountId bigint,
	parentContactId bigint,
	emailAddress varchar(75) null,
	firstName varchar(75) null,
	middleName varchar(75) null,
	lastName varchar(75) null,
	prefixId integer,
	suffixId integer,
	male tinyint,
	birthday datetime null,
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
) engine InnoDB;

create table Counter (
	name varchar(75) not null primary key,
	currentId bigint
) engine InnoDB;

create table Country (
	countryId bigint not null primary key,
	name varchar(75) null,
	a2 varchar(75) null,
	a3 varchar(75) null,
	number_ varchar(75) null,
	idd_ varchar(75) null,
	zipRequired tinyint,
	active_ tinyint
) engine InnoDB;

create table CyrusUser (
	userId varchar(75) not null primary key,
	password_ varchar(75) not null
) engine InnoDB;

create table CyrusVirtual (
	emailAddress varchar(75) not null primary key,
	userId varchar(75) not null
) engine InnoDB;

create table DDLRecord (
	uuid_ varchar(75) null,
	recordId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	versionUserId bigint,
	versionUserName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	DDMStorageId bigint,
	recordSetId bigint,
	version varchar(75) null,
	displayIndex integer
) engine InnoDB;

create table DDLRecordSet (
	uuid_ varchar(75) null,
	recordSetId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	DDMStructureId bigint,
	recordSetKey varchar(75) null,
	name longtext null,
	description longtext null,
	minDisplayRows integer,
	scope integer
) engine InnoDB;

create table DDLRecordVersion (
	recordVersionId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	DDMStorageId bigint,
	recordSetId bigint,
	recordId bigint,
	version varchar(75) null,
	displayIndex integer,
	status integer,
	statusByUserId bigint,
	statusByUserName varchar(75) null,
	statusDate datetime null
) engine InnoDB;

create table DDMContent (
	uuid_ varchar(75) null,
	contentId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	name longtext null,
	description longtext null,
	xml longtext null
) engine InnoDB;

create table DDMStorageLink (
	uuid_ varchar(75) null,
	storageLinkId bigint not null primary key,
	classNameId bigint,
	classPK bigint,
	structureId bigint
) engine InnoDB;

create table DDMStructure (
	uuid_ varchar(75) null,
	structureId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	parentStructureId bigint,
	classNameId bigint,
	structureKey varchar(75) null,
	name longtext null,
	description longtext null,
	xsd longtext null,
	storageType varchar(75) null,
	type_ integer
) engine InnoDB;

create table DDMStructureLink (
	structureLinkId bigint not null primary key,
	classNameId bigint,
	classPK bigint,
	structureId bigint
) engine InnoDB;

create table DDMTemplate (
	uuid_ varchar(75) null,
	templateId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	classNameId bigint,
	classPK bigint,
	templateKey varchar(75) null,
	name longtext null,
	description longtext null,
	type_ varchar(75) null,
	mode_ varchar(75) null,
	language varchar(75) null,
	script longtext null,
	cacheable tinyint,
	smallImage tinyint,
	smallImageId bigint,
	smallImageURL varchar(75) null
) engine InnoDB;

create table DLContent (
	contentId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	repositoryId bigint,
	path_ varchar(255) null,
	version varchar(75) null,
	data_ longblob,
	size_ bigint
) engine InnoDB;

create table DLFileEntry (
	uuid_ varchar(75) null,
	fileEntryId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	versionUserId bigint,
	versionUserName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	classNameId bigint,
	classPK bigint,
	repositoryId bigint,
	folderId bigint,
	name varchar(255) null,
	extension varchar(75) null,
	mimeType varchar(75) null,
	title varchar(255) null,
	description longtext null,
	extraSettings longtext null,
	fileEntryTypeId bigint,
	version varchar(75) null,
	size_ bigint,
	readCount integer,
	smallImageId bigint,
	largeImageId bigint,
	custom1ImageId bigint,
	custom2ImageId bigint,
	manualCheckInRequired tinyint
) engine InnoDB;

create table DLFileEntryMetadata (
	uuid_ varchar(75) null,
	fileEntryMetadataId bigint not null primary key,
	DDMStorageId bigint,
	DDMStructureId bigint,
	fileEntryTypeId bigint,
	fileEntryId bigint,
	fileVersionId bigint
) engine InnoDB;

create table DLFileEntryType (
	uuid_ varchar(75) null,
	fileEntryTypeId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	name varchar(75) null,
	description longtext null
) engine InnoDB;

create table DLFileEntryTypes_DDMStructures (
	structureId bigint not null,
	fileEntryTypeId bigint not null,
	primary key (structureId, fileEntryTypeId)
) engine InnoDB;

create table DLFileEntryTypes_DLFolders (
	fileEntryTypeId bigint not null,
	folderId bigint not null,
	primary key (fileEntryTypeId, folderId)
) engine InnoDB;

create table DLFileRank (
	uuid_ varchar(75) null,
	fileRankId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	fileEntryId bigint,
	active_ tinyint
) engine InnoDB;

create table DLFileShortcut (
	uuid_ varchar(75) null,
	fileShortcutId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	repositoryId bigint,
	folderId bigint,
	toFileEntryId bigint,
	active_ tinyint,
	status integer,
	statusByUserId bigint,
	statusByUserName varchar(75) null,
	statusDate datetime null
) engine InnoDB;

create table DLFileVersion (
	uuid_ varchar(75) null,
	fileVersionId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	repositoryId bigint,
	folderId bigint,
	fileEntryId bigint,
	extension varchar(75) null,
	mimeType varchar(75) null,
	title varchar(255) null,
	description longtext null,
	changeLog varchar(75) null,
	extraSettings longtext null,
	fileEntryTypeId bigint,
	version varchar(75) null,
	size_ bigint,
	checksum varchar(75) null,
	status integer,
	statusByUserId bigint,
	statusByUserName varchar(75) null,
	statusDate datetime null
) engine InnoDB;

create table DLFolder (
	uuid_ varchar(75) null,
	folderId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	repositoryId bigint,
	mountPoint tinyint,
	parentFolderId bigint,
	name varchar(100) null,
	description longtext null,
	lastPostDate datetime null,
	defaultFileEntryTypeId bigint,
	hidden_ tinyint,
	overrideFileEntryTypes tinyint,
	status integer,
	statusByUserId bigint,
	statusByUserName varchar(75) null,
	statusDate datetime null
) engine InnoDB;

create table DLSync (
	syncId bigint not null primary key,
	companyId bigint,
	createDate bigint,
	modifiedDate bigint,
	fileId bigint,
	fileUuid varchar(75) null,
	repositoryId bigint,
	parentFolderId bigint,
	name varchar(255) null,
	description longtext null,
	event varchar(75) null,
	type_ varchar(75) null,
	version varchar(75) null
) engine InnoDB;

create table EmailAddress (
	emailAddressId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	classNameId bigint,
	classPK bigint,
	address varchar(75) null,
	typeId integer,
	primary_ tinyint
) engine InnoDB;

create table ExpandoColumn (
	columnId bigint not null primary key,
	companyId bigint,
	tableId bigint,
	name varchar(75) null,
	type_ integer,
	defaultData longtext null,
	typeSettings longtext null
) engine InnoDB;

create table ExpandoRow (
	rowId_ bigint not null primary key,
	companyId bigint,
	modifiedDate datetime null,
	tableId bigint,
	classPK bigint
) engine InnoDB;

create table ExpandoTable (
	tableId bigint not null primary key,
	companyId bigint,
	classNameId bigint,
	name varchar(75) null
) engine InnoDB;

create table ExpandoValue (
	valueId bigint not null primary key,
	companyId bigint,
	tableId bigint,
	columnId bigint,
	rowId_ bigint,
	classNameId bigint,
	classPK bigint,
	data_ longtext null
) engine InnoDB;

create table Group_ (
	groupId bigint not null primary key,
	companyId bigint,
	creatorUserId bigint,
	classNameId bigint,
	classPK bigint,
	parentGroupId bigint,
	liveGroupId bigint,
	treePath varchar(75) null,
	name varchar(150) null,
	description longtext null,
	type_ integer,
	typeSettings longtext null,
	friendlyURL varchar(255) null,
	site tinyint,
	active_ tinyint
) engine InnoDB;

create table Groups_Orgs (
	groupId bigint not null,
	organizationId bigint not null,
	primary key (groupId, organizationId)
) engine InnoDB;

create table Groups_Roles (
	groupId bigint not null,
	roleId bigint not null,
	primary key (groupId, roleId)
) engine InnoDB;

create table Groups_UserGroups (
	groupId bigint not null,
	userGroupId bigint not null,
	primary key (groupId, userGroupId)
) engine InnoDB;

create table Image (
	imageId bigint not null primary key,
	modifiedDate datetime null,
	type_ varchar(75) null,
	height integer,
	width integer,
	size_ integer
) engine InnoDB;

create table JournalArticle (
	uuid_ varchar(75) null,
	id_ bigint not null primary key,
	resourcePrimKey bigint,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	folderId bigint,
	classNameId bigint,
	classPK bigint,
	articleId varchar(75) null,
	version double,
	title longtext null,
	urlTitle varchar(150) null,
	description longtext null,
	content longtext null,
	type_ varchar(75) null,
	structureId varchar(75) null,
	templateId varchar(75) null,
	layoutUuid varchar(75) null,
	displayDate datetime null,
	expirationDate datetime null,
	reviewDate datetime null,
	indexable tinyint,
	smallImage tinyint,
	smallImageId bigint,
	smallImageURL longtext null,
	status integer,
	statusByUserId bigint,
	statusByUserName varchar(75) null,
	statusDate datetime null
) engine InnoDB;

create table JournalArticleImage (
	articleImageId bigint not null primary key,
	groupId bigint,
	articleId varchar(75) null,
	version double,
	elInstanceId varchar(75) null,
	elName varchar(75) null,
	languageId varchar(75) null,
	tempImage tinyint
) engine InnoDB;

create table JournalArticleResource (
	uuid_ varchar(75) null,
	resourcePrimKey bigint not null primary key,
	groupId bigint,
	articleId varchar(75) null
) engine InnoDB;

create table JournalContentSearch (
	contentSearchId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	privateLayout tinyint,
	layoutId bigint,
	portletId varchar(200) null,
	articleId varchar(75) null
) engine InnoDB;

create table JournalFeed (
	uuid_ varchar(75) null,
	id_ bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	feedId varchar(75) null,
	name varchar(75) null,
	description longtext null,
	type_ varchar(75) null,
	structureId varchar(75) null,
	templateId varchar(75) null,
	rendererTemplateId varchar(75) null,
	delta integer,
	orderByCol varchar(75) null,
	orderByType varchar(75) null,
	targetLayoutFriendlyUrl varchar(255) null,
	targetPortletId varchar(75) null,
	contentField varchar(75) null,
	feedFormat varchar(75) null,
	feedVersion double
) engine InnoDB;

create table JournalFolder (
	uuid_ varchar(75) null,
	folderId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	parentFolderId bigint,
	name varchar(100) null,
	description longtext null,
	status integer,
	statusByUserId bigint,
	statusByUserName varchar(75) null,
	statusDate datetime null
) engine InnoDB;

create table JournalStructure (
	uuid_ varchar(75) null,
	id_ bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	structureId varchar(75) null,
	parentStructureId varchar(75) null,
	name longtext null,
	description longtext null,
	xsd longtext null
) engine InnoDB;

create table JournalTemplate (
	uuid_ varchar(75) null,
	id_ bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	templateId varchar(75) null,
	structureId varchar(75) null,
	name longtext null,
	description longtext null,
	xsl longtext null,
	langType varchar(75) null,
	cacheable tinyint,
	smallImage tinyint,
	smallImageId bigint,
	smallImageURL longtext null
) engine InnoDB;

create table Layout (
	uuid_ varchar(75) null,
	plid bigint not null primary key,
	groupId bigint,
	companyId bigint,
	createDate datetime null,
	modifiedDate datetime null,
	privateLayout tinyint,
	layoutId bigint,
	parentLayoutId bigint,
	name longtext null,
	title longtext null,
	description longtext null,
	keywords longtext null,
	robots longtext null,
	type_ varchar(75) null,
	typeSettings longtext null,
	hidden_ tinyint,
	friendlyURL varchar(255) null,
	iconImage tinyint,
	iconImageId bigint,
	themeId varchar(75) null,
	colorSchemeId varchar(75) null,
	wapThemeId varchar(75) null,
	wapColorSchemeId varchar(75) null,
	css longtext null,
	priority integer,
	layoutPrototypeUuid varchar(75) null,
	layoutPrototypeLinkEnabled tinyint,
	sourcePrototypeLayoutUuid varchar(75) null
) engine InnoDB;

create table LayoutBranch (
	LayoutBranchId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	layoutSetBranchId bigint,
	plid bigint,
	name varchar(75) null,
	description longtext null,
	master tinyint
) engine InnoDB;

create table LayoutPrototype (
	uuid_ varchar(75) null,
	layoutPrototypeId bigint not null primary key,
	companyId bigint,
	name longtext null,
	description longtext null,
	settings_ longtext null,
	active_ tinyint
) engine InnoDB;

create table LayoutRevision (
	layoutRevisionId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	layoutSetBranchId bigint,
	layoutBranchId bigint,
	parentLayoutRevisionId bigint,
	head tinyint,
	major tinyint,
	plid bigint,
	privateLayout tinyint,
	name longtext null,
	title longtext null,
	description longtext null,
	keywords longtext null,
	robots longtext null,
	typeSettings longtext null,
	iconImage tinyint,
	iconImageId bigint,
	themeId varchar(75) null,
	colorSchemeId varchar(75) null,
	wapThemeId varchar(75) null,
	wapColorSchemeId varchar(75) null,
	css longtext null,
	status integer,
	statusByUserId bigint,
	statusByUserName varchar(75) null,
	statusDate datetime null
) engine InnoDB;

create table LayoutSet (
	layoutSetId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	createDate datetime null,
	modifiedDate datetime null,
	privateLayout tinyint,
	logo tinyint,
	logoId bigint,
	themeId varchar(75) null,
	colorSchemeId varchar(75) null,
	wapThemeId varchar(75) null,
	wapColorSchemeId varchar(75) null,
	css longtext null,
	pageCount integer,
	settings_ longtext null,
	layoutSetPrototypeUuid varchar(75) null,
	layoutSetPrototypeLinkEnabled tinyint
) engine InnoDB;

create table LayoutSetBranch (
	layoutSetBranchId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	privateLayout tinyint,
	name varchar(75) null,
	description longtext null,
	master tinyint,
	logo tinyint,
	logoId bigint,
	themeId varchar(75) null,
	colorSchemeId varchar(75) null,
	wapThemeId varchar(75) null,
	wapColorSchemeId varchar(75) null,
	css longtext null,
	settings_ longtext null,
	layoutSetPrototypeUuid varchar(75) null,
	layoutSetPrototypeLinkEnabled tinyint
) engine InnoDB;

create table LayoutSetPrototype (
	uuid_ varchar(75) null,
	layoutSetPrototypeId bigint not null primary key,
	companyId bigint,
	createDate datetime null,
	modifiedDate datetime null,
	name longtext null,
	description longtext null,
	settings_ longtext null,
	active_ tinyint
) engine InnoDB;

create table ListType (
	listTypeId integer not null primary key,
	name varchar(75) null,
	type_ varchar(75) null
) engine InnoDB;

create table Lock_ (
	uuid_ varchar(75) null,
	lockId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	className varchar(75) null,
	key_ varchar(200) null,
	owner varchar(255) null,
	inheritable tinyint,
	expirationDate datetime null
) engine InnoDB;

create table MBBan (
	uuid_ varchar(75) null,
	banId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	banUserId bigint
) engine InnoDB;

create table MBCategory (
	uuid_ varchar(75) null,
	categoryId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	parentCategoryId bigint,
	name varchar(75) null,
	description longtext null,
	displayStyle varchar(75) null,
	threadCount integer,
	messageCount integer,
	lastPostDate datetime null,
	status integer,
	statusByUserId bigint,
	statusByUserName varchar(75) null,
	statusDate datetime null
) engine InnoDB;

create table MBDiscussion (
	uuid_ varchar(75) null,
	discussionId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	classNameId bigint,
	classPK bigint,
	threadId bigint
) engine InnoDB;

create table MBMailingList (
	uuid_ varchar(75) null,
	mailingListId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	categoryId bigint,
	emailAddress varchar(75) null,
	inProtocol varchar(75) null,
	inServerName varchar(75) null,
	inServerPort integer,
	inUseSSL tinyint,
	inUserName varchar(75) null,
	inPassword varchar(75) null,
	inReadInterval integer,
	outEmailAddress varchar(75) null,
	outCustom tinyint,
	outServerName varchar(75) null,
	outServerPort integer,
	outUseSSL tinyint,
	outUserName varchar(75) null,
	outPassword varchar(75) null,
	allowAnonymous tinyint,
	active_ tinyint
) engine InnoDB;

create table MBMessage (
	uuid_ varchar(75) null,
	messageId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	classNameId bigint,
	classPK bigint,
	categoryId bigint,
	threadId bigint,
	rootMessageId bigint,
	parentMessageId bigint,
	subject varchar(75) null,
	body longtext null,
	format varchar(75) null,
	anonymous tinyint,
	priority double,
	allowPingbacks tinyint,
	answer tinyint,
	status integer,
	statusByUserId bigint,
	statusByUserName varchar(75) null,
	statusDate datetime null
) engine InnoDB;

create table MBStatsUser (
	statsUserId bigint not null primary key,
	groupId bigint,
	userId bigint,
	messageCount integer,
	lastPostDate datetime null
) engine InnoDB;

create table MBThread (
	uuid_ varchar(75) null,
	threadId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	categoryId bigint,
	rootMessageId bigint,
	rootMessageUserId bigint,
	messageCount integer,
	viewCount integer,
	lastPostByUserId bigint,
	lastPostDate datetime null,
	priority double,
	question tinyint,
	status integer,
	statusByUserId bigint,
	statusByUserName varchar(75) null,
	statusDate datetime null
) engine InnoDB;

create table MBThreadFlag (
	uuid_ varchar(75) null,
	threadFlagId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	threadId bigint
) engine InnoDB;

create table MDRAction (
	uuid_ varchar(75) null,
	actionId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	classNameId bigint,
	classPK bigint,
	ruleGroupInstanceId bigint,
	name longtext null,
	description longtext null,
	type_ varchar(255) null,
	typeSettings longtext null
) engine InnoDB;

create table MDRRule (
	uuid_ varchar(75) null,
	ruleId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	ruleGroupId bigint,
	name longtext null,
	description longtext null,
	type_ varchar(255) null,
	typeSettings longtext null
) engine InnoDB;

create table MDRRuleGroup (
	uuid_ varchar(75) null,
	ruleGroupId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	name longtext null,
	description longtext null
) engine InnoDB;

create table MDRRuleGroupInstance (
	uuid_ varchar(75) null,
	ruleGroupInstanceId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	classNameId bigint,
	classPK bigint,
	ruleGroupId bigint,
	priority integer
) engine InnoDB;

create table MembershipRequest (
	membershipRequestId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	createDate datetime null,
	comments longtext null,
	replyComments longtext null,
	replyDate datetime null,
	replierUserId bigint,
	statusId integer
) engine InnoDB;

create table Organization_ (
	uuid_ varchar(75) null,
	organizationId bigint not null primary key,
	companyId bigint,
	parentOrganizationId bigint,
	treePath longtext null,
	name varchar(100) null,
	type_ varchar(75) null,
	recursable tinyint,
	regionId bigint,
	countryId bigint,
	statusId integer,
	comments longtext null
) engine InnoDB;

create table OrgGroupRole (
	organizationId bigint not null,
	groupId bigint not null,
	roleId bigint not null,
	primary key (organizationId, groupId, roleId)
) engine InnoDB;

create table OrgLabor (
	orgLaborId bigint not null primary key,
	organizationId bigint,
	typeId integer,
	sunOpen integer,
	sunClose integer,
	monOpen integer,
	monClose integer,
	tueOpen integer,
	tueClose integer,
	wedOpen integer,
	wedClose integer,
	thuOpen integer,
	thuClose integer,
	friOpen integer,
	friClose integer,
	satOpen integer,
	satClose integer
) engine InnoDB;

create table PasswordPolicy (
	uuid_ varchar(75) null,
	passwordPolicyId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	defaultPolicy tinyint,
	name varchar(75) null,
	description longtext null,
	changeable tinyint,
	changeRequired tinyint,
	minAge bigint,
	checkSyntax tinyint,
	allowDictionaryWords tinyint,
	minAlphanumeric integer,
	minLength integer,
	minLowerCase integer,
	minNumbers integer,
	minSymbols integer,
	minUpperCase integer,
	regex varchar(75) null,
	history tinyint,
	historyCount integer,
	expireable tinyint,
	maxAge bigint,
	warningTime bigint,
	graceLimit integer,
	lockout tinyint,
	maxFailure integer,
	lockoutDuration bigint,
	requireUnlock tinyint,
	resetFailureCount bigint,
	resetTicketMaxAge bigint
) engine InnoDB;

create table PasswordPolicyRel (
	passwordPolicyRelId bigint not null primary key,
	passwordPolicyId bigint,
	classNameId bigint,
	classPK bigint
) engine InnoDB;

create table PasswordTracker (
	passwordTrackerId bigint not null primary key,
	userId bigint,
	createDate datetime null,
	password_ varchar(75) null
) engine InnoDB;

create table Phone (
	phoneId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	classNameId bigint,
	classPK bigint,
	number_ varchar(75) null,
	extension varchar(75) null,
	typeId integer,
	primary_ tinyint
) engine InnoDB;

create table PluginSetting (
	pluginSettingId bigint not null primary key,
	companyId bigint,
	pluginId varchar(75) null,
	pluginType varchar(75) null,
	roles longtext null,
	active_ tinyint
) engine InnoDB;

create table PollsChoice (
	uuid_ varchar(75) null,
	choiceId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	questionId bigint,
	name varchar(75) null,
	description longtext null
) engine InnoDB;

create table PollsQuestion (
	uuid_ varchar(75) null,
	questionId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	title longtext null,
	description longtext null,
	expirationDate datetime null,
	lastVoteDate datetime null
) engine InnoDB;

create table PollsVote (
	uuid_ varchar(75) null,
	voteId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	questionId bigint,
	choiceId bigint,
	voteDate datetime null
) engine InnoDB;

create table PortalPreferences (
	portalPreferencesId bigint not null primary key,
	ownerId bigint,
	ownerType integer,
	preferences longtext null
) engine InnoDB;

create table Portlet (
	id_ bigint not null primary key,
	companyId bigint,
	portletId varchar(200) null,
	roles longtext null,
	active_ tinyint
) engine InnoDB;

create table PortletItem (
	portletItemId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	name varchar(75) null,
	portletId varchar(75) null,
	classNameId bigint
) engine InnoDB;

create table PortletPreferences (
	portletPreferencesId bigint not null primary key,
	ownerId bigint,
	ownerType integer,
	plid bigint,
	portletId varchar(200) null,
	preferences longtext null
) engine InnoDB;

create table RatingsEntry (
	entryId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	classNameId bigint,
	classPK bigint,
	score double
) engine InnoDB;

create table RatingsStats (
	statsId bigint not null primary key,
	classNameId bigint,
	classPK bigint,
	totalEntries integer,
	totalScore double,
	averageScore double
) engine InnoDB;

create table Region (
	regionId bigint not null primary key,
	countryId bigint,
	regionCode varchar(75) null,
	name varchar(75) null,
	active_ tinyint
) engine InnoDB;

create table Release_ (
	releaseId bigint not null primary key,
	createDate datetime null,
	modifiedDate datetime null,
	servletContextName varchar(75) null,
	buildNumber integer,
	buildDate datetime null,
	verified tinyint,
	state_ integer,
	testString varchar(1024) null
) engine InnoDB;

create table Repository (
	uuid_ varchar(75) null,
	repositoryId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	classNameId bigint,
	name varchar(75) null,
	description longtext null,
	portletId varchar(75) null,
	typeSettings longtext null,
	dlFolderId bigint
) engine InnoDB;

create table RepositoryEntry (
	uuid_ varchar(75) null,
	repositoryEntryId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	repositoryId bigint,
	mappedId varchar(75) null,
	manualCheckInRequired tinyint
) engine InnoDB;

create table ResourceAction (
	resourceActionId bigint not null primary key,
	name varchar(255) null,
	actionId varchar(75) null,
	bitwiseValue bigint
) engine InnoDB;

create table ResourceBlock (
	resourceBlockId bigint not null primary key,
	companyId bigint,
	groupId bigint,
	name varchar(75) null,
	permissionsHash varchar(75) null,
	referenceCount bigint
) engine InnoDB;

create table ResourceBlockPermission (
	resourceBlockPermissionId bigint not null primary key,
	resourceBlockId bigint,
	roleId bigint,
	actionIds bigint
) engine InnoDB;

create table ResourcePermission (
	resourcePermissionId bigint not null primary key,
	companyId bigint,
	name varchar(255) null,
	scope integer,
	primKey varchar(255) null,
	roleId bigint,
	ownerId bigint,
	actionIds bigint
) engine InnoDB;

create table ResourceTypePermission (
	resourceTypePermissionId bigint not null primary key,
	companyId bigint,
	groupId bigint,
	name varchar(75) null,
	roleId bigint,
	actionIds bigint
) engine InnoDB;

create table Role_ (
	uuid_ varchar(75) null,
	roleId bigint not null primary key,
	companyId bigint,
	classNameId bigint,
	classPK bigint,
	name varchar(75) null,
	title longtext null,
	description longtext null,
	type_ integer,
	subtype varchar(75) null
) engine InnoDB;

create table SCFrameworkVersi_SCProductVers (
	frameworkVersionId bigint not null,
	productVersionId bigint not null,
	primary key (frameworkVersionId, productVersionId)
) engine InnoDB;

create table SCFrameworkVersion (
	frameworkVersionId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	name varchar(75) null,
	url longtext null,
	active_ tinyint,
	priority integer
) engine InnoDB;

create table SCLicense (
	licenseId bigint not null primary key,
	name varchar(75) null,
	url longtext null,
	openSource tinyint,
	active_ tinyint,
	recommended tinyint
) engine InnoDB;

create table SCLicenses_SCProductEntries (
	licenseId bigint not null,
	productEntryId bigint not null,
	primary key (licenseId, productEntryId)
) engine InnoDB;

create table SCProductEntry (
	productEntryId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	name varchar(75) null,
	type_ varchar(75) null,
	tags varchar(255) null,
	shortDescription longtext null,
	longDescription longtext null,
	pageURL longtext null,
	author varchar(75) null,
	repoGroupId varchar(75) null,
	repoArtifactId varchar(75) null
) engine InnoDB;

create table SCProductScreenshot (
	productScreenshotId bigint not null primary key,
	companyId bigint,
	groupId bigint,
	productEntryId bigint,
	thumbnailId bigint,
	fullImageId bigint,
	priority integer
) engine InnoDB;

create table SCProductVersion (
	productVersionId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	productEntryId bigint,
	version varchar(75) null,
	changeLog longtext null,
	downloadPageURL longtext null,
	directDownloadURL varchar(2000) null,
	repoStoreArtifact tinyint
) engine InnoDB;

create table ServiceComponent (
	serviceComponentId bigint not null primary key,
	buildNamespace varchar(75) null,
	buildNumber bigint,
	buildDate bigint,
	data_ longtext null
) engine InnoDB;

create table Shard (
	shardId bigint not null primary key,
	classNameId bigint,
	classPK bigint,
	name varchar(75) null
) engine InnoDB;

create table ShoppingCart (
	cartId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	itemIds longtext null,
	couponCodes varchar(75) null,
	altShipping integer,
	insure tinyint
) engine InnoDB;

create table ShoppingCategory (
	categoryId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	parentCategoryId bigint,
	name varchar(75) null,
	description longtext null
) engine InnoDB;

create table ShoppingCoupon (
	couponId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	code_ varchar(75) null,
	name varchar(75) null,
	description longtext null,
	startDate datetime null,
	endDate datetime null,
	active_ tinyint,
	limitCategories longtext null,
	limitSkus longtext null,
	minOrder double,
	discount double,
	discountType varchar(75) null
) engine InnoDB;

create table ShoppingItem (
	itemId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	categoryId bigint,
	sku varchar(75) null,
	name varchar(200) null,
	description longtext null,
	properties longtext null,
	fields_ tinyint,
	fieldsQuantities longtext null,
	minQuantity integer,
	maxQuantity integer,
	price double,
	discount double,
	taxable tinyint,
	shipping double,
	useShippingFormula tinyint,
	requiresShipping tinyint,
	stockQuantity integer,
	featured_ tinyint,
	sale_ tinyint,
	smallImage tinyint,
	smallImageId bigint,
	smallImageURL longtext null,
	mediumImage tinyint,
	mediumImageId bigint,
	mediumImageURL longtext null,
	largeImage tinyint,
	largeImageId bigint,
	largeImageURL longtext null
) engine InnoDB;

create table ShoppingItemField (
	itemFieldId bigint not null primary key,
	itemId bigint,
	name varchar(75) null,
	values_ longtext null,
	description longtext null
) engine InnoDB;

create table ShoppingItemPrice (
	itemPriceId bigint not null primary key,
	itemId bigint,
	minQuantity integer,
	maxQuantity integer,
	price double,
	discount double,
	taxable tinyint,
	shipping double,
	useShippingFormula tinyint,
	status integer
) engine InnoDB;

create table ShoppingOrder (
	orderId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	number_ varchar(75) null,
	tax double,
	shipping double,
	altShipping varchar(75) null,
	requiresShipping tinyint,
	insure tinyint,
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
	shipToBilling tinyint,
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
	ccExpMonth integer,
	ccExpYear integer,
	ccVerNumber varchar(75) null,
	comments longtext null,
	ppTxnId varchar(75) null,
	ppPaymentStatus varchar(75) null,
	ppPaymentGross double,
	ppReceiverEmail varchar(75) null,
	ppPayerEmail varchar(75) null,
	sendOrderEmail tinyint,
	sendShippingEmail tinyint
) engine InnoDB;

create table ShoppingOrderItem (
	orderItemId bigint not null primary key,
	orderId bigint,
	itemId varchar(75) null,
	sku varchar(75) null,
	name varchar(200) null,
	description longtext null,
	properties longtext null,
	price double,
	quantity integer,
	shippedDate datetime null
) engine InnoDB;

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
	type_ integer,
	extraData longtext null,
	receiverUserId bigint
) engine InnoDB;

create table SocialActivityAchievement (
	activityAchievementId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	createDate bigint,
	name varchar(75) null,
	firstInGroup tinyint
) engine InnoDB;

create table SocialActivityCounter (
	activityCounterId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	classNameId bigint,
	classPK bigint,
	name varchar(75) null,
	ownerType integer,
	currentValue integer,
	totalValue integer,
	graceValue integer,
	startPeriod integer,
	endPeriod integer,
	active_ tinyint
) engine InnoDB;

create table SocialActivityLimit (
	activityLimitId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	classNameId bigint,
	classPK bigint,
	activityType integer,
	activityCounterName varchar(75) null,
	value varchar(75) null
) engine InnoDB;

create table SocialActivitySet (
	activitySetId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	createDate bigint,
	modifiedDate bigint,
	classNameId bigint,
	classPK bigint,
	type_ integer,
	activityCount integer
) engine InnoDB;

create table SocialActivitySetting (
	activitySettingId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	classNameId bigint,
	activityType integer,
	name varchar(75) null,
	value varchar(1024) null
) engine InnoDB;

create table SocialRelation (
	uuid_ varchar(75) null,
	relationId bigint not null primary key,
	companyId bigint,
	createDate bigint,
	userId1 bigint,
	userId2 bigint,
	type_ integer
) engine InnoDB;

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
	type_ integer,
	extraData longtext null,
	receiverUserId bigint,
	status integer
) engine InnoDB;

create table Subscription (
	subscriptionId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	classNameId bigint,
	classPK bigint,
	frequency varchar(75) null
) engine InnoDB;

create table Team (
	teamId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	groupId bigint,
	name varchar(75) null,
	description longtext null
) engine InnoDB;

create table Ticket (
	ticketId bigint not null primary key,
	companyId bigint,
	createDate datetime null,
	classNameId bigint,
	classPK bigint,
	key_ varchar(75) null,
	type_ integer,
	extraInfo longtext null,
	expirationDate datetime null
) engine InnoDB;

create table TrashEntry (
	entryId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	classNameId bigint,
	classPK bigint,
	typeSettings longtext null,
	status integer
) engine InnoDB;

create table TrashVersion (
	versionId bigint not null primary key,
	entryId bigint,
	classNameId bigint,
	classPK bigint,
	status integer
) engine InnoDB;

create table User_ (
	uuid_ varchar(75) null,
	userId bigint not null primary key,
	companyId bigint,
	createDate datetime null,
	modifiedDate datetime null,
	defaultUser tinyint,
	contactId bigint,
	password_ varchar(75) null,
	passwordEncrypted tinyint,
	passwordReset tinyint,
	passwordModifiedDate datetime null,
	digest varchar(255) null,
	reminderQueryQuestion varchar(75) null,
	reminderQueryAnswer varchar(75) null,
	graceLoginCount integer,
	screenName varchar(75) null,
	emailAddress varchar(75) null,
	facebookId bigint,
	ldapServerId bigint,
	openId varchar(1024) null,
	portraitId bigint,
	languageId varchar(75) null,
	timeZoneId varchar(75) null,
	greeting varchar(255) null,
	comments longtext null,
	firstName varchar(75) null,
	middleName varchar(75) null,
	lastName varchar(75) null,
	jobTitle varchar(100) null,
	loginDate datetime null,
	loginIP varchar(75) null,
	lastLoginDate datetime null,
	lastLoginIP varchar(75) null,
	lastFailedLoginDate datetime null,
	failedLoginAttempts integer,
	lockout tinyint,
	lockoutDate datetime null,
	agreedToTermsOfUse tinyint,
	emailAddressVerified tinyint,
	status integer
) engine InnoDB;

create table UserGroup (
	uuid_ varchar(75) null,
	userGroupId bigint not null primary key,
	companyId bigint,
	parentUserGroupId bigint,
	name varchar(75) null,
	description longtext null,
	addedByLDAPImport tinyint
) engine InnoDB;

create table UserGroupGroupRole (
	userGroupId bigint not null,
	groupId bigint not null,
	roleId bigint not null,
	primary key (userGroupId, groupId, roleId)
) engine InnoDB;

create table UserGroupRole (
	userId bigint not null,
	groupId bigint not null,
	roleId bigint not null,
	primary key (userId, groupId, roleId)
) engine InnoDB;

create table UserGroups_Teams (
	userGroupId bigint not null,
	teamId bigint not null,
	primary key (userGroupId, teamId)
) engine InnoDB;

create table UserIdMapper (
	userIdMapperId bigint not null primary key,
	userId bigint,
	type_ varchar(75) null,
	description varchar(75) null,
	externalUserId varchar(75) null
) engine InnoDB;

create table UserNotificationEvent (
	uuid_ varchar(75) null,
	userNotificationEventId bigint not null primary key,
	companyId bigint,
	userId bigint,
	type_ varchar(75) null,
	timestamp bigint,
	deliverBy bigint,
	payload longtext null,
	archived tinyint
) engine InnoDB;

create table Users_Groups (
	userId bigint not null,
	groupId bigint not null,
	primary key (userId, groupId)
) engine InnoDB;

create table Users_Orgs (
	userId bigint not null,
	organizationId bigint not null,
	primary key (userId, organizationId)
) engine InnoDB;

create table Users_Roles (
	userId bigint not null,
	roleId bigint not null,
	primary key (userId, roleId)
) engine InnoDB;

create table Users_Teams (
	userId bigint not null,
	teamId bigint not null,
	primary key (userId, teamId)
) engine InnoDB;

create table Users_UserGroups (
	userGroupId bigint not null,
	userId bigint not null,
	primary key (userGroupId, userId)
) engine InnoDB;

create table UserTracker (
	userTrackerId bigint not null primary key,
	companyId bigint,
	userId bigint,
	modifiedDate datetime null,
	sessionId varchar(200) null,
	remoteAddr varchar(75) null,
	remoteHost varchar(75) null,
	userAgent varchar(200) null
) engine InnoDB;

create table UserTrackerPath (
	userTrackerPathId bigint not null primary key,
	userTrackerId bigint,
	path_ longtext null,
	pathDate datetime null
) engine InnoDB;

create table VirtualHost (
	virtualHostId bigint not null primary key,
	companyId bigint,
	layoutSetId bigint,
	hostname varchar(75) null
) engine InnoDB;

create table WebDAVProps (
	webDavPropsId bigint not null primary key,
	companyId bigint,
	createDate datetime null,
	modifiedDate datetime null,
	classNameId bigint,
	classPK bigint,
	props longtext null
) engine InnoDB;

create table Website (
	websiteId bigint not null primary key,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	classNameId bigint,
	classPK bigint,
	url longtext null,
	typeId integer,
	primary_ tinyint
) engine InnoDB;

create table WikiNode (
	uuid_ varchar(75) null,
	nodeId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	name varchar(75) null,
	description longtext null,
	lastPostDate datetime null,
	status integer,
	statusByUserId bigint,
	statusByUserName varchar(75) null,
	statusDate datetime null
) engine InnoDB;

create table WikiPage (
	uuid_ varchar(75) null,
	pageId bigint not null primary key,
	resourcePrimKey bigint,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	nodeId bigint,
	title varchar(255) null,
	version double,
	minorEdit tinyint,
	content longtext null,
	summary longtext null,
	format varchar(75) null,
	head tinyint,
	parentTitle varchar(255) null,
	redirectTitle varchar(255) null,
	status integer,
	statusByUserId bigint,
	statusByUserName varchar(75) null,
	statusDate datetime null
) engine InnoDB;

create table WikiPageResource (
	uuid_ varchar(75) null,
	resourcePrimKey bigint not null primary key,
	nodeId bigint,
	title varchar(255) null
) engine InnoDB;

create table WorkflowDefinitionLink (
	workflowDefinitionLinkId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	classNameId bigint,
	classPK bigint,
	typePK bigint,
	workflowDefinitionName varchar(75) null,
	workflowDefinitionVersion integer
) engine InnoDB;

create table WorkflowInstanceLink (
	workflowInstanceLinkId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null,
	classNameId bigint,
	classPK bigint,
	workflowInstanceId bigint
) engine InnoDB;


insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (1, 'canada', 'CA', 'CAN', '124', '001', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (2, 'china', 'CN', 'CHN', '156', '086', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (3, 'france', 'FR', 'FRA', '250', '033', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (4, 'germany', 'DE', 'DEU', '276', '049', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (5, 'hong-kong', 'HK', 'HKG', '344', '852', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (6, 'hungary', 'HU', 'HUN', '348', '036', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (7, 'israel', 'IL', 'ISR', '376', '972', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (8, 'italy', 'IT', 'ITA', '380', '039', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (9, 'japan', 'JP', 'JPN', '392', '081', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (10, 'south-korea', 'KR', 'KOR', '410', '082', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (11, 'netherlands', 'NL', 'NLD', '528', '031', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (12, 'portugal', 'PT', 'PRT', '620', '351', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (13, 'russia', 'RU', 'RUS', '643', '007', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (14, 'singapore', 'SG', 'SGP', '702', '065', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (15, 'spain', 'ES', 'ESP', '724', '034', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (16, 'turkey', 'TR', 'TUR', '792', '090', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (17, 'vietnam', 'VN', 'VNM', '704', '084', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (18, 'united-kingdom', 'GB', 'GBR', '826', '044', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (19, 'united-states', 'US', 'USA', '840', '001', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (20, 'afghanistan', 'AF', 'AFG', '4', '093', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (21, 'albania', 'AL', 'ALB', '8', '355', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (22, 'algeria', 'DZ', 'DZA', '12', '213', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (23, 'american-samoa', 'AS', 'ASM', '16', '684', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (24, 'andorra', 'AD', 'AND', '20', '376', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (25, 'angola', 'AO', 'AGO', '24', '244', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (26, 'anguilla', 'AI', 'AIA', '660', '264', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (27, 'antarctica', 'AQ', 'ATA', '10', '672', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (28, 'antigua-barbuda', 'AG', 'ATG', '28', '268', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (29, 'argentina', 'AR', 'ARG', '32', '054', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (30, 'armenia', 'AM', 'ARM', '51', '374', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (31, 'aruba', 'AW', 'ABW', '533', '297', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (32, 'australia', 'AU', 'AUS', '36', '061', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (33, 'austria', 'AT', 'AUT', '40', '043', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (34, 'azerbaijan', 'AZ', 'AZE', '31', '994', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (35, 'bahamas', 'BS', 'BHS', '44', '242', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (36, 'bahrain', 'BH', 'BHR', '48', '973', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (37, 'bangladesh', 'BD', 'BGD', '50', '880', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (38, 'barbados', 'BB', 'BRB', '52', '246', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (39, 'belarus', 'BY', 'BLR', '112', '375', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (40, 'belgium', 'BE', 'BEL', '56', '032', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (41, 'belize', 'BZ', 'BLZ', '84', '501', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (42, 'benin', 'BJ', 'BEN', '204', '229', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (43, 'bermuda', 'BM', 'BMU', '60', '441', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (44, 'bhutan', 'BT', 'BTN', '64', '975', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (45, 'bolivia', 'BO', 'BOL', '68', '591', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (46, 'bosnia-herzegovina', 'BA', 'BIH', '70', '387', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (47, 'botswana', 'BW', 'BWA', '72', '267', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (48, 'brazil', 'BR', 'BRA', '76', '055', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (49, 'british-virgin-islands', 'VG', 'VGB', '92', '284', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (50, 'brunei', 'BN', 'BRN', '96', '673', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (51, 'bulgaria', 'BG', 'BGR', '100', '359', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (52, 'burkina-faso', 'BF', 'BFA', '854', '226', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (53, 'burma-myanmar', 'MM', 'MMR', '104', '095', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (54, 'burundi', 'BI', 'BDI', '108', '257', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (55, 'cambodia', 'KH', 'KHM', '116', '855', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (56, 'cameroon', 'CM', 'CMR', '120', '237', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (57, 'cape-verde-island', 'CV', 'CPV', '132', '238', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (58, 'cayman-islands', 'KY', 'CYM', '136', '345', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (59, 'central-african-republic', 'CF', 'CAF', '140', '236', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (60, 'chad', 'TD', 'TCD', '148', '235', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (61, 'chile', 'CL', 'CHL', '152', '056', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (62, 'christmas-island', 'CX', 'CXR', '162', '061', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (63, 'cocos-islands', 'CC', 'CCK', '166', '061', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (64, 'colombia', 'CO', 'COL', '170', '057', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (65, 'comoros', 'KM', 'COM', '174', '269', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (66, 'republic-of-congo', 'CD', 'COD', '180', '242', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (67, 'democratic-republic-of-congo', 'CG', 'COG', '178', '243', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (68, 'cook-islands', 'CK', 'COK', '184', '682', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (69, 'costa-rica', 'CR', 'CRI', '188', '506', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (70, 'croatia', 'HR', 'HRV', '191', '385', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (71, 'cuba', 'CU', 'CUB', '192', '053', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (72, 'cyprus', 'CY', 'CYP', '196', '357', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (73, 'czech-republic', 'CZ', 'CZE', '203', '420', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (74, 'denmark', 'DK', 'DNK', '208', '045', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (75, 'djibouti', 'DJ', 'DJI', '262', '253', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (76, 'dominica', 'DM', 'DMA', '212', '767', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (77, 'dominican-republic', 'DO', 'DOM', '214', '809', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (78, 'ecuador', 'EC', 'ECU', '218', '593', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (79, 'egypt', 'EG', 'EGY', '818', '020', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (80, 'el-salvador', 'SV', 'SLV', '222', '503', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (81, 'equatorial-guinea', 'GQ', 'GNQ', '226', '240', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (82, 'eritrea', 'ER', 'ERI', '232', '291', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (83, 'estonia', 'EE', 'EST', '233', '372', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (84, 'ethiopia', 'ET', 'ETH', '231', '251', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (85, 'faeroe-islands', 'FO', 'FRO', '234', '298', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (86, 'falkland-islands', 'FK', 'FLK', '238', '500', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (87, 'fiji-islands', 'FJ', 'FJI', '242', '679', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (88, 'finland', 'FI', 'FIN', '246', '358', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (89, 'french-guiana', 'GF', 'GUF', '254', '594', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (90, 'french-polynesia', 'PF', 'PYF', '258', '689', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (91, 'gabon', 'GA', 'GAB', '266', '241', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (92, 'gambia', 'GM', 'GMB', '270', '220', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (93, 'georgia', 'GE', 'GEO', '268', '995', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (94, 'ghana', 'GH', 'GHA', '288', '233', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (95, 'gibraltar', 'GI', 'GIB', '292', '350', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (96, 'greece', 'GR', 'GRC', '300', '030', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (97, 'greenland', 'GL', 'GRL', '304', '299', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (98, 'grenada', 'GD', 'GRD', '308', '473', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (99, 'guadeloupe', 'GP', 'GLP', '312', '590', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (100, 'guam', 'GU', 'GUM', '316', '671', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (101, 'guatemala', 'GT', 'GTM', '320', '502', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (102, 'guinea', 'GN', 'GIN', '324', '224', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (103, 'guinea-bissau', 'GW', 'GNB', '624', '245', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (104, 'guyana', 'GY', 'GUY', '328', '592', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (105, 'haiti', 'HT', 'HTI', '332', '509', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (106, 'honduras', 'HN', 'HND', '340', '504', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (107, 'iceland', 'IS', 'ISL', '352', '354', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (108, 'india', 'IN', 'IND', '356', '091', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (109, 'indonesia', 'ID', 'IDN', '360', '062', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (110, 'iran', 'IR', 'IRN', '364', '098', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (111, 'iraq', 'IQ', 'IRQ', '368', '964', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (112, 'ireland', 'IE', 'IRL', '372', '353', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (113, 'ivory-coast', 'CI', 'CIV', '384', '225', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (114, 'jamaica', 'JM', 'JAM', '388', '876', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (115, 'jordan', 'JO', 'JOR', '400', '962', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (116, 'kazakhstan', 'KZ', 'KAZ', '398', '007', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (117, 'kenya', 'KE', 'KEN', '404', '254', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (118, 'kiribati', 'KI', 'KIR', '408', '686', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (119, 'kuwait', 'KW', 'KWT', '414', '965', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (120, 'north-korea', 'KP', 'PRK', '408', '850', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (121, 'kyrgyzstan', 'KG', 'KGZ', '471', '996', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (122, 'laos', 'LA', 'LAO', '418', '856', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (123, 'latvia', 'LV', 'LVA', '428', '371', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (124, 'lebanon', 'LB', 'LBN', '422', '961', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (125, 'lesotho', 'LS', 'LSO', '426', '266', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (126, 'liberia', 'LR', 'LBR', '430', '231', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (127, 'libya', 'LY', 'LBY', '434', '218', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (128, 'liechtenstein', 'LI', 'LIE', '438', '423', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (129, 'lithuania', 'LT', 'LTU', '440', '370', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (130, 'luxembourg', 'LU', 'LUX', '442', '352', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (131, 'macau', 'MO', 'MAC', '446', '853', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (132, 'macedonia', 'MK', 'MKD', '807', '389', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (133, 'madagascar', 'MG', 'MDG', '450', '261', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (134, 'malawi', 'MW', 'MWI', '454', '265', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (135, 'malaysia', 'MY', 'MYS', '458', '060', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (136, 'maldives', 'MV', 'MDV', '462', '960', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (137, 'mali', 'ML', 'MLI', '466', '223', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (138, 'malta', 'MT', 'MLT', '470', '356', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (139, 'marshall-islands', 'MH', 'MHL', '584', '692', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (140, 'martinique', 'MQ', 'MTQ', '474', '596', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (141, 'mauritania', 'MR', 'MRT', '478', '222', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (142, 'mauritius', 'MU', 'MUS', '480', '230', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (143, 'mayotte-island', 'YT', 'MYT', '175', '269', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (144, 'mexico', 'MX', 'MEX', '484', '052', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (145, 'micronesia', 'FM', 'FSM', '583', '691', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (146, 'moldova', 'MD', 'MDA', '498', '373', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (147, 'monaco', 'MC', 'MCO', '492', '377', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (148, 'mongolia', 'MN', 'MNG', '496', '976', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (149, 'montenegro', 'ME', 'MNE', '499', '382', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (150, 'montserrat', 'MS', 'MSR', '500', '664', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (151, 'morocco', 'MA', 'MAR', '504', '212', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (152, 'mozambique', 'MZ', 'MOZ', '508', '258', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (153, 'namibia', 'NA', 'NAM', '516', '264', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (154, 'nauru', 'NR', 'NRU', '520', '674', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (155, 'nepal', 'NP', 'NPL', '524', '977', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (156, 'netherlands-antilles', 'AN', 'ANT', '530', '599', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (157, 'new-caledonia', 'NC', 'NCL', '540', '687', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (158, 'new-zealand', 'NZ', 'NZL', '554', '064', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (159, 'nicaragua', 'NI', 'NIC', '558', '505', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (160, 'niger', 'NE', 'NER', '562', '227', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (161, 'nigeria', 'NG', 'NGA', '566', '234', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (162, 'niue', 'NU', 'NIU', '570', '683', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (163, 'norfolk-island', 'NF', 'NFK', '574', '672', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (164, 'norway', 'NO', 'NOR', '578', '047', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (165, 'oman', 'OM', 'OMN', '512', '968', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (166, 'pakistan', 'PK', 'PAK', '586', '092', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (167, 'palau', 'PW', 'PLW', '585', '680', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (168, 'palestine', 'PS', 'PSE', '275', '970', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (169, 'panama', 'PA', 'PAN', '591', '507', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (170, 'papua-new-guinea', 'PG', 'PNG', '598', '675', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (171, 'paraguay', 'PY', 'PRY', '600', '595', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (172, 'peru', 'PE', 'PER', '604', '051', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (173, 'philippines', 'PH', 'PHL', '608', '063', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (174, 'poland', 'PL', 'POL', '616', '048', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (175, 'puerto-rico', 'PR', 'PRI', '630', '787', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (176, 'qatar', 'QA', 'QAT', '634', '974', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (177, 'reunion-island', 'RE', 'REU', '638', '262', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (178, 'romania', 'RO', 'ROU', '642', '040', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (179, 'rwanda', 'RW', 'RWA', '646', '250', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (180, 'st-helena', 'SH', 'SHN', '654', '290', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (181, 'st-kitts', 'KN', 'KNA', '659', '869', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (182, 'st-lucia', 'LC', 'LCA', '662', '758', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (183, 'st-pierre-miquelon', 'PM', 'SPM', '666', '508', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (184, 'st-vincent', 'VC', 'VCT', '670', '784', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (185, 'san-marino', 'SM', 'SMR', '674', '378', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (186, 'sao-tome-principe', 'ST', 'STP', '678', '239', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (187, 'saudi-arabia', 'SA', 'SAU', '682', '966', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (188, 'senegal', 'SN', 'SEN', '686', '221', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (189, 'serbia', 'RS', 'SRB', '688', '381', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (190, 'seychelles', 'SC', 'SYC', '690', '248', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (191, 'sierra-leone', 'SL', 'SLE', '694', '249', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (192, 'slovakia', 'SK', 'SVK', '703', '421', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (193, 'slovenia', 'SI', 'SVN', '705', '386', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (194, 'solomon-islands', 'SB', 'SLB', '90', '677', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (195, 'somalia', 'SO', 'SOM', '706', '252', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (196, 'south-africa', 'ZA', 'ZAF', '710', '027', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (197, 'sri-lanka', 'LK', 'LKA', '144', '094', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (198, 'sudan', 'SD', 'SDN', '736', '095', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (199, 'suriname', 'SR', 'SUR', '740', '597', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (200, 'swaziland', 'SZ', 'SWZ', '748', '268', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (201, 'sweden', 'SE', 'SWE', '752', '046', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (202, 'switzerland', 'CH', 'CHE', '756', '041', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (203, 'syria', 'SY', 'SYR', '760', '963', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (204, 'taiwan', 'TW', 'TWN', '158', '886', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (205, 'tajikistan', 'TJ', 'TJK', '762', '992', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (206, 'tanzania', 'TZ', 'TZA', '834', '255', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (207, 'thailand', 'TH', 'THA', '764', '066', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (208, 'togo', 'TG', 'TGO', '768', '228', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (209, 'tonga', 'TO', 'TON', '776', '676', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (210, 'trinidad-tobago', 'TT', 'TTO', '780', '868', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (211, 'tunisia', 'TN', 'TUN', '788', '216', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (212, 'turkmenistan', 'TM', 'TKM', '795', '993', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (213, 'turks-caicos', 'TC', 'TCA', '796', '649', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (214, 'tuvalu', 'TV', 'TUV', '798', '688', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (215, 'uganda', 'UG', 'UGA', '800', '256', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (216, 'ukraine', 'UA', 'UKR', '804', '380', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (217, 'united-arab-emirates', 'AE', 'ARE', '784', '971', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (218, 'uruguay', 'UY', 'URY', '858', '598', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (219, 'uzbekistan', 'UZ', 'UZB', '860', '998', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (220, 'vanuatu', 'VU', 'VUT', '548', '678', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (221, 'vatican-city', 'VA', 'VAT', '336', '039', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (222, 'venezuela', 'VE', 'VEN', '862', '058', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (223, 'wallis-futuna', 'WF', 'WLF', '876', '681', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (224, 'western-samoa', 'WS', 'WSM', '882', '685', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (225, 'yemen', 'YE', 'YEM', '887', '967', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (226, 'zambia', 'ZM', 'ZMB', '894', '260', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (227, 'zimbabwe', 'ZW', 'ZWE', '716', '263', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (228, 'aland-islands', 'AX', 'ALA', '248', '359', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (229, 'bonaire-st-eustatius-saba', 'BQ', 'BES', '535', '599', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (230, 'bouvet-island', 'BV', 'BVT', '74', '047', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (231, 'british-indian-ocean-territory', 'IO', 'IOT', '86', '246', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (232, 'curacao', 'CW', 'CUW', '531', '599', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (233, 'french-southern-territories', 'TF', 'ATF', '260', '033', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (234, 'guernsey', 'GG', 'GGY', '831', '044', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (235, 'heard-island-mcdonald-islands', 'HM', 'HMD', '334', '061', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (236, 'isle-of-man', 'IM', 'IMN', '833', '044', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (237, 'jersey', 'JE', 'JEY', '832', '044', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (238, 'northern-mariana-islands', 'MP', 'MNP', '580', '670', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (239, 'pitcairn', 'PN', 'PCN', '612', '649', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (240, 'south-georgia-south-sandwich-islands', 'GS', 'SGS', '239', '044', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (241, 'south-sudan', 'SS', 'SSD', '728', '211', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (242, 'sint-maarten', 'SX', 'SXM', '534', '721', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (243, 'st-barthelemy', 'BL', 'BLM', '652', '590', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (244, 'st-martin', 'MF', 'MAF', '663', '590', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (245, 'tokelau', 'TK', 'TKL', '772', '690', 0, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (246, 'timor-leste', 'TL', 'TLS', '626', '670', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (247, 'united-states-minor-outlying-islands', 'UM', 'UMI', '581', '699', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (248, 'united-states-virgin-islands', 'VI', 'VIR', '850', '340', 1, 1);
insert into Country (countryId, name, a2, a3, number_, idd_, zipRequired, active_) values (249, 'western-sahara', 'EH', 'ESH', '732', '212', 1, 1);

insert into Region (regionId, countryId, regionCode, name, active_) values (1001, 1, 'AB', 'Alberta', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (1002, 1, 'BC', 'British Columbia', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (1003, 1, 'MB', 'Manitoba', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (1004, 1, 'NB', 'New Brunswick', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (1005, 1, 'NL', 'Newfoundland and Labrador', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (1006, 1, 'NT', 'Northwest Territories', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (1007, 1, 'NS', 'Nova Scotia', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (1008, 1, 'NU', 'Nunavut', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (1009, 1, 'ON', 'Ontario', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (1010, 1, 'PE', 'Prince Edward Island', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (1011, 1, 'QC', 'Quebec', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (1012, 1, 'SK', 'Saskatchewan', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (1013, 1, 'YT', 'Yukon', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (2001, 2, 'CN-34', 'Anhui', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (2002, 2, 'CN-92', 'Aomen', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (2003, 2, 'CN-11', 'Beijing', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (2004, 2, 'CN-50', 'Chongqing', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (2005, 2, 'CN-35', 'Fujian', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (2006, 2, 'CN-62', 'Gansu', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (2007, 2, 'CN-44', 'Guangdong', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (2008, 2, 'CN-45', 'Guangxi', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (2009, 2, 'CN-52', 'Guizhou', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (2010, 2, 'CN-46', 'Hainan', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (2011, 2, 'CN-13', 'Hebei', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (2012, 2, 'CN-23', 'Heilongjiang', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (2013, 2, 'CN-41', 'Henan', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (2014, 2, 'CN-42', 'Hubei', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (2015, 2, 'CN-43', 'Hunan', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (2016, 2, 'CN-32', 'Jiangsu', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (2017, 2, 'CN-36', 'Jiangxi', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (2018, 2, 'CN-22', 'Jilin', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (2019, 2, 'CN-21', 'Liaoning', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (2020, 2, 'CN-15', 'Nei Mongol', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (2021, 2, 'CN-64', 'Ningxia', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (2022, 2, 'CN-63', 'Qinghai', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (2023, 2, 'CN-61', 'Shaanxi', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (2024, 2, 'CN-37', 'Shandong', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (2025, 2, 'CN-31', 'Shanghai', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (2026, 2, 'CN-14', 'Shanxi', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (2027, 2, 'CN-51', 'Sichuan', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (2028, 2, 'CN-71', 'Taiwan', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (2029, 2, 'CN-12', 'Tianjin', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (2030, 2, 'CN-91', 'Xianggang', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (2031, 2, 'CN-65', 'Xinjiang', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (2032, 2, 'CN-54', 'Xizang', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (2033, 2, 'CN-53', 'Yunnan', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (2034, 2, 'CN-33', 'Zhejiang', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (3001, 3, 'A', 'Alsace', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (3002, 3, 'B', 'Aquitaine', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (3003, 3, 'C', 'Auvergne', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (3004, 3, 'P', 'Basse-Normandie', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (3005, 3, 'D', 'Bourgogne', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (3006, 3, 'E', 'Bretagne', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (3007, 3, 'F', 'Centre', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (3008, 3, 'G', 'Champagne-Ardenne', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (3009, 3, 'H', 'Corse', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (3010, 3, 'GF', 'Guyane', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (3011, 3, 'I', 'Franche Comté', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (3012, 3, 'GP', 'Guadeloupe', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (3013, 3, 'Q', 'Haute-Normandie', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (3014, 3, 'J', 'Île-de-France', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (3015, 3, 'K', 'Languedoc-Roussillon', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (3016, 3, 'L', 'Limousin', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (3017, 3, 'M', 'Lorraine', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (3018, 3, 'MQ', 'Martinique', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (3019, 3, 'N', 'Midi-Pyrénées', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (3020, 3, 'O', 'Nord Pas de Calais', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (3021, 3, 'R', 'Pays de la Loire', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (3022, 3, 'S', 'Picardie', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (3023, 3, 'T', 'Poitou-Charentes', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (3024, 3, 'U', 'Provence-Alpes-Côte-d''Azur', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (3025, 3, 'RE', 'Réunion', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (3026, 3, 'V', 'Rhône-Alpes', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (4001, 4, 'BW', 'Baden-Württemberg', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (4002, 4, 'BY', 'Bayern', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (4003, 4, 'BE', 'Berlin', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (4004, 4, 'BR', 'Brandenburg', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (4005, 4, 'HB', 'Bremen', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (4006, 4, 'HH', 'Hamburg', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (4007, 4, 'HE', 'Hessen', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (4008, 4, 'MV', 'Mecklenburg-Vorpommern', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (4009, 4, 'NI', 'Niedersachsen', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (4010, 4, 'NW', 'Nordrhein-Westfalen', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (4011, 4, 'RP', 'Rheinland-Pfalz', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (4012, 4, 'SL', 'Saarland', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (4013, 4, 'SN', 'Sachsen', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (4014, 4, 'ST', 'Sachsen-Anhalt', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (4015, 4, 'SH', 'Schleswig-Holstein', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (4016, 4, 'TH', 'Thüringen', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8001, 8, 'AG', 'Agrigento', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8002, 8, 'AL', 'Alessandria', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8003, 8, 'AN', 'Ancona', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8004, 8, 'AO', 'Aosta', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8005, 8, 'AR', 'Arezzo', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8006, 8, 'AP', 'Ascoli Piceno', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8007, 8, 'AT', 'Asti', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8008, 8, 'AV', 'Avellino', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8009, 8, 'BA', 'Bari', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8010, 8, 'BT', 'Barletta-Andria-Trani', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8011, 8, 'BL', 'Belluno', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8012, 8, 'BN', 'Benevento', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8013, 8, 'BG', 'Bergamo', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8014, 8, 'BI', 'Biella', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8015, 8, 'BO', 'Bologna', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8016, 8, 'BZ', 'Bolzano', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8017, 8, 'BS', 'Brescia', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8018, 8, 'BR', 'Brindisi', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8019, 8, 'CA', 'Cagliari', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8020, 8, 'CL', 'Caltanissetta', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8021, 8, 'CB', 'Campobasso', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8022, 8, 'CI', 'Carbonia-Iglesias', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8023, 8, 'CE', 'Caserta', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8024, 8, 'CT', 'Catania', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8025, 8, 'CZ', 'Catanzaro', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8026, 8, 'CH', 'Chieti', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8027, 8, 'CO', 'Como', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8028, 8, 'CS', 'Cosenza', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8029, 8, 'CR', 'Cremona', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8030, 8, 'KR', 'Crotone', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8031, 8, 'CN', 'Cuneo', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8032, 8, 'EN', 'Enna', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8033, 8, 'FM', 'Fermo', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8034, 8, 'FE', 'Ferrara', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8035, 8, 'FI', 'Firenze', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8036, 8, 'FG', 'Foggia', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8037, 8, 'FC', 'Forli-Cesena', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8038, 8, 'FR', 'Frosinone', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8039, 8, 'GE', 'Genova', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8040, 8, 'GO', 'Gorizia', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8041, 8, 'GR', 'Grosseto', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8042, 8, 'IM', 'Imperia', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8043, 8, 'IS', 'Isernia', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8044, 8, 'AQ', 'L''Aquila', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8045, 8, 'SP', 'La Spezia', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8046, 8, 'LT', 'Latina', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8047, 8, 'LE', 'Lecce', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8048, 8, 'LC', 'Lecco', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8049, 8, 'LI', 'Livorno', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8050, 8, 'LO', 'Lodi', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8051, 8, 'LU', 'Lucca', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8052, 8, 'MC', 'Macerata', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8053, 8, 'MN', 'Mantova', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8054, 8, 'MS', 'Massa-Carrara', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8055, 8, 'MT', 'Matera', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8056, 8, 'MA', 'Medio Campidano', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8057, 8, 'ME', 'Messina', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8058, 8, 'MI', 'Milano', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8059, 8, 'MO', 'Modena', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8060, 8, 'MZ', 'Monza', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8061, 8, 'NA', 'Napoli', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8062, 8, 'NO', 'Novara', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8063, 8, 'NU', 'Nuoro', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8064, 8, 'OG', 'Ogliastra', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8065, 8, 'OT', 'Olbia-Tempio', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8066, 8, 'OR', 'Oristano', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8067, 8, 'PD', 'Padova', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8068, 8, 'PA', 'Palermo', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8069, 8, 'PR', 'Parma', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8070, 8, 'PV', 'Pavia', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8071, 8, 'PG', 'Perugia', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8072, 8, 'PU', 'Pesaro e Urbino', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8073, 8, 'PE', 'Pescara', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8074, 8, 'PC', 'Piacenza', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8075, 8, 'PI', 'Pisa', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8076, 8, 'PT', 'Pistoia', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8077, 8, 'PN', 'Pordenone', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8078, 8, 'PZ', 'Potenza', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8079, 8, 'PO', 'Prato', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8080, 8, 'RG', 'Ragusa', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8081, 8, 'RA', 'Ravenna', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8082, 8, 'RC', 'Reggio Calabria', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8083, 8, 'RE', 'Reggio Emilia', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8084, 8, 'RI', 'Rieti', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8085, 8, 'RN', 'Rimini', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8086, 8, 'RM', 'Roma', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8087, 8, 'RO', 'Rovigo', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8088, 8, 'SA', 'Salerno', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8089, 8, 'SS', 'Sassari', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8090, 8, 'SV', 'Savona', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8091, 8, 'SI', 'Siena', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8092, 8, 'SR', 'Siracusa', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8093, 8, 'SO', 'Sondrio', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8094, 8, 'TA', 'Taranto', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8095, 8, 'TE', 'Teramo', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8096, 8, 'TR', 'Terni', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8097, 8, 'TO', 'Torino', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8098, 8, 'TP', 'Trapani', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8099, 8, 'TN', 'Trento', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8100, 8, 'TV', 'Treviso', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8101, 8, 'TS', 'Trieste', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8102, 8, 'UD', 'Udine', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8103, 8, 'VA', 'Varese', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8104, 8, 'VE', 'Venezia', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8105, 8, 'VB', 'Verbano-Cusio-Ossola', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8106, 8, 'VC', 'Vercelli', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8107, 8, 'VR', 'Verona', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8108, 8, 'VV', 'Vibo Valentia', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8109, 8, 'VI', 'Vicenza', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (8110, 8, 'VT', 'Viterbo', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (11001, 11, 'DR', 'Drenthe', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (11002, 11, 'FL', 'Flevoland', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (11003, 11, 'FR', 'Friesland', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (11004, 11, 'GE', 'Gelderland', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (11005, 11, 'GR', 'Groningen', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (11006, 11, 'LI', 'Limburg', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (11007, 11, 'NB', 'Noord-Brabant', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (11008, 11, 'NH', 'Noord-Holland', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (11009, 11, 'OV', 'Overijssel', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (11010, 11, 'UT', 'Utrecht', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (11011, 11, 'ZE', 'Zeeland', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (11012, 11, 'ZH', 'Zuid-Holland', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (15001, 15, 'AN', 'Andalusia', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (15002, 15, 'AR', 'Aragon', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (15003, 15, 'AS', 'Asturias', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (15004, 15, 'IB', 'Balearic Islands', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (15005, 15, 'PV', 'Basque Country', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (15006, 15, 'CN', 'Canary Islands', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (15007, 15, 'CB', 'Cantabria', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (15008, 15, 'CL', 'Castile and Leon', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (15009, 15, 'CM', 'Castile-La Mancha', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (15010, 15, 'CT', 'Catalonia', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (15011, 15, 'CE', 'Ceuta', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (15012, 15, 'EX', 'Extremadura', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (15013, 15, 'GA', 'Galicia', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (15014, 15, 'LO', 'La Rioja', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (15015, 15, 'M', 'Madrid', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (15016, 15, 'ML', 'Melilla', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (15017, 15, 'MU', 'Murcia', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (15018, 15, 'NA', 'Navarra', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (15019, 15, 'VC', 'Valencia', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19001, 19, 'AL', 'Alabama', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19002, 19, 'AK', 'Alaska', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19003, 19, 'AZ', 'Arizona', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19004, 19, 'AR', 'Arkansas', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19005, 19, 'CA', 'California', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19006, 19, 'CO', 'Colorado', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19007, 19, 'CT', 'Connecticut', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19008, 19, 'DC', 'District of Columbia', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19009, 19, 'DE', 'Delaware', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19010, 19, 'FL', 'Florida', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19011, 19, 'GA', 'Georgia', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19012, 19, 'HI', 'Hawaii', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19013, 19, 'ID', 'Idaho', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19014, 19, 'IL', 'Illinois', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19015, 19, 'IN', 'Indiana', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19016, 19, 'IA', 'Iowa', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19017, 19, 'KS', 'Kansas', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19018, 19, 'KY', 'Kentucky ', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19019, 19, 'LA', 'Louisiana ', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19020, 19, 'ME', 'Maine', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19021, 19, 'MD', 'Maryland', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19022, 19, 'MA', 'Massachusetts', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19023, 19, 'MI', 'Michigan', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19024, 19, 'MN', 'Minnesota', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19025, 19, 'MS', 'Mississippi', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19026, 19, 'MO', 'Missouri', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19027, 19, 'MT', 'Montana', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19028, 19, 'NE', 'Nebraska', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19029, 19, 'NV', 'Nevada', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19030, 19, 'NH', 'New Hampshire', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19031, 19, 'NJ', 'New Jersey', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19032, 19, 'NM', 'New Mexico', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19033, 19, 'NY', 'New York', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19034, 19, 'NC', 'North Carolina', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19035, 19, 'ND', 'North Dakota', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19036, 19, 'OH', 'Ohio', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19037, 19, 'OK', 'Oklahoma ', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19038, 19, 'OR', 'Oregon', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19039, 19, 'PA', 'Pennsylvania', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19040, 19, 'PR', 'Puerto Rico', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19041, 19, 'RI', 'Rhode Island', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19042, 19, 'SC', 'South Carolina', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19043, 19, 'SD', 'South Dakota', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19044, 19, 'TN', 'Tennessee', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19045, 19, 'TX', 'Texas', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19046, 19, 'UT', 'Utah', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19047, 19, 'VT', 'Vermont', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19048, 19, 'VA', 'Virginia', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19049, 19, 'WA', 'Washington', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19050, 19, 'WV', 'West Virginia', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19051, 19, 'WI', 'Wisconsin', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (19052, 19, 'WY', 'Wyoming', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (32001, 32, 'ACT', 'Australian Capital Territory', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (32002, 32, 'NSW', 'New South Wales', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (32003, 32, 'NT', 'Northern Territory', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (32004, 32, 'QLD', 'Queensland', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (32005, 32, 'SA', 'South Australia', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (32006, 32, 'TAS', 'Tasmania', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (32007, 32, 'VIC', 'Victoria', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (32008, 32, 'WA', 'Western Australia', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (144001, 144, 'MX-AGS', 'Aguascalientes', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (144002, 144, 'MX-BCN', 'Baja California', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (144003, 144, 'MX-BCS', 'Baja California Sur', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (144004, 144, 'MX-CAM', 'Campeche', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (144005, 144, 'MX-CHP', 'Chiapas', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (144006, 144, 'MX-CHI', 'Chihuahua', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (144007, 144, 'MX-COA', 'Coahuila', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (144008, 144, 'MX-COL', 'Colima', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (144009, 144, 'MX-DUR', 'Durango', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (144010, 144, 'MX-GTO', 'Guanajuato', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (144011, 144, 'MX-GRO', 'Guerrero', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (144012, 144, 'MX-HGO', 'Hidalgo', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (144013, 144, 'MX-JAL', 'Jalisco', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (144014, 144, 'MX-MEX', 'Mexico', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (144015, 144, 'MX-MIC', 'Michoacan', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (144016, 144, 'MX-MOR', 'Morelos', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (144017, 144, 'MX-NAY', 'Nayarit', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (144018, 144, 'MX-NLE', 'Nuevo Leon', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (144019, 144, 'MX-OAX', 'Oaxaca', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (144020, 144, 'MX-PUE', 'Puebla', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (144021, 144, 'MX-QRO', 'Queretaro', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (144023, 144, 'MX-ROO', 'Quintana Roo', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (144024, 144, 'MX-SLP', 'San Luis Potosí', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (144025, 144, 'MX-SIN', 'Sinaloa', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (144026, 144, 'MX-SON', 'Sonora', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (144027, 144, 'MX-TAB', 'Tabasco', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (144028, 144, 'MX-TAM', 'Tamaulipas', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (144029, 144, 'MX-TLX', 'Tlaxcala', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (144030, 144, 'MX-VER', 'Veracruz', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (144031, 144, 'MX-YUC', 'Yucatan', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (144032, 144, 'MX-ZAC', 'Zacatecas', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (164001, 164, '01', 'Østfold', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (164002, 164, '02', 'Akershus', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (164003, 164, '03', 'Oslo', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (164004, 164, '04', 'Hedmark', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (164005, 164, '05', 'Oppland', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (164006, 164, '06', 'Buskerud', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (164007, 164, '07', 'Vestfold', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (164008, 164, '08', 'Telemark', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (164009, 164, '09', 'Aust-Agder', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (164010, 164, '10', 'Vest-Agder', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (164011, 164, '11', 'Rogaland', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (164012, 164, '12', 'Hordaland', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (164013, 164, '14', 'Sogn og Fjordane', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (164014, 164, '15', 'Møre of Romsdal', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (164015, 164, '16', 'Sør-Trøndelag', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (164016, 164, '17', 'Nord-Trøndelag', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (164017, 164, '18', 'Nordland', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (164018, 164, '19', 'Troms', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (164019, 164, '20', 'Finnmark', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (202001, 202, 'AG', 'Aargau', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (202002, 202, 'AR', 'Appenzell Ausserrhoden', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (202003, 202, 'AI', 'Appenzell Innerrhoden', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (202004, 202, 'BL', 'Basel-Landschaft', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (202005, 202, 'BS', 'Basel-Stadt', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (202006, 202, 'BE', 'Bern', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (202007, 202, 'FR', 'Fribourg', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (202008, 202, 'GE', 'Geneva', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (202009, 202, 'GL', 'Glarus', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (202010, 202, 'GR', 'Graubünden', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (202011, 202, 'JU', 'Jura', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (202012, 202, 'LU', 'Lucerne', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (202013, 202, 'NE', 'Neuchâtel', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (202014, 202, 'NW', 'Nidwalden', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (202015, 202, 'OW', 'Obwalden', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (202016, 202, 'SH', 'Schaffhausen', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (202017, 202, 'SZ', 'Schwyz', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (202018, 202, 'SO', 'Solothurn', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (202019, 202, 'SG', 'St. Gallen', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (202020, 202, 'TG', 'Thurgau', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (202021, 202, 'TI', 'Ticino', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (202022, 202, 'UR', 'Uri', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (202023, 202, 'VS', 'Valais', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (202024, 202, 'VD', 'Vaud', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (202025, 202, 'ZG', 'Zug', 1);
insert into Region (regionId, countryId, regionCode, name, active_) values (202026, 202, 'ZH', 'Zürich', 1);

##
## List types for accounts
##

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

##
## List types for contacts
##

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

##
## List types for organizations
##

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


insert into Counter values ('com.liferay.counter.model.Counter', 10000);






insert into Company (companyId, accountId, webId, mx, system, active_) values (1, 7, 'liferay.com', 'liferay.com', 0, 1);
insert into Account_ (accountId, companyId, userId, userName, createDate, modifiedDate, parentAccountId, name, legalName, legalId, legalType, sicCode, tickerSymbol, industry, type_, size_) values (7, 1, 5, '', now(), now(), 0, 'Liferay', 'Liferay, Inc.', '', '', '', '', '', '', '');
insert into VirtualHost (virtualHostId, companyId, layoutSetId, hostname) values (8, 1, 0, 'localhost');


insert into ClassName_ (classNameId, value) values (9, 'com.liferay.portal.model.Company');
insert into ClassName_ (classNameId, value) values (10, 'com.liferay.portal.model.Group');
insert into ClassName_ (classNameId, value) values (11, 'com.liferay.portal.model.Organization');
insert into ClassName_ (classNameId, value) values (12, 'com.liferay.portal.model.Role');
insert into ClassName_ (classNameId, value) values (13, 'com.liferay.portal.model.User');

insert into Shard (shardId, classNameId, classPK, name) values (14, 9, 1, 'default');


insert into Role_ (roleId, companyId, classNameId, classPK, name, description, type_) values (15, 1, 12, 15, 'Administrator', '', 1);
insert into Role_ (roleId, companyId, classNameId, classPK, name, description, type_) values (16, 1, 12, 16, 'Guest', '', 1);
insert into Role_ (roleId, companyId, classNameId, classPK, name, description, type_) values (17, 1, 12, 17, 'Power User', '', 1);
insert into Role_ (roleId, companyId, classNameId, classPK, name, description, type_) values (18, 1, 12, 18, 'User', '', 1);


insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, site, active_) values (19, 1, 5, 10, 19, 0, 0, 'Guest', 1, '/guest', 1, 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (20, 1, 19, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (21, 1, 19, 0, 0, 'classic', '01', 0);


insert into Organization_ (organizationId, companyId, parentOrganizationId, name, type_, recursable, regionId, countryId, statusId, comments) values (22, 1, 0, 'Liferay, Inc.', 'regular-organization', 1, 19005, 19, 12017, '');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, site, active_) values (23, 1, 5, 11, 22, 0, 0, '23', 0, '/23', 1, 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (24, 1, 23, 1, 0, 'classic', '01', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (25, 1, 23, 0, 0, 'classic', '01', 1);
insert into Layout (uuid_, plid, groupId, companyId, privateLayout, layoutId, parentLayoutId, name, type_, typeSettings, hidden_, friendlyURL, priority) values ('786f0dd0-f3da-47ef-aaf0-4c9048128565', 26, 23, 1, 0, 1, 0, '<?xml version="1.0"?>\n\n<root>\n  <name>Liferay, Inc. Extranet</name>\n</root>', 'portlet', 'layout-template-id=2_columns_ii\ncolumn-1=3,\ncolumn-2=19,', 0, '/23', 0);
insert into Layout (uuid_, plid, groupId, companyId, privateLayout, layoutId, parentLayoutId, name, type_, typeSettings, hidden_, friendlyURL, priority) values ('9d0586ce-2171-46ad-a664-b0cba98c0ce9', 27, 23, 1, 1, 1, 0, '<?xml version="1.0"?>\n\n<root>\n  <name>Liferay, Inc. Intranet</name>\n</root>', 'portlet', 'layout-template-id=2_columns_ii\ncolumn-1=3,\ncolumn-2=19,', 0, '/23', 0);

insert into Organization_ (organizationId, companyId, parentOrganizationId, name, type_, recursable, regionId, countryId, statusId, comments) values (28, 1, 22, 'Liferay Engineering', 'regular-organization', 1, 19005, 19, 12017, '');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (29, 1, 5, 11, 28, 0, 0, '29', 0, '/29', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (30, 1, 29, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (31, 1, 29, 0, 0, 'classic', '01', 0);

insert into Organization_ (organizationId, companyId, parentOrganizationId, name, type_, recursable, regionId, countryId, statusId, comments) values (32, 1, 22, 'Liferay Consulting', 'regular-organization', 1, 19005, 19, 12017, '');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (33, 1, 5, 11, 32, 0, 0, '33', 0, '/33', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (34, 1, 33, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (35, 1, 33, 0, 0, 'classic', '01', 0);

insert into Organization_ (organizationId, companyId, parentOrganizationId, name, type_, recursable, regionId, countryId, statusId, comments) values (36, 1, 22, 'Liferay Support', 'regular-organization', 1, 19005, 19, 12017, '');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (37, 1, 5, 11, 36, 0, 0, '37', 0, '/37', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (38, 1, 37, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (39, 1, 37, 0, 0, 'classic', '01', 0);

insert into Organization_ (organizationId, companyId, parentOrganizationId, name, type_, recursable, regionId, countryId, statusId, comments) values (40, 1, 22, 'Liferay Sales', 'regular-organization', 1, 19005, 19, 12017, '');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (41, 1, 5, 11, 40, 0, 0, '41', 0, '/41', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (42, 1, 41, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (43, 1, 41, 0, 0, 'classic', '01', 0);

insert into Organization_ (organizationId, companyId, parentOrganizationId, name, type_, recursable, regionId, countryId, statusId, comments) values (44, 1, 22, 'Liferay Marketing', 'regular-organization', 1, 19005, 19, 12017, '');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (45, 1, 5, 11, 44, 0, 0, '45', 0, '/45', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (46, 1, 45, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (47, 1, 45, 0, 0, 'classic', '01', 0);


insert into Organization_ (organizationId, companyId, parentOrganizationId, name, type_, recursable, regionId, countryId, statusId, comments) values (48, 1, 22, 'Liferay Los Angeles', 'location', 0, 19005, 19, 12017, '');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (49, 1, 5, 11, 48, 0, 0, '49', 0, '/49', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (50, 1, 49, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (51, 1, 49, 0, 0, 'classic', '01', 0);

insert into Organization_ (organizationId, companyId, parentOrganizationId, name, type_, recursable, regionId, countryId, statusId, comments) values (52, 1, 22, 'Liferay San Francisco', 'location', 0, 19005, 19, 12017, '');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (53, 1, 5, 11, 52, 0, 0, '53', 0, '/53', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (54, 1, 53, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (55, 1, 53, 0, 0, 'classic', '01', 0);

insert into Organization_ (organizationId, companyId, parentOrganizationId, name, type_, recursable, regionId, countryId, statusId, comments) values (56, 1, 22, 'Liferay Chicago', 'location', 0, 19014, 19, 12017, '');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (57, 1, 5, 11, 56, 0, 0, '57', 0, '/57', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (58, 1, 57, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (59, 1, 57, 0, 0, 'classic', '01', 0);

insert into Organization_ (organizationId, companyId, parentOrganizationId, name, type_, recursable, regionId, countryId, statusId, comments) values (60, 1, 22, 'Liferay New York', 'location', 0, 19033, 19, 12017, '');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (61, 1, 5, 11, 60, 0, 0, '61', 0, '/61', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (62, 1, 61, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (63, 1, 61, 0, 0, 'classic', '01', 0);

insert into Organization_ (organizationId, companyId, parentOrganizationId, name, type_, recursable, regionId, countryId, statusId, comments) values (64, 1, 22, 'Liferay Sao Paulo', 'location', 0, 0, 48, 12017, '');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (65, 1, 5, 11, 64, 0, 0, '65', 0, '/65', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (66, 1, 65, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (67, 1, 65, 0, 0, 'classic', '01', 0);

insert into Organization_ (organizationId, companyId, parentOrganizationId, name, type_, recursable, regionId, countryId, statusId, comments) values (68, 1, 22, 'Liferay Frankfurt', 'location', 0, 0, 4, 12017, '');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (69, 1, 5, 11, 68, 0, 0, '69', 0, '/69', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (70, 1, 69, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (71, 1, 69, 0, 0, 'classic', '01', 0);

insert into Organization_ (organizationId, companyId, parentOrganizationId, name, type_, recursable, regionId, countryId, statusId, comments) values (72, 1, 22, 'Liferay Madrid', 'location', 0, 0, 15, 12017, '');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (73, 1, 5, 11, 72, 0, 0, '73', 0, '/73', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (74, 1, 73, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (75, 1, 73, 0, 0, 'classic', '01', 0);

insert into Organization_ (organizationId, companyId, parentOrganizationId, name, type_, recursable, regionId, countryId, statusId, comments) values (76, 1, 22, 'Liferay Dalian', 'location', 0, 0, 2, 12017, '');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (77, 1, 5, 11, 76, 0, 0, '77', 0, '/77', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (78, 1, 77, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (79, 1, 77, 0, 0, 'classic', '01', 0);

insert into Organization_ (organizationId, companyId, parentOrganizationId, name, type_, recursable, regionId, countryId, statusId, comments) values (80, 1, 22, 'Liferay Hong Kong', 'location', 0, 0, 2, 12017, '');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (81, 1, 5, 11, 80, 0, 0, '81', 0, '/81', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (82, 1, 81, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (83, 1, 81, 0, 0, 'classic', '01', 0);

insert into Organization_ (organizationId, companyId, parentOrganizationId, name, type_, recursable, regionId, countryId, statusId, comments) values (84, 1, 22, 'Liferay Kuala Lumpur', 'location', 0, 0, 135, 12017, '');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (85, 1, 5, 11, 84, 0, 0, '85', 0, '/85', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (86, 1, 85, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (87, 1, 85, 0, 0, 'classic', '01', 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('5ccf0fc4-463f-4dca-9a3b-55ddbcf0efb0', 5, 1, now(), now(), 1, 6, 'password', 0, 0, '5', 'default@liferay.com', 'Welcome!', '', '', '', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (6, 1, 5, '', now(), now(), 13, 5, 7, 0, 'default@liferay.com', '', '', '', 1, '1970-01-01');

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, lastLoginDate, failedLoginAttempts, agreedToTermsOfUse, emailAddressVerified, status) values ('80f89b0c-f6eb-4776-83d8-81dc91733b92', 2, 1, now(), now(), 0, 3, 'test', 0, 0, 'joebloggs', 'test@liferay.com', 'Welcome Joe Bloggs!', 'Joe', '', 'Bloggs', now(), now(), 0, 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (3, 1, 2, 'Joe Bloggs', now(), now(), 13, 2, 7, 0, 'test@liferay.com', 'Joe', '', 'Bloggs', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (88, 1, 2, 13, 2, 0, 0, '88', 0, '/joebloggs', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (89, 1, 88, 1, 0, 'classic', '01', 2);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (90, 1, 88, 0, 0, 'classic', '01', 0);
insert into Layout (uuid_, plid, groupId, companyId, privateLayout, layoutId, parentLayoutId, name, type_, typeSettings, hidden_, friendlyURL, priority) values ('8798af11-130b-4208-b86b-3ddf8297edcc', 91, 88, 1, 1, 1, 0, '<?xml version="1.0"?>\n\n<root>\n  <name>Home</name>\n</root>', 'portlet', 'column-1=71_INSTANCE_OY0d,82,23,61,\ncolumn-2=29,8,19,\nlayout-template-id=2_columns_ii\n', 0, '/home', 0);
insert into Layout (uuid_, plid, groupId, companyId, privateLayout, layoutId, parentLayoutId, name, type_, typeSettings, hidden_, friendlyURL, priority) values ('ee8e412b-039c-4e76-9a0c-0b57192d948f', 92, 88, 1, 1, 2, 0, '<?xml version="1.0"?>\n\n<root>\n  <name>Plugins</name>\n</root>', 'portlet', 'column-1=\ncolumn-2=111,\nlayout-template-id=2_columns_ii\n', 0, '/plugins', 1);

insert into Users_Groups values (2, 19);

insert into Users_Orgs (userId, organizationId) values (2, 22);
insert into Users_Orgs (userId, organizationId) values (2, 48);

insert into Users_Roles values (2, 15);
insert into Users_Roles values (2, 17);
insert into Users_Roles values (2, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (93, 88, 1, 2, 'Joe Blogs', now(), now(), 13, 2, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Joe Bloggs', '', '', '', 0, 0, 0, 0, 0);


insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('c358054f-8163-4d00-9122-b57265860b2e', 94, 1, now(), now(), 0, 95, 'test', 0, 0, 'lax1', 'test.lax.1@liferay.com', 'Welcome Test LAX 1!', 'Test', '', 'LAX 1', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (95, 1, 2, 'Test LAX 1', now(), now(), 13, 94, 7, 0, 'test.lax.1@liferay.com', 'Test', '', 'LAX 1', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (96, 1, 94, 13, 94, 0, 0, '96', 0, '/lax1', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (97, 1, 96, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (98, 1, 96, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (94, 19);

insert into Users_Orgs (userId, organizationId) values (94, 22);
insert into Users_Orgs (userId, organizationId) values (94, 48);

insert into Users_Roles values (94, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (99, 96, 1, 2, 'Joe Blogs', now(), now(), 13, 94, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 1', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('d9b28639-8be1-46bd-8379-59071f3b4305', 100, 1, now(), now(), 0, 101, 'test', 0, 0, 'lax2', 'test.lax.2@liferay.com', 'Welcome Test LAX 2!', 'Test', '', 'LAX 2', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (101, 1, 2, 'Test LAX 2', now(), now(), 13, 100, 7, 0, 'test.lax.2@liferay.com', 'Test', '', 'LAX 2', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (102, 1, 100, 13, 100, 0, 0, '102', 0, '/lax2', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (103, 1, 102, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (104, 1, 102, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (100, 19);

insert into Users_Orgs (userId, organizationId) values (100, 22);
insert into Users_Orgs (userId, organizationId) values (100, 48);

insert into Users_Roles values (100, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (105, 102, 1, 2, 'Joe Blogs', now(), now(), 13, 100, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 2', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('72fc766d-b1eb-4115-aa70-71bcb4805ec9', 106, 1, now(), now(), 0, 107, 'test', 0, 0, 'lax3', 'test.lax.3@liferay.com', 'Welcome Test LAX 3!', 'Test', '', 'LAX 3', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (107, 1, 2, 'Test LAX 3', now(), now(), 13, 106, 7, 0, 'test.lax.3@liferay.com', 'Test', '', 'LAX 3', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (108, 1, 106, 13, 106, 0, 0, '108', 0, '/lax3', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (109, 1, 108, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (110, 1, 108, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (106, 19);

insert into Users_Orgs (userId, organizationId) values (106, 22);
insert into Users_Orgs (userId, organizationId) values (106, 48);

insert into Users_Roles values (106, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (111, 108, 1, 2, 'Joe Blogs', now(), now(), 13, 106, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 3', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('500cf27a-81f4-43d5-b618-9347b6bbc881', 112, 1, now(), now(), 0, 113, 'test', 0, 0, 'lax4', 'test.lax.4@liferay.com', 'Welcome Test LAX 4!', 'Test', '', 'LAX 4', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (113, 1, 2, 'Test LAX 4', now(), now(), 13, 112, 7, 0, 'test.lax.4@liferay.com', 'Test', '', 'LAX 4', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (114, 1, 112, 13, 112, 0, 0, '114', 0, '/lax4', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (115, 1, 114, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (116, 1, 114, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (112, 19);

insert into Users_Orgs (userId, organizationId) values (112, 22);
insert into Users_Orgs (userId, organizationId) values (112, 48);

insert into Users_Roles values (112, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (117, 114, 1, 2, 'Joe Blogs', now(), now(), 13, 112, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 4', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('f5bfd969-427a-4fa0-b5e8-e91ba58aeebb', 118, 1, now(), now(), 0, 119, 'test', 0, 0, 'lax5', 'test.lax.5@liferay.com', 'Welcome Test LAX 5!', 'Test', '', 'LAX 5', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (119, 1, 2, 'Test LAX 5', now(), now(), 13, 118, 7, 0, 'test.lax.5@liferay.com', 'Test', '', 'LAX 5', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (120, 1, 118, 13, 118, 0, 0, '120', 0, '/lax5', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (121, 1, 120, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (122, 1, 120, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (118, 19);

insert into Users_Orgs (userId, organizationId) values (118, 22);
insert into Users_Orgs (userId, organizationId) values (118, 48);

insert into Users_Roles values (118, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (123, 120, 1, 2, 'Joe Blogs', now(), now(), 13, 118, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 5', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('8c797212-96cc-4229-9e89-c79445291c2d', 124, 1, now(), now(), 0, 125, 'test', 0, 0, 'lax6', 'test.lax.6@liferay.com', 'Welcome Test LAX 6!', 'Test', '', 'LAX 6', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (125, 1, 2, 'Test LAX 6', now(), now(), 13, 124, 7, 0, 'test.lax.6@liferay.com', 'Test', '', 'LAX 6', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (126, 1, 124, 13, 124, 0, 0, '126', 0, '/lax6', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (127, 1, 126, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (128, 1, 126, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (124, 19);

insert into Users_Orgs (userId, organizationId) values (124, 22);
insert into Users_Orgs (userId, organizationId) values (124, 48);

insert into Users_Roles values (124, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (129, 126, 1, 2, 'Joe Blogs', now(), now(), 13, 124, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 6', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('46321c8e-086b-469b-814b-0a87bbe46195', 130, 1, now(), now(), 0, 131, 'test', 0, 0, 'lax7', 'test.lax.7@liferay.com', 'Welcome Test LAX 7!', 'Test', '', 'LAX 7', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (131, 1, 2, 'Test LAX 7', now(), now(), 13, 130, 7, 0, 'test.lax.7@liferay.com', 'Test', '', 'LAX 7', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (132, 1, 130, 13, 130, 0, 0, '132', 0, '/lax7', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (133, 1, 132, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (134, 1, 132, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (130, 19);

insert into Users_Orgs (userId, organizationId) values (130, 22);
insert into Users_Orgs (userId, organizationId) values (130, 48);

insert into Users_Roles values (130, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (135, 132, 1, 2, 'Joe Blogs', now(), now(), 13, 130, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 7', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('aeaa1819-e9a5-47fa-9408-0f1e29402cea', 136, 1, now(), now(), 0, 137, 'test', 0, 0, 'lax8', 'test.lax.8@liferay.com', 'Welcome Test LAX 8!', 'Test', '', 'LAX 8', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (137, 1, 2, 'Test LAX 8', now(), now(), 13, 136, 7, 0, 'test.lax.8@liferay.com', 'Test', '', 'LAX 8', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (138, 1, 136, 13, 136, 0, 0, '138', 0, '/lax8', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (139, 1, 138, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (140, 1, 138, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (136, 19);

insert into Users_Orgs (userId, organizationId) values (136, 22);
insert into Users_Orgs (userId, organizationId) values (136, 48);

insert into Users_Roles values (136, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (141, 138, 1, 2, 'Joe Blogs', now(), now(), 13, 136, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 8', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('cbf044df-620f-41e2-8b4f-e8c6080ac1a1', 142, 1, now(), now(), 0, 143, 'test', 0, 0, 'lax9', 'test.lax.9@liferay.com', 'Welcome Test LAX 9!', 'Test', '', 'LAX 9', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (143, 1, 2, 'Test LAX 9', now(), now(), 13, 142, 7, 0, 'test.lax.9@liferay.com', 'Test', '', 'LAX 9', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (144, 1, 142, 13, 142, 0, 0, '144', 0, '/lax9', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (145, 1, 144, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (146, 1, 144, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (142, 19);

insert into Users_Orgs (userId, organizationId) values (142, 22);
insert into Users_Orgs (userId, organizationId) values (142, 48);

insert into Users_Roles values (142, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (147, 144, 1, 2, 'Joe Blogs', now(), now(), 13, 142, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 9', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('21992bfe-e34d-4d37-8ab4-d9f0e438d272', 148, 1, now(), now(), 0, 149, 'test', 0, 0, 'lax10', 'test.lax.10@liferay.com', 'Welcome Test LAX 10!', 'Test', '', 'LAX 10', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (149, 1, 2, 'Test LAX 10', now(), now(), 13, 148, 7, 0, 'test.lax.10@liferay.com', 'Test', '', 'LAX 10', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (150, 1, 148, 13, 148, 0, 0, '150', 0, '/lax10', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (151, 1, 150, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (152, 1, 150, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (148, 19);

insert into Users_Orgs (userId, organizationId) values (148, 22);
insert into Users_Orgs (userId, organizationId) values (148, 48);

insert into Users_Roles values (148, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (153, 150, 1, 2, 'Joe Blogs', now(), now(), 13, 148, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 10', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('1c1bcb52-3263-4ea0-901a-0e728124508a', 154, 1, now(), now(), 0, 155, 'test', 0, 0, 'lax11', 'test.lax.11@liferay.com', 'Welcome Test LAX 11!', 'Test', '', 'LAX 11', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (155, 1, 2, 'Test LAX 11', now(), now(), 13, 154, 7, 0, 'test.lax.11@liferay.com', 'Test', '', 'LAX 11', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (156, 1, 154, 13, 154, 0, 0, '156', 0, '/lax11', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (157, 1, 156, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (158, 1, 156, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (154, 19);

insert into Users_Orgs (userId, organizationId) values (154, 22);
insert into Users_Orgs (userId, organizationId) values (154, 48);

insert into Users_Roles values (154, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (159, 156, 1, 2, 'Joe Blogs', now(), now(), 13, 154, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 11', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('dc54784a-cf0c-40bb-be4e-c6991acc923d', 160, 1, now(), now(), 0, 161, 'test', 0, 0, 'lax12', 'test.lax.12@liferay.com', 'Welcome Test LAX 12!', 'Test', '', 'LAX 12', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (161, 1, 2, 'Test LAX 12', now(), now(), 13, 160, 7, 0, 'test.lax.12@liferay.com', 'Test', '', 'LAX 12', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (162, 1, 160, 13, 160, 0, 0, '162', 0, '/lax12', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (163, 1, 162, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (164, 1, 162, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (160, 19);

insert into Users_Orgs (userId, organizationId) values (160, 22);
insert into Users_Orgs (userId, organizationId) values (160, 48);

insert into Users_Roles values (160, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (165, 162, 1, 2, 'Joe Blogs', now(), now(), 13, 160, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 12', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('a8cdf119-9719-4f42-92a8-c5f09d302c38', 166, 1, now(), now(), 0, 167, 'test', 0, 0, 'lax13', 'test.lax.13@liferay.com', 'Welcome Test LAX 13!', 'Test', '', 'LAX 13', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (167, 1, 2, 'Test LAX 13', now(), now(), 13, 166, 7, 0, 'test.lax.13@liferay.com', 'Test', '', 'LAX 13', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (168, 1, 166, 13, 166, 0, 0, '168', 0, '/lax13', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (169, 1, 168, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (170, 1, 168, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (166, 19);

insert into Users_Orgs (userId, organizationId) values (166, 22);
insert into Users_Orgs (userId, organizationId) values (166, 48);

insert into Users_Roles values (166, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (171, 168, 1, 2, 'Joe Blogs', now(), now(), 13, 166, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 13', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('77c09424-7c9f-4313-bf4c-7d237ab049b6', 172, 1, now(), now(), 0, 173, 'test', 0, 0, 'lax14', 'test.lax.14@liferay.com', 'Welcome Test LAX 14!', 'Test', '', 'LAX 14', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (173, 1, 2, 'Test LAX 14', now(), now(), 13, 172, 7, 0, 'test.lax.14@liferay.com', 'Test', '', 'LAX 14', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (174, 1, 172, 13, 172, 0, 0, '174', 0, '/lax14', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (175, 1, 174, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (176, 1, 174, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (172, 19);

insert into Users_Orgs (userId, organizationId) values (172, 22);
insert into Users_Orgs (userId, organizationId) values (172, 48);

insert into Users_Roles values (172, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (177, 174, 1, 2, 'Joe Blogs', now(), now(), 13, 172, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 14', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('cec5bc22-80dd-4787-a9cd-c4b02c0ce2e7', 178, 1, now(), now(), 0, 179, 'test', 0, 0, 'lax15', 'test.lax.15@liferay.com', 'Welcome Test LAX 15!', 'Test', '', 'LAX 15', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (179, 1, 2, 'Test LAX 15', now(), now(), 13, 178, 7, 0, 'test.lax.15@liferay.com', 'Test', '', 'LAX 15', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (180, 1, 178, 13, 178, 0, 0, '180', 0, '/lax15', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (181, 1, 180, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (182, 1, 180, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (178, 19);

insert into Users_Orgs (userId, organizationId) values (178, 22);
insert into Users_Orgs (userId, organizationId) values (178, 48);

insert into Users_Roles values (178, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (183, 180, 1, 2, 'Joe Blogs', now(), now(), 13, 178, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 15', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('108941d8-39ea-4323-9b5b-995f3e34b35b', 184, 1, now(), now(), 0, 185, 'test', 0, 0, 'lax16', 'test.lax.16@liferay.com', 'Welcome Test LAX 16!', 'Test', '', 'LAX 16', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (185, 1, 2, 'Test LAX 16', now(), now(), 13, 184, 7, 0, 'test.lax.16@liferay.com', 'Test', '', 'LAX 16', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (186, 1, 184, 13, 184, 0, 0, '186', 0, '/lax16', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (187, 1, 186, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (188, 1, 186, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (184, 19);

insert into Users_Orgs (userId, organizationId) values (184, 22);
insert into Users_Orgs (userId, organizationId) values (184, 48);

insert into Users_Roles values (184, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (189, 186, 1, 2, 'Joe Blogs', now(), now(), 13, 184, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 16', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('6ab77565-0c28-4deb-b5e7-e3f30879f8a1', 190, 1, now(), now(), 0, 191, 'test', 0, 0, 'lax17', 'test.lax.17@liferay.com', 'Welcome Test LAX 17!', 'Test', '', 'LAX 17', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (191, 1, 2, 'Test LAX 17', now(), now(), 13, 190, 7, 0, 'test.lax.17@liferay.com', 'Test', '', 'LAX 17', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (192, 1, 190, 13, 190, 0, 0, '192', 0, '/lax17', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (193, 1, 192, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (194, 1, 192, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (190, 19);

insert into Users_Orgs (userId, organizationId) values (190, 22);
insert into Users_Orgs (userId, organizationId) values (190, 48);

insert into Users_Roles values (190, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (195, 192, 1, 2, 'Joe Blogs', now(), now(), 13, 190, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 17', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('11db9eeb-638a-4514-b14a-a8288623527f', 196, 1, now(), now(), 0, 197, 'test', 0, 0, 'lax18', 'test.lax.18@liferay.com', 'Welcome Test LAX 18!', 'Test', '', 'LAX 18', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (197, 1, 2, 'Test LAX 18', now(), now(), 13, 196, 7, 0, 'test.lax.18@liferay.com', 'Test', '', 'LAX 18', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (198, 1, 196, 13, 196, 0, 0, '198', 0, '/lax18', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (199, 1, 198, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (200, 1, 198, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (196, 19);

insert into Users_Orgs (userId, organizationId) values (196, 22);
insert into Users_Orgs (userId, organizationId) values (196, 48);

insert into Users_Roles values (196, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (201, 198, 1, 2, 'Joe Blogs', now(), now(), 13, 196, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 18', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('573784b5-cb1d-4674-a3f8-aa75e185591e', 202, 1, now(), now(), 0, 203, 'test', 0, 0, 'lax19', 'test.lax.19@liferay.com', 'Welcome Test LAX 19!', 'Test', '', 'LAX 19', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (203, 1, 2, 'Test LAX 19', now(), now(), 13, 202, 7, 0, 'test.lax.19@liferay.com', 'Test', '', 'LAX 19', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (204, 1, 202, 13, 202, 0, 0, '204', 0, '/lax19', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (205, 1, 204, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (206, 1, 204, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (202, 19);

insert into Users_Orgs (userId, organizationId) values (202, 22);
insert into Users_Orgs (userId, organizationId) values (202, 48);

insert into Users_Roles values (202, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (207, 204, 1, 2, 'Joe Blogs', now(), now(), 13, 202, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 19', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('373e0137-b4fc-4e9a-8103-33755f272ffc', 208, 1, now(), now(), 0, 209, 'test', 0, 0, 'lax20', 'test.lax.20@liferay.com', 'Welcome Test LAX 20!', 'Test', '', 'LAX 20', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (209, 1, 2, 'Test LAX 20', now(), now(), 13, 208, 7, 0, 'test.lax.20@liferay.com', 'Test', '', 'LAX 20', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (210, 1, 208, 13, 208, 0, 0, '210', 0, '/lax20', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (211, 1, 210, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (212, 1, 210, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (208, 19);

insert into Users_Orgs (userId, organizationId) values (208, 22);
insert into Users_Orgs (userId, organizationId) values (208, 48);

insert into Users_Roles values (208, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (213, 210, 1, 2, 'Joe Blogs', now(), now(), 13, 208, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 20', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('f9d4b8b0-9228-4825-b654-0a755414bfe9', 214, 1, now(), now(), 0, 215, 'test', 0, 0, 'lax21', 'test.lax.21@liferay.com', 'Welcome Test LAX 21!', 'Test', '', 'LAX 21', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (215, 1, 2, 'Test LAX 21', now(), now(), 13, 214, 7, 0, 'test.lax.21@liferay.com', 'Test', '', 'LAX 21', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (216, 1, 214, 13, 214, 0, 0, '216', 0, '/lax21', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (217, 1, 216, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (218, 1, 216, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (214, 19);

insert into Users_Orgs (userId, organizationId) values (214, 22);
insert into Users_Orgs (userId, organizationId) values (214, 48);

insert into Users_Roles values (214, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (219, 216, 1, 2, 'Joe Blogs', now(), now(), 13, 214, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 21', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('1a3693e7-ae75-4c59-8f9b-8ae922accacb', 220, 1, now(), now(), 0, 221, 'test', 0, 0, 'lax22', 'test.lax.22@liferay.com', 'Welcome Test LAX 22!', 'Test', '', 'LAX 22', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (221, 1, 2, 'Test LAX 22', now(), now(), 13, 220, 7, 0, 'test.lax.22@liferay.com', 'Test', '', 'LAX 22', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (222, 1, 220, 13, 220, 0, 0, '222', 0, '/lax22', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (223, 1, 222, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (224, 1, 222, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (220, 19);

insert into Users_Orgs (userId, organizationId) values (220, 22);
insert into Users_Orgs (userId, organizationId) values (220, 48);

insert into Users_Roles values (220, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (225, 222, 1, 2, 'Joe Blogs', now(), now(), 13, 220, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 22', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('2b740245-9cd6-42f2-8c49-922c411e72aa', 226, 1, now(), now(), 0, 227, 'test', 0, 0, 'lax23', 'test.lax.23@liferay.com', 'Welcome Test LAX 23!', 'Test', '', 'LAX 23', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (227, 1, 2, 'Test LAX 23', now(), now(), 13, 226, 7, 0, 'test.lax.23@liferay.com', 'Test', '', 'LAX 23', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (228, 1, 226, 13, 226, 0, 0, '228', 0, '/lax23', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (229, 1, 228, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (230, 1, 228, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (226, 19);

insert into Users_Orgs (userId, organizationId) values (226, 22);
insert into Users_Orgs (userId, organizationId) values (226, 48);

insert into Users_Roles values (226, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (231, 228, 1, 2, 'Joe Blogs', now(), now(), 13, 226, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 23', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('ec3dcc47-a846-42d3-97f9-5efde552aeeb', 232, 1, now(), now(), 0, 233, 'test', 0, 0, 'lax24', 'test.lax.24@liferay.com', 'Welcome Test LAX 24!', 'Test', '', 'LAX 24', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (233, 1, 2, 'Test LAX 24', now(), now(), 13, 232, 7, 0, 'test.lax.24@liferay.com', 'Test', '', 'LAX 24', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (234, 1, 232, 13, 232, 0, 0, '234', 0, '/lax24', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (235, 1, 234, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (236, 1, 234, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (232, 19);

insert into Users_Orgs (userId, organizationId) values (232, 22);
insert into Users_Orgs (userId, organizationId) values (232, 48);

insert into Users_Roles values (232, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (237, 234, 1, 2, 'Joe Blogs', now(), now(), 13, 232, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 24', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('ae5ffd0f-9d8c-46bb-8877-d6133d3eca39', 238, 1, now(), now(), 0, 239, 'test', 0, 0, 'lax25', 'test.lax.25@liferay.com', 'Welcome Test LAX 25!', 'Test', '', 'LAX 25', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (239, 1, 2, 'Test LAX 25', now(), now(), 13, 238, 7, 0, 'test.lax.25@liferay.com', 'Test', '', 'LAX 25', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (240, 1, 238, 13, 238, 0, 0, '240', 0, '/lax25', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (241, 1, 240, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (242, 1, 240, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (238, 19);

insert into Users_Orgs (userId, organizationId) values (238, 22);
insert into Users_Orgs (userId, organizationId) values (238, 48);

insert into Users_Roles values (238, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (243, 240, 1, 2, 'Joe Blogs', now(), now(), 13, 238, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 25', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('030169ce-5ee7-412c-986c-153fa81cf139', 244, 1, now(), now(), 0, 245, 'test', 0, 0, 'lax26', 'test.lax.26@liferay.com', 'Welcome Test LAX 26!', 'Test', '', 'LAX 26', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (245, 1, 2, 'Test LAX 26', now(), now(), 13, 244, 7, 0, 'test.lax.26@liferay.com', 'Test', '', 'LAX 26', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (246, 1, 244, 13, 244, 0, 0, '246', 0, '/lax26', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (247, 1, 246, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (248, 1, 246, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (244, 19);

insert into Users_Orgs (userId, organizationId) values (244, 22);
insert into Users_Orgs (userId, organizationId) values (244, 48);

insert into Users_Roles values (244, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (249, 246, 1, 2, 'Joe Blogs', now(), now(), 13, 244, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 26', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('72ba78db-f682-4e60-892e-c7d6634f4081', 250, 1, now(), now(), 0, 251, 'test', 0, 0, 'lax27', 'test.lax.27@liferay.com', 'Welcome Test LAX 27!', 'Test', '', 'LAX 27', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (251, 1, 2, 'Test LAX 27', now(), now(), 13, 250, 7, 0, 'test.lax.27@liferay.com', 'Test', '', 'LAX 27', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (252, 1, 250, 13, 250, 0, 0, '252', 0, '/lax27', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (253, 1, 252, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (254, 1, 252, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (250, 19);

insert into Users_Orgs (userId, organizationId) values (250, 22);
insert into Users_Orgs (userId, organizationId) values (250, 48);

insert into Users_Roles values (250, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (255, 252, 1, 2, 'Joe Blogs', now(), now(), 13, 250, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 27', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('b1a40b9f-8264-4b04-bf99-26798574235f', 256, 1, now(), now(), 0, 257, 'test', 0, 0, 'lax28', 'test.lax.28@liferay.com', 'Welcome Test LAX 28!', 'Test', '', 'LAX 28', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (257, 1, 2, 'Test LAX 28', now(), now(), 13, 256, 7, 0, 'test.lax.28@liferay.com', 'Test', '', 'LAX 28', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (258, 1, 256, 13, 256, 0, 0, '258', 0, '/lax28', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (259, 1, 258, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (260, 1, 258, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (256, 19);

insert into Users_Orgs (userId, organizationId) values (256, 22);
insert into Users_Orgs (userId, organizationId) values (256, 48);

insert into Users_Roles values (256, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (261, 258, 1, 2, 'Joe Blogs', now(), now(), 13, 256, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 28', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('a1d76f86-ded9-40b1-bebb-3227510cb4b9', 262, 1, now(), now(), 0, 263, 'test', 0, 0, 'lax29', 'test.lax.29@liferay.com', 'Welcome Test LAX 29!', 'Test', '', 'LAX 29', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (263, 1, 2, 'Test LAX 29', now(), now(), 13, 262, 7, 0, 'test.lax.29@liferay.com', 'Test', '', 'LAX 29', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (264, 1, 262, 13, 262, 0, 0, '264', 0, '/lax29', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (265, 1, 264, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (266, 1, 264, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (262, 19);

insert into Users_Orgs (userId, organizationId) values (262, 22);
insert into Users_Orgs (userId, organizationId) values (262, 48);

insert into Users_Roles values (262, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (267, 264, 1, 2, 'Joe Blogs', now(), now(), 13, 262, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 29', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('13ed4b3f-dd09-429a-9b90-438d8045cc95', 268, 1, now(), now(), 0, 269, 'test', 0, 0, 'lax30', 'test.lax.30@liferay.com', 'Welcome Test LAX 30!', 'Test', '', 'LAX 30', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (269, 1, 2, 'Test LAX 30', now(), now(), 13, 268, 7, 0, 'test.lax.30@liferay.com', 'Test', '', 'LAX 30', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (270, 1, 268, 13, 268, 0, 0, '270', 0, '/lax30', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (271, 1, 270, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (272, 1, 270, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (268, 19);

insert into Users_Orgs (userId, organizationId) values (268, 22);
insert into Users_Orgs (userId, organizationId) values (268, 48);

insert into Users_Roles values (268, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (273, 270, 1, 2, 'Joe Blogs', now(), now(), 13, 268, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 30', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('fec5ca59-2089-430c-a94a-b9ff6133058e', 274, 1, now(), now(), 0, 275, 'test', 0, 0, 'lax31', 'test.lax.31@liferay.com', 'Welcome Test LAX 31!', 'Test', '', 'LAX 31', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (275, 1, 2, 'Test LAX 31', now(), now(), 13, 274, 7, 0, 'test.lax.31@liferay.com', 'Test', '', 'LAX 31', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (276, 1, 274, 13, 274, 0, 0, '276', 0, '/lax31', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (277, 1, 276, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (278, 1, 276, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (274, 19);

insert into Users_Orgs (userId, organizationId) values (274, 22);
insert into Users_Orgs (userId, organizationId) values (274, 48);

insert into Users_Roles values (274, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (279, 276, 1, 2, 'Joe Blogs', now(), now(), 13, 274, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 31', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('d14bbfe8-0fe3-496c-a0f8-a6e161199b36', 280, 1, now(), now(), 0, 281, 'test', 0, 0, 'lax32', 'test.lax.32@liferay.com', 'Welcome Test LAX 32!', 'Test', '', 'LAX 32', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (281, 1, 2, 'Test LAX 32', now(), now(), 13, 280, 7, 0, 'test.lax.32@liferay.com', 'Test', '', 'LAX 32', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (282, 1, 280, 13, 280, 0, 0, '282', 0, '/lax32', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (283, 1, 282, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (284, 1, 282, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (280, 19);

insert into Users_Orgs (userId, organizationId) values (280, 22);
insert into Users_Orgs (userId, organizationId) values (280, 48);

insert into Users_Roles values (280, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (285, 282, 1, 2, 'Joe Blogs', now(), now(), 13, 280, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 32', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('e86fe0fa-7004-49de-888a-29d469ec2a57', 286, 1, now(), now(), 0, 287, 'test', 0, 0, 'lax33', 'test.lax.33@liferay.com', 'Welcome Test LAX 33!', 'Test', '', 'LAX 33', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (287, 1, 2, 'Test LAX 33', now(), now(), 13, 286, 7, 0, 'test.lax.33@liferay.com', 'Test', '', 'LAX 33', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (288, 1, 286, 13, 286, 0, 0, '288', 0, '/lax33', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (289, 1, 288, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (290, 1, 288, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (286, 19);

insert into Users_Orgs (userId, organizationId) values (286, 22);
insert into Users_Orgs (userId, organizationId) values (286, 48);

insert into Users_Roles values (286, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (291, 288, 1, 2, 'Joe Blogs', now(), now(), 13, 286, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 33', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('c9cd2b4c-c690-4b04-afe7-6540efc65348', 292, 1, now(), now(), 0, 293, 'test', 0, 0, 'lax34', 'test.lax.34@liferay.com', 'Welcome Test LAX 34!', 'Test', '', 'LAX 34', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (293, 1, 2, 'Test LAX 34', now(), now(), 13, 292, 7, 0, 'test.lax.34@liferay.com', 'Test', '', 'LAX 34', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (294, 1, 292, 13, 292, 0, 0, '294', 0, '/lax34', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (295, 1, 294, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (296, 1, 294, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (292, 19);

insert into Users_Orgs (userId, organizationId) values (292, 22);
insert into Users_Orgs (userId, organizationId) values (292, 48);

insert into Users_Roles values (292, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (297, 294, 1, 2, 'Joe Blogs', now(), now(), 13, 292, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 34', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('158c3d0a-64b4-4b4e-92d0-9492fa7e224b', 298, 1, now(), now(), 0, 299, 'test', 0, 0, 'lax35', 'test.lax.35@liferay.com', 'Welcome Test LAX 35!', 'Test', '', 'LAX 35', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (299, 1, 2, 'Test LAX 35', now(), now(), 13, 298, 7, 0, 'test.lax.35@liferay.com', 'Test', '', 'LAX 35', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (300, 1, 298, 13, 298, 0, 0, '300', 0, '/lax35', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (301, 1, 300, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (302, 1, 300, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (298, 19);

insert into Users_Orgs (userId, organizationId) values (298, 22);
insert into Users_Orgs (userId, organizationId) values (298, 48);

insert into Users_Roles values (298, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (303, 300, 1, 2, 'Joe Blogs', now(), now(), 13, 298, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 35', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('265fc57e-dcc7-4921-9875-d8ee811a92e7', 304, 1, now(), now(), 0, 305, 'test', 0, 0, 'lax36', 'test.lax.36@liferay.com', 'Welcome Test LAX 36!', 'Test', '', 'LAX 36', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (305, 1, 2, 'Test LAX 36', now(), now(), 13, 304, 7, 0, 'test.lax.36@liferay.com', 'Test', '', 'LAX 36', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (306, 1, 304, 13, 304, 0, 0, '306', 0, '/lax36', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (307, 1, 306, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (308, 1, 306, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (304, 19);

insert into Users_Orgs (userId, organizationId) values (304, 22);
insert into Users_Orgs (userId, organizationId) values (304, 48);

insert into Users_Roles values (304, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (309, 306, 1, 2, 'Joe Blogs', now(), now(), 13, 304, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 36', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('7d8dccac-b215-452d-9fe3-249cb8e17982', 310, 1, now(), now(), 0, 311, 'test', 0, 0, 'lax37', 'test.lax.37@liferay.com', 'Welcome Test LAX 37!', 'Test', '', 'LAX 37', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (311, 1, 2, 'Test LAX 37', now(), now(), 13, 310, 7, 0, 'test.lax.37@liferay.com', 'Test', '', 'LAX 37', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (312, 1, 310, 13, 310, 0, 0, '312', 0, '/lax37', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (313, 1, 312, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (314, 1, 312, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (310, 19);

insert into Users_Orgs (userId, organizationId) values (310, 22);
insert into Users_Orgs (userId, organizationId) values (310, 48);

insert into Users_Roles values (310, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (315, 312, 1, 2, 'Joe Blogs', now(), now(), 13, 310, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 37', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('7293f3da-f2ce-43e2-be99-8821c01d153e', 316, 1, now(), now(), 0, 317, 'test', 0, 0, 'lax38', 'test.lax.38@liferay.com', 'Welcome Test LAX 38!', 'Test', '', 'LAX 38', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (317, 1, 2, 'Test LAX 38', now(), now(), 13, 316, 7, 0, 'test.lax.38@liferay.com', 'Test', '', 'LAX 38', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (318, 1, 316, 13, 316, 0, 0, '318', 0, '/lax38', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (319, 1, 318, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (320, 1, 318, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (316, 19);

insert into Users_Orgs (userId, organizationId) values (316, 22);
insert into Users_Orgs (userId, organizationId) values (316, 48);

insert into Users_Roles values (316, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (321, 318, 1, 2, 'Joe Blogs', now(), now(), 13, 316, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 38', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('c0a38e3f-5810-4f30-a899-bd35eb789046', 322, 1, now(), now(), 0, 323, 'test', 0, 0, 'lax39', 'test.lax.39@liferay.com', 'Welcome Test LAX 39!', 'Test', '', 'LAX 39', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (323, 1, 2, 'Test LAX 39', now(), now(), 13, 322, 7, 0, 'test.lax.39@liferay.com', 'Test', '', 'LAX 39', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (324, 1, 322, 13, 322, 0, 0, '324', 0, '/lax39', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (325, 1, 324, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (326, 1, 324, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (322, 19);

insert into Users_Orgs (userId, organizationId) values (322, 22);
insert into Users_Orgs (userId, organizationId) values (322, 48);

insert into Users_Roles values (322, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (327, 324, 1, 2, 'Joe Blogs', now(), now(), 13, 322, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 39', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('f1a9af28-2b84-4737-804f-ba6a433bff4d', 328, 1, now(), now(), 0, 329, 'test', 0, 0, 'lax40', 'test.lax.40@liferay.com', 'Welcome Test LAX 40!', 'Test', '', 'LAX 40', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (329, 1, 2, 'Test LAX 40', now(), now(), 13, 328, 7, 0, 'test.lax.40@liferay.com', 'Test', '', 'LAX 40', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (330, 1, 328, 13, 328, 0, 0, '330', 0, '/lax40', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (331, 1, 330, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (332, 1, 330, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (328, 19);

insert into Users_Orgs (userId, organizationId) values (328, 22);
insert into Users_Orgs (userId, organizationId) values (328, 48);

insert into Users_Roles values (328, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (333, 330, 1, 2, 'Joe Blogs', now(), now(), 13, 328, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 40', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('eed38bd3-4176-47c5-8a73-5dafc0a75162', 334, 1, now(), now(), 0, 335, 'test', 0, 0, 'lax41', 'test.lax.41@liferay.com', 'Welcome Test LAX 41!', 'Test', '', 'LAX 41', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (335, 1, 2, 'Test LAX 41', now(), now(), 13, 334, 7, 0, 'test.lax.41@liferay.com', 'Test', '', 'LAX 41', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (336, 1, 334, 13, 334, 0, 0, '336', 0, '/lax41', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (337, 1, 336, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (338, 1, 336, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (334, 19);

insert into Users_Orgs (userId, organizationId) values (334, 22);
insert into Users_Orgs (userId, organizationId) values (334, 48);

insert into Users_Roles values (334, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (339, 336, 1, 2, 'Joe Blogs', now(), now(), 13, 334, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 41', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('2a1b608d-acf1-4477-be01-fc981a012181', 340, 1, now(), now(), 0, 341, 'test', 0, 0, 'lax42', 'test.lax.42@liferay.com', 'Welcome Test LAX 42!', 'Test', '', 'LAX 42', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (341, 1, 2, 'Test LAX 42', now(), now(), 13, 340, 7, 0, 'test.lax.42@liferay.com', 'Test', '', 'LAX 42', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (342, 1, 340, 13, 340, 0, 0, '342', 0, '/lax42', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (343, 1, 342, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (344, 1, 342, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (340, 19);

insert into Users_Orgs (userId, organizationId) values (340, 22);
insert into Users_Orgs (userId, organizationId) values (340, 48);

insert into Users_Roles values (340, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (345, 342, 1, 2, 'Joe Blogs', now(), now(), 13, 340, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 42', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('7acc75c5-12e1-42a8-a79e-ec459393e04a', 346, 1, now(), now(), 0, 347, 'test', 0, 0, 'lax43', 'test.lax.43@liferay.com', 'Welcome Test LAX 43!', 'Test', '', 'LAX 43', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (347, 1, 2, 'Test LAX 43', now(), now(), 13, 346, 7, 0, 'test.lax.43@liferay.com', 'Test', '', 'LAX 43', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (348, 1, 346, 13, 346, 0, 0, '348', 0, '/lax43', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (349, 1, 348, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (350, 1, 348, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (346, 19);

insert into Users_Orgs (userId, organizationId) values (346, 22);
insert into Users_Orgs (userId, organizationId) values (346, 48);

insert into Users_Roles values (346, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (351, 348, 1, 2, 'Joe Blogs', now(), now(), 13, 346, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 43', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('023b1369-eb91-42c6-b0b8-5f5e76198d64', 352, 1, now(), now(), 0, 353, 'test', 0, 0, 'lax44', 'test.lax.44@liferay.com', 'Welcome Test LAX 44!', 'Test', '', 'LAX 44', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (353, 1, 2, 'Test LAX 44', now(), now(), 13, 352, 7, 0, 'test.lax.44@liferay.com', 'Test', '', 'LAX 44', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (354, 1, 352, 13, 352, 0, 0, '354', 0, '/lax44', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (355, 1, 354, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (356, 1, 354, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (352, 19);

insert into Users_Orgs (userId, organizationId) values (352, 22);
insert into Users_Orgs (userId, organizationId) values (352, 48);

insert into Users_Roles values (352, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (357, 354, 1, 2, 'Joe Blogs', now(), now(), 13, 352, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 44', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('48612b55-56a2-4c72-b91c-bae5c14861e4', 358, 1, now(), now(), 0, 359, 'test', 0, 0, 'lax45', 'test.lax.45@liferay.com', 'Welcome Test LAX 45!', 'Test', '', 'LAX 45', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (359, 1, 2, 'Test LAX 45', now(), now(), 13, 358, 7, 0, 'test.lax.45@liferay.com', 'Test', '', 'LAX 45', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (360, 1, 358, 13, 358, 0, 0, '360', 0, '/lax45', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (361, 1, 360, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (362, 1, 360, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (358, 19);

insert into Users_Orgs (userId, organizationId) values (358, 22);
insert into Users_Orgs (userId, organizationId) values (358, 48);

insert into Users_Roles values (358, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (363, 360, 1, 2, 'Joe Blogs', now(), now(), 13, 358, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 45', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('b8ec3d57-99cd-4030-ae59-6248a8c0f355', 364, 1, now(), now(), 0, 365, 'test', 0, 0, 'lax46', 'test.lax.46@liferay.com', 'Welcome Test LAX 46!', 'Test', '', 'LAX 46', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (365, 1, 2, 'Test LAX 46', now(), now(), 13, 364, 7, 0, 'test.lax.46@liferay.com', 'Test', '', 'LAX 46', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (366, 1, 364, 13, 364, 0, 0, '366', 0, '/lax46', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (367, 1, 366, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (368, 1, 366, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (364, 19);

insert into Users_Orgs (userId, organizationId) values (364, 22);
insert into Users_Orgs (userId, organizationId) values (364, 48);

insert into Users_Roles values (364, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (369, 366, 1, 2, 'Joe Blogs', now(), now(), 13, 364, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 46', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('45250b90-4e41-4f68-b89a-a0da7f0ee292', 370, 1, now(), now(), 0, 371, 'test', 0, 0, 'lax47', 'test.lax.47@liferay.com', 'Welcome Test LAX 47!', 'Test', '', 'LAX 47', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (371, 1, 2, 'Test LAX 47', now(), now(), 13, 370, 7, 0, 'test.lax.47@liferay.com', 'Test', '', 'LAX 47', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (372, 1, 370, 13, 370, 0, 0, '372', 0, '/lax47', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (373, 1, 372, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (374, 1, 372, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (370, 19);

insert into Users_Orgs (userId, organizationId) values (370, 22);
insert into Users_Orgs (userId, organizationId) values (370, 48);

insert into Users_Roles values (370, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (375, 372, 1, 2, 'Joe Blogs', now(), now(), 13, 370, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 47', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('d96f5fd6-8b52-4629-8420-84782856f30a', 376, 1, now(), now(), 0, 377, 'test', 0, 0, 'lax48', 'test.lax.48@liferay.com', 'Welcome Test LAX 48!', 'Test', '', 'LAX 48', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (377, 1, 2, 'Test LAX 48', now(), now(), 13, 376, 7, 0, 'test.lax.48@liferay.com', 'Test', '', 'LAX 48', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (378, 1, 376, 13, 376, 0, 0, '378', 0, '/lax48', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (379, 1, 378, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (380, 1, 378, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (376, 19);

insert into Users_Orgs (userId, organizationId) values (376, 22);
insert into Users_Orgs (userId, organizationId) values (376, 48);

insert into Users_Roles values (376, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (381, 378, 1, 2, 'Joe Blogs', now(), now(), 13, 376, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 48', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('3220bbfd-a651-4809-89f1-3ce04f02889a', 382, 1, now(), now(), 0, 383, 'test', 0, 0, 'lax49', 'test.lax.49@liferay.com', 'Welcome Test LAX 49!', 'Test', '', 'LAX 49', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (383, 1, 2, 'Test LAX 49', now(), now(), 13, 382, 7, 0, 'test.lax.49@liferay.com', 'Test', '', 'LAX 49', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (384, 1, 382, 13, 382, 0, 0, '384', 0, '/lax49', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (385, 1, 384, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (386, 1, 384, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (382, 19);

insert into Users_Orgs (userId, organizationId) values (382, 22);
insert into Users_Orgs (userId, organizationId) values (382, 48);

insert into Users_Roles values (382, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (387, 384, 1, 2, 'Joe Blogs', now(), now(), 13, 382, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 49', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('2b693eda-3c95-4b3d-9bb0-4541def6f941', 388, 1, now(), now(), 0, 389, 'test', 0, 0, 'lax50', 'test.lax.50@liferay.com', 'Welcome Test LAX 50!', 'Test', '', 'LAX 50', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (389, 1, 2, 'Test LAX 50', now(), now(), 13, 388, 7, 0, 'test.lax.50@liferay.com', 'Test', '', 'LAX 50', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (390, 1, 388, 13, 388, 0, 0, '390', 0, '/lax50', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (391, 1, 390, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (392, 1, 390, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (388, 19);

insert into Users_Orgs (userId, organizationId) values (388, 22);
insert into Users_Orgs (userId, organizationId) values (388, 48);

insert into Users_Roles values (388, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (393, 390, 1, 2, 'Joe Blogs', now(), now(), 13, 388, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 50', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('befb0dc7-eccd-46c2-9369-0a18d01aa688', 394, 1, now(), now(), 0, 395, 'test', 0, 0, 'lax51', 'test.lax.51@liferay.com', 'Welcome Test LAX 51!', 'Test', '', 'LAX 51', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (395, 1, 2, 'Test LAX 51', now(), now(), 13, 394, 7, 0, 'test.lax.51@liferay.com', 'Test', '', 'LAX 51', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (396, 1, 394, 13, 394, 0, 0, '396', 0, '/lax51', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (397, 1, 396, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (398, 1, 396, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (394, 19);

insert into Users_Orgs (userId, organizationId) values (394, 22);
insert into Users_Orgs (userId, organizationId) values (394, 48);

insert into Users_Roles values (394, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (399, 396, 1, 2, 'Joe Blogs', now(), now(), 13, 394, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 51', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('ddfa46b2-be45-4d08-9194-816cf907090d', 400, 1, now(), now(), 0, 401, 'test', 0, 0, 'lax52', 'test.lax.52@liferay.com', 'Welcome Test LAX 52!', 'Test', '', 'LAX 52', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (401, 1, 2, 'Test LAX 52', now(), now(), 13, 400, 7, 0, 'test.lax.52@liferay.com', 'Test', '', 'LAX 52', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (402, 1, 400, 13, 400, 0, 0, '402', 0, '/lax52', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (403, 1, 402, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (404, 1, 402, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (400, 19);

insert into Users_Orgs (userId, organizationId) values (400, 22);
insert into Users_Orgs (userId, organizationId) values (400, 48);

insert into Users_Roles values (400, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (405, 402, 1, 2, 'Joe Blogs', now(), now(), 13, 400, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 52', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('b1315734-403f-45fb-adbd-6c50e5dc2721', 406, 1, now(), now(), 0, 407, 'test', 0, 0, 'lax53', 'test.lax.53@liferay.com', 'Welcome Test LAX 53!', 'Test', '', 'LAX 53', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (407, 1, 2, 'Test LAX 53', now(), now(), 13, 406, 7, 0, 'test.lax.53@liferay.com', 'Test', '', 'LAX 53', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (408, 1, 406, 13, 406, 0, 0, '408', 0, '/lax53', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (409, 1, 408, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (410, 1, 408, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (406, 19);

insert into Users_Orgs (userId, organizationId) values (406, 22);
insert into Users_Orgs (userId, organizationId) values (406, 48);

insert into Users_Roles values (406, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (411, 408, 1, 2, 'Joe Blogs', now(), now(), 13, 406, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 53', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('b1e630ec-e9ea-4e14-85d0-f0ea940ccc41', 412, 1, now(), now(), 0, 413, 'test', 0, 0, 'lax54', 'test.lax.54@liferay.com', 'Welcome Test LAX 54!', 'Test', '', 'LAX 54', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (413, 1, 2, 'Test LAX 54', now(), now(), 13, 412, 7, 0, 'test.lax.54@liferay.com', 'Test', '', 'LAX 54', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (414, 1, 412, 13, 412, 0, 0, '414', 0, '/lax54', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (415, 1, 414, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (416, 1, 414, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (412, 19);

insert into Users_Orgs (userId, organizationId) values (412, 22);
insert into Users_Orgs (userId, organizationId) values (412, 48);

insert into Users_Roles values (412, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (417, 414, 1, 2, 'Joe Blogs', now(), now(), 13, 412, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 54', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('b35417f3-2008-465d-bacd-e58335e2b313', 418, 1, now(), now(), 0, 419, 'test', 0, 0, 'lax55', 'test.lax.55@liferay.com', 'Welcome Test LAX 55!', 'Test', '', 'LAX 55', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (419, 1, 2, 'Test LAX 55', now(), now(), 13, 418, 7, 0, 'test.lax.55@liferay.com', 'Test', '', 'LAX 55', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (420, 1, 418, 13, 418, 0, 0, '420', 0, '/lax55', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (421, 1, 420, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (422, 1, 420, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (418, 19);

insert into Users_Orgs (userId, organizationId) values (418, 22);
insert into Users_Orgs (userId, organizationId) values (418, 48);

insert into Users_Roles values (418, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (423, 420, 1, 2, 'Joe Blogs', now(), now(), 13, 418, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 55', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('51d0f267-ef5a-43ea-b140-152693b05d18', 424, 1, now(), now(), 0, 425, 'test', 0, 0, 'lax56', 'test.lax.56@liferay.com', 'Welcome Test LAX 56!', 'Test', '', 'LAX 56', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (425, 1, 2, 'Test LAX 56', now(), now(), 13, 424, 7, 0, 'test.lax.56@liferay.com', 'Test', '', 'LAX 56', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (426, 1, 424, 13, 424, 0, 0, '426', 0, '/lax56', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (427, 1, 426, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (428, 1, 426, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (424, 19);

insert into Users_Orgs (userId, organizationId) values (424, 22);
insert into Users_Orgs (userId, organizationId) values (424, 48);

insert into Users_Roles values (424, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (429, 426, 1, 2, 'Joe Blogs', now(), now(), 13, 424, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 56', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('f7dc3e91-e177-4e29-89ab-fa0030030079', 430, 1, now(), now(), 0, 431, 'test', 0, 0, 'lax57', 'test.lax.57@liferay.com', 'Welcome Test LAX 57!', 'Test', '', 'LAX 57', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (431, 1, 2, 'Test LAX 57', now(), now(), 13, 430, 7, 0, 'test.lax.57@liferay.com', 'Test', '', 'LAX 57', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (432, 1, 430, 13, 430, 0, 0, '432', 0, '/lax57', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (433, 1, 432, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (434, 1, 432, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (430, 19);

insert into Users_Orgs (userId, organizationId) values (430, 22);
insert into Users_Orgs (userId, organizationId) values (430, 48);

insert into Users_Roles values (430, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (435, 432, 1, 2, 'Joe Blogs', now(), now(), 13, 430, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 57', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('d0bfd74c-2d08-468d-b39e-5c321570d521', 436, 1, now(), now(), 0, 437, 'test', 0, 0, 'lax58', 'test.lax.58@liferay.com', 'Welcome Test LAX 58!', 'Test', '', 'LAX 58', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (437, 1, 2, 'Test LAX 58', now(), now(), 13, 436, 7, 0, 'test.lax.58@liferay.com', 'Test', '', 'LAX 58', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (438, 1, 436, 13, 436, 0, 0, '438', 0, '/lax58', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (439, 1, 438, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (440, 1, 438, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (436, 19);

insert into Users_Orgs (userId, organizationId) values (436, 22);
insert into Users_Orgs (userId, organizationId) values (436, 48);

insert into Users_Roles values (436, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (441, 438, 1, 2, 'Joe Blogs', now(), now(), 13, 436, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 58', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('5ce47a0c-96d5-4154-82dc-f286eb188621', 442, 1, now(), now(), 0, 443, 'test', 0, 0, 'lax59', 'test.lax.59@liferay.com', 'Welcome Test LAX 59!', 'Test', '', 'LAX 59', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (443, 1, 2, 'Test LAX 59', now(), now(), 13, 442, 7, 0, 'test.lax.59@liferay.com', 'Test', '', 'LAX 59', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (444, 1, 442, 13, 442, 0, 0, '444', 0, '/lax59', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (445, 1, 444, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (446, 1, 444, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (442, 19);

insert into Users_Orgs (userId, organizationId) values (442, 22);
insert into Users_Orgs (userId, organizationId) values (442, 48);

insert into Users_Roles values (442, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (447, 444, 1, 2, 'Joe Blogs', now(), now(), 13, 442, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 59', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('5d4cb408-4998-4020-bd19-4cac187ab89c', 448, 1, now(), now(), 0, 449, 'test', 0, 0, 'lax60', 'test.lax.60@liferay.com', 'Welcome Test LAX 60!', 'Test', '', 'LAX 60', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (449, 1, 2, 'Test LAX 60', now(), now(), 13, 448, 7, 0, 'test.lax.60@liferay.com', 'Test', '', 'LAX 60', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (450, 1, 448, 13, 448, 0, 0, '450', 0, '/lax60', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (451, 1, 450, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (452, 1, 450, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (448, 19);

insert into Users_Orgs (userId, organizationId) values (448, 22);
insert into Users_Orgs (userId, organizationId) values (448, 48);

insert into Users_Roles values (448, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (453, 450, 1, 2, 'Joe Blogs', now(), now(), 13, 448, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 60', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('f2ba7aa7-3478-4549-87e5-b4c7f89a0916', 454, 1, now(), now(), 0, 455, 'test', 0, 0, 'lax61', 'test.lax.61@liferay.com', 'Welcome Test LAX 61!', 'Test', '', 'LAX 61', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (455, 1, 2, 'Test LAX 61', now(), now(), 13, 454, 7, 0, 'test.lax.61@liferay.com', 'Test', '', 'LAX 61', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (456, 1, 454, 13, 454, 0, 0, '456', 0, '/lax61', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (457, 1, 456, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (458, 1, 456, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (454, 19);

insert into Users_Orgs (userId, organizationId) values (454, 22);
insert into Users_Orgs (userId, organizationId) values (454, 48);

insert into Users_Roles values (454, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (459, 456, 1, 2, 'Joe Blogs', now(), now(), 13, 454, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 61', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('3715909b-af60-4728-a58c-326bf089ce39', 460, 1, now(), now(), 0, 461, 'test', 0, 0, 'lax62', 'test.lax.62@liferay.com', 'Welcome Test LAX 62!', 'Test', '', 'LAX 62', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (461, 1, 2, 'Test LAX 62', now(), now(), 13, 460, 7, 0, 'test.lax.62@liferay.com', 'Test', '', 'LAX 62', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (462, 1, 460, 13, 460, 0, 0, '462', 0, '/lax62', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (463, 1, 462, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (464, 1, 462, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (460, 19);

insert into Users_Orgs (userId, organizationId) values (460, 22);
insert into Users_Orgs (userId, organizationId) values (460, 48);

insert into Users_Roles values (460, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (465, 462, 1, 2, 'Joe Blogs', now(), now(), 13, 460, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 62', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('f5d5d0e9-3c5a-4487-9d79-e00043f694dd', 466, 1, now(), now(), 0, 467, 'test', 0, 0, 'lax63', 'test.lax.63@liferay.com', 'Welcome Test LAX 63!', 'Test', '', 'LAX 63', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (467, 1, 2, 'Test LAX 63', now(), now(), 13, 466, 7, 0, 'test.lax.63@liferay.com', 'Test', '', 'LAX 63', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (468, 1, 466, 13, 466, 0, 0, '468', 0, '/lax63', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (469, 1, 468, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (470, 1, 468, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (466, 19);

insert into Users_Orgs (userId, organizationId) values (466, 22);
insert into Users_Orgs (userId, organizationId) values (466, 48);

insert into Users_Roles values (466, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (471, 468, 1, 2, 'Joe Blogs', now(), now(), 13, 466, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 63', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('c033afb9-9148-4c0f-9479-be9791872de3', 472, 1, now(), now(), 0, 473, 'test', 0, 0, 'lax64', 'test.lax.64@liferay.com', 'Welcome Test LAX 64!', 'Test', '', 'LAX 64', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (473, 1, 2, 'Test LAX 64', now(), now(), 13, 472, 7, 0, 'test.lax.64@liferay.com', 'Test', '', 'LAX 64', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (474, 1, 472, 13, 472, 0, 0, '474', 0, '/lax64', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (475, 1, 474, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (476, 1, 474, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (472, 19);

insert into Users_Orgs (userId, organizationId) values (472, 22);
insert into Users_Orgs (userId, organizationId) values (472, 48);

insert into Users_Roles values (472, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (477, 474, 1, 2, 'Joe Blogs', now(), now(), 13, 472, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 64', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('4fedabe1-cf56-4e4d-8cdb-ee16b17e09b8', 478, 1, now(), now(), 0, 479, 'test', 0, 0, 'lax65', 'test.lax.65@liferay.com', 'Welcome Test LAX 65!', 'Test', '', 'LAX 65', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (479, 1, 2, 'Test LAX 65', now(), now(), 13, 478, 7, 0, 'test.lax.65@liferay.com', 'Test', '', 'LAX 65', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (480, 1, 478, 13, 478, 0, 0, '480', 0, '/lax65', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (481, 1, 480, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (482, 1, 480, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (478, 19);

insert into Users_Orgs (userId, organizationId) values (478, 22);
insert into Users_Orgs (userId, organizationId) values (478, 48);

insert into Users_Roles values (478, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (483, 480, 1, 2, 'Joe Blogs', now(), now(), 13, 478, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 65', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('724bcd2d-b8d5-4643-8a97-dd047e72f08f', 484, 1, now(), now(), 0, 485, 'test', 0, 0, 'lax66', 'test.lax.66@liferay.com', 'Welcome Test LAX 66!', 'Test', '', 'LAX 66', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (485, 1, 2, 'Test LAX 66', now(), now(), 13, 484, 7, 0, 'test.lax.66@liferay.com', 'Test', '', 'LAX 66', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (486, 1, 484, 13, 484, 0, 0, '486', 0, '/lax66', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (487, 1, 486, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (488, 1, 486, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (484, 19);

insert into Users_Orgs (userId, organizationId) values (484, 22);
insert into Users_Orgs (userId, organizationId) values (484, 48);

insert into Users_Roles values (484, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (489, 486, 1, 2, 'Joe Blogs', now(), now(), 13, 484, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 66', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('20c42bd2-759c-44f5-826c-4c53cf902baf', 490, 1, now(), now(), 0, 491, 'test', 0, 0, 'lax67', 'test.lax.67@liferay.com', 'Welcome Test LAX 67!', 'Test', '', 'LAX 67', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (491, 1, 2, 'Test LAX 67', now(), now(), 13, 490, 7, 0, 'test.lax.67@liferay.com', 'Test', '', 'LAX 67', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (492, 1, 490, 13, 490, 0, 0, '492', 0, '/lax67', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (493, 1, 492, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (494, 1, 492, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (490, 19);

insert into Users_Orgs (userId, organizationId) values (490, 22);
insert into Users_Orgs (userId, organizationId) values (490, 48);

insert into Users_Roles values (490, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (495, 492, 1, 2, 'Joe Blogs', now(), now(), 13, 490, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 67', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('37ba96db-d1ff-4035-b308-3902701888ea', 496, 1, now(), now(), 0, 497, 'test', 0, 0, 'lax68', 'test.lax.68@liferay.com', 'Welcome Test LAX 68!', 'Test', '', 'LAX 68', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (497, 1, 2, 'Test LAX 68', now(), now(), 13, 496, 7, 0, 'test.lax.68@liferay.com', 'Test', '', 'LAX 68', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (498, 1, 496, 13, 496, 0, 0, '498', 0, '/lax68', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (499, 1, 498, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (500, 1, 498, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (496, 19);

insert into Users_Orgs (userId, organizationId) values (496, 22);
insert into Users_Orgs (userId, organizationId) values (496, 48);

insert into Users_Roles values (496, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (501, 498, 1, 2, 'Joe Blogs', now(), now(), 13, 496, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 68', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('ef8a7cd8-fa1f-482d-8a71-116eaba39ae6', 502, 1, now(), now(), 0, 503, 'test', 0, 0, 'lax69', 'test.lax.69@liferay.com', 'Welcome Test LAX 69!', 'Test', '', 'LAX 69', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (503, 1, 2, 'Test LAX 69', now(), now(), 13, 502, 7, 0, 'test.lax.69@liferay.com', 'Test', '', 'LAX 69', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (504, 1, 502, 13, 502, 0, 0, '504', 0, '/lax69', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (505, 1, 504, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (506, 1, 504, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (502, 19);

insert into Users_Orgs (userId, organizationId) values (502, 22);
insert into Users_Orgs (userId, organizationId) values (502, 48);

insert into Users_Roles values (502, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (507, 504, 1, 2, 'Joe Blogs', now(), now(), 13, 502, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 69', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('1f19fcb9-8c26-4efe-91f9-e88684795fca', 508, 1, now(), now(), 0, 509, 'test', 0, 0, 'lax70', 'test.lax.70@liferay.com', 'Welcome Test LAX 70!', 'Test', '', 'LAX 70', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (509, 1, 2, 'Test LAX 70', now(), now(), 13, 508, 7, 0, 'test.lax.70@liferay.com', 'Test', '', 'LAX 70', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (510, 1, 508, 13, 508, 0, 0, '510', 0, '/lax70', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (511, 1, 510, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (512, 1, 510, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (508, 19);

insert into Users_Orgs (userId, organizationId) values (508, 22);
insert into Users_Orgs (userId, organizationId) values (508, 48);

insert into Users_Roles values (508, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (513, 510, 1, 2, 'Joe Blogs', now(), now(), 13, 508, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 70', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('48cb0ab8-48b8-4daf-aaf1-2c4a6ef3c716', 514, 1, now(), now(), 0, 515, 'test', 0, 0, 'lax71', 'test.lax.71@liferay.com', 'Welcome Test LAX 71!', 'Test', '', 'LAX 71', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (515, 1, 2, 'Test LAX 71', now(), now(), 13, 514, 7, 0, 'test.lax.71@liferay.com', 'Test', '', 'LAX 71', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (516, 1, 514, 13, 514, 0, 0, '516', 0, '/lax71', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (517, 1, 516, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (518, 1, 516, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (514, 19);

insert into Users_Orgs (userId, organizationId) values (514, 22);
insert into Users_Orgs (userId, organizationId) values (514, 48);

insert into Users_Roles values (514, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (519, 516, 1, 2, 'Joe Blogs', now(), now(), 13, 514, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 71', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('24525b96-fccd-4376-9ad9-a948396bb70c', 520, 1, now(), now(), 0, 521, 'test', 0, 0, 'lax72', 'test.lax.72@liferay.com', 'Welcome Test LAX 72!', 'Test', '', 'LAX 72', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (521, 1, 2, 'Test LAX 72', now(), now(), 13, 520, 7, 0, 'test.lax.72@liferay.com', 'Test', '', 'LAX 72', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (522, 1, 520, 13, 520, 0, 0, '522', 0, '/lax72', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (523, 1, 522, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (524, 1, 522, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (520, 19);

insert into Users_Orgs (userId, organizationId) values (520, 22);
insert into Users_Orgs (userId, organizationId) values (520, 48);

insert into Users_Roles values (520, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (525, 522, 1, 2, 'Joe Blogs', now(), now(), 13, 520, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 72', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('af9a57bd-13fe-4194-9ddc-7dc0bf5a6e5b', 526, 1, now(), now(), 0, 527, 'test', 0, 0, 'lax73', 'test.lax.73@liferay.com', 'Welcome Test LAX 73!', 'Test', '', 'LAX 73', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (527, 1, 2, 'Test LAX 73', now(), now(), 13, 526, 7, 0, 'test.lax.73@liferay.com', 'Test', '', 'LAX 73', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (528, 1, 526, 13, 526, 0, 0, '528', 0, '/lax73', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (529, 1, 528, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (530, 1, 528, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (526, 19);

insert into Users_Orgs (userId, organizationId) values (526, 22);
insert into Users_Orgs (userId, organizationId) values (526, 48);

insert into Users_Roles values (526, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (531, 528, 1, 2, 'Joe Blogs', now(), now(), 13, 526, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 73', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('88c3fc8c-e4d9-4d32-a1ae-9a1450577008', 532, 1, now(), now(), 0, 533, 'test', 0, 0, 'lax74', 'test.lax.74@liferay.com', 'Welcome Test LAX 74!', 'Test', '', 'LAX 74', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (533, 1, 2, 'Test LAX 74', now(), now(), 13, 532, 7, 0, 'test.lax.74@liferay.com', 'Test', '', 'LAX 74', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (534, 1, 532, 13, 532, 0, 0, '534', 0, '/lax74', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (535, 1, 534, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (536, 1, 534, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (532, 19);

insert into Users_Orgs (userId, organizationId) values (532, 22);
insert into Users_Orgs (userId, organizationId) values (532, 48);

insert into Users_Roles values (532, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (537, 534, 1, 2, 'Joe Blogs', now(), now(), 13, 532, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 74', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('ee5f8188-0a37-4dc8-82e7-39cecea38072', 538, 1, now(), now(), 0, 539, 'test', 0, 0, 'lax75', 'test.lax.75@liferay.com', 'Welcome Test LAX 75!', 'Test', '', 'LAX 75', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (539, 1, 2, 'Test LAX 75', now(), now(), 13, 538, 7, 0, 'test.lax.75@liferay.com', 'Test', '', 'LAX 75', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (540, 1, 538, 13, 538, 0, 0, '540', 0, '/lax75', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (541, 1, 540, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (542, 1, 540, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (538, 19);

insert into Users_Orgs (userId, organizationId) values (538, 22);
insert into Users_Orgs (userId, organizationId) values (538, 48);

insert into Users_Roles values (538, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (543, 540, 1, 2, 'Joe Blogs', now(), now(), 13, 538, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 75', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('60bda63e-2055-4390-b8c1-20071ad63466', 544, 1, now(), now(), 0, 545, 'test', 0, 0, 'lax76', 'test.lax.76@liferay.com', 'Welcome Test LAX 76!', 'Test', '', 'LAX 76', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (545, 1, 2, 'Test LAX 76', now(), now(), 13, 544, 7, 0, 'test.lax.76@liferay.com', 'Test', '', 'LAX 76', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (546, 1, 544, 13, 544, 0, 0, '546', 0, '/lax76', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (547, 1, 546, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (548, 1, 546, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (544, 19);

insert into Users_Orgs (userId, organizationId) values (544, 22);
insert into Users_Orgs (userId, organizationId) values (544, 48);

insert into Users_Roles values (544, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (549, 546, 1, 2, 'Joe Blogs', now(), now(), 13, 544, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 76', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('d6e8735e-daf4-48ef-8c6e-370e06dcb178', 550, 1, now(), now(), 0, 551, 'test', 0, 0, 'lax77', 'test.lax.77@liferay.com', 'Welcome Test LAX 77!', 'Test', '', 'LAX 77', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (551, 1, 2, 'Test LAX 77', now(), now(), 13, 550, 7, 0, 'test.lax.77@liferay.com', 'Test', '', 'LAX 77', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (552, 1, 550, 13, 550, 0, 0, '552', 0, '/lax77', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (553, 1, 552, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (554, 1, 552, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (550, 19);

insert into Users_Orgs (userId, organizationId) values (550, 22);
insert into Users_Orgs (userId, organizationId) values (550, 48);

insert into Users_Roles values (550, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (555, 552, 1, 2, 'Joe Blogs', now(), now(), 13, 550, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 77', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('c06b42ae-443b-48e1-bb9c-7db634691e79', 556, 1, now(), now(), 0, 557, 'test', 0, 0, 'lax78', 'test.lax.78@liferay.com', 'Welcome Test LAX 78!', 'Test', '', 'LAX 78', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (557, 1, 2, 'Test LAX 78', now(), now(), 13, 556, 7, 0, 'test.lax.78@liferay.com', 'Test', '', 'LAX 78', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (558, 1, 556, 13, 556, 0, 0, '558', 0, '/lax78', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (559, 1, 558, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (560, 1, 558, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (556, 19);

insert into Users_Orgs (userId, organizationId) values (556, 22);
insert into Users_Orgs (userId, organizationId) values (556, 48);

insert into Users_Roles values (556, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (561, 558, 1, 2, 'Joe Blogs', now(), now(), 13, 556, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 78', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('17ed87df-92d1-4b31-ab8a-23d8c44c2ede', 562, 1, now(), now(), 0, 563, 'test', 0, 0, 'lax79', 'test.lax.79@liferay.com', 'Welcome Test LAX 79!', 'Test', '', 'LAX 79', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (563, 1, 2, 'Test LAX 79', now(), now(), 13, 562, 7, 0, 'test.lax.79@liferay.com', 'Test', '', 'LAX 79', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (564, 1, 562, 13, 562, 0, 0, '564', 0, '/lax79', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (565, 1, 564, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (566, 1, 564, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (562, 19);

insert into Users_Orgs (userId, organizationId) values (562, 22);
insert into Users_Orgs (userId, organizationId) values (562, 48);

insert into Users_Roles values (562, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (567, 564, 1, 2, 'Joe Blogs', now(), now(), 13, 562, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 79', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('d48614e1-b615-4420-ab08-e2b636bd0882', 568, 1, now(), now(), 0, 569, 'test', 0, 0, 'lax80', 'test.lax.80@liferay.com', 'Welcome Test LAX 80!', 'Test', '', 'LAX 80', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (569, 1, 2, 'Test LAX 80', now(), now(), 13, 568, 7, 0, 'test.lax.80@liferay.com', 'Test', '', 'LAX 80', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (570, 1, 568, 13, 568, 0, 0, '570', 0, '/lax80', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (571, 1, 570, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (572, 1, 570, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (568, 19);

insert into Users_Orgs (userId, organizationId) values (568, 22);
insert into Users_Orgs (userId, organizationId) values (568, 48);

insert into Users_Roles values (568, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (573, 570, 1, 2, 'Joe Blogs', now(), now(), 13, 568, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 80', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('92eeb366-df1a-41f0-8f2b-5890ac73e343', 574, 1, now(), now(), 0, 575, 'test', 0, 0, 'lax81', 'test.lax.81@liferay.com', 'Welcome Test LAX 81!', 'Test', '', 'LAX 81', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (575, 1, 2, 'Test LAX 81', now(), now(), 13, 574, 7, 0, 'test.lax.81@liferay.com', 'Test', '', 'LAX 81', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (576, 1, 574, 13, 574, 0, 0, '576', 0, '/lax81', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (577, 1, 576, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (578, 1, 576, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (574, 19);

insert into Users_Orgs (userId, organizationId) values (574, 22);
insert into Users_Orgs (userId, organizationId) values (574, 48);

insert into Users_Roles values (574, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (579, 576, 1, 2, 'Joe Blogs', now(), now(), 13, 574, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 81', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('60fa7cd1-96bb-4e57-b709-4d47191d9001', 580, 1, now(), now(), 0, 581, 'test', 0, 0, 'lax82', 'test.lax.82@liferay.com', 'Welcome Test LAX 82!', 'Test', '', 'LAX 82', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (581, 1, 2, 'Test LAX 82', now(), now(), 13, 580, 7, 0, 'test.lax.82@liferay.com', 'Test', '', 'LAX 82', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (582, 1, 580, 13, 580, 0, 0, '582', 0, '/lax82', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (583, 1, 582, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (584, 1, 582, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (580, 19);

insert into Users_Orgs (userId, organizationId) values (580, 22);
insert into Users_Orgs (userId, organizationId) values (580, 48);

insert into Users_Roles values (580, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (585, 582, 1, 2, 'Joe Blogs', now(), now(), 13, 580, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 82', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('acfc79b5-aaf9-49d8-a14e-cf17ea3f8501', 586, 1, now(), now(), 0, 587, 'test', 0, 0, 'lax83', 'test.lax.83@liferay.com', 'Welcome Test LAX 83!', 'Test', '', 'LAX 83', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (587, 1, 2, 'Test LAX 83', now(), now(), 13, 586, 7, 0, 'test.lax.83@liferay.com', 'Test', '', 'LAX 83', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (588, 1, 586, 13, 586, 0, 0, '588', 0, '/lax83', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (589, 1, 588, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (590, 1, 588, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (586, 19);

insert into Users_Orgs (userId, organizationId) values (586, 22);
insert into Users_Orgs (userId, organizationId) values (586, 48);

insert into Users_Roles values (586, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (591, 588, 1, 2, 'Joe Blogs', now(), now(), 13, 586, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 83', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('7b61affe-5435-4cc9-b6b3-48a9b45e5089', 592, 1, now(), now(), 0, 593, 'test', 0, 0, 'lax84', 'test.lax.84@liferay.com', 'Welcome Test LAX 84!', 'Test', '', 'LAX 84', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (593, 1, 2, 'Test LAX 84', now(), now(), 13, 592, 7, 0, 'test.lax.84@liferay.com', 'Test', '', 'LAX 84', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (594, 1, 592, 13, 592, 0, 0, '594', 0, '/lax84', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (595, 1, 594, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (596, 1, 594, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (592, 19);

insert into Users_Orgs (userId, organizationId) values (592, 22);
insert into Users_Orgs (userId, organizationId) values (592, 48);

insert into Users_Roles values (592, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (597, 594, 1, 2, 'Joe Blogs', now(), now(), 13, 592, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 84', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('1c28bd82-2542-472d-bea6-25b8e8079864', 598, 1, now(), now(), 0, 599, 'test', 0, 0, 'lax85', 'test.lax.85@liferay.com', 'Welcome Test LAX 85!', 'Test', '', 'LAX 85', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (599, 1, 2, 'Test LAX 85', now(), now(), 13, 598, 7, 0, 'test.lax.85@liferay.com', 'Test', '', 'LAX 85', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (600, 1, 598, 13, 598, 0, 0, '600', 0, '/lax85', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (601, 1, 600, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (602, 1, 600, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (598, 19);

insert into Users_Orgs (userId, organizationId) values (598, 22);
insert into Users_Orgs (userId, organizationId) values (598, 48);

insert into Users_Roles values (598, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (603, 600, 1, 2, 'Joe Blogs', now(), now(), 13, 598, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 85', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('d3d82dc0-fa0b-43b5-80ff-524e05125f6b', 604, 1, now(), now(), 0, 605, 'test', 0, 0, 'lax86', 'test.lax.86@liferay.com', 'Welcome Test LAX 86!', 'Test', '', 'LAX 86', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (605, 1, 2, 'Test LAX 86', now(), now(), 13, 604, 7, 0, 'test.lax.86@liferay.com', 'Test', '', 'LAX 86', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (606, 1, 604, 13, 604, 0, 0, '606', 0, '/lax86', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (607, 1, 606, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (608, 1, 606, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (604, 19);

insert into Users_Orgs (userId, organizationId) values (604, 22);
insert into Users_Orgs (userId, organizationId) values (604, 48);

insert into Users_Roles values (604, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (609, 606, 1, 2, 'Joe Blogs', now(), now(), 13, 604, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 86', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('3d524d6f-3094-4556-9c88-1c9d6d1553f3', 610, 1, now(), now(), 0, 611, 'test', 0, 0, 'lax87', 'test.lax.87@liferay.com', 'Welcome Test LAX 87!', 'Test', '', 'LAX 87', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (611, 1, 2, 'Test LAX 87', now(), now(), 13, 610, 7, 0, 'test.lax.87@liferay.com', 'Test', '', 'LAX 87', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (612, 1, 610, 13, 610, 0, 0, '612', 0, '/lax87', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (613, 1, 612, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (614, 1, 612, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (610, 19);

insert into Users_Orgs (userId, organizationId) values (610, 22);
insert into Users_Orgs (userId, organizationId) values (610, 48);

insert into Users_Roles values (610, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (615, 612, 1, 2, 'Joe Blogs', now(), now(), 13, 610, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 87', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('9e05142b-4f4c-4daf-8f2a-42ee1410a2c4', 616, 1, now(), now(), 0, 617, 'test', 0, 0, 'lax88', 'test.lax.88@liferay.com', 'Welcome Test LAX 88!', 'Test', '', 'LAX 88', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (617, 1, 2, 'Test LAX 88', now(), now(), 13, 616, 7, 0, 'test.lax.88@liferay.com', 'Test', '', 'LAX 88', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (618, 1, 616, 13, 616, 0, 0, '618', 0, '/lax88', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (619, 1, 618, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (620, 1, 618, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (616, 19);

insert into Users_Orgs (userId, organizationId) values (616, 22);
insert into Users_Orgs (userId, organizationId) values (616, 48);

insert into Users_Roles values (616, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (621, 618, 1, 2, 'Joe Blogs', now(), now(), 13, 616, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 88', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('a82386ef-495f-4c40-b4d7-65f729b3a9a3', 622, 1, now(), now(), 0, 623, 'test', 0, 0, 'lax89', 'test.lax.89@liferay.com', 'Welcome Test LAX 89!', 'Test', '', 'LAX 89', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (623, 1, 2, 'Test LAX 89', now(), now(), 13, 622, 7, 0, 'test.lax.89@liferay.com', 'Test', '', 'LAX 89', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (624, 1, 622, 13, 622, 0, 0, '624', 0, '/lax89', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (625, 1, 624, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (626, 1, 624, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (622, 19);

insert into Users_Orgs (userId, organizationId) values (622, 22);
insert into Users_Orgs (userId, organizationId) values (622, 48);

insert into Users_Roles values (622, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (627, 624, 1, 2, 'Joe Blogs', now(), now(), 13, 622, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 89', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('2e31bc1c-23ff-495d-914a-34cea1804bc0', 628, 1, now(), now(), 0, 629, 'test', 0, 0, 'lax90', 'test.lax.90@liferay.com', 'Welcome Test LAX 90!', 'Test', '', 'LAX 90', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (629, 1, 2, 'Test LAX 90', now(), now(), 13, 628, 7, 0, 'test.lax.90@liferay.com', 'Test', '', 'LAX 90', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (630, 1, 628, 13, 628, 0, 0, '630', 0, '/lax90', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (631, 1, 630, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (632, 1, 630, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (628, 19);

insert into Users_Orgs (userId, organizationId) values (628, 22);
insert into Users_Orgs (userId, organizationId) values (628, 48);

insert into Users_Roles values (628, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (633, 630, 1, 2, 'Joe Blogs', now(), now(), 13, 628, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 90', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('b88fa844-61bf-4201-b35d-9851c185c8e3', 634, 1, now(), now(), 0, 635, 'test', 0, 0, 'lax91', 'test.lax.91@liferay.com', 'Welcome Test LAX 91!', 'Test', '', 'LAX 91', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (635, 1, 2, 'Test LAX 91', now(), now(), 13, 634, 7, 0, 'test.lax.91@liferay.com', 'Test', '', 'LAX 91', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (636, 1, 634, 13, 634, 0, 0, '636', 0, '/lax91', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (637, 1, 636, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (638, 1, 636, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (634, 19);

insert into Users_Orgs (userId, organizationId) values (634, 22);
insert into Users_Orgs (userId, organizationId) values (634, 48);

insert into Users_Roles values (634, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (639, 636, 1, 2, 'Joe Blogs', now(), now(), 13, 634, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 91', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('43e51ad2-c24a-44a6-8659-4f03a28593ac', 640, 1, now(), now(), 0, 641, 'test', 0, 0, 'lax92', 'test.lax.92@liferay.com', 'Welcome Test LAX 92!', 'Test', '', 'LAX 92', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (641, 1, 2, 'Test LAX 92', now(), now(), 13, 640, 7, 0, 'test.lax.92@liferay.com', 'Test', '', 'LAX 92', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (642, 1, 640, 13, 640, 0, 0, '642', 0, '/lax92', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (643, 1, 642, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (644, 1, 642, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (640, 19);

insert into Users_Orgs (userId, organizationId) values (640, 22);
insert into Users_Orgs (userId, organizationId) values (640, 48);

insert into Users_Roles values (640, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (645, 642, 1, 2, 'Joe Blogs', now(), now(), 13, 640, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 92', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('c0e8d9a6-ad3d-4760-bcef-0aec910dbf44', 646, 1, now(), now(), 0, 647, 'test', 0, 0, 'lax93', 'test.lax.93@liferay.com', 'Welcome Test LAX 93!', 'Test', '', 'LAX 93', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (647, 1, 2, 'Test LAX 93', now(), now(), 13, 646, 7, 0, 'test.lax.93@liferay.com', 'Test', '', 'LAX 93', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (648, 1, 646, 13, 646, 0, 0, '648', 0, '/lax93', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (649, 1, 648, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (650, 1, 648, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (646, 19);

insert into Users_Orgs (userId, organizationId) values (646, 22);
insert into Users_Orgs (userId, organizationId) values (646, 48);

insert into Users_Roles values (646, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (651, 648, 1, 2, 'Joe Blogs', now(), now(), 13, 646, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 93', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('010ffc2b-5314-4f0a-b93f-f1af38df7d4b', 652, 1, now(), now(), 0, 653, 'test', 0, 0, 'lax94', 'test.lax.94@liferay.com', 'Welcome Test LAX 94!', 'Test', '', 'LAX 94', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (653, 1, 2, 'Test LAX 94', now(), now(), 13, 652, 7, 0, 'test.lax.94@liferay.com', 'Test', '', 'LAX 94', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (654, 1, 652, 13, 652, 0, 0, '654', 0, '/lax94', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (655, 1, 654, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (656, 1, 654, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (652, 19);

insert into Users_Orgs (userId, organizationId) values (652, 22);
insert into Users_Orgs (userId, organizationId) values (652, 48);

insert into Users_Roles values (652, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (657, 654, 1, 2, 'Joe Blogs', now(), now(), 13, 652, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 94', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('5e7778b2-5b70-4e7f-a23a-85d096805686', 658, 1, now(), now(), 0, 659, 'test', 0, 0, 'lax95', 'test.lax.95@liferay.com', 'Welcome Test LAX 95!', 'Test', '', 'LAX 95', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (659, 1, 2, 'Test LAX 95', now(), now(), 13, 658, 7, 0, 'test.lax.95@liferay.com', 'Test', '', 'LAX 95', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (660, 1, 658, 13, 658, 0, 0, '660', 0, '/lax95', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (661, 1, 660, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (662, 1, 660, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (658, 19);

insert into Users_Orgs (userId, organizationId) values (658, 22);
insert into Users_Orgs (userId, organizationId) values (658, 48);

insert into Users_Roles values (658, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (663, 660, 1, 2, 'Joe Blogs', now(), now(), 13, 658, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 95', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('18a3403a-7e81-426e-9af1-5b2d94edf2b1', 664, 1, now(), now(), 0, 665, 'test', 0, 0, 'lax96', 'test.lax.96@liferay.com', 'Welcome Test LAX 96!', 'Test', '', 'LAX 96', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (665, 1, 2, 'Test LAX 96', now(), now(), 13, 664, 7, 0, 'test.lax.96@liferay.com', 'Test', '', 'LAX 96', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (666, 1, 664, 13, 664, 0, 0, '666', 0, '/lax96', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (667, 1, 666, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (668, 1, 666, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (664, 19);

insert into Users_Orgs (userId, organizationId) values (664, 22);
insert into Users_Orgs (userId, organizationId) values (664, 48);

insert into Users_Roles values (664, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (669, 666, 1, 2, 'Joe Blogs', now(), now(), 13, 664, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 96', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('3451ce98-1cbb-4953-b322-1c0b372a41a4', 670, 1, now(), now(), 0, 671, 'test', 0, 0, 'lax97', 'test.lax.97@liferay.com', 'Welcome Test LAX 97!', 'Test', '', 'LAX 97', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (671, 1, 2, 'Test LAX 97', now(), now(), 13, 670, 7, 0, 'test.lax.97@liferay.com', 'Test', '', 'LAX 97', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (672, 1, 670, 13, 670, 0, 0, '672', 0, '/lax97', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (673, 1, 672, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (674, 1, 672, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (670, 19);

insert into Users_Orgs (userId, organizationId) values (670, 22);
insert into Users_Orgs (userId, organizationId) values (670, 48);

insert into Users_Roles values (670, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (675, 672, 1, 2, 'Joe Blogs', now(), now(), 13, 670, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 97', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('7a5c86b1-648f-44f3-8dd9-3b1c791a64f0', 676, 1, now(), now(), 0, 677, 'test', 0, 0, 'lax98', 'test.lax.98@liferay.com', 'Welcome Test LAX 98!', 'Test', '', 'LAX 98', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (677, 1, 2, 'Test LAX 98', now(), now(), 13, 676, 7, 0, 'test.lax.98@liferay.com', 'Test', '', 'LAX 98', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (678, 1, 676, 13, 676, 0, 0, '678', 0, '/lax98', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (679, 1, 678, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (680, 1, 678, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (676, 19);

insert into Users_Orgs (userId, organizationId) values (676, 22);
insert into Users_Orgs (userId, organizationId) values (676, 48);

insert into Users_Roles values (676, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (681, 678, 1, 2, 'Joe Blogs', now(), now(), 13, 676, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 98', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('36d1b0b8-13e7-47d9-b7ef-60c327fad4c9', 682, 1, now(), now(), 0, 683, 'test', 0, 0, 'lax99', 'test.lax.99@liferay.com', 'Welcome Test LAX 99!', 'Test', '', 'LAX 99', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (683, 1, 2, 'Test LAX 99', now(), now(), 13, 682, 7, 0, 'test.lax.99@liferay.com', 'Test', '', 'LAX 99', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (684, 1, 682, 13, 682, 0, 0, '684', 0, '/lax99', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (685, 1, 684, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (686, 1, 684, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (682, 19);

insert into Users_Orgs (userId, organizationId) values (682, 22);
insert into Users_Orgs (userId, organizationId) values (682, 48);

insert into Users_Roles values (682, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (687, 684, 1, 2, 'Joe Blogs', now(), now(), 13, 682, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 99', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('a42d4a36-a835-42dc-a092-22ee981ad973', 688, 1, now(), now(), 0, 689, 'test', 0, 0, 'lax100', 'test.lax.100@liferay.com', 'Welcome Test LAX 100!', 'Test', '', 'LAX 100', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (689, 1, 2, 'Test LAX 100', now(), now(), 13, 688, 7, 0, 'test.lax.100@liferay.com', 'Test', '', 'LAX 100', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (690, 1, 688, 13, 688, 0, 0, '690', 0, '/lax100', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (691, 1, 690, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (692, 1, 690, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (688, 19);

insert into Users_Orgs (userId, organizationId) values (688, 22);
insert into Users_Orgs (userId, organizationId) values (688, 48);

insert into Users_Roles values (688, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (693, 690, 1, 2, 'Joe Blogs', now(), now(), 13, 688, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test LAX 100', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('38aded64-8d77-4a4c-b0c8-e786f436ff11', 694, 1, now(), now(), 0, 695, 'test', 0, 0, 'sfo1', 'test.sfo.1@liferay.com', 'Welcome Test SFO 1!', 'Test', '', 'SFO 1', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (695, 1, 2, 'Test SFO 1', now(), now(), 13, 694, 7, 0, 'test.sfo.1@liferay.com', 'Test', '', 'SFO 1', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (696, 1, 694, 13, 694, 0, 0, '696', 0, '/sfo1', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (697, 1, 696, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (698, 1, 696, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (694, 19);

insert into Users_Orgs (userId, organizationId) values (694, 22);
insert into Users_Orgs (userId, organizationId) values (694, 52);

insert into Users_Roles values (694, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (699, 696, 1, 2, 'Joe Blogs', now(), now(), 13, 694, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test SFO 1', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('159c7878-062f-421f-84d9-3171bd299c70', 700, 1, now(), now(), 0, 701, 'test', 0, 0, 'sfo2', 'test.sfo.2@liferay.com', 'Welcome Test SFO 2!', 'Test', '', 'SFO 2', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (701, 1, 2, 'Test SFO 2', now(), now(), 13, 700, 7, 0, 'test.sfo.2@liferay.com', 'Test', '', 'SFO 2', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (702, 1, 700, 13, 700, 0, 0, '702', 0, '/sfo2', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (703, 1, 702, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (704, 1, 702, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (700, 19);

insert into Users_Orgs (userId, organizationId) values (700, 22);
insert into Users_Orgs (userId, organizationId) values (700, 52);

insert into Users_Roles values (700, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (705, 702, 1, 2, 'Joe Blogs', now(), now(), 13, 700, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test SFO 2', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('effeb0a9-4d9c-4104-bb5c-54b777b11dd2', 706, 1, now(), now(), 0, 707, 'test', 0, 0, 'sfo3', 'test.sfo.3@liferay.com', 'Welcome Test SFO 3!', 'Test', '', 'SFO 3', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (707, 1, 2, 'Test SFO 3', now(), now(), 13, 706, 7, 0, 'test.sfo.3@liferay.com', 'Test', '', 'SFO 3', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (708, 1, 706, 13, 706, 0, 0, '708', 0, '/sfo3', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (709, 1, 708, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (710, 1, 708, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (706, 19);

insert into Users_Orgs (userId, organizationId) values (706, 22);
insert into Users_Orgs (userId, organizationId) values (706, 52);

insert into Users_Roles values (706, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (711, 708, 1, 2, 'Joe Blogs', now(), now(), 13, 706, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test SFO 3', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('f37fe2bc-225e-4a04-af61-6d2c647b5b19', 712, 1, now(), now(), 0, 713, 'test', 0, 0, 'sfo4', 'test.sfo.4@liferay.com', 'Welcome Test SFO 4!', 'Test', '', 'SFO 4', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (713, 1, 2, 'Test SFO 4', now(), now(), 13, 712, 7, 0, 'test.sfo.4@liferay.com', 'Test', '', 'SFO 4', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (714, 1, 712, 13, 712, 0, 0, '714', 0, '/sfo4', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (715, 1, 714, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (716, 1, 714, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (712, 19);

insert into Users_Orgs (userId, organizationId) values (712, 22);
insert into Users_Orgs (userId, organizationId) values (712, 52);

insert into Users_Roles values (712, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (717, 714, 1, 2, 'Joe Blogs', now(), now(), 13, 712, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test SFO 4', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('ab3c4bb1-5f76-4603-88fb-618a3a285e6d', 718, 1, now(), now(), 0, 719, 'test', 0, 0, 'sfo5', 'test.sfo.5@liferay.com', 'Welcome Test SFO 5!', 'Test', '', 'SFO 5', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (719, 1, 2, 'Test SFO 5', now(), now(), 13, 718, 7, 0, 'test.sfo.5@liferay.com', 'Test', '', 'SFO 5', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (720, 1, 718, 13, 718, 0, 0, '720', 0, '/sfo5', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (721, 1, 720, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (722, 1, 720, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (718, 19);

insert into Users_Orgs (userId, organizationId) values (718, 22);
insert into Users_Orgs (userId, organizationId) values (718, 52);

insert into Users_Roles values (718, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (723, 720, 1, 2, 'Joe Blogs', now(), now(), 13, 718, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test SFO 5', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('f1f5958d-1f3e-401e-b4b4-8f627c660fab', 724, 1, now(), now(), 0, 725, 'test', 0, 0, 'sfo6', 'test.sfo.6@liferay.com', 'Welcome Test SFO 6!', 'Test', '', 'SFO 6', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (725, 1, 2, 'Test SFO 6', now(), now(), 13, 724, 7, 0, 'test.sfo.6@liferay.com', 'Test', '', 'SFO 6', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (726, 1, 724, 13, 724, 0, 0, '726', 0, '/sfo6', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (727, 1, 726, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (728, 1, 726, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (724, 19);

insert into Users_Orgs (userId, organizationId) values (724, 22);
insert into Users_Orgs (userId, organizationId) values (724, 52);

insert into Users_Roles values (724, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (729, 726, 1, 2, 'Joe Blogs', now(), now(), 13, 724, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test SFO 6', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('0ba695e9-551b-41b0-bfbc-88bb3c3f843e', 730, 1, now(), now(), 0, 731, 'test', 0, 0, 'sfo7', 'test.sfo.7@liferay.com', 'Welcome Test SFO 7!', 'Test', '', 'SFO 7', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (731, 1, 2, 'Test SFO 7', now(), now(), 13, 730, 7, 0, 'test.sfo.7@liferay.com', 'Test', '', 'SFO 7', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (732, 1, 730, 13, 730, 0, 0, '732', 0, '/sfo7', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (733, 1, 732, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (734, 1, 732, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (730, 19);

insert into Users_Orgs (userId, organizationId) values (730, 22);
insert into Users_Orgs (userId, organizationId) values (730, 52);

insert into Users_Roles values (730, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (735, 732, 1, 2, 'Joe Blogs', now(), now(), 13, 730, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test SFO 7', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('892a6f7b-fef3-4e66-a526-8e6fe0c8473a', 736, 1, now(), now(), 0, 737, 'test', 0, 0, 'sfo8', 'test.sfo.8@liferay.com', 'Welcome Test SFO 8!', 'Test', '', 'SFO 8', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (737, 1, 2, 'Test SFO 8', now(), now(), 13, 736, 7, 0, 'test.sfo.8@liferay.com', 'Test', '', 'SFO 8', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (738, 1, 736, 13, 736, 0, 0, '738', 0, '/sfo8', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (739, 1, 738, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (740, 1, 738, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (736, 19);

insert into Users_Orgs (userId, organizationId) values (736, 22);
insert into Users_Orgs (userId, organizationId) values (736, 52);

insert into Users_Roles values (736, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (741, 738, 1, 2, 'Joe Blogs', now(), now(), 13, 736, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test SFO 8', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('0af87d4f-cb29-44c0-a10a-4ce3fbb91f33', 742, 1, now(), now(), 0, 743, 'test', 0, 0, 'sfo9', 'test.sfo.9@liferay.com', 'Welcome Test SFO 9!', 'Test', '', 'SFO 9', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (743, 1, 2, 'Test SFO 9', now(), now(), 13, 742, 7, 0, 'test.sfo.9@liferay.com', 'Test', '', 'SFO 9', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (744, 1, 742, 13, 742, 0, 0, '744', 0, '/sfo9', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (745, 1, 744, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (746, 1, 744, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (742, 19);

insert into Users_Orgs (userId, organizationId) values (742, 22);
insert into Users_Orgs (userId, organizationId) values (742, 52);

insert into Users_Roles values (742, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (747, 744, 1, 2, 'Joe Blogs', now(), now(), 13, 742, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test SFO 9', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('5a93bb94-c09c-4a78-b987-24cc3cb7bc18', 748, 1, now(), now(), 0, 749, 'test', 0, 0, 'sfo10', 'test.sfo.10@liferay.com', 'Welcome Test SFO 10!', 'Test', '', 'SFO 10', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (749, 1, 2, 'Test SFO 10', now(), now(), 13, 748, 7, 0, 'test.sfo.10@liferay.com', 'Test', '', 'SFO 10', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (750, 1, 748, 13, 748, 0, 0, '750', 0, '/sfo10', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (751, 1, 750, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (752, 1, 750, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (748, 19);

insert into Users_Orgs (userId, organizationId) values (748, 22);
insert into Users_Orgs (userId, organizationId) values (748, 52);

insert into Users_Roles values (748, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (753, 750, 1, 2, 'Joe Blogs', now(), now(), 13, 748, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test SFO 10', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('8d62a238-55e2-4e92-bfc8-a9466dc170bc', 754, 1, now(), now(), 0, 755, 'test', 0, 0, 'ord1', 'test.ord.1@liferay.com', 'Welcome Test ORD 1!', 'Test', '', 'ORD 1', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (755, 1, 2, 'Test ORD 1', now(), now(), 13, 754, 7, 0, 'test.ord.1@liferay.com', 'Test', '', 'ORD 1', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (756, 1, 754, 13, 754, 0, 0, '756', 0, '/ord1', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (757, 1, 756, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (758, 1, 756, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (754, 19);

insert into Users_Orgs (userId, organizationId) values (754, 22);
insert into Users_Orgs (userId, organizationId) values (754, 56);

insert into Users_Roles values (754, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (759, 756, 1, 2, 'Joe Blogs', now(), now(), 13, 754, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test ORD 1', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('d48257b0-e1d8-480f-839d-5dbbcc96b5fa', 760, 1, now(), now(), 0, 761, 'test', 0, 0, 'ord2', 'test.ord.2@liferay.com', 'Welcome Test ORD 2!', 'Test', '', 'ORD 2', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (761, 1, 2, 'Test ORD 2', now(), now(), 13, 760, 7, 0, 'test.ord.2@liferay.com', 'Test', '', 'ORD 2', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (762, 1, 760, 13, 760, 0, 0, '762', 0, '/ord2', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (763, 1, 762, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (764, 1, 762, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (760, 19);

insert into Users_Orgs (userId, organizationId) values (760, 22);
insert into Users_Orgs (userId, organizationId) values (760, 56);

insert into Users_Roles values (760, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (765, 762, 1, 2, 'Joe Blogs', now(), now(), 13, 760, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test ORD 2', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('951ad437-4f96-4f32-928a-104b19d3c54a', 766, 1, now(), now(), 0, 767, 'test', 0, 0, 'ord3', 'test.ord.3@liferay.com', 'Welcome Test ORD 3!', 'Test', '', 'ORD 3', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (767, 1, 2, 'Test ORD 3', now(), now(), 13, 766, 7, 0, 'test.ord.3@liferay.com', 'Test', '', 'ORD 3', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (768, 1, 766, 13, 766, 0, 0, '768', 0, '/ord3', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (769, 1, 768, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (770, 1, 768, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (766, 19);

insert into Users_Orgs (userId, organizationId) values (766, 22);
insert into Users_Orgs (userId, organizationId) values (766, 56);

insert into Users_Roles values (766, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (771, 768, 1, 2, 'Joe Blogs', now(), now(), 13, 766, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test ORD 3', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('230b6ee9-eaae-4342-8937-b4d4bd05f5db', 772, 1, now(), now(), 0, 773, 'test', 0, 0, 'ord4', 'test.ord.4@liferay.com', 'Welcome Test ORD 4!', 'Test', '', 'ORD 4', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (773, 1, 2, 'Test ORD 4', now(), now(), 13, 772, 7, 0, 'test.ord.4@liferay.com', 'Test', '', 'ORD 4', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (774, 1, 772, 13, 772, 0, 0, '774', 0, '/ord4', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (775, 1, 774, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (776, 1, 774, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (772, 19);

insert into Users_Orgs (userId, organizationId) values (772, 22);
insert into Users_Orgs (userId, organizationId) values (772, 56);

insert into Users_Roles values (772, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (777, 774, 1, 2, 'Joe Blogs', now(), now(), 13, 772, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test ORD 4', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('3ac7741d-71d6-496c-82d4-2a0172abb174', 778, 1, now(), now(), 0, 779, 'test', 0, 0, 'ord5', 'test.ord.5@liferay.com', 'Welcome Test ORD 5!', 'Test', '', 'ORD 5', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (779, 1, 2, 'Test ORD 5', now(), now(), 13, 778, 7, 0, 'test.ord.5@liferay.com', 'Test', '', 'ORD 5', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (780, 1, 778, 13, 778, 0, 0, '780', 0, '/ord5', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (781, 1, 780, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (782, 1, 780, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (778, 19);

insert into Users_Orgs (userId, organizationId) values (778, 22);
insert into Users_Orgs (userId, organizationId) values (778, 56);

insert into Users_Roles values (778, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (783, 780, 1, 2, 'Joe Blogs', now(), now(), 13, 778, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test ORD 5', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('a181b618-d632-4d33-b5b4-c47d413bdc3c', 784, 1, now(), now(), 0, 785, 'test', 0, 0, 'ord6', 'test.ord.6@liferay.com', 'Welcome Test ORD 6!', 'Test', '', 'ORD 6', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (785, 1, 2, 'Test ORD 6', now(), now(), 13, 784, 7, 0, 'test.ord.6@liferay.com', 'Test', '', 'ORD 6', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (786, 1, 784, 13, 784, 0, 0, '786', 0, '/ord6', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (787, 1, 786, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (788, 1, 786, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (784, 19);

insert into Users_Orgs (userId, organizationId) values (784, 22);
insert into Users_Orgs (userId, organizationId) values (784, 56);

insert into Users_Roles values (784, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (789, 786, 1, 2, 'Joe Blogs', now(), now(), 13, 784, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test ORD 6', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('cbb478e3-7a07-4940-9c3a-100ee39de463', 790, 1, now(), now(), 0, 791, 'test', 0, 0, 'ord7', 'test.ord.7@liferay.com', 'Welcome Test ORD 7!', 'Test', '', 'ORD 7', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (791, 1, 2, 'Test ORD 7', now(), now(), 13, 790, 7, 0, 'test.ord.7@liferay.com', 'Test', '', 'ORD 7', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (792, 1, 790, 13, 790, 0, 0, '792', 0, '/ord7', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (793, 1, 792, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (794, 1, 792, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (790, 19);

insert into Users_Orgs (userId, organizationId) values (790, 22);
insert into Users_Orgs (userId, organizationId) values (790, 56);

insert into Users_Roles values (790, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (795, 792, 1, 2, 'Joe Blogs', now(), now(), 13, 790, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test ORD 7', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('00f4df87-e697-4285-9745-e503e5417104', 796, 1, now(), now(), 0, 797, 'test', 0, 0, 'ord8', 'test.ord.8@liferay.com', 'Welcome Test ORD 8!', 'Test', '', 'ORD 8', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (797, 1, 2, 'Test ORD 8', now(), now(), 13, 796, 7, 0, 'test.ord.8@liferay.com', 'Test', '', 'ORD 8', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (798, 1, 796, 13, 796, 0, 0, '798', 0, '/ord8', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (799, 1, 798, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (800, 1, 798, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (796, 19);

insert into Users_Orgs (userId, organizationId) values (796, 22);
insert into Users_Orgs (userId, organizationId) values (796, 56);

insert into Users_Roles values (796, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (801, 798, 1, 2, 'Joe Blogs', now(), now(), 13, 796, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test ORD 8', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('d9fadcbf-98c3-4e72-842e-4416b06ed2d6', 802, 1, now(), now(), 0, 803, 'test', 0, 0, 'ord9', 'test.ord.9@liferay.com', 'Welcome Test ORD 9!', 'Test', '', 'ORD 9', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (803, 1, 2, 'Test ORD 9', now(), now(), 13, 802, 7, 0, 'test.ord.9@liferay.com', 'Test', '', 'ORD 9', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (804, 1, 802, 13, 802, 0, 0, '804', 0, '/ord9', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (805, 1, 804, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (806, 1, 804, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (802, 19);

insert into Users_Orgs (userId, organizationId) values (802, 22);
insert into Users_Orgs (userId, organizationId) values (802, 56);

insert into Users_Roles values (802, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (807, 804, 1, 2, 'Joe Blogs', now(), now(), 13, 802, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test ORD 9', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('1d1d0ce4-4154-4477-86bb-444e8c82e0ed', 808, 1, now(), now(), 0, 809, 'test', 0, 0, 'ord10', 'test.ord.10@liferay.com', 'Welcome Test ORD 10!', 'Test', '', 'ORD 10', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (809, 1, 2, 'Test ORD 10', now(), now(), 13, 808, 7, 0, 'test.ord.10@liferay.com', 'Test', '', 'ORD 10', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (810, 1, 808, 13, 808, 0, 0, '810', 0, '/ord10', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (811, 1, 810, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (812, 1, 810, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (808, 19);

insert into Users_Orgs (userId, organizationId) values (808, 22);
insert into Users_Orgs (userId, organizationId) values (808, 56);

insert into Users_Roles values (808, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (813, 810, 1, 2, 'Joe Blogs', now(), now(), 13, 808, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test ORD 10', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('b7621546-df0b-4a6b-8a9f-cda7bc977f07', 814, 1, now(), now(), 0, 815, 'test', 0, 0, 'nyc1', 'test.nyc.1@liferay.com', 'Welcome Test NYC 1!', 'Test', '', 'NYC 1', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (815, 1, 2, 'Test NYC 1', now(), now(), 13, 814, 7, 0, 'test.nyc.1@liferay.com', 'Test', '', 'NYC 1', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (816, 1, 814, 13, 814, 0, 0, '816', 0, '/nyc1', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (817, 1, 816, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (818, 1, 816, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (814, 19);

insert into Users_Orgs (userId, organizationId) values (814, 22);
insert into Users_Orgs (userId, organizationId) values (814, 60);

insert into Users_Roles values (814, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (819, 816, 1, 2, 'Joe Blogs', now(), now(), 13, 814, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test NYC 1', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('15da7fa2-7056-4817-b6de-28b766761467', 820, 1, now(), now(), 0, 821, 'test', 0, 0, 'nyc2', 'test.nyc.2@liferay.com', 'Welcome Test NYC 2!', 'Test', '', 'NYC 2', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (821, 1, 2, 'Test NYC 2', now(), now(), 13, 820, 7, 0, 'test.nyc.2@liferay.com', 'Test', '', 'NYC 2', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (822, 1, 820, 13, 820, 0, 0, '822', 0, '/nyc2', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (823, 1, 822, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (824, 1, 822, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (820, 19);

insert into Users_Orgs (userId, organizationId) values (820, 22);
insert into Users_Orgs (userId, organizationId) values (820, 60);

insert into Users_Roles values (820, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (825, 822, 1, 2, 'Joe Blogs', now(), now(), 13, 820, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test NYC 2', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('ca21d6b7-609b-471d-9c7e-82b2108c4426', 826, 1, now(), now(), 0, 827, 'test', 0, 0, 'nyc3', 'test.nyc.3@liferay.com', 'Welcome Test NYC 3!', 'Test', '', 'NYC 3', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (827, 1, 2, 'Test NYC 3', now(), now(), 13, 826, 7, 0, 'test.nyc.3@liferay.com', 'Test', '', 'NYC 3', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (828, 1, 826, 13, 826, 0, 0, '828', 0, '/nyc3', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (829, 1, 828, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (830, 1, 828, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (826, 19);

insert into Users_Orgs (userId, organizationId) values (826, 22);
insert into Users_Orgs (userId, organizationId) values (826, 60);

insert into Users_Roles values (826, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (831, 828, 1, 2, 'Joe Blogs', now(), now(), 13, 826, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test NYC 3', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('b0bb9322-6b8a-414f-b604-f761e3f501bf', 832, 1, now(), now(), 0, 833, 'test', 0, 0, 'nyc4', 'test.nyc.4@liferay.com', 'Welcome Test NYC 4!', 'Test', '', 'NYC 4', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (833, 1, 2, 'Test NYC 4', now(), now(), 13, 832, 7, 0, 'test.nyc.4@liferay.com', 'Test', '', 'NYC 4', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (834, 1, 832, 13, 832, 0, 0, '834', 0, '/nyc4', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (835, 1, 834, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (836, 1, 834, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (832, 19);

insert into Users_Orgs (userId, organizationId) values (832, 22);
insert into Users_Orgs (userId, organizationId) values (832, 60);

insert into Users_Roles values (832, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (837, 834, 1, 2, 'Joe Blogs', now(), now(), 13, 832, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test NYC 4', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('dbd19bd7-d8ac-48c1-92ee-b2f3185a7419', 838, 1, now(), now(), 0, 839, 'test', 0, 0, 'nyc5', 'test.nyc.5@liferay.com', 'Welcome Test NYC 5!', 'Test', '', 'NYC 5', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (839, 1, 2, 'Test NYC 5', now(), now(), 13, 838, 7, 0, 'test.nyc.5@liferay.com', 'Test', '', 'NYC 5', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (840, 1, 838, 13, 838, 0, 0, '840', 0, '/nyc5', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (841, 1, 840, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (842, 1, 840, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (838, 19);

insert into Users_Orgs (userId, organizationId) values (838, 22);
insert into Users_Orgs (userId, organizationId) values (838, 60);

insert into Users_Roles values (838, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (843, 840, 1, 2, 'Joe Blogs', now(), now(), 13, 838, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test NYC 5', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('ae1683f9-3543-45c9-a9ac-867a6b8f1862', 844, 1, now(), now(), 0, 845, 'test', 0, 0, 'nyc6', 'test.nyc.6@liferay.com', 'Welcome Test NYC 6!', 'Test', '', 'NYC 6', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (845, 1, 2, 'Test NYC 6', now(), now(), 13, 844, 7, 0, 'test.nyc.6@liferay.com', 'Test', '', 'NYC 6', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (846, 1, 844, 13, 844, 0, 0, '846', 0, '/nyc6', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (847, 1, 846, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (848, 1, 846, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (844, 19);

insert into Users_Orgs (userId, organizationId) values (844, 22);
insert into Users_Orgs (userId, organizationId) values (844, 60);

insert into Users_Roles values (844, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (849, 846, 1, 2, 'Joe Blogs', now(), now(), 13, 844, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test NYC 6', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('7c33cc2d-2826-484b-94a6-39a142c0ea8e', 850, 1, now(), now(), 0, 851, 'test', 0, 0, 'nyc7', 'test.nyc.7@liferay.com', 'Welcome Test NYC 7!', 'Test', '', 'NYC 7', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (851, 1, 2, 'Test NYC 7', now(), now(), 13, 850, 7, 0, 'test.nyc.7@liferay.com', 'Test', '', 'NYC 7', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (852, 1, 850, 13, 850, 0, 0, '852', 0, '/nyc7', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (853, 1, 852, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (854, 1, 852, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (850, 19);

insert into Users_Orgs (userId, organizationId) values (850, 22);
insert into Users_Orgs (userId, organizationId) values (850, 60);

insert into Users_Roles values (850, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (855, 852, 1, 2, 'Joe Blogs', now(), now(), 13, 850, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test NYC 7', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('71c1ef49-65d3-4dcf-901e-a519062543fd', 856, 1, now(), now(), 0, 857, 'test', 0, 0, 'nyc8', 'test.nyc.8@liferay.com', 'Welcome Test NYC 8!', 'Test', '', 'NYC 8', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (857, 1, 2, 'Test NYC 8', now(), now(), 13, 856, 7, 0, 'test.nyc.8@liferay.com', 'Test', '', 'NYC 8', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (858, 1, 856, 13, 856, 0, 0, '858', 0, '/nyc8', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (859, 1, 858, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (860, 1, 858, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (856, 19);

insert into Users_Orgs (userId, organizationId) values (856, 22);
insert into Users_Orgs (userId, organizationId) values (856, 60);

insert into Users_Roles values (856, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (861, 858, 1, 2, 'Joe Blogs', now(), now(), 13, 856, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test NYC 8', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('2e081851-1c7f-49c5-a720-7ea9aecf960f', 862, 1, now(), now(), 0, 863, 'test', 0, 0, 'nyc9', 'test.nyc.9@liferay.com', 'Welcome Test NYC 9!', 'Test', '', 'NYC 9', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (863, 1, 2, 'Test NYC 9', now(), now(), 13, 862, 7, 0, 'test.nyc.9@liferay.com', 'Test', '', 'NYC 9', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (864, 1, 862, 13, 862, 0, 0, '864', 0, '/nyc9', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (865, 1, 864, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (866, 1, 864, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (862, 19);

insert into Users_Orgs (userId, organizationId) values (862, 22);
insert into Users_Orgs (userId, organizationId) values (862, 60);

insert into Users_Roles values (862, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (867, 864, 1, 2, 'Joe Blogs', now(), now(), 13, 862, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test NYC 9', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('04451704-be0c-4d7e-abea-9adcca0987a9', 868, 1, now(), now(), 0, 869, 'test', 0, 0, 'nyc10', 'test.nyc.10@liferay.com', 'Welcome Test NYC 10!', 'Test', '', 'NYC 10', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (869, 1, 2, 'Test NYC 10', now(), now(), 13, 868, 7, 0, 'test.nyc.10@liferay.com', 'Test', '', 'NYC 10', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (870, 1, 868, 13, 868, 0, 0, '870', 0, '/nyc10', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (871, 1, 870, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (872, 1, 870, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (868, 19);

insert into Users_Orgs (userId, organizationId) values (868, 22);
insert into Users_Orgs (userId, organizationId) values (868, 60);

insert into Users_Roles values (868, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (873, 870, 1, 2, 'Joe Blogs', now(), now(), 13, 868, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test NYC 10', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('0c900ca1-1943-487b-9d74-d8a7451a25f2', 874, 1, now(), now(), 0, 875, 'test', 0, 0, 'gru1', 'test.gru.1@liferay.com', 'Welcome Test GRU 1!', 'Test', '', 'GRU 1', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (875, 1, 2, 'Test GRU 1', now(), now(), 13, 874, 7, 0, 'test.gru.1@liferay.com', 'Test', '', 'GRU 1', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (876, 1, 874, 13, 874, 0, 0, '876', 0, '/gru1', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (877, 1, 876, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (878, 1, 876, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (874, 19);

insert into Users_Orgs (userId, organizationId) values (874, 22);
insert into Users_Orgs (userId, organizationId) values (874, 64);

insert into Users_Roles values (874, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (879, 876, 1, 2, 'Joe Blogs', now(), now(), 13, 874, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test GRU 1', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('dce82d49-4c57-4c69-b471-bb017d423e5c', 880, 1, now(), now(), 0, 881, 'test', 0, 0, 'gru2', 'test.gru.2@liferay.com', 'Welcome Test GRU 2!', 'Test', '', 'GRU 2', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (881, 1, 2, 'Test GRU 2', now(), now(), 13, 880, 7, 0, 'test.gru.2@liferay.com', 'Test', '', 'GRU 2', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (882, 1, 880, 13, 880, 0, 0, '882', 0, '/gru2', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (883, 1, 882, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (884, 1, 882, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (880, 19);

insert into Users_Orgs (userId, organizationId) values (880, 22);
insert into Users_Orgs (userId, organizationId) values (880, 64);

insert into Users_Roles values (880, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (885, 882, 1, 2, 'Joe Blogs', now(), now(), 13, 880, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test GRU 2', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('ad979e36-178a-4f38-9a7f-c3303fbb2ccc', 886, 1, now(), now(), 0, 887, 'test', 0, 0, 'gru3', 'test.gru.3@liferay.com', 'Welcome Test GRU 3!', 'Test', '', 'GRU 3', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (887, 1, 2, 'Test GRU 3', now(), now(), 13, 886, 7, 0, 'test.gru.3@liferay.com', 'Test', '', 'GRU 3', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (888, 1, 886, 13, 886, 0, 0, '888', 0, '/gru3', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (889, 1, 888, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (890, 1, 888, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (886, 19);

insert into Users_Orgs (userId, organizationId) values (886, 22);
insert into Users_Orgs (userId, organizationId) values (886, 64);

insert into Users_Roles values (886, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (891, 888, 1, 2, 'Joe Blogs', now(), now(), 13, 886, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test GRU 3', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('22314cf5-cde1-43f8-a507-da1f1c869f4d', 892, 1, now(), now(), 0, 893, 'test', 0, 0, 'gru4', 'test.gru.4@liferay.com', 'Welcome Test GRU 4!', 'Test', '', 'GRU 4', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (893, 1, 2, 'Test GRU 4', now(), now(), 13, 892, 7, 0, 'test.gru.4@liferay.com', 'Test', '', 'GRU 4', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (894, 1, 892, 13, 892, 0, 0, '894', 0, '/gru4', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (895, 1, 894, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (896, 1, 894, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (892, 19);

insert into Users_Orgs (userId, organizationId) values (892, 22);
insert into Users_Orgs (userId, organizationId) values (892, 64);

insert into Users_Roles values (892, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (897, 894, 1, 2, 'Joe Blogs', now(), now(), 13, 892, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test GRU 4', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('73369124-be29-4b67-8fdc-44e045d0e379', 898, 1, now(), now(), 0, 899, 'test', 0, 0, 'gru5', 'test.gru.5@liferay.com', 'Welcome Test GRU 5!', 'Test', '', 'GRU 5', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (899, 1, 2, 'Test GRU 5', now(), now(), 13, 898, 7, 0, 'test.gru.5@liferay.com', 'Test', '', 'GRU 5', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (900, 1, 898, 13, 898, 0, 0, '900', 0, '/gru5', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (901, 1, 900, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (902, 1, 900, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (898, 19);

insert into Users_Orgs (userId, organizationId) values (898, 22);
insert into Users_Orgs (userId, organizationId) values (898, 64);

insert into Users_Roles values (898, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (903, 900, 1, 2, 'Joe Blogs', now(), now(), 13, 898, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test GRU 5', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('842808d5-8805-4c43-9ca8-de70f2c1cde5', 904, 1, now(), now(), 0, 905, 'test', 0, 0, 'gru6', 'test.gru.6@liferay.com', 'Welcome Test GRU 6!', 'Test', '', 'GRU 6', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (905, 1, 2, 'Test GRU 6', now(), now(), 13, 904, 7, 0, 'test.gru.6@liferay.com', 'Test', '', 'GRU 6', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (906, 1, 904, 13, 904, 0, 0, '906', 0, '/gru6', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (907, 1, 906, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (908, 1, 906, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (904, 19);

insert into Users_Orgs (userId, organizationId) values (904, 22);
insert into Users_Orgs (userId, organizationId) values (904, 64);

insert into Users_Roles values (904, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (909, 906, 1, 2, 'Joe Blogs', now(), now(), 13, 904, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test GRU 6', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('0f236c82-8f43-4cc3-b2fa-dcd0287d4dec', 910, 1, now(), now(), 0, 911, 'test', 0, 0, 'gru7', 'test.gru.7@liferay.com', 'Welcome Test GRU 7!', 'Test', '', 'GRU 7', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (911, 1, 2, 'Test GRU 7', now(), now(), 13, 910, 7, 0, 'test.gru.7@liferay.com', 'Test', '', 'GRU 7', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (912, 1, 910, 13, 910, 0, 0, '912', 0, '/gru7', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (913, 1, 912, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (914, 1, 912, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (910, 19);

insert into Users_Orgs (userId, organizationId) values (910, 22);
insert into Users_Orgs (userId, organizationId) values (910, 64);

insert into Users_Roles values (910, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (915, 912, 1, 2, 'Joe Blogs', now(), now(), 13, 910, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test GRU 7', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('5f91d8c0-2048-46f7-b9ab-20c222577049', 916, 1, now(), now(), 0, 917, 'test', 0, 0, 'gru8', 'test.gru.8@liferay.com', 'Welcome Test GRU 8!', 'Test', '', 'GRU 8', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (917, 1, 2, 'Test GRU 8', now(), now(), 13, 916, 7, 0, 'test.gru.8@liferay.com', 'Test', '', 'GRU 8', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (918, 1, 916, 13, 916, 0, 0, '918', 0, '/gru8', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (919, 1, 918, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (920, 1, 918, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (916, 19);

insert into Users_Orgs (userId, organizationId) values (916, 22);
insert into Users_Orgs (userId, organizationId) values (916, 64);

insert into Users_Roles values (916, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (921, 918, 1, 2, 'Joe Blogs', now(), now(), 13, 916, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test GRU 8', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('85c24216-5fee-4604-82a8-d78aca2e6aba', 922, 1, now(), now(), 0, 923, 'test', 0, 0, 'gru9', 'test.gru.9@liferay.com', 'Welcome Test GRU 9!', 'Test', '', 'GRU 9', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (923, 1, 2, 'Test GRU 9', now(), now(), 13, 922, 7, 0, 'test.gru.9@liferay.com', 'Test', '', 'GRU 9', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (924, 1, 922, 13, 922, 0, 0, '924', 0, '/gru9', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (925, 1, 924, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (926, 1, 924, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (922, 19);

insert into Users_Orgs (userId, organizationId) values (922, 22);
insert into Users_Orgs (userId, organizationId) values (922, 64);

insert into Users_Roles values (922, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (927, 924, 1, 2, 'Joe Blogs', now(), now(), 13, 922, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test GRU 9', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('6f2c19e7-6344-49cc-b1f8-404c7707eee9', 928, 1, now(), now(), 0, 929, 'test', 0, 0, 'gru10', 'test.gru.10@liferay.com', 'Welcome Test GRU 10!', 'Test', '', 'GRU 10', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (929, 1, 2, 'Test GRU 10', now(), now(), 13, 928, 7, 0, 'test.gru.10@liferay.com', 'Test', '', 'GRU 10', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (930, 1, 928, 13, 928, 0, 0, '930', 0, '/gru10', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (931, 1, 930, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (932, 1, 930, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (928, 19);

insert into Users_Orgs (userId, organizationId) values (928, 22);
insert into Users_Orgs (userId, organizationId) values (928, 64);

insert into Users_Roles values (928, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (933, 930, 1, 2, 'Joe Blogs', now(), now(), 13, 928, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test GRU 10', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('190bc989-d426-4598-98fe-ff176fa5ac9a', 934, 1, now(), now(), 0, 935, 'test', 0, 0, 'fra1', 'test.fra.1@liferay.com', 'Welcome Test FRA 1!', 'Test', '', 'FRA 1', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (935, 1, 2, 'Test FRA 1', now(), now(), 13, 934, 7, 0, 'test.fra.1@liferay.com', 'Test', '', 'FRA 1', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (936, 1, 934, 13, 934, 0, 0, '936', 0, '/fra1', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (937, 1, 936, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (938, 1, 936, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (934, 19);

insert into Users_Orgs (userId, organizationId) values (934, 22);
insert into Users_Orgs (userId, organizationId) values (934, 68);

insert into Users_Roles values (934, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (939, 936, 1, 2, 'Joe Blogs', now(), now(), 13, 934, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test FRA 1', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('102d559b-c51a-4f90-b9b4-e3c42605a9d8', 940, 1, now(), now(), 0, 941, 'test', 0, 0, 'fra2', 'test.fra.2@liferay.com', 'Welcome Test FRA 2!', 'Test', '', 'FRA 2', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (941, 1, 2, 'Test FRA 2', now(), now(), 13, 940, 7, 0, 'test.fra.2@liferay.com', 'Test', '', 'FRA 2', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (942, 1, 940, 13, 940, 0, 0, '942', 0, '/fra2', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (943, 1, 942, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (944, 1, 942, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (940, 19);

insert into Users_Orgs (userId, organizationId) values (940, 22);
insert into Users_Orgs (userId, organizationId) values (940, 68);

insert into Users_Roles values (940, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (945, 942, 1, 2, 'Joe Blogs', now(), now(), 13, 940, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test FRA 2', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('f666975d-0cc7-4e51-82a7-e8c4e657f99c', 946, 1, now(), now(), 0, 947, 'test', 0, 0, 'fra3', 'test.fra.3@liferay.com', 'Welcome Test FRA 3!', 'Test', '', 'FRA 3', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (947, 1, 2, 'Test FRA 3', now(), now(), 13, 946, 7, 0, 'test.fra.3@liferay.com', 'Test', '', 'FRA 3', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (948, 1, 946, 13, 946, 0, 0, '948', 0, '/fra3', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (949, 1, 948, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (950, 1, 948, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (946, 19);

insert into Users_Orgs (userId, organizationId) values (946, 22);
insert into Users_Orgs (userId, organizationId) values (946, 68);

insert into Users_Roles values (946, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (951, 948, 1, 2, 'Joe Blogs', now(), now(), 13, 946, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test FRA 3', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('3e5e509a-e709-4055-bd0c-201a8d5aa3d1', 952, 1, now(), now(), 0, 953, 'test', 0, 0, 'fra4', 'test.fra.4@liferay.com', 'Welcome Test FRA 4!', 'Test', '', 'FRA 4', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (953, 1, 2, 'Test FRA 4', now(), now(), 13, 952, 7, 0, 'test.fra.4@liferay.com', 'Test', '', 'FRA 4', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (954, 1, 952, 13, 952, 0, 0, '954', 0, '/fra4', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (955, 1, 954, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (956, 1, 954, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (952, 19);

insert into Users_Orgs (userId, organizationId) values (952, 22);
insert into Users_Orgs (userId, organizationId) values (952, 68);

insert into Users_Roles values (952, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (957, 954, 1, 2, 'Joe Blogs', now(), now(), 13, 952, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test FRA 4', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('72d28c29-f7a4-49ce-954b-fbd391ba85f5', 958, 1, now(), now(), 0, 959, 'test', 0, 0, 'fra5', 'test.fra.5@liferay.com', 'Welcome Test FRA 5!', 'Test', '', 'FRA 5', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (959, 1, 2, 'Test FRA 5', now(), now(), 13, 958, 7, 0, 'test.fra.5@liferay.com', 'Test', '', 'FRA 5', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (960, 1, 958, 13, 958, 0, 0, '960', 0, '/fra5', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (961, 1, 960, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (962, 1, 960, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (958, 19);

insert into Users_Orgs (userId, organizationId) values (958, 22);
insert into Users_Orgs (userId, organizationId) values (958, 68);

insert into Users_Roles values (958, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (963, 960, 1, 2, 'Joe Blogs', now(), now(), 13, 958, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test FRA 5', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('be1f043d-b081-4f65-a914-3e11ed432745', 964, 1, now(), now(), 0, 965, 'test', 0, 0, 'fra6', 'test.fra.6@liferay.com', 'Welcome Test FRA 6!', 'Test', '', 'FRA 6', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (965, 1, 2, 'Test FRA 6', now(), now(), 13, 964, 7, 0, 'test.fra.6@liferay.com', 'Test', '', 'FRA 6', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (966, 1, 964, 13, 964, 0, 0, '966', 0, '/fra6', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (967, 1, 966, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (968, 1, 966, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (964, 19);

insert into Users_Orgs (userId, organizationId) values (964, 22);
insert into Users_Orgs (userId, organizationId) values (964, 68);

insert into Users_Roles values (964, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (969, 966, 1, 2, 'Joe Blogs', now(), now(), 13, 964, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test FRA 6', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('c651fd61-d5ca-4651-a289-95093fb6f9f4', 970, 1, now(), now(), 0, 971, 'test', 0, 0, 'fra7', 'test.fra.7@liferay.com', 'Welcome Test FRA 7!', 'Test', '', 'FRA 7', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (971, 1, 2, 'Test FRA 7', now(), now(), 13, 970, 7, 0, 'test.fra.7@liferay.com', 'Test', '', 'FRA 7', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (972, 1, 970, 13, 970, 0, 0, '972', 0, '/fra7', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (973, 1, 972, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (974, 1, 972, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (970, 19);

insert into Users_Orgs (userId, organizationId) values (970, 22);
insert into Users_Orgs (userId, organizationId) values (970, 68);

insert into Users_Roles values (970, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (975, 972, 1, 2, 'Joe Blogs', now(), now(), 13, 970, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test FRA 7', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('332b040d-e255-4261-8574-6fbb51596d9e', 976, 1, now(), now(), 0, 977, 'test', 0, 0, 'fra8', 'test.fra.8@liferay.com', 'Welcome Test FRA 8!', 'Test', '', 'FRA 8', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (977, 1, 2, 'Test FRA 8', now(), now(), 13, 976, 7, 0, 'test.fra.8@liferay.com', 'Test', '', 'FRA 8', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (978, 1, 976, 13, 976, 0, 0, '978', 0, '/fra8', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (979, 1, 978, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (980, 1, 978, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (976, 19);

insert into Users_Orgs (userId, organizationId) values (976, 22);
insert into Users_Orgs (userId, organizationId) values (976, 68);

insert into Users_Roles values (976, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (981, 978, 1, 2, 'Joe Blogs', now(), now(), 13, 976, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test FRA 8', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('69a56af3-c8c7-458a-b164-a6e8404132b5', 982, 1, now(), now(), 0, 983, 'test', 0, 0, 'fra9', 'test.fra.9@liferay.com', 'Welcome Test FRA 9!', 'Test', '', 'FRA 9', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (983, 1, 2, 'Test FRA 9', now(), now(), 13, 982, 7, 0, 'test.fra.9@liferay.com', 'Test', '', 'FRA 9', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (984, 1, 982, 13, 982, 0, 0, '984', 0, '/fra9', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (985, 1, 984, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (986, 1, 984, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (982, 19);

insert into Users_Orgs (userId, organizationId) values (982, 22);
insert into Users_Orgs (userId, organizationId) values (982, 68);

insert into Users_Roles values (982, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (987, 984, 1, 2, 'Joe Blogs', now(), now(), 13, 982, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test FRA 9', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('cfd9bca9-7adf-4ffe-92f7-c5d166a74ab5', 988, 1, now(), now(), 0, 989, 'test', 0, 0, 'fra10', 'test.fra.10@liferay.com', 'Welcome Test FRA 10!', 'Test', '', 'FRA 10', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (989, 1, 2, 'Test FRA 10', now(), now(), 13, 988, 7, 0, 'test.fra.10@liferay.com', 'Test', '', 'FRA 10', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (990, 1, 988, 13, 988, 0, 0, '990', 0, '/fra10', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (991, 1, 990, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (992, 1, 990, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (988, 19);

insert into Users_Orgs (userId, organizationId) values (988, 22);
insert into Users_Orgs (userId, organizationId) values (988, 68);

insert into Users_Roles values (988, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (993, 990, 1, 2, 'Joe Blogs', now(), now(), 13, 988, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test FRA 10', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('8d4dff1d-41c6-4ece-812f-360fafdd5220', 994, 1, now(), now(), 0, 995, 'test', 0, 0, 'mad1', 'test.mad.1@liferay.com', 'Welcome Test MAD 1!', 'Test', '', 'MAD 1', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (995, 1, 2, 'Test MAD 1', now(), now(), 13, 994, 7, 0, 'test.mad.1@liferay.com', 'Test', '', 'MAD 1', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (996, 1, 994, 13, 994, 0, 0, '996', 0, '/mad1', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (997, 1, 996, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (998, 1, 996, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (994, 19);

insert into Users_Orgs (userId, organizationId) values (994, 22);
insert into Users_Orgs (userId, organizationId) values (994, 72);

insert into Users_Roles values (994, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (999, 996, 1, 2, 'Joe Blogs', now(), now(), 13, 994, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test MAD 1', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('81c0623a-b84f-47a7-a149-3050550633b4', 1000, 1, now(), now(), 0, 1001, 'test', 0, 0, 'mad2', 'test.mad.2@liferay.com', 'Welcome Test MAD 2!', 'Test', '', 'MAD 2', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (1001, 1, 2, 'Test MAD 2', now(), now(), 13, 1000, 7, 0, 'test.mad.2@liferay.com', 'Test', '', 'MAD 2', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (1002, 1, 1000, 13, 1000, 0, 0, '1002', 0, '/mad2', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1003, 1, 1002, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1004, 1, 1002, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (1000, 19);

insert into Users_Orgs (userId, organizationId) values (1000, 22);
insert into Users_Orgs (userId, organizationId) values (1000, 72);

insert into Users_Roles values (1000, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (1005, 1002, 1, 2, 'Joe Blogs', now(), now(), 13, 1000, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test MAD 2', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('fbd06cb3-ba77-4243-9aac-9f49a46cbb8d', 1006, 1, now(), now(), 0, 1007, 'test', 0, 0, 'mad3', 'test.mad.3@liferay.com', 'Welcome Test MAD 3!', 'Test', '', 'MAD 3', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (1007, 1, 2, 'Test MAD 3', now(), now(), 13, 1006, 7, 0, 'test.mad.3@liferay.com', 'Test', '', 'MAD 3', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (1008, 1, 1006, 13, 1006, 0, 0, '1008', 0, '/mad3', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1009, 1, 1008, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1010, 1, 1008, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (1006, 19);

insert into Users_Orgs (userId, organizationId) values (1006, 22);
insert into Users_Orgs (userId, organizationId) values (1006, 72);

insert into Users_Roles values (1006, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (1011, 1008, 1, 2, 'Joe Blogs', now(), now(), 13, 1006, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test MAD 3', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('e644cb4f-6fc6-406e-8966-a3539ff5ff58', 1012, 1, now(), now(), 0, 1013, 'test', 0, 0, 'mad4', 'test.mad.4@liferay.com', 'Welcome Test MAD 4!', 'Test', '', 'MAD 4', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (1013, 1, 2, 'Test MAD 4', now(), now(), 13, 1012, 7, 0, 'test.mad.4@liferay.com', 'Test', '', 'MAD 4', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (1014, 1, 1012, 13, 1012, 0, 0, '1014', 0, '/mad4', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1015, 1, 1014, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1016, 1, 1014, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (1012, 19);

insert into Users_Orgs (userId, organizationId) values (1012, 22);
insert into Users_Orgs (userId, organizationId) values (1012, 72);

insert into Users_Roles values (1012, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (1017, 1014, 1, 2, 'Joe Blogs', now(), now(), 13, 1012, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test MAD 4', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('ac06bf7e-9082-4842-b92f-e18fba0413b4', 1018, 1, now(), now(), 0, 1019, 'test', 0, 0, 'mad5', 'test.mad.5@liferay.com', 'Welcome Test MAD 5!', 'Test', '', 'MAD 5', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (1019, 1, 2, 'Test MAD 5', now(), now(), 13, 1018, 7, 0, 'test.mad.5@liferay.com', 'Test', '', 'MAD 5', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (1020, 1, 1018, 13, 1018, 0, 0, '1020', 0, '/mad5', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1021, 1, 1020, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1022, 1, 1020, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (1018, 19);

insert into Users_Orgs (userId, organizationId) values (1018, 22);
insert into Users_Orgs (userId, organizationId) values (1018, 72);

insert into Users_Roles values (1018, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (1023, 1020, 1, 2, 'Joe Blogs', now(), now(), 13, 1018, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test MAD 5', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('5a40252e-655d-404e-9f5d-46c4e621f7ef', 1024, 1, now(), now(), 0, 1025, 'test', 0, 0, 'mad6', 'test.mad.6@liferay.com', 'Welcome Test MAD 6!', 'Test', '', 'MAD 6', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (1025, 1, 2, 'Test MAD 6', now(), now(), 13, 1024, 7, 0, 'test.mad.6@liferay.com', 'Test', '', 'MAD 6', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (1026, 1, 1024, 13, 1024, 0, 0, '1026', 0, '/mad6', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1027, 1, 1026, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1028, 1, 1026, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (1024, 19);

insert into Users_Orgs (userId, organizationId) values (1024, 22);
insert into Users_Orgs (userId, organizationId) values (1024, 72);

insert into Users_Roles values (1024, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (1029, 1026, 1, 2, 'Joe Blogs', now(), now(), 13, 1024, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test MAD 6', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('51472c49-e0d0-4cc4-b6db-485314e63e93', 1030, 1, now(), now(), 0, 1031, 'test', 0, 0, 'mad7', 'test.mad.7@liferay.com', 'Welcome Test MAD 7!', 'Test', '', 'MAD 7', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (1031, 1, 2, 'Test MAD 7', now(), now(), 13, 1030, 7, 0, 'test.mad.7@liferay.com', 'Test', '', 'MAD 7', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (1032, 1, 1030, 13, 1030, 0, 0, '1032', 0, '/mad7', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1033, 1, 1032, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1034, 1, 1032, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (1030, 19);

insert into Users_Orgs (userId, organizationId) values (1030, 22);
insert into Users_Orgs (userId, organizationId) values (1030, 72);

insert into Users_Roles values (1030, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (1035, 1032, 1, 2, 'Joe Blogs', now(), now(), 13, 1030, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test MAD 7', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('80115a79-0033-4c48-b915-01f71c881dd6', 1036, 1, now(), now(), 0, 1037, 'test', 0, 0, 'mad8', 'test.mad.8@liferay.com', 'Welcome Test MAD 8!', 'Test', '', 'MAD 8', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (1037, 1, 2, 'Test MAD 8', now(), now(), 13, 1036, 7, 0, 'test.mad.8@liferay.com', 'Test', '', 'MAD 8', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (1038, 1, 1036, 13, 1036, 0, 0, '1038', 0, '/mad8', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1039, 1, 1038, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1040, 1, 1038, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (1036, 19);

insert into Users_Orgs (userId, organizationId) values (1036, 22);
insert into Users_Orgs (userId, organizationId) values (1036, 72);

insert into Users_Roles values (1036, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (1041, 1038, 1, 2, 'Joe Blogs', now(), now(), 13, 1036, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test MAD 8', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('ebf7441f-acc5-4d98-903a-ae650ff3ed29', 1042, 1, now(), now(), 0, 1043, 'test', 0, 0, 'mad9', 'test.mad.9@liferay.com', 'Welcome Test MAD 9!', 'Test', '', 'MAD 9', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (1043, 1, 2, 'Test MAD 9', now(), now(), 13, 1042, 7, 0, 'test.mad.9@liferay.com', 'Test', '', 'MAD 9', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (1044, 1, 1042, 13, 1042, 0, 0, '1044', 0, '/mad9', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1045, 1, 1044, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1046, 1, 1044, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (1042, 19);

insert into Users_Orgs (userId, organizationId) values (1042, 22);
insert into Users_Orgs (userId, organizationId) values (1042, 72);

insert into Users_Roles values (1042, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (1047, 1044, 1, 2, 'Joe Blogs', now(), now(), 13, 1042, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test MAD 9', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('ecd47508-87c5-4bb2-9b57-9e0e85797cd0', 1048, 1, now(), now(), 0, 1049, 'test', 0, 0, 'mad10', 'test.mad.10@liferay.com', 'Welcome Test MAD 10!', 'Test', '', 'MAD 10', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (1049, 1, 2, 'Test MAD 10', now(), now(), 13, 1048, 7, 0, 'test.mad.10@liferay.com', 'Test', '', 'MAD 10', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (1050, 1, 1048, 13, 1048, 0, 0, '1050', 0, '/mad10', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1051, 1, 1050, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1052, 1, 1050, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (1048, 19);

insert into Users_Orgs (userId, organizationId) values (1048, 22);
insert into Users_Orgs (userId, organizationId) values (1048, 72);

insert into Users_Roles values (1048, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (1053, 1050, 1, 2, 'Joe Blogs', now(), now(), 13, 1048, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test MAD 10', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('0d68e554-c202-4584-ada2-3e50545b96b6', 1054, 1, now(), now(), 0, 1055, 'test', 0, 0, 'dlc1', 'test.dlc.1@liferay.com', 'Welcome Test DLC 1!', 'Test', '', 'DLC 1', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (1055, 1, 2, 'Test DLC 1', now(), now(), 13, 1054, 7, 0, 'test.dlc.1@liferay.com', 'Test', '', 'DLC 1', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (1056, 1, 1054, 13, 1054, 0, 0, '1056', 0, '/dlc1', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1057, 1, 1056, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1058, 1, 1056, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (1054, 19);

insert into Users_Orgs (userId, organizationId) values (1054, 22);
insert into Users_Orgs (userId, organizationId) values (1054, 76);

insert into Users_Roles values (1054, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (1059, 1056, 1, 2, 'Joe Blogs', now(), now(), 13, 1054, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test DLC 1', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('151c56c2-0e39-4435-9bd0-3b0c498e580c', 1060, 1, now(), now(), 0, 1061, 'test', 0, 0, 'dlc2', 'test.dlc.2@liferay.com', 'Welcome Test DLC 2!', 'Test', '', 'DLC 2', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (1061, 1, 2, 'Test DLC 2', now(), now(), 13, 1060, 7, 0, 'test.dlc.2@liferay.com', 'Test', '', 'DLC 2', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (1062, 1, 1060, 13, 1060, 0, 0, '1062', 0, '/dlc2', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1063, 1, 1062, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1064, 1, 1062, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (1060, 19);

insert into Users_Orgs (userId, organizationId) values (1060, 22);
insert into Users_Orgs (userId, organizationId) values (1060, 76);

insert into Users_Roles values (1060, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (1065, 1062, 1, 2, 'Joe Blogs', now(), now(), 13, 1060, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test DLC 2', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('fa4a2470-5a1a-4e2b-976e-00b2707e3a6d', 1066, 1, now(), now(), 0, 1067, 'test', 0, 0, 'dlc3', 'test.dlc.3@liferay.com', 'Welcome Test DLC 3!', 'Test', '', 'DLC 3', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (1067, 1, 2, 'Test DLC 3', now(), now(), 13, 1066, 7, 0, 'test.dlc.3@liferay.com', 'Test', '', 'DLC 3', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (1068, 1, 1066, 13, 1066, 0, 0, '1068', 0, '/dlc3', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1069, 1, 1068, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1070, 1, 1068, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (1066, 19);

insert into Users_Orgs (userId, organizationId) values (1066, 22);
insert into Users_Orgs (userId, organizationId) values (1066, 76);

insert into Users_Roles values (1066, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (1071, 1068, 1, 2, 'Joe Blogs', now(), now(), 13, 1066, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test DLC 3', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('352abf0c-6753-4f4e-ad2e-a429d54fb801', 1072, 1, now(), now(), 0, 1073, 'test', 0, 0, 'dlc4', 'test.dlc.4@liferay.com', 'Welcome Test DLC 4!', 'Test', '', 'DLC 4', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (1073, 1, 2, 'Test DLC 4', now(), now(), 13, 1072, 7, 0, 'test.dlc.4@liferay.com', 'Test', '', 'DLC 4', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (1074, 1, 1072, 13, 1072, 0, 0, '1074', 0, '/dlc4', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1075, 1, 1074, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1076, 1, 1074, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (1072, 19);

insert into Users_Orgs (userId, organizationId) values (1072, 22);
insert into Users_Orgs (userId, organizationId) values (1072, 76);

insert into Users_Roles values (1072, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (1077, 1074, 1, 2, 'Joe Blogs', now(), now(), 13, 1072, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test DLC 4', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('9d8ad28d-a5bb-4254-b99f-7059e111fd4e', 1078, 1, now(), now(), 0, 1079, 'test', 0, 0, 'dlc5', 'test.dlc.5@liferay.com', 'Welcome Test DLC 5!', 'Test', '', 'DLC 5', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (1079, 1, 2, 'Test DLC 5', now(), now(), 13, 1078, 7, 0, 'test.dlc.5@liferay.com', 'Test', '', 'DLC 5', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (1080, 1, 1078, 13, 1078, 0, 0, '1080', 0, '/dlc5', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1081, 1, 1080, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1082, 1, 1080, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (1078, 19);

insert into Users_Orgs (userId, organizationId) values (1078, 22);
insert into Users_Orgs (userId, organizationId) values (1078, 76);

insert into Users_Roles values (1078, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (1083, 1080, 1, 2, 'Joe Blogs', now(), now(), 13, 1078, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test DLC 5', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('efe7d3f6-aca6-431a-b615-66ab58f6898f', 1084, 1, now(), now(), 0, 1085, 'test', 0, 0, 'dlc6', 'test.dlc.6@liferay.com', 'Welcome Test DLC 6!', 'Test', '', 'DLC 6', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (1085, 1, 2, 'Test DLC 6', now(), now(), 13, 1084, 7, 0, 'test.dlc.6@liferay.com', 'Test', '', 'DLC 6', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (1086, 1, 1084, 13, 1084, 0, 0, '1086', 0, '/dlc6', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1087, 1, 1086, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1088, 1, 1086, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (1084, 19);

insert into Users_Orgs (userId, organizationId) values (1084, 22);
insert into Users_Orgs (userId, organizationId) values (1084, 76);

insert into Users_Roles values (1084, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (1089, 1086, 1, 2, 'Joe Blogs', now(), now(), 13, 1084, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test DLC 6', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('9eeef2ca-0bbd-414e-9fec-dae3fc0a58bc', 1090, 1, now(), now(), 0, 1091, 'test', 0, 0, 'dlc7', 'test.dlc.7@liferay.com', 'Welcome Test DLC 7!', 'Test', '', 'DLC 7', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (1091, 1, 2, 'Test DLC 7', now(), now(), 13, 1090, 7, 0, 'test.dlc.7@liferay.com', 'Test', '', 'DLC 7', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (1092, 1, 1090, 13, 1090, 0, 0, '1092', 0, '/dlc7', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1093, 1, 1092, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1094, 1, 1092, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (1090, 19);

insert into Users_Orgs (userId, organizationId) values (1090, 22);
insert into Users_Orgs (userId, organizationId) values (1090, 76);

insert into Users_Roles values (1090, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (1095, 1092, 1, 2, 'Joe Blogs', now(), now(), 13, 1090, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test DLC 7', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('a734cf6d-3abd-4bd7-8b5d-d6c5ac2624d4', 1096, 1, now(), now(), 0, 1097, 'test', 0, 0, 'dlc8', 'test.dlc.8@liferay.com', 'Welcome Test DLC 8!', 'Test', '', 'DLC 8', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (1097, 1, 2, 'Test DLC 8', now(), now(), 13, 1096, 7, 0, 'test.dlc.8@liferay.com', 'Test', '', 'DLC 8', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (1098, 1, 1096, 13, 1096, 0, 0, '1098', 0, '/dlc8', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1099, 1, 1098, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1100, 1, 1098, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (1096, 19);

insert into Users_Orgs (userId, organizationId) values (1096, 22);
insert into Users_Orgs (userId, organizationId) values (1096, 76);

insert into Users_Roles values (1096, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (1101, 1098, 1, 2, 'Joe Blogs', now(), now(), 13, 1096, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test DLC 8', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('946cca26-281b-452d-a7ef-38b8d864da13', 1102, 1, now(), now(), 0, 1103, 'test', 0, 0, 'dlc9', 'test.dlc.9@liferay.com', 'Welcome Test DLC 9!', 'Test', '', 'DLC 9', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (1103, 1, 2, 'Test DLC 9', now(), now(), 13, 1102, 7, 0, 'test.dlc.9@liferay.com', 'Test', '', 'DLC 9', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (1104, 1, 1102, 13, 1102, 0, 0, '1104', 0, '/dlc9', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1105, 1, 1104, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1106, 1, 1104, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (1102, 19);

insert into Users_Orgs (userId, organizationId) values (1102, 22);
insert into Users_Orgs (userId, organizationId) values (1102, 76);

insert into Users_Roles values (1102, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (1107, 1104, 1, 2, 'Joe Blogs', now(), now(), 13, 1102, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test DLC 9', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('5031901a-0435-4392-bab1-80c154c471ec', 1108, 1, now(), now(), 0, 1109, 'test', 0, 0, 'dlc10', 'test.dlc.10@liferay.com', 'Welcome Test DLC 10!', 'Test', '', 'DLC 10', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (1109, 1, 2, 'Test DLC 10', now(), now(), 13, 1108, 7, 0, 'test.dlc.10@liferay.com', 'Test', '', 'DLC 10', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (1110, 1, 1108, 13, 1108, 0, 0, '1110', 0, '/dlc10', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1111, 1, 1110, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1112, 1, 1110, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (1108, 19);

insert into Users_Orgs (userId, organizationId) values (1108, 22);
insert into Users_Orgs (userId, organizationId) values (1108, 76);

insert into Users_Roles values (1108, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (1113, 1110, 1, 2, 'Joe Blogs', now(), now(), 13, 1108, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test DLC 10', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('380573c2-f7d7-4d0b-8366-14da585246db', 1114, 1, now(), now(), 0, 1115, 'test', 0, 0, 'hkg1', 'test.hkg.1@liferay.com', 'Welcome Test HKG 1!', 'Test', '', 'HKG 1', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (1115, 1, 2, 'Test HKG 1', now(), now(), 13, 1114, 7, 0, 'test.hkg.1@liferay.com', 'Test', '', 'HKG 1', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (1116, 1, 1114, 13, 1114, 0, 0, '1116', 0, '/hkg1', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1117, 1, 1116, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1118, 1, 1116, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (1114, 19);

insert into Users_Orgs (userId, organizationId) values (1114, 22);
insert into Users_Orgs (userId, organizationId) values (1114, 80);

insert into Users_Roles values (1114, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (1119, 1116, 1, 2, 'Joe Blogs', now(), now(), 13, 1114, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test HKG 1', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('04612850-0382-4f18-99c0-ff520ae86b9f', 1120, 1, now(), now(), 0, 1121, 'test', 0, 0, 'hkg2', 'test.hkg.2@liferay.com', 'Welcome Test HKG 2!', 'Test', '', 'HKG 2', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (1121, 1, 2, 'Test HKG 2', now(), now(), 13, 1120, 7, 0, 'test.hkg.2@liferay.com', 'Test', '', 'HKG 2', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (1122, 1, 1120, 13, 1120, 0, 0, '1122', 0, '/hkg2', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1123, 1, 1122, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1124, 1, 1122, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (1120, 19);

insert into Users_Orgs (userId, organizationId) values (1120, 22);
insert into Users_Orgs (userId, organizationId) values (1120, 80);

insert into Users_Roles values (1120, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (1125, 1122, 1, 2, 'Joe Blogs', now(), now(), 13, 1120, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test HKG 2', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('03094b15-51ef-47e8-9ef7-b65125af3bbd', 1126, 1, now(), now(), 0, 1127, 'test', 0, 0, 'hkg3', 'test.hkg.3@liferay.com', 'Welcome Test HKG 3!', 'Test', '', 'HKG 3', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (1127, 1, 2, 'Test HKG 3', now(), now(), 13, 1126, 7, 0, 'test.hkg.3@liferay.com', 'Test', '', 'HKG 3', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (1128, 1, 1126, 13, 1126, 0, 0, '1128', 0, '/hkg3', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1129, 1, 1128, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1130, 1, 1128, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (1126, 19);

insert into Users_Orgs (userId, organizationId) values (1126, 22);
insert into Users_Orgs (userId, organizationId) values (1126, 80);

insert into Users_Roles values (1126, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (1131, 1128, 1, 2, 'Joe Blogs', now(), now(), 13, 1126, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test HKG 3', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('2e3b1d58-695e-4aa4-b743-c7647bfb3eed', 1132, 1, now(), now(), 0, 1133, 'test', 0, 0, 'hkg4', 'test.hkg.4@liferay.com', 'Welcome Test HKG 4!', 'Test', '', 'HKG 4', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (1133, 1, 2, 'Test HKG 4', now(), now(), 13, 1132, 7, 0, 'test.hkg.4@liferay.com', 'Test', '', 'HKG 4', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (1134, 1, 1132, 13, 1132, 0, 0, '1134', 0, '/hkg4', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1135, 1, 1134, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1136, 1, 1134, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (1132, 19);

insert into Users_Orgs (userId, organizationId) values (1132, 22);
insert into Users_Orgs (userId, organizationId) values (1132, 80);

insert into Users_Roles values (1132, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (1137, 1134, 1, 2, 'Joe Blogs', now(), now(), 13, 1132, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test HKG 4', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('b4954880-30c5-4793-85b8-ab7fb32d4537', 1138, 1, now(), now(), 0, 1139, 'test', 0, 0, 'hkg5', 'test.hkg.5@liferay.com', 'Welcome Test HKG 5!', 'Test', '', 'HKG 5', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (1139, 1, 2, 'Test HKG 5', now(), now(), 13, 1138, 7, 0, 'test.hkg.5@liferay.com', 'Test', '', 'HKG 5', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (1140, 1, 1138, 13, 1138, 0, 0, '1140', 0, '/hkg5', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1141, 1, 1140, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1142, 1, 1140, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (1138, 19);

insert into Users_Orgs (userId, organizationId) values (1138, 22);
insert into Users_Orgs (userId, organizationId) values (1138, 80);

insert into Users_Roles values (1138, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (1143, 1140, 1, 2, 'Joe Blogs', now(), now(), 13, 1138, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test HKG 5', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('1b3aa456-00f8-40e8-85a6-73218c680ebe', 1144, 1, now(), now(), 0, 1145, 'test', 0, 0, 'hkg6', 'test.hkg.6@liferay.com', 'Welcome Test HKG 6!', 'Test', '', 'HKG 6', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (1145, 1, 2, 'Test HKG 6', now(), now(), 13, 1144, 7, 0, 'test.hkg.6@liferay.com', 'Test', '', 'HKG 6', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (1146, 1, 1144, 13, 1144, 0, 0, '1146', 0, '/hkg6', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1147, 1, 1146, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1148, 1, 1146, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (1144, 19);

insert into Users_Orgs (userId, organizationId) values (1144, 22);
insert into Users_Orgs (userId, organizationId) values (1144, 80);

insert into Users_Roles values (1144, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (1149, 1146, 1, 2, 'Joe Blogs', now(), now(), 13, 1144, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test HKG 6', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('38d6575b-a65d-4f4d-98e4-17763f2c7fc7', 1150, 1, now(), now(), 0, 1151, 'test', 0, 0, 'hkg7', 'test.hkg.7@liferay.com', 'Welcome Test HKG 7!', 'Test', '', 'HKG 7', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (1151, 1, 2, 'Test HKG 7', now(), now(), 13, 1150, 7, 0, 'test.hkg.7@liferay.com', 'Test', '', 'HKG 7', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (1152, 1, 1150, 13, 1150, 0, 0, '1152', 0, '/hkg7', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1153, 1, 1152, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1154, 1, 1152, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (1150, 19);

insert into Users_Orgs (userId, organizationId) values (1150, 22);
insert into Users_Orgs (userId, organizationId) values (1150, 80);

insert into Users_Roles values (1150, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (1155, 1152, 1, 2, 'Joe Blogs', now(), now(), 13, 1150, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test HKG 7', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('618ad19f-77fb-48bb-b9ef-1919ac5d9b49', 1156, 1, now(), now(), 0, 1157, 'test', 0, 0, 'hkg8', 'test.hkg.8@liferay.com', 'Welcome Test HKG 8!', 'Test', '', 'HKG 8', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (1157, 1, 2, 'Test HKG 8', now(), now(), 13, 1156, 7, 0, 'test.hkg.8@liferay.com', 'Test', '', 'HKG 8', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (1158, 1, 1156, 13, 1156, 0, 0, '1158', 0, '/hkg8', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1159, 1, 1158, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1160, 1, 1158, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (1156, 19);

insert into Users_Orgs (userId, organizationId) values (1156, 22);
insert into Users_Orgs (userId, organizationId) values (1156, 80);

insert into Users_Roles values (1156, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (1161, 1158, 1, 2, 'Joe Blogs', now(), now(), 13, 1156, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test HKG 8', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('1a5fdaa9-eb59-43cc-83a2-5079314a16a8', 1162, 1, now(), now(), 0, 1163, 'test', 0, 0, 'hkg9', 'test.hkg.9@liferay.com', 'Welcome Test HKG 9!', 'Test', '', 'HKG 9', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (1163, 1, 2, 'Test HKG 9', now(), now(), 13, 1162, 7, 0, 'test.hkg.9@liferay.com', 'Test', '', 'HKG 9', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (1164, 1, 1162, 13, 1162, 0, 0, '1164', 0, '/hkg9', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1165, 1, 1164, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1166, 1, 1164, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (1162, 19);

insert into Users_Orgs (userId, organizationId) values (1162, 22);
insert into Users_Orgs (userId, organizationId) values (1162, 80);

insert into Users_Roles values (1162, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (1167, 1164, 1, 2, 'Joe Blogs', now(), now(), 13, 1162, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test HKG 9', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('b9611284-c68a-4fa2-a507-15ad80f133a6', 1168, 1, now(), now(), 0, 1169, 'test', 0, 0, 'hkg10', 'test.hkg.10@liferay.com', 'Welcome Test HKG 10!', 'Test', '', 'HKG 10', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (1169, 1, 2, 'Test HKG 10', now(), now(), 13, 1168, 7, 0, 'test.hkg.10@liferay.com', 'Test', '', 'HKG 10', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (1170, 1, 1168, 13, 1168, 0, 0, '1170', 0, '/hkg10', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1171, 1, 1170, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1172, 1, 1170, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (1168, 19);

insert into Users_Orgs (userId, organizationId) values (1168, 22);
insert into Users_Orgs (userId, organizationId) values (1168, 80);

insert into Users_Roles values (1168, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (1173, 1170, 1, 2, 'Joe Blogs', now(), now(), 13, 1168, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test HKG 10', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('655dba65-7eda-4941-a6f8-a92d7555f20d', 1174, 1, now(), now(), 0, 1175, 'test', 0, 0, 'kul1', 'test.kul.1@liferay.com', 'Welcome Test KUL 1!', 'Test', '', 'KUL 1', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (1175, 1, 2, 'Test KUL 1', now(), now(), 13, 1174, 7, 0, 'test.kul.1@liferay.com', 'Test', '', 'KUL 1', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (1176, 1, 1174, 13, 1174, 0, 0, '1176', 0, '/kul1', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1177, 1, 1176, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1178, 1, 1176, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (1174, 19);

insert into Users_Orgs (userId, organizationId) values (1174, 22);
insert into Users_Orgs (userId, organizationId) values (1174, 84);

insert into Users_Roles values (1174, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (1179, 1176, 1, 2, 'Joe Blogs', now(), now(), 13, 1174, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test KUL 1', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('3e6b1540-878d-4822-9410-73122319234a', 1180, 1, now(), now(), 0, 1181, 'test', 0, 0, 'kul2', 'test.kul.2@liferay.com', 'Welcome Test KUL 2!', 'Test', '', 'KUL 2', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (1181, 1, 2, 'Test KUL 2', now(), now(), 13, 1180, 7, 0, 'test.kul.2@liferay.com', 'Test', '', 'KUL 2', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (1182, 1, 1180, 13, 1180, 0, 0, '1182', 0, '/kul2', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1183, 1, 1182, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1184, 1, 1182, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (1180, 19);

insert into Users_Orgs (userId, organizationId) values (1180, 22);
insert into Users_Orgs (userId, organizationId) values (1180, 84);

insert into Users_Roles values (1180, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (1185, 1182, 1, 2, 'Joe Blogs', now(), now(), 13, 1180, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test KUL 2', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('3ea13eca-50f8-48b8-859c-52642318e7c0', 1186, 1, now(), now(), 0, 1187, 'test', 0, 0, 'kul3', 'test.kul.3@liferay.com', 'Welcome Test KUL 3!', 'Test', '', 'KUL 3', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (1187, 1, 2, 'Test KUL 3', now(), now(), 13, 1186, 7, 0, 'test.kul.3@liferay.com', 'Test', '', 'KUL 3', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (1188, 1, 1186, 13, 1186, 0, 0, '1188', 0, '/kul3', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1189, 1, 1188, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1190, 1, 1188, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (1186, 19);

insert into Users_Orgs (userId, organizationId) values (1186, 22);
insert into Users_Orgs (userId, organizationId) values (1186, 84);

insert into Users_Roles values (1186, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (1191, 1188, 1, 2, 'Joe Blogs', now(), now(), 13, 1186, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test KUL 3', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('dfc0dd77-081e-49e5-a8b3-63b474696ca1', 1192, 1, now(), now(), 0, 1193, 'test', 0, 0, 'kul4', 'test.kul.4@liferay.com', 'Welcome Test KUL 4!', 'Test', '', 'KUL 4', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (1193, 1, 2, 'Test KUL 4', now(), now(), 13, 1192, 7, 0, 'test.kul.4@liferay.com', 'Test', '', 'KUL 4', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (1194, 1, 1192, 13, 1192, 0, 0, '1194', 0, '/kul4', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1195, 1, 1194, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1196, 1, 1194, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (1192, 19);

insert into Users_Orgs (userId, organizationId) values (1192, 22);
insert into Users_Orgs (userId, organizationId) values (1192, 84);

insert into Users_Roles values (1192, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (1197, 1194, 1, 2, 'Joe Blogs', now(), now(), 13, 1192, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test KUL 4', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('0e16ba3c-08a3-4f06-b014-3728df6ac306', 1198, 1, now(), now(), 0, 1199, 'test', 0, 0, 'kul5', 'test.kul.5@liferay.com', 'Welcome Test KUL 5!', 'Test', '', 'KUL 5', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (1199, 1, 2, 'Test KUL 5', now(), now(), 13, 1198, 7, 0, 'test.kul.5@liferay.com', 'Test', '', 'KUL 5', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (1200, 1, 1198, 13, 1198, 0, 0, '1200', 0, '/kul5', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1201, 1, 1200, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1202, 1, 1200, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (1198, 19);

insert into Users_Orgs (userId, organizationId) values (1198, 22);
insert into Users_Orgs (userId, organizationId) values (1198, 84);

insert into Users_Roles values (1198, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (1203, 1200, 1, 2, 'Joe Blogs', now(), now(), 13, 1198, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test KUL 5', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('7297c2eb-9024-4c75-8ac8-33d1c8f916c3', 1204, 1, now(), now(), 0, 1205, 'test', 0, 0, 'kul6', 'test.kul.6@liferay.com', 'Welcome Test KUL 6!', 'Test', '', 'KUL 6', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (1205, 1, 2, 'Test KUL 6', now(), now(), 13, 1204, 7, 0, 'test.kul.6@liferay.com', 'Test', '', 'KUL 6', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (1206, 1, 1204, 13, 1204, 0, 0, '1206', 0, '/kul6', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1207, 1, 1206, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1208, 1, 1206, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (1204, 19);

insert into Users_Orgs (userId, organizationId) values (1204, 22);
insert into Users_Orgs (userId, organizationId) values (1204, 84);

insert into Users_Roles values (1204, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (1209, 1206, 1, 2, 'Joe Blogs', now(), now(), 13, 1204, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test KUL 6', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('cca0abdc-d570-4ffe-be21-16570492023a', 1210, 1, now(), now(), 0, 1211, 'test', 0, 0, 'kul7', 'test.kul.7@liferay.com', 'Welcome Test KUL 7!', 'Test', '', 'KUL 7', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (1211, 1, 2, 'Test KUL 7', now(), now(), 13, 1210, 7, 0, 'test.kul.7@liferay.com', 'Test', '', 'KUL 7', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (1212, 1, 1210, 13, 1210, 0, 0, '1212', 0, '/kul7', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1213, 1, 1212, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1214, 1, 1212, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (1210, 19);

insert into Users_Orgs (userId, organizationId) values (1210, 22);
insert into Users_Orgs (userId, organizationId) values (1210, 84);

insert into Users_Roles values (1210, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (1215, 1212, 1, 2, 'Joe Blogs', now(), now(), 13, 1210, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test KUL 7', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('8c5ff16c-a7a5-4a4d-bca6-d91ed0561256', 1216, 1, now(), now(), 0, 1217, 'test', 0, 0, 'kul8', 'test.kul.8@liferay.com', 'Welcome Test KUL 8!', 'Test', '', 'KUL 8', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (1217, 1, 2, 'Test KUL 8', now(), now(), 13, 1216, 7, 0, 'test.kul.8@liferay.com', 'Test', '', 'KUL 8', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (1218, 1, 1216, 13, 1216, 0, 0, '1218', 0, '/kul8', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1219, 1, 1218, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1220, 1, 1218, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (1216, 19);

insert into Users_Orgs (userId, organizationId) values (1216, 22);
insert into Users_Orgs (userId, organizationId) values (1216, 84);

insert into Users_Roles values (1216, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (1221, 1218, 1, 2, 'Joe Blogs', now(), now(), 13, 1216, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test KUL 8', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('fe729b42-cb29-42d2-8eb0-3494a97de696', 1222, 1, now(), now(), 0, 1223, 'test', 0, 0, 'kul9', 'test.kul.9@liferay.com', 'Welcome Test KUL 9!', 'Test', '', 'KUL 9', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (1223, 1, 2, 'Test KUL 9', now(), now(), 13, 1222, 7, 0, 'test.kul.9@liferay.com', 'Test', '', 'KUL 9', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (1224, 1, 1222, 13, 1222, 0, 0, '1224', 0, '/kul9', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1225, 1, 1224, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1226, 1, 1224, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (1222, 19);

insert into Users_Orgs (userId, organizationId) values (1222, 22);
insert into Users_Orgs (userId, organizationId) values (1222, 84);

insert into Users_Roles values (1222, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (1227, 1224, 1, 2, 'Joe Blogs', now(), now(), 13, 1222, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test KUL 9', '', '', '', 0, 0, 0, 0, 0);

insert into User_ (uuid_, userId, companyId, createDate, modifiedDate, defaultUser, contactId, password_, passwordEncrypted, passwordReset, screenName, emailAddress, greeting, firstName, middleName, lastName, loginDate, failedLoginAttempts, agreedToTermsOfUse, status) values ('5f8c0fb5-d4ce-46d8-a768-88f939e3dad6', 1228, 1, now(), now(), 0, 1229, 'test', 0, 0, 'kul10', 'test.kul.10@liferay.com', 'Welcome Test KUL 10!', 'Test', '', 'KUL 10', now(), 0, 1, 0);
insert into Contact_ (contactId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, accountId, parentContactId, emailAddress, firstName, middleName, lastName, male, birthday) values (1229, 1, 2, 'Test KUL 10', now(), now(), 13, 1228, 7, 0, 'test.kul.10@liferay.com', 'Test', '', 'KUL 10', 1, '1970-01-01');

insert into Group_ (groupId, companyId, creatorUserId, classNameId, classPK, parentGroupId, liveGroupId, name, type_, friendlyURL, active_) values (1230, 1, 1228, 13, 1228, 0, 0, '1230', 0, '/kul10', 1);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1231, 1, 1230, 1, 0, 'classic', '01', 0);
insert into LayoutSet (layoutSetId, companyId, groupId, privateLayout, logo, themeId, colorSchemeId, pageCount) values (1232, 1, 1230, 0, 0, 'classic', '01', 0);

insert into Users_Groups values (1228, 19);

insert into Users_Orgs (userId, organizationId) values (1228, 22);
insert into Users_Orgs (userId, organizationId) values (1228, 84);

insert into Users_Roles values (1228, 18);

insert into AssetEntry (entryId, groupId, companyId, userId, userName, createDate, modifiedDate, classNameId, classPK, classUuid, classTypeId, visible, startDate, endDate, publishDate, expirationDate, mimeType, title, description, summary, url, layoutUuid, height, width, priority, viewCount) VALUES (1233, 1230, 1, 2, 'Joe Blogs', now(), now(), 13, 1228, '', 0, 0, NULL, NULL, NULL, NULL, '', 'Test KUL 10', '', '', '', 0, 0, 0, 0, 0);












insert into Release_ (releaseId, createDate, modifiedDate, servletContextName, buildNumber, verified) values (1, now(), now(), 'portal', 6200, 0);


create table QUARTZ_BLOB_TRIGGERS (
	SCHED_NAME varchar(120) not null,
	TRIGGER_NAME varchar(200) not null,
	TRIGGER_GROUP varchar(200) not null,
	BLOB_DATA longblob null,
	primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
) engine InnoDB;

create table QUARTZ_CALENDARS (
	SCHED_NAME varchar(120) not null,
	CALENDAR_NAME varchar(200) not null,
	CALENDAR longblob not null,
	primary key (SCHED_NAME,CALENDAR_NAME)
) engine InnoDB;

create table QUARTZ_CRON_TRIGGERS (
	SCHED_NAME varchar(120) not null,
	TRIGGER_NAME varchar(200) not null,
	TRIGGER_GROUP varchar(200) not null,
	CRON_EXPRESSION varchar(200) not null,
	TIME_ZONE_ID varchar(80),
	primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
) engine InnoDB;

create table QUARTZ_FIRED_TRIGGERS (
	SCHED_NAME varchar(120) not null,
	ENTRY_ID varchar(95) not null,
	TRIGGER_NAME varchar(200) not null,
	TRIGGER_GROUP varchar(200) not null,
	INSTANCE_NAME varchar(200) not null,
	FIRED_TIME bigint not null,
	PRIORITY integer not null,
	STATE varchar(16) not null,
	JOB_NAME varchar(200) null,
	JOB_GROUP varchar(200) null,
	IS_NONCONCURRENT tinyint NULL,
	REQUESTS_RECOVERY tinyint NULL,
	primary key (SCHED_NAME, ENTRY_ID)
) engine InnoDB;

create table QUARTZ_JOB_DETAILS (
	SCHED_NAME varchar(120) not null,
	JOB_NAME varchar(200) not null,
	JOB_GROUP varchar(200) not null,
	DESCRIPTION varchar(250) null,
	JOB_CLASS_NAME varchar(250) not null,
	IS_DURABLE tinyint not null,
	IS_NONCONCURRENT tinyint not null,
	IS_UPDATE_DATA tinyint not null,
	REQUESTS_RECOVERY tinyint not null,
	JOB_DATA longblob null,
	primary key (SCHED_NAME, JOB_NAME, JOB_GROUP)
) engine InnoDB;

create table QUARTZ_LOCKS (
	SCHED_NAME varchar(120) not null,
	LOCK_NAME varchar(40) not null ,
	primary key (SCHED_NAME, LOCK_NAME)
) engine InnoDB;

create table QUARTZ_PAUSED_TRIGGER_GRPS (
	SCHED_NAME varchar(120) not null,
	TRIGGER_GROUP varchar(200) not null,
	primary key (SCHED_NAME, TRIGGER_GROUP)
) engine InnoDB;

create table QUARTZ_SCHEDULER_STATE (
	SCHED_NAME varchar(120) not null,
	INSTANCE_NAME varchar(200) not null,
	LAST_CHECKIN_TIME bigint not null,
	CHECKIN_INTERVAL bigint not null,
	primary key (SCHED_NAME, INSTANCE_NAME)
) engine InnoDB;

create table QUARTZ_SIMPLE_TRIGGERS (
	SCHED_NAME varchar(120) not null,
	TRIGGER_NAME varchar(200) not null,
	TRIGGER_GROUP varchar(200) not null,
	REPEAT_COUNT bigint not null,
	REPEAT_INTERVAL bigint not null,
	TIMES_TRIGGERED bigint not null,
	primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
) engine InnoDB;

create table QUARTZ_SIMPROP_TRIGGERS (
	SCHED_NAME varchar(120) not null,
	TRIGGER_NAME varchar(200) not null,
	TRIGGER_GROUP varchar(200) not null,
	STR_PROP_1 varchar(512) null,
	STR_PROP_2 varchar(512) null,
	STR_PROP_3 varchar(512) null,
	INT_PROP_1 integer null,
	INT_PROP_2 integer null,
	LONG_PROP_1 bigint null,
	LONG_PROP_2 bigint null,
	DEC_PROP_1 NUMERIC(13,4) null,
	DEC_PROP_2 NUMERIC(13,4) null,
	BOOL_PROP_1 tinyint null,
	BOOL_PROP_2 tinyint null,
	primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
) engine InnoDB;

create table QUARTZ_TRIGGERS (
	SCHED_NAME varchar(120) not null,
	TRIGGER_NAME varchar(200) not null,
	TRIGGER_GROUP varchar(200) not null,
	JOB_NAME varchar(200) not null,
	JOB_GROUP varchar(200) not null,
	DESCRIPTION varchar(250) null,
	NEXT_FIRE_TIME bigint null,
	PREV_FIRE_TIME bigint null,
	PRIORITY integer null,
	TRIGGER_STATE varchar(16) not null,
	TRIGGER_TYPE varchar(8) not null,
	START_TIME bigint not null,
	END_TIME bigint null,
	CALENDAR_NAME varchar(200) null,
	MISFIRE_INSTR integer null,
	JOB_DATA longblob null,
	primary key  (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
) engine InnoDB;

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
