create index IX_ECBADAC9 on SiteNavigationMenu (groupId, name[$COLUMN_LENGTH:75$]);
create index IX_3668905B on SiteNavigationMenu (groupId, primary_);
create index IX_DA49575A on SiteNavigationMenu (groupId, secondary);
create index IX_84AE842B on SiteNavigationMenu (groupId, social);

create index IX_75495C39 on SiteNavigationMenuItem (parentSiteNavigationMenuItemId);
create index IX_2294C622 on SiteNavigationMenuItem (siteNavigationMenuId, parentSiteNavigationMenuItemId);