create unique index IX_82120033 on CommerceQualifierEntry (sourceClassNameId, sourceClassPK, targetClassNameId, targetClassPK);
create index IX_C11F2CFF on CommerceQualifierEntry (sourceClassNameId, targetClassNameId, targetClassPK);
create index IX_D4BE2EFE on CommerceQualifierEntry (targetClassNameId, targetClassPK);