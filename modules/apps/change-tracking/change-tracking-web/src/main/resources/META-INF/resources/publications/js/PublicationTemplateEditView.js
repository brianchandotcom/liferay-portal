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

import ClayButton from '@clayui/button';
import {fetch, navigate, objectToFormData} from 'frontend-js-web';
import React, {useState} from 'react';

import {ManageCollaborators} from './ManageCollaborators';
import CollapsablePanel from './components/form/CollapsablePanel';
import TextField from './components/form/TextField';
import {showNotification} from './util/util';

export default function PublicationTemplateEditView({
	actionUrl,
	collaboratorsProps,
	ctCollectionTemplateId,
	description,
	name,
	namespace,
	publicationDescription,
	publicationName,
	redirect,
	saveButtonLabel,
}) {
	const [showModal, setShowModal] = useState(false);
	const [collaboratorData, setCollaboratorData] = useState(null);
	const [nameField, setNameField] = useState(name);
	const [descriptionField, setDescriptionField] = useState(description);
	const [publicationNameField, setPublicationNameField] = useState(
		publicationName
	);
	const [
		publicationDescriptionField,
		setPublicationDescriptionField,
	] = useState(publicationDescription);

	const afterSubmitNotification = () => {
		setShowModal(false);
		setCollaboratorData(null);
	};

	const handleSubmit = () => {
		const bodyContent = objectToFormData({
			[`${namespace}name`]: nameField,
			[`${namespace}ctCollectionTemplateId`]: ctCollectionTemplateId,
			[`${namespace}description`]: descriptionField,
			[`${namespace}publicationName`]: publicationNameField,
			[`${namespace}publicationDescription`]: publicationDescriptionField,
			[`${namespace}publicationsUserRoleUserIds`]: collaboratorData
				? collaboratorData['publicationsUserRoleUserIds']
				: null,
			[`${namespace}roleValues`]: collaboratorData
				? collaboratorData['roleValues']
				: null,
			[`${namespace}userIds`]: collaboratorData
				? collaboratorData['userIds']
				: null,
		});

		fetch(actionUrl, {
			body: bodyContent,
			method: 'POST',
		})
			.then((response) => {
				if (response.status === 200) {
					showNotification(
						'Successfully added template',
						false,
						afterSubmitNotification
					);

					if (response.redirected) {
						navigate(response.url);
					}

					return;
				}

				showNotification(
					response.statusText,
					true,
					afterSubmitNotification
				);

				if (response.redirected) {
					navigate(response.url);
				}
			})
			.catch((error) => {
				showNotification(error.message, true, afterSubmitNotification);
			});
	};

	return (
		<div className="sheet sheet-lg">
			<TextField
				ariaLabel={Liferay.Language.get(
					'publication-template-name-placeholder'
				)}
				componentType="input"
				fieldValue={nameField}
				label="Name"
				onChange={(event) => {
					setNameField(event.target.value);
				}}
				placeholderValue={Liferay.Language.get(
					'publication-template-name-placeholder'
				)}
				required={true}
			/>

			<TextField
				ariaLabel={Liferay.Language.get(
					'publication-template-description-placeholder'
				)}
				componentType="textarea"
				fieldValue={descriptionField}
				label="Description"
				onChange={(event) => {
					setDescriptionField(event.target.value);
				}}
				placeholderValue={Liferay.Language.get(
					'publication-template-description-placeholder'
				)}
				required={false}
			/>

			<CollapsablePanel title="Publication Information">
				<TextField
					ariaLabel={Liferay.Language.get(
						'publication-name-placeholder'
					)}
					componentType="input"
					fieldValue={publicationNameField}
					label="Publication Name"
					onChange={(event) => {
						setPublicationNameField(event.target.value);
					}}
					placeholderValue={Liferay.Language.get(
						'publication-name-placeholder'
					)}
					required={true}
				/>

				<TextField
					ariaLabel={Liferay.Language.get(
						'publication-description-placeholder'
					)}
					componentType="textarea"
					fieldValue={publicationDescriptionField}
					label="Publication Description"
					onChange={(event) => {
						setPublicationDescriptionField(event.target.value);
					}}
					placeholderValue={Liferay.Language.get(
						'publication-description-placeholder'
					)}
					required={false}
				/>
			</CollapsablePanel>

			<CollapsablePanel
				helpTooltip="publication-collaborators-help"
				title="Publication Collaborators"
			>
				<ManageCollaborators
					isPublicationTemplate={true}
					setCollaboratorData={setCollaboratorData}
					setShowModal={setShowModal}
					showModal={showModal}
					trigger={
						<ClayButton
							displayType="info"
							onClick={() => setShowModal(true)}
							small
							type="button"
							value="asdf"
						>
							Invite Users
						</ClayButton>
					}
					{...collaboratorsProps}
				/>
			</CollapsablePanel>

			<div className="button-holder">
				<ClayButton
					displayType="primary"
					id="saveButton"
					onClick={() => handleSubmit()}
					type="submit"
					value={Liferay.Language.get(saveButtonLabel)}
				>
					{Liferay.Language.get(saveButtonLabel)}
				</ClayButton>

				<ClayButton
					displayType="secondary"
					onClick={() => navigate(redirect)}
					type="cancel"
				>
					Cancel
				</ClayButton>
			</div>
		</div>
	);
}
