/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useRef} from 'react';

const RECT_ATTRS = ['bottom', 'height', 'left', 'right', 'top', 'width'];

const EMPTY_OBJECT = {};

const rectChanged = (a, b = EMPTY_OBJECT) =>
	RECT_ATTRS.some((prop) => a[prop] !== b[prop]);

export function useObserveRect(callback, node) {
	const rafIdRef = useRef();

	const run = useCallback(
		(node, state) => {
			const newRect = node.getBoundingClientRect();

			if (rectChanged(newRect, state.rect)) {
				state.rect = newRect;

				callback(state.rect);
			}

			rafIdRef.current = window.requestAnimationFrame(() =>
				run(node, state)
			);
		},
		[callback]
	);

	useEffect(() => {
		if (node) {
			run(node, {
				hasRectChanged: false,
				rect: undefined,
			});

			return () => {
				if (rafIdRef.current) {
					cancelAnimationFrame(rafIdRef.current);
				}
			};
		}
	}, [node, run]);

	if (!node) {
		return;
	}
}
