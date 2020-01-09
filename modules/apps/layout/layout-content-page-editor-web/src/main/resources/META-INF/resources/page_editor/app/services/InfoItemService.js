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

import serviceFetch from './serviceFetch';

export default {
	/**
	 * Get available asset mapping fields
	 * @param {object} options
	 * @param {object} options.config Application config
	 * @param {string} options.classNameId Asset's className
	 * @param {string} options.classPK Asset's classPK
	 */
	getAvailableAssetMappingFields({classNameId, classPK, config}) {
		const {getAssetMappingFieldsURL} = config;

		return serviceFetch(config, getAssetMappingFieldsURL, {
			classNameId,
			classPK
		});
	},

	/**
	 * Get available mapping fields
	 * @param {object} options
	 * @param {object} options.config Application config
	 * @param {string} options.classNameId Asset's className
	 * @param {string} options.classTypeId Asset's classTypeId
	 */
	getAvailableMappingFields({classNameId, classTypeId, config}) {
		const {mappingFieldsURL} = config;

		return serviceFetch(config, mappingFieldsURL, {
			classNameId,
			classTypeId
		});
	},

	/**
	 * Get available templates for an asset
	 * @param {object} options
	 * @param {object} options.config Application config
	 * @param {string} options.className Asset's className
	 * @param {string} options.classPK Asset's classPK
	 */
	getAvailableTemplates({className, classPK, config}) {
		const {getAvailableTemplatesURL} = config;

		return serviceFetch(config, getAvailableTemplatesURL, {
			className,
			classPK
		});
	}
};
