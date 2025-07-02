create index IX_7333931B on Child (companyId, grandParentId);
create index IX_B9646ED7 on Child (companyId, parentChildId);

create index IX_516F7BAC on GrandParent (companyId);

create index IX_E0FFE1E9 on Parent (companyId, grandParentId);