/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayLayout from '@clayui/layout';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import {fetch, navigate, openConfirmModal, unescapeHTML} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useMemo, useReducer, useState} from 'react';

import TranslateActionBar from './components/TranslateActionBar/TranslateActionBar';
import TranslateFieldSetEntries from './components/TranslateFieldSetEntries';
import TranslateHeader from './components/TranslateHeader';
import {FETCH_STATUS} from './constants';
import {setURLParameters} from './utils';

const ACTION_TYPES = {
	UPDATE_FETCH_STATUS: 'UPDATE_FETCH_STATUS',
	UPDATE_FIELD: 'UPDATE_FIELD',
	UPDATE_FIELDS_BULK: 'UPDATE_FIELDS_BULK',
};

const getInfoFields = (infoFieldSetEntries = []) => {
	const sourceFields = {};
	const targetFields = {};

	infoFieldSetEntries.forEach(({fields}) => {
		fields.forEach(({html, id: idSet, sourceContent, targetContent}) => {
			sourceContent.forEach((content, index) => {
				const id = `${idSet}${index}`;

				sourceFields[id] = {
					content,
					html,
				};
				targetFields[id] = {
					content: targetContent[index],
					message: '',
					status: '',
				};
			});
		});
	});

	return {
		sourceFields,
		targetFields,
	};
};

const reducer = (state, action) => {
	switch (action.type) {
		case ACTION_TYPES.UPDATE_FIELD:
			return {
				...state,
				fields: {
					...state.fields,
					[action.payload.id]: {
						...state.fields[action.payload.id],
						...action.payload.field,
					},
				},
				formHasChanges: true,
			};
		case ACTION_TYPES.UPDATE_FIELDS_BULK:
			return {
				...state,
				fields: action.payload,
				formHasChanges: true,
			};
		case ACTION_TYPES.UPDATE_FETCH_STATUS:
			return {...state, fetchAutoTranslateStatus: action.payload};
		default:
			return state;
	}
};

