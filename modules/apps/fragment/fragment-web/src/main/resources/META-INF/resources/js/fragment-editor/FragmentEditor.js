/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayNav from '@clayui/nav';
import ClayTabs from '@clayui/tabs';
import {
	AIAssistantTriggerButton,
	APPLY_FRAGMENT_CODE_EVENT,
} from '@liferay/ai-hub-cell-js-components-web';
import {useIsMounted, usePrevious} from '@liferay/frontend-js-react-web';
import {openToast} from 'frontend-js-components-web';
import {debounce, fetch, navigate, sub} from 'frontend-js-web';
import React, {useCallback, useEffect, useRef, useState} from 'react';

import CodeMirrorEditor from './CodeMirrorEditor';
import EmbeddedWidgetsModal from './EmbeddedWidgetsModal';
import {FieldTypeSelector} from './FieldTypeSelector';
import FragmentPreview from './FragmentPreview';
import createFile from './createFile';

const CHANGES_STATUS = {
	saved: Liferay.Language.get('changes-saved'),
	saving: Liferay.Language.get('saving-changes'),
	unsaved: Liferay.Language.get('unsaved-changes'),
};

const FragmentEditor = ({
	context: {namespace},
	props: {
		allowedStatus = {
			approved: false,
			draft: false,
		},
		autocompleteTags,
		configurationSchema,
		dataAttributes,
		fieldTypes: availableFieldTypes,
		fragmentCollectionId,
		fragmentConfigurationURL,
		fragmentEntryId,
		htmlEditorCustomEntities,
		initialCSS,
		initialConfiguration,
		initialFieldTypes,
		initialHTML,
		initialJS,
		learnMessageHTML,
		name,
		propagationEnabled,
		readOnly,
		showFieldTypes,
		status,
		urls,
	},
}) => {
	const [activeTabKeyValue, setActiveTabKeyValue] = useState(0);
	const [changesStatus, setChangesStatus] = useState(null);
	const [fieldTypes, setFieldTypes] = useState(initialFieldTypes);

	const [code, setCode] = useState(() => ({
		configuration: initialConfiguration,
		css: initialCSS,
		html: initialHTML,
		js: initialJS,
	}));

	const codeRef = useRef(code);

	const [loadedCode, setLoadedCode] = useState(code);
	const [previewData, setPreviewData] = useState(code);

	const previousCode = usePrevious(code) || code;
	const previousFieldTypes = usePrevious(fieldTypes) || initialFieldTypes;

	const isMounted = useIsMounted();

	const contentHasChanged = useCallback(() => {
		return (
			previousCode !== code ||
			previousFieldTypes.length !== fieldTypes.length
		);
	}, [code, fieldTypes, previousCode, previousFieldTypes]);

	const widgetRegex = new RegExp('<lfr-widget(?:-[^>]+)?>', 'g');

	const handlePublishClick = () => {
		if (
			!Liferay.FeatureFlags['LPD-40535'] &&
			(widgetRegex.test(code.html) ||
				code.html.includes('[@liferay_portlet["runtime"]'))
		) {
			EmbeddedWidgetsModal({learnMessageHTML, onPublish: publish});
		}
		else {
			publish();
		}
	};

	const publish = () => {
		setChangesStatus(CHANGES_STATUS.saving);

		const formData = new FormData();

		formData.append(`${namespace}fragmentEntryId`, fragmentEntryId);

		fetch(urls.publish, {
			body: formData,
			method: 'POST',
		})
			.then((response) => response.json())
			.then((response) => {
				if (response.error) {
					throw response.error;
				}

				return response;
			})
			.then((response) => {
				const redirectURL = response.redirect || urls.redirect;

				navigate(redirectURL);
			})
			.catch((error) => {
				if (isMounted()) {
					setChangesStatus(CHANGES_STATUS.unsaved);
				}

				const message =
					typeof error === 'string'
						? error
						: Liferay.Language.get('error');

				openToast({
					message,
					type: 'danger',
				});
			});
	};

	useEffect(() => {
		codeRef.current = code;
	}, [code]);

	useEffect(() => {
		const applyFragmentCodeHandler = Liferay.on(
			APPLY_FRAGMENT_CODE_EVENT,
			({configuration, css, html, js}) => {
				setLoadedCode((loadedCode) => ({
					configuration: configuration
						? JSON.stringify(configuration, null, '\t')
						: loadedCode.configuration,
					css: css ?? loadedCode.css,
					html: html ?? loadedCode.html,
					js: js ?? loadedCode.js,
				}));
			}
		);

		return () => applyFragmentCodeHandler.detach();
	}, []);

	useEffect(() => {
		if (contentHasChanged()) {
			setChangesStatus(CHANGES_STATUS.unsaved);
			debouncedSaveDraft({
				...code,
				allowedStatusDraft: allowedStatus.draft,
				editUrl: urls.edit,
				fieldTypes,
				fragmentCollectionId,
				fragmentEntryId,
				isMounted,
				name,
				namespace,
				setChangesStatus,
				setPreviewData,
			});
		}
	}, [
		allowedStatus.draft,
		code,
		contentHasChanged,
		fieldTypes,
		fragmentCollectionId,
		fragmentEntryId,
		isMounted,
		name,
		namespace,
		urls.edit,
	]);

	return (
		<div className="fragment-editor-container">
			<div className="fragment-editor__toolbar nav-bar-container">
				<div className="navbar navbar-expand-md navbar-underline navigation-bar navigation-bar-light">
					<div className="container-fluid">
						<ClayNav className="navbar-nav" role="tablist">
							<ClayNav.Item>
								<ClayNav.Link
									active={activeTabKeyValue === 0}
									aria-controls="code"
									aria-selected={activeTabKeyValue === 0}
									onClick={() => setActiveTabKeyValue(0)}
									role="tab"
								>
									{Liferay.Language.get('code')}
								</ClayNav.Link>
							</ClayNav.Item>

							<ClayNav.Item>
								<ClayNav.Link
									active={activeTabKeyValue === 1}
									aria-controls="configuration"
									aria-selected={activeTabKeyValue === 1}
									onClick={() => setActiveTabKeyValue(1)}
									role="tab"
								>
									{Liferay.Language.get('configuration')}
								</ClayNav.Link>
							</ClayNav.Item>
						</ClayNav>

						<div className="align-items-center btn-group btn-group-nowrap d-flex float-right">
							{readOnly ? (
								<span className="pr-3 text-info">
									<ClayIcon
										className="mr-2"
										symbol="exclamation-circle"
									/>

									<span>
										{Liferay.Language.get('read-only-view')}
									</span>
								</span>
							) : (
								<>
									{propagationEnabled && (
										<span
											className="align-items-center d-flex lfr-portal-tooltip pr-3 text-info"
											data-title={Liferay.Language.get(
												'automatic-propagation-enabled-help'
											)}
										>
											<ClayIcon
												className="mr-2"
												symbol="exclamation-circle"
											/>

											<span>
												{Liferay.Language.get(
													'automatic-propagation-enabled'
												)}
											</span>
										</span>
									)}

									<div className="btn-group-item ml-2 mr-4">
										<span className="my-0 navbar-text p-0">
											{changesStatus}
										</span>
									</div>

									{Liferay.FeatureFlags['LPD-62272'] &&
										Liferay.FeatureFlags['LPD-94003'] && (
											<div className="btn-group-item mr-2">
												<AIAssistantTriggerButton
													chatbotExternalReferenceCode="L_EDIT_FRAGMENT_CHATBOT"
													getContext={() => ({
														configurationSchema,
														fragmentCSS:
															codeRef.current.css,
														fragmentConfiguration:
															codeRef.current
																.configuration,
														fragmentHTML:
															codeRef.current
																.html,
														fragmentJS:
															codeRef.current.js,
													})}
													greeting={Liferay.Language.get(
														'hi-i-can-help-you-edit-this-fragment'
													)}
													hideLabel
													instructionDefinitionScope=""
													presentation="sidebar"
													pushContainer="#main-content"
												/>
											</div>
										)}

									<div className="btn-group-item">
										<button
											className="btn btn-primary btn-sm"
											disabled={
												changesStatus ===
												CHANGES_STATUS.saving
											}
											onClick={handlePublishClick}
											type="button"
										>
											<span className="lfr-btn-label">
												{Liferay.Language.get(
													'publish'
												)}
											</span>
										</button>
									</div>
								</>
							)}
						</div>
					</div>
				</div>
			</div>

			<ClayTabs.Content activeIndex={activeTabKeyValue} fade>
				<ClayTabs.TabPane aria-labelledby="code">
					<div className="fragment-editor">
						<div className="html source-editor">
							<CodeMirrorEditor
								content={loadedCode.html}
								customDataAttributes={dataAttributes}
								customEntities={htmlEditorCustomEntities}
								customTags={autocompleteTags}
								mode="html"
								onChange={(html) =>
									setCode((current) => ({...current, html}))
								}
								readOnly={readOnly}
							/>
						</div>

						<div className="css source-editor">
							<CodeMirrorEditor
								content={loadedCode.css}
								mode="css"
								onChange={(css) =>
									setCode((current) => ({...current, css}))
								}
								readOnly={readOnly}
							/>
						</div>

						<div className="javascript source-editor">
							<CodeMirrorEditor
								codeFooterText="</script>"
								codeHeaderHelpText={sub(
									Liferay.Language.get(
										'parameter-x-provides-access-to-the-current-fragment-node-use-it-to-manipulate-fragment-components'
									),
									['fragmentElement']
								)}
								codeHeaderTexts={[
									'<script type="module">',
									'const fragmentElement = ...;',
									'const configuration = ...;',
								]}
								content={loadedCode.js}
								mode="javascript"
								onChange={(js) =>
									setCode((current) => ({...current, js}))
								}
								readOnly={readOnly}
							/>
						</div>

						<FragmentPreview
							configuration={previewData.configuration}
							css={previewData.css}
							html={previewData.html}
							js={previewData.js}
							urls={urls}
						/>
					</div>
				</ClayTabs.TabPane>

				<ClayTabs.TabPane aria-labelledby="configuration">
					<div className="fragment-editor fragment-editor__configuration">
						<div className="sheet sheet-lg">
							{showFieldTypes && (
								<>
									<FieldTypeSelector
										availableFieldTypes={
											availableFieldTypes
										}
										description={Liferay.Language.get(
											'specify-which-field-types-this-fragment-supports'
										)}
										fieldTypes={fieldTypes}
										fragmentConfigurationURL={
											fragmentConfigurationURL
										}
										onChangeFieldTypes={setFieldTypes}
										readOnly={readOnly}
										showFragmentConfigurationLink={
											status !== allowedStatus.draft
										}
										title={Liferay.Language.get(
											'field-types'
										)}
									/>
								</>
							)}

							<ClayForm.Group>
								<div className="sheet-section">
									<h2 className="sheet-subtitle">json</h2>

									{!readOnly && (
										<p>
											{Liferay.Language.get(
												'add-the-json-configuration'
											)}
										</p>
									)}

									<CodeMirrorEditor
										content={loadedCode.configuration}
										mode="json"
										onChange={(configuration) =>
											setCode((current) => ({
												...current,
												configuration,
											}))
										}
										readOnly={readOnly}
										showHeader={false}
									/>
								</div>
							</ClayForm.Group>
						</div>
					</div>
				</ClayTabs.TabPane>
			</ClayTabs.Content>
		</div>
	);
};

