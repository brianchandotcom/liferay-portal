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

import EventListener from './utils/EventListener';

const CSS_INVISIBLE = 'invisible';
const STR_BLANK = '';
const STR_CHANGE = 'change';
const STR_CLICK = 'click';
const STR_SUFFIX = '...';

const STRINGS = {
	confirmDiscardImages: Liferay.Language.get(
		'uploads-are-in-progress-confirmation'
	),
	saveDraftError: Liferay.Language.get('could-not-save-draft-to-the-server'),
	saveDraftMessage: Liferay.Language.get('saving-draft'),
	savedAtMessage: Liferay.Language.get('entry-saved-at-x'),
	savedDraftAtMessage: Liferay.Language.get('draft-saved-at-x'),
	titleRequiredAtPublish: Liferay.Language.get(
		'this-field-is-required-to-publish-the-entry'
	),
};

function nsObj(obj, namespace) {
	return Object.entries(obj).reduce((memo, [key, value]) => ({
		...memo,
		[`${namespace}${key}`]: value,
	}));
}

export default class Blogs {
	constructor({
		constants,
		descriptionLength = 400,
		editEntryURL,
		entry,
		namespace,
		saveInterval = 30000,
		strings = STRINGS,
	}) {
		this._config = {
			constants,
			descriptionLength,
			editEntryURL,
			entry,
			namespace,
			saveInterval,
			strings,
		};

		AUI().use('liferay-form', () => {
			this._rootNode = document.getElementById(
				`${this._config.namespace}fm`
			);

			this._listeners = new EventListener();

			this._bindUI();

			const draftEntry = entry && entry.status === constants.STATUS_DRAFT;

			const userEntry =
				entry && entry.userId === themeDisplay.getUserId();

			if (!entry || (userEntry && draftEntry)) {
				this._initDraftSaveInterval();
			}

			const customDescriptionEnabled = entry && entry.customDescription;

			this._customDescription = customDescriptionEnabled
				? entry.description
				: STR_BLANK;
			this._shortenDescription = !customDescriptionEnabled;

			this.setDescription(
				window[`${this._config.namespace}contentEditor`].getText()
			);
		});
	}

	_automaticURL() {
		const automaticURLInput = document.querySelector(
			`input[name=${this._config.namespace}automaticURL]:checked`
		);

		return automaticURLInput.value === 'true';
	}

	_beforePublishBtnClick() {
		const form = Liferay.Form.get(`${this._config.namespace}fm`);

		form.addRule(
			document.getElementById(`${this._config.namespace}title`),
			'required',
			this._config.strings.titleRequiredAtPublish
		);
	}

	_beforeSaveBtnClick() {
		const form = Liferay.Form.get(`${this._config.namespace}fm`);

		form.removeRule(
			document.getElementById(`${this._config.namespace}title`),
			'required'
		);
	}

	_bindUI() {
		const listeners = this._listeners;

		this._captionNode = this._rootNode.querySelector(
			'.cover-image-caption'
		);

		Liferay.on('coverImageDeleted', this._removeCaption, this);
		Liferay.on(
			['coverImageUploaded', 'coverImageSelected'],
			this._showCaption,
			this
		);

		const publishButton = document.getElementById(
			`${this._config.namespace}publishButton`
		);

		if (publishButton) {
			listeners.add(publishButton, STR_CLICK, () => {
				this._beforePublishBtnClick();
				this._checkImagesBeforeSave(false, false);
			});
		}

		const saveButton = document.getElementById(
			`${this._config.namespace}saveButton`
		);

		if (saveButton) {
			listeners.add(saveButton, STR_CLICK, () => {
				this._beforeSaveBtnClick();
				this._checkImagesBeforeSave(true, false);
			});
		}

		const customAbstractOptions = document.querySelectorAll(
			`input[name=${this._config.namespace}customAbstract]`
		);

		if (customAbstractOptions.length) {
			customAbstractOptions.forEach((option) => {
				listeners.add(
					option,
					STR_CHANGE,
					this._configureAbstract.bind(this)
				);
			});
		}

		const urlOptions = document.querySelectorAll(
			`#${this._config.namespace}urlOptions input`
		);

		if (urlOptions.length) {
			urlOptions.forEach((option) => {
				listeners.add(
					option,
					STR_CHANGE,
					this._onChangeURLOptions.bind(this)
				);
			});
		}
	}

