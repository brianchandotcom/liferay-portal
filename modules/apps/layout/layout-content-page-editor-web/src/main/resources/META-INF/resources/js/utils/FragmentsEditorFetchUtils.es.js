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

import {fetch} from 'frontend-js-web';

import {getState} from '../store/store.es';

/**
 * @param {object} body
 * @param {string} portletNamespace
 * @param {FormData} [_formData=new FormData()]
 * @return {FormData}
 * @review
 */
function _getFormData(body, portletNamespace, _formData = new FormData()) {
	Object.entries(body).forEach(([key, value]) => {
		_formData.append(`${portletNamespace}${key}`, value);
	});

	return _formData;
}

/**
 * @param {string} url
 * @param {object} [body={}]
 * @private
 * @return {Promise<object>}
 * @review
 */
function _fetch(url, body = {}) {
	return fetch(url, {
		body: _getFormData(body, getState().portletNamespace),
		method: 'POST'
	})
		.then(
			response =>
				new Promise((resolve, reject) => {
					response
						.clone()
						.json()
						.then(body => resolve([response, body]))
						.catch(() => response.clone().text())
						.then(body => resolve([response, body]))
						.catch(reject);
				})
		)
		.then(([response, body]) => {
			if (typeof body === 'object') {
				if ('exception' in body) {
					throw new Error(body.exception);
				}
				else if ('error' in body) {
					throw new Error(body.error);
				}
			}

			if (response.status >= 400) {
				throw new Error(`${response.status} ${body}`);
			}

			return body;
		});
}

/**
 * @param {string} fragmentEntryLinkId
 * @param {string} body
 */
function addFragmentEntryLinkComment(fragmentEntryLinkId, body) {
	const state = getState();

	return _fetch(state.addFragmentEntryLinkCommentURL, {
		body,
		fragmentEntryLinkId
	});
}

/**
 * @param {string} fragmentEntryLinkId
 * @param {string} parentCommentId
 * @param {string} body
 */
function addFragmentEntryLinkCommentReply(
	fragmentEntryLinkId,
	parentCommentId,
	body
) {
	const state = getState();

	return _fetch(state.addFragmentEntryLinkCommentURL, {
		body,
		fragmentEntryLinkId,
		parentCommentId
	});
}

/**
 * @param {string} ddmFormValues
 * @param {string} ddmStructureId
 */
function addStructuredContent(ddmFormValues, ddmStructureId, title) {
	return _fetch(getState().addStructuredContentURL, {
		ddmFormValues,
		ddmStructureId,
		title
	}).then(response => {
		if (response.errorMessage) {
			throw new Error(response.errorMessage);
		}
		else if (
			!response.classNameId ||
			!response.classPK ||
			!response.title
		) {
			throw new Error(
				Liferay.Language.get('an-unexpected-error-occurred')
			);
		}

		return response;
	});
}

/**
 * @param {string} fragmentEntryLinkId
 * @return {Promise<Response>}
 */
function duplicateFragmentEntryLink(fragmentEntryLinkId) {
	const state = getState();

	return _fetch(state.duplicateFragmentEntryLinkURL, {
		fragmentEntryLinkId
	});
}

/**
 * @param {string} commentId
 */
function deleteFragmentEntryLinkComment(commentId) {
	const state = getState();

	return _fetch(state.deleteFragmentEntryLinkCommentURL, {
		commentId
	});
}

/**
 * @param {string} commentId
 * @param {string} body
 */
function editFragmentEntryLinkComment(commentId, body, resolved) {
	const state = getState();

	return _fetch(state.editFragmentEntryLinkCommentURL, {
		body,
		commentId,
		resolved
	});
}

/**
 * @param {string} classNameId
 * @param {string} classPK
 * @param {string} fieldId
 */
function getAssetFieldValue(classNameId, classPK, fieldId) {
	return _fetch(getState().getAssetFieldValueURL, {
		classNameId,
		classPK,
		fieldId
	});
}

/**
 * @param {string} classNameId
 * @param {string} classPK
 */
function getAssetMappingFields(classNameId, classPK) {
	return _fetch(getState().getAssetMappingFieldsURL, {
		classNameId,
		classPK
	});
}

/**
 * @param {string} className
 * @param {string} classPK
 */
