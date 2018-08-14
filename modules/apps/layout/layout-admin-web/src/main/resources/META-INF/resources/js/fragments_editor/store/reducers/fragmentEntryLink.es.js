import {ADD_FRAGMENT_ENTRY_LINK} from '../actionTypes.es';
import {DRAG_POSITIONS} from './dragDrop.es';

/**
 * @param {!MetalStore} state
 * @param {!string} actionType
 * @param {!object} payload
 * @param {!string} payload.fragmentEntryLinkId
 * @param {!string} payload.fragmentEntryLinkName
 * @return {MetalStore}

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

export {addFragmentEntryLinkReducer};