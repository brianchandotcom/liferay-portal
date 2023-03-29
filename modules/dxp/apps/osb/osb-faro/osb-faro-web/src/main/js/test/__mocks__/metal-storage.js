export class Storage {
	constructor(storage) {
		this.store = storage;
	}

	get(key) {
		return this.store.get(key) || null;
	}

	set(key, value) {
		this.store.set(key, value.toString());
	}

	remove(key) {
		delete this.store.remove(key);
	}
}

export class LocalStorageMechanism {
	get(key) {
		return this.storage().getItem(key);
	}

	remove(key) {
		this.storage().removeItem(key);
	}

	set(key, value) {
		return this.storage().setItem(key, value);
	}

	storage() {
		return this.globals.localStorage;
	}

	globals = {
		localStorage: global.localStorage
	};
}
