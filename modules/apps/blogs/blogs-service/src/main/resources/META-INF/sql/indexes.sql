create index IX_BB0C2905 on BlogsEntry (companyId, displayDate, status);
create index IX_EB2DCE27 on BlogsEntry (companyId, status);
create index IX_A5F57B61 on BlogsEntry (companyId, userId, status);
create index IX_2672F77F on BlogsEntry (displayDate, status);
create index IX_F0E73383 on BlogsEntry (groupId, displayDate, status);
create index IX_1EFD8EE9 on BlogsEntry (groupId, status);
create unique index IX_8DAC6608 on BlogsEntry (groupId, urlTitle[$COLUMN_LENGTH:255$], companyId);
create index IX_DA04F689 on BlogsEntry (groupId, userId, displayDate, status);
create index IX_49E15A23 on BlogsEntry (groupId, userId, status);
create index IX_5E8307BB on BlogsEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_3C52330B on BlogsEntry (uuid_[$COLUMN_LENGTH:75$], groupId, companyId);

create index IX_90CDA39A on BlogsStatsUser (companyId, entryCount);
create index IX_28C78D5C on BlogsStatsUser (groupId, entryCount);
create unique index IX_DBFA1EE3 on BlogsStatsUser (groupId, userId, companyId);
create index IX_507BA031 on BlogsStatsUser (userId, lastPostDate);