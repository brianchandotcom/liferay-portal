/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

export function setDateSelected(d) {
	const newDate = clone(d);

	newDate.setHours(12, 0, 0, 0);
	return `${newDate.getFullYear()} ${newDate.getMonth()} ${newDate.getDate()}`;
}

export function formatDate(d) {
	const dateSplit = d.split(' ');
	const newDate = new Date(...dateSplit);

	return newDate;
}

export function clone(d) {
	return new Date(d.getTime());
}

export function getDaysInMonth(d) {
	const firstDayOfMonth = new Date(d.getFullYear(), d.getMonth(), 1, 12);

	firstDayOfMonth.setMonth(firstDayOfMonth.getMonth() + 1);
	firstDayOfMonth.setDate(firstDayOfMonth.getDate() - 1);

	return firstDayOfMonth.getDate();
}

export function getWeekArray(d, firstDayOfWeek = 0) {
	const dayArray = [];
	const daysInMonth = getDaysInMonth(d);

	let week = [];
	const weekArray = [];

	for (let i = 1; i <= daysInMonth; i += 1) {
		const genDay = new Date(d.getFullYear(), d.getMonth(), i, 12);

		dayArray.push({
			date: genDay,
			dateString: setDateSelected(genDay),
			number: genDay.getDate()
		});
	}

	dayArray.forEach(day => {
		if (week.length > 0 && day.date.getDay() === firstDayOfWeek) {
			weekArray.push(week);
			week = [];
		}
		week.push(day);
		if (dayArray.indexOf(day) === dayArray.length - 1) {
			weekArray.push(week);
		}
	});

	const firstWeek = weekArray[0];

	for (let i = 7 - firstWeek.length; i > 0; i -= 1) {
		const outsideDate = clone(firstWeek[0].date);

		outsideDate.setDate(firstWeek[0].date.getDate() - 1);
		firstWeek.unshift({
			date: outsideDate,
			dateString: setDateSelected(outsideDate),
			number: outsideDate.getDate(),
			outside: true
		});
	}

	const lastWeek = weekArray[weekArray.length - 1];

	for (let i = lastWeek.length; i < 7; i += 1) {
		const outsideDate = clone(lastWeek[lastWeek.length - 1].date);

		outsideDate.setDate(lastWeek[lastWeek.length - 1].date.getDate() + 1);
		lastWeek.push({
			date: outsideDate,
			dateString: setDateSelected(outsideDate),
			number: outsideDate.getDate(),
			outside: true
		});
	}

	return weekArray;
}
