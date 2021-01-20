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

import {openSimpleInputModal} from 'frontend-js-web';

export default function SelectLayoutPageTemplateEntryMasterLayoutVerticalCardPropsTransformer({
	additionalProps: {
		addLayoutPageTemplateEntryUrl,
		dialogTitle,
		mainFieldLabel,
		mainFieldName,
		mainFieldPlaceholder,
	},
	portletNamespace: namespace,
	...otherProps
}) {
	return {
		...otherProps,
		onClick: (event) => {
			event.preventDefault();

			openSimpleInputModal({
				dialogTitle,
				formSubmitURL: addLayoutPageTemplateEntryUrl,
				mainFieldLabel,
				mainFieldName,
				mainFieldPlaceholder,
				namespace,
				spritemap:
					'<%= themeDisplay.getPathThemeImages() %>/clay/icons.svg',
			});
		},
		onKeyDown: (event) => {
			if (
				event.nativeEvent.code === 'Enter' ||
				event.nativeEvent.code === 'Space'
			) {
				event.preventDefault();
				event.target.click();
			}
		},
		role: 'button',
		tabIndex: '0',
	};
}
