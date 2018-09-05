import {DRAG_POSITIONS} from './placeholders.es';
import {
	ADD_FRAGMENT_ENTRY_LINK,
	REMOVE_FRAGMENT_ENTRY_LINK
} from '../actions/actions.es';

/**
 * @param {!object} state
 * @param {!string} actionType
 * @param {!object} payload
 * @param {!string} payload.fragmentEntryLinkId
 * @param {!string} payload.fragmentEntryLinkName
 * @return {object}
 * @review
 */

function addFragmentEntryLinkReducer(state, actionType, payload) {
	return new Promise(
		(resolve, reject) => {
			let nextState = Object.assign({}, state);

			if (actionType === ADD_FRAGMENT_ENTRY_LINK) {
				_addFragmentEntryLink(
					state.addFragmentEntryLinkURL,
					payload.fragmentEntryId,
					payload.fragmentName,
					state.classNameId,
					state.classPK,
					state.portletNamespace
				)
					.then(
						fragmentEntryLink => {
							return _getFragmentEntryLinkContent(
								state.renderFragmentEntryURL,
								fragmentEntryLink,
								state.portletNamespace
							);
						}
					)
					.then(
						fragmentEntryLink => {
							nextState.fragmentEntryLinks = [
								...nextState.fragmentEntryLinks
							];

							const position = _getDropFragmentPosition(
								state.fragmentEntryLinks,
								state.hoveredFragmentEntryLinkId,
								state.hoveredFragmentEntryLinkBorder
							);

							nextState.fragmentEntryLinks.splice(
								position,
								0,
								fragmentEntryLink
							);

							resolve(nextState);
						}
					);
			}
			else {
				resolve(nextState);
			}
		}
	);
}

/**
 * @param {!object} state
 * @param {!string} actionType
 * @param {!object} payload
 * @param {!string} payload.fragmentEntryLinkId
 * @return {object}
 * @review
 */

function removeFragmentEntryLinkReducer(state, actionType, payload) {
	return new Promise(
		resolve => {
			if (actionType === REMOVE_FRAGMENT_ENTRY_LINK) {
				const fragmentEntryLinkId = payload.fragmentEntryLinkId;
				const nextState = Object.assign({}, state);

				_removeFragmentEntryLink(
					state.deleteFragmentEntryLinkURL,
					state.portletNamespace,
					fragmentEntryLinkId
				).then(
					() => {
						const index = state.fragmentEntryLinks.findIndex(
							fragmentEntryLink => (
								fragmentEntryLink.fragmentEntryLinkId === fragmentEntryLinkId
							)
						);

						nextState.fragmentEntryLinks = [
							...nextState.fragmentEntryLinks.slice(0, index),
							...nextState.fragmentEntryLinks.slice(index + 1)
						];

						resolve(nextState);
					}
				);
			}
			else {
				resolve(state);
			}
		}
	);
}

function _addFragmentEntryLink(
	addFragmentEntryLinkURL,
	fragmentEntryId,
	fragmentName,
	classNameId,
	classPK,
	portletNamespace
) {
	const formData = new FormData();

	formData.append(`${portletNamespace}fragmentId`, fragmentEntryId);
	formData.append(`${portletNamespace}classNameId`, classNameId);
	formData.append(`${portletNamespace}classPK`, classPK);

	return fetch(
		addFragmentEntryLinkURL,
		{
			body: formData,
			credentials: 'include',
			method: 'POST'
		}
	).then(
		response => {
			return response.json();
		}
	).then(
		response => {
			if (!response.fragmentEntryLinkId) {
				throw new Error();
			}

			return {
				config: {},
				content: '',
				editableValues: JSON.parse(response.editableValues),
				fragmentEntryId: fragmentEntryId,
				fragmentEntryLinkId: response.fragmentEntryLinkId,
				name: fragmentName
			};
		}
	);
}

function _getDropFragmentPosition(
	fragmentEntryLinks,
	targetFragmentEntryLinkId,
	targetBorder
) {
	let position = fragmentEntryLinks.length;

	const targetPosition = fragmentEntryLinks.findIndex(
		fragmentEntryLink => (
			fragmentEntryLink.fragmentEntryLinkId === targetFragmentEntryLinkId
		)
	);

	if (targetPosition > -1 && targetBorder) {
		if (targetBorder === DRAG_POSITIONS.top) {
			position = targetPosition;
		}
		else {
			position = targetPosition + 1;
		}
	}

	return position;
}

function _getFragmentEntryLinkContent(
	renderFragmentEntryURL,
	fragmentEntryLink,
	portletNamespace
) {
	const formData = new FormData();

	formData.append(
		`${portletNamespace}fragmentEntryLinkId`,
		fragmentEntryLink.fragmentEntryLinkId
	);

	return fetch(
		renderFragmentEntryURL,
		{
			body: formData,
			credentials: 'include',
			method: 'POST'
		}
	).then(
		response => response.json()
	).then(
		response => {
			if (!response.content) {
				throw new Error();
			}

			return Object.assign(
				{},
				fragmentEntryLink,
				{content: response.content}
			);
		}
	);
}

function _removeFragmentEntryLink(
	deleteFragmentEntryLinkURL,
	portletNamespace,
	fragmentEntryLinkId
) {
	const formData = new FormData();

	formData.append(
		`${portletNamespace}fragmentEntryLinkId`,
		fragmentEntryLinkId
	);

	return fetch(
		deleteFragmentEntryLinkURL,
		{
			body: formData,
			credentials: 'include',
			method: 'POST'
		}
	);
}

export {
	addFragmentEntryLinkReducer,
	removeFragmentEntryLinkReducer
};