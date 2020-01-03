create table SPIDefinition (
	spiDefinitionId LONG not null,
	companyId LONG not null,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(200) null,
	connectorAddress VARCHAR(200) null,
	connectorPort INTEGER,
	description STRING null,
	jvmArguments STRING null,
	portletIds STRING null,
	servletContextNames STRING null,
	typeSettings TEXT null,
	status INTEGER,
	statusMessage STRING null,
	primary key (spiDefinitionId, companyId)
);