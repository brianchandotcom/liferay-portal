create index IX_9BF87866 on SiteNavigationMenu (companyId, ctCollectionId);
create index IX_4E4D8BD4 on SiteNavigationMenu (groupId, auto_, ctCollectionId);
create index IX_73889CE8 on SiteNavigationMenu (groupId, ctCollectionId);
create unique index IX_CA90FF27 on SiteNavigationMenu (groupId, name[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_AB5CA069 on SiteNavigationMenu (groupId, type_, ctCollectionId);
create index IX_8B2CA672 on SiteNavigationMenu (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_E9BFF5F2 on SiteNavigationMenu (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_F1C8DDF4 on SiteNavigationMenu (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_9C7CF713 on SiteNavigationMenuItem (companyId, ctCollectionId);
create unique index IX_7B4515FE on SiteNavigationMenuItem (externalReferenceCode[$COLUMN_LENGTH:75$], groupId, ctCollectionId);
create index IX_5A4EA097 on SiteNavigationMenuItem (parentSiteNavigationMenuItemId, ctCollectionId);
create index IX_CB221BDA on SiteNavigationMenuItem (siteNavigationMenuId, ctCollectionId);
create index IX_9E380099 on SiteNavigationMenuItem (siteNavigationMenuId, name[$COLUMN_LENGTH:255$], ctCollectionId);
create index IX_28431880 on SiteNavigationMenuItem (siteNavigationMenuId, parentSiteNavigationMenuItemId, ctCollectionId);
create index IX_B569BB25 on SiteNavigationMenuItem (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_9D80D31F on SiteNavigationMenuItem (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_CD998367 on SiteNavigationMenuItem (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);