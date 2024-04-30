create index IX_314E173E on CommerceQualifierEntry (sourceClassNameId, sourceClassPK);
create unique index IX_30C98C7D on CommerceQualifierEntry (sourceClassNameId, targetClassNameId, sourceClassPK, targetClassPK);
create index IX_C11F2CFF on CommerceQualifierEntry (sourceClassNameId, targetClassNameId, targetClassPK);
create index IX_D4BE2EFE on CommerceQualifierEntry (targetClassNameId, targetClassPK);