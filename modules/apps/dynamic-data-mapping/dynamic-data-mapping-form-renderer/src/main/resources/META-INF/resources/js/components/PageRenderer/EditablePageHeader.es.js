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

import {usePrevious} from 'frontend-js-react-web';
import React, {useEffect, useState} from 'react';

import {EVENT_TYPES} from '../../actions/eventTypes.es';
import {useForm} from '../../hooks/useForm.es';
import {usePage} from '../../hooks/usePage.es';
import {PagesVisitor} from '../../util/visitors.es';

export const PageHeader = ({
	description: initialDescription,
	placeholder,
	title: initialTitle,
}) => {
	const [description, setDescription] = useState(initialDescription);
	const [title, setTitle] = useState(initialTitle);

	const dispatch = useForm();
	const {editingLanguageId, pageIndex, pages} = usePage();

	const prevEditingLanguageId = usePrevious(editingLanguageId);

	useEffect(() => {
		if (prevEditingLanguageId !== editingLanguageId) {
			setDescription(initialDescription);
			setTitle(initialTitle);
		}
	}, [
		editingLanguageId,
		initialDescription,
		initialTitle,
		prevEditingLanguageId,
	]);

	const handlePageDescriptionChanged = (value) => {
		const visitor = new PagesVisitor(pages);

		dispatch({
			payload: visitor.mapPages((page, index) => {
				if (index === pageIndex) {
					page = {
						...page,
						description: value,
						localizedDescription: {
							...page.localizedDescription,
							[editingLanguageId]: value,
						},
					};
				}

				return page;
			}),
			type: EVENT_TYPES.PAGE_UPDATED,
		});
	};

	const handlePageTitleChanged = (value) => {
		const visitor = new PagesVisitor(pages);

		dispatch({
			payload: visitor.mapPages((page, index) => {
				if (index === pageIndex) {
					page = {
						...page,
						localizedTitle: {
							...page.localizedTitle,
							[editingLanguageId]: value,
						},
						title: value,
					};
				}

				return page;
			}),
			type: EVENT_TYPES.PAGE_UPDATED,
		});
	};

	return (
		<div>
			<input
				className="form-builder-page-header-title form-control p-0"
				maxLength="120"
				onChange={(event) => {
					const {value} = event.target;

					setTitle(value);
					handlePageTitleChanged(value);
				}}
				placeholder={placeholder}
				value={title}
			/>
			<input
				className="form-builder-page-header-description form-control p-0"
				maxLength="120"
				onChange={(event) => {
					const {value} = event.target;

					setDescription(value);
					handlePageDescriptionChanged(value);
				}}
				placeholder={Liferay.Language.get(
					'add-a-short-description-for-this-page'
				)}
				value={description}
			/>
		</div>
	);
};
