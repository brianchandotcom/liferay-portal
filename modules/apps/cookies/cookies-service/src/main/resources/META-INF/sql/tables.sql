create table ConsentPreference (
	mvccVersion LONG default 0 not null,
	consentPreferenceId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	domain VARCHAR(75) null,
	expirationDate DATE null,
	name VARCHAR(75) null,
	value VARCHAR(75) null
);