	_checkImagesBeforeSave(draft, ajax) {
		const instance = this;

		const tempImages = this._getTempImages();

		if (tempImages.length) {
			if (confirm(this._config.strings.confirmDiscardImages)) {
				tempImages.each((image) => {
					image.parentElement.remove();
				});

				instance._saveEntry(draft, ajax);
			}
		}
		else {
			instance._saveEntry(draft, ajax);
		}
	}

	_configureAbstract(event) {
		const target = event.target;

		let description = this._customDescription;

		this._shortenDescription = target.value === 'false';

		if (this._shortenDescription) {
			this._customDescription = document.getElementById(
				`${this._config.namespace}description`
			).value;

			description = window[
				`${this._config.namespace}contentEditor`
			].getText();
		}

		this.setDescription(description);
	}

	_getContentImages(content) {
		const contentDom = document.createElement('div');

		contentDom.innerHTML = content;

		const contentImages = contentDom.getElementsByTagName('img');

		const finalImages = [];

		for (let i = 0; i < contentImages.length; i++) {
			const currentImage = contentImages[i];

			if (
				currentImage.parentElement.tagName.toLowerCase() === 'picture'
			) {
				finalImages.push(currentImage.parentElement);
			}
			else {
				finalImages.push(currentImage);
			}
		}

		return finalImages;
	}

	_getPrincipalForm() {
		return this._rootNode;
	}

	_getTempImages() {
		return this._rootNode.querySelectorAll('img[data-random-id]');
	}

	_hasTempImages() {
		return this._getTempImages().length > 0;
	}

	_initDraftSaveInterval() {
		this._saveDraftTimer = setInterval(() => {
			if (!this._hasTempImages()) {
				this._saveEntry(true, true);
			}
		}, this._config.saveInterval);

		const entry = this._config.entry;

		this._oldContent = entry ? entry.content : STR_BLANK;
		this._oldSubtitle = entry ? entry.subtitle : STR_BLANK;
		this._oldTitle = entry ? entry.title : STR_BLANK;
	}

	_onChangeURLOptions() {
		const urlTitleInput = document.getElementById(
			`${this._config.namespace}urlTitle`
		);
		const urlTitleInputLabel = document.querySelector(
			`[for="${this._config.namespace}urlTitle"]`
		);

		if (this._automaticURL()) {
			this._lastCustomURL = urlTitleInput.value;

			const title = document.getElementById(
				`${this._config.namespace}title`
			).value;

			this.updateFriendlyURL(title);

			Liferay.Util.toggleDisabled(urlTitleInput, true);
			Liferay.Util.toggleDisabled(urlTitleInputLabel, true);
		}
		else {
			urlTitleInput.value = this._lastCustomURL || urlTitleInput.value;

			Liferay.Util.toggleDisabled(urlTitleInput, false);
			Liferay.Util.toggleDisabled(urlTitleInputLabel, false);
		}
	}

	_removeCaption() {
		const captionNode = this._captionNode;

		if (captionNode) {
			captionNode.classList.add(CSS_INVISIBLE);
		}

		window[`${this._config.namespace}coverImageCaptionEditor`].setHTML(
			STR_BLANK
		);
	}

