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

import {Config} from 'metal-state';

import {connect, disconnect, Store} from './store.es';
import INITIAL_STATE from './state.es';

/**
 * HOC that returns a component that connects automatically
 * to a Store parameter.
 * @param {Component} Component
 * @param {string[]} [properties=[]] List of properties to be fetched from store
 * @return {Component}
 */
const getConnectedComponent = (Component, properties) => {
	/**
	 * ConnectedComponent
	 */
	class ConnectedComponent extends Component {
		/**
		 * @inheritdoc
		 * @param {object} props
		 * @param  {...any} ...args
		 */
		constructor(props, ...args) {
			super(props, ...args);

			this.on('storeChanged', change => {
				const newStore = change.newVal;
				const prevStore = change.prevVal;

				if (newStore !== prevStore) {
					disconnect(this);

					if (newStore instanceof Store) {
						connect(
							this,
							newStore
						);
					}
				}
			});

			this.on('disposed', () => {
				disconnect(this);
			});

			if (props.store instanceof Store) {
				connect(
					this,
					props.store
				);
			}
		}
	}

	/**
	 * Connected component state
	 */
	ConnectedComponent.STATE = {
		store: Config.instanceOf(Store).value(null)
	};

	properties.forEach(property => {
		try {
			ConnectedComponent.STATE[property] = INITIAL_STATE[
				property
			].internal();
		} catch (e) {
			throw new Error(
				`${property} is not available from ${Component.name}`
			);
		}
	});

	return ConnectedComponent;
};

export {getConnectedComponent};
export default getConnectedComponent;
