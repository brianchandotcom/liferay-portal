/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {config} from '../config/index';
import serviceFetch from './serviceFetch';

export default {

	/**
	 * Get available list item renderers for the list style
	 * @param {object} options
	 * @param {string} options.itemSubtype itemSubtype
	 * @param {string} options.itemType itemType
	 * @param {string} options.listStyle listStyle
	 */
	getAvailableListItemRenderers({itemSubtype, itemType, listStyle}) {
		return serviceFetch(config.getAvailableListItemRenderersURL, {
			body: {
				itemSubtype,
				itemType,
				listStyle,
			},
		});
	},

	/**
	 * Get available list renderers for the class name
	 * @param {object} options
	 * @param {string} options.className className
	 */
	getAvailableListRenderers({className}) {
		return serviceFetch(config.getAvailableListRenderersURL, {
			body: {
				className,
			},
		});
	},

	/**
	 * Get available structure mapping fields
	 * @param {object} options
	 * @param {string} options.classNameId Asset's className
	 * @param {string} options.classTypeId Asset's classTypeId
	 */
	getAvailableStructureMappingFields({classNameId, classTypeId}) {
		return serviceFetch(config.mappingFieldsURL, {
			body: {
				classNameId,
				classTypeId,
			},
		});
	},

	/**
	 * Get available templates for an asset
	 * @param {object} options
	 * @param {string} options.className Asset's className
	 * @param {string} options.classPK Asset's classPK
	 */
	getAvailableTemplates({className, classPK, externalReferenceCode}) {
		const body = {
			className,
		};

		if (classPK) {
			body.classPK = classPK;
		}

		if (externalReferenceCode) {
			body.externalReferenceCode = externalReferenceCode;
		}

		return serviceFetch(config.getAvailableTemplatesURL, {
			body,
		});
	},

	/**
	 * Get the error message of an action
	 * @param {object} options
	 * @param {string} options.classNameId Asset's className
	 * @param {string} options.fieldId
	 */
	getInfoItemActionErrorMessage({classNameId, fieldId}) {
		return serviceFetch(config.getInfoItemActionErrorMessageURL, {
			body: {
				classNameId,
				fieldId,
			},
		});
	},

	/**
	 * Get an item's value
	 * @param {object} options
	 * @param {string} options.classNameId Asset's className
	 * @param {string} options.classPK Asset's classPK
	 * @param {string} options.externalReferenceCode Asset's externalReferenceCode
	 * @param {string} options.fieldId
	 * @param {string} [options.languageId]
	 */
	getInfoItemFieldValue({
		classNameId,
		classPK,
		editableTypeOptions,
		externalReferenceCode,
		fieldId,
		languageId,
	}) {
		const body = {
			classNameId,
			editableTypeOptions: JSON.stringify(editableTypeOptions),
			fieldId,
			languageId,
		};

		if (classPK) {
			body.classPK = classPK;
		}

		if (externalReferenceCode) {
			body.externalReferenceCode = externalReferenceCode;
		}

		return serviceFetch(config.getInfoItemFieldValueURL, {
			body,
		});
	},

	/**
	 * Get page contents
	 * @param {object} options
	 */
	getPageContents({segmentsExperienceId}) {
		return serviceFetch(config.getPageContentsURL, {
			body: {
				segmentsExperienceId,
			},
		});
	},
};
