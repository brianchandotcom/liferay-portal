create index IX_41B032DE on MicroblogsEntry (companyId, creatorClassNameId, creatorClassPK);
create index IX_D294099F on MicroblogsEntry (type_, companyId, creatorClassNameId, creatorClassPK);
create index IX_6BD29B9C on MicroblogsEntry (type_, parentMicroblogsEntryId);
create index IX_AA96AEF9 on MicroblogsEntry (type_, userId, socialRelationType, createDate);
create index IX_6C297B45 on MicroblogsEntry (userId);