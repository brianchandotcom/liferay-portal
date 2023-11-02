import {fromJS} from 'immutable';

export function loadState() {
	try {
		return fromJS({
			maintenanceSeen: JSON.parse(
				atob(localStorage.getItem('maintenanceSeen'))
			),
			sidebar: JSON.parse(atob(localStorage.getItem('sidebar')))
		});
	} catch (err) {
		return undefined;
	}
}

export function saveState(state) {
	try {
		localStorage.setItem(
			'maintenanceSeen',
			btoa(JSON.stringify(state.get('maintenanceSeen')))
		);

		localStorage.setItem(
			'sidebar',
			btoa(JSON.stringify(state.get('sidebar')))
		);
	} catch (err) {}
}
