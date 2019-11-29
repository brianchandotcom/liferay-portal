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

import Component from 'metal-component';
import Soy from 'metal-soy';

import {setIn} from '../../utils/FragmentsEditorUpdateUtils.es';
import templates from './MasterFragmentEntryLinkListRow.soy';

import './MasterFragmentEntryLinkContent.es';

import {Config} from 'metal-state';

import getConnectedComponent from '../../store/ConnectedComponent.es';
import {getAssetFieldValue} from '../../utils/FragmentsEditorFetchUtils.es';
import {editableIsMappedToInfoItem} from '../../utils/FragmentsEditorGetUtils.es';

class MasterFragmentEntryLinkListRow extends Component {
	prepareStateForRender(state) {
		let nextState = state;

		nextState = setIn(
			nextState,
			['_backgroundImageValue'],
			this._getBackgroundImageValue()
		);

		return nextState;
	}

	/**
	 * Updates mapped field value
	 * @private
	 * @review
	 */
	_updateMappedBackgroundFieldValue() {
		if (
			this.getAssetFieldValueURL &&
			this.row.config.backgroundImage &&
			editableIsMappedToInfoItem(this.row.config.backgroundImage)
		) {
			getAssetFieldValue(
				this.row.config.backgroundImage.classNameId,
				this.row.config.backgroundImage.classPK,
				this.row.config.backgroundImage.fieldId
			).then(response => {
				const {fieldValue} = response;

				if (
					fieldValue &&
					fieldValue.url !== this._mappedBackgroundFieldValue
				) {
					this._mappedBackgroundFieldValue = fieldValue.url;
				}
			});
		}
		else {
			this._mappedBackgroundFieldValue = null;
		}
	}

	/**
	 * Handle getAssetFieldValueURL changed
	 * @inheritDoc
	 * @review
	 */
	syncGetAssetFieldValueURL() {
		this._updateMappedBackgroundFieldValue();
	}

	/**
	 * @private
	 */
	_getBackgroundImageValue() {
		if (this._mappedBackgroundFieldValue) {
			return this._mappedBackgroundFieldValue;
		}

		const {config} = this.row;

		if (!config) {
			return '';
		}

		if (typeof config.backgroundImage === 'string') {
			return config.backgroundImage;
		}

		if (typeof config.backgroundImage === 'object') {
			return config.backgroundImage.url;
		}

		return '';
	}
}

const ConnectedMasterFragmentEntryLinkListRow = getConnectedComponent(
	MasterFragmentEntryLinkListRow,
	['getAssetFieldValueURL']
);

MasterFragmentEntryLinkListRow.STATE = {
	/**
	 * Mapped background field value
	 * @instance
	 * @memberOf MasterFragmentEntryLinkListRow
	 * @private
	 * @review
	 * @type {string}
	 */
	_mappedBackgroundFieldValue: Config.internal().string()
};

Soy.register(ConnectedMasterFragmentEntryLinkListRow, templates);

export {
	ConnectedMasterFragmentEntryLinkListRow,
	MasterFragmentEntryLinkListRow
};
export default ConnectedMasterFragmentEntryLinkListRow;
