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

import {State} from '@liferay/frontend-js-state-web';
import {
	STR_NULL_IMAGE_FILE_ENTRY_ID,
	imageSelectorCoverImageAtom,
} from 'item-selector-taglib';

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

function addNamespace(object, namespace) {
	return Object.entries(object).reduce((memo, [key, value]) => ({
		...memo,
		[`${namespace}${key}`]: value,
	}));
}

export default class Blogs {
	constructor({
		constants,
		descriptionLength = 400,
		editEntryURL,
		emailEntryUpdatedEnabled,
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
			this._rootNode = this._getElementById('fm');

			this._eventsHandles = [];

			this._bindUI();

			const draftEntry = entry?.status === constants.STATUS_DRAFT;

			const userEntry = entry?.userId === themeDisplay.getUserId();

			if (!entry || (userEntry && draftEntry)) {
				this._initDraftSaveInterval();
			}

			const customDescriptionEnabled = entry?.customDescription;

			this._customDescription = customDescriptionEnabled
				? entry.description
				: STR_BLANK;
			this._shortenDescription = !customDescriptionEnabled;

			if (emailEntryUpdatedEnabled) {
				Liferay.Util.toggleBoxes(
					`${namespace}sendEmailEntryUpdated`,
					`${namespace}emailEntryUpdatedCommentWrapper`
				);
			}

			window[`${namespace}onChangeContentEditor`] = (html) => {
				this.setDescription(html);
			};
		});
	}

	_addEventListener(target, ...rest) {
		target.addEventListener(...rest);
		this._eventsHandles.push(() => target.removeEventListener(...rest));
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
			`${this._config.namespace}title`,
			'required',
			this._config.strings.titleRequiredAtPublish
		);
	}

	_beforeSaveBtnClick() {
		const form = Liferay.Form.get(`${this._config.namespace}fm`);

		form.removeRule(`${this._config.namespace}title`, 'required');
	}

	_bindUI() {
		this._captionNode = this._rootNode.querySelector(
			'.cover-image-caption'
		);

		this._imageSelectorCoverImageSubscription = State.subscribe(
			imageSelectorCoverImageAtom,
			(data) => this._updateCaption(data)
		);

		const publishButton = this._getElementById('publishButton');

		if (publishButton) {
			this._addEventListener(publishButton, STR_CLICK, () => {
				this._beforePublishBtnClick();
				this._checkImagesBeforeSave(false, false);
			});
		}

		const saveButton = this._getElementById('saveButton');

		if (saveButton) {
			this._addEventListener(saveButton, STR_CLICK, () => {
				this._beforeSaveBtnClick();
				this._checkImagesBeforeSave(true, false);
			});
		}

		const customAbstractOptions = document.querySelectorAll(
			`input[name=${this._config.namespace}customAbstract]`
		);

		if (customAbstractOptions.length) {
			customAbstractOptions.forEach((option) => {
				this._addEventListener(
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
				this._addEventListener(
					option,
					STR_CHANGE,
					this._onChangeURLOptions.bind(this)
				);
			});
		}

		const titleInput = this._getElementById('title');

		if (titleInput) {
			this._addEventListener(titleInput, STR_CHANGE, (event) => {
				this.updateFriendlyURL(event.target.value);
			});
		}

		const descriptionInput = this._getElementById('description');

		if (descriptionInput) {
			this._addEventListener(descriptionInput, STR_CHANGE, (event) => {
				this.setCustomDescription(event.target.value);
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
		let description = this._customDescription;

		this._shortenDescription = event.target.value === 'false';

		if (this._shortenDescription) {
			this._customDescription = this._getElementById('description').value;

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

	_getElementById(id) {
		return document.getElementById(`${this._config.namespace}${id}`);
	}

	_getTempImages() {
		return this._rootNode.querySelectorAll('img[data-random-id]');
	}

	_initDraftSaveInterval() {
		this._saveDraftTimer = setInterval(() => {
			if (!this._getTempImages().length) {
				this._saveEntry(true, true);
			}
		}, this._config.saveInterval);

		const entry = this._config.entry;

		this._oldContent = entry?.content || STR_BLANK;
		this._oldSubtitle = entry?.subtitle || STR_BLANK;
		this._oldTitle = entry?.title || STR_BLANK;
	}

	_onChangeURLOptions() {
		const urlTitleInput = this._getElementById('urlTitle');
		const urlTitleInputLabel = document.querySelector(
			`[for="${this._config.namespace}urlTitle"]`
		);

		if (this._automaticURL()) {
			this._lastCustomURL = urlTitleInput.value;

			const title = this._getElementById('title').value;

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

		CKEDITOR.instances[
			`${this._config.namespace}coverImageCaptionEditor`
		].setData(STR_BLANK);
	}

	_saveEntry(draft, ajax) {
		const constants = this._config.constants;
		const entry = this._config.entry;
		const namespace = this._config.namespace;

		const content = window[`${namespace}contentEditor`].getHTML();

		const coverImageCaption = CKEDITOR.instances[
			`${namespace}coverImageCaptionEditor`
		].getData();

		const subtitle = this._getElementById('subtitle').value;
		const title = this._getElementById('title').value;

		const urlTitle = this._automaticURL()
			? ''
			: this._getElementById('urlTitle').value;

		if (draft && ajax) {
			const hasData =
				content !== STR_BLANK && (draft || title !== STR_BLANK);

			const hasChanged =
				this._oldContent !== content ||
				this._oldSubtitle !== subtitle ||
				this._oldTitle !== title;

			if (hasData && hasChanged) {
				const strings = this._config.strings;

				const saveStatus = this._getElementById('saveStatus');

				const allowPingbacks = this._getElementById('allowPingbacks');
				const allowTrackbacks = this._getElementById('allowTrackbacks');

				const assetTagNames = this._getElementById('assetTagNames');

				const bodyData = addNamespace(
					{
						allowPingbacks: allowPingbacks?.value,
						allowTrackbacks: allowTrackbacks?.value,
						assetTagNames: assetTagNames?.value || '',
						cmd: constants.ADD,
						content,
						coverImageCaption,
						coverImageFileEntryCropRegion: this._getElementById(
							'coverImageFileEntryCropRegion'
						).value,
						coverImageFileEntryId: this._getElementById(
							'coverImageFileEntryId'
						).value,
						displayDateAmPm: this._getElementById('displayDateAmPm')
							.value,
						displayDateDay: this._getElementById('displayDateDay')
							.value,
						displayDateHour: this._getElementById('displayDateHour')
							.value,
						displayDateMinute: this._getElementById(
							'displayDateMinute'
						).value,
						displayDateMonth: this._getElementById(
							'displayDateMonth'
						).value,
						displayDateYear: this._getElementById('displayDateYear')
							.value,
						entryId: this._getElementById('entryId').value,
						referringPortletResource: this._getElementById(
							'referringPortletResource'
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
					bodyData[item.getAttribute('name')] = item.value;
				});

				Liferay.Util.toggleDisabled(
					this._getElementById('publishButton'),
					true
				);

				this._updateStatus(strings.saveDraftMessage);

				const body = new URLSearchParams(bodyData);

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
							saveStatus.classList.remove('hide');
							saveStatus.hidden = false;

							this._getElementById(
								'coverImageFileEntryId'
							).value = message.coverImageFileEntryId;

							this._getElementById('entryId').value =
								message.entryId;

							if (message.content) {
								this._updateContentImages(
									message.content,
									message.attributeDataImageId
								);
							}

							this._getElementById('urlTitle').value =
								message.urlTitle;

							if (saveStatus) {
								const saveText = entry?.pending
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
							this._getElementById('publishButton'),
							false
						);
					})
					.catch(() => {
						this._updateStatus(strings.saveDraftError);
					});
			}
		}
		else {
			this._getElementById(constants.CMD).value = entry
				? constants.UPDATE
				: constants.ADD;

			this._getElementById('content').value = content;
			this._getElementById('coverImageCaption').value = coverImageCaption;
			this._getElementById('workflowAction').value = draft
				? constants.ACTION_SAVE_DRAFT
				: constants.ACTION_PUBLISH;

			submitForm(this._rootNode);
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
			captionNode.classList.remove(CSS_INVISIBLE);
		}
	}

	_updateCaption(imageData) {
		if (imageData.fileEntryId !== STR_NULL_IMAGE_FILE_ENTRY_ID) {
			this._showCaption();
		}
		else {
			this._removeCaption();
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
				const element = document.querySelector(
					`img[${attributeDataImageId}="${tempImageId}"]`
				);

				if (element) {
					const finalImage = finalContentImages[i];

					if (element.tagName === finalImage.tagName) {
						element.removeAttribute('data-cke-saved-src');

						for (let j = 0; j < finalImage.attributes.length; j++) {
							const attr = finalImage.attributes[j];

							element.setAttribute(attr.name, attr.value);
						}

						element.removeAttribute(attributeDataImageId);
					}
					else {
						element.replaceWith(finalContentImages[i]);
					}
				}
			}
		}
	}

	_updateStatus(text) {
		const saveStatus = this._getElementById('saveStatus');

		if (saveStatus) {
			saveStatus.innerHTML = text;
		}
	}

	dispose() {
		if (this._saveDraftTimer) {
			clearInterval(this._saveDraftTimer);
		}

		this._eventsHandles.forEach((removeListener) => removeListener());
		this._eventsHandles = [];

		this._imageSelectorCoverImageSubscription.dispose();
		this._imageSelectorCoverImageSubscription = null;
	}

	setCustomDescription(text) {
		this._customDescription = text;
	}

	setDescription(text) {
		let description = this._customDescription;

		if (this._shortenDescription) {
			description = this._shorten(text);
		}

		const descriptionNode = this._getElementById('description');

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
		const urlTitleInput = this._getElementById('urlTitle');

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
