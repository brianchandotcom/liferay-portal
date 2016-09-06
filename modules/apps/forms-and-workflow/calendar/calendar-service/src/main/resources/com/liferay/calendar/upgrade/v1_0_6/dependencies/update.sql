alter table CalendarBooking add recurringCalendarBookingId LONG null;

update CalendarBooking set recurringCalendarBookingId = calendarBookingId;