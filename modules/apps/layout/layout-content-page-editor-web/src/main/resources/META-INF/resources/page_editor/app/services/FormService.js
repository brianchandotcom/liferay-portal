/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {config} from '../config/index';
import serviceFetch from './serviceFetch';

export default {

	/**
	 * Get form config
	 * @param {object} options
	 * @param {string} options.classNameId Form classNameId
	 * @param {function} options.onNetworkStatus
	 */
	getFormConfig({classNameId, onNetworkStatus = () => {}}) {
		return serviceFetch(
			config.getFormConfigURL,
			{
				body: {
					classNameId,
				},
			},
			onNetworkStatus
		);
	},

	/**
	 * Get available form mapping fields
	 * @param {object} options
	 * @param {string} options.classNameId Form classNameId
	 * @param {string} options.classTypeId Form classTypeId
	 * @param {function} options.onNetworkStatus
	 */
	getFormFields({classNameId, classTypeId, onNetworkStatus = () => {}}) {
		return serviceFetch(
			config.getFormFieldsURL,
			{
				body: {
					classNameId,
					classTypeId,
				},
			},
			onNetworkStatus
		);
	},

	/**
	 * Get allowed field types for a given fragment entry
	 * @param {object} options
	 * @param {string} options.fragmentEntryKey
	 * @param {function} options.onNetworkStatus
	 */
	getFragmentEntryInputFieldTypes({
		fragmentEntryKey,
		groupId,
		onNetworkStatus = () => {},
	}) {
		return serviceFetch(
			config.getFragmentEntryInputFieldTypesURL,
			{
				body: {
					fragmentEntryKey,
					groupId: groupId || null,
				},
			},
			onNetworkStatus
		);
	},

	/**
	 * Updates a config into an item
	 * @param {object} options
	 * @param {object} options.itemConfig Updated item config
	 * @param {string} options.itemId id of the item to be updated
	 * @param {string} options.segmentsExperienceId Segments experience id
	 * @param {function} options.onNetworkStatus
	 * @return {Promise<void>}
	 */
	updateFormItemConfig({
		itemConfig,
		itemId,
		onNetworkStatus,
		segmentsExperienceId,
	}) {
		return serviceFetch(
			config.updateFormItemConfigURL,
			{
				body: {
					itemConfig: JSON.stringify(itemConfig),
					itemId,
					segmentsExperienceId,
				},
			},
			onNetworkStatus,
			{requestGenerateDraft: true}
		);
	},
};
