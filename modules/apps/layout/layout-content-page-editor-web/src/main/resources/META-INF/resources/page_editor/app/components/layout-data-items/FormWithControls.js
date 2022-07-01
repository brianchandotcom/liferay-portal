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

import classNames from 'classnames';
import React from 'react';

import {useSelector, useSelectorCallback} from '../../contexts/StoreContext';
import selectLanguageId from '../../selectors/selectLanguageId';
import FormService from '../../services/FormService';
import {CACHE_KEYS} from '../../utils/cache';
import {formIsMapped} from '../../utils/formIsMapped';
import {getEditableLocalizedValue} from '../../utils/getEditableLocalizedValue';
import isItemEmpty from '../../utils/isItemEmpty';
import useCache from '../../utils/useCache';
import ContainerWithControls from './ContainerWithControls';

const FormWithControls = React.forwardRef(({children, item, ...rest}, ref) => {

	// Ensure form types are loaded if this component is rendered

	useCache({
		fetcher: () => FormService.getAvailableEditPageInfoItemFormProviders(),
		key: [CACHE_KEYS.formTypes],
	});

	const isMapped = formIsMapped(item);

	const isEmpty = useSelectorCallback(
		(state) =>
			isItemEmpty(item, state.layoutData, state.selectedViewportSize),
		[item]
	);

	const showMessagePreview = item.config?.showMessagePreview;

	return (
		<form
			className={classNames('page-editor__form', {
				'page-editor__form--success': showMessagePreview,
			})}
			onSubmit={(event) => event.preventDefault()}
			ref={ref}
		>
			<ContainerWithControls {...rest} item={item}>
				{showMessagePreview ? (
					<FormSuccessMessage item={item} />
				) : isEmpty || !isMapped ? (
					<FormEmptyState isMapped={isMapped} />
				) : (
					children
				)}
			</ContainerWithControls>
		</form>
	);
});

export default FormWithControls;

function FormEmptyState({isMapped}) {
	return (
		<div
			className={classNames('page-editor__no-fragments-message', {
				'bg-lighter': !isMapped,
			})}
		>
			<div className="page-editor__no-fragments-message__title">
				{isMapped
					? Liferay.Language.get('place-fragments-here')
					: Liferay.Language.get(
							'select-a-content-type-to-start-creating-the-form'
					  )}
			</div>
		</div>
	);
}

function FormSuccessMessage({item}) {
	const languageId = useSelector(selectLanguageId);

	return (
		<div className="align-items-center d-flex justify-content-center p-5 page-editor__form__success-message">
			<span className="font-weight-semi-bold text-secondary">
				{getEditableLocalizedValue(
					item.config?.successMessage?.message,
					languageId,
					Liferay.Language.get(
						'thank-you.-your-information-was-successfully-received'
					)
				)}
			</span>
		</div>
	);
}
