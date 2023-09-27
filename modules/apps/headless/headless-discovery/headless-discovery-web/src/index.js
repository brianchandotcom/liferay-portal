/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import ReactDOM from 'react-dom';

import App from './js/App.es';

/* Bug with SwaggerUI: https://github.com/agoncal/swagger-ui-angular6/issues/2 */
/* eslint-disable-next-line no-undef */
window.Buffer = window.Buffer || require('buffer').Buffer;

ReactDOM.render(<App />, document.getElementById('container'));