const Translate = ({
	additionalFields,
	autoTranslateEnabled = false,
	currentUrl,
	experiencesSelectorData,
	getAutoTranslateURL,
	infoFieldSetEntries,
	portletNamespace,
	publishButtonDisabled,
	publishButtonLabel,
	redirectURL,
	saveButtonDisabled,
	saveButtonLabel,
	sourceLanguageId,
	sourceLanguageIdTitle,
	targetLanguageId,
	targetLanguageIdTitle,
	translateLanguagesSelectorData,
	translationPermission,
	updateTranslationPortletURL,
	workflowActions,
}) => {
	const isMounted = useIsMounted();

	const [workflowAction, setWorkflowAction] = useState(
		workflowActions.PUBLISH
	);

	const {sourceFields, targetFields} = useMemo(
		() => getInfoFields(infoFieldSetEntries),
		[infoFieldSetEntries]
	);

	const [state, dispatch] = useReducer(reducer, {
		fetchAutoTranslateStatus: {
			message: '',
			status: '',
		},
		fields: targetFields,
		formHasChanges: false,
	});

	const confirmChangesBeforeReload = (parameters = {}) => {
		const url = setURLParameters(
			Liferay.Util.ns(portletNamespace, parameters),
			currentUrl
		);

		if (!state.formHasChanges) {
			navigate(url);

			return;
		}

		openConfirmModal({
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-leave-the-page-you-may-lose-your-changes'
			),
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					navigate(url);
				}
			},
		});
	};

	const handleOnSaveDraft = () => {
		setWorkflowAction(workflowActions.SAVE_DRAFT);
	};

	const handleOnChangeField = ({content, id}) => {
		dispatch({
			payload: {field: {content}, id},
			type: ACTION_TYPES.UPDATE_FIELD,
		});
	};

	const fetchAutoTranslation = ({fields}) =>
		fetch(getAutoTranslateURL, {
			body: JSON.stringify({
				fields: Object.fromEntries(
					Object.entries(fields).map((a) => [a[0], a[1].content])
				),
				html: Object.fromEntries(
					Object.entries(fields).map((a) => [a[0], a[1].html])
				),
				sourceLanguageId,
				targetLanguageId,
			}),
			method: 'POST',
		}).then((response) => response.json());

	const fetchAutoTranslateFieldsBulk = () => {
		dispatch({
			payload: {
				status: FETCH_STATUS.LOADING,
			},
			type: ACTION_TYPES.UPDATE_FETCH_STATUS,
		});

		fetchAutoTranslation({fields: sourceFields})
			.then(({error, fields, html}) => {
				if (error) {
					throw error;
				}

				if (isMounted()) {
					dispatch({
						payload: Object.entries(fields).reduce(
							(acc, [id, content]) => {
								let contentData;
								if (
									html &&
									sourceFields[id].html === html[id]
								) {
									contentData = content;
								}
								else {
									contentData = unescapeHTML(content);
								}
								acc[id] = {
									content: contentData,
								};

								return acc;
							},
							{}
						),
						type: ACTION_TYPES.UPDATE_FIELDS_BULK,
					});

					dispatch({
						payload: {
							message: Liferay.Language.get(
								'successfully-received-translations'
							),
							status: FETCH_STATUS.SUCCESS,
						},
						type: ACTION_TYPES.UPDATE_FETCH_STATUS,
					});
				}
			})
			.catch(
				({
					message = Liferay.Language.get(
						'an-unexpected-error-occurred'
					),
				}) => {
					if (isMounted()) {
						dispatch({
							payload: {
								message,
								status: FETCH_STATUS.ERROR,
							},
							type: ACTION_TYPES.UPDATE_FETCH_STATUS,
						});
					}
				}
			);
	};

	const fetchAutoTranslateField = (fieldId) => {
		dispatch({
			payload: {field: {status: FETCH_STATUS.LOADING}, id: fieldId},
			type: ACTION_TYPES.UPDATE_FIELD,
		});

		fetchAutoTranslation({
			fields: {[fieldId]: sourceFields[fieldId]},
		})
			.then(({error, fields, html}) => {
				if (error) {
					throw error;
				}

				let contentData;

				if (html && sourceFields[fieldId].html === html[fieldId]) {
					contentData = fields[fieldId];
				}
				else {
					contentData = unescapeHTML(fields[fieldId]);
				}

				if (isMounted()) {
					dispatch({
						payload: {
							field: {
								content: contentData,
								message: Liferay.Language.get(
									'field-translated'
								),
								status: FETCH_STATUS.SUCCESS,
							},
							id: fieldId,
						},
						type: ACTION_TYPES.UPDATE_FIELD,
					});
				}
			})
			.catch(
				({
					message = Liferay.Language.get(
						'an-unexpected-error-occurred'
					),
				}) => {
					if (isMounted()) {
						dispatch({
							payload: {
								field: {
									message,
									status: FETCH_STATUS.ERROR,
								},
								id: fieldId,
							},
							type: ACTION_TYPES.UPDATE_FIELD,
						});
					}
				}
			);
	};

	return (
		<form
			action={updateTranslationPortletURL}
			className="translation-edit"
			method="POST"
			name="translate_fm"
		>
			<input
				defaultValue={workflowAction}
				name={`${portletNamespace}workflowAction`}
				type="hidden"
			/>

			{Object.entries(additionalFields).map(([name, value]) => (
				<input
					defaultValue={value}
					key={name}
					name={`${portletNamespace}${name}`}
					type="hidden"
				/>
			))}

			<TranslateActionBar
				autoTranslateEnabled={autoTranslateEnabled}
				confirmChangesBeforeReload={confirmChangesBeforeReload}
				experienceSelectorData={experiencesSelectorData}
				fetchAutoTranslateFields={fetchAutoTranslateFieldsBulk}
				fetchAutoTranslateStatus={state.fetchAutoTranslateStatus}
				onSaveButtonClick={handleOnSaveDraft}
				portletNamespace={portletNamespace}
				publishButtonDisabled={publishButtonDisabled}
				publishButtonLabel={publishButtonLabel}
				redirectURL={redirectURL}
				saveButtonDisabled={saveButtonDisabled}
				saveButtonLabel={saveButtonLabel}
				translateLanguagesSelectorData={translateLanguagesSelectorData}
			/>

			<ClayLayout.ContainerFluid view>
				<div className="sheet translation-edit-body-form">
					{!translationPermission ? (
						<ClayAlert>
							{Liferay.Language.get(
								'you-do-not-have-permissions-to-translate-to-any-of-the-available-languages'
							)}
						</ClayAlert>
					) : (
						<>
							<TranslateHeader
								autoTranslateEnabled={autoTranslateEnabled}
								sourceLanguageIdTitle={sourceLanguageIdTitle}
								targetLanguageIdTitle={targetLanguageIdTitle}
							/>

							<TranslateFieldSetEntries
								autoTranslateEnabled={autoTranslateEnabled}
								fetchAutoTranslateField={
									fetchAutoTranslateField
								}
								infoFieldSetEntries={infoFieldSetEntries}
								onChange={handleOnChangeField}
								portletNamespace={portletNamespace}
								targetFieldsContent={state.fields}
							/>
						</>
					)}
				</div>
			</ClayLayout.ContainerFluid>
		</form>
	);
};

Translate.propTypes = {
	autoTranslateEnabled: PropTypes.bool,
	currentUrl: PropTypes.string.isRequired,
	experiencesSelectorData: PropTypes.shape({
		label: PropTypes.string.isRequired,
		options: PropTypes.arrayOf(
			PropTypes.shape({
				label: PropTypes.string.isRequired,
				value: PropTypes.string.isRequired,
			})
		),
		value: PropTypes.string.isRequired,
	}),
	getAutoTranslateURL: PropTypes.string.isRequired,
	infoFieldSetEntries: PropTypes.arrayOf(
		PropTypes.shape({
			fields: PropTypes.arrayOf(
				PropTypes.shape({
					editorConfiguration: PropTypes.object,
					html: PropTypes.bool,
					id: PropTypes.string.isRequired,
					label: PropTypes.string.isRequired,
					multiline: PropTypes.bool,
					sourceContent: PropTypes.arrayOf(PropTypes.string)
						.isRequired,
					sourceContentDir: PropTypes.string.isRequired,
					targetContent: PropTypes.arrayOf(PropTypes.string)
						.isRequired,
					targetContentDir: PropTypes.string,
				})
			),
			legend: PropTypes.string.isRequired,
		})
	),
	portletNamespace: PropTypes.string.isRequired,
	sourceLanguageId: PropTypes.string.isRequired,
	sourceLanguageIdTitle: PropTypes.string.isRequired,
	targetLanguageId: PropTypes.string.isRequired,
	targetLanguageIdTitle: PropTypes.string.isRequired,
	translationPermission: PropTypes.bool.isRequired,
	updateTranslationPortletURL: PropTypes.string.isRequired,
	workflowActions: PropTypes.shape({
		PUBLISH: PropTypes.string.isRequired,
		SAVE_DRAFT: PropTypes.string.isRequired,
	}).isRequired,
};

export default Translate;
