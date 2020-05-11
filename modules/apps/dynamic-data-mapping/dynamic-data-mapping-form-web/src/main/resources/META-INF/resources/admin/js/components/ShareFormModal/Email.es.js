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

import ClayMultiSelect from 'clay-multi-select';
import {makeFetch} from 'dynamic-data-mapping-form-renderer/js/util/fetch.es';
import Component from 'metal-jsx';
import {Config} from 'metal-state';

class Email extends Component {
	created() {
		this._fetchEmailAddresses();
	}

	render() {
		const {emailAddresses} = this.state;

		return (
			<div class="share-form-modal-item-email">
				{emailAddresses && (
					<ClayMultiSelect
						autocompleteFilterCondition="label"
						dataSource={emailAddresses}
						helpText={Liferay.Language.get(
							'you-can-use-a-comma-to-enter-multiple-emails'
						)}
						label={Liferay.Language.get('to')}
						placeholder={Liferay.Language.get(
							'enter-email-addresses'
						)}
						showSelectButton={false}
						spritemap={this.props.spritemap}
					/>
				)}
				<div class="form-group">
					<label for="subject">
						{Liferay.Language.get('subject')}
					</label>
					<div class="input-group">
						<div class="input-group-item">
							<input
								class="form-control"
								id="subject"
								type="text"
							/>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label for="message">
						{Liferay.Language.get('message')}
					</label>
					<div class="input-group">
						<div class="input-group-item">
							<textarea
								class="form-control"
								id="message"
								type="text"
							/>
						</div>
					</div>
				</div>
			</div>
		);
	}

	_fetchEmailAddresses() {
		const {emailAddressesURL} = this.props;

		makeFetch({
			method: 'GET',
			url: emailAddressesURL,
		})
			.then((responseData) => {
				if (!this.isDisposed()) {
					this.setState({
						emailAddresses: responseData.map((data) => {
							return {
								label: data.emailAddress,
								value: data.emailAddress,
							};
						}),
					});
				}
			})
			.catch((error) => {
				throw new Error(error);
			});
	}
}

Email.PROPS = {

	/**
	 * @default undefined
	 * @instance
	 * @memberof Email
	 * @type {!string}
	 */
	emailAddressesURL: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Email
	 * @type {!object}
	 */
	localizedName: Config.object(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Email
	 * @type {!spritemap}
	 */
	spritemap: Config.string().required(),
};

Email.STATE = {

	/**
	 * @default undefined
	 * @instance
	 * @memberof Email
	 * @type {!array}
	 */
	emailAddresses: Config.array(),
};

export default Email;
