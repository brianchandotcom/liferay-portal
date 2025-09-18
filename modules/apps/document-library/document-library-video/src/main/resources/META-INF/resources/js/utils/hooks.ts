/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useIsMounted} from '@liferay/frontend-js-react-web';
import {cancelDebounce, debounce} from 'frontend-js-web';
import {useEffect, useMemo, useState} from 'react';

import updateDLVideoFields from './updateDLVideoFields';
import validateUrl from './validateUrl';

function useDebounceCallback<T extends (...args: any[]) => void>(
	callback: T,
	ms: number
): [(...args: Parameters<T>) => void, () => void] {
	const debounced = useMemo(
		() => debounce(callback, ms) as T,
		[callback, ms]
	);

	useEffect(() => {
		return () => cancelDebounce(debounced);
	}, [debounced]);

	return [
		debounced,
		() => {
			cancelDebounce(debounced);
		},
	];
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
			await updateDLVideoFields({
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
