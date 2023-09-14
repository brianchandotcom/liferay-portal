/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FRAGMENT_ENTRY_TYPES} from '../config/constants/fragmentEntryTypes';
import {FREEMARKER_FRAGMENT_ENTRY_PROCESSOR} from '../config/constants/freemarkerFragmentEntryProcessor';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {useSetFormValidations} from '../contexts/FormValidationContext';
import {useGlobalContext} from '../contexts/GlobalContext';
import {useSelectorRef} from '../contexts/StoreContext';
import FormService from '../services/FormService';
import {formIsRestricted} from '../utils/formIsRestricted';
import {CACHE_KEYS, getCacheItem, getCacheKey} from './cache';
import {getDescendantIds} from './getDescendantIds';
import {FORM_ERROR_TYPES} from './getFormErrorDescription';
import getLayoutDataItemUniqueClassName from './getLayoutDataItemUniqueClassName';
import hasDraftSubmitChild from './hasDraftSubmitChild';
import hasRequiredInputChild from './hasRequiredInputChild';
import hasVisibleSubmitChild from './hasVisibleSubmitChild';
import {isLayoutDataItemDeleted} from './isLayoutDataItemDeleted';
import isVisible from './isVisible';

export default function useCheckFormsValidity() {
	const globalContext = useGlobalContext();
	const stateRef = useSelectorRef((state) => state);
	const setValidations = useSetFormValidations();

	const validations = new Map();

	return async () => {
		const {
			fragmentEntryLinks,
			layoutData,
			selectedViewportSize,
		} = stateRef.current;

		const forms = getFormItems(layoutData).filter((form) => {
			const formElement = document.querySelector(
				`.${getLayoutDataItemUniqueClassName(form.itemId)}`
			);

			return isVisible(formElement, globalContext);
		});

		if (!forms.length) {
			return Promise.resolve(true);
		}

		for (const form of forms) {
			if (!hasVisibleSubmitChild(form.itemId, globalContext)) {
				addError(validations, form, FORM_ERROR_TYPES.missingSubmit);
			}

			await checkUnmappedInputChild(
				form,
				fragmentEntryLinks,
				layoutData,
				validations
			);
		}

		const promises = forms.map((form) => {
			const {
				config: {classNameId, classTypeId},
				itemId,
			} = form;

			const payload = {
				classNameId,
				classTypeId,
			};

			const cacheKey = getCacheKey([
				CACHE_KEYS.formFields,
				classNameId,
				classTypeId,
			]);

			const {data: fields} = getCacheItem(cacheKey);

			const promise = fields
				? Promise.resolve({fields, itemId})
				: FormService.getFormFields(payload).then((fields) => ({
						fields,
						itemId,
				  }));

			return promise.then(({fields, itemId}) => {
				return FormService.getFormConfig({classNameId}).then(
					(config) => ({
						config,
						fields,
						itemId,
					})
				);
			});
		});

		return Promise.all(promises).then((forms) => {
			forms.forEach(({config, fields, itemId}) => {
				const formItem = layoutData.items[itemId];

				if (!config.supportStatus && hasDraftSubmitChild(itemId)) {
					addError(
						validations,
						formItem,
						FORM_ERROR_TYPES.draftNotAvailable
					);
				}

				if (
					hasRequiredInputChild({
						checkHidden: true,
						formFields: fields,
						fragmentEntryLinks,
						itemId,
						layoutData,
						selectedViewportSize,
					})
				) {
					addError(
						validations,
						formItem,
						FORM_ERROR_TYPES.hiddenFields
					);
				}

				if (
					hasUnmappedRequiredField(
						itemId,
						fragmentEntryLinks,
						fields,
						layoutData
					)
				) {
					addError(
						validations,
						formItem,
						FORM_ERROR_TYPES.missingFields
					);
				}
			});

			if (validations.size) {
				setValidations(Array.from(validations.values()));

				return false;
			}

			return true;
		});
	};
}

function addError(validations, formItem, type) {
	if (Liferay.FeatureFlags['LPS-169923'] && formIsRestricted(formItem)) {
		return;
	}

	const formValidation = validations.get(formItem.itemId);
	const errors = formValidation ? formValidation.errors : [];
	const nextFormErrors = [...errors, type];

	validations.set(formItem.itemId, {
		classNameId: formItem.config.classNameId,
		errors: nextFormErrors,
	});
}

function getFormItems(layoutData) {
	return Object.values(layoutData.items).filter(
		(item) =>
			item.type === LAYOUT_DATA_ITEM_TYPES.form &&
			item.config.classNameId !== '0' &&
			!isLayoutDataItemDeleted(layoutData, item.itemId)
	);
}

async function checkUnmappedInputChild(
	form,
	fragmentEntryLinks,
	layoutData,
	validations
) {
	const descendantIds = getDescendantIds(layoutData, form.itemId);

	for (const descendantId of descendantIds) {
		const item = layoutData.items[descendantId];

		if (item.type !== LAYOUT_DATA_ITEM_TYPES.fragment) {
			continue;
		}

		const fragmentEntryLink =
			fragmentEntryLinks[item.config.fragmentEntryLinkId];

		if (
			fragmentEntryLink.fragmentEntryType !== FRAGMENT_ENTRY_TYPES.input
		) {
			continue;
		}

		const {inputFieldId} =
			fragmentEntryLink.editableValues[
				FREEMARKER_FRAGMENT_ENTRY_PROCESSOR
			] || {};

		if (inputFieldId) {
			continue;
		}

		const allowedFieldTypes = await FormService.getFragmentEntryInputFieldTypes(
			{
				fragmentEntryKey: fragmentEntryLink.fragmentEntryKey,
				groupId: fragmentEntryLink.groupId,
			}
		);

		const isSpecialFieldType =
			allowedFieldTypes.includes('captcha') ||
			allowedFieldTypes.includes('categorization');

		if (isSpecialFieldType) {
			continue;
		}

		addError(validations, form, FORM_ERROR_TYPES.missingFragments);

		break;
	}
}

function hasUnmappedRequiredField(
	formId,
	fragmentEntryLinks,
	fields,
	layoutData
) {
	const descendantIds = getDescendantIds(layoutData, formId);

	const requiredFields = fields
		.flatMap((fieldSet) => fieldSet.fields)
		.filter((field) => field.required);

	return requiredFields.some(
		(field) =>
			!descendantIds.some((descendantId) => {
				const item = layoutData.items[descendantId];

				if (item.type !== LAYOUT_DATA_ITEM_TYPES.fragment) {
					return false;
				}

				const {inputFieldId} =
					fragmentEntryLinks[item.config.fragmentEntryLinkId]
						.editableValues[FREEMARKER_FRAGMENT_ENTRY_PROCESSOR] ||
					{};

				return inputFieldId === field.key;
			})
	);
}
