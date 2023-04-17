create unique index IX_5960B666 on OSBFaro_FaroChannel (channelId[$COLUMN_LENGTH:75$], workspaceGroupId);
create index IX_28923D9C on OSBFaro_FaroChannel (groupId, userId);
create index IX_286BA5A1 on OSBFaro_FaroChannel (workspaceGroupId);

create index IX_A41A962F on OSBFaro_FaroNotification (createTime);
create index IX_8C52A745 on OSBFaro_FaroNotification (groupId, createTime, ownerId, read_, type_[$COLUMN_LENGTH:75$], subtype[$COLUMN_LENGTH:75$]);
create index IX_CBF4F47E on OSBFaro_FaroNotification (groupId, createTime, ownerId, type_[$COLUMN_LENGTH:75$], subtype[$COLUMN_LENGTH:75$]);
create index IX_A608C94D on OSBFaro_FaroNotification (groupId, scope[$COLUMN_LENGTH:75$], read_);
create index IX_8E7E81C1 on OSBFaro_FaroNotification (userId, scope[$COLUMN_LENGTH:75$], read_);

create unique index IX_12C47BB1 on OSBFaro_FaroPreferences (groupId, ownerId);

create unique index IX_ECF55FFC on OSBFaro_FaroProject (corpProjectUuid[$COLUMN_LENGTH:75$]);
create unique index IX_3D4C0F8C on OSBFaro_FaroProject (groupId);
create index IX_46F39E6A on OSBFaro_FaroProject (serverLocation[$COLUMN_LENGTH:75$]);
create index IX_DC26D918 on OSBFaro_FaroProject (userId);
create unique index IX_D2CDE05C on OSBFaro_FaroProject (weDeployKey[$COLUMN_LENGTH:75$]);

create index IX_82F8539E on OSBFaro_FaroProjectEmailDomain (faroProjectId);
create index IX_7D13235C on OSBFaro_FaroProjectEmailDomain (groupId);

create index IX_5E384504 on OSBFaro_FaroUser (emailAddress[$COLUMN_LENGTH:75$], status);
create unique index IX_6A8038A4 on OSBFaro_FaroUser (groupId, emailAddress[$COLUMN_LENGTH:75$]);
create index IX_FCDBAA3E on OSBFaro_FaroUser (groupId, liveUserId);
create index IX_1B6F355D on OSBFaro_FaroUser (groupId, roleId);
create index IX_79F1D4DE on OSBFaro_FaroUser (groupId, status);
create unique index IX_59B1B46 on OSBFaro_FaroUser (key_[$COLUMN_LENGTH:75$]);
create index IX_F3CF931E on OSBFaro_FaroUser (liveUserId, status);