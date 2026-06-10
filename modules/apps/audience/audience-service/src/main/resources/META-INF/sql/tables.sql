create table AudienceEntry (
	mvccVersion LONG default 0 not null,
	externalReferenceCode VARCHAR(75) null,
	audienceEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	json TEXT null,
	name VARCHAR(75) null
);