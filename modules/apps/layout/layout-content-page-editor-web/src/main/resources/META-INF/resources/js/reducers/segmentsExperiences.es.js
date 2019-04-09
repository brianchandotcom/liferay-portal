import {CREATE_SEGMENTS_EXPERIENCE, DELETE_SEGMENTS_EXPERIENCE, EDIT_SEGMENTS_EXPERIENCE, SELECT_SEGMENTS_EXPERIENCE, UPDATE_SEGMENTS_EXPERIENCE_PRIORITY} from '../actions/actions.es';
import {setIn} from '../utils/FragmentsEditorUpdateUtils.es';

const CREATE_SEGMENTS_EXPERIENCE_URL = '/segments.segmentsexperience/add-segments-experience';

const DELETE_SEGMENTS_EXPERIENCE_URL = '/segments.segmentsexperience/delete-segments-experience';

const EDIT_SEGMENTS_EXPERIENCE_URL = '/segments.segmentsexperience/update-segments-experience';

const UPDATE_SEGMENTS_EXPERIENCE_PRIORITY_URL = '/segments.segmentsexperience/update-segments-experience-priority';

/**
 * @param {!object} state
 * @param {!string} actionType
 * @param {!object} payload
 * @param {string} payload.segmentsEntryId
 * @param {string} payload.name
 * @return {Promise}
 * @review
 */
