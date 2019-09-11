create table SiteNavigationMenu (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	siteNavigationMenuId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	type_ INTEGER,
	auto_ BOOLEAN,
	lastPublishDate DATE null
);

create table SiteNavigationMenuItem (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	siteNavigationMenuItemId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	siteNavigationMenuId LONG,
	parentSiteNavigationMenuItemId LONG,
	name VARCHAR(255) null,
	type_ VARCHAR(75) null,
	typeSettings TEXT null,
	order_ INTEGER,
	lastPublishDate DATE null
);