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

/**
 * @type {object}
 */
let _store;

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
 * @return {Promise<Response>}
 * @review
 */
function _fetch(url, body = {}) {
	return fetch(url, {
		body: _getFormData(body, _store.getState().portletNamespace),
		method: 'POST'
	});
}

/**
 * @param {string} fragmentEntryLinkId
 * @param {string} body
 */
function addFragmentEntryLinkComment(fragmentEntryLinkId, body) {
	const state = _store.getState();

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
	const state = _store.getState();

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
	return _fetch(_store.getState().addStructuredContentURL, {
		ddmFormValues,
		ddmStructureId,
		title
	});
}

/**
 * @param {string} fragmentEntryLinkId
 * @return {Promise<Response>}
 */
function duplicateFragmentEntryLink(fragmentEntryLinkId) {
	const state = _store.getState();

	return _fetch(state.duplicateFragmentEntryLinkURL, {
		fragmentEntryLinkId
	});
}

/**
 * @param {object} fragmentEntryLinks
 * @return {Promise<Response>}
 */
function editFragmentEntryLinks(fragmentEntryLinks) {
	const state = _store.getState();

	return _fetch(state.editFragmentEntryLinksURL, {
		fragmentEntryLinks: JSON.stringify(fragmentEntryLinks)
	});
}

/**
 * @param {string} commentId
 */
function deleteFragmentEntryLinkComment(commentId) {
	const state = _store.getState();

	return _fetch(state.deleteFragmentEntryLinkCommentURL, {
		commentId
	});
}

/**
 * @param {string} commentId
 * @param {string} body
 */
function editFragmentEntryLinkComment(commentId, body, resolved) {
	const state = _store.getState();

	return _fetch(state.editFragmentEntryLinkCommentURL, {
		body,
		commentId,
		resolved
	}).then(response => response.json());
}

/**
 * @param {string} classNameId
 * @param {string} classPK
 * @param {string} fieldId
 */
function getAssetFieldValue(classNameId, classPK, fieldId) {
	return _fetch(_store.getState().getAssetFieldValueURL, {
		classNameId,
		classPK,
		fieldId
	}).then(response => response.json());
}

function getAssetMappingFields(classNameId, classPK) {
	return _fetch(_store.getState().getAssetMappingFieldsURL, {
		classNameId,
		classPK
	}).then(response => response.json());
}

/**
 * @param {string} ddmStructureId
 */
function getContentStructureMappingFields(ddmStructureId) {
	return _fetch(_store.getState().getContentStructureMappingFieldsURL, {
		ddmStructureId
	}).then(response => response.json());
}

function getExperienceUsedPortletIds(segmentsExperienceId) {
	return _fetch(_store.getState().getExperienceUsedPortletsURL, {
		segmentsExperienceId
	}).then(response => response.json());
}

function getMappedContents() {
	const state = _store.getState();
	const {classNameId, classPK, getMappedContentsURL} = state;

	const url = new URL(window.location.href);

	url.searchParams.delete('activeItemType');
	url.searchParams.delete('activeItemId');
	url.searchParams.set('sidebarPanelId', 'mapped-contents');

	const backURL = `${url.pathname}${url.search}`;

	return _fetch(getMappedContentsURL, {
		backURL,
		classNameId,
		classPK
	}).then(response => response.json());
}

function getStructureMappingFields(classNameId, classTypeId) {
	return _fetch(_store.getState().mappingFieldsURL, {
		classNameId,
		classTypeId
	}).then(response => response.json());
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
	const state = _store.getState();

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
	const state = _store.getState();
	const {classNameId, classPK, addSegmentsExperienceURL} = state;

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
	const state = _store.getState();

	return Promise.all(
		fragmentEntryLinkIds.map(fragmentEntryLinkId =>
			_fetch(state.deleteFragmentEntryLinkURL, {
				fragmentEntryLinkId
			})
		)
	);
}

/**
 * Sets the store
 * @param {object} store
 */
function setStore(store) {
	_store = store;
}

/**
 * @param {string} fragmentEntryLinkId
 * @param {object} editableValues
 * @return {Promise<Response>}
 */
function updateEditableValues(fragmentEntryLinkId, editableValues) {
	const state = _store.getState();

	return _fetch(state.editFragmentEntryLinkURL, {
		editableValues: JSON.stringify(editableValues),
		fragmentEntryLinkId
	});
}

/**
 * @param {Object} layoutData
 * @param {string} segmentsExperienceId
 * @return {Promise<Response>}
 */
function updatePageEditorLayoutData(layoutData, segmentsExperienceId) {
	const state = _store.getState();

	return _fetch(state.updateLayoutPageTemplateDataURL, {
		classNameId: state.classNameId,
		classPK: state.classPK,
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
	editFragmentEntryLinks,
	editFragmentEntryLinkComment,
	getAssetFieldValue,
	getAssetMappingFields,
	getContentStructureMappingFields,
	getExperienceUsedPortletIds,
	getMappedContents,
	getStructureMappingFields,
	removeExperience,
	removeFragmentEntryLinks,
	setStore,
	updateEditableValues,
	updatePageEditorLayoutData
};
