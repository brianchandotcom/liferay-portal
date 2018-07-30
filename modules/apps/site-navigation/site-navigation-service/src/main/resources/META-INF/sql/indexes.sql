create index IX_1D786176 on SiteNavigationMenu (groupId, auto_);
create unique index IX_ECBADAC9 on SiteNavigationMenu (groupId, name[$COLUMN_LENGTH:75$]);
create index IX_1125400B on SiteNavigationMenu (groupId, type_);
create index IX_606C7814 on SiteNavigationMenu (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_711BF396 on SiteNavigationMenu (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_75495C39 on SiteNavigationMenuItem (parentSiteNavigationMenuItemId);
create index IX_9FA7003B on SiteNavigationMenuItem (siteNavigationMenuId, name[$COLUMN_LENGTH:255$]);
create index IX_2294C622 on SiteNavigationMenuItem (siteNavigationMenuId, parentSiteNavigationMenuItemId);
create index IX_90D752C7 on SiteNavigationMenuItem (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_6FD3DF09 on SiteNavigationMenuItem (uuid_[$COLUMN_LENGTH:75$], groupId);