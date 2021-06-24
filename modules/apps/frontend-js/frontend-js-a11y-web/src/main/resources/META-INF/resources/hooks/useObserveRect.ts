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
	rect: DOMRect | undefined;
	hasRectChanged: boolean;
}

const DOMRectStub = {} as DOMRect;

const rectChanged = (a: DOMRect, b: DOMRect = DOMRectStub) =>
	rectAttrs.some((prop) => a[prop] !== b[prop]);

export const useObserveRect = (
	callback: (rect: DOMRect | undefined) => void,
	node: Element | null
) => {
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
};
