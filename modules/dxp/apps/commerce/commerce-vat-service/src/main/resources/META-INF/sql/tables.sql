create table CommerceVatNumber (
	commerceVatNumberId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	value VARCHAR(75) null,
	valid BOOLEAN
);