/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import React, {useContext, useEffect, useState} from 'react';

import {DiagramBuilderContext} from '../../DiagramBuilderContext';
import SelectedNodeInfo from './SelectedNodeInfo';
import SidebarBody from './SidebarBody';
import SidebarHeader from './SidebarHeader';

export default function Sidebar() {
	const {selectedNode, setSelectedNode, setSelectedNodeNewId} = useContext(
		DiagramBuilderContext
	);
	const [errors, setErrors] = useState({
		id: false,
		label: false,
	});

	useEffect(() => {
		setSelectedNodeNewId(null);
		setErrors({
			id: false,
			label: false,
		});
	}, [selectedNode?.id, setSelectedNodeNewId]);

	return (
		<div className="sidebar">
			<SidebarHeader
				backButtonFunction={() => {
					setSelectedNode(null);
					setSelectedNodeNewId(null);
					setErrors({
						id: false,
						label: false,
					});
				}}
				showBackButton={selectedNode}
				title={
					selectedNode
						? selectedNode.type
						: Liferay.Language.get('nodes')
				}
			/>

			<SidebarBody displayDefaultContent={!selectedNode}>
				<SelectedNodeInfo errors={errors} setErrors={setErrors} />
			</SidebarBody>
		</div>
	);
}
