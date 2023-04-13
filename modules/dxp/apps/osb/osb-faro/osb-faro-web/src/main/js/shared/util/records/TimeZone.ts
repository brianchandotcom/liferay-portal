import {Record} from 'immutable';

interface ITimeZone {
	country: string;
	displayTimeZone: string;
	timeZoneId: string;
}

export default class TimeZone
	extends Record({
		country: 'UTC',
		displayTimeZone: '(UTC) UTC',
		timeZoneId: 'UTC'
	})
	implements ITimeZone {
	country: string;
	displayTimeZone: string;
	timeZoneId: string;

	constructor(props = {}) {
		super(props);
	}
}
