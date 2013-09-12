CKEDITOR.dialog.add(
	'audio',
	function(editor) {
		var TPL_SCRIPT_PREFIX = 'AUI().use(' +
								'	"aui-base", "aui-audio",' +
								'	function(A) {' +
								'		var audioId = A.guid();' +
								'		var audioDivNode = A.one(".ckaudio-no-id");' +
								'		audioDivNode.attr("id", audioId);' +
								'		audioDivNode.removeClass("ckaudio-no-id");' +
								'		var audioConfig = {';
		
		var TPL_SCRIPT =		'			boundingBox: "#" + audioId,' +
								'			oggUrl: "{oggUrl}",' +
								'			url: "{url}"';
		
		var TPL_SCRIPT_SUFFIX = '		};' +
								'		new A.Audio(audioConfig).render();' +
								'	}' +
								');';

		function commitValue(audioNode) {
			var instance = this;

			var id = instance.id;
			var value = instance.getValue();

			var scriptNode = audioNode.getChild(1);

			var scriptTPL = null;
			var textScript = null;

			var audioOggUrl = audioNode.getAttribute('data-audio-ogg-url');			
			var audioUrl = audioNode.getAttribute('data-audio-url');			

			if (id === 'url') {
				audioNode.setAttribute('data-document-url', value);

				audioUrl = Liferay.Util.addParams('audioPreview=1&type=mp3', value);

				audioNode.setAttribute('data-audio-url', audioUrl);

				audioOggUrl = Liferay.Util.addParams('audioPreview=1&type=ogg', value);

				audioNode.setAttribute('data-audio-ogg-url', audioOggUrl);
				
				scriptTPL = new CKEDITOR.template(TPL_SCRIPT);

				textScript = scriptTPL.output(
					{
						oggUrl: audioOggUrl,						
						url: audioUrl
					}
				);

				scriptNode.setText(TPL_SCRIPT_PREFIX + textScript + TPL_SCRIPT_SUFFIX);
			}			
		}

		function loadValue(audioNode) {
			var instance = this;

			var id = instance.id;

			if (audioNode) {
				var value = null;

				if (id === 'url') {
					value = audioNode.getAttribute('data-document-url');
				}

				if (value !== null) {
					instance.setValue(value);
				}
			}
		}

		return {
			minHeight: 200,
			minWidth: 400,

			contents: [
				{
					elements:
					[
						{
							children: [
								{
									commit: commitValue,
									id: 'url',
									label: Liferay.Language.get('audio'),
									setup: loadValue,
									type: 'text'
								},
								{
									filebrowser:
									{
										action: 'Browse',
										target: 'info:url',
										url: editor.config.filebrowserBrowseUrl + '&Type=Audio'
									},
									hidden: 'true',
									id: 'browse',
									label: editor.lang.common.browseServer,
									style: 'display:inline-block;margin-top:10px;',
									type: 'button'
								}
							],
							type: 'hbox',
							widths: [ '', '100px']
						}
					],
					id: 'info'
				}
			],

			title: Liferay.Language.get('audio-properties'),

			onShow: function() {
				var instance = this;

				instance.fakeImage = null;

				var fakeImage = instance.getSelectedElement();

				editor.plugins.media.restoreElement (editor, instance, fakeImage, 'audio');				
			},
			
			onOk: function() {
				var instance = this;

				var STR_DIV = 'div';				

				var divNode = editor.plugins.media.createDivStructure(editor, 'liferayckeaudio audio-container', 'ckaudio-no-id');
				
				instance.commitContent(divNode);

				var newFakeImage = editor.createFakeElement(divNode, 'liferay_cke_audio', 'audio', false);				

				if (instance.fakeImage) {
					newFakeImage.replace(instance.fakeImage);

					editor.getSelection().selectElement(newFakeImage);
				}
				else {
					editor.insertElement(newFakeImage);
				}
			}
		};
	}
);