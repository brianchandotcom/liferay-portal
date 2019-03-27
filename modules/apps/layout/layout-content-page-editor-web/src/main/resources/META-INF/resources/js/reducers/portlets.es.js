import {ADD_PORTLET} from '../actions/actions.es';
import {addFragment, getFragmentEntryLinkContent} from './fragments.es';
import {getWidgetPath} from '../utils/FragmentsEditorGetUtils.es';
import {setIn, updateLayoutData} from '../utils/FragmentsEditorUpdateUtils.es';
import editableValuesMigrator from '../utils/fragmentMigrator.es';
import {prefixSegmentsExperienceId} from '../utils/prefixSegmentsExperienceId.es';

/**
 * @param {!object} state
 * @param {!string} actionType
 * @param {!object} payload
 * @param {!boolean} payload.instanceable
 * @param {!string} payload.portletId
 * @return {object}
 * @review
 */
function addPortletReducer(state, actionType, payload) {
	return new Promise(
		resolve => {
			let nextState = state;

			if (actionType === ADD_PORTLET) {
				let fragmentEntryLink = null;
				let nextData = null;

				_addPortlet(
					nextState.addPortletURL,
					payload.portletId,
					nextState.classNameId,
					nextState.classPK,
					nextState.portletNamespace,
					nextState.defaultSegmentsExperienceId
				)
					.then(
						response => {
							fragmentEntryLink = response;

							nextData = addFragment(
								fragmentEntryLink.fragmentEntryLinkId,
								nextState.dropTargetBorder,
								nextState.dropTargetItemId,
								nextState.dropTargetItemType,
								nextState.layoutData
							);

							return updateLayoutData(
								{
									classNameId: nextState.classNameId,
									classPK: nextState.classPK,
									data: nextData,
									portletNamespace: nextState.portletNamespace,
									segmentsExperienceId: nextState.segmentsExperienceId,
									updateLayoutPageTemplateDataURL: nextState.updateLayoutPageTemplateDataURL
								}
							);
						}
					)
					.then(
						() => getFragmentEntryLinkContent(
							nextState.renderFragmentEntryURL,
							fragmentEntryLink,
							nextState.portletNamespace
						)
					)
					.then(
						response => {
							fragmentEntryLink = response;

							fragmentEntryLink.portletId = payload.portletId;

							nextState = setIn(
								nextState,
								[
									'fragmentEntryLinks',
									fragmentEntryLink.fragmentEntryLinkId
								],
								fragmentEntryLink
							);

							if (!payload.instanceable) {
								const widgetPath = getWidgetPath(nextState.widgets, payload.portletId);

								nextState = setIn(
									nextState,
									[
										...widgetPath,
										'used'
									],
									true
								);
							}

							nextState = setIn(
								nextState,
								['layoutData'],
								nextData
							);

							resolve(nextState);
						}
					)
					.catch(
						() => {
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

	return fetch(
		addPortletURL,
		{
			body: formData,
			credentials: 'include',
			method: 'POST'
		}
	)
		.then(
			response => response.json()
		)
		.then(
			response => {
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
			}
		);
}

export {addPortletReducer};