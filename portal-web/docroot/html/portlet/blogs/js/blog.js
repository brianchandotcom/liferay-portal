AUI.add(
	'liferay-blogs',
	function(A) {
		var Lang = A.Lang;

		var SELECTOR_SMALL_IMAGE_TYPE = '.lfr-blogs-small-image-type';

		var SELECTOR_SMALL_IMAGE_VALUE = '.lfr-blogs-small-image-value';

		var saveDraftIntervalId = null;

		var oldTitle = null;

		var oldSubtitle = null;

		var oldContent = null;

		var Blogs= A.Component.create(
			{
				ATTRS: {
					cmdValue:{},

					constants: {
						validator: Lang.isObject
					},

					content: {},

					entry:{
						validator: Lang.isObject
					},

					namespace: {
						value: ''
					},

					redirect: {
						value: ''
					},

					selectedCheckbox: {
						validator: Lang.isNumber
					},

					smallImage: {
						validator: Lang.isBoolean
					},

					text: {
						validator: Lang.isObject
					},

					url: {}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'liferay-blogs',

				NS: 'liferay-blogs',

				prototype: {

					initializer: function(config) {
						var instance = this;

						instance._container = instance.one('#smallImageContainer');

						instance._types = instance._container.all(SELECTOR_SMALL_IMAGE_TYPE);

						instance._values = instance._container.all(SELECTOR_SMALL_IMAGE_VALUE);

						instance._bindUI();

						instance._selectSmallImageType(instance.get('selectedCheckbox'));

						instance._initToggler();

					},

					destructor: function() {
						var instance = this;

						instance._ddHandler.destroy();
					},

					_bindUI: function() {
						var instance = this;

						instance._container.delegate(
							'change',
							function(event) {
								var index = instance._types.indexOf(event.currentTarget);

								instance._selectSmallImageType(index);
							},
							SELECTOR_SMALL_IMAGE_TYPE
						);

						var publishButton = instance.one('#publishButton');
						if (publishButton) {
							publishButton.on(
								'click',
								function() {
									instance._saveEntry(false, false);
								}
							);
						}

						var saveButton = instance.one('#saveButton');
						if (saveButton) {
							saveButton.on(
								'click',
								function() {
									instance._saveEntry(true, false);
								}
							);
						}

						var previewButton = instance.one('#previewButton');
						if (previewButton) {
							previewButton.on(
								'click',
								function() {
									instance._previewEntry();
								}
							)
						}

						var cancelButton = instance.one('#cancelButton');
						if (cancelButton) {
							cancelButton.on(
								'click',
								function() {
									instance.clearSaveDraftIntervalId();

									location.href = instance.get('redirect');
								}
							);
						}

					},

					initDraftSaveInterval: function() {
						var instance = this;

						var namespace = instance.get('namespace');

						instance.saveDraftIntervalId = setInterval(
							function() {
								instance._saveEntry(true, true);
							}, 30000);

						instance.oldTitle = document[namespace + 'fm'][namespace + 'title'].value;

						instance.oldContent = instance.get('content');
					},

					_initToggler: function() {
						var instance = this;

						var namespace = instance.get('namespace');

						new A.Toggler(
							{
								animated: true,
								content: '#' +namespace + 'smallImageContainer .lfr-blogs-small-image-content',
								expanded: instance.get('smallImage'),
								header:  '#' +namespace + 'smallImageContainer .lfr-blogs-small-image-header',
								on: {
									animatingChange: function(event) {

										var expanded = !this.get('expanded');

										instance.one('#smallImage').attr('checked', expanded);

										if (expanded) {
											instance._types.each(
												function(item, index, collection) {
													if (item.get('checked')) {
														instance._values.item(index).attr('disabled', false);
													}
												}
											);
										}
										else {
											instance._values.attr('disabled', true);
										}
									}
								}
							}
						);
					},

					clearSaveDraftIntervalId: function() {
						if (this.saveDraftIntervalId != null) {
							clearInterval(this.saveDraftIntervalId);
						}
					},

					_previewEntry: function() {
						var instance = this;
						var namespace = instance.get('namespace');
						var constants = instance.get('constants');

						document[namespace+'fm'][namespace + constants.CMD].value = instance.get('cmdValue');
						document[namespace+'fm'][namespace + 'preview'].value = 'true';
						document[namespace+'fm'][namespace + 'workflowAction'].value = constants.ACTION_SAVE_DRAFT ;

						if (window[namespace + 'editor']) {
							document[namespace+'fm'][namespace+'content'].value = window[namespace + 'editor'].getHTML();
						}

						submitForm(document[namespace+'fm']);
					},

					_saveEntry: function(draft, ajax) {

						var instance = this;
						var namespace = instance.get('namespace');
						var constants = instance.get('constants');

						var title = window[namespace + 'title'].getHTML();
						var subtitle = window[namespace + 'subtitle'].getHTML();
						var content = window[namespace + 'content'].getHTML();

						var publishButton = instance.one('#publishButton');
						var cancelButton  = instance.one('#cancelButton');

						var saveStatus = instance.one('#saveStatus');
						var saveText = instance.get('text').saveText;
						var saveError = instance.get('text').saveDraftError;
						var saveDraft = instance.get('text').saveDraftMessage;

						if (draft && ajax) {
							if ((title == '') || (content == '')) {
								return;
							}

							if ((instance.oldTitle == title) &&
								(instance.oldSubtitle == subtitle) &&
								(instance.oldContent == content)) {
								return;
							}

							instance.oldTitle = title;
							instance.oldContent = content;

							var data = {};
							data[namespace + 'assetTagNames'] = document[namespace+'fm'][namespace + 'assetTagNames'].value;
							data[namespace + constants.CMD] = constants.ADD;
							data[namespace + 'content'] = content;
							data[namespace + 'displayDateAmPm'] = document[namespace+'fm'][namespace + 'displayDateAmPm'].value;
							data[namespace + 'displayDateDay'] = document[namespace+'fm'][namespace + 'displayDateDay'].value;
							data[namespace + 'displayDateHour'] = document[namespace+'fm'][namespace + 'displayDateHour'].value;
							data[namespace + 'displayDateMinute'] = document[namespace+'fm'][namespace + 'displayDateMinute'].value;
							data[namespace + 'displayDateMonth'] = document[namespace+'fm'][namespace + 'displayDateMonth'].value;
							data[namespace + 'displayDateYear'] = document[namespace+'fm'][namespace + 'displayDateYear'].value;
							data[namespace + 'entryId'] = document[namespace+'fm'][namespace + 'entryId'].value;
							data[namespace + 'referringPortletResource'] = document[namespace+'fm'][namespace + 'referringPortletResource'].value;
							data[namespace + 'subtitle'] = subtitle;
							data[namespace + 'title'] = title;
							data[namespace + 'workflowAction'] = constants.ACTION_SAVE_DRAFT;

							var customAttributes = A.one(document[namespace+'fm']).all('[name^=' + namespace + 'ExpandoAttribute]');

							customAttributes.each(
								function(item, index, collection) {
									data[item.attr('name')] = item.val();
								}
							);

							A.io.request(
								instance.get('url'),
								{
									data: data,
									dataType: 'JSON',
									on: {
										failure: function() {
											if (saveStatus) {
												saveStatus.attr('className', 'alert alert-danger save-status');
												saveStatus.html(saveError);
											}
										},
										start: function() {
											Liferay.Util.toggleDisabled(publishButton, true);

											if (saveStatus) {
												saveStatus.attr('className', 'alert alert-info save-status pending');
												saveStatus.html(saveDraft);
											}
										},
										success: function(event, id, obj) {
											var _this = this;

											var message = _this.get('responseData');

											if (message) {
												document[namespace+'fm'][namespace + 'entryId'].value = message.entryId;
												document[namespace+'fm'][namespace + 'redirect'].value = message.redirect;

												var tabs1BackButton = instance.one('#tabs1TabsBack');

												if (tabs1BackButton) {
													tabs1BackButton.attr('href', message.redirect);
												}

												if (cancelButton) {
													cancelButton.detach('click');

													cancelButton.on(
														'click',
														function() {
															location.href = message.redirect;
														}
													);
												}

												var now = saveText.replace(/\[TIME\]/gim, (new Date()).toString());

												if (saveStatus) {
													saveStatus.attr('className', 'alert alert-success save-status');
													saveStatus.html(now);
												}
											}
											else {
												saveStatus.hide();
											}

											Liferay.Util.toggleDisabled(publishButton, false);
										}
									}
								}
							);

						}
						else {
							instance.clearSaveDraftIntervalId();

							document[namespace+'fm'][namespace + constants.CMD].value = instance.get('cmdValue');
							document[namespace+'fm'][namespace + 'title'].value = title;
							document[namespace+'fm'][namespace + 'subtitle'].value = subtitle;
							document[namespace+'fm'][namespace + 'content'].value = content;

							if (draft) {
								document[namespace+'fm'][namespace + 'workflowAction'].value = constants.ACTION_SAVE_DRAFT;
							}
							else {
								document[namespace+'fm'][namespace + 'workflowAction'].value = constants.ACTION_PUBLISH;
							}
							submitForm(document[namespace+'fm']);
						}

					},

					_selectSmallImageType: function(index) {
						var instance = this;

						instance._types.attr('checked', false);

						instance._types.item(index).attr('checked', true);

						instance._values.attr('disabled', true);

						instance._values.item(index).attr('disabled', false);
					}
				}
			}
		);

		Liferay.Blogs = Blogs;
	},
	'',
	{
		requires: ['aui-base', 'aui-io', 'aui-toggler', 'liferay-portlet-base']
	}
);
