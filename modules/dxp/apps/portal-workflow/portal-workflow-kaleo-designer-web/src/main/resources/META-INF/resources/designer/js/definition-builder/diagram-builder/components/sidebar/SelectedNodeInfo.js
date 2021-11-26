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

import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import {DiagramBuilderContext} from '../../DiagramBuilderContext';
import SidebarPanel from './SidebarPanel';

export default function SelectedNodeInfo({errors, setErrors}) {
	const {
		selectedNode,
		selectedNodeNewId,
		setSelectedNode,
		setSelectedNodeNewId,
	} = useContext(DiagramBuilderContext);

	return (
		<SidebarPanel panelTitle={Liferay.Language.get('information')}>
			<ClayForm.Group className={errors.label ? 'has-error' : ''}>
				<label htmlFor="nodeLabel">
					{Liferay.Language.get('label')}

					<span className="ml-1 mr-1 text-warning">*</span>

					<span title={Liferay.Language.get('the-name-of-the-label')}>
						<ClayIcon
							className="text-muted"
							symbol="question-circle-full"
						/>
					</span>
				</label>

				<ClayInput
					id="nodeLabel"
					onChange={({target}) => {
						if (target.value.trim() === '') {
							setErrors({label: true});
						}
						else {
							setErrors({label: false});
						}

						setSelectedNode({
							...selectedNode,
							data: {
								...selectedNode.data,
								label: target.value,
							},
						});
					}}
					type="text"
					value={selectedNode?.data.label || ''}
				/>

				<ClayForm.FeedbackItem>
					{errors.label && (
						<>
							<ClayForm.FeedbackIndicator symbol="exclamation-full" />

							{Liferay.Language.get('this-field-is-required')}
						</>
					)}
				</ClayForm.FeedbackItem>
			</ClayForm.Group>

			<ClayForm.Group className={errors.id ? 'has-error' : ''}>
				<label htmlFor="nodeId">
					<span>
						{`${Liferay.Language.get(
							'node'
						)} ${Liferay.Language.get('id')}`}
					</span>

					<span className="ml-1 mr-1 text-warning">*</span>

					<span
						title={Liferay.Language.get(
							'id-is-the-node-identifier'
						)}
					>
						<ClayIcon
							className="text-muted"
							symbol="question-circle-full"
						/>
					</span>
				</label>

				<ClayInput
					id="nodeId"
					onChange={({target}) => {
						if (target.value.trim() === '') {
							setErrors({id: true});
						}
						else {
							setErrors({id: false});
						}

						setSelectedNodeNewId(target.value);
					}}
					type="text"
					value={(selectedNodeNewId ?? selectedNode?.id) || ''}
				/>

				<ClayForm.FeedbackItem>
					{errors.id && (
						<>
							<ClayForm.FeedbackIndicator symbol="exclamation-full" />

							{Liferay.Language.get('this-field-is-required')}
						</>
					)}
				</ClayForm.FeedbackItem>
			</ClayForm.Group>

			<ClayForm.Group>
				<label htmlFor="nodeDescription">
					{Liferay.Language.get('description')}
				</label>

				<ClayInput
					component="textarea"
					id="nodeDescription"
					onChange={({target}) =>
						setSelectedNode({
							...selectedNode,
							data: {
								...selectedNode.data,
								description: target.value,
							},
						})
					}
					type="text"
					value={selectedNode?.data.description || ''}
				/>
			</ClayForm.Group>
		</SidebarPanel>
	);
}

SelectedNodeInfo.propTypes = {
	errors: PropTypes.object.isRequired,
	setErrors: PropTypes.func.isRequired,
};
