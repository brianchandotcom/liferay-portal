/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

/* eslint no-unused-vars: "warn" */

import React, {useState, useContext} from 'react';
import PropTypes from 'prop-types';
import ClayButton from '@clayui/button';
import ClayModal from '@clayui/modal';
import {PageEditorContext} from '../../../utils/PageEditorContext.es';

/**
 * Second order function to generate a function from a listener
 * that uses the `target.value` of an event
 */
const _inputValueGetter = listener => event => listener(event.target.value);

/**
 * Wrapper for the modal to create an Experiments
 */
function CreateExperimentModal({setVisible, visible, onCreateExperiment}) {
	const [name, setName] = useState(Liferay.Language.get('experiment-new'));
	const [description, setDescription] = useState('');
	const {portletNamespace} = useContext(PageEditorContext);

	const formId = `${portletNamespace}_createExperiement`;

	return (
		<>
			{visible && (
				<ClayModal onClose={_closeModal} size='lg'>
					{onClose => (
						<>
							<ClayModal.Header>
								{Liferay.Language.get('experiment-settings')}
							</ClayModal.Header>
							<ClayModal.Body>
								<form id={formId} onSubmit={_handleSaveAction}>
									<div className='form-group'>
										<label>
											{Liferay.Language.get('name')}
										</label>
										<input
											className='form-control'
											name='name'
											value={name}
											onChange={_inputValueGetter(
												setName
											)}
										/>
									</div>

									<div className='form-group'>
										<label>
											{Liferay.Language.get(
												'description'
											)}
										</label>
										<textarea
											className='form-control'
											name='description'
											value={description}
											onChange={_inputValueGetter(
												setDescription
											)}
										/>
									</div>
								</form>
							</ClayModal.Body>
							<ClayModal.Footer
								first={
									<ClayButton.Group spaced>
										<ClayButton
											name='cancel'
											onClick={onClose}
											displayType='secondary'
										>
											{Liferay.Language.get('cancel')}
										</ClayButton>
									</ClayButton.Group>
								}
								last={
									<ClayButton
										name='save'
										form={formId}
										onClick={_handleSaveAction}
									>
										{Liferay.Language.get('save')}
									</ClayButton>
								}
							/>
						</>
					)}
				</ClayModal>
			)}
		</>
	);

	function _closeModal() {
		setVisible(false);
	}

	function _handleSaveAction(event) {
		event.preventDefault();
		onCreateExperiment({name, description});
		_closeModal();
	}
}

CreateExperimentModal.propTypes = {
	setVisible: PropTypes.func.isRequired,
	visible: PropTypes.bool.isRequired,
	onCreateExperiment: PropTypes.func.isRequired
};

export default CreateExperimentModal;
