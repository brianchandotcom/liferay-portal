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
import {ClayCheckbox} from '@clayui/form';
import {fetch, navigate, objectToFormData} from 'frontend-js-web';
import React, {useState} from 'react';

import CollapsablePanel from '../components/form/CollapsablePanel';
import TextField from '../components/form/TextField';
import {ManageCollaborators} from '../components/manage-collaborators-modal/ManageCollaborators';
import {showNotification} from '../util/util';

export default function PublicationTemplateEditView({
	actionUrl,
	collaboratorsProps,
	ctCollectionTemplateId,
	defaultCTCollectionTemplate,
	defaultSandboxCTCollectionTemplate,
	description,
	getTemplateCollaboratorsURL,
	name,
	namespace,
	publicationDescription,
	publicationName,
	redirect,
	saveButtonLabel,
	tokens,
}) {
	const [collaboratorData, setCollaboratorData] = useState(null);
	const [
		defaultCTCollectionTemplateField,
		setDefaultCTCollectionTemplateField,
	] = useState(defaultCTCollectionTemplate);
	const [
		defaultSandboxCTCollectionTemplateField,
		setDefaultSandboxCTCollectionTemplateField,
	] = useState(defaultSandboxCTCollectionTemplate);
	const [descriptionField, setDescriptionField] = useState(description);
	const [nameField, setNameField] = useState(name);
	const [
		publicationDescriptionField,
		setPublicationDescriptionField,
	] = useState(publicationDescription);
	const [publicationNameField, setPublicationNameField] = useState(
		publicationName
	);

	const [showModal, setShowModal] = useState(false);

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
			[`${namespace}defaultCTCollectionTemplate`]: defaultCTCollectionTemplateField,
			[`${namespace}defaultSandboxCTCollectionTemplate`]: defaultSandboxCTCollectionTemplateField,
		});

		fetch(actionUrl, {
			body: bodyContent,
			method: 'POST',
		})
			.then((response) => {
				if (response.status === 200) {
					const successMessage =
						ctCollectionTemplateId > 0
							? Liferay.Language.get(
									'successfully-edited-the-template'
							  )
							: Liferay.Language.get(
									'successfully-added-the-template'
							  );

					showNotification(
						successMessage,
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
				label={Liferay.Language.get('name')}
				onChange={(event) => {
					setNameField(event.target.value);
				}}
				placeholderValue={Liferay.Language.get(
					'publication-template-name-placeholder'
				)}
				required={true}
				validateLength={true}
			/>

			<TextField
				ariaLabel={Liferay.Language.get(
					'publication-template-description-placeholder'
				)}
				componentType="textarea"
				fieldValue={descriptionField}
				label={Liferay.Language.get('description')}
				onChange={(event) => {
					setDescriptionField(event.target.value);
				}}
				placeholderValue={Liferay.Language.get(
					'publication-template-description-placeholder'
				)}
				required={false}
			/>

			<ClayCheckbox
				checked={defaultCTCollectionTemplateField}
				label={Liferay.Language.get('default-template')}
				onChange={() =>
					setDefaultCTCollectionTemplateField(
						!defaultCTCollectionTemplateField
					)
				}
			/>

			<ClayCheckbox
				checked={defaultSandboxCTCollectionTemplateField}
				label={Liferay.Language.get('default-sandbox-template')}
				onChange={() =>
					setDefaultSandboxCTCollectionTemplateField(
						!defaultSandboxCTCollectionTemplateField
					)
				}
			/>

			<CollapsablePanel
				title={Liferay.Language.get('publication-information')}
			>
				<ClayAlert
					className="alert-autofit-stacked alert-indicator-start"
					displayType="info"
					title={Liferay.Language.get('info')}
				>
					{Liferay.Language.get('publication-template-token-help')}

					<ul>
						{tokens.map((token, i) => (
							<li key={i}>{token}</li>
						))}
					</ul>
				</ClayAlert>

				<TextField
					ariaLabel={Liferay.Language.get(
						'publication-name-placeholder'
					)}
					componentType="input"
					fieldValue={publicationNameField}
					label={Liferay.Language.get('publication-name')}
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
					label={Liferay.Language.get('publication-description')}
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
				helpTooltip={Liferay.Language.get(
					'publication-collaborators-help'
				)}
				title={Liferay.Language.get('publication-collaborators')}
			>
				<ManageCollaborators
					getTemplateCollaboratorsURL={
						ctCollectionTemplateId !== 0
							? getTemplateCollaboratorsURL
							: null
					}
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
						>
							{Liferay.Language.get('add-users')}
						</ClayButton>
					}
					{...collaboratorsProps}
				/>
			</CollapsablePanel>

			<div className="button-group">
				<ClayButton
					disabled={
						!nameField.length ||
						!publicationNameField.length ||
						nameField.length > 75
					}
					displayType="primary"
					id="saveButton"
					onClick={() => handleSubmit()}
					type="submit"
				>
					{saveButtonLabel}
				</ClayButton>

				<ClayButton
					displayType="secondary"
					onClick={() => navigate(redirect)}
					type="cancel"
				>
					{Liferay.Language.get('cancel')}
				</ClayButton>
			</div>
		</div>
	);
}
