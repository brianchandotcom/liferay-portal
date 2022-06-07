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
import ClayForm, {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayTabs from '@clayui/tabs';
import {useIsMounted, usePrevious} from '@liferay/frontend-js-react-web';
import {
	cancelDebounce,
	debounce,
	fetch,
	objectToFormData,
	openToast,
} from 'frontend-js-web';
import React, {useCallback, useEffect, useState} from 'react';

import CodeMirrorEditor from './CodeMirrorEditor';
import FragmentPreview from './FragmentPreview';

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
		cacheable,
		dataAttributes,
		fieldTypes: availableFieldTypes,
		fragmentCollectionId,
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
		urls,
	},
}) => {
	const [activeTabKeyValue, setActiveTabKeyValue] = useState(0);
	const [isCacheable, setIsCacheable] = useState(cacheable);
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

	const isMounted = useIsMounted();

	const contentHasChanged = useCallback(() => {
		return (
			previousConfiguration !== configuration ||
			previousCss !== css ||
			previousFieldTypes.length !== fieldTypes.length ||
			previousHtml !== html ||
			previousJs !== js ||
			cacheable !== isCacheable
		);
	}, [
		cacheable,
		configuration,
		css,
		fieldTypes,
		html,
		previousCss,
		previousConfiguration,
		previousFieldTypes,
		previousHtml,
		previousJs,
		isCacheable,
		js,
	]);

	const onAddFieldType = (type) => setFieldTypes([...fieldTypes, type]);

	const onRemoveFieldType = (type) =>
		setFieldTypes(fieldTypes.filter((fieldType) => fieldType !== type));

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

				Liferay.Util.navigate(redirectURL);
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

			const data = {
				cacheable: isCacheable,
				configurationContent: configuration,
				cssContent: css,
				fieldTypes,
				fragmentCollectionId,
				fragmentEntryId,
				htmlContent: html,
				jsContent: js,
				name,
				status: allowedStatus.draft,
			};

			fetch(urls.edit, {
				body: objectToFormData(Liferay.Util.ns(namespace, data)),
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
		[configuration, css, fieldTypes, html, isCacheable, js]
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

									<div className="btn-group-item custom-checkbox custom-control mb-1 mr-4 mt-1">
										<label
											className="lfr-portal-tooltip"
											data-title={Liferay.Language.get(
												'cacheable-fragment-help'
											)}
										>
											<input
												checked={isCacheable}
												className="custom-control-input toggle-switch-check"
												name="cacheable"
												onChange={(event) =>
													setIsCacheable(
														event.currentTarget
															.checked
													)
												}
												type="checkbox"
												value="true"
											/>

											<span className="custom-control-label">
												<span className="custom-control-label-text">
													{Liferay.Language.get(
														'cacheable'
													)}
												</span>
											</span>
										</label>
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
								codeHeaderHelpText={Liferay.Util.sub(
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
							configuration={configuration}
							css={css}
							html={html}
							js={js}
							namespace={namespace}
							urls={urls}
						/>
					</div>
				</ClayTabs.TabPane>

				<ClayTabs.TabPane aria-labelledby="configuration">
					<div className="fragment-editor fragment-editor__configuration">
						<div className="sheet sheet-lg">
							{showFieldTypes && (
								<FieldTypeSelector
									availableFieldTypes={availableFieldTypes}
									fieldTypes={fieldTypes}
									onAddFieldType={onAddFieldType}
									onRemoveFieldType={onRemoveFieldType}
									readOnly={readOnly}
								/>
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

function FieldTypeSelector({
	availableFieldTypes,
	fieldTypes,
	onAddFieldType,
	onRemoveFieldType,
	readOnly,
}) {
	return (
		<ClayForm.Group>
			<div className="sheet-section">
				<h2 className="sheet-subtitle">
					{Liferay.Language.get('field-types')}
				</h2>

				{readOnly ? (
					fieldTypes.length ? (
						fieldTypes.map((fieldType) => {
							const label = availableFieldTypes.find(
								({key}) => key === fieldType
							).label;

							return (
								<p className="mb-1" key={fieldType}>
									{label}
								</p>
							);
						})
					) : (
						<ClayAlert displayType="info">
							{Liferay.Language.get(
								'no-field-type-defined-for-this-fragment'
							)}
						</ClayAlert>
					)
				) : (
					<>
						<p>
							{Liferay.Language.get(
								'specify-which-field-types-this-fragment-supports'
							)}
						</p>

						{availableFieldTypes.map(({key, label}) => (
							<ClayCheckbox
								aria-label={label}
								checked={fieldTypes.includes(key)}
								key={key}
								label={label}
								onChange={(event) =>
									event.target.checked
										? onAddFieldType(key)
										: onRemoveFieldType(key)
								}
							/>
						))}
					</>
				)}
			</div>
		</ClayForm.Group>
	);
}

export default FragmentEditor;
