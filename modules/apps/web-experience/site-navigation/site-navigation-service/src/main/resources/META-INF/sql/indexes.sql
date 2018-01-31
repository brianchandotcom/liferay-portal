create index IX_1D786176 on SiteNavigationMenu (groupId, auto_);
create index IX_ECBADAC9 on SiteNavigationMenu (groupId, name[$COLUMN_LENGTH:75$]);
create index IX_1125400B on SiteNavigationMenu (groupId, type_);

create index IX_75495C39 on SiteNavigationMenuItem (parentSiteNavigationMenuItemId);
create index IX_2294C622 on SiteNavigationMenuItem (siteNavigationMenuId, parentSiteNavigationMenuItemId);