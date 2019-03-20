/* globals describe, test, jest, expect, beforeAll, afterAll */

import {createSegmentsExperienceReducer, deleteSegmentsExperienceReducer, editSegmentsExperienceReducer} from '../../../src/main/resources/META-INF/resources/js/reducers/segmentsExperiences.es';
import {CREATE_SEGMENTS_EXPERIENCE, DELETE_SEGMENTS_EXPERIENCE, EDIT_SEGMENTS_EXPERIENCE} from '../../../src/main/resources/META-INF/resources/js/actions/actions.es';

const SEGMENTS_EXPERIENCE_ID = 'SEGMENTS_EXPERIENCE_ID';

const SEGMENTS_EXPERIENCE_ID_DEFAULT = 'SEGMENTS_EXPERIENCE_ID_DEFAULT';

const SEGMENTS_EXPERIENCE_ID_SECOND = 'SEGMENTS_EXPERIENCE_ID_SECOND';

const SEGMENTS_EXPERIENCES_LIST = [SEGMENTS_EXPERIENCE_ID, SEGMENTS_EXPERIENCE_ID_SECOND];

describe(
	'segments experiences reducers',
	() => {
		test(
			'createSegmentsExperienceReducer communicates with API and updates the state',
			() => {
				let prevLiferayGlobal = {...global.Liferay};
				global.Liferay = {
					Service(
						URL,
						{
							active,
							classNameId,
							classPK,
							nameMap,
							priority,
							segmentsEntryId
						},
						callbackFunc,
						errorFunc
					) {
						return callbackFunc(
							{
								active,
								nameCurrentValue: JSON.parse(nameMap).en_US,
								priority,
								segmentsEntryId,
								segmentsExperienceId: (experiencesCount++, SEGMENTS_EXPERIENCES_LIST[experiencesCount])
							}
						);
					}
				};

				const availableSegmentsExperiences = {};
				const classNameId = 'test-class-name-id';
				const classPK = 'test-class-p-k';
				const spy = jest.spyOn(global.Liferay, 'Service');

				let experiencesCount = -1;

				const prevState = {
					availableSegmentsExperiences,
					classNameId,
					classPK,
					defaultLanguageId: 'en_US'
				};

				const payload = {
					name: 'test experience name',
					segmentsEntryId: 'test-segment-id'
				};

				const nextState = {
					...prevState,
					availableSegmentsExperiences: {
						[SEGMENTS_EXPERIENCE_ID]: {
							active: true,
							name: payload.name,
							priority: 0,
							segmentsEntryId: payload.segmentsEntryId,
							segmentsExperienceId: SEGMENTS_EXPERIENCE_ID
						}
					},
					segmentsExperienceId: SEGMENTS_EXPERIENCE_ID
				};

				const liferayServiceParams = {
					active: true,
					classNameId: prevState.classNameId,
					classPK: prevState.classPK,
					nameMap: JSON.stringify({en_US: payload.name}),
					priority: 0,
					segmentsEntryId: payload.segmentsEntryId
				};

				expect.assertions(4);

				createSegmentsExperienceReducer(prevState, CREATE_SEGMENTS_EXPERIENCE, payload)
					.then(
						response => {
							expect(response).toEqual(nextState);
						}
					);

				expect(spy).toHaveBeenCalledWith(
					expect.stringContaining(''),
					liferayServiceParams,
					expect.objectContaining({}),
					expect.objectContaining({})
				);

				const secondPayload = {
					name: 'second test experience name',
					segmentsEntryId: 'test-segment-id'
				};

				const secondNextState = {
					...nextState,
					availableSegmentsExperiences: {
						...nextState.availableSegmentsExperiences,
						[SEGMENTS_EXPERIENCE_ID_SECOND]: {
							active: true,
							name: secondPayload.name,
							priority: 1,
							segmentsEntryId: secondPayload.segmentsEntryId,
							segmentsExperienceId: SEGMENTS_EXPERIENCE_ID_SECOND
						}
					},
					segmentsExperienceId: SEGMENTS_EXPERIENCE_ID_SECOND
				};

				const secondLiferayServiceParams = {
					active: true,
					classNameId: prevState.classNameId,
					classPK: prevState.classPK,
					nameMap: JSON.stringify({en_US: secondPayload.name}),
					priority: 1,
					segmentsEntryId: secondPayload.segmentsEntryId
				};

				createSegmentsExperienceReducer(
					nextState,
					CREATE_SEGMENTS_EXPERIENCE,
					secondPayload
				).then(
					response => {
						expect(response).toEqual(secondNextState);
					}
				);

				expect(spy).toHaveBeenLastCalledWith(
					expect.stringContaining(''),
					secondLiferayServiceParams,
					expect.objectContaining({}),
					expect.objectContaining({})
				);

				global.Liferay = prevLiferayGlobal;
			}
		);

		test(
			'deleteExperience communicates with API and updates the state',
			() => {
				let prevLiferayGlobal = {...global.Liferay};

				const availableSegmentsExperiences = {
					[SEGMENTS_EXPERIENCE_ID]: {
						name: 'A test experience',
						segmentsEntryId: 'notRelevantSegmentId',
						segmentsExperienceId: SEGMENTS_EXPERIENCE_ID
					},
					[SEGMENTS_EXPERIENCE_ID_DEFAULT]: {
						name: 'A default test experience',
						segmentsEntryId: 'notRelevantSegmentId',
						segmentsExperienceId: SEGMENTS_EXPERIENCE_ID
					},
					[SEGMENTS_EXPERIENCE_ID_SECOND]: {
						name: 'A second test experience',
						segmentsEntryId: 'notRelevantSegmentId',
						segmentsExperienceId: SEGMENTS_EXPERIENCE_ID_SECOND
					}
				};

				const classNameId = 'test-class-name-id';
				const classPK = 'test-class-p-k';

				const prevState = {
					availableSegmentsExperiences,
					classNameId,
					classPK,
					defaultLanguageId: 'en_US',
					defaultSegmentsExperienceId: SEGMENTS_EXPERIENCE_ID_DEFAULT,
					segmentsExperienceId: SEGMENTS_EXPERIENCE_ID_SECOND
				};

				const nextState = {
					...prevState,
					availableSegmentsExperiences: {
						[SEGMENTS_EXPERIENCE_ID_DEFAULT]: prevState.availableSegmentsExperiences[SEGMENTS_EXPERIENCE_ID_DEFAULT],
						[SEGMENTS_EXPERIENCE_ID_SECOND]: prevState.availableSegmentsExperiences[SEGMENTS_EXPERIENCE_ID_SECOND]
					}
				};

				const secondNextState = {
					...nextState,
					availableSegmentsExperiences: {
						[SEGMENTS_EXPERIENCE_ID_DEFAULT]: prevState.availableSegmentsExperiences[SEGMENTS_EXPERIENCE_ID_DEFAULT]
					},
					segmentsExperienceId: SEGMENTS_EXPERIENCE_ID_DEFAULT
				};

				global.Liferay = {
					Service(
						URL,
						{
							segmentsExperienceId
						},
						callbackFunc,
						errorFunc
					) {
						return callbackFunc(
							{
								segmentsExperienceId
							}
						);
					}
				};

				const spy = jest.spyOn(global.Liferay, 'Service');

				deleteSegmentsExperienceReducer(
					prevState,
					DELETE_SEGMENTS_EXPERIENCE,
					{
						segmentsExperienceId: SEGMENTS_EXPERIENCE_ID
					}
				).then(
					state => {
						expect(state).toEqual(nextState);
					}
				);

				expect(spy).toHaveBeenCalledTimes(1);
				expect(spy).toHaveBeenLastCalledWith(
					expect.stringContaining(''),
					{segmentsExperienceId: SEGMENTS_EXPERIENCE_ID},
					expect.objectContaining({}),
					expect.objectContaining({})
				);

				deleteSegmentsExperienceReducer(
					nextState,
					DELETE_SEGMENTS_EXPERIENCE,
					{
						segmentsExperienceId: SEGMENTS_EXPERIENCE_ID_SECOND
					}
				).then(
					state => {
						expect(state).toEqual(secondNextState);
					}
				);

				expect(spy).toHaveBeenCalledTimes(2);
				expect(spy).toHaveBeenLastCalledWith(
					expect.stringContaining(''),
					{segmentsExperienceId: SEGMENTS_EXPERIENCE_ID_SECOND},
					expect.objectContaining({}),
					expect.objectContaining({})
				);
				global.Liferay = prevLiferayGlobal;
			}
		);

		test(
			'editExperience communicates with API and updates the state',
			() => {
				let prevLiferayGlobal = {...global.Liferay};
				global.Liferay = {
					Service(
						URL,
						{
							active,
							nameMap,
							priority,
							segmentsEntryId,
							segmentsExperienceId
						},
						callbackFunc,
						errorFunc
					) {
						return callbackFunc(
							{
								active,
								nameCurrentValue: JSON.parse(nameMap).en_US,
								priority,
								segmentsEntryId,
								segmentsExperienceId
							}
						);
					}
				};

				const availableSegmentsExperiences = {
					[SEGMENTS_EXPERIENCE_ID]: {
						active: true,
						name: 'A test experience',
						priority: 3,
						segmentsEntryId: 'notRelevantSegmentId',
						segmentsExperienceId: SEGMENTS_EXPERIENCE_ID
					},
					[SEGMENTS_EXPERIENCE_ID_DEFAULT]: {
						active: true,
						name: 'A default test experience',
						priority: 3,
						segmentsEntryId: 'notRelevantSegmentId',
						segmentsExperienceId: SEGMENTS_EXPERIENCE_ID
					},
					[SEGMENTS_EXPERIENCE_ID_SECOND]: {
						active: true,
						name: 'A second test experience',
						priority: 3,
						segmentsEntryId: 'notRelevantSegmentId',
						segmentsExperienceId: SEGMENTS_EXPERIENCE_ID_SECOND
					}
				};

				const payload = {
					name: 'A modified test experience',
					segmentsEntryId: 'relevantSegmentId',
					segmentsExperienceId: SEGMENTS_EXPERIENCE_ID
				};
				const prevState = {
					availableSegmentsExperiences,
					defaultLanguageId: 'en_US'
				};

				expect.assertions(3);

				editSegmentsExperienceReducer(
					prevState,
					EDIT_SEGMENTS_EXPERIENCE,
					payload
				).then(
					state => {
						expect(state.availableSegmentsExperiences[SEGMENTS_EXPERIENCE_ID_SECOND]).toEqual(prevState.availableSegmentsExperiences[SEGMENTS_EXPERIENCE_ID_SECOND]);
						expect(state.availableSegmentsExperiences[SEGMENTS_EXPERIENCE_ID_DEFAULT]).toEqual(prevState.availableSegmentsExperiences[SEGMENTS_EXPERIENCE_ID_DEFAULT]);
						expect(state.availableSegmentsExperiences[SEGMENTS_EXPERIENCE_ID]).toEqual(
							{
								...prevState.availableSegmentsExperiences[SEGMENTS_EXPERIENCE_ID],
								name: payload.name,
								segmentsEntryId: payload.segmentsEntryId
							}
						);
					}
				);

				global.Liferay = prevLiferayGlobal;
			}
		);
	}
);