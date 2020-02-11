create table FragmentComposition (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	fragmentCompositionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	fragmentCollectionId LONG,
	fragmentCompositionKey VARCHAR(75) null,
	name VARCHAR(75) null,
	description VARCHAR(75) null,
	data_ TEXT null,
	previewFileEntryId LONG,
	lastPublishDate DATE null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);

create index IX_5C61E2DD on FragmentComposition (fragmentCollectionId);
create index IX_11001AAC on FragmentComposition (groupId, fragmentCollectionId, name[$COLUMN_LENGTH:75$], status);
create index IX_28248B2D on FragmentComposition (groupId, fragmentCollectionId, status);
create unique index IX_86F7A143 on FragmentComposition (groupId, fragmentCompositionKey[$COLUMN_LENGTH:75$]);
create index IX_4077D454 on FragmentComposition (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_68D15FD6 on FragmentComposition (uuid_[$COLUMN_LENGTH:75$], groupId);