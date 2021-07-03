create index IX_BC95A53F on Calendar (groupId, calendarResourceId, ctCollectionId);
create index IX_8C1A91AC on Calendar (groupId, calendarResourceId, defaultCalendar, ctCollectionId);
create index IX_2640AF6 on Calendar (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_E6BA3BEE on Calendar (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_31D79378 on Calendar (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_497E5A2C on CalendarBooking (calendarId, ctCollectionId);
create unique index IX_BD5AA0AC on CalendarBooking (calendarId, parentCalendarBookingId, ctCollectionId);
create index IX_C2FC5F12 on CalendarBooking (calendarId, status, ctCollectionId);
create unique index IX_8AE4D46C on CalendarBooking (calendarId, vEventUid[$COLUMN_LENGTH:255$], ctCollectionId);
create index IX_98BCEE5A on CalendarBooking (calendarResourceId, ctCollectionId);
create index IX_F52EB5B9 on CalendarBooking (parentCalendarBookingId, ctCollectionId);
create index IX_BBD2DD9F on CalendarBooking (parentCalendarBookingId, status, ctCollectionId);
create index IX_E6747F8C on CalendarBooking (recurringCalendarBookingId, ctCollectionId);
create index IX_FBC02C33 on CalendarBooking (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_E5067ED1 on CalendarBooking (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_1554DFF5 on CalendarBooking (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_21D79014 on CalendarNotificationTemplate (calendarId, ctCollectionId);
create index IX_A70D36E0 on CalendarNotificationTemplate (calendarId, notificationType[$COLUMN_LENGTH:75$], notificationTemplateType[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_843541B on CalendarNotificationTemplate (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_85ED37E9 on CalendarNotificationTemplate (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_10D0E1DD on CalendarNotificationTemplate (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_8A8DD955 on CalendarResource (active_, ctCollectionId);
create unique index IX_CD46CB85 on CalendarResource (classNameId, classPK, ctCollectionId);
create index IX_EE1EA1FB on CalendarResource (companyId, code_[$COLUMN_LENGTH:75$], active_, ctCollectionId);
create index IX_AF6757CF on CalendarResource (groupId, active_, ctCollectionId);
create index IX_470B3B08 on CalendarResource (groupId, code_[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_A61E7CF6 on CalendarResource (groupId, ctCollectionId);
create index IX_F36F624 on CalendarResource (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_B4CA8180 on CalendarResource (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_53277226 on CalendarResource (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);