/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 */

import ClayAlert from '@clayui/alert';
import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import ClayToolbar from '@clayui/toolbar';
import {TranslationAdminSelector} from 'frontend-js-components-web';
import PropTypes from 'prop-types';
import React, {useContext, useEffect, useRef} from 'react';
import {isEdge, isNode} from 'react-flow-renderer';

import {DefinitionBuilderContext} from '../../../DefinitionBuilderContext';
import {defaultLanguageId} from '../../../constants';
import {xmlNamespace} from '../../../source-builder/constants';
import DeserializeUtil from '../../../source-builder/deserializeUtil';
import {serializeDefinition} from '../../../source-builder/serializeUtil';
import XMLUtil from '../../../source-builder/xmlUtil';
import {getAvailableLocalesObject} from '../../../util/availableLocales';
import {
	publishDefinitionRequest,
	saveDefinitionRequest,
} from '../../../util/fetchUtil';
import {isObjectEmpty} from '../../../util/utils';

export default function UpperToolbar({
	displayNames,
	isView,
	languageIds,
	portletNamespace,
}) {
	const {
		active,
		alertMessage,
		alertType,
		blockingErrors,
		currentEditor,
		definitionDescription,
		definitionId,
		definitionName,
		definitionTitle,
		elements,
		selectedLanguageId,
		setAlertMessage,
		setAlertType,
		setDefinitionDescription,
		setDefinitionId,
		setDefinitionTitle,
		setDeserialize,
		setElements,
		setSelectedLanguageId,
		setShowAlert,
		setShowDefinitionInfo,
		setShowInvalidContentMessage,
		setSourceView,
		setTranslations,
		setVersion,
		showAlert,
		sourceView,
		translations,
		version,
	} = useContext(DefinitionBuilderContext);

	function setAlert(alertMessage, alertType, showAlert) {
		setAlertMessage(alertMessage);
		setAlertType(alertType);
		setShowAlert(showAlert);
	}

	const inputRef = useRef(null);

	const availableLocales = getAvailableLocalesObject(
		displayNames,
		languageIds
	);
	const errorTitle = () => {
		if (blockingErrors.errorType === 'duplicated') {
			return Liferay.Language.get('you-have-the-same-id-in-two-nodes');
		}
		if (blockingErrors.errorType === 'emptyField') {
			return Liferay.Language.get('some-fields-need-to-be-filled');
		}
		else {
			return Liferay.Language.get('error');
		}
	};
	const getXMLContent = (exporting) => {
		let currentDescription;
		let currentElements;
		let xmlContent;

		if (currentEditor && !exporting) {
			xmlContent = currentEditor.getData();
		}
		else {
			if (sourceView) {
				const deserializeUtil = new DeserializeUtil();
				const xmlDefinition = currentEditor.getData();

				deserializeUtil.updateXMLDefinition(xmlDefinition);
				const metadata = deserializeUtil.getMetadata();

				currentDescription = metadata.description;
				setDefinitionDescription(currentDescription);

				currentElements = deserializeUtil.getElements();
				setElements(currentElements);
			}
			else {
				currentDescription = definitionDescription;
				currentElements = elements;
			}
			xmlContent = serializeDefinition(
				xmlNamespace,
				{
					description: currentDescription,
					name: definitionName,
					version,
				},
				currentElements.filter(isNode),
				currentElements.filter(isEdge),
				exporting
			);
		}

		return xmlContent;
	};

	const onSelectedLanguageIdChange = (id) => {
		if (id) {
			setSelectedLanguageId(id);
		}
	};

	const onInputBlur = () => {
		if (definitionTitle) {
			let languageId = defaultLanguageId;

			if (selectedLanguageId) {
				languageId = selectedLanguageId;
			}

			setTranslations((previous) => {
				return {...previous, [languageId]: definitionTitle};
			});
		}
	};

	const definitionNotPublished = version === 0 || !active;

	const redirectToSavedDefinition = (name, version) => {
		const definitionURL = new URL(window.location.href);

		definitionURL.searchParams.set(
			portletNamespace + 'draftVersion',
			Number.parseFloat(version).toFixed(1)
		);
		definitionURL.searchParams.set(portletNamespace + 'name', name);

		window.location.replace(definitionURL);
	};

	const publishDefinition = () => {
		let alertMessage;

		if (!definitionTitle) {
			alertMessage = Liferay.Language.get('name-workflow-before-publish');
			setAlert(alertMessage, 'danger', true);
		}
		else if (blockingErrors.errorType !== '') {
			switch (blockingErrors.errorType) {
				case 'emptyField':
					alertMessage = Liferay.Language.get(
						'please-fill-out-the-fields-before-saving-or-publishing'
					);
					break;
				case 'duplicated':
					alertMessage = Liferay.Language.get(
						'please-rename-this-with-another-words'
					);
					break;
				default:
					alertMessage = Liferay.Language.get('error');
			}
			setAlert(alertMessage, 'danger', true);
		}
		else {
			if (definitionNotPublished) {
				alertMessage = Liferay.Language.get(
					'workflow-published-successfully'
				);
			}
			else {
				alertMessage = Liferay.Language.get(
					'workflow-updated-successfully'
				);
			}

			publishDefinitionRequest({
				active,
				content: getXMLContent(true),
				name: definitionId,
				title: definitionTitle,
				title_i18n: translations,
				version,
			}).then((response) => {
				if (response.ok) {
					response.json().then(({name, version}) => {
						setDefinitionId(name);
						setVersion(parseInt(version, 10));
						if (version === '1') {
							localStorage.setItem('firstPublished', true);
							redirectToSavedDefinition(name, version);
						}
						else {
							setAlert(alertMessage, 'success', true);
						}
					});
				}
				else {
					response.json().then(({title}) => {
						setAlert(title, 'danger', true);
					});
				}
			});
		}
	};

	const saveDefinition = () => {
		const successMessage = Liferay.Language.get('workflow-saved');
		const duplicatedAlertMessage = Liferay.Language.get(
			'please-rename-this-with-another-words'
		);
		const emptyFieldAlertMessage = Liferay.Language.get(
			'please-fill-out-the-fields-before-saving-or-publishing'
		);

		if (blockingErrors.errorType === 'emptyField') {
			setAlert(emptyFieldAlertMessage, 'danger', true);
		}

		if (blockingErrors.errorType === 'duplicated') {
			setAlert(duplicatedAlertMessage, 'danger', true);
		}

		if (blockingErrors.errorType === '') {
			saveDefinitionRequest({
				active,
				content: getXMLContent(true),
				name: definitionId,
				title: definitionTitle,
				title_i18n: translations,
				version,
			}).then((response) => {
				if (response.ok) {
					response.json().then(({name, version}) => {
						setDefinitionId(name);
						setVersion(parseInt(version, 10));
						if (version === '1') {
							localStorage.setItem('firstSaved', true);
							redirectToSavedDefinition(name, version);
						}
						else {
							setAlert(successMessage, 'success', true);
						}
					});
				}
			});
		}
	};

	useEffect(() => {
		if (isObjectEmpty(translations)) {
			setTranslations({
				[defaultLanguageId]: Liferay.Language.get('new-workflow'),
			});
		}

		if (selectedLanguageId) {
			setDefinitionTitle(translations[selectedLanguageId]);
		}
	}, [selectedLanguageId, setDefinitionTitle, setTranslations, translations]);

	useEffect(() => {
		if (localStorage.getItem('firstSaved')) {
			setAlert(Liferay.Language.get('workflow-saved'), 'success', true);
			localStorage.removeItem('firstSaved');
		}
		else if (localStorage.getItem('firstPublished')) {
			setAlert(
				Liferay.Language.get('workflow-published-successfully'),
				'success',
				true
			);
			localStorage.removeItem('firstPublished');
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<>
			<ClayToolbar className="upper-toolbar">
				<ClayLayout.ContainerFluid>
					<ClayToolbar.Nav>
						<ClayToolbar.Item>
							<TranslationAdminSelector
								activeLanguageIds={languageIds}
								adminMode
								availableLocales={availableLocales}
								defaultLanguageId={defaultLanguageId}
								onSelectedLanguageIdChange={
									onSelectedLanguageIdChange
								}
								translations={translations}
							/>
						</ClayToolbar.Item>

						<ClayToolbar.Item expand>
							<ClayInput
								autoComplete="off"
								className="form-control-inline"
								disabled={isView}
								id="definition-title"
								onBlur={() => onInputBlur()}
								onChange={({target: {value}}) => {
									setDefinitionTitle(value);
								}}
								placeholder={Liferay.Language.get(
									'untitled-workflow'
								)}
								ref={inputRef}
								type="text"
								value={definitionTitle || ''}
							/>
						</ClayToolbar.Item>

						{version !== 0 && (
							<ClayToolbar.Item>
								<ClayButtonWithIcon
									displayType="secondary"
									onClick={() =>
										setShowDefinitionInfo(
											(previous) => !previous
										)
									}
									symbol="info-circle-open"
								/>
							</ClayToolbar.Item>
						)}

						<ClayToolbar.Item>
							<ClayButton
								displayType="secondary"
								onClick={() => {
									window.history.back();
								}}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>
						</ClayToolbar.Item>

						{definitionNotPublished && (
							<ClayToolbar.Item>
								<ClayButton
									disabled={isView}
									displayType="secondary"
									onClick={saveDefinition}
								>
									{Liferay.Language.get('save')}
								</ClayButton>
							</ClayToolbar.Item>
						)}

						<ClayToolbar.Item>
							<ClayButton
								disabled={isView}
								displayType="primary"
								onClick={publishDefinition}
							>
								{definitionNotPublished
									? Liferay.Language.get('publish')
									: Liferay.Language.get('update')}
							</ClayButton>
						</ClayToolbar.Item>

						<ClayToolbar.Item>
							{sourceView ? (
								<ClayButtonWithIcon
									displayType="secondary"
									onClick={() => {
										if (
											XMLUtil.validateDefinition(
												currentEditor.getData()
											)
										) {
											setSourceView(false);
											setDeserialize(true);
										}
										else {
											setShowInvalidContentMessage(true);
										}
									}}
									symbol="rules"
									title={Liferay.Language.get('diagram-view')}
								/>
							) : (
								<ClayButtonWithIcon
									displayType="secondary"
									onClick={() => setSourceView(true)}
									symbol="code"
									title={Liferay.Language.get('source-view')}
								/>
							)}
						</ClayToolbar.Item>
					</ClayToolbar.Nav>
				</ClayLayout.ContainerFluid>
			</ClayToolbar>

			{showAlert && (
				<ClayAlert.ToastContainer>
					<ClayAlert
						autoClose={5000}
						displayType={alertType}
						onClose={() => setShowAlert(false)}
						title={
							alertType === 'success'
								? `${Liferay.Language.get('success')}:`
								: `${errorTitle().slice(0, -1)}:`
						}
					>
						{alertMessage}
					</ClayAlert>
				</ClayAlert.ToastContainer>
			)}
		</>
	);
}

UpperToolbar.propTypes = {
	displayNames: PropTypes.arrayOf(PropTypes.string).isRequired,
	languageIds: PropTypes.arrayOf(PropTypes.string).isRequired,
	title: PropTypes.PropTypes.string.isRequired,
	translations: PropTypes.object,
	version: PropTypes.PropTypes.string.isRequired,
};