function createSegmentsExperienceReducer(state, actionType, payload) {
	return new Promise(
		(resolve, reject) => {
			let nextState = state;
			if (actionType === CREATE_SEGMENTS_EXPERIENCE) {
				const {name, segmentsEntryId} = payload;

				const {
					classNameId,
					classPK
				} = nextState;

				const nameMap = JSON.stringify(
					{
						[state.defaultLanguageId]: name
					}
				);

				Liferay.Service(
					CREATE_SEGMENTS_EXPERIENCE_URL,
					{
						active: true,
						classNameId,
						classPK,
						nameMap,
						segmentsEntryId: segmentsEntryId,
						serviceContext: JSON.stringify(
							{
								scopeGroupId: themeDisplay.getScopeGroupId(),
								userId: themeDisplay.getUserId()
							}
						)
					},
					(obj) => {

						const {
							active,
							nameCurrentValue,
							segmentsEntryId,
							segmentsExperienceId
						} = obj;

						nextState = setIn(
							nextState,
							[
								'availableSegmentsExperiences',
								segmentsExperienceId
							],
							{
								active,
								name: nameCurrentValue,
								segmentsEntryId,
								segmentsExperienceId
							}
						);

						nextState = setIn(
							nextState,
							['segmentsExperienceId'],
							segmentsExperienceId
						);

						resolve(nextState);
					},
					error => {
						reject(error);
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
 * @param {object} payload
 * @param {!string} payload.experienceId
 * @returns {Promise}
 */
function deleteSegmentsExperienceReducer(state, actionType, payload) {
	return new Promise(
		(resolve, reject) => {
			try {
				let nextState = state;
				if (actionType === DELETE_SEGMENTS_EXPERIENCE) {
					const {segmentsExperienceId} = payload;

					Liferay.Service(
						DELETE_SEGMENTS_EXPERIENCE_URL,
						{
							segmentsExperienceId
						},
						response => {
							const priority = response.priority;

							let availableSegmentsExperiences = Object.assign(
								{},
								nextState.availableSegmentsExperiences
							);

							delete availableSegmentsExperiences[response.segmentsExperienceId];
							const experienceIdToSelect = (segmentsExperienceId === nextState.segmentsExperienceId) ? nextState.defaultSegmentsExperienceId : nextState.segmentsExperienceId;

							Object.entries(availableSegmentsExperiences).forEach(
								([key, experience]) => {
									const segmentExperiencePriority = experience.priority;

									if (segmentExperiencePriority > priority) {
										experience.priority = segmentExperiencePriority - 1;
									}
								}
							);

							nextState = setIn(
								nextState,
								['availableSegmentsExperiences'],
								availableSegmentsExperiences
							);
							nextState = setIn(
								nextState,
								['segmentsExperienceId'],
								experienceIdToSelect
							);
							resolve(nextState);
						},
						(error, {exception}) => {

							reject(exception);
						}
					);

				}
				else {
					resolve(nextState);
				}
			}
			catch (e) {
				reject(e);
			}
		}
	);
}

/**
 *
 *
 * @export
 * @param {!object} state
 * @param {!string} actionType
 * @param {!object} payload
 * @param {!string} payload.segmentsExperienceId
 * @returns
 */
function selectSegmentsExperienceReducer(state, actionType, payload) {
		let nextState = state;

		if (actionType === SELECT_SEGMENTS_EXPERIENCE) {
			nextState = setIn(
				nextState,
				['segmentsExperienceId'],
				payload.segmentsExperienceId,
			);
		}

		return nextState;
}

/**
 * @param {!object} state
 * @param {!string} actionType
 * @param {!object} payload
 * @param {!string} payload.segmentsEntryId
 * @param {!string} payload.name
 * @param {!string} payload.segmentsExperienceId
 * @return {object}
 * @review
 */
function editSegmentsExperienceReducer(state, actionType, payload) {
	return new Promise(
		(resolve, reject) => {
			let nextState = state;
			if (actionType === EDIT_SEGMENTS_EXPERIENCE) {
				const {
					name,
					segmentsEntryId,
					segmentsExperienceId
				} = payload;

				const nameMap = JSON.stringify(
					{
						[state.defaultLanguageId]: name
					}
				);

				Liferay.Service(
					EDIT_SEGMENTS_EXPERIENCE_URL,
					{
						active: true,
						nameMap,
						segmentsEntryId,
						segmentsExperienceId
					},
					(obj) => {

						const {
							active,
							nameCurrentValue,
							priority,
							segmentsEntryId,
							segmentsExperienceId
						} = obj;

						nextState = setIn(
							nextState,
							[
								'availableSegmentsExperiences',
								segmentsExperienceId
							],
							{
								active,
								name: nameCurrentValue,
								priority,
								segmentsEntryId,
								segmentsExperienceId
							}
						);

						resolve(nextState);
					},
					error => {
						reject(error);
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
 *
 *
 * @param {*} state
 * @param {!string} actionType
 * @param {object} payload
 * @param {!('up' | 'down')} payload.direction
 * @param {!string} payload.segmentsExperienceId
 * @param {!number} payload.priority
 */
function updateSegmentsExperiencePriorityReducer(state, actionType, payload) {
	return new Promise((resolve, reject) => {
		let nextState = state;
		if (actionType === UPDATE_SEGMENTS_EXPERIENCE_PRIORITY) {
			const {
				direction,
				priority: oldPriority,
				segmentsExperienceId
			} = payload;

			const priority = parseInt(oldPriority, 10);

			const newPriority = (direction === 'up') ? priority + 1 : priority - 1;

			Liferay.Service(
				UPDATE_SEGMENTS_EXPERIENCE_PRIORITY_URL,
				{
					newPriority,
					segmentsExperienceId
				}
			).then(
				() => {
					const availableSegmentsExperiencesArray = Object.values(nextState.availableSegmentsExperiences);
					const subTargetExperience = availableSegmentsExperiencesArray.find(
						experience => {
							return experience.priority === newPriority;
						}
					);
					const targetExperience = availableSegmentsExperiencesArray.find(
						experience => {
							return experience.priority === priority;
						}
					);
					nextState = setIn(
						nextState,
						[
							'availableSegmentsExperiences',
							targetExperience.segmentsExperienceId,
							'priority'
						],
						newPriority
					);

					nextState = setIn(
						nextState,
						[
							'availableSegmentsExperiences',
							subTargetExperience.segmentsExperienceId,
							'priority'
						],
						priority
					);

					resolve(nextState);
				}
			).catch(
				(error) => {
					reject(error);
				}
			);
		}
		else {
			resolve(nextState);
		}
	});
}

export {
	createSegmentsExperienceReducer,
	deleteSegmentsExperienceReducer,
	editSegmentsExperienceReducer,
	updateSegmentsExperiencePriorityReducer,
	selectSegmentsExperienceReducer
};