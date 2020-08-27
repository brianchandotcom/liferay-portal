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

import {emit, getEmittersValues, subscribe} from './connectorsUtils.es';

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
