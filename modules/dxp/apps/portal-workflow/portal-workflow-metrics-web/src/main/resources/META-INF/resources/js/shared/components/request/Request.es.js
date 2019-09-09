/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {Error, ErrorContext, useError} from './Error.es';
import {Loading, LoadingContext, useLoading} from './Loading.es';
import React from 'react';
import {Success} from './Success.es';

export default function Request({children}) {
	return (
		<LoadingContext.Provider value={useLoading()}>
			<ErrorContext.Provider value={useError()}>
				{children}
			</ErrorContext.Provider>
		</LoadingContext.Provider>
	);
}

Request.Error = Error;
Request.Loading = Loading;
Request.Success = Success;
