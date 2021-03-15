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

/**
 * The validator only removes properties with the value undefined to allow
 * the initialProps to be added.
 */
const validator = (props) => {
	const newProps = {};

	for (const key in props) {
		const value = props[key];

		if (value !== undefined) {
			newProps[key] = value;
		}
	}

	return newProps;
};

export const parseProps = ({
	allowInvalidAvailableLocalesForProperty,
	allowNestedFields,
	autocompleteUserURL,
	cancelLabel,
	config = {},
	containerId,
	contentType,
	context: {sidebarPanels, ...otherContext} = {},
	dataDefinitionId,
	dataLayoutBuilderElementId,
	dataLayoutBuilderId,
	dataLayoutId,
	dataProviderInstanceParameterSettingsURL,
	dataProviderInstancesURL,
	defaultSiteLanguageId,
	fieldSetDefinitionURL,
	fieldTypes,
	formInstanceId,
	functionsMetadata,
	functionsURL,
	groupId,
	portletNamespace,
	publishFormInstanceURL,
	published,
	redirectURL,
	restrictedFormURL,
	rolesURL,
	rules,
	shareFormInstanceURL,
	sharedFormURL,
	showCancelButton,
	showPublishAlert,
	showSubmitButton,
	spritemap,
	submitLabel,
	view,
	...otherProps
}) => ({
	config: validator({
		allowInvalidAvailableLocalesForProperty,
		allowNestedFields,
		autocompleteUserURL,
		cancelLabel,
		...config,
		containerId,
		contentType,
		dataDefinitionId,
		dataLayoutBuilderElementId,
		dataLayoutBuilderId,
		dataLayoutId,
		dataProviderInstanceParameterSettingsURL,
		dataProviderInstancesURL,
		defaultSiteLanguageId,
		fieldSetDefinitionURL,
		fieldTypes,
		formInstanceId,
		functionsMetadata,
		functionsURL,
		groupId,
		portletNamespace,
		publishFormInstanceURL,
		published,
		redirectURL,
		restrictedFormURL,
		rolesURL,
		shareFormInstanceURL,
		sharedFormURL,
		showCancelButton,
		showPublishAlert,
		showSubmitButton,
		sidebarPanels,
		spritemap,
		submitLabel,
		view,
	}),
	state: validator({
		...otherProps,
		...otherContext,
		rules,
	}),
});
