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

module.exports = {
	rules: {
		'@liferay/group-imports': 'off',
		'@liferay/portal/no-loader-import-specifier': 'off',
		'@liferay/portal/no-react-dom-render': 'off',
		'no-case-declarations': 'off',
		'no-empty': ['error', {allowEmptyCatch: true}],
		'no-prototype-builtins': 'off',
		'promise/catch-or-return': 'off',
		'quote-props': 'off',
	},
};
