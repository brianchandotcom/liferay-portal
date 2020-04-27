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

import React, {createContext, useContext, useEffect, useState} from 'react';

const PromisesResolverContext = createContext();

function PromisesResolver({children, promises}) {
	const [error, setError] = useState(null);
	const [loading, setLoading] = useState(true);

	useEffect(() => {
		setError(() => null);
		setLoading(() => true);

		Promise.all(promises)
			.then(() => {
				setLoading(() => false);
			})
			.catch((error) => {
				setError(() => error);
			});
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
