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

import ClayForm, {ClayInput, ClayToggle} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React, {useContext} from 'react';
import {isEdge} from 'react-flow-renderer';

import {DefinitionBuilderContext} from '../../../../DefinitionBuilderContext';
import {defaultLanguageId} from '../../../../constants';
import {DiagramBuilderContext} from '../../../DiagramBuilderContext';
import SidebarPanel from '../SidebarPanel';
import {checkIdErrors, checkLabelErrors, getUpdatedLabelItem} from './utils';

export default function EdgeInformation({errors, setErrors}) {
	const {elements, selectedLanguageId, setElements} = useContext(
		DefinitionBuilderContext
	);
	const {
		selectedItem,
		selectedItemNewId,
		setSelectedItem,
		setSelectedItemNewId,
	} = useContext(DiagramBuilderContext);

	const onToggleDefault = (defaultEdge) => {
		if (defaultEdge) {
			setElements((previousElements) =>
				previousElements.map((element) => {
					if (
						isEdge(element) &&
						element.source === selectedItem.source
					) {
						element.data.defaultEdge = false;
					}

					return element;
				})
			);
		}

		setSelectedItem({
			...selectedItem,
			data: {
				...selectedItem.data,
				defaultEdge,
			},
		});
	};

	return (
		<SidebarPanel panelTitle={Liferay.Language.get('transitions')}>
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
					id="edgeLabel"
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
					errors.id.duplicated || errors.id.empty ? 'has-error' : ''
				}
			>
				<label htmlFor="transitionId">
					<span>
						{`${Liferay.Language.get(
							'transition'
						)} ${Liferay.Language.get('id')}`}
					</span>

					<span className="ml-1 mr-1 text-warning">*</span>

					<span
						title={Liferay.Language.get(
							'id-is-the-transition-identifier'
						)}
					>
						<ClayIcon
							className="text-muted"
							symbol="question-circle-full"
						/>
					</span>
				</label>

				<ClayInput
					id="transitionId"
					onChange={({target}) => {
						setErrors(checkIdErrors(elements, errors, target));
						setSelectedItemNewId(target.value);
					}}
					type="text"
					value={(selectedItemNewId ?? selectedItem?.id) || ''}
				/>

				<ClayForm.FeedbackItem>
					{(errors.id.duplicated || errors.id.empty) && (
						<>
							<ClayForm.FeedbackIndicator symbol="exclamation-full" />

							{errors.id.duplicated
								? Liferay.Language.get(
										'a-transition-with-that-id-already-exists'
								  )
								: Liferay.Language.get(
										'this-field-is-required'
								  )}
						</>
					)}
				</ClayForm.FeedbackItem>
			</ClayForm.Group>

			<ClayForm.Group>
				<label htmlFor="toggleDefault">
					{Liferay.Language.get('default')}

					<span className="ml-1 mr-1 text-warning">*</span>
				</label>

				<div>
					<ClayToggle
						id="toggleDefault"
						label={
							selectedItem?.data?.defaultEdge
								? Liferay.Language.get('true')
								: Liferay.Language.get('false')
						}
						onToggle={onToggleDefault}
						toggled={selectedItem?.data?.defaultEdge}
					/>
				</div>
			</ClayForm.Group>
		</SidebarPanel>
	);
}

EdgeInformation.propTypes = {
	errors: PropTypes.object.isRequired,
	setErrors: PropTypes.func.isRequired,
};
