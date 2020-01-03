create index IX_97FC174E on Calendar (groupId, calendarResourceId, defaultCalendar);
create index IX_97656498 on Calendar (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_29BDFECE on Calendar (uuid_[$COLUMN_LENGTH:75$], groupId, companyId);

create unique index IX_5AAC811A on CalendarBooking (calendarId, parentCalendarBookingId, companyId);
create index IX_470170B4 on CalendarBooking (calendarId, status);
create unique index IX_C100F55A on CalendarBooking (calendarId, vEventUid[$COLUMN_LENGTH:255$], companyId);
create index IX_B198FFC on CalendarBooking (calendarResourceId);
create index IX_F7B8A941 on CalendarBooking (parentCalendarBookingId, status);
create index IX_14ADC52E on CalendarBooking (recurringCalendarBookingId);
create index IX_A21D9FD5 on CalendarBooking (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_D0981831 on CalendarBooking (uuid_[$COLUMN_LENGTH:75$], groupId, companyId);

create index IX_7727A482 on CalendarNotificationTemplate (calendarId, notificationType[$COLUMN_LENGTH:75$], notificationTemplateType[$COLUMN_LENGTH:75$]);
create index IX_4D7D97BD on CalendarNotificationTemplate (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_BECC8549 on CalendarNotificationTemplate (uuid_[$COLUMN_LENGTH:75$], groupId, companyId);

create index IX_76DDD0F7 on CalendarResource (active_);
create unique index IX_7DE532A1 on CalendarResource (classNameId, classPK, companyId);
create index IX_4470A59D on CalendarResource (companyId, code_[$COLUMN_LENGTH:75$], active_);
create index IX_40678371 on CalendarResource (groupId, active_);
create index IX_55C2F8AA on CalendarResource (groupId, code_[$COLUMN_LENGTH:75$]);
create index IX_56A06BC6 on CalendarResource (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_442D2B60 on CalendarResource (uuid_[$COLUMN_LENGTH:75$], groupId, companyId);