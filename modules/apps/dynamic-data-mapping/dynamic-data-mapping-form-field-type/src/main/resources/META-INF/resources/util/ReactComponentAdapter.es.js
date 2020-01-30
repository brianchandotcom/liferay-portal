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
import React from 'react';
import ReactDOM from 'react-dom';

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
		disposed() {
			if (this.instance_) {
				ReactDOM.unmountComponentAtNode(this.instance_);
			}
		}

		render() {
			const {events, ref, store} = this.props;

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
			ReactDOM.render(<ReactComponent {...events} {...store} />, element);

			this.instance_ = element;
		}
	}

	Soy.register(ReactComponentAdapter, templates);

	ReactComponentAdapter.RENDERER = IncrementalDomRenderer;

	return ReactComponentAdapter;
}

export {getConnectedReactComponentAdapter};
export default getConnectedReactComponentAdapter;
