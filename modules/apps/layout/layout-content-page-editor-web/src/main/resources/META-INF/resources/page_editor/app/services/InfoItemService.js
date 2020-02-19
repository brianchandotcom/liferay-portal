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

import {config} from '../config/index';
import serviceFetch from './serviceFetch';

export default {
	/**
	 * Get an asset's value
	 * @param {object} options
	 * @param {string} options.classNameId Asset's className
	 * @param {string} options.classPK Asset's classPK
	 * @param {string} options.fieldId
	 * @param {string} [options.languageId]
	 * @param {function} options.onNetworkStatus
	 */
	getAssetFieldValue({
		classNameId,
		classPK,
		fieldId,
		languageId,
		onNetworkStatus
	}) {
		return serviceFetch(
			config.getAssetFieldValueURL,
			{
				body: {
					classNameId,
					classPK,
					fieldId,
					languageId
				}
			},
			onNetworkStatus
		);
	},

	/**
	 * Get available asset mapping fields
	 * @param {object} options
	 * @param {string} options.classNameId Asset's className
	 * @param {string} options.classPK Asset's classPK
	 * @param {function} options.onNetworkStatus
	 */
	getAvailableAssetMappingFields({classNameId, classPK, onNetworkStatus}) {
		return serviceFetch(
			config.getAssetMappingFieldsURL,
			{
				body: {
					classNameId,
					classPK
				}
			},
			onNetworkStatus
		);
	},

	/**
	 * Get available structure mapping fields
	 * @param {object} options
	 * @param {string} options.classNameId Asset's className
	 * @param {string} options.classTypeId Asset's classTypeId
	 * @param {function} options.onNetworkStatus
	 */
	getAvailableStructureMappingFields({
		classNameId,
		classTypeId,
		onNetworkStatus
	}) {
		return serviceFetch(
			config.mappingFieldsURL,
			{
				body: {
					classNameId,
					classTypeId
				}
			},
			onNetworkStatus
		);
	},

	/**
	 * Get available templates for an asset
	 * @param {object} options
	 * @param {string} options.className Asset's className
	 * @param {string} options.classPK Asset's classPK
	 * @param {function} options.onNetworkStatus
	 */
	getAvailableTemplates({className, classPK, onNetworkStatus}) {
		return serviceFetch(
			config.getAvailableTemplatesURL,
			{
				body: {
					className,
					classPK
				}
			},
			onNetworkStatus
		);
	},

	/**
	 * Get page contents
	 * @param {object} options
	 * @param {function} options.onNetworkStatus
	 */
	getPageContents({onNetworkStatus}) {
		return serviceFetch(config.getPageContentsURL, {}, onNetworkStatus);
	}
};
