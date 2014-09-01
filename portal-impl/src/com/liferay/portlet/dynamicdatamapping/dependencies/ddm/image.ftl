<#include "../init.ftl">

<@aui["field-wrapper"] data=data>
	<@aui.input helpMessage=escape(fieldStructure.tip) inlineField=true label=escape(label) name="${namespacedFieldName}Title" readonly="readonly" style="margin-bottom:0" type="text" value=fileEntryTitle />

	<div class="progress">
		<div aria-valuemax="100" aria-valuemin="0" aria-valuenow="0" class="progress-bar" role="progressbar">
		</div>
	</div>

	<div>
		<@aui["button-row"]>
			<@aui.button id="${portletNamespace}${namespacedFieldName}ChooseFile" value="choose-from-library" />

			<div class="image-upload-container" id="imageUpload"></div>

			<button class="btn btn-default clear-file" id="${portletNamespace}${namespacedFieldName}ClearFile" type="button">Clear</button>
		</@>
	</div>

	<@aui.input name=namespacedFieldName type="hidden" value=fieldRawValue />

	<#assign alt = "">
	<#assign folderId = "">

	<#if fieldRawValue?has_content>
		<#assign fileJSONObject = getFileJSONObject(fieldRawValue)>

		<#assign fileEntry = getFileEntry(fileJSONObject)>

		<#assign alt = fileJSONObject.getString("alt")>
		<#assign src = fileJSONObject.getString("data")>
	</#if>

	<#if src?has_content>
		[ <a href="javascript:;" id="${portletNamespace}${namespacedFieldName}ToggleImage" onClick="${portletNamespace}${namespacedFieldName}ToggleImage();">${languageUtil.get(locale, "show")}</a> ]

		<div class="ddm-image-preview hide" id="${portletNamespace}${namespacedFieldName}Container">
			<#if !required>
				<a href="javascript:;" id="${portletNamespace}${namespacedFieldName}DeleteImage" onClick="${portletNamespace}${namespacedFieldName}ToggleDeleteImage();">${languageUtil.get(locale, "delete")}</a>
			</#if>

			<img id="${portletNamespace}${namespacedFieldName}Image" src="${src}" />
		</div>
	</#if>

	<@aui.input label="image-description" name="${namespacedFieldName}Alt" type="text" value="${alt}" />

	${fieldStructure.children}
</@>

<@aui.script>
	var uploader;

	YUI().use('aui-base', 'uploader', function(Y) {
		var acceptedFileFormats = ['image/gif', 'image/jpeg', 'image/jpg', 'image/png'];

		var clearFileButton = Y.one("#${portletNamespace}${namespacedFieldName}ClearFile");

		var titleNode = Y.one('#${portletNamespace}${namespacedFieldName}Title');
		var fieldNode = Y.one('#${portletNamespace}${namespacedFieldName}');

		setInputValue();

		function isFileFormatAccepted(fileFormat) {
			return acceptedFileFormats.indexOf(fileFormat) >= 0;
		}

		function normalizeUploadFileButton() {
			var btn = Y.one('#imageUpload button');

			btn.removeClass('yui3-button');
			btn.addClass('btn btn-default');

			btn.setHTML('Upload');
		}

		function onTotalUploadProgress(event) {
			Y.one('.progress').setStyle('display', 'block');

			var progressBar = Y.one('.progress .progress-bar');

			var percentLoaded = event.percentLoaded + '%';

			progressBar.setStyle('width', percentLoaded);
		}

		function setDragDropArea() {
			uploader.set('dragAndDropArea', titleNode);
		}

		function setInputValue(value) {
			if (!value) {
				value = '<@liferay_ui.message key=escape("drag-file-here") />';
			}
			titleNode.val(value);
		}

		if (Y.Uploader.TYPE !== 'none' && !Y.UA.ios) {
			uploader = new Y.Uploader({ appendNewFiles: false,
										fileFilters: acceptedFileFormats,
										fileFieldName: 'file',
	                                    height: '34px',
	                                    uploadURL: '<@liferay_portlet.actionURL><@liferay_portlet.param name="struts_action" value="/journal/upload_image"></@liferay_portlet.param><@liferay_portlet.param name="cmd" value="add_dynamic"></@liferay_portlet.param><@liferay_portlet.param name="repositoryId" value="${scopeGroupId?c}"></@liferay_portlet.param><@liferay_portlet.param name="folderId" value="${folderId}"></@liferay_portlet.param></@liferay_portlet.actionURL>',
										width: '100px',
	                                    withCredentials: false
	                                }).render('#imageUpload');

			normalizeUploadFileButton();
			setDragDropArea();

			uploader.after(
				'fileselect',
				function (event) {
					var file = event.fileList[0];

					if (isFileFormatAccepted(file.get('type'))) {
						if (titleNode) {
							setInputValue(file.get('name'));
						}

						uploader.uploadAll();

						uploader.on('uploadcomplete', function(evt) {

							fieldNode.val(evt.data);
						});

						uploader.on('uploaderror', function(evt) {

							// TODO

						});

						clearFileButton.setStyle('display', 'inline-block');
					}
				}
			);

			clearFileButton.on(
				'click',
				function() {
					var fileList = uploader.get('fileList');

					var progressBar = Y.one('.progress');

					if (fileList.length > 0) {
						uploader.set('fileList', []);
					}

					progressBar.setStyle('display', 'none');

					window['${portletNamespace}${namespacedFieldName}clearFileEntry']('<@liferay_ui.message key=escape("drag-file-here") />');

					this.setStyle('display', 'none');
			});

			uploader.on('totaluploadprogress', onTotalUploadProgress);
		}
	});

	Liferay.provide(
		window,
		'${portletNamespace}${namespacedFieldName}ToggleImage',
		function() {
			var A = AUI();

			var toggleText = '${languageUtil.get(locale, "show")}';

			var containerNode = A.one('#${portletNamespace}${namespacedFieldName}Container');

			if (containerNode.test(':hidden')) {
				toggleText = '${languageUtil.get(locale, "hide")}';
			}

			A.one('#${portletNamespace}${namespacedFieldName}ToggleImage').setContent(toggleText);

			containerNode.toggle();
		},
		['aui-base']
	);

	Liferay.provide(
		window,
		'${portletNamespace}${namespacedFieldName}ToggleDeleteImage',
		function() {
			var A = AUI();

			var buttonText = '${languageUtil.get(locale, "cancel")}';

			var disabled = true;

			var chooseFileButton = A.one('#${portletNamespace}${namespacedFieldName}ChooseFile');

			var imageAltInputNode = A.one('#${portletNamespace}${namespacedFieldName}Alt');
			var imageInputNode = A.one('#${portletNamespace}${namespacedFieldName}');

			if (imageInputNode.get('disabled')) {
				buttonText = '${languageUtil.get(locale, "delete")}';

				disabled = false;
			}

			A.one('#${portletNamespace}${namespacedFieldName}DeleteImage').setContent(buttonText);

			chooseFileButton.attr('disabled', disabled);

			imageAltInputNode.attr('disabled', disabled);
			imageInputNode.attr('disabled', disabled);

			uploader.set('enabled', !disabled);

			A.one('#${portletNamespace}${namespacedFieldName}Image').toggle();
		},
		['aui-base']
	);
</@>

<#include "select-file-entry-actions.ftl">