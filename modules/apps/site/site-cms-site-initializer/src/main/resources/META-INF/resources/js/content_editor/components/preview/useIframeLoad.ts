/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useLayoutEffect, useRef, useState} from 'react';

// An iframe embed can fail silently — no JS error fires. We infer failure
// from `onLoad` timing:
//
// - Header block (X-Frame-Options, frame-ancestors): the browser shows
//   `chrome-error://` as soon as response headers arrive and fires `onLoad`
//   under 600 ms — well below a real page, which must also load body and
//   subresources.
// - Unreachable URL (dead DNS, no response): `onLoad` never fires; the
//   timeout catches it.

const IFRAME_LOAD_BLOCKED_MS = 600;
const IFRAME_LOAD_TIMEOUT_MS = 8000;

export default function useIframeLoad(
	previewURL: string | undefined,
	isExternalURL: boolean
) {
	const [iframeError, setIframeError] = useState<boolean>(false);
	const [iframeKey, setIframeKey] = useState<number>(0);
	const loadStartRef = useRef<number>(0);
	const loadTimeoutRef = useRef<ReturnType<typeof setTimeout> | null>(null);

	const clearLoadTimeout = () => {
		if (loadTimeoutRef.current === null) {
			return;
		}

		clearTimeout(loadTimeoutRef.current);

		loadTimeoutRef.current = null;
	};

	useLayoutEffect(() => {
		setIframeError(false);

		if (!previewURL) {
			return;
		}

		loadStartRef.current = performance.now();

		loadTimeoutRef.current = setTimeout(() => {
			setIframeError(true);
		}, IFRAME_LOAD_TIMEOUT_MS);

		return clearLoadTimeout;
	}, [iframeKey, previewURL]);

	const handleIframeLoad = () => {
		clearLoadTimeout();

		if (!isExternalURL) {
			return;
		}

		const loadTime = performance.now() - loadStartRef.current;

		if (loadTime < IFRAME_LOAD_BLOCKED_MS) {
			setIframeError(true);
		}
	};

	const reloadIframe = () => {
		setIframeKey((key) => key + 1);
	};

	return {handleIframeLoad, iframeError, iframeKey, reloadIframe};
}
