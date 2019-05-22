import {ADD_PORTLET} from '../actions/actions.es';
import {addFragment, getFragmentEntryLinkContent} from './fragments.es';
import {getWidgetPath} from '../utils/FragmentsEditorGetUtils.es';
import {prefixSegmentsExperienceId} from '../utils/prefixSegmentsExperienceId.es';
import {setIn} from '../utils/FragmentsEditorUpdateUtils.es';
import {updatePageEditorLayoutData} from '../utils/FragmentsEditorFetchUtils.es';
import editableValuesMigrator from '../utils/fragmentMigrator.es';

/**
 * @param {object} state
 * @param {object} action
 * @param {boolean} action.instanceable
 * @param {string} action.portletId
 * @param {string} action.type
 * @return {object}
 * @review
 */
function addPortletReducer(state, action) {
	return new Promise(resolve => {
		let nextState = state;

		if (action.type === ADD_PORTLET) {
			let fragmentEntryLink = null;
			let nextData = null;

			_addPortlet(
				nextState.addPortletURL,
				action.portletId,
				nextState.classNameId,
				nextState.classPK,
				nextState.portletNamespace,
				nextState.defaultSegmentsExperienceId
			)
				.then(response => {
					fragmentEntryLink = response;

					nextData = addFragment(
						fragmentEntryLink.fragmentEntryLinkId,
						nextState.dropTargetBorder,
						nextState.dropTargetItemId,
						nextState.dropTargetItemType,
						nextState.layoutData
					);

					return updatePageEditorLayoutData(
						nextData,
						nextState.segmentsExperienceId
					);
				})
				.then(() =>
					getFragmentEntryLinkContent(
						nextState.renderFragmentEntryURL,
						fragmentEntryLink,
						nextState.portletNamespace
					)
				)
				.then(response => {
					fragmentEntryLink = response;

					fragmentEntryLink.portletId = action.portletId;

					nextState = setIn(
						nextState,
						[
							'fragmentEntryLinks',
							fragmentEntryLink.fragmentEntryLinkId
						],
						fragmentEntryLink
					);

					if (!action.instanceable) {
						const widgetPath = getWidgetPath(
							nextState.widgets,
							action.portletId
						);

						nextState = setIn(
							nextState,
							[...widgetPath, 'used'],
							true
						);
					}

					nextState = setIn(nextState, ['layoutData'], nextData);

					resolve(nextState);
				})
				.catch(() => {
					resolve(nextState);
				});
		} else {
			resolve(nextState);
		}
	});
}

/**
 * @param {string} addPortletURL
 * @param {string} portletId
 * @param {string} classNameId
 * @param {string} classPK
 * @param {string} portletNamespace
 * @param {string} defaultSegmentsExperienceId
 * @return {object}
 * @review
 */
function _addPortlet(
	addPortletURL,
	portletId,
	classNameId,
	classPK,
	portletNamespace,
	defaultSegmentsExperienceId
) {
	const formData = new FormData();

	formData.append(`${portletNamespace}portletId`, portletId);
	formData.append(`${portletNamespace}classNameId`, classNameId);
	formData.append(`${portletNamespace}classPK`, classPK);

	return fetch(addPortletURL, {
		body: formData,
		credentials: 'include',
		method: 'POST'
	})
		.then(response => response.json())
		.then(response => {
			if (!response.fragmentEntryLinkId) {
				throw new Error();
			}

			return {
				config: {},
				content: response.content,
				editableValues: editableValuesMigrator(
					response.editableValues,
					prefixSegmentsExperienceId(defaultSegmentsExperienceId)
				),
				fragmentEntryLinkId: response.fragmentEntryLinkId,
				name: response.name
			};
		});
}

export {addPortletReducer};
