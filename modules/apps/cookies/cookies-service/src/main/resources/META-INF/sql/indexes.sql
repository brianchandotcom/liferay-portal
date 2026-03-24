create index IX_E489B15D on CookiesConsentPreference (expirationDate);
create unique index IX_681E72DD on CookiesConsentPreference (userId, domain[$COLUMN_LENGTH:75$], name[$COLUMN_LENGTH:75$]);