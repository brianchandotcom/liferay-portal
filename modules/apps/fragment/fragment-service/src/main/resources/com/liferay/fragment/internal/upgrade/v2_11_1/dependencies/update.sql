create table FragmentEntryContributed (
	  mvccVersion LONG default 0 not null,
	  ctCollectionId LONG default 0 not null,
	  fragmentEntryContributedId LONG not null,
	  createDate DATE null,
	  modifiedDate DATE null,
	  fragmentEntryKey VARCHAR(75) null,
	  css TEXT null,
	  html TEXT null,
	  js TEXT null,
	  configuration TEXT null,
	  type_ INTEGER,
	  primary key (fragmentEntryContributedId, ctCollectionId)
);

create unique index IX_9501C32A on FragmentEntryContributed (fragmentEntryKey[$COLUMN_LENGTH:75$], ctCollectionId);