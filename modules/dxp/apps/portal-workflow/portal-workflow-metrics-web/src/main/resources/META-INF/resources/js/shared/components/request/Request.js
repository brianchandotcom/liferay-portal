import {Error, ErrorContext, useError} from './Error';
import {Loading, LoadingContext, useLoading} from './Loading';
import React from 'react';
import {Success} from './Success';

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