	_saveEntry(draft, ajax) {
		const constants = this._config.constants;
		const entry = this._config.entry;
		const namespace = this._config.namespace;

		const content = window[`${namespace}contentEditor`].getHTML();

		const coverImageCaption = window[
			`${namespace}coverImageCaptionEditor`
		].getHTML();
		const subtitle = document.getElementById(`${namespace}subtitle`).value;
		const title = document.getElementById(`${namespace}title`).value;

		const automaticURL = document.querySelector(
			`input[name=${namespace}automaticURL]:checked`
		).value;

		const urlTitle = automaticURL
			? ''
			: document.getElementById(`${namespace}urlTitle`).value;

		const form = this._getPrincipalForm();

		if (draft && ajax) {
			const hasData =
				content !== STR_BLANK && (draft || title !== STR_BLANK);

			const hasChanged =
				this._oldContent !== content ||
				this._oldSubtitle !== subtitle ||
				this._oldTitle !== title;

			if (hasData && hasChanged) {
				const strings = this._config.strings;

				const saveStatus = document.getElementById(
					`${namespace}saveStatus`
				);

				const allowPingbacks = document.getElementById(
					`${namespace}allowPingbacks`
				);
				const allowTrackbacks = document.getElementById(
					`${namespace}allowTrackbacks`
				);

				const assetTagNames = document.getElementById(
					`${namespace}assetTagNames`
				);

				const data = nsObj(
					{
						allowPingbacks: allowPingbacks?.value,
						allowTrackbacks: allowTrackbacks?.value,
						assetTagNames: assetTagNames?.value || '',
						cmd: constants.ADD,
						content,
						coverImageCaption,
						coverImageFileEntryCropRegion: document.getElementById(
							`${namespace}coverImageFileEntryCropRegion`
						).value,
						coverImageFileEntryId: document.getElementById(
							`${namespace}coverImageFileEntryId`
						).value,
						displayDateAmPm: document.getElementById(
							`${namespace}displayDateAmPm`
						).value,
						displayDateDay: document.getElementById(
							`${namespace}displayDateDay`
						).value,
						displayDateHour: document.getElementById(
							`${namespace}displayDateHour`
						).value,
						displayDateMinute: document.getElementById(
							`${namespace}displayDateMinute`
						).value,
						displayDateMonth: document.getElementById(
							`${namespace}displayDateMonth`
						).value,
						displayDateYear: document.getElementById(
							`${namespace}displayDateYear`
						).value,
						entryId: document.getElementById(`${namespace}entryId`)
							.value,
						referringPortletResource: document.getElementById(
							`${namespace}referringPortletResource`
						).value,
						subtitle,
						title,
						urlTitle,
						workflowAction: constants.ACTION_SAVE_DRAFT,
					},
					namespace
				);

				const customAttributes = document.querySelectorAll(
					`[name=${namespace}ExpandoAttribute`
				);

				customAttributes.forEach((item) => {
					data[item.getAttribute('name')] = item.value;
				});

				Liferay.Util.toggleDisabled(
					document.getElementById(`${namespace}publishButton`),
					true
				);

				this._updateStatus(strings.saveDraftMessage);

				const body = new URLSearchParams(data);

				Liferay.Util.fetch(this._config.editEntryURL, {
					body,
					method: 'POST',
				})
					.then((response) => response.json())
					.then((data) => {
						this._oldContent = content;
						this._oldSubtitle = subtitle;
						this._oldTitle = title;

						const message = data;

						if (message) {
							document.getElementById(
								`${namespace}coverImageFileEntryId`
							).value = message.coverImageFileEntryId;

							document.getElementById(
								`${namespace}entryId`
							).value = message.entryId;

							if (message.content) {
								this._updateContentImages(
									message.content,
									message.attributeDataImageId
								);
							}

							if (saveStatus) {
								const saveText =
									entry && entry.pending
										? strings.savedAtMessage
										: strings.savedDraftAtMessage;

								const now = saveText.replace(
									/\{0\}/gim,
									new Date().toString()
								);

								this._updateStatus(now);
							}
						}
						else {
							saveStatus.classList.add('hide');
							saveStatus.hidden = true;
						}

						Liferay.Util.toggleDisabled(
							document.getElementById(
								`${namespace}publishButton`
							),
							false
						);
					})
					.catch(() => {
						this._updateStatus(strings.saveDraftError);
					});
			}
		}
		else {
			document.getElementById(
				`${namespace}${constants.CMD}`
			).value = entry ? constants.UPDATE : constants.ADD;

			document.getElementById(`${namespace}content`).value = content;
			document.getElementById(
				`${namespace}coverImageCaption`
			).value = coverImageCaption;
			document.getElementById(`${namespace}workflowAction`).value = draft
				? constants.ACTION_SAVE_DRAFT
				: constants.ACTION_PUBLISH;

			submitForm(form);
		}
	}

