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

import IncrementalDomRenderer from 'metal-incremental-dom';
import JSXComponent from 'metal-jsx';
import Soy from 'metal-soy';
import {Config} from 'metal-state';
import React, {useEffect, useState} from 'react';
import ReactDOM from 'react-dom';

import Observer from './Observer.es';

const CONFIG_BLACKLIST = ['children', 'events', 'key', 'ref', 'visible'];

/**
 * The Adapter needs the React component instance to render and the soy template
 * is only used to bridge when a Metal+soy component calls the Metal+jsx component
 * inside Soy Templates, the rest ReactComponentAdapter will take care of render.
 * @example
 * // import getConnectedReactComponentAdapter from '/path/ReactComponentAdapter.es';
 * // import templates from './ComponentNameAdapter.soy';
 * //
 * // const DatePickerWithState = ({dispatch}) => (
 * // 	<ClayDatePicker {...} />
 * // );
 * //
 * // const ReactDatePickerAdapter = getConnectedReactComponentAdapter(
 * // 	DatePickerWithState,
 * // 	templates
 * // );
 * `ReactComponentAdapter` will pass the `events` and `store` properties to the
 * React component.
 * @example
 * // {call ReactDatePickerAdapter.render}
 * // 	{param events: ['dispatch': $_handleOnDispatch] /}
 * //	{param store: ['value': $value, 'name': $name] /}
 * // {/call}
 * @example
 * // const DatePickerWithState = ({
 * // 	dispatch,
 * // 	value,
 * // 	name,
 * // }) => (
 * // 	<ClayDatePicker
 * //		value={value}
 * //		onValueChange={newValue => dispatch({type: 'value', payload: newValue})}
 * // 	/>
 * // );
 * @param {React.createElement} ReactComponent
 * @param {Soy} templates
 */

function getConnectedReactComponentAdapter(ReactComponent, templates) {
	class ReactComponentAdapter extends JSXComponent {
		/**
		 * For Metal to track config changes, we need to declare the
		 * configs in the static property so that willReceiveProps is
		 * called as expected. We added the configs before Metal started
		 * configuring so that they are recognized later.
		 */
		constructor(config, parentElement) {
			const props = {};
			Object.keys(config).forEach(key => {
				if (!CONFIG_BLACKLIST.includes(key)) {
					props[key] = Config.any();
				}
			});

			ReactComponentAdapter.PROPS = props;

			super(config, parentElement);
		}

		created() {
			this.observer = new Observer();
		}

		disposed() {
			if (this.instance_) {
				ReactDOM.unmountComponentAtNode(this.instance_);
				this.instance_ = null;
			}
		}

		willReceiveProps(changes) {
			if (
				changes &&
				changes.events &&
				changes.children &&
				Object.keys(changes).length > 2
			) {
				const newValues = {};
				const keys = Object.keys(changes);

				keys.forEach(key => {
					if (!CONFIG_BLACKLIST.includes(key)) {
						newValues[key] = changes[key].newVal;
					}
				});

				this.observer.dispatch(newValues);
			}
		}

		/**
		 * Disable Metal rendering and let React render in the best
		 * possible way.
		 */
		shouldUpdate() {
			return false;
		}

		syncVisible(value) {
			this.observer.dispatch({visible: value});
		}

		render() {
			const {events, ref, store, ...otherProps} = this.props;

			/* eslint-disable no-undef */
			IncrementalDOM.elementOpen(
				'div',
				ref,
				[],
				'class',
				'react-component-adapter'
			);
			const element = IncrementalDOM.currentElement();
			IncrementalDOM.skip();
			IncrementalDOM.elementClose('div');
			/* eslint-enable no-undef */

			// eslint-disable-next-line liferay-portal/no-react-dom-render
			ReactDOM.render(
				<ObserverSubscribe observer={this.observer}>
					<ReactComponent
						{...otherProps}
						{...events}
						{...store}
						instance={this}
					/>
				</ObserverSubscribe>,
				element
			);

			this.instance_ = element;
		}
	}

	Soy.register(ReactComponentAdapter, templates);

	ReactComponentAdapter.RENDERER = IncrementalDomRenderer;

	return ReactComponentAdapter;
}

/**
 * Adds a sub observer to maintain the updated state of the
 * component.
 */
const ObserverSubscribe = ({children, observer}) => {
	const [state, setState] = useState({});

	useEffect(() => {
		const change = value => setState({...state, ...value});

		observer.subscribe(change);

		return () => {
			observer.unsubscribe(change);
		};
	}, [state, setState, observer]);

	return React.cloneElement(children, {
		...children.props,
		...state,
	});
};

export {getConnectedReactComponentAdapter};
export default getConnectedReactComponentAdapter;
