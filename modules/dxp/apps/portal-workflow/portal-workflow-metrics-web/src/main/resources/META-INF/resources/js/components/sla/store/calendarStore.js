import client from '../../../shared/rest/fetch';

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
