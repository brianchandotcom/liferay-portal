create table CustomElementsPortletDesc (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	customElementsPortletDescId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	cssURLs TEXT null,
	htmlElementName VARCHAR(75) null,
	instanceable BOOLEAN,
	name VARCHAR(75) null,
	properties TEXT null
);

create table CustomElementsSource (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	customElementsSourceId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	htmlElementName VARCHAR(75) null,
	name VARCHAR(75) null,
	urls TEXT null
);