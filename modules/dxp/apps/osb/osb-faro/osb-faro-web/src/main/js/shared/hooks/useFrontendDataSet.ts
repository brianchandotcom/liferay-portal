import React, {useEffect, useState} from 'react';

let cachedComponent: React.ComponentType<any> | null = null;
let fetchPromise: Promise<React.ComponentType<any>> | null = null;

export const useFrontendDataSet = (): React.ComponentType<any> | null => {
	const [dataSet, setDataSet] = useState<React.ComponentType<any> | null>(
		() => cachedComponent
	);

	useEffect(() => {
		let isMounted = true;

		const fetchFDS = async () => {
			try {
				if (!fetchPromise) {
					fetchPromise = import(
						/* webpackIgnore: true */
						`${window.location.origin}/o/frontend-data-set-web/__liferay__/index.js`
					).then(module => {
						cachedComponent = module.FrontendDataSet;

						return cachedComponent as React.ComponentType<any>;
					});
				}

				const Component = await fetchPromise;

				if (isMounted) {
					setDataSet(() => Component as any);
				}
			} catch (error) {
				// eslint-disable-next-line no-console
				console.error('Failed to load FrontendDataSet:', error);

				fetchPromise = null;
			}
		};

		if (!cachedComponent) {
			fetchFDS();
		}

		return () => {
			isMounted = false;
		};
	}, []);

	return dataSet;
};
