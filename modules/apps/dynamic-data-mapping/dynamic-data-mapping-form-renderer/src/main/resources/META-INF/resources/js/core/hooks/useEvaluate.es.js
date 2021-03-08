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

import {useCallback} from 'react';

import {useConfig} from './useConfig.es';
import {useFormState} from './useForm.es';

/**
 * This hook is a partial function that removes the need to pass the same
 * properties every time they are called, this is only for thunks that use
 * the `evaluate` function.
 */
export const useEvaluate = (thunk) => {
	const {groupId, portletNamespace, viewMode} = useConfig();
	const {defaultLanguageId, editingLanguageId, pages, rules} = useFormState();

	return useCallback(
		(args) =>
			thunk({
				defaultLanguageId,
				editingLanguageId,
				groupId,
				pages,
				portletNamespace,
				rules,
				viewMode,
				...args,
			}),
		[
			defaultLanguageId,
			editingLanguageId,
			groupId,
			pages,
			portletNamespace,
			rules,
			thunk,
			viewMode,
		]
	);
};
