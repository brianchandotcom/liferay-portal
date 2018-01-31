create table SiteNavigationMenu (
	siteNavigationMenuId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	type_ INTEGER,
	auto_ BOOLEAN
);

create table SiteNavigationMenuItem (
	siteNavigationMenuItemId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	siteNavigationMenuId LONG,
	parentSiteNavigationMenuItemId LONG,
	type_ VARCHAR(75) null,
	typeSettings TEXT null,
	order_ INTEGER
);