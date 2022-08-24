create table CTCollectionTemplate (
      mvccVersion LONG default 0 not null,
      ctCollectionTemplateId LONG not null primary key,
      companyId LONG,
      userId LONG,
      createDate DATE null,
      modifiedDate DATE null,
      name VARCHAR(75) null,
      description VARCHAR(75) null
);

create index IX_489283B9 on CTCollectionTemplate (companyId);

COMMIT_TRANSACTION;