create index IX_DD85AA60 on OAuth_OAuthApplication (companyId, name[$COLUMN_LENGTH:75$]);
create unique index IX_507EB776 on OAuth_OAuthApplication (consumerKey[$COLUMN_LENGTH:75$], companyId);
create index IX_2B33FAA0 on OAuth_OAuthApplication (userId, name[$COLUMN_LENGTH:75$]);

create unique index IX_7DDB69C3 on OAuth_OAuthUser (accessToken[$COLUMN_LENGTH:75$], companyId);
create index IX_4167B528 on OAuth_OAuthUser (oAuthApplicationId);
create unique index IX_5455C686 on OAuth_OAuthUser (userId, oAuthApplicationId, companyId);