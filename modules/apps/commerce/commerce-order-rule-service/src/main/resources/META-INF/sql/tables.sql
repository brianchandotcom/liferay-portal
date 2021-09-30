create table CommerceOrderRuleEntry (
	externalReferenceCode VARCHAR(75) null,
	commerceOrderRuleEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	active_ BOOLEAN,
	description VARCHAR(75) null,
	name VARCHAR(75) null,
	priority INTEGER,
	type_ VARCHAR(75) null,
	typeSettings VARCHAR(75) null
);

create table CommerceOrderRuleEntryRel (
	commerceOrderRuleEntryRelId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	commerceOrderRuleEntryId LONG
);