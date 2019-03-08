/* globals describe, test, jest, expect, beforeAll, afterAll */

import {createSegmentsExperienceReducer, deleteSegmentsExperienceReducer, endCreateSegmentsExperience, startCreateSegmentsExperience} from '../../../src/main/resources/META-INF/resources/js/reducers/segmentsExperiences.es';
import {CREATE_SEGMENTS_EXPERIENCE, DELETE_SEGMENTS_EXPERIENCE, END_CREATE_SEGMENTS_EXPERIENCE, START_CREATE_SEGMENTS_EXPERIENCE} from '../../../src/main/resources/META-INF/resources/js/actions/actions.es';

const SEGMENTS_EXPERIENCE_ID = 'SEGMENTS_EXPERIENCE_ID';

const SEGMENTS_EXPERIENCE_ID_SECOND = 'SEGMENTS_EXPERIENCE_ID_SECOND';

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
							classNameId,
							classPK,
							segmentsEntryId,
							nameMap,
							active,
							priority
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

				const SEGMENTS_EXPERIENCES_LIST = [SEGMENTS_EXPERIENCE_ID, SEGMENTS_EXPERIENCE_ID_SECOND];

				let experiencesCount = -1;

				const prevState = {
					availableSegmentsExperiences,
					classNameId,
					classPK,
					defaultLanguageId: 'en_US'
				};

				const payload = {
					segmentsEntryId: 'test-segment-id',
					segmentsExperienceLabel: 'test experience label'
				};

				const nextState = {
					...prevState,
					availableSegmentsExperiences: {
						[SEGMENTS_EXPERIENCE_ID]: {
							active: true,
							priority: 0,
							segmentsEntryId: payload.segmentsEntryId,
							segmentsExperienceId: SEGMENTS_EXPERIENCE_ID,
							segmentsExperienceLabel: payload.segmentsExperienceLabel
						}
					},
					segmentsExperienceId: SEGMENTS_EXPERIENCE_ID
				};

				const liferayServiceParams = {
					active: true,
					classNameId: prevState.classNameId,
					classPK: prevState.classPK,
					nameMap: JSON.stringify({en_US: payload.segmentsExperienceLabel}),
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
					segmentsEntryId: 'test-segment-id',
					segmentsExperienceLabel: 'second test experience label'
				};

				const secondNextState = {
					...nextState,
					availableSegmentsExperiences: {
						...nextState.availableSegmentsExperiences,
						[SEGMENTS_EXPERIENCE_ID_SECOND]: {
							active: true,
							priority: 1,
							segmentsEntryId: secondPayload.segmentsEntryId,
							segmentsExperienceId: SEGMENTS_EXPERIENCE_ID_SECOND,
							segmentsExperienceLabel: secondPayload.segmentsExperienceLabel
						}
					},
					segmentsExperienceId: SEGMENTS_EXPERIENCE_ID_SECOND
				};

				const secondLiferayServiceParams = {
					active: true,
					classNameId: prevState.classNameId,
					classPK: prevState.classPK,
					nameMap: JSON.stringify({en_US: secondPayload.segmentsExperienceLabel}),
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
			'startCreateSegmentsExperience and endCreateSegmentsExperience switch states',
			() => {
				const prevState = {};

				const creatingSegmentsExperienceState = startCreateSegmentsExperience(prevState, START_CREATE_SEGMENTS_EXPERIENCE);
				expect(creatingSegmentsExperienceState).toMatchObject(
					{
						experienceSegmentsCreation: {
							creatingSegmentsExperience: true,
							error: null
						}
					}
				);
				const notEdtingState = endCreateSegmentsExperience(creatingSegmentsExperienceState, END_CREATE_SEGMENTS_EXPERIENCE);

				expect(notEdtingState).toMatchObject(
					{
						experienceSegmentsCreation: {
							creatingSegmentsExperience: false,
							error: null
						}
					}
				);
			}
		);

		test(
			'deleteExperience communicates with API and updates the state',
			() => {
				let prevLiferayGlobal = {...global.Liferay};
				const SEGMENTS_EXPERIENCE_ID_DEFAULT = 'SEGMENTS_EXPERIENCE_ID_DEFAULT';

				const availableSegmentsExperiences = {
					[SEGMENTS_EXPERIENCE_ID]: {
						segmentsEntryId: 'notRelevantSegmentId',
						segmentsExperienceId: SEGMENTS_EXPERIENCE_ID,
						segmentsExperienceLabel: 'A test experience'
					},
					[SEGMENTS_EXPERIENCE_ID_DEFAULT]: {
						segmentsEntryId: 'notRelevantSegmentId',
						segmentsExperienceId: SEGMENTS_EXPERIENCE_ID,
						segmentsExperienceLabel: 'A default test experience'
					},
					[SEGMENTS_EXPERIENCE_ID_SECOND]: {
						segmentsEntryId: 'notRelevantSegmentId',
						segmentsExperienceId: SEGMENTS_EXPERIENCE_ID_SECOND,
						segmentsExperienceLabel: 'A second test experience'
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
	}
);