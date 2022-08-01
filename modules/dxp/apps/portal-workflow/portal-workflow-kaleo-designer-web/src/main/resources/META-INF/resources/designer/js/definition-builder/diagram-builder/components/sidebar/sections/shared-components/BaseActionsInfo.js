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

import ClayForm, {ClayInput, ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React, {useEffect} from 'react';

import {sortElements} from '../utils';

const BaseActionsInfo = ({
	description,
	executionType,
	executionTypeInput,
	executionTypeOptions,
	name,
	placeholderName,
	placeholderScript,
	priority,
	script,
	scriptLabel,
	scriptLabelSecondary,
	setDescription,
	setExecutionType,
	setExecutionTypeOptions,
	setName,
	setPriority,
	setScript,
	updateActionInfo,
}) => {
	useEffect(() => {
		if (executionTypeOptions) {
			sortElements(executionTypeOptions, 'value');

			return function cleanup() {
				setExecutionTypeOptions(
					executionTypeOptions.filter(({value}) => {
						return value !== 'onAssignment';
					})
				);
			};
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<>
			<ClayForm.Group>
				<label htmlFor="name">
					{Liferay.Language.get('name')}

					<span className="ml-1 mr-1 text-warning">*</span>
				</label>

				<ClayInput
					id="name"
					onBlur={() =>
						updateActionInfo({
							description,
							executionType,
							name,
							priority,
							script,
						})
					}
					onChange={({target}) => {
						setName(target.value);
					}}
					placeholder={placeholderName}
					type="text"
					value={name}
				/>
			</ClayForm.Group>
			<ClayForm.Group>
				<label htmlFor="description">
					{Liferay.Language.get('description')}
				</label>

				<ClayInput
					id="description"
					onBlur={() =>
						updateActionInfo({
							description,
							executionType,
							name,
							priority,
							script,
						})
					}
					onChange={({target}) => {
						setDescription(target.value);
					}}
					type="text"
					value={description}
				/>
			</ClayForm.Group>
			<ClayForm.Group>
				<label htmlFor="script">
					{`${scriptLabel} (${scriptLabelSecondary})`}

					<span className="ml-1 mr-1 text-warning">*</span>
				</label>

				<ClayInput
					component="textarea"
					id="script"
					onBlur={() =>
						updateActionInfo({
							description,
							executionType,
							name,
							priority,
							script,
						})
					}
					onChange={({target}) => {
						setScript(target.value);
					}}
					placeholder={placeholderScript}
					type="text"
					value={script}
				/>
			</ClayForm.Group>

			{typeof executionTypeInput !== 'undefined' && (
				<ClayForm.Group>
					<label htmlFor="execution-type">
						{Liferay.Language.get('execution-type')}
					</label>

					<ClaySelect
						aria-label="Select"
						defaultValue={executionType}
						id="execution-type"
						onChange={({target}) => {
							setExecutionType(target.value);
						}}
						onClickCapture={() =>
							updateActionInfo({
								description,
								executionType,
								name,
								priority,
								script,
							})
						}
					>
						{executionTypeOptions &&
							executionTypeOptions.map((item) => (
								<ClaySelect.Option
									key={item.value}
									label={item.label}
									value={item.value}
								/>
							))}
					</ClaySelect>
				</ClayForm.Group>
			)}

			<ClayForm.Group>
				<label htmlFor="priority">
					{Liferay.Language.get('priority')}
				</label>

				<span
					className="ml-1"
					title={Liferay.Language.get(
						'lower-numbers-represent-higher-priority'
					)}
				>
					<ClayIcon
						className="text-muted"
						symbol="question-circle-full"
					/>
				</span>

				<ClayInput
					aria-label="Select"
					id="priority"
					onBlur={({target}) => {
						const {value: newValue} = target;
						setPriority(newValue);

						updateActionInfo({
							description,
							executionType,
							name,
							priority,
							script,
						});
					}}
					onChange={({target}) => {
						const {value: newValue} = target;
						setPriority(newValue);
					}}
					onWheel={(event) => event.target.blur()}
					type="number"
					value={priority}
				/>
			</ClayForm.Group>
		</>
	);
};

BaseActionsInfo.propTypes = {
	description: PropTypes.string,
	executionType: PropTypes.string,
	executionTypeInput: PropTypes.func,
	executionTypeOptions: PropTypes.object,
	name: PropTypes.string,
	placeholderName: PropTypes.string,
	placeholderScript: PropTypes.string,
	priority: PropTypes.number,
	script: PropTypes.string,
	scriptLabel: PropTypes.string,
	scriptLabelSecondary: PropTypes.string,
	selectedItem: PropTypes.object,
	setDescription: PropTypes.func,
	setExecutionType: PropTypes.func,
	setExecutionTypeOptions: PropTypes.func,
	setName: PropTypes.func,
	setPriority: PropTypes.func,
	setScript: PropTypes.func,
	updateActionInfo: PropTypes.func,
};

export default BaseActionsInfo;
