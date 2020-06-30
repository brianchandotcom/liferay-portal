create index IX_17806804 on JournalArticle (DDMStructureKey[$COLUMN_LENGTH:75$]);
create index IX_75CCA4D1 on JournalArticle (DDMTemplateKey[$COLUMN_LENGTH:75$]);
create index IX_C761B675 on JournalArticle (classNameId, DDMTemplateKey[$COLUMN_LENGTH:75$]);
create index IX_717D0FA0 on JournalArticle (classNameId, expirationDate, status);
create index IX_323DF109 on JournalArticle (companyId, status);
create index IX_E82F322B on JournalArticle (companyId, version, status);
create index IX_EA05E9E1 on JournalArticle (displayDate, status);
create index IX_D8EB0D84 on JournalArticle (groupId, DDMStructureKey[$COLUMN_LENGTH:75$]);
create index IX_31B74F51 on JournalArticle (groupId, DDMTemplateKey[$COLUMN_LENGTH:75$]);
create index IX_4D5CD982 on JournalArticle (groupId, articleId[$COLUMN_LENGTH:75$], status);
create unique index IX_85C52EEC on JournalArticle (groupId, articleId[$COLUMN_LENGTH:75$], version);
create index IX_353BD560 on JournalArticle (groupId, classNameId, DDMStructureKey[$COLUMN_LENGTH:75$]);
create index IX_6E801BF5 on JournalArticle (groupId, classNameId, DDMTemplateKey[$COLUMN_LENGTH:75$]);
create index IX_9CE6E0FA on JournalArticle (groupId, classNameId, classPK);
create index IX_A2534AC2 on JournalArticle (groupId, classNameId, layoutUuid[$COLUMN_LENGTH:75$]);
create index IX_F35391E8 on JournalArticle (groupId, folderId, status);
create index IX_3C028C1E on JournalArticle (groupId, layoutUuid[$COLUMN_LENGTH:75$]);
create index IX_301D024B on JournalArticle (groupId, status);
create index IX_D2D249E8 on JournalArticle (groupId, urlTitle[$COLUMN_LENGTH:255$], status);
create index IX_43A0F80F on JournalArticle (groupId, userId, classNameId);
create index IX_3F1EA19E on JournalArticle (layoutUuid[$COLUMN_LENGTH:75$]);
create index IX_451D63EC on JournalArticle (resourcePrimKey, indexable, status);
create index IX_3E2765FC on JournalArticle (resourcePrimKey, status);
create index IX_EF9B7028 on JournalArticle (smallImageId);
create index IX_71520099 on JournalArticle (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_3463D95B on JournalArticle (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_ACF2560A on JournalArticleLocalization (articlePK, languageId[$COLUMN_LENGTH:75$]);

create unique index IX_88DF994A on JournalArticleResource (groupId, articleId[$COLUMN_LENGTH:75$]);
create index IX_CC7576C7 on JournalArticleResource (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_84AB0309 on JournalArticleResource (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_9207CB31 on JournalContentSearch (articleId[$COLUMN_LENGTH:75$]);
create index IX_42F51F38 on JournalContentSearch (companyId);
create index IX_6838E427 on JournalContentSearch (groupId, articleId[$COLUMN_LENGTH:75$]);
create index IX_7CC7D73E on JournalContentSearch (groupId, privateLayout, articleId[$COLUMN_LENGTH:75$]);
create unique index IX_C3AA93B8 on JournalContentSearch (groupId, privateLayout, layoutId, portletId[$COLUMN_LENGTH:200$], articleId[$COLUMN_LENGTH:75$]);
create index IX_8DAF8A35 on JournalContentSearch (portletId[$COLUMN_LENGTH:200$]);

create unique index IX_65576CBC on JournalFeed (groupId, feedId[$COLUMN_LENGTH:75$]);
create index IX_CB37A10F on JournalFeed (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_39031F51 on JournalFeed (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_C36B0443 on JournalFolder (companyId, status);
create index IX_E988689E on JournalFolder (groupId, name[$COLUMN_LENGTH:100$]);
create unique index IX_65026705 on JournalFolder (groupId, parentFolderId, name[$COLUMN_LENGTH:100$]);
create index IX_EFD9CAC on JournalFolder (groupId, parentFolderId, status);
create index IX_54F89E1F on JournalFolder (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_E002061 on JournalFolder (uuid_[$COLUMN_LENGTH:75$], groupId);