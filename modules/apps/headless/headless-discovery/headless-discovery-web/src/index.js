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
import ReactDOM from 'react-dom';

import App from './js/App.es';

/* Bug with SwaggerUI: https://github.com/agoncal/swagger-ui-angular6/issues/2 */
/* eslint-disable-next-line no-undef */
window.Buffer = window.Buffer || require('buffer').Buffer;

ReactDOM.render(<App />, document.getElementById('container'));
