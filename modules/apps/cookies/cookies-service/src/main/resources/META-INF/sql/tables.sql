create table CookiesConsentPreference (
	mvccVersion LONG default 0 not null,
	cookiesConsentPreferenceId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	domain VARCHAR(75) null,
	expirationDate DATE null,
	name VARCHAR(75) null,
	value VARCHAR(75) null
);