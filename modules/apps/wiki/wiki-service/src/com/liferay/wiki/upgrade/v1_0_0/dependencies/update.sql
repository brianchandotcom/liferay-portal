alter table WikiPageResource add companyId LONG;

create index IX_13319367 on WikiPageResource (uuid_, companyId);