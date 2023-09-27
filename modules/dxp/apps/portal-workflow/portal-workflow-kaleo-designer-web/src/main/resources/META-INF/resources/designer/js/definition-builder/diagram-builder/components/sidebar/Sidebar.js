/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext, useEffect, useState} from 'react';
import {isNode} from 'react-flow-renderer';

import {DefinitionBuilderContext} from '../../../DefinitionBuilderContext';
import {DiagramBuilderContext} from '../../DiagramBuilderContext';
import DefinitionInfo from './DefnitionInfo';
import SidebarBody from './SidebarBody';
import SidebarHeader from './SidebarHeader';
import sectionComponents from './sections/sectionComponents';

const contents = {
	'actions': {
		backButton: (setContentName, selectedItemType) => () =>
			setContentName(selectedItemType),
		deleteFunction: (setSelectedItem) => () =>
			setSelectedItem((previousValue) => ({
				...previousValue,
				data: {
					...previousValue.data,
					actions: null,
				},
			})),
		sections: ['actions'],
		showDeleteButton: true,
		title: Liferay.Language.get('actions'),
	},
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
		sections: ['nodeInformation', 'notificationsSummary', 'actionsSummary'],
		showDeleteButton: true,
		title: Liferay.Language.get('condition-node'),
	},
	'end': {
		sections: ['nodeInformation', 'notificationsSummary', 'actionsSummary'],
		showDeleteButton: true,
		title: Liferay.Language.get('end'),
	},
	'fork': {
		sections: ['nodeInformation', 'notificationsSummary', 'actionsSummary'],
		showDeleteButton: true,
		title: Liferay.Language.get('fork-node'),
	},
	'join': {
		sections: ['nodeInformation', 'notificationsSummary', 'actionsSummary'],
		showDeleteButton: true,
		title: Liferay.Language.get('join-node'),
	},
	'join-xor': {
		sections: ['nodeInformation', 'notificationsSummary', 'actionsSummary'],
		showDeleteButton: true,
		title: Liferay.Language.get('join-xor-node'),
	},
	'notifications': {
		backButton: (setContentName, selectedItemType) => () =>
			setContentName(selectedItemType),
		deleteFunction: (setSelectedItem) => () =>
			setSelectedItem((previousValue) => ({
				...previousValue,
				data: {
					...previousValue.data,
					notifications: null,
				},
			})),
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
	'scripted-reassignment': {
		backButton: (setContentName) => () => setContentName('timers'),
		sections: ['timerSourceCode'],
		showDeleteButton: false,
		title: Liferay.Language.get('scripted-reassignment'),
	},
	'start': {
		sections: ['nodeInformation', 'notificationsSummary', 'actionsSummary'],
		showDeleteButton: true,
		title: Liferay.Language.get('start'),
	},
	'state': {
		sections: ['nodeInformation', 'notificationsSummary', 'actionsSummary'],
		showDeleteButton: true,
		title: Liferay.Language.get('state'),
	},
	'task': {
		sections: [
			'nodeInformation',
			'assignmentsSummary',
			'notificationsSummary',
			'actionsSummary',
			'timersSummary',
		],
		showDeleteButton: true,
		title: Liferay.Language.get('task'),
	},
	'timers': {
		backButton: (setContentName) => () => setContentName('task'),
		deleteFunction: (setSelectedItem) => () =>
			setSelectedItem((previousValue) => ({
				...previousValue,
				data: {
					...previousValue.data,
					taskTimers: null,
				},
			})),
		sections: ['timers'],
		showDeleteButton: true,
		title: Liferay.Language.get('timers'),
	},
	'transition': {
		sections: ['edgeInformation'],
		showDeleteButton: true,
		title: Liferay.Language.get('transition'),
	},
};

const errorsDefaultValues = {
	id: false,
	label: false,
};

export default function Sidebar() {
	const {definitionTitle, setBlockingErrors, showDefinitionInfo} = useContext(
		DefinitionBuilderContext
	);

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
		setBlockingErrors((prev) => {
			if (errors?.label === true || errors?.id?.empty === true) {
				return {
					...prev,
					errorMessage: Liferay.Language.get(
						'please-fill-out-the-fields-before-saving-or-publishing'
					),
					errorType: 'emptyField',
				};
			}
			else if (errors?.id?.duplicated === true) {
				return {
					...prev,
					errorMessage: Liferay.Language.get(
						'please-rename-this-with-another-words'
					),
					errorType: 'duplicated',
				};
			}
			else {
				return {...prev, errorType: ''};
			}
		});

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [errors]);

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
				showBackButton={!!content && !showDefinitionInfo}
				showDeleteButton={
					content?.showDeleteButton && !showDefinitionInfo
				}
				title={!showDefinitionInfo ? title : definitionTitle}
			/>

			<SidebarBody
				displayDefaultContent={!content && !showDefinitionInfo}
			>
				{!showDefinitionInfo ? (
					content?.sections?.map((sectionKey) => {
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
					})
				) : (
					<DefinitionInfo />
				)}
			</SidebarBody>
		</div>
	);
}
