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

import FormRenderer from '../../components/FormRenderer/FormRenderer.es';
import templates from '../../components/FormRenderer/FormRenderer.soy';
import withStore from '../../store/withStore.es';

const Form = withStore(FormRenderer);

Soy.register(Form, templates);

export default Form;
