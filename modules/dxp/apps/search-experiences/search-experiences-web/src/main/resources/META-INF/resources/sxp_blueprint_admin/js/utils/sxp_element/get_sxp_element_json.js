/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import formatLocaleWithUnderscores from '../language/format_locale_with_underscores';
import renameKeys from '../language/rename_keys';
import transformLocale from '../language/transform_locale';
import replaceTemplateVariable from './replace_template_variable';

/**
 * Function that provides the element JSON, with title, description, and elementDefinition.
 * The elementDefinition's configuration is updated to have its variables replaced with
 * values from uiConfigurationValues.
 *
 * @param {object} sxpElement SXP Element with title, description, elementDefinition
 * @param {object=} uiConfigurationValues Values that will replace the keys in uiConfiguration
 * @return {object}
 */
export default function getSXPElementJSON(sxpElement, uiConfigurationValues) {
	const {description_i18n, elementDefinition = {}, title_i18n} = sxpElement;

	const {category, configuration, icon} = elementDefinition;

	const descriptionObject = renameKeys(description_i18n, transformLocale);
	const titleObject = renameKeys(title_i18n, transformLocale);

	return {
		description_i18n: renameKeys(
			descriptionObject,
			formatLocaleWithUnderscores
		),
		elementDefinition: {
			category,
			configuration: uiConfigurationValues
				? replaceTemplateVariable({
						sxpElement,
						uiConfigurationValues,
				  })
				: configuration,
			icon,
		},
		title_i18n: renameKeys(titleObject, formatLocaleWithUnderscores),
	};
}
