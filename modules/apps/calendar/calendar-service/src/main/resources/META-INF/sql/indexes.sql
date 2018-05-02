create index IX_97FC174E on Calendar (groupId, calendarResourceId, defaultCalendar);
create index IX_97656498 on Calendar (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_3AE311A on Calendar (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_113A264E on CalendarBooking (calendarId, parentCalendarBookingId);
create index IX_470170B4 on CalendarBooking (calendarId, status);
create unique index IX_8B23DA0E on CalendarBooking (calendarId, vEventUid[$COLUMN_LENGTH:255$]);
create index IX_B198FFC on CalendarBooking (calendarResourceId);
create index IX_F7B8A941 on CalendarBooking (parentCalendarBookingId, status);
create index IX_14ADC52E on CalendarBooking (recurringCalendarBookingId);
create index IX_A21D9FD5 on CalendarBooking (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_F4C61797 on CalendarBooking (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_7727A482 on CalendarNotificationTemplate (calendarId, notificationType[$COLUMN_LENGTH:75$], notificationTemplateType[$COLUMN_LENGTH:75$]);
create index IX_4D7D97BD on CalendarNotificationTemplate (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_4012E97F on CalendarNotificationTemplate (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_76DDD0F7 on CalendarResource (active_);
create unique index IX_16A12327 on CalendarResource (classNameId, classPK);
create index IX_4470A59D on CalendarResource (companyId, code_[$COLUMN_LENGTH:75$], active_);
create index IX_40678371 on CalendarResource (groupId, active_);
create index IX_55C2F8AA on CalendarResource (groupId, code_[$COLUMN_LENGTH:75$]);
create index IX_56A06BC6 on CalendarResource (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_4ABD2BC8 on CalendarResource (uuid_[$COLUMN_LENGTH:75$], groupId);