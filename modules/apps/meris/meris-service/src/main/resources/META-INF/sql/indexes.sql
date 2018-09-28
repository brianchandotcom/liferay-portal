create index IX_328E1494 on MerisSegment (groupId, active_);
create unique index IX_4F40C029 on MerisSegment (groupId, key_[$COLUMN_LENGTH:75$]);

create index IX_184CF385 on MerisSegmentRel (classNameId, classPK);
create index IX_97A7A3AE on MerisSegmentRel (merisSegmentId);