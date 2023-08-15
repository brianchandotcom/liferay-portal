/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-admin',
	(A) => {
		const Lang = A.Lang;

		const CONCURRENT_MODE = 'concurrent';

		const IN_PROGRESS_SELECTOR = '.background-task-status-in-progress';

		const INTERVAL_RENDER_IDLE = 60000;

		const INTERVAL_RENDER_IN_PROGRESS = 2000;

		const MAP_DATA_PARAMS = {
			classname: 'className',
		};

		const STR_CLICK = 'click';

		const STR_FORM = 'form';

		const STR_INDEX_ACTIONS_PANEL = 'indexActionsPanel';

		const STR_URL = 'url';

		const Admin = A.Component.create({
			ATTRS: {
				controlMenuCategoryKey: {
					validator: Lang.isString,
					value: 'tools',
				},

				elasticSearchDiskSpace: {
					validator: Lang.isObject,
					value: {},
				},

				form: {
					setter: A.one,
					value: null,
				},

				indexActionWrapperSelector: {
					validator: Lang.isString,
					value: null,
				},

				indexActionsPanel: {
					validator: Lang.isString,
					value: null,
				},

				redirectUrl: {
					validator: Lang.isString,
					value: null,
				},

				submitButton: {
					validator: Lang.isString,
					value: null,
				},

				url: {
					value: null,
				},
			},

			AUGMENTS: [Liferay.PortletBase],

			EXTENDS: A.Base,

			NAME: 'admin',

			prototype: {
				_addInputsFromData(data) {
					const instance = this;

					const form = instance.get(STR_FORM);

					// eslint-disable-next-line @liferay/aui/no-object
					const inputsArray = A.Object.map(data, (value, key) => {
						key = MAP_DATA_PARAMS[key] || key;

						const nsKey = instance.ns(key);

						return (
							'<input id="' +
							nsKey +
							'" name="' +
							nsKey +
							'" type="hidden" value="' +
							value +
							'" />'
						);
					});

					form.append(inputsArray.join(''));
				},

				_getConfirmationModalBodyHTML(
					data,
					isConcurrentMode,
					availableDiskSpace,
					currentDiskSpaceUsed,
					isLowOnDiskSpace
				) {
					const instance = this;

					const infoHTML = `
						<div class="text-secondary">
							<div class="c-mb-2">
								${Liferay.Language.get('reindex-actions-time-info')}
							</div>
							<div>
								${
									isConcurrentMode ||
									data.classname ||
									data.cmd === 'reindexDictionaries'
										? Liferay.Language.get(
												'reindex-actions-search-results-available-info'
										  )
										: Liferay.Language.get(
												'reindex-actions-search-results-not-available-info'
										  )
								}
							</div>
						</div>`;

					const checkboxHTML = `
						<div class="c-pt-4">
							<div class="custom-checkbox custom-control">
								<label>
									<input class="custom-control-input"
										id="${instance.ns('hideModalCheckbox')}" type="checkbox" />
									<span class="custom-control-label">
										<span class="custom-control-label-text">
											${Liferay.Language.get('do-not-show-me-this-again')}
										</span>
									</span>
								</label>
							</div>
						</div>`;

					let bodyHTML = infoHTML + checkboxHTML;

					if (isConcurrentMode) {
						const totalDiskSpace =
							availableDiskSpace + currentDiskSpaceUsed;

						const usedPercentage =
							(currentDiskSpaceUsed /
								(availableDiskSpace + currentDiskSpaceUsed)) *
							100;

						const progressBarHTML = `
							<label>
								${Liferay.Language.get('disk-usage')}
							</label>
							<div class="progress ${isLowOnDiskSpace && 'progress-warning'}">
								<div
									aria-valuemax="100"
									aria-valuemin="0"
									aria-valuenow="${usedPercentage}"
									class="progress-bar"
									role="progressbar"
									style="width: ${usedPercentage}%;"
								>
								</div>
							</div>
							<div class="text-3 text-secondary">
								<span>
									${Liferay.Util.sub(
										Liferay.Language.get('used-x-of-x-gb'),
										currentDiskSpaceUsed.toFixed(1),
										totalDiskSpace.toFixed(1)
									)}
								</span>
								<span class="float-right">
									${Liferay.Util.sub(
										Liferay.Language.get('x-gb-free'),
										availableDiskSpace.toFixed(1)
									)}
								</span>
							</div>`;

						const lowDiskSpaceDescriptionHTML = `
							<span>
								${Liferay.Language.get('reindex-elasticsearch-disk-space-warning')}:
							</span>
							<ul>
								<li>
									${Liferay.Util.sub(
										Liferay.Language.get(
											'available-disk-space-x'
										),
										availableDiskSpace.toFixed(1)
									)}
								</li>
								<li>
									${Liferay.Util.sub(
										Liferay.Language.get(
											'current-disk-space-used-x'
										),
										currentDiskSpaceUsed.toFixed(1)
									)}
								</li>
							</ul>
							<span class="c-mt-2">
								${Liferay.Language.get('do-you-still-wish-to-execute-reindex')}
							</span>`;

						bodyHTML = isLowOnDiskSpace
							? progressBarHTML +
							  `<div class="c-mt-2 text-secondary">
							  		${lowDiskSpaceDescriptionHTML}
								</div>`
							: infoHTML +
							  `<div class="c-mb-0 panel" style="height: 120px;" role="tablist">
									<button
										aria-controls="collapsePanel"
										aria-expanded="false"
										class="disk-space-panel c-pb-0 c-pl-0 collapsed btn btn-link"
										data-target="#collapsableDiskSpace"
										data-toggle="liferay-collapse"
										role="tab"
									>
										<span class="collapse-button-closed">
											${Liferay.Language.get('view-disk-space')}
										</span>
										<span class="collapse-button-open">
											${Liferay.Language.get('hide-disk-space')}
										</span>
									</button>
									<div class="panel-collapse collapse" id="collapsableDiskSpace" role="tabpanel">
										<div class="c-pl-0 c-pr-0 panel-body">
											${progressBarHTML}
										</div>
									</div>
								</div>` +
							  checkboxHTML;
					}

					return `
						<div class="reindex-actions-confirmation-modal-body">
							${bodyHTML}
						</div>`;
				},

				_getControlMenuReloadItem(element) {
					let controlMenuReloadItem;

					if (!element) {
						return;
					}

					element
						.querySelectorAll('.control-menu-nav-item')
						.forEach((element) => {
							if (
								element.getElementsByClassName(
									'lexicon-icon-reload'
								).length
							) {
								controlMenuReloadItem = element;
							}
						});

					return controlMenuReloadItem;
				},

				_isBackgroundTaskInProgress() {
					const instance = this;

					const indexActionsNode = A.one(
						instance.get(STR_INDEX_ACTIONS_PANEL)
					);

					return !!(
						indexActionsNode &&
						indexActionsNode.one(IN_PROGRESS_SELECTOR)
					);
				},

				_onSubmit(event) {
					const instance = this;

					const data = event.currentTarget.getData();

					const companyIds = document.getElementsByName(
						instance.ns('companyIds')
					)[0].value;

					if (!companyIds) {
						this._showError(
							Liferay.Language.get('missing-instance-error')
						);

						return;
					}

					if (Liferay.FeatureFlags['LPS-183661']) {
						this._showConfirmationModal(data);
					}
					else {
						this._onSubmitForm(data);
					}
				},

				_onSubmitForm(data) {
					const instance = this;

					const form = instance.get(STR_FORM);

					const redirect = instance.one('#redirect', form);

					if (redirect) {
						redirect.val(instance.get('redirectUrl'));
					}

					instance._addInputsFromData(data);

					submitForm(form, instance.get(STR_URL));

					if (
						Liferay.FeatureFlags['LPS-183661'] &&
						[
							'reindexDictionaries',
							'reindexIndexReindexer',
						].includes(data.cmd)
					) {
						document
							.querySelectorAll(instance.get('submitButton'))
							.forEach((element) => {
								element.disabled = true;
							});

						const currentControlMenu = document.getElementById(
							instance.ns('controlMenu')
						);

						const currentControlMenuCategory = currentControlMenu.querySelector(
							`.${instance.get(
								'controlMenuCategoryKey'
							)}-control-group .control-menu-nav`
						);

						const syncIcon = document.createElement('div');

						syncIcon.innerHTML = `
							<svg class="lexicon-icon" focusable="false">
								<use href="${Liferay.Icons.spritemap}#reload" />
							</svg>`;

						currentControlMenuCategory.appendChild(syncIcon);
					}
				},

				_saveConfirmationModalVisibility() {
					const instance = this;

					if (
						document.getElementById(
							instance.ns('hideModalCheckbox')
						)?.checked
					) {
						Liferay.Util.LocalStorage.setItem(
							instance.ns(
								`${Liferay.ThemeDisplay.getUserId()}_hideReindexConfirmationModal`
							),
							true,
							Liferay.Util.LocalStorage.TYPES.FUNCTIONAL
						);
					}
				},

				_showConfirmationModal(data) {
					const instance = this;

					const isConcurrentMode =
						document.querySelector(
							`#${instance.ns('executionMode')}`
						)?.value === CONCURRENT_MODE;

					const {
						availableDiskSpace = 0,
						currentDiskSpaceUsed = 0,
					} = instance.get('elasticSearchDiskSpace');

					const isLowOnDiskSpace =
						availableDiskSpace && currentDiskSpaceUsed
							? availableDiskSpace < 1.5 * currentDiskSpaceUsed
							: true;

					const status =
						isConcurrentMode && isLowOnDiskSpace
							? 'warning'
							: 'info';

					const hideModal = Liferay.Util.LocalStorage.getItem(
						instance.ns(
							`${Liferay.ThemeDisplay.getUserId()}_hideReindexConfirmationModal`
						),
						Liferay.Util.LocalStorage.TYPES.FUNCTIONAL
					);

					if (!hideModal || (isConcurrentMode && isLowOnDiskSpace)) {
						Liferay.Util.openModal({
							bodyHTML: this._getConfirmationModalBodyHTML(
								data,
								isConcurrentMode,
								availableDiskSpace,
								currentDiskSpaceUsed,
								isLowOnDiskSpace
							),
							buttons: [
								{
									displayType: 'secondary',
									label: Liferay.Language.get('cancel'),
									type: 'cancel',
								},
								{
									autoFocus: true,
									displayType: 'primary',
									label: Liferay.Language.get('execute'),
									onClick: ({processClose}) => {
										this._saveConfirmationModalVisibility();

										processClose();

										this._onSubmitForm(data);
									},
								},
							],
							id: instance.ns('reindexActionsConfirmationModal'),
							size: 'md',
							status,
							title:
								isConcurrentMode && isLowOnDiskSpace
									? Liferay.Language.get(
											'reindex-elasticsearch-disk-space-warning'
									  )
									: data.cmd === 'reindexDictionaries'
									? Liferay.Language.get(
											'reindex-spell-check-dictionaries'
									  )
									: data.displayname
									? Liferay.Util.sub(
											Liferay.Language.get(
												'reindex-type-x'
											),
											'<' + data.displayname + '>'
									  )
									: Liferay.Language.get(
											'reindex-search-indexes'
									  ),
						});
					}
					else {
						this._onSubmitForm(data);
					}
				},

				_showError(message) {
					Liferay.Util.openToast({
						message,
						type: 'danger',
					});
				},

				_updateIndexActions() {
					const instance = this;

					const currentAdminIndexPanel = A.one(
						instance.get(STR_INDEX_ACTIONS_PANEL)
					);

					const executionMode = document.querySelector(
						`#${instance.ns('executionMode')}`
					)?.value;

					if (currentAdminIndexPanel) {
						Liferay.Util.fetch(instance.get(STR_URL), {
							method: 'POST',
						})
							.then((response) => {
								return response.text();
							})
							.then((response) => {
								const responseDataNode = A.Node.create(
									response
								);

								// Replace each progress bar

								const responseAdminIndexPanel = responseDataNode.one(
									instance.get(STR_INDEX_ACTIONS_PANEL)
								);

								if (
									currentAdminIndexPanel &&
									responseAdminIndexPanel
								) {
									const responseAdminIndexNodeList = responseAdminIndexPanel.all(
										instance.get(
											'indexActionWrapperSelector'
										)
									);

									const currentAdminIndexNodeList = currentAdminIndexPanel.all(
										instance.get(
											'indexActionWrapperSelector'
										)
									);

									const inProgress = currentAdminIndexNodeList.some(
										(currentNode, index) => {
											const responseAdminIndexNode = responseAdminIndexNodeList.item(
												index
											);

											return (
												currentNode.one(
													IN_PROGRESS_SELECTOR
												) ||
												responseAdminIndexNode.one(
													IN_PROGRESS_SELECTOR
												)
											);
										}
									);

									if (inProgress) {
										currentAdminIndexNodeList.each(
											(currentNode, index) => {
												const responseAdminIndexNode = responseAdminIndexNodeList.item(
													index
												);

												// If concurrent mode is enabled, disable the
												// buttons with the 'data-concurrent-disabled'
												// attribute.

												const executeButtonElement = responseAdminIndexNode.one(
													instance.get('submitButton')
												);

												if (
													executeButtonElement &&
													executionMode ===
														CONCURRENT_MODE &&
													executeButtonElement.attr(
														'data-concurrent-disabled'
													)
												) {
													executeButtonElement.addClass(
														'disabled'
													);
												}

												currentNode.replace(
													responseAdminIndexNode
												);
											}
										);
									}
								}

								// Add or remove the reload icon in the top
								// control menu bar

								const responseDocument = new DOMParser().parseFromString(
									response,
									'text/html'
								);

								const controlMenuId = instance.ns(
									'controlMenu'
								);
								const controlMenuCategoryClassName = `${instance.get(
									'controlMenuCategoryKey'
								)}-control-group`;

								const currentControlMenu = document.getElementById(
									controlMenuId
								);
								const responseControlMenu = responseDocument.getElementById(
									controlMenuId
								);

								if (currentControlMenu && responseControlMenu) {
									const currentControlMenuCategory = currentControlMenu.getElementsByClassName(
										controlMenuCategoryClassName
									)[0];

									const currentReloadItem = instance._getControlMenuReloadItem(
										currentControlMenuCategory
									);

									const responseControlMenuCategory = responseControlMenu.getElementsByClassName(
										controlMenuCategoryClassName
									)[0];

									const responseReloadItem = instance._getControlMenuReloadItem(
										responseControlMenuCategory
									);

									if (
										!currentReloadItem &&
										responseReloadItem
									) {
										currentControlMenuCategory.appendChild(
											responseReloadItem
										);
									}
									else if (
										currentReloadItem &&
										!responseReloadItem
									) {
										currentReloadItem.remove();
									}
								}

								// Start timeout for refreshing the data

								let renderInterval = INTERVAL_RENDER_IDLE;

								if (instance._isBackgroundTaskInProgress()) {
									renderInterval = INTERVAL_RENDER_IN_PROGRESS;
								}

								instance._laterTimeout = A.later(
									renderInterval,
									instance,
									'_updateIndexActions'
								);
							});
					}
				},

				bindUI() {
					const instance = this;

					instance._eventHandles.push(
						instance
							.get(STR_FORM)
							.delegate(
								STR_CLICK,
								A.bind('_onSubmit', instance),
								instance.get('submitButton')
							)
					);
				},

				destructor() {
					const instance = this;

					A.Array.invoke(instance._eventHandles, 'detach');

					instance._eventHandles = null;

					A.clearTimeout(instance._laterTimeout);
				},

				initializer() {
					const instance = this;

					instance._eventHandles = [];

					instance.bindUI();

					instance._laterTimeout = A.later(
						INTERVAL_RENDER_IN_PROGRESS,
						instance,
						'_updateIndexActions'
					);
				},
			},
		});

		Liferay.Portlet.Admin = Admin;
	},
	'',
	{
		requires: [
			'aui-io-plugin-deprecated',
			'liferay-portlet-base',
			'querystring-parse',
		],
	}
);
