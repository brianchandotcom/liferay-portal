CREATE TABLE LayoutPageTemplateStructure (
  uuid_ VARCHAR(75) null,
  layoutPageTemplateStructureId LONG not null,
  groupId LONG not null,
  companyId LONG not null,
  userId LONG not null,
  userName VARCHAR(75) not null,
  createDate DATE not null,
  modifiedDate DATE not null,
  classNameId LONG not null,
  classPK LONG not null,
  data_ STRING not null,
  primary key (layoutPageTemplateStructureId)
);

create index IX_6DB0225A on LayoutPageTemplateStructure (uuid_, companyId);
create unique index IX_87B60D9 on LayoutPageTemplateStructure (groupId, classNameId, classPK);
create unique index IX_4DB1775C on LayoutPageTemplateStructure (uuid_, groupId);

COMMIT_TRANSACTION;