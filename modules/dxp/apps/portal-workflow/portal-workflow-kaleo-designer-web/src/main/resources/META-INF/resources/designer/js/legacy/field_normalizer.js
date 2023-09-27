/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-kaleo-designer-field-normalizer',
	(A) => {
		const AArray = A.Array;
		// eslint-disable-next-line @liferay/aui/no-object
		const AObject = A.Object;
		const Lang = A.Lang;

		const KaleoDesignerRemoteServices = Liferay.KaleoDesignerRemoteServices;

		const isArray = Lang.isArray;
		const isObject = Lang.isObject;
		const isValue = Lang.isValue;

		const STR_BLANK = '';

		const isNotEmptyValue = function (item) {
			return isValue(item) && item !== STR_BLANK;
		};

		const COL_TYPES_ASSIGNMENT = [
			'address',
			'receptionType',
			'resourceActions',
			'roleId',
			'roleType',
			'scriptedAssignment',
			'scriptedRecipient',
			'taskAssignees',
			'user',
			'userId',
		];

		const populateRole = function (assignments) {
			KaleoDesignerRemoteServices.getRole(assignments.roleId, (data) => {
				AArray.each(data, (item) => {
					if (item) {
						const index = assignments.roleId.indexOf(item.roleId);

						assignments.roleNameAC[index] = item.name;
					}
				});
			});
		};

		const populateUser = function (assignments) {
			if (
				isArray(assignments.emailAddress) &&
				assignments.emailAddress.filter(isNotEmptyValue).length !== 0
			) {
				KaleoDesignerRemoteServices.getUser(
					assignments.emailAddress,
					null,
					null,
					(data) => {
						AArray.each(data, (item) => {
							if (item) {
								const index = assignments.emailAddress.indexOf(
									item.emailAddress
								);

								assignments.fullName[index] = item.fullName;
							}
						});
					}
				);
			}
			else if (
				isArray(assignments.screenName) &&
				assignments.screenName.filter(isNotEmptyValue).length !== 0
			) {
				KaleoDesignerRemoteServices.getUser(
					null,
					assignments.screenName,
					null,
					(data) => {
						AArray.each(data, (item) => {
							if (item) {
								const index = assignments.screenName.indexOf(
									item.screenName
								);

								assignments.fullName[index] = item.fullName;
							}
						});
					}
				);
			}
			else if (
				isArray(assignments.userId) &&
				assignments.userId.filter(isNotEmptyValue).length !== 0
			) {
				KaleoDesignerRemoteServices.getUser(
					null,
					null,
					assignments.userId,
					(data) => {
						AArray.each(data, (item) => {
							if (item) {
								const index = assignments.userId.indexOf(
									item.userId
								);

								assignments.fullName[index] = item.fullName;
							}
						});
					}
				);
			}
		};

		const _put = function (object, key, value, index) {
			object[key] = object[key] || [];

			if (index === undefined) {
				object[key].push(value);
			}
			else {
				object[key][index] = value;
			}
		};

		const FieldNormalizer = {
			normalizeToActions(data) {
				const actions = {};

				data = data || {};

				data.forEach((item1, index1) => {
					A.each(item1, (item2, index2) => {
						if (index2 === 'name' || isNotEmptyValue(item2)) {
							_put(actions, index2, item2, index1);
						}
					});
				});

				return actions;
			},

			normalizeToAssignments(data) {
				const assignments = {};

				if (data && data.length) {
					COL_TYPES_ASSIGNMENT.forEach((item1) => {
						const value = data[0][item1];

						if (item1 === 'taskAssignees' && value === '') {
							assignments.assignmentType = 'taskAssignees';
						}

						if (!isNotEmptyValue(value)) {
							return;
						}

						const assignmentValue = AArray(value);

						assignmentValue.forEach((item2, index2) => {
							if (isObject(item2)) {
								A.each(item2, (item3, index3) => {
									_put(assignments, index3, item3, index2);
								});
							}
							else {
								_put(assignments, item1, item2);
							}
						});

						// Reception type is an assignment attribute but never a type of assignment

						if (
							item1 !== 'receptionType' &&
							AArray.some(assignmentValue, (item2) => {
								let valid = isNotEmptyValue(item2);

								if (
									valid &&
									['user', 'roleId', 'roleType'].indexOf(
										item1
									) > -1
								) {
									valid = AArray.some(
										AObject.values(item2),
										isNotEmptyValue
									);
								}

								return valid;
							})
						) {
							assignments.assignmentType = AArray(item1);
						}
					});

					if (assignments.assignmentType === 'roleId') {
						populateRole(assignments);
					}
					else if (assignments.assignmentType === 'user') {
						populateUser(assignments);
					}
				}

				return assignments;
			},

			normalizeToDelays(data) {
				const delays = {};

				data = data || {};

				data.forEach((item1, index1) => {
					A.each(item1, (item2, index2) => {
						if (isNotEmptyValue(item2)) {
							_put(delays, index2, item2, index1);
						}
					});
				});

				return delays;
			},

			normalizeToNotifications(data) {
				const notifications = {};

				data = data || {};

				data.forEach((item1, index1) => {
					A.each(item1, (item2, index2) => {
						if (isNotEmptyValue(item2)) {
							if (index2 === 'recipients') {
								if (item2[0] && item2[0].receptionType) {
									_put(
										notifications,
										'receptionType',
										item2[0].receptionType
									);
								}

								item2 = FieldNormalizer.normalizeToAssignments(
									item2
								);
							}

							_put(notifications, index2, item2, index1);
						}
					});
				});

				return notifications;
			},

			normalizeToTaskTimers(data) {
				const taskTimers = {};

				data = data || {};

				data.forEach((item1, index1) => {
					A.each(item1, (item2, index2) => {
						if (isNotEmptyValue(item2)) {
							if (index2 === 'delay' || index2 === 'recurrence') {
								return;
							}
							else if (index2 === 'timerNotifications') {
								item2 = FieldNormalizer.normalizeToNotifications(
									item2
								);
							}
							else if (index2 === 'timerActions') {
								item2 = FieldNormalizer.normalizeToActions(
									item2
								);
							}
							else if (index2 === 'reassignments') {
								if (item2[0]?.taskAssignees === '') {
									item2[0].taskAssignees = null;
								}

								item2 = FieldNormalizer.normalizeToAssignments(
									item2
								);
							}

							_put(taskTimers, index2, item2, index1);
						}
					});

					const delays = item1.delay.concat(item1.recurrence);

					_put(
						taskTimers,
						'delay',
						FieldNormalizer.normalizeToDelays(delays)
					);
				});

				return taskTimers;
			},
		};

		Liferay.KaleoDesignerFieldNormalizer = FieldNormalizer;
	},
	'',
	{
		requires: ['liferay-kaleo-designer-remote-services'],
	}
);
