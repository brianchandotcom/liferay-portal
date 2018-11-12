create index IX_5BFEEA84 on SegmentsEntry (active_, type_[$COLUMN_LENGTH:75$]);
create index IX_755F267E on SegmentsEntry (groupId, active_, type_[$COLUMN_LENGTH:75$]);
create unique index IX_E72E3826 on SegmentsEntry (groupId, key_[$COLUMN_LENGTH:75$]);
create index IX_7BB6BCA6 on SegmentsEntry (groupId, type_[$COLUMN_LENGTH:75$], active_);
create index IX_5296FAFD on SegmentsEntry (type_[$COLUMN_LENGTH:75$]);

create index IX_E1165342 on SegmentsEntryRel (classNameId, classPK);
create index IX_5FBA8532 on SegmentsEntryRel (groupId, classNameId, classPK);
create unique index IX_55B38A5 on SegmentsEntryRel (segmentsEntryId, classNameId, classPK);