import {fromJS} from 'immutable';
import {LocalStorageMechanism, Storage} from 'metal-storage';

const storage = new Storage(new LocalStorageMechanism());

export function loadState() {
	try {
		return fromJS({
			maintenanceSeen: JSON.parse(atob(storage.get('maintenanceSeen'))),
			sidebar: JSON.parse(atob(storage.get('sidebar')))
		});
	} catch (err) {
		return undefined;
	}
}

export function saveState(state) {
	try {
		storage.set(
			'maintenanceSeen',
			btoa(JSON.stringify(state.get('maintenanceSeen')))
		);

		storage.set('sidebar', btoa(JSON.stringify(state.get('sidebar'))));
	} catch (err) {}
}
