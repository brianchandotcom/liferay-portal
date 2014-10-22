AUI.add(
	'liferay-blogs',
	function(A) {
		var Lang = A.Lang;

		var STR_BLANK = '';

		var STR_CLICK = 'click';

		var STR_CHANGE = 'change';

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

						if (!entry || (userEntry && draftEntry)) {
							instance._initDraftSaveInterval();
						}

						var customDescriptionEnabled = entry && entry.customDescription;

						instance._customDescription = customDescriptionEnabled ? entry.description : STR_BLANK;
						instance._shortenDescription = !customDescriptionEnabled;

						instance.setDescription(window[instance.ns('contentEditor')].getHTML());
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

						var eventHandles = [];

						var publishButton = instance.one('#publishButton');

						if (publishButton) {
							eventHandles.push(
								publishButton.on(STR_CLICK, A.bind('_saveEntry', instance, false, false))
							);
						}

						var saveButton = instance.one('#saveButton');

						if (saveButton) {
							eventHandles.push(
								saveButton.on(STR_CLICK, A.bind('_saveEntry', instance, true, false))
							);
						}

						var previewButton = instance.one('#previewButton');

						if (previewButton) {
							eventHandles.push(
								previewButton.on(STR_CLICK, instance._previewEntry, instance)
							);
						}

						var customAbstractOptions = instance.one('#entryAbstractOptions');

						if (customAbstractOptions) {
							eventHandles.push(
								customAbstractOptions.delegate(STR_CHANGE, instance._configureAbstract, 'input[type="radio"]', instance)
							);
						}

						instance._eventHandles = eventHandles;
					},

					_configureAbstract: function(event) {
						var instance = this;

						var target = event.target;

						var description = instance._customDescription;

						instance._shortenDescription = (target.val() === 'false');

						if (instance._shortenDescription) {
							instance._customDescription = window[instance.ns('descriptionEditor')].getHTML();

							description = window[instance.ns('contentEditor')].getHTML();
						}

						instance._setDescriptionReadOnly(instance._shortenDescription);

						instance.setDescription(description);
					},

					_getPrincipalForm: function(formName) {
						var instance = this;

						return instance.one('form[name=' + instance.ns(formName || 'fm') + ']');
					},

					_initDraftSaveInterval: function() {
						var instance = this;

						instance._saveDraftTimer = A.later(
							instance.get('saveInterval'),
							instance,
							instance._saveEntry,
							[true, true],
							true
						);

						var entry = instance.get('entry');

						instance._oldContent = entry ? entry.content : STR_BLANK;
						instance._oldSubtitle = entry ? entry.subtitle : STR_BLANK;
						instance._oldTitle = entry ? entry.title : STR_BLANK;
					},

					_previewEntry: function() {
						var instance = this;

						var constants = instance.get('constants');

						var form = instance._getPrincipalForm();

						instance.one('#' + constants.CMD).val(instance.get('entry') ? constants.UPDATE : constants.ADD);

						instance.one('#preview').val('true');
						instance.one('#workflowAction').val(constants.ACTION_SAVE_DRAFT);

						var contentEditor = window[instance.ns('contentEditor')];

						if (contentEditor) {
							instance.one('#content').val(contentEditor.getHTML());
						}

						var descriptionEditor = window[instance.ns('descriptionEditor')];

						if (descriptionEditor) {
							instance.one('#description').val(descriptionEditor.getHTML());
						}

						var subtitleEditor = window[instance.ns('subtitleEditor')];

						if (subtitleEditor) {
							instance.one('#subtitle').val(subtitleEditor.getHTML());
						}

						var titleEditor = window[instance.ns('titleEditor')];

						if (titleEditor) {
							instance.one('#title').val(titleEditor.getHTML());
						}

						submitForm(form);
					},

					_saveEntry: function(draft, ajax) {
						var instance = this;

						var constants = instance.get('constants');
						var entry = instance.get('entry');

						var title = window[instance.ns('titleEditor')].getHTML();
						var subtitle = window[instance.ns('subtitleEditor')].getHTML();
						var content = window[instance.ns('contentEditor')].getHTML();
						var description = window[instance.ns('descriptionEditor')].getHTML();

						var form = instance._getPrincipalForm();

						if (draft && ajax) {
							var hasData = (content !== STR_BLANK) && (title !== STR_BLANK);

							var hasChanged = (instance._oldContent !== content) || (instance._oldSubtitle !== subtitle) || (instance._oldTitle !== title);

							if (hasData && hasChanged) {
								var strings = instance.get('strings');

								var saveStatus = instance.one('#saveStatus');

								var data = instance.ns(
									{
										'assetTagNames': instance.one('#assetTagNames').val(),
										'cmd': constants.ADD,
										'content': content,
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
													instance.one('#entryId').val(message.entryId);
													instance.one('#redirect').val(message.redirect);

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

														var saveText = (entry && entry.pending) ? strings.savedAtMessage : strings.savedDraftAtMessage;

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

							instance.one('#title').val(title);
							instance.one('#subtitle').val(subtitle);
							instance.one('#content').val(content);
							instance.one('#description').val(description);

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
								var spaceIndex = text.lastIndexOf(' ', (descriptionLength - STR_SUFFIX.length));

								text = text.substring(0, spaceIndex).concat(STR_SUFFIX);
							}
						}

						return text;
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