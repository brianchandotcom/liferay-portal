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

import {Align} from 'metal-position';
import React, {forwardRef, useLayoutEffect, useRef, useState} from 'react';

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

const ALIGNMENTS_MAP = {
	bottom: Align.Bottom,
	'bottom-left': Align.BottomLeft,
	'bottom-right': Align.BottomRight,
	left: Align.Left,
	right: Align.Right,
	top: Align.Top,
	'top-left': Align.TopLeft,
	'top-right': Align.TopRight
};

export default forwardRef(
	function ConfigurationPanel({configurationPanel, popoverRef, ...otherProps}, ref) {
		const configurationPanelRef = useRef(null);

		const [configurationPanelAlign, setConfigurationPanelAlign] = useState(
			null
		);

		const ConfigurationPanelComponent =
			configurationPanel &&
			FLOATING_TOOLBAR_CONFIGURATIONS[configurationPanel];

		useLayoutEffect(() => {
			if (configurationPanelRef.current && popoverRef.current) {
				const newAlignment =
					ALIGNMENTS[
						Align.align(
							configurationPanelRef.current,
							popoverRef.current,
							ALIGNMENTS_MAP['bottom-right'],
							false
						)
					];

				if (newAlignment !== configurationPanelAlign) {
					setConfigurationPanelAlign(newAlignment);
				}
			}
		}, [
			configurationPanel,
			popoverRef,
			configurationPanelRef,
			configurationPanelAlign
		]);

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
					{ConfigurationPanelComponent && <ConfigurationPanelComponent />}
				</div>
			</div>
		);
	}
);
