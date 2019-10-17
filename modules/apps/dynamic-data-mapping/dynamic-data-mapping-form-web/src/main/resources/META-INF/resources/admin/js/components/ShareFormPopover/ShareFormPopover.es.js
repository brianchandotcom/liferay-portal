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

import getCN from 'classnames';
import ClipboardJS from 'clipboard';
import {selectText} from 'dynamic-data-mapping-form-builder/js/util/dom.es';
import Component, {Config} from 'metal-jsx';
import {Align} from 'metal-position';

import Popover from '../Popover/Popover.es';

class ShareFormPopover extends Component {
	attached() {
		this._clipboard = new ClipboardJS('.ddm-copy-clipboard');

		this._clipboard.on('success', this._handleClipboardSuccess.bind(this));
	}

	disposeInternal() {
		super.disposeInternal();

		this._clipboard.destroy();
	}

	render() {
		const {alignElement, spritemap, url, visible} = this.props;
		const {success} = this.state;

		const buttonClasses = getCN('btn ddm-copy-clipboard', {
			'btn-default': true,
			'btn-success': success
		});

		const formClasses = getCN('form-group m-0', {
			'has-success': success
		});

		return (
			<Popover
				alignElement={alignElement}
				events={{
					popoverClosed: this._handlePopoverClosed.bind(this)
				}}
				placement={Align.LeftCenter}
				portalElement={document.body}
				ref={'popover'}
				title={Liferay.Language.get('copy-url')}
				visible={visible}
			>
				<div class={formClasses}>
					<div class="input-group">
						<div class="input-group-item input-group-prepend">
							<input
								class="form-control"
								readOnly={true}
								ref="shareFieldURL"
								type="text"
								value={url}
							/>
							{success && (
								<div class="form-feedback-group">
									<div class="form-feedback-item">
										{Liferay.Language.get(
											'copied-to-clipboard'
										)}
									</div>
								</div>
							)}
						</div>
						<span class="input-group-append input-group-item input-group-item-shrink">
							<button
								class={buttonClasses}
								data-clipboard-text={url}
								type="button"
							>
								{success ? (
									<span class="publish-button-success-icon pl-2 pr-2">
										<svg
											aria-hidden="true"
											class={
												'lexicon-icon lexicon-icon-check'
											}
										>
											<use
												xlink:href={`${spritemap}#check`}
											/>
										</svg>
									</span>
								) : (
									<span class="publish-button-text">
										{Liferay.Language.get('copy')}
									</span>
								)}
							</button>
						</span>
					</div>
				</div>
			</Popover>
		);
	}

	rendered() {
		const {success} = this.state;

		if (success) {
			setTimeout(() => {
				const shareFieldURL = this.element.querySelector('input');

				selectText(shareFieldURL);
			}, 30);
		}
	}

	_handleClipboardSuccess() {
		this.setState({
			success: true
		});
	}

	_handlePopoverClosed() {
		this.setState({
			success: false
		});

		this.emit('popoverClosed');
	}
}

ShareFormPopover.PROPS = {
	/**
	 * The element to align with.
	 * @default ""
	 * @instance
	 * @memberof ShareFormPopover
	 * @type {object}
	 */

	alignElement: Config.object().required(),

	/**
	 * The spritemap for Clay icons.
	 * @default ""
	 * @instance
	 * @memberof ShareFormPopover
	 * @type {spritemap}
	 */

	spritemap: Config.string().required(),

	/**
	 * The url to share the form.
	 * @default ""
	 * @instance
	 * @memberof ShareFormPopover
	 * @type {string}
	 */

	url: Config.string().required()
};

ShareFormPopover.STATE = {
	success: Config.bool().value(false)
};

export default ShareFormPopover;
