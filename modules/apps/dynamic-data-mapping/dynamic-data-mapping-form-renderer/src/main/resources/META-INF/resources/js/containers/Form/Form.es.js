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

import Soy from 'metal-soy';
import React from 'react';

import FormRenderer from '../../components/FormRenderer/FormRenderer.es';
import {FormProvider} from '../../hooks/useForm.es';
import withStore from '../../store/withStore.es';
import {getConnectedReactComponentAdapter} from '../../util/ReactComponentAdapter.es';
import templates from './index.soy';

const FormProxy = ({instance, ...otherProps}) => (
	<FormProvider on={(type, payload) => instance.emit(type, payload)}>
		<FormRenderer {...otherProps} />
	</FormProvider>
);

const ReactFormAdapter = withStore(
	getConnectedReactComponentAdapter(FormProxy)
);

Soy.register(ReactFormAdapter, templates);

export default ReactFormAdapter;
