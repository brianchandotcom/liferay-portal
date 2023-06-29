/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {VerticalBar} from '@clayui/core';
import React, {ReactNode} from 'react';

import './CustomVerticalBar.scss';

interface CustomVerticalBarProps {
	children: ReactNode;
	defaultActive: string;
	panelWidth?: number;
	panelWidthMax?: number;
	panelWidthMin?: number;
	position: 'left' | 'right';
	resize?: boolean;
	triggerSideBarAnimation: boolean;
	verticalBarItems: {
		title: string;
	}[];
}

export function CustomVerticalBar({
	children,
	defaultActive,
	panelWidth = 1000,
	panelWidthMax = 1200,
	panelWidthMin = 400,
	position,
	resize = true,
	triggerSideBarAnimation,
	verticalBarItems,
}: CustomVerticalBarProps) {
	return (
		<VerticalBar
			className={
				triggerSideBarAnimation
					? `lfr__objects-custom-vertical-bar--${position}-open`
					: `lfr__objects-custom-vertical-bar--${position}-closed`
			}
			defaultActive={defaultActive}
			defaultPanelWidth={panelWidth}
			panelWidthMax={panelWidthMax}
			panelWidthMin={panelWidthMin}
			position={position}
			resize={resize}
		>
			<div className="lfr__objects-custom-vertical-bar-content">
				<VerticalBar.Content items={verticalBarItems}>
					{(item) => (
						<VerticalBar.Panel key={item.title}>
							{children}
						</VerticalBar.Panel>
					)}
				</VerticalBar.Content>
			</div>
		</VerticalBar>
	);
}
