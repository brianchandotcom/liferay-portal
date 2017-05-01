create index IX_C44AC7B1 on WeDeployAuth_WeDeployAuthApp (clientId[$COLUMN_LENGTH:75$], clientSecret[$COLUMN_LENGTH:75$]);

create index IX_8D11CF06 on WeDeployAuth_WeDeployAuthToken (clientId[$COLUMN_LENGTH:75$], token[$COLUMN_LENGTH:75$], type_);