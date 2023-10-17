/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CustomVerticalBar} from '@liferay/object-js-components-web';
import React, {ReactNode, useEffect, useState} from 'react';

import './RightSidebarRoot.scss';
import {useObjectFolderContext} from '../ModelBuilderContext/objectFolderContext';

interface IRightSidebarRoot {
	children: ReactNode;
}

export function RightSideBarRoot({children}: IRightSidebarRoot) {
	const [
		{
			selectedObjectDefinitionNode,
			selectedObjectField,
			selectedObjectRelationship,
			showSidebars,
		},
	] = useObjectFolderContext();
	const [loading, setLoading] = useState(false);
	const [verticalBarWidth, setVerticalBarWidth] = useState(320);

	const setNewVerticalBarWidthValue = (width: number) => {
		setLoading(true);

		setVerticalBarWidth(width);

		setTimeout(() => setLoading(false), 50);
	};

	useEffect(() => {
		if (
			selectedObjectField &&
			selectedObjectField.businessType === 'Aggregation'
		) {
			setNewVerticalBarWidthValue(950);

			return;
		}

		setNewVerticalBarWidthValue(320);

		return;
	}, [
		selectedObjectDefinitionNode,
		selectedObjectField,
		selectedObjectRelationship,
	]);

	return (
		<>
			{!loading && (
				<CustomVerticalBar
					defaultActive="objectsModelBuilderRightSidebar"
					panelWidth={verticalBarWidth}
					position="right"
					resize={false}
					triggerSideBarAnimation={showSidebars}
					verticalBarItems={[
						{
							title: 'objectsModelBuilderRightSidebar',
						},
					]}
				>
					{children}
				</CustomVerticalBar>
			)}
		</>
	);
}
