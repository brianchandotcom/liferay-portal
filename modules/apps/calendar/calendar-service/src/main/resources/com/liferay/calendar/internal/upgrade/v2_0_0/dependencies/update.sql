alter table CalendarBooking add recurringCalendarBookingId LONG null;

create index IX_14ADC52E on CalendarBooking (recurringCalendarBookingId);

update CalendarBooking set recurringCalendarBookingId = calendarBookingId;