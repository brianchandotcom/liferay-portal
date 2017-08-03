create index IX_DD85AA60 on OAuth_OAuthApplication (companyId, name[$COLUMN_LENGTH:75$]);
create unique index IX_B12A5172 on OAuth_OAuthApplication (consumerKey[$COLUMN_LENGTH:75$]);
create index IX_2B33FAA0 on OAuth_OAuthApplication (userId, name[$COLUMN_LENGTH:75$]);

create unique index IX_84260D45 on OAuth_OAuthUser (accessToken[$COLUMN_LENGTH:75$]);
create index IX_4167B528 on OAuth_OAuthUser (oAuthApplicationId);
create unique index IX_7B260C62 on OAuth_OAuthUser (userId, oAuthApplicationId);