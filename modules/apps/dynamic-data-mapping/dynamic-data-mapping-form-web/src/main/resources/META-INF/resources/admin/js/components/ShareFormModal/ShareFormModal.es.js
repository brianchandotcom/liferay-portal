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

import ClayModal from 'clay-modal';
import {
	convertToFormData,
	makeFetch,
} from 'dynamic-data-mapping-form-renderer/js/util/fetch.es';
import dom from 'metal-dom';
import {EventHandler} from 'metal-events';
import Component, {Config} from 'metal-jsx';

import Email from './Email.es';
import Link from './Link.es';

class ShareFormModal extends Component {
	attached() {
		this._eventHandler.add(
			dom.on(
				'.nav-item > .lfr-ddm-share-url-button',
				'click',
				this._handleShareButtonClicked.bind(this)
			)
		);
	}

	close() {
		this.refs.shareFormModalRef.emit('hide');
	}

	created() {
		this._eventHandler = new EventHandler();
	}

	disposeInternal() {
		const modalBackdrop = document.querySelector('.modal-backdrop');
		dom.exitDocument(modalBackdrop);

		super.disposeInternal();
		this._eventHandler.removeAllListeners();
	}

	open() {
		this.refs.shareFormModalRef.refs.emailRef.init();
		this.refs.shareFormModalRef.refs.linkRef.init();
		this.refs.shareFormModalRef.visible = true;
	}

	render() {
		const {autocompleteUserURL, spritemap, url} = this.props;

		return (
			<div class="share-form-modal">
				<ClayModal
					body={
						<div class="share-form-modal-items">
							<div class="share-form-modal-item">
								<div class="popover-header">
									{Liferay.Language.get('link')}
								</div>
								<div class="popover-body">
									{
										<Link
											ref="linkRef"
											spritemap={spritemap}
											url={url}
										/>
									}
								</div>
							</div>
							<div class="share-form-modal-item">
								<div class="popover-header">
									{Liferay.Language.get('email')}
								</div>
								<div class="popover-body">
									{
										<Email
											autocompleteUserURL={
												autocompleteUserURL
											}
											localizedName={
												this.props.localizedName
											}
											ref="emailRef"
											spritemap={spritemap}
											url={url}
										/>
									}
								</div>
							</div>
						</div>
					}
					events={{
						clickButton: this._handleClickFooterButton.bind(this),
						hide: this._handleShareFormModalClosed.bind(this),
					}}
					footerButtons={[
						{
							alignment: 'right',
							label: Liferay.Language.get('cancel'),
							style: 'secondary',
							type: 'close',
						},
						{
							alignment: 'right',
							label: Liferay.Language.get('done'),
							style: 'primary',
							type: 'button',
						},
					]}
					ref={'shareFormModalRef'}
					size={'lg'}
					spritemap={spritemap}
					title={Liferay.Language.get('share')}
				/>
			</div>
		);
	}

	showNotification(message, error) {
		const parentOpenToast = Liferay.Util.getOpener().Liferay.Util.openToast;

		const openToastParams = {message};

		if (error) {
			openToastParams.title = Liferay.Language.get('error');
			openToastParams.type = 'danger';
		}
		else {
			openToastParams.title = Liferay.Language.get('success');
		}

		parentOpenToast(openToastParams);
	}

	submitEmailContent() {
		const {portletNamespace, shareFormInstanceURL} = this.props;
		const {emailContent} = this.refs.shareFormModalRef.refs.emailRef.state;
		const {addresses} = emailContent;

		if (!addresses || !addresses.length) {
			return;
		}

		const data = {
			[`${portletNamespace}addresses`]: addresses
				.map(({label}) => label)
				.join(','),
			[`${portletNamespace}message`]: emailContent.message,
			[`${portletNamespace}subject`]: emailContent.subject,
		};

		makeFetch({
			body: convertToFormData(data),
			method: 'POST',
			url: shareFormInstanceURL,
		})
			.then((response) => {
				return response.successMessage
					? this.showNotification(response.successMessage)
					: this.showNotification(response.errorMessage, true);
			})
			.catch((error) => {
				throw new Error(error);
			});
	}

	_handleClickFooterButton(event) {
		if (event.target.classList.contains('btn-primary')) {
			this.submitEmailContent();
			this.close();
		}
	}

	_handleShareButtonClicked() {
		this.open();
	}

	_handleShareFormModalClosed(event) {
		this.emit('shareFormModalClosed', event);
	}
}

ShareFormModal.PROPS = {

	/**
	 * @default undefined
	 * @instance
	 * @memberof ShareFormModal
	 * @type {!string}
	 */
	autocompleteUserURL: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof ShareFormModal
	 * @type {!object}
	 */
	localizedName: Config.object(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof ShareFormModal
	 * @type {!string}
	 */
	portletNamespace: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof ShareFormModal
	 * @type {!string}
	 */
	shareFormInstanceURL: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof ShareFormModal
	 * @type {!string}
	 */
	spritemap: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof ShareFormModal
	 * @type {!string}
	 */
	url: Config.string(),
};

export default ShareFormModal;