function getAvailableTemplates(className, classPK) {
	return _fetch(getState().getAvailableTemplatesURL, {
		className,
		classPK
	});
}

/**
 * @param {string} ddmStructureId
 */
function getContentStructureMappingFields(ddmStructureId) {
	return _fetch(getState().getContentStructureMappingFieldsURL, {
		ddmStructureId
	});
}

function getExperienceUsedPortletIds(segmentsExperienceId) {
	return _fetch(getState().getExperienceUsedPortletsURL, {
		segmentsExperienceId
	});
}

function getPageContents() {
	const state = getState();
	const {getPageContentsURL} = state;

	const url = new URL(window.location.href);

	url.searchParams.delete('activeItemType');
	url.searchParams.delete('activeItemId');
	url.searchParams.set('sidebarPanelId', 'contents');

	const backURL = `${url.pathname}${url.search}`;

	return _fetch(getPageContentsURL, {backURL});
}

/**
 * @param {string} classNameId
 * @param {string} classTypeId
 */
function getStructureMappingFields(classNameId, classTypeId) {
	return _fetch(getState().mappingFieldsURL, {
		classNameId,
		classTypeId
	});
}

/**
 * @param {string} segmentsExperienceId
 * @param {Array<string>} [fragmentEntryLinkIds=[]]
 * @return {Promise<Response>}
 */
function removeExperience(
	segmentsExperienceId,
	fragmentEntryLinkIds = [],
	deleteSegmentsExperience = true
) {
	const state = getState();

	const body = {
		deleteSegmentsExperience,
		segmentsExperienceId
	};

	if (fragmentEntryLinkIds && fragmentEntryLinkIds.length) {
		body.fragmentEntryLinkIds = JSON.stringify(fragmentEntryLinkIds);
	}

	return _fetch(state.deleteSegmentsExperienceURL, body);
}

/**
 * @param {string} segmentsExperienceId
 * @param {Array<string>} [fragmentEntryLinkIds=[]]
 * @return {Promise<Response>}
 */
function addSegmentsExperience({name, segmentsEntryId}) {
	const state = getState();
	const {addSegmentsExperienceURL, classNameId, classPK} = state;

	const body = {
		active: true,
		classNameId,
		classPK,
		name,
		segmentsEntryId
	};

	return _fetch(addSegmentsExperienceURL, body);
}

/**
 * @param {string[]} fragmentEntryLinkIds
 * @return {Promise<Response[]>}
 */
function removeFragmentEntryLinks(fragmentEntryLinkIds) {
	const state = getState();

	return Promise.all(
		fragmentEntryLinkIds.map(fragmentEntryLinkId =>
			_fetch(state.deleteFragmentEntryLinkURL, {
				fragmentEntryLinkId
			})
		)
	);
}

/**
 * @param {string} fragmentEntryLinkId
 * @param {object} editableValues
 * @param {boolean} updateClassedModel Update classed model(Layout) associated
 * with the fragment entry link
 * @return {Promise<Response>}
 */
function updateEditableValues(
	fragmentEntryLinkId,
	editableValues,
	updateClassedModel = true
) {
	const state = getState();

	return _fetch(state.editFragmentEntryLinkURL, {
		editableValues: JSON.stringify(editableValues),
		fragmentEntryLinkId,
		updateClassedModel
	});
}

/**
 * @param {Object} layoutData
 * @param {string} segmentsExperienceId
 * @return {Promise<Response>}
 */
function updatePageEditorLayoutData(layoutData, segmentsExperienceId) {
	const state = getState();

	return _fetch(state.updateLayoutPageTemplateDataURL, {
		data: JSON.stringify(layoutData),
		segmentsExperienceId
	});
}

export {
	addFragmentEntryLinkComment,
	addFragmentEntryLinkCommentReply,
	addSegmentsExperience,
	addStructuredContent,
	deleteFragmentEntryLinkComment,
	duplicateFragmentEntryLink,
	editFragmentEntryLinkComment,
	getAssetFieldValue,
	getAssetMappingFields,
	getAvailableTemplates,
	getContentStructureMappingFields,
	getExperienceUsedPortletIds,
	getPageContents,
	getStructureMappingFields,
	removeExperience,
	removeFragmentEntryLinks,
	updateEditableValues,
	updatePageEditorLayoutData
};
