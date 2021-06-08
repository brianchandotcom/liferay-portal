create unique index IX_1F92C081 on CAccountGroupCAccountRel (commerceAccountGroupId, commerceAccountId);
create index IX_9808F83D on CAccountGroupCAccountRel (commerceAccountId);
create index IX_41A0DD5 on CAccountGroupCAccountRel (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);