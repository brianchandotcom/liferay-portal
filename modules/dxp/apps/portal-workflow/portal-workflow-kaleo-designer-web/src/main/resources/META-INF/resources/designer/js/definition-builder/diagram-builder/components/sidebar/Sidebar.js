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
import SidebarBody from './SidebarBody';
import SidebarHeader from './SidebarHeader';
import sectionComponents from './sections/sectionComponents';

const contents = {
	'assignments': {
		backButton: (setContentName) => () => setContentName('task'),
		deleteFunction: (setSelectedItem) => () =>
			setSelectedItem((previousValue) => ({
				...previousValue,
				data: {
					...previousValue.data,
					assignments: null,
				},
			})),
		sections: ['assignments'],
		showDeleteButton: true,
		title: Liferay.Language.get('assignments'),
	},
	'condition': {
		sections: ['nodeInformation', 'notificationsSummary'],
		showDeleteButton: true,
		title: Liferay.Language.get('condition-node'),
	},
	'end': {
		sections: ['nodeInformation', 'notificationsSummary'],
		showDeleteButton: true,
		title: Liferay.Language.get('end'),
	},
	'fork': {
		sections: ['nodeInformation', 'notificationsSummary'],
		showDeleteButton: true,
		title: Liferay.Language.get('fork-node'),
	},
	'join': {
		sections: ['nodeInformation', 'notificationsSummary'],
		showDeleteButton: true,
		title: Liferay.Language.get('join-node'),
	},
	'join-xor': {
		sections: ['nodeInformation', 'notificationsSummary'],
		showDeleteButton: true,
		title: Liferay.Language.get('join-xor-node'),
	},
	'notifications': {
		backButton: (setContentName, selectedItemType) => () =>
			setContentName(selectedItemType),
		sections: ['notifications'],
		showDeleteButton: true,
		title: Liferay.Language.get('notifications'),
	},
	'scripted-assignment': {
		backButton: (setContentName) => () => setContentName('assignments'),
		sections: ['sourceCode'],
		showDeleteButton: false,
		title: Liferay.Language.get('scripted-assignment'),
	},
	'start': {
		sections: ['nodeInformation', 'notificationsSummary'],
		showDeleteButton: true,
		title: Liferay.Language.get('start'),
	},
	'state': {
		sections: ['nodeInformation', 'notificationsSummary'],
		showDeleteButton: true,
		title: Liferay.Language.get('state'),
	},
	'task': {
		sections: [
			'nodeInformation',
			'assignmentsSummary',
			'notificationsSummary',
		],
		showDeleteButton: true,
		title: Liferay.Language.get('task'),
	},
	'transition': {
		sections: ['edgeInformation'],
		showDeleteButton: true,
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
	const [contentName, setContentName] = useState('');
	const [errors, setErrors] = useState(errorsDefaultValues);

	const clearErrors = () => {
		setErrors(errorsDefaultValues);
	};

	const defaultBackButton = () => {
		setSelectedItem(null);
		setSelectedItemNewId(null);
		clearErrors();
	};

	useEffect(() => {
		setSelectedItemNewId(null);
		clearErrors();

		let contentKey = '';

		if (selectedItem?.id) {
			contentKey = isNode(selectedItem)
				? selectedItem?.type
				: 'transition';
		}

		setContentName(contentKey);

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selectedItem?.id, setSelectedItemNewId]);

	const content = contents[contentName];
	const title = content?.title ?? Liferay.Language.get('nodes');

	return (
		<div className="sidebar">
			<SidebarHeader
				backButtonFunction={
					content?.backButton?.(setContentName, selectedItem?.type) ||
					defaultBackButton
				}
				contentName={contentName}
				deleteButtonFunction={
					content?.deleteFunction?.(setSelectedItem) || null
				}
				showBackButton={!!content}
				showDeleteButton={content?.showDeleteButton}
				title={title}
			/>

			<SidebarBody displayDefaultContent={!content}>
				{content?.sections?.map((sectionKey) => {
					const SectionComponent = sectionComponents[sectionKey];

					return (
						<SectionComponent
							errors={errors}
							key={sectionKey}
							sections={content?.sections || []}
							setContentName={setContentName}
							setErrors={setErrors}
						/>
					);
				})}
			</SidebarBody>
		</div>
	);
}
