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
import Soy from 'metal-soy';
import React, {forwardRef} from 'react';

import {PageProvider} from '../../hooks/usePage.es';
import {useFieldTypesResource} from '../../hooks/useResource.es';
import {getConnectedReactComponentAdapter} from '../../util/ReactComponentAdapter.es';
import {Field} from './Field.es';
import templates from './ReactFieldAdapter.soy';

/**
 * This creates a compatibility layer for the Field component on React, allowing
 * it to be called by Metal+Soy file.
 *
 * This component creates a mask for the Field component, some DDM fields access
 * the `usePage` and we need to bring the `usePage` here since this component
 * will not have a React context above it.
 */
export const ReactFieldAdapter = forwardRef(
	({fieldType, instance, ...field}) => {
		const {resource: fieldTypes} = useFieldTypesResource();

		if (!fieldType || fieldType === '') {
			return null;
		}

		const emit = (name, event) => {
			instance.emit(name, {
				...event,
				fieldInstance: {
					...event.fieldInstance,
					element: instance.element,
				},
			});
		};

		return (
			<PageProvider value={{...field, fieldTypes}}>
				<ClayIconSpriteContext.Provider value={field.spritemap}>
					<Field
						field={{
							...field,
							type: fieldType,
						}}
						onBlur={(event) => emit('fieldBlurred', event)}
						onChange={(event) => emit('fieldEdited', event)}
						onFocus={(event) => emit('fieldFocused', event)}
					/>
				</ClayIconSpriteContext.Provider>
			</PageProvider>
		);
	}
);

const ReactComponentAdapter = getConnectedReactComponentAdapter(
	ReactFieldAdapter
);

Soy.register(ReactComponentAdapter, templates);

export default ReactComponentAdapter;
