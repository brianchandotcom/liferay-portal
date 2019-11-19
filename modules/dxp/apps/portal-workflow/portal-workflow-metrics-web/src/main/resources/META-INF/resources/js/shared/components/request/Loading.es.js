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

import React, {useState, createContext, useContext} from 'react';

import LoadingState from '../loading/LoadingState.es';

function useLoading() {
	const [loading, setLoading] = useState(false);

	return {
		loading,
		setLoading
	};
}

const LoadingContext = createContext(false);

function Loading({children}) {
	const {loading} = useContext(LoadingContext);

	return loading && (children || <LoadingState className="pb-6 pt-5" />);
}

export {Loading, LoadingContext, useLoading};
