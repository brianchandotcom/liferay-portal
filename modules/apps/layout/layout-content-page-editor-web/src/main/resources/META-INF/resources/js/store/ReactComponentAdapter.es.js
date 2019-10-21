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

import {ClayIconSpriteContext} from '@clayui/icon';
import {Component} from 'metal-component';
import Soy from 'metal-soy';
import React from 'react';
import ReactDOM from 'react-dom';

import {getConnectedComponent} from './ConnectedComponent.es';
import StateProvider from './StateProvider.es';
import {StoreContext} from './StoreContext.es';

function getConnectedReactComponentAdapter(ReactComponent, templates) {
	class ReactComponentAdapter extends Component {
		disposed() {
			ReactDOM.unmountComponentAtNode(this.refs.app);
		}

		syncStore(store, prevStore) {
			if (store && store !== prevStore) {
				this._mountApp();
			}
		}

		_mountApp() {
			ReactDOM.unmountComponentAtNode(this.refs.app);

			// eslint-disable-next-line liferay-portal/no-react-dom-render
			ReactDOM.render(
				<ClayIconSpriteContext.Provider
					value={this.store.getState().spritemap}
				>
					<StoreContext.Provider value={this.store}>
						<StateProvider>
							<ReactComponent />
						</StateProvider>
					</StoreContext.Provider>
				</ClayIconSpriteContext.Provider>,
				this.refs.app
			);
		}
	}

	const ConnectedReactComponentAdapter = getConnectedComponent(
		ReactComponentAdapter,
		[]
	);

	Soy.register(ConnectedReactComponentAdapter, templates);

	return ConnectedReactComponentAdapter;
}

export {getConnectedReactComponentAdapter};
export default getConnectedReactComponentAdapter;
