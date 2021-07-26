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

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayModal, {useModal} from '@clayui/modal';
import PropTypes from 'prop-types';
import React, {useRef} from 'react';

const ImportStructureModal = ({namespace, onClose: onCloseProp}) => {
	const {observer, onClose} = useModal({
		onClose: onCloseProp,
	});
	const inputFileRef = useRef();

	const nameInputId = `${namespace}_name`;
	const jsonFileInputId = `${namespace}_jsonFile`;

	return (
		<ClayModal observer={observer} size="md">
			<ClayModal.Header>
				{Liferay.Language.get('import-structure')}
			</ClayModal.Header>
			<ClayModal.Body>
				<ClayAlert
					displayType="info"
					title={Liferay.Language.get('info')}
				>
					{Liferay.Language.get(
						'once-you-click-import-the-process-will-run-in-the-background-this-may-take-a-while'
					)}
				</ClayAlert>
				<ClayForm.Group>
					<label htmlFor={nameInputId}>
						{Liferay.Language.get('name')}
					</label>
					<ClayInput
						id={nameInputId}
						name={`${namespace}_name`}
						type="text"
					/>
					<label htmlFor={jsonFileInputId}>
						{Liferay.Language.get('json-file')}
					</label>
					<ClayInput.Group>
						<ClayInput.GroupItem prepend>
							<ClayInput id={jsonFileInputId} type="text" />
						</ClayInput.GroupItem>
						<ClayInput.GroupItem append shrink>
							<ClayButton
								displayType="secondary"
								onClick={() => inputFileRef.current.click()}
							>
								{Liferay.Language.get('select')}
							</ClayButton>
						</ClayInput.GroupItem>
						<ClayInput.GroupItem shrink>
							<ClayButton displayType="secondary">
								{Liferay.Language.get('clear')}
							</ClayButton>
						</ClayInput.GroupItem>
					</ClayInput.Group>
				</ClayForm.Group>
				<input
					className="d-none"
					name={`${namespace}_jsonFile`}
					ref={inputFileRef}
					type="file"
				/>
			</ClayModal.Body>
			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton displayType="secondary" onClick={onClose}>
							{Liferay.Language.get('cancel')}
						</ClayButton>
						<ClayButton>
							{Liferay.Language.get('import')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
};

ImportStructureModal.propTypes = {
	namespace: PropTypes.string,
	onClose: PropTypes.func.isRequired,
};

export default ImportStructureModal;
