/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import {DefinitionBuilderContext} from '../../../../DefinitionBuilderContext';
import {defaultLanguageId} from '../../../../constants';
import {DiagramBuilderContext} from '../../../DiagramBuilderContext';
import ScriptInput from '../../shared-components/ScriptInput';
import SidebarPanel from '../SidebarPanel';
import {checkIdErrors, checkLabelErrors, getUpdatedLabelItem} from './utils';

export default function NodeInformation({errors, setErrors}) {
	const {elements, selectedLanguageId} = useContext(DefinitionBuilderContext);
	const {
		selectedItem,
		selectedItemNewId,
		setSelectedItem,
		setSelectedItemNewId,
	} = useContext(DiagramBuilderContext);

	return (
		<SidebarPanel panelTitle={Liferay.Language.get('information')}>
			<ClayForm.Group className={errors.label ? 'has-error' : ''}>
				<label htmlFor="nodeLabel">
					{Liferay.Language.get('label')}

					<span className="ml-1 mr-1 text-warning">*</span>

					<span title={Liferay.Language.get('label-name')}>
						<ClayIcon
							className="text-muted"
							symbol="question-circle-full"
						/>
					</span>
				</label>

				<ClayInput
					id="nodeLabel"
					onChange={({target}) => {
						setErrors(checkLabelErrors(errors, target));

						const key =
							selectedLanguageId !== ''
								? selectedLanguageId
								: defaultLanguageId;

						setSelectedItem(
							getUpdatedLabelItem(key, selectedItem, target)
						);
					}}
					type="text"
					value={
						(selectedLanguageId
							? selectedItem?.data.label[selectedLanguageId]
							: selectedItem?.data.label[defaultLanguageId]) || ''
					}
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

			<ClayForm.Group
				className={
					errors?.id?.duplicated || errors?.id?.empty
						? 'has-error'
						: ''
				}
			>
				<label htmlFor="nodeName">
					<span>
						{`${Liferay.Language.get(
							'node'
						)} ${Liferay.Language.get('name')}`}
					</span>

					<span className="ml-1 mr-1 text-warning">*</span>

					<span
						title={Liferay.Language.get(
							'name-is-the-node-identifier'
						)}
					>
						<ClayIcon
							className="text-muted"
							symbol="question-circle-full"
						/>
					</span>
				</label>

				<ClayInput
					id="nodeName"
					onChange={({target}) => {
						const filteredElements = elements.slice();

						filteredElements.splice(
							elements.findIndex(
								(element) => element.id === selectedItem.id
							),
							1
						);

						setErrors(
							checkIdErrors(filteredElements, errors, target)
						);
						setSelectedItemNewId(target.value);
					}}
					type="text"
					value={(selectedItemNewId ?? selectedItem?.id) || ''}
				/>

				<ClayForm.FeedbackItem>
					{(errors?.id?.duplicated || errors?.id?.empty) && (
						<>
							<ClayForm.FeedbackIndicator symbol="exclamation-full" />

							{errors.id.duplicated
								? Liferay.Language.get(
										'a-node-with-that-name-already-exists'
								  )
								: Liferay.Language.get(
										'this-field-is-required'
								  )}
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
						setSelectedItem({
							...selectedItem,
							data: {
								...selectedItem.data,
								description: target.value,
							},
						})
					}
					type="text"
					value={selectedItem?.data.description || ''}
				/>
			</ClayForm.Group>

			{selectedItem?.type === 'condition' && (
				<ScriptInput
					defaultScriptLanguage={selectedItem?.data.scriptLanguage}
					handleClickCapture={(scriptLanguage) =>
						setSelectedItem({
							...selectedItem,
							data: {
								...selectedItem.data,
								scriptLanguage,
							},
						})
					}
					inputValue={selectedItem?.data.script || ''}
					updateSelectedItem={({target}) =>
						setSelectedItem({
							...selectedItem,
							data: {
								...selectedItem.data,
								script: target.value,
							},
						})
					}
				/>
			)}
		</SidebarPanel>
	);
}

NodeInformation.propTypes = {
	errors: PropTypes.object.isRequired,
	setErrors: PropTypes.func.isRequired,
};
