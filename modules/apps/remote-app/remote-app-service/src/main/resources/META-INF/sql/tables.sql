create table RemoteAppEntry (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	remoteAppEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	customElementCSSURLs TEXT null,
	customElementHTMLElementName VARCHAR(255) null,
	customElementURLs TEXT null,
	iFrameURL STRING null,
	instanceable BOOLEAN,
	name STRING null,
	portletCategoryName VARCHAR(75) null,
	properties TEXT null,
	type_ VARCHAR(75) null
);