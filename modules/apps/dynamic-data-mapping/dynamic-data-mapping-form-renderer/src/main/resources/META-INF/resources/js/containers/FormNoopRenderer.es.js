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

import FormRenderer from '../components/FormRenderer/FormRenderer.es';
import {FormNoopProvider} from '../hooks/useForm.es';

/**
 * It is an implementation of FormRenderer no-op, it just renders the
 * layout and propagates any event to the instance `dispatch`.
 */
export const FormNoopRenderer = React.forwardRef(
	({instance, ...otherProps}, ref) => (
		<FormNoopProvider
			onEvent={(type, payload) =>
				instance?.context.dispatch(type, payload)
			}
		>
			<FormRenderer {...otherProps} ref={ref} />
		</FormNoopProvider>
	)
);

FormNoopRenderer.displayName = 'FormNoopRenderer';
