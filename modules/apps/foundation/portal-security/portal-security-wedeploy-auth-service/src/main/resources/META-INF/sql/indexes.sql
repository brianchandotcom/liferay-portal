create index IX_C44AC7B1 on WeDeployAuth_WeDeployAuthApp (clientId[$COLUMN_LENGTH:75$], clientSecret[$COLUMN_LENGTH:75$]);
create index IX_6E8FF6F2 on WeDeployAuth_WeDeployAuthApp (redirectURI[$COLUMN_LENGTH:75$], clientId[$COLUMN_LENGTH:75$]);

create index IX_8D11CF06 on WeDeployAuth_WeDeployAuthToken (clientId[$COLUMN_LENGTH:75$], token[$COLUMN_LENGTH:75$], type_);
create index IX_1741E4CC on WeDeployAuth_WeDeployAuthToken (token[$COLUMN_LENGTH:75$], type_);