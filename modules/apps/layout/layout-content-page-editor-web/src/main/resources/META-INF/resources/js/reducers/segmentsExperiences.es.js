import {CREATE_SEGMENTS_EXPERIENCE, DELETE_SEGMENTS_EXPERIENCE, END_CREATE_SEGMENTS_EXPERIENCE, SELECT_SEGMENTS_EXPERIENCE, START_CREATE_SEGMENTS_EXPERIENCE} from '../actions/actions.es';
import {setIn} from '../utils/utils.es';

const CREATE_SEGMENTS_EXPERIENCE_URL = '/segments.segmentsexperience/add-segments-experience';

const DELETE_SEGMENTS_EXPERIENCE_URL = '/segments.segmentsexperience/delete-segments-experience';

/**
 * @param {!object} state
 * @param {!string} actionType
 * @param {!object} payload
 * @param {string} payload.segmentsEntryId
 * @param {string} payload.segmentsExperienceLabel
 * @return {object}
 * @review
 */
function createSegmentsExperienceReducer(state, actionType, payload) {
	return new Promise(
		resolve => {
			let nextState = state;
			if (actionType === CREATE_SEGMENTS_EXPERIENCE) {
				const {segmentsEntryId, segmentsExperienceLabel} = payload;

				const {
					classNameId,
					classPK
				} = nextState;

				const nameMap = JSON.stringify(
					{
						[state.defaultLanguageId]: segmentsExperienceLabel
					}
				);
				const priority = Object.values(nextState.availableSegmentsExperiences || []).length;

				Liferay.Service(
					CREATE_SEGMENTS_EXPERIENCE_URL,
					{
						active: true,
						classNameId,
						classPK,
						nameMap,
						priority,
						segmentsEntryId: segmentsEntryId
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
								priority,
								segmentsEntryId,
								segmentsExperienceId,
								segmentsExperienceLabel: nameCurrentValue
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
 * @export
 * @param {!object} state
 * @param {!string} actionType
 * @param {object} payload
 * @param {!string} payload.experienceId
 * @returns\
 */
function deleteSegmentsExperienceReducer(state, actionType, payload) {
	return new Promise(
		resolve => {
			let nextState = state;
			if (actionType === DELETE_SEGMENTS_EXPERIENCE) {
				const {segmentsExperienceId} = payload;

				Liferay.Service(
					DELETE_SEGMENTS_EXPERIENCE_URL,
					{
						segmentsExperienceId
					},
					response => {
						const availableSegmentsExperiences = Object.assign({}, nextState.availableSegmentsExperiences);
						delete availableSegmentsExperiences[response.segmentsExperienceId];
						const experienceIdToSelect = (segmentsExperienceId === nextState.segmentsExperienceId) ? nextState.defaultSegmentsExperienceId : nextState.segmentsExperienceId;
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
					error => {
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
 * @return {object}
 * @review
 */
function startCreateSegmentsExperience(state, actionType) {
	let nextState = state;

	if (actionType === START_CREATE_SEGMENTS_EXPERIENCE) {
		nextState = setIn(
			nextState,
			['experienceSegmentsCreation'],
			{
				creatingSegmentsExperience: true,
				error: null
			}
		);
	}
	return nextState;
}

/**
 * @param {!object} state
 * @param {!string} actionType
 * @return {object}
 * @review
 */
function endCreateSegmentsExperience(state, actionType) {
	let nextState = state;

	if (actionType === END_CREATE_SEGMENTS_EXPERIENCE) {
		nextState = setIn(
			nextState,
			['experienceSegmentsCreation'],
			{
				creatingSegmentsExperience: false,
				error: null
			}
		);
	}
	return nextState;
}

/**
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

export {
	createSegmentsExperienceReducer,
	deleteSegmentsExperienceReducer,
	startCreateSegmentsExperience,
	endCreateSegmentsExperience,
	selectSegmentsExperienceReducer
};