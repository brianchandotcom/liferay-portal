create table CAccountGroupCAccountRel (
	externalReferenceCode VARCHAR(75) null,
	CAccountGroupCAccountRelId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	commerceAccountGroupId LONG,
	commerceAccountId LONG
);