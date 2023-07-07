create table CTRemote (
      mvccVersion LONG default 0 not null,
      ctRemoteId LONG not null primary key,
      companyId LONG,
      userId LONG,
      createDate DATE null,
      modifiedDate DATE null,
      name VARCHAR(75) null,
      description VARCHAR(75) null,
      url VARCHAR(75) null
);

create index IX_9B9391EB on CTRemote (companyId);

COMMIT_TRANSACTION;