/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Disposable from './Disposable';

/**
 * EventHandler utility. It's useful for easily removing a group of
 * listeners from different EventEmitter instances.
 * @extends {Disposable}
 */
class EventHandler extends Disposable {

	/**
	 * EventHandler constructor
	 */
	constructor() {
		super();

		/**
		 * An array that holds the added event handles, so the listeners can be
		 * removed later.
		 * @type {Array.<EventHandle>}
		 * @protected
		 */
		this._eventHandles = [];
	}

	/**
	 * Adds event handles to be removed later through the `removeAllListeners`
	 * method.
	 * @param {...(!EventHandle)} args
	 */
	add(...args) {
		for (let i = 0; i < args.length; i++) {
			this._eventHandles.push(args[i]);
		}
	}

	/**
	 * Disposes of this instance's object references.
	 * @override
	 */
	disposeInternal() {
		this._eventHandles = null;
	}

	/**
	 * Removes all listeners that have been added through the `add` method.
	 */
	removeAllListeners() {
		for (let i = 0; i < this._eventHandles.length; i++) {
			this._eventHandles[i].removeListener();
		}

		this._eventHandles = [];
	}
}

export default EventHandler;
