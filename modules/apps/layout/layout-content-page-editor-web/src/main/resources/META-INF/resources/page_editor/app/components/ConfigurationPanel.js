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

import {useEventListener} from 'frontend-js-react-web';
import {Align} from 'metal-position';
import React, {
	forwardRef,
	useLayoutEffect,
	useRef,
	useState,
	useMemo,
	useCallback
} from 'react';

import {FLOATING_TOOLBAR_CONFIGURATIONS} from '../config/constants/floatingToolbarConfigurations';

const ALIGNMENTS = [
	'top',
	'top-right',
	'right',
	'bottom-right',
	'bottom',
	'bottom-left',
	'left',
	'top-left'
];

export const alignElement = (element, alignElement) => {
	const suggestedAlign = Align.suggestAlignBestRegion(
		element,
		alignElement,
		Align.BottomRight
	);

	const bestPosition =
		suggestedAlign.position !== Align.BottomRight
			? Align.TopRight
			: Align.BottomRight;

	return Align.align(element, alignElement, bestPosition, false);
};

export default forwardRef(
	({configurationPanel, item, popoverRef, ...otherProps}, ref) => {
		const configurationPanelRef = useRef(null);
		const [configurationPanelAlign, setConfigurationPanelAlign] = useState(
			null
		);

		const wrapperContainerEl = useMemo(
			() => document.getElementById('wrapper'),
			[]
		);

		const ConfigurationPanelComponent =
			configurationPanel &&
			FLOATING_TOOLBAR_CONFIGURATIONS[configurationPanel];

		const alignPanel = useCallback(() => {
			if (configurationPanelRef.current && popoverRef.current) {
				const newAlignment =
					ALIGNMENTS[
						alignElement(
							configurationPanelRef.current,
							popoverRef.current
						)
					];

				if (newAlignment !== configurationPanelAlign) {
					setConfigurationPanelAlign(newAlignment);
				}
			}
		}, [configurationPanelAlign, configurationPanelRef, popoverRef]);

		useEventListener(
			'scroll',
			() => alignPanel(),
			true,
			wrapperContainerEl
		);

		useLayoutEffect(() => alignPanel(), [alignPanel, configurationPanel]);

		return (
			<div
				className="fragments-editor__floating-toolbar-panel"
				ref={node => {
					configurationPanelRef.current = node;
					if (ref) {
						ref.current = node;
					}
				}}
				{...otherProps}
			>
				<div className="p-3 popover popover-scrollable">
					{ConfigurationPanelComponent && (
						<ConfigurationPanelComponent item={item} />
					)}
				</div>
			</div>
		);
	}
);
