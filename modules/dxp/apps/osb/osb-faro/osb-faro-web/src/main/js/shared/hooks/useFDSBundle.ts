import React, {useEffect, useState} from 'react';

type FDSGetAtomArgs = {
	atom?: unknown;
	atomKey?: string;
	fdsName?: string;
};

export type FDSBundle = {
	FrontendDataSet: React.ComponentType<any>;
	getFDSAtom: (args: FDSGetAtomArgs) => unknown;
};

let cachedBundle: FDSBundle | null = null;
let fetchPromise: Promise<FDSBundle> | null = null;

const fetchBundle = (): Promise<FDSBundle> => {
	if (!fetchPromise) {
		fetchPromise = import(
			/* webpackIgnore: true */
			`${window.location.origin}/o/frontend-data-set-web/__liferay__/index.js`
		)
			.then((module: FDSBundle) => {
				cachedBundle = module;

				return module;
			})
			.catch(error => {
				// eslint-disable-next-line no-console
				console.error('Failed to load FrontendDataSet:', error);

				fetchPromise = null;

				throw error;
			});
	}

	return fetchPromise;
};

export const useFDSBundle = (): FDSBundle | null => {
	const [bundle, setBundle] = useState<FDSBundle | null>(() => cachedBundle);

	useEffect(() => {
		let isMounted = true;

		if (!cachedBundle) {
			fetchBundle()
				.then(loaded => {
					if (isMounted) {
						setBundle(loaded);
					}
				})
				.catch(() => {});
		}

		return () => {
			isMounted = false;
		};
	}, []);

	return bundle;
};
