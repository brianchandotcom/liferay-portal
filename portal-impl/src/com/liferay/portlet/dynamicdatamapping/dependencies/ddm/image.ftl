<#include "../init.ftl">

<#if !(fields?? && fields.get(fieldName)??) && (fieldRawValue == "")>
	<#assign fieldRawValue = predefinedValue>
</#if>

<#assign fieldRawValue = paramUtil.getString(request, "${namespacedFieldName}", fieldRawValue)>

<#assign fileEntryTitle = languageUtil.get(locale, "drag-file-here")>

<#assign alt = "">
<#assign imageData = "">
<#assign folderId = "">

<#if fieldRawValue?has_content>
	<#assign fileJSONObject = getFileJSONObject(fieldRawValue)>

	<#assign alt = fileJSONObject.getString("alt")>
	<#assign imageData = fileJSONObject.getString("data")>
</#if>

<@aui["field-wrapper"] data=data>
	<div class="hide" id="${portletNamespace}${namespacedFieldName}ImageUpload"></div>

	<div class="hide" id="${portletNamespace}${namespacedFieldName}PreviewContainer">
		<a href="${imageData}">
			<img src="${imageData}" />
		</a>
	</div>

	<@aui.input helpMessage=escape(fieldStructure.tip) inlineField=true label=escape(label) name="${namespacedFieldName}Title" readonly="readonly" style="margin-bottom:0" type="text" value=fileEntryTitle />

	<div class="hide image-upload-progress" id="${portletNamespace}${namespacedFieldName}ImageUploadProgress">
		<div aria-valuemax="100" aria-valuemin="0" aria-valuenow="0" class="progress-bar" role="progressbar"></div>
	</div>

	<@aui.input name=namespacedFieldName type="hidden" value=fieldRawValue />

	<div>
		<@aui["button-row"]>
			<@aui.button id="${portletNamespace}${namespacedFieldName}UploadFile" value="upload" />

			<@aui.button id="${portletNamespace}${namespacedFieldName}ChooseFile" value="choose-from-library" />

			<#if imageData?has_content>
				<@aui.button id="${portletNamespace}${namespacedFieldName}PreviewFile" value="preview" />
			</#if>

			<@aui.button cssClass="${(imageData?has_content)?string('','hide')}" id="${portletNamespace}${namespacedFieldName}ClearFile" value="clear" />
		</@>
	</div>

	<@aui.input label="image-description" name="${namespacedFieldName}Alt" type="text" value="${alt}" />

	${fieldStructure.children}
</@>

<@aui.script use="aui-base,aui-image-viewer,json,liferay-notice,uploader">
	var acceptedFileFormats = ['image/gif', 'image/jpeg', 'image/jpg', 'image/png'];

	var clearFileButtonNode = A.one("#${portletNamespace}${namespacedFieldName}ClearFile");
	var titleNode = A.one('#${portletNamespace}${namespacedFieldName}Title');
	var fieldNode = A.one('#${portletNamespace}${namespacedFieldName}');
	var progressNode = A.one('#${portletNamespace}${namespacedFieldName}ImageUploadProgress');
	var previewFileButtonNode = A.one('#${portletNamespace}${namespacedFieldName}PreviewFile');
	var uploadFileButtonNode = A.one('#${portletNamespace}${namespacedFieldName}UploadFile');

	var viewer;

	if (previewFileButtonNode) {
		viewer = new A.ImageViewer(
			{
				links: '#${portletNamespace}${namespacedFieldName}PreviewContainer a',
				caption: '${alt}',
				preloadAllImages: true,
				zIndex: Liferay.zIndex.OVERLAY
			}
		);
	}

	function acceptFileFormat(fileFormat) {
		return acceptedFileFormats.indexOf(fileFormat) >= 0;
	}

	function clearUpload() {
		clearFileButtonNode.hide();
		previewFileButtonNode.hide();
		progressNode.hide();

		if (viewer) {
			viewer.hide();
		}

		window['${portletNamespace}${namespacedFieldName}clearFileEntry']('<@liferay_ui.message key=escape("drag-file-here") />');

		titleNode.val('<@liferay_ui.message key=escape("drag-file-here") />');
	}

	var notice = new Liferay.Notice(
		{
			toggleText: false,
			type: 'warning'
		}
	).hide();

	var uploader = new A.Uploader(
		{
			after: {
				fileselect: function (event) {
					var file = event.fileList[0];

					if (acceptFileFormat(file.get('type'))) {
						notice.hide();

						uploader.uploadAll();

						progressNode.show();
					}
				}
			},
			appendNewFiles: false,
			dragAndDropArea: titleNode,
			fileFieldName: 'file',
			fileFilters: acceptedFileFormats,
			on: {
				uploadprogress: function(event) {
					if (event.percentLoaded) {
						var percentLoaded = event.percentLoaded + '%';

						progressNode.one('.progress-bar').setStyle('width', percentLoaded);
					}
				},
				uploadcomplete: function(event) {
					var data;

					try {
						data = A.JSON.parse(event.data);

						if (data.status) {
							notice.html(data.message);
							notice.show();

							clearUpload();
						}
						else {
							clearUpload()

							fieldNode.val(A.JSON.stringify(data));
							titleNode.val(event.file.get('name'));

							progressNode.one('.progress-bar').setStyle('width', '100%');

							clearFileButtonNode.show();
						}
					}
					catch (e) {
						notice.html('<@liferay_ui.message key=escape("an-unexpected-error-occurred") />');
						notice.show();
					}
				},
				uploaderror: function(event) {
					notice.html('<@liferay_ui.message key=escape("an-unexpected-error-occurred") />');
					notice.show();
				}
			},
			uploadURL: '<@liferay_portlet.actionURL><@liferay_portlet.param name="struts_action" value="/journal/upload_image"></@liferay_portlet.param><@liferay_portlet.param name="cmd" value="add_dynamic"></@liferay_portlet.param><@liferay_portlet.param name="repositoryId" value="${scopeGroupId?c}"></@liferay_portlet.param><@liferay_portlet.param name="folderId" value="${folderId}"></@liferay_portlet.param></@liferay_portlet.actionURL>',
			withCredentials: false
		}
	).render('#${portletNamespace}${namespacedFieldName}ImageUpload');

	clearFileButtonNode.on(
		'click',
		function() {
			uploader.set('fileList', []);

			clearUpload();
		}
	);

	if (previewFileButtonNode && viewer) {
		previewFileButtonNode.on(
			'click',
			function() {
				viewer.render();
				viewer.set('currentIndex', 0);
				viewer.show();
			}
		);
	}

	uploadFileButtonNode.on('click', A.bind(uploader.openFileSelectDialog, uploader));
</@aui.script>

<#include "select-file-entry-actions.ftl">