/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import classNames from 'classnames';
import React, {useCallback} from 'react';

import FormMappingOptions from '../../../plugins/browser/components/page_structure/components/item_configuration_panels/FormMappingOptions';
import {
	useDispatch,
	useSelector,
	useSelectorCallback,
} from '../../contexts/StoreContext';
import selectLanguageId from '../../selectors/selectLanguageId';
import updateFormItemConfig from '../../thunks/updateFormItemConfig';
import {formIsMapped} from '../../utils/formIsMapped';
import {formIsRestricted} from '../../utils/formIsRestricted';
import {formIsUnavailable} from '../../utils/formIsUnavailable';
import {getEditableLocalizedValue} from '../../utils/getEditableLocalizedValue';
import isItemEmpty from '../../utils/isItemEmpty';
import ContainerWithControls from './ContainerWithControls';

const FormWithControls = React.forwardRef(({children, item, ...rest}, ref) => {
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
				<Form item={item}>{children}</Form>
			</ContainerWithControls>
		</form>
	);
});

function Form({children, item}) {
	const showLoadingState = item.config?.loading;

	const isEmpty = useSelectorCallback(
		(state) =>
			isItemEmpty(item, state.layoutData, state.selectedViewportSize),
		[item]
	);

	if (showLoadingState) {
		return <FormLoadingState />;
	}

	if (Liferay.FeatureFlags['LPS-169923']) {
		if (formIsUnavailable(item)) {
			return (
				<ClayAlert
					displayType="warning"
					title={`${Liferay.Language.get('warning')}:`}
				>
					{Liferay.Language.get(
						'this-content-is-currently-unavailable-or-has-been-deleted.-users-cannot-see-this-fragment'
					)}
				</ClayAlert>
			);
		}
		else if (formIsRestricted(item)) {
			return (
				<ClayAlert displayType="secondary">
					{Liferay.Language.get(
						'this-content-cannot-be-displayed-due-to-permission-restrictions'
					)}
				</ClayAlert>
			);
		}
	}

	const isMapped = formIsMapped(item);

	if (isEmpty || !isMapped) {
		return <FormEmptyState isMapped={isMapped} item={item} />;
	}

	const {showMessagePreview} = item.config;

	return (
		<>
			{showMessagePreview && <FormSuccessMessage item={item} />}

			<div
				className={classNames('page-editor__form-children', {
					'd-none': showMessagePreview,
				})}
			>
				{children}
			</div>
		</>
	);
}

function FormEmptyState({isMapped, item}) {
	const dispatch = useDispatch();

	const onValueSelect = useCallback(
		(nextConfig) =>
			dispatch(
				updateFormItemConfig({
					itemConfig: nextConfig,
					itemId: item.itemId,
				})
			),
		[dispatch, item.itemId]
	);

	if (item.config.showMessagePreview) {
		return <FormSuccessMessage item={item} />;
	}

	if (isMapped) {
		return (
			<div className="page-editor__no-fragments-state">
				<p className="m-0 page-editor__no-fragments-state__message">
					{Liferay.Language.get('place-fragments-here')}
				</p>
			</div>
		);
	}

	return (
		<div className="align-items-center bg-lighter d-flex flex-column page-editor__form-unmapped-state page-editor__no-fragments-state">
			<p className="page-editor__no-fragments-state__title">
				{Liferay.Language.get('map-your-form')}
			</p>

			<p className="mb-3 page-editor__no-fragments-state__message">
				{Liferay.Language.get(
					'select-a-content-type-to-start-creating-the-form'
				)}
			</p>

			<div
				className="cadmin"
				onClick={(event) => event.stopPropagation()}
			>
				<FormMappingOptions
					hideLabel={true}
					item={item}
					onValueSelect={onValueSelect}
				/>
			</div>
		</div>
	);
}

function FormLoadingState() {
	return (
		<div className="bg-lighter page-editor__no-fragments-state">
			<ClayLoadingIndicator />

			<p className="m-0 page-editor__no-fragments-state__message">
				{Liferay.Language.get(
					'your-form-is-being-loaded.-this-may-take-some-time'
				)}
			</p>
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

export default FormWithControls;
