import {get} from 'lodash';

const requestSymbol = Symbol('request');

/**
 * This util contains decorators that are to be use in conjuction with each other (@autoCancel & @hasRequest).
 * @example
 * @hasRequest
 * class Test extends Component {
 *	@autoCancel
 *	fetchData() {
 *		return API.fetchData();
 *	}
 * }
 */

/**
 * Class decorator that will automatically cancel all pending requests
 * that have been added to the cancellation queue via the class member
 * decorator (@autoCancel)
 * @param {Component} WrappedComponent
 * @return {Component}
 */
export function hasRequest(WrappedComponent) {
	return class extends WrappedComponent {
		componentWillUnmount() {
			const requests = get(this, requestSymbol, []);

			requests.map(requestKey => {
				const request = get(this, requestKey);

				if (request && request.cancel) {
					request.cancel();
				}
			});

			if (WrappedComponent.prototype.componentWillUnmount) {
				WrappedComponent.prototype.componentWillUnmount.apply(this);
			}
		}
	};
}

/**
 * Class member decorator that will queue the Promise for cancellation.
 * This decorator is meant to be used in conjunction with the class
 * decorator (@hasRequest). The cancellation will only occur on
 * disposed unless the cancel param is passed.
 * @param {boolean} cancel - whether to cancel on a new request or not.
 */
export function autoCancelWith(cancel = true) {
	return (target, key, descriptor) => {
		const {get: getter} = descriptor;

		return {
			get() {
				const value = getter
					? getter.apply(this)
					: descriptor.value.bind(this);

				const requestKey = Symbol(key);

				if (!this[requestSymbol]) {
					Object.defineProperty(this, requestSymbol, {
						value: [],
						writable: true
					});
				}

				Object.defineProperty(this, key, {
					configurable: true,
					value: (...args) => {
						const curRequest = get(this, requestKey);

						if (cancel && curRequest && curRequest.cancel) {
							curRequest.cancel();
						}

						const requests = get(this, requestSymbol);

						if (!requests.includes(requestKey)) {
							this[requestSymbol] = requests.concat(requestKey);
						}

						this[requestKey] = value(...args);

						return this[requestKey];
					},
					writable: true
				});

				return this[key];
			}
		};
	};
}

export const autoCancel = autoCancelWith();
