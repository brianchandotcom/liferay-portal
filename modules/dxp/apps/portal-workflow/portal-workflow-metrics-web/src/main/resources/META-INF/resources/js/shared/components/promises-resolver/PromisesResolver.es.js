/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {createContext, useContext, useEffect, useState} from 'react';

const PromisesResolverContext = createContext();

function PromisesResolver({children, promises}) {
	const [error, setError] = useState(null);
	const [loading, setLoading] = useState(true);

	useEffect(() => {
		setError(null);
		setLoading(true);

		Promise.all(promises)
			.then(() => setLoading(false))
			.catch(setError);
	}, [promises]);

	return (
		<PromisesResolverContext.Provider value={{error, loading}}>
			{children}
		</PromisesResolverContext.Provider>
	);
}

const usePromisesResolverContext = () => {
	const promisesResolverContext = useContext(PromisesResolverContext);

	if (!promisesResolverContext) {
		return {};
	}

	return promisesResolverContext;
};

const Pending = ({children}) => {
	const {error, loading} = usePromisesResolverContext();

	return !error && loading ? children : null;
};

const Rejected = ({children}) => {
	const {error} = usePromisesResolverContext();

	return error ? children : null;
};

const Resolved = ({children}) => {
	const {error, loading} = usePromisesResolverContext();

	return !error && !loading ? children : null;
};

PromisesResolver.Pending = Pending;
PromisesResolver.Rejected = Rejected;
PromisesResolver.Resolved = Resolved;

export default PromisesResolver;
export {PromisesResolverContext};
