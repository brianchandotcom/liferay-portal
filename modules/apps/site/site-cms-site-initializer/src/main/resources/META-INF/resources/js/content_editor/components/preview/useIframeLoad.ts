/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {sessionStorage} from 'frontend-js-web';
import {useEffect, useRef, useState} from 'react';

import {PREVIEW_CACHED_EXTERNAL_URL_SESSION_KEY} from './sessionKeys';

// Iframes can fail silently (no JS error fires). We detect failures via
// `onLoad` timing: header-blocked iframes (X-Frame-Options) fire `onLoad`
// in <600ms with `chrome-error://`; unreachable URLs never fire `onLoad`,
// so we also need a timeout.

const IFRAME_LOAD_BLOCKED_MS = 600;
const IFRAME_LOAD_TIMEOUT_MS = 8000;

type Status = 'error' | 'idle' | 'loaded' | 'loading';

export default function useIframeLoad(
	previewURL: string | undefined,
	isExternalURL: boolean
) {
	const [status, setStatus] = useState<Status>('idle');
	const [iframeKey, setIframeKey] = useState<number>(0);

	const cachedURL = sessionStorage.getItem(
		PREVIEW_CACHED_EXTERNAL_URL_SESSION_KEY,
		sessionStorage.TYPES.NECESSARY
	);

	const cachedURLsRef = useRef<Set<string>>(
		new Set(cachedURL ? [cachedURL] : [])
	);
	const loadStartRef = useRef<number>(0);

	useEffect(() => {
		if (!previewURL) {
			setStatus('idle');

			return;
		}

		setStatus('loading');

		loadStartRef.current = performance.now();

		const timeoutId = setTimeout(() => {
			setStatus((current) => (current === 'loading' ? 'error' : current));
		}, IFRAME_LOAD_TIMEOUT_MS);

		return () => clearTimeout(timeoutId);
	}, [iframeKey, previewURL]);

	const handleIframeLoad = () => {
		if (isExternalURL && previewURL) {
			const loadTime = performance.now() - loadStartRef.current;

			if (loadTime >= IFRAME_LOAD_BLOCKED_MS) {
				cachedURLsRef.current.add(previewURL);

				sessionStorage.setItem(
					PREVIEW_CACHED_EXTERNAL_URL_SESSION_KEY,
					previewURL,
					sessionStorage.TYPES.NECESSARY
				);
			}
			else if (!cachedURLsRef.current.has(previewURL)) {
				setStatus('error');

				return;
			}
		}

		setStatus((current) => (current === 'loading' ? 'loaded' : current));
	};

	const reloadIframe = () => {
		setIframeKey((key) => key + 1);
	};

	return {
		handleIframeLoad,
		iframeError: status === 'error',
		iframeKey,
		isIframeLoading: status === 'loading',
		reloadIframe,
	};
}
