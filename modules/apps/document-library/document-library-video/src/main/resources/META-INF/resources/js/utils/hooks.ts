/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useIsMounted} from '@liferay/frontend-js-react-web';
import {cancelDebounce, debounce} from 'frontend-js-web';
import {useEffect, useRef, useState} from 'react';

import updateDLVideoFields from './updateDLVideoFields';
import validateUrl from './validateUrl';

function useDebounceCallback<T extends (...args: any[]) => any>(
	callback: T,
	milliseconds: number
): [T, () => void] {
	const callbackRef = useRef(debounce(callback, milliseconds) as T);

	return [callbackRef.current, () => cancelDebounce(callbackRef.current)];
}

export function useDLVideoExternalShortcutFields({
	getDLVideoExternalShortcutFieldsURL,
	namespace,
	url,
}: {
	getDLVideoExternalShortcutFieldsURL: string;
	namespace: string;
	url: string;
}) {
	const [error, setError] = useState<string | null>(null);
	const [fields, setFields] = useState<Record<string, any> | null>(null);
	const [loading, setLoading] = useState(false);
	const isMounted = useIsMounted();

	const [getFields] = useDebounceCallback(
		async (dlVideoExternalShortcutURL: string) => {
			updateDLVideoFields({
				getVideoFieldsURL: getDLVideoExternalShortcutFieldsURL,
				namespace,
				onError: () => {
					if (isMounted()) {
						setLoading(false);
						setFields(null);
						setError(
							Liferay.Language.get(
								'sorry,-this-platform-is-not-supported'
							)
						);
					}
				},
				onUpdate: (fields) => {
					if (isMounted()) {
						setLoading(false);
						setFields(fields);
					}
				},
				videoURL: dlVideoExternalShortcutURL,
			});
		},
		500
	);

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
