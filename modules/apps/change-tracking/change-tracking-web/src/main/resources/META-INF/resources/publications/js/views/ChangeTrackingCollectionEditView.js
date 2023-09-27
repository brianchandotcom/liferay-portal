/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayForm, {ClaySelect} from '@clayui/form';
import {fetch, navigate, objectToFormData, sub} from 'frontend-js-web';
import React, {useState} from 'react';

import TextField from '../components/form/TextField';
import {showNotification} from '../util/util';

export default function ChangeTrackingCollectionEditView({
	actionUrl,
	ctCollectionId,
	ctCollectionTemplates,
	ctCollectionTemplatesData,
	ctRemoteId,
	defaultCTCollectionTemplateId,
	descriptionFieldMaxLength,
	inviteUsersURL,
	nameFieldMaxLength,
	namespace,
	publicationDescription,
	publicationName,
	redirect,
	revertingPublication,
	saveButtonLabel,
	showTemplates,
}) {
	const templates = JSON.parse(ctCollectionTemplates);
	const data = JSON.parse(ctCollectionTemplatesData);

	const [nameField, setNameField] = useState(
		defaultCTCollectionTemplateId > 0
			? data[defaultCTCollectionTemplateId].name
			: publicationName
	);
	const [descriptionField, setDescriptionField] = useState(
		defaultCTCollectionTemplateId > 0
			? data[defaultCTCollectionTemplateId].description
			: publicationDescription
	);
	const [publishTimeField, setPublishTimeField] = useState(null);
	const [saveButtonDisabled, setSaveButtonDisabled] = useState(
		revertingPublication
	);
	const [ctCollectionTemplateId, setCtCollectionTemplateId] = useState(
		defaultCTCollectionTemplateId ? defaultCTCollectionTemplateId : 0
	);
	const handleSubmit = (event) => {
		event.preventDefault();

		setSaveButtonDisabled(true);

		const bodyContent = objectToFormData({
			[`${namespace}ctCollectionId`]: ctCollectionId,
			[`${namespace}ctRemoteId`]: ctRemoteId,
			[`${namespace}name`]: nameField,
			[`${namespace}description`]: descriptionField,
			[`${namespace}publishTime`]: publishTimeField,
		});

		fetch(actionUrl, {
			body: bodyContent,
			method: 'POST',
		})
			.then((response) => {
				if (response.status === 200) {
					return response.json();
				}

				showNotification(response.statusText, true);
			})
			.then((responseJson) => {
				if (responseJson.errorMessage) {
					showNotification(responseJson.errorMessage, true);

					return responseJson;
				}

				let action = 'created';

				if (revertingPublication) {
					action = 'reverted';
				}
				else if (ctCollectionId > 0) {
					action = 'updated';
				}

				showNotification(
					`Successfully ${action} the collection`,
					false
				);

				if (
					ctCollectionTemplateId > 0 &&
					responseJson.ctCollectionId &&
					!!data[ctCollectionTemplateId].userIds
				) {
					sendInvite(
						responseJson.ctCollectionId,
						data[ctCollectionTemplateId]
							.publicationsUserRoleUserIds,
						data[ctCollectionTemplateId].roleValues,
						data[ctCollectionTemplateId].userIds
					);
				}

				return responseJson;
			})
			.then((responseJson) => {
				if (responseJson.redirect === true) {
					if (revertingPublication === true) {
						navigate(responseJson.revertedRedirectURL);
					}
					else {
						navigate(redirect);
					}
				}
				else {
					setSaveButtonDisabled(false);
				}
			})
			.catch((error) => {
				showNotification(error.message, true);
			});
	};

	const onSelectValueChange = (value) => {
		setCtCollectionTemplateId(value);
		setNameField(data[value].name);
		setDescriptionField(data[value].description);
	};

	const resetForm = () => {
		setCtCollectionTemplateId(null);
		setNameField(null);
		setDescriptionField(null);
	};

	const sendInvite = (
		ctCollectionId,
		publicationsUserRoleUserIds,
		roleValues,
		userIds
	) => {
		if (!userIds || !userIds.length) {
			return;
		}

		const formData = {
			[`${namespace}ctCollectionId`]: ctCollectionId,
			[`${namespace}publicationsUserRoleUserIds`]: publicationsUserRoleUserIds.join(
				','
			),
			[`${namespace}roleValues`]: roleValues.join(','),
			[`${namespace}userIds`]: userIds.join(','),
		};

		fetch(inviteUsersURL, {
			body: objectToFormData(formData),
			method: 'POST',
		})
			.then((response) => response.json())
			.then(({errorMessage, successMessage}) => {
				if (errorMessage) {
					showNotification(errorMessage, true);

					return;
				}

				showNotification(successMessage);
			})
			.catch((error) => {
				showNotification(error.message, true);
			});
	};

	return (
		<div className="sheet sheet-lg">
			<ClayForm onSubmit={handleSubmit}>
				{revertingPublication && (
					<>
						<ClayAlert
							displayType="info"
							title={Liferay.Language.get('info')}
						>
							{Liferay.Language.get(
								'reverting-creates-a-new-publication-with-the-reverted-changes'
							)}
						</ClayAlert>

						<h3 className="sheet-subtitle">
							{Liferay.Language.get(
								'publication-with-reverted-changes'
							)}
						</h3>
					</>
				)}

				{showTemplates && (
					<ClayForm.Group>
						<label htmlFor="templateSelector">Template</label>

						<ClaySelect
							aria-label={sub(
								Liferay.Language.get('select-x'),
								Liferay.Language.get('template')
							)}
							defaultValue={
								defaultCTCollectionTemplateId
									? defaultCTCollectionTemplateId
									: 0
							}
							id="templateSelector"
							onChange={(event) => {
								onSelectValueChange(event.target.value);
							}}
						>
							<ClaySelect.Option
								disabled
								hidden
								label={Liferay.Language.get(
									'no-template-selected'
								)}
								value={0}
							/>

							{templates.map((item) => (
								<ClaySelect.Option
									key={item.ctCollectionTemplateId}
									label={item.name}
									value={item.ctCollectionTemplateId}
								/>
							))}
						</ClaySelect>
					</ClayForm.Group>
				)}

				<TextField
					ariaLabel={Liferay.Language.get('name')}
					componentType="input"
					fieldValue={nameField}
					label={Liferay.Language.get('name')}
					maxLength={nameFieldMaxLength}
					name="publication-name"
					onChange={(event) => {
						setNameField(event.target.value);
					}}
					placeholderValue={Liferay.Language.get(
						'publication-name-placeholder'
					)}
					required={true}
					validateLength={true}
				/>

				<TextField
					ariaLabel={Liferay.Language.get('description')}
					componentType="textarea"
					fieldValue={descriptionField}
					label={Liferay.Language.get('description')}
					maxLength={descriptionFieldMaxLength}
					name="publication-description"
					onChange={(event) => {
						setDescriptionField(event.target.value);
					}}
					placeholderValue={Liferay.Language.get(
						'publication-description-placeholder'
					)}
					required={false}
					validateLength={true}
				/>

				{revertingPublication && (
					<fieldset className="publications-fieldset">
						<legend className="fieldset-legend">
							<span className="legend">
								{Liferay.Language.get(
									'when-do-you-want-to-publish'
								)}
							</span>
						</legend>

						<div className="panel-body">
							<div className="col-10 row">
								<div className="col-5">
									<div className="autofit-row">
										<div className="autofit-col">
											<input
												className="field"
												id="publishTimeNow"
												name="publishTime"
												onChange={(event) => {
													if (event.target.checked) {
														setPublishTimeField(
															event.target.value
														);
													}
													setSaveButtonDisabled(
														false
													);
												}}
												type="radio"
												value="now"
											/>
										</div>

										<div className="autofit-col autofit-col-expand">
											<label
												className="radio-inline"
												htmlFor="publishTimeNow"
											>
												<div className="publications-radio-label">
													{Liferay.Language.get(
														'now'
													)}
												</div>

												<div className="publications-radio-help">
													{Liferay.Language.get(
														'revert-your-changes-to-production-immediately'
													)}
												</div>
											</label>
										</div>
									</div>
								</div>

								<div className="col-6">
									<div className="autofit-row">
										<div className="autofit-col">
											<input
												className="field"
												id="publishTimeLater"
												name="publishTime"
												onChange={(event) => {
													if (event.target.checked) {
														setPublishTimeField(
															event.target.value
														);
													}
													setSaveButtonDisabled(
														false
													);
												}}
												type="radio"
												value="later"
											/>
										</div>

										<div className="autofit-col autofit-col-expand">
											<label
												className="radio-inline"
												htmlFor="publishTimeLater"
											>
												<div className="publications-radio-label">
													{Liferay.Language.get(
														'later'
													)}
												</div>

												<div className="publications-radio-help">
													{Liferay.Language.get(
														'make-additional-changes-and-publish-them-when-you-are-ready'
													)}
												</div>
											</label>
										</div>
									</div>
								</div>
							</div>
						</div>
					</fieldset>
				)}

				<ClayForm.Group>
					<ClayButton.Group spaced>
						<ClayButton
							disabled={
								saveButtonDisabled ||
								(nameField &&
									(nameField.length > nameFieldMaxLength ||
										nameField.length < 1)) ||
								(descriptionField &&
									descriptionField.length >
										descriptionFieldMaxLength)
							}
							displayType="primary"
							id="saveButton"
							type="submit"
						>
							{saveButtonLabel}
						</ClayButton>

						<ClayButton
							displayType="secondary"
							onClick={(event) => {
								event.preventDefault();

								resetForm();
								navigate(redirect);
							}}
							type="cancel"
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>
					</ClayButton.Group>
				</ClayForm.Group>
			</ClayForm>
		</div>
	);
}
