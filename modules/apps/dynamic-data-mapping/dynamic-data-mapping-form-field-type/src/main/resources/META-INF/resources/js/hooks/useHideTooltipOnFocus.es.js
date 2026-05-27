/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useRef, useState} from 'react';

/**
 * Tracks input focus state and cancels any in-flight ClayTooltipProvider
 * tooltip so the field tooltip doesn't obstruct the input while typing.
 * Consumers attach `tooltipWrapperRef` to the tooltip wrapper, gate the wrapper's
 * `title` on `isFocused`, and call `onFocus`/`onBlur` from the input's focus
 * and blur handlers.
 */
export function useHideTooltipOnFocus() {
	const [isFocused, setIsFocused] = useState(false);
	const tooltipWrapperRef = useRef(null);

	return {
		isFocused,
		onBlur: () => setIsFocused(false),
		onFocus: () => {
			setIsFocused(true);
			tooltipWrapperRef.current?.dispatchEvent(
				new MouseEvent('mouseout', {bubbles: true})
			);
		},
		tooltipWrapperRef,
	};
}
