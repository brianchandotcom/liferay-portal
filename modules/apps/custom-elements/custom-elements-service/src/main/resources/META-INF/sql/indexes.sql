create index IX_37C0779D on CustomElementsPortletDesc (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_D382FF9F on CustomElementsSource (companyId);
create unique index IX_C654AF63 on CustomElementsSource (htmlElementName[$COLUMN_LENGTH:75$]);
create index IX_90C1C5E8 on CustomElementsSource (name[$COLUMN_LENGTH:75$]);
create index IX_C21D029D on CustomElementsSource (uuid_[$COLUMN_LENGTH:75$], companyId);