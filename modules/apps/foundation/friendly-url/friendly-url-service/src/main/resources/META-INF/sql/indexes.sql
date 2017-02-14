create index IX_C9E74CCE on FriendlyURL (groupId, classNameId);
create index IX_D8ED11AA on FriendlyURL (groupId, companyId, classNameId, classPK, main);
create index IX_56FC929A on FriendlyURL (groupId, companyId, classNameId, classPK, urlTitle[$COLUMN_LENGTH:255$]);
create index IX_53418A03 on FriendlyURL (groupId, companyId, classNameId, urlTitle[$COLUMN_LENGTH:255$]);
create index IX_BBBC6ADE on FriendlyURL (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_1C7E10E0 on FriendlyURL (uuid_[$COLUMN_LENGTH:75$], groupId);