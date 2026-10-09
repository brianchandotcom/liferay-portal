create index IX_68E2B208 on SiteNavigationMenu (companyId);
create index IX_1D786176 on SiteNavigationMenu (groupId, auto_);
create unique index IX_222127D1 on SiteNavigationMenu (groupId, externalReferenceCode[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_CA90FF27 on SiteNavigationMenu (groupId, name[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_1125400B on SiteNavigationMenu (groupId, type_);
create unique index IX_9BA6C248 on SiteNavigationMenu (groupId, uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_828EC794 on SiteNavigationMenu (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_16568744 on SiteNavigationMenuItem (groupId, externalReferenceCode[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_2294C622 on SiteNavigationMenuItem (siteNavigationMenuId, parentSiteNavigationMenuItemId);
create index IX_5551DEE2 on SiteNavigationMenuItem (type_[$COLUMN_LENGTH:75$]);
create unique index IX_CD998367 on SiteNavigationMenuItem (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);