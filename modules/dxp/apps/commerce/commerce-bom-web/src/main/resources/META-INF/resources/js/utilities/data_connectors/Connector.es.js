import { subscribe, emit, getEmittersValues } from './connectorsUtils.es';

export default class Connector {
	constructor(data) {
		this.id = data.id;
		this.on = data.on;
		this.emitters = data.emitters;
		this.getValue = this.getValue.bind(this);
		this.notify = this.notify.bind(this);
		this.notified = this.notified.bind(this);

		subscribe(this.id, this.emitters, this.getValue, this.notified);
	}

	getEmittersValues() {
		return getEmittersValues(this.id);
	}

	getValue() {
		return this.on.getValue();
	}

	notify() {
		return emit(this.id);
	}

	notified() {
		return this.on.notified(this.getEmittersValues());
	}
}
