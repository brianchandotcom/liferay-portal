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
import {Config} from 'metal-state';
import React, {useEffect, useState} from 'react';
import ReactDOM from 'react-dom';

import Observer from './Observer.es';

const CONFIG_BLACKLIST = ['children', 'events', 'ref', 'visible'];
const CONFIG_DEFAULT = ['displayErrors'];

/**
 * The Adapter creates a communication bridge between the Metal and React components.
 * The Adapter when it is rendered for the first time uses `ReactDOM.render` to assemble
 * the component and subsequent renderings are done by React. We created a tunnel with
 * an Observer that updates the internal state of the component in React that makes a
 * wrapper over the main component to force React to render at the best time, we also
 * ignore the rendering of Metal.
 *
 * @example
 * // import getConnectedReactComponentAdapter from '/path/ReactComponentAdapter.es';
 * //
 * // const ReactComponent = ({children, className}) => <div className={className}>{children}</div>;
 * // const ReactComponentAdapter = getConnectedReactComponentAdapter(
 * //   ReactComponent
 * // );
 * //
 * // In the rendering of Metal
 * // render() {
 * //	return (
 * //		<ReactComponentAdapter className="h1-title">
 * //			<h1>{'Title'}</h1>
 * //		</ReactComponentAdapter>
 * //	);
 * // }
 *
 * To call the React component in the context of Metal + soy, where varient is not an option,
 * you can use Metal's `Soy.register` to create a fake component so that you can call the React
 * component in Soy. The use of children from Soy components for React does not work.
 *
 * @example
 * // import Soy from 'metal-soy';
 * // import getConnectedReactComponentAdapter from '/path/ReactComponentAdapter.es';
 * // import templates from './FakeAdapter.soy';
 * //
 * // const ReactComponent = ({className}) => <div className={className} />;
 * // const ReactComponentAdapter = getConnectedReactComponentAdapter(
 * //   ReactComponent
 * // );
 * // Soy.register(ReactComponentAdapter, templates);
 * //
 * // In soy
 * // {call FakeAdapter.render}
 * //	{param className: 'test' /}
 * // {/call}
 *
 * @param {React.createElement} ReactComponent
 */

function getConnectedReactComponentAdapter(ReactComponent) {
	class ReactComponentAdapter extends JSXComponent {

		/**
		 * For Metal to track config changes, we need to config the state so
		 * that willReceiveProps is called as expected.
		 */
		constructor(config, parentElement) {
			const props = {};
			Object.keys(config)
				.concat(CONFIG_DEFAULT)
				.forEach((key) => {
					if (!CONFIG_BLACKLIST.includes(key)) {
						props[key] = Config.any().value(config[key]);
					}
				});

			super(config, parentElement);

			const data = JSXComponent.DATA_MANAGER.getManagerData(this);

			data.props_.configState({
				...JSXComponent.DATA,
				...props,
			});
		}

		created() {
			this.observer = new Observer();
			this.reactComponentRef = React.createRef();
		}

		disposed() {
			if (this.instance_) {
				ReactDOM.unmountComponentAtNode(this.instance_);
				this.instance_ = null;
			}
		}

		willReceiveProps(changes) {

			// Delete the events and children properties to make it easier to
			// check which values have been changed, events and children are
			// properties that are changing all the time when new renderings
			// happen, a new reference is created all the time.

			delete changes.events;
			delete changes.children;

			if (changes && Object.keys(changes).length > 0) {
				const newValues = {};
				const keys = Object.keys(changes);

				keys.forEach((key) => {
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

			// eslint-disable-next-line @liferay/portal/no-react-dom-render
			ReactDOM.render(
				<ObserverSubscribe observer={this.observer}>
					<ReactComponent
						{...otherProps}
						{...events}
						{...store}
						instance={this}
						ref={this.reactComponentRef}
					/>
				</ObserverSubscribe>,
				element
			);

			this.instance_ = element;
		}
	}

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
		const change = (value) => setState({...state, ...value});

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
