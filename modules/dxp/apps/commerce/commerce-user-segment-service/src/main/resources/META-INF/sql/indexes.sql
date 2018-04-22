create index IX_53E31CE on CommerceUserSegmentCriterion (commerceUserSegmentEntryId);

create index IX_C7D6A8EA on CommerceUserSegmentEntry (groupId, active_);
create unique index IX_DE8A7B93 on CommerceUserSegmentEntry (groupId, key_[$COLUMN_LENGTH:75$]);