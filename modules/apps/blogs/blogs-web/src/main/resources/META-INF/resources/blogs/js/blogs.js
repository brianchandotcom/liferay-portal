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
	'liferay-blogs',
	A => {
		var Lang = A.Lang;

		var CSS_INVISIBLE = 'invisible';

		var STR_BLANK = '';

		var STR_CHANGE = 'change';

		var STR_CLICK = 'click';

		var STR_SUFFIX = '...';

		var Blogs = A.Component.create({
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
						confirmDiscardImages: Liferay.Language.get(
							'uploads-are-in-progress-confirmation'
						),
						saveDraftError: Liferay.Language.get(
							'could-not-save-draft-to-the-server'
						),
						saveDraftMessage: Liferay.Language.get('saving-draft'),
						savedAtMessage: Liferay.Language.get(
							'entry-saved-at-x'
						),
						savedDraftAtMessage: Liferay.Language.get(
							'draft-saved-at-x'
						),
						titleRequiredAtPublish: Liferay.Language.get(
							'this-field-is-required-to-publish-the-entry'
						)
					}
				}
			},

			AUGMENTS: [Liferay.PortletBase],

			EXTENDS: A.Base,

			NAME: 'liferay-blogs',

			NS: 'liferay-blogs',

			prototype: {
				_automaticURL() {
					return (
						this.one('#urlOptions')
							.one('input:checked')
							.val() === 'true'
					);
				},

				_beforePublishBtnClick() {
					var instance = this;

					var form = Liferay.Form.get(instance.ns('fm'));

					var strings = instance.get('strings');

					form.addRule(
						instance.ns('titleEditor'),
						'required',
						strings.titleRequiredAtPublish
					);
				},

				_beforeSaveBtnClick() {
					var instance = this;

					var form = Liferay.Form.get(instance.ns('fm'));

					form.removeRule(instance.ns('titleEditor'), 'required');
				},

				_bindUI() {
					var instance = this;

					instance._captionNode = instance.one(
						'.cover-image-caption'
					);

					var eventHandles = [
						Liferay.on(
							'coverImageDeleted',
							instance._removeCaption,
							instance
						),
						Liferay.on(
							['coverImageUploaded', 'coverImageSelected'],
							instance._showCaption,
							instance
						)
					];

					var publishButton = instance.one('#publishButton');

					if (publishButton) {
						eventHandles.push(
							publishButton.before(
								STR_CLICK,
								A.bind('_beforePublishBtnClick', instance)
							),
							publishButton.on(
								STR_CLICK,
								A.bind(
									'_checkImagesBeforeSave',
									instance,
									false,
									false
								)
							)
						);
					}

					var saveButton = instance.one('#saveButton');

					if (saveButton) {
						eventHandles.push(
							saveButton.before(
								STR_CLICK,
								A.bind('_beforeSaveBtnClick', instance)
							),
							saveButton.on(
								STR_CLICK,
								A.bind(
									'_checkImagesBeforeSave',
									instance,
									true,
									false
								)
							)
						);
					}

					var customAbstractOptions = instance.one(
						'#entryAbstractOptions'
					);

					if (customAbstractOptions) {
						eventHandles.push(
							customAbstractOptions.delegate(
								STR_CHANGE,
								instance._configureAbstract,
								'input[type="radio"]',
								instance
							)
						);
					}

					var urlOptions = instance.one('#urlOptions');

					eventHandles.push(
						urlOptions.delegate(
							STR_CHANGE,
							instance._onChangeURLOptions,
							'input[type="radio"]',
							instance
						)
					);

					instance._eventHandles = eventHandles;
				},

				_checkImagesBeforeSave(draft, ajax) {
					var instance = this;

					if (instance._hasTempImages()) {
						if (
							confirm(
								instance.get('strings').confirmDiscardImages
							)
						) {
							instance._getTempImages().each(node => {
								node.ancestor().remove();
							});

							instance._saveEntry(draft, ajax);
						}
					} else {
						instance._saveEntry(draft, ajax);
					}
				},

				_configureAbstract(event) {
					var instance = this;

					var target = event.target;

					var description = instance._customDescription;

					instance._shortenDescription = target.val() === 'false';

					if (instance._shortenDescription) {
						instance._customDescription = instance
							.one('#description')
							.val();

						description = window[
							instance.ns('contentEditor')
						].getText();
					}

					instance.setDescription(description);
				},

				_getContentImages(content) {
					var contentDom = document.createElement('div');

					contentDom.innerHTML = content;

					var contentImages = contentDom.getElementsByTagName('img');

					var finalImages = [];

					for (var i = 0; i < contentImages.length; i++) {
						var currentImage = contentImages[i];

						if (
							currentImage.parentElement.tagName.toLowerCase() ===
							'picture'
						) {
							finalImages.push(currentImage.parentElement);
						} else {
							finalImages.push(currentImage);
						}
					}

					return finalImages;
				},

				_getPrincipalForm(formName) {
					var instance = this;

					return instance.one(
						'form[name=' + instance.ns(formName || 'fm') + ']'
					);
				},

				_getTempImages() {
					var instance = this;

					return instance.all('img[data-random-id]');
				},

				_hasTempImages() {
					var instance = this;

					return instance._getTempImages().size() > 0;
				},

				_initDraftSaveInterval() {
					var instance = this;

					instance._saveDraftTimer = A.later(
						instance.get('saveInterval'),
						instance,
						() => {
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

				_onChangeURLOptions() {
					var instance = this;

					var urlTitleInput = instance.one('#urlTitle');
					var urlTitleInputLabel = instance.one(
						'[for="' + instance.ns('urlTitle') + '"]'
					);

					if (instance._automaticURL()) {
						instance._lastCustomURL = urlTitleInput.val();

						var title = window[
							instance.ns('titleEditor')
						].getText();

						instance.updateFriendlyURL(title);

						urlTitleInput.setAttribute('disabled', true);

						urlTitleInputLabel.addClass('disabled');
					} else {
						urlTitleInput.val(
							instance._lastCustomURL || urlTitleInput.val()
						);

						urlTitleInput.removeAttribute('disabled');

						urlTitleInputLabel.removeClass('disabled');
					}
				},

				_removeCaption() {
					var instance = this;

					var captionNode = instance._captionNode;

					if (captionNode) {
						captionNode.addClass(CSS_INVISIBLE);
					}

					window[instance.ns('coverImageCaptionEditor')].setHTML(
						STR_BLANK
					);
				},

				_saveEntry(draft, ajax) {
					var instance = this;

					var constants = instance.get('constants');

					var content = window[
						instance.ns('contentEditor')
					].getHTML();
					var coverImageCaption = window[
						instance.ns('coverImageCaptionEditor')
					].getHTML();
					var subtitle = window[
						instance.ns('subtitleEditor')
					].getHTML();
					var title = window[instance.ns('titleEditor')].getText();

					var automaticURL = instance
						.one(
							'input[name=' +
								instance.ns('automaticURL') +
								']:checked'
						)
						.val();

					var urlTitle = automaticURL
						? ''
						: instance.one('#urlTitle').val();

					var form = instance._getPrincipalForm();

					if (draft && ajax) {
						var hasData =
							content !== STR_BLANK &&
							(draft || title !== STR_BLANK);

						var hasChanged =
							instance._oldContent !== content ||
							instance._oldSubtitle !== subtitle ||
							instance._oldTitle !== title;

						if (hasData && hasChanged) {
							var strings = instance.get('strings');

							var saveStatus = instance.one('#saveStatus');

							var allowPingbacks = instance.one(
								'#allowPingbacks'
							);
							var allowTrackbacks = instance.one(
								'#allowTrackbacks'
							);

							var data = instance.ns({
								allowPingbacks:
									allowPingbacks && allowPingbacks.val(),
								allowTrackbacks:
									allowTrackbacks && allowTrackbacks.val(),
								assetTagNames: instance
									.one('#assetTagNames')
									.val(),
								cmd: constants.ADD,
								content,
								coverImageCaption,
								coverImageFileEntryCropRegion: instance
									.one('#coverImageFileEntryCropRegion')
									.val(),
								coverImageFileEntryId: instance
									.one('#coverImageFileEntryId')
									.val(),
								displayDateAmPm: instance
									.one('#displayDateAmPm')
									.val(),
								displayDateDay: instance
									.one('#displayDateDay')
									.val(),
								displayDateHour: instance
									.one('#displayDateHour')
									.val(),
								displayDateMinute: instance
									.one('#displayDateMinute')
									.val(),
								displayDateMonth: instance
									.one('#displayDateMonth')
									.val(),
								displayDateYear: instance
									.one('#displayDateYear')
									.val(),
								entryId: instance.one('#entryId').val(),
								referringPortletResource: instance
									.one('#referringPortletResource')
									.val(),
								subtitle,
								title,
								urlTitle,
								workflowAction: constants.ACTION_SAVE_DRAFT
							});

							var customAttributes = form.all(
								'[name^=' + instance.NS + 'ExpandoAttribute]'
							);

							customAttributes.each(item => {
								data[item.attr('name')] = item.val();
							});

							Liferay.Util.toggleDisabled(
								instance.one('#publishButton'),
								true
							);

							instance._updateStatus(strings.saveDraftMessage);

							const body = new URLSearchParams(data);

							Liferay.Util.fetch(instance.get('editEntryURL'), {
								body,
								method: 'POST'
							})
								.then(response => {
									return response.json();
								})
								.then(data => {
									instance._oldContent = content;
									instance._oldSubtitle = subtitle;
									instance._oldTitle = title;

									var message = data;

									if (message) {
										instance
											.one('#coverImageFileEntryId')
											.val(message.coverImageFileEntryId);

										instance
											.one('#entryId')
											.val(message.entryId);

										if (message.content) {
											instance._updateContentImages(
												message.content,
												message.attributeDataImageId
											);
										}

										if (saveStatus) {
											var entry = instance.get('entry');

											var saveText =
												entry && entry.pending
													? strings.savedAtMessage
													: strings.savedDraftAtMessage;

											var now = saveText.replace(
												/\{0\}/gim,
												new Date().toString()
											);

											instance._updateStatus(now);
										}
									} else {
										saveStatus.hide();
									}

									Liferay.Util.toggleDisabled(
										instance.one('#publishButton'),
										false
									);
								})
								.catch(() => {
									instance._updateStatus(
										strings.saveDraftError
									);
								});
						}
					} else {
						instance
							.one('#' + constants.CMD)
							.val(
								instance.get('entry')
									? constants.UPDATE
									: constants.ADD
							);

						instance.one('#content').val(content);
						instance
							.one('#coverImageCaption')
							.val(coverImageCaption);
						instance.one('#subtitle').val(subtitle);
						instance.one('#title').val(title);
						instance
							.one('#workflowAction')
							.val(
								draft
									? constants.ACTION_SAVE_DRAFT
									: constants.ACTION_PUBLISH
							);

						submitForm(form);
					}
				},

				_shorten(text) {
					var instance = this;

					var descriptionLength = instance.get('descriptionLength');

					if (text.length > descriptionLength) {
						text = text.substring(0, descriptionLength);

						if (STR_SUFFIX.length < descriptionLength) {
							var spaceIndex = text.lastIndexOf(
								' ',
								descriptionLength - STR_SUFFIX.length
							);

							text = text
								.substring(0, spaceIndex)
								.concat(STR_SUFFIX);
						}
					}

					return text;
				},

				_showCaption() {
					var instance = this;

					var captionNode = instance._captionNode;

					if (captionNode) {
						captionNode.removeClass(CSS_INVISIBLE);
					}
				},

				_updateContentImages(finalContent, attributeDataImageId) {
					var instance = this;

					var originalContent = window[
						instance.ns('contentEditor')
					].getHTML();

					var originalContentImages = instance._getContentImages(
						originalContent
					);

					var finalContentImages = instance._getContentImages(
						finalContent
					);

					if (
						originalContentImages.length !=
						finalContentImages.length
					) {
						return;
					}

					for (var i = 0; i < originalContentImages.length; i++) {
						var image = originalContentImages[i];

						var tempImageId = image.getAttribute(
							attributeDataImageId
						);

						if (tempImageId) {
							var el = instance.one(
								'img[' +
									attributeDataImageId +
									'"=' +
									tempImageId +
									'"]'
							);

							if (el) {
								var finalImage = finalContentImages[i];

								if (el.get('tagName') === finalImage.tagName) {
									el.removeAttribute('data-cke-saved-src');

									for (
										var j = 0;
										j < finalImage.attributes.length;
										j++
									) {
										var attr = finalImage.attributes[j];

										el.attr(attr.name, attr.value);
									}

									el.removeAttribute(attributeDataImageId);
								} else {
									el.replace(finalContentImages[i]);
								}
							}
						}
					}
				},

				_updateStatus(text) {
					var instance = this;

					var saveStatus = instance.one('#saveStatus');

					if (saveStatus) {
						saveStatus.html(text);
					}
				},

				destructor() {
					var instance = this;

					if (instance._saveDraftTimer) {
						instance._saveDraftTimer.cancel();
					}

					new A.EventHandle(instance._eventHandles).detach();
				},

				initializer() {
					var instance = this;

					instance._bindUI();

					var entry = instance.get('entry');

					var draftEntry =
						entry &&
						entry.status === instance.get('constants').STATUS_DRAFT;

					var userEntry =
						entry && entry.userId === themeDisplay.getUserId();

					if (!entry || (userEntry && draftEntry)) {
						instance._initDraftSaveInterval();
					}

					var customDescriptionEnabled =
						entry && entry.customDescription;

					instance._customDescription = customDescriptionEnabled
						? entry.description
						: STR_BLANK;
					instance._shortenDescription = !customDescriptionEnabled;

					instance.setDescription(
						window[instance.ns('contentEditor')].getText()
					);
				},

				setDescription(text) {
					var instance = this;

					var description = instance._customDescription;

					if (instance._shortenDescription) {
						description = instance._shorten(text);
					}

					var descriptionNode = instance.one('#description');

					descriptionNode.val(description);

					descriptionNode.attr(
						'disabled',
						instance._shortenDescription
					);

					var descriptionLabelNode = instance.one(
						'[for="' + instance.ns('description') + '"]'
					);

					var form = Liferay.Form.get(instance.ns('fm'));

					if (!instance._shortenDescription) {
						descriptionLabelNode.removeClass('disabled');

						form.addRule(instance.ns('description'), 'required');
					} else {
						descriptionLabelNode.addClass('disabled');

						form.removeRule(instance.ns('description'), 'required');
					}
				},

				updateFriendlyURL(title) {
					var instance = this;

					var urlTitleInput = instance.one('#urlTitle');

					var friendlyURLEmpty = !urlTitleInput.val();

					if (
						instance._automaticURL() &&
						(friendlyURLEmpty ||
							instance._originalFriendlyURLChanged)
					) {
						urlTitleInput.val(
							Liferay.Util.normalizeFriendlyURL(title)
						);
					}

					instance._originalFriendlyURLChanged = true;
				}
			}
		});

		Liferay.Blogs = Blogs;
	},
	'',
	{
		requires: ['aui-base', 'liferay-form']
	}
);
