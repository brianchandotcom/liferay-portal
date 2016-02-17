AUI.add(
	'liferay-blogs',
	function(A) {
		var Lang = A.Lang;

		var CSS_INVISIBLE = 'invisible';

		var STR_BLANK = '';

		var STR_CHANGE = 'change';

		var STR_CLICK = 'click';

		var STR_SUFFIX = '...';

		var Blogs = A.Component.create(
			{
				ATTRS: {
					constants: {
						validator: Lang.isObject
					},

					descriptionLength: {
						validator: Lang.isNumber,
						value: 400
					},

					editEntryURL: {
						validator: Lang.isString
					},

					entry: {
						validator: Lang.isObject
					},

					saveInterval: {
						value: 30000
					},

					strings: {
						validator: Lang.isObject,
						value: {
							confirmDiscardImages: Liferay.Language.get('uploads-are-in-progress-confirmation'),
							savedAtMessage: Liferay.Language.get('entry-saved-at-x'),
							savedDraftAtMessage: Liferay.Language.get('draft-saved-at-x'),
							saveDraftError: Liferay.Language.get('could-not-save-draft-to-the-server'),
							saveDraftMessage: Liferay.Language.get('saving-draft')
						}
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'liferay-blogs',

				NS: 'liferay-blogs',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._bindUI();

						var entry = instance.get('entry');

						var draftEntry = entry && entry.status === instance.get('constants').STATUS_DRAFT;

						var userEntry = entry && entry.userId === themeDisplay.getUserId();

						if (!entry || userEntry && draftEntry) {
							instance._initDraftSaveInterval();
						}

						var customDescriptionEnabled = entry && entry.customDescription;

						instance._customDescription = customDescriptionEnabled ? entry.description : STR_BLANK;
						instance._shortenDescription = !customDescriptionEnabled;

						instance.setDescription(window[instance.ns('contentEditor')].getText());
					},

					destructor: function() {
						var instance = this;

						if (instance._saveDraftTimer) {
							instance._saveDraftTimer.cancel();
						}

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					setDescription: function(text) {
						var instance = this;

						var description = instance._customDescription;

						if (instance._shortenDescription) {
							description = instance._shorten(text);
						}

						window[instance.ns('descriptionEditor')].setHTML(description);
					},

					_bindUI: function() {
						var instance = this;

						instance._captionNode = instance.one('.cover-image-caption');

						var eventHandles = [
							Liferay.on('coverImageDeleted', instance._removeCaption, instance),
							Liferay.on(['coverImageUploaded', 'coverImageSelected'], instance._showCaption, instance)
						];

						var publishButton = instance.one('#publishButton');

						if (publishButton) {
							eventHandles.push(
								publishButton.on(STR_CLICK, A.bind('_checkImagesBeforeSave', instance, false, false))
							);
						}

						var saveButton = instance.one('#saveButton');

						if (saveButton) {
							eventHandles.push(
								saveButton.on(STR_CLICK, A.bind('_checkImagesBeforeSave', instance, true, false))
							);
						}

						var customAbstractOptions = instance.one('#entryAbstractOptions');

						if (customAbstractOptions) {
							eventHandles.push(
								customAbstractOptions.delegate(STR_CHANGE, instance._configureAbstract, 'input[type="radio"]', instance)
							);
						}

						instance._editIcon = instance.one('#editIcon');
						instance._settingsIcon = instance.one('#settingsIcon');

						eventHandles.push(
							instance._editIcon.on(STR_CLICK, instance._switchView, instance),
							instance._settingsIcon.on(STR_CLICK, instance._switchView, instance)
						);

						instance._editSection = instance.one('#editSection');
						instance._settingsSection = instance.one('#settingsSection');

						instance._eventHandles = eventHandles;
					},

					_checkImagesBeforeSave: function(draft, ajax) {
						var instance = this;

						if (instance._hasTempImages()) {
							if (confirm(instance.get('strings').confirmDiscardImages)) {

								instance._getTempImages().each(
									function(node) {
										node.ancestor().remove();
									}
								);

								instance._saveEntry(draft, ajax);
							}
						}
						else {
							instance._saveEntry(draft, ajax);
						}
					},

					_configureAbstract: function(event) {
						var instance = this;

						var target = event.target;

						var description = instance._customDescription;

						instance._shortenDescription = target.val() === 'false';

						if (instance._shortenDescription) {
							instance._customDescription = window[instance.ns('descriptionEditor')].getHTML();

							description = window[instance.ns('contentEditor')].getText();
						}

						instance._setDescriptionReadOnly(instance._shortenDescription);

						instance.setDescription(description);
					},

					_getPrincipalForm: function(formName) {
						var instance = this;

						return instance.one('form[name=' + instance.ns(formName || 'fm') + ']');
					},

					_getTempImages: function() {
						var instance = this;

						return instance.all('img[data-random-id]');
					},

					_hasTempImages: function() {
						var instance = this;

						return instance._getTempImages().size() > 0;
					},

					_initDraftSaveInterval: function() {
						var instance = this;

						instance._saveDraftTimer = A.later(
							instance.get('saveInterval'),
							instance,
							function() {
								if (!instance._hasTempImages()) {
									instance._saveEntry(true, true);
								}
							},
							null,
							true
						);

						var entry = instance.get('entry');

						instance._oldContent = entry ? entry.content : STR_BLANK;
						instance._oldSubtitle = entry ? entry.subtitle : STR_BLANK;
						instance._oldTitle = entry ? entry.title : STR_BLANK;
					},

					_removeCaption: function() {
						var instance = this;

						var captionNode = instance._captionNode;

						if (captionNode) {
							captionNode.addClass(CSS_INVISIBLE);
						}

						window[instance.ns('coverImageCaptionEditor')].setHTML(STR_BLANK);
					},

					_saveEntry: function(draft, ajax) {
						var instance = this;

						var constants = instance.get('constants');

						var content = window[instance.ns('contentEditor')].getHTML();
						var coverImageCaption = window[instance.ns('coverImageCaptionEditor')].getHTML();
						var description = window[instance.ns('descriptionEditor')].getHTML();
						var subtitle = window[instance.ns('subtitleEditor')].getHTML();
						var title = window[instance.ns('titleEditor')].getHTML();

						var form = instance._getPrincipalForm();

						if (draft && ajax) {
							var hasData = content !== STR_BLANK && title !== STR_BLANK;

							var hasChanged = instance._oldContent !== content || instance._oldSubtitle !== subtitle || instance._oldTitle !== title;

							if (hasData && hasChanged) {
								var strings = instance.get('strings');

								var saveStatus = instance.one('#saveStatus');

								var allowPingbacks = instance.one('#allowPingbacks');
								var allowTrackbacks = instance.one('#allowTrackbacks');

								var data = instance.ns(
									{
										'allowPingbacks': allowPingbacks && allowPingbacks.val(),
										'allowTrackbacks': allowTrackbacks && allowTrackbacks.val(),
										'assetTagNames': instance.one('#assetTagNames').val(),
										'cmd': constants.ADD,
										'content': content,
										'coverImageCaption': coverImageCaption,
										'coverImageFileEntryCropRegion': instance.one('#coverImageFileEntryCropRegion').val(),
										'coverImageFileEntryId': instance.one('#coverImageFileEntryId').val(),
										'displayDateAmPm': instance.one('#displayDateAmPm').val(),
										'displayDateDay': instance.one('#displayDateDay').val(),
										'displayDateHour': instance.one('#displayDateHour').val(),
										'displayDateMinute': instance.one('#displayDateMinute').val(),
										'displayDateMonth': instance.one('#displayDateMonth').val(),
										'displayDateYear': instance.one('#displayDateYear').val(),
										'entryId': instance.one('#entryId').val(),
										'referringPortletResource': instance.one('#referringPortletResource').val(),
										'subtitle': subtitle,
										'title': title,
										'workflowAction': constants.ACTION_SAVE_DRAFT
									}
								);

								var customAttributes = form.all('[name^=' + instance.NS + 'ExpandoAttribute]');

								customAttributes.each(
									function(item, index, collection) {
										data[item.attr('name')] = item.val();
									}
								);

								A.io.request(
									instance.get('editEntryURL'),
									{
										data: data,
										dataType: 'JSON',
										on: {
											failure: function() {
												instance._updateStatus(strings.saveDraftError, 'alert alert-danger save-status');
											},
											start: function() {
												Liferay.Util.toggleDisabled(instance.one('#publishButton'), true);

												instance._updateStatus(strings.saveDraftMessage, 'alert alert-info save-status pending');
											},
											success: function(event, id, obj) {
												instance._oldContent = content;
												instance._oldSubtitle = subtitle;
												instance._oldTitle = title;

												var message = this.get('responseData');

												if (message) {
													instance.one('#coverImageFileEntryId').val(message.coverImageFileEntryId);

													instance.one('#entryId').val(message.entryId);

													if (message.updateRedirect) {
														instance.one('#redirect').val(message.redirect);
													}

													if (message.blogsEntryAttachmentReferences) {
														instance._updateImages(message.blogsEntryAttachmentReferences);
													}

													var tabs1BackButton = instance.one('#tabs1TabsBack');

													if (tabs1BackButton) {
														tabs1BackButton.attr('href', message.redirect);
													}

													var cancelButton = instance.one('#cancelButton');

													if (cancelButton) {
														cancelButton.attr('href', message.redirect);
													}

													if (saveStatus) {
														var entry = instance.get('entry');

														var saveText = entry && entry.pending ? strings.savedAtMessage : strings.savedDraftAtMessage;

														var now = saveText.replace(/\{0\}/gim, (new Date()).toString());

														instance._updateStatus(now, 'alert alert-success save-status');
													}
												}
												else {
													saveStatus.hide();
												}

												Liferay.Util.toggleDisabled(instance.one('#publishButton'), false);
											}
										}
									}
								);
							}
						}
						else {
							instance.one('#' + constants.CMD).val(instance.get('entry') ? constants.UPDATE : constants.ADD);

							instance.one('#content').val(content);
							instance.one('#coverImageCaption').val(coverImageCaption);
							instance.one('#description').val(description);
							instance.one('#subtitle').val(subtitle);
							instance.one('#title').val(title);
							instance.one('#workflowAction').val(draft ? constants.ACTION_SAVE_DRAFT : constants.ACTION_PUBLISH);

							submitForm(form);
						}
					},

					_setDescriptionReadOnly: function(readOnly) {
						var instance = this;

						var descriptionEditorNode = instance.one('#descriptionEditor');

						descriptionEditorNode.attr('contenteditable', !readOnly);
						descriptionEditorNode.toggleClass('readonly', readOnly);
					},

					_shorten: function(text) {
						var instance = this;

						var descriptionLength = instance.get('descriptionLength');

						if (text.length > descriptionLength) {
							text = text.substring(0, descriptionLength);

							if (STR_SUFFIX.length < descriptionLength) {
								var spaceIndex = text.lastIndexOf(' ', descriptionLength - STR_SUFFIX.length);

								text = text.substring(0, spaceIndex).concat(STR_SUFFIX);
							}
						}

						return text;
					},

					_showCaption: function() {
						var instance = this;

						var captionNode = instance._captionNode;

						if (captionNode) {
							captionNode.removeClass(CSS_INVISIBLE);
						}
					},

					_switchView: function() {
						var instance = this;

						instance._editSection.toggle();
						instance._settingsSection.toggle();

						instance._editIcon.toggle();
						instance._settingsIcon.toggle();
					},

					_updateImages: function(persistentImages) {
						var instance = this;

						persistentImages.forEach(
							function(item, index) {
								var el = instance.one('img[' + item.attributeDataImageId + '="' + item.fileEntryId + '"]');

								if (el) {
									el.attr('src', item.fileEntryUrl);

									el.removeAttribute(item.attributeDataImageId);
								}
							}
						);
					},

					_updateStatus: function(text, className) {
						var instance = this;

						var saveStatus = instance.one('#saveStatus');

						if (saveStatus) {
							saveStatus.html(text);

							saveStatus.attr('className', className);
						}
					}
				}
			}
		);

		Liferay.Blogs = Blogs;
	},
	'',
	{
		requires: ['aui-base', 'aui-io-request', 'liferay-portlet-base']
	}
);