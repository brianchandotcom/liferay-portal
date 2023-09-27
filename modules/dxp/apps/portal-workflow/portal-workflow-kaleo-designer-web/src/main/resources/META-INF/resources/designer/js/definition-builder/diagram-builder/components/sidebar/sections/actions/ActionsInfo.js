/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import PropTypes from 'prop-types';
import React, {useContext, useState} from 'react';

import {DiagramBuilderContext} from '../../../../DiagramBuilderContext';
import SidebarPanel from '../../SidebarPanel';
import BaseActionsInfo from '../shared-components/BaseActionsInfo';

const ActionsInfo = ({
	identifier,
	index,
	executionTypeInput = () => {
		'';
	},
	sectionsLength,
	setSections,
}) => {
	const {
		functionActionExecutors,
		selectedItem,
		setSelectedItem,
		statuses,
	} = useContext(DiagramBuilderContext);
	const {actions} = selectedItem.data;

	const [script, setScript] = useState(actions?.script?.[index] || '');

	const [status, setStatus] = useState(actions?.status?.[index] || '');

	const [description, setDescription] = useState(
		actions?.description?.[index] || ''
	);

	const [executionTypeOptions, setExecutionTypeOptions] = useState([
		{
			label: Liferay.Language.get('on-entry'),
			value: 'onEntry',
		},
		{
			label: Liferay.Language.get('on-exit'),
			value: 'onExit',
		},
	]);

	const actionTypeOptions = [
		{
			label: Liferay.Language.get('groovy'),
			type: 'script',
			value: 'groovy',
		},
		{
			label: Liferay.Language.get('java'),
			type: 'script',
			value: 'java',
		},
		{
			label: Liferay.Language.get('update-status'),
			type: 'status',
			value: 'update-status',
		},
	];

	if (functionActionExecutors?.length) {
		actionTypeOptions.push(
			...functionActionExecutors.map((item) => ({
				label: item,
				type: 'functionActionExecutor',
				value: item,
			}))
		);
	}

	const [scriptLanguage, setScriptLanguage] = useState(
		actions?.scriptLanguage?.[index] || 'select-a-script-type'
	);

	let defaultActionType;

	if (status) {
		defaultActionType = actionTypeOptions.find(
			(item) => item.value === 'update-status'
		);
	}
	else {
		defaultActionType = actionTypeOptions.find(
			(item) => item.value === scriptLanguage
		);
	}

	const [selectedActionType, setSelectedActionType] = useState(
		defaultActionType
	);

	if (
		selectedItem.type === 'task' &&
		executionTypeOptions &&
		executionTypeOptions[0].value !== 'onAssignment'
	) {
		executionTypeOptions.unshift({
			label: Liferay.Language.get('on-assignment'),
			value: 'onAssignment',
		});
	}

	const [executionType, setExecutionType] = useState(
		actions?.executionType?.[index] ?? executionTypeOptions[0].value
	);
	const [name, setName] = useState(actions?.name?.[index] || '');
	const [priority, setPriority] = useState(actions?.priority?.[index] || 1);

	const deleteSection = () => {
		setSections((prevSections) => {
			const newSections = prevSections.filter(
				(prevSection) => prevSection.identifier !== identifier
			);

			if (name && executionType) {
				updateSelectedItem(newSections);
			}

			return newSections;
		});
	};

	const updateActionInfo = (item) => {
		if (
			item.name &&
			(item.script ||
				(selectedActionType?.type === 'functionActionExecutor' &&
					item.script === '') ||
				item.status) &&
			item.executionType
		) {
			setSections((prev) => {
				const updatedSection = [...prev];

				updatedSection[index] = {
					...updatedSection[index],
					...item,
				};

				updateSelectedItem(updatedSection);

				return updatedSection;
			});
		}
	};

	const updateSelectedItem = (values) => {
		setSelectedItem((previousItem) => ({
			...previousItem,
			data: {
				...previousItem.data,
				actions: {
					description: values.map(({description}) => description),
					executionType: values.map(
						({executionType}) => executionType
					),
					name: values.map(({name}) => name),
					priority: values.map(({priority}) => priority),
					script: values.map(({script}) => script),
					scriptLanguage: values.map(
						({scriptLanguage}) => scriptLanguage
					),
					sectionsData: values.map((values) => values),
					status: values.map((status) => status.status),
				},
			},
		}));
	};

	return (
		<SidebarPanel panelTitle={Liferay.Language.get('information')}>
			<BaseActionsInfo
				actionTypes={actionTypeOptions}
				description={description}
				executionType={executionType}
				executionTypeInput={executionTypeInput}
				executionTypeOptions={executionTypeOptions}
				index={index}
				name={name}
				placeholderName={Liferay.Language.get('my-action')}
				placeholderScript="${userName} sent you a ${entryType} for review in the workflow."
				priority={priority}
				script={script}
				scriptLanguage={scriptLanguage}
				selectedActionType={selectedActionType}
				selectedItem={selectedItem}
				setDescription={setDescription}
				setExecutionType={setExecutionType}
				setExecutionTypeOptions={setExecutionTypeOptions}
				setName={setName}
				setPriority={setPriority}
				setScript={setScript}
				setScriptLanguage={setScriptLanguage}
				setSelectedActionType={setSelectedActionType}
				setStatus={setStatus}
				status={status}
				statuses={statuses}
				updateActionInfo={updateActionInfo}
			/>

			<div className="section-buttons-area">
				<ClayButton
					className="mr-3"
					disabled={
						actions?.name === '' ||
						(!selectedActionType === 'functionActionExecutor' &&
							script === '')
					}
					displayType="secondary"
					onClick={() =>
						setSections((prev) => {
							return [
								...prev,
								{identifier: `${Date.now()}-${prev.length}`},
							];
						})
					}
				>
					{Liferay.Language.get('new-action')}
				</ClayButton>

				{sectionsLength > 1 && (
					<ClayButtonWithIcon
						className="delete-button"
						displayType="unstyled"
						onClick={deleteSection}
						symbol="trash"
					/>
				)}
			</div>
		</SidebarPanel>
	);
};

ActionsInfo.propTypes = {
	executionTypeInput: PropTypes.func,
	identifier: PropTypes.string.isRequired,
	index: PropTypes.number.isRequired,
	sectionsLength: PropTypes.number.isRequired,
	setSections: PropTypes.func.isRequired,
};

export default ActionsInfo;
