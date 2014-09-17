AUI.add(
	'liferay-blogs',
	function(A) {
		var Lang = A.Lang;

		var STR_CLICK = 'click';

		var Blogs = A.Component.create(
			{
				ATTRS: {
					constants: {
						validator: Lang.isObject
					},

					editEntryURL: {
						validator: Lang.isString
					},

					entry:{
						validator: Lang.isObject
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
					},

					destructor: function() {
						var instance = this;

						if (instance._saveDraftTimer) {
							instance._saveDraftTimer.cancel();
						}

						(new A.EventHandle(instance._eventHandles)).detach();
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

						instance._eventHandles = eventHandles;
					},

					_getPrincipalForm: function(formName) {
						var instance = this;

						return instance.one('form[name=' + instance.ns(formName || 'fm') + ']');
					},

					_initDraftSaveInterval: function() {
						var instance = this;

						instance._saveDraftTimer = A.later(
							30000,
							instance,
							instance._saveEntry,
							[true, true]
						);

						var entry = instance.get('entry');

						instance._oldContent = entry ? entry.content : '';
						instance._oldSubtitle = entry ? entry.subtitle : '';
						instance._oldTitle = entry ? entry.title : '';
					},

					_previewEntry: function() {
						var instance = this;

						var constants = instance.get('constants');

						var form = instance._getPrincipalForm();

						instance.one('#' + constants.CMD, form).val(instance.get('entry') ? constants.UPDATE : constants.ADD);
						instance.one('#preview', form).val('true');
						instance.one('#workflowAction', form).val(constants.ACTION_SAVE_DRAFT);

						var titleEditor = window[instance.ns('titleEditor')];

						if (contentEditor) {
							instance.one('#title', form).val(titleEditor.getHTML());
						}

						var subtitleEditor = window[instance.ns('subtitleEditor')];

						if (subtitleEditor) {
							instance.one('#subtitle', form).val(subtitleEditor.getHTML());
						}

						var contentEditor = window[instance.ns('contentEditor')];

						if (contentEditor) {
							instance.one('#content', form).val(contentEditor.getHTML());
						}

						submitForm(form);
					},

					_saveEntry: function(draft, ajax) {
						var instance = this;

						var constants = instance.get('constants');

						var title = window[instance.ns('titleEditor')].getHTML();

						var subtitle = window[instance.ns('subtitleEditor')].getHTML();

						var content = window[instance.ns('contentEditor')].getHTML();

						var form = instance._getPrincipalForm();

						if (draft && ajax) {
							var hasData = (content !== '') && (title !== '');

							var hasChanged = (instance._oldContent !== content) || (instance._oldSubtitle !== subtitle) || (instance._oldTitle !== title);

							if (hasData && hasChanged) {
								var strings = instance.get('strings');

								var saveStatus = instance.one('#saveStatus');

								var data = instance.ns(
									{
										'assetTagNames': instance.one('#assetTagNames', form).val(),
										'cmd': constants.ADD,
										'content': content,
										'displayDateAmPm': instance.one('#displayDateAmPm', form).val(),
										'displayDateDay': instance.one('#displayDateDay', form).val(),
										'displayDateHour': instance.one('#displayDateHour', form).val(),
										'displayDateMinute': instance.one('#displayDateMinute', form).val(),
										'displayDateMonth': instance.one('#displayDateMonth', form).val(),
										'displayDateYear': instance.one('#displayDateYear', form).val(),
										'entryId': instance.one('#entryId', form).val(),
										'referringPortletResource': instance.one('#referringPortletResource', form).val(),
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
												if (saveStatus) {
													saveStatus.attr('className', 'alert alert-danger save-status');
													saveStatus.html(strings.saveDraftError);
												}
											},
											start: function() {
												Liferay.Util.toggleDisabled(instance.one('#publishButton'), true);

												if (saveStatus) {
													saveStatus.attr('className', 'alert alert-info save-status pending');
													saveStatus.html(strings.saveDraftMessage);
												}
											},
											success: function(event, id, obj) {
												instance._oldContent = content;
												instance._oldSubtitle = subtitle;
												instance._oldTitle = title;

												var _this = this;

												var message = _this.get('responseData');

												if (message) {
													instance.one('#entryId', form).val(message.entryId);
													instance.one('#redirect', form).val(message.redirect);

													var tabs1BackButton = instance.one('#tabs1TabsBack');

													if (tabs1BackButton) {
														tabs1BackButton.attr('href', message.redirect);
													}

													var cancelButton  = instance.one('#cancelButton');

													if (cancelButton) {
														cancelButton.attr('href', message.redirect);
													}

													if (saveStatus) {
														var entry = instance.get('entry');

														var saveText = (entry && entry.isPending) ? strings.savedAtMessage : strings.savedDraftAtMessage;

														var now = saveText.replace(/\{0\}/gim, (new Date()).toString());

														saveStatus.attr('className', 'alert alert-success save-status');
														saveStatus.html(now);
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
							instance.one('#' + constants.CMD, form).val(instance.get('entry') ? constants.UPDATE : constants.ADD);
							instance.one('#title', form).val(title);
							instance.one('#subtitle', form).val(subtitle);
							instance.one('#content', form).val(content);
							instance.one('#workflowAction', form).val(draft ? constants.ACTION_SAVE_DRAFT : constants.ACTION_PUBLISH);

							submitForm(form);
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