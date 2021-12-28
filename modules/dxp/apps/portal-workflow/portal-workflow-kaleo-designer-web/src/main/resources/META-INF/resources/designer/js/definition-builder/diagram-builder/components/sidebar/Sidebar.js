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
import {isNode} from 'react-flow-renderer';

import {DiagramBuilderContext} from '../../DiagramBuilderContext';
import SelectedEdgeInfo from './SelectedEdgeInfo';
import SelectedNodeInfo from './SelectedNodeInfo';
import SidebarBody from './SidebarBody';
import SidebarHeader from './SidebarHeader';

const contents = {
	end: {
		component: SelectedNodeInfo,
		title: Liferay.Language.get('end'),
	},
	start: {
		component: SelectedNodeInfo,
		title: Liferay.Language.get('start'),
	},
	state: {
		component: SelectedNodeInfo,
		title: Liferay.Language.get('state'),
	},
	task: {
		component: SelectedNodeInfo,
		title: Liferay.Language.get('task'),
	},
	transition: {
		component: SelectedEdgeInfo,
		title: Liferay.Language.get('transition'),
	},
};

const errorsDefaultValues = {
	id: {duplicated: false, empty: false},
	label: false,
};

export default function Sidebar() {
	const {selectedItem, setSelectedItem, setSelectedItemNewId} = useContext(
		DiagramBuilderContext
	);
	const [errors, setErrors] = useState(errorsDefaultValues);

	const clearErrors = () => {
		setErrors(errorsDefaultValues);
	};

	useEffect(() => {
		setSelectedItemNewId(null);
		clearErrors();
	}, [selectedItem?.id, setSelectedItemNewId]);

	let contentKey = '';

	if (selectedItem?.id) {
		contentKey = isNode(selectedItem) ? selectedItem?.type : 'transition';
	}

	const content = contents[contentKey];
	const ContentComponent = content?.component;
	const title = content?.title ?? Liferay.Language.get('nodes');

	return (
		<div className="sidebar">
			<SidebarHeader
				backButtonFunction={() => {
					setSelectedItem(null);
					setSelectedItemNewId(null);
					clearErrors();
				}}
				showHeaderButtons={!!content}
				title={title}
			/>

			<SidebarBody displayDefaultContent={!content}>
				<ContentComponent errors={errors} setErrors={setErrors} />
			</SidebarBody>
		</div>
	);
}
