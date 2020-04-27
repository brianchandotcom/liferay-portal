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

import {useRef} from 'react';

const useCancellablePromises = () => {
	const pendingPromises = useRef([]);

	const append = (promise) => {
		pendingPromises.current = [...pendingPromises.current, promise];
	};

	const remove = (promise) => {
		pendingPromises.current = pendingPromises.current.filter(
			(p) => p !== promise
		);
	};

	const clear = () => {
		pendingPromises.current.map((p) => p.cancel());
	};

	return {
		append,
		clear,
		remove,
	};
};

const cancellablePromise = (promise) => {
	let isCanceled = false;

	const wrappedPromise = new Promise((resolve, reject) => {
		promise.then(
			(value) =>
				isCanceled ? reject({isCanceled, value}) : resolve(value),
			(error) => reject({error, isCanceled})
		);
	});

	return {
		cancel: () => {
			isCanceled = true;
		},
		promise: wrappedPromise,
	};
};

export default (onClick, onDoubleClick) => {
	const promises = useCancellablePromises();

	const handleOnClick = (event) => {
		promises.clear();

		const waitForClick = cancellablePromise(
			new Promise((resolve) => setTimeout(resolve, 300))
		);

		promises.append(waitForClick);

		return waitForClick.promise
			.then(() => {
				promises.remove(waitForClick);
				onClick(event);
			})
			.catch((e) => {
				promises.remove(waitForClick);

				if (!e.isCanceled) {
					throw e.error;
				}
			});
	};

	const handleOnDoubleClick = (event) => {
		promises.clear();
		onDoubleClick(event);
	};

	return [handleOnClick, handleOnDoubleClick];
};
