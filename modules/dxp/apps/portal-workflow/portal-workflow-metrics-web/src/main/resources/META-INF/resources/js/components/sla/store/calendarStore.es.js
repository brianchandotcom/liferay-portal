/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import client from '../../../shared/rest/fetch.es';

class CalendarStore {
	constructor(client) {
		this.client = client;
		this.state = {
			calendars: []
		};
	}

	fetchCalendars() {
		return this.client.get('/calendars').then(({data}) =>
			this.setState({
				calendars: data.items
			})
		);
	}

	get defaultCalendar() {
		const defaultCalendars = this.state.calendars.filter(
			calendar => calendar.defaultCalendar
		);

		return defaultCalendars.length ? defaultCalendars[0] : {};
	}

	getState() {
		return this.state;
	}

	setState(props) {
		this.state = Object.assign({}, this.getState(), props);
	}
}

export default new CalendarStore(client);
export {CalendarStore};
