import {CREATE_SEGMENTS_EXPERIENCE, DELETE_SEGMENTS_EXPERIENCE, EDIT_SEGMENTS_EXPERIENCE, END_CREATE_SEGMENTS_EXPERIENCE, END_EDIT_SEGMENTS_EXPERIENCE, SELECT_SEGMENTS_EXPERIENCE, START_CREATE_SEGMENTS_EXPERIENCE, START_EDIT_SEGMENTS_EXPERIENCE} from '../actions/actions.es';
import {setIn} from '../utils/utils.es';

const CREATE_SEGMENTS_EXPERIENCE_URL = '/segments.segmentsexperience/add-segments-experience';

const DELETE_SEGMENTS_EXPERIENCE_URL = '/segments.segmentsexperience/delete-segments-experience';

const EDIT_SEGMENTS_EXPERIENCE_URL = '/segments.segmentsexperience/update-segments-experience';

/**
 * @param {!object} state
 * @param {!string} actionType
 * @param {!object} payload
 * @param {string} payload.segmentsEntryId
 * @param {string} payload.name
 * @return {object}
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
								name: nameCurrentValue,
								priority,
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
 * @returns\
 */
function deleteSegmentsExperienceReducer(state, actionType, payload) {
	return new Promise(
		(resolve, reject) => {
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
					(error, {exception}) => {

						reject(exception);
					}
				);

			}
			else {
				resolve(nextState);
			}
		}
	);
}

function startEditExperienceReducer(state, actionType, payload) {
	let nextState = state;
	if (actionType === START_EDIT_SEGMENTS_EXPERIENCE) {
		const {name, segmentsEntryId, segmentsExperienceId} = payload;
		nextState = setIn(
			nextState,
			['segmentsExperienceEdition'],
			{
				error: null,
				name,
				segmentsEntryId,
				segmentsExperienceId
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
 * @param {!object} state
 * @param {!string} actionType
 * @return {object}
 * @review
 */
function endEditExperienceReducer(state, actionType) {
	let nextState = state;

	if (actionType === END_EDIT_SEGMENTS_EXPERIENCE) {
		nextState = setIn(
			nextState,
			['segmentsExperienceEdition'],
			{
				error: null,
				name: null,
				segmentsEntryId: null,
				segmentsExperienceId: null
			}
		);
	}
	return nextState;
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

				const priority = Object.values(nextState.availableSegmentsExperiences || []).length;

				Liferay.Service(
					EDIT_SEGMENTS_EXPERIENCE_URL,
					{
						active: true,
						nameMap,
						priority,
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

export {
	createSegmentsExperienceReducer,
	deleteSegmentsExperienceReducer,
	startCreateSegmentsExperience,
	startEditExperienceReducer,
	endEditExperienceReducer,
	editSegmentsExperienceReducer,
	endCreateSegmentsExperience,
	selectSegmentsExperienceReducer
};