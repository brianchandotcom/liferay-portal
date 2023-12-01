create index IX_314E173E on CommerceQualifierEntry (sourceClassNameId, sourceClassPK);
create unique index IX_F09D8A9 on CommerceQualifierEntry (sourceClassNameId, targetClassNameId, targetClassPK, sourceClassPK);
create index IX_D4BE2EFE on CommerceQualifierEntry (targetClassNameId, targetClassPK);