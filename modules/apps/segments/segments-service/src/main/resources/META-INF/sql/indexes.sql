create index IX_2E0C3F77 on SegmentsEntry (groupId, active_);
create unique index IX_E72E3826 on SegmentsEntry (groupId, key_[$COLUMN_LENGTH:75$]);

create index IX_E1165342 on SegmentsEntryRel (classNameId, classPK);
create index IX_5FBA8532 on SegmentsEntryRel (groupId, classNameId, classPK);
create unique index IX_55B38A5 on SegmentsEntryRel (segmentsEntryId, classNameId, classPK);