	_shorten(text) {
		const descriptionLength = this._config.descriptionLength;

		if (text.length > descriptionLength) {
			text = text.substring(0, descriptionLength);

			if (STR_SUFFIX.length < descriptionLength) {
				const spaceIndex = text.lastIndexOf(
					' ',
					descriptionLength - STR_SUFFIX.length
				);

				text = text.substring(0, spaceIndex).concat(STR_SUFFIX);
			}
		}

		return text;
	}

	_showCaption() {
		const captionNode = this._captionNode;

		if (captionNode) {
			captionNode.removeClass(CSS_INVISIBLE);
		}
	}

	_updateContentImages(finalContent, attributeDataImageId) {
		const originalContent = window[
			`${this._config.namespace}contentEditor`
		].getHTML();

		const originalContentImages = this._getContentImages(originalContent);

		const finalContentImages = this._getContentImages(finalContent);

		if (originalContentImages.length != finalContentImages.length) {
			return;
		}

		for (let i = 0; i < originalContentImages.length; i++) {
			const image = originalContentImages[i];

			const tempImageId = image.getAttribute(attributeDataImageId);

			if (tempImageId) {
				const el = document.querySelector(

					// TODO it´s working old `img[${attributeDataImageId}"=${tempImageId}"]`
					// check`img[${attributeDataImageId}="${tempImageId}"]`

					`img[${attributeDataImageId}"=${tempImageId}"]`
				);

				if (el) {
					const finalImage = finalContentImages[i];

					if (el.tagName === finalImage.tagName) {
						el.removeAttribute('data-cke-saved-src');

						for (let j = 0; j < finalImage.attributes.length; j++) {
							const attr = finalImage.attributes[j];

							el.setAttribute(attr.name, attr.value);
						}

						el.removeAttribute(attributeDataImageId);
					}
					else {

						// TODO: check AUI_Element.replace === el.replaceWith

						el.replaceWith(finalContentImages[i]);
					}
				}
			}
		}
	}

	_updateStatus(text) {
		const saveStatus = document.getElementById(
			`${this._config.namespace}saveStatus`
		);

		if (saveStatus) {
			saveStatus.innerHTML = text;
		}
	}

	dispose() {
		if (this._saveDraftTimer) {
			clearInterval(this._saveDraftTimer);
		}

		this._listeners.removeAll();
	}

	setCustomDescription(text) {
		this._customDescription = text;
	}

	setDescription(text) {
		let description = this._customDescription;

		if (this._shortenDescription) {
			description = this._shorten(text);
		}

		const descriptionNode = document.getElementById(
			`${this._config.namespace}description`
		);

		descriptionNode.value = description;

		descriptionNode.setAttribute('disabled', this._shortenDescription);

		const descriptionLabelNode = this._rootNode.querySelector(
			`[for="${this._config.namespace}description"]`
		);

		const form = Liferay.Form.get(`${this._config.namespace}fm`);

		if (!this._shortenDescription) {
			Liferay.Util.toggleDisabled(descriptionNode, false);
			Liferay.Util.toggleDisabled(descriptionLabelNode, false);

			form.addRule(`${this._config.namespace}description`, 'required');
		}
		else {
			Liferay.Util.toggleDisabled(descriptionNode, true);
			Liferay.Util.toggleDisabled(descriptionLabelNode, true);

			form.removeRule(`${this._config.namespace}description`, 'required');
		}
	}

	updateFriendlyURL(title) {
		const urlTitleInput = document.getElementById(
			`${this._config.namespace}urlTitle`
		);

		const friendlyURLEmpty = !urlTitleInput.value;

		if (
			this._automaticURL() &&
			(friendlyURLEmpty || this._originalFriendlyURLChanged)
		) {
			urlTitleInput.value = Liferay.Util.normalizeFriendlyURL(title);
		}

		this._originalFriendlyURLChanged = true;
	}
}
