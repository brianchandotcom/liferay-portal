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
	const [name, setName] = useState(Liferay.Language.get('new-test'));
	const [description, setDescription] = useState('');
	const {portletNamespace} = useContext(PageEditorContext);

	const formId = `${portletNamespace}_createExperiement`;

	return (
		<React.Fragment>
			{visible && (
				<ClayModal onClose={_closeModal} size="lg">
					{onClose => (
						<React.Fragment>
							<ClayModal.Header>
								{Liferay.Language.get('test-settings')}
							</ClayModal.Header>
							<ClayModal.Body>
								<form id={formId} onSubmit={_handleSaveAction}>
									<div className="form-group">
										<label>
											{Liferay.Language.get('name')}
										</label>
										<input
											className="form-control"
											name="name"
											onChange={_inputValueGetter(
												setName
											)}
											value={name}
										/>
									</div>

									<div className="form-group">
										<label>
											{Liferay.Language.get(
												'description'
											)}
										</label>
										<textarea
											className="form-control"
											name="description"
											onChange={_inputValueGetter(
												setDescription
											)}
											value={description}
										/>
									</div>
								</form>
							</ClayModal.Body>
							<ClayModal.Footer
								first={
									<ClayButton.Group spaced>
										<ClayButton
											displayType="secondary"
											name="cancel"
											onClick={onClose}
										>
											{Liferay.Language.get('cancel')}
										</ClayButton>
									</ClayButton.Group>
								}
								last={
									<ClayButton
										form={formId}
										name="save"
										onClick={_handleSaveAction}
									>
										{Liferay.Language.get('save')}
									</ClayButton>
								}
							/>
						</React.Fragment>
					)}
				</ClayModal>
			)}
		</React.Fragment>
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
