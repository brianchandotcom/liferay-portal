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

const ALTER_RETURN = 'alterReturn';

const HALT = 'halt';

const METAL_AOP = '__METAL_AOP__';

const PREVENT = 'prevent';

/**
 * AOP class
 */
class AOP {
	/**
	 * Constructor for AOP class.
	 * @param {!Object} obj The object containing the displaced function.
	 * @param {!string} fnName The name of the displaced function.
	 * @constructor
	 */
	constructor(obj, fnName) {
		/**
		 * Array of listeners that will invoke after the displaced function.
		 * @type {!Array}
		 * @protected
		 */
		this.after_ = [];

		/**
		 * Array of listeners that will invoke before the displaced function.
		 * @type {!Array}
		 * @protected
		 */
		this.before_ = [];

		/**
		 * The name of the displaced function.
		 * @type {string}
		 * @protected
		 */
		this.fnName_ = fnName;

		/**
		 * The displaced function.
		 * @type {Function}
		 * @protected
		 */
		this.fn_ = obj[fnName];

		/**
		 * The object hosting the method to displace.
		 * @type {Object}
		 * @protected
		 */
		this.obj_ = obj;
	}

	/**
	 * Creates handle for detaching listener from displaced function.
	 * @param {!Function} fn The listener
	 * @param {!boolean} before Determines when listener fires
	 * @return {Object}
	 */
	createHandle(fn, before) {
		return {
			detach: this.detach_.bind(this, fn, before)
		};
	}

	/**
	 * Detaches listener from displaced function.
	 * @param {!Function} fn The listener
	 * @param {!boolean} before Determines when listener fires
	 */
	detach_(fn, before) {
		const listenerArray = before ? this.before_ : this.after_;

		listenerArray.splice(listenerArray.indexOf(fn), 1);
	}

	/**
	 *
	 * @param {any} args* Arguments are passed to the wrapping and wrapped functions.
	 * @return {any} Return value of wrapped function.
	 */
	exec(...args) {
		let listenerRetVal;
		let prevented = false;
		let retVal;

		for (let i = 0; i < this.before_.length; i++) {
			listenerRetVal = this.before_[i].apply(this.obj_, args);

			if (listenerRetVal && listenerRetVal.type) {
				if (listenerRetVal.type === HALT) {
					return listenerRetVal.value;
				}
				else if (listenerRetVal.type === PREVENT) {
					prevented = true;
				}
			}
		}

		if (!prevented) {
			retVal = this.fn_.apply(this.obj_, args);
		}

		AOP.currentRetVal = retVal;
		AOP.originalRetVal = retVal;

		for (let i = 0; i < this.after_.length; i++) {
			listenerRetVal = this.after_[i].apply(this.obj_, args);

			if (listenerRetVal && listenerRetVal.type) {
				if (listenerRetVal.type === HALT) {
					return listenerRetVal.value;
				}
				else if (listenerRetVal.type === ALTER_RETURN) {
					retVal = listenerRetVal.value;

					AOP.currentRetVal = retVal;
				}
			}
		}

		return retVal;
	}

	/**
	 * Registers an AOP listener.
	 *
	 * @param {!Function} fn the function to execute.
	 * @param {boolean} before determines when the listener is invoked.
	 * @return {EventHandle} Can be used to remove the listener.
	 */
	register(fn, before) {
		if (before) {
			this.before_.push(fn);
		}
		else {
			this.after_.push(fn);
		}

		return this.createHandle(fn, before);
	}

	/**
	 * Executes the supplied method after the specified function.
	 *
	 * @param {!Function} fn the function to execute.
	 * @param {!Object} obj the object hosting the method to displace.
	 * @param {!string} fnName the name of the method to displace.
	 * @return {EventHandle} Can be used to remove the listener.
	 */
	static after(fn, obj, fnName) {
		return AOP.inject(false, fn, obj, fnName);
	}

	/**
	 * Return an alterReturn object when you want to change the result returned
	 * from the core method to the caller.
	 *
	 * @param {any} value Return value passed to code that invoked the wrapped
	 * function.
	 * @return {Object}
	 */
	static alterReturn(value) {
		return AOP.modify_(ALTER_RETURN, value);
	}

	/**
	 * Executes the supplied method before the specified function.
	 *
	 * @param {!Function} fn the function to execute.
	 * @param {!Object} obj the object hosting the method to displace.
	 * @param {!string} fnName the name of the method to displace.
	 * @return {EventHandle} Can be used to remove the listener.
	 */
	static before(fn, obj, fnName) {
		return AOP.inject(true, fn, obj, fnName);
	}

	/**
	 * Return a halt object when you want to terminate the execution
	 * of all subsequent subscribers as well as the wrapped method
	 * if it has not executed yet.
	 *
	 * @param {any} value Return value passed to code that invoked the wrapped
	 * function.
	 * @return {Object}
	 */
	static halt(value) {
		return AOP.modify_(HALT, value);
	}

	/**
	 * Executes the supplied method before or after the specified function.
	 *
	 * @param {boolean} before determines when the listener is invoked.
	 * @param {!Function} fn the function to execute.
	 * @param {!Object} obj the object hosting the method to displace.
	 * @param {!string} fnName the name of the method to displace.
	 * @return {EventHandle} Can be used to remove the listener.
	 */
	static inject(before, fn, obj, fnName) {
		let aopObj = obj[METAL_AOP];

		if (!aopObj) {
			aopObj = obj[METAL_AOP] = {};
		}

		if (!aopObj[fnName]) {
			aopObj[fnName] = new AOP(obj, fnName);

			obj[fnName] = function(...args) {
				return aopObj[fnName].exec(...args);
			};
		}

		return aopObj[fnName].register(fn, before);
	}

	/**
	 * Returns object which instructs `exec` method to modify the return
	 * value or prevent default behavior of wrapped function.
	 *
	 * @param {!string} type The type of modification to be made
	 * @param {any} value Return value passed to code that invoked the wrapped
	 * function.
	 * @return {Object}
	 */
	static modify_(type, value) {
		return {
			type,
			value
		};
	}

	/**
	 * Return a prevent object when you want to prevent the wrapped function
	 * from executing, but want the remaining listeners to execute.
	 * @return {Object}
	 */
	static prevent() {
		return AOP.modify_(PREVENT);
	}
}

export default AOP;
export {AOP};
