create index IX_A821854B on PM_UserThread (mbThreadId);
create index IX_434EE852 on PM_UserThread (userId, deleted);
create index IX_466F2985 on PM_UserThread (userId, mbThreadId);
create index IX_A16EF3C7 on PM_UserThread (userId, read_, deleted);

create index IX_2281B549 on UserThread (mbThreadId);
create index IX_2A230714 on UserThread (userId, deleted);
create index IX_D167B83 on UserThread (userId, mbThreadId);
create index IX_400116C5 on UserThread (userId, read_, deleted);