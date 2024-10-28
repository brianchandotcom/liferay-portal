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
import {hasVisibleFormButtonChild} from './hasVisibleFormButtonChild';
import hasVisibleSubmitChild from './hasVisibleSubmitChild';
import {isItemHidden} from './isItemHidden';
import {isLayoutDataItemDeleted} from './isLayoutDataItemDeleted';
import {isMultistepForm} from './isMultistepForm';
import isStepper from './isStepper';
import isVisible from './isVisible';

export default function useCheckFormsValidity() {
	const globalContext = useGlobalContext();
	const stateRef = useSelectorRef((state) => state);
	const setValidations = useSetFormValidations();

	const validations = new Map();

	return async () => {
		const {fragmentEntryLinks, layoutData, selectedViewportSize} =
			stateRef.current;

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
			if (
				!hasVisibleSubmitChild(
					form.itemId,
					globalContext,
					layoutData,
					fragmentEntryLinks,
					selectedViewportSize
				)
			) {
				addError(validations, form, FORM_ERROR_TYPES.missingSubmit);
			}

			if (isMultistepForm(form)) {
				const emptySteps = getEmptySteps(
					form.itemId,
					layoutData,
					selectedViewportSize
				);

				const stepsWithoutNext = getStepsWithoutNext(
					form.itemId,
					layoutData,
					fragmentEntryLinks,
					selectedViewportSize
				);

				const stepsWithoutPrevious = getStepsWithoutPrevious(
					form.itemId,
					layoutData,
					fragmentEntryLinks,
					selectedViewportSize
				);

				if (emptySteps.length) {
					addError(
						validations,
						form,
						FORM_ERROR_TYPES.emptySteps,
						emptySteps
					);
				}

				if (stepsWithoutNext.length) {
					addError(
						validations,
						form,
						FORM_ERROR_TYPES.missingNextButton,
						stepsWithoutNext
					);
				}

				if (stepsWithoutPrevious.length) {
					addError(
						validations,
						form,
						FORM_ERROR_TYPES.missingPreviousButton,
						stepsWithoutPrevious
					);
				}
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

function addError(validations, formItem, type, steps) {
	if (formIsRestricted(formItem)) {
		return;
	}

	const formValidation = validations.get(formItem.itemId);
	const errors = formValidation ? formValidation.errors : [];
	const nextFormErrors = [...errors, {steps, type}];

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
			fragmentEntryLink.fragmentEntryType !==
				FRAGMENT_ENTRY_TYPES.input ||
			isStepper(fragmentEntryLink)
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

		const allowedFieldTypes =
			await FormService.getFragmentEntryInputFieldTypes({
				fragmentEntryKey: fragmentEntryLink.fragmentEntryKey,
				groupId: fragmentEntryLink.groupId,
			});

		const isSpecialFieldType =
			allowedFieldTypes.includes('captcha') ||
			allowedFieldTypes.includes('categorization') ||
			allowedFieldTypes.includes('formButton');

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
		.filter(
			(field) =>
				field.required && !field.key.includes('ObjectRelationship')
		);

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

function getStepContainer(formId, layoutData) {
	const form = layoutData.items[formId];

	const stepContainerId = form.children.find(
		(childId) =>
			layoutData.items[childId].type ===
			LAYOUT_DATA_ITEM_TYPES.formStepContainer
	);

	return layoutData.items[stepContainerId];
}

function getEmptySteps(formId, layoutData, viewportSize) {
	const stepContainer = getStepContainer(formId, layoutData);

	const indexes = [];

	for (const [index, stepId] of stepContainer.children.entries()) {
		const step = layoutData.items[stepId];

		if (
			!step.children.length ||
			step.children.every((childId) => {
				isItemHidden(layoutData, childId, viewportSize);
			})
		) {
			indexes.push(index + 1);
		}
	}

	return indexes;
}

function getStepsWithoutNext(
	formId,
	layoutData,
	fragmentEntryLinks,
	viewportSize
) {
	const stepContainer = getStepContainer(formId, layoutData);

	const indexes = [];

	for (const [index, stepId] of stepContainer.children.entries()) {
		const step = layoutData.items[stepId];

		if (
			index === stepContainer.children.length - 1 ||
			!step.children.length
		) {
			continue;
		}

		if (
			!hasVisibleFormButtonChild({
				fragmentEntryLinks,
				itemId: stepId,
				layoutData,
				type: 'next',
				viewportSize,
			})
		) {
			indexes.push(index + 1);
		}
	}

	return indexes;
}

function getStepsWithoutPrevious(
	formId,
	layoutData,
	fragmentEntryLinks,
	viewportSize
) {
	const stepContainer = getStepContainer(formId, layoutData);

	const indexes = [];

	for (const [index, stepId] of stepContainer.children.entries()) {
		const step = layoutData.items[stepId];

		if (index === 0 || !step.children.length) {
			continue;
		}

		if (
			!hasVisibleFormButtonChild({
				fragmentEntryLinks,
				itemId: stepId,
				layoutData,
				type: 'previous',
				viewportSize,
			})
		) {
			indexes.push(index + 1);
		}
	}

	return indexes;
}
