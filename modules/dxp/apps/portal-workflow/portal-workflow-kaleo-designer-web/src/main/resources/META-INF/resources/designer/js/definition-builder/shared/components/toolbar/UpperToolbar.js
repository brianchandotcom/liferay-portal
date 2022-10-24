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
import React, {useContext, useEffect, useRef, useState} from 'react';
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
		definitionTitle,
		definitionTitleTranslations,
		elements,
		selectedLanguageId,
		setAlertMessage,
		setAlertType,
		setBlockingErrors,
		setDefinitionDescription,
		setDefinitionId,
		setDefinitionName,
		setDefinitionTitle,
		setDefinitionTitleTranslations,
		setDeserialize,
		setElements,
		setSelectedLanguageId,
		setShowAlert,
		setShowDefinitionInfo,
		setShowInvalidContentMessage,
		setSourceView,
		setVersion,
		showAlert,
		sourceView,
		version,
	} = useContext(DefinitionBuilderContext);

	const [translations, setTranslations] = useState({});

	function findEmptyElements(element, language) {
		if (element.data.label && !(language in element.data.label)) {
			return true;
		}
	}

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
			return Liferay.Language.get(
				'you-have-the-same-id-in-two-nodes'
			).slice(0, -1);
		}
		else if (blockingErrors.errorType === 'emptyField') {
			return Liferay.Language.get('some-fields-need-to-be-filled').slice(
				0,
				-1
			);
		}
		else if (blockingErrors.errorType === 'assignment') {
			return Liferay.Language.get('warning');
		}
		else {
			return Liferay.Language.get('error');
		}
	};

	const getXMLContent = (exporting) => {
		let currentDescription;
		let currentElements;
		let currentName;
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

				currentName = metadata.name;
				setDefinitionName(currentName);

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
					name: currentName,
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
		if (!definitionTitle) {
			setAlert(
				Liferay.Language.get('name-workflow-before-publish'),
				'danger',
				true
			);
		}
		else if (blockingErrors.errorType !== '') {
			setAlert(blockingErrors.errorMessage, 'danger', true);
		}
		else {
			let alertMessage;

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
				title_i18n: definitionTitleTranslations,
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
		if (blockingErrors.errorType !== '') {
			setAlert(blockingErrors.errorMessage, 'danger', true);
		}
		else {
			saveDefinitionRequest({
				active,
				content: getXMLContent(true),
				name: definitionId,
				title: definitionTitle,
				title_i18n: definitionTitleTranslations,
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
							setAlert(
								Liferay.Language.get('workflow-saved'),
								'success',
								true
							);
						}
					});
				}
			});
		}
	};

	useEffect(() => {
		if (isObjectEmpty(definitionTitleTranslations)) {
			setDefinitionTitleTranslations({
				[defaultLanguageId]: Liferay.Language.get('new-workflow'),
			});
		}

		if (selectedLanguageId) {
			setDefinitionTitle(definitionTitleTranslations[selectedLanguageId]);
		}
	}, [
		selectedLanguageId,
		setDefinitionTitle,
		setDefinitionTitleTranslations,
		definitionTitleTranslations,
	]);

	useEffect(() => {
		let languageId = defaultLanguageId;

		if (selectedLanguageId) {
			languageId = selectedLanguageId;
		}

		setDefinitionTitleTranslations((previous) => ({
			...previous,
			[languageId]: definitionTitle,
		}));

		languageIds.map((currentLanguage) => {
			const emptyLabel = elements?.find((elements) =>
				findEmptyElements(elements, currentLanguage)
			);
			if (!emptyLabel && definitionTitleTranslations[currentLanguage]) {
				setTranslations((previous) => ({
					...previous,
					[currentLanguage]: true,
				}));
			}
			else {
				setTranslations((previous) => ({
					...previous,
					[currentLanguage]: false,
				}));
			}
		});
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [definitionTitle, elements]);

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

	useEffect(() => {
		if (blockingErrors.errorType === 'assignment') {
			setAlert(blockingErrors.errorMessage, 'warning', true);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [blockingErrors]);

	const resetAlert = () => {
		setShowAlert(false);
		if (blockingErrors.errorType === 'assignment') {
			setBlockingErrors({errorType: ''});
		}
	};

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
						onClose={() => resetAlert()}
						title={
							alertType === 'success'
								? `${Liferay.Language.get('success')}:`
								: `${errorTitle()}:`
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
	definitionTitleTranslations: PropTypes.object,
	displayNames: PropTypes.arrayOf(PropTypes.string).isRequired,
	languageIds: PropTypes.arrayOf(PropTypes.string).isRequired,
	title: PropTypes.PropTypes.string.isRequired,
	version: PropTypes.PropTypes.string.isRequired,
};
