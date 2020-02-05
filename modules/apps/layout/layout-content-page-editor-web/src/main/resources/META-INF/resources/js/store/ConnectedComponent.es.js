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
import React, {useContext, useEffect, useState} from 'react';

import StoreContext from './StoreContext.es';
import INITIAL_STATE from './state.es';
import {connect, disconnect, Store} from './store.es';

/**
 * HOC that returns a component that connects automatically
 * to a Store parameter.
 * @param {Component} Component
 * @param {string[]} [properties=[]] List of properties to be fetched from store
 * @param {function} [mapStateToProps] Function to map store state to
 *  component props
 * @return {Component}
 */
const getConnectedComponent = (Component, properties, mapStateToProps) => {
	const defaultMapStateToProps = (state, props) => {
		const mappedProps = {...props};

		properties.forEach(property => {
			mappedProps[property] = state[property];
		});

		return mappedProps;
	};

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
			let syncedProps = props;

			if (syncedProps.store instanceof Store) {
				syncedProps = {
					...syncedProps,
					...(mapStateToProps || defaultMapStateToProps)(
						syncedProps.store.getState(),
						syncedProps
					)
				};
			}

			super(syncedProps, ...args);

			this.on('storeChanged', change => {
				const newStore = change.newVal;
				const prevStore = change.prevVal;

				if (newStore !== prevStore) {
					disconnect(this);

					if (newStore instanceof Store) {
						connect(
							this,
							newStore,
							mapStateToProps || defaultMapStateToProps
						);
					}
				}
			});

			this.on('disposed', () => {
				disconnect(this);
			});

			if (syncedProps.store instanceof Store) {
				connect(
					this,
					syncedProps.store,
					mapStateToProps || defaultMapStateToProps
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
		}
		catch (e) {
			throw new Error(
				`${property} is not available from ${Component.name}`
			);
		}
	});

	return ConnectedComponent;
};

/**
 * Second order function to produce a Connected Component Wrapper
 * @param {Function} mapStateToProps - Recieves the state and returns mapped version of it ready for consumption by the Wrapped Component
 * @param {Function} mapDispatchToProps - Recieves the dispatch and returns a set of component props that use it to call the modify the state tree
 * @returns {object}
 */
function getConnectedReactComponent(mapStateToProps, mapDispatchToProps) {
	/**
	 *
	 * @param {React.Component} WrappedComponent - The Component to connect to the store
	 * @returns {React.Component} - Wrapper Connected Component that propagates every store change mapped to the component
	 */
	return function _getConnectedWrapperComponent(WrappedComponent) {
		return function ConnectedWrapperComponent(props) {
			const store = useContext(StoreContext);

			const [storeState, setStoreState] = useState(
				store ? store.getState() : {}
			);

			useEffect(() => {
				if (store) {
					const handleStoreChange = () =>
						setStoreState(store.getState());
					const subscriber = store.on('change', handleStoreChange);

					return () => subscriber.removeListener();
				}
			}, [store]);

			return store ? (
				<WrappedComponent
					{...props}
					{...mapStateToProps(storeState, props)}
					{...mapDispatchToProps(store.dispatch, props)}
				/>
			) : null;
		};
	};
}

export {getConnectedComponent, getConnectedReactComponent};
export default getConnectedComponent;
