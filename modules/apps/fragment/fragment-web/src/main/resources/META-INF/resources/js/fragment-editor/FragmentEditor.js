/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayTabs from '@clayui/tabs';
import {useIsMounted, usePrevious} from '@liferay/frontend-js-react-web';
import {
	cancelDebounce,
	debounce,
	fetch,
	navigate,
	openToast,
	sub,
} from 'frontend-js-web';
import React, {useCallback, useEffect, useState} from 'react';

import CodeMirrorEditor from './CodeMirrorEditor';
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
	const [configuration, setConfiguration] = useState(initialConfiguration);
	const [css, setCss] = useState(initialCSS);
	const [html, setHtml] = useState(initialHTML);
	const [js, setJs] = useState(initialJS);
	const [fieldTypes, setFieldTypes] = useState(initialFieldTypes);
	const previousConfiguration =
		usePrevious(configuration) || initialConfiguration;
	const previousCss = usePrevious(css) || initialCSS;
	const previousFieldTypes = usePrevious(fieldTypes) || initialFieldTypes;
	const previousHtml = usePrevious(html) || initialHTML;
	const previousJs = usePrevious(js) || initialJS;

	const [previewData, setPreviewData] = useState({
		configuration: initialConfiguration,
		css: initialCSS,
		html: initialHTML,
		js: initialJS,
	});

	const isMounted = useIsMounted();

	const contentHasChanged = useCallback(() => {
		return (
			previousConfiguration !== configuration ||
			previousCss !== css ||
			previousFieldTypes.length !== fieldTypes.length ||
			previousHtml !== html ||
			previousJs !== js
		);
	}, [
		configuration,
		css,
		fieldTypes,
		html,
		previousCss,
		previousConfiguration,
		previousFieldTypes,
		previousHtml,
		previousJs,
		js,
	]);

	const publish = () => {
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

	/* eslint-disable-next-line react-hooks/exhaustive-deps */
	const saveDraft = useCallback(
		debounce(() => {
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
			formData.append(
				`${namespace}jsContent`,
				createFile('jsContent', js)
			);
			formData.append(`${namespace}name`, name);
			formData.append(`${namespace}status`, allowedStatus.draft);

			fetch(urls.edit, {
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
		}, 500),
		[configuration, css, fieldTypes, html, js]
	);

	const previousSaveDraft = usePrevious(saveDraft);

	useEffect(() => {
		if (previousSaveDraft && previousSaveDraft !== saveDraft) {
			cancelDebounce(previousSaveDraft);
		}
	}, [previousSaveDraft, saveDraft]);

	useEffect(() => {
		if (contentHasChanged()) {
			setChangesStatus(CHANGES_STATUS.unsaved);
			saveDraft();
		}
	}, [contentHasChanged, saveDraft]);

	return (
		<div className="fragment-editor-container">
			<div className="fragment-editor__toolbar nav-bar-container">
				<div className="navbar navbar-expand navbar-underline navigation-bar navigation-bar-light">
					<div className="container-fluid container-fluid-max-xl">
						<div className="navbar-nav">
							<ClayTabs>
								<ClayTabs.Item
									active={activeTabKeyValue === 0}
									innerProps={{
										'aria-controls': 'code',
										'aria-expanded': 'true',
									}}
									onClick={() => setActiveTabKeyValue(0)}
								>
									{Liferay.Language.get('code')}
								</ClayTabs.Item>

								<ClayTabs.Item
									active={activeTabKeyValue === 1}
									innerProps={{
										'aria-controls': 'configuration',
										'aria-expanded': 'false',
									}}
									onClick={() => setActiveTabKeyValue(1)}
								>
									{Liferay.Language.get('configuration')}
								</ClayTabs.Item>
							</ClayTabs>
						</div>

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

									<div className="btn-group-item">
										<button
											className="btn btn-primary btn-sm"
											disabled={
												changesStatus ===
												CHANGES_STATUS.saving
											}
											onClick={publish}
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
								content={initialHTML}
								customDataAttributes={dataAttributes}
								customEntities={htmlEditorCustomEntities}
								customTags={autocompleteTags}
								mode="html"
								onChange={setHtml}
								readOnly={readOnly}
							/>
						</div>

						<div className="css source-editor">
							<CodeMirrorEditor
								content={initialCSS}
								mode="css"
								onChange={setCss}
								readOnly={readOnly}
							/>
						</div>

						<div className="javascript source-editor">
							<CodeMirrorEditor
								codeFooterText="}"
								codeHeaderHelpText={sub(
									Liferay.Language.get(
										'parameter-x-provides-access-to-the-current-fragment-node-use-it-to-manipulate-fragment-components'
									),
									['fragmentElement']
								)}
								codeHeaderText="function(fragmentElement, configuration) {"
								content={initialJS}
								mode="javascript"
								onChange={setJs}
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
										content={initialConfiguration}
										mode="json"
										onChange={setConfiguration}
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

export default FragmentEditor;
