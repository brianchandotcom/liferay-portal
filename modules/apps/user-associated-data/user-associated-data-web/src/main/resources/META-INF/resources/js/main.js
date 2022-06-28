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

AUI.add(
	'liferay-uad-export',
	(A) => {
		const Lang = A.Lang;

		const isString = Lang.isString;

		const RENDER_INTERVAL_IDLE = 60000;

		const RENDER_INTERVAL_IN_PROGRESS = 2000;

		const UADExport = A.Component.create({
			ATTRS: {
				exportProcessesNode: {
					setter: '_setNode',
				},

				exportProcessesResourceURL: {
					setter: 'isString',
				},
			},

			AUGMENTS: [Liferay.PortletBase],

			EXTENDS: A.Base,

			NAME: 'uadexport',

			prototype: {
				_isBackgroundTaskInProgress() {
					const instance = this;

					const exportProcessesNode = instance.get(
						'exportProcessesNode'
					);

					return !!exportProcessesNode.one(
						'.export-process-status-in-progress'
					);
				},

				_renderExportProcesses() {
					const instance = this;

					const exportProcessesNode = instance.get(
						'exportProcessesNode'
					);
					const exportProcessesResourceURL = instance.get(
						'exportProcessesResourceURL'
					);

					if (exportProcessesNode && exportProcessesResourceURL) {
						Liferay.Util.fetch(exportProcessesResourceURL)
							.then((response) => {
								return response.text();
							})
							.then((response) => {
								exportProcessesNode.plug(A.Plugin.ParseContent);

								exportProcessesNode.empty();

								exportProcessesNode.setContent(response);

								instance._scheduleRenderProcess();
							});
					}
				},

				_scheduleRenderProcess() {
					const instance = this;

					let renderInterval = RENDER_INTERVAL_IDLE;

					if (instance._isBackgroundTaskInProgress()) {
						renderInterval = RENDER_INTERVAL_IN_PROGRESS;
					}

					instance._renderTimer = A.later(
						renderInterval,
						instance,
						instance._renderExportProcesses
					);
				},

				_setNode(val) {
					const instance = this;

					if (isString(val)) {
						val = instance.one(val);
					}
					else {
						val = A.one(val);
					}

					return val;
				},

				destructor() {
					const instance = this;

					if (instance._renderTimer) {
						instance._renderTimer.cancel();
					}
				},

				initializer() {
					const instance = this;

					instance._renderTimer = A.later(
						RENDER_INTERVAL_IN_PROGRESS,
						instance,
						instance._renderExportProcesses
					);

					Liferay.once(
						'beforeNavigate',
						instance.destroy.bind(instance)
					);
				},
			},
		});

		Liferay.UADExport = UADExport;
	},
	'',
	{
		requires: ['aui-parse-content', 'liferay-portlet-base'],
	}
);
