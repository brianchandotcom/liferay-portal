/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import _axios from 'axios';

import {Liferay} from '../../utils/liferay';

const {REACT_APP_LIFERAY_API = window.location.origin} = process.env;

const axios = _axios.create({
	baseURL: REACT_APP_LIFERAY_API,
	headers: {
		'x-csrf-token': Liferay.authToken,
	},
});

export {axios};
