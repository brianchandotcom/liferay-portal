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
	'liferay-item-selector-uploader',
	(A) => {
		const CSS_PROGRESS = 'progress';

		const CSS_UPLOADING = 'uploading';

		const NAME = 'itemselectoruploader';

		const PROGRESS_HEIGHT = '6';

		const STR_VALUE = 'value';

		const TPL_PROGRESS_BAR =
			'<div class="progress-container">' +
			'<div class="upload-details">' +
			'<strong id="{0}itemName"></strong>' +
			'<a href="javascript:void(0);" id="{0}cancel">' +
			Liferay.Language.get('cancel') +
			'</a>' +
			'</div>' +
			'<div class="' +
			CSS_PROGRESS +
			'"></div>' +
			'</div>';

		const ItemUploader = A.Component.create({
			AUGMENTS: [Liferay.PortletBase],

			EXTENDS: A.Base,

			NAME,

			NS: NAME,

			prototype: {
				_createProgressBar() {
					const instance = this;

					const rootNode = instance.rootNode;

					const progressbarNode = A.Node.create(
						A.Lang.sub(TPL_PROGRESS_BAR, [instance.NS])
					);

					rootNode.append(progressbarNode);

					instance._progressBarNode = progressbarNode;

					const progressbar = new A.ProgressBar({
						boundingBox: progressbarNode.one('.' + CSS_PROGRESS),
						height: PROGRESS_HEIGHT,
					}).render();

					instance._progressBar = progressbar;
				},

				_getUploader() {
					const instance = this;

					let uploader = instance._uploader;

					if (!uploader) {
						uploader = new A.Uploader({
							fileFieldName: 'imageSelectorFileName',
						});

						instance._uploader = uploader;
					}

					return uploader;
				},

				_onCancel() {
					const instance = this;

					instance._currentFile.cancelUpload();

					instance._stopProgress();

					instance.fire('itemUploadCancel');
				},

				_onUploadComplete(event) {
					const instance = this;

					instance._stopProgress();

					const data = JSON.parse(event.data);

					const eventName = data.success
						? 'itemUploadComplete'
						: 'itemUploadError';

					instance.fire(eventName, data);
				},

				_onUploadError(event) {
					const instance = this;

					event.target.cancelUpload();

					instance._stopProgress();

					instance.fire('itemUploadError', event.details[0]);
				},

				_onUploadProgress(event) {
					const instance = this;

					const percentLoaded = Math.round(event.percentLoaded);

					instance._progressBar.set(
						STR_VALUE,
						Math.ceil(percentLoaded)
					);
				},

				_stopProgress() {
					const instance = this;

					instance._progressBar.set(STR_VALUE, 0);

					instance.rootNode.removeClass(CSS_UPLOADING);
				},

				destructor() {
					const instance = this;

					if (instance._uploader) {
						instance._uploader.destroy();
					}

					if (instance._progressBar) {
						instance._progressBar.destroy();
					}

					new A.EventHandle(instance._eventHandles).detach();
				},

				initializer() {
					const instance = this;

					const uploader = instance._getUploader();

					instance._createProgressBar();

					const cancelBtn = instance._progressBarNode.one(
						'#' + instance.NS + 'cancel'
					);

					instance._eventHandles = [
						uploader.on(
							'uploadcomplete',
							instance._onUploadComplete,
							instance
						),
						uploader.on(
							'uploaderror',
							instance._onUploadError,
							instance
						),
						uploader.on(
							'uploadprogress',
							instance._onUploadProgress,
							instance
						),
						cancelBtn.on('click', instance._onCancel, instance),
					];
				},

				startUpload(file, url) {
					const instance = this;

					file = new A.FileHTML5(file);

					const uploader = instance._getUploader();

					uploader.upload(file, url);

					instance._currentFile = file;

					instance._progressBarNode
						.one('#' + instance.NS + 'itemName')
						.html(file.get('name'));

					instance.rootNode.addClass(CSS_UPLOADING);
				},
			},
		});

		A.LiferayItemSelectorUploader = ItemUploader;
	},
	'',
	{
		requires: [
			'aui-base',
			'aui-progressbar',
			'liferay-portlet-base',
			'uploader',
		],
	}
);
