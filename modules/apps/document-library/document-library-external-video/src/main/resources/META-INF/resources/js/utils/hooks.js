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

import {useIsMounted} from 'frontend-js-react-web';
import {addParams, cancelDebounce, debounce, fetch} from 'frontend-js-web';
import {useEffect, useRef, useState} from 'react';

import validateUrl from './validateUrl';

function useDebounceCallback(callback, milliseconds) {
	const callbackRef = useRef(debounce(callback, milliseconds));

	return [callbackRef.current, () => cancelDebounce(callbackRef.current)];
}

export const useDLExternalVideoFields = ({
	getDLExternalVideoFieldsURL,
	namespace,
	url,
}) => {
	const [error, setError] = useState('');
	const [fields, setFields] = useState(null);
	const [loading, setLoading] = useState(false);
	const isMounted = useIsMounted();

	const [getFields] = useDebounceCallback((dlExternalVideoURL) => {
		fetch(
			addParams(
				{
					[`${namespace}dlExternalVideoURL`]: dlExternalVideoURL,
				},
				getDLExternalVideoFieldsURL
			)
		)
			.then((res) => res.json())
			.then((fields) => {
				if (isMounted()) {
					setLoading(false);
					setFields(fields);
				}
			})
			.catch(() => {
				if (isMounted()) {
					setLoading(false);
					setFields(null);
					setError(
						Liferay.Language.get(
							'sorry,-this-platform-is-not-supported'
						)
					);
				}
			});
	}, 500);

	useEffect(() => {
		setError(null);

		if (url && validateUrl(url)) {
			setLoading(true);
			getFields(url);
		}
		else {
			setLoading(false);
			setFields(null);
		}
	}, [getFields, url]);

	return {error, fields, loading};
};