const debouncedSaveDraft = debounce(
	({
		allowedStatusDraft,
		configuration,
		css,
		editUrl,
		fieldTypes,
		fragmentCollectionId,
		fragmentEntryId,
		html,
		isMounted,
		js,
		name,
		namespace,
		setChangesStatus,
		setPreviewData,
	}) => {
		setChangesStatus(CHANGES_STATUS.saving);

		const formData = new FormData();

		formData.append(`${namespace}configurationContent`, configuration);
		formData.append(
			`${namespace}cssContent`,
			createFile('cssContent', css)
		);
		formData.append(`${namespace}fieldTypes`, fieldTypes);
		formData.append(
			`${namespace}fragmentCollectionId`,
			fragmentCollectionId
		);
		formData.append(`${namespace}fragmentEntryId`, fragmentEntryId);
		formData.append(
			`${namespace}htmlContent`,
			createFile('htmlContent', html)
		);
		formData.append(`${namespace}jsContent`, createFile('jsContent', js));
		formData.append(`${namespace}name`, name);
		formData.append(`${namespace}status`, allowedStatusDraft);

		fetch(editUrl, {
			body: formData,
			method: 'POST',
		})
			.then((response) => response.json())
			.then((response) => {
				if (response.error) {
					throw response.error;
				}

				return response;
			})
			.then(() => {
				setPreviewData({configuration, css, html, js});

				setChangesStatus(CHANGES_STATUS.saved);
			})
			.catch((error) => {
				if (isMounted()) {
					setChangesStatus(CHANGES_STATUS.unsaved);
				}

				const message =
					typeof error === 'string'
						? error
						: Liferay.Language.get('error');

				openToast({
					message,
					type: 'danger',
				});
			});
	},
	500
);

export default FragmentEditor;
