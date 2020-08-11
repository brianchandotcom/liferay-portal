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

(function () {
	const isIE = CKEDITOR.env.ie;

	/**
	 * CKEditor plugin which allows Drag&Drop of images directly into the editable area. The image will be encoded
	 * as Data URI. An event `beforeImageAdd` will be fired with the list of dropped images. If any of the listeners
	 * returns `false` or cancels the event, the images won't be added to the content. Otherwise,
	 * an event `imageAdd` will be fired with the inserted element into the editable area.
	 *
	 * @class CKEDITOR.plugins.addimages
	 */

	/**
	 * Fired before adding images to the editor.
	 *
	 * @event CKEDITOR.plugins.addimages#beforeImageAdd
	 * @instance
	 * @memberof CKEDITOR.plugins.addimages
	 * @param {Array} imageFiles Array of image files
	 */

	/**
	 * Fired when an image is being added to the editor successfully.
	 *
	 * @event CKEDITOR.plugins.addimages#imageAdd
	 * @instance
	 * @memberof CKEDITOR.plugins.addimages
	 * @param {CKEDITOR.dom.element} el The created image with src as Data URI
	 * @param {File} file The image file
	 */

	CKEDITOR.plugins.add('addimages', {
		_fetchBlob(url) {
			return Liferay.Util.fetch(url).then((response) => response.blob());
		},

		/**
		 * Accepts an array of dropped files to the editor. Then, it filters the images and sends them for further
		 * processing to {{#crossLink "CKEDITOR.plugins.addimages/_processFile:method"}}{{/crossLink}}
		 *
		 * @fires CKEDITOR.plugins.addimages#beforeImageAdd
		 * @instance
		 * @memberof CKEDITOR.plugins.addimages
		 * @method _handleFiles
		 * @param {Array} files Array of dropped files. Only the images from this list will be processed.
		 * @param {Object} editor The current editor instance
		 * @protected
		 */
		_handleFiles(files, editor) {
			let file;
			let i;

			const imageFiles = [];

			for (i = 0; i < files.length; i++) {
				file = files[i];

				if (file.type.indexOf('image') === 0) {
					imageFiles.push(file);
				}
			}

			const result = editor.fire('beforeImageAdd', {
				imageFiles,
			});

			if (result) {
				for (i = 0; i < imageFiles.length; i++) {
					file = imageFiles[i];

					this._processFile(file, editor);
				}
			}

			return false;
		},

		/**
		 * A list of src attributes used to store base64 images
		 */
		_imageSrcToReplace: [],

		/**
		 * Handles drag drop event. The function will create a selection from the current
		 * point and will send a list of files to be processed to
		 * {{#crossLink "CKEDITOR.plugins.addimages/_handleFiles:method"}}{{/crossLink}} method.
		 *
		 * @instance
		 * @memberof CKEDITOR.plugins.addimages
		 * @method _onDragDrop
		 * @param {CKEDITOR.dom.event} event dragdrop event, as received natively from CKEditor
		 * @protected
		 */
		_onDragDrop(event) {
			const nativeEvent = event.data.$;

			const transferFiles = nativeEvent.dataTransfer.files;

			if (transferFiles.length > 0) {
				new CKEDITOR.dom.event(nativeEvent).preventDefault();

				const editor = event.editor;

				this._handleFiles(transferFiles, editor);
			}
		},

		/**
		 * Handles drag enter event. In case of IE, this function will prevent the event.
		 *
		 * @instance
		 * @memberof CKEDITOR.plugins.addimages
		 * @method _onDragEnter
		 * @param {DOM event} event dragenter event, as received natively from CKEditor
		 * @protected
		 */
		_onDragEnter(event) {
			if (isIE) {
				this._preventEvent(event);
			}
		},

		/**
		 * Handles drag over event. In case of IE, this function will prevent the event.
		 *
		 * @instance
		 * @memberof CKEDITOR.plugins.addimages
		 * @method _onDragOver
		 * @param {DOM event} event dragover event, as received natively from CKEditor
		 * @protected
		 */
		_onDragOver(event) {
			if (isIE) {
				this._preventEvent(event);
			}
		},

		/**
		 * Handler for when images are uploaded
		 *
		 * @instance
		 * @memberof CKEDITOR.plugins.addimages
		 * @method _onImageUploaded
		 * @param {Object} event Event data
		 * @protected
		 */
		_onImageUploaded(event) {
			if (event.data && event.data.el) {
				const instance = this;

				const editor = event.editor;
				const fragment = CKEDITOR.htmlParser.fragment.fromHtml(
					editor.getData()
				);

				const filter = new CKEDITOR.htmlParser.filter({
					elements: {
						img(element) {
							const index = instance._imageSrcToReplace.indexOf(
								element.attributes.src
							);
							if (index !== -1) {
								instance._imageSrcToReplace.splice(index, 1);

								element.attributes.src = event.data.el.src;
							}
						},
					},
				});

				const writer = new CKEDITOR.htmlParser.basicWriter();

				fragment.writeChildrenHtml(writer, filter);

				editor.setData(writer.getHtml(), () => {
					editor.updateElement();
				});
			}
		},

		/**
		 * Checks if the pasted data is image or html.
		 * In the case of images, it passes it to
		 * {{#crossLink "CKEDITOR.plugins.addimages/_processFile:method"}}{{/crossLink}} for processing.
		 *
		 * @instance
		 * @memberof CKEDITOR.plugins.addimages
		 * @method _onPaste
		 * @param {CKEDITOR.dom.event} event A `paste` event, as received natively from CKEditor
		 * @protected
		 */
		_onPaste(event) {
			if (
				event.data &&
				event.data.$ &&
				event.data.$.clipboardData &&
				event.data.$.clipboardData.items &&
				event.data.$.clipboardData.items.length > 0
			) {
				const pastedData = event.data.$.clipboardData.items[0];

				if (pastedData.type.indexOf('image') === 0) {
					const imageFile = pastedData.getAsFile();

					this._processFile(imageFile, event.editor);
				}
			}
			else if (event.data && event.data.type === 'html') {
				const editor = event.editor;
				const fragment = CKEDITOR.htmlParser.fragment.fromHtml(
					event.data.dataValue
				);

				fragment.forEach((element) => {
					if (
						element.type === CKEDITOR.NODE_ELEMENT &&
						element.name === 'img'
					) {
						const base64Regex = /^data:image\/(.*);base64,/;
						const match = element.attributes.src.match(base64Regex);

						if (element.attributes.src && match) {
							const src = element.attributes.src;
							const extension = match[1];
							const name = `${Date.now().toString()}.${extension}`;

							// Mark this "src" attribute as something we want to replace

							this._imageSrcToReplace.push(src);

							this._fetchBlob(src)
								.then((blob) => {
									const file = new File([blob], name, {
										type: blob.type,
									});

									const element = CKEDITOR.dom.element.createFromHtml(
										`<img src="${src}">`
									);

									editor.fire('imageAdd', {
										el: element,
										file,
									});
								})
								.catch(() => {
									Liferay.Util.openToast({
										message: Liferay.Language.get(
											'an-unexpected-error-occurred-while-uploading-your-file'
										),
										type: 'danger',
									});
								});
						}
					}
				});
			}
		},

		/**
		 * Prevents a native event.
		 *
		 * @instance
		 * @memberof CKEDITOR.plugins.addimages
		 * @method _preventEvent
		 * @param {DOM event} event The event to be prevented.
		 * @protected
		 */
		_preventEvent(event) {
			event = new CKEDITOR.dom.event(event.data.$);

			event.preventDefault();
			event.stopPropagation();
		},

		/**
		 * Processes an image file. The function creates an img element and sets as source
		 * a Data URI, then fires an 'imageAdd' event via CKEditor's event system.
		 *
		 * @fires CKEDITOR.plugins.addimages#imageAdd
		 * @instance
		 * @memberof CKEDITOR.plugins.addimages
		 * @method _preventEvent
		 * @param {DOM event} event The event to be prevented.
		 * @protected
		 */
		_processFile(file, editor) {
			const reader = new FileReader();

			reader.addEventListener('loadend', () => {
				const bin = reader.result;

				const el = CKEDITOR.dom.element.createFromHtml(
					'<img src="' + bin + '">'
				);

				editor.insertElement(el);

				const imageData = {
					el,
					file,
				};

				editor.fire('imageAdd', imageData);
			});

			reader.readAsDataURL(file);
		},

		/**
		 * Initialization of the plugin, part of CKEditor plugin lifecycle.
		 * The function registers a 'dragenter', 'dragover', 'drop' and `paste` events on the editing area.
		 *
		 * @instance
		 * @memberof CKEDITOR.plugins.addimages
		 * @method init
		 * @param {Object} editor The current editor instance
		 */
		init(editor) {
			editor.once('contentDom', () => {
				editor.on('dragenter', this._onDragEnter.bind(this));
				editor.on('dragover', this._onDragOver.bind(this));
				editor.on('drop', this._onDragDrop.bind(this));
				editor.on('paste', this._onPaste.bind(this));
				editor.on('imageUploaded', this._onImageUploaded.bind(this));
			});

			AUI().use('aui-progressbar,uploader', (A) => {
				const ATTR_DATA_RANDOM_ID = 'data-random-id';
				const CSS_UPLOADING_IMAGE = 'uploading-image';

				const TPL_IMAGE_CONTAINER =
					'<div class="uploading-image-container"></div>';

				const TPL_PROGRESS_BAR = '<div class="progressbar"></div>';

				editor.on('imageAdd', (event) => {
					const eventData = event.data;

					let file = eventData.file;
					const image = eventData.el.$;

					const randomId = eventData.randomId || A.guid();

					image.setAttribute(ATTR_DATA_RANDOM_ID, randomId);

					image.classList.add(CSS_UPLOADING_IMAGE);

					this._tempImage = image;

					let uploader = eventData.uploader;

					if (uploader) {
						uploader.on('uploadcomplete', _onUploadComplete);
						uploader.on('uploaderror', _onUploadError);
						uploader.on('uploadprogress', _onUploadProgress);
					}
					else {
						file = new A.FileHTML5(file);

						uploader = new A.Uploader({
							fileFieldName: 'imageSelectorFileName',
							uploadURL: editor.config.uploadUrl,
						});

						uploader.on('uploadcomplete', _onUploadComplete);
						uploader.on('uploaderror', _onUploadError);
						uploader.on('uploadprogress', _onUploadProgress);

						uploader.set('postVarsPerFile', {
							randomId,
						});

						uploader.upload(file);
					}

					file.progressbar = _createProgressBar(image);
				});

				const _createProgressBar = (image) => {
					const imageContainerNode = A.Node.create(
						TPL_IMAGE_CONTAINER
					);
					const progressBarNode = A.Node.create(TPL_PROGRESS_BAR);

					A.one(image).wrap(imageContainerNode);

					imageContainerNode.appendChild(progressBarNode);

					const progressbar = new A.ProgressBar({
						boundingBox: progressBarNode,
					}).render();

					return progressbar;
				};

				const _onUploadComplete = (event) => {
					const target = event.details[0].target;

					const progressbar = target.progressbar;

					if (progressbar) {
						progressbar.destroy();
					}

					const data = JSON.parse(event.data);

					if (data.success) {
						const image = this._tempImage;

						if (image) {
							image.removeAttribute(ATTR_DATA_RANDOM_ID);
							image.classList.remove(CSS_UPLOADING_IMAGE);

							image.setAttribute(
								data.file.attributeDataImageId,
								data.file.fileEntryId
							);

							image.src = editor.config.attachmentURLPrefix
								? editor.config.attachmentURLPrefix +
								  data.file.title
								: data.file.url;

							const imageContainer = image.parentElement;

							A.one(image).unwrap(imageContainer);

							imageContainer.remove();

							editor.fire('imageUploaded', {
								el: image,
								fileEntryId: data.file.fileEntryId,
								uploadImageReturnType: '',
							});
						}
					}
					else {
						_onUploadError();
					}
				};

				const _onUploadError = () => {
					var image = this._tempImage;

					if (image) {
						image.parentElement.remove();
					}

					Liferay.Util.openToast({
						message: Liferay.Language.get(
							'an-unexpected-error-occurred-while-uploading-your-file'
						),
						type: 'danger',
					});
				};

				const _onUploadProgress = (event) => {
					var percentLoaded = Math.round(event.percentLoaded);

					var target = event.details[0].target;

					var progressbar = target.progressbar;

					if (progressbar) {
						progressbar.set('label', percentLoaded + ' %');

						progressbar.set('value', Math.ceil(percentLoaded));
					}
				};
			});
		},
	});
})();
