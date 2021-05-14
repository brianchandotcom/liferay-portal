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

import './A11y.scss';

import ClayIcon from '@clayui/icon';
import ClayList from '@clayui/list';
import ClayPopover from '@clayui/popover';
import React, {useCallback, useEffect, useMemo, useRef, useState} from 'react';
import ReactDOM from 'react-dom';

import useA11y from './useA11y';

import type {A11yCheckerOptions} from './A11yChecker';
import type {Violation as TViolation} from './useA11y';

declare var Liferay: {
	Language: {
		get(value: string): string;
	};
};

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

const useObserveRect = (
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

const Overlay = React.forwardRef<
	HTMLDivElement,
	React.ButtonHTMLAttributes<HTMLDivElement>
>(({style, ...othersProps}, ref) =>
	ReactDOM.createPortal(
		<div {...othersProps} className="a11y-overlay" ref={ref} style={style}>
			<div className="a11y-indicator">
				<ClayIcon symbol="info-circle" />
			</div>
			<div className="a11y-backdrop" />
		</div>,
		document.body
	)
);

type ViolationProps = {
	modifyIndex: number;
	target: string;
	violations: Array<TViolation>;
};

function Violation({target, violations}: ViolationProps) {
	const [visible, setVisible] = useState(false);
	const [bounds, setBounds] = useState<React.CSSProperties>();

	const node = useMemo(() => document.querySelector(target), [target]);

	useObserveRect(
		useCallback(
			(bounds) => {
				bounds = bounds ?? (node as Element).getBoundingClientRect();

				setBounds({
					height: bounds.height,
					left: bounds.left,
					top: bounds.top,
					width: bounds.width,
				});
			},
			[node]
		),
		node
	);

	return (
		<ClayPopover
			className="a11y-popover"
			header={
				<>
					<div className="inline-item">
						<ClayIcon
							className="text-danger"
							symbol="info-circle"
						/>
					</div>
					<div className="inline-item inline-item-after">
						<span>
							{Liferay.Language.get('accessibility-violation')}
						</span>
					</div>
				</>
			}
			onShowChange={setVisible}
			show={visible}
			trigger={<Overlay style={bounds} />}
		>
			<div className="list-group">
				{violations.map(({help, id, impact}) => (
					<button
						className="list-group-item list-group-item-action list-group-item-flex list-group-item-flush"
						key={id}
					>
						<ClayList.ItemField expand>
							<ClayList.ItemTitle>
								{id}{' '}
								<span className="text-secondary">{`- ${impact}`}</span>
							</ClayList.ItemTitle>
							<ClayList.ItemText subtext>
								{help}
							</ClayList.ItemText>
						</ClayList.ItemField>
						<ClayList.ItemField>
							<ClayIcon
								className="text-secondary"
								symbol="angle-right"
							/>
						</ClayList.ItemField>
					</button>
				))}
			</div>
		</ClayPopover>
	);
}

export function A11y(props: Omit<A11yCheckerOptions, 'callback'>) {
	const violations = useA11y(props);

	if (violations) {
		return violations.map(({target, ...otherProps}, index) => (
			<Violation
				key={`${target}:${index}`}
				target={target}
				{...otherProps}
			/>
		));
	}

	return null;
}
