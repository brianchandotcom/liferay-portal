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

import React from 'react';

/**
 * The proxy combines the responsibilities of the withDispatch utilities to
 * maintain compatibility with the use of FieldBase in other fields in React
 * and isolate connection to the Metal store.
 * @param {MetalComponentInstance} object.instance
 */
export const connectStore = Component => {
	return function WithDispatch({instance, ...otherProps}) {
		const {context} = instance;

		const dispatch = (...args) =>
			(context.dispatch || instance.emit)(...args);

		const store = context.store || {};

		const emit = (name, event, value) =>
			instance.emit(name, {
				// A hacky to imitate an instance of a Metal+soy component
				fieldInstance: {
					...instance,
					...instance.props,

					// Explicitly declare the element, because it will get lost
					// in the destructuring above because the element is a getter.
					element: instance.element,
					isDisposed: instance.isDisposed,
				},
				originalEvent: event,
				value,
			});

		return (
			<Component
				{...otherProps}
				dispatch={dispatch}
				emit={emit}
				store={store}
			/>
		);
	};
};
