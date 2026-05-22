/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {observeRect, useOverlayPosition} from '@clayui/shared';
import classNames from 'classnames';
import React, {useEffect, useRef, useState} from 'react';

import type {AlignPoints} from '@clayui/shared';

export type Direction = 'all' | 'horizontal' | 'vertical';

export type Placement = 'center' | 'tooltip';

export type Props = {

	/**
	 * When provided, the indicator positions itself relative to the
	 * referenced element. See `placement` for the supported anchoring
	 * modes. When omitted, the indicator renders in normal flow.
	 */
	anchorRef?: React.RefObject<HTMLElement>;

	/**
	 * Optional `class` for the root element.
	 */
	className?: string;

	/**
	 * Which arrow keys are active for navigation. Selects the matching
	 * state class (`clay-keyboard-arrows-all`,
	 * `clay-keyboard-arrows-horizontal`, or `clay-keyboard-arrows-vertical`)
	 * on the wrapper, which CSS uses to mute the inactive keycap paths
	 * inside the single `arrows-all` icon.
	 */
	direction: Direction;

	/**
	 * How the indicator is positioned relative to `anchorRef`. `tooltip`
	 * (default) places it alongside the anchor with a directional arrow
	 * that flips to fit the viewport. `center` overlays the indicator on
	 * the anchor's center with no arrow — useful for narrow interactive
	 * elements such as a resize handle, where the indicator should mark
	 * the surface itself rather than point at it.
	 */
	placement?: Placement;

	/**
	 * Path to the Clay icon spritemap. Optional when `ClayIconSpriteContext`
	 * is provided by an ancestor `Provider`.
	 */
	spritemap?: string;
} & Omit<React.HTMLAttributes<HTMLDivElement>, 'aria-hidden' | 'role'>;

// `alignmentPosition: 2` is `RightCenter` — anchor's center-right aligns
// to the indicator's center-left. `autoBestAlign` flips to `LeftCenter`
// when the indicator would overflow on the right; `dom-align` negates
// the horizontal offset internally during that flip, so a single
// `getOffset` value drives both sides. The default 4px gap from the
// shared offset map is too tight for triggers that render a focus ring
// around their bounding box, so the offset is widened.

const TOOLTIP_ALIGNMENT_POSITION = 2;

const TOOLTIP_OFFSET = 12;

// `['cc', 'cc']` aligns the indicator's center to the anchor's center
// for the overlay-style placement used by narrow interactive elements
// like the resize handle. `dom-align` accepts arbitrary point strings
// at runtime, but the shared `AlignPoints` type only enumerates the
// entries in `ALIGN_MAP`, so the array is cast at the call site.

const CENTER_ALIGNMENT_POINTS = ['cc', 'cc'] as const;

const NO_OFFSET: [number, number] = [0, 0];

function getTooltipOffset(): [number, number] {
	return [TOOLTIP_OFFSET, 0];
}

function getCenterOffset(): [number, number] {
	return NO_OFFSET;
}

export function KeyboardArrowsIndicator({
	anchorRef,
	className,
	direction,
	placement = 'tooltip',
	spritemap,
	...otherProps
}: Props) {
	const indicatorRef = useRef<HTMLDivElement>(null);
	const fallbackTriggerRef = useRef<HTMLDivElement>(null);

	const [flipped, setFlipped] = useState(false);
	const [hasFocusWithin, setHasFocusWithin] = useState(false);

	const isTooltip = placement === 'tooltip';

	useOverlayPosition(
		{
			alignmentByViewport: isTooltip,
			alignmentPosition: isTooltip
				? TOOLTIP_ALIGNMENT_POSITION
				: (CENTER_ALIGNMENT_POINTS as unknown as AlignPoints),
			autoBestAlign: isTooltip,
			getOffset: isTooltip ? getTooltipOffset : getCenterOffset,
			isOpen: !!anchorRef,
			ref: indicatorRef,
			triggerRef: anchorRef ?? fallbackTriggerRef,
		},
		[anchorRef, isTooltip]
	);

	// `useOverlayPosition` does not expose which side `autoBestAlign`
	// resolved to, and `getOffset` is only called with the initial
	// points, so the resolved alignment cannot be read from there.
	// Compare bounding rects after each alignment pass — observed via
	// `observeRect` — to toggle the flipped modifier class so the
	// tooltip arrow renders on the side facing the anchor. Skipped for
	// the centered placement, which has no arrow to flip.

	useEffect(() => {
		if (!isTooltip || !anchorRef?.current || !indicatorRef.current) {
			return;
		}

		const anchor = anchorRef.current;
		const indicator = indicatorRef.current;

		function update() {
			const anchorRect = anchor.getBoundingClientRect();
			const indicatorRect = indicator.getBoundingClientRect();
			const isFlipped =
				indicatorRect.left + indicatorRect.width / 2 <
				anchorRect.left + anchorRect.width / 2;

			setFlipped((previous) =>
				previous === isFlipped ? previous : isFlipped
			);
		}

		update();

		return observeRect(indicator, update);
	}, [anchorRef, isTooltip]);

	// Toggle the indicator's visibility based on focus-within of the
	// anchor element. Listeners attach to the anchor itself, not the
	// document — `focusin`/`focusout` bubble from descendants up to the
	// anchor, so they fire as long as focus crosses the anchor's
	// subtree. This stays encapsulated and survives consumers like
	// Table's `FocusWithinProvider` that call `event.stopPropagation()`
	// at the React root, which would otherwise stop a document-level
	// listener from ever seeing the event.

	useEffect(() => {
		if (!anchorRef?.current) {
			return;
		}

		const anchor = anchorRef.current;

		function update() {
			setHasFocusWithin(anchor.contains(document.activeElement));
		}

		update();

		anchor.addEventListener('focusin', update);
		anchor.addEventListener('focusout', update);

		return () => {
			anchor.removeEventListener('focusin', update);
			anchor.removeEventListener('focusout', update);
		};
	}, [anchorRef]);

	return (
		<div
			{...otherProps}
			aria-hidden="true"
			className={classNames(
				'clay-keyboard-arrows-indicator',
				`clay-keyboard-arrows-${direction}`,
				{
					'clay-keyboard-arrows-indicator-floating': !!anchorRef,
					'clay-keyboard-arrows-indicator-floating-centered':
						!!anchorRef && !isTooltip,
					'clay-keyboard-arrows-indicator-floating-flipped':
						!!anchorRef && isTooltip && flipped,
					'clay-keyboard-arrows-indicator-floating-hidden':
						!!anchorRef && !hasFocusWithin,
					'clay-keyboard-arrows-indicator-floating-tooltip':
						!!anchorRef && isTooltip,
				},
				className
			)}
			ref={indicatorRef}
		>
			<ClayIcon spritemap={spritemap} symbol="arrows-all" />
		</div>
	);
}
