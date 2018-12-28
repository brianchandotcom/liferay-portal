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

			this.on(
				'storeChanged',
				change => {
					const newStore = change.newVal;
					const prevStore = change.prevVal;

					if (newStore !== prevStore) {
						disconnect(this);

						if (newStore instanceof Store) {
							connect(this, newStore);
						}
					}
				}
			);

			this.on(
				'disposed',
				() => {
					disconnect(this);
				}
			);

			if (props.store instanceof Store) {
				connect(this, props.store);
			}
		}

	}

	/**
	 * Connected component state
	 */
	ConnectedComponent.STATE = {
		store: Config
			.instanceOf(Store)
			.value(null)
	};

	properties.forEach(
		property => {
			try {
				ConnectedComponent.STATE[property] = INITIAL_STATE[property].internal();
			}
			catch (e) {
				throw new Error(`${property} is not available fro ${Component.name}`);
			}
		}
	);

	return ConnectedComponent;
};

export {getConnectedComponent};
export default getConnectedComponent;