/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useRef} from 'react';

const rectAttrs: Array<keyof DOMRect> = [
	'bottom',
	'height',
	'left',
	'right',
	'top',
	'width',
];

interface IRectState {
	hasRectChanged: boolean;
	rect: DOMRect | undefined;
}

const DOMRectStub = {} as DOMRect;

const rectChanged = (a: DOMRect, b: DOMRect = DOMRectStub) =>
	rectAttrs.some((prop) => a[prop] !== b[prop]);

export function useObserveRect(
	callback: (rect: DOMRect | undefined) => void,
	node: Element | null
) {
	const rafIdRef = useRef<number>();

	const run = useCallback(
		(node: Element, state: IRectState) => {
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
}
