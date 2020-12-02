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

import ClayIcon from '@clayui/icon';
import ClayTabs from '@clayui/tabs';
import {useIsMounted, usePrevious} from 'frontend-js-react-web';
import {cancelDebounce, debounce, fetch, openToast} from 'frontend-js-web';
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
		fragmentCollectionId,
		fragmentEntryId,
		htmlEditorCustomEntities,
		initialCSS,
		initialConfiguration,
		initialHTML,
		initialJS,
		name,
		propagationEnabled,
		readOnly,
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

	const previousConfiguration =
		usePrevious(configuration) || initialConfiguration;
	const previousCss = usePrevious(css) || initialCSS;
	const previousHtml = usePrevious(html) || initialHTML;
	const previousJs = usePrevious(js) || initialJS;

	const isMounted = useIsMounted();

	const contentHasChanged = useCallback(() => {
		return (
			previousConfiguration !== configuration ||
			previousCss !== css ||
			previousHtml !== html ||
			previousJs !== js ||
			cacheable !== isCacheable
		);
	}, [
		cacheable,
		configuration,
		css,
		html,
		previousCss,
		previousConfiguration,
		previousHtml,
		previousJs,
		isCacheable,
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

			const formData = new FormData();

			formData.append(`${namespace}cacheable`, isCacheable);
			formData.append(`${namespace}configurationContent`, configuration);
			formData.append(`${namespace}cssContent`, css);
			formData.append(`${namespace}htmlContent`, html);
			formData.append(
				`${namespace}fragmentCollectionId`,
				fragmentCollectionId
			);
			formData.append(`${namespace}fragmentEntryId`, fragmentEntryId);
			formData.append(`${namespace}jsContent`, js);
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
		[configuration, css, html, isCacheable, js]
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
				<div className="navbar navbar-default pb-2 pt-2">
					<div className="container">
						<div className="navbar navbar-collapse-absolute navbar-expand-md navbar-underline navigation-bar navigation-bar-light">
							<ClayTabs modern>
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

						<div className="btn-group btn-group-nowrap float-right mt-1">
							{readOnly ? (
								<span className="pr-3 pt-1 text-info">
									<ClayIcon symbol="exclamation-circle" />

									<span>
										{Liferay.Language.get('read-only-view')}
									</span>
								</span>
							) : (
								<>
									{propagationEnabled && (
										<span
											className="lfr-portal-tooltip pr-3 pt-1 text-info"
											data-title={Liferay.Language.get(
												'automatic-propagation-enabled-help'
											)}
										>
											<ClayIcon symbol="exclamation-circle" />

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
					<div className="fragment-editor">
						<CodeMirrorEditor
							content={initialConfiguration}
							mode="json"
							onChange={setConfiguration}
							readOnly={readOnly}
						/>
					</div>
				</ClayTabs.TabPane>
			</ClayTabs.Content>
		</div>
	);
};

export default FragmentEditor;
