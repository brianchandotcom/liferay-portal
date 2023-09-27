/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useIsMounted} from '@liferay/frontend-js-react-web';
import {addParams, cancelDebounce, debounce, fetch} from 'frontend-js-web';
import {useEffect, useRef, useState} from 'react';

import validateUrl from './validateUrl';

function useDebounceCallback(callback, milliseconds) {
	const callbackRef = useRef(debounce(callback, milliseconds));

	return [callbackRef.current, () => cancelDebounce(callbackRef.current)];
}

export function useDLVideoExternalShortcutFields({
	getDLVideoExternalShortcutFieldsURL,
	namespace,
	url,
}) {
	const [error, setError] = useState('');
	const [fields, setFields] = useState(null);
	const [loading, setLoading] = useState(false);
	const isMounted = useIsMounted();

	const [getFields] = useDebounceCallback((dlVideoExternalShortcutURL) => {
		fetch(
			addParams(
				{
					[`${namespace}dlVideoExternalShortcutURL`]: dlVideoExternalShortcutURL,
				},
				getDLVideoExternalShortcutFieldsURL
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
}
